package com.xct.cms.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


public class ProxyUtil {

	public static String PROXYHOST = "";

	public static String PROXYPORT = "";

	public static String PROXYUSER = "";

	public static String PROXYPASSWORD = "";

	public void getProxy() {
		
//		System.out.println("System.getProperty(PROXY_HOME)------->"+ System.getProperty("PROXY_HOME"));
		Properties props = new Properties();
		InputStream is;
		try {
			is = new BufferedInputStream(new FileInputStream(System.getProperty("PROXY_HOME")/*"c:/c.properties"*/));
			props.load(is);
			if (is != null)
				is.close();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		PROXYHOST = props.getProperty("proxyHost").trim();
		PROXYPORT = props.getProperty("proxyPort").trim();
		PROXYUSER = props.getProperty("proxyUser").trim();
		PROXYPASSWORD = props.getProperty("proxyPassword").trim();
//		System.out.println(PROXYHOST + "-" + PROXYPORT + "-" + PROXYUSER + "-"+ PROXYPASSWORD);	
	}
	
    /**  
     * 修改或添加键值对 如果key存在，修改 反之，添加。  
     *   
     * @param key  
     * @param value  
     */  
    public  void writeProperties(String []key, String value) {
    	
        Properties prop = new Properties();   
        try {   
            File file = new File(System.getProperty("PROXY_HOME")/*"c:/c.properties"*/);   
            if (!file.exists())   
                file.createNewFile();   
            InputStream fis = new FileInputStream(file);   
            prop.load(fis);   
            fis.close();//一定要在修改值之前关闭fis   
            OutputStream fos = new FileOutputStream(System.getProperty("PROXY_HOME")/*"c:/c.properties"*/);   
            for(int i=0,n=key.length;i<n;i++){
            	prop.setProperty(key[i], value);
            }
            prop.store(fos, "create proxy.properties ");   
            fos.close();   
        } catch (IOException e) {   
        	e.printStackTrace();
//            System.out.println("Visit " + System.getProperty("PROXY_HOME") + " for updating "  + value + " value error");        
        }   
    }
    
    public static void main(String []args){
    	String [] key={"proxyHost","proxyPort","proxyUser","proxyPassword"};
    	ProxyUtil proxyutil=	new ProxyUtil();
//    	proxyutil.writeProperties(key, "xxx");
    	proxyutil.getProxy();
    }
}
