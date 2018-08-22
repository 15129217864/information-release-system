<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<% String left_menu=request.getParameter("left_menu")==null?"":request.getParameter("left_menu");
 String title=request.getParameter("title")==null?"ROOT":request.getParameter("title");
 String zu_id=request.getParameter("zu_id")==null?"no":request.getParameter("zu_id");
 String type=request.getParameter("type")==null?"":request.getParameter("type");
%>
<html>
	<head>
		<title>多媒体信息发布系统-终端管理</title>
		<script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style2.css" type="text/css">
		
		<%if("".equals(left_menu)){ %>
		    <script language="JavaScript">
			   parent.menu.location.href="/admin/media/left.jsp";	
			</script>
		<%} %>

	</head>
		<iFRAME  scrolling="no" frameBorder="0" width="100%" height="100%"
			src="/admin/media/ter_header.jsp?title=<%=title %>&left_menu=<%=left_menu %>&zu_id=<%=zu_id %>&type=<%=type %>"
			name="deviceTop" />
		

</html>