package com.xct.cms.utils;

import java.io.UnsupportedEncodingException;

public class UrlEncodeUtil {
	
	
	public static String englishSysCharTo(String s){
		
		if(null!=System.getProperty("ISENGLNISHSYS")){
		
			if(System.getProperty("ISENGLNISHSYS").equals("YES")){//µœ øƒ·win2008 64Œª ”¢Œƒ∞Ê◊™¬Îµ˜”√
				 String a="NOSTRING";
				 try {
			//		a = new String(s.getBytes("gb2312"),"utf-8"); -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8
			//		 System.out.println("1---------->"+a);
			//		 a  = new String(s.getBytes("ISO8859-1"),"utf-8");
			//		 System.out.println("2---------->"+a);
			//		 a  = new String(s.getBytes("ISO8859-1"),"gb2312");
			//		 System.out.println("3---------->"+a);
			//		 a  = new String(s.getBytes("utf-8"),"gb2312");
			//		 System.out.println("4---------->"+a);
					 a  = new String(s.getBytes("gb2312"),"ISO8859-1");
//					 System.out.println("5---englistSysCharTo------->"+a);
			//		 a  = new String(s.getBytes("utf-8"),"ISO8859-1");
			//		 System.out.println("6---------->"+a);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
					return s;
				}
				return a;
			}else{
				return s;
			}
		}else{
			return s;
		}
			
	}
	
	public static String englishSysCharToBack(String s){//µœ øƒ·win2008 64Œª ”¢Œƒ∞Ê÷’∂À…œ¥´◊÷¥Æ◊™¬Îµ˜”√
//		 System.out.println("---englishSysCharToBack------->"+s);
		if(null!=System.getProperty("ISENGLNISHSYS")){
			if(System.getProperty("ISENGLNISHSYS").equals("YES")){
				 String a="NOSTRING";
				 try {
					 a  = new String(s.getBytes("ISO8859-1"),"GBK");
		//			 System.out.println("55---englistSysCharTo------->"+a);
				 } catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				 }
				return a;
			}else{
				return s;
			}
		}else{
			return s;
		}
	}

	/**
	 * Â∞ÜÂ≠óÁ¨¶‰∏≤ËΩ¨Êç¢‰∏∫URLÁºñÁ†Å
	 * @param text 
	 * @return
	 */
	public static String Utf8ToURLencode(String text) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c >= 0 && c <= 255) {
				result.append(c);
			} else {
				byte[] b = new byte[0];
				try {
					b = Character.toString(c).getBytes("gbk");
				} catch (Exception ex) {
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					result.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return result.toString();
	}

	/**
	 * Â∞ÜURLÁºñÁ†ÅËΩ¨Êç¢‰∏∫Â≠óÁ¨¶‰∏≤
	 * @param text
	 * @return
	 */
	public static String URLdencodeToUtf8(String text) {
		String result = "";
		int p = 0;
		if (text != null && text.length() > 0) {
			text = text.toLowerCase();
			p = text.indexOf("%e");
			if (p == -1)
				return text;
			while (p != -1) {
				result += text.substring(0, p);
				text = text.substring(p, text.length());
				if (text == "" || text.length() < 9)
					return result;
				result += CodeToWord(text.substring(0, 9));
				text = text.substring(9, text.length());
				p = text.indexOf("%e");
			}
		}
		return result + text;
	}

	private static String CodeToWord(String text) {
		String result;
		if (Utf8codeCheck(text)) {
			byte[] code = new byte[3];
			code[0] = (byte) (Integer.parseInt(text.substring(1, 3), 16) - 256);
			code[1] = (byte) (Integer.parseInt(text.substring(4, 6), 16) - 256);
			code[2] = (byte) (Integer.parseInt(text.substring(7, 9), 16) - 256);
			try {
				result = new String(code, "gbk");
			} catch (Exception ex) {
				result = null;
				ex.printStackTrace();
			}
		} else {
			result = text;
		}
		return result;
	}

	private static boolean Utf8codeCheck(String text) {
		String sign = "";
		if (text.startsWith("%e"))
			for (int i = 0, p = 0; p != -1; i++) {
				p = text.indexOf("%", p);
				if (p != -1)
					p++;
				sign += p;
			}
		return sign.equals("147-1");
	}
	
	
	public static void main(String[] args) {
		// TODO Ëá™Âä®ÁîüÊàêÊñπÊ≥ïÂ≠òÊ†π

	}
}
