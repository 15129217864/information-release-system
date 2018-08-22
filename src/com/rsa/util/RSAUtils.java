package com.rsa.util;

import java.security.Key;
import java.util.Map;

public class RSAUtils {

	  public static String PUBLICKEY;
	  public static String PRIVATEKEY;
	  
	  static {
			try {
				Map<String, Key> keyMap = RSACoder.initKey();
				PUBLICKEY = RSACoder.getPublicKey(keyMap);
				PRIVATEKEY = RSACoder.getPrivateKey(keyMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
	  }
	  
	  public RSAUtils() {
		   
	  }
}
