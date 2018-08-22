package com.rsa.util;

import org.junit.Before;
import org.junit.Test;

import java.security.Key;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lake on 17-4-12.
 */
public class RSACoderTest {
    private String publicKey;
    private String privateKey;

    @Before
    public void setUp() throws Exception {
        Map<String, Key> keyMap = RSACoder.initKey();
        publicKey = RSACoder.getPublicKey(keyMap);
        privateKey = RSACoder.getPrivateKey(keyMap);
        System.err.println("��Կ: \n\r" + publicKey);
        System.err.println("˽Կ�� \n\r" + privateKey);
    }

    @Test
    public void test() throws Exception {
        System.err.println("��Կ���ܡ���˽Կ����");
        String inputStr = "888";
        byte[] encodedData = RSACoder.encryptByPublicKey(inputStr.getBytes(), publicKey);
        System.out.println("------------>"+new String(encodedData));
        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData, privateKey);
        String outputStr = new String(decodedData);
        System.err.println("����ǰ: " + inputStr + "\n\r" + "���ܺ�: " + outputStr);
        assertEquals(inputStr, outputStr);
    }

//    @Test
    public void testSign() throws Exception {
        System.err.println("˽Կ���ܡ�����Կ����");
        String inputStr = "sign";
        byte[] data = inputStr.getBytes();
        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);
        byte[] decodedData = RSACoder.decryptByPublicKey(encodedData, publicKey);
        String outputStr = new String(decodedData);
        System.err.println("����ǰ: " + inputStr + "\n\r" + "���ܺ�: " + outputStr);
        assertEquals(inputStr, outputStr);
        System.err.println("˽Կǩ��������Կ��֤ǩ��");
        // ����ǩ��
        String sign = RSACoder.sign(encodedData, privateKey);
        System.err.println("ǩ��:" + sign);
        // ��֤ǩ��
        boolean status = RSACoder.verify(encodedData, publicKey, sign);
        System.err.println("״̬:" + status);
        assertTrue(status);
    }
}