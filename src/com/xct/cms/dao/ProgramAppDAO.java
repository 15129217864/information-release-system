package com.xct.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.ProgramApp;

public class ProgramAppDAO extends DBConnection {
	Logger logger = Logger.getLogger(ProgramAppDAO.class);
	
	
//	达海集成,已发送节目单
	public List<ProgramApp> getOnlyProgramApp(String mac,String begintime,String endtime){
		
		begintime=begintime.replaceAll("_"," ");
		endtime=endtime.replaceAll("_"," ");
		
		String sqlString="select a.id,program_name,program_jmurl,program_app_time,b.name,program_playdate,program_playtime,program_enddate,program_endtime,program_play_type,program_playlong,program_play_terminal "  
			+" from xct_jmapp a ,xct_lg b where a.program_app_userid=b.lg_name and (program_playdate+' '+program_playtime)>='"+begintime+"' and (program_enddate+' '+program_endtime)<='"+endtime+"' or a.program_play_type='3'  and program_play_terminal like '%"+mac+"%'";
		
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		List<ProgramApp> list=null;
		try{
			pstmt=con.prepareStatement(sqlString);
			rs=pstmt.executeQuery();
			list=new ArrayList<ProgramApp>();
			String todayString=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			while(rs.next()){
				ProgramApp program= new ProgramApp();
				program.setId(rs.getInt("id"));
				program.setMac(mac);
				program.setProgram_name(rs.getString("program_name"));
				program.setProgram_jmurl(rs.getString("program_jmurl"));
				program.setProgram_app_userid(rs.getString("name"));//中文姓名
				program.setProgram_app_time(rs.getString("program_app_time"));//节目创建时间
				
				int program_play_type=rs.getInt("program_play_type");
				program.setProgram_playdate(program_play_type==3?todayString:rs.getString("program_playdate"));
				
				program.setProgram_playtime(rs.getString("program_playtime"));
				program.setProgram_enddate(program_play_type==3?todayString:rs.getString("program_enddate"));
				
				program.setProgram_endtime(rs.getString("program_endtime"));
				program.setProgram_play_type(program_play_type);
				program.setProgram_playlong(rs.getInt("program_playlong"));

				list.add(program);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(mac).append("_").append(begintime).append("_").append(endtime).append(">>获取已发送节目出错！==getOnlyProgramApp===").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	
	public List<ProgramApp> getALLProgramAppByStr(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		List<ProgramApp> list=null;
		try{
			String sql="select * from xct_JMApp "+str;
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			list=new ArrayList<ProgramApp>();
			while(rs.next()){
				ProgramApp program= new ProgramApp();
				program.setId(rs.getInt("id"));
				program.setProgram_id(rs.getString("program_id"));
				program.setProgram_jmurl(rs.getString("program_jmurl"));
				program.setProgram_name(rs.getString("program_name"));
				program.setProgram_playdate(rs.getString("program_playdate"));
				program.setProgram_playtime(rs.getString("program_playtime"));
				program.setProgram_enddate(rs.getString("program_enddate"));
				program.setProgram_endtime(rs.getString("program_endtime"));
				program.setProgram_playlong(rs.getInt("program_playlong"));
				program.setProgram_play_type(rs.getInt("program_play_type"));
				
				String program_play_terminal=rs.getString("program_play_terminal");
				String program_sendok_terminal=rs.getString("program_sendok_terminal");
				int program_app_status=rs.getInt("program_app_status");
				
				program.setProgram_play_terminal(program_play_terminal);
				program.setProgram_sendok_terminal(program_sendok_terminal);
				program.setProgram_app_userid(rs.getString("program_app_userid"));
				program.setProgram_app_time(rs.getString("program_app_time"));
				
				if(program_app_status==1&&!program_play_terminal.equals(program_sendok_terminal)){
					program_app_status=4;
				}
				program.setProgram_app_status(program_app_status);
				program.setProgram_treeid(rs.getInt("program_treeid"));
				program.setTemplateid(rs.getString("templateid"));
				
				list.add(program);
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取已发送节目出错！==1===").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	/**
	 * 根据 BATCH  分组查询数据
	 * @param str  条件
	 * @return  返回一个List<ProgramApp>
	 */ 
	public List<ProgramApp> getGroupByBatch(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		List<ProgramApp> list=null;
		try{
			String sql="select program_app_time,program_play_terminal,batch,program_app_userid,name,send_user,send_time from  xct_JMApp,xct_LG  where lg_name=program_app_userid "+str+" Group by batch,program_app_time,program_app_userid,name,send_user,send_time,program_play_terminal order by batch desc";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			list=new ArrayList<ProgramApp>();
			while(rs.next()){
				ProgramApp program= new ProgramApp();
				program.setProgram_app_time(rs.getString("program_app_time"));
				program.setProgram_play_terminal(rs.getString("program_play_terminal"));
				program.setBatch(rs.getInt("batch"));
				program.setProgram_app_userid(rs.getString("program_app_userid"));
				program.setProgram_app_name(rs.getString("name"));
				program.setSend_user(rs.getString("send_user"));
				program.setSend_time(rs.getString("send_time"));
				list.add(program);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>BATCH分组查询数据出错！==2===").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	
	public List<ProgramApp> getALLProgramAppByStr(Connection con ,String str){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		List<ProgramApp> list=null;
		try{
			String sql="select * from xct_JMApp "+str;
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			list=new ArrayList<ProgramApp>();
			while(rs.next()){
				ProgramApp program= new ProgramApp();
				program.setId(rs.getInt("id"));
				program.setProgram_id(rs.getString("program_id"));
				program.setProgram_jmurl(rs.getString("program_jmurl"));
				program.setProgram_name(rs.getString("program_name"));
				program.setProgram_playdate(rs.getString("program_playdate"));
				program.setProgram_playtime(rs.getString("program_playtime"));
				program.setProgram_enddate(rs.getString("program_enddate"));
				program.setProgram_endtime(rs.getString("program_endtime"));
				program.setProgram_playlong(rs.getInt("program_playlong"));
				program.setProgram_play_type(rs.getInt("program_play_type"));
				program.setProgram_play_terminal(rs.getString("program_play_terminal"));
				program.setProgram_sendok_terminal(rs.getString("program_sendok_terminal"));
				program.setProgram_app_userid(rs.getString("program_app_userid"));
				program.setProgram_app_time(rs.getString("program_app_time"));
				program.setProgram_app_status(rs.getInt("program_app_status"));
				program.setProgram_treeid(rs.getInt("program_treeid"));
				program.setTemplateid(rs.getString("templateid"));
				
				list.add(program);
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取已发送节目出错！===3==").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return list;
	}
	public String getProgramAppByStrProgramid(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sting="";
		try{
			String sql=new StringBuffer().append("select id from xct_JMApp where program_id='").append(str).append("'").toString();
			 pstmt=con.prepareStatement(sql);
//			 System.out.println(sql);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				 sting=rs.getInt("id")+"";
		    }
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取已发送节目出错！==4===").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return sting;
	}
	
	public String getProgramAppByStrProgramid(Connection con,String str){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sting="";
		try{
			String sql=new StringBuffer().append("select id from xct_JMApp where program_id='").append(str).append("'").toString();
			 pstmt=con.prepareStatement(sql);
//			 System.out.println(sql);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				 sting=rs.getInt("id")+"";
		    }
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取已发送节目出错！===5==").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return sting;
	}
	public ProgramApp getProgramAppByStr(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		ProgramApp program=null;
		try{
			String sql="select * from xct_JMApp "+str;
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				 program= new ProgramApp();
				program.setId(rs.getInt("id"));
				program.setProgram_id(rs.getString("program_id"));
				program.setProgram_jmurl(rs.getString("program_jmurl"));
				program.setProgram_name(rs.getString("program_name"));
				program.setProgram_playdate(rs.getString("program_playdate"));
				program.setProgram_playtime(rs.getString("program_playtime"));
				program.setProgram_enddate(rs.getString("program_enddate"));
				program.setProgram_endtime(rs.getString("program_endtime"));
				program.setProgram_playlong(rs.getInt("program_playlong"));
				program.setProgram_play_type(rs.getInt("program_play_type"));
				program.setProgram_play_terminal(rs.getString("program_play_terminal"));
				program.setProgram_sendok_terminal(rs.getString("program_sendok_terminal"));
				program.setProgram_app_userid(rs.getString("program_app_userid"));
				program.setProgram_app_time(rs.getString("program_app_time"));
				program.setProgram_app_status(rs.getInt("program_app_status"));
				program.setProgram_treeid(rs.getInt("program_treeid"));
				program.setTemplateid(rs.getString("templateid"));
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取已发送节目出错！==6===").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return program;
	}public ProgramApp getProgramAppByStr(Connection con,String str){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		ProgramApp program=null;
		try{
			String sql=new StringBuffer().append("select * from xct_JMApp ").append(str).toString();
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			while(rs.next()){
				 program= new ProgramApp();
				program.setId(rs.getInt("id"));
				program.setProgram_id(rs.getString("program_id"));
				program.setProgram_jmurl(rs.getString("program_jmurl"));
				program.setProgram_name(rs.getString("program_name"));
				program.setProgram_playdate(rs.getString("program_playdate"));
				program.setProgram_playtime(rs.getString("program_playtime"));
				program.setProgram_enddate(rs.getString("program_enddate"));
				program.setProgram_endtime(rs.getString("program_endtime"));
				program.setProgram_playlong(rs.getInt("program_playlong"));
				program.setProgram_play_type(rs.getInt("program_play_type"));
				program.setProgram_play_terminal(rs.getString("program_play_terminal"));
				program.setProgram_sendok_terminal(rs.getString("program_sendok_terminal"));
				program.setProgram_app_userid(rs.getString("program_app_userid"));
				program.setProgram_app_time(rs.getString("program_app_time"));
				program.setProgram_app_status(rs.getInt("program_app_status"));
				program.setProgram_treeid(rs.getInt("program_treeid"));
				program.setTemplateid(rs.getString("templateid"));
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取已发送节目出错！==7==").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return program;
	}
	public boolean  UpdateSendState(Connection conn,String sql ){
		PreparedStatement pstmt = null;
		//String sql ="update " + table + " set " + setvalue + " where " + condition+ "";
		 int i=0;
		try {
			pstmt = conn.prepareStatement(sql);
		    i=	pstmt.executeUpdate();
			if(i>0)
				return true;
		} catch (Exception e) {
			logger.error(new StringBuffer().append("更新发送状态出错<<").append(sql).append(">>！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}finally {
			returnResources(pstmt);
		}
		return false;
	}
	
	public int getMaxbatch(Connection conn){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		int batch=0;
		try{
			String sql="select Max(batch) from xct_JMApp ";
			 pstmt=conn.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				batch=rs.getInt(1)+1;
		    }
		}catch(Exception e){
			logger.error(new StringBuffer().append("获取最大的批次号！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return  batch;
	}
}
