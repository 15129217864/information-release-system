<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
    <%@page import="java.util.*"%>
    <%@page import="com.xct.cms.domin.Terminal"%>
<%@page import="com.xct.cms.dao.TerminalDAO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

function selectCategory(groupId, category){
	parent.selectCategory(groupId,category);
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
var init_tree = null;
//if(init_tree == true)	
content.clearCookie();	
content.add('1',-1,"我的文件夹","javascript:;",'','','/images/dtreeimg/folder.gif');	
<c:forEach var="mediazu" items="${meida_zu}">
content.add('${mediazu.zu_id}','${mediazu.zu_pth}','${mediazu.zu_name}',"JavaScript:selectCategory('${mediazu.zu_id}',' >${mediazu.zu_name}');",'','','/images/dtreeimg/folder${mediazu.is_share==1?'1':''}.gif');
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
<c:if test="${empty meida_zu}">
	<script language="javascript">
		alert("暂无文件类别，请在左边“类别”栏目里添加文件类别！");
	</script>
</c:if>
</html>

