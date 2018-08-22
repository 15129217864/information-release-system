package com.xct.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Media;
import com.xct.cms.domin.Module;

public class ModuleDAO extends DBConnection {
	Logger logger = Logger.getLogger(ModuleDAO.class);
	
	/**
	 * 根据模板ID获取模块
	 * @param templateid
	 * @return
	 */
	public List<Module> getModuleByTemplateId(String templateid){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Module module=null;
		List<Module> list=null;
		try{
			String sql="select * from xct_module where template_id=? ";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, templateid);
			 rs=pstmt.executeQuery();
			list=new ArrayList<Module>();
			while(rs.next()){
				module= new Module();
				module.setId(rs.getInt("id"));
				module.setArea_id(rs.getInt("area_id"));
				module.setM_left(rs.getInt("m_left"));
				module.setM_top(rs.getInt("m_top"));
				module.setM_width(rs.getInt("m_width"));
				module.setM_height(rs.getInt("m_height"));
				module.setTemplate_id(rs.getString("template_id"));
				module.setM_filetype(rs.getString("m_filetype"));

				list.add(module);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID'").append(templateid).append("'获取模块出错！=====").append(e.getMessage()).toString());
		    e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	/**
	 * 根据模板ID获取所有模块和模块里面的媒体
	 * @param templateid
	 * @return
	 */
	public List<Module> getModuleMediaByTemplateId(String templateid){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Module module=null;
		Media media=null;
		List<Module> list=null;
		List<Media> medialist=null;
		ResultSet rs2=null;
		try{
			String sql=new StringBuffer().append("select id,area_id,m_left,m_top,m_width,m_height,template_id,m_filetype,m_other,m_text,")
					.append("name,background,foreground,span,fontName,fontTyle,fontSize,alpha,state " )
							.append("from xct_module_temp where template_id=? ").toString();
			 pstmt=con.prepareStatement(sql);
//			 System.out.println(sql);
			 pstmt.setString(1, templateid);
			 rs=pstmt.executeQuery();
			list=new ArrayList<Module>();
			while(rs.next()){
				module= new Module();
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
				
				String sql2=new StringBuffer().append("select media_title,media_type,m_play_time,file_name,file_path,slightly_img_path,slightly_img_name ")
						        .append("from xct_media,xct_module_temp,xct_module_media ")
								.append("where xct_media.media_id=xct_module_media.media_id " )
								.append("and xct_module_temp.id=xct_module_media.module_id and xct_module_temp.id=? order by sequence asc").toString();
				pstmt=con.prepareStatement(sql2);
//				System.out.println(sql2);
				pstmt.setInt(1, module.getId());
				rs2=pstmt.executeQuery();
				medialist=new ArrayList<Media>();
				while(rs2.next()){
					media=new Media();
					media.setMedia_title(rs2.getString("media_title"));
					media.setMedia_type(rs2.getString("media_type"));
					media.setM_play_time(rs2.getString("m_play_time"));
					media.setFile_name(rs2.getString("file_name"));
					media.setFile_path(rs2.getString("file_path"));
					media.setSlightly_img_path(rs2.getString("slightly_img_path"));
					media.setSlightly_img_name(rs2.getString("slightly_img_name"));
					medialist.add(media);
				}
				module.setMediaList(medialist);
				list.add(module);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID'").append(templateid).append("'获取模块出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			try {
				if(rs2!=null)rs2.close();
			} catch (SQLException e) {
			}
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	
	/**
	 * 根据节目URL获取节目每个模块的信息
	 * @param templateid
	 * @return
	 */
	public List<Module> getModuleMediaByProgrameUrl(String[] programeurl){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Module module=null;
		Media media=null;
		List<Module> list=new ArrayList<Module>();
		List<Media> medialist=null;
		ResultSet rs2=null;
		try{
			String sql=new StringBuffer().append("select program_Name,id,area_id,template_id,m_filetype,name,state from xct_JMPZ,xct_module_temp where ")
					.append("xct_JMPZ.templateid=xct_module_temp.template_id and program_JMurl=?").toString();
			for (int i = 0; i < programeurl.length; i++) {
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, programeurl[i]);
				rs=pstmt.executeQuery();
				while(rs.next()){
					module= new Module();
					module.setProgram_name(rs.getString("program_Name"));
					module.setId(rs.getInt("id"));
					module.setArea_id(rs.getInt("area_id"));
					module.setTemplate_id(rs.getString("template_id"));
					module.setM_filetype(rs.getString("m_filetype"));
					module.setName(rs.getString("name"));
					module.setState(rs.getInt("state"));
					String sql2=new StringBuffer().append("select xct_module_media.id as mid  from xct_module_temp,xct_module_media ")
							.append("where  xct_module_temp.id=xct_module_media.module_id and xct_module_temp.id=?").toString();
					pstmt=con.prepareStatement(sql2);
					pstmt.setInt(1, module.getId());
					rs2=pstmt.executeQuery();
					medialist=new ArrayList<Media>();
					while(rs2.next()){
						media=new Media();
						media.setId(rs2.getInt("mid"));
						medialist.add(media);
					}
					module.setMediaList(medialist);
					list.add(module);
				}
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据节目URL").append(programeurl).append("获取节目每个模块的信息出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			try {
				if(rs2!=null)rs2.close();
			} catch (SQLException e) {
			}
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	
	
	public List<Module> getModuleMediaByTemplateId(Connection con,String templateid){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Module module=null;
		Media media=null;
		List<Module> list=null;
		List<Media> medialist=null;
		ResultSet rs2=null;
		try{
			String sql="select id,area_id,m_left,m_top,m_width,m_height,template_id,m_filetype,m_other,m_text,name,background,foreground,span,fontName,fontTyle,fontSize,alpha,state from xct_module_temp where template_id=? ";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, templateid);
			 rs=pstmt.executeQuery();
			list=new ArrayList<Module>();
			while(rs.next()){
				module= new Module();
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
				String sql2=new StringBuffer().append("select media_title,media_type,m_play_time,file_name,file_path,slightly_img_path,slightly_img_name from xct_media,xct_module_temp,xct_module_media ")
						.append("where xct_media.media_id=xct_module_media.media_id and xct_module_temp.id=xct_module_media.module_id and xct_module_temp.id=? order by sequence asc").toString();
				pstmt=con.prepareStatement(sql2);
				pstmt.setInt(1, module.getId());
				rs2=pstmt.executeQuery();
				medialist=new ArrayList<Media>();
				while(rs2.next()){
					media=new Media();
					media.setMedia_title(rs2.getString("media_title"));
					media.setMedia_type(rs2.getString("media_type"));
					media.setM_play_time(rs2.getString("m_play_time"));
					media.setFile_name(rs2.getString("file_name"));
					media.setFile_path(rs2.getString("file_path"));
					media.setSlightly_img_path(rs2.getString("slightly_img_path"));
					media.setSlightly_img_name(rs2.getString("slightly_img_name"));
					medialist.add(media);
				}
				module.setMediaList(medialist);
				list.add(module);
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID'").append(templateid).append("'获取模块出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			try {
				if(rs2!=null)rs2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			returnResources(rs,pstmt);
		}
		return list;
	}
	
	public boolean updateModuleTempByTemplateIdForAlpha(String type,String alphaString,String templateid){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		int i=-1;
		alphaString=alphaString.equals("")?"1.0":alphaString;
		String sql=new StringBuffer().append("update  xct_module_temp set alpha='").append(alphaString).append("' where m_filetype='").append(type).append("' and template_id=? ").toString();
//		String sql2="alter table xct_module_temp alter column alpha nvarchar(200)";
//		System.out.println("updateModuleTempByTemplateIdForAlpha---------1--------->"+sql+"_____________"+templateid);
		try{
//			pstmt=con.prepareStatement(sql2);
//			pstmt.executeUpdate();
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, templateid);
			 i=pstmt.executeUpdate();
			
			if(i>0){
				return true;
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID'").append(templateid).append("'修改alpha出错！===1==").append(e.getMessage()).toString());
			e.printStackTrace();
			return false;
		}
		finally{
			returnResources(pstmt,con);
		}
		return false;
	}	
	
	public boolean updateModuleTempByTemplateIdForAlpha(String moduleid,String type,String alphaString,String templateid){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		int i=-1;
		alphaString=alphaString.equals("")?"1.0":alphaString;
		String sql=new StringBuffer().append("update  xct_module_temp set alpha='").append(alphaString).append("' where m_filetype='").append(type).append("' and template_id=? and id=?").toString();
//		String sql2="alter table xct_module_temp alter column alpha nvarchar(200)";
//		System.out.println("updateModuleTempByTemplateIdForAlpha--------2---------->"+sql+"_____________"+templateid);
		try{
//			pstmt=con.prepareStatement(sql2);
//			pstmt.executeUpdate();
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, templateid);
			 pstmt.setInt(2, Integer.parseInt(moduleid));
			 i=pstmt.executeUpdate();
			
			if(i>0){
				return true;
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID'").append(templateid).append("'修改alpha出错！=2====").append(e.getMessage()).toString());
			e.printStackTrace();
			return false;
		}
		finally{
			returnResources(pstmt,con);
		}
		return false;
	}	
	
	public boolean updateModuleById(String column,String values,String moudleid){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		String sql=new StringBuffer().append("update  xct_module_temp set ").append(column).append("='").append(values).append("' where  id=").append(moudleid).toString();
		try{
			 pstmt=con.prepareStatement(sql);
			 if(pstmt.executeUpdate()>0)
				return true;
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID'").append(moudleid).append("'修改").append(column).append("出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
			return false;
		}
		finally{
			returnResources(pstmt,con);
		}
		return false;
	}
	
	public List<Module> getModuleTempByTemplateIdForBackimge(String templateid){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Module module=null;
		List<Module> list=null;
		try{
			String sql="select * from xct_module_temp where template_id=? ";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, templateid);
			 rs=pstmt.executeQuery();
			list=new ArrayList<Module>();
			while(rs.next()){
				module= new Module();
				module.setId(rs.getInt("id"));
		/*		module.setArea_id(rs.getInt("area_id"));
				module.setM_left(rs.getInt("m_left"));
				module.setM_top(rs.getInt("m_top"));
				module.setM_width(rs.getInt("m_width"));
				module.setM_height(rs.getInt("m_height"));*/
				module.setTemplate_id(rs.getString("template_id"));
				module.setM_filetype(rs.getString("m_filetype"));
				module.setAlpha(rs.getString("alpha"));
				list.add(module);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID'").append(templateid).append("'获取模块出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}	
	
	public List<Module> getModuleTempByTemplateId(String templateid){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Module module=null;
		List<Module> list=null;
		try{
			String sql="select * from xct_module_temp where template_id=? ";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, templateid);
			 rs=pstmt.executeQuery();
			list=new ArrayList<Module>();
			while(rs.next()){
				module= new Module();
				module.setId(rs.getInt("id"));
				module.setArea_id(rs.getInt("area_id"));
				module.setM_left(rs.getInt("m_left"));
				module.setM_top(rs.getInt("m_top"));
				module.setM_width(rs.getInt("m_width"));
				module.setM_height(rs.getInt("m_height"));
				module.setTemplate_id(rs.getString("template_id"));
				module.setM_filetype(rs.getString("m_filetype"));
				list.add(module);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID'").append(templateid).append("'获取模块出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}	
	
	public List<Module> getModuleTempByTemplateId(Connection con,String templateid){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Module module=null;
		List<Module> list=null;
		try{
			String sql="select * from xct_module_temp where template_id=? ";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, templateid);
			 rs=pstmt.executeQuery();
			list=new ArrayList<Module>();
			while(rs.next()){
				module= new Module();
				module.setId(rs.getInt("id"));
				module.setArea_id(rs.getInt("area_id"));
				module.setM_left(rs.getInt("m_left"));
				module.setM_top(rs.getInt("m_top"));
				module.setM_width(rs.getInt("m_width"));
				module.setM_height(rs.getInt("m_height"));
				module.setTemplate_id(rs.getString("template_id"));
				module.setM_filetype(rs.getString("m_filetype"));
				list.add(module);
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID'").append(templateid).append("'获取模块出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return list;
	}
	/**
	 * 根据模块ID获取模块
	 * @param templateid
	 * @return
	 */
	public Module getModuleTempByModuleid(int moduleid){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Module module= new Module();
		try{
			String sql="select id,area_id,m_left,m_top,m_width,m_height,template_id,m_filetype,m_other,m_text,name,background,foreground,span,fontName,fontTyle,fontSize,alpha,state  from xct_module_temp where id=? ";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setInt(1, moduleid);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				module= new Module();
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
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模块ID'").append(moduleid).append("'获取模块出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return module;
	}
	public Module getModuleTempByModuleid(Connection con,int moduleid){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Module module= new Module();
		try{
			String sql=new StringBuffer().append("select id,area_id,m_left,m_top,m_width,m_height,template_id,m_filetype,m_other,m_text," )
					.append("name,background,foreground,span,fontName,fontTyle,fontSize,alpha,state  from xct_module_temp where id=? ").toString();
			 pstmt=con.prepareStatement(sql);
//			 System.out.println(sql+"_"+moduleid);
			 pstmt.setInt(1, moduleid);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				module= new Module();
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
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模块ID'").append(moduleid).append("'获取模块出错！=====").append(e.getMessage()));
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return module;
	}
	/**
	 * 根据模块ID获取模块里面的媒体
	 * @param moduleid
	 * @return
	 */
	public List<Module> getMediaByModuleId(int moduleid){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		List<Module> list=null;
		Module module=null;
		try{
			String sql=new StringBuffer().append("select xct_media.media_id,media_title,media_type,xct_module_media.id,xct_media.file_path,xct_media.file_name,xct_media.slightly_img_path,xct_media.slightly_img_name,xct_module_media.sequence,xct_module_temp.span from xct_media,xct_module_temp,xct_module_media ")
							.append("where xct_media.media_id=xct_module_media.media_id and xct_module_temp.id=xct_module_media.module_id and xct_module_media.module_id=? order by sequence asc").toString();
			 pstmt=con.prepareStatement(sql);
			 pstmt.setInt(1, moduleid);
			 rs=pstmt.executeQuery();
			list=new ArrayList<Module>();
			while(rs.next()){
				module= new Module();
				module.setMedia_id(rs.getString("media_id"));
				module.setMedia_title(rs.getString("media_title"));
				module.setMedia_type(rs.getString("media_type"));
				module.setId(rs.getInt("id"));
				module.setWeatherfile(rs.getString("file_path")+rs.getString("file_name"));
				module.setSlightly_img_path(rs.getString("slightly_img_path")+rs.getString("slightly_img_name"));
				module.setSequence(rs.getInt("sequence"));
				module.setSpan(rs.getString("span"));
				list.add(module);
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模块ID'").append(moduleid).append("'获取媒体出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	
	public List<Module> getMediaByModuleId(Connection con,int moduleid){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		List<Module> list=null;
		Module module=null;
		try{
			String sql=new StringBuffer().append("select xct_media.media_id,media_title,media_type,xct_module_media.id,xct_media.slightly_img_path,")
					.append("xct_media.slightly_img_name,xct_module_media.sequence,xct_module_temp.span from xct_media,xct_module_temp,xct_module_media ")
					.append("where xct_media.media_id=xct_module_media.media_id and xct_module_temp.id=xct_module_media.module_id " )
					.append("and xct_module_media.module_id=? order by sequence asc").toString();
			 pstmt=con.prepareStatement(sql);
			 pstmt.setInt(1, moduleid);
			 rs=pstmt.executeQuery();
			list=new ArrayList<Module>();
			while(rs.next()){
				module= new Module();
				module.setMedia_id(rs.getString("media_id"));
				module.setMedia_title(rs.getString("media_title"));
				module.setMedia_type(rs.getString("media_type"));
				module.setId(rs.getInt("id"));
				module.setSlightly_img_path(rs.getString("slightly_img_path")+rs.getString("slightly_img_name"));
				module.setSequence(rs.getInt("sequence"));
				module.setSpan(rs.getString("span"));
				list.add(module);
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模块ID'").append(moduleid).append("'获取媒体出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return list;
	}
	public void updatesequence(String module_id,int sequence,int mid,String order){
		String sql1="update xct_module_media set sequence=sequence+1 where module_id =? and sequence=?";
		String sql2="update xct_module_media set sequence=sequence-1 where id=?";
		if("desc".equals(order)){
			sql1="update xct_module_media set sequence=sequence-1 where module_id =? and sequence=?";
			sql2="update xct_module_media set sequence=sequence+1 where id=?";
		}
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		try{
			pstmt=con.prepareStatement(sql1);
			pstmt.setString(1, module_id);
			if("desc".equals(order)){
				pstmt.setInt(2, sequence+1);
			}else{
				pstmt.setInt(2, sequence-1);
			}
			
			pstmt.executeUpdate();
			
			pstmt=con.prepareStatement(sql2);
			pstmt.setInt(1, mid);
			pstmt.executeUpdate();
			
		}catch(Exception e){
			logger.error(new StringBuffer().append("修改模块里的媒体顺序出错！=====").append(e.getMessage()));
			e.printStackTrace();
		}
		finally{
			returnResources(pstmt,con);
		}
	}
	public int getSequenceBymid(String module_id){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try{
			//Mysql
			String sql="select  sequence from xct_module_media where module_id =? order by sequence desc limit 1";
//			SQLServer
			//String sql="select top 1 sequence from xct_module_media where module_id =? order by sequence desc";
			pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, module_id);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt("sequence")+1;
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("获取xct_module_media表排序号出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return 1;
	}
	
	public int getSequenceBymid(Connection con,String module_id){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try{
			//Mysql
			String sql="select  sequence from xct_module_media where module_id =? order by sequence desc limit 1";
//			SQLServer
			//String sql="select top 1 sequence from xct_module_media where module_id =? order by sequence desc";
			pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, module_id);
			 rs=pstmt.executeQuery();
			if(rs.next()){
					return rs.getInt("sequence")+1;
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("获取xct_module_media表排序号出错！=====").append(e.getMessage()));
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt);
		}
		return 1;
		
		
	}
	
	/***
	 * 验证模块（视频，FLASH，流媒体，时钟，只能为一个）（天气预报最多3个）
	 * @param mtype 模块类型
	 * @param moduleid 模块ID
	 * @return 
	 */
	
	public List<Module> getModuleTypeByTemplateid(String templateid){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Module module=null;
		List<Module> list=new ArrayList<Module>();
		try{
			String sql="select id,area_id,m_filetype from xct_module_temp where template_id=?";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, templateid);
			 rs=pstmt.executeQuery();
			 while(rs.next()) { 
				 module= new Module();
				 module.setId(rs.getInt("id"));
				 module.setArea_id(rs.getInt("area_id"));
				 module.setM_filetype(rs.getString("m_filetype"));
				list.add(module);
			 }
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID<<").append(templateid).append(">>获取模块类型出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	public  String checkModuleType(int moduleid,String m_filetype,String templateid){
		List<Module> moduleList=new ModuleDAO().getModuleTypeByTemplateid(templateid);
		String result="ok";
		if("video".equals(m_filetype) /*|| "iptv".equals(m_filetype)*/){
			for(int i=0;i<moduleList.size();i++){
				Module module=moduleList.get(i);
				String mtype=module.getM_filetype();
				if("video".equals(mtype)/* || "iptv".equals(mtype)*/){
					if(moduleid!=module.getId()){
						String mtypetitle=new Module().getMtypeTitle(mtype);
						result= new StringBuffer().append("“播放区域").append(module.getArea_id()).append("”已添加").append(mtypetitle).append("类型。（视频只能单独存在）").toString();//和流媒体
						break;
					}
				}
			}
		}else if("flash".equals(m_filetype)){
			for(int i=0;i<moduleList.size();i++){
				Module module=moduleList.get(i);
				String mtype=module.getM_filetype();
				if("video".equals(mtype) || "iptv".equals(mtype) ||"flash".equals(mtype)){
					if(moduleid!=module.getId()){
						String mtypetitle=new Module().getMtypeTitle(mtype);
						result=new StringBuffer().append("flash_ok#“播放区域").append(module.getArea_id()).append("”已添加").append(mtypetitle).append("类型，如果再添加一个带声音的FLASH，会有双声音存在！").toString();
						//result= "“播放区域"+module.getArea_id()+"”已添加"+mtypetitle+"类型。（视频、流媒体、FLASH只能单独存在）";
						break;
					}
				}
			}
		}else if("clock".equals(m_filetype)){
			for(int i=0;i<moduleList.size();i++){
				Module module=moduleList.get(i);
				String mtype=module.getM_filetype();
				if("clock".equals(mtype)){
					result= new StringBuffer().append("“播放区域").append(module.getArea_id()).append("”已添加时钟类型。（时钟只能有一个）！").toString();
					break;
				}
			}
		}else if("weather".equals(m_filetype)){
			int wnum=0;
			for(int i=0;i<moduleList.size();i++){
				Module module=moduleList.get(i);
				String mtype=module.getM_filetype();
				if("weather".equals(mtype)){
					wnum++;
				}
			}
			if(wnum>=3){
				result= "天气预报最多只能有三个，请确认！";
			}
		}
		return result;
	}
	public static void main(String [] args){
		int result=new ModuleDAO().orderMedia(1392,1396,1411);
		System.out.println("=="+result);
	}
	public int orderMedia(int new_order_media_id,int old_order_media_id,int module_id){
//		System.out.println(new_order_media_id+"==="+ old_order_media_id+"==="+ module_id);
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Module module=null;
		List<Module> list=new ArrayList<Module>();
		int i=0;
		try{
			String sql="select id,sequence from xct_module_media where module_id=?";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setInt(1, module_id);
			 rs=pstmt.executeQuery();
			 while(rs.next()) { 
				 module= new Module();
				 module.setId(rs.getInt("id"));
				 module.setSequence(rs.getInt("sequence"));
				list.add(module);
			 }
			 int old_sequence=0;
			 for(int j=0;j<list.size();j++){  ////获取排序到某一个媒体前面的那个媒体的排序号
				 module=list.get(j);
				 if(old_order_media_id==module.getId()){
					 old_sequence=module.getSequence();
					 break;
				 }
			 } 
			 int new_sequence1=0;
			 for(int j=0;j<list.size();j++){  ////获取排序到某一个媒体前面的那个媒体的排序号
				 module=list.get(j);
				 if(new_order_media_id==module.getId()){
					 new_sequence1=module.getSequence();
					 break;
				 }
			 }
			String sql1="update xct_module_media set sequence=? where id =?";
			pstmt=con.prepareStatement(sql1);
			pstmt.setInt(1, old_sequence);
			pstmt.setInt(2, new_order_media_id);
			pstmt.executeUpdate();
//			System.out.println(new_sequence1+"------"+old_sequence);
			if(new_sequence1>old_sequence){
				 for(int j=0;j<list.size();j++){  ////获取排序到某一个媒体前面的那个媒体的排序号
					 module=list.get(j);
					 if(module.getSequence()>=old_sequence && module.getSequence()<new_sequence1){
						    int new_sequencetemp=module.getSequence();
							pstmt=con.prepareStatement(sql1);
							pstmt.setInt(1, new_sequencetemp+1);
							pstmt.setInt(2, module.getId());
							pstmt.executeUpdate();
					 }
				 }
			}else{
				for(int j=0;j<list.size();j++){  ////获取排序到某一个媒体前面的那个媒体的排序号
					 module=list.get(j);
					 if(module.getSequence()<=old_sequence && module.getSequence()>new_sequence1){
						    int new_sequencetemp=module.getSequence();
							pstmt=con.prepareStatement(sql1);
							pstmt.setInt(1, new_sequencetemp-1);
							pstmt.setInt(2, module.getId());
							pstmt.executeUpdate();
					 }
				 }
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("排序出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return i;
	}
	
	public void delMedia_sequence(int mid,int moudleid ){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Module module=null;
		List<Module> list=new ArrayList<Module>();
		try{
			String sql="select id,sequence from xct_module_media where module_id=?";
			String sql1="update xct_module_media set sequence=? where id =?";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setInt(1, moudleid);
			 rs=pstmt.executeQuery();
			 while(rs.next()) { 
				 module= new Module();
				 module.setId(rs.getInt("id"));
				 module.setSequence(rs.getInt("sequence"));
				list.add(module);
			 }
			 int del_sequence=0;
			 for(int j=0;j<list.size();j++){  ////获取排序到某一个媒体前面的那个媒体的排序号
				 module=list.get(j);
				 if(mid==module.getId()){
					 del_sequence=module.getSequence();
					 break;
				 }
			 } 
			 for(int j=0;j<list.size();j++){  ////获取排序到某一个媒体前面的那个媒体的排序号
				 module=list.get(j);
				 if(module.getSequence()>del_sequence){
					 int new_sequence=module.getSequence();
						pstmt=con.prepareStatement(sql1);
						pstmt.setInt(1, new_sequence-1);
						pstmt.setInt(2, module.getId());
						pstmt.executeUpdate();
				 }
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("删除媒体时排序出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
	}
}
