package com.xct.cms.utils;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.rsslibj.elements.Channel;
import com.rsslibj.elements.Item;
import com.rsslibj.elements.RSSReader;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import com.xct.cms.db.DBConnection;
import com.xct.cms.servlet.FirstStartServlet;

public class UtilDAO extends DBConnection {
   static Logger logger = Logger.getLogger(UtilDAO.class);
   public static String rss_content;

   public boolean isExistTable(String tablename) {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sqlString="select COUNT(*) as onetable from sysobjects  where name='"+tablename+"'";
		try {
			pstmt=con.prepareStatement(sqlString);
			rs=pstmt.executeQuery();
			if(rs.next()){
				if(rs.getInt("onetable")==1)
				   return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			returnResources(rs,pstmt,con);
		}
		return false;
	}
   
   public boolean createTable(String table,String sql) {
	   Connection con = super.getConection();
		PreparedStatement pstmt = null;
//		System.out.println(sql);
		try {
			pstmt=con.prepareStatement(sql);
			if(pstmt.executeUpdate()>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			returnResources(pstmt,con);
		}
		return false;
  }
   
 public List<String>getWeatherProvince(){
	 List<String>list=new ArrayList<String>();
	 Connection con = super.getConection();
	 PreparedStatement pstmt = null;
	 ResultSet rs=null;
	 String sqlString ="select distinct province from  weather_city";
	 try {
		pstmt=con.prepareStatement(sqlString);
		rs=pstmt.executeQuery();
		while(rs.next()){
			list.add(rs.getString("province"));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		returnResources(rs,pstmt,con);
	}
	 return list;
 }
 
 public List<String>getWeatherCity(String province){
	 
	 List<String>list=new ArrayList<String>();
	 Connection con = super.getConection();
	 PreparedStatement pstmt = null;
	 ResultSet rs=null;
	 String sqlString ="select distinct city from  weather_city where province='"+province+"'";
	 try {
		pstmt=con.prepareStatement(sqlString);
		rs=pstmt.executeQuery();
		while(rs.next()){
			list.add(rs.getString("city"));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		returnResources(rs,pstmt,con);
	}
	 return list;
 }


public  void alterColumn(String tablename,String columnname) {
	
	Connection con = super.getConection();
	PreparedStatement pstmt = null;
    String sqlString="if not exists(select * from syscolumns where id=object_id('"+tablename+"') and name='"+columnname+"') "
		+"begin"
		+ " alter table "+tablename+" add "+columnname+" nvarchar(50)"
		+" end";
	try {
		pstmt=con.prepareStatement(sqlString);
		pstmt.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		returnResources(pstmt,con);
	}
}

public  void alterColumn(String tablename,String columnname,String type) {
	
	Connection con = super.getConection();
	PreparedStatement pstmt = null;
    String sqlString="if not exists(select * from syscolumns where id=object_id('"+tablename+"') and name='"+columnname+"') "
		+"begin"
		+ " alter table "+tablename+" add "+columnname+"  "+type
		+" end";
	try {
		pstmt=con.prepareStatement(sqlString);
		pstmt.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		returnResources(pstmt,con);
	}
}


	/**
	 * ͨ�õĸ��·��������������������£����������ֶη���Map�ĵ�һ���͵ڶ���
	 * 
	 * @param map
	 *            ��Ҫ���µ�һ������
	 * @param table
	 *            Ҫ���µı���
	 * @return ���³ɹ�����true,���򷵻�false
	 */
	public boolean updateinfo2(Map info, String table) {
		boolean bool=false;
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		try {
			
			String str = "";
			Set set = (Set) info.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext()) {
				Map.Entry entry = (Map.Entry) i.next();
				String key = (String) entry.getKey();
				str += key + "=?,";
			}
			String str1 = str.substring(str.indexOf(",") + 1, str.length() - 1);
			String str3 = str1.substring(str1.indexOf(",") + 1, str1.length());
			String str2 = str.substring(0, str.indexOf(","));
			String str4 = str1.substring(0, str1.indexOf(","));
			String sql = "update " + table + " set " + str3 + " where " + str2
					+ " and " + str4;
			pstmt = con.prepareStatement(sql);
			int j = 1;
			int w = 1;
			Iterator it = set.iterator();
			String wherestr1 = "";
			String wherestr2 = "";
			while (it.hasNext()) {
				if (w == 1 || w == 2) {
					Map.Entry entry = (Map.Entry) it.next();
					String values = entry.getValue() + "";
					if (w == 1)
						wherestr1 = values;
					if (w == 2)
						wherestr2 = values;
					w++;
				} else {
					Map.Entry entry1 = (Map.Entry) it.next();
					String values1 = entry1.getValue() + "";
					pstmt.setString(j, values1);
					j++;
				}
			}
			pstmt.setString(j, wherestr1);
			pstmt.setString(j + 1, wherestr2);
			pstmt.executeUpdate();
			bool=true;
		} catch (Exception e) {
			logger.info("����"+table+"�����====="+e.getMessage());
			e.printStackTrace();
		} finally {
			returnResources(pstmt,con);
		}
		return bool;
	}

	/**
	 * ͨ�õĸ��·���������һ���������£���������ֶα������map���ϵĵ�һ��
	 * 
	 * @param map
	 *            ��Ҫ���µ�һ������
	 * @param table
	 *            Ҫ���µı���
	 * @return ���³ɹ�����true,���򷵻�false
	 */
	public boolean updateinfo(Map info, String table) {
		boolean bool=false;
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		try {
			String str = "";
			Set set = (Set) info.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext()) {
				Map.Entry entry = (Map.Entry) i.next();
				String key = (String) entry.getKey();
				str += key + "=?,";
			}
			String str1 = str.substring(str.indexOf(",") + 1, str.length() - 1);
			String str2 = str.substring(0, str.indexOf(","));
			String sql = "update " + table + " set " + str1 + " where " + str2
					+ "";
			pstmt = con.prepareStatement(sql);
//			System.out.println(sql);
			int j = 1;
			Iterator it = set.iterator();
			String whereid = "";
			while (it.hasNext()) {
				if (j == 1) {
					Map.Entry entry = (Map.Entry) it.next();
					String values = entry.getValue() + "";
					whereid = values;
				}
				Map.Entry entry1 = (Map.Entry) it.next();
				String values1 = entry1.getValue() + "";
				pstmt.setString(j, values1);
				j++;
			}
			pstmt.setString(j, whereid);
			pstmt.executeUpdate();
			bool=true;
		} catch (Exception e) {
			logger.info("����"+table+"�����====="+e.getMessage());
			e.printStackTrace();
		} finally {
			returnResources(pstmt,con);
		}
		return bool;
	}
	
	public boolean updateinfo(Connection con,Map info, String table) {
		boolean bool=false;
		PreparedStatement pstmt = null;
		try {
			if (null!=con) {
					String str = "";
					Set set = (Set) info.entrySet();
					Iterator i = set.iterator();
					while (i.hasNext()) {
						Map.Entry entry = (Map.Entry) i.next();
						String key = (String) entry.getKey();
						str += key + "=?,";
					}
					String str1 = str.substring(str.indexOf(",") + 1, str.length() - 1);
					String str2 = str.substring(0, str.indexOf(","));
					String sql = "update " + table + " set " + str1 + " where " + str2+ "";
					pstmt = con.prepareStatement(sql);
					//System.out.println(sql);
					int j = 1;
					Iterator it = set.iterator();
					String whereid = "";
					while (it.hasNext()) {
						if (j == 1) {
							Map.Entry entry = (Map.Entry) it.next();
							String values = entry.getValue() + "";
							whereid = values;
						}
						Map.Entry entry1 = (Map.Entry) it.next();
						String values1 = entry1.getValue() + "";
						pstmt.setString(j, values1);
						j++;
					}
					pstmt.setString(j, whereid);
					pstmt.executeUpdate();
					bool=true;
			}
		} catch (Exception e) {
			logger.info("����"+table+"�����====="+e.getMessage());
			e.printStackTrace();
		} finally {
			returnResources(pstmt);
		}
		return bool;
	}
	public boolean upeateinfo(String setvalue,String condition,String table){

		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		String sql ="update " + table + " set " + setvalue + " where " + condition+ "";
		 int i=0;
		try {
			pstmt = con.prepareStatement(sql);
		    i=	pstmt.executeUpdate();
			if(i>0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			returnResources(pstmt,con);
		}
		
		return false;
	}
	public boolean upeateinfo(Connection conn,String setvalue,String condition,String table){
		PreparedStatement pstmt = null;
		String sql ="update " + table + " set " + setvalue + " where " + condition+ "";
		
		//System.out.println(sql);
		 int i=0;
		try {
			pstmt = conn.prepareStatement(sql);
		    i=	pstmt.executeUpdate();
			if(i>0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			returnResources(pstmt);
		}
		
		return false;
	}
	/**
	 * ͨ�õı��淽��
	 * 
	 * @param map
	 *            ���и��µ�һ������
	 * @param table
	 *            Ҫ���µı���
	 * @return ���³ɹ�����true,���򷵻�false
	 */
	public synchronized boolean saveinfo(Map info, String table) {
		
		boolean bool=false;
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		try {
			String str = "";
			String keys = "";
			String key = "";
			Set set = (Set) info.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext()) {
				Map.Entry entry = (Map.Entry) i.next();
				key += (String) entry.getKey() + ",";
				str += "?,";
			}
			String str1 = str.substring(0, str.length() - 1);
			keys = key.substring(0, key.length() - 1);
			String sql = "insert into " + table + "(" + keys + ") values("
					+ str1 + ")";
			pstmt = con.prepareStatement(sql);
			Iterator it = set.iterator();
			int j = 1;
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String values = (String) entry.getValue();
				pstmt.setString(j, values);
				j++;
			}
			pstmt.executeUpdate();
			bool=true;
		} catch (Exception e) {
			logger.info("����"+table+"�����ݳ���====="+e.getMessage());
			e.printStackTrace();
		} finally {
			returnResources(pstmt,con);
		}
		return bool;
	}
	
	public synchronized boolean isExistMAC(Connection con,String cnt_mac){
		
		boolean bool=false;
		if(null!=con){
			String sqlmac="select cnt_mac from xct_ipaddress where cnt_mac='"+cnt_mac+"'";
			PreparedStatement pstmt = null;
			ResultSet rs=null;
			try {
				pstmt = con.prepareStatement(sqlmac);
				rs=pstmt.executeQuery();
				if(rs.next()){
					bool=true;
				}
			}catch (Exception e) {
				e.printStackTrace();
				logger.info("xct_ipaddress MAC====="+e.getMessage());
				e.printStackTrace();
				return  bool;
			} finally {
				returnResources(rs,pstmt);
			}
		}
		return  bool;
	}
	
    public synchronized boolean isExistMAC(String cnt_mac){
    	Connection con=super.getConection();
		boolean bool=false;
		if(null!=con){
			String sqlmac=new StringBuffer().append("select cnt_mac from xct_ipaddress where cnt_mac='").append(cnt_mac).append("'").toString();
			PreparedStatement pstmt = null;
			ResultSet rs=null;
			try {
				pstmt = con.prepareStatement(sqlmac);
				rs=pstmt.executeQuery();
				if(rs.next()){
					bool=true;
				}
			}catch (Exception e) {
				logger.info("xct_ipaddress MAC====="+e.getMessage());
				e.printStackTrace();
				return  bool;
			} finally {
				returnResources(rs,pstmt,con);
			}
		}
		return  bool;
	}
	
	public synchronized boolean saveinfo2(/*Connection con,*/Map<String,String> info, String table) {
		Connection con=super.getConection();
		boolean bool=false;
		if(null!=con){
			PreparedStatement pstmt = null;
			try {
					String str = "";
					String keys = "";
					String key = "";
					Set set = (Set) info.entrySet();
					Iterator i = set.iterator();
					while (i.hasNext()) {
						Map.Entry entry = (Map.Entry) i.next();
						key += (String) entry.getKey() + ",";
						str += "?,";
					}
					String str1 = str.substring(0, str.length() - 1);
					keys = key.substring(0, key.length() - 1);
					
					String sql = "insert into " + table + "(" + keys + ") values("+ str1 + ")";
					pstmt = con.prepareStatement(sql);
					Iterator it = set.iterator();
					int j = 1;
					while (it.hasNext()) {
						Map.Entry entry = (Map.Entry) it.next();
						String values = (String) entry.getValue();
						pstmt.setString(j, values);
						j++;
					}
					pstmt.executeUpdate();
					bool=true;
			} catch (Exception e) {
				logger.info("����"+table+"�����ݳ���====="+e.getMessage());
				e.printStackTrace();
			} finally {
				returnResources(pstmt,con);
			}
		}else {
			logger.info("==========���ݿ�����Ϊnull��==========");
		}
		return bool;
	}
	
	public synchronized boolean saveinfo(Connection con,Map<String,String> info, String table) {
		boolean bool=false;
		if(null!=con){
			PreparedStatement pstmt = null;
			try {
					String str = "";
					String keys = "";
					String key = "";
					Set set = (Set) info.entrySet();
					Iterator i = set.iterator();
					while (i.hasNext()) {
						Map.Entry entry = (Map.Entry) i.next();
						key += (String) entry.getKey() + ",";
						str += "?,";
					}
					String str1 = str.substring(0, str.length() - 1);
					keys = key.substring(0, key.length() - 1);
					
					String sql = "insert into " + table + "(" + keys + ") values("+ str1 + ")";
					pstmt = con.prepareStatement(sql);
					Iterator it = set.iterator();
					int j = 1;
					while (it.hasNext()) {
						Map.Entry entry = (Map.Entry) it.next();
						String values = (String) entry.getValue();
						pstmt.setString(j, values);
						j++;
					}
					pstmt.executeUpdate();
					bool=true;
			} catch (Exception e) {
				logger.info("����"+table+"�����ݳ���====="+e.getMessage());
				e.printStackTrace();
			} finally {
				returnResources(pstmt);
			}
		}else {
			logger.info("==========���ݿ�����Ϊnull��==========");
		}
		return bool;
	}

	/**
	 * ͨ�õ�ɾ������������һ������ɾ��
	 * 
	 * @param strColumn
	 *            ��������
	 * @param strColumnValue 
	 *            ������ֵ
	 * @param table 
	 *				����
	 * @return ɾ���ɹ�����true,���򷵻�false
	 */
	public boolean deleteinfo(String strColumn, String strColumnValue,
			String table) {
		boolean bool=false;
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		try {
			String sql = "delete from " + table + " where " + strColumn + " =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, strColumnValue);
			pstmt.executeUpdate();
			bool=true;
		} catch (Exception e) {
			logger.info("ɾ��"+table+"�����ݳ���====="+e.getMessage());
			e.printStackTrace();
		} finally {
			returnResources(pstmt,con);
		}
		return bool;
	}
	
	/**
	 * ͨ�õ�ɾ������������һ������ɾ��
	 * 
	 * @param strColumn
	 *            ��������
	 * @param strColumnValue 
	 *            ������ֵ
	 * @param table 
	 *				����
	 * @return ɾ���ɹ�����true,���򷵻�false
	 */
	public boolean deleteinfo(Connection conn,String strColumn, String strColumnValue,String table,String username) {
			
		boolean bool=false;
		PreparedStatement pstmt = null;
		try {
			String sql = "delete from " + table + " where " + strColumn + " =?";
			
			if("xct_module_media".equals(table))
				logger.info("xct_module_media---UtilDAO---->"+sql);
			if("xct_ipaddress".equals(table)){
			   logger.info(new StringBuffer().append(username).append("ɾ���ն�: ").append(strColumnValue).toString());
			 }
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strColumnValue);
			pstmt.executeUpdate();
			bool=true;
		} catch (Exception e) {
			logger.info("ɾ��"+table+"�����ݳ���====="+table+"====="+e.getMessage());
			e.printStackTrace();
		} finally {
			returnResources(pstmt);
		}
		return bool;
	}
	
	/**
	 * ͨ�õ�ɾ������������һ������ɾ��
	 * 
	 * @param strColumn
	 *            ��������
	 * @param strColumnValue 
	 *            ������ֵ
	 * @param table 
	 *				����
	 * @return ɾ���ɹ�����true,���򷵻�false
	 */
	public boolean deleteinfo(Connection conn,String strColumn, String strColumnValue,String table) {
			
		boolean bool=false;
		PreparedStatement pstmt = null;
		try {
			String sql = "delete from " + table + " where " + strColumn + " =?";
			
			if("xct_module_media".equals(table))
				logger.info("xct_module_media---UtilDAO---->"+sql);
			if("xct_ipaddress".equals(table)){
			   logger.info("ɾ���նˣ�==xct_ipaddress==="+strColumnValue);
			 }
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strColumnValue);
			pstmt.executeUpdate();
			bool=true;
		} catch (Exception e) {
			logger.info("ɾ��"+table+"�����ݳ���====="+table+"====="+e.getMessage());
			e.printStackTrace();
		} finally {
			returnResources(pstmt);
		}
		return bool;
	}
	/**
	 * ͨ�õ�ɾ������������һ������ɾ��
	 * 
	 * @param strColumn
	 *            ��������
	 * @param strColumnValue 
	 *            ������ֵ
	 * @param table 
	 *				����
	 * @return ɾ���ɹ�����true,���򷵻�false
	 */
	public boolean deleteinfo(String strColumnValue,String table) {
		boolean bool=false;
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		try {
			String sql = "delete from " + table + " where " + strColumnValue ;
			pstmt = con.prepareStatement(sql);
			if (pstmt.executeUpdate() == 1)
				bool=true;
		} catch (Exception e) {
			logger.info("ɾ��"+table+"�����ݳ���====="+e.getMessage());
			e.printStackTrace();
		} finally {
			returnResources(pstmt,con);
		}
		return bool;
	}
	public boolean deleteinfo1(Connection con,String strColumnValue,String table) {
		boolean bool=false;
		PreparedStatement pstmt = null;
		try {
			String sql = "delete from " + table + " where " + strColumnValue ;
			pstmt = con.prepareStatement(sql);
			if (pstmt.executeUpdate() == 1)
				bool=true;
		} catch (Exception e) {
			logger.info("ɾ��"+table+"�����ݳ���====="+e.getMessage());
			e.printStackTrace();
		} finally {
			returnResources(pstmt);
		}
		return bool;
	}
	
	/**
	 * ����һ�������Map
	 * 
	 */
	public static Map getMap() {
		Map params = (TreeMap) new TreeMap(new Comparator() {
			public int compare(Object o1, Object o2) {
				return 1;
			}
		});
		return params;
	}
	public static String getweek() {
		Date nowtime = new Date();
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(nowtime);
		  String weekstr="";
		  int week=cal.get(Calendar.DAY_OF_WEEK);
		  if(week==1){
			  weekstr= "������";
		  }else if(week==2){
			  weekstr= "����һ";
		  }else if(week==3){
			  weekstr= "���ڶ�";
		  }else if(week==4){
			  weekstr= "������";
		  }else if(week==5){
			  weekstr= "������";
		  }else if(week==6){
			  weekstr= "������";
		  }else if(week==7){
			  weekstr= "������";
		  }
		  return weekstr;
	}
	//public static void main(String[] args) {
		//String programpath="D:/tomcat/tomcat/webapps/xctSer/";
        //String imageRealPath =programpath+"mediafile/video/20101104140710328.wmv";   
		/////System.out.println(getbeginTime(2,"yyyy-MM-dd HH:mm:ss"));
		/* // TODO Auto-generated method stub
		String videoRealPath = "c:\\AJ21.rmvb";   

        //��ͼ��·�������·����   

        String imageRealPath ="c:\\a.jpg";   

        try {   

            //�����������ļ�   

            Runtime.getRuntime().exec("cmd /c start C:\\cutimg\\run.bat " + videoRealPath + " " + imageRealPath);   

        } catch (IOException e) {   

            // TODO Auto-generated catch block   

            e.printStackTrace();   

        }*/
		/*Media newmedia= new Media(); 
		newmedia.setFile_name("11111");
		newmedia.setFile_path("/mediafile/");
		newmedia.setFile_size(10000);
		
		Map formmap= new HashMap();
		formmap.put("id", "11");
		formmap.put("media",newmedia);
		*/
		/*Set set = (Set) formmap.entrySet();
		Iterator inter = set.iterator();
		Media media= new Media(); 
		while (inter.hasNext()) {
			Map.Entry entry = (Map.Entry) inter.next();
			String key = (String) entry.getKey();
			Media Media1 = (Media) entry.getValue();
			
			/////System.out.println(key+"==="+Media1);
		}*/
		/*Media Media1=(Media)formmap.get("media");
		String id=(String)formmap.get("id");
		/////System.out.println("Media1==="+Media1.getFile_name());*/
		///////System.out.println("==="+formmap.get("media").toString());
		//new UtilDAO().getRSS("http://www.people.com.cn/rss/politics.xml", "c://aaa.txt");
		
		//System.out.println(new UtilDAO().gettime("2011-06-14 16:00:00", 10));
	//}
/**
 * ������Ƶ�ļ�����ͼ
 * @param videoRealPath  Ҫ���ɵ��ļ�·��
 * @return
 */
	public static boolean  createThumbnailVideo(String videoRealPath,String filename){
        //��ͼ��·�������·����   
		String programpath=FirstStartServlet.projectpath;
        String imageRealPath =programpath+"mediafile/video/"+filename+"_slightly.jpg";   
        /////ffmpeg��·��
        String ffmpegPath=programpath+"admin/ffmpeg/";
        try {
        	String cmd="cmd /c start "+ffmpegPath+"run.bat \"" +  ffmpegPath + "\" \"" + videoRealPath  + "\" \"" + imageRealPath +"\"";
        	logger.info("������Ƶ�ļ�ִ��·����"+cmd);
            //�����������ļ�   
            String result=Runtime.getRuntime().exec(cmd).toString();  
        } catch (Exception e){
        	logger.info("������Ƶ�ļ�"+videoRealPath+"����ͼ����====="+e.getMessage());
        	e.printStackTrace();
        }
        return false;
	}
	/**
	 * ����ͼƬ�ļ�����ͼ
	 * @param videoRealPath  Ҫ���ɵ��ļ�·��
	 * @return
	 */
	public static boolean createThumbnailImg(String videoRealPath,String filename){
		try {
			String programpath=FirstStartServlet.projectpath;
			int nw =160; //640;
			int nh =120; //480 ;

			File _file = new File(videoRealPath); // �����ļ�
			Image src = javax.imageio.ImageIO.read(_file); // ����Image����
			BufferedImage tag = new BufferedImage(nw , nh ,BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(src, 0, 0, nw , nh, null); // ������С���ͼ
			FileOutputStream out = new FileOutputStream(programpath+"mediafile/img/"+filename+"_slightly.jpg"); // ������ļ���
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tag);
			param.setQuality(70f, true);
			encoder.encode(tag, param);
			out.flush();
			out.close();
			File file = new File(programpath+"mediafile/img/"+filename+"_slightly.jpg");///�ж��Ƿ���������ͼ�ɹ�
			if (file.exists()) {
				return true;
			}
		} catch(Exception e) {
			logger.info("����ͼƬ�ļ�"+videoRealPath+"����ͼ����====="+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * ����һ�����ظ���ID�ַ���
	 * 
	 * @param tablename
	 *            ���ݿ����
	 * @param clumename
	 *            ���ݿ�����
	 * @param defaultId
	 *            Ĭ��ID
	 */
	public synchronized String buildId(String tablename, String clumename,
			String defaultId) {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			String sql = "select max(" + clumename + ") from " + tablename
					+ " ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			String str1 = "";
			if (rs.next()) {
				String id = rs.getString(1);
				int lettersNum = defaultId.indexOf(".") + 1;
				str1 = getMaxChar(id, defaultId, lettersNum);
			}
			return str1;
		} catch (Exception e) {
			logger.info("����"+tablename+"��ID����====="+e.getMessage());
			e.printStackTrace();
		} finally {
			returnResources(rs,pstmt,con);
		}
		return defaultId;
	}
	public synchronized String buildId(Connection con ,String tablename, String clumename,
			String defaultId) {
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			String sql = "select max(" + clumename + ") from " + tablename
					+ " ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			String str1 = "";
			if (rs.next()) {
				String id = rs.getString(1);
				int lettersNum = defaultId.indexOf(".") + 1;
				str1 = getMaxChar(id, defaultId, lettersNum);
			}
			return str1;
		} catch (Exception e) {
			logger.info("����"+tablename+"��ID����====="+e.getMessage());
			e.printStackTrace();
		} finally {
			returnResources(rs,pstmt);
		}
		return defaultId;
	}
/**
 * 
 * @param maxChar
 * @param defaultId
 * @param i
 * @return
 */
	public synchronized static String getMaxChar(String maxChar,
			String defaultId, int i) {
		if (null == maxChar || "".equals(maxChar)) {
			return defaultId;
		} else {
			String chr = maxChar.substring(0, i);
			String bro = maxChar.substring(i);
			Integer num = new Integer("1" + bro);
			return chr + "" + String.valueOf(num.intValue() + 1).substring(1)
					+ "";
		}
	}
	/**
	 * 
	 * @param datestyle    ��ʽ�����ڣ��磺yyyy-MM-dd HH:mm:ss��
	 * @return
	 */
	public synchronized static String getNowtime(String datestyle) {
		Date nowtime = new Date();
		SimpleDateFormat format = new SimpleDateFormat(datestyle);
		String strnowtime = format.format(nowtime);
		return strnowtime;
	}
	/**
	 * 
	 * @param datestyle    ��ʽ�����ڣ��磺yyyy-MM-dd HH:mm:ss��
	 * @return
	 */
	public synchronized static String getbeginTime(int day,String datestyle) {
		Date nowtime = new Date();
		nowtime.setTime(nowtime.getTime()-24*60*60*1000*day);
		SimpleDateFormat format = new SimpleDateFormat(datestyle);
		String strnowtime = format.format(nowtime);
		return strnowtime;
	}
	/**
	 * ���ַ���ת��ΪGBK
	 */
	public static String getUTF8(String str) {
		String bar = "";
		try {
			bar = new String((str == null ? "" : str).getBytes("iso-8859-1"),
					"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.info("�ַ���ת����GBK����====="+e1.getMessage());
			e1.printStackTrace();
		}
		return bar;
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
			e1.printStackTrace();
		}
		return bar;
	}
	
	public static String endocdStr(String str) {
		String bar = "";
		try {
			bar = new String((str == null ? "" : str).getBytes("iso-8859-1"),
					"GBK");
		} catch (UnsupportedEncodingException e1) {
			logger.info("�ַ���ת����GBK����====="+e1.getMessage());
			e1.printStackTrace();
		}
		return bar;
	}

	/**
	 * ���ַ���ת��Ϊgbk
	 */
	public static String getUTF(String str) {
		String bar = "";
		try {
			bar = new String((str == null ? "" : str).getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			logger.info("�ַ���ת����UTF-8����====="+e1.getMessage());
			e1.printStackTrace();
		}
		return bar;
	}
	/**
	 * ���ַ���ת��ΪURL����
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
	 * ��URL����ת��Ϊ�ַ���
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
				ex.printStackTrace();
				result = null;
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
	public static String nvl(String str, String ifNull) {
		if (str == null)
			return ifNull;
		else
			return str;
	}

	public static String nvl(String str) {
		if (str == null)
			return "";
		else
			return str;
	}
	public static boolean VisitUrl(String strURL) {
		boolean flag = false;
		URL url = null;
		URLConnection connection = null;
		HttpURLConnection httpConn = null;
		try {
			url = new URL(strURL);
			connection = url.openConnection();
			httpConn = (HttpURLConnection) connection;
			httpConn.setConnectTimeout(3000);
			int code = httpConn.getResponseCode();
			if (code == 200) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			logger.info("====="+e.getMessage());
			e.printStackTrace();
		} finally {
			url = null;
			connection = null;
			httpConn = null;
		}
		return flag;
	}
	public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			logger.info("����"+time+"�̳߳���====="+e.getMessage());
			e.printStackTrace();
		}

	}
	public static  void writeFiletoSingle(String file, String str, boolean flag,boolean newline) {
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
			e.printStackTrace();
		}
	}
	/**
	 * ����RSS·��  ����TXT�ļ�
	 * @param urlstr
	 * @param encoding
	 * @param rssscroll
	 * @throws IOException
	 */
	public static boolean  getRSS(String urlstr,String rssscroll) {
		
		//////////////// ���������ô������������////////////////////////////////////	
		if(!(ProxyUtil.PROXYHOST.equals("xxx")&&ProxyUtil.PROXYPORT.equals("xxx"))){
			System.getProperties().put("proxySet", "true"); 
            //	����http����Ҫʹ�õĴ���������ĵ�ַ
			System.getProperties().setProperty("http.proxyHost", ProxyUtil.PROXYHOST); //����������������IP
			// ����http����Ҫʹ�õĴ���������Ķ˿� 
			System.getProperties().setProperty("http.proxyPort", ProxyUtil.PROXYPORT); //����������������˿�
			logger.info("------rss--------->����http����Ҫʹ�õĴ���������ĵ�ַ�Ͷ˿�");
		}
		if(!(ProxyUtil.PROXYUSER.equals("xxx")&&ProxyUtil.PROXYPASSWORD.equals("xxx"))){
            //����Ҫ�õ� �û��������룬����ע��ȡ��   
            System.getProperties().setProperty("http.proxyUser",ProxyUtil.PROXYUSER); //�û���     
			System.getProperties().setProperty("http.proxyPassword",ProxyUtil.PROXYPASSWORD);  //����
			logger.info("-------rss-------->����http����Ҫʹ�õĴ�����������û���������");
		}
       ///////////////////////////////////////////////////////////////////////////////
		
		 GetXMLEncoding xmlecoding = new GetXMLEncoding();
		 String encoding="utf-8";
		 if(xmlecoding.checkXml(xmlecoding.getContent(urlstr))){
			 encoding= xmlecoding.getXmlencoding();
		 }
		 logger.info("rss����-------->"+encoding);
		
		URL url = null;
		InputStream is = null;
		Channel channel = null;
		BufferedReader reader = null;
		try {
			RSSReader rssReader = new RSSReader();
			url = new URL(urlstr);
			is = url.openStream(); // ��ָ��URL��������������ȡ��URlRSS������
			reader = new BufferedReader(new InputStreamReader(is, encoding)); // ���ֽ�����ʽ������ת�����ַ���
			rssReader.setReader(reader); // �Ѵ�rss�������Ķ�ȡ��set��RSSReader��ȥ
			channel = rssReader.getChannel(); // ��ȡRSS��Channel
			// System.out.println(channel.getFeed(FEED_TYPE));
			StringBuffer sb = new StringBuffer();
			List<Item> items = channel.getItems();
			for (Iterator<Item> iter = items.iterator(); iter.hasNext();) {
				Item item = iter.next();
				sb.append(item.getTitle()).append("    ");
				// System.out.println(item.getTitle());
				// System.out.println(item.getDcDescription());
			}
			if(sb.toString().length()>50){
				rss_content=sb.toString().substring(0,40);
			}else{
				rss_content=sb.toString();
			}
			writeFiletoSingle(rssscroll, sb.toString(), false, false);
			return true;
		}catch (Exception e) {
			logger.info("��ȡRSS�쳣--------------��"+e.getMessage());
			e.printStackTrace();
			return false;
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					logger.info(getTrace(e));
				}
			}
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					logger.info(getTrace(e));
				}
			}
		}
	}
	/**
	 *����һ��ʱ�䣬��һ��ʱ���������ӣ���ȡ��һ��ʱ��
	 */
	public static String gettimeBytime(String date,int length){
		
		GregorianCalendar temp = new GregorianCalendar();
		temp.setTime(getDate(date).getTime());
		temp.add(GregorianCalendar.MINUTE, length);
		Date cal=temp.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd#HH:mm:ss");
		String strnowtime = format.format(cal);
		return strnowtime;
	}
	

	
	public static GregorianCalendar getDate(String date) {
		
		GregorianCalendar temp = new GregorianCalendar();
		java.text.SimpleDateFormat f = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			temp.setTime(f.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	/**
	 * ����Զ�̻����նˣ�ֻ�ܾ�����ʹ�ã�
	 * @param mac  �ն�MAC��ַ�������ԡ�:���ָ�  �磺00:00:00:00:00:00
	 */
	public static String  actionTerminal(String mac){ 
		try{ 
			String[] hex=mac.split(":");  ///���MAC��ַ
			int port=9;
			byte[] MACbytes=new byte[6]; 
			int i; 
			for(i=0;i<6;i++){ 
				MACbytes[i]=(byte)Integer.parseInt(hex[i], 16); 
			}
			byte[] magicPacket=new byte[6+16*MACbytes.length]; 
			for(i=0;i<6;i++) 
				magicPacket[i]=(byte)0xff; 
			for(i=6;i<magicPacket.length;i+=MACbytes.length){ 
				System.arraycopy(MACbytes,0,magicPacket,i,MACbytes.length); 
			} 
			InetAddress address=InetAddress.getByName("255.255.255.255"); 
			DatagramPacket packet=new DatagramPacket(magicPacket,magicPacket.length,address,port); 
			DatagramSocket socket=new DatagramSocket(); 
			socket.send(packet); 
			socket.close(); 
		}catch(NumberFormatException nfe){ 
			nfe.printStackTrace();
			return "���ͻ�������������ն�MAC��ַ��ʽ�д���"; 
		}catch(IOException ioe){ 
			ioe.printStackTrace();
			return "���ͻ�������������ն�����ͨ�ų�����";
		}catch(Exception e){
			e.printStackTrace();
			return "���ͻ������������δ֪���󣩣�";
		}
		return "���ͻ�������ɹ����ն���������...���Ժ�"; 
	}
	
	

/**
 * 
 * @param server  ip��ַ
 * @param timeout ��ʱ ����
 * @return 
 */
public static   boolean pingServer(String server, int timeout) {
		
		//�����ڼ�������Ƿ���ˣ��������������ݿ�֮ǰ�ж�
		int i=0;
		BufferedReader in = null;
		Runtime r = Runtime.getRuntime();

		String pingCommand = "ping " + server + " -n 2 -w " + timeout; // timeout --- �ȴ�������� ping ip
		
		try {
			Process p = r.exec(pingCommand);
			if (p == null) {
				return false;
			}
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				
				if (line.toUpperCase().indexOf("TTL")!=-1
						/*||line.startsWith("Reply from")||line.startsWith("����")*/) {
					++i;
				}
			}
			if(i==2){
			  return true;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

   public static long remainDay(){
	    long day=-1;
		String keycontent=Util.getFileValues(FirstStartServlet.projectpath+"/key.txt");//"19C848E96B00C7DC-11CAE9E6E5D8CBAD";;
//		System.out.println("*****************"+keycontent);
		if(!"".equals(keycontent)&&keycontent.indexOf("-")!=-1){
			String key[]=keycontent.split("-");
			String e_key=DESPlusUtil.Get().decrypt(key[1]);
			if(!"".equals(e_key)){
				String nowtime=UtilDAO.getNowtime("yyyyMMdd");
				String nowtime2=UtilDAO.getNowtime("yyyy").substring(0,2);
				
				java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd HH:mm");    
				java.util.Date beginDate;
				java.util.Date endDate;
				try{
					beginDate = format.parse(nowtime+" 23:59");
					endDate= format.parse(nowtime2+e_key+" 23:59");    
					day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);    
					//System.out.println("���������="+day);
				} catch (ParseException e){
					e.printStackTrace();
				}   
			}
		}
	    return day;	
   }

	public static int checkkey(){
		int bool=0;
		String keycontent=Util.getFileValues(FirstStartServlet.projectpath+"/key.txt");//"19C848E96B00C7DC-11CAE9E6E5D8CBAD";
//		String keycontent="E80A8E6C0043CE7C-80CFE800C5C5ACD4";
//		System.out.println("----------->"+keycontent);
		if(!"".equals(keycontent)&&keycontent.indexOf("-")!=-1){
			String key[]=keycontent.split("-");
//			String s_key=DESPlusUtil.Get().decrypt(key[0]);
			String e_key=DESPlusUtil.Get().decrypt(key[1].replaceAll("\\s*", ""));
//			System.out.println(/*s_key+*/"___________________"+e_key);
			if(/*!"".equals(s_key)&&*/!"".equals(e_key)){
				String nowtime=UtilDAO.getNowtime("yyMMdd");
				if(e_key.length()==6){
					if(/*nowtime.compareTo(s_key)>=0&&*/nowtime.compareTo(e_key)<=0){
						bool=1;
					}else{
						bool=3;//��Ȩ�ļ�����
					}
				}else if(e_key.length()==8){
					 nowtime=UtilDAO.getNowtime("yyyyMMdd");
					if(e_key.equals("99999999")||nowtime.compareTo(e_key)<=0){
						bool=1;
					}else{
						bool=3;//��Ȩ�ļ�����
					}
				}else{
					bool=2;//������Ȩ�ļ�����
				}
			}else{
				bool=2;//������Ȩ�ļ�����
			}
		}else{
			bool=4;//��ȡ��Ȩ�ļ������ļ���
		}
		return bool;
	}
	public  String redkey(){
		String keycontent=Util.readfile(FirstStartServlet.projectpath+"/key.txt");//"19C848E96B00C7DC-11CAE9E6E5D8CBAD";;
		
		if(!"".equals(keycontent)&&keycontent.indexOf("-")>0){
			String key[]=keycontent.split("-");
			String e_key=DESPlusUtil.Get().decrypt(key[1]);
			if(!"".equals(e_key)){
				String ss="";
//				System.out.println(e_key);
				if(e_key.compareTo("500101")>0){
					ss="unrestricted";
				}else{
					for(int i=0;i<e_key.length();i++){
						ss+=e_key.substring(i,i+1);
						if(i==1){
							ss+="��";
						}else if(i==3){
							ss+="��";
						}
					}
					ss="20"+ss+"��";
				}  
				return ss;
			}
		}
		return "<span style='color:red'>δ֪</span>";
	}
	public int regKey(String keyvalue){
		int bool=0;
		try{
			if(!"".equals(keyvalue)){
				String kk=DESPlusUtil.Get().decrypt(keyvalue);
				if(!"".equals(kk)){
					if(new UtilDAO().isNumeric(kk)){
						String nowtime=UtilDAO.getNowtime("yyMMdd");
						String n_key=DESPlusUtil.Get().encrypt(nowtime).toUpperCase();
						String keycontent=n_key+"-"+keyvalue;
						if(Util.writeFiletoSingle(FirstStartServlet.projectpath+"/key.txt", keycontent,false,false)){
							bool=checkkey();;///ע��OK
						}else{
							bool=2;   //д���ļ�ʧ��
						}
					}else{
						bool=2;  //���ܺ��ַ����в�ȫ�����ֵ� 
					}
				}else{
					bool=2;  ///����ʧ��
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			bool=2;
		}
		return bool;
	}
	public  boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 

	public static void main(String[] args) {
		System.out.println(UtilDAO.checkkey());
//		System.out.println(new UtilDAO().isNumeric("20110822"));
	}

}
