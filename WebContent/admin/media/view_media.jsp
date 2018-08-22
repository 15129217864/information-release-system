<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String media_type=request.getParameter("media_type");
String filepath=request.getParameter("filepath");
if("TEXT".equals(media_type)){
	String projectpath=FirstStartServlet.projectpath;
	String content=Util.readfile(projectpath+filepath);
	request.setAttribute("content",content);
}
request.setAttribute("media_type",media_type);
request.setAttribute("filepath",filepath);
%>
<html>
  <head>
    <title>My JSP 'view_media.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script>
function closedivframe(){
	parent.closedivframe(2);
}

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
     // alert("----view_media.jsp---pauseVLC");
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
  <c:if test="${media_type=='MOVIE'}">
	<object classid="clsid:E23FE9C6-778E-49D4-B537-38FCDE4887D8" codebase="http://downloads.videolan.org/pub/videolan/vlc/latest/win32/axvlc.cab"
	  width="800" height="500" events="True" id="vlcid">
		<param name="MRL" value="${filepath}" />
		<param name="ShowDisplay" value="True" />
		<param name="Loop" value="true" />
		<param name="AutoPlay" value="true" />
	</object>
	<script> 
	if(document.all.vlcid.object == null) {
	closedivframe();
	alert("您未安装视频预览工具，不能预览视频文件，请下载安装！");
	}
	</script> 
 </c:if>
 
 <c:if test="${media_type=='IMAGE'}">
<center> <img src="${filepath}" height="500"  width="800"  border="0" alt="图片" title="点击看原图" onclick="window.open('${filepath}')" /></center>
 </c:if>
  <c:if test="${media_type=='SOUND'}">
	  <object classid="clsid:E23FE9C6-778E-49D4-B537-38FCDE4887D8" codebase="http://downloads.videolan.org/pub/videolan/vlc/latest/win32/axvlc.cab"
	  width="0" height="0" events="True" id="vlcid">
		<param name="MRL" value="${filepath}" />
		<param name="ShowDisplay" value="True" />
		<param name="Loop" value="true" />
		<param name="AutoPlay" value="true" />
	</object>
<script> 
if(document.all.vlcid.object == null) {
closedivframe();
alert("您未安装视频预览工具，不能预览声音文件，请下载安装！");
}
</script> 
<center><img src="/mediafile/sound_slightly.gif"/></center>
</c:if>
 <c:if test="${media_type=='FLASH'}">
<center>
<div><embed src="${filepath}" type="application/x-shockwave-flash" play="true" width="800" height="500" loop="true" menu="true"></embed></div>

</center>
 </c:if>
 
  <c:if test="${media_type=='WEB'}">
<center>
<div><iframe src="${filepath}" width="800" height="500" frameborder="0"/></div>
</center>
 </c:if>
 
  <c:if test="${media_type=='TEXT'}">
<center>
<textarea style="width: 800;height: 500" readonly="readonly">${content}</textarea>
</center>
 </c:if>
  </body>
</html>
