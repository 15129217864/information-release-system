package com.xct.cms.xy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import com.xct.cms.dao.ProgramHistoryASC;
import com.xct.cms.dao.TerminalSortASC;
import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.ProjectBean;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.HttpRequest;
import com.xct.cms.utils.ReadMacXmlUtils;
import com.xct.cms.utils.UtilDAO;
import com.xct.cms.xy.domain.ClientProjectBean;


public class ManagerProjectDao extends DBConnection{
	
	Logger logger = Logger.getLogger(ManagerProjectDao.class);

	String []setdatetime=null;  //
	String []enddatetime=null;  //
	
	String []startdatetemp=null;
	String []enddatetemp=null;
	Calendar start = Calendar.getInstance();
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	  String everday="";
	  PreparedStatement pstmt = null;
//	  Statement stmt=null;
	  List<ClientProjectBean> clientprojectlist=new ArrayList<ClientProjectBean>(); 
	  ClientProjectBean clientprojectbean=null;
	  
	 
	public boolean insertProjectToDB(Connection conn,int id,String projectdirectory,String playname,String program_SetDateTime,String startdate,String enddate,
			                         String program_EndDateTime,String timecount,int playtype, String ip,String mac){// String mac  对应数据库中的 program_delid 字段
		//发送了节目操作，只要一发送，就把 program_issend 改成 1；
		String sql="insert into xct_JMhistory (program_senduser,program_JMurl,program_Name, program_SetDateTime,program_SetDate,program_EndDate,program_EndDateTime,program_ISloop,program_PlaySecond,program_ip,program_delid,program_issend)" +
				   " values("+id+",'"+projectdirectory+"','"+playname+"','"+program_SetDateTime+"','"+startdate+"','"+enddate+"','"+program_EndDateTime+"',"+playtype+",'"+timecount+"','"+ip+"','"+mac+"',1)";
		PreparedStatement pstmt1=null;
		try { 
//			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);   
//			stmt.execute(sql);
			pstmt1=conn.prepareStatement(sql);
			if(pstmt1.executeUpdate()>0)
			   return true;
		}catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(pstmt1);
		}
		return false;
	}
	
	public void insertProjectToDB1(Connection conn,/*Statement stmt,*/ int id,String projectdirectory,String playname,String program_SetDateTime,String startdate,String enddate,
            String program_EndDateTime,String timecount,int playtype, String ip,String mac,int program_issend){// String mac  对应数据库中的 program_delid 字段
		//发送了节目操作，只要一发送，就把 program_issend 改成 1；
		String sql2="select count(*) from xct_JMhistory where program_JMurl='"+projectdirectory+"' and program_SetDate='"+startdate+"' and program_EndDate='"+enddate+"' and program_ISloop="+playtype+" and program_PlaySecond="+timecount+" and program_delid ='"+mac+"'";
		if(playtype==3){
			sql2="select count(*) from xct_JMhistory where program_JMurl='"+projectdirectory+"'  and program_ISloop="+playtype+"  and program_delid ='"+mac+"'";	
		}
		String sql3="update xct_JMhistory set program_SetDateTime='"+program_SetDateTime+"',program_SetDate='"+startdate+"'," +
				"program_EndDate='"+enddate+"',program_EndDateTime='"+program_EndDateTime+"' where program_JMurl='"+projectdirectory+"'  " +
						"and program_ISloop="+playtype+"  and program_delid ='"+mac+"'";	
		PreparedStatement pstmt1=null;
		ResultSet rs=null;
		try {
			boolean bool=true;
			//System.out.println(sql2);
			pstmt1=conn.prepareStatement(sql2);
			rs=pstmt1.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0){
					bool=false;
//					pstmt1.executeUpdate(sql3);
					pstmt1=conn.prepareStatement(sql3);
					pstmt1.executeUpdate();
				}
			}
			//=========================================================
			//如下注释，待测试稳定后取消注释
			//旺传媒需求--> 如创建新节目单，添加新节目，需要把原来的节目单全部删除。是否可以在这个部分做相应的程序优化，具体为创建新节目单发送时，
//			发现新节目单中有与终端时间档重复部分，直接替换终端原有时间档内容，而不需要先删除，再投播？
//			//(convert(nvarchar(50),live_SetDate,20) = '2016-01-28 09:00:00') and (convert(nvarchar(50),program_EndDate,20) = '2016-01-28 09:30:00')
			
//			String isexitssql="select distinct program_JMurl from xct_JMhistory where (convert(nvarchar(50),program_SetDate,20) = '"+startdate+"') and (convert(nvarchar(50),program_EndDate,20) = '"+enddate+"')";
//			ResultSet rSet= pstmt1.executeQuery(isexitssql);
//			while(rSet.next()){
//				String jmurl=rSet.getString("program_JMurl");
//				if(null!=jmurl&&!jmurl.equals("")){
//				   bool=false;
//				   String updatesql="update xct_JMhistory set program_JMurl='"+projectdirectory+"',program_Name='"+playname+"' where  (convert(nvarchar(50),program_SetDate,20) = '"+startdate+"') and (convert(nvarchar(50),program_EndDate,20) = '"+enddate+"')";
//				   pstmt1.executeUpdate(updatesql);
//				}
//			}
			//======================================================
			if(bool){
				String sql="insert into xct_JMhistory (program_senduser,program_JMurl,program_Name, program_SetDateTime,program_SetDate,program_EndDate,program_EndDateTime,program_ISloop,program_PlaySecond,program_ip,program_delid,program_issend)" +
				" values("+id+",'"+projectdirectory+"','"+playname+"','"+program_SetDateTime+"','"+startdate+"','"+enddate+"','"+program_EndDateTime+"',"+playtype+",'"+timecount+"','"+ip.split("_")[0]+"','"+mac+"',"+program_issend+")";
//				stmt.execute(sql);
				pstmt1=conn.prepareStatement(sql);
				pstmt1.executeUpdate();
			}
		}catch (SQLException e) {
			logger.info("insertProjectToDB1------------------>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt1);
		}
	}	
	
	
	public void insertProjectParm(Connection conn,/*Statement stmt,*/int id,String projectdirectory,String playname,String program_SetDateTime,String startdate,String enddate,
            String program_EndDateTime,String timecount,int playtype, String ip,String mac,int program_issend){
		
		if(!program_SetDateTime.equals(program_EndDateTime)){
			
        	setdatetime=program_SetDateTime.split("-");
        	enddatetime=program_EndDateTime.split("-");
        	startdatetemp=startdate.split(" ");
        	enddatetemp=enddate.split(" ");
			
			 //请注意月份是从0-11
	        start = Calendar.getInstance();
//	        start.set(2009, 1, 28);
	        start.set(Integer.parseInt(setdatetime[0]), Integer.parseInt(setdatetime[1])-1, Integer.parseInt(setdatetime[2]));
	        
	        Calendar end = Calendar.getInstance();
//	        end.set(2009, 2, 25);
	        end.set(Integer.parseInt(enddatetime[0]), Integer.parseInt(enddatetime[1])-1, Integer.parseInt(enddatetime[2]));
	        while(start.compareTo(end) <= 0){
	        	everday=format.format(start.getTime());
	            //打印每天    
	            insertProjectToDB1(conn,/*stmt,*/id,projectdirectory, playname,everday, everday+" "+startdatetemp[1], everday+" "+enddatetemp[1],everday, timecount, playtype, ip,mac,program_issend);
	            start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
	            //循环，每次天数加1
	        }
        }else{
            insertProjectToDB1(conn,/*stmt,*/id,projectdirectory, playname,program_SetDateTime, startdate, enddate,program_EndDateTime, timecount, playtype, ip,mac,program_issend);
        }
	}

	public List<ClientProjectBean> createMacXmlOnWeeHours(/*String today,*/Boolean bool){ //30天后的节目（包括今天的）加入 和 永久循环节目的加入
		
		List<ClientProjectBean> clientprojectlist=new ArrayList<ClientProjectBean>(); 
		String sql="";
		if(bool){
			//Mysql
			sql = new StringBuffer().append("select a.program_name, a.program_jmurl,a.program_setdate,a.program_enddate,a.program_isloop,a.program_playsecond, a.program_ip, b.cnt_mac,a.program_senduser ")
					.append( "from xct_jmhistory a, xct_ipaddress b ")
					.append( "where a.program_setdate between  now() and NOW()+INTERVAL 30 DAY ")
					.append( " and program_issend in(1,2) and a.program_delid=b.cnt_mac").toString();
		   //SQLServer
//		   sql= new StringBuffer().append("select a.program_name, a.program_jmurl,a.program_setdate,a.program_enddate,a.program_isloop,a.program_playsecond, a.program_ip, b.cnt_mac,a.program_senduser ")
				//   .append("from xct_jmhistory a, xct_ipaddress b ")
				//   .append("where a.program_setdate between  CONVERT(varchar(10),getdate(), 120) and dateadd(day,30,getdate()) ")
				//   .append(" and program_issend in(1,2) and a.program_delid=b.cnt_mac").toString();
		}else{
			sql= new StringBuffer().append("select a.program_name, a.program_jmurl,a.program_setdate,a.program_enddate,a.program_isloop,a.program_playsecond, a.program_ip, b.cnt_mac,a.program_senduser ")
					.append("from xct_jmhistory a, xct_ipaddress b ")
					.append("where  a.program_ISloop=3 and program_issend=2 and a.program_delid=b.cnt_mac").toString();
		}
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
//			System.out.println(sql);
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			ClientProjectBean clientprojectbean=null;
			while(rs.next()){
				int program_isloop=rs.getInt("program_isloop");
				if(!bool && program_isloop==3){//永久循环
					String datetmp;
					for(int i=0;i<31;i++){
						datetmp=new SimpleDateFormat("yyyy-MM-dd").format(getAfterDate(i));
						clientprojectbean=new ClientProjectBean();
						clientprojectbean.setPlayclient(rs.getString("cnt_mac"));//此处原本是节目的名称，现设置为 mac 
						clientprojectbean.setName( rs.getString("program_Name"));
						clientprojectbean.setJmurl( rs.getString("program_jmurl"));
						clientprojectbean.setSetdate(datetmp+" "+rs.getString("program_SetDate").replace(".0", "").split(" ")[1]);
						clientprojectbean.setEnddate(datetmp+" "+rs.getString("program_EndDate").replace(".0", "").split(" ")[1]);
						clientprojectbean.setIsloop(String.valueOf(program_isloop));
						
						String strtemp="";
						if(program_isloop==3){
							strtemp="loop";
							clientprojectbean.setForoverloop("y");
						}
						clientprojectbean.setPlaytype(strtemp);
						clientprojectbean.setPlaysecond(rs.getInt("program_PlaySecond")+"");
						clientprojectbean.setClientip(rs.getString("program_ip"));
						clientprojectbean.setJmappid(rs.getInt("program_senduser"));
						clientprojectlist.add(clientprojectbean);
					}
				}else{
					clientprojectbean=new ClientProjectBean();
					clientprojectbean.setPlayclient(rs.getString("cnt_mac"));//此处原本是节目的名称，现设置为 mac 
					clientprojectbean.setName( rs.getString("program_Name"));
					clientprojectbean.setJmurl( rs.getString("program_jmurl"));
					clientprojectbean.setSetdate(rs.getString("program_SetDate").replace(".0", ""));
					clientprojectbean.setEnddate(rs.getString("program_EndDate").replace(".0", ""));
					clientprojectbean.setIsloop(String.valueOf(program_isloop));
					
					String strtemp="";
					if(program_isloop==0)
						strtemp="loop";
					if(program_isloop==1)
						strtemp="insert";
					if(program_isloop==2)
						strtemp="active";
					clientprojectbean.setForoverloop("n");
					clientprojectbean.setPlaytype(strtemp);
					clientprojectbean.setPlaysecond(rs.getInt("program_PlaySecond")+"");
					clientprojectbean.setClientip(rs.getString("program_ip"));
					clientprojectbean.setJmappid(rs.getInt("program_senduser"));
					clientprojectlist.add(clientprojectbean);
				}
			}
			return clientprojectlist;
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return null;
	}
	
	public List<ClientProjectBean> createMacXmlOnWeeHours(Connection conn,String mac,int daynum){ //30天后的节目（包括今天的）加入 和 永久循环节目的加入
		
		//mysql
		String   sql="select a.program_name, a.program_jmurl,a.program_setdate,a.program_enddate,a.program_isloop,a.program_playsecond, a.program_ip, b.cnt_mac,a.program_senduser " +
				"from xct_jmhistory a, xct_ipaddress b " +
				"where a.program_SetDateTime between  DATE_FORMAT(NOW(),'%y-%m-%d') and DATE_FORMAT((NOW()+INTERVAL "+daynum+" DAY),'%y-%m-%d') " +
						" and program_issend=2 and a.program_delid=b.cnt_mac and a.program_delid='"+mac+"'";
		
		//SQLServer
//		String   sql="select a.program_name, a.program_jmurl,a.program_setdate,a.program_enddate,a.program_isloop,a.program_playsecond, a.program_ip, b.cnt_mac,a.program_senduser " +
//				"from xct_jmhistory a, xct_ipaddress b " +
//				"where a.program_SetDateTime between  CONVERT(varchar(10),getdate(), 120) and dateadd(day,"+daynum+",getdate()) " +
//				" and program_issend=2 and a.program_delid=b.cnt_mac and a.program_delid='"+mac+"'";
		
		
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
					clientprojectbean=new ClientProjectBean();
					clientprojectbean.setPlayclient(rs.getString("cnt_mac"));//此处原本是节目的名称，现设置为 mac 
					clientprojectbean.setName( rs.getString("program_Name"));
					clientprojectbean.setJmurl( rs.getString("program_jmurl"));
					clientprojectbean.setSetdate(rs.getString("program_SetDate").replace(".0", ""));
					clientprojectbean.setEnddate(rs.getString("program_EndDate").replace(".0", ""));
					clientprojectbean.setIsloop(rs.getInt("program_ISloop")+"");
					int program_isloop=rs.getInt("program_isloop");
					String strtemp="";
					if(program_isloop==0)
						strtemp="loop";
					if(program_isloop==1)
						strtemp="insert";
					if(program_isloop==2)
						strtemp="active";
					clientprojectbean.setForoverloop("n");
					clientprojectbean.setPlaytype(strtemp);
					clientprojectbean.setPlaysecond(rs.getInt("program_PlaySecond")+"");
					clientprojectbean.setClientip(rs.getString("program_ip"));
					clientprojectbean.setJmappid(rs.getInt("program_senduser"));
					clientprojectlist.add(clientprojectbean);
			}

		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt);
		}
		return clientprojectlist;
	}
	
	
   public List<ClientProjectBean> createMacXmlOnWeeHoursForeverProject(){ 
		
		List<ClientProjectBean> clientprojectlist=new ArrayList<ClientProjectBean>(); 
		
		String sql="select a.program_name, a.program_jmurl,a.program_setdate,a.program_enddate,a.program_isloop,a.program_playsecond, a.program_ip, b.cnt_mac " +
				"from xct_jmhistory a, xct_ipaddress b " +
				"where  a.program_ISloop=3 and a.program_delid=b.cnt_mac";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			ClientProjectBean clientprojectbean=null;
			while(rs.next()){
				clientprojectbean=new ClientProjectBean();
				clientprojectbean.setPlayclient(rs.getString("cnt_mac"));//此处原本是节目的名称，现设置为 mac 
				clientprojectbean.setName( rs.getString("program_Name"));
				clientprojectbean.setJmurl( rs.getString("program_jmurl"));
				clientprojectbean.setSetdate(rs.getString("program_SetDate"));
				clientprojectbean.setEnddate(rs.getString("program_EndDate"));
				clientprojectbean.setIsloop(rs.getInt("program_ISloop")+"");
				int program_isloop=rs.getInt("program_isloop");
				String strtemp="";
				if(program_isloop==0||program_isloop==3)
					strtemp="loop";
				if(program_isloop==1)
					strtemp="insert";
				if(program_isloop==2)
					strtemp="active";
				
				clientprojectbean.setPlaytype(strtemp);
				clientprojectbean.setPlaysecond(rs.getInt("program_PlaySecond")+"");
				clientprojectbean.setClientip(rs.getString("program_ip"));
				clientprojectlist.add(clientprojectbean);
			}
			return clientprojectlist;
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return null;
	}
   
	public boolean updateProjectIsSend(Connection conn,int updateissend,int oldissend,String cnt_programurl,String mac){
		String sql="update xct_JMhistory set program_issend="+updateissend+" where program_delid='"+mac+"' and program_issend in(3,"+oldissend+") and program_jmurl='"+cnt_programurl+"'" ;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			///////System.out.println("updateProjectIsSend-------> "+sql);
			pstmt=conn.prepareStatement(sql);
			if(pstmt.executeUpdate()>0)
			  return true;
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt);
		}
		return false;
	}
	
    public boolean updateProjectIsSendByMac(Connection conn,int updateissend,int oldissend,String cnt_programurl,String mac){
		
		String sql="update xct_JMhistory set program_issend="+updateissend+" where program_delid='"+mac+"' and program_issend in(3,"+oldissend+") and program_jmurl='"+cnt_programurl+"'" ;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(sql);
			if(pstmt.executeUpdate()>0)
			  return true;
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt);
		}
		return false;
	}
	
	public List<ClientProjectBean>getForEverLoopProject(){
		List<ClientProjectBean> clientprojectlist=new ArrayList<ClientProjectBean>();
		String sql = "select distinct b.program_JMid,  a.cnt_name,b.program_jmurl,b.program_name,b.program_setdate,b.program_enddate,b.program_isloop,b.program_playsecond,b.program_ip ,b.program_delid" +
				" from xct_JMhistory b,xct_ipaddress a where b.program_delid=a.cnt_mac and b.program_isloop=3";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		 try {
			pstmt=con.prepareStatement(sql);
//			System.out.println(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				String strtemp="";
				int program_isloop=rs.getInt("program_isloop");
	/*			if(program_isloop==0)
					strtemp="循环";
				if(program_isloop==1)
					strtemp="插播";
				if(program_isloop==2)
					strtemp="定时";*/
				if(program_isloop==3)
				    strtemp="永久循环";	
				clientprojectlist.add(new ClientProjectBean(rs.getInt("program_JMid")+"",rs.getString("cnt_name"),rs.getString("program_jmurl"),rs.getString("program_name"),(null==rs.getString("program_setdate")?rs.getString("program_setdate"):rs.getString("program_setdate").replace(".0", "")),
						(null==rs.getString("program_enddate")?rs.getString("program_enddate"):rs.getString("program_enddate").replace(".0", "")),strtemp,rs.getInt("program_playsecond")+" 分钟",rs.getString("program_ip"),strtemp,rs.getString("program_delid")));
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return clientprojectlist;
	}
	
	
	public ClientProjectBean getClientPaojecByJmid(int jmid){
		
		String sql="select program_jmurl,program_name,program_setdate,program_isloop,program_playsecond,program_ip,program_delid from xct_jmhistory where program_jmid='"+jmid+"'";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			ClientProjectBean clientprojectbean=null;
			if(rs.next()){
				clientprojectbean=new ClientProjectBean();
				clientprojectbean.setJmurl(rs.getString("program_jmurl"));
				clientprojectbean.setName(rs.getString("program_name"));
				clientprojectbean.setSetdate(rs.getString("program_setdate").replace(".0", ""));
				clientprojectbean.setIsloop(rs.getString("program_isloop"));
				clientprojectbean.setForoverloop(rs.getString("program_isloop").equals("3")?"y":"n");
				clientprojectbean.setPlaysecond(rs.getString("program_playsecond"));
				clientprojectbean.setClientip(rs.getString("program_ip"));
				clientprojectbean.setProgram_delid(rs.getString("program_delid"));//mac
				return clientprojectbean;
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return null;
	}
	
	
	public boolean deleteClientProject(int jmid){
		
		ClientProjectBean clientprojectbean=getClientPaojecByJmid(jmid);
//		String datetime=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String strtemp;
		if(null!=clientprojectbean/*&&clientprojectbean.getSetdate().split(" ")[0].equals(datetime)*/){
			
			if(clientprojectbean.getForoverloop().equals("y"))//永久循环
			     strtemp="lv0025@"+clientprojectbean.getName()+"#"+clientprojectbean.getJmurl()+"#"+
			               clientprojectbean.getSetdate()+"#"+clientprojectbean.getPlaytype()+"#"+clientprojectbean.getPlaysecond();
			else
				 strtemp="lv0025@"+clientprojectbean.getName()+"#"+clientprojectbean.getJmurl()+"#"+
	               clientprojectbean.getSetdate()+"#"+clientprojectbean.getPlaytype()+"#"+clientprojectbean.getPlaysecond();
			
			//删除ftp上的mac地址文件对应的节目
			String cntmac=FirstStartServlet.projectpath+"serverftp\\program_file\\"+clientprojectbean.getProgram_delid()+".xml";
			
			String ipaddress=clientprojectbean.getClientip();
			FirstStartServlet.client.allsend(clientprojectbean.getProgram_delid(),ipaddress, strtemp);
			
			new ReadMacXmlUtils().getProjectFromMacXml(cntmac,new ProjectBean(clientprojectbean.getName(),clientprojectbean.getJmurl(),clientprojectbean.getSetdate(),clientprojectbean.getPlaytype(),clientprojectbean.getPlaysecond()),clientprojectbean.getForoverloop(),true); 
		}
		
		String sql="delete from xct_jmhistory where program_jmid="+jmid+"";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			if(pstmt.executeUpdate()>0)
				return true;
		 } catch (SQLException e) {
			 logger.info(e.getMessage());
			 e.printStackTrace();
		 }finally{
				returnResources(rs,pstmt,con);
		}
	    return false;
	}
	
	//  查出本年本月的所有节目
	public List<ClientProjectBean> getClientProject(int pages,int pagecount,String builddate){
		
		List<ClientProjectBean> clientprojectlist=new ArrayList<ClientProjectBean>();
		String sqlstr="";
		//Mysql
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" b.program_JMid, ");
		sql.append(" a.cnt_name, ");
		sql.append(" a.cnt_mac, ");
		sql.append(" b.program_jmurl, ");
		sql.append(" b.program_name, ");
		sql.append(" b.program_setdate, ");
		sql.append(" b.program_enddate, ");
		sql.append(" b.program_isloop, ");
		sql.append(" b.program_playsecond, ");
		sql.append(" b.program_ip, ");
		sql.append(" b.program_delid ");
		sql.append(" FROM xct_ipaddress a, ");
		sql.append(" xct_jmhistory b ");
		sql.append(" WHERE b.program_jmid NOT IN ( ");
		sql.append(" SELECT c.program_jmid	FROM ");
		sql.append(" xct_jmhistory c, ");
		sql.append(" xct_ipaddress d ");
		sql.append(" WHERE c.program_setdatetime ='" + builddate + "'");
		sql.append(" AND d.cnt_mac = c.program_delid ");
		sql.append(" ORDER BY d.cnt_ip asc )");
		sql.append(" AND b.program_setdatetime ='" + builddate + "'");
		sql.append(" AND a.cnt_mac = b.program_delid ");
		sql.append(" ORDER BY a.cnt_ip DESC ");
		sql.append(" LIMIT " + pagecount + "," + pages);
		
		sqlstr=sql.toString();
		
		//SQLServer
//		 sqlstr="select top "+pages+"  b.program_JMid,  a.cnt_name,a.cnt_mac,b.program_jmurl,b.program_name,b.program_setdate,b.program_enddate,b.program_isloop,b.program_playsecond,b.program_ip,b.program_delid "+
//					" from xct_ipaddress a , xct_jmhistory b  WHERE b.program_jmid "+
//					" NOT IN  (SELECT TOP "+pages*(pagecount-1)+" c.program_jmid FROM xct_jmhistory c , xct_ipaddress d  where  c.program_setdatetime = '"+builddate+"' and d.cnt_mac=c.program_delid ORDER BY convert(numeric(15),replace(d.cnt_ip,'.',''))) "+
//					 "  and b.program_setdatetime = '"+builddate+"' and a.cnt_mac=b.program_delid  ORDER BY convert(numeric(15),replace(a.cnt_ip,'.',''))";
		
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		 try {
			pstmt=con.prepareStatement(sqlstr);
			rs=pstmt.executeQuery();
			while(rs.next()){
				clientprojectlist.add(new ClientProjectBean(rs.getInt("program_JMid")+"",rs.getString("cnt_name"),rs.getString("program_jmurl"),rs.getString("program_name"),(null==rs.getString("program_setdate")?rs.getString("program_setdate"):rs.getString("program_setdate").replace(".0", "")),
						(null==rs.getString("program_enddate")?rs.getString("program_enddate"):rs.getString("program_enddate").replace(".0", "")),rs.getInt("program_isloop")+"",rs.getInt("program_playsecond")+"",rs.getString("program_ip"),"",rs.getString("program_delid")));//
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return clientprojectlist;
	}
	
	//pages  每页显示的行数 ，  pagecount 要显示的页数
	public List<ClientProjectBean> getClientProect1(int pages,int pagecount,String setstartdate,String setenddate,String programname,String playtypetemp){
		String strtmp="0";
		if(programname.indexOf("循环")!=-1)
			strtmp="0";
		 else if(programname.indexOf("插播")!=-1)
			 strtmp="1";
		 else if(programname.indexOf("定时")!=-1)
			 strtmp="2";
		 else if(programname.indexOf("永久循环")!=-1)
			 strtmp="3";
		 else
			 strtmp="0";
		
		 List<ClientProjectBean> clientprojectlist=new ArrayList<ClientProjectBean>();
		//过滤条件 三个都具备
		 //Mysql
		 String sql = "SELECT	b.program_JMid,	a.cnt_name,	b.program_jmurl,b.program_name,	b.program_setdate, b.program_enddate,b.program_isloop, b.program_playsecond, b.program_ip,b.program_delid FROM xct_ipaddress a,	xct_jmhistory b	WHERE b.program_jmid NOT IN (SELECT	c.program_jmid	FROM xct_jmhistory c,xct_ipaddress d WHERE (d.cnt_name LIKE '%"
					+ programname
					+ "%'	)	AND c.program_setdatetime BETWEEN '"
					+ programname
					+ "'	AND '"
					+ setenddate
					+ "' AND d.cnt_mac = c.program_delid	ORDER BY c.program_setdate,	d.cnt_ip ) AND ( a.cnt_name LIKE '%"
					+ programname
					+ "%'	) AND b.program_setdatetime BETWEEN '"
					+ setstartdate
					+ "' AND '"
					+ setenddate
					+ "'	AND a.cnt_mac = b.program_delid	ORDER BY b.program_setdate,	a.cnt_ip LIMIT"
					+ pagecount + "," + pages;
		 
		  // SQLServer
//		   
//		 String sql="select top "+pages+"  b.program_JMid,  a.cnt_name,b.program_jmurl,b.program_name,b.program_setdate,b.program_enddate,b.program_isloop,b.program_playsecond,b.program_ip,b.program_delid from xct_ipaddress a , xct_jmhistory b " +
//		 			" WHERE b.program_jmid NOT IN  ("+
//                                   "SELECT TOP "+pages*(pagecount-1)+" c.program_jmid FROM xct_jmhistory c, xct_ipaddress d  "+
//				                      "where  (d.cnt_name like '%"+programname+"%') and c.program_setdatetime between '"+setstartdate+"' and '"+setenddate+"' and d.cnt_mac=c.program_delid  ORDER BY c.program_setdate,convert(numeric(15),replace(d.cnt_ip,'.',''))  " +
//				                     ")" +
//				            "  and   (a.cnt_name like '%"+programname+"%')  and b.program_setdatetime between '"+setstartdate+"' and '"+setenddate+"' and a.cnt_mac=b.program_delid  ORDER BY b.program_setdate,convert(numeric(15),replace(a.cnt_ip,'.','')) ";
		 
		 
		 if("1".equals(playtypetemp))
			 //Mysql
			 sql = "SELECT b.program_JMid, a.cnt_name, b.program_jmurl, b.program_name,	b.program_setdate, b.program_enddate, b.program_isloop, b.program_playsecond, b.program_ip,	b.program_delid	FROM xct_ipaddress a, xct_jmhistory b WHERE b.program_jmid NOT IN ( SELECT c.program_jmid FROM xct_jmhistory c,xct_ipaddress d	WHERE (	c.program_Name LIKE '%"
						+ programname
						+ "%' )	AND c.program_setdatetime BETWEEN '"
						+ setstartdate
						+ "'	AND '"
						+ setenddate
						+ "' AND d.cnt_mac = c.program_delid ORDER BY c.program_setdate, d.cnt_ip ) AND ( b.program_Name LIKE '%"
						+ programname
						+ "%'	)	AND b.program_setdatetime BETWEEN '"
						+ setstartdate
						+ "' AND '"
						+ setenddate
						+ "'"
						+ " AND a.cnt_mac = b.program_delid ORDER BY b.program_setdate,	a.cnt_ip limit"
						+ pagecount + "," + pages;
			 
			 
			 //SQLServer
//			 sql="select top "+pages+"  b.program_JMid,  a.cnt_name,b.program_jmurl,b.program_name,b.program_setdate,b.program_enddate,b.program_isloop,b.program_playsecond,b.program_ip,b.program_delid from xct_ipaddress a , xct_jmhistory b " +
//	 			" WHERE b.program_jmid NOT IN  ("+
//                            "SELECT TOP "+pages*(pagecount-1)+" c.program_jmid FROM xct_jmhistory c, xct_ipaddress d  "+
//			                      "where  (c.program_Name like '%"+programname+"%') and c.program_setdatetime between '"+setstartdate+"' and '"+setenddate+"' and d.cnt_mac=c.program_delid  ORDER BY c.program_setdate,convert(numeric(15),replace(d.cnt_ip,'.',''))  " +
//			                     ")" +
//			            "  and   (b.program_Name like '%"+programname+"%')  and b.program_setdatetime between '"+setstartdate+"' and '"+setenddate+"' and a.cnt_mac=b.program_delid  ORDER BY b.program_setdate,convert(numeric(15),replace(a.cnt_ip,'.','')) ";
		 
		 
		 else if("2".equals(playtypetemp))
			 //Mysql
			 sql = "select   b.program_JMid,  a.cnt_name,b.program_jmurl,b.program_name,b.program_setdate,b.program_enddate,b.program_isloop,b.program_playsecond,b.program_ip,b.program_delid from xct_ipaddress a , xct_jmhistory b "
						+ " WHERE b.program_jmid NOT IN  ("
						+ "SELECT c.program_jmid FROM xct_jmhistory c, xct_ipaddress d  "
						+ "where  ( b.program_ISloop ='"
						+ strtmp
						+ "') and c.program_setdatetime between '"
						+ setstartdate
						+ "' and '"
						+ setenddate
						+ "' and d.cnt_mac=c.program_delid  ORDER BY c.program_setdate, d.cnt_ip  "
						+ ")"
						+ "  and   ( b.program_ISloop ='"
						+ strtmp
						+ "')  and b.program_setdatetime between '"
						+ setstartdate
						+ "' and '"
						+ setenddate
						+ "' and a.cnt_mac=b.program_delid  ORDER BY b.program_setdate,a.cnt_ip limit "
						+ pagecount + "," + pages;
			 
			 //SQLServer
//			 sql="select top "+pages+"  b.program_JMid,  a.cnt_name,b.program_jmurl,b.program_name,b.program_setdate,b.program_enddate,b.program_isloop,b.program_playsecond,b.program_ip,b.program_delid from xct_ipaddress a , xct_jmhistory b " +
//	 			" WHERE b.program_jmid NOT IN  ("+
//                            "SELECT TOP "+pages*(pagecount-1)+" c.program_jmid FROM xct_jmhistory c, xct_ipaddress d  "+
//			                      "where  ( b.program_ISloop ='"+strtmp+"') and c.program_setdatetime between '"+setstartdate+"' and '"+setenddate+"' and d.cnt_mac=c.program_delid  ORDER BY c.program_setdate,convert(numeric(15),replace(d.cnt_ip,'.',''))  " +
//			                     ")" +
//			            "  and   ( b.program_ISloop ='"+strtmp+"')  and b.program_setdatetime between '"+setstartdate+"' and '"+setenddate+"' and a.cnt_mac=b.program_delid  ORDER BY b.program_setdate,convert(numeric(15),replace(a.cnt_ip,'.','')) ";
		 
		 Connection con = super.getConection();
			PreparedStatement pstmt = null;
			ResultSet rs=null;
		 try {
//			System.out.println(sql);
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				String strtemp="";
				int program_isloop=rs.getInt("program_isloop");
				if(program_isloop==0)
					strtemp="循环";
				if(program_isloop==1)
					strtemp="点播";
				if(program_isloop==2)
					strtemp="定时";//
				if(program_isloop==3)
					strtemp="永久循环";
				clientprojectlist.add(new ClientProjectBean(rs.getInt("program_JMid")+"",rs.getString("cnt_name"),rs.getString("program_jmurl"),rs.getString("program_name"),(null==rs.getString("program_setdate")?rs.getString("program_setdate"):rs.getString("program_setdate").replace(".0", "")),
						(null==rs.getString("program_enddate")?rs.getString("program_enddate"):rs.getString("program_enddate").replace(".0", "")),strtemp,rs.getInt("program_playsecond")+"",rs.getString("program_ip"),strtemp,rs.getString("program_delid")));//
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return clientprojectlist;
	}
	
//	pages  每页显示的行数 ，  pagecount 要显示的页数
	public List<ClientProjectBean> getClientProect_H(int pages,int pagecount,String str){
		 List<ClientProjectBean> clientprojectlist=new ArrayList<ClientProjectBean>();
		 //过滤条件 三个都具备  
		 //Mysql
		 String sql = "select b.program_JMid,  a.cnt_name,b.program_jmurl,b.program_name,b.program_setdate,b.program_enddate,b.program_isloop,b.program_playsecond,b.program_ip,b.program_delid from xct_ipaddress a ,xct_jmhistory b "
					+ " WHERE b.program_jmid NOT IN  ("
					+ "SELECT  c.program_jmid FROM xct_jmhistory c, xct_ipaddress d where a.cnt_mac=b.program_delid  "
					+ str
					+ "  ORDER BY b.program_setdate,a.cnt_ip ) and a.cnt_mac=b.program_delid "
					+ str
					+ "   ORDER BY b.program_setdate,a.cnt_ip limit "
							+ pagecount + "," + pages;
		 
		 //SQLServer
//		  String sql="select top "+pages+"  b.program_JMid,  a.cnt_name,b.program_jmurl,b.program_name,b.program_setdate,b.program_enddate,b.program_isloop,b.program_playsecond,b.program_ip,b.program_delid from xct_ipaddress a , xct_jmhistory b " +
		 	//		" WHERE b.program_jmid NOT IN  ("+
            //                       "SELECT TOP "+pages*(pagecount-1)+" c.program_jmid FROM program_jmhistory c, program_ipaddress d   where    a.cnt_mac=b.program_delid  "+str+"  ORDER BY b.program_setdate,convert(numeric(15),replace(a.cnt_ip,'.','')) ) and a.cnt_mac=b.program_delid "+str +"   ORDER BY b.program_setdate,convert(numeric(15),replace(a.cnt_ip,'.',''))";
	
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		 try {
//			System.out.println(sql);
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				String strtemp="";
				int program_isloop=rs.getInt("program_isloop");
				if(program_isloop==0)
					strtemp="循环";
				if(program_isloop==1)
					strtemp="点播";
				if(program_isloop==2)
					strtemp="定时";//
				if(program_isloop==3)
					strtemp="永久循环";
				clientprojectlist.add(new ClientProjectBean(rs.getInt("program_JMid")+"",rs.getString("cnt_name"),rs.getString("program_jmurl"),rs.getString("program_name"),(null==rs.getString("program_setdate")?rs.getString("program_setdate"):rs.getString("program_setdate").replace(".0", "")),
						(null==rs.getString("program_enddate")?rs.getString("program_enddate"):rs.getString("program_enddate").replace(".0", "")),strtemp,rs.getInt("program_playsecond")+"",rs.getString("program_ip"),strtemp,rs.getString("program_delid")));//
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return clientprojectlist;
	}
//	pages  每页显示的行数 ，  pagecount 要显示的页数
	public List<ClientProjectBean> getClientProect_H2(int pages,int pagecount,String str){
		 List<ClientProjectBean> clientprojectlist=new ArrayList<ClientProjectBean>();
		 //过滤条件 三个都具备  
		 
		 //Mysql
		 pagecount=(pagecount==1)?0:pagecount;
		 String sql = "SELECT  b.program_JMid,  a.cnt_name,b.program_jmurl,b.program_name,b.program_setdate,b.program_enddate,b.program_isloop,b.program_playsecond,b.program_ip,b.program_delid from xct_ipaddress a , xct_jmhistory b "
					+ " WHERE  a.cnt_mac=b.program_delid "
					+ str + " ORDER BY program_JMid limit "+pagecount+","+pages;
		 
//		 String sql = "SELECT  b.program_JMid,  a.cnt_name,b.program_jmurl,b.program_name,b.program_setdate,b.program_enddate,b.program_isloop,b.program_playsecond,b.program_ip,b.program_delid from xct_ipaddress a , xct_jmhistory b "
//					+ " WHERE b.program_JMid >(SELECT IFNULL(MAX(program_JMid),0) FROM "
//					+ "(SELECT  program_JMid FROM xct_jmhistory c, xct_ipaddress d where  d.cnt_mac=c.program_delid "
//					+ str
//					+ "   ORDER BY program_JMid) A) and a.cnt_mac=b.program_delid "
//					+ str + " ORDER BY program_JMid limit "+pagecount+","+pages;
		 
		 //SQLServer
//		  String sql="SELECT TOP  "+pages+" b.program_JMid,  a.cnt_name,b.program_jmurl,b.program_name,b.program_setdate,b.program_enddate,b.program_isloop,b.program_playsecond,b.program_ip,b.program_delid from xct_ipaddress a , xct_jmhistory b " +
		 //		" WHERE b.program_JMid >(SELECT ISNULL(MAX(program_JMid),0) FROM "+
		 //		"(SELECT TOP "+pages*(pagecount-1)+" program_JMid FROM xct_jmhistory c, xct_ipaddress d  where  d.cnt_mac=c.program_delid "+str+" ORDER BY program_JMid) A) and a.cnt_mac=b.program_delid   "+str+" ORDER BY program_JMid";

		//System.out.println(sql);
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		 try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				clientprojectlist.add(new ClientProjectBean(rs.getInt("program_JMid")+"",rs.getString("cnt_name"),rs.getString("program_jmurl"),rs.getString("program_name"),(null==rs.getString("program_setdate")?rs.getString("program_setdate"):rs.getString("program_setdate").replace(".0", "")),
						(null==rs.getString("program_enddate")?rs.getString("program_enddate"):rs.getString("program_enddate").replace(".0", "")),rs.getInt("program_isloop")+"",rs.getInt("program_playsecond")+"",rs.getString("program_ip"),"",rs.getString("program_delid")));//
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		if(clientprojectlist!=null) 
			Collections.sort(clientprojectlist,new ProgramHistoryASC()); 
		return clientprojectlist;
	}	
	////查询全部
	public List<ClientProjectBean> getALLClientProect(String str){
		 List<ClientProjectBean> clientprojectlist=new ArrayList<ClientProjectBean>(); 
		 String sql="select   b.program_JMid,  a.cnt_name,b.program_jmurl,b.program_name,b.program_setdate,b.program_enddate,b.program_isloop,b.program_playsecond,b.program_ip,b.program_delid from xct_ipaddress a , xct_jmhistory b " +
		 			" WHERE   a.cnt_mac=b.program_delid "+str +"   ORDER BY b.program_setdate,convert(numeric(15),replace(a.cnt_ip,'.',''))";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		 try {
//			System.out.println(sql);
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				clientprojectlist.add(new ClientProjectBean(rs.getInt("program_JMid")+"",rs.getString("cnt_name"),rs.getString("program_jmurl"),rs.getString("program_name"),(null==rs.getString("program_setdate")?rs.getString("program_setdate"):rs.getString("program_setdate").replace(".0", "")),
						(null==rs.getString("program_enddate")?rs.getString("program_enddate"):rs.getString("program_enddate").replace(".0", "")),rs.getInt("program_isloop")+"",rs.getInt("program_playsecond")+"",rs.getString("program_ip"),"",rs.getString("program_delid")));//
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return clientprojectlist;
	}
	public List<ClientProjectBean> getALLClientProect2(String str){
		 List<ClientProjectBean> clientprojectlist=new ArrayList<ClientProjectBean>(); 
		 String sql="select   b.program_JMid,  a.cnt_name,b.program_jmurl,b.program_name,b.program_setdate,b.program_enddate,b.program_isloop,b.program_playsecond,b.program_ip,b.program_delid from xct_ipaddress a , xct_jmhistory b " +
		 			" WHERE   a.cnt_mac=b.program_delid "+str +"   ORDER BY b.program_setdate,convert(numeric(15),replace(a.cnt_ip,'.',''))";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		 try {
//			System.out.println(sql);
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				clientprojectlist.add(new ClientProjectBean(rs.getInt("program_JMid")+"",rs.getString("cnt_name"),rs.getString("program_jmurl"),rs.getString("program_name"),(null==rs.getString("program_setdate")?rs.getString("program_setdate"):rs.getString("program_setdate").replace(".0", "")),
						(null==rs.getString("program_enddate")?rs.getString("program_enddate"):rs.getString("program_enddate").replace(".0", "")),rs.getInt("program_isloop")+"",rs.getInt("program_playsecond")+"",rs.getString("program_ip"),"",rs.getString("program_delid")));//
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return clientprojectlist;
	}
	
	public int getClientProect_H(String str){
		 int i=0;
		 String sql="select  count(*) from xct_ipaddress a , xct_jmhistory b where  a.cnt_mac=b.program_delid " +str;
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		//System.out.println(sql);
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())
			  i=rs.getInt(1);
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return i;
	}
	
	List<ClientProjectBean> list = new ArrayList<ClientProjectBean>();
	
	public int deletALLeclient(String str){
		
		String nowYear=UtilDAO.getNowtime("yyyy-MM-dd");
		//Mysql
		list = getALLClientProect2(" and program_SetDateTime = '"+ nowYear + " 00:00:00' " + str);
		//SQLServer
//		list=getALLClientProect2(" and convert(nvarchar(50),program_SetDateTime,20) = '"+nowYear+" 00:00:00' "+str );
		new Thread(new Runnable() {
			public void run() {
				ClientProjectBean clientprojectbean=null;
				for(int i=0;i<list.size();i++){
					clientprojectbean=list.get(i);
					String strtemp;
					if(null!=clientprojectbean){
							 strtemp="lv0025@"+clientprojectbean.getName()+"#"+clientprojectbean.getJmurl()+"#"+
				               clientprojectbean.getSetdate()+"#"+clientprojectbean.getPlaytype()+"#"+clientprojectbean.getPlaysecond();
						
						//删除ftp上的mac地址文件对应的节目
						String cntmac=FirstStartServlet.projectpath+"serverftp\\program_file\\"+clientprojectbean.getProgram_delid()+".xml";
						
						String ipaddress=clientprojectbean.getClientip();
						FirstStartServlet.client.allsend(clientprojectbean.getProgram_delid(),ipaddress, strtemp);
						new ReadMacXmlUtils().getProjectFromMacXml(cntmac,new ProjectBean(clientprojectbean.getName(),clientprojectbean.getJmurl(),clientprojectbean.getSetdate(),clientprojectbean.getPlaytype(),clientprojectbean.getPlaysecond()),clientprojectbean.getForoverloop(),true); 
					}
				}
				}}).start();
		
		String sql="delete  from xct_jmhistory b,xct_ipaddress a  where  a.cnt_mac=b.program_delid   "+str;
		PreparedStatement pstmt = null;
		Connection con = super.getConection();
		 try {
			pstmt=con.prepareStatement(sql);
			return pstmt.executeUpdate();
		 }catch(Exception e){
			 logger.info(e.getMessage());
			 e.printStackTrace();
		 }finally{
				returnResources(con);
		 }
		 return 0;
	}
	
	
	// 查找此客户端当天是否有此节目
	public int getClientProect(Connection conn,String program_setdate,String ip){
		 int i=0; 
		 String sql="select  count(*) from  xct_jmhistory  " +
		 			" WHERE  program_ip='"+ip+"' and program_setdate='"+program_setdate+"'";
			PreparedStatement pstmt = null;
			ResultSet rs=null;
		 try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())
			  i=rs.getInt(1);
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt);
		}
		return i;
	}
	
	//获取当天所有插播节目
	public int getClientProectInsertCount(String today){
	    String sql=new StringBuffer().append("select  count(*) from  xct_jmhistory WHERE  program_ISloop =1 and program_setdatetime ='").append(today).append("'").toString();
		Connection conn = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		 try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,conn);
		}
		return 0;
	}
	
	public int deleteInsertProject(String program_jmurl){
		String sql="delete from xct_jmhistory   WHERE  program_ISloop =1 and program_JMurl='"+program_jmurl+"'";
		Connection conn = super.getConection();
		PreparedStatement pstmt = null;
		 try {
			pstmt=conn.prepareStatement(sql);
			return pstmt.executeUpdate();
		 }catch(Exception e){
			 logger.info(e.getMessage());
			 e.printStackTrace();
		 }finally{
				returnResources(pstmt,conn);
		 }
		 return 0;
	}
	
	public ClientProjectBean getClientProectForLoop(Connection conn,String program_JMurl,String stardate,String enddate,String mac,int playtype,String playtypetemp){
		
		 String sql="select a.program_JMid, a.program_JMurl,a.program_Name,a.program_SetDate,a.program_EndDate,a.program_ISloop,a.program_PlaySecond,a.program_ip, a.program_delid,b.cnt_name from xct_jmhistory a , xct_ipaddress b" +
		 			" WHERE a.program_issend=2 and a.program_JMurl='"+program_JMurl+"'  and a.program_delid='"+mac+"' and a.program_setdatetime = '"+stardate+"' and a.program_ISloop in(0,3) " +
		 			" and a.program_delid=b.cnt_mac";                                           
			PreparedStatement pstmt = null;
			ResultSet rs=null;
		 try {
//			/////System.out.println(sql);
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				
				return	 new ClientProjectBean(rs.getString("program_JMid"),rs.getString("cnt_name"),
					                                       rs.getString("program_JMurl"),
					                                       rs.getString("program_Name"),
					                                       rs.getString("program_SetDate").replace(":00.0", ""),
					                                       rs.getString("program_EndDate").replace(":00.0", ""),
					                                       rs.getInt("program_ISloop")+"",
					                                       rs.getInt("program_PlaySecond")+"",
					                                       rs.getString("program_ip"),
					                                       playtypetemp,
					                                       rs.getString("program_delid"));
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt);
		}
		return null;
	}
	
	
	public ClientProjectBean getClientProectForInsert(Connection conn,String mac,int playtype,String playtypetemp){
		 
		 String enddatetime=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) ;
		 String sql="select a.program_JMid, a.program_JMurl,a.program_Name,a.program_SetDate,a.program_EndDate,a.program_ISloop,a.program_PlaySecond,a.program_ip,a.program_delid,b.cnt_name from  xct_jmhistory a , xct_ipaddress b" +
		 			" WHERE a.program_issend=2 and  a.program_delid='"+mac+"' and  program_enddatetime='"+enddatetime+"'  and a.program_ISloop ="+playtype+" " +
		 			" and a.program_delid=b.cnt_mac";                                           
			PreparedStatement pstmt = null;
			ResultSet rs=null;
		 try {
//			/////System.out.println("getClientProectForInsert--------> "+sql);
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){ 
				return	new ClientProjectBean(rs.getString("program_JMid"),
					   									rs.getString("cnt_name"),
					                                       rs.getString("program_JMurl"),
					                                       rs.getString("program_Name"),
					                                       rs.getString("program_SetDate").replace(":00.0", ""),
					                                       rs.getString("program_EndDate").replace(":00.0", ""),
					                                       rs.getInt("program_ISloop")+"",
					                                       rs.getInt("program_PlaySecond")+"",
					                                       rs.getString("program_ip"),
					                                       playtypetemp,
					                                       rs.getString("program_delid"));
					                                       
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt);
		}
		return null;
	}
	
	
	// 查出选择时间段内的所有定时节目(多个) 
	public ClientProjectBean getClientActiveProject(Connection conn,String setstartdate,String enddatetime,String mac,int playtype){
		 //过滤条件 三个都具备  
		 String sql="select a.program_JMurl,a.program_Name,a.program_SetDate,a.program_EndDate,a.program_ISloop,a.program_PlaySecond,a.program_ip ,a.program_delid,b.cnt_name  from  xct_jmhistory a , xct_ipaddress b " +
		 			" WHERE a.program_issend=2 and  a.program_delid='"+mac+"' and a.program_ISloop ="+playtype+"  and" +
		 					" a.program_delid=b.cnt_mac  " +
		 					        " and a.program_setdate <= '"+setstartdate+"' and a.program_enddate >= '"+enddatetime+"'" + //比 开始大 和 比 结束小 ，但与 开始时间和结束时间有个交集
		 							" and a.program_setdate <= '"+setstartdate+"' and a.program_enddate <= '"+enddatetime+"'" +  //比 开始大 和 比 结束大，但与 开始时间和结束时间有个交集
		 							" and a.program_setdate >= '"+setstartdate+"' and a.program_enddate >= '"+enddatetime+"'";// 比 开始小 和 比 结束小，但与 开始时间和结束时间有个交集
			PreparedStatement pstmt = null;
			ResultSet rs=null;
		 try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			ClientProjectBean clientprojectbean=null;
			if(rs.next()){
				clientprojectbean=new ClientProjectBean();
				clientprojectbean.setPlayclient(rs.getString("cnt_name"));
				clientprojectbean.setName( rs.getString("program_Name"));
				clientprojectbean.setSetdate(rs.getString("program_SetDate").split(" ")[1].replace(".0", ""));
				clientprojectbean.setEnddate(rs.getString("program_EndDate").split(" ")[1].replace(".0", ""));
				clientprojectbean.setIsloop(rs.getInt("program_ISloop")+"");
				
				String strtemp="";
				int program_isloop=rs.getInt("program_isloop");
				if(program_isloop==0||program_isloop==3)
					strtemp="循环";
				if(program_isloop==1)
					strtemp="点播";
				if(program_isloop==2)
					strtemp="定时";
				
				clientprojectbean.setPlaytype(strtemp);
				clientprojectbean.setPlaysecond(rs.getInt("program_PlaySecond")+"");
				clientprojectbean.setClientip(rs.getString("program_ip"));//
				clientprojectbean.setProgram_delid(rs.getString("program_delid"));//mac
			  return	clientprojectbean;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt);
		}
		return null;
	} 
	
	public List<ClientProjectBean> getClientCompareProject(List<ClientProjectBean> list,String startetemp,String endtimetemp){
		
		 List<ClientProjectBean> clientprojectlist=new ArrayList<ClientProjectBean>();
		 String []starttime=startetemp.split(" "); //取时间用
		 String []endttime=endtimetemp.split(" "); //取时间用
		 boolean flag;
		 ClientProjectBean clientprojectbean=null;
		 for(int i=0,n=list.size();i<n;i++){
			 flag=false;
			 clientprojectbean=list.get(i);
			 String[] setstartdate=clientprojectbean.getSetdate().split(" ");//取日期用
			 String[] enddatetime=clientprojectbean.getEnddate().split(" ");//取日期用
			 
			 String tempstart=setstartdate[0]+" "+starttime[1];
			 String tempend=enddatetime[0]+" "+ endttime[1];

			 if (tempstart.compareTo(clientprojectbean.getSetdate()) >= 0 && tempstart.compareTo(clientprojectbean.getEnddate()) <= 0){
				 flag=true;
			 } 
			 if(tempend.compareTo(clientprojectbean.getSetdate()) >= 0 && tempend.compareTo(clientprojectbean.getEnddate()) <= 0){
				  flag=true;
		     }
			 if(flag)
			   clientprojectlist.add(clientprojectbean);
		 }
		 return clientprojectlist;
	}
	
	//获取客户端所有插播类型的节目列表
	public List<ClientProjectBean> getClientForDeleteInsertProject(int playtype){
		
		List<ClientProjectBean> clientprojectlist=new ArrayList<ClientProjectBean>();
		 //过滤条件 三个都具备  
		 String sql="select  program_JMid, program_JMurl,program_Name,program_SetDate,program_EndDate,program_ISloop,program_PlaySecond,program_ip,program_delid  from  xct_jmhistory " +
		 			" WHERE  program_ISloop ="+playtype+"";
		 Connection conn = super.getConection();
			PreparedStatement pstmt = null;
			ResultSet rs=null;
		 try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				  clientprojectlist.add(new ClientProjectBean(rs.getString("program_JMid"),"",
                          rs.getString("program_JMurl"),
                          rs.getString("program_Name"),
                          rs.getString("program_SetDate").replace(":00.0", ""),
                          rs.getString("program_EndDate").replace(":00.0", ""),
                          rs.getInt("program_ISloop")+"",
                          rs.getInt("program_PlaySecond")+"",
                          rs.getString("program_ip")
                          ,"",rs.getString("program_delid")));
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,conn);
		}
		return clientprojectlist;
	}

	
	public int getClientProect1(String setstartdate,String setenddate,String programname,String substr){
		 int i=0;
		 if("".equals(setstartdate)||null==setstartdate)
			 setstartdate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		 String strtmp="0";
		 if(programname.indexOf("循环")!=-1)
			 strtmp="0";
		 else if(programname.indexOf("插播")!=-1)
			 strtmp="1";
		 else if(programname.indexOf("定时")!=-1)
			 strtmp="2";
		 else if(programname.indexOf("永久循环")!=-1)
			 strtmp="3";
		 else
			 strtmp="0";
		 
		 String sql="select  count(*) from xct_ipaddress a , xct_jmhistory b " +
		 			" WHERE   (a.cnt_name like '%"+programname+"%') and" +
		 					" b.program_setdatetime between '"+setstartdate+"' and '"+setenddate+"' and a.cnt_mac=b.program_delid ";
		 
		 if(substr.equals("1"))//节目名
		    sql="select  count(*) from xct_ipaddress a , xct_jmhistory b " +
			" WHERE   (b.program_Name like '%"+programname+"%') and" +
					" b.program_setdatetime between '"+setstartdate+"' and '"+setenddate+"' and a.cnt_mac=b.program_delid ";
		 
		 else if(substr.equals("2"))//播放类型
		    sql="select  count(*) from xct_ipaddress a , xct_jmhistory b " +
			" WHERE   ( b.program_ISloop ='"+strtmp+"') and" +
					" b.program_setdatetime between '"+setstartdate+"' and '"+setenddate+"' and a.cnt_mac=b.program_delid ";
		
		 Connection con = super.getConection();
			PreparedStatement pstmt = null;
			ResultSet rs=null;
		 try {
//			 System.out.println(sql);
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next())
			  i=rs.getInt(1);
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return i;
	}
	
	public int getClientProects(String setstartdate){
		
		 int i=0;
		 if("".equals(setstartdate)||null==setstartdate)
			 setstartdate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				 
		 //过滤条件 三个都具备  
		 String sql="select  count(*) from xct_ipaddress a , xct_jmhistory b "+
                    " WHERE  b.program_setdatetime = '"+setstartdate+"' and a.cnt_mac=b.program_delid ";//
		
		 Connection con = super.getConection();
			PreparedStatement pstmt = null;
			ResultSet rs=null;
		 try {
//			 /////System.out.println(sql);
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next())
			  i=rs.getInt(1);
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return i;
	}
	
	 //获得根据当天的日期计算N个工作日后日期是哪天
	 public static  Date getAfterDate(int n){ 
		  Calendar c = Calendar.getInstance(); 
		  c.add(Calendar.DAY_OF_MONTH, n); 
		  return c.getTime(); 
	 }
	
	public static void main(String []agrs){
//		/////System.out.println(new ManagerProjectDao().getClientProect("zhangfang", " ",  "10.0.32.30"));
		
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//	    java.util.Date now;
//	    long day=0l;
//		try {
//			now = df.parse("2004-03-26");
//			java.util.Date date=df.parse("2004-03-22");
//			long l=now.getTime()-date.getTime();
//			day=l/(24*60*60*1000);
//			/////System.out.println(day);
//			
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		for(int i=0,n=(int) day;i<n;i++){
//			/////System.out.println( new SimpleDateFormat("yyyy,MM,dd").format(getAfterDate(0)));
//		}
    }
}
