<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
    <%@page import="java.util.*"%>
<%@page import="com.xct.cms.dao.TerminalDAO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String paneType=request.getParameter("paneType")==null?"":request.getParameter("paneType");
TerminalDAO terminaldao= new TerminalDAO();
List meida_zu=terminaldao.getAllZu("where zu_type=1");
request.setAttribute("meida_zu", meida_zu);
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

function selectCategory(groupId){
	parent.media_ifram.location.href="/admin/program/mediaList.jsp?zuid="+groupId+"&paneType=<%=paneType%>";
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
content.add('1',-1,"Ä¬ÈÏ×é","javascript:selectCategory('1');",'','','/images/dtreeimg/folder.gif');
<c:forEach var="mediazu" items="${meida_zu}">
content.add('${mediazu.zu_id}','${mediazu.zu_pth}','${mediazu.zu_name}',"javascript:selectCategory('${mediazu.zu_id}');",'','','/images/dtreeimg/folder.gif');
</c:forEach>
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
</html>

