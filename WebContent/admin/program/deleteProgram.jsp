<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.LogsDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>

<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="com.xct.cms.domin.Program"/>
<%
String programe_file=request.getParameter("programe_file");
String rqurl=request.getParameter("rqurl");
LogsDAO logsdao= new LogsDAO();
ProgramDAO programdao= new ProgramDAO();
Users user = (Users) request.getSession().getAttribute("lg_user");
String resip[] = programe_file.split("!");
DBConnection dbc=new DBConnection();
Connection conn= dbc.getConection();
//UtilDAO utildao= new UtilDAO();
String del_error_program="";
String del_str="";
for (int i = 1; i < resip.length; i++) {
	String p_id=resip[i];
	Program p=programdao.getProgram1(conn," and program_JMurl='"+p_id+"'");
	if(p!=null){
			if(programdao.deleteinfo(conn,p_id)){
			   Util.deleteFile(FirstStartServlet.projectpath+"/serverftp/program_file/"+p_id);
			   del_str+="【"+p.getZu_name()+"组下面的节目："+p.getProgram_Name()+"】；";
			}
		//}else{
		//	del_error_program+=p.getProgram_Name()+"、";
		//}
	}
}
logsdao.addlogs1(conn,user.getName(), "用户【"+user.getLg_name()+"】删除了"+del_str, 1);
dbc.returnResources(conn);
if(!"".equals(del_error_program)){
%>
<script type="text/javascript">
<!--
alert("[<%=del_error_program.substring(0,del_error_program.length()-1) %>] 不是您所创建，您无权删除！");
parent.parent.content.location.href="<%=rqurl%>";
//-->
</script>
<%
}else{
%>
<script type="text/javascript">
<!--
alert("删除节目成功！");
parent.parent.content.location.href="<%=rqurl%>";
//-->
</script>
<%}%>