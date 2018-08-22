<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<%
	   // String iptmp=request.getParameter("iptmp")==null?"127.0.0.1":request.getParameter("iptmp");
		String cntmac=request.getParameter("cntmac")==null?"":request.getParameter("cntmac");
		//String link_status=request.getParameter("link_status")==null?"3":request.getParameter("link_status");
		String cnt_name=Util.getGBK(request.getParameter("cnt_name"));
		String terminal_zu=Util.getGBK(request.getParameter("terminal_zu"));
		if(terminal_zu.equals("")){
		   return;
		}
		//String cnt_kjtime=Util.getGBK(request.getParameter("cnt_kjtime"));//+":"+Util.getGBK(request.getParameter("sleepstartmunite"));
		//String cnt_gjtime=Util.getGBK(request.getParameter("cnt_gjtime"));//+":"+Util.getGBK(request.getParameter("sleependmunite"));
				
		//String cnt_downtime=Util.getGBK(request.getParameter("cnt_downtime"));//+":"+Util.getGBK(request.getParameter("cnt_downtimeminute"));
		String cnt_miaoshu=Util.getGBK(request.getParameter("cnt_miaoshu"));
		
		UtilDAO utildao= new UtilDAO();
		Map map= utildao.getMap();
		map.put("cnt_mac", cntmac);
		map.put("cnt_name", cnt_name);
		//if("1".equals(link_status)){
			//map.put("cnt_kjtime", cnt_kjtime);
			//map.put("cnt_gjtime", cnt_gjtime);
			//map.put("cnt_downtime", cnt_downtime);
		//}
		map.put("cnt_miaoshu", cnt_miaoshu);
		map.put("cnt_zuid", terminal_zu);
		utildao.updateinfo(map, "xct_ipaddress");
		
		Map<String,Terminal> terminals=FirstStartServlet.terminalMap;
		Terminal terminal=terminals.get(cntmac);
		terminal.setCnt_name(cnt_name);
		terminal.setCnt_miaoshu(cnt_miaoshu);
		terminal.setCnt_zuid(Integer.parseInt(terminal_zu));	
		//====================================================================================
		for (Map.Entry<String, Terminal> entry : FirstStartServlet.terminalMap.entrySet()) {
			Terminal terminal2=entry.getValue();
			if(terminal2.getCnt_zuid()==Integer.parseInt(terminal_zu)){
			   terminal.setZu_name(terminal2.getZu_name());
			   break;
			}
		}
		//====================================================================================
		terminals.put(cntmac, terminal);
		FirstStartServlet.terminalMap.put(cntmac, terminal);
		
		///if("1".equals(link_status)){
		//String opp="lv0023@"+cnt_downtime+"#"+cnt_gjtime+"#"+cnt_kjtime;
		//FirstStartServlet.terminalMap=new TerminalDAO().getALLTerminalMap("");
		
		//request.setAttribute("checkips", "!"+iptmp);
		//request.setAttribute("opp", opp);
		//request.setAttribute("cmd", "UPDATE_DOWNLOAD_START_END");
		//request.getRequestDispatcher("/rq/requestClientOperating").forward(request,response);
		//}else{
			request.setAttribute("update_type", "ok");
			request.getRequestDispatcher("/admin/terminal/updateClient.jsp?cntmac="+cntmac).forward(request,response);
		//}
    %>
 