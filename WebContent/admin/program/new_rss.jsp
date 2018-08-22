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
var scroll_content=scroll_form.rss_content.value.replace(/\s/g,"");
if(scroll_content==""){
	alert("RSS地址不能为空！");
	return;
}
document.getElementById("sub_id").disabled="disabled";
document.getElementById("al_tr").style.display="block";
document.getElementById("al_tr1").style.display="none";
document.getElementById("al_tr2").style.display="none";
document.getElementById("al_tr3").style.display="none";
scroll_form.action="/admin/program/new_rssDO.jsp";
scroll_form.submit();
}
</script>
  </head>
  
  <body>
  <form action="" name="scroll_form">
  <input type="hidden" name="moduleid" value="<%=moduleid%>"/>
  <table width="280" height="100%" style="font-size: 12px;" border="0" align="center">
  	<tr id="al_tr1">
  		<td align="center" style="font-weight: bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请输入RSS地址</td>
  		<td align="right"><a href="javascript:;" onclick="parent.closeDiv();"><img src="/images/del.gif" height="12px" border="0"/></a></td>
  	</tr>
  	<tr id="al_tr2">
  		<td colspan="2"><textarea rows="3" cols="50" name="rss_content"></textarea></td>
  	</tr>
  		<tr style="display: none;" id="al_tr" height="100%" valign="middle" align="center">
  		<td><img src="/images/mid_giallo.gif"/><span style="color: green;">正在解析RSS地址，请稍后...</span></td>
  	</tr>
  	<tr  id="al_tr3">
  		<td  colspan="2" align="center">
  		<input type="button" value="添 加"  id="sub_id" class="button" onclick="addscroll();"/>&nbsp;&nbsp;
  		<input type="button" value="取 消" class="button" onclick="parent.closeDiv();"/></td>
  		
  	</tr>
  </table>
  </form>
   
   
  </body>
</html>
