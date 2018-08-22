<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%
String t_id=request.getParameter("t_id");
String programe_file=request.getParameter("programe_file");
String templateid=request.getParameter("templateid");
	String program_name = request.getParameter("program_name");
UtilDAO utildao = new UtilDAO();

utildao.deleteinfo("id",t_id,"xct_JMPZ_type");
request.setAttribute("deltype", "delok");
request.getRequestDispatcher("/admin/program/opprojecttype.jsp?program_file="+programe_file+"&program_name="+program_name+"&templateid="+templateid).forward(request,response);

%>
