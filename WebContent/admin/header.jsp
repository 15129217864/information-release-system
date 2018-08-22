<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.utils.DESPlusUtil"/>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";

 Users user = (Users) request.getSession().getAttribute("lg_user");
 if(null==user){
     out.print("<script language=javascript> parent.parent.parent.parent.location.href= '/index.jsp'; </script>");
     return ;
 }
String username=user.getLg_name(); 
request.setAttribute("name",DESPlusUtil.Get().encrypt(user.getName()));
request.setAttribute("lg_name",username);
 
 %>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma"  content="no-cache"/> 
<title>Header Layout</title><script language="javascript" src="/js/vcommon.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<script language="JavaScript" src="/js/prototype.js"></script>
<script language="JavaScript" src="/js/jquery1.4.2.js"></script>
<script language="JavaScript" src="/js/did_common.js"></script>
<script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script language="JavaScript" type="text/JavaScript">

var timer=null;
var timer1=null;
var username='<%=username%>';

$(function(){
   
});

function onLoad(){
	DwrClass.getTerminalNumBystr(requestCallback);//查看终端的在线和断开
}

function requestCallback(terminalnum){	
	var nums=terminalnum.split("_");	
	var oobj = document.getElementById("ocnt");	
	var dobj = document.getElementById("dcnt");
	var aobj = document.getElementById("acnt");
	var fobj = document.getElementById("fcnt");	
	oobj.innerHTML = "<span style='font:12px;cursor: pointer;' onclick='javascript:goOn();'>"+nums[0]+"</span>";	
	fobj.innerHTML = "<span style='font:12px;cursor: pointer;' onclick='javascript:goClose();'>"+nums[1]+"</span>"
	aobj.innerHTML = "<span style='font:12px;cursor: pointer;' onclick='javascript:${sessionScope.lg_user.lg_role==1?'goNotAudit()':''};'>"+nums[2]+"</span>";
	dobj.innerHTML = "<span style='font:12px;cursor: pointer;'  onclick='javascript:goDormancy();'>"+nums[3]+"</span>";		 
	//setTimeout("onLoad()",3436);
}

function islogined(){
    DwrClass.checkIsLogin("${sessionScope.logined}",jump);
}

var aaa=0;

function jump(logined){//踢掉之前登录的同一账户
	if(logined!=null){
		var islogin=logined.split("_");
	  	//if(islogin[0]=='1'&&aaa==0){
			//aaa++;
			//alert("用户【${lg_user.name }】已经在别处登录 或者 登录时间过长！");
		   // parent.parent.location.href="/logout.jsp";
		//}else{
	   	document.getElementById("onlinenum_id").innerHTML=islogin[1];
	   //}
	}
}
function logout(){
	if(confirm("提示信息：确认退出系统？")){
		parent.location.href="/rq/login?initdo=1";
	}
}
function a111(){
	onLoad();
}
function goOn(){//在线
	parent.content.location.href = "/admin/terminal/index.jsp?cmd=LIST&left_cmd=ACTIVE&title=ACTIVE&zu_id=no&t=" + new Date().getTime();
}

function goClose(){//断开
	parent.content.location.href = "/admin/terminal/index.jsp?cmd=LIST&left_cmd=INACTIVE&title=INACTIVE&zu_id=no&t=" + new Date().getTime();
}

function goNotAudit(){	//待审核
	//top.content.location.href = "/admin/terminal/index.jsp?left_cmd=NOTAUDIT&title=NOTAUDIT&cmd=NOTAUDIT";
	parent.content.location.href="/admin/terminal/index.jsp?cmd=TOPNOTAUDIT&t=" + new Date().getTime();
}

function goDormancy(){//休眠
	parent.content.location.href = "/admin/terminal/index.jsp?cmd=LIST&left_cmd=DORMANCY&title=DORMANCY&zu_id=no&t=" + new Date().getTime();
}
function getOnlineNum(){
	DwrClass.getOnlineNum(onlinenum);
}
function onlinenum(num){
	document.getElementById("onlinenum_id").innerHTML=num;
}

//timer=setInterval("DwrClass.checkIsLogin('${sessionScope.logined}',jump)",5341);

timer=setInterval("onLoad()", 3456);
timer1=setInterval("getOnlineNum()",2500); //检测在线人数


	function updatepassword(lg_name){
	
		showDiv1("密码修改",300,300,"/admin/competence/updatePassword.jsp?userids="+lg_name);
	}
	function show_online_user(){
		showDiv1("当前在线用户",400,300,"/admin/competence/show_online_user.jsp");
	}
	function showDiv1(title,fwidth,fheight,url){
			parent.parent.document.body.scrollTop = "0px";
			parent.parent.document.getElementById("div_iframe1").width=fwidth;
			parent.parent.document.getElementById("div_iframe1").height=fheight;
			parent.parent.document.getElementById("divframe1").style.left=(parent.parent.document.body.clientWidth - fwidth) / 2 + "px";
			parent.parent.document.getElementById("divframe1").style.top="100px";
			parent.parent.document.getElementById("div_iframe1").src=url;
			parent.parent.document.getElementById("titlename1").innerHTML=title;
			parent.parent.document.getElementById("divframe1").style.display='block';
			parent.parent.document.getElementById("massage1").style.display='block';
			parent.parent.document.getElementById("bgDiv").style.visibility='visible';
		}
		
		function opmeeting(){
		   //window.open("http://www.baidu.com");
		   //window.open("http://10.6.6.30:9080/login.do?op=0&username=${lg_name}&password=${meetingpassword}&name=${name}&t=" + new Date().getTime());
		    window.open("<%=basePath%>meeting/login.do?op=0&username=${lg_name}&password=${meetingpassword}&name=${name}&t=" + new Date().getTime());
		}
		
//function reload_href(){
//window.location.reload();
//}
//setInterval("reload_href()",3600000);
</script>

<body>
<form name="fm1">
<input type='hidden' name='cmd' value='DASH_BOARD'/>

<table width="100%" height="68px"  border="0" cellpadding="0" cellspacing="0" background="/images/topmenu/menu_bg.gif">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr >
        <td width="200px">
        	<table width="180px" border="0"  cellspacing="0" cellpadding="0" background="/images/topmenu/logo.gif">
	        <tr>
       			<td  height="68px">&nbsp;</td> 
       		</tr>    		
       		</table>
       	</td>
        <td  align="left">
          <table border="0" cellspacing="0" cellpadding="0">
          <tr>
           <!--  <td id="m0" onmouseout="MM_swapBgImgRestore()" onmouseover="MM_swapBgImage('m0','','/images/topmenu/homemenu_over.gif',1)" background="/images/topmenu/homemenu.gif" width="80" height="68" align="center" onClick='parent.content.location.href="/admin/home/index.jsp"' style="cursor:hand" >
			<table width="100%" height="100%" border="0"><tr><td valign="bottom" align="center">
			<span style="font-size:8pt;" >
			主页
			</span></td></tr><tr></tr></table></td> -->

			<td id="m1" style="cursor:hand;padding-top:36px" onmouseout="MM_swapBgImgRestore()" onmouseover="MM_swapBgImage('m1','','/images/topmenu/topmenu7_over.gif',1)" background="/images/topmenu/topmenu7.gif" width="80" height="68" align="center" onClick='parent.content.location.href="/admin/terminal/index.jsp?t=" + new Date().getTime()'>
			   <span style="font-size:8pt;" >终端管理</span>
			</td>
			
			 <td id="m2" style="cursor:hand;padding-top: 36px"  onmouseout="MM_swapBgImgRestore()" onmouseover="MM_swapBgImage('m2','','/images/topmenu/topmenu1_over.gif',1)" background="/images/topmenu/topmenu1.gif" width="80px" height="68px"  onClick='parent.content.location.href="/admin/media/index.jsp?t=" + new Date().getTime()' >
			      <span style="font-size:8pt;margin-left: 20px">
			      媒体库
			      </span>
			</td>
			
			<td id="m7"  style="cursor:hand;padding-top: 36px"  onmouseout="MM_swapBgImgRestore()" onmouseover="MM_swapBgImage('m7','','/images/topmenu/topmenu6_over.gif',1)" background="/images/topmenu/topmenu6.gif" width="80" height="68" align="center" onClick='parent.content.location.href="/admin/template/index.jsp?t=" + new Date().getTime()'>
			   <span style="font-size:8pt;" >模板管理</span>
			</td>
			
			<td id="m3"  style="cursor:hand;padding-top: 36px"  onmouseout="MM_swapBgImgRestore()" onmouseover="MM_swapBgImage('m3','','/images/topmenu/topmenu3_over.gif',1)" background="/images/topmenu/topmenu3.gif" width="80" height="68" align="center" onClick='parent.content.location.href="/admin/program/index.jsp?t=" + new Date().getTime()'>
	
			<span style="font-size:8pt;" >节目管理</span></td>
			
			<td id="m4"  style="cursor:hand;padding-top: 36px" onmouseout="MM_swapBgImgRestore()" onmouseover="MM_swapBgImage('m4','','/images/topmenu/topmenu2_over.gif',1)" background="/images/topmenu/topmenu2.gif" width="80" height="68" align="center"  onClick='parent.content.location.href="/admin/schedule/index.jsp?t=" + new Date().getTime()'>
			<span style="font-size:8pt;" >
			时间表
			</span></td>
			
			<c:if test="${sessionScope.lg_user.lg_role==1}">
			<td id="m5" style="cursor:hand;padding-top: 36px" onmouseout="MM_swapBgImgRestore()" onmouseover="MM_swapBgImage('m5','','/images/topmenu/topmenu5_over.gif',1)" background="/images/topmenu/topmenu5.gif" width="80" height="68" align="center"  onClick='parent.content.location.href="/admin/competence/index.jsp?t=" + new Date().getTime()' >
			<span style="font-size:8pt;" >
			权限管理
			</span></td>   
			
			<td id="m6"  style="cursor:hand;padding-top: 36px" onmouseout="MM_swapBgImgRestore()" onmouseover="MM_swapBgImage('m6','','/images/topmenu/topmenu4_over.gif',1)" background="/images/topmenu/topmenu4.gif" width="80" height="68" align="center"  onClick='parent.content.location.href="/admin/sysmanager/index.jsp?t=" + new Date().getTime()'>
			<span style="font-size:8pt;" >
			系统管理
			</span></td>            		
			</c:if>
			<!--<td id="m8"  style="cursor:hand;padding-top: 36px" onmouseout="MM_swapBgImgRestore()" onmouseover="MM_swapBgImage('m8','','/images/topmenu/topmenu8_over.gif',1)" background="/images/topmenu/topmenu8.gif" width="80" height="68" align="center"  onClick='opmeeting()'>
			<span style="font-size:8pt;" >
			&nbsp;&nbsp;会议预订
			</span></td> -->    	
          </tr>
          </table>
        </td>  
        <td width="*" >&nbsp;</td>
    <td width="302" rowspan="2" valign="top" align="right" class="Bold" height="10">
          	<table border='0' width="302" cellpadding="0" cellspacing="0" style="margin-right:10px">
          		<tr>
          		<td  width='225' id='alarm_cnt' width="225" height="19" align='center' background='/images/topmenu/alarm_blue_black.gif'>
          			<table width='225' height='19' border='0' cellpadding="0" cellspacing="0">
          				<tr>
          				<td width='13%'>&nbsp;</td>
          				<td width='30%' onclick="javascript:goOn();" style="cursor: pointer;"><span style='font:11px'>连接:</span></td>
          				<td id='ocnt' width='20%' align='left' ><span style='font:12px'>0</span></td>   
          				<td width='25%' onclick="javascript:goClose();"  style="cursor: pointer;"><span style='font:11px'>断开:</span></td>
          				<td id='fcnt' width='12%' align='left' ><span style='font:12px'>0</span></td>        
          				
          				</tr>
          			</table>
          		</td>
          		<td width="78"  height="19" align="center" onclick='logout();' background='/images/topmenu/admin_over.gif' style="cursor:hand" onMouseOut="MM_swapBgImgRestore()" onMouseOver="MM_swapBgImage('admin','','/images/topmenu/admin_over.gif',1)">
	          	<span style='font:11px'>注销</span>
	          </td>
          		</tr>
          		<tr>
          		<td id='alarm_cnt' width="225" height="19" align='center' background='/images/topmenu/alarm_yellow_red.gif'>
          			<table width='225' height='19' border='0' cellpadding="0" cellspacing="0">
          				<tr>
          				<td width='13%'>&nbsp;</td> 
          				<td width='30%' onclick="javascript:${sessionScope.lg_user.lg_role==1?'goNotAudit()':''};"  style="cursor: pointer;"><span style='font:11px'>待审核:</span></td>
          				<td id='acnt' width='20%' align='left'  ><span style='font:12px'>0</span></td>           				
          				<td width='25%' onclick="javascript:goDormancy();" style="cursor: pointer;"><span style='font:11px' >休眠:</span></td>
          				<td id='dcnt' width='12%' align='left'><span style='font:12px'>0</span></td>      				
          				</tr>
          			</table>
          		</td>
			    <td width="78"  height="19" >
	          
	            </td>         		
          		</tr>
          		<tr >
          		 <td colspan="2" height="25px" align="left" valign="bottom" >
          		 	<table border="0" width="100%" cellpadding="0" cellspacing="0">
          		 		<tr>
          		 			<td width="50%"><span style='font:11px'><strong>登录用户：【<a href="javascript:;" title="密码修改" onclick="updatepassword('${lg_user.lg_name}')">${lg_user.name }</a>】</strong></span></td>
          		 			<td align="right"><strong onclick="show_online_user();" style="cursor: pointer;" title="查看在线用户">在线用户数：<span id="onlinenum_id">0</span>人</strong></td>
          		 		</tr>
          		 	</table>
          		</td>
          		</tr>
          		
          	</table>          		
        </td>
	                          
      
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>
