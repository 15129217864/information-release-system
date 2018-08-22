<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>

<%
String programIds=request.getParameter("programIds");
 String zuid=request.getParameter("zuid")==null?"1":request.getParameter("zuid");
 String rqurl=request.getParameter("rqurl");
 request.setAttribute("rqurl",rqurl);
 
if(programIds!=null&&programIds.indexOf("!")>-1){
	String mediaids[] = programIds.split("!");
	UtilDAO utildao= new UtilDAO();
	DBConnection dbc= new DBConnection();
 	Connection con = dbc.getConection();
	for (int i = 0; i < mediaids.length; i++) {
		Map<String ,String> mediamap=utildao.getMap();
		mediamap.put("program_JMurl",mediaids[i]);
		mediamap.put("program_treeid",zuid);
		utildao.updateinfo(con,mediamap, "xct_JMPZ");
	}
	dbc.returnResources(con);
 %>
	<script type="text/javascript">
	parent.closedivframe(2);
	alert("移动节目成功！");
	
	//顶级首页(index.jsp)框架名称：parent.homeframe ；二级admin下的index.jsp框架名称：parent.homeframe.content
	//三级admin/program下的index.jsp框架名称：parent.homeframe.content.content
	parent.homeframe.content.content.location.href='${rqurl}';//从顶层一级级到programList.jsp
	</script>

<%}else{%>
	<script type="text/javascript">
	parent.closedivframe(2);
	alert("移动节目失败！");
	</script>
<%}%>

