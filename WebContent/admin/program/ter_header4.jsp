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
alert("��ѡ���նˣ�");
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
alert("��ѡ����Ҫ�޸ĵĽ�Ŀ��");
return;
}else if(num>1){
alert("ֻ��ѡ��һ����Ŀ�޸ģ�");
return;
}
var checkIPs= getCheckIPs().split("!");
parent.listtop.location.href="/admin/program/programtop1.jsp";
parent.content.location.href="/admin/program/update_program.jsp?templatefile="+checkIPs[1];
}
function onDeleteProgram(){
var num = chkNum();
if(num<1){
alert("��ѡ����Ҫɾ���Ľ�Ŀ��");
return;
}
if(confirm("��ʾ��Ϣ��ȷ��ɾ��ѡ�н�Ŀ��")){
var rqurl=escape(parent.content.location.href);
var checkIPs= getCheckIPs();
parent.listtop.location.href="/admin/program/deleteProgramApp.jsp?programe_file="+checkIPs+"&rqurl="+rqurl;
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
		   <table width="110" border="0" cellspacing="0" cellpadding="0">
	          <tr>
				<%--<td id="btn1" onmouseout="MM_swapBgImgRestore()" 
				onmouseover="MM_swapBgImage('btn1','','/images/device/btn_vnc_over.gif',1)" 
				background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onUpdateProgram();" style="cursor:hand">
				&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
				�޸Ľ�Ŀ
				</span></td>
				--%><td id="btn2" height="30">
				&nbsp;
				</td>
				
	          </tr>
	      </table>
	      
	  </td>
      
        </tr>
  </table>
   <table width="100%"  height="32" border="0" cellspacing="0" cellpadding="3" class="TitleBackgroundBg">
    <tr>
		<td class="TitleSmall">&nbsp;&nbsp;
		��Ŀ���� - ����ʧ�ܽ�Ŀ</td>
        </tr>
      </table>  

</form>	
</body> 

</html>
