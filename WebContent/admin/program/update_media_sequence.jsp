<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO"/>
<%
String moduleid=request.getParameter("moduleid");
String mediaid=request.getParameter("mediaid");
String sequence=request.getParameter("sequence");
String order=request.getParameter("order"); 
ModuleDAO moduledao= new ModuleDAO();
moduledao.updatesequence(moduleid,Integer.parseInt(sequence),Integer.parseInt(mediaid),order);
request.getRequestDispatcher("/admin/program/addmediaList.jsp?moduleid="+moduleid+"&opp=0").forward(request,response);
%>
