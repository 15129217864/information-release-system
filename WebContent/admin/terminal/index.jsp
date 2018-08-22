<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>

<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String left_cmd=request.getParameter("left_cmd")==null?"":request.getParameter("left_cmd");
 String cmd=request.getParameter("cmd")==null?"LIST":request.getParameter("cmd");
 String title=request.getParameter("title")==null?"ROOT":request.getParameter("title");
 String zu_id=request.getParameter("zu_id")==null?"no":request.getParameter("zu_id");
%>
<html>
	<head>
		<title>多媒体信息发布系统-终端管理
		</title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style2.css" type="text/css">
		<%
		String deviceTopURL="/admin/terminal/ter_header.jsp?title="+title+"&left_cmd="+left_cmd+"&zu_id="+zu_id;
		String deviceContentURL="/rq/terminalList?cmd="+cmd+"&left_cmd="+left_cmd+"&zu_id="+zu_id;
		
		if("TOPNOTAUDIT".equals(cmd)){ 
			String romd=Math.random()+"";
			deviceTopURL="/admin/terminal/ter_header.jsp?cmd=NOTAUDIT&left_cmd=NOTAUDIT&title=NOTAUDIT";
			deviceContentURL="/rq/terminalList?cmd=NOTAUDIT&left_cmd=NOTAUDIT&title=NOTAUDIT&t="+romd;
		}%>
		<script language="JavaScript">
			parent.menu.location.href="/admin/terminal/left.jsp";	
		</script>
	</head>

	<FRAMESET rows="87px,*" border="0">
		<FRAME noresize scrolling="no" frameBorder="0" src="<%=deviceTopURL %>"
			name="deviceTop" />
		<FRAME frameBorder="0" scrolling="no" frameBorder="0"
				src="<%=deviceContentURL %>" 
				marginheight="0" marginwidth="0" name="deviceContent" />
	</FRAMESET>
</html>