<%@ page language="java"  pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${requestScope.islogin!='yes'}">
<script type="text/javascript">
window.location.href="/login/login.jsp";
</script>
</c:if>
<html>
  <head>
    <title>多媒体信息发布系统</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css" />
    <style type="text/css">
		#bgDiv {
		    position: absolute;
		    display:none;
		    z-index: 1;
		    top: 0px;
		    left: 0px;
		    right:0px;
		    background-color: #777;
		    filter:progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75)
		    opacity: 0.6;
		}
		#divframe {
			position: absolute;
			z-index: 999;
			filter: dropshadow(color = #666666, offx = 3, offy = 3, positive = 2);
			display: none;
		}
		#massage {
			border: #6699cc solid;
			border-width: 1 1 1 1;
			background: #fff;
			color: #036;
			font-size: 12px;
			line-height: 150%;
			display: none;
		}#divframe1 {
			position: absolute;
			z-index: 999;
			filter: dropshadow(color = #666666, offx = 3, offy = 3, positive = 2);
			display: none;
		}
		#massage1 {
			border: #6699cc solid;
			border-width: 1 1 1 1;
			background: #fff;
			color: #036;
			font-size: 12px;
			line-height: 150%;
			display: none;
		}
		.header {
			background: url(/images/device/btn_background.gif);
			font-family: Verdana, Arial, Helvetica, sans-serif;
			font-size: 12px;
			padding: 3 5 0 5;
			cursor: move;
		}
    </style>
    <script language="javascript" src="/js/vcommon.js"></script>
	<script type="text/javascript">
		function showbgDiv(){
		    var bgObj=document.getElementById("bgDiv");
			bgObj.style.width = "100%";
			bgObj.style.height = "100%";
			bgObj.style.display = "block";
		}function hiddenbgDiv(){
		    var bgObj=document.getElementById("bgDiv");
			bgObj.style.width = "0";
			bgObj.style.height = "0";
			bgObj.style.display = "none";
		}
		function closedivframe(){
			document.getElementById("div_iframe").src="/loading.jsp";
			document.getElementById("divframe").style.display='none';
			document.getElementById("massage").style.display='none';
			hiddenbgDiv();
		} 
		function closedivframe1(){
			document.getElementById("div_iframe1").src="/loading.jsp";
			document.getElementById("divframe1").style.display='none';
			document.getElementById("massage1").style.display='none';
			hiddenbgDiv();
		}
		function refresh(){
		   window.frames["div_iframe1"].location.reload();
		}  		
    </script>
  </head>
  <body style="margin: 0px;overflow-x:hidden;overflow-y:hidden">
     <iframe name="homeframe"  src="/admin/index.jsp" width="100%" height="100%" frameborder="0" scrolling="no" ></iframe>
   <div id="bgDiv"></div>
   	<div id="divframe">
			<div id="massage">
				<table cellpadding="0" cellspacing="0">
					<tr height="5px;" class=header>
						<td align="left" style="font-weight: bold">
							<span id="titlename"></span>
						</td>
					</tr>
					<tr>
						<td>
						<iframe src="/loading.jsp" scrolling="no" id="div_iframe"
								name="div_iframe" frameborder="0" width="330" height="150"></iframe>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div id="divframe1">
			<div  id="massage1">
				<table cellpadding="0" cellspacing="0" >
					<tr  height="30px;" class=header  onmousedown=MDown(divframe1)><td align="left" style="font-weight: bold"><span id="titlename1"></span></td>
						<td height="15px" align="right"><img src="/images/refresh.gif" title="刷新" style="cursor: pointer;" onclick="javaScript:refresh();"/>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javaScript:;" title="关闭"  style="color: #000000" onclick="closedivframe1();">关闭</a></td></tr>
					<tr><td colspan="2">
					    <iframe src="/loading.jsp" scrolling="no" id="div_iframe1"  name="div_iframe1" frameborder="0" ></iframe>
					</td></tr>
				</table>
			</div>
		</div>
  </body>
</html>

