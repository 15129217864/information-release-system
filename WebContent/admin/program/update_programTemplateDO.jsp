<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.dao.LogsDAO"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Program"/>

<%
	String jmurl=request.getParameter("jmurl");
	String jm_templateid=request.getParameter("jm_templateid");
	String templateid=request.getParameter("templateid");
	Users user = (Users) request.getSession().getAttribute("lg_user");
	DBConnection dbconn= new DBConnection();
	Connection conn=dbconn.getConection();
	ProgramDAO programdao= new ProgramDAO();
	LogsDAO logsdao= new LogsDAO();
	UtilDAO utildao= new UtilDAO();
	TemplateDAO templatedao= new TemplateDAO();
	Program p=programdao.getProgram1(conn," and program_JMurl='"+jmurl+"'");
	if(p!=null){
		//////////添加节目新模板
		String newtemplate_id=new StringBuffer().append("t.").append(UUID.randomUUID().toString()).toString();
		//System.out.println("templateid------------->"+templateid);
		templatedao.addTemplateToTempByTemplateid(conn,newtemplate_id,templateid,jm_templateid);
		
		//////////////////////更新节目新模板
		programdao.updateProgramTemplate(conn,jmurl,jm_templateid,newtemplate_id);
		//写入日志
		logsdao.addlogs1(conn,user.getLg_name(), "用户"+user.getLg_name()+"修改了节目组【"+p.getZu_name()+"】下面的节目【"+p.getProgram_Name()+"】的模板", 1);
		///////////////////////////////////////
		 dbconn.returnResources(conn);
	 %>
		<script>
			parent.div_iframe2.location.href="/admin/program/update_program.jsp?templatefile=<%=jmurl%>&opp=1&t=" + new Date().getTime();
			parent.closedivframe3();
			alert("节目模板修改成功！");
		</script>
	<%}else{
	///////////////////////////////////////
		dbconn.returnResources(conn);
	%>
	<script>
		parent.closedivframe3();
		alert("节目模板修改失败！");
	</script>
	<%} %>
