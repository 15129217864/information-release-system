<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.utils.DESPlusUtil"/>
<jsp:directive.page import="com.jspsmart.upload.SmartUpload"/>
<%

try{
String filename=request.getParameter("p")==null?"":request.getParameter("p").toString(); 
if(!filename.equals("")){
String filepath= FirstStartServlet.projectpath+DESPlusUtil.Get().decrypt(filename);
			SmartUpload su = new SmartUpload(); 
			su.initialize(pageContext); 
			su.setContentDisposition(null); 
			su.downloadFile(filepath); 
}
}catch(Exception e){
out.println("<script>alert('�����������Դ������');</script>");
}
out.clear(); out = pageContext.pushBody(); 
%>
<script>
window.opener=null;window.open('','_self','');window.close();
</script>