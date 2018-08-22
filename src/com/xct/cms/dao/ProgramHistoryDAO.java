package com.xct.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.ProgramHistory;
import com.xct.cms.domin.Terminal;

public class ProgramHistoryDAO extends DBConnection {

	Logger logger = Logger.getLogger(TerminalDAO.class);
	
	public List<ProgramHistory> getSomeProgramListMenu(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		ProgramHistory program=null;
		List <ProgramHistory>list=null;
		try{
			String sql=new StringBuffer().append("select distinct program_JMurl,program_Name,program_SetDate,program_EndDate,program_ISloop,program_PlaySecond from xct_JMhistory ").append(str).toString();
//			logger.error(new StringBuffer().append("getSomeProgramListMenu-----> "+sql); 
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			list=new ArrayList<ProgramHistory>();
			while(rs.next()){
				program= new ProgramHistory();
				program.setProgram_JMurl(rs.getString("program_JMurl"));
				program.setProgram_Name(rs.getString("program_Name"));
				program.setProgram_SetDate(rs.getString("program_SetDate").replace(".0", ""));
				program.setProgram_EndDate(rs.getString("program_EndDate").replace(".0", ""));
				int isloop=rs.getInt("program_ISloop");
				program.setProgram_ISloop(isloop);
				//program.setIsforover(isloop==3?"y":"n");
				program.setProgram_PlaySecond(rs.getInt("program_PlaySecond"));
				program.setProject_url_datetime(program.getProgram_JMurl()+program.getProgram_SetDate().replace(":", "").replace(".0", "").replace(" ", ""));
				//if(isloop!=1){//点播不放入左边菜单
				  list.add(program);
			    //}
		   }
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取所有节目出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	
	public List<ProgramHistory> getSomeProgramList(String str){
		
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		ProgramHistory program=null;
		List <ProgramHistory>list=null;
		try{
			String sql="select * from xct_JMhistory "+str;
//			System.out.println("getSomeProgramList-----> "+sql);
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			list=new ArrayList<ProgramHistory>();
			while(rs.next()){
					program= new ProgramHistory();
					program.setProgram_JMurl(rs.getString("program_JMurl"));
					program.setProgram_Name(rs.getString("program_Name"));
					program.setProgram_SetDate(rs.getString("program_SetDate").replace(".0", ""));
					program.setProgram_EndDate(rs.getString("program_EndDate").replace(".0", ""));
					int isloop=rs.getInt("program_ISloop");
					program.setProgram_ISloop(isloop);
					//program.setIsforover(isloop==3?"y":"n");
					program.setProgram_PlaySecond(rs.getInt("program_PlaySecond"));
					program.setProject_url_datetime(program.getProgram_JMurl()+program.getProgram_SetDate().replace(":", "").replace(".0", "").replace(" ", ""));
					//if(isloop!=1){//点播不放入左边菜单
					  list.add(program);
				    //}
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取所有节目出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	
	
	/**
	 * 根据条件查询节目
	 * @param str
	 * @return
	 */
	public List<ProgramHistory> getALLProgram(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		ProgramHistory program=null;
		List <ProgramHistory>list=null;
		try{
			String sql="select * from xct_JMhistory "+str;
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			list=new ArrayList<ProgramHistory>();
			while(rs.next()){
				program= new ProgramHistory();
				program.setProgram_JMid(rs.getInt("program_JMid"));
				program.setProgram_JMurl(rs.getString("program_JMurl"));
				program.setProgram_Name(rs.getString("program_Name"));
				program.setProgram_SetDateTime(rs.getString("program_SetDateTime"));
				program.setProgram_Sethour(rs.getInt("program_Sethour"));
				program.setProgram_Setminute(rs.getInt("program_Setminute"));
				program.setProgram_SetDate(rs.getString("program_SetDate").replace(".0", ""));
				program.setProgram_EndDate(rs.getString("program_EndDate").replace(".0", ""));
				program.setProgram_EndDateTime(rs.getString("program_EndDateTime"));
				program.setProgram_Endhour(rs.getInt("program_Endhour"));
				program.setProgram_Endminute(rs.getInt("program_Endminute"));
				program.setProgram_ISloop(rs.getInt("program_ISloop"));
				program.setProgram_PlaySecond(rs.getInt("program_PlaySecond"));
				program.setProgram_lr(rs.getString("program_lr"));
				program.setProgram_lx(rs.getString("program_lx"));
				program.setProgram_playcount(rs.getInt("program_playcount"));
				program.setProgram_ggs(rs.getString("program_ggs"));
				program.setProgram_ip(rs.getString("program_ip"));
				program.setProgram_delid(rs.getString("program_delid"));
				program.setProgram_issend(rs.getInt("program_issend"));
				program.setProgram_downtime(rs.getString("program_downtime"));
				list.add(program);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取所有节目出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	/**
	 * 根据条件查询历史节目单的时间列表
	 * @param str
	 * @return
	 */
	public List<ProgramHistory> getALLProgramTime(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		ProgramHistory program=null;
		List <ProgramHistory>list=null;
		try{
			//Mysql
			String sql=new StringBuffer().append("select program_JMurl,program_Name,program_ISloop,date_format(program_SetDate, '%Y-%m-%d') as d,  date_format(program_SetDate, '%H:%i:%s') as d1, date_format(program_EndDate, '%H:%i:%s') as d2,program_ip,program_delid from xct_JMhistory ").append(str).toString();

			//SQLServer
			//String sql=new StringBuffer().append("select program_JMurl,program_Name,program_ISloop,CONVERT(varchar(12), program_SetDateTime, 111) as d,CONVERT(varchar(12), program_SetDate, 108) as d1,CONVERT(varchar(12), program_EndDate, 108) as d2,program_ip,program_delid from xct_JMhistory ").append(str).toString();
			
			pstmt=con.prepareStatement(sql);
//			System.out.println("===ProgramHistoryDAO.getALLProgramTime===发送节目时====2===="+sql);
			rs=pstmt.executeQuery();
			list=new ArrayList<ProgramHistory>();
			//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			while(rs.next()){
				program= new ProgramHistory();
				program.setProgram_JMurl(rs.getString("program_JMurl"));
				program.setProgram_Name(rs.getString("program_Name"));
				program.setProgram_ISloop(rs.getInt("program_ISloop"));
				program.setProgram_SetDateTime(rs.getString("d").replace("/", "-"));
				program.setProgram_SetDate(rs.getString("d1"));
				program.setProgram_EndDate(rs.getString("d2"));
				program.setProgram_ip(rs.getString("program_ip"));
				program.setProgram_delid(rs.getString("program_delid"));
				list.add(program);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取所有节目出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	/**
	 * 根据终端IP地址，节目URL 验证节目单时间是否有冲突
	 * @param iparray   终端IP地址数组
	 * @param programefile  节目URL数组
	 * @return
	 */
	public String checkProgramMenu(String []iparray,String [] programefile){
		
		String str="";
		String result="";
		for(int i=0;i<iparray.length;i++){
			if(!"".equals(iparray[i])){
			   str+=iparray[i].split("_")[1]+",";
			}
		}
		str=(str.equals(","))?str.substring(0,str.length()-1):str;
		
		//Mysql
		List<ProgramHistory> history_list= getALLProgramTime(new StringBuffer().append(" where LOCATE(program_delid,'").append(str).append("')<>0  and   date_format(program_SetDate, '%Y-%m-%d') >= date_format(now(), '%Y-%m-%d') ").toString());
		//SQLServer
//		List<ProgramHistory> history_list= getALLProgramTime(new StringBuffer().append(" where CHARINDEX (program_delid,'").append(str).append("')<>0  and   CONVERT(varchar(12), program_SetDate, 111) >= CONVERT(varchar(12), GETDATE(), 111) ").toString());
//=====================================================================
		//		List<ProgramHistory> history_list= getALLProgramTime(" where CHARINDEX (Program_ip,'"+str+"')<>0  and   CONVERT(varchar(12), Program_SetDate, 111) >= CONVERT(varchar(12), GETDATE(), 111) ");
//////		数据库选中终端今天以后的所有节目单   ^
		ProgramDAO prodap= new ProgramDAO();
		List<ProgramHistory> jmMenu_list= prodap.getProgramMenuByjmurl(iparray,programefile);
		//System.out.println(jmMenu_list.size());
		for(int i=0;i<history_list.size();i++){
			ProgramHistory oldMenu=history_list.get(i);
			for(int j=0;j<jmMenu_list.size();j++){
				ProgramHistory newMenu=jmMenu_list.get(j);
				//System.out.println("oldMenu.getProgram_ip().equals(newMenu.getProgram_ip()="+oldMenu.getProgram_ip().equals(newMenu.getProgram_ip()));
				if(oldMenu.getProgram_ip().equals(newMenu.getProgram_ip())){
					//System.out.println("oldMenu.getProgram_SetDateTime().equals(newMenu.getProgram_SetDateTime()"+oldMenu.getProgram_SetDateTime().equals(newMenu.getProgram_SetDateTime()));
					if(oldMenu.getProgram_SetDateTime().equals(newMenu.getProgram_SetDateTime())){
						if((oldMenu.getProgram_ISloop()==2||oldMenu.getProgram_ISloop()==1)&&(newMenu.getProgram_ISloop()==2||newMenu.getProgram_ISloop()==1)){
							//System.out.println("111"+(newMenu.getProgram_SetDate().compareTo(oldMenu.getProgram_EndDate())>=0 && newMenu.getProgram_EndDate().compareTo(oldMenu.getProgram_EndDate())>=0));
						//	System.out.println("222"+(newMenu.getProgram_SetDate().compareTo(oldMenu.getProgram_SetDate())<=0 && newMenu.getProgram_EndDate().compareTo(oldMenu.getProgram_SetDate())<=0));
							
							if((newMenu.getProgram_SetDate().compareTo(oldMenu.getProgram_EndDate())>=0 && newMenu.getProgram_EndDate().compareTo(oldMenu.getProgram_EndDate())>=0) ||
								(newMenu.getProgram_SetDate().compareTo(oldMenu.getProgram_SetDate())<=0 && newMenu.getProgram_EndDate().compareTo(oldMenu.getProgram_SetDate())<=0)){
							}else{
								if(result.indexOf(oldMenu.getProgram_ip())==-1){
									result+=oldMenu.getProgram_ip()+"!";
								}
							}
						}
					}
				}
			}
		}
//		System.out.println("===checkProgramMenu===发送节目时====2========= from “selectclientIP.jsp”=="+result);
		return result;
	}
	
	public String checkProgramMenu2(String []iparray,String programefile){
		Connection conn= new DBConnection().getConection();
		String result="";
		for(int i=0;i<iparray.length;i++){
			if(!"".equals(iparray[i])){
				String [] macip=iparray[i].split("_");
				if(getCheckDBProgram(conn,new StringBuffer().append(" where program_ip='").append(macip[0]).append("'  and program_delid='").append(macip[1]).append("' and program_JMurl='").append(programefile).append("' and program_ISloop=1 ").toString())){
					Terminal t=new TerminalDAO().getTerminalBystr(new StringBuffer().append(" and cnt_ip= '").append(macip[0]).append("' and cnt_mac='").append(macip[1]).append("'").toString());
					result=new StringBuffer().append("【终端").append(t.getCnt_name()).append("】已存在该插播节目！").toString();
					break;
				}
			}
		}
		return result;
		
	}////验证点播节目
	public boolean  getCheckDBProgram(Connection con ,String str){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		boolean bool=false;
		try{
			String sql=new StringBuffer().append("select count(*) from xct_JMhistory ").append(str).toString();
			//System.out.println(sql);
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				if(rs.getInt(1)>0){
					bool=true;
				}
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取所有节目出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return bool;
	}
	/**
	 * 根据终端IP地址，节目URL 查看冲突的节目单列表
	 * @param iparray   终端IP地址数组
	 * @param programefile  节目URL数组
	 * @return
	 */
	public List<ProgramHistory> showErrorProgramMenu(String []iparray,String programefile[]){
		String str="";
		for(int i=0;i<iparray.length;i++){
			str+=iparray[i].split("_")[1]+","; //MAC地址
		}
		List<ProgramHistory>  plist= new ArrayList<ProgramHistory>();
		//Mysql
		List<ProgramHistory> history_list= getALLProgramTime(new StringBuffer().append(" where LOCATE(program_delid,'").append(str).append("')<>0  and  date_format(program_SetDate, '%Y-%m-%d') >= date_format(now(), '%Y-%m-%d') ").toString());
		//SQLServer
		//List<ProgramHistory> history_list= getALLProgramTime(new StringBuffer().append(" where CHARINDEX (program_delid,'").append(str).append("')<>0  and  CONVERT(varchar(12), program_SetDate, 111) >= CONVERT(varchar(12), GETDATE(), 111) ").toString());
//==========================================================================
		
		//		List<ProgramHistory> history_list= programHistorydao.getALLProgramTime(" where CHARINDEX (Program_ip,'"+str+"')<>0  and  CONVERT(varchar(12), Program_SetDate, 111) >= CONVERT(varchar(12), GETDATE(), 111) ");
//////		数据库选中终端今天以后的所有节目单   ^
		ProgramDAO prodap= new ProgramDAO();
		List<ProgramHistory> jmMenu_list= prodap.getProgramMenuByjmurl(iparray,programefile);
		for(int i=0;i<history_list.size();i++){
			ProgramHistory oldMenu=history_list.get(i);
			for(int j=0;j<jmMenu_list.size();j++){
				ProgramHistory newMenu=jmMenu_list.get(j);
			
				if(oldMenu.getProgram_ip().equals(newMenu.getProgram_ip())){
					if(oldMenu.getProgram_SetDateTime().equals(newMenu.getProgram_SetDateTime())){
						if((oldMenu.getProgram_ISloop()==2||oldMenu.getProgram_ISloop()==1)&&(newMenu.getProgram_ISloop()==2||newMenu.getProgram_ISloop()==1)){
							if((newMenu.getProgram_SetDate().compareTo(oldMenu.getProgram_EndDate())>=0 && newMenu.getProgram_EndDate().compareTo(oldMenu.getProgram_EndDate())>=0) ||
								(newMenu.getProgram_SetDate().compareTo(oldMenu.getProgram_SetDate())<=0 && newMenu.getProgram_EndDate().compareTo(oldMenu.getProgram_SetDate())<=0)){
							}else{
								plist.add(oldMenu);
							}
						}
					}
				}
			}
		}
		return plist;
	}
	
	/**
	 * 查询发送失败节目列表
	 * @param str
	 * @return
	 */
	public List<ProgramHistory> getSendErrorProgram(String username){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		ProgramHistory program=null;
		List <ProgramHistory>list=null;
		try{
			String sql=new StringBuffer().append("select distinct program_JMurl, program_Name,program_SetDateTime,program_SetDate,program_EndDate,program_EndDateTime,program_ISloop,program_PlaySecond,program_senduser from xct_JMhistory where program_issend in(1,3) and program_senduser='").append(username).append("'").toString();
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			list=new ArrayList<ProgramHistory>();
			while(rs.next()){
				program= new ProgramHistory();
				program.setProgram_JMurl(rs.getString("program_JMurl"));
				program.setProgram_Name(rs.getString("program_Name"));
				program.setProgram_SetDateTime(rs.getString("program_SetDateTime"));
				program.setProgram_SetDate(rs.getString("program_SetDate").replace(".0", ""));
				program.setProgram_EndDate(rs.getString("program_EndDate").replace(".0", ""));
				program.setProgram_EndDateTime(rs.getString("program_EndDateTime"));
				program.setProgram_ISloop(rs.getInt("program_ISloop"));
				program.setProgram_PlaySecond(rs.getInt("program_PlaySecond"));
				program.setProgram_senduser(rs.getString("program_senduser"));
				list.add(program);
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("查询发送失败节目列表出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	
	public boolean deleteJMhistory(String str) {
		boolean bool=false;
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		try {
			String sql = new  StringBuffer().append("delete from xct_JMhistory " ).append( str).toString();
			//System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			if (pstmt.executeUpdate() == 1)
				bool=true;
		} catch (SQLException e) {
			logger.error(new StringBuffer().append("根据").append(str).append("删除xct_JMhistory表数据出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		} finally {
			returnResources(pstmt,con);
		}
		return bool;
	}
	
	public boolean updateForoverJMhistory(String str) {
		boolean bool=false;
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		try {
			String sql = new  StringBuffer().append("update xct_JMhistory ").append( str).toString();
//			System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			if (pstmt.executeUpdate() == 1)
				bool=true;
		} catch (SQLException e) {
			logger.error(new StringBuffer().append("根据").append(str).append("删除xct_JMhistory表数据出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		} finally {
			returnResources(pstmt,con);
		}
		return bool;
	}
	/**
	 * 删除节目单10天以前的数据
	 *
	 */
	public void deleteExpiredProgram(Connection conn){
		PreparedStatement pstmt = null;
		try {
			//Mysql
			String sql = "delete from xct_JMhistory where (now()-program_setdatetime)>10 and program_ISloop!=3";
			//SQLServer
//			String sql = "delete from xct_JMhistory where (getdate()-program_setdatetime)>10 and program_ISloop!=3";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(new StringBuffer().append("删除xct_JMhistory10天以前的数据出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		} finally {
			returnResources(pstmt);
		}
	}
	
	/**
	 * 根据条件查询节目，查询单个节目的配置，for delete client programe
	 * @param str
	 * @return
	 */
	public Map<String,String> getSomeProgram(Connection conn,String str){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		int program_ISloop=0;
		String program_Name="";
		String program_JMurl="";
		String program_SetDate="";
		int program_playsencond;
		String type="loop";
		String program_ip="";
		String mac="";
		Map<String,String>map=new HashMap<String,String>();
		try{
			 String sql= new  StringBuffer().append("select program_JMurl,program_Name,program_SetDate,program_PlaySecond,program_ISloop,program_ip,program_delid from xct_JMhistory ").append(str).toString();
			 pstmt=conn.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			 String somestring="";
			 while(rs.next()){
				 program_JMurl=rs.getString("program_JMurl");
				 program_Name=rs.getString("program_Name");
				 program_SetDate=rs.getString("program_SetDate").replace(".0", "");
				 program_playsencond=rs.getInt("program_PlaySecond");
				 program_ISloop=rs.getInt("program_ISloop");
				 program_ip=rs.getString("program_ip");
				  mac=rs.getString("program_delid");
				if(program_ISloop==0){
					type="loop";
				}else if(program_ISloop==1){
					type="insert";
				}else if(program_ISloop==2){
					type="active";
				}else if(program_ISloop==3){
					type="defaultloop";
				}
				// 用rs.getString(..) 串联写时，会出现 异常 ResultSet can not re-read row data for column 3. 
				somestring=new StringBuffer().append(program_Name).append("#").append(program_JMurl).append("#").append(program_SetDate).append("#").append(type).append("#").append(program_playsencond).toString();
				map.put(new StringBuffer().append(program_ip).append("_").append(mac).toString(), somestring);
		     }
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取所有节目出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt);
		}
		return map;
	}	
	public void deleteinfo(Connection conn,String strColumn, String strColumnValue,
			String table) {
		PreparedStatement pstmt = null;
		try {
			String sql = new  StringBuffer().append("delete from " ).append( table).append( " where ").append( strColumn ).append(" =?").toString();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strColumnValue);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(new StringBuffer().append("删除").append(table).append("表数据出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		} finally {
			returnResources(pstmt);
		}
	}
	
	public int getPlayProgramTimeCount(String templateid,String program_jmurl,String program_name){ //获取节目的播放时长
		
		int i=0;
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try{
			String sql=new  StringBuffer().append("select d.m_play_time ,d.media_type  ")
					.append("from xct_template_temp as a,xct_module_media as b, xct_module_temp as c,xct_media as d,xct_jmpz as e ")
					.append("where c.id=b.module_id and a.template_id=c.template_id and b.media_id=d.media_id and a.template_id=e.templateid ")
					.append("and e.templateid='").append(templateid).append("' and e.program_jmurl='").append(program_jmurl).append("' ").toString();
//			/////System.out.println("getPlayProgramTimeCount-----> "+sql);
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			int images_num=0;
			while(rs.next()){ 
				String media_type=rs.getString("media_type");
				if("MOVIE".equals(media_type)){
					i+=Integer.parseInt(rs.getString("m_play_time"));
					
				}else if("IMAGE".equals(media_type)){
					images_num++;
				}
			}
			String sql2=new  StringBuffer().append("select span from xct_module_temp where template_id='").append(templateid).append("' and m_filetype='image' ").toString();
			pstmt=con.prepareStatement(sql2);
			rs=pstmt.executeQuery();
			if(rs.next()){
				i+=Integer.parseInt(rs.getString("span"))*images_num;
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(templateid).append(">>获取所有节目出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
			return 2;
		}finally{
			returnResources(rs,pstmt,con);
		}
		//int itemp=i/1000;
		//int itmp=i%1000==0?itemp:itemp+1;
		return i%60000==0?i/60000:i/60000+1; //算出分钟
		//return itmp; //算出秒
	}
}
