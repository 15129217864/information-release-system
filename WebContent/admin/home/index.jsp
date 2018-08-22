<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<% String left_cmd=request.getParameter("left_cmd")==null?"":request.getParameter("left_cmd");
 String cmd=request.getParameter("cmd")==null?"MONITORING":request.getParameter("cmd");
 String title=request.getParameter("title")==null?"ROOT":request.getParameter("title");
 String zu_id=request.getParameter("zu_id")==null?"no":request.getParameter("zu_id");
%>
<html>
	<head>
		<title>多媒体信息发布系统-终端管理
		</title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style2.css" type="text/css">
		<%if("".equals(left_cmd)){ %>
			<script language="JavaScript">
			parent.menu.location.href="/admin/left.jsp";	
			</script>
		<%} %>
	</head>
	<FRAMESET rows="31px,*" border="0">
		<FRAME noresize scrolling="no" frameBorder="0" src="/admin/home/home_header.jsp?title=<%=title %>&left_cmd=<%=left_cmd %>&zu_id=<%=zu_id %>"
			name="deviceTop" />
		<FRAME frameBorder="0" scrolling="auto" frameBorder="0"
				src="/admin/home/home.jsp" 
				marginheight="0" marginwidth="0" name="deviceContent" />
	</FRAMESET>
</html>