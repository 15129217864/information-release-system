package com.xct.cms.action;

import java.awt.Color;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class SendNoticeAction extends Action {
	
	private String url = "";
	private String resip[];
	String opp;
	Logger logger = Logger.getLogger(RequestClientOperatingAction.class);
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		
//		Users user=(Users)request.getSession().getAttribute("lg_user");
//		LogsDAO logsdao= new LogsDAO();
		
		String cmd = request.getParameter("cmd");
		opp = request.getParameter("opp") == null ? "" : request.getParameter("opp");
		String resips = request.getParameter("checkips") == null ? "!": request.getParameter("checkips");
		
		String scroll_family=request.getParameter("fontName");
		String scroll_style=request.getParameter("fontTyle");
		String scroll_size=request.getParameter("fontSize");
		String scroll_span=request.getParameter("span");
		String scroll_alpha=request.getParameter("alpha");
		
//		String scrollfg=request.getParameter("scrollfg");
		String scrollfgRGB=request.getParameter("scrollfgRGB");//颜色值不能是负值，否则报异常
//		String scrollbg=request.getParameter("scrollbg");
		String scrollbgRGB=request.getParameter("scrollbgRGB"); //颜色值不能是负值，否则报异常
		
//		scrollfgRGB==============11=========>-5_-15_83
//		scrollbgRGB==============22=========>-5_-14_85
		
		String foreground="";
		String background="";
       
		if(scrollbgRGB.indexOf("_")<0){
		 	background=scrollbgRGB;
		}else{
			String[] bgs=scrollbgRGB.split("_");
			Color color= new Color(Math.abs(Integer.parseInt(bgs[0])),Math.abs(Integer.parseInt(bgs[1])),Math.abs(Integer.parseInt(bgs[2])));
		 	background=color.getRGB()+"";
		}
		if(scrollfgRGB.indexOf("_")<0){
		 	foreground=scrollfgRGB;
		}else{
			String[] fgs=scrollfgRGB.split("_");
			Color color1= new Color(Math.abs(Integer.parseInt(fgs[0])),Math.abs(Integer.parseInt(fgs[1])),Math.abs(Integer.parseInt(fgs[2])));
			foreground=color1.getRGB()+"";
		}
		StringBuffer sb=new StringBuffer(opp);
	    String  somebody=new StringBuffer(scroll_family).append("###").append(scroll_style).append("###").append(scroll_size)
	                                                     .append("###").append(scroll_span).append("###").append(scroll_alpha)
	                                                     .append("###").append(foreground).append("###").append(background).append("###").toString();
	    String string= sb.insert(7, somebody).toString();
	    
	    // lv0024@宋体###1###40###20###0.1###-1###-16777012###5###hello，我是滚动精灵！

	    //System.out.println(string);
		//resip = resips.split("!");
		//for (int i = 1; i < resip.length; i++) {
					//FirstStartServlet.client.allsend(resip[i], string);
		//}
		//logsdao.addlogs(user.getLg_name(), "<span>用户"+user.getLg_name()+"向终端"+resips.replace("!", ",")+"发送了文字通知</span>", 1);
		request.setAttribute("opp", string);
		request.setAttribute("cmd", cmd);
		request.setAttribute("checkips", resips);
		return mapping.findForward(cmd);
	}
}
