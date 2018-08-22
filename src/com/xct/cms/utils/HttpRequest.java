package com.xct.cms.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.xct.cms.dao.UsersDAO;

public class HttpRequest {

	/**
	 * ���ӳ�ʱ
	 */
	private static int connectTimeOut = 5000;

	/**
	 * ��ȡ���ݳ�ʱ
	 */
	private static int readTimeOut = 10000;

	/**
	 * �������
	 */
	private static String requestEncoding = "gbk";

	private static Logger logger = Logger.getLogger(HttpRequest.class);


	/**
	 * @return ���ӳ�ʱ(����)
	 * @see com.hengpeng.common.web.HttpRequestProxy#connectTimeOut
	 */
	public static int getConnectTimeOut() {
		return HttpRequest.connectTimeOut;
	}

	/**
	 * @return ��ȡ���ݳ�ʱ(����)
	 * @see com.hengpeng.common.web.HttpRequestProxy#readTimeOut
	 */
	public static int getReadTimeOut() {
		return HttpRequest.readTimeOut;
	}

	/**
	 * @return �������
	 * @see com.hengpeng.common.web.HttpRequestProxy#requestEncoding
	 */
	public static String getRequestEncoding() {
		return requestEncoding;
	}

	/**
	 * @param connectTimeOut ���ӳ�ʱ(����)
	 * @see com.hengpeng.common.web.HttpRequestProxy#connectTimeOut
	 */
	public static void setConnectTimeOut(int connectTimeOut) {
		HttpRequest.connectTimeOut = connectTimeOut;
	}

	/**
	 * @param readTimeOut ��ȡ���ݳ�ʱ(����)
	 * @see com.hengpeng.common.web.HttpRequestProxy#readTimeOut
	 */
	public static void setReadTimeOut(int readTimeOut) {
		HttpRequest.readTimeOut = readTimeOut;
	}

	/**
	 * @param requestEncoding �������
	 * @see com.hengpeng.common.web.HttpRequestProxy#requestEncoding
	 */
	public static void setRequestEncoding(String requestEncoding) {
		HttpRequest.requestEncoding = requestEncoding;
	}
	
	
	/**
	 * <pre>
	 * ���ʹ�������GET��HTTP����
	 * </pre>
	 * 
	 * @param reqUrl HTTP����URL
	 * @param parameters ����ӳ���
	 * @return HTTP��Ӧ���ַ���
	 */
	public static String doGet(String reqUrl, Map<?, ?> parameters, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator<?> iter = parameters.entrySet().iterator(); iter.hasNext();) {
				Entry<?, ?> element = (Entry<?, ?>) iter.next();
				params.append(element.getKey().toString());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue().toString(), HttpRequest.requestEncoding));
				params.append("&");
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}

			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("GET");
			//System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(HttpRequest.connectTimeOut));// ����λ�����룩jdk1.4�������,���ӳ�ʱ
			//System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(HttpRequest.readTimeOut)); // ����λ�����룩jdk1.4�������,��������ʱ
			url_con.setConnectTimeout(5000);//����λ�����룩jdk
			// 1.5�������,���ӳ�ʱ
			url_con.setReadTimeout(5000);//����λ�����룩jdk 1.5�������,��������ʱ
			url_con.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();

			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer temp = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				temp.append(tempLine);
				temp.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = temp.toString();
			rd.close();
			in.close();
		}
		catch (IOException e) {
			logger.info("���ʿͻ��ˡ�"+reqUrl+"���������====="+e.getMessage());
			e.printStackTrace();
		}
		finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}

		return responseContent;
	}

	/**
	 * <pre>
	 * ���Ͳ���������GET��HTTP����
	 * </pre>
	 * 
	 * @param reqUrl HTTP����URL
	 * @return HTTP��Ӧ���ַ���
	 */
	public static String doGet(String reqUrl, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			StringBuffer params = new StringBuffer();
			String queryUrl = reqUrl;
			int paramIndex = reqUrl.indexOf("?");

			if (paramIndex > 0) {
				queryUrl = reqUrl.substring(0, paramIndex);
				String parameters = reqUrl.substring(paramIndex + 1, reqUrl.length());
				String[] paramArray = parameters.split("&");
				for (int i = 0; i < paramArray.length; i++) {
					String string = paramArray[i];
					int index = string.indexOf("=");
					if (index > 0) {
						String parameter = string.substring(0, index);
						String value = string.substring(index + 1, string.length());
						params.append(parameter);
						params.append("=");
						params.append(URLEncoder.encode(value, HttpRequest.requestEncoding));
						params.append("&");
					}
				}

				params = params.deleteCharAt(params.length() - 1);
			}

			URL url = new URL(queryUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("GET");
			//System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(HttpRequest.connectTimeOut));// ����λ�����룩jdk1.4�������,���ӳ�ʱ
			//System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(HttpRequest.readTimeOut)); // ����λ�����룩jdk1.4�������,��������ʱ
			 url_con.setConnectTimeout(5000);//����λ�����룩jdk
			// 1.5�������,���ӳ�ʱ
			 url_con.setReadTimeout(5000);//����λ�����룩jdk 1.5�������,��������ʱ
			url_con.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();
			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer temp = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				temp.append(tempLine);
				temp.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = temp.toString();
			rd.close();
			in.close();
		}
		catch (Exception e) {
			logger.info("���ʿͻ��ˡ�"+reqUrl+"���������====="+e.getMessage());
			e.printStackTrace();
		}
		finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}

		return responseContent;
	}

	/**
	 * <pre>
	 * ���ʹ�������POST��HTTP����
	 * </pre>
	 * 
	 * @param reqUrl HTTP����URL
	 * @param parameters ����ӳ���
	 * @return HTTP��Ӧ���ַ���
	 */
	public static String doPost(String reqUrl, Map<String, String> parameters, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
				StringBuffer params = new StringBuffer();
				for (Iterator<?> iter = parameters.entrySet().iterator(); iter.hasNext();) {
					Entry<?, ?> element = (Entry<?, ?>) iter.next();
					params.append(element.getKey().toString());
					params.append("=");
					params.append(URLEncoder.encode(element.getValue().toString(), HttpRequest.requestEncoding));
					params.append("&");
				}

				if (params.length() > 0) {
					params = params.deleteCharAt(params.length() - 1);
				}
			    URL url = new URL(reqUrl);
			    url_con = (HttpURLConnection) url.openConnection();
			    url_con.setRequestMethod("POST");
			    url_con.setConnectTimeout(4000);//����λ�����룩jdk
			    url_con.setReadTimeout(5000);//����λ�����룩jdk 1.5�������,��������ʱ
				url_con.setDoOutput(true);
				byte[] b = params.toString().getBytes();
				url_con.getOutputStream().write(b, 0, b.length);
				url_con.getOutputStream().flush();
				url_con.getOutputStream().close();
				InputStream in = url_con.getInputStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
				String tempLine = rd.readLine();
				StringBuffer temp = new StringBuffer();
				String crlf = System.getProperty("line.separator");
				while (tempLine != null) {
					temp.append(tempLine);
					temp.append(crlf);
					tempLine = rd.readLine();
				}
				responseContent = temp.toString();
				rd.close();
				in.close();
			}
			catch (Exception e) {
				logger.info("���ʿͻ��ˡ�"+reqUrl+"���������====="+e.getMessage());
				e.printStackTrace();
			}
			finally {
				if (url_con != null) {
					url_con.disconnect();
				}
			}
			return responseContent;
	}


	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("op", "xu0009");
		String temp = HttpRequest.doPost("http://192.168.10.11/NoticeToClientOparator", map, "gbk");
		/////System.out.println("���ص���Ϣ��:" + temp);
	}

}
