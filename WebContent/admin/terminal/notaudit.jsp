<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title></title><script language="javascript" src="/js/vcommon.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<style type="text/css">
#divframe{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2);  visibility:hidden}
#mask{ position:absolute; top:0; left:0; width:expression(body.scrollWidth); height:expression(body.scrollHeight); background:#000000; filter:ALPHA(opacity=60); z-index:1; visibility:hidden}
#massage{border:#6699cc solid; border-width:1 1 1 1; background:#fff; color:#036; font-size:12px; line-height:150%; visibility:hidden}
.header{background:url(/images/device/btn_background.gif);  font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff;cursor: move;}
</style>

<script language="JavaScript" type="text/JavaScript">
var rqurl;
function audit(mac){
	rqurl=escape(window.location.href);
	//alert(rqurl);
	showDivFrame("审核终端","/rq/viwe?cmd=AUDIT&cid="+mac,"480","300");
}

function parentlocationreload(){
  // alert("98779")
   window.location.reload();
}

function onUpdateSoftware(checkIPs){
 cntip="";
showDivFrame("终端软件升级","/admin/terminal/updateSoftware.jsp?timeStamp=" + new Date().getTime(),"480","300");
}
function onRestart(checkIPs){
 cntip="";
showDivFrame("其他设置","/admin/terminal/restart.jsp?timeStamp=" + new Date().getTime(),"480","400");
}
function onDown(checkIPs){
 cntip="";
showDivFrame("终端休眠","/admin/terminal/ondown.jsp?timeStamp=" + new Date().getTime(),"480","300");
}
function onStartcnt(checkIPs){
 cntip="";
showDivFrame("停止休眠","/admin/terminal/startcnt.jsp?timeStamp=" + new Date().getTime(),"480","300");
}
function sendNotice(checkIPs){
 cntip="";
showDivFrame("发布文字通知","/admin/terminal/sendNotice.jsp?timeStamp=" + new Date().getTime(),"750","320");
}
function clearPrograme(checkIPs){
 cntip="";
showDivFrame("节目初始化","/admin/terminal/clearproject.jsp?timeStamp=" + new Date().getTime(),"480","300");
}
function timeSynchronization(){
 cntip="";
 showDivFrame("时间同步","/admin/terminal/clientTimeSynchronization.jsp?timeStamp=" + new Date().getTime(),"480","300");
}
function updateDownloadStartEnd(){
 cntip="";
  showDivFrame("开关机设置","/admin/terminal/update_download_start_end.jsp?timeStamp=" + new Date().getTime(),"530","300");
}
function deleteClient(){
 cntip="";
  showDivFrame("删除终端","/admin/terminal/deleteClient.jsp?timeStamp=" + new Date().getTime(),"480","300");
}

   var cntip;
function guiCamera(cnt_title,cntIp,cntMac){
    cntip=cntIp;
	document.getElementById("div_iframe").src="/loading.jsp";
	showDivFrame("终端【"+cnt_title+"】当前截屏图片","/admin/terminal/guiCamera.jsp?cntIp="+cntIp+"&cnt_mac="+cntMac+"&t=" + new Date().getTime(),"700","500");
}
function viewProjectMenu(programName,cntmac,cntIp){
  cntip="";
  showDivFrame("终端【"+cntIp.replace("!","")+"】节目单","/admin/terminal/viewProjectMenu.jsp?resips="+cntIp+"&cntmac="+cntmac+"&t=" + new Date().getTime(),"800","400");
}
function viewterminal(cname,cid){
   cntip="";
   showDivFrame("终端【"+cname+"】详细信息","/rq/viwe?cmd=viweOk&cid="+cid,"600","300");
}
function showDivFrame(title,url,fwidth,fheight){
    parent.parent.parent.parent.document.body.scrollTop = "0px";
	parent.parent.parent.parent.document.getElementById("div_iframe1").width=fwidth;
	if(fheight!=""){
		parent.parent.parent.parent.document.getElementById("div_iframe1").height=fheight;
	}
	parent.parent.parent.parent.document.getElementById("divframe1").style.left=(parent.parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";;
	parent.parent.parent.parent.document.getElementById("divframe1").style.top=(parent.parent.parent.parent.document.body.clientHeight - fheight) / 3+"px";
	parent.parent.parent.parent.document.getElementById("div_iframe1").src=url;
	parent.parent.parent.parent.document.getElementById("titlename1").innerHTML=title;
	parent.parent.parent.parent.document.getElementById("divframe1").style.display='block';
	parent.parent.parent.parent.document.getElementById("massage1").style.display='block';
    parent.parent.parent.parent.document.getElementById("bgDiv").style.visibility='visible';
}

function SetWinHeight(obj) 
{ 
 var win=obj; 
 if (document.getElementById) 
 { 
  if (win && !window.opera) 
  { 
	if (win.contentDocument && win.contentDocument.body.offsetHeight){  
	win.height = win.contentDocument.body.offsetHeight;  
	}else if(win.Document && win.Document.body.scrollHeight){ 
    win.height = win.Document.body.scrollHeight;
	}
  } 
 } 
}
function closedivframe(){
	cntip="";
	document.getElementById("div_iframe").src="/loading.jsp";
	document.getElementById('divframe').style.visibility="hidden";	
}
function refresh(){
 if(cntip!=""){
   	document.getElementById("div_iframe").src="http://"+cntip+"/guiCamera1.jsp?timeStamp="+new Date().getTime();
   	window.frames["div_iframe"].location.reload();
 }else{
   window.frames["div_iframe"].location.reload();
 }
}
function deletecnt(mac){
window.location.href="/admin/terminal/deleteNotauditClient.jsp?mac="+mac;
}
</script>
</head>
<body  >

<!-- list -->
  <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="table1">
  
    <tr>
      <td>
      <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0"  class="TitleBackground">
    <tr>
      <td width="20%" class="InfoTitle">IP地址</td>
      <td width="20%" class="InfoTitle">MAC地址</td>
      <td width="20%" class="InfoTitle">当前播放节目</td> 
      <td width="15%" class="InfoTitle">连接状态</td>
      
      <td width="25%" class="InfoTitle">操作</td>           
    </tr>
</table>
<c:if test="${empty requestScope.allTerminal}">
<br/><center>暂无待审核终端！</center>

</c:if>
 <div style="overflow: auto;height: 450;width: 100%" id="content_div_id">
      <c:forEach var="terminal" items="${requestScope.allTerminal}">
	   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0" id="${terminal.cnt_mac }">
	  
        <tr class=TableTrBg06_ onmouseover="this.className='TableTrBg06'" onmouseout="this.className='TableTrBg06_'">     
          <td width="20%">${terminal.cnt_ip }</td>
          <td width="20%">${terminal.cnt_mac }</td>
           <td width="20%">${terminal.cnt_nowProgramName }</td>  
          <td width="15%">${terminal.cnt_islink_zh }</td> 
         
          <td width="25%">
          <input type="button" onclick="audit('${terminal.cnt_mac }')" class="button"  value= " 审 核 " />  
           <input type="button" onclick="deletecnt('${terminal.cnt_mac }')" class="button"  value= " 删 除 " />   
         </tr>
      </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="Line_01"></td>
          </tr>
        </table>
	  	</c:forEach>	
	  	</div>			       
      

<div id="divframe">
<div  id="massage">
<table cellpadding="0" cellspacing="0" >
	<tr  height="30px;" class=header  onmousedown=MDown(divframe)><td align="left" style="font-weight: bold"><span id="titlename"></span></td>
		<td height="5px" align="right"><img src="/images/refresh.gif" style="cursor: pointer;" onclick="javaScript:refresh();"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javaScript:;" style="color: #000000" onclick="closedivframe();">关闭</a></td></tr>
	<tr><td colspan="2">
	<iframe src="/loading.jsp" scrolling="no" id="div_iframe" onload="SetWinHeight(this)"  name="div_iframe" frameborder="0" ></iframe>
		
	</td></tr>
</table>
</div>
</div>
</body>
</html>