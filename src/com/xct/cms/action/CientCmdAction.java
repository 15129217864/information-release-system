package com.xct.cms.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CientCmdAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		 String cmd=request.getParameter("cmd")==null?"":request.getParameter("cmd");
		 String ipmac=request.getParameter("ipmac")==null?"":request.getParameter("ipmac");
		 PrintWriter out=null;
		 try {
			out = response.getWriter();
			out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(null!=out){
					out.close();
				}
			}
		 
		return null;
	}
}
