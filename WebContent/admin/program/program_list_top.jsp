<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String createprogram=request.getParameter("createprogram")==null?"":request.getParameter("createprogram");
request.setAttribute("createprogram",createprogram);
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
	parent.content.all_chk(val);
}
//-->
</script>
<body  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="onLoad()">
  <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0"  class="TitleBackground">
    <tr>
      <td width="3%" align="center"><input type="checkbox" name="sel_all" value="checkbox" onclick="all_chk(this.checked);"></td>
      <td width="22%" class="InfoTitle">��Ŀ����</td>
      <td width="10%" class="InfoTitle">��Ŀ�ļ�</td>
      <td width="10%" class="InfoTitle">��Ŀ��</td>
      <td width="15%" class="InfoTitle">����ʱ��</td>
      <td width="10%" class="InfoTitle">������</td>
       <c:if test="${isauditing eq '1' }">
        <td width="5%" class="InfoTitle">��Ŀ״̬</td>
      </c:if>
      <td width="35%" class="InfoTitle">����</td>
    </tr>  
  </table>
</body>
</html>
<c:if test="${createprogram=='ok'}">
<script>
alert("��ʾ��Ϣ���½���Ŀ�ɹ���")
</script>
</c:if>