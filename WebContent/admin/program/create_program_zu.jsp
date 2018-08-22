<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
    <%@page import="java.util.*"%>
<%@page import="com.xct.cms.dao.TerminalDAO"%>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    Users user=(Users)request.getSession().getAttribute("lg_user");
	if(null==user){
	    return ;
	}
	Map<Integer,Terminal> allzu=TerminalDAO.getZuListByUsername1("2",user.getLg_name());
	Terminal terminal = allzu.get(1); 
	terminal.setZu_pth(-1);
	allzu.put(1, terminal);
    request.setAttribute("allzu",allzu);
%>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma"  content="no-cache"/> 
<link rel="stylesheet" href="/css/dtree.css" type="text/css" />
</head>

<body bottommargin="0" topmargin="0" leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0" bgcolor="white">
<script language="JavaScript" src="/js/dtree.js"></script>
<script language="JavaScript">
<!--//
function selectCategory(groupId,zuname){
	parent.document.getElementById("zuid").value=groupId;
	parent.document.getElementById("zuname").value=zuname;
	parent.closeSubIFLayer();
}
//-->
</script>
<table cellpadding="5" cellspacing="0" cellpadding="10" border="0" width="100%">
<tr>
	<td>
    <%//--Tree Menu--//%>					
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="5">&nbsp;</td>
			<td>
            <script language="javascript">
content = new dTree('content'); 
var init_tree = null;
if(init_tree == true)	content.clearCookie();	
content.add('1',-1,"我的文件夹","javascript:;",'','','/images/dtreeimg/folder.gif');
<%								
	for (Map.Entry<Integer, Terminal> entry : allzu.entrySet()) { //
		terminal=entry.getValue();
%>														
	    content.add('<%=terminal.getZu_id()%>','<%=terminal.getZu_pth()%>','<%=terminal.getZu_name()%>',"<% if(terminal.getZu_type()==1){out.print("javascript:selectCategory('"+terminal.getZu_id()+"','"+terminal.getZu_name()+"');");}else{out.print("JavaScript:alert('您无权在该节目组下新建节目！');");}%>",'','','/images/dtreeimg/folder.gif');
<%
	}
%>

 document.write(content); 
 var selected_content = content.getSelected();
 if(selected_content == null  || selected_content == "")
    content.s(0);			                    
							 
		    </script>
			</td>
		</tr>
	</table>
	</td>
	
</tr>
</table>

</body>
<c:if test="${empty allzu}">
	<script language="javascript">
		alert("暂无文件组，请联系管理员分配节目组！");
	</script>
</c:if>
</html>

