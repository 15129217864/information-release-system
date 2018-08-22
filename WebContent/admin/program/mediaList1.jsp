<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.MediaDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String zuid=request.getParameter("zuid")==null?"1":request.getParameter("zuid");
String paneType=request.getParameter("paneType")==null?"":request.getParameter("paneType");
String meidaType="";
if("ppt".equals(paneType)){
meidaType="PPT";
}else if("image".equals(paneType)){
meidaType="IMAGE";
}else if("video".equals(paneType)){
meidaType="MOVIE";
}else if("scroll".equals(paneType)){
meidaType="TEXT";
}else if("flash".equals(paneType)){
meidaType="FLASH";
}else if("word".equals(paneType)){
meidaType="WORD";
}else if("web".equals(paneType)){
meidaType="WEB";
}else if("excel".equals(paneType)){
meidaType="EXCEL";
}else if("htmltext".equals(paneType)){
meidaType="HTML";
}else{
meidaType="SOUND";
}
MediaDAO mediadao= new MediaDAO();
List mediaList=mediadao.getALLMediaDAO(" and xct_zu.zu_id="+zuid+" and media_type='"+meidaType+"'");
request.setAttribute("mediaList",mediaList);
%>
<html>
  <head>
    <title>My JSP 'mediaList.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="/css/style.css" type="text/css"/>
  </head>
  
  <body style="margin: 5px;">
  <form action="" name="frm1" method="post">
   <table width="100%" border="0" cellpadding="0" cellspacing="0">
   <c:if test="${mediaList=='[]'}">
   ÎŞÄÚÈİ£¡
   </c:if>
   		<c:forEach var="media" items="${mediaList}">
   			<tr>
   				<td>
   				<input type="checkbox" name="meidaIds" value="${media.media_id}" />
   				${media.media_title }</td>
   			</tr>
   		</c:forEach>
   
   </table>
   </form>
  </body>
</html>
