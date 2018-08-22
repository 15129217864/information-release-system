<%@ page language="java" pageEncoding="gbk"%>
<%
request.getSession().invalidate();
response.sendRedirect("/");%>
