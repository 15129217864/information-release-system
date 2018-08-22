package com.xct.cms.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.livemeeting.util.LivemeetingUtil;

public class LiveMeetingAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String opString=request.getParameter("op");
		if("insnquametting".equals(opString)){//ÆÖ¶«¼ìÑé¼ìÒß¾Ö
			LivemeetingUtil livemeetingutil=new LivemeetingUtil();
			request.setAttribute("meetinglist", livemeetingutil.getInspectionQuarantineBeanList());
			return mapping.findForward("insnquametting");
		}else{
			LivemeetingUtil livemeetingutil=new LivemeetingUtil();
			request.setAttribute("meetinglist", livemeetingutil.getAllProgramMeetingSanLing(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			return mapping.findForward("livemeeting");
		}
	}
}
