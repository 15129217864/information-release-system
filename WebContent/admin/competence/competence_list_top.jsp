<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
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
      <td width="10%" class="InfoTitle">姓名</td>
      <td width="20%" class="InfoTitle">用户名</td>
      <td width="20%" class="InfoTitle">邮箱</td>
      <td width="15%" class="InfoTitle">最后登录时间</td>
      <td width="10%" class="InfoTitle">权限</td>
      <td width="20%" class="InfoTitle">操作</td>
    </tr>  
  </table>
</body>
</html>
