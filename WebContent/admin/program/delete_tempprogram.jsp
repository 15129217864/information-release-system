<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<%
String programe_file=request.getParameter("programe_file");
//Util.deleteFile(FirstStartServlet.projectpath+"/admin/temp_program/"+programe_file);

%>
<script type="text/javascript">
<!--
window.location.href="/admin/program/programtop1.jsp";
//-->
</script>