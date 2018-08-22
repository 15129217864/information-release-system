<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%
String t_id=request.getParameter("t_id");
String programe_file=request.getParameter("programe_file");
UtilDAO utildao = new UtilDAO();

utildao.deleteinfo("id",t_id,"xct_JMPZ_type");
request.getRequestDispatcher("/admin/program/selectclientIP.jsp?programe_file="+programe_file).forward(request,response);

%>
