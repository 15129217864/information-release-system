<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<%
Connection conn= new  DBConnection().getConection();
out.println(conn);
%>

