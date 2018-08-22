<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.dao.LogsDAO"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.domin.Template"/>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<%
String programe_file=request.getParameter("programe_file");
Users user = (Users) request.getSession().getAttribute("lg_user");
String rqurl=request.getParameter("rqurl");
UtilDAO utildao= new UtilDAO();
LogsDAO logsdao= new LogsDAO();
DBConnection dbc=new DBConnection();
Connection conn= dbc.getConection();
String resip[] = programe_file.split("!");
TemplateDAO tdao= new TemplateDAO();
String del_str="";
for (int i = 1; i < resip.length; i++) {
	String t_id=resip[i];
	Template t=tdao.getTemplateById(conn,t_id);
	if (t!=null){
		utildao.deleteinfo(conn,"template_id",t_id,"xct_template");
		utildao.deleteinfo(conn,"template_id", t_id, "xct_module");
		del_str+="【"+t.getTemplate_name()+"】；";
	}
}
logsdao.addlogs1(conn,user.getName(), "用户【"+user.getName()+"】删除了模板"+del_str, 1);
dbc.returnResources(conn);
%>
<script type="text/javascript">
	//parent.parent.content.location.reload();
	parent.parent.content.location.href="<%=rqurl%>";
	alert("删除模板成功！");
</script>
