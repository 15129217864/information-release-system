<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.utils.DESPlusUtil"/>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String templateid = request.getParameter("templateid");
String zuid = request.getParameter("zuid");

String programtitle=request.getParameter("programe");
String programName = DESPlusUtil.Get().encrypt(UtilDAO.getGBK(programtitle));

String zunametitle=request.getParameter("zuname");
String zuname = DESPlusUtil.Get().encrypt(UtilDAO.getGBK(zunametitle));

if(System.getProperty("ISENGLNISHSYS").equals("YES")){
   programName = DESPlusUtil.Get().encrypt(programtitle);
   zuname = DESPlusUtil.Get().encrypt(zunametitle);
}

UtilDAO utildao= new UtilDAO();
TemplateDAO templatedao= new TemplateDAO();
String newtemplate_id=new StringBuffer().append("t.").append(UUID.randomUUID().toString()).toString();
templatedao.addTemplateToTempByTemplateid(newtemplate_id,templateid);
%>
<script type="text/javascript">
<!--
window.location.href="/admin/program/create_program.jsp?templateid=<%=newtemplate_id%>&programe=<%=programName%>&zuid=<%=zuid%>&zuname=<%=zuname%>";
//-->
</script>
