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
	goInit_tree();
	slide('sub2');
}
function doResize(ifrm_name,val) {
	var o = document.getElementById(ifrm_name);
	if(o && o.contentWindow){
    var frameBody = o.contentWindow.document.body;
	var Cval      = (screen.availHeight*val);
	o.style.height   = document.body.clientHeight-110;
	 frameBody.scroll = "auto";
	}
}

function goInit_tree(){
		var forms = document.fm1;
		forms.target = 'ifrm_cms';	
		forms.cmd.value = 'MEDIA';
		forms.action = "/rq/searchTerminal";
		forms.submit();
}

function gosub(val){
	var forms = document.fm1;
	if(val==1 && mflag1){
		forms.target = 'ifrm_media';	
		forms.cmd.value = 'MEDIA';
		forms.action = "/rq/leftmenu";
		forms.submit();
	}else if(val==2 && mflag2){
		//mflag2 = false;
		forms.target = 'ifrm_cms';	
		forms.cmd.value = 'MEDIA';
		forms.action = "/rq/searchTerminal";
		forms.submit();
	}else if(val==3 && mflag3){
		//mflag3 = false;
		forms.target = 'ifrm_date';	
		forms.cmd.value = 'DATE';
		forms.action = "/rq/leftmenu";
		forms.submit();
	}
	var obj;
	obj = document.getElementById('sub'+eval(val));
	if(obj._expand) ; else obj._expand = false;

	if(val==1 && reflag){
		//if((obj._expand != true) && mflag!=val)
		goMedia();
	}else if(val==2 && reflag){
		//if((obj._expand != true) && mflag!=val)
		goList();
	}else if(val==3 && reflag){
		//if((obj._expand != true) && mflag!=val)
		goDate();
	}else if(val==4 && reflag){
		//if((obj._expand != true) && mflag!=val)
		sortAsc();
	}	
	mflag = val;	 
}

function initFlag(){
	mflag  = 0;
	reflag = true;
}
function goMedia(){
	parent.content.location = "/admin/media/index.jsp?left_menu=MOVIE";
}
function goList(){
	parent.content.location = "/admin/media/index.jsp?left_menu=TYPE_ZU";
}
function goDate(){
	var ltype = "PL";
	if(parent.content.list_body && parent.content.list_body.document.getElementById("ltype"))
		ltype = parent.content.list_body.document.getElementById("ltype").value;
	var dd  = new Date();
	var val = dd.getYear()+zeroPlus(dd.getMonth()+1);
	parent.content.location = "/admin/media/index.jsp?left_menu=DATE";
}
function sortAsc(){
	parent.content.location = "/admin/media/index.jsp?left_menu=TITLE_ASC";
}
function sortDesc(){
	parent.content.location = "/admin/media/index.jsp?left_menu=TITLE_DESC";}
function playList(){
	var lstsize="10";
	if(parent.content.fm1)
		lstsize = parent.content.fm1.lstsize.value;
	parent.content.location = "/cms/cmsplaylist.do?jtype=INIT&lstsize="+lstsize;
}
function slideShow(){
	var lstsize="10";
	if(parent.content.fm1)
		lstsize = parent.content.fm1.lstsize.value;
	parent.content.location = "/cms/slideshow.do?jtype=INIT&type=photolist&lstsize="+lstsize;
}
function videowall(){
	parent.content.location = "/cms/cmsvwl.do?cmd=INIT";
}


</script>
<style>
  html,body {
	font-size:9pt;
	margin: 0 ;
	height: 100%;
    width: 100%;
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
   Ã½Ìå¿â</span></td>
  </tr>
</table>

<div class="menu" onClick="gosub(1);slide('sub1');"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;Ã½Ìå</div>
<div id="sub1" class="submenu">
	<iframe name="ifrm_media" width="96%" height="100%" scrolling='no' frameborder='0' marginwidth="0" marginheight="0" onLoad="doResize('ifrm_media',0.32)">
	</iframe>	
</div>
<div class="menu" onClick="gosub(4);slide('sub4');"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;Ãû³ÆÅÅÐò</div>
<div id="sub4" class="submenu">
		<table>
		<tr>
		<td width="15"><img src="/images/leftmenu/ascending.gif" width="19" height="19"/></td>
		<td><a href="javascript:;" onclick="sortAsc(); ">&nbsp;&nbsp;&nbsp;ÉýÐò</a></td>
		</tr>
		<tr>
		<td width="15"><img src="/images/leftmenu/descending.gif" width="19" height="19"/></td>
		<td><a href="javascript:;" onclick="sortDesc();">&nbsp;&nbsp;&nbsp;½µÐò</a></td>
		</tr>		
		</table>
</div>

<div class="menu" onClick="slide('sub2');"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;Àà±ð</div>
<div id="sub2" class="submenu">
		<iframe name="ifrm_cms" width="96%" height="99%" scrolling='auto' frameborder='0' marginwidth="0" marginheight="0" onLoad="doResize('ifrm_cms',0.50)">
		</iframe>
</div>

<!-- <div class="menu" onClick="gosub(3);slide('sub3');"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;ÈÕÆÚÅÅÐò</div>
<div id="sub3" class="submenu">
	<div>
	<iframe name="ifrm_date" width="100%" height="100%" scrolling='no' frameborder='0' marginwidth="0" marginheight="0" onLoad="doResize('ifrm_date',0.33)">
	</iframe>	
	</div>
</div> -->

<div id="sub99" class="submenu"></div>

</form>
</body>
</html>