<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<%
if(null!=request.getSession().getAttribute("lg_user")){
	out.println(((Users)request.getSession().getAttribute("lg_user")).getLg_name());
	out.println(request.getSession().getMaxInactiveInterval());
	out.println("<br/>");
	long aa=request.getSession().getCreationTime();
	Date   date=new   Date(aa); 
	out.println("����ʱ�䣺"+date);
	out.println("<br/>");
	Date   date1=new   Date(request.getSession().getLastAccessedTime()); 
	out.println("���һ�η���ʱ�䣺"+date1);
	out.println("<br/>");
	out.println("SESSION���ʱ�䣺"+request.getSession().getMaxInactiveInterval()/60+"����");
}
%>