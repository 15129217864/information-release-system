package com.xct.cms.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.dao.ModuleDAO;

public class UdateBackgroundAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String op=request.getParameter("op");
		String bgcolor=request.getParameter("bgcolor");
		String moduleid=request.getParameter("moduleid");
//		System.out.println(op+"__"+bgcolor+"__"+moduleid);
		ModuleDAO moduledao=new ModuleDAO();
		if("htmltext".equals(op)){
			moduledao.updateModuleById("background", bgcolor, moduleid);
		}
		return null;
	}
}
