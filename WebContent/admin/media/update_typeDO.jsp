<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>

<%
String mediaIds=request.getParameter("mediaIds");
 String groupid=request.getParameter("group_id")==null?"1":request.getParameter("group_id");
 
if(mediaIds!=null&&mediaIds.indexOf("!")>-1){
	String mediaids[] = mediaIds.split("!");
	UtilDAO utildao= new UtilDAO();
	DBConnection dbc= new DBConnection();
 	Connection con = dbc.getConection();
	for (int i = 0; i < mediaids.length; i++) {
		Map<String ,String> mediamap=utildao.getMap();
		mediamap.put("media_id",mediaids[i]);
		mediamap.put("zu_id",groupid);
		utildao.updateinfo(con,mediamap, "xct_media");
	}
	dbc.returnResources(con);
 %>
<script type="text/javascript">
parent.homeframe.content.deviceTop.content.location.reload();
parent.closedivframe(2);
alert("移动媒体成功！");
</script>

<%}else{
 %>
<script type="text/javascript">
parent.homeframe.content.deviceTop.content.location.reload();
parent.closedivframe(2);
alert("移动媒体失败！");
</script>
<%


}%>