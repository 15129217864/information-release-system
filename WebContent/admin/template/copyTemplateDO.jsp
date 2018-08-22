<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<jsp:directive.page import="java.net.URLDecoder"/>

<%
String templateids=request.getParameter("templateid");
 //String zuid=request.getParameter("zuid")==null?"1":request.getParameter("zuid");
String newtemplatename=request.getParameter("templatename")==null?"新复制模版":request.getParameter("templatename");

String rqurl=request.getParameter("rqurl");
request.setAttribute("rqurl",rqurl);

try {
	newtemplatename=URLDecoder.decode(URLDecoder.decode(newtemplatename,"UTF-8"),"UTF-8");
} catch (Exception e) {
	e.printStackTrace();
}

if(templateids!=null&&templateids.indexOf("!")>-1){
	TemplateDAO templatedao=new TemplateDAO();
	String templateidsarray[] = templateids.split("!");
	for (int i = 0; i < templateidsarray.length; i++) {
		if(!templateidsarray[i].equals("")){
		   templatedao.copyTemplateB(newtemplatename,templateidsarray[i]);
		}
	}
 %>
	<script type="text/javascript">
	parent.closedivframe(2);
	alert("复制模版成功！");
	parent.homeframe.content.content.location.href='${rqurl}';
	</script>

<%}else{%>
	<script type="text/javascript">
	parent.closedivframe(2);
	alert("复制模版失败！");
	</script>
<%}%>

