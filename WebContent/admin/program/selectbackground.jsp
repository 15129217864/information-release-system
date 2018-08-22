<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
    <%@page import="java.util.*"%>
<%@page import="com.xct.cms.dao.TerminalDAO"%>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
String meidaType=request.getParameter("meidaType")==null?"NO":request.getParameter("meidaType");	
String type=request.getParameter("type")==null?"NO":request.getParameter("type");
String templateid=request.getParameter("templateid");

TerminalDAO terminaldao= new TerminalDAO();
Users user=(Users)request.getSession().getAttribute("lg_user");
String str="and (zu_username ='"+user.getLg_name()+"||' or is_share=1)";
if("1".equals(user.getLg_role())){
	str="";
}
List meida_zu=terminaldao.getAllZu(" where zu_type=1 "+str+" and zu_name <>'sys_text' order by is_share  ");
request.setAttribute("meida_zu", meida_zu);
request.setAttribute("meidaType", meidaType);
request.setAttribute("type", type);
request.setAttribute("templateid", templateid);
%>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma"  content="no-cache"/> 
<link rel="stylesheet" href="/css/dtree.css" type="text/css" />
	<style type="text/css">
	.template_div{width: 100%;height: 290;; border: #6699cc 0 solid;  padding-top: 0px; padding-left: 0px;  overflow:auto ;}
	</style>
</head>

<body bottommargin="0" topmargin="0" leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0" bgcolor="white">
<script language="JavaScript" src="/js/dtree.js"></script>
<script language="JavaScript">
<!--//

function selectCategory(groupId){
	media_ifram.location.href="/admin/program/selectbackground1.jsp?zuid="+groupId+"&meidaType=${meidaType }&sstype=${type}&templateid=${templateid}";
}
//-->
</script>
<table cellpadding="5" cellspacing="0" cellpadding="10" height="100%"  border="0" width="100%">
<tr>
	<td valign="top" width="40%">
    <%//--Tree Menu--//%>					
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="5">&nbsp;</td>
			<td>
			<div class="template_div" id="template_div">
            <script language="javascript">
content = new dTree('content'); 
var init_tree = null;
if(init_tree == true)content.clearCookie();	
content.add('1',-1,"我的文件夹","javascript:selectCategory('1');",'','','/images/dtreeimg/folder.gif');
<c:set var="sessionlogname" value="${sessionScope.lg_user.lg_name}||"></c:set>
							<c:forEach var="mediazu" items="${meida_zu}">
							<c:set var="title_str" value=""></c:set>
						<c:choose>
							<c:when test="${mediazu.zu_username==sessionlogname}">
								<c:set var="title_str" value="我的${mediazu.is_share==1?'共享':''}文件夹"></c:set>
							</c:when>
							<c:otherwise>
								<c:set var="title_str" value="用户【${fn:replace(mediazu.zu_username, '||', '')}】创建的${mediazu.is_share==1?'共享':'私有'}文件夹"></c:set>
							</c:otherwise>
						</c:choose>
content.add('${mediazu.zu_id}','${mediazu.zu_pth}','<span onclick="selectCategory(${mediazu.zu_id})" title=${title_str}>${mediazu.zu_name}</span>',"javascript:;",'','menu','/images/dtreeimg/folder${mediazu.is_share==1?'1':''}.gif');
</c:forEach> 



content.add('sys0',-1,"系统文件夹","javascript:;",'','','/images/dtreeimg/top_folder.gif');
<c:if test="${meidaType=='IMAGE'}">
content.add('sys_1','sys0','图片',"javascript:selectCategory('${meidaType}');",'','','/images/dtreeimg/top_folder.gif');
</c:if>
<c:if test="${meidaType=='SOUND'}">
content.add('sys_2','sys0','声音',"javascript:selectCategory('${meidaType}');",'','','/images/dtreeimg/top_folder.gif');
</c:if>
 document.write(content); 
 var selected_content = content.getSelected();
if(selected_content == null  || selected_content == "")
content.s(0);		 
		    </script>
		    </div>
			</td>
		</tr>
	</table>
	</td>
	<td  width="60%" valign="top">
	<fieldset  style="width: 200px; height: 290px; border: #6699cc 1 solid; ">
		<iframe src="/admin/program/selectbackground1.jsp?zuid=1&meidaType=${meidaType}&sstype=${type}&templateid=${templateid}" scrolling="no" width="100%" height="285"   name="media_ifram" id="media_ifram" frameborder="0"></iframe>
	</fieldset>
	</td>
</tr>
</table>
</body>
</html>