<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<html>
<head>
<% String left_cmd=request.getParameter("left_cmd")==null?"":request.getParameter("left_cmd");
String zu_id=request.getParameter("zu_id")==null?"no":request.getParameter("zu_id");
 String terminalstr=UtilDAO.getGBK(request.getParameter("terminalstr"));
 request.setAttribute("port",System.getProperty("CLENTPORT_HOME"));
%>
<title></title>
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<script language="JavaScript" src="/js/movebar.js"></script>
<script language="javascript" src="/js/vcommon.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
		var firstIframe= "list_ifrm";
		function onLoad(){	
			goList();
		}
		function goList(){
			var forms = document.listForm;
			forms.target = 'list_ifrm';	
			forms.action = "/rq/terminalList?cmd=list_ifrmList&left_cmd=<%=left_cmd%>&terminalstr=<%=terminalstr%>&zu_id=<%=zu_id%>";
			forms.submit();
		}
		
		function onUpdateSoftware(checkIPs){
		 cntip="";
		showDivFrame("&nbsp;�ն��������","/admin/terminal/updateSoftware.jsp?timeStamp=" + new Date().getTime(),"510","300");
		}
		function onRestart(checkIPs){
		 cntip="";
		showDivFrame("&nbsp;��������","/admin/terminal/restart.jsp?timeStamp=" + new Date().getTime(),"520","400");
		}
		function onDown(checkIPs){
		
		 cntip="";
		 showDivFrame("&nbsp;�ն�����","/admin/terminal/ondown.jsp?timeStamp=" + new Date().getTime(),"480","300");
		}
		function onStartcnt(checkIPs){
		 cntip="";
		showDivFrame("&nbsp;ֹͣ����","/admin/terminal/startcnt.jsp?timeStamp=" + new Date().getTime(),"480","300");
		}
		function sendNotice(checkIPs){
		 cntip="";
		showDivFrame("&nbsp;��������֪ͨ","/admin/terminal/sendNotice.jsp?timeStamp=" + new Date().getTime(),"800","320");
		}
		function clearPrograme(checkIPs){
		 cntip="";
		showDivFrame("&nbsp;��Ŀ��ʼ��","/admin/terminal/clearproject.jsp?timeStamp=" + new Date().getTime(),"480","300");
		}
		
		function timeSynchronization(){
		 cntip="";
		 showDivFrame("&nbsp;ʱ��ͬ��","/admin/terminal/clientTimeSynchronization.jsp?timeStamp=" + new Date().getTime(),"480","300");
		}
		
		function ledManager(){
		 cntip="";
		 showDivFrame("&nbsp;LED����","/admin/terminal/bank_ledManager.jsp?timeStamp=" + new Date().getTime(),"600","550");
		}
		
		function updateDownloadStartEnd(){
		 cntip="";
		  showDivFrame("&nbsp;���ػ�����","/admin/terminal/update_download_start_end.jsp?timeStamp=" + new Date().getTime(),"580","300");
		}
		
		function deleteClient(){
		 cntip="";
		  showDivFrame("&nbsp;ɾ���ն�","/admin/terminal/deleteClient.jsp?timeStamp=" + new Date().getTime(),"500","300");
		}
		
		function allsetipport(){
		  cntip="";
		  showDivFrame("&nbsp;�����ն�","/admin/terminal/clientset_ip_port.jsp?timeStamp=" + new Date().getTime(),"500","300");
		}
		
		function ledset(mac,ip){
			showDivFrame("&nbsp;LED��ʾ����","/admin/program/ledshow/ledset.jsp?mac="+mac+"&ip="+ip+"&timeStamp=" + new Date().getTime(),"550","300");
		}
		
		var cntip;
		function guiCamera(cnt_title,cntIp,cntMac){
		    cntip=cntIp;
			document.getElementById("div_iframe").src="/loading.jsp";
			showDivFrame("&nbsp;�նˡ�"+cnt_title+"����ǰ����ͼƬ","/admin/terminal/guiCamera.jsp?cntIp="+cntIp+"&cnt_mac="+cntMac+"&t=" + new Date().getTime(),"700","500");
		}
		
		function viewProjectMenu(cnt_title,programName,cntmac,cntIp){
		  cntip="";
		  showDivFrame("&nbsp;�նˡ�"+cnt_title+"����Ŀ��","/admin/terminal/viewProjectMenu.jsp?resips="+cntIp+"&cntmac="+cntmac+"&t=" + new Date().getTime(),"800","400");
		}
		function viewterminal(cname,cid){
		   cntip="";
		   showDivFrame("&nbsp;�նˡ�"+cname+"����ϸ��Ϣ","/rq/viwe?cmd=viweOk&cid="+cid+"&t=" + new Date().getTime(),"600","300");
		}
		     function showDivFrame(title,url,fwidth,fheight){
		         
					parent.parent.parent.document.body.scrollTop = "0px";
					parent.parent.parent.document.getElementById("div_iframe1").width=fwidth;
					if(fheight!=""){
						parent.parent.parent.document.getElementById("div_iframe1").height=fheight;
					}
					parent.parent.parent.document.getElementById("divframe1").style.left=(parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";;
					parent.parent.parent.document.getElementById("divframe1").style.top=(parent.parent.parent.document.body.clientHeight - fheight) / 4+"px";
					parent.parent.parent.document.getElementById("div_iframe1").src=url;
					parent.parent.parent.document.getElementById("titlename1").innerHTML=title;
					parent.parent.parent.document.getElementById("divframe1").style.display='block';
					parent.parent.parent.document.getElementById("massage1").style.display='block';
					parent.parent.parent.document.getElementById("bgDiv").style.display='block';
					parent.parent.parent.document.getElementById("bgDiv").style.visibility='visible';
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
		function closedivframe(){
			cntip="";
			document.getElementById("div_iframe").src="/loading.jsp";
			document.getElementById("divframe").style.display='none';
			document.getElementById('divframe').style.visibility="hidden";	
		}
		function refresh(){
		 //alert(cntip);
		 if(cntip!=""){
		   	document.getElementById("div_iframe").src="http://"+cntip+"${port}/guiCamera1.jsp?timeStamp="+new Date().getTime();
		   	window.frames["div_iframe"].location.reload();
		 }else{
		   window.frames["div_iframe"].location.reload();
		 }
		}

//-->
</script>
<style type="text/css">
#divframe{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2);  visibility:hidden}
#mask{ position:absolute; top:0; left:0; width:expression(body.scrollWidth); height:expression(body.scrollHeight); background:#000000; filter:ALPHA(opacity=60); z-index:1; visibility:hidden}
#massage{border:#6699cc solid; border-width:1 1 1 1; background:#fff; color:#036; font-size:12px; line-height:150%; visibility:hidden}
.header{background:url(/images/device/btn_background.gif);  font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; cursor: move;}
</style>
</head>
<body  onload="javascript:onLoad();" >

<div id="divframe">
<div  id="massage">
<table cellpadding="0" cellspacing="0" >
	<tr  height="30px;" class=header  onmousedown=MDown(divframe)><td align="left" style="font-weight: bold"><span id="titlename"></span></td>
		<td height="5px" align="right" id="div_close_td" style="display: none;"><img src="/images/refresh.gif" style="cursor: pointer;" onclick="javaScript:refresh();"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javaScript:;" style="color: #000000" onclick="closedivframe();"   >�ر�</a></td></tr>
	<tr><td colspan="2">
	<iframe src="/loading.jsp" scrolling="no" id="div_iframe" onload="SetWinHeight(this)"  name="div_iframe" frameborder="0" ></iframe>
		
	</td></tr>
</table>
</div>
</div>
<form name="ipforms"><input type="hidden" name="checkips"/></form>
<form method="POST" name="listForm" id="listForm" onsubmit="return false;">
<!-- list -->
<input type="hidden" name="sel_all" id="sel_all" onclick="all_chk(this.checked);">
<table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0"  class="TitleBackground">
    <tr> 
    	<td width="3%" class="InfoTitle">���</td>
    	<td width="13%" class="InfoTitle">�ն���</td>
      	<td width="18%" class="InfoTitle">�ն�����</td>
     	<td width="11%" class="InfoTitle">IP��ַ</td>
      	<td width="7%" class="InfoTitle">��ǰ״̬</td>
       <td width="7%" class="InfoTitle">����״̬</td>
       <td width="10%" class="InfoTitle">�ն˰汾��</td>
      <td width="14%" class="InfoTitle">��ǰ���Ž�Ŀ</td>
      <td width="17%" class="InfoTitle">����</td>        
    </tr>
</table>
<iframe name="list_ifrm"  width="100%"  scrolling='no' height="92%"  frameborder='0' marginwidth="0" marginheight="0"  ></iframe>
</form>    
</body>
</html>