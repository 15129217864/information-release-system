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
      <td width="10">&nbsp;</td>
      <td width="15"><input type="checkbox" name="sel_all" value="checkbox" onclick="all_chk(this.checked);"></td>
      <td width="12%" class="InfoTitle">节目名称</td>
      <td width="12%" class="InfoTitle">节目文件</td>
      <td width="12%" class="InfoTitle">开始时间</td>
      <td width="12%" class="InfoTitle">结束时间</td>
      <td width="12%" class="InfoTitle">播放类型</td>
      <td width="12%" class="InfoTitle">申请时间</td>
      <td width="12%" class="InfoTitle">当前状态</td>
      <td width="12%" class="InfoTitle">播放终端</td>
    </tr>  
  </table>
</body>
</html>
