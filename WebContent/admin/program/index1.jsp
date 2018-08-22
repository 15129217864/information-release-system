<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<% String left_menu=request.getParameter("left_menu")==null?"":request.getParameter("left_menu");
 String title=request.getParameter("title")==null?"ROOT":request.getParameter("title");
 String zu_id=request.getParameter("zu_id")==null?"no":request.getParameter("zu_id");
 String type=request.getParameter("type")==null?"":request.getParameter("type");
%>
<html>
	<head>
		<title>多媒体信息发布系统-终端管理</title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style2.css" type="text/css">
		
		<%if("".equals(left_menu)){ %>
		<script language="JavaScript">
			parent.menu.location.href="/admin/program/left.jsp";	
			</script>
		<%} %>

	</head>
	<FRAMESET rows="60,30,*" cols="*" border="0">
		<FRAME noresize scrolling="no" frameBorder="0"
			src="/admin/program/ter_header1.jsp?title=<%=title %>&left_menu=<%=left_menu %>&zu_id=<%=zu_id %>"
			name="deviceTop" />
		<FRAME noresize scrolling="no" frameBorder="0"
			src="/admin/program/program_list_top1.jsp" name="listtop" />
		<FRAME frameBorder="0" scrolling="auto" frameBorder="0"
			src="/admin/program/templateList.jsp" 
			marginheight="0" marginwidth="0" name="content" />
	</FRAMESET>

</html>