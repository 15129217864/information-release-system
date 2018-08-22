<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.utils.MD5"/>
<jsp:directive.page import="com.xct.cms.dao.UsersDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>

 <link rel="stylesheet" href="/css/style.css" type="text/css">
<%
String username=request.getParameter("username")==null?"":request.getParameter("username");
String password=request.getParameter("password")==null?"":request.getParameter("password").replace(" ","");
String newpassword=request.getParameter("newpassword")==null?"":request.getParameter("newpassword").replace(" ","");
String renewpassword=request.getParameter("renewpassword")==null?"":request.getParameter("renewpassword").replace(" ","");
UtilDAO utildao= new UtilDAO();
UsersDAO userdao= new UsersDAO();
MD5 md5= new MD5();
String md5password=md5.getMD5ByStr(password);
String md5newpassword=md5.getMD5ByStr(newpassword);
Users checkUser=userdao.getUserBystr("where lg_name='"+username+"' and lg_password ='"+md5password+"'");
if("".equals(password) || "".equals(newpassword) ||"".equals(renewpassword) ){
%>

<script type="text/javascript">
<!--

history.go(-1);
alert("密码项不能为空！");
//-->
</script>
<%
return;
}
if(checkUser==null){
%>
<script type="text/javascript">
<!--

history.go(-1);
alert("原密码错误，请确认！");
//-->
</script>
<%
return;
}
if(!newpassword.equals(renewpassword)){
%>
<script type="text/javascript">
<!--

history.go(-1);
alert("两次密码不一致！");
//-->
</script>
<%
return;
}
Map map= utildao.getMap();
map.put("lg_name",username);
map.put("lg_password",md5newpassword);
boolean bool=utildao.updateinfo(map,"xct_LG");
if(bool){%>
<script type="text/javascript">
<!--

parent.closedivframe(1);
alert("密码修改成功！");
//-->
</script>
<%}else{ %>
<script type="text/javascript">
<!--

parent.closedivframe(1);
alert("密码修改失败！");
//-->
</script>


<%}%>
