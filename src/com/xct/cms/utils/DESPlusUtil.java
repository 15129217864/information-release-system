package com.xct.cms.utils;

import javax.crypto.Cipher; 

import org.apache.log4j.Logger;


import java.security.Key; 
import java.security.Security; 

public class DESPlusUtil { 
	Logger logger = Logger.getLogger(DESPlusUtil.class);
	
    //加密密匙 
    private static String strDefaultKey = "vvliveweb"; //xctcmsweb

    private Cipher encryptCipher = null; 

    private Cipher decryptCipher = null; 
    private static DESPlusUtil ins = new DESPlusUtil(); 

    public static DESPlusUtil Get() { 
        return ins; 
    } 

    /** 
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[] 
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程 
     * 
     * @param arrB 需要转换的byte数组 
     * @return 转换后的字符串 
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出 
     */ 
    private static String byteArr2HexStr(byte[] arrB) throws Exception { 
        int iLen = arrB.length; 
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍 
        StringBuffer sb = new StringBuffer(iLen * 2); 
        for (int i = 0; i < iLen; i++) { 
            int intTmp = arrB[i]; 
            // 把负数转换为正数 
            while (intTmp < 0) { 
                intTmp = intTmp + 256; 
            } 
            // 小于0F的数需要在前面补0 
            if (intTmp < 16) { 
                sb.append("0"); 
            } 
            sb.append(Integer.toString(intTmp, 16)); 
        } 
        return sb.toString(); 
    } 

    /** 
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB) 
     * 互为可逆的转换过程 
     * 
     * @param strIn 需要转换的字符串 
     * @return 转换后的byte数组 
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出 
     * @author <a href="mailto:leo841001@163.com">LiGuoQing</a> 
     */ 
    private static byte[] hexStr2ByteArr(String strIn) { 
    	byte[] arrOut=null;
    	try{
	        byte[] arrB = strIn.getBytes(); 
	        int iLen = arrB.length; 
	
	        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2 ,11CAE9E6E5D8CBAD<****>598A860FFF0D77C3
	        arrOut = new byte[iLen / 2]; 
	        for (int i = 0; i < iLen; i = i + 2) {
//	        	System.out.println(i);
	        	if(i+2 <=iLen){
		            String strTmp = new String(arrB, i, 2); 
		            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16); 
	        	}
	        } 
    	}catch (Exception e) {
			e.printStackTrace();
		}
        return arrOut; 
    } 

    /** 
     * 默认构造方法，使用默认密钥 
     * 
     * @throws Exception 
     */ 
    private DESPlusUtil() { 
        this(strDefaultKey); 
    } 

    /** 
     * 指定密钥构造方法 
     * 
     * @param strKey 指定的密钥 
     * @throws Exception 
     */ 
    private DESPlusUtil(String strKey) { 
        try { 
            Security.addProvider(new com.sun.crypto.provider.SunJCE()); 
            Key key = getKey(strKey.getBytes()); 

            encryptCipher = Cipher.getInstance("DES"); 
            encryptCipher.init(Cipher.ENCRYPT_MODE, key); 

            decryptCipher = Cipher.getInstance("DES"); 
            decryptCipher.init(Cipher.DECRYPT_MODE, key); 
        } catch (Exception e) { 
            /////System.out.println("构造加密解密工具异常"); 
			e.printStackTrace();
        } 
    } 

    /** 
     * 加密字节数组 
     * 
     * @param arrB 需加密的字节数组 
     * @return 加密后的字节数组 
     * @throws Exception 
     */ 
    private byte[] encrypt(byte[] arrB) throws Exception { 
        return encryptCipher.doFinal(arrB); 
    } 

    /** 
     * 加密字符串 
     * 
     * @param strIn 需加密的字符串 
     * @return 加密后的字符串 
     * @throws Exception 
     */ 
    public String encrypt(String strIn)  { 
        try {
			return byteArr2HexStr(encrypt(strIn.getBytes()));
		} catch (Exception e) {
		
			e.printStackTrace();
		} 
		return null;
    } 

    /** 
     * 解密字节数组 
     * 
     * @param arrB 需解密的字节数组 
     * @return 解密后的字节数组 
     * @throws Exception 
     */ 
    private byte[] decrypt(byte[] arrB) { 
        try {
			return decryptCipher.doFinal(arrB);
		} catch (Exception e) {
//			报此异常： javax.crypto.BadPaddingException: Given final block not properly padded
			e.printStackTrace();
			logger.info(new StringBuffer().append("---DESPlusUtil.decrypt is Exception---").append(e.getMessage()).toString());
		} 
		return null;
    } 
    
 /** 
     * 解密字符串 
     * 
     * @param strIn 需解密的字符串 
     * @return 解密后的字符串 
     * @throws Exception 
     */ 
  public String decrypt(String strIn) { 
    	
    	String str="";
//    	strIn=strIn.replaceAll("\\s*", "");//可以匹配空格、制表符、换页符等空白字符的其中任意一个
    	boolean flag=strIn.matches("^*[0-9a-fA-F]*$");//可以匹配16进制字符
    	
    	if(flag){
	        try {
	        	byte[] arrOut=hexStr2ByteArr(strIn);
	        	if(null!=arrOut){
	        		 byte[] decryptbye=decrypt(arrOut);
	        		 if(null!=decryptbye){
	        			 str= new String(decryptbye);
	        		 }else{
	        			 logger.info("error decrypt string-----3--->"+strIn);
	        		 }
	        	}else{
	        		logger.info("error decrypt string-----2--->"+strIn);
	   		    }
			} catch (Exception e) {
				e.printStackTrace();
			} 
    	}else{
    		logger.info("***未加密字串："+strIn);
    	}
		return str;
    } 

    /** 
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位 
     * 
     * @param arrBTmp 构成该字符串的字节数组 
     * @return 生成的密钥 
     * @throws java.lang.Exception 
     */ 
    
    private Key getKey(byte[] arrBTmp) throws Exception { 
        // 创建一个空的8位字节数组（默认值为0） 
        byte[] arrB = new byte[8]; 

        // 将原始字节数组转换为8位 
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) { 
            arrB[i] = arrBTmp[i]; 
        } 
        // 生成密钥 
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES"); 
        return key; 
    } 
    
   /* 原码:xu0000
    key:otiancai
    加密后:353FBEBEAEEEED78*/
    
    public static void main(String[] args) throws Exception { //
//        String s = "99999999"; 
        String s = "260225"; 
        String s2 = "lv0024_OK";
        String d = DESPlusUtil.Get().encrypt(s); 
        System.out.println("加密前: "+s); 
        
        System.out.println("加密后: "+d.toUpperCase());          
//        														 
//        System.out.println("解密后: "+DESPlusUtil.Get().decrypt("6C6594D29891E64A"));
//        System.out.println("解密后: "+DESPlusUtil.Get().decrypt("9fb5cfd970f124e7ef1466de4b290902f6f999a02abcd46f4e6d4f899336184aa901f93097dd2d60ba6421b6ef185bb1")); //无限期  6位  991211
//        System.out.println("解密后: "+DESPlusUtil.Get().decrypt("20F1C3FF0E26553BEAFAAD597AEF3FAC")); //无限期   8位 99999999
//        System.out.println("解密后: "+DESPlusUtil.Get().decrypt("9fb5cfd970f124e7ef1466de4b290902f6f999a02abcd46fd5d48d38d7848763d0d2568319569e2f98dbbec464cf4af5")); 
    } 
}

