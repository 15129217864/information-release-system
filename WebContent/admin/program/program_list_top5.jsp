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
   	 <td width="3%" align="center"><input type="checkbox" name="sel_all" value="checkbox" onclick="all_chk(this.checked);"></td>
      <td width="13%" class="InfoTitle">{stadNoDesc}</td>
      <td width="12%" class="InfoTitle">{limitName}</td>
      <td width="16%" class="InfoTitle">{scrNoDesc}</td>
      <td width="12%" class="InfoTitle">{enterDate}</td>
      <td width="11%" class="InfoTitle">{distributionnum}</td>
      <td width="11%" class="InfoTitle">locknum</td>
      <td width="11%" class="InfoTitle">{useed}</td>
      <td width="11%" class="InfoTitle">{enableNum}</td><%--
      
      	 <td width="3%" align="center"><input type="checkbox" name="sel_all" value="checkbox" onclick="all_chk(this.checked);"></td>
      <td width="13%" class="InfoTitle">场馆{stadNoDesc}</td>
      <td width="12%" class="InfoTitle">额度库{limitName}</td>
      <td width="16%" class="InfoTitle">场次{scrNoDesc}</td>
      <td width="12%" class="InfoTitle">入馆日期{enterDate}</td>
      <td width="11%" class="InfoTitle">分配数</td>
      <td width="11%" class="InfoTitle">锁定数</td>
      <td width="11%" class="InfoTitle">已用数</td>
      <td width="11%" class="InfoTitle">可用数{enableNum}</td>
    --%></tr>  
  </table>
</body>
</html>
