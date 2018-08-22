package com.xct.cms.utils;

import javax.crypto.Cipher; 

import org.apache.log4j.Logger;


import java.security.Key; 
import java.security.Security; 

public class DESPlusUtil { 
	Logger logger = Logger.getLogger(DESPlusUtil.class);
	
    //�����ܳ� 
    private static String strDefaultKey = "vvliveweb"; //xctcmsweb

    private Cipher encryptCipher = null; 

    private Cipher decryptCipher = null; 
    private static DESPlusUtil ins = new DESPlusUtil(); 

    public static DESPlusUtil Get() { 
        return ins; 
    } 

    /** 
     * ��byte����ת��Ϊ��ʾ16����ֵ���ַ����� �磺byte[]{8,18}ת��Ϊ��0813�� ��public static byte[] 
     * hexStr2ByteArr(String strIn) ��Ϊ�����ת������ 
     * 
     * @param arrB ��Ҫת����byte���� 
     * @return ת������ַ��� 
     * @throws Exception �������������κ��쳣�������쳣ȫ���׳� 
     */ 
    private static String byteArr2HexStr(byte[] arrB) throws Exception { 
        int iLen = arrB.length; 
        // ÿ��byte�������ַ����ܱ�ʾ�������ַ����ĳ��������鳤�ȵ����� 
        StringBuffer sb = new StringBuffer(iLen * 2); 
        for (int i = 0; i < iLen; i++) { 
            int intTmp = arrB[i]; 
            // �Ѹ���ת��Ϊ���� 
            while (intTmp < 0) { 
                intTmp = intTmp + 256; 
            } 
            // С��0F������Ҫ��ǰ�油0 
            if (intTmp < 16) { 
                sb.append("0"); 
            } 
            sb.append(Integer.toString(intTmp, 16)); 
        } 
        return sb.toString(); 
    } 

    /** 
     * ����ʾ16����ֵ���ַ���ת��Ϊbyte���飬 ��public static String byteArr2HexStr(byte[] arrB) 
     * ��Ϊ�����ת������ 
     * 
     * @param strIn ��Ҫת�����ַ��� 
     * @return ת�����byte���� 
     * @throws Exception �������������κ��쳣�������쳣ȫ���׳� 
     * @author <a href="mailto:leo841001@163.com">LiGuoQing</a> 
     */ 
    private static byte[] hexStr2ByteArr(String strIn) { 
    	byte[] arrOut=null;
    	try{
	        byte[] arrB = strIn.getBytes(); 
	        int iLen = arrB.length; 
	
	        // �����ַ���ʾһ���ֽڣ������ֽ����鳤�����ַ������ȳ���2 ,11CAE9E6E5D8CBAD<****>598A860FFF0D77C3
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
     * Ĭ�Ϲ��췽����ʹ��Ĭ����Կ 
     * 
     * @throws Exception 
     */ 
    private DESPlusUtil() { 
        this(strDefaultKey); 
    } 

    /** 
     * ָ����Կ���췽�� 
     * 
     * @param strKey ָ������Կ 
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
            /////System.out.println("������ܽ��ܹ����쳣"); 
			e.printStackTrace();
        } 
    } 

    /** 
     * �����ֽ����� 
     * 
     * @param arrB ����ܵ��ֽ����� 
     * @return ���ܺ���ֽ����� 
     * @throws Exception 
     */ 
    private byte[] encrypt(byte[] arrB) throws Exception { 
        return encryptCipher.doFinal(arrB); 
    } 

    /** 
     * �����ַ��� 
     * 
     * @param strIn ����ܵ��ַ��� 
     * @return ���ܺ���ַ��� 
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
     * �����ֽ����� 
     * 
     * @param arrB ����ܵ��ֽ����� 
     * @return ���ܺ���ֽ����� 
     * @throws Exception 
     */ 
    private byte[] decrypt(byte[] arrB) { 
        try {
			return decryptCipher.doFinal(arrB);
		} catch (Exception e) {
//			�����쳣�� javax.crypto.BadPaddingException: Given final block not properly padded
			e.printStackTrace();
			logger.info(new StringBuffer().append("---DESPlusUtil.decrypt is Exception---").append(e.getMessage()).toString());
		} 
		return null;
    } 
    
 /** 
     * �����ַ��� 
     * 
     * @param strIn ����ܵ��ַ��� 
     * @return ���ܺ���ַ��� 
     * @throws Exception 
     */ 
  public String decrypt(String strIn) { 
    	
    	String str="";
//    	strIn=strIn.replaceAll("\\s*", "");//����ƥ��ո��Ʊ������ҳ���ȿհ��ַ�����������һ��
    	boolean flag=strIn.matches("^*[0-9a-fA-F]*$");//����ƥ��16�����ַ�
    	
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
    		logger.info("***δ�����ִ���"+strIn);
    	}
		return str;
    } 

    /** 
     * ��ָ���ַ���������Կ����Կ������ֽ����鳤��Ϊ8λ ����8λʱ���油0������8λֻȡǰ8λ 
     * 
     * @param arrBTmp ���ɸ��ַ������ֽ����� 
     * @return ���ɵ���Կ 
     * @throws java.lang.Exception 
     */ 
    
    private Key getKey(byte[] arrBTmp) throws Exception { 
        // ����һ���յ�8λ�ֽ����飨Ĭ��ֵΪ0�� 
        byte[] arrB = new byte[8]; 

        // ��ԭʼ�ֽ�����ת��Ϊ8λ 
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) { 
            arrB[i] = arrBTmp[i]; 
        } 
        // ������Կ 
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES"); 
        return key; 
    } 
    
   /* ԭ��:xu0000
    key:otiancai
    ���ܺ�:353FBEBEAEEEED78*/
    
    public static void main(String[] args) throws Exception { //
//        String s = "99999999"; 
        String s = "260225"; 
        String s2 = "lv0024_OK";
        String d = DESPlusUtil.Get().encrypt(s); 
        System.out.println("����ǰ: "+s); 
        
        System.out.println("���ܺ�: "+d.toUpperCase());          
//        														 
//        System.out.println("���ܺ�: "+DESPlusUtil.Get().decrypt("6C6594D29891E64A"));
//        System.out.println("���ܺ�: "+DESPlusUtil.Get().decrypt("9fb5cfd970f124e7ef1466de4b290902f6f999a02abcd46f4e6d4f899336184aa901f93097dd2d60ba6421b6ef185bb1")); //������  6λ  991211
//        System.out.println("���ܺ�: "+DESPlusUtil.Get().decrypt("20F1C3FF0E26553BEAFAAD597AEF3FAC")); //������   8λ 99999999
//        System.out.println("���ܺ�: "+DESPlusUtil.Get().decrypt("9fb5cfd970f124e7ef1466de4b290902f6f999a02abcd46fd5d48d38d7848763d0d2568319569e2f98dbbec464cf4af5")); 
    } 
}

