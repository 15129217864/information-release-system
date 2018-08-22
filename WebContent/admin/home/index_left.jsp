<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma"  content="no-cache"/> 
<title>Sliding Menu</title><script language="javascript" src="/js/vcommon.js"></script>
<link rel="stylesheet"
	href="/css/style.css" type="text/css" />
<script language="javascript"
	src="/script/slide.js"></script>
<style>
BODY {
	font-size:9pt;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url('/images/menu_bg.gif');
}
.menu {
    cursor:hand;
	border:0px solid ;
	padding:7px 3px 3px 2px;
	background-image:URL('/images/slide_bg.gif');
	width:216px;
	height:25px;
	font-size:9pt;
	font-weight:bold;
	color:FFFFFF;
}

.submenu {
	width:215px;
	padding:0px 0px 0px 5px;
	display:none;
}
</style>
</head>

<body>
<form name="fm1" method="post"><input type=hidden name="cmd" />
<table width="216" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td
			background="/images/title_main.gif"
			width="216" height="30" align="center"><span class="MenuTitle"> <fmt:message key="did.layouts.common.main" />
				</span>
		</td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td align="center">
		<table width="207" height="57" border="0" cellpadding="0"
			cellspacing="5"
			background="/images/main/welcome_back.gif">
			<tr>
				<td align="center" class="Bold"></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center">
		<table width="207" border="0" cellpadding="0" cellspacing="5">
			<tr>	
				<td align="center"><span style="font:8pt;font-weight:bold">
				»¶Ó­ÄúµÇÂ¼CMSServer
				</span></td>
			</tr>
		</table>
		</td>
	</tr>	
	<tr>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<td align="center">
		<table width="207" height="57" border="0" cellpadding="0"
			cellspacing="5">
			<tr>
				<td width="15" valign="top" class="Bold"><img
					src="/images/main/clock.gif"
					width="16" height="16"></td>
				<td><span class="LastLoginTime"><fmt:message
					key="did.layouts.common.lastlogin" /></span><br>
				</td>
			</tr>
			<tr>
				<td valign="top" class="Bold" align="center"><img
					src="/images/main/clock.gif"
					width="16" height="16"></td>
				<td><span class="CurrentLogintime"><fmt:message
					key="did.layouts.common.curlogin" /> </span><br>
				</td>
			</tr>
		</table>
		</td>
	</tr>			
</table>
</form>
</body>
</html>
