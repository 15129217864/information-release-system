<%@ page language="java" contentType="text/html; charset=gbk"    pageEncoding="gbk"%>
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
		    //alert(document.body.clientHeight);
			gosub(true);
			slide('sub1');
		}
		function doResize(ifrm_name,val) {
			var o = document.getElementById(ifrm_name);
			if(o && o.contentWindow){
			    var frameBody = o.contentWindow.document.body;  
				var Cval      = (screen.availHeight*val);
				//alert(document.body.clientHeight);
				o.style.height   = document.body.clientHeight-110;
				frameBody.scroll = "auto";	
			}
		}
		function gosub(init_tree){
			var forms = document.fm1;
			forms.target = 'ifrm_rms';	
			forms.cmd.value = 'TERMINAL';
			forms.init_tree.value = init_tree;
			forms.action = "/rq/searchTerminal";
			forms.submit();	
		}
		
		function goContent(val){
			var obj;
			obj = document.getElementById('sub'+eval(val));
			if(obj._expand) ; else obj._expand = false;
			
			if(val==4){
				if((obj._expand != true) && mflag!=val)
				goActive();
			}else if(val==1){
				if((obj._expand != true) && mflag!=val)
				goList();
			}else if(val==2){
				if((obj._expand != true) && mflag!=val)
				sortAsc();
			}	
			mflag = val;	
		}
		
		function goList(){	
			parent.content.location = "/admin/terminal/index.jsp?left_cmd=ZU&title=ZU&zu_id=no";
		}
		
		function goActive(){
			var conteturl=parent.content.deviceContent.location.href;
			var cmd=conteturl.split("=")[1].split("&")[0];
			if(cmd=="NOTAUDIT"){
				parent.content.deviceTop.location.href = "/admin/terminal/ter_header.jsp?cmd=MONITORING&left_cmd=ACTIVE";
				cmd="MONITORING";
			}
			parent.content.deviceTop.location.href = "/admin/terminal/ter_header.jsp?cmd="+cmd+"&left_cmd=ACTIVE&title=ACTIVE&zu_id=";
			parent.content.deviceContent.location.href = "/rq/terminalList?cmd="+cmd+"&left_cmd=ACTIVE&zu_id=";
		}
		function goDormancy(){
			var conteturl=parent.content.deviceContent.location.href;
			var cmd=conteturl.split("=")[1].split("&")[0];
			if(cmd=="NOTAUDIT"){
				parent.content.deviceTop.location.href = "/admin/terminal/ter_header.jsp?cmd=MONITORING&left_cmd=DORMANCY";
				cmd="MONITORING";
			}
			parent.content.deviceTop.location.href = "/admin/terminal/ter_header.jsp?cmd="+cmd+"&left_cmd=DORMANCY&title=DORMANCY&zu_id=";
			parent.content.deviceContent.location.href = "/rq/terminalList?cmd="+cmd+"&left_cmd=DORMANCY&zu_id=";
		
		}  
		
		function goInactive(){
			var conteturl=parent.content.deviceContent.location.href;
			var cmd=conteturl.split("=")[1].split("&")[0];
			if(cmd=="NOTAUDIT"){
				parent.content.deviceTop.location.href = "/admin/terminal/ter_header.jsp?cmd=MONITORING&left_cmd=INACTIVE";
				cmd="MONITORING";
			}
		    parent.content.deviceTop.location.href = "/admin/terminal/ter_header.jsp?cmd="+cmd+"&left_cmd=INACTIVE&title=INACTIVE&zu_id=";
			parent.content.deviceContent.location.href = "/rq/terminalList?cmd="+cmd+"&left_cmd=INACTIVE&zu_id=";
		}
		function sortAsc(){
			var conteturl=parent.content.deviceContent.location.href;
			var cmd=conteturl.split("=")[1].split("&")[0];
			if(cmd=="NOTAUDIT"){
				parent.content.deviceTop.location.href = "/admin/terminal/ter_header.jsp?cmd=MONITORING&left_cmd=ASC";
				cmd="MONITORING";
			}
			parent.content.deviceTop.location.href = "/admin/terminal/ter_header.jsp?cmd="+cmd+"&left_cmd=ASC&title=ASC&zu_id=";
			parent.content.deviceContent.location.href = "/rq/terminalList?cmd="+cmd+"&left_cmd=ASC&zu_id=";
		}
		function sortDesc(){
			var conteturl=parent.content.deviceContent.location.href;
			var cmd=conteturl.split("=")[1].split("&")[0];
			if(cmd=="NOTAUDIT"){
				parent.content.deviceTop.location.href = "/admin/terminal/ter_header.jsp?cmd=MONITORING&left_cmd=DESC";
				cmd="MONITORING";
			}
			parent.content.deviceTop.location.href = "/admin/terminal/ter_header.jsp?cmd="+cmd+"&left_cmd=DESC&title=DESC&zu_id=";
			parent.content.deviceContent.location.href = "/rq/terminalList?cmd="+cmd+"&left_cmd=DESC&zu_id=";
		}
		
		function gosub2(){
			var forms = document.fm1;
			forms.submit();	
		}
		
		function linkPage(url){
			parent.content.location.href=url;
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
	width:96%;
	padding:5px 0px 0px 5px;
	display:none;
}
</style>
</head>
<body onLoad="init()">

		    <div style="margin:0px;">
				<table width="100%" cellpadding="0" cellspacing="0" border="0" style="margin-top: 0px;padding: 0px" >
				  <tr>
				   <td height="30px" background="/images/device/btn_background.gif" align="center">
				     <span class="MenuTitle">终端管理</span></td>
				  </tr>
				</table>
			</div>
			<div class="menu" onClick="slide('sub4');"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;终端状态</div>
			<div id="sub4" class="submenu">
					<table>
						<tr>
							<td width="15"><img src="/images/leftmenu/active.gif" width="19" height="19"/><br></td>
							<td><a href="javascript:;" onclick="goActive();">&nbsp;&nbsp;&nbsp;当前连接</a><br></td>
						</tr>
						<tr>
							<td width="15"><img src="/images/leftmenu/active.gif" width="19" height="19"/><br></td>
							<td><a href="javascript:;" onclick="goDormancy();">&nbsp;&nbsp;&nbsp;当前休眠</a><br></td>
						</tr>
						<tr>
							<td width="15"><img src="/images/leftmenu/inactive.gif" width="19" height="19"/><br></td>
							<td><a href="javascript:;" onclick="goInactive();">&nbsp;&nbsp;&nbsp;当前断开</a><br></td>
						</tr>		
					</table>
			</div>
			<div class="menu" onClick="slide('sub2');"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;名称排序</div>
			<div id="sub2" class="submenu">
					<table>
						<tr>
							<td width="15"><img src="/images/leftmenu/ascending.gif" width="19" height="19"/><br></td>
							<td><a href="javascript:;" onclick="sortAsc();">&nbsp;&nbsp;&nbsp;升序排序</a><br></td>
						</tr>
						<tr>
							<td width="15"><img src="/images/leftmenu/descending.gif" width="19" height="19"/><br></td>
							<td><a href="javascript:;" onclick="sortDesc();">&nbsp;&nbsp;&nbsp;降序排序</a><br></td>
						</tr>		
					</table>
			</div>
			<div class="menu" onClick="gosub(false);slide('sub1');"><img src="/images/bullet.gif">&nbsp;&nbsp;&nbsp;组管理</div>
			<div id="sub1" class="submenu">
				<iframe name="ifrm_rms" width="98%" height="100%" scrolling='no' frameborder='0' marginwidth="0" marginheight="0"  onLoad="doResize('ifrm_rms',0.55)">
				</iframe>
			</div>
			
			<div id="sub33" class="submenu"></div>

       <form name="fm1" id="fm1" method="post">
			<input type=hidden name="jtype"/>
			<input type="hidden" name="subtype" id="subtype" value="" >		
			<input type="hidden" name="cmd" id="cmd"  value="" >	
			<input type="hidden" name="init_tree" id="init_tree"  value="" >
       </form>
</body>
</html>