package com.xct.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.CntResponse;

public class CntResponseDAO extends DBConnection {
	Logger logger = Logger.getLogger(CntResponseDAO.class);
	/**
	 * �����ն�Ip��ַ������õ����ն˷��ص�����һ������Ľ��
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
			String sql="select  * from xct_cnt_response where cnt_ip=? and cnt_cmd=?  order by id desc limit 1 ";
			//SQLServer
//			String sql="select top 1 * from xct_cnt_response where cnt_ip=? and cnt_cmd=?  order by id desc ";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, cnt_ip);
			 pstmt.setString(2, cmd);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				cntresponse= new CntResponse();
				cntresponse.setId(rs.getInt("id"));
				cntresponse.setCnt_ip(rs.getString("cnt_ip"));
				cntresponse.setCnt_cmd(rs.getString("cnt_cmd"));
				cntresponse.setCnt_cmdstatus(rs.getString("cnt_cmdstatus"));
				cntresponse.setCnt_cmdresult(rs.getString("cnt_cmdresult"));
				cntresponse.setCnt_programurl(rs.getString("cnt_programurl"));
				return cntresponse;
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("�����ն�Ip��ַ������õ����ն˷��ص�����һ������Ľ����==>").append(e.getMessage()).toString());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return cntresponse;
	}
	/**
	 * 
	 * ���������ȡ�����ն˵ķ��ؽ��
	 * @param cmd
	 * @return
	 */
	public List<CntResponse> getCntResponsesByCmd(String cmd){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		List<CntResponse>list=null;
		try{
			String sql="select * from xct_cnt_response where cnt_cmd=?";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, cmd);
			 rs=pstmt.executeQuery();
			list=new ArrayList<CntResponse>();
			CntResponse cntresponse=null;
			while(rs.next()){
				cntresponse= new CntResponse();
				cntresponse.setId(rs.getInt("id"));
				cntresponse.setCnt_ip(rs.getString("cnt_ip"));
				cntresponse.setCnt_cmd(rs.getString("cnt_cmd"));
				cntresponse.setCnt_cmdstatus(rs.getString("cnt_cmdstatus"));
				cntresponse.setCnt_cmdresult(rs.getString("cnt_cmdresult"));
				cntresponse.setCnt_programurl(rs.getString("cnt_programurl"));
				list.add(cntresponse);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("��������").append(cmd).append("��ȡ�����ն˵ķ��ؽ������=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	
	public List<CntResponse> getCntResponsesByStr(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		List<CntResponse> list=null;
		try{
			String sql="select * from xct_cnt_response "+str;
			 pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			list=new ArrayList<CntResponse>();
			CntResponse cntresponse=null;
			while(rs.next()){
				cntresponse= new CntResponse();
				cntresponse.setId(rs.getInt("id"));
				cntresponse.setCnt_ip(rs.getString("cnt_ip"));
				cntresponse.setCnt_cmd(rs.getString("cnt_cmd"));
				cntresponse.setCnt_cmdstatus(rs.getString("cnt_cmdstatus"));
				cntresponse.setCnt_cmdresult(rs.getString("cnt_cmdresult"));
				cntresponse.setCnt_programurl(rs.getString("cnt_programurl"));
				list.add(cntresponse);
				}
			
		}catch(Exception e){
			logger.error(new StringBuffer().append("��������").append(str).append("��ȡ�����ն˵ķ��ؽ������=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
}
