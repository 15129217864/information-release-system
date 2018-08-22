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
	
    public static String today_weather="今天："; 
    public static String tomorrow_weather="#明天："; 
    public static String the_day_after_tomorrow_weather="#后天："; 
    public static String headerxml="<?xml version=\"1.0\" encoding=\"GBK\"?>";
//    public static String homepath=getOSPath();
	
	 public static String readWeatherFromNet(String url){
		  
			String curLine = "";
			StringBuffer context = new StringBuffer("");
			URL server;
			BufferedReader reader = null;
			String netstring="";
			
//			Properties   prop   =   System.getProperties();      
			//////////////// 局域网设置代理服务后的设置////////////////////////////////////	
/*			if(!(ProxyUtil.PROXYHOST.equals("xxx")&&ProxyUtil.PROXYPORT.equals("xxx"))){
				System.getProperties().put("proxySet", "true"); 
	            //	设置http访问要使用的代理服务器的地址
				System.getProperties().setProperty("http.proxyHost", ProxyUtil.PROXYHOST); //联合利华上网代理IP
				// 设置http访问要使用的代理服务器的端口 
				System.getProperties().setProperty("http.proxyPort", ProxyUtil.PROXYPORT); //联合利华上网代理端口
				System.out.println("-------weather-------->设置http访问要使用的代理服务器的地址和端口");
			}
			if(!(ProxyUtil.PROXYUSER.equals("xxx")&&ProxyUtil.PROXYPASSWORD.equals("xxx"))){
	            //如需要用到 用户名和密码，如下注释取消   
	            System.getProperties().setProperty("http.proxyUser",ProxyUtil.PROXYUSER); //用户名     
				System.getProperties().setProperty("http.proxyPassword",ProxyUtil.PROXYPASSWORD);  //密码
				System.out.println("------weather--------->设置http访问要使用的代理服务器的用户名和密码");
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
				System.out.println("MalformedURLException----获取天气预报信息出错，请检查网络-->");
				return netstring;
			} catch (UnsupportedEncodingException e) {
				// logger.info(getTrace(e));
				System.out.println("UnsupportedEncodingException----获取天气预报信息出错，请检查网络-->");
						
				return null;
			} catch (IOException e) {
				// logger.info(getTrace(e));，如果用来代理上网，要加代理IP，端口，用户名，密码
				System.out.println("IOException-------获取天气预报信息出错，请检查网络-->");
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

	/* 根据起始位置和结束位置，截取字符串 */
	public static String subString(String start, String end, String content) {
		int iStart = content.indexOf(start);
		int iEnd = content.indexOf(end);
		if (iStart < iEnd) {
			return content.substring(iStart, iEnd+end.length());
		}
		return null;
	}
	
	/* 根据起始位置和结束位置，截取字符串 */
	public static String subStringEnd(String start, String end, String content) {
		int iStart = content.indexOf(start);
		int iEnd = content.indexOf(end);
		if (iStart < iEnd) {
			return content.substring(iStart, iEnd);
		}
		return null;
	}


	/* 去除标签 */
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

	// 打印所有的异常信息
	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}
}
