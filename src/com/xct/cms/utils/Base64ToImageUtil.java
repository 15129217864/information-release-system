package com.xct.cms.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;


//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

//import java.util.Base64;//JDK8 ���д���, Ϊ�˼��ݵͰ汾�� �Ȳ��ô�

public class Base64ToImageUtil {
	
	public static void main(String[] args) {
		
		System.out.println(getImageBASE64EStr("12.jpg"));
		
//		System.out.println(DESPlusUtil.Get().encrypt(getImageBASE64EStr("381C554A78C.jpg")));
		
		String imagepath="screen.jpg";
//		String str=DESPlusUtil.Get().decrypt(getFileValues("base.txt"));
//		String str=DESPlusUtil.Get().decrypt(getFileValues("base64.txt"));
		String str=getFileValues("base64.txt");
//		System.out.println(str);
	    generateImageFromBase64Str(str,imagepath);
	}
	
	/**
     * �Ƚ�ָ�����ڵĲ�
     */
    public static long getTwoDateDay(long time1, long time2){
    	  long quot = 0;
    	   quot = time1 - time2;
    	   quot = quot / 1000 / 60 / 60 / 24;
    	  return quot;
    }
	
	 /**
     * �Ƚ�ָ�����ڵĲ�
     */
    public static long getTwoDateDay(String time1, String time2){
    	  long quot = 0;
    	  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    	  try {
	    	   Date date1 = ft.parse( time1 );
	    	   Date date2 = ft.parse( time2 );
	    	   quot = date1.getTime() - date2.getTime();
	    	   quot = quot / 1000 / 60 / 60 / 24;
    	  } catch (ParseException e) {
    	       e.printStackTrace();
    	  }
    	  return quot;
    }

	public static String getFileValues(String url) {
		File fl = new File(url);
		if (fl.exists()) {
			StringBuffer strBuffer = new StringBuffer();
			BufferedReader bufferedReader=null;
			String currentLine = null;
			try {
				 bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fl),"UTF-8"));
				while ((currentLine = bufferedReader.readLine()) != null) {
					strBuffer.append(currentLine);
				}
				return strBuffer.toString().trim();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(null!=bufferedReader){
					try {
						bufferedReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
   }
	
	//��ͼƬת�� BASE64 ����
	public static String getImageBASE64EStr(String imagepath){
//		String imagepath="111.jpg";
		InputStream in=null;
		byte[]data=null;
		//��ȡͼƬ�ֽ�����
		try {
			in=new FileInputStream(imagepath);
			data=new byte[in.available()];
			in.read(data);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=in){
			    try {
			    	in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return	Base64.encodeBase64String(data);//����Base64��������ֽ������ַ�//org.apache.commons.codec
		//--------------------------------------------------------------------------------------------
//		return	Base64.getEncoder().encodeToString(data);//����Base64��������ֽ������ַ���jdk8 ��ʹ�� java.util.Base64;
		//--------------------------------------------------------------------------------------------
//		BASE64Encoder encoder=new BASE64Encoder();
//		return encoder.encode(data);//����Base64��������ֽ������ַ�د
	}
	
	public static boolean generateImageFromBase64Str(String base64str,String imagepath){
		if(null==base64str){
			return false;
		}else{
//			BASE64Decoder decoder=new BASE64Decoder();

			byte[] b=null;
			OutputStream out=null;
			try {
//				b = decoder.decodeBuffer(base64str);
				//------------------------------------------------------------------
//				b = Base64.getDecoder().decode(base64str);//jdk8 ��ʹ�� java.util.Base64;
				//------------------------------------------------------------------
				b = Base64.decodeBase64(base64str);//org.apache.commons.codec
				//------------------------------------------------------------------
				for (int i = 0; i < b.length; ++i) {
					if(b[i]<0){//�����쳣����
						b[i]+=256;
					}
				}
				//����ͼƬ
//				String imagepath="111.jpg";
				 out=new FileOutputStream(imagepath);
				out.write(b);
				out.flush();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(null!=out){
				    try {
					  out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}
}
