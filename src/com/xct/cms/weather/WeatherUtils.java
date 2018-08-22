package com.xct.cms.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WeatherUtils {
	
    public static String today_weather="���죺"; 
    public static String tomorrow_weather="#���죺"; 
    public static String the_day_after_tomorrow_weather="#���죺"; 
    public static String headerxml="<?xml version=\"1.0\" encoding=\"GBK\"?>";
//    public static String homepath=getOSPath();
	
	 public static String readWeatherFromNet(String url){
		  
			String curLine = "";
			StringBuffer context = new StringBuffer("");
			URL server;
			BufferedReader reader = null;
			String netstring="";
			
//			Properties   prop   =   System.getProperties();      
			//////////////// ���������ô������������////////////////////////////////////	
/*			if(!(ProxyUtil.PROXYHOST.equals("xxx")&&ProxyUtil.PROXYPORT.equals("xxx"))){
				System.getProperties().put("proxySet", "true"); 
	            //	����http����Ҫʹ�õĴ���������ĵ�ַ
				System.getProperties().setProperty("http.proxyHost", ProxyUtil.PROXYHOST); //����������������IP
				// ����http����Ҫʹ�õĴ���������Ķ˿� 
				System.getProperties().setProperty("http.proxyPort", ProxyUtil.PROXYPORT); //����������������˿�
				System.out.println("-------weather-------->����http����Ҫʹ�õĴ���������ĵ�ַ�Ͷ˿�");
			}
			if(!(ProxyUtil.PROXYUSER.equals("xxx")&&ProxyUtil.PROXYPASSWORD.equals("xxx"))){
	            //����Ҫ�õ� �û��������룬����ע��ȡ��   
	            System.getProperties().setProperty("http.proxyUser",ProxyUtil.PROXYUSER); //�û���     
				System.getProperties().setProperty("http.proxyPassword",ProxyUtil.PROXYPASSWORD);  //����
				System.out.println("------weather--------->����http����Ҫʹ�õĴ�����������û���������");
			}*/
           ///////////////////////////////////////////////////////////////////////////////
			
			try {
				server = new URL(url);
				reader = new BufferedReader(new InputStreamReader(server.openStream()/*, "UTF-8"*/));
				while ((curLine = reader.readLine()) != null) {
					context.append(curLine).append("\r\n");
				}
				netstring=context.toString();
			} catch (MalformedURLException e) {
				// logger.info(getTrace(e));
				System.out.println("MalformedURLException----��ȡ����Ԥ����Ϣ������������-->");
				return netstring;
			} catch (UnsupportedEncodingException e) {
				// logger.info(getTrace(e));
				System.out.println("UnsupportedEncodingException----��ȡ����Ԥ����Ϣ������������-->");
						
				return null;
			} catch (IOException e) {
				// logger.info(getTrace(e));�������������������Ҫ�Ӵ���IP���˿ڣ��û���������
				System.out.println("IOException-------��ȡ����Ԥ����Ϣ������������-->");
				return netstring;
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						// logger.info(getTrace(e));
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
	
	/* ������ʼλ�úͽ���λ�ã���ȡ�ַ��� */
	public static String subStringEnd(String start, String end, String content) {
		int iStart = content.indexOf(start);
		int iEnd = content.indexOf(end);
		if (iStart < iEnd) {
			return content.substring(iStart, iEnd);
		}
		return null;
	}


	/* ȥ����ǩ */
	public static String trimTag(String content) {
		String regEx = "<[^>]+>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(content);
		String result = content;
		if (m.find()) {
			result = m.replaceAll("");
		}
		result = result.replace(" ", "").replace(">", "").replace(">", "");
		return result;
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
			// logger.info(getTrace(e));
			System.out.println(e.getMessage());
		}
	}

	public static String getOSPath() {

		String location = null;
		try {
			location = URLDecoder.decode(WeatherUtils.class
					.getProtectionDomain().getCodeSource().getLocation()
					.getPath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		if (location != null) {
			location = new File(location).getParent().replace("\\", "/");

			if (!location.endsWith("/"))
				location += "/";
		}
		return location;
	}

	// ��ӡ���е��쳣��Ϣ
	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}
}
