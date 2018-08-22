<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%
String updateprogram=request.getParameter("updateprogram")==null?"":request.getParameter("updateprogram");
request.setAttribute("updateprogram",updateprogram);
 %>
<html>
<head>
<title>ContentList Init</title><script language="javascript" src="/js/vcommon.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css" />

<script language="JavaScript" type="text/JavaScript">
<!--
function onLoad(){
	//parent.document.body.focus();
}
function all_chk(val){
	parent.all_chk(val);
}
//-->
</script>
<body  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="onLoad()">
  <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0"  class="TitleBackground">
    <tr>
      <td width="100%">&nbsp;&nbsp;&nbsp;&nbsp;节目编辑</td>
   
    </tr>  
  </table>
</body>
</html>
<c:if test="${updateprogram=='ok'}">
<script>
alert("提示信息：修改节目成功！")
</script>
</c:if>