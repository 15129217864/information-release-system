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
		//加入定时和循环节目
		clientprojectbeanlist=managerprojectdao.createMacXmlOnWeeHours(/*new SimpleDateFormat("yyyy-MM-dd").format(new Date()),*/true);
        
		if(null!=maclist&&!maclist.isEmpty()){
			if(null!=clientprojectbeanlist&&!clientprojectbeanlist.isEmpty()){
				boolean flagB=false;
				for(String mac: maclist){
					
					for(ClientProjectBean clientprojectbean:clientprojectbeanlist){
						if(mac.equals(clientprojectbean.getPlayclient())){
							macfile=new File(new StringBuffer().append(System.getProperty("MAC_HOME")).append(mac).append(".xml").toString());
//							logger.info("---WeeHoursCreateProjectXmlObserver.update-----定时生成节目单，用于第二天下载-------->"+clientprojectbean+"__"+macfile);
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

//			加入永久循环节目,暂时搁置，这是以前每天生成永久循环节目，如果客户删除了永久循环节目，到第二天重新生成永久循环节目，误认为删除永久循环不成功的现象
//			clientprojectbeanlistforerver=managerprojectdao.createMacXmlOnWeeHours(/*new SimpleDateFormat("yyyy-MM-dd").format(new Date()),*/false);/*createMacXmlOnWeeHoursForeverProject();*/
//			////现在永久循环只要下发下去，终端会每天生成当天的永久循环节目单，不需要再从服务端下载了
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
	 * 拷贝一个文件到另外一个给定的位置
	 * 
	 * @param from
	 *            被拷贝的文件
	 * @param to
	 *            拷贝到这个文件
	 * @return 如果成功返回真，反之假
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
				logger.info(from + "不是一个文件！");
				return false;
			}
		} else {
			logger.info(from + "不存在！");
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
		
		logger.info(new  StringBuffer().append("---WeeHoursCreateProjectXmlObserver.createMacXml---准备生成节目单:").append(macfilename).append("-->").append(clientprojectbean).toString());
//		File macFile2=new File(macfile);
		StringBuffer sb=new StringBuffer();
//		sb.append(new File(macFile2.getParent()).getParent()).append(File.separator).append(clientprojectbean.getJmurl());
		sb.append(System.getProperty("XCT_FTP_HOME")).append(clientprojectbean.getJmurl());
		
		Document document=DocumentHelper.createDocument();
		Element element =document.addElement("project");
		if(bool){
			if(new File(sb.toString()).exists()){
				logger.info(new  StringBuffer().append("---WeeHoursCreateProjectXmlObserver.createMacXml---生成节目单:").append(macfilename).append("-->").append(clientprojectbean).toString());
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
//				bool=false;//把当天过期的定时和插播节目屏蔽掉
//				System.out.println("--addMacXml-把当天过期的定时和插播节目屏蔽掉-------->"+clientprojectbean.getName()+"-"+clientprojectbean.getPlaytype());
//			}
//		}
		String macfilename=new File(macfile).getName();
//		有时插播不写入节目单
		//把节目单文件中相同的节目 不加入节目单
		List<ClientProjectBean> getclientprojectbeanlist= getClientProjectBeanlist(macfile);
		for(ClientProjectBean clientprojectbeantmp:getclientprojectbeanlist){
			if(clientprojectbeantmp.equals(clientprojectbean)){
				bool=false;
			}
		}
		logger.info(new  StringBuffer().append("---WeeHoursCreateProjectXmlObserver.addMacXml---准备加入节目单---3---").append(bool).append("-->").append(macfilename).append("-->").append(clientprojectbean).toString());
		if(bool){
			logger.info(new  StringBuffer().append("---WeeHoursCreateProjectXmlObserver.addMacXml---加入节目单---4-->").append(macfilename).append("-->").append(clientprojectbean).toString());
//			File macFile2=new File(macfile);
			StringBuffer sb=new StringBuffer();
//			sb.append(new File(macFile2.getParent()).getParent()).append(File.separator).append(clientprojectbean.getJmurl());
			sb.append(System.getProperty("XCT_FTP_HOME")).append(clientprojectbean.getJmurl());
			
			if(new File(sb.toString()).exists()){
//				logger.info("---WeeHoursCreateProjectXmlObserver.createMacXml--准备添加节目单--------");
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
				logger.info("____添加节目单时，节目文件夹不存在———〉"+clientprojectbean);
			}
		}
	}
	
	
	public void addMacXml1(String macfile,ClientProjectBean clientprojectbean){////创建永久循环  验证
		
		boolean bool=true;
//		datetime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//		if("active".equals(clientprojectbean.getPlaytype())||"insert".equals(clientprojectbean.getPlaytype())){
//			if(clientprojectbean.getEnddate().replace(".0", "").compareTo(datetime)<0){
//				bool=false;//把当天过期的定时和插播节目屏蔽掉
//				System.out.println("--addMacXml-把当天过期的定时和插播节目屏蔽掉-------->"+clientprojectbean.getName()+"-"+clientprojectbean.getPlaytype());
//			}
//		}
		String macfilename=new File(macfile).getName();
		
		//把节目单文件中相同的节目 不加入节目单
		List<ClientProjectBean> getclientprojectbeanlist= getClientProjectBeanlist(macfile);
		for(ClientProjectBean clientprojectbeantmp:getclientprojectbeanlist){
			if(clientprojectbeantmp.equals1(clientprojectbean)){
				bool=false;
			}
		}
		logger.info(new  StringBuffer().append("---WeeHoursCreateProjectXmlObserver.addMacXml1---准备加入节目单---1--").append(bool).append("-->").append(macfilename).append("-->").append(clientprojectbean).toString());
//		logger.info("_____添加节目单(创建永久循环节目单)——addMacXml1—〉"+clientprojectbean+"------>"+bool);
		if(bool){
			logger.info(new  StringBuffer().append("---WeeHoursCreateProjectXmlObserver.addMacXml1---加入节目单---2-->").append(macfilename).append("-->").append(clientprojectbean).toString());
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
