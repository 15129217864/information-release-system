<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
    <%
    	String left_menu=request.getParameter("left_menu");
	 request.setAttribute("left_menu",left_menu);
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
  <table width="99%" height="27"  border="0" cellpadding="0" cellspacing="0"  class="TitleBackground">
    <tr>
      <td width="5%" align="center"><input type="checkbox" name="sel_all" value="checkbox" onclick="all_chk(this.checked);"></td>
      <td width="20%" class="InfoTitle">${left_menu=='ISSEND'?'����':'����' }���κ�</td>
      <td width="15%" class="InfoTitle">${left_menu=='ISSEND'?'����':'����' }��</td>
      <td width="20%" class="InfoTitle">${left_menu=='ISSEND'?'����':'����' }ʱ��</td>
      <td width="10%" class="InfoTitle">����״̬</td>
      <td width="15%" class="InfoTitle">�����ն�</td>
      <td width="15%" class="InfoTitle">����&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>  
  </table>
</body>
</html>
