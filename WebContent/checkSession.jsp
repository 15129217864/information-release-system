<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
 <% 
 Users user = (Users) request.getSession().getAttribute("lg_user");
if (user == null) {%>
<script type="text/javascript">
<!--
alert("SESSION过期，请重新登录！");
parent.parent.parent.parent.location.href="/index.jsp";
//-->
</script>
<%return;}%>
