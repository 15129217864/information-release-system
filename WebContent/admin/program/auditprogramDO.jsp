<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<%
String batchs=request.getParameter("batchs")==null?"!":request.getParameter("batchs");
String xct_app_status=request.getParameter("program_app_status")==null?"0":request.getParameter("program_app_status");
UtilDAO utildao= new UtilDAO();
String batch_array[] = batchs.split("!");
DBConnection dbconn=new DBConnection();
Connection conn= dbconn.getConection();
for (int i = 0; i < batch_array.length; i++) {
	if(!"".equals(batch_array[i]))
	utildao.upeateinfo(conn,"xct_app_status="+xct_app_status,"batch='"+batch_array[i]+"'","xct_JMApp");
}
dbconn.returnResources(conn);
%>
<script type="text/javascript">
<!--
alert("审核节目成功！");
parent.parent.content.location.reload();
//-->
</script>