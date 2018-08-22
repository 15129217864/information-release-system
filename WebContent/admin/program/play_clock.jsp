<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String color=request.getParameter("color1")==null?"":request.getParameter("color1");
String fontsize=request.getParameter("fontsize")==null?"30":request.getParameter("fontsize");
String scale=request.getParameter("scale")==null?"1":request.getParameter("scale");
request.setAttribute("scale",scale);
request.setAttribute("color",color);
request.setAttribute("fontsize",fontsize);
%>
<html>
  <head>
	<title></title>
	<script language="javascript" src="/js/vcommon.js"></script>
  </head>
  <body STYLE="background-color:transparent"> 
</body>
<div id="bgclockshade"  style="width:100%; height:100%;font-family:Arial;color:#${color};font-size:${fontsize<30?fontsize*(scale/1.5):30}px; font-weight:bold;"></div>
</html>
<script>
function clockon() {
thistime= new Date()
var hours=thistime.getHours()
var minutes=thistime.getMinutes()
var seconds=thistime.getSeconds()
if (eval(hours) <10) {hours="0"+hours}
if (eval(minutes) < 10) {minutes="0"+minutes}
if (seconds < 10) {seconds="0"+seconds}
thistime = hours+":"+minutes+":"+seconds
document.getElementById("bgclockshade").innerHTML=thistime;
var timer=setTimeout("clockon()",1000)
}
clockon();
</script>