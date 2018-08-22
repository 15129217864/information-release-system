<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%
String programe_file=request.getParameter("programe_file");
UtilDAO utildao= new UtilDAO();
String resip[] = programe_file.split("!");
for (int i = 1; i < resip.length; i++) {
	utildao.deleteinfo("template_id",resip[i],"xct_template");
	utildao.deleteinfo("template_id", resip[i], "xct_module");
}

%>
<script type="text/javascript">
<!--
alert("É¾³ýÄ£°å³É¹¦£¡");
parent.parent.content.location.href="/admin/program/templateList.jsp";
//-->
</script>
