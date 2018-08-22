package com.xct.cms.observer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.xct.cms.utils.CreatMacXmlUtils;
import com.xct.cms.xy.dao.GetAllClinetDao;
import com.xct.cms.xy.dao.ManagerProjectDao;
import com.xct.cms.xy.domain.ClientProjectBean;

public class WeeHoursCreateProjectXmlObserver implements Observer {

	static Logger logger = Logger.getLogger(WeeHoursCreateProjectXmlObserver.class);
	
	GetAllClinetDao getallclinetdao=new GetAllClinetDao();
	ManagerProjectDao managerprojectdao=new ManagerProjectDao();
	List<ClientProjectBean> clientprojectbeanlist=null;
	List<ClientProjectBean> clientprojectbeanlistforerver=null;
	List<String> maclist=null;
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		
		maclist=getallclinetdao.getClientAllMac();
		File macfile=null;
		for(String mac: maclist){
			macfile=new File(new StringBuffer().append(System.getProperty("MAC_HOME_TEMP")).append(mac).append(".xml").toString());
			if(macfile.exists())
				macfile.delete();
		}
		//���붨ʱ��ѭ����Ŀ
		clientprojectbeanlist=managerprojectdao.createMacXmlOnWeeHours(/*new SimpleDateFormat("yyyy-MM-dd").format(new Date()),*/true);
        
		if(null!=maclist&&!maclist.isEmpty()){
			if(null!=clientprojectbeanlist&&!clientprojectbeanlist.isEmpty()){
				boolean flagB=false;
				for(String mac: maclist){
					
					for(ClientProjectBean clientprojectbean:clientprojectbeanlist){
						if(mac.equals(clientprojectbean.getPlayclient())){
							macfile=new File(new StringBuffer().append(System.getProperty("MAC_HOME")).append(mac).append(".xml").toString());
//							logger.info("---WeeHoursCreateProjectXmlObserver.update-----��ʱ���ɽ�Ŀ�������ڵڶ�������-------->"+clientprojectbean+"__"+macfile);
//							macfile=new File(System.getProperty("MAC_HOME_TEMP")+mac+".xml");
							if(!macfile.exists()){
								createMacXml(macfile.getPath(),clientprojectbean);
								flagB=true;
							}else{
								addMacXml(macfile.getPath(),clientprojectbean);
								flagB=true;
							}
						}
					}
//					if(flagB){
//					    copyTo(System.getProperty("MAC_HOME_TEMP")+mac+".xml",System.getProperty("MAC_HOME")+mac+".xml");
//					}
				}
			}

//			��������ѭ����Ŀ,��ʱ���ã�������ǰÿ����������ѭ����Ŀ������ͻ�ɾ��������ѭ����Ŀ�����ڶ���������������ѭ����Ŀ������Ϊɾ������ѭ�����ɹ�������
//			clientprojectbeanlistforerver=managerprojectdao.createMacXmlOnWeeHours(/*new SimpleDateFormat("yyyy-MM-dd").format(new Date()),*/false);/*createMacXmlOnWeeHoursForeverProject();*/
//			////��������ѭ��ֻҪ�·���ȥ���ն˻�ÿ�����ɵ��������ѭ����Ŀ��������Ҫ�ٴӷ����������
//			if(null!=clientprojectbeanlistforerver&&!clientprojectbeanlistforerver.isEmpty()){
//				for(String mac: maclist){
//					for(ClientProjectBean clientprojectbean:clientprojectbeanlistforerver){
//						if(mac.equals(clientprojectbean.getPlayclient())){
//							
//							File macfile=new File(System.getProperty("MAC_HOME")+mac+".xml");
//							String datestring=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//							if(!macfile.exists()){
//								clientprojectbean.setSetdate(datestring+" "+clientprojectbean.getSetdate().split(" ")[1]);
//								clientprojectbean.setEnddate(datestring+" "+clientprojectbean.getEnddate().split(" ")[1]);
//							    createMacXml(macfile.getPath(),clientprojectbean);
//							}else{
//								clientprojectbean.setSetdate(datestring+" "+clientprojectbean.getSetdate().split(" ")[1]);
//								clientprojectbean.setEnddate(datestring+" "+clientprojectbean.getEnddate().split(" ")[1]);
//								addMacXml(macfile.getPath(),clientprojectbean);
//							}
//						}
//					}
//				 }
//			}
		}
	}
	
	/**
	 * ����һ���ļ�������һ��������λ��
	 * 
	 * @param from
	 *            ���������ļ�
	 * @param to
	 *            ����������ļ�
	 * @return ����ɹ������棬��֮��
	 */
	public static boolean copyTo(String from, String to) {

		File f = new File(from);
		if (f.exists()) {

			if (f.isFile()) {

				File Dirto = new File(to).getParentFile();
				if (!Dirto.exists())
					Dirto.mkdir();

				FileInputStream input;

				try {
					input = new FileInputStream(f);
					FileOutputStream output = new FileOutputStream(to);
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("[ReadFileFactory-copyTo]Is error by copy file ");
					return false;
				}

			} else {
				logger.info(from + "����һ���ļ���");
				return false;
			}
		} else {
			logger.info(from + "�����ڣ�");
			return false;
		}
		return true;
	}

	
	public void createMacXml(String macfile,ClientProjectBean clientprojectbean){
		
		boolean bool=true;

//		List<ClientProjectBean> getclientprojectbeanlist= getClientProjectBeanlist(macfile);
//		for(ClientProjectBean clientprojectbeantmp:getclientprojectbeanlist){
//			if(clientprojectbeantmp.equals(clientprojectbean)){
//				bool=false;
//			}
//		}
		String macfilename=new File(macfile).getName();
		
		logger.info(new  StringBuffer().append("---WeeHoursCreateProjectXmlObserver.createMacXml---׼�����ɽ�Ŀ��:").append(macfilename).append("-->").append(clientprojectbean).toString());
//		File macFile2=new File(macfile);
		StringBuffer sb=new StringBuffer();
//		sb.append(new File(macFile2.getParent()).getParent()).append(File.separator).append(clientprojectbean.getJmurl());
		sb.append(System.getProperty("XCT_FTP_HOME")).append(clientprojectbean.getJmurl());
		
		Document document=DocumentHelper.createDocument();
		Element element =document.addElement("project");
		if(bool){
			if(new File(sb.toString()).exists()){
				logger.info(new  StringBuffer().append("---WeeHoursCreateProjectXmlObserver.createMacXml---���ɽ�Ŀ��:").append(macfilename).append("-->").append(clientprojectbean).toString());
			       element.addElement("list")
					  .addAttribute("userName",clientprojectbean.getName())
				      .addAttribute("directName",clientprojectbean.getJmurl())
				      .addAttribute("beginTimer",clientprojectbean.getSetdate().replace(".0", ""))
				      .addAttribute("endTimer",clientprojectbean.getEnddate().replace(".0", ""))
					  .addAttribute("type",clientprojectbean.getPlaytype())
					  .addAttribute("length", clientprojectbean.getPlaysecond())
					  .addAttribute("isforover", clientprojectbean.getForoverloop())
				      .addAttribute("jmappid", clientprojectbean.getJmappid()+"");		
			}
		}
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GBK");
			XMLWriter output = new XMLWriter(new FileWriter(macfile), format);
			output.write(document);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<ClientProjectBean> getClientProjectBeanlist(String macfile){
		
	        List<ClientProjectBean> clientprojectbeanlist=new ArrayList<ClientProjectBean>();
		    SAXReader saxreader =new SAXReader();
			Document document;
			try {
				document = saxreader.read(macfile);
				Element element=null;
				List list=document.selectNodes("/project/list");
				for(int i=0,n=list.size();i<n;i++){
					element=(Element)list.get(i);
					clientprojectbeanlist.add(new ClientProjectBean(element.attributeValue("userName"),
							element.attributeValue("directName"),
							element.attributeValue("beginTimer"),
							element.attributeValue("type"),
							element.attributeValue("length")));
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			return clientprojectbeanlist;
	}
	
//	String datetime;
	
	public void addMacXml(String macfile,ClientProjectBean clientprojectbean){
		
		boolean bool=true;
//		datetime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//		if("active".equals(clientprojectbean.getPlaytype())||"insert".equals(clientprojectbean.getPlaytype())){
//			if(clientprojectbean.getEnddate().replace(".0", "").compareTo(datetime)<0){
//				bool=false;//�ѵ�����ڵĶ�ʱ�Ͳ岥��Ŀ���ε�
//				System.out.println("--addMacXml-�ѵ�����ڵĶ�ʱ�Ͳ岥��Ŀ���ε�-------->"+clientprojectbean.getName()+"-"+clientprojectbean.getPlaytype());
//			}
//		}
		String macfilename=new File(macfile).getName();
//		��ʱ�岥��д���Ŀ��
		//�ѽ�Ŀ���ļ�����ͬ�Ľ�Ŀ �������Ŀ��
		List<ClientProjectBean> getclientprojectbeanlist= getClientProjectBeanlist(macfile);
		for(ClientProjectBean clientprojectbeantmp:getclientprojectbeanlist){
			if(clientprojectbeantmp.equals(clientprojectbean)){
				bool=false;
			}
		}
		logger.info(new  StringBuffer().append("---WeeHoursCreateProjectXmlObserver.addMacXml---׼�������Ŀ��---3---").append(bool).append("-->").append(macfilename).append("-->").append(clientprojectbean).toString());
		if(bool){
			logger.info(new  StringBuffer().append("---WeeHoursCreateProjectXmlObserver.addMacXml---�����Ŀ��---4-->").append(macfilename).append("-->").append(clientprojectbean).toString());
//			File macFile2=new File(macfile);
			StringBuffer sb=new StringBuffer();
//			sb.append(new File(macFile2.getParent()).getParent()).append(File.separator).append(clientprojectbean.getJmurl());
			sb.append(System.getProperty("XCT_FTP_HOME")).append(clientprojectbean.getJmurl());
			
			if(new File(sb.toString()).exists()){
//				logger.info("---WeeHoursCreateProjectXmlObserver.createMacXml--׼����ӽ�Ŀ��--------");
				SAXReader saxreader =new SAXReader();
				try {
					Document document=saxreader.read(macfile);
					Element element= document.getRootElement();
					element.addElement("list")
					.addAttribute("userName",clientprojectbean.getName())
					.addAttribute("directName",clientprojectbean.getJmurl())
					.addAttribute("beginTimer",clientprojectbean.getSetdate().replace(".0", ""))
					.addAttribute("endTimer",clientprojectbean.getEnddate().replace(".0", ""))
					.addAttribute("type",clientprojectbean.getPlaytype())
					.addAttribute("length", clientprojectbean.getPlaysecond())
					.addAttribute("isforover", clientprojectbean.getForoverloop())
					.addAttribute("jmappid", clientprojectbean.getJmappid()+"");	
					OutputFormat format=OutputFormat.createCompactFormat();
					format.setEncoding("GBK");
					XMLWriter xmlwriter=new XMLWriter(new FileWriter(new File(macfile)),format);
					xmlwriter.write(document);
					xmlwriter.close();
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				logger.info("____��ӽ�Ŀ��ʱ����Ŀ�ļ��в����ڡ�������"+clientprojectbean);
			}
		}
	}
	
	
	public void addMacXml1(String macfile,ClientProjectBean clientprojectbean){////��������ѭ��  ��֤
		
		boolean bool=true;
//		datetime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//		if("active".equals(clientprojectbean.getPlaytype())||"insert".equals(clientprojectbean.getPlaytype())){
//			if(clientprojectbean.getEnddate().replace(".0", "").compareTo(datetime)<0){
//				bool=false;//�ѵ�����ڵĶ�ʱ�Ͳ岥��Ŀ���ε�
//				System.out.println("--addMacXml-�ѵ�����ڵĶ�ʱ�Ͳ岥��Ŀ���ε�-------->"+clientprojectbean.getName()+"-"+clientprojectbean.getPlaytype());
//			}
//		}
		String macfilename=new File(macfile).getName();
		
		//�ѽ�Ŀ���ļ�����ͬ�Ľ�Ŀ �������Ŀ��
		List<ClientProjectBean> getclientprojectbeanlist= getClientProjectBeanlist(macfile);
		for(ClientProjectBean clientprojectbeantmp:getclientprojectbeanlist){
			if(clientprojectbeantmp.equals1(clientprojectbean)){
				bool=false;
			}
		}
		logger.info(new  StringBuffer().append("---WeeHoursCreateProjectXmlObserver.addMacXml1---׼�������Ŀ��---1--").append(bool).append("-->").append(macfilename).append("-->").append(clientprojectbean).toString());
//		logger.info("_____��ӽ�Ŀ��(��������ѭ����Ŀ��)����addMacXml1����"+clientprojectbean+"------>"+bool);
		if(bool){
			logger.info(new  StringBuffer().append("---WeeHoursCreateProjectXmlObserver.addMacXml1---�����Ŀ��---2-->").append(macfilename).append("-->").append(clientprojectbean).toString());
			SAXReader saxreader =new SAXReader();
			try {
				Document document=saxreader.read(macfile);
				Element element= document.getRootElement();
				element.addElement("list")
				.addAttribute("userName",clientprojectbean.getName())
				.addAttribute("directName",clientprojectbean.getJmurl())
				.addAttribute("beginTimer",clientprojectbean.getSetdate().replace(".0", ""))
				.addAttribute("endTimer",clientprojectbean.getEnddate().replace(".0", ""))
				.addAttribute("type",clientprojectbean.getPlaytype())
				.addAttribute("length", clientprojectbean.getPlaysecond())
				.addAttribute("isforover", clientprojectbean.getForoverloop())
				.addAttribute("jmappid", clientprojectbean.getJmappid()+"");	
				OutputFormat format=OutputFormat.createCompactFormat();
				format.setEncoding("GBK");
				XMLWriter xmlwriter=new XMLWriter(new FileWriter(new File(macfile)),format);
				xmlwriter.write(document);
				xmlwriter.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
