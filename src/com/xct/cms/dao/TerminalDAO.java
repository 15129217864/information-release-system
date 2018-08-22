package com.xct.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;


import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Terminal;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.Util;
import com.xct.cms.utils.UtilDAO;

public class TerminalDAO extends DBConnection {
	
	static Logger logger = Logger.getLogger(TerminalDAO.class);
	private static  String zu_path="";
	private static  List<Terminal> zu_pathList=new ArrayList<Terminal>();
	private static  List<Terminal> zu_subList=new ArrayList<Terminal>();
	private static  Map<Integer,Terminal> zu_Map=new HashMap<Integer, Terminal>();
	/**
	 * ���µ�ǰ�ն˵�����״̬
	 * @param where
	 * @param vlaue
	 */
	public void updateCntIsLink(int vlaue,int where){
		Map<String,Terminal> terminals=FirstStartServlet.terminalMap;
		Terminal terminal=null;
		String strkey="";
		if(null!=terminals&&terminals.size()>0){
			 for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
				  strkey=(String)entry.getKey();
				  terminal=(Terminal)entry.getValue();
				  if(terminal.getCnt_islink()==where){
					  	terminal.setCnt_islink(vlaue);
					  	if(where==2){
					  		logger.info("�նˣ���"+terminal.getCnt_name()+"����"+terminal.getCnt_ip()+"���Ͽ�����");
					  		terminal.setCnt_nowProgramName("��");
					  		terminal.setCnt_playstyle("CLOSE");
					  	}
					  	FirstStartServlet.terminalMap.put(strkey, terminal);
				  }
			}
		}
	}
	
   SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
   public void updateCntIsLink2(int vlaue,int online){
		
		Map<String,Terminal> terminals=FirstStartServlet.terminalMap;
		if(null!=terminals&&terminals.size()>0){
			 for (Map.Entry<String, Terminal> entry : terminals.entrySet()){
				  Terminal  terminal=(Terminal)entry.getValue();
				  if(terminal.getCnt_islink()==online){//�����ߵ����
					  if(System.currentTimeMillis()-terminal.getIsonlinetime()>=(50*1000)/*(1*60*1000)*/){//50����û���ߣ�����Ϊ�Ͽ�
						  	terminal.setCnt_islink(vlaue);
						  	FirstStartServlet.terminalMap.put(entry.getKey(), terminal);
						  	logger.info(MessageFormat.format("***�����նˣ�{0}_{1}_{2}�����ע��ʱ�䣺{3}",terminal.getCnt_name(),terminal.getCnt_ip(),terminal.getCnt_mac(),sDateFormat.format(new Date(terminal.getIsonlinetime()))));
						  	if(null!=FirstStartServlet.RESPONSE_INTERFACE_TERMINALMAP.get(entry.getKey())){
								FirstStartServlet.RESPONSE_INTERFACE_TERMINALMAP.put(entry.getKey(), terminal);
							}
					  }
				  }
			}
		}
	}
	
	boolean flagB=true;
	
	static int execcountI=0;
	
	public void updateCntstatus(Map<String,Terminal> terminals){
		
		List<Terminal>terminallist=new ArrayList<Terminal>();
		
		if(!Util.TERMINAL_OP_MAP.isEmpty()){
			 for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
				 if(null!=entry){
					 if(null!=Util.TERMINAL_OP_MAP.get(entry.getKey())){
						 if(!Util.TERMINAL_OP_MAP.get(entry.getKey()).equals(entry.getValue().toString())){
							 terminallist.add(entry.getValue());
						 }
					 }
				 }
			 }
		}
		//===============================================================
		Connection con =null;
		PreparedStatement pstmt = null;
		String sql="update xct_ipaddress set cnt_ip=?,cnt_nowProgramName=?,cnt_playstyle=?,cnt_islink=?,cnt_playprojecttring=?,client_version=? where cnt_mac=?";
		try{
			if(Util.TERMINAL_OP==0){
				Util.TERMINAL_OP=1;
				 if(flagB){
					    flagB=false;
						con = super.getConection();
						if(null!=con){
							 for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
								 if(null!=entry){
									 Terminal  t=entry.getValue();
									 if(null!=t){
											pstmt=con.prepareStatement(sql);
											pstmt.setString(1, t.getCnt_ip());
											pstmt.setString(2, t.getCnt_nowProgramName());
											pstmt.setString(3, t.getCnt_playstyle());
											pstmt.setInt(4, t.getCnt_islink());
											pstmt.setString(5, t.getCnt_playprojecttring());
											pstmt.setString(6, t.getClient_version());
											pstmt.setString(7, t.getCnt_mac());
											pstmt.executeUpdate();
											pstmt.close();
											Util.TERMINAL_OP_MAP.put(t.getCnt_mac(), t.toString());
									 }
								 }
							 }
						}else {
							logger.info("----------updateCntstatus-----1------���ݿ�����δ��ȡ����");
						}
						
				   }else {
					   logger.info("----------updateCntstatus------1-----�ϴ����ݸ���δ��ɣ��ȴ��´θ��£�");
				   }
				   flagB=true;
			}else{
				if(!terminallist.isEmpty()){
				   if(flagB){
					    flagB=false;
						con = super.getConection();
						if(null!=con){
							 for (Terminal  t : terminallist) {
								 if(null!=t){
									pstmt=con.prepareStatement(sql);
									pstmt.setString(1, t.getCnt_ip());
									pstmt.setString(2, t.getCnt_nowProgramName());
									pstmt.setString(3, t.getCnt_playstyle());
									pstmt.setInt(4, t.getCnt_islink());
									pstmt.setString(5, t.getCnt_playprojecttring());
									pstmt.setString(6, t.getClient_version());
									pstmt.setString(7, t.getCnt_mac());
									pstmt.executeUpdate();
									pstmt.close();
									Util.TERMINAL_OP_MAP.put(t.getCnt_mac(), t.toString());
								 }
							  }
						}else {
							logger.info("----------updateCntstatus-----2------���ݿ�����δ��ȡ����");
						}
				     }else {
					   logger.info("----------updateCntstatus------2-----�ϴ����ݸ���δ��ɣ��ȴ��´θ��£�");
				     }
				  }
			  }
			}catch(Exception e){
				flagB=true;
				e.printStackTrace();
				logger.info("���µ�ǰ�ն�����״̬����===updateCntstatus=="+e.getMessage());
				if(e.getMessage().indexOf("Error establishing socket")!=-1&&++execcountI>=15){//���ݿⲻ�ܽ����ﵽ10��������������
					execcountI=0;
					logger.error("�������ݿ�����10�κ���������===updateCntstatus==");
				    FirstStartServlet.restartXctService();
				}
			}finally{
				returnResources(con);
				flagB=true;
				if(!terminallist.isEmpty()){
				   terminallist.clear();
				   terminallist=null;
				}
			}
	}
	
	public  void  actionTerminal(Map<String,Terminal> terminals){ 
		try{
			 Terminal t=null; 
			 String nowtime=UtilDAO.getNowtime("H:m");
			
			 for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
				 if(entry!=null){
					 t=entry.getValue(); 
					 if(nowtime.equals(t.getCnt_kjtime())){
						// System.out.println("ʱ�����ִ������");
						 if("1".equals(t.getCnt_status())&&t.getCnt_islink()==3){
							// System.out.println("ֻ����Ͽ��ն�");
							 boolean bool=UtilDAO.pingServer(t.getCnt_ip(), 2000);  //
							 String mac=t.getCnt_mac();
							 if(!bool){
								String mac_="";
								for(int i=0;i<mac.length();i++){
									if(i!=0&&i%2==0){
										mac_+=":";
									}
									mac_+=mac.substring(i,i+1);
								}
								logger.info("�����նˡ�"+t.getCnt_name()+"��"+t.getCnt_ip()+"����");
								UtilDAO.actionTerminal(mac_);
							}
						 }
					 }
				 }
			 }
		}catch(Exception e){
			logger.error("���µ�ǰ�ն�����״̬����====="+e.getMessage());
			e.printStackTrace();
		} 
	}
	
	
	public boolean updateClientZuid(int fzuid, int cur_zuid){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		String sql="update xct_ipaddress set cnt_zuid=? where cnt_zuid=?";
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, fzuid);
			pstmt.setInt(2, cur_zuid);
			if(pstmt.executeUpdate()>0)
				return true;
			
		}catch(Exception e){
			logger.info(new StringBuffer().append("���µ�ǰ�����=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(pstmt,con);
		}
		return false;
	}
	
	public boolean updateDayDownLoad(String daydownloadflag,String macstring){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		String sql="update xct_ipaddress set is_day_download=? where cnt_mac=?";
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, daydownloadflag);
			pstmt.setString(2, macstring);
			if(pstmt.executeUpdate()>0)
				return true;
			
		}catch(Exception e){
			logger.error(new StringBuffer().append("���µ�ǰ�ն�����״̬����con��=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(pstmt,con);
		}
		return false;
	}
	
	public void updateCntstatus(Connection con,Map<String,Terminal> terminals){
		PreparedStatement pstmt = null;
		String sql="update xct_ipaddress set cnt_ip=?,cnt_nowProgramName=?,cnt_playstyle=?,cnt_islink=?,cnt_playprojecttring=? where cnt_mac=?";
		try{
			 Terminal t=null;
			 for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
				 if(entry!=null){
				 	t=entry.getValue();
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1, t.getCnt_ip());
					pstmt.setString(2, t.getCnt_nowProgramName());
					pstmt.setString(3, t.getCnt_playstyle());
					pstmt.setInt(4, t.getCnt_islink());
					pstmt.setString(5, t.getCnt_playprojecttring());
					pstmt.setString(6, t.getCnt_mac());
					pstmt.executeUpdate();
				 }
			  }
		}catch(Exception e){
			logger.error(new StringBuffer().append("���µ�ǰ�ն�����״̬����con��=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(pstmt);
		}
	}
	
	//ɾ��δ����ն�
	public boolean delNotAuditTerminalDAO(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		try{
			 String sql=new StringBuffer().append("delete from  xct_ipaddress  where  cnt_status='0' and ").append(str).toString();
			 pstmt = con.prepareStatement(sql);
			 int i=pstmt.executeUpdate();
			if(i>0)return true;
		}catch(Exception e){
			logger.error(new StringBuffer().append("ɾ��<<").append(str).append(">>������ն˳���=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(null,pstmt,con);
		}
		return false;
	}
	
	
	public List<Terminal> getALLTerminalDAO(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Terminal terminal=null;
		List<Terminal> list=null;
		try{
			 String sql="select * from xct_ipaddress "+str;
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			 list=new ArrayList<Terminal>();
			while(rs.next()){
				terminal= new Terminal();
				terminal.setCnt_name(rs.getString("cnt_name"));
				terminal.setCnt_ip(rs.getString("cnt_ip"));
				terminal.setCnt_mac(rs.getString("cnt_mac"));
				terminal.setCnt_playstyle(rs.getString("cnt_playstyle"));
				terminal.setCnt_islink(rs.getInt("cnt_islink"));
				terminal.setCnt_nowProgramName(rs.getString("cnt_nowProgramName"));
				terminal.setCnt_playprojecttring(rs.getString("cnt_playprojecttring"));
				list.add(terminal);
				}
			
		}catch(Exception e){
			logger.error("����<<"+str+">>��ȡ�����ն˳���====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	
	public List<Terminal> getALLTerminalDAO(Connection con,String str){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Terminal terminal=null;
		List<Terminal> list=null;
		try{
			 String sql="select * from xct_ipaddress "+str;
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			 list=new ArrayList<Terminal>();
			while(rs.next()){
				terminal= new Terminal();
				terminal.setCnt_name(rs.getString("cnt_name"));
				terminal.setCnt_ip(rs.getString("cnt_ip"));
				terminal.setCnt_mac(rs.getString("cnt_mac"));
				terminal.setCnt_playstyle(rs.getString("cnt_playstyle"));
				terminal.setCnt_islink(rs.getInt("cnt_islink"));
				terminal.setCnt_nowProgramName(rs.getString("cnt_nowProgramName"));
				terminal.setCnt_playprojecttring(rs.getString("cnt_playprojecttring"));
				String is_day_download=rs.getString("is_day_download");
				is_day_download=((null==is_day_download||is_day_download.equals("1"))?"1":"0");
				terminal.setIs_day_download(is_day_download);//�ж��Ƿ���԰�������
				list.add(terminal);
			}
			
		}catch(Exception e){
			logger.error("����<<"+str+">>��ȡ�����ն˳���====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return list;
	}
	public Map<String,Terminal> getALLTerminalMap(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Terminal terminal=null;
		Map<String,Terminal> map=null;
		try{
			String sql="select * from xct_ipaddress "+str;
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			 map=new HashMap<String, Terminal>();
			while(rs.next()){
				terminal= new Terminal();
				terminal.setCnt_name(rs.getString("cnt_name"));
				terminal.setCnt_ip(rs.getString("cnt_ip"));
				terminal.setCnt_mac(rs.getString("cnt_mac"));
				int cnt_link=rs.getInt("cnt_islink");
				if(cnt_link==3){
					terminal.setCnt_playstyle("CLOSE");
					terminal.setCnt_nowProgramName("��");
				}else{
					terminal.setCnt_playstyle(rs.getString("cnt_playstyle"));
					terminal.setCnt_nowProgramName(rs.getString("cnt_nowProgramName"));
				}
				terminal.setCnt_islink(cnt_link);
				terminal.setCnt_playprojecttring(rs.getString("cnt_playprojecttring"));
				terminal.setCnt_status(rs.getString("cnt_status"));
				terminal.setCnt_zuid(rs.getInt("cnt_zuid"));
				map.put(terminal.getCnt_mac(), terminal);
				}
		}catch(Exception e){
			logger.error("����<<"+str+">>��ȡ�����ն˳���====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return map;
	}	
	
	public boolean updateModuleTempByTemplateIdForAlphaField(){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		
		//Mysql
		String sql2="alter table xct_module_temp modify column alpha varchar(200)";
		//Sqlserver
//		String sql2="alter table xct_module_temp alter column alpha varchar(200)";
		try{
			if(null!=con){
				pstmt=con.prepareStatement(sql2);
				int i=-1;
				i=pstmt.executeUpdate();
				if(i>0){
					return true;
				}
			}
		}catch(Exception e){
			logger.error("�޸�alpha�ֶγ��ȳ���====="+e.getMessage());
			e.printStackTrace();
			return false;
		}finally{
			returnResources(pstmt,con);
		}
		return false;
	}	
	
	
	public ConcurrentHashMap<String,Terminal> getALLTerminalMapEnty(String str){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Terminal terminal=null;
		ConcurrentHashMap<String,Terminal> map=new ConcurrentHashMap<String, Terminal>();
		Connection con = super.getConection();
		
		try{
			if(null!=con){
				 String sql="select * from xct_ipaddress,xct_zu where zu_id=cnt_zuid "+str;
				 pstmt=con.prepareStatement(sql);
				 rs=pstmt.executeQuery();
				 while(rs.next()){
					terminal= new Terminal();
					terminal.setCnt_name(rs.getString("cnt_name"));
					terminal.setCnt_ip(rs.getString("cnt_ip"));
					terminal.setCnt_mac(rs.getString("cnt_mac"));
					int cnt_link=rs.getInt("cnt_islink");
					if(cnt_link==3){
						terminal.setCnt_playstyle("CLOSE");
						terminal.setCnt_nowProgramName("��");
					}else{
						terminal.setCnt_playstyle(rs.getString("cnt_playstyle"));
						terminal.setCnt_nowProgramName(rs.getString("cnt_nowProgramName"));
					}
					terminal.setCnt_kjtime(rs.getString("cnt_kjtime"));
					terminal.setCnt_islink(cnt_link);
					terminal.setCnt_playprojecttring(rs.getString("cnt_playprojecttring"));
					terminal.setClient_version(rs.getString("client_version"));
					
					terminal.setCnt_status(String.valueOf(rs.getInt("cnt_status")));
					
					terminal.setSend_type(System.getProperty("SENDTYPE"));
					
					String is_day_download=rs.getString("is_day_download");
					is_day_download=((null==is_day_download||is_day_download.equals("1"))?"1":"0");
					
					terminal.setIs_day_download(is_day_download);//�ж��Ƿ���԰�������
//					System.out.println("---------getALLTerminalMapEnty----------------->"+is_day_download);
					terminal.setCnt_status(rs.getString("cnt_status"));
					terminal.setCnt_zuid(rs.getInt("cnt_zuid"));
					terminal.setZu_name(rs.getString("zu_name"));
					map.put(terminal.getCnt_mac(), terminal);
				}
			}else {
				logger.info("��ȡ�����ն˳���,���ݿ�����δ��ȡ��------------->null");
			}
		}catch(Exception e){
			logger.error("����<<"+str+">>��ȡ�����ն˳���==getALLTerminalMapEnty==="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return map;
	}
	
	Map<String,Terminal> terminals;

	public List<Terminal> getTerminalListByMap(Map<Integer,Terminal> zumap,String left_cmd,String zuid){
		
//		System.out.println(zuid+"=========="+left_cmd);
		
		List<Terminal> list=new ArrayList<Terminal>();
		terminals=FirstStartServlet.terminalMap;
		//Map<Integer,Terminal> zumap=getZuListByUsername(username);
//		List<Terminal> zulist=getAllZu(" where zu_type=0  order by zu_id");
		
		Terminal cnt_zu = new Terminal();
		
		if(terminals==null)
			return null;
		
		Terminal terminal=null;
		if("ACTIVE".equals(left_cmd)){//���ߵ�
			int zuid_t=0;
			for (Map.Entry<Integer, Terminal> ter : zumap.entrySet()) {
				zuid_t=ter.getKey();
				cnt_zu=ter.getValue();
				if(cnt_zu.getZu_type()==1){
					 for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
						 terminal=entry.getValue();
						if("1".equals(terminal.getCnt_status())&&(terminal.getCnt_islink()==1 ||terminal.getCnt_islink()==2)
								 &&zuid_t==terminal.getCnt_zuid() 
								 &&!"CLOSE".equals(terminal.getCnt_playstyle()) &&!"NULL".equals(terminal.getCnt_nowProgramName().toUpperCase())){
							list.add(terminal);
						}
					}
				}
			}
		}else if("INACTIVE".equals(left_cmd)){//�Ͽ�
			int zuid_t=0;
			for (Map.Entry<Integer, Terminal> ter : zumap.entrySet()) {
				zuid_t=ter.getKey();
				cnt_zu=ter.getValue();
				if(cnt_zu.getZu_type()==1){
					 for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
						 terminal=entry.getValue();
						if("1".equals(terminal.getCnt_status())&&terminal.getCnt_islink()==3&&zuid_t==terminal.getCnt_zuid()){
							list.add(terminal);
						}
					}
				}
			}
		}else if("DORMANCY".equals(left_cmd)){//����
			int zuid_t=0;
			for (Map.Entry<Integer, Terminal> ter : zumap.entrySet()) {
				zuid_t=ter.getKey();
				cnt_zu=ter.getValue();
				if(cnt_zu.getZu_type()==1){
					 for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
						 terminal=entry.getValue();
						if("1".equals(terminal.getCnt_status())&& zuid_t==terminal.getCnt_zuid() 
								&& (terminal.getCnt_islink()==1 ||terminal.getCnt_islink()==2)
								&&"NULL".equals(terminal.getCnt_nowProgramName().toUpperCase()) && "CLOSE".equals(terminal.getCnt_playstyle())){
							list.add(terminal);
						}
					}
				}
			}
		}else if("ZU".equals(left_cmd)){

			int zuid_t=0;
			for (Map.Entry<Integer, Terminal> ter : zumap.entrySet()) {//�鼯��
				zuid_t=ter.getKey();
				cnt_zu=ter.getValue();
			    for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {//�ն˼���
					terminal=entry.getValue();
					if(cnt_zu.getZu_type()==1&&"1".equals(terminal.getCnt_status())){
						if(zuid.equals(terminal.getCnt_zuid()+"")&&zuid_t==terminal.getCnt_zuid()){//�ն˱�����ڵ���
							 setcoll.add(terminal);
						}
					}
				}
			}
			if(!"".equals(zuid))
			  zuRecursion(zumap,Integer.parseInt(zuid));//��Ը���͸���ĸ����ϵ���ն�
			list.addAll(setcoll);
			if(null!=setcoll&&setcoll.size()!=0)
			  setcoll.clear();
			
		}else if("SEARCHTER".equals(left_cmd)){
			int zuid_t=0;
			for (Map.Entry<Integer, Terminal> ter : zumap.entrySet()) {
				zuid_t=ter.getKey();
				cnt_zu=ter.getValue();
				if(cnt_zu.getZu_type()==1){
					for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
						 terminal=entry.getValue();
						if("1".equals(terminal.getCnt_status())
								&&(terminal.getCnt_name().indexOf(zuid)!=-1 || terminal.getCnt_ip().indexOf(zuid)!=-1)
								&&zuid_t==terminal.getCnt_zuid()){
							list.add(terminal);
						}
					}
				}
			}
		}else if("USER".equals(left_cmd)){
			    int zuid_t=0;
				for (Map.Entry<Integer, Terminal> ter : zumap.entrySet()) {
					zuid_t=ter.getKey();
					cnt_zu=ter.getValue();
					if(cnt_zu.getZu_type()==1){
						for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
							 terminal=entry.getValue();
							if("1".equals(terminal.getCnt_status())&&zuid_t==terminal.getCnt_zuid()){
//								list.add(terminal);
								setcoll.add(terminal);
							}
						 }
					}
				}
				if(!"".equals(zuid))
				   zuRecursion(zumap,Integer.parseInt(zuid));//��Ը���͸���ĸ����ϵ���ն�
				
				list.addAll(setcoll);
				if(null!=setcoll&&setcoll.size()!=0)
				  setcoll.clear();
		}else{//��ʾ�����ն˻���
			int zuid_t=0;
			for (Map.Entry<Integer, Terminal> ter : zumap.entrySet()) {
				zuid_t=ter.getKey();
				cnt_zu=ter.getValue();
				if(cnt_zu.getZu_type()==1){
					for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
						terminal=entry.getValue();
						if("1".equals(terminal.getCnt_status())&&zuid_t==terminal.getCnt_zuid()){
						  list.add(terminal);
						}
					}
				}
			}
		}
		return list;
	}
	
	Set<Terminal> setcoll=new HashSet<Terminal>();

	public Set<Terminal> zuRecursion(Map<Integer, Terminal> zumap,int zuid){//��ݹ�
		
		if(zuid!=1){//�������ʱ����Ӧ
			Terminal terminaltmp;
			Terminal terminal;
			for (Map.Entry<Integer, Terminal> ter : zumap.entrySet()) {//�鼯��
				terminaltmp=ter.getValue();
				if(zuid==terminaltmp.getZu_pth()){
					 for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {//�ն˼���
							terminal=entry.getValue();
							if(terminaltmp.getZu_id()==terminal.getCnt_zuid())
							  setcoll.add(terminal);
					 }
					 zuRecursion(zumap,terminaltmp.getZu_id());
				}
			}
		}
		return setcoll;
	}
	
	public List<Terminal> getSortList(List<Terminal> list,String sort){
		if("ASC".equals(sort)){
			if(list!=null&&!list.isEmpty()) Collections.sort(list,new TerminalSortASC()); 
		}else if("DESC".equals(sort)){
			if(list!=null&&!list.isEmpty())Collections.sort(list,new TerminalSortDESC()); 
		}
		return list;
	}
	
	public List<Terminal> getSortIP(List<Terminal> list){
			if(list!=null&&!list.isEmpty())
				Collections.sort(list,new TerminalSortIP()); 
		return list;
	}
	/**
	 * ����MAC��ַ����Ƿ�����ն�
	 * @param mac
	 * @return
	 */
	public synchronized int checkIsTerminal(String mac,String cnt_ip){
		
		if(FirstStartServlet.terminalMap==null||FirstStartServlet.terminalMap.isEmpty())
		  return 0;
		for (Map.Entry<String, Terminal> entry : FirstStartServlet.terminalMap.entrySet()) {
//			Terminal terminal=entry.getValue();
			if(mac.equals(entry.getKey())
					/*&&terminal.getCnt_status().equals("1")*/){
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * ��ѯδ����ն˵ĸ���
	 * @param mac
	 * @return
	 */
	public int getNOTAuditTer(){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		int num=0;
		try{
			String sql="select count(*) from xct_ipaddress where cnt_status=0";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			 }
		}catch(Exception e){
			logger.error("��ѯδ����ն˵ĸ�������====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return num;
	}
	public static int getTerminalNum(Connection con){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		int num=0;
		try{
			String sql="select count(*) from xct_ipaddress";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
		return num;
	}
	/**
	 * ��ѯ�ն˿������ػ�������ˣ����߸���
	 * @param str
	 * @return
	 */
	public String  getTerminalNumBystr(Map<Integer,Terminal> zumap){
		
		//=TerminalDAO.getZuListByUsername(username);
		Terminal cnt_zu = new Terminal();
		
		String num="0_0_0_0";   ////���Ӹ������Ͽ�����������˸��������߸���
		Map<String,Terminal> terminals=FirstStartServlet.terminalMap;
		if(terminals==null)return num;
		Terminal terminal=null;
		int link=0;
		int close=0;
		int dsh=0;
		int dormancy=0;
		int zuid_t=0;
		
		for (Map.Entry<Integer, Terminal> ter : zumap.entrySet()) {
			zuid_t=ter.getKey();
			cnt_zu=ter.getValue();
			if(cnt_zu.getZu_type()==1){
				
				for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
					 terminal=entry.getValue();
					 /////����
					if("1".equals(terminal.getCnt_status())&&zuid_t==terminal.getCnt_zuid()
							&&(terminal.getCnt_islink()==1 ||terminal.getCnt_islink()==2) 
							&& !"CLOSE".equals(terminal.getCnt_playstyle()) &&!"NULL".equals(terminal.getCnt_nowProgramName().toUpperCase())){
						
						link++;
					}
					 ////�Ͽ�
					if("1".equals(terminal.getCnt_status())&&zuid_t==terminal.getCnt_zuid()&&terminal.getCnt_islink()==3){
						close++;
					}
					////����
					if("1".equals(terminal.getCnt_status())&&zuid_t==terminal.getCnt_zuid() 
							&& (terminal.getCnt_islink()==1 ||terminal.getCnt_islink()==2) 
							&&"NULL".equals(terminal.getCnt_nowProgramName().toUpperCase()) && "CLOSE".equals(terminal.getCnt_playstyle())){
						
						dormancy++;
					}
					/////�����
					if("0".equals(terminal.getCnt_status())&&zuid_t==terminal.getCnt_zuid()){
						dsh++;
					}
				}
			}
		}
		num=link+"_"+close+"_"+dsh+"_"+dormancy;
		return num;
	}
	/**
	 * �õ����е��ն˺�����
	 */
	public List getALLTerminalAndZu(){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Terminal terminal=null;
		List<Terminal> list=null;
		try{
			String sql="select cnt_name,cnt_ip,cnt_mac,zu_id,zu_pth,zu_name from xct_ipaddress,xct_zu where zu_id=cnt_zuid";
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			list=new ArrayList<Terminal>();
			while(rs.next()){
				terminal= new Terminal();
				terminal.setCnt_name(rs.getString("cnt_name"));
				terminal.setCnt_ip(rs.getString("cnt_ip"));
				terminal.setCnt_mac(rs.getString("cnt_mac"));
				terminal.setZu_id(rs.getInt("zu_id"));
				terminal.setZu_pth(rs.getInt("zu_pth"));
				terminal.setZu_name(rs.getString("zu_name"));
				list.add(terminal);
				}
			
		}catch(Exception e){
			logger.error("�õ����е��ն˺���������====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
		
	}
	/**
	 * ���������õ��ն˵�������
	 */
	public List<Terminal> getAllZu(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Terminal terminal_zu=null;
		List<Terminal> list=null;
		try{
			String sql="select zu_id,zu_pth,zu_name,zu_username,is_share from xct_zu "+str;
			//System.out.println(sql);
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			list=new ArrayList<Terminal>();
			while(rs.next()){
				terminal_zu= new Terminal();
				terminal_zu.setZu_id(rs.getInt("zu_id"));
				terminal_zu.setZu_pth(rs.getInt("zu_pth"));
				terminal_zu.setZu_name(rs.getString("zu_name"));
				terminal_zu.setZu_username(rs.getString("zu_username"));
				terminal_zu.setIs_share(rs.getInt("is_share"));
				list.add(terminal_zu);
				}
			
		}catch(Exception e){
			logger.error("�õ��ն˵����������====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
		
	}
	public List<Terminal> getAllZu(Connection con,String str){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Terminal terminal_zu=null;
		List<Terminal> list=null;
		try{
			String sql="select zu_id,zu_pth,zu_name,zu_username from xct_zu "+str;
//			System.out.println(sql);
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			list=new ArrayList<Terminal>();
			while(rs.next()){
				terminal_zu= new Terminal();
				terminal_zu.setZu_id(rs.getInt("zu_id"));
				terminal_zu.setZu_pth(rs.getInt("zu_pth"));
				terminal_zu.setZu_name(rs.getString("zu_name"));
				terminal_zu.setZu_username(rs.getString("zu_username"));
				list.add(terminal_zu);
				}
			
		}catch(Exception e){
			logger.error("�õ��ն˵����������====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return list;
		
	}
	/**
	 * ���������õ��ն˵�������
	 */
	public String  getAllZuID(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String allzuid="|";
		try{
			String sql="select zu_id from xct_zu "+str;
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				allzuid+=rs.getInt("zu_id")+"|";
				}
			
		}catch(Exception e){
			logger.error("�õ��ն˵����������====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return allzuid;
		
	}
	/**
	 * �¼������
	 */
	public int newzu(String zu_pth,String zu_name,String zu_type,String zu_username){
		
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql="select zu_id from xct_zu where zu_pth=? and zu_name=? and zu_type=? and zu_username=?";
		String sql1="insert into xct_zu (zu_pth,zu_name,zu_type,zu_username,is_share) values (?,?,?,?,0)";
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, zu_pth);
			pstmt.setString(2, zu_name);
			pstmt.setString(3, zu_type);
			pstmt.setString(4, zu_username);
			rs=pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt("zu_id");
			}else{
				pstmt=con.prepareStatement(sql1);
				pstmt.setString(1, zu_pth);
				pstmt.setString(2, zu_name);
				pstmt.setString(3, zu_type);
				pstmt.setString(4, zu_username);
				pstmt.executeUpdate();
				
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, zu_pth);
				pstmt.setString(2, zu_name);
				pstmt.setString(3, zu_type);
				pstmt.setString(4, zu_username);
				rs=pstmt.executeQuery();
				if(rs.next()){
					return rs.getInt("zu_id");
				}
				
			}
		}catch(Exception e){
			logger.error("�¼������====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return 1;
	}
	/**
	 * �����û����ƻ�ȡ�û���Ĭ����id
	 */
	public Terminal getZuByUsername(Connection conn,String str){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Terminal  terminal_zu= new Terminal();
		try{
			String sql="select zu_id,zu_pth,zu_name from xct_zu "+str;
			 pstmt=conn.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				terminal_zu.setZu_id(rs.getInt("zu_id"));
				terminal_zu.setZu_pth(rs.getInt("zu_pth"));
				terminal_zu.setZu_name(rs.getString("zu_name"));
				}
			return terminal_zu;
		}catch(Exception e){
			logger.error("�õ��ն˵����������====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return terminal_zu;
		
	}
	
	/**
	 * �����û����ƻ�ȡ�û���Ĭ����id
	 */
	public Terminal getZuByUsername(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Terminal  terminal_zu= new Terminal();
		try{
			String sql="select zu_id,zu_pth,zu_name from xct_zu "+str;
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();//�г����쳣������������ varchar ת��Ϊ bigint ʱ����
			if(rs.next()){
				terminal_zu.setZu_id(rs.getInt("zu_id"));
				terminal_zu.setZu_pth(rs.getInt("zu_pth"));
				terminal_zu.setZu_name(rs.getString("zu_name"));
				}
			return terminal_zu;
		}catch(Exception e){
			logger.error(new StringBuffer().append("�õ��ն˵����������==").append(str).append("===").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return terminal_zu;
		
	}
	/**
	 * ��������ID��ȡ��������и��� �����ַ�����ʽ===��>����>����
	 * @param map Map<Integer, Terminal>
	 * @param id
	 * @return
	 */
	public static String getzu_pathByzuID(Map<Integer, Terminal> map,int zu_id){
		zu_path="";
		new TerminalDAO().aaa(map, zu_id);
		return zu_path;
		
	}
	public void aaa(Map<Integer, Terminal> map,int zu_id){
		zu_path="";
		for (Map.Entry<Integer, Terminal> entry : map.entrySet()) {
			Terminal t= (Terminal)entry.getValue();
			if(zu_id==t.getZu_id()){
				aaa(map,t.getZu_pth());
				zu_path+=t.getZu_name()+">";
			}
		}
		
	}
	
	//////////////////
	
	public static void getsubBypth(List list,int zu_id){
		for(int i=0;i<list.size();i++){
			Terminal t= (Terminal)list.get(i);
			if(zu_id==t.getZu_pth()){
				//id=t.getZu_id();
				//getsubBypth(list,id);
				zu_path+=","+t.getZu_name();
			}
		}
	}
	public static String getzu_pathByzuID1(List list,int zu_id){
		zu_path="";
		TerminalDAO.getsubBypth(list, zu_id);
		return zu_path;
		
	}
	/**
	 * ��������ID��ȡ��������и��� �����ַ�����ʽ===��>����>����
	 * @param list
	 * @param id
	 * @return
	 */
	public static String getzu_pathByzuID(List list,int id){  
		zu_path="";
		new TerminalDAO().aaa(list, id);
		return zu_path;
		
	}public void aaa(List list,int id){
		zu_path="��";
		for(int i=0;i<list.size();i++){
			Terminal t= (Terminal)list.get(i);
			if(id==t.getZu_id()){
				aaa(list,t.getZu_pth());
				zu_path+=">"+t.getZu_name();
			}
		}
		
	}
	public static void main(String [] args){
		TerminalDAO terminaldao=new TerminalDAO();
		List list=terminaldao.getALLTerminalDAO("");
			
		for(int i=0;i<list.size();i++){
			Terminal t= (Terminal)list.get(i);
			Util.copyFile("c://00237D4C9184.xml", "C://aaa//"+t.getCnt_mac()+".xml");
			
			
		}
		
		
		//terminaldao.getzu_pathByzuID(list,13);
		//System.out.println(zu_path);
		/*Map<Integer,Terminal> map=TerminalDAO.getZuListByUsername("999");
		for (Map.Entry<Integer, Terminal> entry : map.entrySet()) {
			System.out.println(entry.getKey());
		}*/
	}

	/**
	 * �����û�����ȡ�û��������ն���  ����Map<Integer,Terminal> 
	 * @param username
	 * @return
	 */
	public static Map<Integer,Terminal> getZuListByUsername(String username){  
		
		TerminalDAO terminaldao=new TerminalDAO();
		zu_Map=new HashMap<Integer, Terminal>();
		
		Terminal terminal=new Terminal();
		terminal.setZu_id(1);
		terminal.setZu_name("��");
		terminal.setZu_pth(-1);
		terminal.setZu_type(0);
		terminal.setZu_username(username);
		
		zu_Map.put(1, terminal);
		
		List<Terminal> list=terminaldao.getAllZu(" where zu_type=0 or zu_type=99 order by zu_id");
		Terminal t= new Terminal();
		
		for(int i=0;i<list.size();i++){
			t=list.get(i);
			if(t.getZu_username().indexOf(username+"||")!=-1){
				int zu_ueserid=t.getZu_id();
				terminaldao.ddd(list,zu_ueserid);//�ݹ�
				terminaldao.eee(list,zu_ueserid);//�ݹ�
			}
		}
		for(int i=0;i<list.size();i++){
			t=list.get(i);
			if(t.getZu_username().indexOf(username+"||")!=-1){
				int zu_ueserid=t.getZu_id();
				Terminal tt = zu_Map.get(zu_ueserid);
				if(tt!=null){
					tt.setZu_type(1);
					zu_Map.put(zu_ueserid, tt);
				}
			}
		}
		return zu_Map;
	}
	/**
	 * �����û�����ȡ�û��������ն���  ����Map<Integer,Terminal> 
	 * @param username
	 * @return
	 */
	public static Map<Integer,Terminal> getZuListByUsername1(String type,String username){  
		
		TerminalDAO terminaldao=new TerminalDAO();
		zu_Map=new HashMap<Integer, Terminal>();
		
		Terminal terminal=new Terminal();
		terminal.setZu_id(1);
		terminal.setZu_name("��");
		terminal.setZu_pth(-1);
		terminal.setZu_type(0);
		terminal.setZu_username(username);
		
		zu_Map.put(1, terminal);
		
		List<Terminal> list=terminaldao.getAllZu(" where zu_type="+type+" order by zu_id");
		Terminal t= new Terminal();
		
		for(int i=0;i<list.size();i++){
			t=list.get(i);
			if(t.getZu_username().indexOf(username+"||")!=-1){
				int zu_ueserid=t.getZu_id();
				terminaldao.ddd(list,zu_ueserid);//�ݹ�
				terminaldao.eee(list,zu_ueserid);//�ݹ�
			}
		}
		for(int i=0;i<list.size();i++){
			t=list.get(i);
			if(t.getZu_username().indexOf(username+"||")!=-1){
				int zu_ueserid=t.getZu_id();
				Terminal tt = zu_Map.get(zu_ueserid);
				tt.setZu_type(1);
				zu_Map.put(zu_ueserid, tt);
			}
		}
		return zu_Map;
	}
	public void ddd(List<Terminal> list,int zuid){
		
		Terminal t1=new Terminal();
		for(int j=0;j<list.size();j++){
			t1= list.get(j);
			if(zuid==t1.getZu_id()){
				ddd(list,t1.getZu_pth());
				t1.setZu_type(0);
				zu_Map.put(t1.getZu_id(), t1);
			}
		}
	}
	
	public void eee(List<Terminal> list,int zuid){
		Terminal t=new Terminal();
		for(int i=0;i<list.size();i++){
			t= list.get(i);
			if(zuid==t.getZu_pth()){
				eee(list,t.getZu_id());
				t.setZu_type(1);
				zu_Map.put(t.getZu_id(), t);
			}
		}
	}
	
	/**
	 * ���ݸ���ID��ȡ������������鷵��List<Terminal>��
	 * @param list �����鼯��
	 * @param zu_pth  ����
	 * @return
	 */
	public static List<Terminal> getzu_subListByzu_pth(List<Terminal> list,int zu_pth){  
		zu_subList= new ArrayList<Terminal>();
		new TerminalDAO().ccc(list, zu_pth);
		
		for(int i=0;i<list.size();i++){
			Terminal t= (Terminal)list.get(i);
			if(zu_pth==t.getZu_id()){
				zu_subList.add(t);
				break;
			}
		}
		
		return zu_subList;
	}
	public void ccc(List<Terminal> list,int zu_pth){
		Terminal t=new Terminal();
		for(int i=0;i<list.size();i++){
			t= (Terminal)list.get(i);
			if(zu_pth==t.getZu_pth()){
				zu_subList.add(t);
				ccc(list,t.getZu_id());
			}
		}
		
	}
	/**
	 * ��������ID��ȡ��������и��鷵��List<Terminal>
	 * @param list
	 * @param zuid
	 * @return
	 */
	public static List<Terminal> getzu_pathListByzuid(List<Terminal> list,int zuid){  
		zu_pathList= new ArrayList<Terminal>();
		new TerminalDAO().bbb(list, zuid);
		return zu_pathList;
		
	}
	public void bbb(List<Terminal> list,int id){
		for(int i=0;i<list.size();i++){
			Terminal t= (Terminal)list.get(i);
			if(id==t.getZu_id()){
				bbb(list,t.getZu_pth());
				zu_pathList.add(t);
			}
		}
		
	}
	/**
	 * ����һ�������õ�һ���ն˵���ϸ��Ϣ
	 */
	public Terminal getTerminalBystr(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Terminal terminal=null;
		try{
			String sql="select * from xct_ipaddress, xct_zu where zu_id=cnt_zuid " +str;
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				terminal= new Terminal();
				terminal.setCnt_name(rs.getString("cnt_name"));
				terminal.setCnt_ip(rs.getString("cnt_ip"));
				terminal.setCnt_port(rs.getString("cnt_port"));
				terminal.setCnt_mac(rs.getString("cnt_mac"));
				terminal.setCnt_miaoshu(rs.getString("cnt_miaoshu"));
				terminal.setCnt_playstyle(rs.getString("cnt_playstyle"));
				terminal.setCnt_kjtime(rs.getString("cnt_kjtime"));
				terminal.setCnt_gjtime(rs.getString("cnt_gjtime"));
				terminal.setCnt_downtime(rs.getString("cnt_downtime"));
				terminal.setCnt_addtime(rs.getString("cnt_addtime"));
				terminal.setCnt_islink(rs.getInt("cnt_islink"));
				terminal.setCnt_username(rs.getString("cnt_username"));
				terminal.setCnt_nowProgramName(rs.getString("cnt_nowProgramName"));
				terminal.setCnt_playprojecttring(rs.getString("cnt_playprojecttring"));
				terminal.setZu_id(rs.getInt("zu_id"));
				terminal.setZu_name(rs.getString("zu_name"));
			}
		}catch(Exception e){
			logger.error("����һ�������õ�һ���ն˵���ϸ��Ϣ����====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return terminal;
	}
	/**
	 * ����һ�������õ�һ���ն˵���ϸ��Ϣ
	 */
	public Terminal getTerminalBystr1(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Terminal terminal=null;
		try{
			String sql="select * from xct_ipaddress " +str;
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				terminal= new Terminal();
				terminal.setCnt_name(rs.getString("cnt_name"));
				terminal.setCnt_ip(rs.getString("cnt_ip"));
				terminal.setCnt_port(rs.getString("cnt_port"));
				terminal.setCnt_mac(rs.getString("cnt_mac"));
				terminal.setCnt_miaoshu(rs.getString("cnt_miaoshu"));
				terminal.setCnt_playstyle(rs.getString("cnt_playstyle"));
				terminal.setCnt_kjtime(rs.getString("cnt_kjtime"));
				terminal.setCnt_gjtime(rs.getString("cnt_gjtime"));
				terminal.setCnt_downtime(rs.getString("cnt_downtime"));
				terminal.setCnt_addtime(rs.getString("cnt_addtime"));
				terminal.setCnt_islink(rs.getInt("cnt_islink"));
				terminal.setCnt_username(rs.getString("cnt_username"));
				terminal.setCnt_nowProgramName(rs.getString("cnt_nowProgramName"));
				
				String is_day_download=rs.getString("is_day_download");
				is_day_download=((null==is_day_download||is_day_download.equals("1"))?"1":"0");
				terminal.setIs_day_download(is_day_download);
			}
		}catch(Exception e){
			logger.error("����һ�������õ�һ���ն˵���ϸ��Ϣ����====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return terminal;
	}
	
	public void updateZuUserName(String zuid,String username){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		String sql="update xct_zu set zu_username=zu_username+'"+username+"||' where zu_id=?";
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, zuid);
			pstmt.executeUpdate();
		}catch(Exception e){
			logger.error("�����������û�����====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(pstmt,con);
		}
	}
	
	
	public void clearUserName(String username){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		String sql1="update xct_zu set zu_username=replace(zu_username,'||"+username+"', '')  where  zu_type=0 and zu_username like '%"+username+"%'";
		try{
			pstmt=con.prepareStatement(sql1);
			pstmt.executeUpdate();
		}catch(Exception e){
			logger.error("�����������û�����====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(pstmt,con);
		}
	}
	public void clearProgreamUserName(String username){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		String sql1="update xct_zu set zu_username=replace(zu_username,'||"+username+"', '')  where  zu_type=2 and zu_username like '%"+username+"%'";
		try{
			pstmt=con.prepareStatement(sql1);
			pstmt.executeUpdate();
		}catch(Exception e){
			logger.error("�����������û�����====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(pstmt,con);
		}
	}
	/**
	 * ����һ��MAC��ַ��ȡ�ն�����
	 * @param ip
	 * @return
	 */
	public static String getTerminalNameByMac(String mac){
//		System.out.println("mac==="+mac);
		String cnt_name="";
		Terminal t=FirstStartServlet.terminalMap.get(mac);
		if(t!=null){
			cnt_name=t.getCnt_name();
		}
		return cnt_name;
	}

	public static String getTerminalNameByIp(String ip){
		String cnt_name="";
		for (Map.Entry<String, Terminal> ter : FirstStartServlet.terminalMap.entrySet()) {
			Terminal t=ter.getValue();
			if(t!=null&&ip.equals(t.getCnt_ip())){
				cnt_name=t.getCnt_name();
				break;
			}
		}
		return cnt_name;
	}
	
	public static String getTerminalNameByIp(String ip,String mac){
		String cnt_name="";
		for (Map.Entry<String, Terminal> ter : FirstStartServlet.terminalMap.entrySet()) {
			Terminal t=ter.getValue();
			if(null!=t&&ip.equals(t.getCnt_ip())
					&&null!=mac&&mac.equals(t.getCnt_mac())){
				cnt_name=t.getCnt_name();
				break;
			}
		}
		return cnt_name;
	}
	
	public static String getTerminalNameByMAC(String ipmaccmd){//ipmaccmd=ip_mac_cmd
//		logger.info("----ipmaccmd----getTerminalNameByMAC-----"+ipmaccmd);
		String cnt_name="";
		String []ipmacarr=ipmaccmd.split("_");
		if(ipmacarr.length>=2) {
			String mac=ipmacarr[1];
			Terminal t=FirstStartServlet.terminalMap.get(mac);
			if(t!=null){
				cnt_name=t.getCnt_name();
			}
		}
		return cnt_name;
		
//		String cnt_name="";
//		for (Map.Entry<String, Terminal> ter : FirstStartServlet.terminalMap.entrySet()) {
//			Terminal t=ter.getValue();
//			if(null!=mac&&mac.equals(t.getCnt_mac())){
//				cnt_name=t.getCnt_name();
//				break;
//			}
//		}
//		return cnt_name;
	}
	/**
	 * ���ݶ��IP��ַ��ȡ�ն�����  
	 * @param ips �ԡ�#���ָ�
	 * @return
	 */
	public static String getTerminalNameByIps(String ips){
		String cnt_name="";
		String ip_array[]=ips.split("#");
		for (Map.Entry<String, Terminal> ter : FirstStartServlet.terminalMap.entrySet()) {
			Terminal t=ter.getValue();
			for(int i=0;i<ip_array.length;i++){
				if(t!=null&&ip_array[i].equals(t.getCnt_ip())){
					cnt_name+=","+t.getCnt_name();
				}
			}
		}
		return cnt_name;
	}
}
