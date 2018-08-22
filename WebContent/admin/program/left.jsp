<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma"  content="no-cache"/> 
<title>Sliding Menu</title><script language="javascript" src="/js/vcommon.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<script language="javascript" src="/js/slide.js"></script>
<script language="javascript">
var mflag = 0;
function init(){
		gosub(true);
		slide('sub1');
}
function doResize(ifrm_name,val) {
	var o = document.getElementById(ifrm_name);
	if(o && o.contentWindow){
    var frameBody = o.contentWindow.document.body;  
	var Cval      = (screen.availHeight*0.35);
	o.style.height   = Cval;
	frameBody.scroll = "auto";	
	}
}
function gosub(init_tree){
	var forms = document.fm1;
	forms.target = 'ifrm_rms';	
	forms.cmd.value = 'searchOk';
	forms.init_tree.value = init_tree;
	forms.action = "/admin/program/program_zu.jsp";
	forms.submit();	
}

function goContent(val){
	var obj;
	obj = document.getElementById('sub'+eval(val));
	if(obj._expand) ; else obj._expand = false;
	
	if(val==1){
		if((obj._expand != true) && mflag!=val)
		goList();
	}else if(val==2){
		if((obj._expand != true) && mflag!=val)
		sortAsc();
	}else if(val==3){
		if((obj._expand != true) && mflag!=val)
		gosendprogram();
	}else if(val==4){
		if((obj._expand != true) && mflag!=val)
		goActive();
	}else if(val==5){
		if((obj._expand != true) && mflag!=val)
		goAudit();
	}else if(val==6){
		if((obj._expand != true) && mflag!=val)
		goSendErrorProgram();
	}else if(val==10){
		if((obj._expand != true) && mflag!=val)
		goAuditSendProgram();
	}else if(val==111){
		if((obj._expand != true) && mflag!=val)
		goNotAuditProgram();
	}
	mflag = val;	
	
	
}

function goAudit(){
parent.content.location = "/admin/program/index3.jsp?left_menu=AUDIT&title=AUDIT";
}
function goAuditSendProgram(){
parent.content.location = "/admin/program/index3.jsp?left_menu=AUDITSEND&title=AUDITSEND";
}
function goNotAuditProgram(){
parent.content.location = "/admin/program/index3.jsp?left_menu=NOTAUDIT&title=NOTAUDIT";
}
function goList(){	
	parent.content.location = "/admin/program/index.jsp?left_menu=ZU&title=ZU&zu_id=no";
}

function goActive(){
	parent.content.location.href = "/admin/program/index1.jsp?left_menu=ACTIVE&title=ACTIVE";
}  
function gosendprogram(){
	///parent.content.location.href = "/admin/program/index2.jsp?left_menu=SENDPROGRAM&title=SENDPROGRAM";
	
	parent.content.location = "/admin/program/index3.jsp?left_menu=ISSEND&title=ISSEND";
}  
function goInactive(){
	parent.content.location.href = "/admin/terminal/index.jsp?left_menu=INACTIVE&title=INACTIVE";
}
function sortAsc(){
	parent.content.location.href = "/admin/program/index.jsp?left_menu=TITLE_ASC&title=TITLE_ASC";
}
function sortDesc(){
	parent.content.location.href = "/admin/program/index.jsp?left_menu=TITLE_DESC&title=TITLE_DESC";
}

function gosub2(){
	var forms = document.fm1;
	forms.submit();	
}

function linkPage(url){	
	top.content.location.href=url;
}
function goSendErrorProgram(){
//parent.content.location.href = "/admin/program/index4.jsp?left_menu=SENDERROR&title=SENDERROR";
parent.content.location = "/admin/program/index3.jsp?left_menu=SENDERROR&title=SENDERROR";
}
</script>
<style>
BODY {
	font-size:9pt;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-repeat:repeat-y;
	background-position:right;
	background-image: url('/images/menu_bg1.gif');
}
.menu {
    cursor:hand;
	border:0px solid ;
	padding:3px 3px 0px 2px;
	background-image:URL('/images/slide_bg.gif');
	background-repeat:no-repeat;
	width:100%;
	height:23px;
	font-size:9pt;
	font-weight:bold;
	color:222222;
}

.menu2 {
    cursor:hand;
	border:0px solid ;
	padding:7px 3px 3px 2px;
	background-image:URL('/images/slide_bg2.gif');
	background-repeat:no-repeat;
	width:100%;
	height:25px;
	font-size:9pt;
	font-weight:bold;
	color:666666;
}

.menu3 {
    cursor:hand;
	border:0px solid ;
	padding:7px 3px 3px 2px;
	background-image:URL('/images/slide_bg3.gif');
	background-repeat:no-repeat;
	width:100%;
	height:25px;
	font-size:9pt;
	font-weight:bold;
	color:666666;
}

.submenu {
	width:99%;
	padding:5px 0px 0px 5px;
	display:none;
}
</style>
</head>
<body onLoad="init()">
<form name=fm1 method="post">
<input type=hidden name="jtype"/>
<input type="hidden" name="subtype" id="subtype" value="" >		
<input type="hidden" name="cmd" id="cmd"  value="" >	
<input type="hidden" name="init_tree" id="init_tree"  value="" >	
<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
   <td height="30" background="/images/device/btn_background.gif" valign="middle" align="center">
   <span class="MenuTitle">&nbsp;&nbsp;&nbsp;节目管理</span></td>
  </tr>
</table>

<%--<div class="menu" onClick="goContent('5');slide('sub5');"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;待审核节目</div>
<div id="sub5" class="submenu">
		<table>
		<tr>
		<td width="15"><img src="/images/leftmenu/active.gif" width="19" height="19"/><br></td>
		<td><a href="javascript:;" onclick="goAudit();">&nbsp;&nbsp;&nbsp;待审核节目</a><br></td>
		</tr>	
		</table>
</div>
<div class="menu" onClick="goContent('10');slide('sub10');"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;待发送节目</div>
<div id="sub10" class="submenu">
		<table>
		<tr>
		<td width="15"><img src="/images/leftmenu/active.gif" width="19" height="19"/><br></td>
		<td><a href="javascript:;" onclick="goAuditSendProgram();">&nbsp;&nbsp;&nbsp;待发送节目</a><br></td>
		</tr>	
		</table>
</div>

<div class="menu" onClick="goContent('3');slide('sub3');"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;已发送节目</div>
<div id="sub3" class="submenu">
		<table>
		<tr>
		<td width="15"><img src="/images/leftmenu/active.gif" width="19" height="19"/><br></td>
		<td><a href="javascript:;" onclick="gosendprogram();">&nbsp;&nbsp;&nbsp;已发送节目</a><br></td>
		</tr>	
		</table>
</div>
--%>
<div class="menu" onClick="goContent('1');gosub(false);slide('sub1');"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;节目组管理</div>
<div id="sub1" class="submenu">
	<iframe name="ifrm_rms" width="96%" height="100%" scrolling='no' frameborder='0' marginwidth="0" marginheight="0"  onLoad="doResize('ifrm_rms',0.45)">
	</iframe>
</div>
<!-- 
<div class="menu" onClick="goContent('2');slide('sub2');"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;名称排序</div>
<div id="sub2" class="submenu">
		<table>
		<tr>
		<td width="15"><img src="/images/leftmenu/ascending.gif" width="19" height="19"/><br></td>
		<td><a href="javascript:;" onclick="sortAsc();">&nbsp;&nbsp;&nbsp;升序</a><br></td>
		</tr>
		<tr>
		<td width="15"><img src="/images/leftmenu/descending.gif" width="19" height="19"/><br></td>
		<td><a href="javascript:;" onclick="sortDesc();">&nbsp;&nbsp;&nbsp;降序</a><br></td>
		</tr>		
		</table>
</div> -->
<%--<div class="menu" onClick="goContent('111');slide('sub111');"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;审核未通过节目</div>
<div id="sub111" class="submenu">
		<table>
		<tr>
		<td width="15"><img src="/images/leftmenu/active.gif" width="19" height="19"/><br></td>
		<td><a href="javascript:;" onclick="goNotAuditProgram();">&nbsp;&nbsp;&nbsp;审核未通过节目</a><br></td>
		</tr>	
		</table>
</div>
<div class="menu" onClick="goContent('6');slide('sub6');"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;发送失败节目</div>
<div id="sub6" class="submenu">
		<table>
		<tr>
		<td width="15"><img src="/images/leftmenu/active.gif" width="19" height="19"/><br></td>
		<td><a href="javascript:;" onclick="goSendErrorProgram();">&nbsp;&nbsp;&nbsp;发送失败节目</a><br></td>
		</tr>	
		</table>
</div>
--%></form>
</body>
</html>