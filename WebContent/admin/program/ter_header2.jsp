<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String title=request.getParameter("title");
	String left_menu=request.getParameter("left_menu");
	 String zu_id=request.getParameter("zu_id");
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
function onUpdateSoftware(){
var num = chkNum();
if(num<1){
alert("请选择终端！");
return;
}
var checkIPs= getCheckIPs();
parent.deviceContent.onUpdateSoftware(checkIPs);
}

function onCreateProgram(){
parent.listtop.location.href="/admin/program/programtop1.jsp";
parent.content.location.href="/admin/program/create_program1.jsp";
}
function onUpdateProgram(){
var num = chkNum();
if(num<1){
alert("请选择需要修改的节目！");
return;
}else if(num>1){
alert("只能选择一个节目修改！");
return;
}
var checkIPs= getCheckIPs().split("!");
parent.listtop.location.href="/admin/program/programtop1.jsp";
parent.content.location.href="/admin/program/update_program.jsp?templatefile="+checkIPs[1];
}
function onDeleteProgram(){
var num = chkNum();
if(num<1){
alert("请选择需要删除的项！");
return;
}
if(confirm("提示信息：确认删除选中项？")){
var batchs= getCheckIPs();
display_iframe.location.href="/admin/program/deleteProgramApp.jsp?batchs="+batchs+"&t=" + new Date().getTime();
//parent.listtop.location.href="/admin/program/deleteProgramApp.jsp?programe_file="+checkIPs+"&rqurl="+rqurl;
}
}
function onAuditProgram(status){
var num = chkNum();
if(num<1){
alert("请选择需要审核的项！");
return;
}
if(confirm("提示信息：确认审核选中项？")){
var batchs= getCheckIPs();
display_iframe.location.href="/admin/program/auditprogramDO.jsp?batchs="+batchs+"&program_app_status="+status+"&t=" + new Date().getTime();
}
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
<form name="headForm" id="headForm" method="post" onsubmit="return false;">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0" background="/images/device/btn_background.gif">
    <tr>
      <td>
		   <table width="345" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	          <c:if test="${lg_user.lg_role==2||lg_user.lg_role==1}">
		          	<c:if test="${left_menu=='AUDIT'||left_menu=='NOTAUDIT'}">
					<td id="btn1" onmouseout="MM_swapBgImgRestore()" 
					onmouseover="MM_swapBgImage('btn1','','/images/device/btn_vnc_over.gif',1)" 
					background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onAuditProgram(3);" style="cursor:hand">
					&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
					审核通过
					</span></td></c:if>
					<c:if test="${left_menu=='AUDIT'||left_menu=='AUDITSEND'}">
					<td id="btn3" onmouseout="MM_swapBgImgRestore()" 
					onmouseover="MM_swapBgImage('btn3','','/images/device/btn_vnc_over.gif',1)" 
					background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onAuditProgram(2);" style="cursor:hand">
					&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
					审核不通过
					</span></td>
					</c:if>
				</c:if>
				<td id="btn2" onmouseout="MM_swapBgImgRestore()" 
				onmouseover="MM_swapBgImage('btn2','','/images/device/btn_vnc_over.gif',1)" 
				background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onDeleteProgram();" style="cursor:hand">
				&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
				删&nbsp;&nbsp;&nbsp;&nbsp;除
				</span></td>
				<td>&nbsp;</td>
	          </tr>
	      </table>
	      
	  </td>
      
        </tr>
  </table>
   <table width="100%"  height="30" border="0" cellspacing="0" cellpadding="3" class="TitleBackgroundBg">
    <tr>
		<td class="TitleSmall">&nbsp;&nbsp;
		<%if("TITLE_ASC".equals(left_menu)){
			out.println("名称排序 - 升序");
		} else if("TITLE_DESC".equals(left_menu)){
			out.println("名称排序 - 降序");
		}else if("AUDIT".equals(left_menu)){
			out.println("节目管理 - 待审核节目");
		}else if("AUDITSEND".equals(left_menu)){
			out.println("节目管理 - 待发送节目");
		}else if("NOTAUDIT".equals(left_menu)){
			out.println("节目管理 - 审核未通过节目");
		}else if("ISSEND".equals(left_menu)){
			out.println("节目管理 - 已发送节目");
		}else if("SENDERROR".equals(left_menu)){
			out.println("节目管理 - 发送失败节目");
		}else{
			out.println("节目管理");
		} 
		
		
		%><iframe src="/loading.jsp" style="display: none;"  scrolling="no" id="display_iframe"
								name="display_iframe" frameborder="0" width="0" height="0"></iframe></td>
        </tr>
      </table>  
</form>	
</body> 

</html>
