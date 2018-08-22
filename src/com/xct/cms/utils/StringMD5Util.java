package com.xct.cms.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringMD5Util {

	public static String getMD5String(String str) {  
        MessageDigest messageDigest = null;  
  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
  
            messageDigest.reset();  
  
            messageDigest.update(str.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {  
            System.out.println("NoSuchAlgorithmException caught!");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
  
        byte[] byteArray = messageDigest.digest();  
  
        StringBuffer md5StrBuff = new StringBuffer();  
  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
  
        return md5StrBuff.toString().toUpperCase();  
    }  
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String string="0A113EF6B61820DAA5611C870ED8D5EE";
		String string2="0A113EF6B61820DAA5611C870ED8D5EE";//888__******___0A113EF6B61820DAA5611C870ED8D5EE
        System.out.println(string.equals(string2));
	    System.out.println(getMD5String("888"));
	}

}
