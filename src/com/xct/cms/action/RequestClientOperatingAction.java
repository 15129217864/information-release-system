/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.xct.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.dao.TerminalDAO;
import com.xct.cms.domin.Terminal;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.Util;
import com.xct.cms.utils.UtilDAO;


public class RequestClientOperatingAction extends Action {
	
	private String resip[];
	private String opp="";
	Logger logger = Logger.getLogger(RequestClientOperatingAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		
		//Users user=(Users)request.getSession().getAttribute("lg_user");
		//LogsDAO logsdao= new LogsDAO();
		String cmd=request.getAttribute("cmd")==null?"":request.getAttribute("cmd").toString();
		if("".equals(cmd)){
			cmd = request.getParameter("cmd") == null ? "": request.getParameter("cmd");
		}
		
		opp=request.getAttribute("opp")==null?"":request.getAttribute("opp").toString();
		if("".equals(opp)){
			opp = request.getParameter("opp") == null ? "": request.getParameter("opp");
		}
		String resips=request.getAttribute("checkips")==null?"":request.getAttribute("checkips").toString();
		if("".equals(resips)){
			resips = request.getParameter("checkips") == null ? "!": request.getParameter("checkips");
		}
		
		String programName=UtilDAO.getGBK(request.getParameter("programName"));
		String cntmac= request.getParameter("cntmac") == null ? "!": request.getParameter("cntmac");
			
		resip = resips.replaceAll("\\s+","").split("!");
		if(opp.indexOf("lv0101")>-1){
			opp=Util.getGBK(opp).replace("!","#");
		}
		/*if(opp.indexOf("lv0024")>-1){
			for (int i = 1; i < resip.length; i++) {
				FirstStartServlet.client_result_states.remove(resip[i]+"_lv0024");
			}
		}else if(opp.indexOf("lv0014")>-1){
			for (int i = 1; i < resip.length; i++) {
				FirstStartServlet.client_result_states.remove(resip[i]+"_lv0014");
			}
		}else if(opp.indexOf("lv0023")>-1){
			for (int i = 1; i < resip.length; i++) {
				FirstStartServlet.client_result_states.remove(resip[i]+"_lv0023");
			}
		}else if(opp.indexOf("lv0036")>-1){
			for (int i = 1; i < resip.length; i++) {
				FirstStartServlet.client_result_states.remove(resip[i]+"_lv0036");
			}
		}else{
			for (int i = 1; i < resip.length; i++) {
				FirstStartServlet.client_result_states.remove(resip[i]+"_"+opp);
			}
		}*/
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < resip.length; i++) {
					if(!"".equals(resip[i])){
						String []clientarray=resip[i].split("_");
					    FirstStartServlet.client.allsend(clientarray[1],clientarray[0], opp);
					}
				}
			}
		}).start();
		String oppp=opp;
		if(opp.indexOf("lv0024")>-1){
			if(opp.indexOf("###")>-1){
				request.setAttribute("notice_content", opp.substring(opp.lastIndexOf("###")+3));
			}
			oppp="lv0024";
		}else if(opp.indexOf("lvled03")>-1){
			if(opp.indexOf("###")>-1){
				request.setAttribute("notice_content", opp.substring(opp.lastIndexOf("###")+3));
			}
			oppp="lvled03";
		}else if(opp.indexOf("lv0014")>-1){
			oppp="lv0014";
		}else if(opp.indexOf("lv0023")>-1){
			oppp="lv0023";
		}else if(opp.indexOf("lv0036")>-1){
			oppp="lv0036";
		}else if(opp.indexOf("lv0037")>-1){
			oppp="lv0037";
		}else if(opp.indexOf("lv0101")>-1){
			oppp="lv0101";
		}else if(opp.indexOf("lv0103")>-1){
			oppp="lv0103";
		}
		
		List<Terminal> t_list= new ArrayList<Terminal>();
		Map<String,Terminal> cnt_map=FirstStartServlet.terminalMap;
		for(Map.Entry<String, Terminal>enty: cnt_map.entrySet()){
			Terminal t=enty.getValue();
			for (int i = 0; i < resip.length; i++) {
				if(!"".equals(resip[i])){
					if(t.getCnt_mac().equals(resip[i].split("_")[1])){
	//					if(t.getCnt_ip().equals(resip[i])){
						t_list.add(t);
					}
				}
			}
		}
		TerminalDAO terminaldao= new TerminalDAO();
		request.setAttribute("t_list", terminaldao.getSortList(t_list,"ASC"));
		request.setAttribute("resips", resips);
		request.setAttribute("resipList", resip);
		request.setAttribute("programName", programName);
		request.setAttribute("cntmac", cntmac);
		request.setAttribute("opp", oppp);
		request.setAttribute("cmd", cmd);
		return mapping.findForward(cmd);
	}
}