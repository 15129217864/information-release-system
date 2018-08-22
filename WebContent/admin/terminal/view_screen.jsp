<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String filepath=request.getParameter("filepath");
request.setAttribute("filepath",filepath);
%>
<html>
  <head>
    <title>My JSP 'view_media.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script>
function closedivframe(){
	parent.closedivframe(1);
}
		</script>
  </head>
  
  <body>
<center> <img src="${filepath}" height="500" width="700" border="0" alt="½ØÆÁÍ¼Æ¬"  ondblclick="closedivframe()" /></center>
 
  </body>
</html>
