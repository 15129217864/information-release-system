<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<% String title=request.getParameter("title");
	String left_menu=request.getParameter("left_menu");
	 String zu_id=request.getParameter("zu_id");
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
		function onDeleteUser(){
			var num = chkNum();
			if(num<1){
				alert("请选择需要删除的用户！");
				return;
			}
			if(confirm("提示信息：确认删除选中用户？")){
				var userids= getCheckIPs();
				document.getElementById("header_iframe").src="/admin/competence/deleteUser.jsp?userids="+userids;
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
		   <table width="228" border="0" cellspacing="0" cellpadding="0">
	          <tr>
		        <td id="btn" onmouseout="MM_swapBgImgRestore()" 
				onmouseover="MM_swapBgImage('btn','','/images/device/btn_vnc_over.gif',1)" 
				background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onAddUser();" style="cursor:hand">
				&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
				新建用户
				</span></td>
				<td id="btn2" onmouseout="MM_swapBgImgRestore()" 
				onmouseover="MM_swapBgImage('btn2','','/images/device/btn_vnc_over.gif',1)" 
				background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onDeleteUser();" style="cursor:hand">
				&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
				删除用户
				</span></td>
				
	          </tr>
	      </table>
	      
	  </td>
     
        </tr>
  </table>
   <table width="100%"  height="32" border="0" cellspacing="0" cellpadding="3" class="TitleBackgroundBg">
    <tr>
		<td class="TitleSmall">&nbsp;&nbsp;
		<%if("TITLEASC".equals(left_menu)){
			out.println("权限管理 - 名称排序");
		}else{
			out.println("权限管理 - 所有用户");
		} 
		%></td>
        </tr>
      </table>  

</form>
<div style="display: none;" ><iframe src="" scrolling="no" id="header_iframe" name="header_iframe" frameborder="0"></iframe></div>
</body> 

</html>
