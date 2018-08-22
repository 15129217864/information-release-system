<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>

<%
String userids=request.getParameter("userids");
UtilDAO utildao= new UtilDAO();
 DBConnection dbc= new DBConnection();
 Connection con = dbc.getConection();
String useridarray[] = userids.split("!");
for (int i = 1; i < useridarray.length; i++) {
	utildao.deleteinfo(con,"lg_name",useridarray[i],"xct_LG");
	utildao.upeateinfo(con,"zu_username=replace(zu_username,'||"+useridarray[i]+"', '')","1=1","xct_zu");
}
dbc.returnResources(con);
%>
<script type="text/javascript">
<!--
alert("删除用户成功！");
parent.parent.content.location.href="/admin/competence/userList.jsp";
//-->
</script>
