<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<%
String batchs=request.getParameter("batchs")==null?"!":request.getParameter("batchs");
UtilDAO utildao= new UtilDAO();
String batch_array[] = batchs.split("!");
DBConnection dbconn=new DBConnection();
Connection conn= dbconn.getConection();
for (int i = 0; i < batch_array.length; i++) {
	if(!"".equals(batch_array[i]))
	utildao.deleteinfo(conn,"batch",batch_array[i],"xct_JMApp");
}
dbconn.returnResources(conn);
%>
<script type="text/javascript">
<!--
alert("É¾³ý³É¹¦£¡");
parent.parent.content.location.reload();
//-->
</script>
