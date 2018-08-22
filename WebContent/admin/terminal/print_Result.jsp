<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String cmd=request.getAttribute("cmd")==null?"":request.getAttribute("cmd").toString();
String ok_str="",error_str="";
String isoperatoring="终端还有未执行指令！";

if("ONDOWN".equals(cmd)){
	ok_str="发送休眠命令成功，终端已休眠！";
	error_str="终端休眠失败！";
}else if("STOPONDOWN".equals(cmd)){
	ok_str="发送停止休眠命令成功，终端已停止休眠！";
	error_str="终端停止休眠失败！";
}else if("RESTART".equals(cmd)){
	ok_str="发送重启命令成功，终端正在重启！";
	error_str="终端重启失败！";
}else if("SENDNOTICE".equals(cmd)){
	//String notice_content=request.getAttribute("notice_content")==null?"":request.getAttribute("notice_content").toString();
	ok_str="发送文字通知成功！"; 
	error_str="发送文字通知失败！"; 
}else if("SENDLEDNOTICE".equals(cmd)){
	//String notice_content=request.getAttribute("notice_content")==null?"":request.getAttribute("notice_content").toString();
	ok_str="发送LED文字通知成功！"; 
	error_str="发送LED文字通知失败！"; 
}
else if("CLEAR".equals(cmd)){
	ok_str="发送初始化成功！";
	error_str="发送初始化失败！";
}else if("SYNCHRONIZATION".equals(cmd)){
	ok_str="发送时间同步成功！";
	error_str="发送时间同步失败！";
}else if("UPDATE_DOWNLOAD_START_END".equals(cmd)){
	ok_str="发送音量时间设置成功！";
	error_str="发送音量时间设置失败！";
}else if("UPDATESOFTWARE".equals(cmd)){
	ok_str="发送软件升级成功！";
	error_str="发送软件升级失败！";
}else if("ONCLOSE".equals(cmd)){
	ok_str="发送关机命令成功！";
	error_str="发送关机命令失败！";
}else if("DELETEDEMANDPROGRAM".equals(cmd)){
	ok_str="删除点播节目成功！";
	error_str="删除点播节目失败！";
}else if("STARTLED".equals(cmd)){
	ok_str="开启终端LED屏成功！";
	error_str="开启终端LED屏失败！";
}else if("CLOSELED".equals(cmd)){
	ok_str="关闭终端LED屏成功！";
	error_str="关闭终端LED屏失败！";
}
 %>

<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		    <script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<script language="javascript" src="/js/vcommon.js"></script>
    <script type="text/javascript">
    var timer=null;
    var time_num=0;
   
    var cntips='${resips}';
    var cnt_array=cntips.split("!");
    var cnt_array_num=cnt_array.length;
    var timeout_num=50;
    
    function cnt_result(resultmaps){
		time_num++;
		 var allok_num=0;
		for(i=0;i<cnt_array_num;i++){
			if(document.getElementById(cnt_array[i])){
				if(document.getElementById(cnt_array[i]).className=="deff_ok"){
				    allok_num++;
				 }
			}
		}
		//	alert(allok_num+"====="+cnt_array_num+"---------"+time_num);
		if(allok_num==(cnt_array_num-1)){
			clearInterval(timer);
		}
		if(cnt_array_num>10){
			timeout_num=cnt_array_num*3;
		}
		if(time_num<timeout_num){
	    	 for(var ipresult in resultmaps){
	    	 	if(document.getElementById(ipresult)){
		    	 	if(resultmaps[ipresult]=='OK'){
			    		document.getElementById(ipresult).innerHTML="<span  style='color: green'><%=ok_str%></span>";
			    		document.getElementById(ipresult).className="deff_ok";
		    		}else if(resultmaps[ipresult]=='ERROR'){
			    		document.getElementById(ipresult).innerHTML="<span  style='color: red'><%=error_str%></span>";
			    		document.getElementById(ipresult).className="deff_ok";
		    		}else if(resultmaps[ipresult]=='NOOP'){
			    		document.getElementById(ipresult).innerHTML="<span  style='color: red'><%=isoperatoring%></span>";
			    		document.getElementById(ipresult).className="deff_ok";
		    		}
	    		}
	    	}
    	}else{
    		for(i=0;i<cnt_array_num;i++){
    			if(document.getElementById(cnt_array[i])){
    				if(document.getElementById(cnt_array[i]).className=="deff"){
		    			document.getElementById(cnt_array[i]).innerHTML="<span  style='color: red'>发送失败（失败原因：网络超时！）</span>";
		    			DwrClass.addlogs("向终端"+cnt_array[i]+"<%=error_str%>（失败原因：网络超时!）")
		    			document.getElementById(cnt_array[i]).className="deff_ok";
	    			}
    			}
    		}
    	}
    }
    timer= setInterval("DwrClass.get_cmd_result('${lg_user.name}','${resips}','${opp}','<%=ok_str%>','<%=error_str%>',cnt_result)", 3000);
    
    </script>
	</head>

	<body>
	<table align="center" width="100%"  border="0">
			<tr>
				<td align="center" width="50%" colspan="2"><strong>终端名称</strong></td>
				<td align="center" width="50%" ><strong>状态</strong></td>
			</tr>
			<tr>
				<td colspan="3" align="center" width="100%">
				<div style="overflow: auto;height: 300px;position:absolute;width: 100%">
					<table align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
						<c:forEach var="ter" items="${t_list}">
							<tr>
								<td  width="5%" height="20px" align="right"><img src="/images/dtreeimg/base.gif"/></td>
								<td  width="45%">【${ter.cnt_name}】</td>
								<td   width="50%" id="${ter.cnt_ip}_${ter.cnt_mac}" class="deff" style="style='word-break:break-all'"><img src="/images/mid_giallo.gif"/>&nbsp;&nbsp;<span  style="color: maroon">正在发送,请稍等...</span></td>
							</tr>
							<tr><td class="Line_01" colspan="3"></td></tr>
						</c:forEach>
					</table>
				</div>
	<div style="height:300px"></div>
	<center><div style="height: 50px;vertical-align: middle;"  ><br/><input type="button" class="button1"  onclick="parent.closedivframe(1);" value=" 关闭 ">
</div></center>
				
				</td>
			</tr>
	</table>

	</body>
</html>
