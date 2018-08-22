<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>

<%
String mac=request.getParameter("mac");
boolean booltmp=new  TerminalDAO().delNotAuditTerminalDAO(" cnt_mac='"+mac+"'");
//UtilDAO utildao= new UtilDAO();
//utildao.deleteinfo("cnt_mac", mac, "xct_ipaddress");
if(booltmp&&null!=FirstStartServlet.terminalMap.get(mac)
          &&FirstStartServlet.terminalMap.get(mac).getCnt_status().equals("0")){
   FirstStartServlet.terminalMap.remove(mac);
}
%>
<script type="text/javascript">
<!--
window.location.href="/rq/terminalList?cmd=NOTAUDIT&left_cmd=NOTAUDIT&title=NOTAUDIT";
//-->
</script>
