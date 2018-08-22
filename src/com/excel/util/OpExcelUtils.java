package com.excel.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.excel.bean.ExcelsBeans;
import com.excel.bean.NoticeBeanKH;
import com.linsoft.xml.dom4j.Dom4j;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.xct.cms.dao.UsersDAO;
import com.xct.cms.domin.PoliceSubstationBean;
import com.xct.cms.servlet.FirstStartServlet;

import jxl.Sheet;
import jxl.Workbook;

public class OpExcelUtils {
	
	public List<String> getImportUserinfo(String xsl){
		Workbook rwb = null;
		List<String>list=new ArrayList<String>();
		try {
			InputStream input = new FileInputStream(new File(xsl));
			rwb = Workbook.getWorkbook(input);
			int sheetcounts=rwb.getSheets().length;
			String tempstring="";
//			System.out.println("����sheet����-----> "+sheetcounts);
			if(sheetcounts>0){
				 Sheet sheet = rwb.getSheet(0);
				 int rows = sheet.getRows();
				 int cells = sheet.getColumns();	
				 for (int i = 1; i < rows; i++) {
					 StringBuffer sBuffer=new StringBuffer();
					for (int j = 0; j < cells; ++j) {
						tempstring=sheet.getCell(j, i).getContents();
						sBuffer.append(tempstring).append("###");
					}
					if(!sBuffer.equals(""))
				      list.add(sBuffer.toString().substring(0,sBuffer.toString().length()-3));
				}
			}
//			for(String s:list){
//				System.out.println(s);
//			}
			input.close();
			rwb.close();
		} catch (Exception e) {
			if(null!=rwb)
				rwb.close();
//			System.out.println("����Excel����------->"+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	//�����ɳ���
	public void getPolicesubstation(String homepath ,String xsl,String xmlfile){
		Workbook rwb = null;
		try {
			InputStream input = new FileInputStream(new File(xsl));
			rwb = Workbook.getWorkbook(input);
			int sheetcounts=rwb.getSheets().length;
//			System.out.println("����sheet����-----> "+sheetcounts);
			if(sheetcounts>0){
	            for(int k=0,kn=sheetcounts;k<kn;k++){
	            	 List<PoliceSubstationBean> list=createPolicesubstationList(rwb,k);
	            	if(!list.isEmpty()){
	            		createPolicesubstationXml(list,homepath+xmlfile);
	            	}
	            }
			}
			input.close();
			rwb.close();
		} catch (Exception e) {
			if(null!=rwb)
				rwb.close();
//			System.out.println("����Excel����------->"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private  List<PoliceSubstationBean> createPolicesubstationList(Workbook rwb,int k) {
		    Sheet sheet = rwb.getSheet(k);
		    List<PoliceSubstationBean>list=new ArrayList<PoliceSubstationBean>();
            List<String>list2=new ArrayList<String>();
			String tempstring="";
			 int rows = sheet.getRows();
			 int cells = sheet.getColumns();	
			 for (int i = 1; i < rows; i++) {
				 StringBuffer sBuffer=new StringBuffer();
				for (int j = 0; j < cells; ++j) {
					tempstring=sheet.getCell(j, i).getContents();
					sBuffer.append(tempstring).append("@#@");
				}
				if(!sBuffer.equals(""))
			      list2.add(sBuffer.toString());
			}
			 String []strings=null;
			 if(!list2.isEmpty()){
				 for(String s:list2){
					 strings=s.split("@#@");
					 list.add(new PoliceSubstationBean(strings[0],strings[2],strings[1],strings[3],strings[4]));
				 }
			 }
			return list;
	}
	
   public  void createPolicesubstationXml(List<PoliceSubstationBean> list,String xmlfiles){
	 
	   if(null!=list&&!list.isEmpty()){
		   PoliceSubstationBean policesubstationbean=null;
	    	Document document=DocumentHelper.createDocument();
	    	Element titleelement=document.addElement("court");
	    	 Element element=null;
	    	 for(int i=0,n=list.size();i<n;i++){
	    		 policesubstationbean=list.get(i);
	    		 element=titleelement.addElement("row");
	    		 element.addAttribute("roomno",policesubstationbean.getRoomno());
	    		 element.addAttribute("casecode",policesubstationbean.getCasecode());
	    		 element.addAttribute("policename",policesubstationbean.getPolicename());
	    		 element.addAttribute("casecharacter",policesubstationbean.getCasecharacter());
	    		 element.addAttribute("suspect",policesubstationbean.getSuspect());
	    	 }
	    	try {
				OutputFormat format = OutputFormat.createPrettyPrint();
				format.setEncoding("GBK");
				XMLWriter output = new XMLWriter(new FileWriter(new File(xmlfiles)), format);
				output.write(document);
				output.close();
			} catch (IOException e) {
//				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	   }
 }
	
//---------------------------------------------------------------------------------
//	��ȡExcel
	public  String getFileFromExcelSell(String homepath ,String xsl,String  sheetname,String xmlfile) {
        String stempString=null;
		Workbook rwb = null;
		try {
			InputStream input = new FileInputStream(new File(xsl));
			rwb = Workbook.getWorkbook(input);
			int sheetcounts=rwb.getSheets().length;
//			System.out.println("����sheet����-----> "+sheetcounts);

			if(sheetcounts>0){
	            for(int k=0,kn=sheetcounts;k<kn;k++){
	            	ExcelsBeans excelsbeans=createList(rwb,k,sheetname);
	            	if(null!=excelsbeans){
	            	  createXml(excelsbeans,homepath+xmlfile);
	            	
	            	  stempString=xmlfile;
	            	  fileList.add(new File(homepath+xmlfile).getName());
	            	}
	            }
			}
			input.close();
			rwb.close();
			
		} catch (Exception e) {
			if(null!=rwb)
				rwb.close();
//			System.out.println("����Excel����------->"+e.getMessage());
			e.printStackTrace();
			return null;
		}
		return stempString;
	}
	
	
	private static ExcelsBeans createList(Workbook rwb,int k,String sheetname) {
		
			Sheet sheet = rwb.getSheet(k);
			ExcelsBeans  excelsbeans = null;
			
			if(sheetname.equals(sheet.getName())){
				
				List<String>list=new ArrayList<String>();
				excelsbeans = new ExcelsBeans();
				String updatetime="";
				StringBuffer sb=null;
				String tempstring="";
				String span="";
				 int rows = sheet.getRows();
				 int cells = sheet.getColumns();
				 excelsbeans.setColomncounts(cells+"");
				 excelsbeans.setTitle(sheetname);
//			     System.out.println("sheets ���ƣ�-----> "+sheetname);
		
				 for (int i = 0; i < rows; i++) {
					sb=new StringBuffer("");
					
					for (int j = 0; j < cells; ++j) {
						tempstring=sheet.getCell(j, i).getContents();
						
						/*if(i<1){
							if(!"".equals(tempstring))
								updatetime=tempstring;
							    excelsbeans.setUpdatetime(updatetime);
						}else{
						   sb.append(tempstring).append("#");
						
						}
						
						*/
						if(i<1){
							if(!"".equals(tempstring)){
								updatetime=tempstring.trim();
							    excelsbeans.setUpdatetime(updatetime);
							}
						}else if(i<2){
							if(!"".equals(tempstring)){
								span=tempstring.trim();
							    excelsbeans.setSpan(span);
							}
						}else{
						   sb.append(tempstring.trim()).append("#");
						}
					}
					if(!sb.toString().equals("")&&!"####".equals(sb.toString())&&!"###".equals(sb.toString())
							&&!"##".equals(sb.toString())&&!"#####".equals(sb.toString())){
					  list.add(sb.toString());
					}
				}
			   excelsbeans.setCellslist(list);
//		       System.out.println("update time----->"+updatetime);
		       
//		       for(String s:list){
//		    	   if(!s.equals("")/*&&!"####".equals(s)*/)
//		    	     System.out.println("s--->"+s.substring(0,s.length()-1));
//		       }
			}
		return excelsbeans;
	}
	
	 public  void createXml(ExcelsBeans excelsbeans,String xmlfiles){
		 
		   if(null!=excelsbeans){
			    List<String>list=excelsbeans.getCellslist();
		    	Document document=DocumentHelper.createDocument();
		    	Element titleelement=document.addElement("notice");
		    	Element childement=titleelement.addElement("headtime");
		    	childement.addAttribute("head",excelsbeans.getTitle())
		    	.addAttribute("time",excelsbeans.getUpdatetime())
		    	.addAttribute("span",excelsbeans.getSpan());
		    	
		    	for(int i=0,n=list.size();i<n;i++){
		    		
		    		    String [] cells=list.get(i).split("#");
			    		if(i==0){
			    			if(cells.length==3){
			    			   titleelement.addAttribute("colomns",excelsbeans.getColomncounts());
				    		   titleelement.addAttribute("cell1",cells[0]);
				    		   titleelement.addAttribute("cell2",cells[1]);
				    		   titleelement.addAttribute("cell3",cells[2]);
				    		  
				    		}
				    		if(cells.length==4){
				    			 titleelement.addAttribute("colomns",excelsbeans.getColomncounts());
				    			 titleelement.addAttribute("cell1",cells[0]);
					    		 titleelement.addAttribute("cell2",cells[1]);
					    		 titleelement.addAttribute("cell3",cells[2]);
					    		 titleelement.addAttribute("cell4",cells[3]);
				    		}
				    		if(cells.length==5){
				    			 titleelement.addAttribute("colomns",excelsbeans.getColomncounts());
				    			 titleelement.addAttribute("cell1",cells[0]);
					    		 titleelement.addAttribute("cell2",cells[1]);
					    		 titleelement.addAttribute("cell3",cells[2]);
					    		 titleelement.addAttribute("cell4",cells[3]);
					    		 titleelement.addAttribute("cell5",cells[4]);
				    		}
			    		}else{
				    		Element cellselement=childement.addElement("row");
				    		if(cells.length==3){
				    			cellselement.addAttribute("cell1",cells[0]);
				    			cellselement.addAttribute("cell2",cells[1]);
				    			cellselement.addAttribute("cell3",cells[2]);
				    			
				    		}
				    		if(cells.length==4){
				    			cellselement.addAttribute("cell1",cells[0]);
				    			cellselement.addAttribute("cell2",cells[1]);
				    			cellselement.addAttribute("cell3",cells[2]);
				    			cellselement.addAttribute("cell4",cells[3]);
				    		}
				    		if(cells.length==5){
				    			cellselement.addAttribute("cell1",cells[0]);
				    			cellselement.addAttribute("cell2",cells[1]);
				    			cellselement.addAttribute("cell3",cells[2]);
				    			cellselement.addAttribute("cell4",cells[3]);
				    			cellselement.addAttribute("cell5",cells[4]);
				    		}
			    		}
		    	}
		    	try {
					OutputFormat format = OutputFormat.createPrettyPrint();
					format.setEncoding("GBK");
					XMLWriter output = new XMLWriter(new FileWriter(new File(xmlfiles)), format);
					output.write(document);
					output.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
		   }
	    }
	 
	 public static void sleep(long l){
		 try {
			Thread.sleep(l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	 }
	 static Map<String,String>map=new LinkedHashMap<String,String>();
	 static Map<String,String>servicesmap=new LinkedHashMap<String,String>();
	 static Map<String,String>filesmap=new LinkedHashMap<String,String>();
	 
	 static{
		    map.put("OP���۶�������","kh-op-sell.xml");
		    map.put("����ָ��������(Creative)","kh-sell-quota-creative.xml");
		    map.put("����ָ��������(Logistic)","kh-sell-quota-logistic.xml");
		    map.put("Ӧ�ս�������","kh-jl-ys.xml");
		    map.put("��Ʊ��������","kh-jl-kp.xml");
			map.put("��Ʊ����","kh-kp-yq.xml");
			map.put("��Ʊ����","kh-kp-tx.xml");
			map.put("�ѿ�Ʊδ����������̵�Ӧ���˿�","kh-kp-ysyq.xml");
			
			///////////////////////////////////////////////////////////////
			servicesmap.put("OP���۶�������","kh-excel/kh-op-sell.xml");
			servicesmap.put("����ָ��������(Creative)","kh-excel/kh-sell-quota-creative.xml");
			servicesmap.put("����ָ��������(Logistic)","kh-excel/kh-sell-quota-logistic.xml");
			servicesmap.put("Ӧ�ս�������","kh-excel/kh-jl-ys.xml");
			servicesmap.put("��Ʊ��������","kh-excel/kh-jl-kp.xml");
			servicesmap.put("��Ʊ����","kh-excel/kh-kp-yq.xml");
			servicesmap.put("��Ʊ����","kh-excel/kh-kp-tx.xml");
			servicesmap.put("�ѿ�Ʊδ����������̵�Ӧ���˿�","kh-excel/kh-kp-ysyq.xml");
	 }
	 
	 List<String> xmlimagelist=new ArrayList<String>();

	 public   List<String>  createKHXML(String homepath,String file) {

			String string=null;
			for(Map.Entry<String,String>mm:map.entrySet()){
				string=getFileFromExcelSell(homepath,/*files[i].getPath()*/file,mm.getKey(),mm.getValue());
				if(null!=string)
					xmlimagelist.add(string);
			}
			
			createImageServices(homepath,homepath,xmlimagelist);
			return xmlimagelist;
	}
	 
	 //ɨ�����ĳ���ļ�����ĳ���ļ��Ƿ����仯
	 static int size=0;
	 public   void  createKHXMLServices(String homepath,String file) {
        if(new File(file).exists()){
		    size=0;
			File[]files=new File(file).listFiles();
			for (int i = 0; i < files.length; i++) {
				if(files[i].getName().endsWith(".xls")){
					++size;
				} 
			}
			
			if(filesmap.isEmpty()){
				for (int i = 0; i < files.length; i++) {
					if(files[i].getName().endsWith(".xls")){
						filesmap.put(files[i].getName(), files[i].lastModified()+"");
					}
				}
				size=filesmap.size();
//				System.out.println("-------1----------->����Excel");
				get(files,homepath);
				FirstStartServlet.client.allUDPsend("255.255.255.255","lvdownexcel");//�㲥
				
			}else{
				
				if(size==filesmap.size()){
					
					for (int i = 0; i < files.length; i++) {
						if(files[i].getName().endsWith(".xls")){
							for(Map.Entry<String,String>mm:filesmap.entrySet()){
								
								if(mm.getKey().equals(files[i].getName())
										&&!mm.getValue().equals(files[i].lastModified()+"")){
									
									    filesmap.put(files[i].getName(), files[i].lastModified()+"");
									    get(files,homepath);
//										System.out.println("--------2---------->����Excel");
										FirstStartServlet.client.allUDPsend("255.255.255.255","lvdownexcel");//�㲥
								}
							}
						}
					}
				}else{
					
					for (int i = 0; i < files.length; i++) {
						if(files[i].getName().endsWith(".xls")){
							if(null==filesmap.get(files[i].getName())){
								filesmap.put(files[i].getName(), files[i].lastModified()+"");
							}
						}
					}
				
					if(size==filesmap.size()){
						 get(files,homepath);
//						 System.out.println("-------------3----->����Excel");
						 FirstStartServlet.client.allUDPsend("255.255.255.255","lvdownexcel");//�㲥
					}
					if(size!=filesmap.size()){
						filesmap.clear();
						for (int i = 0; i < files.length; i++) {
							if(files[i].getName().endsWith(".xls")){
								filesmap.put(files[i].getName(), files[i].lastModified()+"");
							}
						}
						size=filesmap.size();
					    get(files,homepath);
//					    System.out.println("-------------4----->����Excel");
					    FirstStartServlet.client.allUDPsend("255.255.255.255","lvdownexcel");//�㲥
					}
				}
			}
         }
	 }
	 
	 
	 List<String>fileList=new ArrayList<String>();
	 public  void get(File [] files,String homepath) {
		 
		
		 List<String>xlsfileList=new ArrayList<String>();
		 for (int i = 0; i < files.length; i++) {
			 xlsfileList.add(files[i].getPath());
		 } 
		 Collections.sort(xlsfileList);
		 for(String ss:xlsfileList){
			 if(ss.endsWith(".xls")){
				for(Map.Entry<String,String>mm:servicesmap.entrySet()){
					//ѭ�����������쳣�� Unable to recognize OLE stream
					getFileFromExcelSell(homepath,ss,mm.getKey(),mm.getValue());
				}
			 }
		  }
//		 for(String s:fileList)
//			 System.out.println(s);
		 deleteLogAndOtherFile(homepath+"kh-image/");
		 
		 createImageServices(homepath+"kh-excel/",homepath+"kh-image/",fileList);
		 STEP=100;
	}
	
	 
	  public void deleteLogAndOtherFile(String imgesfile){
			
		    File	file =new File(imgesfile);
		    if(file.exists()){
				File[] files = file.listFiles();
				for (int j = files.length - 1; j >= 0; j--) {
						
				  deleteFile(files[j].getPath());
				}
		    }
		}	
		
		public boolean deleteFile(String fileName) {
			
			File file = new File(fileName);
			// ����ļ�·����Ӧ���ļ����ڣ�������һ���ļ�����ֱ��ɾ����
			if (file.exists() && file.isFile()) {
				if (file.delete())
					return true;
				else
					return false;
			} else {
				return false;
			}
		}
	 
	 private String title;//�ϱ���
	    
	 private String endtitle;//�±���
	 
	 private int span=8000;//�л�ʱ��
	 
	 String colomuns="";//����
	 
	 String cel_list[];
	 
	  private int rowcount;
	 
	 public  List<NoticeBeanKH>geNoticeBeanKHtList(String filepath){
//		System.out.println("geNoticeBeanKHtList----------->"+filepath);
		List<NoticeBeanKH> listtemp=new ArrayList<NoticeBeanKH>();
	    Dom4j dom = new Dom4j(filepath);
		Document document = dom.getDocument();
		List notice = document.selectNodes("/notice");
		for (int i = 0; i < notice.size(); i++) {
			
			Element e = (Element) notice.get(i);
			colomuns=e.attributeValue("colomns").trim();
			cel_list=new String[Integer.parseInt(colomuns)];
			
			if(colomuns.equals("3")){
				cel_list[0]=e.attributeValue("cell1").trim();
				cel_list[1]=e.attributeValue("cell2").trim();
				cel_list[2]=e.attributeValue("cell3").trim();
			}else if(colomuns.equals("4")){
				cel_list[0]=e.attributeValue("cell1").trim();
				cel_list[1]=e.attributeValue("cell2").trim();
				cel_list[2]=e.attributeValue("cell3").trim();
				cel_list[3]=e.attributeValue("cell4").trim();
			}else if(colomuns.equals("5")){
				cel_list[0]=e.attributeValue("cell1").trim();
				cel_list[1]=e.attributeValue("cell2").trim();
				cel_list[2]=e.attributeValue("cell3").trim();
				cel_list[3]=e.attributeValue("cell4").trim();
				cel_list[4]=e.attributeValue("cell5").trim();
			}
		}
		
		List headtime = document.selectNodes("/notice/headtime");
		for (int i = 0; i < headtime.size(); i++) {
			
			Element e = (Element) headtime.get(i);
			title=e.attributeValue("head").trim();
			endtitle=e.attributeValue("time").trim();
			if(isNumeric(e.attributeValue("span").trim()))
			  span=Integer.parseInt(e.attributeValue("span").trim())*1000;
		}
		
	    List rows = document.selectNodes("/notice/headtime/row");
		
		rowcount=rows.size();
		NoticeBeanKH wwmb = null;
	
		
		for (int i = 0; i < rows.size(); i++) {
			Element e = (Element) rows.get(i);
			
			if(colomuns.equals("3")){
				wwmb=new NoticeBeanKH(
						e.attributeValue("cell1").trim(),
						e.attributeValue("cell2").trim(),
						e.attributeValue("cell3").trim(),title,endtitle,span,cel_list);
			}else if(colomuns.equals("4")){
				wwmb=new NoticeBeanKH(
						e.attributeValue("cell1").trim(),
						e.attributeValue("cell2").trim(),
						e.attributeValue("cell3").trim(),
						e.attributeValue("cell4"),title,endtitle,span,cel_list);
			}else if(colomuns.equals("5")){
				wwmb=new NoticeBeanKH(
						e.attributeValue("cell1").trim(),
						e.attributeValue("cell2").trim(),
						e.attributeValue("cell3").trim(),
						e.attributeValue("cell4").trim(),
						e.attributeValue("cell5").trim(),title,endtitle,span,cel_list);
			}
			
			listtemp.add(wwmb);
		}
		return listtemp;
	}
		
	 public  boolean isNumeric(String s){
		   
	      if((s != null)&&(s!=""))
	       return s.matches("^[0-9]*$");
	      else
	       return false;
	 }
	 
	 
	 //����д��ͼƬ��
	 public static void commCreatimageForTableHead(File fromimage,String toimgage,NoticeBeanKH  noticebeankh, List<NoticeBeanKH>list,int []cellwidth){
		 
				BufferedImage buff = null;
				try {
					buff = javax.imageio.ImageIO.read(fromimage);
				} catch (IOException e) {
					return;
				}
				Graphics grap_right = buff.createGraphics();
				Graphics2D g2 = (Graphics2D) grap_right;
				RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				renderHints.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
				
				g2.setRenderingHints(renderHints);
		
				Font font=new Font("΢���ź�",Font.BOLD,80);
				FontMetrics fm = g2.getFontMetrics(font); 
				
                 
					g2.setColor(Color.WHITE);
					g2.setFont(font);
					g2.drawString(/*"OP���۶�������"*/noticebeankh.getTilte(), 200,font.getSize()+3);
				
					g2.setColor(new Color(1,57,44));//����ɫ
					font=new Font("΢���ź�",Font.BOLD,62);
					g2.setFont(font);
					int xtemp=10;
					//��ͷ
					if(noticebeankh.getCel_list().length==3){
						
						drawTableHead(noticebeankh.getCel_list()[0],fm,g2,font,xtemp,cellwidth[0],156);		
						xtemp+=cellwidth[0];
						drawTableHead(noticebeankh.getCel_list()[1],fm,g2,font,xtemp,cellwidth[1],156);
						xtemp+=cellwidth[1];
						drawTableHead(noticebeankh.getCel_list()[2],fm,g2,font,xtemp,cellwidth[2],156);
	
						
					}else if(noticebeankh.getCel_list().length==4){
						
						drawTableHead(noticebeankh.getCel_list()[0],fm,g2,font,xtemp,cellwidth[0],156);	
						xtemp+=cellwidth[0];
						drawTableHead(noticebeankh.getCel_list()[1],fm,g2,font,xtemp,cellwidth[1],156);
						xtemp+=cellwidth[1];
						drawTableHead(noticebeankh.getCel_list()[2],fm,g2,font,xtemp,cellwidth[2],156);
						xtemp+=cellwidth[2];
						drawTableHead(noticebeankh.getCel_list()[3],fm,g2,font,xtemp,cellwidth[3],156);
						
					}else if(noticebeankh.getCel_list().length==5){
						
						drawTableHead(noticebeankh.getCel_list()[0],fm,g2,font,xtemp,cellwidth[0],156);		
						xtemp+=cellwidth[0];
						drawTableHead(noticebeankh.getCel_list()[1],fm,g2,font,xtemp,cellwidth[1],156);
						xtemp+=cellwidth[1];
						drawTableHead(noticebeankh.getCel_list()[2],fm,g2,font,xtemp,cellwidth[2],156);
						xtemp+=cellwidth[2];
						drawTableHead(noticebeankh.getCel_list()[3],fm,g2,font,xtemp,cellwidth[3],156);
						xtemp+=cellwidth[3];
						drawTableHead(noticebeankh.getCel_list()[4],fm,g2,font,xtemp,cellwidth[4],156);
					}
                 

				g2.dispose();
				grap_right.dispose();
				buff.flush();
				save(buff, toimgage);

	 }
	 
     public static int TEMP=0;
	 
	 //����д��ͼƬ��
	 public static void commCreatimage(String s,File fromimage,String toimgage,NoticeBeanKH  noticebeankh, List<NoticeBeanKH>list,int []cellwidth){
		 
				BufferedImage buff = null;
				try {
					buff = javax.imageio.ImageIO.read(fromimage);
				} catch (IOException e) {
					return;
				}
				Graphics grap_right = buff.createGraphics();
				Graphics2D g2 = (Graphics2D) grap_right;
				RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				renderHints.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
				
				g2.setRenderingHints(renderHints);
		
				Font font=new Font("΢���ź�",Font.BOLD,80);
				FontMetrics fm = g2.getFontMetrics(font); 
				
                 if(TEMP==0){
                	TEMP=1;
					g2.setColor(Color.WHITE);
					g2.setFont(font);
					g2.drawString(/*"OP���۶�������"*/noticebeankh.getTilte(), 200,font.getSize()+3);
				
					g2.setColor(new Color(1,57,44));//����ɫ
					font=new Font("΢���ź�",Font.BOLD,62);
					g2.setFont(font);
					int xtemp=10;
					//��ͷ
					if(noticebeankh.getCel_list().length==3){
						
						drawTableHead(noticebeankh.getCel_list()[0],fm,g2,font,xtemp,cellwidth[0],156);		
						xtemp+=cellwidth[0];
						drawTableHead(noticebeankh.getCel_list()[1],fm,g2,font,xtemp,cellwidth[1],156);
						xtemp+=cellwidth[1];
						drawTableHead(noticebeankh.getCel_list()[2],fm,g2,font,xtemp,cellwidth[2],156);
	
						
					}else if(noticebeankh.getCel_list().length==4){
						
						drawTableHead(noticebeankh.getCel_list()[0],fm,g2,font,xtemp,cellwidth[0],156);	
						xtemp+=cellwidth[0];
						drawTableHead(noticebeankh.getCel_list()[1],fm,g2,font,xtemp,cellwidth[1],156);
						xtemp+=cellwidth[1];
						drawTableHead(noticebeankh.getCel_list()[2],fm,g2,font,xtemp,cellwidth[2],156);
						xtemp+=cellwidth[2];
						drawTableHead(noticebeankh.getCel_list()[3],fm,g2,font,xtemp,cellwidth[3],156);
						
					}else if(noticebeankh.getCel_list().length==5){
						
						drawTableHead(noticebeankh.getCel_list()[0],fm,g2,font,xtemp,cellwidth[0],156);		
						xtemp+=cellwidth[0];
						drawTableHead(noticebeankh.getCel_list()[1],fm,g2,font,xtemp,cellwidth[1],156);
						xtemp+=cellwidth[1];
						drawTableHead(noticebeankh.getCel_list()[2],fm,g2,font,xtemp,cellwidth[2],156);
						xtemp+=cellwidth[2];
						drawTableHead(noticebeankh.getCel_list()[3],fm,g2,font,xtemp,cellwidth[3],156);
						xtemp+=cellwidth[3];
						drawTableHead(noticebeankh.getCel_list()[4],fm,g2,font,xtemp,cellwidth[4],156);
					}
					
					font=new Font("΢���ź�",Font.BOLD,66);
					g2.setColor(Color.WHITE);
					g2.setFont(font);
					g2.drawString(/*"2012-05-29"*/noticebeankh.getEndtitle(),900,font.getSize()+970);
                 }

				//////////////////////////////////////////////////////////////////////////////
	
				g2.setColor(new Color(1,57,44));//����ɫ
				font=new Font("΢���ź�",Font.BOLD,54);
				g2.setFont(font);
				
				int fontheight=0;
				int xtemp2=10;
				int ytemp=156;
				int index=0;
				NoticeBeanKH  noticebeankhtemp=null;
				if(cellwidth.length==3){
					
					for(int i=0,n=list.size();i<n;i++){
						g2.setColor(new Color(1,57,44));//����ɫ
						xtemp2=10;
						noticebeankhtemp=list.get(i);
						index=0;
						fontheight=0;
						fm = g2.getFontMetrics(font); 
						fontheight=fm.getHeight();//�ַ��ĸ߶�
						if(i==0)
							ytemp=ytemp*2;
						else{
						
						    ytemp=ytemp+135;
						}
						drawKHString(index,noticebeankhtemp.getCell1(),fm,g2,font,xtemp2,cellwidth[0],ytemp,"center");
						xtemp2+=cellwidth[0];
					
						drawKHString(index+1,noticebeankhtemp.getCell2(),fm,g2,font,xtemp2,cellwidth[1],ytemp,"center");
						
						if(noticebeankhtemp.getCell3().trim().equalsIgnoreCase("n"))
							g2.setColor(new Color(161,0,10));//���ɫ
						
						xtemp2+=cellwidth[1];
						drawKHString(index+1,noticebeankhtemp.getCell3(),fm,g2,font,xtemp2,cellwidth[2],ytemp,"center");
						
					}
					
				}else if(cellwidth.length==4){
					
					for(int i=0,n=list.size();i<n;i++){
						g2.setColor(new Color(1,57,44));//����ɫ
						xtemp2=10;
						noticebeankhtemp=list.get(i);
						index=0;
						fontheight=0;
						fm = g2.getFontMetrics(font); 
						fontheight=fm.getHeight();//�ַ��ĸ߶�
						if(i==0)
							ytemp=ytemp*2;
						else{
						
						    ytemp=ytemp+135;
						}
						drawKHString(index,noticebeankhtemp.getCell1(),fm,g2,font,xtemp2,cellwidth[0],ytemp,"center");
						xtemp2+=cellwidth[0];
					
						drawKHString(index+1,noticebeankhtemp.getCell2(),fm,g2,font,xtemp2,cellwidth[1],ytemp,"center");
						xtemp2+=cellwidth[1];
						
						drawKHString(index+1,noticebeankhtemp.getCell3(),fm,g2,font,xtemp2,cellwidth[2],ytemp,"center");
						xtemp2+=cellwidth[2];
						
						if(noticebeankhtemp.getCell4().trim().equalsIgnoreCase("n"))
							g2.setColor(new Color(161,0,10));//���ɫ
					
						drawKHString(index+1,noticebeankhtemp.getCell4(),fm,g2,font,xtemp2,cellwidth[3],ytemp,"center");
						
					}
					
				}else if(cellwidth.length==5){
					
					
					for(int i=0,n=list.size();i<n;i++){
						g2.setColor(new Color(1,57,44));//����ɫ
						xtemp2=10;
						noticebeankhtemp=list.get(i);
						index=0;
						fontheight=0;
						fm = g2.getFontMetrics(font); 
						fontheight=fm.getHeight();//�ַ��ĸ߶�
						if(i==0)
							ytemp=ytemp*2;
						else{
						
						    ytemp=ytemp+135;
						}
						drawKHString(index,noticebeankhtemp.getCell1(),fm,g2,font,xtemp2,cellwidth[0],ytemp,"center");
						xtemp2+=cellwidth[0];
					
						drawKHString(index+1,noticebeankhtemp.getCell2(),fm,g2,font,xtemp2,cellwidth[1],ytemp,"center");

						xtemp2+=cellwidth[1];
						drawKHString(index+1,noticebeankhtemp.getCell3(),fm,g2,font,xtemp2,cellwidth[2],ytemp,"center");
					
						xtemp2+=cellwidth[2];
						if(s.endsWith("kh-kp-ysyq.xml")){
							drawKHString(index+1,noticebeankhtemp.getCell4(),fm,g2,font,xtemp2,cellwidth[3],ytemp,"right");
						}else{
						    drawKHString(index+1,noticebeankhtemp.getCell4(),fm,g2,font,xtemp2,cellwidth[3],ytemp,"center");
						}
						if(noticebeankhtemp.getCell5().trim().equalsIgnoreCase("n"))
							g2.setColor(new Color(161,0,10));//���ɫ
						xtemp2+=cellwidth[3];
						drawKHString(index+1,noticebeankhtemp.getCell5(),fm,g2,font,xtemp2,cellwidth[4],ytemp,"center");
						
					}
				}
				//////////////////////////////////////////////////////////////////////////////
				g2.dispose();
				grap_right.dispose();
				buff.flush();
				save(buff, toimgage);

	 }
	 
	 public static void drawTableHead (String string ,FontMetrics fm,Graphics2D g2,Font font,int xtemp,int fwidth,int ytemp){
		 
			if(!string.trim().equals("")){
			    int widths=0;//�ַ�����ռʵ�ʳ���
				for(int i=0;i<string.length();i++){
					widths+=fm.charWidth(string.charAt(i));
				}
				
				int ii=xtemp+fwidth/2-widths/2+font.getSize()/2;//������ʾ
//				System.out.println("ii--------------------->"+ii);
			    g2.drawString(string, ii,font.getSize()+ytemp);
	      }
	 }
	 
	 
	 
	 public static void  drawKHString(int j,String string,FontMetrics fm,Graphics2D g2,Font font,int xtemp2,int fwidth,int ytemp,String position) {
		 
		    int widths=0;//�ַ�����ռʵ�ʳ���
		    for(int i=0;i<string.length();i++){				
				widths+=fm.charWidth(string.charAt(i));
			}
		    int ii=xtemp2+fwidth/2-widths/2;//������ʾ
		    
		    if(position.equals("left")){//������ʾ
		    	ii=xtemp2;
		    }
		    if(position.equals("right")){//������ʾ
		    	ii=xtemp2+(fwidth-widths);
		    }
			g2.drawString(string,ii,font.getSize()+ytemp);
	 }
	 
	 public static void save(BufferedImage buff, String saveTo) {
			try {
				FileOutputStream fos = new FileOutputStream(saveTo);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
				
				 JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(buff); 
				 param.setQuality(70f, true); 
				 encoder.encode(buff, param); 

				fos.flush();
				fos.close();
				fos = null;

			} catch (Exception e) {
				System.out.println("DrawFactory save Picture error!");
			}
		}
	 static int STEP=100;
	 public  void createImageServices(String homepath,String imagehome,List<String>fileList){
		 
		    TEMP=0;
		    int []threewidths={625,800,475};//3 ��
			int []fourwidths={250,800,400,500};//4 ��
			int []fivewidths={100,600,450,425,325};//5 ��
			int []five_kp_tx_widths={100,750,250,425,425};//5 �� ���� kh-kp-tx.xml
//			int []five_kp_ys_widths={680,170,400,300,400};//5 �� ���� kh-kp-ysyq.xml
			int []five_kp_ys_widths={680,165,395,290,420};//5 �� ���� kh-kp-ysyq.xml
			File dirfile=new File(imagehome);
            if(!dirfile.exists())
            	dirfile.mkdirs();
			
//			List<String> fileList=new ArrayList<String>();
//			fileList.add(homepath+"kh-op-sell.xml");
//			fileList.add(homepath+"kh-kp-tx.xml");
//			fileList.add(homepath+"kh-kp-ysyq.xml");
//			fileList.add(homepath+"kh-kp-yq.xml");
//			fileList.add(homepath+"kh-jl-ys.xml");
//			fileList.add(homepath+"kh-jl-kp.xml");
			
			List<NoticeBeanKH>list=null;
			
			String imagetemp=null;
			File file=new File(FirstStartServlet.projectpath+"images/kh.jpg");
			List<NoticeBeanKH >listtemp=null;
			String s=null;
			String imagestirString=null;
			for(int jj=0,jn=fileList.size();jj<jn;jj++){
				
				s=fileList.get(jj);
//				System.out.println("s-=-=-==-=-=-=-=>"+homepath+s);
				list=geNoticeBeanKHtList(homepath+s);
				int pagetemp=0;
				if(!list.isEmpty()){
					int sizetemp=list.size()%5;
					if(sizetemp==0){
						pagetemp=list.size()/5;
					}else {
						pagetemp=list.size()/5+1;
					}
					
					///////////////////////////////////////////////////////////////////////////////////////////////////
//					imagetemp="temp"+(STEP++)+".jpg";
//					
//					if(s.endsWith("kh-kp-tx.xml")){//5��
//					  commCreatimageForTableHead(file,imagehome+imagetemp,list.get(0),listtemp,five_kp_tx_widths);
//					
//					}else  if(s.endsWith("kh-op-sell.xml")){//5��
//						  commCreatimageForTableHead(file,imagehome+imagetemp,list.get(0),listtemp,fivewidths);
//					 
//					}else if(s.endsWith("kh-kp-ysyq.xml")||s.endsWith("kh-kp-yq.xml")
//						||s.endsWith("kh-jl-ys.xml")||s.endsWith("kh-jl-kp.xml")||s.endsWith("kh-sell-quota.xml")){//4�� 
//						
//						commCreatimageForTableHead(file,imagehome+imagetemp,list.get(0),listtemp,fourwidths);
//					}else{
//						commCreatimageForTableHead(file,imagehome+imagetemp,list.get(0),listtemp,threewidths);
//					}
				////////////////////////////////////////////////////////////////////////////////////////////////////////
				
					for (int i = 0; i <pagetemp; i++) {
						listtemp=list.subList(i*5,((i+1)*5)>=list.size()?list.size():((i+1)*5));
		
						imagestirString="temp"+(STEP++)+".jpg";
						imagetemp=imagehome+imagestirString;
							
						///////////////////////////////////////////////////////////////////////
						if(s.endsWith("kh-op-sell.xml")){//5��
							  OpExcelUtils.commCreatimage(s,file,imagetemp,list.get(0),listtemp,fivewidths);
							  xmlimagelist.add(imagestirString);
							  
						}else if(s.endsWith("kh-kp-tx.xml")){//5��
							  OpExcelUtils.commCreatimage(s,file,imagetemp,list.get(0),listtemp,five_kp_tx_widths);
							  xmlimagelist.add(imagestirString);
	
						}else if(s.endsWith("kh-kp-ysyq.xml")){//5��
						   OpExcelUtils.commCreatimage(s,file,imagetemp,list.get(0),listtemp,five_kp_ys_widths);
						   xmlimagelist.add(imagestirString);
						  
						}  else if(s.endsWith("kh-kp-yq.xml")||s.endsWith("kh-jl-ys.xml")||s.endsWith("kh-jl-kp.xml")){//4��
			
						   OpExcelUtils.commCreatimage(s,file,imagetemp,list.get(0),listtemp,fourwidths);
						   xmlimagelist.add(imagestirString);
						   
						}else{//3��
						   OpExcelUtils.commCreatimage(s,file,imagetemp,list.get(0),listtemp,threewidths);
						   xmlimagelist.add(imagestirString);
						}
					    TEMP=0;
					}
				}
			}
	 }
	 
	public static void main(String[] args) {
		
		OpExcelUtils opexcelutils=new OpExcelUtils();
		
		System.out.println(new UsersDAO().addUserInfo(opexcelutils.getImportUserinfo("c://��Ϣ����ϵͳ���������û���.xls")));
		
//		opexcelutils.createKHXMLServices("D:\\WebWorkspace\\XCTCMSSServer\\","D:\\WebWorkspace\\XCTCMSSServer\\kh-excel");
//		
//		String homepath="kh-excel/";
//		List<String> fileList=new ArrayList<String>();
//		fileList.add(homepath+"kh-op-sell.xml");
//		fileList.add(homepath+"kh-kp-tx.xml");
//		fileList.add(homepath+"kh-kp-ysyq.xml");
//		fileList.add(homepath+"kh-kp-yq.xml");
//		fileList.add(homepath+"kh-jl-ys.xml");
//		fileList.add(homepath+"kh-jl-kp.xml");
//		fileList.add(homepath+"kh-sell-quota-creative.xml");
//		fileList.add(homepath+"kh-sell-quota-logistic.xml");
//		opexcelutils.createImageServices("D:\\XCTCMSSServerWebWorkspace\\XCTCMSSServerSer\\kh-excel\\","D:\\XCTCMSSServerWebWorkspace\\XCTCMSSServerSer\\kh-image\\",fileList);
		
		//һ����������������쳣
//		getFileFromExcelSell("excel/OP���۶�������.xls","OP���۶�������","kh-excel/kh-op-sell.xml");
//		getFileFromExcelSell("excel/��Ʊ.xls","��Ʊ����","kh-excel/kh-kp-yq.xml");
//		getFileFromExcelSell("excel/��Ʊ.xls","��Ʊ����","kh-excel/kh-kp-tx.xml");
//		getFileFromExcelSell("excel/��Ʊ.xls","Ӧ������","kh-excel/kh-kp-ysyq.xml");
//		getFileFromExcelSell("excel/Ӧ�ս��뿪Ʊ��.xls","Ӧ�ս�������","kh-excel/kh-jl-ys.xml");
//		getFileFromExcelSell("excel/Ӧ�ս��뿪Ʊ��.xls","��Ʊ��������","kh-excel/kh-jl-kp.xml");
	}


	public  void setSTEP(int step) {
		STEP = step;
	}

}
