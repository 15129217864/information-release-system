<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String keytime=new UtilDAO().redkey();
request.setAttribute("keytime",keytime);
 %>
<html>
  <head>
    <title>My JSP 'syskey.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <link rel="stylesheet" href="/css/style.css" type="text/css" />
  <script type="text/javascript">
  function onsyskey(){
  	keyvalue=myform.keyvalue.value;
  	if(keyvalue==""||keyvalue.length!=16){
  		alert("��������Ч��Ȩ��Կ��");
  		returen ;
  	}
  
	myform.action="/admin/sysmanager/syskeyDO.jsp";
	myform.submit();
  
  }
  </script>
  </head>
  
  <body><form action="" name="myform" method="post" >
   		<table width="500" style="margin-left: 50px;" border="0">
   			<tr>
   				<td colspan="2" height="50px"  class="TitleSmall">��ӭ��ʹ�ö�ý����Ϣ����ϵͳ</td>
   			</tr>
   			<tr>
   				<td width="150"  height="30px">��ǰ����汾��</td>
   				<td width="350">6.1.8</td>
   			</tr>
   			<tr>
   				<td  height="30px">��ǰ�����Ч�ڣ�</td>
   				<td><span style='color:green'>${keytime eq 'unrestricted'?'������':keytime}</span></td>
   			</tr>
   		</table>
   		<c:if test="${keytime ne 'unrestricted'}">
	   		<table width="500px" style="margin-left: 50px;margin-top: 30px" border="0">
	   			<tr>
	   				<td width="150px"  height="30px">��������Ȩ��Կ��</td>
	   				<td><input type="text" size="50" name="keyvalue"/></td>
	   			</tr>
	   				<tr><td>&nbsp;</td>
	   				<td height="30px" style="color: green">��������Ȩ��Կ��</td></tr>
	   			<tr>
	   				<td>&nbsp;</td>
	   				<td height="30px"><input type="button" class="button1" value="���" onclick="onsyskey();"/></td>
	   			</tr>
	   		</table>
   		</c:if>
   		</form>
  </body>
</html>
<c:if test="${regok=='ok'}">
<script>alert('${regresult}')</script>
</c:if>