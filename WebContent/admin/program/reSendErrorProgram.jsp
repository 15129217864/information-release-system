<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ProgramAppDAO"/>
<jsp:directive.page import="com.xct.cms.domin.ProgramApp"/>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
	String programid=request.getParameter("program_id");
	Users user = (Users) request.getSession().getAttribute("lg_user");
	ProgramAppDAO programappdao= new ProgramAppDAO();
	ProgramApp programapp=programappdao.getProgramAppByStr("where program_id='"+programid+"'");
	
	TerminalDAO  terminaldao= new TerminalDAO();
	List<Terminal> allTerminalList=terminaldao.getALLTerminalDAO("  order by cnt_ip");
	List <Terminal> appiplist=new ArrayList<Terminal>();

	String terminalips=programapp.getProgram_play_terminal();
	String[] program_sendok_terminals=programapp.getProgram_sendok_terminal().split("#");
	for(int i=0;i<program_sendok_terminals.length;i++){
	if(!"".equals(program_sendok_terminals[i])){
	terminalips=terminalips.replace("#"+program_sendok_terminals[i],"");
	}
	}
	String[] allips=terminalips.split("#");
	if(programapp!=null){
        for(int j=0;j<allTerminalList.size();j++){
			Terminal terminal=allTerminalList.get(j);
			for(int i=1,n=allips.length;i<n;i++){
				if(terminal.getCnt_ip().equals(allips[i])){
					appiplist.add(terminal);
				}
        	}
		}
		request.setAttribute("clentips",terminalips);
		request.setAttribute("appiplist",appiplist);
		request.setAttribute("programapp",programapp);
		request.setAttribute("programid",programapp.getId());
   }
%>
<html>
	<head>
		<title>发送节目</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<script type="text/javascript">
			   var timer=null;
			   var sendErrorProgramIps="${programid}@";
		       function sendclient(){//发送节目
			       DwrClass.checkErrorProgramIsExist('${programapp.program_id}',checkProgram); 
		       }
		       function checkProgram(clientprojectlist){
		       
			       if(clientprojectlist==null || clientprojectlist==""){
			       
			       		var clentips='${clentips}';
				        var ips=clentips.split("#");
				        for(var i=1;i<ips.length;i++){
				         	document.getElementById(ips[i]).innerHTML="<span style=color:'maroon'><img src='/images/loading.gif'>正在发送,请稍等...</span>";	
				        }
				        progressShow();
				        DwrClass.sendProgram('${programid}@${clentips}','<%=user.getLg_name()%>',sendProgramStatus); 
				        document.getElementById("sendclient").disabled="disabled";
			  
			       }else{//显示终端有此类型节目或者同一个节目不能发送两次
					      var programlist="<form action='' name='ifrm_Form'><table width='100%'  border='0'  cellpadding='0' cellspacing='0'>";
					      for(var i=0;i<clientprojectlist.length;i++){
					      	 	programlist+="<tr style='background-color:#F5F9FD;' onmouseover=this.style.backgroundColor='#DAE8F5' onmouseout=this.style.backgroundColor='#F5F9FD'><td width='1%'><input type='checkbox' name='checkbox' value='"+clientprojectlist[i].jmid+"'/></td>"+
					      	 	"<td  width='16%' align='center' >"+clientprojectlist[i].playclient+"</td>"+
					      	 	"<td  width='15%' align='center'>"+clientprojectlist[i].clientip+"</td>"+
					      	 	"<td  width='16%' align='center'>"+clientprojectlist[i].name+"</td>"+
					      	 	"<td  width='16%' align='center'>"+clientprojectlist[i].setdate+"</td>"+
					      	 	"<td  width='16%' align='center'>"+clientprojectlist[i].enddate+"</td>"+
					      	 	"<td  width='8%' align='center'>"+clientprojectlist[i].playtypeZh+"</td>"+
					      	 	"<td  width='12%' align='center'>"+clientprojectlist[i].playsecond+"分钟</td>"+
					      	 	"</tr><tr><td class='Line_01' colspan='8'></td></tr>";
					      }
					    programlist+="</table></form>"
					    document.getElementById("showprogramid").innerHTML=programlist;
					    document.getElementById("divframe").style.left=(document.body.clientWidth - 900) / 2 + "px";
						document.getElementById("divframe").style.top="0px";
						document.getElementById("titlename").innerHTML="终端节目单已存在此播放类型的节目或者播放时间冲突，请先删除终端节目！";
					    document.getElementById("divframe").style.visibility='visible';
					    document.getElementById("massage").style.visibility='visible';
				   }
		       }
		       var senderrorarray=new Array();
		        function sendProgramStatus(resultmaps){
		            progressBarHidden();
			        for(var ipresult in resultmaps){//返回map值，遍历
			              if(resultmaps[ipresult]==null){
			              	 document.getElementById(ipresult).innerHTML="<span style=color:'red'><img src='/images/error.gif'  height='16px'>发送失败，请检查网络是否连接！</span>";
			             	 sendErrorProgramIps+="#"+ipresult;
			             	 document.getElementById("resendid").disabled="";
			             	  senderrorarray.push(ipresult);
			              }else if(resultmaps[ipresult]=='DOWNLOAD'){
			              	document.getElementById(ipresult).innerHTML="<span style='color:red'><img src='/images/error.gif'  height='16px'>发送失败，客户端正在下载节目，请稍后再试！</span>";	
			             	 senderrorarray.push(ipresult);
			              }else{
			              	document.getElementById(ipresult).innerHTML="<span style=color:'green'><img src='/images/loading.gif'>发送成功，客户端正在下载...请稍候！</span>";	
			              	
			              	document.getElementById("tishiid").style.display="block";
			              
			              }
			        }
			        //////////////////*******************激活刷新下载状态。。。。。。。。。
			        timer=setInterval("DwrClass.clientRequest('${programapp.program_jmurl}@${clentips}',clientRequesttatus)", 2000);
				 }
				 
				
				function clientRequesttatus(resultmaps){
				
					 for(var ipresult in resultmaps){ 
					       if(resultmaps[ipresult]!=""){
						 		document.getElementById(ipresult).innerHTML=resultmaps[ipresult];
						 		if(resultmaps[ipresult].indexOf("green")==-1){
								    document.getElementById("resendid").disabled="";
								    senderrorarray.push(ipresult);
								    //去除数组中重复的数据
								    senderrorarray=arrayrepeat(senderrorarray);
						 		}
						 	}
					 }
				 }
		
		       function cannel(){//取消发送
		          clearInterval(timer);
		          i=0;
		          timer= setInterval("getNewDownloadStatus()", refreshTime);
		       }
		       function onllload(){
		      	 	parent.listtop.location.href="/admin/program/programtop2.jsp";
		       }
		       
		       function goback(){
			       window.location.href="/admin/program/sendErrorList.jsp";
			       parent.listtop.location.href="/admin/program/program_list_top4.jsp";
		       }
		       
		       function progressBarOpen(){
		  			var cWidth = document.body.clientWidth;
		  			var cHeight= document.body.clientHeight;
					var divNode = document.createElement( 'div' );	
					divNode.setAttribute("id", "systemWorking");
					var topPx=(cHeight)*0.4;
					var defaultLeft=(cWidth)*0.4;
					divNode.style.cssText='position:absolute;margin:0;top:'+topPx+'px;left:'+defaultLeft+';width:215;height:59;background-image:url(/images/wait_background.gif);z-index:9999;text-align:center;padding-top:20'; 
					divNode.innerHTML= "<img src='/images/mid_giallo.gif' align=absmiddle><font style='font-size:12px;'>&nbsp;正在发送节目，请稍后...</font>";
					divNode.style.display='none';	
					document.getElementsByTagName("body")[0].appendChild(divNode);   		
		       }
		  
			   function progressShow() {
				  document.all.systemWorking.style.display = "block";
			   }   
			
			   function progressBarHidden() {
				  document.all.systemWorking.style.display = "none";
			   }
			
			  function arrayrepeat(arr){//去除数组重复元素
			     return arr.reverse().join(",").match( /([^,]+)(?!.*\1)/ig).reverse();
			  }
			
			   function reSendProgram(){ //重新发送下载失败的节目
					clearInterval(timer);
					
					var arraytemp=arrayrepeat(senderrorarray);
					for(var i=0,n=arraytemp.length;i<n;i++){
					  sendErrorProgramIps+="#"+arraytemp[i];
					}
					arraytemp.length=0;
					senderrorarray.length=0;//数组清空
					
					var ips=(sendErrorProgramIps.split("@")[1]).split("#");
					for(var i=1;i<ips.length;i++){
					   document.getElementById(ips[i]).innerHTML="<span style=color:'maroon'><img src='/images/loading.gif'>正在重新发送,请稍等...</span>";	
					}
					progressShow();
					DwrClass.sendProgram(sendErrorProgramIps,'<%=user.getLg_name()%>',resendProgramStatus); 
					sendErrorProgramIps="${programid}@";
		       }
		       
		        function resendProgramStatus(resultmaps){
		           progressBarHidden();
			       for(var ipresult in resultmaps){ 
			              if(resultmaps[ipresult]==null){
			              	  document.getElementById(ipresult).innerHTML="<span style=color:'red'><img src='/images/error.gif'  height='25px'>重新发送失败,请检查网络是否连接！</span>";
			              	  document.getElementById("resendid").disabled="";
			              	   senderrorarray.push(ipresult);
			              }else if(resultmaps[ipresult]=='DOWNLOAD'){
			              	document.getElementById(ipresult).innerHTML="<span style='color:red'><img src='/images/error.gif'  height='16px'>重新发送失败，客户端正在下载节目，请稍后再试！</span>";	
			             	 senderrorarray.push(ipresult);
			              }else{
			              	  document.getElementById(ipresult).innerHTML="<span style=color:'green'><img src='/images/loading.gif'>发送成功,客户端正在下载...</span>";	
			              }
			       }
			       //////////////////*******************激活刷新下载状态。。。。。。。。。
			       setInterval("DwrClass.clientRequest('${programapp.program_jmurl}@${clentips}',clientRequesttatus)", 2000);
			    } 
				function closedivframe(){
					document.getElementById('divframe').style.visibility="hidden";
					document.getElementById("massage").style.visibility='hidden';
				}
				function all_chk(sel_all){
					var ckform = document.ifrm_Form;
				  	var cbox   = ckform.checkbox;
				  	if(cbox){
					    if(cbox.length){
					    	for(i=0;i<cbox.length;i++){
					    		ckform.checkbox[i].checked=sel_all;
					    	}
					    }else{
					    	ckform.checkbox.checked = sel_all;
					    }   
					}
			   }
			   function chk_checkbox(){
					var forms = document.ifrm_Form;
					var checkids="";
					
					for(i=0;i<forms.checkbox.length;i++){
						if(forms.checkbox[i].checked == true) {
							id = forms.checkbox[i].value;
							checkids+="#"+id;
						}
					}
					if(forms.checkbox.checked == true){
					   checkids+="#"+forms.checkbox.value;
					}
					return checkids;
			   }
			   function deleteProgramHistory(){
				    var checkids=chk_checkbox();
					if(checkids==""){
						alert("选择要删除的项！");
						return;
				    }
					if(confirm("确认删除选中的节目单？")){
						DwrClass.deleteProgramHistory(checkids,deleteProgramHistoryOk); 
					}
			  }
			  function deleteProgramHistoryOk(status){
					if("ok"==status){
					   alert("删除节目单成功！");
				     }
				    closedivframe();
				    window.location.href= '#top';
			  }
</script>
<style type="text/css">
#divframe {
	position: absolute;
	z-index: 999;
	filter: dropshadow(color = #666666, offx = 3, offy = 3, positive = 2);
	visibility: hidden
}

#mask {
	position: absolute;
	top: 0;
	left: 0;
	width: expression(body . scrollWidth);
	height: expression(body . scrollHeight);
	background: #000000;
	filter: ALPHA(opacity = 60);
	z-index: 1;
	visibility: hidden
}

#massage {
	border: #6699cc solid;
	border-width: 1 1 1 1;
	background: #fff;
	color: #036;
	font-size: 12px;
	line-height: 150%;
	visibility: hidden
}

.header {
	background: url(/images/device/btn_background.gif);
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	padding: 3 5 0 5;
	color: #fff
}
</style>     
     
	</head>
	<body  onload="onllload();progressBarOpen();"><a name="top"></a><br/>
		<center>
			<fieldset style="width:90%; margin-top: 10px;border: #6699cc 1 solid;">
				<table border="0" width="100%" align="center">
					<tr height="30px">
						<td style="font-size: 12px; ">
							<span style="font-weight: bold; color: blue">节目名称：</span> ${programapp.program_name }
						</td>
						<td style="font-size: 12px; ">
							<span style="font-weight: bold;color: blue">播放时长：</span>  ${programapp.program_playlong }&nbsp;分钟
						</td>
						<td style="font-size: 12px;">
								<span style="font-weight: bold;color: blue">节目播放类型：</span>  ${programapp.program_play_typeZh }
						</td>
					</tr>
					<tr>
						<td colspan="4" style="font-size: 12px;">
							<span style="font-weight: bold;color: blue">播放时间：</span> 
							<c:choose>
							  <c:when test="${programapp.program_play_type==0 }">
							     ${programapp.program_playdate }  —— ${programapp.program_enddate }&nbsp;&nbsp; 区间，&nbsp;&nbsp;&nbsp;&nbsp;每天循环播放&nbsp;&nbsp;  ${programapp.program_playlong }&nbsp;&nbsp;分钟
							  </c:when>
							  <c:when test="${programapp.program_play_type==3 }">
							      &nbsp;&nbsp;&nbsp;&nbsp;每天循环播放&nbsp;&nbsp;  ${programapp.program_playlong }&nbsp;&nbsp;分钟
							  </c:when>
							  <c:when test="${programapp.program_play_type==1 }">
							     ${programapp.program_playdate } ${programapp.program_playtime } —— ${programapp.program_enddate } ${programapp.program_endtime }
							  </c:when>
							  <c:otherwise>
							     ${programapp.program_playdate } —— ${programapp.program_enddate }&nbsp;&nbsp; 区间，&nbsp;&nbsp;&nbsp;&nbsp;每天在&nbsp;&nbsp; ${programapp.program_playtime } ——${programapp.program_endtime } 定时播放
							  </c:otherwise>
							</c:choose>
							
						</td>
					</tr>
					<tr>
						<td colspan="3" >
							<table border="0" width="100%" cellpadding="0" cellspacing="0">
									<tr align="center"  class="TitleBackground">
										<td height="30px" width="6%" style="font-weight: bold">
											序号
										</td>
										<td width="20%" style="font-weight: bold">
											终端名称
										</td>
										<td style="font-weight: bold" width="20%" >
											终端IP
										</td>
										<td style="font-weight: bold" width="20%" >
											连接状态
										</td>
										<td style="font-weight: bold" width="34%" >
											下载状态
										</td>
									</tr>
							</table>
							<div style="height:300px;overflow:auto;">
								<table border="0" width="100%" cellpadding="0" cellspacing="0">
									<c:if test="${!empty appiplist}">
										<c:set var="i" value="0"></c:set>
										<c:forEach items="${appiplist}" var="appip">
											<c:set var="i" value="${i+1}"></c:set>
											<tr align="center" onmouseover="this.className='TableTdBg04'" onmouseout="this.className=''">
												<td  height="20px"  width="6%" >
													${i }
												</td>
												<td   width="20%" >
													${appip.cnt_name }
												</td>
												<td  width="20%" >
													${appip.cnt_ip }
												</td>
												<td  width="20%" >
													${appip.cnt_islink_zh }
												</td>
												<td id="${appip.cnt_ip }"  width="34%" >
													等待发送
												</td>
											</tr>
											
											          <tr>
											            <td class="Line_01" colspan="5"></td>
											          </tr>
											
										</c:forEach>
									</c:if>
								</table>
							</div>
							<div align="center" style="height: 40px" id="tishidiv"><br/>
							<input type="button" onclick="goback();" class="button1"
									value="  返 回  " />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" onclick="sendclient();" class="button1"
									id="sendclient" value="发送到终端"  />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" onclick="reSendProgram(); this.disabled='disabled'" class="button1"
									value=" 重新发送 " disabled id="resendid"/>
									<span style="color: green;display: none;" id="tishiid" >&nbsp;&nbsp;友情提示：客户端正在下载节目，您离开本页面客户端会继续下载节目！</span>
							</div>
						</td>
					</tr>
				</table>
			</fieldset>
		</center>
		
		
<div id="divframe">
			<div id="massage">
				<table cellpadding="0" cellspacing="0">
					<tr height="30px;" class=header>
						<td align="left" style="font-weight: bold">
							<span id="titlename"></span>
						</td>
						<td height="5px" align="right">
							<a href="javaScript:void(0);" style="color: #000000"
								onclick="closedivframe();">关闭</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
							<table width="800px;" border="0" cellpadding="0" cellspacing="0">
								<tr   class="TitleBackground">
							<td width="1%"><input type="checkbox" onclick="all_chk(this.checked);"/></td>
							<td style="font-weight: bold" width="16%" align="center" height="25px">
								终端名称
							</td>
							<td style="font-weight: bold" width="15%" align="center">
								终端&nbsp;&nbsp;IP
							</td>
							<td  style="font-weight: bold" width="16%" align="center">
								节目名称
							</td>
							<td style="font-weight: bold" width="16%" align="center">
								开始时间
							</td>
							<td  style="font-weight: bold" width="16%" align="center">
								结束时间
							</td>
							<td style="font-weight: bold" width="8%" align="center">
								播放类型
							</td>
							<td style="font-weight: bold" width="12%" align="center">
								播放时长
							</td>
						</tr>
						<tr style='background-color:#F5F9FD;'>
							<td colspan="8" id="showprogramid" width="100%">
								
							</td>
						</tr>
						<tr style='background-color:#F5F9FD;'>
							<td colspan="8" height="40px">
								&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button1" onclick="deleteProgramHistory()" value=" 删 除 "/>
							</td>
						</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>		
<script>
document.getElementById('loadid').style.display='none';
</script>		
		
	</body>
</html>
