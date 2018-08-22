package com.xct.cms.xy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xct.cms.db.DBConnection;
import com.xct.cms.xy.domain.CntResponse;


public class CntResponseDAO extends DBConnection {
	Logger logger = Logger.getLogger(CntResponseDAO.class);
	/**
	 * 根据终端Ip地址和命令得到该终端返回的最新一条命令的结果
	 * @param cnt_ip
	 * @return
	 */
	public CntResponse getNewCmd(String cnt_ip,String cmd){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		CntResponse cntresponse=null;
		try{
			//Mysql
			String sql="select * from xct_cnt_response where cnt_ip=? and cnt_cmd=?  order by id desc limit 1 ";
			//SQLServer
//			String sql="select top 1 * from xct_cnt_response where cnt_ip=? and cnt_cmd=?  order by id desc ";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, cnt_ip);
			 pstmt.setString(2, cmd);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				cntresponse= new CntResponse();
				cntresponse.setCnt_ip(rs.getString("cnt_ip"));
				cntresponse.setCnt_cmd(rs.getString("cnt_cmd"));
				cntresponse.setCnt_cmdstatus(rs.getString("cnt_cmdstatus"));
				cntresponse.setCnt_cmdresult(rs.getString("cnt_cmdresult"));
				return cntresponse;
			}
		}catch(Exception e){
			logger.info("根据终端Ip地址和命令得到该终端返回的最新一条命令的结果！====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return cntresponse;
		
	}
	/**
	 * 
	 * 根据命令获取所有终端的返回结果
	 * @param cmd
	 * @return
	 */
	public List getCntResponsesByCmd(String cmd){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		List list=null;
		CntResponse cntresponse=null;
		try{
			String sql="select * from xct_cnt_response where cnt_cmd=?";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, cmd);
			 rs=pstmt.executeQuery();
			list=new ArrayList();
			while(rs.next()){
				cntresponse= new CntResponse();
				cntresponse.setCnt_ip(rs.getString("cnt_ip"));
				cntresponse.setCnt_cmd(rs.getString("cnt_cmd"));
				cntresponse.setCnt_cmdstatus(rs.getString("cnt_cmdstatus"));
				cntresponse.setCnt_cmdresult(rs.getString("cnt_cmdresult"));
				list.add(cntresponse);
				}
			
		}catch(Exception e){
			logger.info("根据命令"+cmd+"获取所有终端的返回结果出错！====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
}
