package com.xct.cms.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.PoliceSubstationBean;
import com.xct.cms.servlet.FirstStartServlet;


public class PoliceSubstationDao extends DBConnection  {
	
	Logger logger = Logger.getLogger(UsersDAO.class);
	
	public boolean isExistTable() {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sqlString="select COUNT(*) as onetable from sysobjects  where name='police_substation'";
		try {
			pstmt=con.prepareStatement(sqlString);
			rs=pstmt.executeQuery();
			if(rs.next()){
				rs.getInt("onetable");
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
	
	public boolean createTable() {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		String sqlString="CREATE TABLE [dbo].[police_substation] ("+
						"[id] [int] IDENTITY (1, 1) NOT NULL ,"+
						"[roomno] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"+
						"[casecode] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"+
						"[policename] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"+
						"[casecharacter] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NULL ,"+
						"[suspect] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NULL ,"+
					    "[clientip] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"+
					") ON [PRIMARY]";
		try {
			pstmt=con.prepareStatement(sqlString);
			if(pstmt.executeUpdate()>0){
			   return true;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			returnResources(pstmt,con);
		}
		return false;
	}
//	String roomno, String casecode, String policename, String casecharacter, String suspect, String clientip
	public boolean update(List<PoliceSubstationBean>list) {
		String sqlString="update police_substation set roomno=?,casecode=?,policename=?,casecharacter=?,suspect=?,clientip=? where id=?";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		try {
			for (PoliceSubstationBean bean : list) {
				pstmt=con.prepareStatement(sqlString);
				pstmt.setString(1, bean.getRoomno());
				pstmt.setString(2, bean.getCasecode());
				pstmt.setString(3, bean.getPolicename());
				pstmt.setString(4, bean.getCasecharacter());
				pstmt.setString(5, bean.getSuspect());
				pstmt.setString(6, bean.getClientip());
				pstmt.setInt(7, bean.getId());
				pstmt.executeUpdate();
			}
		   return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			returnResources(pstmt,con);
		}
	}
	
	public boolean insert(List<PoliceSubstationBean>list) {
		String sqlString="insert into police_substation (roomno,casecode,policename,casecharacter,suspect,clientip) values (?,?,?,?,?,?)";
		
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		try {
			for (PoliceSubstationBean bean : list) {
				pstmt=con.prepareStatement(sqlString);
				pstmt.setString(1, bean.getRoomno());
				pstmt.setString(2, bean.getCasecode());
				pstmt.setString(3, bean.getPolicename());
				pstmt.setString(4, bean.getCasecharacter());
				pstmt.setString(5, bean.getSuspect());
				pstmt.setString(6, bean.getClientip());
				pstmt.executeUpdate();
			}
		   return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			returnResources(pstmt,con);
		}
	}
	
	public boolean insertOrupdate(List<PoliceSubstationBean>list){
		
		String sqlString1="select * from police_substation where clientip=? and  seqno=?";
		String sqlString2="update police_substation set roomno=?,casecode=?,policename=?,casecharacter=?,suspect=? where clientip=? and  seqno=?";
		String sqlString3="insert into police_substation (seqno,roomno,casecode,policename,casecharacter,suspect,clientip) values (?,?,?,?,?,?,?)";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
		    for (PoliceSubstationBean bean : list) {
			
				pstmt=con.prepareStatement(sqlString1);
				pstmt.setString(1, bean.getClientip());
				pstmt.setString(2, bean.getSeqno());
				rs=pstmt.executeQuery();
				if(rs.next()){
					pstmt=con.prepareStatement(sqlString2);
					pstmt.setString(1, bean.getRoomno());
					pstmt.setString(2, bean.getCasecode());
					pstmt.setString(3, bean.getPolicename());
					pstmt.setString(4, bean.getCasecharacter());
					pstmt.setString(5, bean.getSuspect());
					pstmt.setString(6, bean.getClientip());
					pstmt.setString(7, bean.getSeqno());
					pstmt.executeUpdate();
				}else {
					pstmt=con.prepareStatement(sqlString3);
					pstmt.setString(1, bean.getSeqno());
					pstmt.setString(2, bean.getRoomno());
					pstmt.setString(3, bean.getCasecode());
					pstmt.setString(4, bean.getPolicename());
					pstmt.setString(5, bean.getCasecharacter());
					pstmt.setString(6, bean.getSuspect());
					pstmt.setString(7, bean.getClientip());
					pstmt.executeUpdate();
				}
			}
		    return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			returnResources(rs,pstmt,con);
		}
	}
//	Map<String, String>macipMap=new HashMap<String, String>();
	
	public Map<String, String> getClientsMap() {
		Map<String, String>map=new HashMap<String, String>();
		String sqlString="select cnt_name,cnt_ip,cnt_mac from xct_ipaddress";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sqlString);
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(null!=rs.getString("cnt_name")&&!rs.getString("cnt_name").equals("")){
					String ip=rs.getString("cnt_ip");
					String cnt_mac=rs.getString("cnt_mac");
					map.put(ip+"_"+cnt_mac, rs.getString("cnt_name"));
					
//					macipMap.put(ip, cnt_mac);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return map;
	}
	
	public boolean CreateXml(List<PoliceSubstationBean>list,String mac){
		
		if(null!=list&&!list.isEmpty()){
			   String xmlfiles=FirstStartServlet.projectpath+"serverftp/program_file/police_"+mac+".xml";
			   PoliceSubstationBean policesubstationbean=null;
		    	Document document=DocumentHelper.createDocument();
		    	Element titleelement=document.addElement("court");
		    	 Element element=null;
		    	 for(int i=0,n=list.size();i<n;i++){
		    		 policesubstationbean=list.get(i);
		    		 element=titleelement.addElement("row");
		    		 element.addAttribute("roomno",policesubstationbean.getRoomno().equals("")?"NAN":policesubstationbean.getRoomno());
		    		 element.addAttribute("casecode",policesubstationbean.getCasecode().equals("")?"NAN":policesubstationbean.getCasecode());
		    		 element.addAttribute("policename",policesubstationbean.getPolicename().equals("")?"NAN":policesubstationbean.getPolicename());
		    		 element.addAttribute("casecharacter",policesubstationbean.getCasecharacter().equals("")?"NAN":policesubstationbean.getCasecharacter());
		    		 element.addAttribute("suspect",policesubstationbean.getSuspect().equals("")?"NAN":policesubstationbean.getSuspect());
		    	 }
		    	try {
					OutputFormat format = OutputFormat.createPrettyPrint();
					format.setEncoding("GBK");
					XMLWriter output = new XMLWriter(new FileWriter(new File(xmlfiles)), format);
					output.write(document);
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
				return true;
		   }else{
			   return false;
		   }
	}
	
	public List<PoliceSubstationBean> getPoliceSubstationsOrderBySeqno(String clientip){
		
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
        List<PoliceSubstationBean> policeSubstationBeanList=new ArrayList<PoliceSubstationBean>();
		try{
			String sql="select * from police_substation where clientip='"+clientip+"' order by seqno";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				policeSubstationBeanList.add(new PoliceSubstationBean(rs.getInt("id"),rs.getString("seqno"),rs.getString("roomno"),
						rs.getString("casecode"), rs.getString("policename"), rs.getString("casecharacter"),
						rs.getString("suspect"),rs.getString("clientip")));
			}
			Collections.sort(policeSubstationBeanList, new policeSubstationBeanCompare());
		}catch(Exception e){
			logger.error("获取派出所询问室出错！====="+e.getMessage());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return policeSubstationBeanList;
	}
	
	public List<PoliceSubstationBean> getPoliceSubstations(String clientip){
		
        List<PoliceSubstationBean> policeSubstationBeanList=getPoliceSubstationsOrderBySeqno(clientip);
		if(policeSubstationBeanList.isEmpty()){
			for(int i=0,n=4;i<n;i++){
				policeSubstationBeanList.add(new PoliceSubstationBean("",((i+1)+""),"","","", "", ""));
			}
		}else{
			if(policeSubstationBeanList.size()==1){
				boolean flagB=false;
				List<String>list=new ArrayList<String>();
				for(int i=0,n=4;i<n;i++){
					list.add(((i+1)+""));
				}
				for(PoliceSubstationBean policesubstationbean:policeSubstationBeanList){
					flagB=false;
					for(int i=0,n=4;i<n;i++){
						if(policesubstationbean.getSeqno().equals((i+1)+"")){
							flagB=true;
							break;
						}
					}
					if(flagB)
					  list.remove(policesubstationbean.getSeqno());
				}
				for(int i=0,n=list.size();i<n;i++){
				   policeSubstationBeanList.add(new PoliceSubstationBean("",((i+1)+""),"","","", "", ""));
				}
				Collections.sort(policeSubstationBeanList, new policeSubstationBeanCompare());
			}
			//-----------------------------------------------
			if(policeSubstationBeanList.size()==2){
				boolean flagB=false;
				List<String>list=new ArrayList<String>();
				for(int i=0,n=4;i<n;i++){
					list.add(((i+1)+""));
				}
				for(PoliceSubstationBean policesubstationbean:policeSubstationBeanList){
					flagB=false;
					for(int i=0,n=4;i<n;i++){
						if(policesubstationbean.getSeqno().equals((i+1)+"")){
							flagB=true;
							break;
						}
					}
					if(flagB)
					  list.remove(policesubstationbean.getSeqno());
				}
				for(int i=0,n=list.size();i<n;i++){
				   policeSubstationBeanList.add(new PoliceSubstationBean("",((i+1)+""),"","","", "", ""));
				}
				Collections.sort(policeSubstationBeanList, new policeSubstationBeanCompare());
			}
			if(policeSubstationBeanList.size()==3){
				boolean flagB=false;
				List<String>list=new ArrayList<String>();
				for(int i=0,n=4;i<n;i++){
					list.add(((i+1)+""));
				}
				for(PoliceSubstationBean policesubstationbean:policeSubstationBeanList){
					flagB=false;
					for(int i=0,n=4;i<n;i++){
						if(policesubstationbean.getSeqno().equals((i+1)+"")){
							flagB=true;
							break;
						}
					}
					if(flagB)
					  list.remove(policesubstationbean.getSeqno());
				}
				for(int i=0,n=list.size();i<n;i++){
				   policeSubstationBeanList.add(new PoliceSubstationBean("",((i+1)+""),"","","", "", ""));
				}
				Collections.sort(policeSubstationBeanList, new policeSubstationBeanCompare());
			}
			//-----------------------------------------------
		}
		return policeSubstationBeanList;	
	}

//	public Map<String, String> getMacipMap() {
//		return macipMap;
//	}
//
//	public void setMacipMap(Map<String, String> macipMap) {
//		this.macipMap = macipMap;
//	}
}
class policeSubstationBeanCompare implements Comparator<PoliceSubstationBean>{

	public int compare(PoliceSubstationBean o1, PoliceSubstationBean o2) {
		String string1=o1.getSeqno();
		String string2=o2.getSeqno();
		if(string1.compareTo(string2)>0)
			return 1;
		else {
			return -1;
		}
	}
}
