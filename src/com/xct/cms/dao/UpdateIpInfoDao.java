package com.xct.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.xct.cms.db.DBConnection;

public class UpdateIpInfoDao extends DBConnection {
	Logger logger = Logger.getLogger(UsersDAO.class);
	
//	xct_ipaddress,xct_JMhistory,xct_JMApp(要带#号的处理),xct_cnt_response,xct_fault
	
//	update xct_ipaddress set cnt_ip='192.168.10.45' where cnt_ip='192.168.10.44'
	
	public boolean  updateIpInfo(String newip,String oldip){
		
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		String sql1="update xct_ipaddress set cnt_ip='"+newip+"' where cnt_ip='"+oldip+"'";//终端表
		String sql2="update xct_JMhistory set program_ip='"+newip+"' where program_ip='"+oldip+"'";//节目表
		String sql3="update xct_cnt_response set cnt_ip='"+newip+"' where cnt_ip='"+oldip+"'";//终端返回结果表
		String sql4="update xct_fault set fault_ip='"+newip+"' where fault_ip='"+oldip+"'";//故障表
		String sql5="update xct_JMApp set program_play_terminal=replace(program_play_terminal,'"+oldip+"','"+newip+"')," +
				                            "program_sendok_terminal=replace(program_sendok_terminal,'"+oldip+"','"+newip+"')  " +
				                            "where program_play_terminal like '%"+oldip+"%' or program_sendok_terminal like '%"+oldip+"%'";
		try { 
			//事务提交 这个设置需要在连接池的URL后面加SelectMethod=Cursor
//			con.setAutoCommit(false);
			
			pstmt=con.prepareStatement(sql1);
			pstmt.executeUpdate();
			pstmt=con.prepareStatement(sql2);
			pstmt.executeUpdate();	
			pstmt=con.prepareStatement(sql3);
			pstmt.executeUpdate();
			pstmt=con.prepareStatement(sql4);
			pstmt.executeUpdate();
			pstmt=con.prepareStatement(sql5);
			pstmt.executeUpdate();
//			con.commit();
			return true;
		}catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return false;
		}finally{
			returnResources(pstmt,con);
		}
	}

}
