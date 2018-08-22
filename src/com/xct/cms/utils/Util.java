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
	
	public static CopyOnWriteArrayList<String> INSER_PROGRAM_LIST=new CopyOnWriteArrayList<String>();//��¼���ͽ�Ŀʱ��һ���в岥��Ŀ���ϣ��ͼ���
	
	public static int INSER_PROGRAM_OP=0;
	
	public  static void sleepS(long l){
		try {
			Thread.sleep(l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����MAC��ַ����Ƿ�����ն�
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
		 
		 String ioexecp="The Network Adapter could not establish the connection";//IO�쳣
		 String nobufferexecp="No buffer space available";//�������������쳣
		 String connecexecp="Error establishing socket";//���ݿ������쳣
		 
		 boolean flag=(message.indexOf(nobufferexecp)!=-1||message.indexOf(ioexecp)!=-1);
		 
		 if(flag)
		    WriteEventLog(message);
	 }
	 
	 
	 //��Windowsϵͳ����д���¼���־
	 public static void WriteEventLog(String message) {
			
//       String message = "����xct_DBд��ϵͳ�¼���־";
       Process process;
       try{

       	String error="error";//����
       	String warning="warning";//����
       	String information="information";
           
           String eventID="1000";  
           //�����Ҫ�û��������� ����������� -u user -p password
           String cmd ="eventcreate -l application -so XCT -t "+error+"  -d \""+ message + "\"  -id " + eventID +"";

           System.out.println(cmd);
           process = Runtime.getRuntime().exec(cmd);
           int returnCode = process.waitFor();
           if (returnCode == 0) {
        	   logger.error(new StringBuffer().append("д��Windows ������ �����¼���־�ɹ���").toString());
           }
           else {
        	   logger.error(new StringBuffer().append("д��Windows ������ �����¼���־ʧ�ܣ������룺 ").append(returnCode).toString());
           }
           
       }catch (Exception e) {
           e.printStackTrace();
       }
   }

    File _file ;

	Image src;

	public  void FormatJpg(String formimag){
	
		if(formimag.toLowerCase().endsWith("jpg")){
			 _file = new File(formimag); // �����ļ�
			try {
				src = ImageIO.read(_file);// ����Image����
				int width = src.getWidth(null); // �õ�Դͼ��
				int height = src.getHeight(null); // �õ�Դͼ��
				if(width>1920){

						BufferedImage tag = new BufferedImage(width / 2, height / 2,BufferedImage.TYPE_INT_RGB);
						tag.getGraphics().drawImage(src, 0, 0, width / 2,height / 2, null); // ������С���ͼ
						FileOutputStream out = new FileOutputStream(formimag); // ������ļ���
						JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
						
						JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tag);
						param.setQuality(70f, true);
						encoder.encode(tag, param);
						out.flush();
						out.close();
						tag.flush();
						
						/*encoder.encode(tag); // ��JPEG����
						 out.close();*/
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
	    }
	}
	/**
	 * ��ָ���ַ������ض��ĸ�ʽд�뵽ָ���ļ�
	 * 
	 * @param file
	 *            �ļ�URL
	 * @param str
	 *            ָ���ַ�
	 * @param flag
	 *            �Ƿ��滻,Ϊ�ٵ�ʱ���滻
	 * @param newline
	 *            �Ƿ���
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
     * ��ȡwidnows������mac��ַ.  
     * @return mac��ַ  
     */  
    public static String getWindowsMACAddress(boolean flag) {  
    	
        String macandip = "127.0.0.1";   
        BufferedReader bufferedReader = null;   
        Process process = null;   
        try {   
            process = Runtime.getRuntime().exec("ipconfig /all");// windows�µ������ʾ��Ϣ�а�����mac��ַ��Ϣ ip  
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
	 * �õ�����MAC ��ַ(Linux��windows)
	 */

	public static String getMACAddress() {
		String systemType = getSystemType();
		String macaddress="��ȡMAC��ַʧ�ܣ�";
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
				process = Runtime.getRuntime().exec("ifconfig");// linux�µ����һ��ȡeth0��Ϊ����������
				// ��ʾ��Ϣ�а�����mac��ַ��Ϣ
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
				logger.info("��ȡMAC��ַ����====="+e.getMessage());
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
			process = Runtime.getRuntime().exec("ifconfig");// linux�µ����һ��ȡeth0��Ϊ����������
			// ��ʾ��Ϣ�а�����mac��ַ��Ϣ
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
			logger.info("��ȡMAC��ַ����====="+e.getMessage());
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
	 * �õ��������������ַ(Linux��windows)
	 */
	public static String getSubnetAddress() {
		String systemType = getSystemType();
		String subnetaddress="��ȡ���������ַʧ�ܣ�";
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
				process = Runtime.getRuntime().exec("ifconfig");// linux�µ����һ��ȡeth0��Ϊ����������
				// ��ʾ��Ϣ�а�����mac��ַ��Ϣ
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
				logger.info("��ȡ���������ַ����====="+e.getMessage());
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
	 * �õ�����Ĭ������ ��ַ(Linux��windows)
	 */
	public static String getGatewayAddress() {
		String systemType = getSystemType();
		String gatewayaddress="��ȡĬ�����ص�ַʧ�ܣ�";
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
				process = Runtime.getRuntime().exec("route");// linux�µ����һ��ȡeth0��Ϊ����������
				// ��ʾ��Ϣ�а�����mac��ַ��Ϣ
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
				logger.info("��ȡĬ�����ص�ַ����====="+e.getMessage());
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
	 * �õ�����DNS��ַ(Linux��windows)
	 */
	public static String getDns() {
		String systemType = getSystemType();
		String dns="��ȡDNS��ַʧ�ܣ�";
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
			return "δ֪����";
		} else {
			return outstr.substring(findIndex + find.length() + 1,
					findIndex + find.length() + strLength).replace(",", "")
					.trim();
		}
	}

	/**
	 * �õ�����IP ��ַ(Linux��windows)
	 */
	public static String getIpaddress() {
		String systemType = getSystemType();
		String ipaddr="δ֪��ַ";
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
				process = Runtime.getRuntime().exec("ifconfig");// linux�µ����һ��ȡeth0��Ϊ����������
				// ��ʾ��Ϣ�а�����mac��ַ��Ϣ
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
				logger.info("��ȡIP��ַ����====="+e.getMessage());
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
	 * ����IP��ַ���������ƣ���IP��ַ�����������ַ�������ص�ַ���µ�DNS��ַ��(Linux��windows)
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
				///////System.out.println(localname + "��ip��ַ�޸ĳɹ���");
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
				///////System.out.println(localname + "��ip��ַ�޸ĳɹ���");
				return true;
			}

		} catch (Exception e) {
			logger.info("����IP��ַ����====="+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ��ȡ��ǰϵͳ����
	 */
	public static String getSystemType() {
		Properties props = System.getProperties();
		String osName = props.getProperty("os.name");
		return osName; 
	}

	/**
	 * ��ȡ��ǰϵͳ����
	 */
	public static String getSystemVersion() {
		Properties props = System.getProperties();
		String osVersion = props.getProperty("os.version");
		return osVersion;
	}

	/**
	 * �߳�����
	 */
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			logger.info("����"+time+"�̳߳���====="+e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * ��ȡ������Ϣ�������������List
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
			list.add("��������");
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
	 * ���ַ���ת��ΪGBK
	 */
	public static String getGBK(String str) {
		String bar = "";
		try {
			bar = new String((str == null ? "" : str).getBytes("iso-8859-1"),
					"GBK");
		} catch (UnsupportedEncodingException e1) {
			logger.info("�ַ���ת����GBK����====="+e1.getMessage());
		}
		return bar;
	}

	/**
	 * ���ַ���ת��Ϊgbk
	 */
	public static String getUTF(String str) {
		String bar = "";
		try {
			bar = new String((str == null ? "" : str).getBytes("iso-8859-1"),
					"gbk");
		} catch (UnsupportedEncodingException e1) {
			logger.info("�ַ���ת����GBK����====="+e1.getMessage());
		}
		return bar;
	}

	/**
	 * ����ϵͳ
	 */
	public static void restartSystem() {
		String edit = System.getProperty("java.home") + "/bin/java -jar "
				+ getOSPath() + "reboot.jar ";
		Process pro;
		try {
			pro = Runtime.getRuntime().exec(edit);
			pro.waitFor();
		} catch (Exception e) {
			logger.info("����ϵͳGBK����====="+e.getMessage());
			e.printStackTrace();
		}

	}

	public static String format(Date date, String format) {
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(date);
	}

	/**
	 * �õ���ǰʱ�䣬����1�����ꡢ����2�����¡�����3�����ա�����4����ʱ������5���ط֡�����6�����롣
	 */
	public static String getNowTime(int resulttype) {
		Date date = new Date();
		String[] result = format(date, "yyyy-MM-dd-HH-mm-ss").split("-");
		if (resulttype == 1) { // //������
			return result[0];
		} else if (resulttype == 2) {// //������
			return result[1];
		} else if (resulttype == 3) {// //������
			return result[2];
		} else if (resulttype == 4) {// //����ʱ
			return result[3];
		} else if (resulttype == 5) {// //���ط�
			return result[4];
		} else if (resulttype == 6) {// //������
			return result[5];
		}
		return "0";
	}

	/**
	 * �����ļ���Ҫ���Ƶ��ļ�·����Ŀ��·����
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // �ļ�����ʱ
				InputStream inStream = new FileInputStream(oldPath); // ����ԭ�ļ�
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024 * 2];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // �ֽ��� �ļ���С
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			logger.info("���Ƶ����ļ���������====="+e.getMessage());
			e.printStackTrace();

		}

	}
	
	/**
	 * �����ļ��е�һ��Ŀ¼���棨Ҫ���Ƶ��ļ���·����Ŀ��·����
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
	 * �޸�ϵͳʱ�䣬ֻ֧�� windows��Linuxϵͳ���������ꡢ�¡��ա�ʱ����HH:mm:ss��
	 */
	public static boolean updateSystemTiem(String year, String month,
			String day, String hms) {
		String osName = System.getProperty("os.name");
		String cmd = "";
		try {
			if (osName.matches("^(?i)Windows.*$")) {// Window ϵͳ
				// ��ʽ HH:mm:ss
				cmd = "  cmd /c time " + hms;
				Runtime.getRuntime().exec(cmd);
				// ��ʽ��yyyy-MM-dd
				cmd = " cmd /c date " + year + "-" + month + "-" + day;
				Runtime.getRuntime().exec(cmd);
			} else {// Linux ϵͳ
				// ��ʽ��yyyyMMdd
				cmd = "  date -s " + year + month + day;
				Runtime.getRuntime().exec(cmd);
				// ��ʽ HH:mm:ss
				cmd = "  date -s " + hms;
				Runtime.getRuntime().exec(cmd);
			}
			return true;
		} catch (Exception e) {
			logger.info("�޸�ϵͳʱ�����====="+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * �����ļ��У�ȫ·��
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
	 * ɾ���ļ���ɾ���ļ���·������Ҳ��ɾ���ļ��м��ļ�����������ݡ�
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			// /////System.out.println("ɾ���ļ�ʧ�ܣ�"+fileName+"�ļ�������");
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
	 * ɾ�������ļ�
	 * 
	 * @param fileName
	 *            ��ɾ���ļ����ļ���
	 * @return �����ļ�ɾ���ɹ�����true,���򷵻�false
	 */
	public static boolean deletesignFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			// /////System.out.println("ɾ�������ļ�"+fileName+"�ɹ���");
			return true;
		} else {
			// /////System.out.println("ɾ�������ļ�"+fileName+"ʧ�ܣ�");
			return false;
		}
	}
	/**
	 * �ж�һ���ļ��Ƿ����
	 * @param fileName �ļ�·��
	 * @return 1�����ڣ�2��������
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
	 * ɾ��Ŀ¼���ļ��У��Լ�Ŀ¼�µ��ļ�
	 * 
	 * @param dir
	 *            ��ɾ��Ŀ¼���ļ�·��
	 * @return Ŀ¼ɾ���ɹ�����true,���򷵻�false
	 */
	public static boolean deleteDirectory(String dir) {
		// ���dir�����ļ��ָ�����β���Զ�����ļ��ָ���
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// ���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			// /////System.out.println("ɾ��Ŀ¼ʧ��"+dir+"Ŀ¼�����ڣ�");
			return false;
		}
		boolean flag = true;
		// ɾ���ļ����µ������ļ�(������Ŀ¼)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// ɾ�����ļ�
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// ɾ����Ŀ¼
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			/////System.out.println("ɾ��Ŀ¼ʧ��");
			return false;
		}

		// ɾ����ǰĿ¼
		if (dirFile.delete()) {
			// /////System.out.println("ɾ��Ŀ¼"+dir+"�ɹ���");
			return true;
		} else {
			// /////System.out.println("ɾ��Ŀ¼"+dir+"ʧ�ܣ�");
			return false;
		}
	}
	public static double getMplayerLength(String file) {//������Ƶʱ��,ͦ׼

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
			logger.info("������Ƶʱ������====="+e.getMessage());
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

		// setIP("������������","192.168.10.5","255.255.255.0","192.168.10.1","202.96.209.133");
		// /////System.out.println(getOSPath());
		// String cmd="-ip=" + newip + " -wash=" + newsubnet+ " -gate=" +
		// newgateway;
		 String ledjar = System.getProperty("java.home") + "/bin/java -jar c://LEDControl.jar SendSingleLineText-192.168.1.11-192-32-��ӭ����-����-255-15";
		// String strpath= getOSPath().split("xct")[0]+ "xct/";
		// String datapath= strpath+"data/";
		// /////System.out.print(getOSPath());
		 Process pro = Runtime.getRuntime().exec(ledjar);
		// pro.waitFor();
		/*
		 * SysCommand syscmd=new SysCommand(); //ϵͳ���� String cmd="cmd.exe /c
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
	 * �ָ��������� ɾ��Xct/dateĿ¼�������default�ļ��������ļ��������Xct/project.xml���������
	 */
	public static void changeMotheDateTimeForDelete() {
		String strpath = getOSPath().split("Xct")[0] + "Xct/";
		String datapath = strpath + "data/";
		File file[] = new File(datapath).listFiles();
		for (int i = 0; i < file.length; i++) {
			String filename = file[i].getName();
			if (!filename.equals("default")) {
				// /ɾ������default�������ļ�
				deleteDirectory(datapath + filename);
			}

		}
		copyFile(getOSPath() + "project.xml", strpath + "project.xml");

	}

	/**
	 * ����һ���ļ���������ļ�������List �ļ����ļ���
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
	 * ����һ���ļ���������ļ���������#�ֿ����ַ���
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
				//�ж��ļ�����
				String codestring= "GBK";//new FileCharsetDetector().guestFileEncoding(url);
//				System.out.println(url+"_____"+codestring);
				
				StringBuffer strBuffer = new StringBuffer();
				String currentLine = null;
				BufferedReader bufferedReader= null;
				try {
					bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fl),codestring)); //��ʿ��UTF-8
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
				//---------------------ԭʼ��----------------------------
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
		String content = ""; // content�����ļ����ݣ�
		BufferedReader reader = null; // ����BufferedReader
		try {
			reader = new BufferedReader(new FileReader(filepath));
			// ���ж�ȡ�ļ������뵽content�С�
			// ��readLine��������nullʱ��ʾ�ļ���ȡ��ϡ�
			String line;
			while ((line = reader.readLine()) != null) {
				content += line + "\n";
			}
		} catch (Exception e) {
			logger.info("��ȡ" + filepath + "�ļ�ʧ�ܣ������Ƿ���ڣ���====="+e.getMessage());
			e.printStackTrace();
		} finally {
			// ���Ҫ��finally�н�reader����ر�
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
			logger.info("д��" + filepath + "�ļ�ʧ�ܣ������Ƿ���ڣ�====="+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public synchronized static String getNowtime() {

		Date nowtime = new Date();
		String datestyle = "yyyy��MM��dd�� HH:mm";
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
			// path��ָ�����ص��ļ���·����
			File file = new File(path);
			// ȡ���ļ�����
			String filename = file.getName();
			// ȡ���ļ��ĺ�׺����
			// String ext = filename.substring(filename.lastIndexOf(".") +
			// 1).toUpperCase();

			// ��������ʽ�����ļ���
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// ���response
			response.reset();
			// ����response��Header
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
			logger.info("�ͻ���ȡ�����ء�"+path+"���ļ���====="+e.getMessage());
		}
		return null;
	}

	public static HttpServletResponse downloadBigFile(String path,
			HttpServletRequest request, HttpServletResponse response) {
		
		FileInputStream in = null; // ������
		ServletOutputStream out = null; // �����

		try {
			File file = new File(path);
			String filename = file.getName();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			in = new FileInputStream(path); // �����ļ�
			out = response.getOutputStream();
			out.flush();
			int aRead = 0;
			while ((aRead = in.read()) != -1 & in != null) {
				out.write(aRead);
			}
			out.flush();
		} catch (Exception e) {
			logger.info("�ͻ������ء�"+path+"���ļ���====="+e.getMessage());
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
			throws Exception { // ѹ��ͼƬ
		File _file = new File(imagepath); // �����ļ�
		Image src = javax.imageio.ImageIO.read(_file); // ����Image����

		if (imagepath.endsWith("jpg") || imagepath.endsWith("JPG")) {
			if (src.getWidth(null) > 1024) {
				BufferedImage tag = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(src, 0, 0, width, height, null); // ������С���ͼ
				FileOutputStream out = new FileOutputStream(imagepath); // ������ļ���
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tag);
				param.setQuality(70f, true);
				encoder.encode(tag, param);
				out.flush();
				out.close();
				/*
				 * encoder.encode(tag); // ��JPEG���� out.close();
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
//		System.out.println("------------------------����ʼ��ȡ������");
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
//					System.out.println("---------------------�����ر�");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}
	
	/**
	 * ѹ���ļ������ļ�ֻ��ȫ���ļ����������ļ��У�
	 * @param zipFile  zip�ļ���
	 * @param zipDirector  �ļ�����
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
				logger.info("��"+zipDirector+"���ļ���ת����ZIP����====="+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ѹ���ļ�ΪZIP�����ļ�����������ļ��У�
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
			logger.info("��"+inputFile+"���ļ���ת����ZIP����====="+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("��"+inputFile+"���ļ���ת����ZIP����====="+e.getMessage());
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