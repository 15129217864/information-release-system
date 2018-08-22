package com.xct.cms.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.linsoft.file.Txt;
import com.linsoft.xml.dom4j.Dom4j;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Terminal;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.xy.domain.ClientProjectBean;

public class Util {// Util.INSER_PROGRAM_LIST
	
	static Logger logger = Logger.getLogger(Util.class);
	
	public static String localport="80";
	
	public static Map<String,Long> TIMEROUT_TERMINALMAP=new ConcurrentHashMap<String, Long>();
	
	public static Map<String,String> TERMINAL_OP_MAP=new ConcurrentHashMap<String, String>();
	
	public static int TERMINAL_OP=0;
	
	public static CopyOnWriteArrayList<String> INSER_PROGRAM_LIST=new CopyOnWriteArrayList<String>();//记录发送节目时，一旦有插播节目集合，就加入
	
	public static int INSER_PROGRAM_OP=0;
	
	public  static void sleepS(long l){
		try {
			Thread.sleep(l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据MAC地址检查是否存在终端
	 * @param mac
	 * @return
	 */
	public synchronized static int checkIsTerminal(String mac,String cnt_ip){
		
		if(null==FirstStartServlet.terminalMap||FirstStartServlet.terminalMap.isEmpty())
		  return 0;
		
		for (Map.Entry<String, Terminal> entry : FirstStartServlet.terminalMap.entrySet()) {
//			Terminal terminal=entry.getValue();
			if(mac.equals(entry.getKey())
					/*&&terminal.getCnt_status().equals("1")*/){
				return 1;
			}
		}
		return 0;
	}
	
	public static boolean isNumeric(String str){
	    
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() )
        {
              return false;
        }
        return true;
  }
	public static String getFileContext(String url){
		File fl = new File(url);
		StringBuffer strBuffer = new StringBuffer();
		if (fl.exists()) {
			BufferedReader bufferedReader =null;
			FileReader fileReader = null;
			String currentLine = null;
			try {
				fileReader = new FileReader(fl);
				bufferedReader = new BufferedReader(fileReader);
				while ((currentLine = bufferedReader.readLine()) != null) {
					strBuffer.append(currentLine);
				}
				return strBuffer.toString();
		
			} catch (Exception e) {
				logger.error(new StringBuffer().append(" load file is error!").toString());
				e.printStackTrace();
				return "NA";
			}finally{
				if(null!=bufferedReader)
					try {
						bufferedReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}else {
			return "NA";
		}
	}
	
	
	 public static void writeWindowsLog(String message){
		 
		 String ioexecp="The Network Adapter could not establish the connection";//IO异常
		 String nobufferexecp="No buffer space available";//访问外网出现异常
		 String connecexecp="Error establishing socket";//数据库连接异常
		 
		 boolean flag=(message.indexOf(nobufferexecp)!=-1||message.indexOf(ioexecp)!=-1);
		 
		 if(flag)
		    WriteEventLog(message);
	 }
	 
	 
	 //往Windows系统里面写入事件日志
	 public static void WriteEventLog(String message) {
			
//       String message = "测试xct_DB写入系统事件日志";
       Process process;
       try{

       	String error="error";//错误
       	String warning="warning";//警告
       	String information="information";
           
           String eventID="1000";  
           //如果需要用户名和密码 则在里面加上 -u user -p password
           String cmd ="eventcreate -l application -so XCT -t "+error+"  -d \""+ message + "\"  -id " + eventID +"";

           System.out.println(cmd);
           process = Runtime.getRuntime().exec(cmd);
           int returnCode = process.waitFor();
           if (returnCode == 0) {
        	   logger.error(new StringBuffer().append("写入Windows “错误” 事务事件日志成功！").toString());
           }
           else {
        	   logger.error(new StringBuffer().append("写入Windows “错误” 事务事件日志失败！返回码： ").append(returnCode).toString());
           }
           
       }catch (Exception e) {
           e.printStackTrace();
       }
   }

    File _file ;

	Image src;

	public  void FormatJpg(String formimag){
	
		if(formimag.toLowerCase().endsWith("jpg")){
			 _file = new File(formimag); // 读入文件
			try {
				src = ImageIO.read(_file);// 构造Image对象
				int width = src.getWidth(null); // 得到源图宽
				int height = src.getHeight(null); // 得到源图长
				if(width>1920){

						BufferedImage tag = new BufferedImage(width / 2, height / 2,BufferedImage.TYPE_INT_RGB);
						tag.getGraphics().drawImage(src, 0, 0, width / 2,height / 2, null); // 绘制缩小后的图
						FileOutputStream out = new FileOutputStream(formimag); // 输出到文件流
						JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
						
						JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tag);
						param.setQuality(70f, true);
						encoder.encode(tag, param);
						out.flush();
						out.close();
						tag.flush();
						
						/*encoder.encode(tag); // 近JPEG编码
						 out.close();*/
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
	    }
	}
	/**
	 * 把指定字符按照特定的格式写入到指定文件
	 * 
	 * @param file
	 *            文件URL
	 * @param str
	 *            指定字符
	 * @param flag
	 *            是否替换,为假的时候替换
	 * @param newline
	 *            是否换行
	 */
	public static boolean writeFiletoSingle(String file, String str, boolean flag,
			boolean newline) {
		File f = new File(file);
		if (!f.exists()) {
			File t = f.getParentFile();
			if (!t.exists())
				t.mkdir();
		}
		FileWriter fw;
		try {
			fw = new FileWriter(file, flag);
			if (newline && flag)
				fw.write("\r\n");
			fw.write(str);
			if (!flag && newline)
				fw.write("\r\n");
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
			// log
			// .error("[ReadFileFactory-writeFiletoSingle]Is error by write file
			// ");
		}
	}

	
	
	
	  /**  
     * 获取widnows网卡的mac地址.  
     * @return mac地址  
     */  
    public static String getWindowsMACAddress(boolean flag) {  
    	
        String macandip = "127.0.0.1";   
        BufferedReader bufferedReader = null;   
        Process process = null;   
        try {   
            process = Runtime.getRuntime().exec("ipconfig /all");// windows下的命令，显示信息中包含有mac地址信息 ip  
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));   
            String line = null;   
            int index = -1;   
            while ((line = bufferedReader.readLine()) != null) {   
//            	System.out.println(line);
            	if(flag){
	                index = line.toLowerCase().indexOf("physical address");  
	                if (index >= 0) {  
	                    index = line.indexOf(":"); 
	                    if (index>=0) {   
	                    	macandip = line.substring(index + 1).trim();  
	                    }   
	                    break;   
	                }   
            	}else{
	                index = line.toLowerCase().indexOf("ip address");
	                if (index >= 0) {   
	                    index = line.indexOf(":");  
	                    if (index>=0) {   
	                    	macandip = line.substring(index + 1).trim(); 
	                    }   
	                    break;   
	                }   
            	}
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (bufferedReader != null) {   
                    bufferedReader.close();   
                }   
            } catch (IOException e1) {   
                e1.printStackTrace();   
            }   
            bufferedReader = null;   
            process = null;   
        }   
  
        return flag? macandip.replace("-",""):macandip;   
    } 
	
	
	/**
	 * 得到本机MAC 地址(Linux、windows)
	 */

	public static String getMACAddress() {
		String systemType = getSystemType();
		String macaddress="获取MAC地址失败！";
		if (systemType.indexOf("Windows") > -1) { // /windows getMAC
			SysCommand syscmd = new SysCommand();
			String cmd = "cmd.exe /c ipconfig/all";
			String find = "Physical Address. . . . . . . . . :";
			int MACLength = 18;
			Vector result;
			result = syscmd.execute(cmd);
			macaddress= getCmdStr(result.toString(), find, MACLength);
		} else { // ///linux getMAC
			String macandip = null;
			BufferedReader bufferedReader = null;
			Process process = null;
			try {
				process = Runtime.getRuntime().exec("ifconfig");// linux下的命令，一般取eth0作为本地主网卡
				// 显示信息中包含有mac地址信息
				bufferedReader = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				String line = null;
				int index = -1;
				while ((line = bufferedReader.readLine()) != null) {
					index = line.toLowerCase().indexOf("hwaddr");
					if (index >= 0) {
						macandip = line
								.substring(index + "hwaddr".length() + 1)
								.trim();
						break;
					}
				}
			} catch (Exception e) {
				logger.info("获取MAC地址出错！====="+e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				bufferedReader = null;
				process = null;
			}

			macaddress= macandip;
		}
		return macaddress;
	}

	public static String getUnixMACAddress(boolean flag) { // true mac false ip
		String macandip = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ifconfig");// linux下的命令，一般取eth0作为本地主网卡
			// 显示信息中包含有mac地址信息
			bufferedReader = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				if (flag) {
					index = line.toLowerCase().indexOf("hwaddr");
					if (index >= 0) {
						macandip = line
								.substring(index + "hwaddr".length() + 1)
								.trim();
						break;
					}
				} else {// index = inet addr: 192.168.10.88 Bcast:192.168.10.255
					// Mask:255.255.255.0
					index = line.toLowerCase().indexOf("inet addr:");
					if (index >= 0) {
						macandip = line.substring(
								index + "inet addr:".length(),
								line.toLowerCase().indexOf("bcast")).trim();
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.info("获取MAC地址出错！====="+e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return flag ? macandip : macandip;
	}

	/**
	 * 得到本机子网掩码地址(Linux、windows)
	 */
	public static String getSubnetAddress() {
		String systemType = getSystemType();
		String subnetaddress="获取子网掩码地址失败！";
		if (systemType.indexOf("Windows") > -1) {
			SysCommand syscmd = new SysCommand();
			String cmd = "cmd.exe /c ipconfig/all";
			String find = "Subnet Mask . . . . . . . . . . . :";
			int SubnetLength = 16;
			Vector result;
			result = syscmd.execute(cmd);
			subnetaddress= getCmdStr(result.toString(), find, SubnetLength);
		} else {
			String macandip = null;
			BufferedReader bufferedReader = null;
			Process process = null;
			try {
				process = Runtime.getRuntime().exec("ifconfig");// linux下的命令，一般取eth0作为本地主网卡
				// 显示信息中包含有mac地址信息
				bufferedReader = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				String line = null;
				int index = -1;
				while ((line = bufferedReader.readLine()) != null) {
					index = line.toLowerCase().indexOf("mask");
					if (index >= 0) {
						macandip = line.substring(index + "mask".length() + 1)
								.trim();
						break;
					}
				}
			} catch (IOException e) {
				logger.info("获取子网掩码地址出错！====="+e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				bufferedReader = null;
				process = null;
			}

			subnetaddress= macandip;
		}
		return subnetaddress;
	}

	/**
	 * 得到本机默认网关 地址(Linux、windows)
	 */
	public static String getGatewayAddress() {
		String systemType = getSystemType();
		String gatewayaddress="获取默认网关地址失败！";
		if (systemType.indexOf("Windows") > -1) {
			SysCommand syscmd = new SysCommand();
			String cmd = "cmd.exe /c ipconfig/all";
			String find = "Default Gateway . . . . . . . . . :";
			int SubnetLength = 15;
			Vector result;
			result = syscmd.execute(cmd);
			gatewayaddress=getCmdStr(result.toString(), find, SubnetLength);
		} else {

			String macandip = null;
			BufferedReader bufferedReader = null;
			Process process = null;
			try {
				process = Runtime.getRuntime().exec("route");// linux下的命令，一般取eth0作为本地主网卡
				// 显示信息中包含有mac地址信息
				bufferedReader = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				String line = null;
				int index = -1;
				while ((line = bufferedReader.readLine()) != null) {
					index = line.toLowerCase().indexOf("default");
					if (index >= 0) {
						macandip = line.trim().substring(
								index + "default".length() + 1).trim().split(
								" ")[0];
						break;
					}
				}
			} catch (IOException e) {
				logger.info("获取默认网关地址出错！====="+e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				bufferedReader = null;
				process = null;
			}

			gatewayaddress= macandip;
		}
		return gatewayaddress;
	}

	/**
	 * 得到本机DNS地址(Linux、windows)
	 */
	public static String getDns() {
		String systemType = getSystemType();
		String dns="获取DNS地址失败！";
		if (systemType.indexOf("Windows") > -1) {
			SysCommand syscmd = new SysCommand();
			String cmd = "cmd.exe /c ipconfig/all";
			String find = "DNS Servers . . . . . . . . . . . :";
			int SubnetLength = 16;
			Vector result;
			result = syscmd.execute(cmd);
			dns= getCmdStr(result.toString(), find, SubnetLength);
		} else {
			dns= "";
		}
		return dns;
	}

	static public String getCmdStr(String outstr, String find, int strLength) {
		int findIndex = outstr.indexOf(find);
		if (findIndex == -1) {
			return "未知错误！";
		} else {
			return outstr.substring(findIndex + find.length() + 1,
					findIndex + find.length() + strLength).replace(",", "")
					.trim();
		}
	}

	/**
	 * 得到本机IP 地址(Linux、windows)
	 */
	public static String getIpaddress() {
		String systemType = getSystemType();
		String ipaddr="未知地址";
		if (systemType.indexOf("Windows") > -1) {
			InetAddress myIPaddress;
			try {
				myIPaddress = InetAddress.getLocalHost();
				ipaddr= myIPaddress.toString().replace("/", "_").split("_")[1];
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			String macandip = null;
			BufferedReader bufferedReader = null;
			Process process = null;
			try {
				process = Runtime.getRuntime().exec("ifconfig");// linux下的命令，一般取eth0作为本地主网卡
				// 显示信息中包含有mac地址信息
				bufferedReader = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				String line = null;
				int index = -1;
				while ((line = bufferedReader.readLine()) != null) {
					index = line.toLowerCase().indexOf("inet addr:");
					if (index >= 0) {
						macandip = line.substring(
								index + "inet addr:".length(),
								line.toLowerCase().indexOf("bcast")).trim();
						break;
					}
				}
			} catch (Exception e) {
				logger.info("获取IP地址出错！====="+e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				bufferedReader = null;
				process = null;
			}

			ipaddr= macandip;
		}
return ipaddr;
	}

	/**
	 * 更改IP地址（网卡名称，新IP地址，新子掩码地址，新网关地址，新的DNS地址）(Linux、windows)
	 */
	public static boolean setIP(String localname, String newip,
			String newsubnet, String newgateway, String newdns) {

		try {
			String systemType = getSystemType();
			if (systemType.indexOf("Windows") > -1) {
				Runtime.getRuntime().exec(
						"netsh   interface   ip   set   addr   \"" + localname
								+ "\"   static   " + newip + "   " + newsubnet
								+ "   " + newgateway + "   1 ");
				sleep(20000);
				// Runtime.getRuntime().exec("netsh interface ip set dns
				// \""+localname+"\" static "+newdns);
				// sleep(5000);
				// Runtime.getRuntime().exec("netsh interface ip set addr
				// \""+localname+"\" static "+newip+" "+newsubnet+"
				// "+newgateway+" 1 ");
				// sleep(5000);
				// Runtime.getRuntime().exec("netsh interface ip set dns
				// \""+localname+"\" static "+newdns);
				// sleep(5000);
				///////System.out.println(localname + "：ip地址修改成功！");
				return true;
			} else {
				String cmd = "-ip=" + newip + " -wash=" + newsubnet + " -gate="
						+ newgateway;

				String edit = System.getProperty("java.home")
						+ "/bin/java -jar " + getOSPath() + "EditIP.jar " + cmd;
				///////System.out.println(edit);
				Process pro = Runtime.getRuntime().exec(edit);
				pro.waitFor();
				sleep(20000);
				///////System.out.println(localname + "：ip地址修改成功！");
				return true;
			}

		} catch (Exception e) {
			logger.info("更改IP地址出错！====="+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取当前系统名称
	 */
	public static String getSystemType() {
		Properties props = System.getProperties();
		String osName = props.getProperty("os.name");
		return osName; 
	}

	/**
	 * 获取当前系统名称
	 */
	public static String getSystemVersion() {
		Properties props = System.getProperties();
		String osVersion = props.getProperty("os.version");
		return osVersion;
	}

	/**
	 * 线程休眠
	 */
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			logger.info("休眠"+time+"线程出错！====="+e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 获取网卡信息，多个网卡返回List
	 */
	public static List getnetworks() {
		String systemType = getSystemType();
		List list = new ArrayList();
		if (systemType.indexOf("Windows") > -1) {
			SysCommand syscmd = new SysCommand();
			String cmd = "cmd.exe /c ipconfig/all";
			String find = "Ethernet adapter";
			String[] result = syscmd.execute(cmd).toString().split(find);
			for (int i = 1; i < result.length; i++) {
				list.add(result[i].split(":")[0].trim());
			}
		} else {
			list.add("本地网卡");
		}
		return list;
	}

	public static String getOSPath() {

		String location = null;
		try {
			location = URLDecoder.decode(Util.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath(), "gbk");

		} catch (UnsupportedEncodingException e) {
			return null;
		}
		if (location != null) {
			location = new File(location).getParent().replace("\\", "/");

			if (!location.endsWith("/"))
				location += "/";
		}
		return location;

	}

	/**
	 * 将字符串转换为GBK
	 */
	public static String getGBK(String str) {
		String bar = "";
		try {
			bar = new String((str == null ? "" : str).getBytes("iso-8859-1"),
					"GBK");
		} catch (UnsupportedEncodingException e1) {
			logger.info("字符串转换成GBK出错！====="+e1.getMessage());
		}
		return bar;
	}

	/**
	 * 将字符串转换为gbk
	 */
	public static String getUTF(String str) {
		String bar = "";
		try {
			bar = new String((str == null ? "" : str).getBytes("iso-8859-1"),
					"gbk");
		} catch (UnsupportedEncodingException e1) {
			logger.info("字符串转换成GBK出错！====="+e1.getMessage());
		}
		return bar;
	}

	/**
	 * 重启系统
	 */
	public static void restartSystem() {
		String edit = System.getProperty("java.home") + "/bin/java -jar "
				+ getOSPath() + "reboot.jar ";
		Process pro;
		try {
			pro = Runtime.getRuntime().exec(edit);
			pro.waitFor();
		} catch (Exception e) {
			logger.info("重启系统GBK出错！====="+e.getMessage());
			e.printStackTrace();
		}

	}

	public static String format(Date date, String format) {
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(date);
	}

	/**
	 * 得到当前时间，参数1返回年、参数2返回月、参数3返回日、参数4返回时、参数5返回分、参数6返回秒。
	 */
	public static String getNowTime(int resulttype) {
		Date date = new Date();
		String[] result = format(date, "yyyy-MM-dd-HH-mm-ss").split("-");
		if (resulttype == 1) { // //返回年
			return result[0];
		} else if (resulttype == 2) {// //返回月
			return result[1];
		} else if (resulttype == 3) {// //返回日
			return result[2];
		} else if (resulttype == 4) {// //返回时
			return result[3];
		} else if (resulttype == 5) {// //返回分
			return result[4];
		} else if (resulttype == 6) {// //返回秒
			return result[5];
		}
		return "0";
	}

	/**
	 * 复制文件（要复制的文件路径，目的路径）
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024 * 2];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			logger.info("复制单个文件操作出错！====="+e.getMessage());
			e.printStackTrace();

		}

	}
	
	/**
	 * 复制文件夹到一个目录下面（要复制的文件夹路径，目的路径）
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public static void copyFolder(String oldPath, String newPath){
		createFile(newPath);
		File file = new File(oldPath);
		if (!file.exists()) {
			 file.mkdirs();
		}
		List filelist=getFileByFilepath(oldPath);
		if(filelist!=null){
			for(int i=0;i<filelist.size();i++){
				copyFile(oldPath+"/"+filelist.get(i), newPath+"/"+filelist.get(i));
			}
		}
		
	}
	
	
	public static void fileReName(String oldfileName,String newfileName){
		File oldfile = new File(oldfileName);
		File newfile = new File(newfileName);
		oldfile.renameTo(newfile);
	}

	/**
	 * 修改系统时间，只支持 windows和Linux系统。参数（年、月、日、时分秒HH:mm:ss）
	 */
	public static boolean updateSystemTiem(String year, String month,
			String day, String hms) {
		String osName = System.getProperty("os.name");
		String cmd = "";
		try {
			if (osName.matches("^(?i)Windows.*$")) {// Window 系统
				// 格式 HH:mm:ss
				cmd = "  cmd /c time " + hms;
				Runtime.getRuntime().exec(cmd);
				// 格式：yyyy-MM-dd
				cmd = " cmd /c date " + year + "-" + month + "-" + day;
				Runtime.getRuntime().exec(cmd);
			} else {// Linux 系统
				// 格式：yyyyMMdd
				cmd = "  date -s " + year + month + day;
				Runtime.getRuntime().exec(cmd);
				// 格式 HH:mm:ss
				cmd = "  date -s " + hms;
				Runtime.getRuntime().exec(cmd);
			}
			return true;
		} catch (Exception e) {
			logger.info("修改系统时间出错！====="+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 创建文件夹，全路径
	 * @param filename
	 * @return
	 */
	public static boolean createFile(String filename){
		File file = new File(filename);
		if (file.exists()) {
			return false;
		} else {
			return file.mkdirs();
		}
	}
	/**
	 * 删除文件（删除文件的路径），也可删除文件夹及文件夹下面的内容。
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			// /////System.out.println("删除文件失败："+fileName+"文件不存在");
			return false;
		} else {
			if (file.isFile()) {
				return deletesignFile(fileName);
			} else {
				return deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean deletesignFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			// /////System.out.println("删除单个文件"+fileName+"成功！");
			return true;
		} else {
			// /////System.out.println("删除单个文件"+fileName+"失败！");
			return false;
		}
	}
	/**
	 * 判断一个文件是否存在
	 * @param fileName 文件路径
	 * @return 1、存在，2、不存在
	 */
	public static int decideFile(String fileName){
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			return 1;
		}else{
			return 0;
		}
	}
	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true,否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			// /////System.out.println("删除目录失败"+dir+"目录不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			/////System.out.println("删除目录失败");
			return false;
		}

		// 删除当前目录
		if (dirFile.delete()) {
			// /////System.out.println("删除目录"+dir+"成功！");
			return true;
		} else {
			// /////System.out.println("删除目录"+dir+"失败！");
			return false;
		}
	}
	public static double getMplayerLength(String file) {//计算视频时长,挺准

		double length = 0;
		String mplayer = FirstStartServlet.projectpath+"mplayer/mplayer.exe  -identify  -nosound -vc dummy -vo null |ID_LENGTH  "+ file;	
		try {
			Process process = Runtime.getRuntime().exec(mplayer + "\n");
			InputStream is = process.getInputStream();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(is));
			String line;
			for (; (line = stdout.readLine()) != null;) {
				if (line.startsWith("ID_LENGTH")) {
					length =  Double.parseDouble(line.split("=")[1]);
					break;
				}
			}
			stdout.close();
		} catch (Exception e) {
			logger.info("就是视频时长出错！====="+e.getMessage());
			e.printStackTrace();
		}
		return length;
	}
	public static void main(String args[]) throws IOException,
			InterruptedException {
		
		System.out.println(new  File("E:\\XCTServer\\webapps\\XctServer\\erverftp\\program_file\\035DD4BBA21.xml").getName());
		/////System.out.println(getMplayerLength("D:\\tomcat\\xctSer_tomcat\\webapps\\xctSer\\serverftp\\program_file\\20100916221620\\20100916221559703.wmv"));
		//fileReName("D:\\tomcat\\xctSer_tomcat\\webapps\\xctSer\\admin\\newprogram\\20100905174257937\\20100113102915.jpg","D:\\tomcat\\xctSer_tomcat\\webapps\\xctSer\\admin\\newprogram\\20100905174257937\\2010qqqqqqqqqqq.jpg");
		//Util.deleteFile("D:\\tomcat\\xctSer_tomcat\\webapps\\xctSer\\admin\\newprogram\\20100908175424");
		// /////System.out.println(getNowTime());

		// setIP("无线网络连接","192.168.10.5","255.255.255.0","192.168.10.1","202.96.209.133");
		// /////System.out.println(getOSPath());
		// String cmd="-ip=" + newip + " -wash=" + newsubnet+ " -gate=" +
		// newgateway;
		 String ledjar = System.getProperty("java.home") + "/bin/java -jar c://LEDControl.jar SendSingleLineText-192.168.1.11-192-32-欢迎光临-宋体-255-15";
		// String strpath= getOSPath().split("xct")[0]+ "xct/";
		// String datapath= strpath+"data/";
		// /////System.out.print(getOSPath());
		 Process pro = Runtime.getRuntime().exec(ledjar);
		// pro.waitFor();
		/*
		 * SysCommand syscmd=new SysCommand(); //系统命令 String cmd="cmd.exe /c
		 * ipconfig/all"; String find="Ethernet adapter"; int SubnetLength=16;
		 * String [] result=syscmd.execute(cmd).toString().split("Ethernet
		 * adapter"); for(int i=1;i<result.length;i++){
		 * /////System.out.println(result[i].split(":")[0].trim()); }
		 */
		
	}
	public static void newTemplate(){
		SysCommand syscmd = new SysCommand();
		String nowtime=getNowtime1();
		String cmd = "java  -Xmx512m -jar C:/Program Files/xct/view.jar -xmlpath=C:/Program Files/xct/"+nowtime+".xml";
		String find = "Physical Address. . . . . . . . . :";
		int MACLength = 18;
		Vector result;
		result = syscmd.execute(cmd);
		
	}

	/*
	 * public static String getNowTime(){ MyTimerTask.getTime(); return
	 * MyTimerTask.getNowtime();
	 *  }
	 */

	/**
	 * 恢复出厂设置 删除Xct/date目录下面除了default文件的所有文件，和清空Xct/project.xml里面的内容
	 */
	public static void changeMotheDateTimeForDelete() {
		String strpath = getOSPath().split("Xct")[0] + "Xct/";
		String datapath = strpath + "data/";
		File file[] = new File(datapath).listFiles();
		for (int i = 0; i < file.length; i++) {
			String filename = file[i].getName();
			if (!filename.equals("default")) {
				// /删除不是default的所有文件
				deleteDirectory(datapath + filename);
			}

		}
		copyFile(getOSPath() + "project.xml", strpath + "project.xml");

	}

	/**
	 * 遍历一个文件夹下面的文件，返回List 文件名的集合
	 */
	public static List getFileByFilepath(String filepath) {
		List list = new ArrayList();
		File file[] = new File(filepath).listFiles();
		for (int i = 0; i < file.length; i++) {
			String filename = file[i].getName();
			list.add(filename);
		}
		return list;
	}
	/**
	 * 遍历一个文件夹下面的文件，返回已#分开的字符串
	 */
	public static String getFileStrByFilepath(String filepath) {
		String str="#";
		File file[] = new File(filepath).listFiles();
		if(file.length<=3){
			for (int i = 0; i < file.length; i++) {
				str +=file[i].getName()+"#";
			}
		}else{
			for (int i = 0; i < 3; i++) {
				str +=file[i].getName()+"#";
			}
		}
		return str;
	}

	public static List getLogsName() {
		String strpath = getOSPath().split("Xct")[0] + "Xct/";
		String datapath = strpath + "log/";
		File file[] = new File(datapath).listFiles();
		List list = new ArrayList();
		for (int i = 0; i < file.length; i++) {
			String filename = file[i].getName();
			if (filename.substring(filename.length() - 3).equals("log")
					&& !filename.equals("NULL.log")
					&& !filename.equals("null.log")) {
				list.add(filename);
			}
		}
		Collections.sort(list, new WantMeetingRoomComparatorForSort());
		return list;

	}
	
	   public static String getFileValues(String url) {

			File fl = new File(url);
			if (fl.exists()) {
				//判断文件编码
				String codestring= "GBK";//new FileCharsetDetector().guestFileEncoding(url);
//				System.out.println(url+"_____"+codestring);
				
				StringBuffer strBuffer = new StringBuffer();
				String currentLine = null;
				BufferedReader bufferedReader= null;
				try {
					bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fl),codestring)); //迪士尼UTF-8
					while ((currentLine = bufferedReader.readLine()) != null) {
						strBuffer.append(currentLine);
					}
					return strBuffer.toString();
				} catch (Exception e) {
					e.printStackTrace();
					return "NO";
				}finally{
					if(null!=bufferedReader)
						try {
							bufferedReader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
				//---------------------原始的----------------------------
//				FileReader fileReader = null;
//				String currentLine = null;
//				try {
//					fileReader = new FileReader(fl);
//					BufferedReader bufferedReader = new BufferedReader(fileReader);
//					while ((currentLine = bufferedReader.readLine()) != null) {
//						strBuffer.append(currentLine);
//					}
//					return strBuffer.toString();
	//
//				} catch (Exception e) {
//				}
			}
			return "NO";
		}
	 
	

	public static String readfile(String filepath) {
		String content = ""; // content保存文件内容，
		BufferedReader reader = null; // 定义BufferedReader
		try {
			reader = new BufferedReader(new FileReader(filepath));
			// 按行读取文件并加入到content中。
			// 当readLine方法返回null时表示文件读取完毕。
			String line;
			while ((line = reader.readLine()) != null) {
				content += line + "\n";
			}
		} catch (Exception e) {
			logger.info("读取" + filepath + "文件失败，请检查是否存在！！====="+e.getMessage());
			e.printStackTrace();
		} finally {
			// 最后要在finally中将reader对象关闭
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content;
		// return Txt.newInstance().toString(filepath);
	}
	
	

	public static boolean writefile(String filepath, String content) {
//		String s = new String();
//		String s1 = new String();
		try {
			File f = new File(filepath);
			if (!f.exists()) {
				f.createNewFile();
			} 
			OutputStreamWriter write = new  OutputStreamWriter(new  FileOutputStream(f),/*"UTF-8"*/"GBK"); 
			BufferedWriter output = new BufferedWriter(write/*new FileWriter(f)*/);
			output.write(content);
			output.close();
			return true;
		} catch (Exception e) {
			logger.info("写入" + filepath + "文件失败，请检查是否存在！====="+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public synchronized static String getNowtime() {

		Date nowtime = new Date();
		String datestyle = "yyyy年MM月dd日 HH:mm";
		SimpleDateFormat format = new SimpleDateFormat(datestyle);
		String strnowtime = format.format(nowtime);
		return strnowtime;
	}public synchronized static String getNowtime1() {

		Date nowtime = new Date();
		String datestyle = "yyyyMMddHHmmss";
		SimpleDateFormat format = new SimpleDateFormat(datestyle);
		String strnowtime = format.format(nowtime);
		return strnowtime;
	}public synchronized static String getNowtime2() {

		Date nowtime = new Date();
		String datestyle = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat format = new SimpleDateFormat(datestyle);
		String strnowtime = format.format(nowtime);
		return strnowtime;
	}

	public static HttpServletResponse download(String path,
			HttpServletResponse response) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。
			// String ext = filename.substring(filename.lastIndexOf(".") +
			// 1).toUpperCase();

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response
					.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (Exception e) {
			logger.info("客户端取消下载《"+path+"》文件！====="+e.getMessage());
		}
		return null;
	}

	public static HttpServletResponse downloadBigFile(String path,
			HttpServletRequest request, HttpServletResponse response) {
		
		FileInputStream in = null; // 输入流
		ServletOutputStream out = null; // 输出流

		try {
			File file = new File(path);
			String filename = file.getName();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			in = new FileInputStream(path); // 读入文件
			out = response.getOutputStream();
			out.flush();
			int aRead = 0;
			while ((aRead = in.read()) != -1 & in != null) {
				out.write(aRead);
			}
			out.flush();
		} catch (Exception e) {
			logger.info("客户端下载《"+path+"》文件！====="+e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (Throwable e) {
				/////System.out.println("FileDownload doGet() IO close error!");
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void FormatJpg(String imagepath, int width, int height)
			throws Exception { // 压缩图片
		File _file = new File(imagepath); // 读入文件
		Image src = javax.imageio.ImageIO.read(_file); // 构造Image对象

		if (imagepath.endsWith("jpg") || imagepath.endsWith("JPG")) {
			if (src.getWidth(null) > 1024) {
				BufferedImage tag = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(src, 0, 0, width, height, null); // 绘制缩小后的图
				FileOutputStream out = new FileOutputStream(imagepath); // 输出到文件流
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tag);
				param.setQuality(70f, true);
				encoder.encode(tag, param);
				out.flush();
				out.close();
				/*
				 * encoder.encode(tag); // 近JPEG编码 out.close();
				 */
			}
		}
	}
	
	public static void OPownpoint(String home,String srcfilepath,String destfile) {
		String cmd= System.getProperty("java.home") + "/bin/java -jar "+home+"PowerPoint.jar "+srcfilepath+" "+destfile;
		Process process=null;
		try {
			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"--->"+cmd);
			 process= Runtime.getRuntime().exec(cmd + "\n");
			 loadStream(process.getInputStream());
				if(process!=null)
			       loadStream(process.getErrorStream());
				
				process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"-----------------over-------------");
	}
	
	public static String loadStream(InputStream in) {
//		System.out.println("------------------------》开始读取进程流");
		int ptr = 0;
		in = new BufferedInputStream(in);
		StringBuffer buffer = new StringBuffer();
		try {
			while ((ptr = in.read()) != -1) {
				buffer.append((char) ptr);
//				System.out.println(buffer.toString());
			}
		} catch (IOException e) {//Bad file descriptor
			e.printStackTrace();
		}finally{
			try {
				if(in!=null){
					in.close();
//					System.out.println("---------------------》流关闭");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 压缩文件（该文件只能全是文件，不能有文件夹）
	 * @param zipFile  zip文件名
	 * @param zipDirector  文件夹名
	 */
	public static void fileToZip(String zipFile, String zipDirector) {
		
		if (new File(zipDirector).exists()) {
			try {
				BufferedInputStream origin = null;
				FileOutputStream dest = new FileOutputStream(zipFile);
				ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
				byte data[] = new byte[2048];
				File f = new File(zipDirector);
				File files[] = f.listFiles();
				for (int i = 0; i < files.length; i++) {
					FileInputStream fi = new FileInputStream(files[i]);
					origin = new BufferedInputStream(fi, 2048);
					ZipEntry entry = new ZipEntry(files[i].getName());
					out.putNextEntry(entry);
					int count;
					while ((count = origin.read(data, 0, 2048)) != -1) {
						out.write(data, 0, count);
					}
					origin.close();
				}
				out.close();
			} catch (Exception e) {
				logger.info("《"+zipDirector+"》文件夹转换成ZIP出错====="+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 压缩文件为ZIP（该文件下面可以有文件夹）
	 * @param zipFileName
	 * @param inputFile
	 */
	public static void zip(String zipFileName, String inputFile){
		
		File f = new File(inputFile);
		ZipOutputStream out;
		try {
			FileOutputStream dest = new FileOutputStream(zipFileName);
			out = new ZipOutputStream(new BufferedOutputStream(dest));
			byte data[] = new byte[2048];
			zip(data,out, f, null);
			out.close();
		} catch (FileNotFoundException e) {
			logger.info("《"+inputFile+"》文件夹转换成ZIP出错====="+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("《"+inputFile+"》文件夹转换成ZIP出错====="+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void zip(byte data[],ZipOutputStream out, File f, String base)throws Exception {
		
//		/////System.out.println("zipping " + f.getAbsolutePath());
		BufferedInputStream origin = null;
		if (f.isDirectory()) {
			File[] fc = f.listFiles();
			if (base != null)
				out.putNextEntry(new ZipEntry(base + "/"));
			base = base == null ? "" : base + "/";
			for (int i = 0; i < fc.length; i++) {
				zip(data,out, fc[i], base + fc[i].getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			origin = new BufferedInputStream(in, 2048);
			int count;
			while ((count = origin.read(data, 0, 2048)) != -1) {
				out.write(data, 0, count);
			}
			origin.close();
			in.close();
		}
	}

}

class MyTimerTask extends TimerTask {
	static String nowtime = "";

	public void run() {
		Date d = new Date();
		/////System.out.println(d.toString());
	}

	public static void getTime() {
		Timer t = new Timer();
		MyTimerTask task = new MyTimerTask();
		t.schedule(task, 1, 1000);
	}

	public static String getNowtime() {
		return nowtime;
	}

	public static void setNowtime(String nowtime) {
		MyTimerTask.nowtime = nowtime;
	}
}