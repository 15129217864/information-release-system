<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>
<jsp:directive.page import="com.xct.cms.utils.Pager"/>
<jsp:directive.page import="com.xct.cms.utils.PageDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String programstr=UtilDAO.getGBK(request.getParameter("programstr"));
ProgramDAO programdao= new ProgramDAO();
List programList=programdao.getALLProgram(" and program_Name like '%"+programstr+"%'");
if(programList!=null&&programList.size()>0){
				int pagenum =Integer.parseInt(request.getParameter("pagenum")==null?"1":request.getParameter("pagenum"));
				Pager pager= new Pager(programList.size(),pagenum,10); 
				List list3= new PageDAO().getPageList(programList, pager.getCurrentPage(), pager.getPageSize());
				request.setAttribute("pager", pager);
				request.setAttribute("programList", list3);
			}
 %>
<html>
<head>
<title></title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
<script language="JavaScript" type="text/JavaScript">
function onCreateProgram(){
document.body.scrollTop = "0px";
document.getElementById("div_iframe").src="/admin/program/create_program.jsp";
document.getElementById("divframe").style.left=(document.body.clientWidth - 900) / 2 + "px";
document.getElementById("divframe").style.top="0px";
document.getElementById("titlename").innerHTML="新建节目";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}
function SetWinHeight(obj)
{ 
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
	if (win.contentDocument && win.contentDocument.body.offsetWidth){  
	win.width = win.contentDocument.body.offsetWidth;  
	}else if(win.Document && win.Document.body.scrollWidth){ 
    win.width = win.Document.body.scrollWidth; 
	}
  } 
 } 
}
function closedivframe(){
document.getElementById("div_iframe").src="/loading.jsp";
document.getElementById('divframe').style.visibility="hidden";
}
function all_chk(sel_all){
	var ckform = document.ifrm_Form;
  	var cbox   = ckform.checkbox;
  	if(cbox){
	    if(cbox.length){
	    	for(i=0;i<cbox.length;i++){
	    		ckform.checkbox[i].checked=sel_all;
	    	}
	    }else{
	    	ckform.checkbox.checked = sel_all;
	    }   
	}
}
function sendProgram(program_name,program_file){
parent.listtop.location.href="/admin/program/programtop2.jsp";
window.location.href="/admin/program/opprojecttype.jsp?program_file="+program_file+"&program_name="+program_name;
}
</script>
<style type="text/css">
#divframe {
	position: absolute;
	z-index: 999;
	filter: dropshadow(color = #666666, offx = 3, offy = 3, positive = 2);
	visibility: hidden
}

#mask {
	position: absolute;
	top: 0;
	left: 0;
	width: expression(body . scrollWidth);
	height: expression(body . scrollHeight);
	background: #000000;
	filter: ALPHA(opacity = 60);
	z-index: 1;
	visibility: hidden
}

#massage {
	border: #6699cc solid;
	border-width: 1 1 1 1;
	background: #fff;
	color: #036;
	font-size: 12px;
	line-height: 150%;
	visibility: hidden
}

.header {
	background: url(/images/device/btn_background.gif);
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	padding: 3 5 0 5;
	color: #fff
}
</style>

</head>
<body >
<form action="" name="ifrm_Form">
<!-- list -->
  <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="table1">
    <tr>
      <td>
      <c:if test="${empty requestScope.programList}">
<br/>
<center>未找到关键字“<span  style="color:red"><%=programstr %></span>”的节目！</center>
</c:if>
	<c:set var="i" value="0"></c:set>
	<c:set var="classnm"></c:set>
      <c:forEach var="programcla" items="${requestScope.programList}">
      <c:set var="i" value="${i+1}"></c:set>
      <c:choose>
      	<c:when test="${i%2==0}">
      		<c:set var="classnm" value="TableTrBg06"></c:set>
      	</c:when>
      	<c:otherwise>
      	<c:set var="classnm" value="TableTrBg05"></c:set>
      	</c:otherwise>
      </c:choose>
	   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0" >
        <tr class="${classnm }">
          <td width="10">&nbsp;</td> 
          <td width="15">
          <input type="checkbox" name="checkbox" value="${programcla.program_JMurl }" >
          </td>             
          <td width="120"><a href="javascript:;" onClick="javaScript:;"><strong>${programcla.program_Name }</strong></a></td>
          <td width="120">${programcla.program_JMurl }</td>
          <td width="150">${programcla.zu_name }</td>
          <td width="150">${programcla.program_SetDateTime }</td>
          <td width="150">${programcla.program_adduser }</td>  
           <td width="100">${programcla.program_adduser }</td>
           <td width="100">
				<input type="button" value="发送节目"  class="button" onclick="sendProgram('${programcla.program_Name }','${programcla.program_JMurl }');"/>
				

			</td>       
         </tr>
      </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="Line_01"></td>
          </tr>
        </table>
	  	</c:forEach>				       
      </td>
    </tr>
  </table>
</form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" valign="top"align="right">
                  <a href="/admin/program/searchPrograma.jsp?programstr=<%=programstr %>&pagenum=1"><img src="/images/btn_first.gif" border="0"/></a>&nbsp;
				  <a href="/admin/program/searchPrograma.jsp?programstr=<%=programstr %>&pagenum=${pager.currentPage-1 }"><img src="/images/btn_pre.gif" border="0"/></a>&nbsp; ${pager.currentPage }&nbsp; 
                  <a href="/admin/program/searchPrograma.jsp?programstr=<%=programstr %>&pagenum=${pager.currentPage+1 }"><img src="/images/btn_next.gif" border="0"/></a>&nbsp; 
                  <a href="/admin/program/searchPrograma.jsp?programstr=<%=programstr %>&pagenum=${pager.end }"><img src="/images/btn_end.gif" border="0"/></a>&nbsp;
                  共${pager.totalPage }页 
				</td>
			</tr>
		</table>
<div id="divframe">
			<div id="massage">
				<table cellpadding="0" cellspacing="0">
					<tr height="30px;" class=header>
						<td align="left" style="font-weight: bold">
							<span id="titlename"></span>
						</td>
						<td height="5px" align="right">
							<a href="javaScript:void(0);" style="color: #000000"
								onclick="closedivframe();">关闭</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<iframe src="/loading.jsp" scrolling="no" id="div_iframe"
								name="div_iframe" frameborder="0"
								onload="Javascript:SetWinHeight(this)"></iframe>
							<form name="ipforms">
								<input type="hidden" name="checkips" />
							</form>
						</td>
					</tr>
				</table>
			</div>
		</div>
</body>
</html>