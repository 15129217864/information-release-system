package com.xct.cms.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import com.linsoft.xml.dom4j.Dom4j;
import com.xct.cms.utils.Util;

public class ProjectInfo {
	static Logger logger = Logger.getLogger(ProjectInfo.class);
	private String name="";
	private String type="";
	private String x="";
	private String y="";
	private String width="";
	private String height="";
	private String background="";
	private String foreground="";
	private String span="";
	private String fontName="";
	private String fontTyle="";
	private String fontSize="";
	private String alpha="";
	private List panefileList=new ArrayList();
	
	private String program_backimage="";
	private String program_backmusic="";
	
	public String getProgram_backimage() {
		return program_backimage;
	}
	public void setProgram_backimage(String program_backimage) {
		this.program_backimage = program_backimage;
	}
	public String getProgram_backmusic() {
		return program_backmusic;
	}
	public void setProgram_backmusic(String program_backmusic) {
		this.program_backmusic = program_backmusic;
	}
	public String getAlpha() {
		return alpha;
	}
	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public String getFontSize() {
		return fontSize;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontTyle() {
		return fontTyle;
	}
	public void setFontTyle(String fontTyle) {
		this.fontTyle = fontTyle;
	}
	public String getForeground() {
		return foreground;
	}
	public void setForeground(String foreground) {
		this.foreground = foreground;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpan() {
		return span;
	}
	public void setSpan(String span) {
		this.span = span;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		String[] typelist=Util.readfile(Util.getOSPath()+"/type_paramater.txt").split("#");
		for(int i=0;i<typelist.length;i++){
			String[] types=typelist[i].split("-");
			if(types[0].equals(type)){
				this.type = types[1];
				return;
			}
		}
		this.type = type;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	

	public List getPanefileList() {
		return panefileList;
	}
	public void setPanefileList(List panefileList) {
		this.panefileList = panefileList;
	}
	public static ProjectInfo getPaneForElement(Element elment) {
		ProjectInfo info=new ProjectInfo();
		info.setAlpha(elment.attributeValue("alpha").trim());
		info.setBackground(elment.attributeValue("background").trim());
		info.setFontName(elment.attributeValue("fontName").trim());
		info.setFontSize(elment.attributeValue("fontSize").trim());
		info.setFontTyle(elment.attributeValue("fontTyle").trim());
		info.setForeground(elment.attributeValue("foreground").trim());
		info.setHeight(elment.attributeValue("height").trim());
		info.setSpan(elment.attributeValue("span").trim());
		info.setType(elment.attributeValue("type").trim());
		info.setWidth(elment.attributeValue("width").trim());
		info.setX(elment.attributeValue("x").trim());
		info.setY(elment.attributeValue("y").trim());
		info.setName(elment.attributeValue("name").trim());

		return info;
}
	public static List getPane(String strpath){
		try{
		Dom4j dom = new Dom4j(strpath);
		Iterator iterator = dom.getDocument().selectNodes("/xct/pane").iterator();
		List list= new ArrayList();
		while (iterator.hasNext()) {
			Element elment = (Element) iterator.next();
			ProjectInfo info = getPaneForElement(elment);
			Iterator iterator1 = elment.elementIterator("file");
			List panefileList=new ArrayList();
			while (iterator1.hasNext()) {
				Element e = (Element) iterator1.next();
				String [] panefile=new String[3];
				panefile[0]=e.attributeValue("fileName").trim();
				panefile[1]=e.attributeValue("filePath").trim();
				panefile[2]=e.attributeValue("length").trim();
				panefileList.add(panefile);
			}
			info.setPanefileList(panefileList);
			list.add(info);
		}
		return list;
		}catch(Exception e){
			logger.error(new StringBuffer().append("读取/xct/pane子节点出错，请确认").append(strpath).append("文件是否存在！").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		return null;
	}	
	public static ProjectInfo getProgramInfo(String strpath){
		try{
			Dom4j dom = new Dom4j(strpath);
			Iterator iterator = dom.getDocument().selectNodes("/xct").iterator();
			ProjectInfo info = new ProjectInfo();
			while (iterator.hasNext()) {
				Element elment = (Element) iterator.next();
				info.setProgram_backimage(elment.attributeValue("backImage").trim());
				info.setProgram_backmusic(elment.attributeValue("backMusic").trim());
			}
			return info;
			}catch(Exception e){
				logger.error(new StringBuffer().append("读取/xct子节点出错，请确认").append(strpath).append("文件是否存在！").append(e.getMessage()).toString());
				e.printStackTrace();
			}
			return null;
		}
	public static boolean updateProjectXctAttribute(String projectpath,String xctAttribute,String xctAttributeValue) { ///修改file属性
		try{
			Dom4j dom = new Dom4j(projectpath);
			Document document =dom.getDocument();
			Iterator iterator = document.selectNodes("/xct").iterator();
			while (iterator.hasNext()) {
				Element elment = (Element) iterator.next();
				elment.addAttribute(xctAttribute, xctAttributeValue);
				dom.formatXMLFile();
			}
			return true;
			}catch(Exception e){
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			return false;
	}
	public static List getPaneFile(String strpath,String panename){
		try{
			Dom4j dom = new Dom4j(strpath);
			Iterator iterator = dom.getDocument().selectNodes("/xct/pane").iterator();
			List list= new ArrayList();
			while (iterator.hasNext()) {
				Element elment = (Element) iterator.next();
				ProjectInfo info = getPaneForElement(elment);
				list.add(info);
			}
			return list;
			}catch(Exception e){
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			return null;
		
	}
	public static boolean deleteProjectChild(String strpath,String panename,String filepath) { ///删除file
		try{
			Dom4j dom = new Dom4j(strpath);
			Document document =dom.getDocument();
			Iterator iterator = document.selectNodes("/xct/pane").iterator();
			while (iterator.hasNext()) {
				Element elment = (Element) iterator.next();
				if(elment.attributeValue("type").trim().equals(panename)){
					Iterator iterator1 = elment.elementIterator("file");
					while (iterator1.hasNext()) {
						Element e = (Element) iterator1.next();
						if(e.attributeValue("filePath").trim().equals(filepath)){
							e.detach();
							dom.formatXMLFile();
						}
					}
				}
			}
			return true;
			}catch(Exception e){
				logger.info(e.getMessage());
				e.printStackTrace();
			}
			return false;
	}
	
	public static boolean updateProjectChildAttribute(String strpath,String panename,String newfilename,String filepath,String newfilepath) { ///修改file属性
		try{
			Dom4j dom = new Dom4j(strpath);
			Document document =dom.getDocument();
			Iterator iterator = document.selectNodes("/xct/pane").iterator();
			while (iterator.hasNext()) {
				Element elment = (Element) iterator.next();
				if(elment.attributeValue("type").trim().equals(panename)){
					Iterator iterator1 = elment.elementIterator("file");
					while (iterator1.hasNext()) {
						Element e = (Element) iterator1.next();
						if(e.attributeValue("filePath").trim().equals(filepath)){
							e.addAttribute("fileName", newfilename);
							e.addAttribute("filePath", newfilepath);
							dom.formatXMLFile();
						}
					}
				}
			}
			return true;
			}catch(Exception e){
				logger.info(e.getMessage());
				e.printStackTrace();
			}
			return false;
	}
	
	public static boolean updateProjectPaneAttribute(String strpath,String panename,String attribute,String attributeValue) { ///修改file属性
		try{
			Dom4j dom = new Dom4j(strpath);
			Document document =dom.getDocument();
			Iterator iterator = document.selectNodes("/xct/pane").iterator();
			while (iterator.hasNext()) {
				Element elment = (Element) iterator.next();
				if(elment.attributeValue("type").trim().equals(panename)){
					elment.addAttribute(attribute, attributeValue);
					dom.formatXMLFile();
				}
			}
			return true;
			}catch(Exception e){
				logger.info(e.getMessage());
				e.printStackTrace();
			}
			return false;
	}
	
	public static boolean addProjectChild(String strpath,String panename,String newfilename,String newfilepath,String newlength) { ///修改file属性
		try{
			Dom4j dom = new Dom4j(strpath);
			Document document =dom.getDocument();
			Iterator iterator = document.selectNodes("/xct/pane").iterator();
			while (iterator.hasNext()) {
				Element elment = (Element) iterator.next();
				if(elment.attributeValue("type").trim().equals(panename)){
					Element child =elment.addElement("file");
					child.addAttribute("fileName", newfilename);
					child.addAttribute("filePath", newfilepath);
					child.addAttribute("length", newlength);
					dom.formatXMLFile();
				}
			}
			return true;
			}catch(Exception e){
				logger.info(e.getMessage());
				e.printStackTrace();
			}
			return false;
	}
	
	public static int getMplayerLength(String file) {//计算视频时长,挺准,windows管用

		int length = 0;
		String path=Util.getOSPath().split("WEB-INF")[0];
		String mplayer = path+"/mplayer/mplayer.exe  -identify  -nosound -vc dummy -vo null |ID_LENGTH "
				+ file;
		try {
			Process process = Runtime.getRuntime().exec(mplayer + "\n");
			InputStream is = process.getInputStream();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(is));
			String line;
			for (; (line = stdout.readLine()) != null;) {
				if (line.startsWith("ID_LENGTH")) {
					length = (int) Double.parseDouble(line.split("=")[1]);
				}
			}
			stdout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return length;
	}
	
	public static int writeXML(String fileName,Document document){   
		int returnValue = 0 ;   
		
		try  
		{   
			XMLWriter writer = new XMLWriter(new FileWriter(new File(fileName)));   
			writer.write(document);   
			writer.close();   
			returnValue = 1 ;   
		}   
		catch(Exception e)   
		{   
			logger.info(e.getMessage());
			returnValue = 0 ;   
		}      
		return returnValue ;   
		}  
	public static void main(String [] args){
		addProjectChild("C:/xct/data/20090930100803/20090930100803.xml","image","11111","01.jpg","88888");
		///////System.out.println(getPane("C:/xct/data/20090930100803/20090930100803.xml").size());
	}
}
