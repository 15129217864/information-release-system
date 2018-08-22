<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% String title=request.getParameter("title");
	String left_cmd=request.getParameter("left_cmd");
	 String zu_id=request.getParameter("zu_id");
	  String cmd=request.getParameter("cmd")==null?"LIST":request.getParameter("cmd");
	  String terminalstr=UtilDAO.getGBK(request.getParameter("terminalstr"));
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
		  <script language="javascript" src="/js/jquery1.4.2.js"></script>
		<script language="javascript" src="/js/vcommon.js"></script>
		<script language="JavaScript" src="/js/prototype.js"></script>				
		<script language="JavaScript" src="/js/did_common.js"></script>
		<script language="JavaScript" src="/js/common.js"></script>
		<script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
        <script type="text/javascript">
        var cmdval="NO";
        jQuery.noConflict();

        function goList(val,dsn){
            cmdval=val;
			var dval;
			if(dsn!=null) 
			   dval = dsn;
	
			if(val=="MONITORING"){
				jQuery("#btn_list").attr({"background":"/images/device/tab_01.gif"});
				jQuery("#btn_monitoring").attr({"background":"/images/device/tab_02_over.gif"});
			}else if(val=="LIST"){
				jQuery("#btn_list").attr({"background":"/images/device/tab_01_over.gif"});
				jQuery("#btn_monitoring").attr({"background":"/images/device/tab_02.gif"});
			}	
			var form = document.headForm;
			form.action = "/rq/terminalList?cmd="+val+"&left_cmd=<%=left_cmd%>&zu_id=<%=zu_id %>&terminalstr=<%=terminalstr%>";
			form.target = "deviceContent"; 
			form.submit();
        }
        
        function chkNum(){
			if(parent.deviceContent.list_ifrm && parent.deviceContent.list_ifrm.document.ifrm_Form.checkbox!=null){
				var forms = parent.deviceContent.list_ifrm.document.ifrm_Form;
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
			parent.deviceContent.onUpdateSoftware("");
		}
		function onRestart(){
			parent.deviceContent.onRestart("");
		}
		function onDown(){
			parent.deviceContent.onDown("");
		}
		function onStartcnt(){
			parent.deviceContent.onStartcnt("");
		}
		function sendNotice(){
			  parent.deviceContent.sendNotice("");
		}
		function  clearPrograme(){
			  parent.deviceContent.clearPrograme("");
		}
		function  clientSynchronization(){
			  parent.deviceContent.timeSynchronization();
		}
		
		function  updatedownloadstartend(){
			  parent.deviceContent.updateDownloadStartEnd();
		}
		function deleteClient(){
			parent.deviceContent.deleteClient();
		}
		function ledManager(){
			parent.deviceContent.ledManager();
		}
        
        function allsetipport(){
          parent.deviceContent.allsetipport();
        }


		function getCheckIPs(){
		   alert("getCheckIPs()");
			var forms = parent.deviceContent.list_ifrm.document.ifrm_Form;
			var checkIPs="";
			for(i=0;i<forms.checkbox.length;i++){
				if(forms.checkbox[i].checked == true)  {
					checkIPs+="!"+forms.checkbox[i].value;
				}
			}
			if(forms.checkbox.checked == true){
				checkIPs+="!"+forms.checkbox.value;
			}
			return checkIPs;
		}
		function serchTerminal(){
			var scmd="";
			if(cmdval=="NO"){
				scmd="<%=cmd%>";
			}else{
				scmd=cmdval;
			}
			var form = document.headForm;
			var terminalstr=document.headForm.terminalstr.value;
			form.action = "/rq/terminalList?cmd="+scmd+"&left_cmd=SEARCHTER&zu_id=<%=zu_id %>&terminalstr="+terminalstr;
			form.target = "deviceContent"; 
			form.submit();
			window.location.href="/admin/terminal/ter_header.jsp?cmd="+scmd+"&left_cmd=SEARCHTER&title=SEARCHTER&zu_id=&terminalstr="+terminalstr;
		}
		function downvnc(){
		  window.location.href="/download_vnc.jsp";
		}
		function downie8(){
		  window.location.href="/download_ie8.jsp";
		}
		
		function ledManager(){
			parent.deviceContent.ledManager();
		}
		
		function RestartServices(){
			if(confirm("提示信息：确认重启服务？"))
			  DwrClass.restartXctService(backdata);
			}
		function backdata(data){
			   if(data=="ok"){
			      alert("服务重启中...，请稍候重新登录系统！");
			      window.parent.parent.parent.parent.location.reload();
			   }else{
			      alert("服务重启失败！");
			   }
		}
		function goKeypress(){
			if (event.keyCode == 13)
				serchTerminal();
		}
</script>
  </head>
  
 <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">	
<form name="headForm" id="headForm" method="post" onsubmit="return false;">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0" background="/images/device/btn_background.gif">
    <tr>
      <td>
		   <table  border="0" cellspacing="0" cellpadding="0">
	            <tr>
			   <c:set var="str" value="${lg_authority}"/>
			   
	            <%--<c:if test="${fn:contains(str,'A')}">
				        <td id="btn" onmouseout="MM_swapBgImgRestore()" 
						onmouseover="MM_swapBgImage('btn','','/images/device/btn_vnc_over.gif',1)" 
						background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onDown();" 
						style="cursor:hand;background-repeat:no-repeat;">
						&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
						休&nbsp;&nbsp;眠
						</span></td>
				</c:if>
				<c:if test="${fn:contains(str,'B')}">
						<td  id="btn1" onmouseout="MM_swapBgImgRestore()" 
						onmouseover="MM_swapBgImage('btn1','','/images/device/btn_vnc_over.gif',1)" 
						background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onStartcnt();" 
						style="cursor:hand;background-repeat:no-repeat;">
						&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
						停止休眠
						</span></td>
				</c:if>
				--%>
				
						<c:if test="${fn:contains(str,'E')}">
						<td id="btn4" onmouseout="MM_swapBgImgRestore()" 
						onmouseover="MM_swapBgImage('btn4','','/images/device/btn_vnc_over.gif',1)" 
						background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:sendNotice();" 
						style="cursor:hand;background-repeat:no-repeat;">
						&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
						文字通知
						</span></td>
						</c:if>
						<!--
						
						<c:if test="${fn:contains(str,'F')}">
						<td id="btn5" onmouseout="MM_swapBgImgRestore()" 
						onmouseover="MM_swapBgImage('btn5','','/images/device/btn_vnc_over.gif',1)" 
						background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:clearPrograme();" 
						style="cursor:hand;background-repeat:no-repeat;">
						&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
						终端初始化
						</span></td>
						</c:if> 
						
						
						<c:if test="${fn:contains(str,'G')}">
						<td id="btn6" onmouseout="MM_swapBgImgRestore()" 
						onmouseover="MM_swapBgImage('btn6','','/images/device/btn_vnc_over.gif',1)" 
						background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:clientSynchronization();" 
						style="cursor:hand;background-repeat:no-repeat;">
						&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
						时间同步
						</span></td>
						</c:if> -->
						<c:if test="${fn:contains(str,'H')}">
						<td id="btn7" onmouseout="MM_swapBgImgRestore()" 
						onmouseover="MM_swapBgImage('btn7','','/images/device/btn_vnc_over.gif',1)" 
						background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:updatedownloadstartend();" 
						style="cursor:hand;background-repeat:no-repeat;">
						&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
						开关机设置
						</span></td>
						</c:if>
						<c:if test="${fn:contains(str,'C')}">
						<td id="btn2" onmouseout="MM_swapBgImgRestore()" 
						onmouseover="MM_swapBgImage('btn2','','/images/device/btn_vnc_over.gif',1)" 
						background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onRestart();" 
						style="cursor:hand;background-repeat:no-repeat;">
						&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
						其他设置
						</span></td>
						</c:if>
						<c:if test="${fn:contains(str,'I')}">
									<td id="btn8" onmouseout="MM_swapBgImgRestore()" 
									onmouseover="MM_swapBgImage('btn8','','/images/device/btn_vnc_over.gif',1)" 
									background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:deleteClient();" 
									style="cursor:hand;background-repeat:no-repeat;">
									&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
									删除终端
									</span></td>
								</c:if>
					<c:if test="${fn:contains(str,'D')}">
						<td id="btn3" onmouseout="MM_swapBgImgRestore()" 
						onmouseover="MM_swapBgImage('btn3','','/images/device/btn_vnc_over.gif',1)" 
						background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:onUpdateSoftware();" 
						style="cursor:hand;background-repeat:no-repeat;">
						&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
						软件升级
						</span></td>
				    </c:if>
							<%--<td id="btn9" onmouseout="MM_swapBgImgRestore()" 
									onmouseover="MM_swapBgImage('btn9','','/images/device/btn_vnc_over.gif',1)" 
									background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:ledManager();" 
									style="cursor:hand;background-repeat:no-repeat;">
									&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
									LED管理
									</span></td>
					  --%>
					  	<td id="btn9" onmouseout="MM_swapBgImgRestore()" 
						onmouseover="MM_swapBgImage('btn9','','/images/device/btn_vnc_over.gif',1)" 
						background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:RestartServices();" 
						style="cursor:hand;background-repeat:no-repeat;">
						&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
						重启服务
						</span>
						</td>
						<c:if test="${sessionScope.lg_user.lg_role==1}">
						  <td id="btn10" onmouseout="MM_swapBgImgRestore()" 
						onmouseover="MM_swapBgImage('btn10','','/images/device/btn_vnc_over.gif',1)" 
						background="/images/device/btn_vnc.gif" width="114" height="30" align="center"  onclick="javascript:allsetipport();" 
						style="cursor:hand;background-repeat:no-repeat;">
						&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
						配置终端
						</span>
						</td>
						</c:if>
					<td width="1" height="30" ></td>
					  
					  <td width="1" height="30" ></td>
		    </tr>
		  </table>
	  </td>
     
        </tr>
  </table>
   <table width="100%"  height="32" border="0" cellspacing="0" cellpadding="3" class="TitleBackgroundBg">
    <tr>
		<td  style="color: #4C9A2B;font-weight:bold;">&nbsp;&nbsp;
		<%if("ACTIVE".equals(title)){
			out.println("终端管理 > 终端状态 > 当前连接");
		}else if("INACTIVE".equals(title)){
			out.println("终端管理 > 终端状态 > 当前断开");
		}else if("ASC".equals(title)){
			out.println("终端管理 > 名称排序 > 升序排序");
		} else if("DESC".equals(title)){
			out.println("终端管理 > 名称排序 > 降序排序");
		} else if("ZU".equals(title)){
			out.println("终端管理 > 组管理 ");
		} else if("NOTAUDIT".equals(title)){
			out.println("终端管理 > 待审核终端");
		}else if("DORMANCY".equals(title)){
			out.println("终端管理 > 终端状态 > 当前休眠");
		}else{
			out.println("终端管理 > ");
		} 
		
		
		
		%></td>
		<!-- <td align="right" style="color: red;padding-right: 50px;font-weight: bold">当前版本只支持一台终端！</td> -->
        </tr>
      </table>  
     <table width="100%"  border="0" cellspacing="0" cellpadding="0" class="TitleBackgroundBg">
    <tr>
	  <td class="TabBackground">
	  <table border="0" cellspacing="0" cellpadding="0">
        <tr> 
        <%if("NOTAUDIT".equals(left_cmd)){ %>
            <td width="100" height="25" id="btn_list"  align="center"  background='/images/device/tab_01.gif'>列 表</td>
       
        <%}else if("LIST".equals(cmd)){ %>
          
		   <td width="100" height="25"  id="btn_list"  align="center" onclick="JavaScript:goList('LIST','');" background='/images/device/tab_01_over.gif'>
		   		<div style='cursor:hand'>列 表</div>
		   </td>
		    <td width="100" height="25" id="btn_monitoring" align="center" onclick="JavaScript:goList('MONITORING','');" background='/images/device/tab_01.gif'>
           		<div style='cursor:hand'>监 控</div>
           </td>
        <%}else{ %>
           
		   <td width="100" height="25"  id="btn_list"  align="center" onclick="JavaScript:goList('LIST','');" background='/images/device/tab_01.gif'>
		   		<div style='cursor:hand'>列 表</div>
		   </td>
		   <td width="100" height="25" id="btn_monitoring" align="center" onclick="JavaScript:goList('MONITORING','');" background='/images/device/tab_01_over.gif'>
           		<div style='cursor:hand'>监 控</div> 
           </td>
       <%} %>
        </tr>
      </table>
      </td>
      <td align="right">
     
       <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	          	<td><a href="javascript:;" style="color: #1C8EF6" title="下载VNC" onclick="downvnc()">远程桌面工具：VNC下载</a></td>
	          <!-- 	<td><a href="javascript:;" style="color: #1C8EF6" title="下载IE8-WindowsXP" onclick="downie8()">IE浏览器：IE8-WindowsXP下载</a></td> -->
	            <td align='right'><input type="text" id="terminalstr" name="terminalstr" size="30" value="<%=terminalstr%>" maxlength="100"  onkeypress="goKeypress();"></td>
	            <td>&nbsp;</td>
	 	        <td width="60" height="18" align='center' onclick="serchTerminal();" background='/images/device/btn_search_blk.gif' style='cursor:hand'>
	 	        <span style="font:7pt;color:white;" >
				搜 索
				</span>
	 	        </td>				
	          </tr>
	      </table></td>
    </tr>
  </table> 
</form>	
</body> 
<div style="display: none" ><iframe src="" scrolling="no" id="header_iframe" name="header_iframe" frameborder="0"></iframe></div>
</html>
