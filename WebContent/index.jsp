<%@ page language="java"  pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <title>多媒体信息发布系统</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css" />
    <style type="text/css">
		#bgDiv {
		    position:absolute; top:0; left:0; width:expression(body.scrollWidth); height:expression(body.scrollHeight); background:#ffffff; filter:ALPHA(opacity=60); z-index:50; visibility:hidden
		}#bgDiv2 {
		    position:absolute; top:0; left:0; width:expression(body.scrollWidth); height:expression(body.scrollHeight); background:#ffffff; filter:ALPHA(opacity=60); z-index:70; visibility:hidden
		}
		#divframe1 {
			position: absolute;
			z-index: 80;
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
		#divframe2 {
			position: absolute;
			z-index: 60;
			filter: dropshadow(color = #666666, offx = 3, offy = 3, positive = 2);
			display: none;
		}
		#massage2 {
			border: #6699cc solid;
			border-width: 1 1 1 1;
			background: #fff;
			color: #036;
			font-size: 12px;
			line-height: 150%;
			display: none;
		}#divframe3 {
			position: absolute;
			z-index: 80;
			filter: dropshadow(color = #666666, offx = 3, offy = 3, positive = 2);
			display: none;
		}
		#massage3 {
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
	
		function closedivframe(num){
			document.getElementById("divframe"+num).style.display='none';
			document.getElementById("massage"+num).style.display='none';
			document.getElementById("bgDiv").style.display='none';
			document.getElementById("bgDiv").style.visibility='hidden';
			document.getElementById("div_iframe"+num).src="/loading.jsp";
			
            //判断某个函数是否存在
			if(typeof  window.frames["div_iframe"+num].pauseVLC=='function'){
			    //alert("确人关闭！");
			    window.frames["div_iframe"+num].pauseVLC();//关闭视频预览窗口
			}
		}
		
		function closedivframe1(){
		    closedivframe(2);
		}
		
	    function showDiv(title,fwidth,fheight,url){
			
			document.body.scrollTop = "0px";
			document.getElementById("div_iframe3").width=fwidth;
			document.getElementById("div_iframe3").height=fheight;
			document.getElementById("divframe3").style.left=(parent.parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";
			document.getElementById("divframe3").style.top="5px";
			document.getElementById("div_iframe3").src=url;
			document.getElementById("titlename3").innerHTML=title;
			document.getElementById("divframe3").style.display='block';
			document.getElementById("massage3").style.display='block';
			document.getElementById("bgDiv2").style.display='block';
			document.getElementById("bgDiv2").style.visibility='visible';
		}
		function showDiv1(title,fwidth,fheight,url){
			document.body.scrollTop = "0px";
			document.getElementById("div_iframe1").width=fwidth;
			document.getElementById("div_iframe1").height=fheight;
			document.getElementById("divframe1").style.left=(parent.parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";
			document.getElementById("divframe1").style.top="5px";
			document.getElementById("div_iframe1").src=url;
			document.getElementById("titlename1").innerHTML=title;
			document.getElementById("divframe1").style.display='block';
			document.getElementById("massage1").style.display='block';
			document.getElementById("bgDiv").style.display='block';
			document.getElementById("bgDiv").style.visibility='visible';
		}
		function closedivframe3(){

			document.getElementById("div_iframe3").src="/loading.jsp";
			document.getElementById("divframe3").style.display='none';
			document.getElementById("massage3").style.display='none';
			document.getElementById("bgDiv2").style.display='none';
			document.getElementById("bgDiv2").style.visibility='hidden';
		}
		function closedivframe2(){
			document.getElementById("div_iframe1").src="/loading.jsp";
			document.getElementById("divframe1").style.display='none';
			document.getElementById("massage1").style.display='none';
			document.getElementById("bgDiv2").style.display='none';
			document.getElementById("bgDiv2").style.visibility='hidden';
		}
	function showDiv2(title,fwidth,fheight,url){
			
			document.body.scrollTop = "0px";
			document.getElementById("div_iframe2").width=fwidth;
			document.getElementById("div_iframe2").height=fheight;
			document.getElementById("divframe2").style.left=(parent.parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";
			document.getElementById("divframe2").style.top=(parent.parent.parent.parent.document.body.clientHeight - fheight) / 2 + "px";
			document.getElementById("div_iframe2").src=url;
			document.getElementById("titlename2").innerHTML=title;
			document.getElementById("divframe2").style.display='block';
			document.getElementById("massage2").style.display='block';
			document.getElementById("bgDiv2").style.display='none';
			document.getElementById("bgDiv2").style.visibility='visible';
		}
		function SetWinHeight(obj){
				 
					 var win=obj; 
					 if (document.getElementById) {
					  
						  if (win && !window.opera){ 
						   
							if (win.contentDocument && win.contentDocument.body.offsetHeight){  
							    win.height = win.contentDocument.body.offsetHeight;  
							}else if(win.Document && win.Document.body.scrollHeight){ 
						        win.height = win.Document.body.scrollHeight;
							}
						  } 
					 } 
				}
//window.onbeforeunload=function(){
//var n=window.event.screenX-window.screenLeft;
//var b=n>document.documentElement.scrollWidth-20;
//if(b&&window.event.clientY<0||window.event.altKey){
//}else{
    //event.returnValue ="友情提示：刷新页面将退出系统！";
//}
//}

    </script>
  </head>
  <body style="margin: 0px;overflow-x:hidden;overflow-y:hidden" >
  
   <iframe  name="homeframe"  src="/rq/login?initdo=1"  width="100%" height="100%" frameborder="5" scrolling="auto"></iframe>
  
   <div id="bgDiv"></div>
   <div id="bgDiv2"></div>
   <div id="divframe2">
			<div  id="massage2"><!-- 带提示信息 -->
				<table cellpadding="0" cellspacing="0" >
					<tr  height="30px;" class=header  onmousedown=MDown(divframe2)><td align="left" style="font-weight: bold"><span id="titlename2"></span></td>
						<td height="15px" align="right">&nbsp;&nbsp;&nbsp;&nbsp;<a href="javaScript:;" title="关闭2"  style="color: #000000" onclick="closedivframe1();return false">关闭&nbsp;&nbsp;</a></td></tr>
					<tr><td colspan="2">
					    <iframe src="/loading.jsp" scrolling="no" id="div_iframe2" onload="SetWinHeight(this)"   name="div_iframe2" frameborder="0" ></iframe>
					</td></tr>
				</table>
			</div>
		</div>
		<div id="divframe1"><!-- 不带提示信息 -->
			<div  id="massage1">
				<table cellpadding="0" cellspacing="0" >
					<tr  height="30px;" class=header  onmousedown=MDown(divframe1)><td align="left" style="font-weight: bold"><span id="titlename1"></span></td>
						<td height="15px" align="right">&nbsp;&nbsp;&nbsp;&nbsp;<a href="javaScript:;" title="关闭1"  style="color: #000000" onclick="closedivframe(1);return false">关闭&nbsp;&nbsp;</a></td></tr>
					<tr><td colspan="2">
					    <iframe src="/loading.jsp" scrolling="no" id="div_iframe1" onload="SetWinHeight(this)"   name="div_iframe1" frameborder="0" ></iframe>
					</td></tr>
				</table>
			</div>
		</div>
		<div id="divframe3"><!-- 建节目选择媒体 -->
			<div  id="massage3">
				<table cellpadding="0" cellspacing="0" >
					<tr  height="30px;" class=header  onmousedown=MDown(divframe3)><td align="left" style="font-weight: bold"><span id="titlename3"></span></td>
						<td height="15px" align="right">&nbsp;&nbsp;&nbsp;&nbsp;<a href="javaScript:;" title="关闭3"  style="color: #000000" onclick="closedivframe3();return false">关闭&nbsp;&nbsp;</a></td></tr>
					<tr><td colspan="2">
					    <iframe src="/loading.jsp" scrolling="no" id="div_iframe3" onload="SetWinHeight(this)"   name="div_iframe3" frameborder="0" ></iframe>
					</td></tr>
				</table>
			</div>
		</div>
		
		
  </body>
</html>