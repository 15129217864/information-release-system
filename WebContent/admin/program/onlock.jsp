<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%
String jmurl=request.getParameter("jmurl");
UtilDAO utildao= new UtilDAO();
Map map=UtilDAO.getMap();
map.put("program_JMurl",jmurl);
map.put("program_ISloop","0");
utildao.updateinfo(map,"xct_JMPZ");
%>
<script type="text/javascript">
parent.location.reload();
</script>