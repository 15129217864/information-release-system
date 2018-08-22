<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>My JSP 'restart.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>

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
		<c:if test="${sendcmd=='RESTART'}">
			<script>
				alert("命令发送成功！终端正在重启..");
				parent.closedivframe();
			</script>
		</c:if>
		<c:if test="${sendcmd=='SENDNOTICE'}">
			<script>
				alert("文字通知发送成功！");
				parent.closedivframe();
			</script>
		</c:if>
		<c:if test="${sendcmd=='ONDOWN'}">
			<script>
				alert("命令发送成功！正在关闭选中终端..");
				parent.closedivframe();
			</script>
		</c:if>
	</body>
</html>
