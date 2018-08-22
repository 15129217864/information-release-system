<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
    <jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="java.util.List"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
Users user = (Users) request.getSession().getAttribute("lg_user");
if(null==user){
    return ;
}
Map<Integer,Terminal> allzu=TerminalDAO.getZuListByUsername(user.getLg_name());
String month=request.getParameter("month");
String type=request.getParameter("type");
String onedate=request.getParameter("onedate");
String twodate=request.getParameter("twodate");
String opp=request.getParameter("opp")==null?"0":request.getParameter("opp");
request.setAttribute("opp",opp);
TerminalDAO terminaldao= new TerminalDAO();
List<Terminal> terminalList=terminaldao.getSortList(terminaldao.getTerminalListByMap(allzu,"",""),"ASC");

Terminal t = allzu.get(1);
t.setZu_pth(-1);
allzu.put(1, t);
					
 %>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma"  content="no-cache"/> 
<title>Sliding Menu</title><script language="javascript" src="/js/vcommon.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<link rel="stylesheet" href="/css/dtree.css" type="text/css" />
<script language="JavaScript" src="/js/dtree.js"></script>
<script language="JavaScript" src="/js/did_common.js"></script>

<style>
BODY {
	font-size:9pt;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url('/images/menu_bg.gif');
}
.menu {
    cursor:hand;
	border:0px solid ;
	padding:7px 3px 3px 18px;
	background-image:URL('/images/slide_bg.gif');
	background-repeat:no-repeat;
	width:100%;
	height:25px;
	font-size:9pt;
	font-weight:bold;
	color:222222;
}

.menu2 {
    cursor:hand;
	border:0px solid ;
	padding:7px 3px 3px 2px;
	background-image:URL('/images/slide_bg2.gif');
	width:100%;
	height:25px;
	font-size:9pt;
	font-weight:bold;
	color:666666;
}

.menu3 {
    cursor:hand;
	border:0px solid ;
	padding:7px 3px 3px 2px;
	background-image:URL('/images/slide_bg3.gif');
	width:100%;
	height:25px;
	font-size:9pt;
	font-weight:bold;
	color:666666;
}
.submenu {
width:99%;
	padding-left:10px;

	
}
#a1{ font-size: 14px;font-weight: bold;}
#a2{ font-size: 12px}
</style>
<script type="text/javascript">
function goPageLink(zuid){
parent.content.location.href="/admin/schedule/index.jsp?type=<%=type%>&left_menu=ZU&zuid="+zuid+"&month=<%=month%>&onedate=<%=onedate%>&twodate=<%=twodate%>&openstatus=1";
parent.deviceTop.location.href="/admin/schedule/ter_header.jsp?month=<%=month%>&left_menu=ZU&zuid="+zuid+"&type=<%=type%>";
}
function goPageLink1(mac){
parent.content.location.href="/admin/schedule/index.jsp?type=<%=type%>&left_menu=CNT&cnt_mac="+mac+"&month=<%=month%>&onedate=<%=onedate%>&twodate=<%=twodate%>&openstatus=1";
parent.deviceTop.location.href="/admin/schedule/ter_header.jsp?month=<%=month%>&left_menu=CNT&cnt_mac="+mac+"&type=<%=type%>";
}
</script>
</head>
<body>
<form name=fm1 method="post">
<input type=hidden name="cmd"/>
<table width="100%"   cellpadding="0" cellspacing="0" border="0">
  <tr>
   <td height="30" background="/images/device/btn_background.gif" valign="middle" align="center">
   <span class="MenuTitle">
   时间表</span></td>
  </tr>
</table>

<div class="menu"><img src="/images/bullet.gif">&nbsp;&nbsp;<span id="nowyear"></span>&nbsp;&nbsp;所有终端</div>
<div id="sub1" class="submenu" >
		<table width="95%" border="0" height="100%" cellpadding="0" cellspacing="0">
			<!-- <tr><td>&nbsp;</td></tr> -->
		<tr>
			<td valign="top">
			<div style="border: 0px solid red;width:100%; height: 50px;overflow: auto" id="cnt_div_id">
           <script language="javascript">
				 device = new dTree('device'); 
				 var init_tree = null;
				<c:if test="${opp==0}">
				    device.clearCookie();	
				  </c:if>	
					<%
					Terminal terminal = new Terminal();
					for (Map.Entry<Integer, Terminal> entry : allzu.entrySet()) {
						terminal=entry.getValue();
						//System.out.println(zu_username+"======="+zu_username.indexOf(user.getLg_name())+">>>>"+user.getLg_name()); 
					%>														
						device.add('<%=terminal.getZu_id()%>','<%=terminal.getZu_pth()%>','<%=terminal.getZu_name()%>',"<% if(terminal.getZu_type()==1){out.print("JavaScript:goPageLink('"+terminal.getZu_id()+"');");}else{out.print("JavaScript:;");}%>",'','menu','/images/dtreeimg/device_folder_close.gif');
					<%
					}if(terminalList !=null)for(int i=0;i<terminalList.size();i++){
						Terminal ter=terminalList.get(i);
					%>
					device.add('<%=ter.getCnt_mac()%>','<%=ter.getCnt_zuid()%>','<%=ter.getCnt_name()%>',"JavaScript:goPageLink1('<%=ter.getCnt_mac()%>');",'','menu','/images/dtreeimg/base.gif');		
					<%}%>		                    
						document.write(device);
						var selected_device = device.getSelected();
					    if(selected_device == null  || selected_device == "")
						   device.s(0);
		    </script>
		    <div style="height: 30px"></div>
		    </div>
			</td>
		</tr>
	</table>
</div>
</form>
<script type="text/javascript">
document.getElementById("cnt_div_id").style.height=document.body.clientHeight-75;

</script>
</body>
</html>