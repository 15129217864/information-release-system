package com.xct.cms.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetXMLEncoding {

	 private String[] encodings = {"utf-8", "gbk", "gb2312", "gb18030", "iso8859-1"};

	 public String getXmlencoding() {
		return xmlencoding;
	}

	public void setXmlencoding(String xmlencoding) {
		this.xmlencoding = xmlencoding;
	}
	
	  
//	 一个public方法，返回字符串，错误则返回"error open url"

		public  String getContent(String strUrl) {

			try {

				URL url = new URL(strUrl);

				BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

				String s = "";

				StringBuffer sb = new StringBuffer("");

				while ((s = br.readLine()) != null) {

					sb.append(s + "\r\n");

				}

				br.close();

				return sb.toString();

			} catch (Exception e) {
				e.printStackTrace();
				return "error open url:" + strUrl;
			}
		}

	public static void main(String args[]) {
		
		//http://news.163.com/special/00011K6L/rss_newstop.xml   gbk
		//http://www.people.com.cn/rss/politics.xml    utf-8
		//http://rss.sina.com.cn/news/marquee/ddt.xml   utf-8
		//http://news.qq.com/newsgn/rss_newsgn.xml   gb2312
		//http://news.baidu.com/n?cmd=4&class=civilnews&tn=rss gb2312
		//http://rss.news.sohu.com/rss/guonei.xml  utf-8
		
//	        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//	                "<test>\n" +
//	                "    <a item=\"1\">1</a>\n" +
//	                "</text>";
		    GetXMLEncoding t = new GetXMLEncoding();
		    String xml = t.getContent("http://news.163.com/special/00011K6L/rss_newstop.xml");
//	        System.out.println(xml);
//	        System.out.println();
	        if(t.checkXml(xml))
	          System.out.println(t.getXmlencoding());
	    }
	 
	    String xmlencoding ;
	    public boolean checkXml(String xml) {
	    	
	        String regex = "<\\?xml\\s+version=\"1.0\"\\s+encoding=\"(.*?)\"\\?>";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(xml);
	        boolean b = false;
	      
	        while(matcher.find()) {
	            if(!b) {
	                b = true;
	                xmlencoding = matcher.group(1);   
//	                System.out.println(xmlencoding);
	            }else{
	                return false;
	            }
	        }
	        return isEncoding(xmlencoding);
	    }
	    
	    private boolean isEncoding(String encoding) {
	        for(int i = 0; i < encodings.length; i++) {
	            if(encodings[i].equalsIgnoreCase(encoding)) {
	                return true;
	            }
	        }
	        return false;
	    }
	}

