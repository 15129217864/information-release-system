<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.jspsmart.upload.SmartUpload"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<%
try{
String filepath= FirstStartServlet.projectpath+"vlc-wxp.rar";
	SmartUpload su = new SmartUpload(); 
			su.initialize(pageContext); 
			su.setContentDisposition(null); 
			su.downloadFile(filepath); 
}catch(Exception e){
out.println("<script>alert('您所请求的资源不存在');</script>");
}
out.clear(); out = pageContext.pushBody(); 
%>
<script>
window.opener=null;window.open('','_self','');window.close();
</script>