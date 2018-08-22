<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
		
		function onCreateTemplate(){
			var cnt_height=parent.parent.parent.document.body.clientHeight;
			var cnt_width=parent.parent.parent.document.body.clientWidth;
			template_width=1000;
			parent.content.showDiv(2,"新建模板",template_width,cnt_height-100,"/admin/template/create_template.jsp");
		}
		function onUpdateProgram(){
			var num = chkNum();
			if(num<1){
			alert("请选择需要修改的模版！");
			return;
			}else if(num>1){
			alert("只能选择一个模版修改！");
			return;
			}
			var checkIPs= getCheckIPs().split("!");
			parent.listtop.location.href="/admin/template/programtop1.jsp";
			parent.content.location.href="/admin/template/update_program.jsp?templatefile="+checkIPs[1];
		}
		function onDeleteTemplate(){
			var num = chkNum();
			if(num<1){
			alert("请选择需要删除的模版！");
			return;
			}
			if(confirm("提示信息：确认删除选中模版？")){
			   var rqurl=escape(parent.content.location.href);
			   var checkIPs= getCheckIPs();
			   //header_iframe.location.href="/admin/template/delete_template.jsp?programe_file="+checkIPs;
			   
			   document.getElementById("header_iframe").src="/admin/template/delete_template.jsp?programe_file="+checkIPs+"&rqurl="+rqurl;
			}
		}
		
		function copyTemplate(){
			var num = chkNum();
			if(num<1){
				alert("请选择需要复制的模版！");
				return;
			}else if(num>1){
				alert("只能选择一个模版！");
				return;
			}
			var checkIds= getCheckIPs();
			var rqurl=escape(parent.content.location.href);
			//alert(checkIds+"____"+rqurl);
			parent.content.showDiv(2,"&nbsp;复制模版",300,300,"/admin/template/copyTemplate.jsp?templateid="+checkIds+"&rqurl="+rqurl);
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
		function serchTemplate(){
			var templatestr=document.getElementById("templatestr").value;
			parent.content.location.href="/admin/template/searchTemplate.jsp?templatestr="+templatestr;
		}
		
		function goKeypress(){
		   if (event.keyCode == 13)
			  serchTemplate();
		}
</script>
  </head>
  
 <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">	
<form name="headForm" id="headForm" method="post" onsubmit="return false;">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0" background="/images/device/btn_background.gif">
    <tr>
      <td>
		   <table border="0" cellspacing="0" cellpadding="0">
	          <tr>
				   <c:set var="str" value="${lg_authority}"/>
				   <c:if test="${fn:contains(str,'N')}">
	          
			        <td id="btn" onmouseout="MM_swapBgImgRestore()" 
					onmouseover="MM_swapBgImage('btn','','/images/device/btn_vnc_over.gif',1)" 
					background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onCreateTemplate();" style="cursor:hand;background-repeat:no-repeat;">
					&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
					新建模板
					</span></td>
				</c:if>
				<td id="btn1" onmouseout="MM_swapBgImgRestore()" 
				onmouseover="MM_swapBgImage('btn1','','/images/device/btn_vnc_over.gif',1)" 
				background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:copyTemplate();" style="cursor:hand;background-repeat:no-repeat;">
				&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
				复制模板
				</span></td>
				
				<c:if test="${fn:contains(str,'O')}">
				<td id="btn2" onmouseout="MM_swapBgImgRestore()" 
				onmouseover="MM_swapBgImage('btn2','','/images/device/btn_vnc_over.gif',1)" 
				background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onDeleteTemplate();" style="cursor:hand;background-repeat:no-repeat;">
				&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
				删除模板
				</span></td>
				</c:if><td width="1" height="30" ></td>
	          </tr>
	      </table>
	      
	  </td>
      <td align="right" width="*">
	      <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td align='right'><input type="text" id="templatestr" name="templatestr" size="30" value="" maxlength="100"   onkeypress="goKeypress();" ></td>
	            <td>&nbsp;</td>
	 	        <td width="60" height="18" align='center' onclick="serchTemplate();" background='/images/device/btn_search_blk.gif' style='cursor:hand'>
	 	        <span style="font:7pt;color:white;" >
				搜 索
				</span>
	 	        </td>				
	            
	          </tr>
	      </table>
      </td>
        </tr>
  </table>
   <table width="100%"  height="32" border="0" cellspacing="0" cellpadding="3" class="TitleBackgroundBg">
    <tr>
		<td class="TitleSmall">&nbsp;&nbsp;
		<%
			out.println("模板管理 > 所有模板");
		%></td>
        </tr>
      </table>  

</form>	
<div style="display: none;" ><iframe src="/loading.jsp" scrolling="no" id="header_iframe" name="header_iframe" frameborder="0"></iframe></div>

</body> 

</html>
