/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.xct.cms.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.utils.UtilDAO;

/** 
 * MyEclipse Struts
 * Creation date: 08-24-2010
 * 
 * XDoclet definition:
 * @struts.action scope="request" validate="true"
 */
public class ReceivingClientCmdAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String cnt_ip=request.getRemoteAddr();
		String cnt_cmd=request.getParameter("cmd")==null?"":request.getParameter("cmd");
		String cnt_cmdstatus=request.getParameter("cmdStatus")==null?"_":request.getParameter("cmdStatus");
		String cnt_cmdResult=request.getParameter("cmdResult")==null?"":request.getParameter("cmdResult");
		String cnt_programurl=request.getParameter("programurl")==null?"":request.getParameter("programurl");
		UtilDAO utildao= new  UtilDAO();
		String nowtime =utildao.getNowtime("yyyy-MM-dd HH:mm:ss");
		Map map= utildao.getMap();
		map.put("cnt_ip", cnt_ip);
		map.put("cnt_cmd", cnt_cmd);
		map.put("cnt_cmdstatus", cnt_cmdstatus);
		map.put("cnt_cmdresult", cnt_cmdResult);
		map.put("cnt_programurl", cnt_programurl);
		map.put("cnt_sendtime", nowtime);
		utildao.saveinfo(map, "xct_cnt_response");
		return null;
	}
}