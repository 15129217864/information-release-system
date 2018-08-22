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
		// 若没有commons-codec-1.4-bin.zip, 这里将会出错
		PostMethod filePost = new PostMethod(targetURL); 
		
//		如果PostMethod提交的是中文字符，需要加上相应的编码格式
		filePost.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");     
												
		try {
//			 通过以下方法可以模拟页面参数POST提交
			NameValuePair[] param = { new NameValuePair("username","汉字"), new NameValuePair("password","bill123")} ;   
			filePost.setRequestBody(param);
			HttpClient client = new HttpClient();
			
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
					
			int status = client.executeMethod(filePost);
			System.out.println("status--------->"+status);
			if (status == HttpStatus.SC_OK) {
				System.out.println(new String(filePost.getResponseBodyAsString().getBytes("ISO-8859-1"),"GBK"));
				System.out.println("提交成功");
				// 上传成功
			} else {
				System.out.println("提交失败");
				// 上传失败
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
	}
}
