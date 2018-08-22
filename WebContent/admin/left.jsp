<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma"  content="no-cache"/> 
<title>Sliding Menu</title><script language="javascript" src="/js/vcommon.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<style>
BODY {
	font-size:9pt;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url('/images/menu_bg.gif');
}

</style>
</head>
<body>	
<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
   <td height="30" background="/images/device/btn_background.gif" valign="middle" align="center">
   <span class="MenuTitle">&nbsp;&nbsp;&nbsp;主页</span></td>
  </tr>
   <tr>
		<td align="center" height="5px"></td>
	</tr>
    <tr>
		<td align="center">
		<table width="207" height="57" border="0" cellpadding="0"
			cellspacing="5"
			background="/images/main/welcome_back.gif">
			<tr>
				<td align="center" class="Bold">欢迎：${lg_user.lg_name }</td>
			</tr>
		</table>
		</td>
	</tr>
	  <tr>
		<td align="center" height="5px">
		&nbsp;
		</td>
	</tr>
	<tr>
		<td align="center">
		<table width="207" border="0" cellpadding="0" cellspacing="5">
			<tr>	
				<td align="center"><span style="font:8pt;font-weight:bold; line-height: 25px">
				欢迎您登录<br/>多媒体信息发布系统
				</span></td>
			</tr>
		</table>
		</td>
	</tr>	
	  <tr>
		<td align="center" height="5px">
		&nbsp;
		</td>
	</tr>
	
	<tr>
		<td align="center">
		<table width="180" height="77" border="0" cellpadding="0"
			cellspacing="5">
			<tr>
				<td width="15" valign="top" class="Bold"><img
					src="/images/main/clock.gif"
					width="16" height="16"></td>
				<td height="50" valign="top"><span class="LastLoginTime" >上次登录时间</span><br>
				${lg_user.last_login_time }
				</td>
			</tr>
			<tr>
				<td valign="top" class="Bold" align="center"><img
					src="/images/main/clock.gif"
					width="16" height="16"></td>
				<td><span class="CurrentLogintime">当前登录时间</span><br>
				${lg_user.now_login_time }
				</td>
			</tr>
		</table>
		</td>
	</tr>			
</table>


</body>
</html>