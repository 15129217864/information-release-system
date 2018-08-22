package com.xct.cms.xy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.xct.cms.dao.ClientIpAddressASC;
import com.xct.cms.dao.TerminalSortASC;
import com.xct.cms.dao.TerminalSortDESC;
import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Terminal;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.xy.domain.ClientIpAddress;
import com.xct.cms.xy.domain.Group;

public class ConnectionFactory  extends DBConnection{
	
	Logger logger = Logger.getLogger(ConnectionFactory.class);
    
    public boolean addTerminal(String ip,String mac,String statu,String nowprojramname){
    	
    	String sqled="select * from Terminal where mac='"+mac+"'";
		String sql ="insert into Terminal  (ip,mac,statu,playname) values ('"+ip+"','"+mac+"','"+statu+"','"+nowprojramname+"')";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt = con.prepareStatement(sqled);
			rs=pstmt.executeQuery();
			if(rs.next()){
				return false;
			}else{
				pstmt = con.prepareStatement(sql);
				if(pstmt.executeUpdate()>0)
					return true;
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return false;
    }
    
    
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
		String sql ="select * from xct_zu where zu_type=0 or zu_type=99";
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
    
    public Map<String,ClientIpAddress> getAllIpAddress(boolean  flag){
    	
    	Map<String,ClientIpAddress> clientipaddressmap=new LinkedHashMap<String,ClientIpAddress>();
		String sql ="select * from xct_ipaddress";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(flag)
				  clientipaddressmap.put(rs.getInt("cnt_zuid")+"",new ClientIpAddress(rs.getString("cnt_zuid"),rs.getString("cnt_ip"),rs.getString("cnt_name"),rs.getString("cnt_mac")));
				else
				  clientipaddressmap.put(rs.getString("cnt_ip"),new ClientIpAddress(rs.getString("cnt_zuid"),rs.getString("cnt_ip"),rs.getString("cnt_name"),rs.getString("cnt_mac")));
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return clientipaddressmap;
    }
    public List<ClientIpAddress> getAllIpAddress(String str){
    	
   	 List<ClientIpAddress> clientipaddresslist=new ArrayList<ClientIpAddress>();
   //Mysql
	    String sql ="select * from xct_ipaddress "+str+" order by cast(replace(cnt_ip,'.','')as signed)";
     //SQLServer     
//	String sql ="select * from xct_ipaddress "+str+" order by convert(numeric(15),replace(cnt_ip,'.',''))";
   		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
   		try {
   			pstmt = con.prepareStatement(sql);
   			rs = pstmt.executeQuery();
   			while(rs.next()){
   				clientipaddresslist.add(new ClientIpAddress(rs.getInt("cnt_zuid")+"",rs.getString("cnt_ip"),rs.getString("cnt_name"),rs.getString("cnt_mac")));
   			}
   		}catch(Exception e){
   			logger.info(e.getMessage());
   			e.printStackTrace();
   		}finally{
   			returnResources(rs,pstmt,con);
		}
   		return clientipaddresslist;
       }
    /**
     * 根据TYPE获取所有连接的终端
     * 1=当前连接终端
     * @return
     */
    public List<ClientIpAddress> getAllLinkCnt(int type){
    	Map<String,Terminal> cnt_map=FirstStartServlet.terminalMap;
    	List<ClientIpAddress> clientipaddresslist=new ArrayList<ClientIpAddress>();
    	Terminal t= new Terminal();
		for(Map.Entry<String, Terminal>enty: cnt_map.entrySet()){
			t=enty.getValue();
			if(null!=t){
				if(type==1){  ///当前审核并连接未休眠终端
					if(!"NULL".equals(t.getCnt_nowProgramName().toUpperCase())&&null!=t.getCnt_name()&&!"CLOSE".equals(t.getCnt_playstyle())&&(t.getCnt_islink()==1||t.getCnt_islink()==2)&&"1".equals(t.getCnt_status()))
					clientipaddresslist.add(new ClientIpAddress(t.getCnt_zuid()+"",t.getCnt_ip(),t.getCnt_name(),t.getCnt_mac()));
				}else if(type==2){ ///当前审核并连接休眠终端
					if("NULL".equals(t.getCnt_nowProgramName().toUpperCase())&&null!=t.getCnt_name()&&"CLOSE".equals(t.getCnt_playstyle())&&(t.getCnt_islink()==1||t.getCnt_islink()==2)&&"1".equals(t.getCnt_status()))
					clientipaddresslist.add(new ClientIpAddress(t.getCnt_zuid()+"",t.getCnt_ip(),t.getCnt_name(),t.getCnt_mac()));
				}else if(type==3){ ///当前所有审核并连接终端
					if((t.getCnt_islink()==1||t.getCnt_islink()==2)&&"1".equals(t.getCnt_status())&&null!=t.getCnt_name())
						clientipaddresslist.add(new ClientIpAddress(t.getCnt_zuid()+"",t.getCnt_ip(),t.getCnt_name(),t.getCnt_mac()));
				}else if(type==4){ ///当前所有已审核终端
					if("1".equals(t.getCnt_status())&&null!=t.getCnt_name())
						clientipaddresslist.add(new ClientIpAddress(t.getCnt_zuid()+"",t.getCnt_ip(),t.getCnt_name(),t.getCnt_mac()));
				}
			}
		}
		clientipaddresslist=getSortList(clientipaddresslist);
      	return clientipaddresslist;
     }
    public List<ClientIpAddress> getSortList(List<ClientIpAddress> list){
		if(list!=null)Collections.sort(list,new ClientIpAddressASC()); 
		return list;
	}
    
   public Map<String,ClientIpAddress> getAllClient(){
    	
    	Map<String,ClientIpAddress> clientipaddressmap=new LinkedHashMap<String,ClientIpAddress>();
		String sql ="select * from xct_ipaddress";
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
    
    public static void main(String []args){
     }
}
