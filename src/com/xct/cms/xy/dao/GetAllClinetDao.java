package com.xct.cms.xy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.xct.cms.db.DBConnection;
import com.xct.cms.xy.domain.ClientIpAddress;
import com.xct.cms.xy.domain.Group;

public class GetAllClinetDao extends DBConnection{
	
	Logger logger = Logger.getLogger(GetAllClinetDao.class);

	public List<Group> getFatherGroup(){
    	
    	List<Group> groupbeanlist=new ArrayList<Group>();
		String sql ="select * from xct_zu where zu_pth='0'";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				///////System.out.println(rs.getInt("zu_id")+" "+rs.getInt("zu_pth")+" "+rs.getString("zu_name"));
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return groupbeanlist;
    }

    
    public List<Group> getAllGroup(){
    	
    	List<Group> groupbeanlist=new ArrayList<Group>();
		String sql ="select * from xct_zu";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				groupbeanlist.add(new Group(rs.getInt("zu_id"),rs.getInt("zu_pth"),rs.getString("zu_name")));
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return groupbeanlist;
    }
    
    public Map<String,ClientIpAddress> getAllIpAddress(int  flag){
    	
    	Map<String,ClientIpAddress> clientipaddressmap=new LinkedHashMap<String,ClientIpAddress>();
		String sql ="select * from xct_ipaddress";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(flag==0)
				   clientipaddressmap.put(rs.getString("cnt_zuid")+"",new ClientIpAddress(rs.getString("cnt_zuid"),rs.getString("cnt_ip"),rs.getString("cnt_name"),rs.getString("cnt_mac")));
				else if(flag==1)
				   clientipaddressmap.put(rs.getString("cnt_ip"),new ClientIpAddress(rs.getString("cnt_zuid"),rs.getString("cnt_ip"),rs.getString("cnt_name"),rs.getString("cnt_mac")));
				else if(flag==2)
				   clientipaddressmap.put(rs.getString("cnt_name"),new ClientIpAddress(rs.getString("cnt_zuid"),rs.getString("cnt_ip"),rs.getString("cnt_name"),rs.getString("cnt_mac")));
				else if(flag==3)
				   clientipaddressmap.put(rs.getString("cnt_mac"),new ClientIpAddress(rs.getString("cnt_zuid"),rs.getString("cnt_ip"),rs.getString("cnt_name"),rs.getString("cnt_mac")));}
		}catch(Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return clientipaddressmap;
    }
    
    public List<String> getClientAllMac(){
    	
    	 List<String> maclist=new ArrayList<String>();
    	String sql ="select cnt_mac from xct_ipaddress";
    	Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				maclist.add(rs.getString("cnt_mac"));
			}
		
		}catch(Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return maclist;
    }
    
    
    public Map<String ,String> getClientIpMac(){
    	
    	Map<String ,String>map=new HashMap<String, String>();
    	String sql ="select cnt_mac,cnt_ip from xct_ipaddress";
    	Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				map.put(rs.getString("cnt_mac"), rs.getString("cnt_ip"));
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return map;
   }
    
   public Map<String,ClientIpAddress> getClient(String info){
    	
    	Map<String,ClientIpAddress> clientipaddressmap=new LinkedHashMap<String,ClientIpAddress>();
    	//Mysql
    	String sql ="select * from xct_JMhistory a,xct_ipaddress b where a.program_delid=b.cnt_mac and (a.program_ip like '%"+info + "%' or a.program_Name like '%"+info + "%' or b.cnt_name like '%"+info + "%' limit 10";
    	//SQLServer
//		String sql ="select top(10)* from xct_JMhistory a,xct_ipaddress b where a.program_delid=b.cnt_mac and (a.program_ip like '%"+info + "%' or a.program_Name like '%"+info + "%' or b.cnt_name like '%"+info + "%'";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				  clientipaddressmap.put(rs.getString("cnt_name"),new ClientIpAddress(rs.getString("cnt_zuid"),rs.getString("cnt_ip"),rs.getString("cnt_name"),rs.getString("cnt_mac")));
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return clientipaddressmap;
    }
}
