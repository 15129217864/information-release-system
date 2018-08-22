<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.LogsDAO"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<%
String logdates=request.getParameter("logdates");
String left_menu=request.getParameter("left_menu");
String logtype=request.getParameter("logtype");
LogsDAO logsdao= new  LogsDAO();
DBConnection dbcon= new DBConnection();
Connection con =dbcon.getConection();

String logdatearray[] = logdates.split("!");
for (int i = 1; i < logdatearray.length; i++) {
logsdao.deleteLogs(con," where logtype="+logtype+" and logdate='"+logdatearray[i]+"'");
}
dbcon.returnResources(con);
%>
<script type="text/javascript">
<!--
alert("删除日志成功！");
parent.parent.content.location.href="/admin/sysmanager/logdate.jsp?left_menu=<%=left_menu %>&logtype=<%=logtype %>";
//-->
</script>
