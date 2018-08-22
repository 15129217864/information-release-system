package com.xct.cms.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.xct.cms.action.LoginAction;
import com.xct.cms.domin.ProjectBean;
import com.xct.cms.servlet.FirstStartServlet;

public class ReadMacXmlUtils {
	Logger logger = Logger.getLogger(LoginAction.class);
  public static  Date getAfterDate(int n){ 
		 
		  Calendar c = Calendar.getInstance(); 
		  c.add(Calendar.DAY_OF_MONTH, n); 
		  return c.getTime(); 
  } 
	
  public void getProjectFromMacXml(String macxml,ProjectBean projectbean,String isforover,boolean bool){
	  
	  if(new File(macxml).exists()&&null!=projectbean){
		  SAXReader saxreader=new SAXReader();
		  Document document = null;
		  try {
			document=saxreader.read(macxml);
			List list=document.selectNodes("/project/list");
			ProjectBean projectbeantmp;
			for(int i=0,n=list.size();i<n;i++){
				Element element=(Element)list.get(i);
				projectbeantmp =getProjectForElement(element);
				
//				System.out.println(projectbean+"<====>"+projectbeantmp);
//				System.out.println(projectbean.getDirectName()+"<---->"+projectbeantmp.getDirectName());
				if(projectbean.equals(projectbeantmp)
//						终端返回的节目单和服务器上有差别时，依旧删除永久循环
						||(projectbean.getDirectName().equals(projectbeantmp.getDirectName())&&projectbean.getType().equals("defaultloop"))
						||(bool&&projectbean.getDirectName().equals(projectbeantmp.getDirectName()))&&"y".equals(isforover))
				   element.detach();
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GBK");
			XMLWriter output = new XMLWriter(new FileWriter(new File(macxml)), format);
			output.write(document);
			output.close();
			
		} catch (DocumentException e1) {
			e1.printStackTrace();
			logger.error(new StringBuffer().append("删除节目单时读取mac xml文件有错------>").append(e1.getMessage()).toString());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(new StringBuffer().append("删除节目单时保存mac xml文件有错------>").append(e.getMessage()).toString());
		}
	  }
  }
  public void deleteMacXml(String mac,String jmappid){
	  String macxml=FirstStartServlet.projectpath+"serverftp\\program_file\\"+mac+".xml";
	  if(new File(macxml).exists()){
		  SAXReader saxreader=new SAXReader();
		  Document document = null;
		  try {
			document=saxreader.read(macxml);
			List list=document.selectNodes("/project/list");
			ProjectBean projectbeantmp;
			for(int i=0,n=list.size();i<n;i++){
				Element element=(Element)list.get(i);
				projectbeantmp =getProjectForElement(element);
				
//				System.out.println(projectbean+"<====>"+projectbeantmp);
//				System.out.println(projectbean.getDirectName()+"<---->"+projectbeantmp.getDirectName());
				if(jmappid.equals(projectbeantmp.getJmappid()+""))
				   element.detach();
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GBK");
			XMLWriter output = new XMLWriter(new FileWriter(new File(macxml)), format);
			output.write(document);
			output.close();
		} catch (DocumentException e1) {
			logger.error(new StringBuffer().append("删除节目单时读取mac xml文件有错------>").append(e1.getMessage()).toString());
			e1.printStackTrace();
		} catch (IOException e) {
			logger.error(new StringBuffer().append("删除节目单时保存mac xml文件有错------>").append(e.getMessage()).toString());
			e.printStackTrace();
		}
	  }
  }
  
  public  ProjectBean getProjectForElement(Element elment) {

		String type = elment.attributeValue("type").trim();
		String userName = elment.attributeValue("userName").trim();
		String directName = elment.attributeValue("directName").trim();
		String beginTimer = elment.attributeValue("beginTimer").trim();
		String length = elment.attributeValue("length").trim();
		int jmappid=Integer.parseInt(elment.attributeValue("jmappid").trim());
		return new ProjectBean(userName, directName, beginTimer, type, length,jmappid);     
	}
}
