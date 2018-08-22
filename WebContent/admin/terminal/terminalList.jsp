<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<html>
  <head>
    <title>My JSP 'terminalList.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
<script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type="text/javascript">

function progressShow() {
	document.all.systemWorking.style.display = "block";
}   
function progressBarHidden() {
	document.all.systemWorking.style.display = "none";
}
 function progressBarOpen(){
  			var cWidth = document.body.clientWidth;
  			var cHeight= document.body.clientHeight;
			var divNode = document.createElement( 'div' );	
			divNode.setAttribute("id", "systemWorking");
			var topPx=(cHeight)*0.4;
			var defaultLeft=(cWidth)*0.4;
			divNode.style.cssText='position:absolute;margin:0;top:'+topPx+'px;left:'+defaultLeft+';width:215;height:59;background-image:url(/images/wait_background.gif);z-index:9999;text-align:center;padding-top:20'; 
			divNode.innerHTML= "<img src='/images/mid_giallo.gif' align=absmiddle><font>&nbsp;Loading...</font>";
			divNode.style.display='none';	
			document.getElementsByTagName("body")[0].appendChild(divNode);   		
  }
</script>


  </head>
  
  <body   onload="progressBarOpen();">
    This is my JSP page. <br>
  </body>
</html>
