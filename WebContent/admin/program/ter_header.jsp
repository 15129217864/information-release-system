<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% String title=request.getParameter("title");
	String left_menu=request.getParameter("left_menu");
	 String zu_id=request.getParameter("zu_id");
	 String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    
    <title>My JSP 'ter_header.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script language="JavaScript" src="/js/jquery1.4.2.js"></script>
		<script language="JavaScript" src="/js/prototype.js"></script>
		<script language="javascript" src="/js/vcommon.js"></script>				
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
		   
			var cnt_height=parent.parent.parent.document.body.clientHeight;
			var cnt_width=parent.parent.parent.document.body.clientWidth;
			template_width=1030;
			parent.content.showDiv(2,"�½���Ŀ",template_width,cnt_height-60,"/admin/program/create_program1.jsp");
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
			
			var cnt_height=parent.parent.parent.document.body.clientHeight;
			var cnt_width=parent.parent.parent.document.body.clientWidth;
			template_width=1030;
			parent.content.showDiv(2,"�޸Ľ�Ŀ",template_width,cnt_height-30,"/admin/program/update_program.jsp?templatefile="+checkIPs[1]+"&t=" + new Date().getTime());

		}
		function onSendDefaultProgram(){
			var num = chkNum();
			if(num<1){
				alert("��ѡ����Ҫ���͵Ľ�Ŀ��");
				return;
			}else if(num>1){
				alert("ֻ��ѡ��һ����ĿΪĬ�Ͻ�Ŀ��");
				return;
			}
			
			 var checkIPs=getCheckIPs().split("!");
			 var isauditing='${isauditing}';
				if(isauditing==1){
					 if(checkAuditingStatus(checkIPs[1]))
					   parent.content.showDiv(1,"����Ĭ�Ͻ�Ŀ",700,310,"/admin/program/sendDefaultProgram.jsp?programe_file="+checkIPs[1]+"&t=" + new Date().getTime());
					 else
					   alert("��Ǹ,��ǰ��Ŀδ��ˣ�");
				}else{
					 parent.content.showDiv(1,"����Ĭ�Ͻ�Ŀ",700,310,"/admin/program/sendDefaultProgram.jsp?programe_file="+checkIPs[1]+"&t=" + new Date().getTime());
				}
		}
		
		function onSendDemandFileProgram(){//���͵㲥�ļ����նˣ�
		     var num = chkNum();
			if(num<1){
				alert("��ѡ����Ҫ���͵㲥��Ŀ�ļ���");
				return;
			}
			var checkprogrames= getCheckIPs();
			checkprogrames=checkprogrames.substring(1);
			//alert(checkprogrames);
			var isauditing='${isauditing}';
			if(isauditing==1){
				 if(checkAuditingStatus(checkprogrames))
				    parent.content.showDiv(1,"���͵㲥�ļ����ն�",700,310,"/admin/program/sendDemandFileProgram.jsp?programe_file="+checkprogrames+"&t=" + new Date().getTime());
				 else
				    alert("��Ǹ,��ǰ��Ŀδ��ˣ�");
			}else{
				parent.content.showDiv(1,"���͵㲥�ļ����ն�",700,310,"/admin/program/sendDemandFileProgram.jsp?programe_file="+checkprogrames+"&t=" + new Date().getTime());
			}
		}
		
		function onStopDemandProgram(){
		   parent.content.showDiv(1,"ֹͣ�㲥��Ŀ",500,310,"/admin/program/closeDemandProgram.jsp?t=" + new Date().getTime());
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
				document.getElementById("header_iframe").src="/admin/program/deleteProgram.jsp?programe_file="+checkIPs+"&rqurl="+rqurl;
			}
		}
       function onSendProgram(){
		
		    var num = chkNum();
			if(num<1){
				alert("��ѡ����Ҫ���͵Ľ�Ŀ��");
				return;
			}
			//if(getCheckIPs22()==1){
				//alert("��ѡ��Ŀû�����ò������ԣ���������Ŀ�������á���ť���ã�");
				//return;
			//}
			newSendProject();
		}
		function getCheckIPs22(){
			var forms = parent.content.ifrm_Form;
			for(i=0;i<forms.checkbox.length;i++){
				if(forms.checkbox[i].checked == true) {
					ip = forms.checkbox[i].value.split("_")[1];
					if(ip==0){
					return 1;
					}
				}
			}
			if(forms.checkbox.checked == true){
				ip=forms.checkbox.value.split("_")[1];
				if(ip==0){
					return 1;
				}
			}
			return 0;
		}
		
		function newSendProject(){
				//var checkIPs= getCheckIPs();
				var checkprogrames= getCheckIPs();
				var isauditing='${isauditing}';
				if(isauditing==1){
				    if(checkAuditingStatus(checkprogrames.substring(1)))
					   parent.content.showDiv(2,"���ͽ�Ŀ",900,500,"/admin/program/selectclientIP.jsp?programe_file="+checkprogrames+"&t=" + new Date().getTime());
				    else
				    	alert("��Ǹ,��ǰ��Ŀδ��ˣ�");
				}else{
					parent.content.showDiv(2,"���ͽ�Ŀ",900,500,"/admin/program/selectclientIP.jsp?programe_file="+checkprogrames+"&t=" + new Date().getTime());
				}
		}
		function exportProject(){
			var num = chkNum();
			if(num<1){
				alert("��ѡ����Ҫ�����Ľ�Ŀ��");
				return;
			}
				var checkIPs= getCheckIPs();
				parent.content.showDiv(2,"������Ŀ�ͽ�Ŀ�����ṩUSB���ţ�",655,500,"/admin/program/selectclientIP5.jsp?programe_file="+checkIPs+"&t=" + new Date().getTime());
		}
		function getCheckIPs(){
		
			var forms = parent.content.ifrm_Form;
			var checkIPs="";
			
			for(i=0;i<forms.checkbox.length;i++){
				if(forms.checkbox[i].checked == true) {
					ip = forms.checkbox[i].value.split("_")[0];
					checkIPs+="!"+ip;
				}
			}
			if(forms.checkbox.checked == true){
			   checkIPs+="!"+forms.checkbox.value.split("_")[0];
			}
			return checkIPs;
		}
		

		function moveProgram(){
			var num = chkNum();
			if(num<1){
				alert("��ѡ����Ҫ�ƶ��Ľ�Ŀ��");
				return;
			}
			var checkIPs= getCheckIPs();
			var rqurl=escape(parent.content.location.href);
			parent.content.showDiv(2,"&nbsp;�ƶ���Ŀ",330,260,"/admin/program/moveProgram.jsp?programIds="+checkIPs+"&rqurl="+rqurl);
		}
		
		function copyProgram(){
			var num = chkNum();
			if(num<1){
				alert("��ѡ����Ҫ���ƵĽ�Ŀ��");
				return;
			}else if(num>1){
				alert("ֻ��ѡ��һ����Ŀ��");
				return;
			}
			var checkIPs= getCheckIPs();
			var rqurl=escape(parent.content.location.href);
			parent.content.showDiv(2,"&nbsp;���ƽ�Ŀ",330,300,"/admin/program/copyProgram.jsp?programurls="+checkIPs+"&rqurl="+rqurl);
		}

		function serchProgram(){
			var programstr=document.getElementById("programstr").value;
			parent.content.location.href="/admin/program/searchPrograma.jsp?programstr="+programstr;
		}
		function piaoWu(){
			window.location.href="/admin/program/ter_header.jsp?title=PIAOWU_INFO&left_menu=PIAOWU_INFO&zu_id=no";
			parent.listtop.location.href="/admin/program/program_list_top5.jsp";
			parent.content.location.href="/admin/program/ticketList.jsp";
		}
		function opolicesubstation(){
		   parent.content.showDiv2(1,"����Ѷ������Ϣ",1020,600,"<%=basePath%>rq/sendpolicesubstation?op=list",100);
		}
		
		function goKeypress(){
			if (event.keyCode == 13)
			  serchProgram();
		}

        function checkAuditingStatus(jmurl){
           var bool=false;
            var param="programe_file="+jmurl+"&op=getstatus&t=" + new Date().getTime();
            //alert(param);
            var j = jQuery.noConflict();
            j.ajax({
			    type: "POST",
			    url:  "/rq/jmauditingstatus",
			    dataType:"text",
				data:param,
				async:false,
			    success: function (data){
			      //alert(data);
			      if(data=="1"){
			       	 bool=true;
			      }else{
			        bool=false;
			       //alert("��Ǹ,��ǰ��Ŀδ��ˣ�");
			      }
			    },
                   beforeSend: function() {}, //���󷢳�ǰ�Ĵ�����,����(XMLHttpRequest����,����״̬)
			    error:function(XmlHttpRequest,textStatus,errorThrown){  
		            var $errorPage = XmlHttpRequest.responseText;  
					if($errorPage!='')
		              alert($($errorPage).text());  
		          },
			    complete: function (XHR, TS) { XHR = null; } //�ͷ�ajax��Դ\
			});
			return bool;
        }
</script>
  </head>
  
 <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">	
<form name="headForm" id="headForm" method="post" onsubmit="return false;">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0" background="/images/device/btn_background.gif">
    <tr>
      <td>
		   <table  border="0"  cellspacing="0" cellpadding="0">
	          <tr>
	           <c:if test="${not empty lg_authority}">
				  <c:set var="str" value="${lg_authority}"/>
				   <c:if test="${fn:contains(str,'Q')}">
			        <td id="btn" onmouseout="MM_swapBgImgRestore()" 
					onmouseover="MM_swapBgImage('btn','','/images/device/btn_vnc_over.gif',1)" 
					background="/images/device/btn_vnc.gif"  width="110" height="30" align="center"  onclick="javascript:onCreateProgram();" style="cursor:pointer;background-repeat:no-repeat">
					<span style="font-size:11px;padding-left:10px" >
					�½���Ŀ
					</span></td>
				</c:if>
				<c:if test="${fn:contains(str,'R')}">
					<td id="btn1" onmouseout="MM_swapBgImgRestore()" 
					onmouseover="MM_swapBgImage('btn1','','/images/device/btn_vnc_over.gif',1)" 
					background="/images/device/btn_vnc.gif" width="110" height="30" align="center"  onclick="javascript:onUpdateProgram();" style="cursor:pointer;background-repeat:no-repeat;">
					<span style="font-size:11px;padding-left:10px" >
					�޸Ľ�Ŀ
					</span></td>
				</c:if>
				 <c:if test="${fn:contains(str,'S')}">
					<td id="btn2" onmouseout="MM_swapBgImgRestore()" 
					onmouseover="MM_swapBgImage('btn2','','/images/device/btn_vnc_over.gif',1)" 
					background="/images/device/btn_vnc.gif" width="110" height="30" align="center"  onclick="javascript:onDeleteProgram();" style="cursor:pointer;background-repeat:no-repeat;">
					<span style="font-size:11px;padding-left:10px" >
					ɾ����Ŀ
					</span></td>
				</c:if>
				<c:if test="${fn:contains(str,'T')}">
					<td id="btn3" onmouseout="MM_swapBgImgRestore()" 
					onmouseover="MM_swapBgImage('btn3','','/images/device/btn_vnc_over.gif',1)" 
					background="/images/device/btn_vnc.gif" width="110" height="30" align="center"  onclick="javascript:onSendProgram();" style="cursor:pointer;background-repeat:no-repeat;">
					<span style="font-size:11px;padding-left:10px" >
					���ͽ�Ŀ
					</span></td>
				</c:if> 
				<c:if test="${sessionScope.lg_user.lg_role!=3}">
				<c:if test="${fn:contains(str,'T')}">
				   <c:if test="${ispointplay eq '1' }">
						<td id="btn4" onmouseout="MM_swapBgImgRestore()" 
						onmouseover="MM_swapBgImage('btn4','','/images/device/btn_vnc_over.gif',1)" 
						background="/images/device/btn_vnc.gif" width="110" height="30" align="center"  onclick="javascript:onSendDemandFileProgram();" style="cursor:pointer;background-repeat:no-repeat;">
						<span style="font-size:10px;padding-left:20px" >
						���͵㲥�ļ�
						</span></td>
					</c:if>
				</c:if>
				<c:if test="${fn:contains(str,'T')}">
					<td id="btn4_1" onmouseout="MM_swapBgImgRestore()" 
					onmouseover="MM_swapBgImage('btn4_1','','/images/device/btn_vnc_over.gif',1)" 
					background="/images/device/btn_vnc.gif" width="110" height="30" align="center"  onclick="javascript:onSendDefaultProgram();" style="cursor:pointer;background-repeat:no-repeat;">
					<span style="font-size:10px;padding-left:20px" >
					����Ĭ�Ͻ�Ŀ
					</span></td>
				</c:if>
				</c:if>
				<c:if test="${fn:contains(str,'T')}">
				   <td id="btn12" onmouseout="MM_swapBgImgRestore()" 
					onmouseover="MM_swapBgImage('btn12','','/images/device/btn_vnc_over.gif',1)" 
					background="/images/device/btn_vnc.gif" width="110" height="30" align="center"  onclick="javascript:copyProgram();" style="cursor:pointer;background-repeat:no-repeat;">
					<span style="font-size:11px;padding-left:10px" >
					���ƽ�Ŀ
					</span></td>
					<td id="btn13" onmouseout="MM_swapBgImgRestore()" 
					onmouseover="MM_swapBgImage('btn13','','/images/device/btn_vnc_over.gif',1)" 
					background="/images/device/btn_vnc.gif" width="110" height="30" align="center"  onclick="javascript:moveProgram();" style="cursor:pointer;background-repeat:no-repeat;">
					<span style="font-size:11px;padding-left:10px" >
					�ƶ���Ŀ
					</span></td>
					
					<td id="btn11" onmouseout="MM_swapBgImgRestore()" 
					onmouseover="MM_swapBgImage('btn11','','/images/device/btn_vnc_over.gif',1)" 
					background="/images/device/btn_vnc.gif" width="110" height="30" align="center"  onclick="javascript:exportProject();" style="cursor:pointer;background-repeat:no-repeat;">
					<span style="font-size:11px;padding-left:10px" >
					������Ŀ
					</span></td>
				</c:if>
				<!--<c:if test="${fn:contains(str,'T')}">
					<td id="btn6" onmouseout="MM_swapBgImgRestore()" 
					onmouseover="MM_swapBgImage('btn6','','/images/device/btn_vnc_over.gif',1)" 
					background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:piaoWu();" style="cursor:pointer;background-repeat:no-repeat;">
					<span style="font-size:12px;" >
					LED��Ϣ
					</span></td>
				</c:if>
				<!--<c:if test="${fn:contains(str,'T')}">
					<td id="btn11" onmouseout="MM_swapBgImgRestore()" 
					onmouseover="MM_swapBgImage('btn11','','/images/device/btn_vnc_over.gif',1)" 
					background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:opolicesubstation();" style="cursor:pointer;background-repeat:no-repeat;">
					<span style="font-size:12px;" >
					Ѷ������Ϣ
					</span></td>
				</c:if>
				
				<!--<c:if test="${fn:contains(str,'T')}">
					<td id="btn5" onmouseout="MM_swapBgImgRestore()" 
					onmouseover="MM_swapBgImage('btn5','','/images/device/btn_vnc_over.gif',1)" 
					background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onStopDemandProgram();" style="cursor:hand;background-repeat:no-repeat;">
					<span style="font-size:12px;" >
					ֹͣ�㲥
					</span></td>
				</c:if>-->
				</c:if>
	          </tr>
	      </table>
	      
	  </td>
      <td align="right">
	      <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td align='right'><input type="text" id="programstr" name="programstr" size="14" value="" maxlength="50" style="width:80px" onkeypress="goKeypress();" ></td>
	            <td>&nbsp;</td>
	 	        <td width="60" height="18" align='center'  onclick="serchProgram();"background='/images/device/btn_search_blk.gif' style='cursor:pointer'>
	 	        <span style="font:7pt;color:white;" >
				�� ��
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
		<%if("TEMPLET".equals(left_menu)){
			out.println("ģ�����");
		}else if("PROGRAM_ZU".equals(left_menu)){
			out.println("��Ŀ�����");
		}else if("TITLE_ASC".equals(left_menu)){
			out.println("�������� - ����");
		} else if("TITLE_DESC".equals(left_menu)){
			out.println("�������� - ����");
		}else if("PIAOWU_INFO".equals(left_menu)){
			out.println("��Ŀ���� - LED��Ϣ");
		}else{
			out.println("��Ŀ���� - ���н�Ŀ");
		} 
		%></td>
		<%if("PIAOWU_INFO".equals(left_menu)){
			%>
			<td width="80%" >
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><input type="button" value="LED��ʾ����" class="button" onclick="parent.content.ledset()"/></td>
						</tr>
					</table>

			</td>
			
			<%}%>
        </tr>
      </table>  

</form>	
<div style="display: none;" ><iframe src="/loading.jsp" scrolling="no" id="header_iframe" name="header_iframe" frameborder="0"></iframe></div>

</body> 

</html>
