package com.xct.cms.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;


public class URLGetPost {
	/**
	 * ��ָ��URL����GET����������
	 * 
	 * @param url
	 *            ���������URL
	 * @param param
	 *            ����������������Ӧ����name1=value1&name2=value2����ʽ��
	 * @return URL������Զ����Դ����Ӧ
	 */

	public  boolean sendGet(String url, String param) {
		
		BufferedReader in = null;
		try {
			String urlName = url + "?" + param; 
			URL realUrl = new URL(urlName);
			// �򿪺�URL֮�������
			URLConnection conn = realUrl.openConnection();
			// ����ͨ�õ���������
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");		
			// ����ʵ�ʵ�����
			conn.connect();
			
		/*	// ��ȡ������Ӧͷ�ֶ�
			Map<String, List<String>> map = conn.getHeaderFields();
			// �������е���Ӧͷ�ֶ�
			for (String key : map.keySet()) {
				/////System.out.println(key + "--->" + map.get(key));
			}*/
			
			// ����BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           return true;
		} catch (Exception e) {
			/////System.out.println("����GET��������쳣��----1" + e);
			e.printStackTrace();
			return false;
		}
		// ʹ��finally�����ر�������
		finally {
			try {
				if (in != null) 
					in.close();
			} catch (IOException ex) {
				/////System.out.println("����GET��������쳣��---2" + ex);
				ex.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * ��ָ��URL����POST����������
	 * 
	 * @param url
	 *            ���������URL
	 * @param param
	 *            ����������������Ӧ����name1=value1&name2=value2����ʽ��
	 * @return URL������Զ����Դ����Ӧ
	 */
	public  void sendPost(String url, String param) {
		
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// �򿪺�URL֮�������
			URLConnection conn = realUrl.openConnection();
			// ����ͨ�õ���������
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
					
			// ����POST�������������������
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			// ��ȡURLConnection�����Ӧ�������
			out = new PrintWriter(conn.getOutputStream());
			// �����������
//			System.out.println(param);
			out.print(param);
			// flush������Ļ���
			out.flush();
			// ����BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			/////System.out.println(url);
		} catch (Exception e) {
			/////System.out.println("����POST��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر��������������
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

//    xu0028@ѭ����Ŀ#20100618165444#2010-08-05 16:32:31#loop#30$$$1500-1600#20100407201802#2010-08-05 19:22:06#loop#2$$$1400-1500#20100407201447#2010-08-05 19:23:47#loop#16$$$1000-1100#20100407192920#2010-08-05 19:25:46#loop#11$$$0900-1000#20100407192503#2010-08-05 19:27:16#loop#7$$$1700-1930#20100421140505#2010-08-05 19:29:04#loop#7

	// �ṩ�����������Է���GET�����POST����
	public static void main(String args[]) {
		
		URLGetPost urlgetpost=new URLGetPost();
		String url="http://192.168.10.3:80/cgi-bin/cgiTest.cgi";
		
		// ����GET����
		try {
//			urlgetpost.sendGet(url,"Name="+DESPlusUtil.Get().encrypt("��ӭ��")+"&sex="+DESPlusUtil.Get().encrypt("Ů"));
			urlgetpost.sendPost(url,"Name="+DESPlusUtil.Get().encrypt("���")+"&sex="+DESPlusUtil.Get().encrypt("��").toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// ����POST����
	}
}
