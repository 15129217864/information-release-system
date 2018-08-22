<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<%
response.setHeader("Cache-Control","no-store"); 
response.setHeader("Pragrma","no-cache"); 
response.setDateHeader("Expires",0); 

String cnt_mac=request.getParameter("cnt_mac");
String cntIp=request.getParameter("cntIp");
FirstStartServlet.client_result_states.remove(cntIp+"_"+cnt_mac+"_lv0011");
FirstStartServlet.client.allsend(cnt_mac,cntIp, "lv0011");

 %>
<html>
  <head>
  <link rel="stylesheet" href="/css/style.css" type="text/css" />
    <title></title>
    <script language="javascript" src="/js/vcommon.js"></script>
    <script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
    <script type="text/javascript">
    var timer=null;
    var time_num=0;
    function guiCamera(result_int){
		time_num++;
		if(time_num<50){
	    	if(result_int==1){
	    		document.getElementById("guiCameraid").innerHTML="<img src='/serverftp/program_file/screen/<%=cnt_mac%>.jpg?t=" + new Date().getTime()+"' alt='Ωÿ∆¡Õº∆¨'  border='0' width='700' height='500'/>";
	    		//parent.document.getElementById("div_close_td").style.display="block";
	    		clearInterval(timer);
	    	}else if(result_int==2){
	    		document.getElementById("guiCameraid").innerHTML="<span style='color:red'>Ωÿ∆¡ ß∞‹£°«ÎºÏ≤ÈFTP «∑Ò¡¨Ω”£°</span>";
	    		//parent.document.getElementById("div_close_td").style.display="block";
	    		clearInterval(timer);
	    	}else if(result_int==3){
	    		document.getElementById("guiCameraid").innerHTML="<span style='color:red'>Ωÿ∆¡≥ˆ¥Ì£°</span>";
	    		//parent.document.getElementById("div_close_td").style.display="block";
	    		clearInterval(timer);
	    	}else if(result_int==4){
	    		document.getElementById("guiCameraid").innerHTML="<span style='color:red'>÷’∂Àªπ”–Œ¥÷¥––÷∏¡Ó£¨«Î…‘∫ÛΩÿ∆¡£°</span>";
	    		//parent.document.getElementById("div_close_td").style.display="block";
	    		clearInterval(timer);
	    	}
    	}else{
    		document.getElementById("guiCameraid").innerHTML="<span style='color:red'>Õ¯¬Á≥¨ ±£¨«Î…‘∫Û‘Ÿ ‘£°</span>"
    		//parent.document.getElementById("div_close_td").style.display="block";
    		clearInterval(timer);
    	}
    }
    
    timer= setInterval("DwrClass.guiCamera('<%=cnt_mac%>','<%=cntIp%>','lv0011',guiCamera)", 1000);
    
	function closedivframe(){
		parent.closedivframe(1);
	}
		</script>
  </head>

  <body style="margin: 0px;" ondblclick="closedivframe()" >
     <table align="center" width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
    	<tr><td align="center" valign="middle" id="guiCameraid">
    		<img src="/images/mid_giallo.gif" />&nbsp;’˝‘⁄Ωÿ∆¡£¨«Î…‘∫Û...</td>
    	</tr>
    </table>
   </body>
</html>
