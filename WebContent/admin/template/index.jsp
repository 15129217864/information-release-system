<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<% String left_menu=request.getParameter("left_menu")==null?"":request.getParameter("left_menu");
 String title=request.getParameter("title")==null?"ROOT":request.getParameter("title");
%>
<html>
	<head>
		<title>多媒体信息发布系统-模板管理</title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style2.css" type="text/css">
		
		<%if("".equals(left_menu)){ %>
		<script language="JavaScript">
			parent.menu.location.href="/admin/template/left.jsp";	
			</script>
		<%} %>

	</head>
	<FRAMESET rows="63,30,*" cols="*" border="0">
		<FRAME noresize scrolling="no" frameBorder="0"
			src="/admin/template/ter_header.jsp?title=<%=title %>&left_menu=<%=left_menu %>"
			name="deviceTop" />
		<FRAME noresize scrolling="no" frameBorder="0"
			src="/admin/template/competence_list_top.jsp" name="listtop" />
		<FRAME frameBorder="0" scrolling="auto" frameBorder="0"
			src="/admin/template/templateList.jsp?left_menu=<%=left_menu %>"
			marginheight="0" marginwidth="0" name="content" />
	</FRAMESET>

</html>