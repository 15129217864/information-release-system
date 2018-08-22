<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title>My JSP 'oppcatrgoryok.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  
   <script type="text/javascript">
    <c:if test="${oppstatus=='addok'}">
    parent.closedivframe();
    parent.location.href="/rq/searchTerminal?cmd=MEDIA";
    </c:if>
   <c:if test="${oppstatus=='updateok'}">
   parent.closedivframe();
    parent.location.href="/rq/searchTerminal?cmd=MEDIA";
   </c:if>
   <c:if test="${oppstatus=='deleteok'}">
    window.location.href="/rq/searchTerminal?cmd=MEDIA";
    parent.parent.content.location.reload();
   </c:if>
    
    </script>
  </body>
</html>
