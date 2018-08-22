<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<%@page import="java.util.*"%>
<%@page import="com.xct.cms.domin.Terminal"%>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma"  content="no-cache"/> 
<title></title><script language="javascript" src="/js/vcommon.js"></script>
<link rel="stylesheet" href="/css/dtree.css" type="text/css" />
<script language="JavaScript" src="/js/dtree.js"></script>
<script language="JavaScript" src="/js/did_common.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function goPageLink(zuid,url){	
	top.content.location.href = "/admin/terminal/index.jsp?left_cmd=ZU&title=ZU&zu_id="+zuid;
}
function NotAudit(){	
	top.content.location.href = "/admin/terminal/index.jsp?left_cmd=NOTAUDIT&title=NOTAUDIT&cmd=NOTAUDIT";
}

function frefresh(){
 parent.gosub(false);
}
//-->
</script>
</head>

<body leftmargin="0" topmargin="10" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('/images/leftmenu/content1_over.gif','/images/leftmenu/content2_over.gif','/images/leftmenu/content3_over.gif')" >
<form  name="treeForm"  id="treeForm" method="POST">
    <%//--Tree Menu--//%>					
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
   <script language="javascript">
						  		device = new dTree('device'); 
						  		device.clearCookie();
						  		device.add('0',-1,'´ýÉóºËÖÕ¶Ë £¨${NotAuditnum}£©',"JavaScript:ifrm_rms.NotAudit();",'','menu','');
							<%								
							List allzu=(List)request.getAttribute("allterminal_zu");
							for(int i=0;i<allzu.size();i++){
									Terminal terminal   = (Terminal)allzu.get(i);
									
										if(i==0){%>	
											device.add('1',-1,'Ä¬ÈÏ×é',"JavaScript:ifrm_rms.goPageLink('1','');",'','menu','');
										<%}else{ %>										
											device.add('<%=terminal.getZu_id()%>','<%=terminal.getZu_pth()%>','<%=terminal.getZu_name()%>',"JavaScript:ifrm_rms.goPageLink('<%=terminal.getZu_id()%>','');",'','menu','/images/dtreeimg/device_folder_close.gif');
								<%}
									}%>
				                    
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
