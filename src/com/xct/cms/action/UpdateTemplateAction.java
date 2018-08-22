package com.xct.cms.action;

import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.xct.cms.dao.LogsDAO;
import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Users;
import com.xct.cms.form.CreateTemplateForm;
import com.xct.cms.utils.UtilDAO;

public class UpdateTemplateAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		 Users user = (Users) request.getSession().getAttribute("lg_user");
		 String templateid=request.getParameter("templateid");
		 UtilDAO utildao= new  UtilDAO();	
		CreateTemplateForm template= (CreateTemplateForm)form;
		String template_name=template.getTemplate_name();
		String template_background=template.getTemplate_background();
		String template_type=template.getTemplate_type();
		String template_expla=template.getTemplate_expla();
		int template_width=template.getTemplate_width();
		int template_height=template.getTemplate_height();
		String template_module=template.getTemplate_module();
		
		DBConnection dbconn=new DBConnection();
		Connection conn=dbconn.getConection();
		
		Map map= utildao.getMap();
		map.put("template_id", templateid);
		map.put("template_name", template_name);
		map.put("template_type", template_type);
		map.put("template_width", template_width+"");
		map.put("template_height", template_height+"");
		map.put("template_expla", template_expla);
		map.put("template_background", template_background);
		utildao.updateinfo(conn,map, "xct_template");
		utildao.deleteinfo(conn,"template_id", templateid, "xct_module");
		String module[]=template_module.split("!");
		for(int i=0;i<module.length;i++){
			String moduleAttr[]=module[i].split(",");
			String left=moduleAttr[0];
			String top=moduleAttr[1];
			String width=moduleAttr[2];
			String height=moduleAttr[3];
			map= utildao.getMap();
			map.put("area_id", i+1+"");
			map.put("m_left", left+"");
			map.put("m_top", top+"");  
			map.put("m_width", width+"");  
			map.put("m_height", height+"");  
			map.put("template_id", templateid);  
			map.put("m_filetype", "");  
			map.put("media_id", ""); 
			utildao.saveinfo(conn,map, "xct_module");
		}
		new LogsDAO().addlogs1(conn,user.getName(), new StringBuffer().append("用户【").append(user.getName()).append("】修改了模板：【").append(template_name).append("】").toString(), 1);
		dbconn.returnResources(conn);
		return mapping.findForward("updateOk");
	}

}
