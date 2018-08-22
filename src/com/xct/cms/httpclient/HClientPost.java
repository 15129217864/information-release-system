package com.xct.cms.httpclient;

import java.io.File;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

public class HClientPost {
	public static void main(String args[]) {
		
		String targetURL = null;
		targetURL = "http://localhost/xctConfig/HttpClientServlets"; // servleturl
		// ��û��commons-codec-1.4-bin.zip, ���ｫ�����
		PostMethod filePost = new PostMethod(targetURL); 
		
//		���PostMethod�ύ���������ַ�����Ҫ������Ӧ�ı����ʽ
		filePost.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");     
												
		try {
//			 ͨ�����·�������ģ��ҳ�����POST�ύ
			NameValuePair[] param = { new NameValuePair("username","����"), new NameValuePair("password","bill123")} ;   
			filePost.setRequestBody(param);
			HttpClient client = new HttpClient();
			
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
					
			int status = client.executeMethod(filePost);
			System.out.println("status--------->"+status);
			if (status == HttpStatus.SC_OK) {
				System.out.println(new String(filePost.getResponseBodyAsString().getBytes("ISO-8859-1"),"GBK"));
				System.out.println("�ύ�ɹ�");
				// �ϴ��ɹ�
			} else {
				System.out.println("�ύʧ��");
				// �ϴ�ʧ��
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
	}
}
