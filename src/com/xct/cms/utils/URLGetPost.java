package com.xct.cms.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;


public class URLGetPost {
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */

	public  boolean sendGet(String url, String param) {
		
		BufferedReader in = null;
		try {
			String urlName = url + "?" + param; 
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");		
			// 建立实际的连接
			conn.connect();
			
		/*	// 获取所有响应头字段
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				/////System.out.println(key + "--->" + map.get(key));
			}*/
			
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           return true;
		} catch (Exception e) {
			/////System.out.println("发送GET请求出现异常！----1" + e);
			e.printStackTrace();
			return false;
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) 
					in.close();
			} catch (IOException ex) {
				/////System.out.println("发送GET请求出现异常！---2" + ex);
				ex.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * 向指定URL发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public  void sendPost(String url, String param) {
		
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
					
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
//			System.out.println(param);
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			/////System.out.println(url);
		} catch (Exception e) {
			/////System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
//	  xu0034@1000-1100#20100407192920#2010-08-05 19:25:46#loop#11

//    xu0028@循环节目#20100618165444#2010-08-05 16:32:31#loop#30$$$1500-1600#20100407201802#2010-08-05 19:22:06#loop#2$$$1400-1500#20100407201447#2010-08-05 19:23:47#loop#16$$$1000-1100#20100407192920#2010-08-05 19:25:46#loop#11$$$0900-1000#20100407192503#2010-08-05 19:27:16#loop#7$$$1700-1930#20100421140505#2010-08-05 19:29:04#loop#7

	// 提供主方法，测试发送GET请求和POST请求
	public static void main(String args[]) {
		
		URLGetPost urlgetpost=new URLGetPost();
		String url="http://192.168.10.3:80/cgi-bin/cgiTest.cgi";
		
		// 发送GET请求
		try {
//			urlgetpost.sendGet(url,"Name="+DESPlusUtil.Get().encrypt("欢迎牌")+"&sex="+DESPlusUtil.Get().encrypt("女"));
			urlgetpost.sendPost(url,"Name="+DESPlusUtil.Get().encrypt("李刚")+"&sex="+DESPlusUtil.Get().encrypt("男").toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 发送POST请求
	}
}
