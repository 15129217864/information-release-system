package com.xct.cms.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;


/**
 * 借助JCharDet获取文件字符集 JCharDet 是mozilla自动字符集探测算法代码的java移植，其官方主页为：
 * http://jchardet.sourceforge.net/
 */
public class FileCharsetDetector {

 private boolean found = false;

 /**
  * 如果完全匹配某个字符集检测算法, 则该属性保存该字符集的名称. 否则(如二进制文件)其值就为默认值 null, 这时应当查询属性
  */
 private String encoding = null;

 public static void main(String[] argv) throws Exception {

  System.out.println("文件编码:"
    + new FileCharsetDetector().guestFileEncoding("C:\\nircmd\\20131031150519360.txt"));
 }

 /**
  * 传入一个文件(File)对象，检查文件编码
  * 
  * @param file
  *            File对象实例
  * @return 文件编码，若无，则返回null
  * @throws FileNotFoundException
  * @throws IOException
  */
 public String guestFileEncoding(File file) {
	
	 String codeing=geestFileEncoding(file, new nsDetector());
	 codeing=((null==codeing)?"GBK":codeing);
	return codeing;
 }

 /**
  * 获取文件的编码
  * 
  * @param file
  *            File对象实例
  * @param languageHint
  *            语言提示区域代码 eg：1 : Japanese; 2 : Chinese; 3 : Simplified Chinese;
  *            4 : Traditional Chinese; 5 : Korean; 6 : Dont know (default)
  * @return 文件编码，eg：UTF-8,GBK,GB2312形式，若无，则返回null
  * @throws FileNotFoundException
  * @throws IOException
  */
 public String guestFileEncoding(File file, int languageHint)
   throws FileNotFoundException, IOException {
  return geestFileEncoding(file, new nsDetector(languageHint));
 }

 /**
  * 获取文件的编码
  * 
  * @param path
  *            文件路径
  * @return 文件编码，eg：UTF-8,GBK,GB2312形式，若无，则返回null
  * @throws FileNotFoundException
  * @throws IOException
  */
 public  String guestFileEncoding(String path)  {
  return guestFileEncoding(new File(path));
 }

 /**
  * 获取文件的编码
  * 
  * @param path
  *            文件路径
  * @param languageHint
  *            语言提示区域代码 eg：1 : Japanese; 2 : Chinese; 3 : Simplified Chinese;
  *            4 : Traditional Chinese; 5 : Korean; 6 : Dont know (default)
  * @return
  * @throws FileNotFoundException
  * @throws IOException
  */
 public String guestFileEncoding(String path, int languageHint)
   throws FileNotFoundException, IOException {
  return guestFileEncoding(new File(path), languageHint);
 }

 /**
  * 获取文件的编码
  * 
  * @param file
  * @param det
  * @return
  * @throws FileNotFoundException
  * @throws IOException
  */
 private String geestFileEncoding(File file, nsDetector det){
   
  // Set an observer...
  // The Notify() will be called when a matching charset is found.
  det.Init(new nsICharsetDetectionObserver() {
   public void Notify(String charset) {
    found = true;
    encoding = charset;
   }
  });

  BufferedInputStream imp=null;
	try {
		  imp = new BufferedInputStream(new FileInputStream(file));
		  byte[] buf = new byte[1024];
		  int len;
		  boolean done = false;
		  boolean isAscii = true;
		
		  while ((len = imp.read(buf, 0, buf.length)) != -1) {
		   // Check if the stream is only ascii.
		   if (isAscii)
		    isAscii = det.isAscii(buf, len);
		
		   // DoIt if non-ascii and not done yet.
		   if (!isAscii && !done)
		    done = det.DoIt(buf, len, false);
		  }
		  det.DataEnd();
		
		  if (isAscii) {
		   encoding = "ASCII";
		   found = true;
		  }
		
		  if (!found) {
		   String prob[] = det.getProbableCharsets();
		   if (prob.length > 0) {
		    // 在没有发现情况下，则取第一个可能的编码
		    encoding = prob[0];
		   } else {
		    return null;
		   }
		  }
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		if(imp!=null){
			try {
				imp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
  return encoding;
 }
}