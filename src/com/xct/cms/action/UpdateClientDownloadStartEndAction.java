package com.xct.cms.action;

import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Terminal;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.UtilDAO;

public class UpdateClientDownloadStartEndAction extends Action {
	
	Logger logger = Logger.getLogger(RequestClientOperatingAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		
		String cmd = request.getParameter("cmd");
		String opp = request.getParameter("opp") == null ? "" : request.getParameter("opp");
		String resips = request.getParameter("checkips") == null ? "!": request.getParameter("checkips");
		String [] downloadstartend=opp.split("@")[1].split("#");
		final String value=new StringBuffer().append(" cnt_kjtime='").append(downloadstartend[2]).append("', cnt_gjtime='"+downloadstartend[1]).append("', cnt_downtime='").append(downloadstartend[0]).append("'").toString();
		
		DBConnection dbconn=new DBConnection();
		Connection conn=dbconn.getConection();
		UtilDAO utildao=new UtilDAO();
		
		String resip[] = resips.split("!");
		Map<String,Terminal> terminals=FirstStartServlet.terminalMap;
		Terminal t=null;
		String t_mac="";
		for (int i = 1; i < resip.length; i++) {
			String mac=resip[i].split("_")[1];
			utildao.upeateinfo(conn,value, new StringBuffer().append(" cnt_mac='").append(mac).append("'").toString(), "xct_ipaddress");//修改终端数据库休眠开关和定时下载时间
			 for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
				 t=entry.getValue();
				 t_mac=entry.getKey();
				 if(t!=null){
//					 if(resip[i].equals(t.getCnt_ip())){
						 if(mac.equals(t_mac)){
						 t.setCnt_kjtime(downloadstartend[2]);
						 FirstStartServlet.terminalMap.put(t_mac, t);
						 break;
					 }
				 }
			 }
		}
		dbconn.returnResources(conn);
		request.setAttribute("opp", opp);
		request.setAttribute("cmd", cmd);
		request.setAttribute("checkips", resips);
		return mapping.findForward("updateOk");
	}
}
