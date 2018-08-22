<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Program"/>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	    String programe_file=request.getParameter("programe_file");//选择要发送的节目名称
	    String opp=request.getParameter("opp")==null?"0":request.getParameter("opp");
	    String programefile[] = programe_file.split("!"); 
	    //getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory.getAllIpAddress(" where (cnt_islink=1 or cnt_islink=2) and cnt_status=1 "),"tree_"+username+".xml");//生成带复选框的树状菜单
	    ProgramDAO prodap= new ProgramDAO();
		Map<String,List<Program>> programmap= prodap.getProgram2(programefile);
		request.setAttribute("programmap",programmap);
		request.setAttribute("programe_file",programe_file);
		String nowdatetime=UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
		request.setAttribute("nowdate",nowdatetime.split(" ")[0]);
		request.setAttribute("nowtime",nowdatetime.split(" ")[1]);
		request.setAttribute("opp",opp);
%>
<html>
	<head>
		<title>节目配置</title>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<link rel="STYLESHEET" type="text/css" href="/admin/checkboxtree/dhtmlxtree.css">
		<link rel="stylesheet" href="/admin/checkboxtree/style.css" type="text/css" media="screen" />
		<script language="JavaScript" src="/admin/checkboxtree/dhtmlxcommon.js"></script>
		<script language="JavaScript" src="/admin/checkboxtree/dhtmlxtree.js"></script>
		<script language="javascript" src="/js/vcommon.js"></script>
		<script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<script src="/js/jquery1.4.2.js" type="text/javascript"></script>
        <style type="text/css">
			.parent		{background-color:F0F5F9;background-image:url('/images/title_background.gif'); background-repeat:repeat-X; height:30;}  /* 偶数行样式*/
			.odd		{ background-color:F0F5F9;}  /* 奇数行样式*/
			.selected		{ color:#fff;}
        </style>
       

		<script type="text/javascript">
		       // $(function(){
				//	$('tr.parent').click(function(){   // 获取所谓的父行
				//			$(this)
				//				.toggleClass("selected")   // 添加/删除高亮
				//				.siblings('.child_'+this.id).toggle();  // 隐藏/显示所谓的子行
				//	}).click();
				//})
function getStrCount(scrstr,armstr)
{ //scrstr 源字符串 armstr 特殊字符
 var count=0;
 while(scrstr.indexOf(armstr) >=1 )
 {
    scrstr = scrstr.replace(armstr,"")
    count++;    
 }
 return count;
}

function checkProgram(isold,program_type){
	if(getStrCount(program_type,"2")>1){
		alert("提示信息：一次只能发送一个【插播】节目！");
		return;
	}
	if(isold==1){
		alert("提示信息：节目列表中有时间过期的节目，请修改过期节目的播放时间！");
		return;
	}
	 progressShow()
	projectform.action="/admin/program/selectclientIP5DO.jsp";

	projectform.submit();

}

function closedivframe(){
if(confirm("提示信息：确认取消导出节目？")){
	parent.closedivframe(2);
}
}
function closedivframe1(){
	parent.closedivframe(2);
}



function progressBarOpen(){
  			var cWidth = document.body.clientWidth;
  			var cHeight= document.body.clientHeight;
			var divNode = document.createElement( 'div' );	
			divNode.setAttribute("id", "systemWorking");
			var topPx=(cHeight)*0.4-50;
			var defaultLeft=(cWidth)*0.4-50;
			divNode.style.cssText='position:absolute;margin:0;display:none;top:'+topPx+'px;left:'+defaultLeft+';width:215;height:59;background-image:url(/images/wait_background.gif);z-index:9999;text-align:center;padding-top:20'; 
			divNode.innerHTML= "<img src='/images/mid_giallo.gif' align=absmiddle><font style='font-size:12px'>&nbsp;正在导出节目，请稍后...</font>";
			document.getElementsByTagName("body")[0].appendChild(divNode);
			 var bgObj=document.getElementById("bgDiv");
  }
   function progressShow() {
			   if(document.all.systemWorking)
				  document.all.systemWorking.style.display = "block";
				  document.getElementById("send_button_id").disabled="disabled";
			   }   
			
			   function progressBarHidden() {
			    if(document.all.systemWorking)
				  document.all.systemWorking.style.display = "none";
				  document.getElementById("send_button_id").disabled="";
			   }
			   
    </script>
	</head>
	<body onload="progressBarOpen();">
					<form id="project" name="projectform" method="post"  action="">
						<input type="hidden" name="allips" value="" />
						<input type="hidden" name="templateid" value="${templateid}" />
						<input type="hidden" name="programe_file" value="${programe_file }"/>
				       <table border="0" width="100%" height="400px" cellpadding="0" cellspacing="0">
				       <tr>
							 <td valign="top">
										<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th width="25%" height="30px">节目名</th>
								<th width="12%">播放类型</th>
								<th width="25%">播放日期</th>
								<th width="19%">播放时间</th>
								<th width="9%">时长</th>
								<th width="10%">操作</th>
								</tr>
						</thead>
						<tr>
							<td colspan="6">
								<div style="width:100%;height: 400px;  overflow: auto">
									<table border="0" width="100%" cellpadding="0" cellspacing="0">

									<c:if test="${not empty programmap}">
												<tbody>
												    <c:set var="i" value="0"/>
												    <c:set var="isold" value="0"></c:set>
												    <c:set var="all_program_type" value=""/>
											   	    <c:forEach items="${programmap}" var="program">
														<tr class="parent" id="row_<c:out value='${i+1}'/>" >
														<td colspan="6" height="25px">※&nbsp;<b>${program.key}</b></td></tr>
														<c:if test="${empty program.value}">
															<tr><td colspan="6" height="25px" style="padding-left: 50px">该节目未设置播放属性，默认播放属性为：永久循环！</td></tr>
														</c:if>
														
														    <c:forEach items="${program.value}" var="ppp">
																<tr class="child_row_<c:out value='${i+1}'/>">
																	<td  align="center"  width="25%" height="25px">${program.key}</td>
																	<td  align="center"  width="12%">${ppp.play_type_Zh }</td> 
																	 <c:set var="all_program_type" value="${all_program_type},${ppp.play_type}"/>
																	<c:choose>
																	  <c:when test="${ppp.play_type==4}">
																	     <td  width="25%" align="center" >全年</td>
																	  </c:when>
																	  <c:when test="${ppp.play_type==2}">
																	     <td  width="25%" align="center" >当前时间</td>
																	  </c:when>
																	  <c:otherwise>
																	  		<c:if test="${nowdate>ppp.play_end_time}">
																	  			<c:set var="isold" value="1"></c:set>
																	  		</c:if>
																	     	<td  width="25%" align="center" ${nowdate>ppp.play_end_time?'style="color:red" title="时间过期"':''} >${ppp.play_start_time} ～ ${ppp.play_end_time }</td>
																	  </c:otherwise>
																	</c:choose>
																	<c:choose>
																	  <c:when test="${ppp.play_type==1||ppp.play_type==4}">
																	     <td  width="19%" align="center" >全天</td>
																	  </c:when>
																	   <c:when test="${ppp.play_type==2}">
																	     <td  width="19%" align="center" >当前时间</td>
																	  </c:when>
																	  <c:otherwise>
																	 	 <c:if test="${nowdate==ppp.play_end_time&&nowtime>ppp.day_etime1}">
																	  			<c:set var="isold" value="1"></c:set>
																	  		</c:if>
																	  <td  width="19%" align="center"  ${nowdate==ppp.play_end_time&&nowtime>ppp.day_etime1?'style="color:red" title="时间过期"':''}>${ppp.day_stime1} - ${ppp.day_etime1}</td>
																	  </c:otherwise>
																	</c:choose>
																	
																	<td  width="9%" align="center" >${ppp.play_count }分钟</td>
																	
																</tr>
															</c:forEach>
												
														 <c:set var="i" value="${i+1}"/>
													</c:forEach>
												</tbody>
										</c:if>
									</table>
								</div>
							</td>
							</table>
						  </td>
						</tr>
						<tr>
			            <td class="Line_01"></td>
			          </tr>
						 <tr>
							<td align="center"  height="50px"  >
							<table width="100%" border="0">
								<tr>
									<td width="220" align="right" style="padding-right: 50px;display: none;"><br><br>导出&nbsp;&nbsp;<input type="text" name="e_date" value="30"  style="width: 30px;height: 20;pxime-mode:Disabled" onkeypress="return event.keyCode>=48&&event.keyCode<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" maxlength="3" />&nbsp;天节目<br><br><br><br></td>
									<td  align="center"><input type="button" id="send_button_id" class="button1" onclick="checkProgram('${isold}','${all_program_type}');" value= " 导 出 " />&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" class="button1" onclick="closedivframe();" value=" 取 消 " /></td>
								</tr>
								<tr>
									<td width="220" align="center" style="padding-right: 50px;color: green"><strong style="color:red">注意: </strong>请把导出的节目压缩包放到U盘xct_data文件夹下；Android终端需要解压节目到U盘xct_data文件夹下</td>
								</tr>
							</table>
						  </td>
						</tr>
				         </table> 
			      </form> 
  
	</body>
	<c:if test="${opp=='5'}">
	<script type="text/javascript">
		alert("导出节目成功");
	window.location.href="/admin/program/download_zm.jsp";
	</script>
	</c:if>
	
</html>
