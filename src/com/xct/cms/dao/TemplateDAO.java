package com.xct.cms.dao;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.excel.util.OpExcelUtils;
import com.linsoft.office.JacobUtil;
import com.linsoft.office.Office;
import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Media;
import com.xct.cms.domin.Module;
import com.xct.cms.domin.Template;
import com.xct.cms.livemeeting.util.LivemeetingUtil;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.Pdf2Image;
import com.xct.cms.utils.Util;
import com.xct.cms.utils.UtilDAO;

public class TemplateDAO extends DBConnection {
	Logger logger = Logger.getLogger(TemplateDAO.class);
	
	/**
	 * 获取所有模板 MESH@2012
	 * @return
	 */
	public List getAlltemplate(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Template template=null;
		List<Template> list=null;
		try{
			String sql=new StringBuffer().append("select * from xct_template ").append(str).toString();
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			list=new ArrayList<Template>();
			while(rs.next()){
				template= new Template();
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
				list.add(template);
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("获取所有模板出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	
	//复制模版
	public boolean copyTemplateB(String newtemplatename,String templateid){
		
		String template_id=new  UtilDAO().buildId("xct_template", "template_id", "t.0000001");
		
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Template template=null;
		try{
			String sql=new StringBuffer().append("select * from xct_template where template_id='").append(templateid).append("'").toString();
			String sql2=new StringBuffer().append("select * from xct_module where template_id='").append(templateid).append("' order by area_id").toString();

			String insertsql="insert into xct_template (template_id,template_name,template_type,template_width,template_height,template_expla,template_background,template_backmusic,template_create_name,create_time) values (?,?,?,?,?,?,?,?,?,?)";
			
			String insertsql2="insert into xct_module (area_id,m_left,m_top,m_width,m_height,template_id,m_filetype,media_id) values (?,?,?,?,?,?,?,?)" ;
//			logger.info("------copyTemplateB------"+sql);
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				template= new Template();
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
			}
			//========================================================
			List<Module>modulelist=new ArrayList<Module>();
			pstmt=con.prepareStatement(sql2);
			rs=pstmt.executeQuery();
			while(rs.next()){
				Module module=new Module();
				module.setArea_id(rs.getInt("area_id"));
				module.setM_left(rs.getInt("m_left"));
				module.setM_top(rs.getInt("m_top"));
				module.setM_width(rs.getInt("m_width"));
				module.setM_height(rs.getInt("m_height"));
				module.setTemplate_id(rs.getString("template_id"));
				module.setM_filetype(rs.getString("m_filetype"));
				module.setMedia_id(rs.getString("media_id"));
				modulelist.add(module);
			}
			con.setAutoCommit(false);
			
            //========================================================
			pstmt=con.prepareStatement(insertsql);
			pstmt.setString(1,template_id);
			pstmt.setString(2,newtemplatename);
			pstmt.setInt(3,template.getTemplate_type());
			pstmt.setInt(4,template.getTemplate_width());
			pstmt.setInt(5,template.getTemplate_height());
			pstmt.setString(6,template.getTemplate_expla());
			pstmt.setString(7,template.getTemplate_background());
			pstmt.setString(8,template.getTemplate_backmusic());
			pstmt.setString(9,template.getTemplate_create_name());
			pstmt.setString(10,template.getCreate_time());
			pstmt.executeUpdate();
			//=======================================================
			pstmt=con.prepareStatement(insertsql2);
			for (Module module : modulelist) {
				pstmt.setInt(1,module.getArea_id());
				pstmt.setInt(2,module.getM_left());
				pstmt.setInt(3,module.getM_top());
				pstmt.setInt(4,module.getM_width());
				pstmt.setInt(5,module.getM_height());
				pstmt.setString(6,template_id);
				pstmt.setString(7,module.getM_filetype());
				pstmt.setString(8,module.getMedia_id());
				pstmt.executeUpdate();
			}
			con.commit();
			return true;
		}catch(Exception e){
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error(new StringBuffer().append("获取模板出错！==copyTemplateB===").append(e.getMessage()).toString());
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		return false;
	}
	
//  ('20160125164709','20160125164947')
	public String getTemplageNameByID(String  jmurl){
		String program_sql="select * from  xct_template where template_id in("+jmurl+")";
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		StringBuffer sBuffer=new StringBuffer();
		try {
			pstmt=con.prepareStatement(program_sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				sBuffer.append(rs.getString("template_name")).append(" ,");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			returnResources(rs,pstmt,con);
		}
		if(sBuffer.indexOf(",")!=-1)return sBuffer.substring(0,sBuffer.length()-1);
		return sBuffer.toString();
	}
	
	
	/**
	 * 获取所有屏幕
	 * @return
	 */
	public List<Template> getAllScreenType(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Template template=null;
		List<Template> list=null;
		try{
			String sql="select * from screen_type "+str;
			 pstmt=con.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			list=new ArrayList<Template>();
			while(rs.next()){
				template= new Template();
				template.setS_id(rs.getInt("id"));
				template.setS_title(rs.getString("s_title"));
				template.setS_width(rs.getInt("s_width"));
				template.setS_height(rs.getInt("s_height"));
				template.setAll_width(rs.getInt("all_width"));
				template.setAll_height(rs.getInt("all_height"));
				template.setS_type(rs.getInt("s_type"));
				list.add(template);
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("获取所有屏幕类型出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return list;
	}
	/**
	 * 获取所有屏幕
	 * @return
	 */
	public Template getScreenTypeBySid(String sid){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Template  template= new Template();
		try{
			String sql="select * from screen_type where id =?";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setInt(1, Integer.parseInt(sid));
			 rs=pstmt.executeQuery();
			if(rs.next()){
				template.setS_id(rs.getInt("id"));
				template.setS_title(rs.getString("s_title"));
				template.setS_width(rs.getInt("s_width"));
				template.setS_height(rs.getInt("s_height"));
				template.setAll_width(rs.getInt("all_width"));
				template.setAll_height(rs.getInt("all_height"));
				template.setS_type(rs.getInt("s_type"));
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("获取所有屏幕类型出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return template;
	}
	
	/**
	 * 
	 * 根据模板ID获取模板
	 * @param template_id
	 * @return
	 */
	public Template getTemplateById(String template_id){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Template template=null;
		try{
			String sql="select * from xct_template where template_id=?";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, template_id);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				template= new Template();
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
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID获取模板出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return template;
	}
	public Template getTemplateById1(String template_id){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Template template=null;
		try{
			String sql=new StringBuffer().append("select xct_template.id as vid,template_id,template_name,template_type,template_width,template_height,template_expla,template_background,template_backmusic,template_create_name,")
					.append("create_time, screen_type.id as s_id,s_title,s_width,s_height ")
							.append("from xct_template, screen_type where screen_type.id=xct_template.template_type and  template_id=?").toString();
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, template_id);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				template= new Template();
				template.setId(rs.getInt("vid"));
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
				
				template.setS_id(rs.getInt("s_id"));
				template.setS_title(rs.getString("s_title"));
				template.setS_width(rs.getInt("s_width"));
				template.setS_height(rs.getInt("s_height"));
			}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID获取模板出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return template;
	}
	/**
	 * 
	 * 根据模板ID获取模板
	 * @param template_id
	 * @return
	 */
	public Template getTemplateById(Connection con,String template_id){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Template template=null;
		try{
			String sql="select * from xct_template where template_id=?";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, template_id);
			 rs=pstmt.executeQuery();
			if(rs.next()){
				template= new Template();
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
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID获取模板出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return template;
	}/**
	 * 
	 * 根据模板ID获取模板
	 * @param template_id
	 * @return
	 */
	public Template getTemplateTempById(String template_id){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Template template=null;
		try{
			String sql="select * from xct_template_temp where template_id=?";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, template_id);
			 rs=pstmt.executeQuery();
			while(rs.next()){
				template= new Template();
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
				
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID获取模板出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return template;
	}public Template getTemplateTempById(Connection con ,String template_id){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Template template= new Template();
		try{
			String sql="select * from xct_template_temp where template_id=?";
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, template_id);
			 rs=pstmt.executeQuery();
			while(rs.next()){
				template= new Template();
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
				
				}
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID获取模板出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
		return template;
	}
	/**
	 * 根据模板ID复制一个模板和一个模板的模块到xct_template_temp表和xct_module
	 * @param template_id
	 * @return
	 */
	public void addTemplateToTempByTemplateid(String newtemplate_id,String template_id){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try{
			String nowtime=UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
			String sql=new StringBuffer().append("insert into xct_template_temp (template_id,template_name,template_type,template_width,template_height,")
					.append("template_expla,template_background,template_backmusic,template_create_name,create_time,type) ")
					.append("select ?,template_name,template_type,template_width,template_height,")
					.append("template_expla,template_background,template_backmusic,template_create_name,'").append(nowtime).append("',0")
					.append(" from xct_template where template_id=?").toString();
			
			String sql2=new StringBuffer().append("insert into xct_module_temp(area_id,m_left,m_top,m_width,m_height,template_id,m_filetype,m_other,m_text,name,background,foreground,span,fontName,fontTyle,fontSize,alpha,state)")
					.append("select area_id,m_left,m_top,m_width,m_height,?,m_filetype,'',' ','','-16776961','-1','30000','宋体','1','20','1.0',0 from xct_module where template_id=?").toString();
			 
			String updatesql=new StringBuffer().append("update xct_template_temp set parent_template_id='").append(template_id).append("' where template_id='").append(newtemplate_id).append("'").toString();
			
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, newtemplate_id);
			 pstmt.setString(2, template_id);
			 pstmt.executeUpdate();
			 
			 pstmt=con.prepareStatement(updatesql);
			 pstmt.executeUpdate();
			 
			 pstmt=con.prepareStatement(sql2);
			 pstmt.setString(1, newtemplate_id);
			 pstmt.setString(2, template_id);
			 pstmt.executeUpdate();
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID<").append(template_id).append(">复制模板出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
	}
	
	public void addTemplateToTempByTemplateid(Connection con,String newtemplate_id,String template_id,String jm_templateid){
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try{
			String nowtime=UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
			String sql=new StringBuffer().append("insert into xct_template_temp (template_id,template_name,template_type,template_width,template_height,")
					.append("template_expla,template_background,template_backmusic,template_create_name,create_time,type) ")
					.append("select ?,template_name,template_type,template_width,template_height,")
					.append("template_expla,template_background,template_backmusic,template_create_name,'").append(nowtime).append("',0")
					.append(" from xct_template where template_id=?").toString();
			
			String updatesql=new StringBuffer().append("update xct_template_temp set parent_template_id='").append(template_id).append("' where template_id='").append(newtemplate_id).append("'").toString();
			
			String sql2=new StringBuffer().append("insert into xct_module_temp(area_id,m_left,m_top,m_width,m_height,template_id,m_filetype,m_other,m_text,name,background,foreground,span,fontName,fontTyle,fontSize,alpha,state)")
					.append("select area_id,m_left,m_top,m_width,m_height,?,m_filetype,'',' ','','-16776961','-1','30000','宋体','1','20','1.0',0 from xct_module where template_id=?").toString();
			
			String selectsql0=new StringBuffer().append("select * from xct_module_temp where template_id ='").append(newtemplate_id).append("' order by area_id asc").toString();
			
			//查出旧的模块属性
			String selectsql=new StringBuffer().append("select * from xct_module_temp where template_id ='").append(jm_templateid).append("' order by area_id asc").toString();
            //修改成新的模块属性
			String updatesql2="update xct_module_temp set m_filetype=?,m_other=?,m_text=?,name=?,background=?,foreground=?,span=?,fontName=?,fontTyle=?,fontSize=?,alpha=?,state=?  where id=? and template_id =?";
           
			 String updatemediasql="update xct_module_media set module_id=? where module_id=? ";
			//=========================================================================================================
			
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, newtemplate_id);
			 pstmt.setString(2, template_id);
			 pstmt.executeUpdate();
			 
			 pstmt=con.prepareStatement(updatesql);
			 pstmt.executeUpdate();
			 
			 pstmt=con.prepareStatement(sql2);
			 pstmt.setString(1, newtemplate_id);
			 pstmt.setString(2, template_id);
			 pstmt.executeUpdate();
			 //===============================================================================
			 pstmt=con.prepareStatement(selectsql0);
			 rs=pstmt.executeQuery();
			 List<Integer>moudleidlist=new ArrayList<Integer>();
			 while(rs.next()){
				 moudleidlist.add(rs.getInt("id"));
			 }
			 int newtemplatemodulecount=moudleidlist.size();//获取新模版的模块id数量
			 
//			 System.out.println("newtemplatemodulecount-------获取新模版的模块数量------->"+newtemplatemodulecount);
             //================================================================================
			 pstmt=con.prepareStatement(selectsql);
			 rs=pstmt.executeQuery();
			 List<Module>list=new ArrayList<Module>();
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
				 list.add(module);
			 }
			 int countflagI=0;
//			 System.out.println("-------获取旧模版的模块数量------->"+list.size());
			 
			 for (Module module : list) {//把旧模版的模块的部分属性修改到新模版的模块属性。   多模块变成少的模块，少的模块变成多的模块
				 if(countflagI<newtemplatemodulecount){
					 pstmt=con.prepareStatement(updatesql2);
					 
					 pstmt.setString(1, module.getM_filetype());
					 pstmt.setString(2, module.getM_other());
					 pstmt.setString(3, module.getM_text());
					 pstmt.setString(4, module.getName());
					 pstmt.setString(5, module.getBackground());
					 pstmt.setString(6, module.getForeground());
					 pstmt.setString(7, module.getSpan());
					 pstmt.setString(8, module.getFontName());
					 pstmt.setString(9, module.getFontTyle());
					 pstmt.setString(10, module.getFontSize());
					 pstmt.setString(11, module.getAlpha());
					 pstmt.setInt(12, module.getState());
					 //where
					 pstmt.setInt(13, moudleidlist.get(countflagI));
					 pstmt.setString(14, newtemplate_id);
					 
					 pstmt.executeUpdate();
					 //把原有的模块的就素材改成新模版模块的素材
					 pstmt=con.prepareStatement(updatemediasql);
					 pstmt.setInt(1, moudleidlist.get(countflagI));
					 pstmt.setInt(2, module.getId());
					 pstmt.executeUpdate();
					 
					 ++countflagI;
				 }
			}

		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID<").append(template_id).append(">复制模板出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt);
		}
	}
	/**
	 * 另存为一个节目模板和模块
	 * 
	 * @param newtemplate_id
	 * @param template_id
	 */
	public void saveAsTemplateByTemplateid(String newtemplate_id,String template_id){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try{
			String sql=new StringBuffer().append("insert into xct_template_temp (template_id,template_name,template_type,template_width,template_height,")
					.append("template_expla,template_background,template_backmusic,template_create_name,create_time,type) ")
							.append("select ?,template_name,template_type,template_width,template_height,")
									.append("template_expla,template_background,template_backmusic,template_create_name,create_time,1")
											.append(" from xct_template_temp where template_id=?").toString();
			
			String sql2=new StringBuffer().append("insert into xct_module_temp(area_id,m_left,m_top,m_width,m_height,template_id,m_filetype,m_other,m_text,name,background,foreground,span,fontName,fontTyle,fontSize,alpha,state)")
					.append("select area_id,m_left,m_top,m_width,m_height,template_id,m_filetype,m_other,m_text,name,background,foreground,span,fontName,fontTyle,fontSize,alpha,state from xct_module_temp where template_id=?").toString();
			 pstmt=con.prepareStatement(sql);
			 pstmt.setString(1, newtemplate_id);
			 pstmt.setString(2, template_id);
			 pstmt.executeUpdate();
			 
			 pstmt=con.prepareStatement(sql2);
			 pstmt.setString(1, newtemplate_id);
			 pstmt.setString(2, template_id);
			 pstmt.executeUpdate();
		}catch(Exception e){
			logger.error(new StringBuffer().append("根据模板ID<").append(template_id).append(">复制模板出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
	}
	
	public void saveTemplate(Template template,List moduleList) {
		File workHome = new File(template.getProgramePath());
		if (!workHome.exists())
			workHome.mkdir();
		Document document = DocumentHelper.createDocument();
		Element booksElement = document.addElement("xct");
		String mediabackimg=template.getTemplate_background();
		
		String htmltextbackimg=template.getHtmltextbackimg();
		String marketstockbackimg=template.getMarketstockbackimg();
		
		String mediabackMusic=template.getTemplate_backmusic();
		String programpath=FirstStartServlet.projectpath;
		String backimgName="NULL";
		String backMusicName="NULL";
		if(!"".equals(mediabackimg)){
			backimgName=mediabackimg.substring(mediabackimg.lastIndexOf("/")+1);
			Util.copyFile(programpath+mediabackimg,template.getProgramePath()+"/"+backimgName);
		}
		if(!"".equals(mediabackMusic)){
			boolean bool=true;
			for (int i = 0; i < moduleList.size(); i++) {
				Module module = (Module) moduleList.get(i);
				String mtype=module.getM_filetype();
				if("video".equals(mtype) || "iptv".equals(mtype)/* || "flash".equals(mtype)*/){
					bool=false;
					break;
				}
			}
			if(bool){
				backMusicName=mediabackMusic.substring(mediabackMusic.lastIndexOf("/")+1);
				Util.copyFile(programpath+mediabackMusic,template.getProgramePath()+"/"+backMusicName);
			}
		}
		booksElement.addAttribute("version", template.getVersion())
		        .addAttribute("playName", template.getProgramname())
				.addAttribute("backImage",backimgName)
				.addAttribute("backMusic",backMusicName)
				.addAttribute("width", template.getTemplate_width()+"")
				.addAttribute("height", template.getTemplate_height()+"")
				.addAttribute("rotate", template.getRotate());
		for (int i = 0; i < moduleList.size(); i++) {
			Module module = (Module) moduleList.get(i);
			Element element = booksElement.addElement("pane");
			writePaneBeanXML(element,module,template.getProgramUrl(),htmltextbackimg,marketstockbackimg/*,ch_enString*/);
		}
		XMLWriter writer;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("gbk");
		try {
			writer = new XMLWriter(new FileWriter(template.getProgramePath()+"/"+template.getProgramUrl()+".xml"), format);
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			logger.error(new StringBuffer().append("保存节目出错！=====").append(e.getMessage()).toString());
			e.printStackTrace();
		}
	}
	public static Element writePaneBeanXML(Element element, Module module,String jmurl,String htmltextbackimg,String marketstockbackimg/*,String ch_enString*/) {
		String mtype=module.getM_filetype();
		if("clock".equals(mtype)){
			mtype="tvclock";
		}
		element.addAttribute("name", module.getName());
		element.addAttribute("type", mtype);
		element.addAttribute("x", module.getM_left() + "");
		element.addAttribute("y", module.getM_top() + "");
		element.addAttribute("width", module.getM_width()+"");
		element.addAttribute("height", module.getM_height()+"");
		element.addAttribute("background", module.getBackground());
		element.addAttribute("foreground", module.getForeground());
		element.addAttribute("span", module.getSpan());
		element.addAttribute("fontName", module.getFontName());
		element.addAttribute("fontTyle", module.getFontTyle());
	    element.addAttribute("fontSize", module.getFontSize());
	    if("htmltext".equals(mtype)||"marketstock".equals(mtype)/*||"goldtrend".equals(mtype)*/){
	    	  if("htmltext".equals(mtype)){
	    		if(!"".equals(htmltextbackimg)){
		         element.addAttribute("alpha", htmltextbackimg);
	    	  }else {
		    	  element.addAttribute("alpha", "1.0");
	    	  }
	        }

		    if("marketstock".equals(mtype)){
		    	if(!"".equals(marketstockbackimg)){
			    element.addAttribute("alpha", marketstockbackimg);
	
//		      ModuleDAO moduledao= new ModuleDAO();
//		      if(null!=htmltextbackimg)
//		    	  moduledao.updateModuleTempByTemplateIdForAlpha(htmltextbackimg,module.getTemplate_id());
		    	}else {
		    	  element.addAttribute("alpha", "1.0");
				}
		    }
	    }else{
	      element.addAttribute("alpha", module.getAlpha());
	    }
		String programpath=FirstStartServlet.projectpath;
		
		 if("weather".equals(mtype)){
			Util.copyFile(programpath+"serverftp/module_file/"+module.getWeatherfile(),programpath+"serverftp/program_file/"+jmurl+"/"+module.getWeatherfile());
			element.addElement("file")
				        .addAttribute("fileName",module.getWeatherfile())
						.addAttribute("filePath",module.getWeatherfile())
						.addAttribute("length","8000");
		 }else if("weathersimple".equals(mtype)){//简洁的天气预报
			    String weatherString="今天：多云气温：21℃～27℃风力：西南风 3-4级#明天：多云气温：21℃～27℃风力：西南风 3-4级#后天：多云气温：21℃～29℃风力：西南风 3-4级";
			    Util.writeFiletoSingle(programpath+"serverftp/module_file/weather.txt", weatherString, false, false);
				Util.copyFile(programpath+"serverftp/module_file/weather.txt"/*+module.getWeatherfile()*/,programpath+"serverftp/program_file/"+jmurl+"/weather.txt"/*+module.getWeatherfile()*/);
				element.addElement("file")
					        .addAttribute("fileName",/*module.getWeatherfile()*/"weather.txt")
							.addAttribute("filePath",/*module.getWeatherfile()*/"weather.txt")
							.addAttribute("length","8000");
		}else if("iptv".equals(mtype)){
			Util.copyFile(programpath+"serverftp/module_file/"+module.getIwfile(),programpath+"serverftp/program_file/"+jmurl+"/"+module.getIwfile());
			element.addElement("file")
				        .addAttribute("fileName",module.getIwfile())
						.addAttribute("filePath",module.getIwfile())
						.addAttribute("length","8000");
			
		}else if("count_down".equals(mtype)){
			Util.copyFile(programpath+"serverftp/module_file/"+module.getIwfile(),programpath+"serverftp/program_file/"+jmurl+"/"+module.getIwfile());
			element.addElement("file")
				        .addAttribute("fileName",module.getIwfile())
						.addAttribute("filePath",module.getIwfile())
						.addAttribute("length","8000");
			
		}else if("kingentranceguard".equals(mtype)){//金山刷卡信息
			try {
				new File(programpath+"serverftp/program_file/"+jmurl+"/ICCardInfo.txt").createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			element.addElement("file")
				        .addAttribute("fileName","ICCardInfo.txt")
						.addAttribute("filePath","ICCardInfo.txt")
						.addAttribute("length","8000");
			
		}else if("nbqueuingmore".equals(mtype)){//宁波排队叫号综合屏
			Util.copyFile(programpath+"images/quening.jpg",programpath+"serverftp/program_file/"+jmurl+"/quening.jpg");
			element.addElement("file")
				        .addAttribute("fileName","quening.jpg")
						.addAttribute("filePath","quening.jpg")
						.addAttribute("length","8000");
			
		}/*else if("web".equals(mtype)){
			Util.copyFile(programpath+"serverftp/module_file/"+module.getIwfile(),programpath+"serverftp/program_file/"+jmurl+"/"+module.getIwfile());
			element.addElement("file")
				        .addAttribute("fileName",module.getIwfile())
						.addAttribute("filePath",module.getIwfile())
						.addAttribute("length","8000");
			
		}*/else if("htmltext".equals(mtype)){
			
			String htmlbackimgString=htmltextbackimg;
			File file=null;
			if(null!=htmltextbackimg&&!"".equals(htmltextbackimg)){
				file=new File(programpath+htmltextbackimg);
				if(file.exists()){
					htmlbackimgString=htmlbackimgString.substring(htmlbackimgString.lastIndexOf("/")+1);
//					System.out.println(programpath+"__"+htmlbackimgString+"__"+htmltextbackimg);
					
					Util.copyFile(programpath+htmltextbackimg,programpath+"serverftp/program_file/"+jmurl+"/"+htmlbackimgString);
				}
			}
			Util.copyFile(programpath+"serverftp/module_file/"+module.getM_other(),programpath+"serverftp/program_file/"+jmurl+"/"+module.getM_other());

			
			element.addElement("file")
				        .addAttribute("fileName",module.getM_other())
						.addAttribute("filePath",module.getM_other())
						.addAttribute("length","8000");
			
			if(null!=htmltextbackimg&&!"".equals(htmltextbackimg)&&file.exists()){
				element.addElement("file")
					        .addAttribute("fileName",htmlbackimgString)
							.addAttribute("filePath",htmlbackimgString)
							.addAttribute("length","8000");
			}
			
		}else if("nordermeeting".equals(mtype)){//联合利华非预定会议室
			
				Util.copyFile(programpath+"serverftp/module_file/"+module.getM_other(),programpath+"serverftp/program_file/"+jmurl+"/"+module.getM_other());
				element.addElement("file")
					        .addAttribute("fileName",module.getM_other())
							.addAttribute("filePath",module.getM_other())
							.addAttribute("length","8000");
		
		}else if("marketstock".equals(mtype)){//张家港国泰集团 股市,大宗商品,外汇
			
			String marketstockbackimgString=marketstockbackimg;
			File file=null;
			if(null!=marketstockbackimg&&!"".equals(marketstockbackimg)){
				file=new File(programpath+marketstockbackimg);
				if(file.exists()){
					marketstockbackimgString=marketstockbackimgString.substring(marketstockbackimgString.lastIndexOf("/")+1);
//					System.out.println(programpath+"__"+htmlbackimgString+"__"+htmltextbackimg);
					
					Util.copyFile(programpath+marketstockbackimg,programpath+"serverftp/program_file/"+jmurl+"/"+marketstockbackimgString);
				}
			}
			Util.copyFile(programpath+"serverftp/program_file/all.html",programpath+"serverftp/program_file/"+jmurl+"/"+module.getM_other());

			
			element.addElement("file")
				        .addAttribute("fileName",module.getM_other())
						.addAttribute("filePath",module.getM_other())
						.addAttribute("length","8000");
			
			if(null!=marketstockbackimg&&!"".equals(marketstockbackimg)&&file.exists()){
				element.addElement("file")
					        .addAttribute("fileName",marketstockbackimgString)
							.addAttribute("filePath",marketstockbackimgString)
							.addAttribute("length","8000");
			}
		}else if("htmeetingnotice".equals(mtype)){
			//System.out.println("web====="+programpath+"serverftp/module_file/"+module.getIwfile());
			LivemeetingUtil livemeetingutil=new LivemeetingUtil();
			livemeetingutil.saveXML(livemeetingutil.getAllProgramMeetingSanLing(new SimpleDateFormat("yyyy-MM-dd").format(new Date())),programpath+"serverftp/program_file/"+jmurl+"/");
//			Util.copyFile(programpath+"serverftp/module_file/"+module.getIwfile(),programpath+"serverftp/program_file/"+jmurl+"/"+module.getIwfile());
			element.addElement("file")
				        .addAttribute("fileName","lh-notice.xml")
						.addAttribute("filePath","lh-notice.xml")
						.addAttribute("length","8000");
			
		}else if("insnquametting".equals(mtype)){
			//System.out.println("web====="+programpath+"serverftp/module_file/"+module.getIwfile());
			LivemeetingUtil livemeetingutil=new LivemeetingUtil();
			livemeetingutil.saveInspectionQuarantineXML(livemeetingutil.getInspectionQuarantineBeanList(),programpath+"serverftp/program_file/"+jmurl+"/");
//			Util.copyFile(programpath+"serverftp/module_file/"+module.getIwfile(),programpath+"serverftp/program_file/"+jmurl+"/"+module.getIwfile());
			element.addElement("file")
				        .addAttribute("fileName","pdiq-notice.xml")
						.addAttribute("filePath","pdiq-notice.xml")
						.addAttribute("length","8000");
			
		}
		 ////////////////////////旺旺定制
		else if("web".equals(mtype)){
			//System.out.println("web====="+programpath+"serverftp/module_file/"+module.getIwfile());
			Util.copyFile(programpath+"serverftp/module_file/"+module.getIwfile(),programpath+"serverftp/program_file/"+jmurl+"/"+module.getIwfile());
			element.addElement("file")
				        .addAttribute("fileName",module.getIwfile())
						.addAttribute("filePath",module.getIwfile())
						.addAttribute("length","8000");
			
		}
		else if("weatherthree".equals(mtype)){
			//System.out.println("weatherthree====="+programpath+"serverftp/module_file/"+module.getW_weatherfile());
			Util.copyFile(programpath+"serverftp/module_file/"+module.getW_weatherfile(),programpath+"serverftp/program_file/"+jmurl+"/"+module.getW_weatherfile());
			element.addElement("file")
				        .addAttribute("fileName",module.getW_weatherfile())
						.addAttribute("filePath",module.getW_weatherfile())
						.addAttribute("length","8000");
		}else if("stock".equals(mtype)){
			//System.out.println("stock====="+programpath+"serverftp/program_file/DB/"+module.getStock_file());
			Util.copyFile(programpath+"serverftp/program_file/DB/"+module.getStock_file(),programpath+"serverftp/program_file/"+jmurl+"/"+module.getStock_file());
			element.addElement("file")
				        .addAttribute("fileName",module.getStock_file())
						.addAttribute("filePath",module.getStock_file())
						.addAttribute("length","8000");
		}else if("stockother".equals(mtype)){
			//System.out.println("stockother====="+programpath+"serverftp/program_file/DB/"+module.getStock_file());
			Util.copyFile(programpath+"serverftp/program_file/DB/"+module.getStock_file(),programpath+"serverftp/program_file/"+jmurl+"/"+module.getStock_file());
			element.addElement("file")
				        .addAttribute("fileName",module.getStock_file())
						.addAttribute("filePath",module.getStock_file())
						.addAttribute("length","8000");
		}else if("birthday".equals(mtype)){
			//System.out.println("birthday====="+programpath+"serverftp/program_file/DB/"+module.getStock_file());
			String [] files=module.getWant_files();
			Util.copyFile(programpath+"serverftp/program_file/DB/want-birthdayconf.xml",programpath+"serverftp/program_file/"+jmurl+"/want-birthdayconf.xml");
			for(int i=0;i<files.length;i++){
				Util.copyFile(programpath+"serverftp/program_file/DB/"+files[i],programpath+"serverftp/program_file/"+jmurl+"/"+files[i]);
				element.addElement("file")
					        .addAttribute("fileName",files[i])
							.addAttribute("filePath",files[i])
							.addAttribute("length","8000");
			}
		}else if("wwnotice".equals(mtype)){
			//System.out.println("wwnotice====="+programpath+"serverftp/program_file/DB/"+module.getStock_file());
			Util.copyFile(programpath+"serverftp/program_file/DB/"+module.getStock_file(),programpath+"serverftp/program_file/"+jmurl+"/"+module.getStock_file());
			element.addElement("file")
				        .addAttribute("fileName",module.getStock_file())
						.addAttribute("filePath",module.getStock_file())
						.addAttribute("length","8000");
		}else if("filialeSell".equals(mtype)){
			String [] files=module.getWant_files();
			Util.copyFile(programpath+"serverftp/program_file/DB/WWFilialeSell7.jpg",programpath+"serverftp/program_file/"+jmurl+"/WWFilialeSell7.jpg");
			Util.copyFile(programpath+"serverftp/program_file/DB/WWFilialeSell6.jpg",programpath+"serverftp/program_file/"+jmurl+"/WWFilialeSell6.jpg");
			for(int i=0;i<files.length;i++){
				Util.copyFile(programpath+"serverftp/program_file/DB/"+files[i],programpath+"serverftp/program_file/"+jmurl+"/"+files[i]);
				element.addElement("file")
					        .addAttribute("fileName",files[i])
							.addAttribute("filePath",files[i])
							.addAttribute("length","8000");
			}
			element.addElement("file").addAttribute("fileName","WWFilialeSell7.jpg").addAttribute("filePath","WWFilialeSell7.jpg").addAttribute("length","8000");
			element.addElement("file").addAttribute("fileName","WWFilialeSell6.jpg").addAttribute("filePath","WWFilialeSell6.jpg").addAttribute("length","8000");
		}else if("wwmilkdrink".equals(mtype)){
			String [] files=module.getWant_files();
			for(int i=0;i<files.length;i++){
				Util.copyFile(programpath+"serverftp/program_file/DB/"+files[i],programpath+"serverftp/program_file/"+jmurl+"/"+files[i]);
				element.addElement("file")
					        .addAttribute("fileName",files[i])
							.addAttribute("filePath",files[i])
							.addAttribute("length","8000");
			}
		}
		 
		List<Media> list = module.getMediaList();
		DBConnection dbc= new DBConnection();
		Connection conn=dbc.getConection();
		OpExcelUtils opexcelutils= new OpExcelUtils();
		
		for (int i = 0; i < list.size(); i++) {
			Media media=list.get(i);
			String filepath=media.getFile_path();
			String filename=media.getFile_name();
			String filenamesuffix=filename.substring(filename.lastIndexOf("."));
			String newfilepath=programpath+"serverftp/program_file/"+jmurl+"/";
			
			if("pdf".equals(mtype)){
				
				String pdffile=newfilepath+jmurl+filenamesuffix;
				String jmurlpdf=jmurl+"pdf";
				String pdfdir=newfilepath+jmurlpdf;
				
				Util.copyFile(programpath+filepath+filename,pdffile);
//				new Pdf2Image().PDF2JPG_RGB(pdffile, pdfdir);//如果图片上有文字，有毛刺
//				new Pdf2Image().PDF2JPG_ARGB(pdffile, pdfdir);//高清，但文字有毛刺
				new Pdf2Image().PDF2JPGPixels(pdffile, pdfdir);//实际像素
				
				Util.fileToZip(newfilepath+ jmurlpdf+".zip",pdfdir);
				
				
				String filelist=Util.getFileStrByFilepath(pdfdir);
				UtilDAO utildao= new UtilDAO();
				utildao.upeateinfo(conn," m_other='"+filelist+"'", "id="+module.getId(), "xct_module_temp");
				
				element.addElement("file")
		        .addAttribute("fileName",jmurlpdf+".zip")
				.addAttribute("filePath",jmurlpdf+".zip")
				.addAttribute("length","1000");

		     }else if("ppt".equals(mtype)){
				Util.copyFile(programpath+filepath+filename,newfilepath+jmurl+filenamesuffix);
				Office off = new Office();
				String jmurlppt=jmurl+"ppt";
//				Util.OPownpoint(programpath+"jar/",newfilepath+jmurl+".ppt", newfilepath+jmurlppt+"\\");//做成调用jar包执行
				off.chageFormat(newfilepath+jmurl+filenamesuffix, newfilepath+jmurlppt);//在64位上运行不行
				Util.fileToZip(newfilepath+ jmurlppt+".zip",newfilepath+jmurlppt);
				Util.writefile(newfilepath+ jmurlppt+".txt","data/"+jmurl+"/"+jmurl+filenamesuffix);
				
				String filelist=Util.getFileStrByFilepath(newfilepath+jmurlppt);
				UtilDAO utildao= new UtilDAO();
				utildao.upeateinfo(conn," m_other='"+filelist+"'", "id="+module.getId(), "xct_module_temp");
				
				element.addElement("file")
		        .addAttribute("fileName",jmurlppt+".zip")
				.addAttribute("filePath",jmurlppt+".zip")
				.addAttribute("length","1000");
				
				element.addElement("file")
		        .addAttribute("fileName",jmurl+filenamesuffix)
				.addAttribute("filePath",jmurl+filenamesuffix)
				.addAttribute("length","1000");
				
				element.addElement("file")
		        .addAttribute("fileName",jmurlppt+".txt")
				.addAttribute("filePath",jmurlppt+".txt")
				.addAttribute("length","1000");
				
			}else if("word".equals(mtype)){
				Util.copyFile(programpath+filepath+filename,newfilepath+jmurl+filenamesuffix);
				String nowtime="word_"+jmurl;
				String word_html="word_"+jmurl+".html";
				String word_files="word_"+jmurl+".files";
				JacobUtil.wordToHtml(10,newfilepath+jmurl+filenamesuffix,newfilepath + "/"+word_html);
			 	Util.createFile(newfilepath+nowtime);
			 	Util.copyFile(newfilepath + "/"+word_html,newfilepath+nowtime + "/"+word_html);
			 	Util.copyFolder(newfilepath + "/"+word_files,newfilepath+nowtime + "/"+word_files);
			 	Util.zip(newfilepath + nowtime+".zip",newfilepath+nowtime);  
			 	Util.deleteFile(newfilepath + "/"+word_html);
			 	Util.deleteFile(newfilepath + "/"+word_files);
			 	Util.deleteFile(newfilepath+jmurl+filenamesuffix);
				filename=nowtime+".zip";
				
				element.addElement("file")
		        .addAttribute("fileName",media.getMedia_title())
				.addAttribute("filePath",filename)
				.addAttribute("length",media.getM_play_time());
			}else if("excel".equals(mtype)){
				Util.copyFile(programpath+filepath+filename,newfilepath+jmurl+filenamesuffix);
				String nowtime="excel_"+jmurl;
				String excel_html="excel_"+jmurl+".html";
				String excel_files="excel_"+jmurl+".files";
				
				JacobUtil.excelToHtml(newfilepath+jmurl+filenamesuffix,newfilepath + excel_html);
			 	Util.createFile(newfilepath+nowtime);
			 	Util.copyFile(newfilepath + "/"+excel_html,newfilepath+nowtime + "/"+ excel_html);
			 	Util.copyFolder(newfilepath + "/"+excel_files,newfilepath+nowtime + "/"+excel_files);
			 	Util.zip(newfilepath + nowtime+".zip",newfilepath+nowtime);  
			 	Util.deleteFile(newfilepath + "/"+excel_html);
			 	Util.deleteFile(newfilepath + "/"+excel_files);
			 	Util.deleteFile(newfilepath+jmurl+filenamesuffix);
				filename=nowtime+".zip";
				
				element.addElement("file")
		        .addAttribute("fileName",media.getMedia_title())
				.addAttribute("filePath",filename)
				.addAttribute("length",media.getM_play_time());
				
			}else if("khmeetingnotice".equals(mtype)){
				
			       String sfileString=programpath+filepath+filename;
			       File file=new File(sfileString);
				   Util.copyFile(programpath+filepath+filename,newfilepath+file.getName());
				   
				   element.addElement("file")
			        .addAttribute("fileName",media.getMedia_title())
					.addAttribute("filePath",filename)
					.addAttribute("length",media.getM_play_time());
				   
				   List<String> listtemp= opexcelutils.createKHXML(newfilepath,newfilepath+file.getName());
				   
	                for(int index=0,n=listtemp.size();index<n;index++){
	                	element.addElement("file")
	                	.addAttribute("fileName",listtemp.get(index))
	                	.addAttribute("filePath",listtemp.get(index))
	                	.addAttribute("length","8000");
	                }
	                listtemp.clear();
				
			 }else if("policesubstation".equals(mtype)){
					
			       String sfileString=programpath+filepath+filename;
			       File file=new File(sfileString);
				   Util.copyFile(programpath+filepath+filename,newfilepath+file.getName());
				   
				   element.addElement("file")
			        .addAttribute("fileName",media.getMedia_title())
					.addAttribute("filePath",filename)
					.addAttribute("length","8000");
				   
				     opexcelutils.getPolicesubstation(newfilepath,file.getPath(),"police_substation.xml");
				   
                	element.addElement("file")
                	.addAttribute("fileName","police_substation.xml")
                	.addAttribute("filePath","police_substation.xml")
                	.addAttribute("length","8000");

			 }/*else if("scroll".equals(mtype)){
				if(media.getMedia_title().indexOf("scroll_")>-1){
					Util.copyFile(programpath+filepath+filename,newfilepath+filename);
					element.addElement("file")
					        .addAttribute("fileName",media.getMedia_title())
							.addAttribute("filePath",filename)
							.addAttribute("length",media.getM_play_time());
					UtilDAO utildao= new UtilDAO();
					utildao.deleteinfo("media_id",media.getMedia_id(),"xct_media");
					Util.deleteFile(programpath+filepath+filename);
				}
			}*/
			else{
				Util.copyFile(programpath+filepath+filename,newfilepath+filename);
				element.addElement("file")
				        .addAttribute("fileName",media.getMedia_title())
						.addAttribute("filePath",filename)
						.addAttribute("length",media.getM_play_time());
			}
		}
		opexcelutils.setSTEP(100);
		dbc.returnResources(conn);
		
		return element;
	}
	
	public static void main(String [] args){
		
		/*TemplateDAO templdatedao= new TemplateDAO();
		ModuleDAO moduledao= new ModuleDAO();
		Template template=templdatedao.getTemplateTempById("t.000000003");
		List moduleList=moduledao.getModuleMediaByTemplateId("t.000000003");
		template.setVersion("5.5");
		template.setProgramname("测试节目");
		template.setRotate("0");
		template.setProgramePath("c:\\");
		template.setProgramUrl("20202020");
		
		
		new TemplateDAO().saveTemplate(template, moduleList);*/
		JacobUtil.excelToHtml("D:\\tomcat\\tomcat\\webapps\\xctSer\\serverftp\\program_file\\20101208161730\\20101208161730.xls","D:\\tomcat\\xctSer_tomcat\\webapps\\xctSer\\serverftp\\program_file\\20101208161730\\excel2003.html");
	}
	
}
