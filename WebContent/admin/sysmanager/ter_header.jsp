<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String left_menu=request.getParameter("left_menu");
	String logtype=request.getParameter("logtype")==null?"1":request.getParameter("logtype");
	request.setAttribute("left_menu",left_menu);
%>
<html>
  <head>
    
    <title>My JSP 'ter_header.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script language="JavaScript" src="/js/prototype.js"></script>				
		<script language="JavaScript" src="/js/did_common.js"></script>
		<script language="JavaScript" src="/js/common.js"></script>
<script type="text/javascript">
function goList(val,dsn){
			var dval;
			if(dsn!=null) dval = dsn;
			var btn1= document.getElementById("btn_monitoring");
			var btn2= document.getElementById("btn_list");			
			if(val=="MONITORING"){
				btn1.background="/images/device/tab_01_over.gif";
				btn2.background="/images/device/tab_02.gif";
			}else if(val=="LIST"){
				btn1.background="/images/device/tab_01.gif";
				btn2.background="/images/device/tab_02_over.gif";
			}		
			var form = document.headForm;
			form.action = "";
			form.target = "deviceContent"; 
			form.submit();
}
function chkNum(){
			if(parent.content.document.ifrm_Form && parent.content.document.ifrm_Form.checkbox!=null){
				var forms = parent.content.ifrm_Form;
				var num=0;
			
				for(i=0;i<forms.checkbox.length;i++){
					if(forms.checkbox[i].checked == true) num++; 
				}	
				if(forms.checkbox.checked == true)num++;
				return num;
			} else {
				return 0;
			}
		}

function onAddUser(){
parent.content.onAddUser();
}
function onUpdateUser(){
var num = chkNum();
if(num<1){
alert("请选择需要修改的用户！");
return;
}else if(num>1){
alert("只能选择一个用户修改！");
return;
}
var userids= getCheckIPs().split("!");
parent.content.onUpdateUser(userids[1]);
}
function onExportLogs(){
var num = chkNum();
if(num<1){
alert("请选择要导出日志的日期！");
return;
}
var userids= getCheckIPs();
window.location.href="/admin/sysmanager/exportLogs.jsp?logdates="+userids+"&logtype=<%=logtype%>";
}

function onDeleteLogs(){
var num = chkNum();
if(num<1){
alert("请选择要删除日志的日期！");
return;
}
var userids= getCheckIPs();
document.getElementById("header_iframe").src="/admin/sysmanager/deleteLogs.jsp?left_menu=<%=left_menu%>&logdates="+userids+"&logtype=<%=logtype%>";
}

function getCheckIPs(){
var forms = parent.content.ifrm_Form;
var checkIPs="";

for(i=0;i<forms.checkbox.length;i++){
	if(forms.checkbox[i].checked == true) {
		ip = forms.checkbox[i].value;
		checkIPs+="!"+ip;
	}
}
if(forms.checkbox.checked == true){
checkIPs+="!"+forms.checkbox.value;
}
return checkIPs;
}
</script>
  </head>
  
 <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">	
  <table width="100%"  border="0" cellpadding="0" cellspacing="0" background="/images/device/btn_background.gif">
    <tr>
      <td height="30">
		   <table  border="0" cellspacing="0" cellpadding="0">
		   
	          <tr>
	          <c:if test="${left_menu!='SYSKEY'}">
		        <td id="btn" onmouseout="MM_swapBgImgRestore()" 
				onmouseover="MM_swapBgImage('btn','','/images/device/btn_vnc_over.gif',1)" 
				background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onExportLogs();" style="cursor:hand">
				&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
				导出XLS
				</span></td>
				 <td id="btn1" onmouseout="MM_swapBgImgRestore()" 
				onmouseover="MM_swapBgImage('btn1','','/images/device/btn_vnc_over.gif',1)" 
				background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onDeleteLogs();" style="cursor:hand">
				&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
				删除日志
				</span></td>
				</c:if>
				<td>&nbsp;</td>
	          </tr>
	      </table>
	      
	  </td>
   
        </tr>
  </table>
   <table width="100%"  height="32" border="0" cellspacing="0" cellpadding="3" class="TitleBackgroundBg">
    <tr>
		<td class="TitleSmall">&nbsp;&nbsp;
		<%if("SYSLOGS".equals(left_menu)){
			out.println("系统管理 - 系统日志");
		}else if("SYSKEY".equals(left_menu)) {
			out.println("系统管理 - 输入授权密钥");
		}else {
			out.println("系统管理 - 操作日志");
		}
		%></td>
        </tr>
      </table>  
<div style="display: none" ><iframe src="/loading.jsp" scrolling="no" id="header_iframe" name="header_iframe" frameborder="0"></iframe></div>
</body> 

</html>
