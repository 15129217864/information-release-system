package com.xct.cms.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ForeignExchange {//今日汇率    美元(USD):  633.52    日元(JPY):  7.6217    港元(HKD):  81.63    欧元(EUR):  828.32

	//农行外汇：  http://app.abchina.com/rateinfo/RateSearch.aspx?id=1
	//工行外汇：http://www.icbc.com.cn/ICBCDynamicSite/Optimize/Quotation/QuotationListIframe.aspx?time=-1213203519
	
	public static String FOREIGSTRING="";
	
	 public static String readForeignExchangeFromNet(String url){
		  
			String curLine = "";
			StringBuffer context = new StringBuffer("");
			URL server;
			BufferedReader reader = null;
			String netstring="";
			
			//////////////// 局域网设置代理服务后的设置////////////////////////////////////
//			Properties   prop   =   System.getProperties();      
			
/*			System.getProperties().put("proxySet", "true"); 
         //	设置http访问要使用的代理服务器的地址
			System.getProperties().setProperty("http.proxyHost", "10.38.18.224"); //联合利华上网代理IP：156.5.80.49，江苏常州：10.38.18.224
			// 设置http访问要使用的代理服务器的端口 
			System.getProperties().setProperty("http.proxyPort", "808"); //联合利华上网代理端口：8080，江苏常州：808
*/			
			
         //如需要用到 用户名和密码，如下注释取消   
/*          System.getProperties().setProperty("http.proxyUser","www"); //用户名     
			System.getProperties().setProperty("http.proxyPassword","www");  //密码
*/
        ///////////////////////////////////////////////////////////////////////////////
			
			try {
				server = new URL(url);
				reader = new BufferedReader(new InputStreamReader(server.openStream(), "UTF-8"));
				while ((curLine = reader.readLine()) != null) {
					context.append(curLine).append("\r\n");
				}
				netstring=context.toString();
			} catch (MalformedURLException e) {
				// logger.info(getTrace(e));
				e.printStackTrace();
				return netstring;
			} catch (UnsupportedEncodingException e) {
				// logger.info(getTrace(e));
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// logger.info(getTrace(e));
				e.printStackTrace();
				return netstring;
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
						return netstring;
					}
				}
			}
			return netstring;
	    }
		/* 根据起始位置和结束位置，截取字符串 */
		public static String subString(String start, String end, String content) {
			int iStart = content.indexOf(start);
			int iEnd = content.indexOf(end);
			if (iStart < iEnd) {
				return content.substring(iStart, iEnd+end.length());
			}
			return null;
		}
		
	 
	    public static String getForeignExchanger(String context,String foreignexchangerxml) {
		    String headerxml="<?xml version=\"1.0\" encoding=\"GBK\"?>";
		    boolean flag = true;
//		    String contentString = "";
		    String netstring=context;
			netstring=subString("<table class=\"DataList MarginTop10 FontCenter\"","</table>",netstring);
//			contentString=StringUtils.deleteWhitespace(netstring);
//			System.out.println(netstring);
			
			if(flag&&null!=netstring){
				writeFiletoSingle(foreignexchangerxml, headerxml+netstring, false, false);
			}
			return netstring;
	}
	 
		public static boolean getForeignExchangerXml(String foreignexchangerxmlfile,String path){
		
			String daystring="今日汇率    ";
			boolean flag=false;
			if(new File(foreignexchangerxmlfile).exists()){
				
				SAXReader saxreader=new SAXReader();
				try {
					Document document=saxreader.read(new File(foreignexchangerxmlfile));
					Element root=document.getRootElement();
					List list=root.selectNodes("tr");
					if(null!=list&&!list.isEmpty()){
						
						for(int j=1,m=list.size();j<m;j++){
							Element element=(Element)list.get(j);
							List childlist=element.selectNodes("td");
							for(int i=0,n=childlist.size();i<n;i++){
								
								 Element e =(Element)childlist.get(i);
								 String string=e.getStringValue();
								 if(string.indexOf("USD")!=-1||string.indexOf("JPY")!=-1||string.indexOf("HKD")!=-1||string.indexOf("EUR")!=-1){
									 if(i==0){//币种
										 daystring+= string+": ";
									 }
									 flag=true;
								 }
								 if(i==1&&flag){//基准汇率
									daystring+=string+"    ";
									 flag=false;
									break;
								 }
								 
							}
						}
					}
					FOREIGSTRING=daystring.trim();
					writeFiletoSingle(/*WeatherUtils.homepath+"foreignexchanger.txt"*/path, daystring.trim(), false, false);
					return true;
				} catch (DocumentException e) {
					e.printStackTrace();
					return false;
				}
			}
			return false;
		}
		
		public static void writeFiletoSingle(String file, String str, boolean flag,
				boolean newline) {

			File f = new File(file);
			if (!f.exists()) {
				File t = f.getParentFile();
				if (!t.exists())
					t.mkdir();
			}
			FileWriter fw;
			try {
				fw = new FileWriter(file, flag);
				if (newline && flag)
					fw.write("\r\n");
				fw.write(str);
				if (!flag && newline)
					fw.write("\r\n");
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static boolean foreignExchangeToTxt(String url,String xmlfile,String path){
			getForeignExchanger(readForeignExchangeFromNet(url),xmlfile);
			return getForeignExchangerXml(xmlfile,path);
		}
		
	public static void main(String[] args) {
		foreignExchangeToTxt("http://app.abchina.com/rateinfo/RateSearch.aspx?id=1","/foreignexchanger.xml","/foreignexchanger.txt");

	}

}
