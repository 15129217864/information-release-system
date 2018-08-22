<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO" />
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<jsp:directive.page import="java.util.Map"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    Users user = (Users) request.getSession().getAttribute("lg_user");
    if(null==user){
	     return ;
	 }
	Map<Integer,Terminal> allzu=TerminalDAO.getZuListByUsername1("2",user.getLg_name());
	Terminal terminal = allzu.get(1); 
	terminal.setZu_pth(-1);
	allzu.put(1, terminal);
%>
<html>
	<head>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="Pragma" content="no-cache" />
		<link rel="stylesheet" href="/css/dtree.css" type="text/css" />
	<script language="javascript" src="/js/vcommon.js"></script>
		<script language="JavaScript" src="/js/dtree.js"></script>
		<script language="JavaScript" src="/js/did_common.js"></script>
		<script language="JavaScript"> 
<!-- program_zu.jsp
function onLoad(){
	//parent.slide('sub2');
}
function fcreate(){
var selectzuid=fm1.zu_id.value;
oppcategory("添加组",selectzuid,"add");
}
 
function fdestory(){
var selectzuid=fm1.zu_id.value;
var zu_pth=fm1.zu_pth.value;
if(zu_pth==-1){
alert('由于该类别为根类别，因此无法进行删除。');
return;
}
if(confirm("提示信息：子组和子组下面的所有节目也将一起删除，请慎重！确认删除？")){
		window.location.href="/admin/program/oppcatrgoryok.jsp?cmd=DELETE&zu_id="+selectzuid;
}
}
function fedit(){
var selectzuid=fm1.zu_id.value;
var zu_pth=fm1.zu_pth.value;
if(zu_pth==-1){
alert('由于该类别为根类别，因此无法进行编辑。');
}else{
oppcategory("编辑组",selectzuid,"update");
}
}
function frefresh(){
	window.location.href="/admin/program/program_zu.jsp";
}
function goPageLink(zuid,zu_pth){	
	fm1.zu_id.value=zuid;
	fm1.zu_pth.value=zu_pth;
	parent.parent.content.location.href = "/admin/program/index.jsp?left_menu=PROGRAM_ZU&zu_id="+zuid;
}
function oppcategory(title,selectzuid,opptype){
	document.body.scrollTop = "0px";
	document.getElementById("div_iframe").src="/admin/program/oppcatrgory.jsp?zuid="+selectzuid+"&opptype="+opptype;
	document.getElementById("divframe").style.left="0px";
	document.getElementById("divframe").style.top="50px";
	document.getElementById("titlename").innerHTML=title;
	document.getElementById('divframe').style.display="block";
	document.getElementById("divframe").style.visibility='visible';
	document.getElementById("massage").style.visibility='visible';

}

function closedivframe(){
document.getElementById("div_iframe").src="/loading.jsp";
document.getElementById('divframe').style.display="none";
document.getElementById('divframe').style.visibility="hidden";
}
 
//-->
</script>
		<style type="text/css">
#divframe {
	position: absolute;
	width:200px;
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
	<body leftmargin="0" topmargin="10" marginwidth="0" marginheight="0">
	<div id="divframe">
			<div id="massage">
				<table cellpadding="0" cellspacing="0">
					<tr height="20px;" class=header>
						<td align="center" style="font-weight: bold">
							<span id="titlename"></span>
						</td>
					</tr>
					<tr>
						<td>
							<iframe src="/loading.jsp" scrolling="no" id="div_iframe"
								name="div_iframe" frameborder="0" width="100%" height="80px"></iframe>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<form name="fm1" method="POST">
			<input type="hidden" name="zu_id" value="1"/>
			<input type="hidden" name="zu_pth" value="-1"/>
			<input type="hidden" name="cmd" value="DELETE" />
			<c:if test="${sessionScope.lg_user.lg_role==1}">
			<table width="150" border="0" cellpadding="0" cellspacing="0"  style="margin-bottom: 10px">
				<tr>
					<td width="25" height="25">
						<a href="javascript:frefresh();" onMouseOut="MM_swapImgRestore()"
							onMouseOver="MM_swapImage('schedule1','','/images/leftmenu/content1_over.gif',1)"><img
								src="/images/leftmenu/content1_on.gif" name="schedule1"
								width="25" height="25" border="0" title="刷新"> </a>
					</td>
					<td width="25" height="25">
						<a href="javascript:fcreate();" onMouseOut="MM_swapImgRestore()"
							onMouseOver="MM_swapImage('schedule2','','/images/leftmenu/schedule2_over.gif',1)"><img
								src="/images/leftmenu/schedule2_on.gif" name="schedule2"
								width="25" height="25" border="0" title="添加"> </a>
					</td>
					<td width="25" height="25">
						<a href="javascript:fdestory();" onMouseOut="MM_swapImgRestore()"
							onMouseOver="MM_swapImage('schedule3','','/images/leftmenu/schedule3_over.gif',1)"><img
								src="/images/leftmenu/schedule3_on.gif" name="schedule3"
								width="25" height="25" border="0" title="删除"> </a>
					</td>
					<td width="25" height="25">
						<a href="javascript:fedit();" onMouseOut="MM_swapImgRestore()"
							onMouseOver="MM_swapImage('schedule4','','/images/leftmenu/folder_modify_over.gif',1)"><img
								src="/images/leftmenu/folder_modify_on.gif" name="schedule4"
								width="25" height="25" border="0" title="编辑"> </a>
					</td>
					<td width="25" height="25">
						&nbsp;
					</td>
				</tr>
			</table>
          </c:if>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="5">
						&nbsp;
					</td>
					<td>

						<script language="javascript">
		
						content = new dTree('content'); 
						var init_tree = null;
						//if(init_tree == true)	
						   content.clearCookie();	
						 <%if("1".equals(user.getLg_role())){%>
						   content.add('1',-1,"我的文件夹","javascript:goPageLink(1,-1);",'','','/images/dtreeimg/folder.gif');
						   <%}else{%>
						    content.add('1',-1,"我的文件夹","javascript:;",'','','/images/dtreeimg/folder.gif');
						   <%}%>
						<%								
					for (Map.Entry<Integer, Terminal> entry : allzu.entrySet()) {
						terminal=entry.getValue();
					%>														
						content.add('<%=terminal.getZu_id()%>','<%=terminal.getZu_pth()%>','<%=terminal.getZu_name()%>',"<% if(terminal.getZu_type()==1){out.print("javascript:goPageLink('"+terminal.getZu_id()+"','"+terminal.getZu_pth()+"');");}else{out.print("JavaScript:alert('您无权查看该节目组的节目！');");}%>",'','','/images/dtreeimg/folder.gif');
					<%
					}
					%>				                    
						document.write(content);
						var selected_device = content.getSelected();
					    if(selected_device == null  || selected_device == "")
						   content.s(2);
						   
		   				 </script>
					</td>
				</tr>
			</table>
		</form>

		
	</body>
</html>

