package com.xct.cms.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Module;
import com.xct.cms.domin.Program;
import com.xct.cms.domin.ProgramHistory;
import com.xct.cms.domin.Template;
import com.xct.cms.utils.FileUtils;
import com.xct.cms.utils.UtilDAO;

public class ProgramDAO extends DBConnection {
	
	Logger logger = Logger.getLogger(ProgramDAO.class);
	
	public static void main(String [] args){
//		new ProgramDAO().deleteinfo("20101102154845");
		new ProgramDAO().copyProgramB("复制测试","20160125164947",null);
	}
	//===================================================================================================
	//===================================================================================================
	
	public int updateProgramAuditingStatus(String program_JMurl) {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			String sql="update  xct_JMPZ set auditingstatus=1 where program_JMurl=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, program_JMurl);
			return	pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(new StringBuffer().append("修改节目状态<<").append(program_JMurl).append(">>出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		} finally {
			returnResources(rs,pstmt);
		}
		return 0;
	}
	
	public int getProgramAuditingStatus(String  jmurl){
		String [] jmurlarray=jmurl.split("!");
		String program_jmpz_sql="select auditingstatus from  xct_JMPZ where program_JMurl=?";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		int count=0;
		try {
			for (String jmurlstr : jmurlarray) {
				pstmt=con.prepareStatement(program_jmpz_sql);
				pstmt.setString(1, jmurlstr);
				rs=pstmt.executeQuery();
				if(rs.next()){
					count= rs.getInt("auditingstatus");
				}
				if(count==0){
					break;
				}
			}
			return count;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return 0;
	}
	
	public String getProgramByJmUrl(String  jmurl){
		String program_jmpz_sql="select * from  xct_jmpz where program_jmurl =?";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(program_jmpz_sql);
			pstmt.setString(1, jmurl);
			rs=pstmt.executeQuery();
			if(rs.next()){
				return rs.getString("program_Name");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return "no";
	}
	
	//===================================================================================================
	//===================================================================================================
	
	//  ('20160125164709','20160125164947')
	public String getProgramNameByUrl(String  jmurl){
		String program_jmpz_sql=new StringBuffer().append("select * from  xct_jmpz where program_jmurl in(").append(jmurl).append(")").toString();
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		StringBuffer sBuffer=new StringBuffer();
		try {
			pstmt=con.prepareStatement(program_jmpz_sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				sBuffer.append(rs.getString("program_Name")).append(" ,");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		if(sBuffer.indexOf(",")!=-1)return sBuffer.substring(0,sBuffer.length()-1);
		return sBuffer.toString();
	}
	
	//复制节目 
	public boolean copyProgramB(String new_programname,String old_jmurl,String zuid){
		
		String program_jmpz_sql="select * from  xct_jmpz where program_jmurl=?";//result: Objest
		String program_template_temp_sql="select * from  xct_template_temp where template_id=?";//result: Objest
		String program_module_temp_sql="select * from  xct_module_temp where template_id=? order by id";//result: List
		String program_module_media_sql="select * from  xct_module_media where module_id=?";//result: List
		//==================================================================================================
		String program_template_temp_insertsql="insert into xct_template_temp (template_id,template_name,template_type,template_width,template_height,template_expla,template_background,template_backmusic,template_create_name,create_time,type) values (?,?,?,?,?,?,?,?,?,?,?)";
		String program_jmpz_insertsql="insert into xct_jmpz (program_JMurl,program_Name,program_SetDateTime,program_ISloop,program_PlaySecond,program_lr,program_lx,program_playcount,program_ggs,program_ip,program_adduser,program_status,program_treeid,program_isdel,templateid,auditingstatus) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		String program_module_temp_insertsql="insert into xct_module_temp (area_id,m_left,m_top,m_width,m_height,template_id,m_filetype,m_other,m_text,name,background,foreground,span,fontName,fontTyle,fontSize,alpha,state,otherparam) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String program_module_media_insertsql="insert into xct_module_media (module_id,media_id,type,sequence) values (?,?,?,?) ";
		
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String new_programurl="";
		try {
			Program program= new Program();
			pstmt=con.prepareStatement(program_jmpz_sql);
			pstmt.setString(1, old_jmurl);
			rs=pstmt.executeQuery();
			if(rs.next()){
				program.setProgram_JMid(rs.getInt("program_JMid"));
				program.setProgram_JMurl(rs.getString("program_JMurl"));
				program.setProgram_Name(rs.getString("program_Name"));
				program.setProgram_SetDateTime(rs.getString("program_SetDateTime"));
				program.setProgram_ISloop(rs.getInt("program_ISloop")); 
				program.setProgram_PlaySecond(rs.getInt("program_PlaySecond")); 
				program.setProgram_lr(rs.getString("program_lr")) ;
				program.setProgram_lx(rs.getString("program_lx")) ;
				program.setProgram_playcount(rs.getInt("program_playcount"));
				program.setProgram_ggs(rs.getString("program_ggs"));
				program.setProgram_downtime("");
				program.setProgram_ip(rs.getString("program_ip"));
				program.setProgram_adduser(rs.getString("program_adduser"));
				program.setProgram_status(rs.getInt("program_status"));
				program.setProgram_treeid(rs.getInt("program_treeid"));
				program.setProgram_isdel(rs.getInt("program_isdel"));
				program.setTemplateid(rs.getString("templateid"));
				program.setAuditingstatus(rs.getInt("auditingstatus"));
				//--------------------------------------------------------
				Template template=new Template();
				
				pstmt=con.prepareStatement(program_template_temp_sql);
				pstmt.setString(1, program.getTemplateid());
				rs=pstmt.executeQuery();
				
				if(rs.next()){
					template.setId(rs.getInt("id"));
					template.setTemplate_id(rs.getString("template_id"));
					template.setTemplate_name(rs.getString("template_name"));
					template.setTemplate_type(rs.getInt("template_type"));
					template.setTemplate_width(rs.getInt("template_width"));
					template.setTemplate_height(rs.getInt("template_height"));
					template.setTemplate_expla(rs.getString("template_expla"));
					template.setTemplate_background(rs.getString("template_background"));
					template.setTemplate_backmusic(rs.getString("template_backmusic"));
					template.setTemplate_create_name(rs.getString("template_create_name"));
					template.setCreate_time(rs.getString("create_time"));
					template.setType(rs.getInt("type"));
					
//					--------------------------------------------------------
					List<Module>modulelist=new ArrayList<Module>();
					
					pstmt=con.prepareStatement(program_module_temp_sql);
					pstmt.setString(1, program.getTemplateid());
					rs=pstmt.executeQuery();
					while(rs.next()){
						Module module=new Module();
						module.setId(rs.getInt("id"));
						module.setArea_id(rs.getInt("area_id"));
						module.setM_left(rs.getInt("m_left"));
						module.setM_top(rs.getInt("m_top"));
						module.setM_width(rs.getInt("m_width"));
						module.setM_height(rs.getInt("m_height"));
						module.setTemplate_id(rs.getString("template_id"));
						module.setM_filetype(rs.getString("m_filetype"));
						module.setM_other(rs.getString("m_other"));
						module.setM_text(rs.getString("m_text"));
						module.setName(rs.getString("name"));
						module.setBackground(rs.getString("background"));
						module.setForeground(rs.getString("foreground"));
						module.setSpan(rs.getString("span"));
						module.setFontName(rs.getString("fontName"));
						module.setFontTyle(rs.getString("fontTyle"));
						module.setFontSize(rs.getString("fontSize"));
						module.setAlpha(rs.getString("alpha"));
						module.setState(rs.getInt("state"));
						module.setTemplate_id(rs.getString("otherparam"));
						modulelist.add(module);
					}
					
					//---------------查询用于模版的素材--------------------------------
					Map<String, List<Module>>moudelMap=new HashMap<String, List<Module>>();
					
					for (Module module : modulelist){
						pstmt=con.prepareStatement(program_module_media_sql);
						pstmt.setInt(1, module.getId());
						rs=pstmt.executeQuery();
						List<Module>modulelist2=new ArrayList<Module>();
						while(rs.next()){
							Module module2=new Module();
							module2.setId(rs.getInt("id"));
							module2.setModule_id(rs.getInt("module_id"));
							module2.setMedia_id(rs.getString("media_id"));
							module2.setType(rs.getInt("type"));
							module2.setSequence(rs.getInt("sequence"));
							modulelist2.add(module2);
						}
						moudelMap.put(new StringBuffer().append(module.getArea_id()).append(module.getM_filetype()).toString(), modulelist2);
					}
					con.setAutoCommit(false);
					//---------------------------增加数据到 xct_template_temp--------------------------------------
					String newtemplate_id=new StringBuffer().append("t.").append(UUID.randomUUID().toString()).toString();
//					String newtemplate_id=utildao.buildId("xct_template_temp", "template_id", "t.000000001");
					
//					template_id,template_name,template_type,template_width,template_height,template_expla,
//					template_background,template_backmusic,template_create_name,create_time,type
					
					pstmt=con.prepareStatement(program_template_temp_insertsql);
					pstmt.setString(1, newtemplate_id);
					pstmt.setString(2, template.getTemplate_name());
					pstmt.setInt(3, template.getTemplate_type());
					pstmt.setInt(4, template.getTemplate_width());
					pstmt.setInt(5, template.getTemplate_height());
					pstmt.setString(6, template.getTemplate_expla());
					pstmt.setString(7, template.getTemplate_background());
					pstmt.setString(8, template.getTemplate_backmusic());
					pstmt.setString(9, template.getTemplate_create_name());
					pstmt.setString(10, template.getCreate_time());
					pstmt.setInt(11, template.getType());
					pstmt.executeUpdate();
					
//					----------------------增加数据到 xct_jmpz-------------------------------------------
					
					new_programurl=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//节目url
					
					pstmt=con.prepareStatement(program_jmpz_insertsql);
					pstmt.setString(1, new_programurl);
					pstmt.setString(2, new_programname);
					pstmt.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					pstmt.setInt(4, program.getProgram_ISloop());
					pstmt.setInt(5, program.getProgram_PlaySecond());
					pstmt.setString(6, program.getProgram_lr());
					pstmt.setString(7, program.getProgram_lx());
					pstmt.setInt(8, program.getProgram_playcount());
					pstmt.setString(9, program.getProgram_ggs());
					pstmt.setString(10, program.getProgram_ip());
					pstmt.setString(11,program.getProgram_adduser());
					pstmt.setInt(12, program.getProgram_status());
					pstmt.setInt(13, (null==zuid)?program.getProgram_treeid():Integer.parseInt(zuid));
					pstmt.setInt(14, program.getProgram_isdel());
					pstmt.setString(15,newtemplate_id);
					pstmt.setInt(16,program.getAuditingstatus());
					pstmt.executeUpdate();
					
					//-----------------------增加数据到xct_module_temp---------------------------------------
//					area_id,m_left,m_top,m_width,m_height,template_id,m_filetype,
//					m_other,m_text,name,background,foreground,span,fontName,fontTyle,fontSize,alpha,state,otherparam
					for (Module moduletmp : modulelist){
						pstmt=con.prepareStatement(program_module_temp_insertsql);
						pstmt.setInt(1, moduletmp.getArea_id());
						pstmt.setInt(2, moduletmp.getM_left());
						pstmt.setInt(3, moduletmp.getM_top());
						pstmt.setInt(4, moduletmp.getM_width());
						pstmt.setInt(5, moduletmp.getM_height());
						pstmt.setString(6, newtemplate_id);
						pstmt.setString(7, moduletmp.getM_filetype());
						pstmt.setString(8, moduletmp.getM_other());
						pstmt.setString(9, moduletmp.getM_text());
						pstmt.setString(10, moduletmp.getName());
						pstmt.setString(11, moduletmp.getBackground());
						pstmt.setString(12, moduletmp.getForeground());
						pstmt.setString(13, moduletmp.getSpan());
						pstmt.setString(14, moduletmp.getFontName());
						pstmt.setString(15,moduletmp.getFontTyle());
						pstmt.setString(16, moduletmp.getFontSize());
						pstmt.setString(17,moduletmp.getAlpha());
						pstmt.setInt(18, moduletmp.getState());
						pstmt.setString(19,moduletmp.getOtherparam());
						pstmt.executeUpdate();
					}
//					-----------------------增加数据到xct_module_media，针对每个moudle 的素材---------------------------------------
                    List<Module>modulelist2=new ArrayList<Module>();
					
					pstmt=con.prepareStatement(program_module_temp_sql);
					pstmt.setString(1,newtemplate_id);
					rs=pstmt.executeQuery();
					while(rs.next()){
						Module module3=new Module();
						module3.setId(rs.getInt("id"));
						module3.setArea_id(rs.getInt("area_id"));
						module3.setM_filetype(rs.getString("m_filetype"));
						modulelist2.add(module3);//获取新生成的moudle
					}
					for (Module moudle4 : modulelist2) {
						for (Map.Entry<String, List<Module>>  mediatmpmap : moudelMap.entrySet()) {
							String keytmp=new StringBuffer().append(moudle4.getArea_id()).append(moudle4.getM_filetype()).toString();
							if(keytmp.equals(mediatmpmap.getKey())){
//								System.out.println(keytmp+"___"+mediatmpmap.getKey()+"__"+mediatmpmap.getValue().size());
								for (Module module5 : mediatmpmap.getValue()) {
	//						         module_id,media_id,type,sequence
//									System.out.println(moudle4.getId()+"_******_"+module5.getMedia_id());
									pstmt=con.prepareStatement(program_module_media_insertsql);
									pstmt.setInt(1, moudle4.getId());
									pstmt.setString(2, module5.getMedia_id());
									pstmt.setInt(3, module5.getType());
									pstmt.setInt(4, module5.getSequence());
									pstmt.executeUpdate();
								}
							}
						}
					}
				}
			}
			con.commit();

			if(null!=new_programurl&&!new_programurl.equals("")){
				String testurlString=System.getProperty("user.dir");
				StringBuffer newurl=new StringBuffer();
				
				newurl.append(null==System.getProperty("XCT_FTP_HOME")?testurlString:System.getProperty("XCT_FTP_HOME")).append(new_programurl);
						
				String oldxml=new StringBuffer().append(newurl.toString()).append("/").append(old_jmurl).append(".xml").toString();
				
				File newprogramFile=new File(newurl.toString());
				if(!newprogramFile.exists()){
					newprogramFile.mkdirs();
				}
				String newprogramxml=newurl.append("/").append(new_programurl).append(".xml").toString();
				File newxmlFile=new File(newprogramxml);
				if(!newxmlFile.exists())
					newxmlFile.createNewFile();
				//待复制的节目文件夹
				StringBuffer oldprogramFile=new StringBuffer();
				
				oldprogramFile.append(null==System.getProperty("XCT_FTP_HOME")?testurlString:System.getProperty("XCT_FTP_HOME")).append(old_jmurl);
						
				
				FileUtils.copyFolder(new File(oldprogramFile.toString()), newprogramFile);
				
				FileUtils.copyTo(oldprogramFile.append("/").append(old_jmurl).append(".xml").toString(),newprogramxml);
					
				File oldxmlFile=new File(oldxml);
				if(oldxmlFile.exists()){
					oldxmlFile.delete();
				}
			}
			return true;
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return false;
	}
	
	/**
	 * 根据条件查询节目
	 * @param str
	 * @return
	 */
	public List<Program> getALLProgram(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs=null;
		ResultSet rs2=null;
		List<Program> list=null;
		Program program=null;
		try{
			String sql=new StringBuffer().append("select program_JMid,program_JMurl,program_Name,program_SetDateTime,program_ISloop,program_adduser,program_status,program_treeid,auditingstatus,xct_JMPZ.templateid,zu_name,template_height,template_width from xct_JMPZ,xct_zu,xct_template_temp where program_treeid=zu_id and program_isdel=0 and xct_JMPZ.templateid=xct_template_temp.template_id  ").append(str).toString();
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			list=new ArrayList<Program>();
			while(rs.next()){
				program= new Program();
				program.setProgram_JMid(rs.getInt("program_JMid"));
				String program_jmurl=rs.getString("program_JMurl");
				program.setProgram_JMurl(program_jmurl);
				program.setProgram_Name(rs.getString("program_Name"));
				program.setProgram_SetDateTime(rs.getString("program_SetDateTime"));
				program.setProgram_ISloop(rs.getInt("program_ISloop")); ///显示是否被锁定
				program.setProgram_adduser(rs.getString("program_adduser"));
				program.setProgram_status(rs.getInt("program_status"));
				program.setProgram_treeid(rs.getInt("program_treeid"));
				program.setTemplateid(rs.getString("templateid"));
				program.setZu_name(rs.getString("zu_name"));
				program.setTemplate_height(rs.getInt("template_height"));
				program.setTemplate_width(rs.getInt("template_width"));
				program.setAuditingstatus(rs.getInt("auditingstatus"));
				
				pstmt2=con.prepareStatement(new StringBuffer().append("select COUNT(id) as countaa from xct_JMPZ_type where program_JMurl='").append(program_jmurl).append("'").toString());
				rs2=pstmt2.executeQuery();
				if(rs2.next()){
					program.setIsSet_play_type(rs2.getInt(1));
				}else{
					program.setIsSet_play_type(0);
				}
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
	public List<Program> getALLProgram(Connection con,String str){
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs=null;
		ResultSet rs2=null;
		List<Program> list=null;
		Program program=null;
		try{
			String sql=new StringBuffer().append("select program_JMid,program_JMurl,program_Name,program_SetDateTime,program_ISloop,program_adduser,program_status,program_treeid,xct_JMPZ.templateid,zu_name,template_height,template_width from xct_JMPZ,xct_zu,xct_template_temp where program_treeid=zu_id and program_isdel=0 and xct_JMPZ.templateid=xct_template_temp.template_id  ").append(str).toString();
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			list=new ArrayList<Program>();
			while(rs.next()){
				program= new Program();
				program.setProgram_JMid(rs.getInt("program_JMid"));
				String program_jmurl=rs.getString("program_JMurl");
				program.setProgram_JMurl(program_jmurl);
				program.setProgram_Name(rs.getString("program_Name"));
				program.setProgram_SetDateTime(rs.getString("program_SetDateTime"));
				program.setProgram_ISloop(rs.getInt("program_ISloop")); ///显示是否被锁定
				program.setProgram_adduser(rs.getString("program_adduser"));
				program.setProgram_status(rs.getInt("program_status"));
				program.setProgram_treeid(rs.getInt("program_treeid"));
				program.setTemplateid(rs.getString("templateid"));
				program.setZu_name(rs.getString("zu_name"));
				program.setTemplate_height(rs.getInt("template_height"));
				program.setTemplate_width(rs.getInt("template_width"));
				
				pstmt2=con.prepareStatement(new StringBuffer().append("select COUNT(id) as countaa from xct_JMPZ_type where program_JMurl='").append(program_jmurl).append("'").toString());
				rs2=pstmt2.executeQuery();
				if(rs2.next()){
					program.setIsSet_play_type(rs2.getInt(1));
				}else{
					program.setIsSet_play_type(0);
				}
				list.add(program);
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取所有节目出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return list;
	}
	
	public int checkProgramMedia(Connection conn,String templateid){
		int result=1;
		ModuleDAO moduledao= new ModuleDAO();
		List moduleList=moduledao.getModuleMediaByTemplateId(conn,templateid);
		for (int i = 0; i < moduleList.size(); i++) {
			Module module = (Module) moduleList.get(i);
			String mtype=module.getM_filetype();
			if("".equals(mtype)){
				result=0;
				break;
			}else{
				 if("scroll".equals(mtype)){
					 if(module.getMediaList().size()==0){
						 result=0;
							break;
					 }
					 if(module.getState()==0){
						 result=0;
						break;
					 }
				}else if("weather".equals(mtype)){
					 if(module.getState()==0){
						 result=0;
						break;
					 }
				}else if("clock".equals(mtype)||"dateother".equals(mtype)||"date".equals(mtype)||"birthday".equals(mtype)
						||"stock".equals(mtype)||"stockother".equals(mtype)||"wwnotice".equals(mtype)||"wwmilkdrink".equals(mtype)
						||"filialeSell".equals(mtype)){
					result=1;
				}else if("iptv".equals(mtype)){
					 if(module.getState()==0){
						 result=0;
							break;
						 }
				}else if("web".equals(mtype)){
					 if(module.getState()==0){
						 result=0;
							break;
						 }
				}else if("htmltext".equals(mtype)){
					 if(module.getState()==0){
						 result=0;
							break;
						 }
				}
				/////旺旺定制
				else if("w_web".equals(mtype)){
					 if(module.getState()==0){
						 result=0;
							break;
						 }
				}else if("weatherthree".equals(mtype)){
					 if(module.getState()==0){
						 result=0;
							break;
						 }
				}
				else{
					if(module.getMediaList().size()==0){
						result=0;
					break;
					}
				}
			}
		}
		return result;
	}
	
	
	public Program getProgram(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Program program= new Program();
		try{
			String sql=new StringBuffer().append("select * from xct_JMPZ,xct_zu where program_treeid=zu_id  ").append(str).toString();
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				program.setProgram_JMid(rs.getInt("program_JMid"));
				program.setProgram_JMurl(rs.getString("program_JMurl"));
				program.setProgram_Name(rs.getString("program_Name"));
				program.setProgram_SetDateTime(rs.getString("program_SetDateTime"));
				program.setProgram_ISloop(rs.getInt("program_ISloop"));
				program.setProgram_adduser(rs.getString("program_adduser"));
				program.setProgram_status(rs.getInt("program_status"));
				program.setProgram_treeid(rs.getInt("program_treeid"));
				program.setTemplateid(rs.getString("templateid"));
				program.setZu_name(rs.getString("zu_name"));
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取节目出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return program;
	}
	
	public Program getProgram1(Connection conn, String str){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Program program= new Program();
		try{
			String sql=new StringBuffer().append("select * from xct_JMPZ,xct_zu where program_treeid=zu_id  ").append(str).toString();
			 pstmt=conn.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				program.setProgram_JMid(rs.getInt("program_JMid"));
				program.setProgram_JMurl(rs.getString("program_JMurl"));
				program.setProgram_Name(rs.getString("program_Name"));
				program.setProgram_SetDateTime(rs.getString("program_SetDateTime"));
				program.setProgram_ISloop(rs.getInt("program_ISloop"));
				program.setProgram_adduser(rs.getString("program_adduser"));
				program.setProgram_status(rs.getInt("program_status"));
				program.setProgram_treeid(rs.getInt("program_treeid"));
				program.setTemplateid(rs.getString("templateid"));
				program.setZu_name(rs.getString("zu_name"));
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取节目出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return program;
	}
	public 	Map<String,List<Program>> getProgram2(String[] programeurl){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2=null;
		Map<String,List<Program>> projectlistmap=new HashMap<String,List<Program>>();
		try{
			String sql="select program_Name from xct_JMPZ  where program_JMurl=?";
			String sql2="select * from xct_JMPZ_type  where program_JMurl=?";
			for (int i = 1; i < programeurl.length; i++) {
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, programeurl[i]);
				rs=pstmt.executeQuery();
				if(rs.next()){
					String program_name=rs.getString("program_Name");
					
					pstmt2=con.prepareStatement(sql2);
					pstmt2.setString(1, programeurl[i]);
					rs2=pstmt2.executeQuery();
					List<Program> list=new ArrayList<Program>(); 
					//int ii=0;
					while(rs2.next()){
						Program program= new Program();
						program.setProgram_Name(program_name);
						program.setProgram_JMid(rs2.getInt("id"));
						program.setProgram_JMurl(rs2.getString("program_JMurl"));
						program.setPlay_type(rs2.getInt("play_type"));
						program.setPlay_start_time(rs2.getString("play_start_time"));
						program.setPlay_end_time(rs2.getString("play_end_time"));
						program.setPlay_count(rs2.getString("play_count"));
						program.setDay_stime1(rs2.getString("day_stime1"));
						program.setDay_etime1(rs2.getString("day_etime1"));
						program.setTemplateid(rs2.getString("templateid"));
						list.add(program);
						//ii=1;
					}
					//if(ii==1){
					projectlistmap.put(program_name, list);
					//}
				}
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(programeurl).append(">>获取xct_JMPZ_type表节目属性出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return projectlistmap;
	}
	public 	Map<String,List<Program>> getProgram3(Connection con,String isdb,String playcunt,String[] programeurl){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2=null;
		String nowdate=UtilDAO.getNowtime("yyyy-MM-dd");
		String nowtime=UtilDAO.getNowtime("HH:mm")+":00";
		Map<String,List<Program>> projectlistmap=new HashMap<String,List<Program>>();
		try{
			String sql="select * from xct_JMPZ  where program_JMurl=?";
			String sql2="select * from xct_JMPZ_type  where program_JMurl=?";
			for (int i = 1; i < programeurl.length; i++) {
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, programeurl[i]);
				rs=pstmt.executeQuery();
				if(rs.next()){
					String program_name=rs.getString("program_Name");
					
					pstmt2=con.prepareStatement(sql2);
					pstmt2.setString(1, programeurl[i]);
					rs2=pstmt2.executeQuery();
					List<Program> list=new ArrayList<Program>(); 
					int ii=0;
					if(!"cb".equals(isdb)){ ///不是插播类型
						while(rs2.next()){
							Program program= new Program();
							program.setProgram_Name(program_name);
							program.setProgram_JMid(rs2.getInt("id"));
							program.setProgram_JMurl(rs2.getString("program_JMurl"));
							program.setPlay_type(rs2.getInt("play_type"));
							program.setPlay_start_time(rs2.getString("play_start_time"));
							program.setPlay_end_time(rs2.getString("play_end_time"));
							program.setPlay_count(rs2.getString("play_count"));
							program.setDay_stime1(rs2.getString("day_stime1"));
							program.setDay_etime1(rs2.getString("day_etime1"));
							program.setTemplateid(rs2.getString("templateid"));
							list.add(program);
							ii=1;
						}
					}
					if(ii==0 &&"cb".equals(isdb)){ ///直接插播
						String[] newtime2=UtilDAO.gettimeBytime(nowdate+" "+nowtime,Integer.parseInt(playcunt)).split("#");
						Program program= new Program();
						program.setProgram_Name(program_name);
						program.setProgram_JMid(0);
						program.setProgram_JMurl(programeurl[i]);
						program.setPlay_type(2);
						program.setPlay_start_time(nowdate);
						program.setPlay_end_time(nowdate);
						program.setPlay_count(playcunt);
						program.setDay_stime1(nowtime);
						program.setDay_etime1(newtime2[1]);
						program.setTemplateid(rs.getString("templateid"));
						list.add(program);
						ii=1;
					}if(ii==0){   ///默认永久循环
						Program program= new Program();
						program.setProgram_Name(program_name);
						program.setProgram_JMid(0);
						program.setProgram_JMurl(programeurl[i]);
						program.setPlay_type(4);
						program.setPlay_start_time(nowdate);
						program.setPlay_end_time(nowdate);
						program.setPlay_count("30");
						program.setDay_stime1("20:00:00");
						program.setDay_etime1("20:00:00");
						program.setTemplateid("0");
						list.add(program);
					}
					
					projectlistmap.put(program_name, list);
				}
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(programeurl).append(">>获取xct_JMPZ_type表节目属性出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return projectlistmap;
	}
	
	public 	Map<String,List<Program>> getProgram3(Connection con,String[] programeurl){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2=null;
		String nowdate=UtilDAO.getNowtime("yyyy-MM-dd");
		Map<String,List<Program>> projectlistmap=new HashMap<String,List<Program>>();
		try{
			String sql="select program_Name from xct_JMPZ  where program_JMurl=?";
			String sql2="select * from xct_JMPZ_type  where program_JMurl=?";
			for (int i = 1; i < programeurl.length; i++) {
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, programeurl[i]);
				rs=pstmt.executeQuery();
				if(rs.next()){
					String program_name=rs.getString("program_Name");
					
					pstmt2=con.prepareStatement(sql2);
					pstmt2.setString(1, programeurl[i]);
					rs2=pstmt2.executeQuery();
					List<Program> list=new ArrayList<Program>(); 
					int ii=0;
					while(rs2.next()){
						Program program= new Program();
						program.setProgram_Name(program_name);
						program.setProgram_JMid(rs2.getInt("id"));
						program.setProgram_JMurl(rs2.getString("program_JMurl"));
						program.setPlay_type(rs2.getInt("play_type"));
						program.setPlay_start_time(rs2.getString("play_start_time"));
						program.setPlay_end_time(rs2.getString("play_end_time"));
						program.setPlay_count(rs2.getString("play_count"));
						program.setDay_stime1(rs2.getString("day_stime1"));
						program.setDay_etime1(rs2.getString("day_etime1"));
						program.setTemplateid(rs2.getString("templateid"));
						list.add(program);
						ii=1;
					}
					if(ii==0){
						Program program= new Program();
						program.setProgram_Name(program_name);
						program.setProgram_JMid(0);
						program.setProgram_JMurl(programeurl[i]);
						program.setPlay_type(4);
						program.setPlay_start_time(nowdate);
						program.setPlay_end_time(nowdate);
						program.setPlay_count("30");
						program.setDay_stime1("20:00:00");
						program.setDay_etime1("20:00:00");
						program.setTemplateid("0");
						list.add(program);
					}
					
					projectlistmap.put(program_name, list);
				}
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(programeurl).append(">>获取xct_JMPZ_type表节目属性出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return projectlistmap;
	}
	public 	Map<String,List<Program>> getProgram2(Connection con,String[] programeurl){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2=null;
		Map<String,List<Program>> projectlistmap=new HashMap<String,List<Program>>();
		try{
			String sql="select program_Name,program_treeid from xct_JMPZ  where program_JMurl=?";
			String sql2="select * from xct_JMPZ_type  where program_JMurl=?";
			for (int i = 1; i < programeurl.length; i++) {
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, programeurl[i]);
				rs=pstmt.executeQuery();
				if(rs.next()){
					String program_name=rs.getString("program_Name");
					int program_treeid=rs.getInt("program_treeid");
					pstmt2=con.prepareStatement(sql2);
					pstmt2.setString(1, programeurl[i]);
					rs2=pstmt2.executeQuery();
					List<Program> list=new ArrayList<Program>(); 
					while(rs2.next()){
						Program program= new Program();
						program.setProgram_Name(program_name);
						program.setProgram_JMid(rs2.getInt("id"));
						program.setProgram_JMurl(rs2.getString("program_JMurl"));
						program.setPlay_type(rs2.getInt("play_type"));
						program.setPlay_start_time(rs2.getString("play_start_time"));
						program.setPlay_end_time(rs2.getString("play_end_time"));
						program.setPlay_count(rs2.getString("play_count"));
						program.setDay_stime1(rs2.getString("day_stime1"));
						program.setDay_etime1(rs2.getString("day_etime1"));
						program.setTemplateid(rs2.getString("templateid"));
						program.setProgram_treeid(program_treeid);
						list.add(program);
					}
					projectlistmap.put(program_name, list);
					}
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(programeurl).append(">>获取xct_JMPZ_type表节目属性出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return projectlistmap;
	}
	/**
	 * 根据一个节目URL数组获取这些节目的节目单列表
	 * @param programeurl   节目URL数组
	 * @return
	 */
	public 	List <ProgramHistory> getProgramMenuByjmurl(String[] cnt_ips,String[] programeurl){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2=null;
		List <ProgramHistory>list=new ArrayList<ProgramHistory>();
		ProgramHistory program=null;
		try{
			String sql="select program_Name from xct_JMPZ  where program_JMurl=?";
			String sql2="select program_JMurl,play_start_time,play_end_time,day_stime1,day_etime1,play_type,play_count from xct_JMPZ_type  where program_JMurl=?";
			String program_name="",program_jmurl="";
			String play_start_time="",play_end_time="",day_stime1="",day_etime1="";
			int program_play_type=0,play_count=1;
			String []setdatetime=null;  //
			String []enddatetime=null;  //
			for (int i = 0; i < programeurl.length; i++) {
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, programeurl[i]);
				rs=pstmt.executeQuery();
				if(rs.next()){
					program_name=rs.getString("program_Name");
					program_jmurl=programeurl[i];
					pstmt2=con.prepareStatement(sql2);
					pstmt2.setString(1, programeurl[i]);
					rs2=pstmt2.executeQuery();
					while(rs2.next()){
						program_play_type=rs2.getInt("play_type")-1;
						if(program_play_type==1){
							play_count=rs2.getInt("play_count");
							String nowdate=UtilDAO.getNowtime("yyyy-MM-dd");
							String nowtime=UtilDAO.getNowtime("HH:mm:ss");
							play_start_time=nowdate;
							play_end_time=nowdate;
							day_stime1=nowtime;
							day_etime1=UtilDAO.gettimeBytime(nowdate+" "+nowtime,play_count).split("#")[1];
						}else{
							play_start_time=rs2.getString("play_start_time");
							play_end_time=rs2.getString("play_end_time");
							day_stime1=rs2.getString("day_stime1");
							day_etime1=rs2.getString("day_etime1");
						}
						if(!play_start_time.equals(play_end_time)){   ///一个时间段的情况
							setdatetime=play_start_time.split("-");
				        	enddatetime=play_end_time.split("-");
							 //请注意月份是从0-11
					        Calendar start = Calendar.getInstance();
					        start.set(Integer.parseInt(setdatetime[0]), Integer.parseInt(setdatetime[1])-1, Integer.parseInt(setdatetime[2]));
					        Calendar end = Calendar.getInstance();
					        end.set(Integer.parseInt(enddatetime[0]), Integer.parseInt(enddatetime[1])-1, Integer.parseInt(enddatetime[2]));
					        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					        String everday="";
					        while(start.compareTo(end) <= 0){
					        	everday=format.format(start.getTime());
								for(int j=0;j<cnt_ips.length;j++){
									if(!"".equals(cnt_ips[j])){
										program= new ProgramHistory();
										program.setProgram_JMurl(program_jmurl);
										program.setProgram_Name(program_name);
										program.setProgram_SetDateTime(everday);   ////日期  哪天
										program.setProgram_SetDate(day_stime1);    //开始时间
										program.setProgram_EndDate(day_etime1);   //结束时间
										String[] ipmac=cnt_ips[j].split("_");
										program.setProgram_ip(ipmac[0]);
										program.setProgram_delid(ipmac[1]);
										program.setProgram_ISloop(program_play_type);
										list.add(program);
									}
								}
					            start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
					            //循环，每次天数加1
					        }
						}else{
							for(int j=0;j<cnt_ips.length;j++){
								if(!"".equals(cnt_ips[j])){
									program= new ProgramHistory();
									program.setProgram_JMurl(program_jmurl);
									program.setProgram_Name(program_name);
									program.setProgram_SetDateTime(play_start_time);   ////日期  哪天
									program.setProgram_SetDate(day_stime1);    //开始时间
									program.setProgram_EndDate(day_etime1);   //结束时间
									String[] ipmac=cnt_ips[j].split("_");
									program.setProgram_ip(ipmac[0]);
									program.setProgram_delid(ipmac[1]);
									program.setProgram_ISloop(program_play_type);
									list.add(program);
								}
							}
						}
					}
					}
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(programeurl).append(">>获取xct_JMPZ_type表节目属性出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	
	
	public Program getProgram_type(String id){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Program program= new Program();
		try{
			String sql="select * from xct_JMPZ_type  where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			if(rs.next()){	
						program.setProgram_JMid(rs.getInt("id"));
						program.setProgram_JMurl(rs.getString("program_JMurl"));
						program.setPlay_type(rs.getInt("play_type"));
						program.setPlay_start_time(rs.getString("play_start_time"));
						program.setPlay_end_time(rs.getString("play_end_time"));
						program.setPlay_count(rs.getString("play_count"));
						program.setDay_stime1(rs.getString("day_stime1"));
						program.setDay_etime1(rs.getString("day_etime1"));
						program.setTemplateid(rs.getString("templateid"));
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(id).append(">>获取xct_JMPZ_type表节目属性出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return program;
		
	}
	public List<Program> getProgram_typeBystr(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		List<Program> list=new ArrayList<Program>(); 
		
		try{
			String sql=new StringBuffer().append("select * from xct_JMPZ_type ").append(str).toString();
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				Program program= new Program();
						program.setProgram_JMid(rs.getInt("id"));
						program.setProgram_JMurl(rs.getString("program_JMurl"));
						program.setPlay_type(rs.getInt("play_type"));
						program.setPlay_start_time(rs.getString("play_start_time"));
						program.setPlay_end_time(rs.getString("play_end_time"));
						program.setPlay_count(rs.getString("play_count"));
						program.setDay_stime1(rs.getString("day_stime1"));
						program.setDay_etime1(rs.getString("day_etime1"));
						program.setTemplateid(rs.getString("templateid"));
						list.add(program);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据<<").append(str).append(">>获取xct_JMPZ_type表节目属性出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
		
	}
	
	public boolean checkProgramName(String programName){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try{
			String sql=new StringBuffer().append("select * from xct_JMPZ where program_Name = '").append(programName).append("' and program_isdel=0").toString();
			 pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			 if(rs.next()){
				return true;
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("新建节目时，检查节目名：<<").append(programName).append(">>出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return false;
	}
	public boolean checkProgramName(Connection con,String programName){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try{
			String sql=new StringBuffer().append("select * from xct_JMPZ where program_Name = '").append(programName).append("' and program_isdel=0").toString();
			 pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			 if(rs.next()){
				return true;
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("新建节目时，检查节目名：<<").append(programName+">>出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return false;
	}
/**
 * 根据节目URL删除节目，对应xct_template_temp表和xct_module_temp也删除
 * @return
 */
	public void deleteinfo(String program_JMurl) {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			String sql="select templateid from xct_JMPZ where program_JMurl=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, program_JMurl);
			rs=pstmt.executeQuery();
			String templateid="";
			if(rs.next()){
				templateid=rs.getString("templateid");
				String sql2 = new StringBuffer().append("delete from xct_JMPZ where program_JMurl ='").append(program_JMurl).append("'").toString();	
				String sql3 = new StringBuffer().append("delete from xct_module_temp where template_id ='").append(templateid).append("'").toString();	
				String sql4 = new StringBuffer().append("delete from xct_template_temp where template_id ='").append(templateid).append("'").toString();	
				String sql5 = new StringBuffer().append("delete from xct_module_media where module_id in (select id from  xct_module_temp where  template_id ='").append(templateid).append("')").toString();	
				pstmt = con.prepareStatement(sql5);
				pstmt.executeUpdate();
				pstmt = con.prepareStatement(sql4);
				pstmt.executeUpdate();
				pstmt = con.prepareStatement(sql3);
				pstmt.executeUpdate();
				pstmt = con.prepareStatement(sql2);
				pstmt.executeUpdate();
			}	
		} catch (SQLException e) {
			logger.error(new StringBuffer().append("删除节目<<").append(program_JMurl).append(">>出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		} finally {
			returnResources(rs,pstmt,con);
		}
	}
	/**
	 * 根据节目URL删除节目，对应xct_template_temp表和xct_module_temp也删除
	 * @param program_JMurl
	 * @return
	 */
		public boolean deleteinfo(Connection con,String program_JMurl) {
			boolean flagB=false;
			PreparedStatement pstmt = null;
			ResultSet rs=null;
			try {
				String sql="select templateid from xct_JMPZ where program_JMurl=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, program_JMurl);
				rs=pstmt.executeQuery();
				String templateid="";
				if(rs.next()){
					templateid=rs.getString("templateid");
					String sql2 = new StringBuffer().append( "delete from xct_JMPZ where program_JMurl ='").append(program_JMurl).append("'").toString();	
					String sql3 = new StringBuffer().append("delete from xct_module_temp where template_id ='").append(templateid).append("'").toString();	
					String sql4 = new StringBuffer().append("delete from xct_template_temp where template_id ='").append(templateid).append("'").toString();	
					String sql5 = new StringBuffer().append("delete from xct_module_media where module_id in (select id from  xct_module_temp where  template_id ='").append(templateid).append("')").toString();	
				    String sql6 = new StringBuffer().append("delete from xct_jmapp where program_JMurl ='").append(program_JMurl).append("'").toString();
				    String sql7 = new StringBuffer().append( "delete from xct_jmhistory  where program_JMurl ='").append(program_JMurl).append("'").toString();
				    
					pstmt = con.prepareStatement(sql5);
					pstmt.executeUpdate();
					pstmt = con.prepareStatement(sql4);
					pstmt.executeUpdate();
					pstmt = con.prepareStatement(sql3);
					pstmt.executeUpdate();
					pstmt = con.prepareStatement(sql2);
					pstmt.executeUpdate();
					pstmt = con.prepareStatement(sql6);
					pstmt.executeUpdate();
					pstmt = con.prepareStatement(sql7);
					pstmt.executeUpdate();
			        flagB=true;
				}	
			} catch (SQLException e) {
				logger.error(new StringBuffer().append("删除节目<<").append(program_JMurl).append(">>出错！=====").append(e.getMessage()).toString());
				e.printStackTrace();
			} finally {
				returnResources(rs,pstmt);
			}
			return flagB;
		}
		
	
		
		/**
		 * 修改节目模板
		 * @param con
		 * @param program_JMurl
		 */
		public void updateProgramTemplate(Connection con,String program_JMurl,String old_templateid, String new_templateid) {
			PreparedStatement pstmt = null;
			ResultSet rs=null;
			try {
				String sql="update  xct_JMPZ set templateid=? where program_JMurl=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, new_templateid);
				pstmt.setString(2, program_JMurl);
				pstmt.executeUpdate();
				
				String sql3 = new StringBuffer().append("delete from xct_module_temp where template_id ='").append(old_templateid).append("'").toString();	
				String sql4 = new StringBuffer().append("delete from xct_template_temp where template_id ='").append(old_templateid).append("'").toString();	
				String sql5 = new StringBuffer().append("delete from xct_module_media where module_id in (select id from  xct_module_temp where  template_id ='").append(old_templateid+"')").toString();	
				pstmt = con.prepareStatement(sql5);
				pstmt.executeUpdate();
				pstmt = con.prepareStatement(sql3);
				pstmt.executeUpdate();
				pstmt = con.prepareStatement(sql4);
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				logger.error(new StringBuffer().append("修改节目模板<<").append(program_JMurl).append(">>出错！=====").append(e.getMessage()).toString());
				e.printStackTrace();
			} finally {
				returnResources(rs,pstmt);
			}
		}
	/**
	 * 删除两天前未保存节目
	 */
	public void deleteNoSaveProgram() {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			String nowtime=UtilDAO.getbeginTime(2,"yyyy-MM-dd HH:mm:ss");
			String sql=new StringBuffer().append("select template_id from xct_template_temp where type=0 and create_time < '").append(nowtime).append("'").toString();
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				String templateid=rs.getString("template_id");
				String sql4 = new StringBuffer().append("delete from xct_module_temp where template_id ='").append(templateid).append("'").toString();	
				String sql5 = new StringBuffer().append("delete from xct_module_media where module_id in (select id from  xct_module_temp where  template_id ='").append(templateid).append("')").toString();	
				pstmt = con.prepareStatement(sql5);
				pstmt.executeUpdate();
				pstmt = con.prepareStatement(sql4);
				pstmt.executeUpdate();
			}
			String sql3 = new StringBuffer().append("delete from xct_template_temp where type=0 and create_time < '").append(nowtime).append("'").toString();
			pstmt = con.prepareStatement(sql3);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(new StringBuffer().append("删除两天前未保存节目出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		} finally {
			returnResources(rs,pstmt,con);
		}
	}public void deleteNoSaveProgram(Connection con) {
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			String nowtime=UtilDAO.getbeginTime(2,"yyyy-MM-dd HH:mm:ss");
			String sql=new StringBuffer().append("select template_id from xct_template_temp where type=0 and create_time < '").append(nowtime).append("'").toString();
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				String templateid=rs.getString("template_id");
				String sql4 = new StringBuffer().append("delete from xct_module_temp where template_id ='").append(templateid).append("'").toString();	
				String sql5 = new StringBuffer().append("delete from xct_module_media where module_id in (select id from  xct_module_temp where  template_id ='").append(templateid).append("')").toString();	
				pstmt = con.prepareStatement(sql5);
				pstmt.executeUpdate();
				pstmt = con.prepareStatement(sql4);
				pstmt.executeUpdate();
			}
			String sql3 = new StringBuffer().append("delete from xct_template_temp where type=0 and create_time < '").append(nowtime).append("'").toString();
			pstmt = con.prepareStatement(sql3);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(new StringBuffer().append("删除两天前未保存节目出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		} finally {
			returnResources(rs,pstmt);
		}
	}
}
