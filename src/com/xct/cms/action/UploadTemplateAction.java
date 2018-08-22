package com.xct.cms.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.Util;
import com.xct.cms.utils.UtilDAO;

public class UploadTemplateAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String template_name =Util.getGBK(request.getParameter("template_name")==null?"Î´ÖªÄ£°åÃû³Æ":request.getParameter("template_name"));
		String template_file=request.getParameter("template_file")==null?"0000000000000":request.getParameter("template_file");
		UtilDAO utildao= new  UtilDAO();
		String nowtime =utildao.getNowtime("yyyy-MM-dd HH:mm:ss");
		Map map= utildao.getMap();
		map.put("template_name", template_name);
		map.put("template_file", template_file);
		map.put("template_xml", new StringBuffer().append(template_file).append(".xml").toString());
		map.put("template_img", new StringBuffer().append(template_file).append(".jpg").toString());
		map.put("template_create_name", "888");
		map.put("create_time", nowtime);
		boolean bool=utildao.saveinfo(map, "xct_template");
		if(bool){
			String projectpath=FirstStartServlet.projectpath;
			Util.copyFolder(new StringBuffer().append(projectpath).append("serverftp\\template_file\\").append(template_file).toString(), projectpath+"admin\\template\\"+template_file);
			Util.deleteFile(new StringBuffer().append(projectpath).append("serverftp\\template_file\\").append(template_file).toString());
		}
		return null;
	}
}
