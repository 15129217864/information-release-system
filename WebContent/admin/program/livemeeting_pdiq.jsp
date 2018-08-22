<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'livemeeting.jsp' starting page</title>
    
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
    <table width="100%" align="center" border="0" cellpadding="5" cellspacing="2" class="mb10">
        <tr>
          <td align="center" bgcolor="#D6E1ED"><strong>序号</strong></td>
          <td height="28" align="center" bgcolor="#D6E1ED"><strong>时间</strong></td>
          <td align="center" bgcolor="#D6E1ED"><strong>地点</strong></td>
          <td align="center" bgcolor="#D6E1ED"><strong>会议名</strong></td>
          <td align="center" bgcolor="#D6E1ED"><strong>参与部门</strong></td>
        </tr>
        <c:choose>
         <c:when test="${not empty meetinglist}">
             <c:forEach var="meeting" items="${meetinglist}" varStatus="index">
		          <tr>
		              <td width="5%" height="28" align="center"  bgcolor="#F1F4F9">${index.count}</td>
		              <td width="14%" height="28" align="center" bgcolor="#F1F4F9">${meeting.builddate}-${meeting.buidstime}-${meeting.buidetime}</td>
			          <td width="18%" height="28" align="center" bgcolor="#F1F4F9">${meeting.attendman}</td>
			          <td width="15%" height="28" align="center" bgcolor="#F1F4F9">${meeting.task}</td>
			          <td width="15%" height="28" align="center" bgcolor="#F1F4F9">${meeting.meetingdept}</td>
		        </tr>
		     </c:forEach>
         </c:when>
         <c:otherwise>
          <tr>
            <td colspan="5" height="28" align="center" bgcolor="#F1F4F9" >今天暂无会议预订信息</td>
          </tr>
         </c:otherwise>
        </c:choose>
       
      </table>
  </body>
</html>
