<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ProgramAppDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String batch=request.getParameter("batch");
ProgramAppDAO programappdao= new ProgramAppDAO();
List plist= programappdao.getALLProgramAppByStr(" where  batch="+batch+" order by program_playdate ,program_playtime ");
int i=1;
if(plist!=null)i=plist.size();
if(i>5)i=5;
request.setAttribute("i",i);
request.setAttribute("plist", plist);
request.setAttribute("batch",batch);

%>
<html>
  <head>
    <title>My JSP 'showTerminal.jsp' starting page</title>
    <script language="javascript" src="/js/vcommon.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<style type="text/css">
		.a_link:hover{color: blue; text-decoration: underline;}
		</style>
  </head>
  
  <body>
  <table cellpadding="0" cellspacing="0" border="0" width="700px">
  	<tr  class="TitleBackground">
  		<td class="InfoTitle" width="30%">节目名称&nbsp;&nbsp;</td>
  		<td class="InfoTitle" width="20%">开始时间&nbsp;&nbsp;</td>
  		<td class="InfoTitle" width="20%">结束时间&nbsp;&nbsp;</td>
  		<td class="InfoTitle" width="10%">播放时长&nbsp;&nbsp;</td>
  		<td class="InfoTitle" width="10%">播放类型&nbsp;&nbsp;</td>
  		<td class="InfoTitle" width="10%">发送状态&nbsp;&nbsp;</td>
  	</tr>
  	<c:if test="${empty plist}">
  	<tr  >
  		<td align="center" colspan="6" height="30">暂无节目信息</td>
  	</tr>
  	
  	</c:if>
  	<tr  >
  		<td align="center" colspan="6" height="30">
  		<div style="width: 100%;height: ${i*35}px;overflow: auto">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<c:forEach var="programcla" items="${plist}">
   	<tr  class="TableTrBg23_" onmouseover="this.className='TableTrBg23'" onmouseout="this.className='TableTrBg23_'">
  		<td align="center"  width="30%"><a href="javascript:;" title="预览节目"  class="a_link" onclick="parent.viewProgram('${programcla.program_name }','${programcla.program_jmurl }','${programcla.templateid }',600,450);"><strong>${programcla.program_name }</strong></a></td>
  		<td align="center" width="20%">${programcla.program_playdate } ${programcla.program_playtime }</td>
  		<td align="center" width="20%">${programcla.program_enddate } ${programcla.program_endtime }</td>
  		<td align="center" width="10%">${programcla.program_playlong }分钟</td>
  		<td align="center" width="10%">${programcla.program_play_typeZh }</td>
  		<td align="center" width="10%">${programcla.program_app_statusZh }</td>
  	</tr>
  	<tr><td class="Line_01" colspan="6"></td></tr>
   </c:forEach>
			</table>
</div>
		</td>
  	</tr>
   <tr><td align="center" colspan="6"  height="30"><input type="button" class="button1" onclick="parent.closedivframe(1);" value=" 确 定 "/></td></tr>
     </table>
  </body>
</html>
