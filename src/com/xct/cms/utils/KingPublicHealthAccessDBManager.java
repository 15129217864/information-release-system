package com.xct.cms.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;


public class KingPublicHealthAccessDBManager {
	
	public static String HOMEPATH;
	public static String  []fstrings;
	
	static{
		HOMEPATH= System.getProperty("MAC_HOME");
		File file=new File(HOMEPATH+"conf/iCCard.properties");
		if(file.exists())
		  fstrings=readProperties(file.getAbsolutePath());
	}
	
	//逐行写入文件
	public static void writeLineFile(List<String>list,String tofile){
		
			BufferedWriter bfw = null;
	        try {
	            bfw =new BufferedWriter( new FileWriter(tofile,false));//第二个参数若为 true，则将字节写入文件末尾处，而不是写入文件开始处。
	            int i=0;
	            if(null!=list&&!list.isEmpty()){
		            for (String string : list) {
		            	if(!string.trim().equals("")){
			            	bfw.write(string);
		            	}
		            	if(++i<list.size())
		            	  bfw.newLine();
		            	bfw.flush();
					}
	            }else {
	            	bfw.write("");
	            	bfw.flush();
				}
	        } catch (IOException e1) {
	            e1.printStackTrace(); 
	        }finally{
	        	 try {
	        		 if(null!=bfw)
					   bfw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
    }
	
	
//	逐行读取文件
	public static final List<String> readLineFile(String filePath){
		
		List<String>list=new ArrayList<String>();
        BufferedReader br=null;
		try {
			br = new BufferedReader(new InputStreamReader( new FileInputStream(filePath)));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				if(!"".equals(line))
				  list.add(line);               
			}
//			for (String string : list) {
//				System.out.println(string);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(null!=br)
				   br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
        return list;
    }
	
	
	public static String [] readProperties(String filePath) {
		
		String [] strings=new String[6];
		Properties props = new Properties();
		try {
			InputStream ips = new BufferedInputStream(new FileInputStream(filePath));
			props.load(ips);
			if(ips!=null)
				ips.close();
		    strings[0]=props.getProperty("iCCard_mdbpath");
		    strings[1]=props.getProperty("doorid_info");
		    strings[2]=props.getProperty("mdbuser");
		    strings[3]=props.getProperty("mdbpass");
		    strings[4]=props.getProperty("mdb_showsql");
		    strings[5]=props.getProperty("twodoor");
//		    for (String string : strings) {
//				System.out.println(string);
//			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strings;
	}
	
	 //获得根据当天的日期计算N个工作日后日期是哪天
	 public static  Date getAfterDate(int n){ 
		 
		  Calendar c = Calendar.getInstance(); 
		  c.add(Calendar.DAY_OF_MONTH, n); 
		  return c.getTime(); 
	} 
	 
	 public static int getHour() {
			return ((GregorianCalendar) GregorianCalendar.getInstance())
					.get(Calendar.HOUR_OF_DAY);

		}

		public static int getMinute() {

			return ((GregorianCalendar) GregorianCalendar.getInstance())
					.get(Calendar.MINUTE);
		}
		public int getSecond() {

			return ((GregorianCalendar) GregorianCalendar.getInstance())
					.get(Calendar.SECOND);
		}
		
	static	boolean flag=false;
		
	public static boolean deleteOneYearAgoCardRecord(){
		
		if(getMinute()==50)
			flag=true;
		
//		if(flag&&getHour()==5&&getMinute()==0){
			flag=false;
			String oneyearago=new SimpleDateFormat("yyyy-MM-dd").format(getAfterDate(-365))+" 00:00:00";
//			System.out.println(oneyearago);
			String sql="delete from t_d_CardRecord where f_ReadDate <= #"+oneyearago+"#";
			
			PreparedStatement pstmt=null;
			Connection conn=null;
			String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="+fstrings[0];
//			String url = "jdbc:rmi://172.30.80.27/jdbc:odbc"+fstrings[0];// 数据库的连接地址 
			 try {
//				 System.out.println(sql);
//				 Class.forName("org.objectweb.rmijdbc.Driver").newInstance();// 创建数据库驱动的实例 
				conn = DriverManager.getConnection(url,fstrings[2],fstrings[3]);
				pstmt = conn.prepareStatement(sql);
				int count=pstmt.executeUpdate();
				if(count>0){
					System.out.println("删除记录条数--------->"+count);
				   return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				 return false;
			}finally{
				try {
					if(null!=pstmt)
						pstmt.close();
					if(null!=conn)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
//		}
		 return false;
	}

	public static List<String> getICCardUserInfo() {// hyszggwc  172.30.80.27
		 
	    
	    List<String>inlist=new ArrayList<String>();
	    List<String>usersnamelist=new ArrayList<String>();
        
	    TreeSet<String> doornameset=new TreeSet<String>();
	    TreeSet<String> allincomeset=new TreeSet<String>();
		 
	    String today =new SimpleDateFormat("yyyy-MM-dd").format(new Date())/*"2013-10-14"*/;  //Y110A ,Y110B
	    PreparedStatement pstmt=null;
	    Connection conn=null;
	    ResultSet rs=null;
	    
	    String [] inoutdoor={"[进门]","[出门]"};
		try{
			String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="+fstrings[0];
			
//			 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			 conn = DriverManager.getConnection(url,fstrings[2],fstrings[3]);
			 
			 String doornamesql="select distinct a.f_DoorID,a.f_DoorName from t_b_Door a,t_d_Privilege b,t_b_Consumer c " +
				                       "where a.f_DoorID=b.f_DoorID and b.f_ConsumerID=c.f_ConsumerID and a.f_DoorID in (S_DOORID)";
			 
			 String sqlstirng1= " and  f_ReadDate between #"+today+" 00:00:00# and #"+today+" 23:59:59# ";
			 
			 
			 String userssql="select distinct f_ConsumerName from v_d_CardRecord where f_ConsumerName<>'' " +sqlstirng1;
								
			
			 String sqlstring="select top 1 f_ReaderName,f_ConsumerName,f_ReadDate from v_d_CardRecord where f_ConsumerName='";
			
			 String sqlstring3="' "+sqlstirng1+"and f_ReaderName = '";
			 
			 String sqlstring2="' order by f_ReadDate desc";
		
			
			if(null!=conn){
			
					 doornamesql=doornamesql.replace("S_DOORID",fstrings[1]);
					if(fstrings[4].equals("true"))
					   System.out.println(doornamesql);
					
					 pstmt = conn.prepareStatement(doornamesql);
					 rs = pstmt.executeQuery();
					 while(rs.next()){
						 doornameset.add(new StringBuffer(rs.getString("f_DoorID")).append("#").append(rs.getString("f_DoorName")).toString());
					 }
//					------------------------------------------------------------------------------------------------------------------
					 pstmt = conn.prepareStatement(userssql);
					 rs = pstmt.executeQuery();
					 while(rs.next()){
						 usersnamelist.add(new StringBuffer(rs.getString("f_ConsumerName")).toString());
					 }
				//------------------------------------------------------------------------------------------------------------------
					 //如下查询为： 每个门每个人进出门都要查询
					 for (String string : doornameset) {
						 for (String user : usersnamelist) {
							 for (String string2 : inoutdoor) {
							
								 String [] flagstr=string.split("#");
								 String  flagsqlstring=new StringBuffer(sqlstring).append(user).append(sqlstring3).append(flagstr[1]).append(string2).append(sqlstring2).toString();
								 
								 if(fstrings[4].equals("true"))
									 System.out.println(flagsqlstring);
								 
								 pstmt = conn.prepareStatement(flagsqlstring);
								 rs = pstmt.executeQuery();
								 while(rs.next()){
									 String f_readername=rs.getString("f_ReaderName");
									 allincomeset.add(new StringBuffer(flagstr[0]).append("#").append(f_readername).append("#").append(rs.getString("f_ConsumerName")).append("#").append(rs.getString("f_ReadDate")).toString());
								 }
							 }
						 }
					 }
					 
					 Map<String,String>userlist=new LinkedHashMap<String,String>();
					 
					 for (String string : allincomeset) {//记录每个人进出门的最后记录
						 String []tempstr=string.split("#");
						 if(null==userlist.get(tempstr[2])){
						    userlist.put(tempstr[2],tempstr[1]+"#"+tempstr[3]+"#"+tempstr[0]);
					     }else {
							String[] testring=userlist.get(tempstr[2]).split("#");
							if(tempstr[3].compareTo(testring[1])>0){//时间比较大小
								 userlist.put(tempstr[2],tempstr[1]+"#"+tempstr[3]+"#"+tempstr[0]);
							}
						 }
//						 System.out.println(tempstr[2]+"----------------->"+tempstr[1]+"#"+tempstr[3]+"#"+tempstr[0]);
					 }
					 
//					 String []outstr=fstrings[5].split("#");//过滤有2个门以上的
//					 Map<String,String>moredooropmap=new LinkedHashMap<String,String>();
//					 Map<String,String>opmap=new LinkedHashMap<String,String>();
//				     for (Map.Entry<String,String> map: userlist.entrySet()) {
////					     System.out.println(map.getKey()+"----------------------------->"+map.getValue());
//						     String [] vluesstring=map.getValue().split("#");
//							 for (String string2 : outstr) {
//								 String []idstring=string2.split(",");
////							 System.out.println(idstring[0]+"___"+idstring[1]+"---------%%%%%%%%%%%%------->"+vluesstring[2]);
//								 if((idstring[0].equals(vluesstring[2])||idstring[1].equals(vluesstring[2]))){
//										moredooropmap.put(map.getKey(),map.getValue());
//										if(moredooropmap.get(map.getKey()).split("#")[1].compareTo(vluesstring[1])<0){
//										   moredooropmap.put(map.getKey(),map.getValue());
//									    }
//								 } else {
//										opmap.put(map.getKey(),map.getValue());
//										if(opmap.get(map.getKey()).split("#")[1].compareTo(vluesstring[1])<0){
//											opmap.put(map.getKey(),map.getValue());
//										}
//								 }
//					        }
//					 }
////			   System.out.println(moredooropmap.size()+"___"+opmap.size());
//					 for (Map.Entry<String,String> map: moredooropmap.entrySet()) {
////						 System.out.println(map.getKey()+"___"+map.getValue());
//							if(map.getValue().indexOf("进门")!=-1)
//								inlist.add(new StringBuffer(map.getKey()).append("#").append(map.getValue()).toString());
//					 }
////					 System.out.println("-----------------------------------");
//					 for (Map.Entry<String,String>map: opmap.entrySet()) {
////						 System.out.println(map.getKey()+"___"+map.getValue());
//							if(map.getValue().indexOf("进门")!=-1)
//								inlist.add(new StringBuffer(map.getKey()).append("#").append(map.getValue()).toString());
//					 }

					 
					 for (Map.Entry<String,String> map: userlist.entrySet()) {
						 if(map.getValue().indexOf("进门")!=-1){
							inlist.add(new StringBuffer(map.getKey()).append("#").append(map.getValue()).toString());
						 }
					 }
					 
//					 System.out.println(inlist.size());
					 Collections.sort(inlist,new  ComparatorString());
//					 for (String string : inlist) {
//						System.out.println("***************进门**********>"+string);
//					 }
					 
			}else {
//				System.out.println("--连接数据库异常--");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(null!=rs)
				   rs.close();
				if(null!=pstmt)
					pstmt.close();
				if(null!=conn)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	return inlist;
}
	
	public static void ManagerICCardInfo(){
		if(new File(System.getProperty("MAC_HOME")+"conf/iCCard.properties").exists()){
			Timer timer=new Timer();
			timer.schedule(new TimerTask(){
				boolean flag=true;
				@Override
				public void run() {
					if(flag){
						flag=false;
						new Thread(new Runnable(){
							public void run() {
								try {
									int hour=getHour();
									if(hour>5&&hour<=18){
										writeLineFile(getICCardUserInfo(),HOMEPATH+"conf/ICCardInfo.txt");
										deleteOneYearAgoCardRecord();
									}
									 flag=true;
								} catch (Exception e) {
									flag=true;
								}
							}
						}).start();
				    }
				}
			},3000,10000);
	   }
	}

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		ManagerICCardInfo();
//		readLineFile("test.txt");
//		getICCardUserInfo();
//		deleteOneYearAgoCardRecord();		
	}
}
class ComparatorString implements Comparator<String>{

	 public int compare(String arg0, String arg1) {
		  String user0=arg0.split("#")[2];
		  String user1=arg1.split("#")[2];
		  int flag=user0.compareTo(user1);
		  if(flag>=0){
		     return flag;
		  }else{
		     return -1;
		  }  
	 }
}
