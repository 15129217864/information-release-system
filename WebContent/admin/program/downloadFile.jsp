<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<%
String filename=request.getParameter("filename")==null?"":request.getParameter("filename").toString(); 
if(!filename.equals("")){
String logfilepath= FirstStartServlet.projectpath+"admin/temp_program"+filename;
Util.downloadBigFile(logfilepath,request,response);
out.clear();
out = pageContext.pushBody();
}
%>
