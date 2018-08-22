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


public class ForeignExchange {//���ջ���    ��Ԫ(USD):  633.52    ��Ԫ(JPY):  7.6217    ��Ԫ(HKD):  81.63    ŷԪ(EUR):  828.32

	//ũ����㣺  http://app.abchina.com/rateinfo/RateSearch.aspx?id=1
	//������㣺http://www.icbc.com.cn/ICBCDynamicSite/Optimize/Quotation/QuotationListIframe.aspx?time=-1213203519
	
	public static String FOREIGSTRING="";
	
	 public static String readForeignExchangeFromNet(String url){
		  
			String curLine = "";
			StringBuffer context = new StringBuffer("");
			URL server;
			BufferedReader reader = null;
			String netstring="";
			
			//////////////// ���������ô������������////////////////////////////////////
//			Properties   prop   =   System.getProperties();      
			
/*			System.getProperties().put("proxySet", "true"); 
         //	����http����Ҫʹ�õĴ���������ĵ�ַ
			System.getProperties().setProperty("http.proxyHost", "10.38.18.224"); //����������������IP��156.5.80.49�����ճ��ݣ�10.38.18.224
			// ����http����Ҫʹ�õĴ���������Ķ˿� 
			System.getProperties().setProperty("http.proxyPort", "808"); //����������������˿ڣ�8080�����ճ��ݣ�808
*/			
			
         //����Ҫ�õ� �û��������룬����ע��ȡ��   
/*          System.getProperties().setProperty("http.proxyUser","www"); //�û���     
			System.getProperties().setProperty("http.proxyPassword","www");  //����
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
		/* ������ʼλ�úͽ���λ�ã���ȡ�ַ��� */
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
		
			String daystring="���ջ���    ";
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
									 if(i==0){//����
										 daystring+= string+": ";
									 }
									 flag=true;
								 }
								 if(i==1&&flag){//��׼����
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
