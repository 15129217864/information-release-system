<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
    <%@page import="java.util.*"%>
    <%@page import="com.xct.cms.domin.Terminal"%>
<%@page import="com.xct.cms.dao.TerminalDAO"%>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma"  content="no-cache"/> 
<link rel="stylesheet" href="/css/dtree.css" type="text/css" />
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
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
				if(init_tree == true)	
				   content.clearCookie();	

				<%								
					Map<Integer,Terminal>  allzu=(Map<Integer,Terminal>)request.getAttribute("allterminal_zu");
					Terminal terminal = new Terminal();
					for (Map.Entry<Integer, Terminal> entry : allzu.entrySet()) {
						terminal=entry.getValue();
						int zu_id=terminal.getZu_id();
						String zu_path= new TerminalDAO().getzu_pathByzuID(allzu,zu_id);
						//System.out.println(zu_username+"======="+zu_username.indexOf(user.getLg_name())+">>>>"+user.getLg_name()); 
					%>														
						 content.add('<%=terminal.getZu_id()%>','<%=terminal.getZu_pth()%>','<%=terminal.getZu_name()%>',"<%=terminal.getZu_id()==1?"javascript:;":"javascript:selectCategory('"+terminal.getZu_id()+"','"+zu_path+"');"%>",'','','/images/dtreeimg/folder.gif');
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
</html>

