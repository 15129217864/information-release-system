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

public class Led extends DBConnection {
	Logger logger = Logger.getLogger(Led.class);

	private int id;

	private String pno;

	private String title;

	private String ip;

	private int x;

	private int y;
	
	private int width;

	private int height;

	private String text;

	private String fontName;

	private int fontColor;
	
	private int fontSize;
	
	private String stunt;
	
	private int Playspeed;
	
	private String bold;
	private String italic;
	private String underline;
	
	private String sleeptime;

	private String l_num;
	private String def_text;
	
	private String s_time1;
	private String s_time2;
	private String s_time3;
	private String s_time4;
	private String s_time5;
	private String e_time1;
	private String e_time2;
	private String e_time3;
	private String e_time4;
	private String e_time5;
	
	
	private String s_text1;
	private String s_text2;
	private String s_text3;
	private String s_text4;
	private String s_text5;
	
	
	//供高邮之仰邦BX-5M2网口通讯LED
	public Led(int id, String pno, String ip, int x, int y, int width, int height, String text, String fontName, int fontColor, int fontSize, String stunt, int playspeed, String bold, String sleeptime, String l_num) {
		super();
		this.id = id;
		this.pno = pno; //终端机ip地址
		this.ip = ip;//led条屏IP地址，目标地址
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.fontName = fontName;
		this.fontColor = fontColor;
		this.fontSize = fontSize;
		this.stunt = stunt; //特效
		Playspeed = playspeed;//运行速度
		this.bold = bold;//是否粗体
		this.sleeptime = sleeptime;//停留时间
		this.l_num = l_num;//led控制卡的型号
	}
	//供高邮之仰邦BX-5M2网口通讯LED
	public Led(String pno,String title, String ip, int x, int y, int width, int height, String text, String fontName, int fontColor, int fontSize, String stunt, int playspeed, String bold, String sleeptime, String l_num) {
		super();
		
		this.pno = pno; //终端机ip地址
		this.title=title;
		this.ip = ip;//led条屏IP地址，目标地址
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.fontName = fontName;
		this.fontColor = fontColor;
		this.fontSize = fontSize;
		this.stunt = stunt; //特效
		Playspeed = playspeed;//运行速度
		this.bold = bold;//是否粗体
		this.sleeptime = sleeptime;//停留时间
		this.l_num = l_num;//led控制卡的型号
	}

	public Led() {
		// TODO Auto-generated constructor stub
	}

	public String getS_text1() {
		return s_text1;
	}

	public void setS_text1(String s_text1) {
		this.s_text1 = s_text1;
	}

	public String getS_text2() {
		return s_text2;
	}

	public void setS_text2(String s_text2) {
		this.s_text2 = s_text2;
	}

	public String getS_text3() {
		return s_text3;
	}

	public void setS_text3(String s_text3) {
		this.s_text3 = s_text3;
	}

	public String getS_text4() {
		return s_text4;
	}

	public void setS_text4(String s_text4) {
		this.s_text4 = s_text4;
	}

	public String getS_text5() {
		return s_text5;
	}

	public void setS_text5(String s_text5) {
		this.s_text5 = s_text5;
	}

	public String getS_time1() {
		return s_time1;
	}

	public void setS_time1(String s_time1) {
		this.s_time1 = s_time1;
	}

	public String getS_time2() {
		return s_time2;
	}

	public void setS_time2(String s_time2) {
		this.s_time2 = s_time2;
	}

	public String getS_time3() {
		return s_time3;
	}

	public void setS_time3(String s_time3) {
		this.s_time3 = s_time3;
	}

	public String getS_time4() {
		return s_time4;
	}

	public void setS_time4(String s_time4) {
		this.s_time4 = s_time4;
	}

	public String getS_time5() {
		return s_time5;
	}

	public void setS_time5(String s_time5) {
		this.s_time5 = s_time5;
	}

	public String getDef_text() {
		return def_text;
	}

	public void setDef_text(String def_text) {
		this.def_text = def_text;
	}

	public String getL_num() {
		return l_num;
	}

	public void setL_num(String l_num) {
		this.l_num = l_num;
	}

	public int getFontColor() {
		return fontColor;
	}

	public void setFontColor(int fontColor) {
		this.fontColor = fontColor;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
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

	public int getPlayspeed() {
		return Playspeed;
	}

	public void setPlayspeed(int playspeed) {
		Playspeed = playspeed;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * 根据条件查询LED
	 * 
	 * @param str
	 * @return
	 */
	public List<Led> getALLLed(String str) {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Led led = null;
		List<Led> list = null;
		try {
			String sql = "select * from led  " + str + " order by id";

			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<Led>();
			while (rs.next()) {
				led = new Led();
				led.setId(rs.getInt("id"));
				led.setPno(rs.getString("pno"));
				led.setTitle(rs.getString("title"));
				led.setIp(rs.getString("ip"));
				
				led.setX(rs.getInt("x"));
				led.setY(rs.getInt("y"));
				
				led.setWidth(rs.getInt("width"));
				led.setHeight(rs.getInt("height"));
				led.setText(rs.getString("text"));
				led.setFontName(rs.getString("fontName"));
				led.setFontColor(rs.getInt("fontColor"));
				led.setFontSize(rs.getInt("fontsize"));
				
				led.setStunt(rs.getString("stunt"));//显示特技
				led.setPlayspeed(rs.getInt("Playspeed"));//运行速度
				led.setBold(rs.getString("bold"));
				led.setItalic(rs.getString("italic"));
				led.setUnderline(rs.getString("underline"));
				
				led.setSleeptime(rs.getString("sleeptime"));//停留时间
				
				led.setL_num(rs.getString("l_num"));
				led.setDef_text(rs.getString("def_text"));
				led.setS_time1(rs.getString("s_time1"));
				led.setE_time1(rs.getString("e_time1"));
				led.setS_text1(rs.getString("s_text1"));
				led.setS_time2(rs.getString("s_time2"));
				led.setE_time2(rs.getString("e_time2"));
				led.setS_text2(rs.getString("s_text2"));
				led.setS_time3(rs.getString("s_time3"));
				led.setE_time3(rs.getString("e_time3"));
				led.setS_text3(rs.getString("s_text3"));
				led.setS_time4(rs.getString("s_time4"));
				led.setE_time4(rs.getString("e_time4"));
				led.setS_text4(rs.getString("s_text4"));
				led.setS_time5(rs.getString("s_time5"));
				led.setE_time5(rs.getString("e_time5"));
				led.setS_text5(rs.getString("s_text5"));
				
				list.add(led);
			}
		} catch (Exception e) {
			logger.info("根据<<" + str + ">>获取LED出错！=====" + e.getMessage());
		} finally {
			returnResources(rs, pstmt, con);
		}
		return list;
	}
	public List<Led> getALLLed(String str2,String str) {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Led led = null;
		List<Led> list = null;
		try {
			String sql = "select "+str2+" * from led  " + str + " order by id";

			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<Led>();
			while (rs.next()) {
				led = new Led();
				led.setId(rs.getInt("id"));
				led.setPno(rs.getString("pno"));
				led.setTitle(rs.getString("title"));
				led.setIp(rs.getString("ip"));
				
				led.setX(rs.getInt("x"));
				led.setY(rs.getInt("y"));
				
				led.setWidth(rs.getInt("width"));
				led.setHeight(rs.getInt("height"));
				led.setText(rs.getString("text"));
				led.setFontName(rs.getString("fontName"));
				led.setFontColor(rs.getInt("fontColor"));
				led.setFontSize(rs.getInt("fontsize"));
				
				led.setStunt(rs.getString("stunt"));//显示特技
				led.setPlayspeed(rs.getInt("Playspeed"));//运行速度
				led.setBold(rs.getString("bold"));
				led.setItalic(rs.getString("italic"));
				led.setUnderline(rs.getString("underline"));
				led.setSleeptime(rs.getString("sleeptime"));//停留时间
				
				led.setL_num(rs.getString("l_num"));
				led.setDef_text(rs.getString("def_text"));
				
				led.setS_time1(rs.getString("s_time1"));
				led.setE_time1(rs.getString("e_time1"));
				led.setS_text1(rs.getString("s_text1"));
				led.setS_time2(rs.getString("s_time2"));
				led.setE_time2(rs.getString("e_time2"));
				led.setS_text2(rs.getString("s_text2"));
				led.setS_time3(rs.getString("s_time3"));
				led.setE_time3(rs.getString("e_time3"));
				led.setS_text3(rs.getString("s_text3"));
				led.setS_time4(rs.getString("s_time4"));
				led.setE_time4(rs.getString("e_time4"));
				led.setS_text4(rs.getString("s_text4"));
				led.setS_time5(rs.getString("s_time5"));
				led.setE_time5(rs.getString("e_time5"));
				led.setS_text5(rs.getString("s_text5"));
				list.add(led);
			}
		} catch (Exception e) {
			logger.info("根据<<" + str + ">>获取LED出错！=====" + e.getMessage());
		} finally {
			returnResources(rs, pstmt, con);
		}
		return list;
	}

	public Map<String, Led> getALLLedMap(String str) {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Led led = null;
		Map<String, Led> map = null;
		try {
			String sql = "select * from led  " + str + " order by id";

			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			map = new HashMap<String, Led>();
			while (rs.next()) {
				led = new Led();
				led.setId(rs.getInt("id"));
				led.setPno(rs.getString("pno"));
				led.setTitle(rs.getString("title"));
				led.setIp(rs.getString("ip"));
				led.setX(rs.getInt("x"));
				led.setY(rs.getInt("y"));
				
				led.setWidth(rs.getInt("width"));
				led.setHeight(rs.getInt("height"));
				led.setText(rs.getString("text"));
				led.setFontName(rs.getString("fontName"));
				led.setFontColor(rs.getInt("fontColor"));
				led.setFontSize(rs.getInt("fontsize"));
				
				led.setStunt(rs.getString("stunt"));//显示特技
				led.setPlayspeed(rs.getInt("Playspeed"));//运行速度
				led.setBold(rs.getString("bold"));
				led.setItalic(rs.getString("italic"));
				led.setUnderline(rs.getString("underline"));
				led.setSleeptime(rs.getString("sleeptime"));//停留时间
				
				led.setL_num(rs.getString("l_num"));
				led.setDef_text(rs.getString("def_text"));
				
				led.setS_time1(rs.getString("s_time1"));
				led.setE_time1(rs.getString("e_time1"));
				led.setS_text1(rs.getString("s_text1"));
				led.setS_time2(rs.getString("s_time2"));
				led.setE_time2(rs.getString("e_time2"));
				led.setS_text2(rs.getString("s_text2"));
				led.setS_time3(rs.getString("s_time3"));
				led.setE_time3(rs.getString("e_time3"));
				led.setS_text3(rs.getString("s_text3"));
				led.setS_time4(rs.getString("s_time4"));
				led.setE_time4(rs.getString("e_time4"));
				led.setS_text4(rs.getString("s_text4"));
				led.setS_time5(rs.getString("s_time5"));
				led.setE_time5(rs.getString("e_time5"));
				led.setS_text5(rs.getString("s_text5"));
				
				map.put(led.getIp(), led);
			}
		} catch (Exception e) {
			logger.info("根据<<" + str + ">>获取LED出错！=====" + e.getMessage());
		} finally {
			returnResources(rs, pstmt, con);
		}
		return map;
	}
	public Map<String, Led> getALLLedMap(int top,String str) {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Led led = null;
		Map<String, Led> map = null;
		try {
			String sql = "select top "+top+" * from led  " + str + " order by id";

			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			map = new HashMap<String, Led>();
			while (rs.next()) {
				led = new Led();
				led.setId(rs.getInt("id"));
				led.setPno(rs.getString("pno"));
				led.setTitle(rs.getString("title"));
				led.setIp(rs.getString("ip"));
				
				led.setX(rs.getInt("x"));
				led.setY(rs.getInt("y"));
				
				led.setWidth(rs.getInt("width"));
				led.setHeight(rs.getInt("height"));
				led.setText(rs.getString("text"));
				led.setFontName(rs.getString("fontName"));
				led.setFontColor(rs.getInt("fontColor"));
				led.setFontSize(rs.getInt("fontsize"));
				
				led.setStunt(rs.getString("stunt"));//显示特技
				led.setPlayspeed(rs.getInt("Playspeed"));//运行速度
				led.setBold(rs.getString("bold"));
				led.setItalic(rs.getString("italic"));
				led.setUnderline(rs.getString("underline"));
				led.setSleeptime(rs.getString("sleeptime"));//停留时间
				
				led.setL_num(rs.getString("l_num"));
				led.setDef_text(rs.getString("def_text"));
				led.setS_time1(rs.getString("s_time1"));
				led.setE_time1(rs.getString("e_time1"));
				led.setS_text1(rs.getString("s_text1"));
				led.setS_time2(rs.getString("s_time2"));
				led.setE_time2(rs.getString("e_time2"));
				led.setS_text2(rs.getString("s_text2"));
				led.setS_time3(rs.getString("s_time3"));
				led.setE_time3(rs.getString("e_time3"));
				led.setS_text3(rs.getString("s_text3"));
				led.setS_time4(rs.getString("s_time4"));
				led.setE_time4(rs.getString("e_time4"));
				led.setS_text4(rs.getString("s_text4"));
				led.setS_time5(rs.getString("s_time5"));
				led.setE_time5(rs.getString("e_time5"));
				led.setS_text5(rs.getString("s_text5"));
				
				map.put(led.getIp(), led);
			}
		} catch (Exception e) {
			logger.info("根据<<" + str + ">>获取LED出错！=====" + e.getMessage());
		} finally {
			returnResources(rs, pstmt, con);
		}
		return map;
	}

	public Led getLedBystr(String str) {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Led led = null;
		try {
			String sql = "select * from led  " + str;
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				led = new Led();
				led.setId(rs.getInt("id"));
				led.setPno(rs.getString("pno"));
				led.setTitle(rs.getString("title"));
				led.setIp(rs.getString("ip"));
				
				led.setX(rs.getInt("x"));
				led.setY(rs.getInt("y"));
				
				led.setWidth(rs.getInt("width"));
				led.setHeight(rs.getInt("height"));
				led.setText(rs.getString("text"));
				led.setFontName(rs.getString("fontName"));
				led.setFontColor(rs.getInt("fontColor"));
				led.setFontSize(rs.getInt("fontsize"));
				
				led.setStunt(rs.getString("stunt"));//显示特技
				led.setPlayspeed(rs.getInt("Playspeed"));//运行速度
				led.setBold(rs.getString("bold"));
				led.setItalic(rs.getString("italic"));
				led.setUnderline(rs.getString("underline"));
				led.setSleeptime(rs.getString("sleeptime"));//停留时间
				
				led.setL_num(rs.getString("l_num"));
				led.setDef_text(rs.getString("def_text"));
				led.setS_time1(rs.getString("s_time1"));
				led.setE_time1(rs.getString("e_time1"));
				led.setS_text1(rs.getString("s_text1"));
				led.setS_time2(rs.getString("s_time2"));
				led.setE_time2(rs.getString("e_time2"));
				led.setS_text2(rs.getString("s_text2"));
				led.setS_time3(rs.getString("s_time3"));
				led.setE_time3(rs.getString("e_time3"));
				led.setS_text3(rs.getString("s_text3"));
				led.setS_time4(rs.getString("s_time4"));
				led.setE_time4(rs.getString("e_time4"));
				led.setS_text4(rs.getString("s_text4"));
				led.setS_time5(rs.getString("s_time5"));
				led.setE_time5(rs.getString("e_time5"));
				led.setS_text5(rs.getString("s_text5"));
				
			}
		} catch (Exception e) {
			logger.info("根据<<" + str + ">>获取LED出错！=====" + e.getMessage());
		} finally {
			returnResources(rs, pstmt, con);
		}
		return led;
	}

	public void sendLedInfo(Map<String, Led> ledmap) {

		Util.deleteFile(ledstring);
		String ips = "";
		try {
			Led led = null;
			for (Map.Entry<String, Led> entry : ledmap.entrySet()) {
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
			//logger.info("向LED屏【" + ips + "】发送了文字");
		} catch (Exception e) {
			logger.info("向LED屏【" + ips + "】发送文字出错！=====" + e.getMessage());
		}
	}
	
	public void runtime(){
		
		String ledjar = "java -jar " + FirstStartServlet.projectpath + "jar/LEDControl.jar";
		try {
			Runtime.getRuntime().exec(ledjar);
		} catch (IOException e) {
			logger.info("执行发送LEDP文字runtime出错！=====" + e.getMessage());
		}
	}

	String ledstring = FirstStartServlet.projectpath + "jar/led.txt";

	public void sendLedByled(Led led,int size) {
		//String ppath = FirstStartServlet.projectpath;
//		try {
			if (led != null) {
				String ips = led.getIp();
				String new_text=led.getText();
				// SendSingleLineText#@#20.31.129.111#@#128#@#64#@#欢迎光临#@#宋体#@#65535#@#15
				String cmd = "SendSingleLineText#@#" + ips + "#@#"
						+ led.getWidth() + "#@#" + led.getHeight() + "#@#"
						+ new_text + "#@#" + led.getFontName() + "#@#"
						+ led.getFontColor() + "#@#" + led.getPlayspeed()+"#@#"+28;
				
				// String ledjar = System.getProperty("java.home") + "/bin/java
				// -jar "+ppath+"jar/LEDControl.jar "+cmd;
				
//				String ledjar = "java -jar " + ppath + "jar/LED.jar";
				
				/*String ledjar = "java -jar " + ppath + "jar/LEDControl.jar";
				System.out.println(ledjar);*/
				String old_text=FirstStartServlet.old_led_text.get(ips);
				if(old_text==null||!old_text.equals(new_text)){
	                if(size>1)
					  writeFiletoSingle(ledstring, cmd, true, true);
	                else if(size==1){
	                  writeFiletoSingle(ledstring, cmd, false, false);
	                }
	                FirstStartServlet.old_led_text.put(ips, new_text);
	                logger.info("向LED屏【" + ips + "】发送了文字【" + cmd + "】");
				}
				
//				Runtime.getRuntime().exec(ledjar);
				
			}

//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public  void writeFiletoSingle(String file, String str, boolean flag,
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
			System.out.println(e.getMessage());
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

	
	
	
	public String getE_time1() {
		return e_time1;
	}

	public void setE_time1(String e_time1) {
		this.e_time1 = e_time1;
	}

	public String getE_time2() {
		return e_time2;
	}

	public void setE_time2(String e_time2) {
		this.e_time2 = e_time2;
	}

	public String getE_time3() {
		return e_time3;
	}

	public void setE_time3(String e_time3) {
		this.e_time3 = e_time3;
	}

	public String getE_time4() {
		return e_time4;
	}

	public void setE_time4(String e_time4) {
		this.e_time4 = e_time4;
	}

	public String getE_time5() {
		return e_time5;
	}

	public void setE_time5(String e_time5) {
		this.e_time5 = e_time5;
	}

	public String getSleeptime() {
		return sleeptime;
	}

	public void setSleeptime(String sleeptime) {
		this.sleeptime = sleeptime;
	}

	public String getStunt() {
		return stunt;
	}

	public void setStunt(String stunt) {
		this.stunt = stunt;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getBold() {
		return bold;
	}

	public void setBold(String bold) {
		this.bold = bold;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public String getItalic() {
		return italic;
	}

	public void setItalic(String italic) {
		this.italic = italic;
	}

	public String getUnderline() {
		return underline;
	}

	public void setUnderline(String underline) {
		this.underline = underline;
	}
}
