package com.xct.cms.action;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.servlet.FirstStartServlet;


public class UpdateClientIPAndPortAction extends Action {
	
	Logger logger = Logger.getLogger(UpdateClientIPAndPortAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		
		    String serviceip=request.getParameter("serviceip").trim();
		    String serviceport=request.getParameter("serviceport").trim();
		    String clientip = request.getParameter("clientip").trim();
		    
		    StringBuffer sBuffer=new StringBuffer();
		    sBuffer.append("ip:").append(serviceip).append(":").append(serviceport);
		    
//		    System.out.println(clientip+"__"+sBuffer.toString());
		    
		    FirstStartServlet.client.allUDPsend(clientip, sBuffer.toString());
		    try {
				response.getWriter().print("OK");
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return null;
	}
}
