<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String filepath=request.getParameter("filepath")==null?"":request.getParameter("filepath");
%>
<html>
  <head>
	<title></title>
	<script language="javascript" src="/js/vcommon.js"></script>
	<link rel="stylesheet" href="/css/style.css" type="text/css" />
	<script type="text/javascript">
	  function closeVLC(){
		   var vlc = getVLC("vlcid");
		   if(vlc){
		     alert("closeVLC");
			  vlc.stop();//播放remvb格式视频 时IE会蹦溃
		   }
		}
		function pauseVLC(){
		   var vlc = getVLC("vlcid");
		   if(vlc){
		      //alert("play_video__pauseVLC");
		      vlc.pause();
		   }
		}
		
		function getVLC(name)
		{
		    if (window.document[name])
		    {
		        return window.document[name];
		    }
		    if (navigator.appName.indexOf("Microsoft Internet")==-1)
		    {
		        if (document.embeds && document.embeds[name])
		            return document.embeds[name];
		    }
		    else // if (navigator.appName.indexOf("Microsoft Internet")!=-1)
		    {
		        return document.getElementById(name);
		    }
		}
	
	</script>
  </head>
  <body>
<object classid="clsid:E23FE9C6-778E-49D4-B537-38FCDE4887D8" codebase="http://downloads.videolan.org/pub/videolan/vlc/latest/win32/axvlc.cab"
  width="100%" height="100%" events="True" id="vlcid">
<param name="MRL" value="<%=filepath%>" />
<param name="ShowDisplay" value="True" />
<param name="Loop" value="False" />
<param name="AutoPlay" value="true" />
</object> 
<script type="text/javascript"> 
if(document.all.vlcid.object == null) {
parent.parent.closedivframe(1);
alert("您未安装视频预览工具，不能预览带视频节目，请在【媒体库】右上角点击下载安装！");
}
</script> 
</body>
</html>
