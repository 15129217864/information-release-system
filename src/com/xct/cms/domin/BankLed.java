package com.xct.cms.domin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.xct.cms.db.DBConnection;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.Util;

public class BankLed extends DBConnection {
	
	Logger logger = Logger.getLogger(BankLed.class);

	private int id;

	private String name;

	private String ip;

	private String width;

	private String height;

	private String text;

	private String fontName;

	private String fontColor;
	
	private String fontsize;

	private String Playspeed;
	
	private String default_text;
	
	private String response_code;
	
	private String islink;
	
	private int zu_id;

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPlayspeed() {
		return Playspeed;
	}

	public void setPlayspeed(String playspeed) {
		Playspeed = playspeed;
	}

	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
    //添加led终端的信息
	public boolean addLedCleint(String name,String ip,String width,String height) {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		int i=0;
		try {
			
			String sql = "insert into  bank_led( name,ip,width,height,text,fontname,fontcolor,fontsize,playspeed,default_text,response_code,islink,zu_id) " +
					"values('" + name + "','" + ip + "','" + width + "','" + height + "','请输入文字','宋体','255','26','15','请输入文字','0','1',1)";
			pstmt = con.prepareStatement(sql);
			i = pstmt.executeUpdate();
			
			if (i>0) {
				return true;
			}
		} catch (Exception e) {
			logger.info("插入LED数据出错！=====" + e.getMessage());
		} finally {
			returnResources(pstmt, con);
		}
		return false;
	}
	
	 //修改led终端的信息
	public boolean updateLedCleint(String id,String name,String ip,String width,String height) {
		
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		int i=0;
		try {
			String sql = "update bank_led set name='"+name+"',ip='"+ip+"',width='"+width+"',height='"+height+"' where id="+id;
			pstmt = con.prepareStatement(sql);
			i = pstmt.executeUpdate();
			if (i>0) {
				return true;
			}
		} catch (Exception e) {
			logger.info("修改LED属性数据出错！=====" + e.getMessage());
			return false;
		} finally {
			returnResources(pstmt, con);
		}
		return false;
	}
	
	 //修改led终端的发送信息
	public boolean updateLedCleint(String id,String text,String fontname,String fontsize) {
		
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		int i=0;
		try {
			String sql = "update bank_led set text='"+text+"', fontname='"+fontname+"',fontsize='"+fontsize+"' where id="+id;
			pstmt = con.prepareStatement(sql);
			i = pstmt.executeUpdate();
			if (i>0) {
				return true;
			}
		} catch (Exception e) {
			logger.info("修改LE发送数据出错！=====" + e.getMessage());
			return false;
		} finally {
			returnResources(pstmt, con);
		}
		return false;
	}
	
	
	/**
	 * 根据条件查询LED
	 * 
	 * @param str
	 * @return
	 */
	public List<BankLed> getLedByString(String str) {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BankLed led = null;
		List<BankLed> list = null;
		try {
			
			String sql = "select * from bank_led  " + str + " order by id";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<BankLed>();
			while (rs.next()) {
				led = new BankLed();
				led.setId(rs.getInt("id"));
				led.setName(rs.getString("name"));
				led.setIp(rs.getString("ip"));
				led.setWidth(rs.getString("width"));
				led.setHeight(rs.getString("height"));
				led.setText(rs.getString("text"));
				led.setFontName(rs.getString("fontname"));
				led.setFontColor(rs.getString("fontcolor"));
				led.setFontsize(rs.getString("fontsize"));
				led.setPlayspeed(rs.getString("playspeed"));

				led.setDefault_text(rs.getString("default_text"));
				led.setResponse_code(rs.getString("response_code"));
				led.setIslink(rs.getString("islink"));
				led.setZu_id(rs.getInt("zu_id"));
				list.add(led);
			}
		} catch (Exception e) {
			logger.info("根据<<" + str + ">>获取LED出错！=====" + e.getMessage());
		} finally {
			returnResources(rs, pstmt, con);
		}
		return list;
	}

	public List<BankLed>ledclientList=new ArrayList<BankLed>();
	
	//查询所有的led屏属性
	public Map<String, BankLed> getALLLedMap() {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BankLed led = null;
		Map<String, BankLed> map = null;
		try {
			String sql = "select * from bank_led  order by id";

			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			map = new HashMap<String, BankLed>();
			while (rs.next()) {
				led = new BankLed();
				led.setId(rs.getInt("id"));
				led.setName(rs.getString("name"));
				led.setIp(rs.getString("ip"));
				led.setWidth(rs.getString("width"));
				led.setHeight(rs.getString("height"));
				led.setText(rs.getString("text"));
				led.setFontName(rs.getString("fontname"));
				led.setFontColor(rs.getString("fontcolor"));
				led.setFontsize(rs.getString("fontsize"));
				led.setPlayspeed(rs.getString("playspeed"));
				led.setDefault_text(rs.getString("default_text"));
				led.setResponse_code(rs.getString("response_code"));
				led.setIslink(rs.getString("islink"));
				led.setZu_id(rs.getInt("zu_id"));
				ledclientList.add(led);
				map.put(led.getIp(), led);
			}
		} catch (Exception e) {
			logger.info("获取LED信息出错！=====" + e.getMessage());
		} finally {
			returnResources(rs, pstmt, con);
		}
		return map;
	}
	
	public void sendLedInfo(Map<String, BankLed> ledmap) {
		try {
			String ips = "";
			BankLed led = null;
			for (Map.Entry<String, BankLed> entry : ledmap.entrySet()) {
				if (entry != null) {
					led = entry.getValue();
					ips += led.getIp() + "、";
					
					// String ledjar = System.getProperty("java.home") +
					// "/bin/java -jar "+ppath+"/jar/LEDControl.jar
					// SendSingleLineText-"+led.getIp()+"-"+led.getWidth()+"-"+led.getHeight()+"-"+led.getText()+"-"+led.getFontName()+"-"+led.getFontColor()+"-"+led.getPlayspeed();
					// System.out.println(ledjar);
					// Runtime.getRuntime().exec(ledjar);
					
					led.sendLedByled(led,ledmap.size());
//					Util.sleep(7000);
				}
			}
			runtime();
			logger.info("向LED屏【" + ips + "】发送了文字");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void runtime(){
		
		String ledjar = "java -jar " + FirstStartServlet.projectpath + "jar/LEDControl.jar";
		try {
			Runtime.getRuntime().exec(ledjar);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	String ledstring = FirstStartServlet.projectpath + "jar/led.txt";

	public void sendLedByled(BankLed led,int size) {

		String ppath = FirstStartServlet.projectpath;
//		try {
			if (led != null) {
				String ips = led.getIp();
				// SendSingleLineText#@#20.31.129.111#@#128#@#64#@#欢迎光临#@#宋体#@#65535#@#15
				String cmd = "SendSingleLineText#@#" + ips + "#@#"
						+ led.getWidth() + "#@#" + led.getHeight() + "#@#"
						+ led.getText() + "#@#" + led.getFontName() + "#@#"
						+ led.getFontColor() + "#@#" + led.getPlayspeed();
				
				// String ledjar = System.getProperty("java.home") + "/bin/java
				// -jar "+ppath+"jar/LEDControl.jar "+cmd;
				
//				String ledjar = "java -jar " + ppath + "jar/LED.jar";
				
				/*String ledjar = "java -jar " + ppath + "jar/LEDControl.jar";
				System.out.println(ledjar);*/
                if(size>1)
				  writeFiletoSingle(ledstring, cmd, true, true);
                else if(size==1){
                  writeFiletoSingle(ledstring, cmd, false, false);
                }
                	
//				Runtime.getRuntime().exec(ledjar);
				logger.info("向LED屏【" + ips + "】发送了文字【" + cmd + "】");
			}

//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static void writeFiletoSingle(String file, String str, boolean flag,
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
		} catch (IOException e) {
		}
	}

	/*
	 * public void sendLedByled(Led led){ String
	 * ppath=FirstStartServlet.projectpath; try { if(led!=null){ String
	 * ips=led.getIp(); String
	 * cmd="SendSingleLineText#@#"+ips+"#@#"+led.getWidth()+"#@#"+led.getHeight()+"#@#"+led.getText()+"#@#"+led.getFontName()+"#@#"+led.getFontColor()+"#@#"+led.getPlayspeed();
	 * String ledjar = System.getProperty("java.home") + "/bin/java -jar
	 * "+ppath+"jar/LEDControl.jar "+cmd; System.out.println(cmd);
	 * Runtime.getRuntime().exec(ledjar);
	 * logger.info("向LED屏【"+ips+"】发送了文字【"+cmd+"】"); }
	 *  } catch (IOException e) { e.printStackTrace(); } }
	 */

	public static void main(String args[]) {
		// new Led().sendLedInfo();
	}

	public String getDefault_text() {
		return default_text;
	}

	public void setDefault_text(String default_text) {
		this.default_text = default_text;
	}

	public String getFontsize() {
		return fontsize;
	}

	public void setFontsize(String fontsize) {
		this.fontsize = fontsize;
	}

	public String getIslink() {
		return islink;
	}

	public void setIslink(String islink) {
		this.islink = islink;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResponse_code() {
		return response_code;
	}

	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}

	public int getZu_id() {
		return zu_id;
	}

	public void setZu_id(int zu_id) {
		this.zu_id = zu_id;
	}

	public List<BankLed> getLedclientList() {
		return ledclientList;
	}
}
