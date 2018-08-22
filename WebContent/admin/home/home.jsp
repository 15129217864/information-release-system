<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<html>
  <head>
    <title>home</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="/css/style.css" type="text/css" />
	 <script language="javascript" src="/js/vcommon.js"></script>
		<script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
.left_fieldset{width: 100%;height: 90%; border: #6699cc 1 solid; padding-left:10px;  padding-top: 5px; margin: 20px; font-size: 13px}
.left_div{width: 450px;height: 200; border: #6699cc 0 solid;  overflow: auto;}
</style>
<script type="text/javascript">
var myDate = new Date();
var _year=myDate.getYear();
var _month=myDate.getMonth()+1;
var _date=myDate.getDate();
if(_month<10){_month="0"+_month};
if(_date<10){_date="0"+_date};
var nowtime=_year+"-"+_month+"-"+_date;
function onLoad(){
NotAudit();
Close();
Sleep();
Logs();
}
function NotAudit(){
DwrClass.getTerminalBystr(" where cnt_status=0",getNotAudit);
}
function Close(){
DwrClass.getTerminalBystr(" where cnt_islink=3 and cnt_status=1 ",getClose);
}
function Sleep(){
DwrClass.getTerminalBystr(" where  (cnt_islink=1 or  cnt_islink=2) and  cnt_playstyle='CLOSE' ",getSleep);
}
function Logs(){
DwrClass.getLogsBystr(" where logdel=0 and  logdate='"+nowtime+"' ",getLogs);
}
function getNotAudit(notAuditList){
var str="<table width='100%' cellpadding='0' cellspacing='0' border=0>";
 for(var i=0;i<notAuditList.length;i++){
 str+="<tr class='TableTrBg23_' onmouseover=this.className='TableTrBg23' onmouseout=this.className='TableTrBg23_' height='20px'><td  width='25%'>"+notAuditList[i].cnt_ip+"</td><td  width='25%'>"+notAuditList[i].cnt_mac+"</td><td  width='40%'>"+notAuditList[i].cnt_nowProgramName+"</td><td  width='10%'>"+notAuditList[i].cnt_islink_zh+"</td></tr>";
 }
  if(notAuditList.length==0){
 str+="<tr class='TableTrBg22_'><td  width='100%' colspan='1' align='center' height='100px' vlign='bottom' >暂无待审核终端</td></tr>";
 }
 str+="</table>";
document.getElementById("notaudit_div").innerHTML=str;
setTimeout("NotAudit()",10000);
}
function getClose(closeList){
var str="<table width='100%' cellpadding='0' cellspacing='0' border=0>";
 for(var i=0;i<closeList.length;i++){
 str+="<tr class='TableTrBg23_' onmouseover=this.className='TableTrBg23' onmouseout=this.className='TableTrBg23_' height='20px'><td  width='25%'>"+closeList[i].cnt_ip+"</td><td  width='25%'>"+closeList[i].cnt_mac+"</td><td  width='40%'>"+closeList[i].cnt_nowProgramName+"</td><td  width='10%'>"+closeList[i].cnt_islink_zh+"</td></tr>";
 }
  if(closeList.length==0){
 str+="<tr class='TableTrBg22_'><td  width='100%' colspan='1' align='center' height='100px' vlign='bottom' >暂无断开终端</td></tr>";
 }
 str+="</table>";
document.getElementById("close_div").innerHTML=str;
setTimeout("Close()",10000);
}
function getSleep(sleepList){
var str="<table width='100%' cellpadding='0' cellspacing='0' border=0>";
 for(var i=0;i<sleepList.length;i++){
 str+="<tr class='TableTrBg23_' onmouseover=this.className='TableTrBg23' onmouseout=this.className='TableTrBg23_' height='20px'><td  width='25%'>"+sleepList[i].cnt_ip+"</td><td  width='25%'>"+sleepList[i].cnt_mac+"</td><td  width='40%'>"+sleepList[i].cnt_nowProgramName+"</td><td  width='10%'>"+sleepList[i].cnt_islink_zh+"</td></tr>";
 }
 if(sleepList.length==0){
 str+="<tr class='TableTrBg22_'><td  width='100%' colspan='1' align='center' height='100px' vlign='bottom' >暂无休眠终端</td></tr>";
 }
 str+="</table>";
document.getElementById("sleep_div").innerHTML=str;
setTimeout("Sleep()",10000);
}

function getLogs(logsList){
var str="<table width='100%' cellpadding='0' cellspacing='0' border=0>";
 for(var i=0;i<logsList.length;i++){
 str+="<tr class='TableTrBg23_' onmouseover=this.className='TableTrBg23' onmouseout=this.className='TableTrBg23_' height='20px'><td  width='25%'>"+logsList[i].logtime+"&nbsp;&nbsp;&nbsp;</td><td>"+logsList[i].loglog+"</td></tr>";
 }
 if(logsList.length==0){
 str+="<tr class='TableTrBg22_'><td  width='100%' colspan='1' align='center' height='100px' vlign='bottom' >暂无系统日志</td></tr>";
 }
 str+="</table>";
document.getElementById("logs_div").innerHTML=str;
setTimeout("Logs()",10000);
}
</script>
  </head>
  <body style="background-color:#F5F9FD;" onload="onLoad()">
      <table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
      	<tr>
      		<td valign="top" width="50%" height="50%"  >
				<fieldset class="left_fieldset">
					<legend align="center" >待审核终端</legend>
					<table width='97%' cellpadding='0' cellspacing='0' height="30px"><tr  class='TableTrBg22_'><td width='25%'>终端IP</td><td width='25%'>终端MAC</td><td width='40%'>当前播放节目</td><td width='10%'>状态</td></tr>
					</table>
					<div  class="left_div" id="notaudit_div">
						<table align="center" width="100%" height="100%" border="0" style="font-size: 12px">
						
    	<tr><td align="center" valign="middle"><img src="/images/mid_giallo.gif"/>&nbsp;loading...</td></tr>
    </table>
					</div>
				</fieldset>
			</td>
      		<td  width="50%"  valign="top">
			<fieldset class="left_fieldset">
					<legend align="center" >当前断开终端</legend>
					<table width='97%' cellpadding='0' cellspacing='0' height="30px"><tr  class='TableTrBg22_'><td width='25%'>终端IP</td><td width='25%'>终端MAC</td><td width='40%'>当前播放节目</td><td width='10%'>状态</td></tr>
					</table>
					<div  class="left_div" id="close_div">
						<table align="center" width="100%" height="100%" border="0"  style="font-size: 12px">
    	<tr><td align="center" valign="middle"><img src="/images/mid_giallo.gif"/>&nbsp;loading...</td></tr>
    </table>
					</div>
				</fieldset>
			</td>
      	</tr>
    	<tr>
      		<td valign="top" width="50%" height="50%">
				<fieldset class="left_fieldset">
					<legend align="center" >当前休眠终端</legend>
					<table width='97%' cellpadding='0' cellspacing='0' height="30px"><tr  class='TableTrBg22_'><td width='25%'>终端IP</td><td width='25%'>终端MAC</td><td width='40%'>当前播放节目</td><td width='10%'>状态</td></tr>
					</table>
					<div  class="left_div" id="sleep_div">
						<table align="center" width="100%" height="100%" border="0"  style="font-size: 12px">
    	<tr><td align="center" valign="middle"><img src="/images/mid_giallo.gif"/>&nbsp;loading...</td></tr>
    </table>
					</div>
				</fieldset>
			</td>  
      		<td  width="50%"  valign="top">
			<fieldset class="left_fieldset">
					<legend align="center" >今日系统日志</legend>
					<table width='97%' cellpadding='0' cellspacing='0' height="30px"><tr  class='TableTrBg22_'><td width='25%'>日志时间</td><td width='75%'>日志内容</td></tr>
					</table>
					<div  class="left_div" id="logs_div">
						<table align="center" width="100%" height="100%" border="0"  style="font-size: 12px">
    	<tr><td align="center" valign="middle"><img src="/images/mid_giallo.gif"/>&nbsp;loading...</td></tr>
    </table>
					</div>
				</fieldset>
			</td>
      	</tr>
      </table>
  </body>
</html>
