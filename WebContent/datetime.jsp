<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%
String nowtime=UtilDAO.getNowtime("yyyy-MM-dd_HH:mm:ss");
out.print(nowtime);
%>
