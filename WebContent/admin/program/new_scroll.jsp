<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String moduleid=request.getParameter("moduleid")==null?"0":request.getParameter("moduleid");
%>
<html>
  <head>
    <title></title>
    
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
function addscroll(){
var scroll_content=scroll_form.scroll_content.value.replace(/\s/g,"");
if(scroll_content==""){
	alert("�������ֲ���Ϊ�գ�");
	return;
}if(scroll_content.length>1000){
	alert("�������ֱ���С��1000���ֽڣ�");
	return;
}
scroll_form.scroll_content.value=encodeURI(scroll_form.scroll_content.value);
scroll_form.action="/admin/program/new_scrollDO.jsp";
scroll_form.submit();
}
</script>
  </head>
  
  <body>
  <form action="" name="scroll_form">
  <input type="hidden" name="moduleid" value="<%=moduleid%>"/>
  <table width="280" style="font-size: 12px;" border="0" align="center">
  	<tr>
  		<td align="center" style="font-weight: bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;¼���������</td>
  		<td align="right"><a href="javascript:;" onclick="parent.closeDiv();return false"><img src="/images/del.gif" height="12px" border="0"/></a></td>
  	</tr>
  	<tr>
  		<td colspan="2"><textarea rows="6" cols="50" name="scroll_content"></textarea></td>
  	</tr>
  	<tr>
  		<td  colspan="2" align="center">
  		<input type="button" value="�� ��" class="button" onclick="addscroll();"/>
  		<input type="button" value="ȡ ��" class="button" onclick="parent.closeDiv();"/></td>
  	</tr>
  </table>
  </form>
   
   
  </body>
</html>
