<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<% String left_menu=request.getParameter("left_menu")==null?"OPPLOGS":request.getParameter("left_menu");
String logtype=request.getParameter("logtype")==null?"1":request.getParameter("logtype");
%>
<html>
	<head>
		<title>多媒体信息发布系统-终端管理</title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style2.css" type="text/css">
		
		<%if("OPPLOGS".equals(left_menu)){ %>
		<script language="JavaScript">
			parent.menu.location.href="/admin/sysmanager/left.jsp";	
			</script>
		<%}
		String content_url="/admin/sysmanager/logdate.jsp?left_menu="+left_menu+"&logtype="+logtype;
		if ("SYSKEY".equals(left_menu)){
			content_url="/admin/sysmanager/syskey.jsp";
		}%>
		

	</head>
	<FRAMESET rows="63,30,*" cols="*" border="0">
		<FRAME noresize scrolling="no" frameBorder="0"
			src="/admin/sysmanager/ter_header.jsp?left_menu=<%=left_menu %>&logtype=<%=logtype %>"
			name="deviceTop" />
		<FRAME noresize scrolling="no" frameBorder="0"
			src="/admin/sysmanager/competence_list_top.jsp?left_menu=<%=left_menu %>" name="listtop" />
		<FRAME frameBorder="0" scrolling="auto" frameBorder="0"
			src="<%=content_url %>"
			marginheight="0" marginwidth="0" name="content" />
	</FRAMESET>

</html>