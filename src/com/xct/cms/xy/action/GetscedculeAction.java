/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.xct.cms.xy.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.dao.TerminalDAO;
import com.xct.cms.domin.Terminal;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.Util;
import com.xct.cms.xy.dao.getStaksDao;


/** 
 * MyEclipse Struts
 * Creation date: 09-20-2010
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class GetscedculeAction extends Action {
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out=null;
	    try {
			out = response.getWriter();  
			request.setCharacterEncoding("UTF-8"); //���������ַ���ΪUTF-8
		} catch (IOException e) {
			e.printStackTrace();
		}
		String action = request.getParameter("action"); //��ȡaction��Ϣ
		
		String left_menu=request.getParameter("left_menu");
		String zuid=request.getParameter("zuid");
		String cnt_mac=request.getParameter("cnt_mac");
		
		if ("getTasks".equals(action)) {//��ȡ����������Ϣ
	        String month = request.getParameter("month");
	        String str="";
	        
	        if("ZU".equals(left_menu)&&!"1".equals(zuid)){
	    	 	str=" and cnt_zuid="+zuid+" ";
	    	 }else if("CNT".equals(left_menu)){
	    	     str=" and program_delid ='"+cnt_mac+"' ";
	        }
	        //System.out.println(str);
	        String result =new getStaksDao().getTasks(month,str+" order by program_SetDate ");
	        out.println(result);
	    }
		out.flush();
		out.close();
		return null;
	}
}