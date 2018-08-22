<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
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
     <form method="post" action="http://127.0.0.1:8080/rq/screenCarpure" enctype="multipart/form-data">
     <!--<form method="post" action="http://106.14.96.43/rq/screenCarpure" enctype="multipart/form-data">-->
	选择一个文件:
	<input type="file" name="uploadFile" />
	<br/><br/>
	<input type="submit" value="上传" />
</form>
  </body>
</html>
