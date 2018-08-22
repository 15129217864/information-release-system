package com.xct.cms.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.dao.UpdateIpInfoDao;
import com.xct.cms.utils.HttpRequest;
import com.xct.cms.utils.UtilDAO;

public class UpdateClientIPAction extends Action {
	
	private String url = "";
	Logger logger = Logger.getLogger(UpdateClientIPAction.class);
	private Map<String, String> map = new HashMap<String, String>();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		
		    String ips=request.getParameter("ips").trim();
		    String ipaddress=request.getParameter("ipaddress").trim();
		    String opp = request.getParameter("opp") == null ? "" : request.getParameter("opp").trim();
		    String []string=ips.split("!");
		    String strtemp=new StringBuffer().append(opp).append("@").append(string[0]).append("-").append(string[1]).append("-").append(string[2]).toString();
		    map.put("op", strtemp);
		    
			url = new StringBuffer().append("http://" ).append( ipaddress).append( System.getProperty("CLENTPORT_HOME")).append("/NoticeToClientOparator").toString();
					
			//把终端改成断开状态
			HttpRequest.doPost(url, map, "gbk");
			Map<String, String> ipmap=new HashMap<String, String>();
			ipmap.put("cnt_ip", ipaddress);
			ipmap.put("cnt_islink", "3");
			new UtilDAO().updateinfo(ipmap, "xct_ipaddress");
			
			//修改IP地址后，需要修改一下几张表
			new UpdateIpInfoDao().updateIpInfo(string[0], ipaddress);
			
			logger.info(new StringBuffer().append("向" ).append( url ).append( "发送命令===").append(map.get("op")).toString());
		
		return null;
	}
}
