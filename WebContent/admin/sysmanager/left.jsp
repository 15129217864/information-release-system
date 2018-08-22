<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma"  content="no-cache"/> 
<title>Sliding Menu</title><script language="javascript" src="/js/vcommon.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<script language="JavaScript" src="/js/did_common.js"></script>
<script language="javascript" src="/js/slide.js"></script>
<script language="javascript">
var reflag=true;
var mflag1=true;
var mflag2=true;
var mflag3=true;
var mflag = 0;
function init(){
	slide('sub1');
}
function gosub(val){
	if(val==1 && reflag){
		//if((obj._expand != true) && mflag!=val)
		allUser();
	}else if(val==2 && reflag){
		//if((obj._expand != true) && mflag!=val)
		sortAsc();
	}else if(val==3 && reflag){
		//if((obj._expand != true) && mflag!=val)
		sortDesc();
	}
	mflag = val;	 
}
function oppLogs(){
	parent.content.location = "/admin/sysmanager/index.jsp?left_menu=OPPLOGS&logtype=1";
}
function sysLogs(){
	parent.content.location = "/admin/sysmanager/index.jsp?left_menu=SYSLOGS&logtype=0";
}
function syskey(){
parent.content.location = "/admin/sysmanager/index.jsp?left_menu=SYSKEY";
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
<input type=hidden name="cmd"/>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
   <td height="30" background="/images/device/btn_background.gif" valign="middle" align="center">
   <span class="MenuTitle">
   系统管理</span></td>
  </tr>
</table>
<div class="menu" onClick="slide('sub1');oppLogs();"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;系统日志管理</div>
<div id="sub1" class="submenu">
		<table>
		<tr>
		<td width="15"><img src="/images/leftmenu/ascending.gif" width="19" height="19"/></td>
		<td><a href="javascript:;" onclick="oppLogs()">&nbsp;&nbsp;&nbsp;操作日志</a></td>
		</tr>
		<tr>
		<td width="15"><img src="/images/leftmenu/ascending.gif" width="19" height="19"/></td>
		<td><a href="javascript:;" onclick="sysLogs(); ">&nbsp;&nbsp;&nbsp;系统日志</a></td>
		</tr>			
		</table>
</div>
<div class="menu" onClick="slide('sub2');syskey();"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;输入授权密钥</div>
<div id="sub2" class="submenu">
		<table>
		<tr>
		<td width="15"><img src="/images/leftmenu/ascending.gif" width="19" height="19"/></td>
		<td><a href="javascript:;" onclick="syskey(); ">&nbsp;&nbsp;&nbsp;输入授权密钥</a></td>
		</tr>			
		</table>
</div>
</form>
</body>
</html>