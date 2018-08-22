package com.xct.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Logs;
import com.xct.cms.utils.UtilDAO;

public class LogsDAO extends DBConnection{
	Logger logger = Logger.getLogger(LogsDAO.class);
	
	/**
	 * 得到所有日志的日期，根据日期降序排序
	 * @return
	 */
	public List<String> getALLLogDate(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		List<String> list=null;
		try{
			String sql=new StringBuffer().append("select distinct logdate  from xct_log ").append(str).append(" order by logdate desc ").toString();
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			list=new ArrayList<String>();
			while(rs.next()){
				list.add(rs.getString("logdate"));
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("得到所有日志的日期，根据日期降序排序出错！===").append(e.getMessage()).toString());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	/**
	 * 
	 * 根据日期查询所有日志
	 * @param str
	 * @return
	 */
	public List<Logs> getLogsByStr(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		List<Logs> list=null;
		try{
			String sql=new StringBuffer().append("select *  from xct_log ").append(str).append("  order by logid desc ").toString();
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			list=new ArrayList<Logs>();
			while(rs.next()){
				Logs logs= new Logs();
				logs.setLogid(rs.getInt("logid"));
				logs.setLoguser(rs.getString("loguser"));
				logs.setLogdate(rs.getString("logdate"));
				logs.setLogtime(rs.getString("logtime"));
				logs.setLoglog(rs.getString("loglog"));
				logs.setLogdel(rs.getInt("logdel"));
				list.add(logs);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据日期查询所有日志出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	public void addlogs(String loguser,String loginfo,int logtype){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		try{
			String nowtime=UtilDAO.getNowtime("yyyy-MM-dd#HH:mm:ss");
			String nowtimearray[]=nowtime.split("#");
			String sql=new StringBuffer().append("insert into xct_log(loguser,logdate,logtime,loglog,logdel,logtype) ")
					.append("values('").append(loguser).append("','").append(nowtimearray[0]).append("','").append(nowtimearray[1]).append("','").append(loginfo).append("',0,'").append(logtype).append("') ").toString();
			pstmt=con.prepareStatement(sql);
			pstmt.executeUpdate();
		}catch(Exception e){
			logger.error(new StringBuffer().append("添加日志出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(pstmt,con);
		}
	}public void addlogs1(Connection conn,String loguser,String loginfo,int logtype){
		PreparedStatement pstmt = null;
		try{
			String nowtime=UtilDAO.getNowtime("yyyy-MM-dd#HH:mm:ss");
			String nowtimearray[]=nowtime.split("#");
			String sql=new StringBuffer().append("insert into xct_log(loguser,logdate,logtime,loglog,logdel,logtype) ")
					.append("values('").append(loguser).append("','").append(nowtimearray[0]).append("','").append(nowtimearray[1]).append("','").append(loginfo).append("',0,'").append(logtype).append("') ").toString();
			 pstmt=conn.prepareStatement(sql);
			pstmt.executeUpdate();
		}catch(Exception e){
			logger.error( new StringBuffer().append("添加日志出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(pstmt);
		}
	}
	public boolean deleteLogs(String str) {
		boolean bool=false;
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		try {
			String sql = new StringBuffer().append("delete from xct_log " ).append(str).toString();
			pstmt = con.prepareStatement(sql);
			if (pstmt.executeUpdate() == 1)
				bool=true;
		} catch (SQLException e) {
			logger.error(new StringBuffer().append("删除日志出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		} finally {
			returnResources(pstmt,con);
		}
		return bool;
	}public boolean deleteLogs(Connection con,String str) {
		boolean bool=false;
		PreparedStatement pstmt = null;
		try {
			String sql =new StringBuffer().append( "delete from xct_log " ).append(str).toString();
			pstmt = con.prepareStatement(sql);
			if (pstmt.executeUpdate() == 1)
				bool=true;
		} catch (SQLException e) {
			logger.error(new StringBuffer().append("删除日志出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		} finally {
			returnResources(pstmt);
		}
		return bool;
	}
	public static void main(String[] args){
		new LogsDAO().addlogs("888", "发送节目", 1);
		
	}
}
