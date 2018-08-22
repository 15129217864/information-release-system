<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:directive.page import="com.xct.cms.xy.dao.ConnectionFactory"/>
<jsp:directive.page import="com.xct.cms.utils.GetGroupChange"/>
<jsp:directive.page import="com.xct.cms.domin.BankLed"/>
<%      BankLed bankled=new BankLed();
		Map<String, BankLed> map=bankled.getALLLedMap();
		request.setAttribute("ledlist",bankled.getLedclientList());
%>
<html>
	<head>
		<title>My JSP 'restart.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
						<link rel="STYLESHEET" type="text/css"
			href="/admin/checkboxtree/dhtmlxtree.css">
		<link rel="stylesheet" href="/admin/checkboxtree/style.css"
			type="text/css" media="screen" />
		<script language="JavaScript"
			src="/admin/checkboxtree/dhtmlxcommon.js"></script>
		<script language="JavaScript" src="/admin/checkboxtree/dhtmlxtree.js"></script>
		<script type="text/javascript">
			function isIP(strIP) {
				var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
			    var reg = strIP.match(exp);
			    if(reg==null){  
			      return false;
			    }else{
			        return true;
			    }
		   }
	
function startLED(){
	var checkips=tree.getAllChecked();
	if(checkips==""){
		alert("提示信息：请选择终端！");
		return ;
}
var checkipArray=checkips.split(",");
var reips="";
for(i=0;i<checkipArray.length;i++){
if(isIP(checkipArray[i])){
	reips+="!"+checkipArray[i].replace(",","!");
}
}
if(reips=="!" || reips==""){
		alert("提示信息：请选择终端！");
		return ;
}
restartForm.checkips.value=reips;
restartForm.action="/rq/requestClientOperating?cmd=STARTLED&opp=lvled01";
restartForm.submit();
document.getElementById("xm_button").disabled="disabled";
}
//////关闭LED
function closeLED(){
	var checkips=tree.getAllChecked();
if(checkips==""){
		alert("提示信息：请选择终端！");
		return ;
}
var checkipArray=checkips.split(",");
var reips="";
for(i=0;i<checkipArray.length;i++){
if(isIP(checkipArray[i])){
reips+="!"+checkipArray[i].replace(",","!");
}
}
if(reips=="!" || reips==""){
		alert("提示信息：请选择终端！");
		return ;
}
restartForm.checkips.value=reips;
restartForm.action="/rq/requestClientOperating?cmd=CLOSELED&opp=lvled02";
restartForm.submit();
document.getElementById("xm_button").disabled="disabled";
}

 function showDiv(title,url,fwidth,fheight){
			document.body.scrollTop = "0px";
			document.getElementById("div_iframe").width=fwidth;
			if(fheight!=""){
				document.getElementById("div_iframe").height=fheight;
			}
			alert((parent.parent.parent.parent.document.body.clientWidth - fwidth) / 2);
			alert((parent.parent.parent.parent.document.body.clientHeight - fheight) / 3);
			document.getElementById("divframe").style.left=(parent.parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";
			document.getElementById("divframe").style.top=(parent.parent.parent.parent.document.body.clientHeight - fheight) / 3+"px";
			document.getElementById("div_iframe").src=url;
			document.getElementById("titlename").innerHTML=title;
			document.getElementById("divframe").style.display='block';
			document.getElementById("massage").style.display='block';
			document.getElementById("bgDiv").style.visibility='visible';
}

function closedivframe(){
	cntip="";
	document.getElementById("div_iframe").src="/loading.jsp";
	document.getElementById('divframe').style.visibility="hidden";	
}


function addled(){
  showDiv("添加led属性","/admin/terminal/bank_led_add.jsp",300,500);

}


function SetWinHeight(obj){ 

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
	</script>
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
	#divframe{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2);   display: none }
	#massage{border:#6699cc solid; border-width:1 1 1 1; background:#fff; color:#036; font-size:12px; line-height:150%;  display: none }
	.header{background:url(/images/device/btn_background.gif);  font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff;cursor: move;}
</style>
  </head>
  
  <body>
  <div><input type="button" name="add" value="添加LED" onclick="addled()"/></div>
  <div style="overflow: auto ">
		   <table align="center" border="0" width="100%">
			   <tr style="background-color:graytext;">
				   	<td>LED名称</td>
				   	<td>IP地址</td>
				   	<td>宽度</td>
				   	<td>高度</td>
				   	<td>操作</td>
			   </tr>
			   <c:choose>
			     <c:when test="${not empty ledlist}">
			        <c:forEach items="${ledlist}" var="led">
					     <tr>
					  		<td align="left" >${led.name }</td>
					  		<td align="left" >${led.ip }</td>
					  		<td align="left" >${led.width }</td>
					  		<td align="left" >${led.height }</td>
					  		<td align="left" >
						  		<input type="button" name="update" value="修改" onclick=""/>
						  		<input type="button" name="send" value="发送" onclick=""/>
						  		<input type="hidden" name="id" value="${led.id }"/> 
					  		</td>
						</tr>
					</c:forEach>
			     </c:when>
			     <c:otherwise>
				     <tr>
				  		<td colspan="5" align="left" ><span style="color: red;">暂无数据!</span></td>
					 </tr>
			     </c:otherwise>
			   </c:choose>
		     </table>

   </div>
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
		
	</body>
</html>
