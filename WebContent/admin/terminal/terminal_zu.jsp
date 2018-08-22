<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.*"%>
<%@page import="com.xct.cms.domin.Terminal"%>
<%
 Users user = (Users) request.getSession().getAttribute("lg_user");
 if(user==null){
 	response.sendRedirect("/index.jsp");
 }
 %>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma"  content="no-cache"/> 

<title></title>

<script language="javascript" src="/js/jquery-1.8.3.min.js"></script>
<script language="javascript" src="/js/vcommon.js"></script>
<link rel="stylesheet" href="/css/dtree.css" type="text/css" />
<script language="JavaScript" src="/js/dtree.js"></script>
<script language="JavaScript" src="/js/did_common.js"></script>

<script language="JavaScript" type="text/JavaScript">

	function goPageLink(zuid,url){
	    
		//fm1.zu_id.value=zuid;
		$("#zu_id").val(zuid);
		var conteturl=window.parent.parent.content.deviceContent.location.href;
		var cmd=conteturl.split("=")[1].split("&")[0];
		
		if(cmd=="NOTAUDIT"){
			parent.parent.content.deviceTop.location.href = "/admin/terminal/ter_header.jsp?cmd=MONITORING&left_cmd=ZU";
			cmd="MONITORING";
		}
		parent.parent.content.deviceTop.location.href = "/admin/terminal/ter_header.jsp?cmd="+cmd+"&left_cmd=ZU&title=ZU&zu_id="+zuid;
		parent.parent.content.deviceContent.location.href = "/rq/terminalList?cmd="+cmd+"&left_cmd=ZU&title=ZU&zu_id="+zuid;
	}
	
	function NotAudit(){
		//top.content.location.href = "/admin/terminal/index.jsp?left_cmd=NOTAUDIT&title=NOTAUDIT&cmd=NOTAUDIT";
		parent.parent.content.deviceTop.location.href = "/admin/terminal/ter_header.jsp?cmd=NOTAUDIT&left_cmd=NOTAUDIT&title=NOTAUDIT";
		parent.parent.content.deviceContent.location.href = "/rq/terminalList?cmd=NOTAUDIT&left_cmd=NOTAUDIT&title=NOTAUDIT";
	}
	function frefresh(){
	    parent.gosub(false);
	}
	function fcreate(){
		var selectzuid=fm1.zu_id.value;
		oppcategory("添加组",selectzuid,"add");
	}
	 
	function fdestory(){
		if(confirm("提示信息：子组和子组下面的所有终端也将一起删除，请慎重！确认删除？")){
			var selectzuid=fm1.zu_id.value;
			if(selectzuid==1){
				alert('由于该类别为根类别，因此无法进行删除。');
			}else{
				window.location.href="/admin/terminal/oppcatrgoryok.jsp?cmd=DELETE&zu_id="+selectzuid;
			}
		}
	}
	function fedit(){
		var selectzuid=fm1.zu_id.value;
		if(selectzuid==1){
			alert('由于该类别为根类别，因此无法进行编辑。');
		}else{
			oppcategory("编辑组",selectzuid,"update");
		}
	}
	function oppcategory(title,selectzuid,opptype){
		document.body.scrollTop = "0px";
		document.getElementById("div_iframe").src="/admin/terminal/oppcatrgory.jsp?zuid="+selectzuid+"&opptype="+opptype;
		document.getElementById("divframe").style.left="0px";
		document.getElementById("divframe").style.top="36px";
		document.getElementById("titlename").innerHTML=title;
		document.getElementById("divframe").style.display="block";
		document.getElementById("divframe").style.visibility='visible';
		document.getElementById("massage").style.visibility='visible';
	}
	
	function closedivframe(){
		document.getElementById("div_iframe").src="/loading.jsp";
		document.getElementById("divframe").style.display="none";
		document.getElementById("divframe").style.visibility="hidden";
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
.audcls:hover{text-decoration: underline; font-weight: bold;color: blue;}
.audcls{font-weight: bold;color: blue;}
</style>
</head>

<body leftmargin="0" topmargin="10" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('/images/leftmenu/content1_over.gif','/images/leftmenu/content2_over.gif','/images/leftmenu/content3_over.gif')" >
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
		<input type="hidden" name="zu_id"  id="zu_id" value="1"/>
		<input type="hidden" name="cmd" value="DELETE"/>
		<c:if test="${sessionScope.lg_user.lg_role==1}">
           <table width="150px" border="0" cellpadding="0" cellspacing="0" style="margin-bottom: 10px">
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
			<td>
           <script language="javascript">
				 device = new dTree('device'); 
				 var init_tree = null;
				 //if(init_tree == true)	
				    device.clearCookie();	
						  		
					<%
					String browser= session.getAttribute("browser").toString();
											
					Map<Integer,Terminal>  allzu=(Map<Integer,Terminal>)request.getAttribute("allterminal_zu");
					Terminal terminal = new Terminal();
					for (Map.Entry<Integer, Terminal> entry : allzu.entrySet()) {
						terminal=entry.getValue();
						//System.out.println(zu_username+"======="+zu_username.indexOf(user.getLg_name())+">>>>"+user.getLg_name()); 
						if(browser.equals("ie")||browser.equals("firefox")){%>														
							device.add('<%=terminal.getZu_id()%>','<%=terminal.getZu_pth()%>','<%=terminal.getZu_name()%>',"<% if(terminal.getZu_type()==1){out.print("JavaScript:ifrm_rms.goPageLink('"+terminal.getZu_id()+"','');");}else{out.print("JavaScript:;");}%>",'','menu','/images/dtreeimg/device_folder_close.gif');
						 <%}else{%>
						    device.add('<%=terminal.getZu_id()%>','<%=terminal.getZu_pth()%>','<%=terminal.getZu_name()%>',"<% if(terminal.getZu_type()==1){out.print("JavaScript:goPageLink('"+terminal.getZu_id()+"','');");}else{out.print("JavaScript:;");}%>",'','menu','/images/dtreeimg/device_folder_close.gif');
						 <%
						  }
					}
					%>				                    
						document.write(device);
						var selected_device = device.getSelected();
					    if(selected_device == null  || selected_device == "")
						   device.s(0);
		    </script>
			</td>
		</tr>
	</table>
</form>  

</body>
</html>
