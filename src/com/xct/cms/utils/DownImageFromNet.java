package com.xct.cms.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;   
import java.io.FileNotFoundException;
import java.io.FileOutputStream;   
import java.io.IOException;   
import java.net.Authenticator;
import java.net.PasswordAuthentication;
   
import org.apache.commons.httpclient.HttpClient;   
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;   

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
   
/**  
 * ��HttpClient����ͼƬ  
 */   
public class DownImageFromNet {   
	
	private boolean flag;
	
	public static void initProxy(String host, int port, final String username,final String password) {

		Authenticator.setDefault(new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(username,

				new String(password).toCharArray());

			}

		});

		System.setProperty("http.proxyType", "4");

		System.setProperty("http.proxyPort", Integer.toString(port));

		System.setProperty("http.proxyHost", host);

		System.setProperty("http.proxySet", "true");

	}
	
	//����ٳ��� 0 kb ͼƬ����ʱ�ŵ�һ����ʱ�ļ����ڣ��ж������ 0 kb���򲻸��Ƶ���Ŀ¼��
	public void getImagmeFromNet(String url,String imagepath){
		
	/*	String proxy = "10.0.0.80";

		int port = 8088;

		String username = "11fmedia01";

		String password = "123456";

		String curLine = "";

		String content = "";

		initProxy(proxy, port, username, password);*/
		
		    HttpClient client = new HttpClient();   
	        GetMethod get = new GetMethod(url);   
	        try {
				client.executeMethod(get);
				flag=true;
			} catch (HttpException e) {
				e.printStackTrace();
				flag=false;
				return;
			} catch (IOException e) {
				e.printStackTrace();
				flag=false;
				return;
			}   
			
	        File storeFile=null ;   
	        FileOutputStream output=null;
	        if(flag){
	        	
				try {
//					storeFile = new File(imagepath+url.substring(url.lastIndexOf("/")));  
					storeFile = new File(imagepath/*+"usd.png"*/);  
					output = new FileOutputStream(storeFile);
					//�õ�������Դ���ֽ�����,��д���ļ�   
					output.write(get.getResponseBody());   
					output.close();   
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return;
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}finally{
					if(output!=null){
						try {
							output.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}   
	        }
	}
	
	public static String png2jpg(String fromfile,String tofile) {
		   
		File _file = new File(fromfile); // �����ļ�
	    if(_file.exists()){                                       
	        Image src=null;
			try {
				src = javax.imageio.ImageIO.read(_file);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}                                           
	        int   wideth=src.getWidth(null);                                                                           
	        int   height=src.getHeight(null);                                                                         
	        BufferedImage   tag   =   new   BufferedImage(wideth,height,BufferedImage.TYPE_INT_RGB); 
	        tag.getGraphics().drawImage(src,0,0,wideth,height,null);               
	        FileOutputStream out = null;
			try {
				out = new   FileOutputStream(tofile);
			} catch (FileNotFoundException e) {
			
				e.printStackTrace();
				return null;
			}      
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tag);
			param.setQuality(5f, true);
			try {
						encoder.encode(tag, param);
						out.flush();
						out.close();
						/*encoder.encode(tag); // ��JPEG����
						 out.close();*/
					
			} catch (ImageFormatException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return tofile;
	    }
	    return null;
	}

       
    public static void main(String[] args) throws Exception{   
    	
//        String url ="http://image.sinajs.cn/newchart/hk_stock/min/00151.gif";//���� ��ʱ����ͼ
//        String url ="http://company.caihuanet.com/dataimg/hreal/r00151.png";//��ʱ����ͼ
//        String url ="http://company.caihuanet.com/dataimg/hday/day00151.png";//��K��
//        String url ="http://company.caihuanet.com/dataimg/hweek/wek00151.png";//��K��
//        String url ="http://company.caihuanet.com/dataimg/hmonth/mon00151.png";//��K��
        
//        String url =  "http://img.gtimg.cn/images/kline/hongkong_rt/week/00151.png";
    //====================================================/=========    
     //   �żҸ� �����г���Ʒ�۸��� ,��ȡ��������ݺ�����ͼ
		// http://price.mofcom.gov.cn/channel/pricequery.shtml

	//	 �ƽ������������� ԭ�ͣ��������޻���������ͭ���Ϻ�����
	//	 �󶹣�CBT��������ͣ����������ף��������������󣨰Ĵ����ǣ������Ƹ�

		 //----------------------------------------------------------------------------
	//	�ƽ������� http://price.mofcom.gov.cn/channel/priceinfo.shtml?prod_id=0110001
//		String url ="http://price.mofcom.gov.cn/channel/createpic/0110001.jpg";

  //             ������������ http://price.mofcom.gov.cn/channel/priceinfo.shtml?prod_id=0110011
//		String url ="http://price.mofcom.gov.cn/channel/createpic/0110011.jpg";

	//	ԭ�ͣ������� http://price.mofcom.gov.cn/channel/priceinfo.shtml?prod_id=0310002
//		String url ="http://price.mofcom.gov.cn/channel/createpic/0310002.jpg";

   //            �޻��������� http://price.mofcom.gov.cn/channel/priceinfo.shtml?prod_id=0610000
//		String url ="http://price.mofcom.gov.cn/channel/createpic/0610000.jpg";
              
	//        ͭ���Ϻ����� http://price.mofcom.gov.cn/channel/priceinfo.shtml?prod_id=0101011
//		String url ="http://price.mofcom.gov.cn/channel/createpic/0101011.jpg";

	//	�󶹣�CBT����http://price.mofcom.gov.cn/channel/priceinfo.shtml?prod_id=0410061
//		String url ="http://price.mofcom.gov.cn/channel/createpic/0410061.jpg";

	//	����ͣ�������http://price.mofcom.gov.cn/channel/priceinfo.shtml?prod_id=0410141
//		String url ="http://price.mofcom.gov.cn/channel/createpic/0410141.jpg";

	//	���ף���������http://price.mofcom.gov.cn/channel/priceinfo.shtml?prod_id=0412010
//		String url ="http://price.mofcom.gov.cn/channel/createpic/0412010.jpg";

   //            �����󣨰Ĵ����ǣ���  http://price.mofcom.gov.cn/channel/priceinfo.shtml?prod_id=0111811
//	        String url ="http://price.mofcom.gov.cn/channel/createpic/0111811.jpg";

	//        ���Ƹ֣�http://price.mofcom.gov.cn/channel/priceinfo.shtml?prod_id=0102010
	         String url ="http://image.cngold.org/chart/forex/usd_72_zhishu.png";

        
        String ss=url.substring(url.lastIndexOf("/"));
        System.out.println(ss);
        String imagepath="c:/tmp/";
    	new DownImageFromNet().getImagmeFromNet(url,imagepath);
//    	png2jpg("c:/tmp/"+ss,"c:/tmp/"+ss+".jpg");
    }   
}   
