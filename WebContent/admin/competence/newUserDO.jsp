<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.utils.MD5"/>
<jsp:directive.page import="com.xct.cms.dao.UsersDAO"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>

<%
Users user= (Users)session.getAttribute("lg_user");
String username=request.getParameter("username")==null?"":request.getParameter("username");
String password=request.getParameter("password")==null?"":request.getParameter("password");

String name=Util.getGBK(request.getParameter("name"));
String email=Util.getGBK(request.getParameter("email"));

String role=request.getParameter("role")==null?"":request.getParameter("role");
String cnt_zuid=request.getParameter("cnt_zuid")==null?"!":request.getParameter("cnt_zuid");
String cnt_zuidprogram=request.getParameter("cnt_zuidrogram")==null?"!":request.getParameter("cnt_zuidrogram");
String []authoritys=request.getParameterValues("authority");
String authority="";
if(null!=authoritys){
	for(int i=0,n=authoritys.length;i<n;i++){
	  authority+=authoritys[i]+"#";
	}
}

//System.out.println(username+"__"+password+"__"+name+"__"+role+"__"+cnt_zuid+"__"+cnt_zuidprogram+"__"+authority);

UsersDAO userdao= new UsersDAO();
if(userdao.checkUser(username.trim())){%>
	<script type="text/javascript">
	<!--
	alert("用户名已存在，请重新输入用户名！");
	history.go(-1);
	//-->
	</script>
	<%return;
}

UtilDAO utildao= new UtilDAO();
MD5 md5= new MD5();
String md5password=md5.getMD5ByStr(password);
Map map= utildao.getMap();
map.put("lg_name",username);
map.put("lg_password",md5password);
map.put("name",name);
map.put("email",email);
map.put("last_login_time","");
map.put("lg_role",role);
map.put("lg_authority",authority);//操作权限
boolean bool=utildao.saveinfo(map,"xct_LG");

String[] cnt_zuids=cnt_zuid.split("!");
TerminalDAO terminaldao= new TerminalDAO();
for(int i=0;i<cnt_zuids.length;i++){
	terminaldao.updateZuUserName(cnt_zuids[i],username);
}
String [] cnt_zuidprograms=cnt_zuidprogram.split("!");
for(int i=0;i<cnt_zuidprograms.length;i++){
	terminaldao.updateZuUserName(cnt_zuidprograms[i],username);
}

if(bool){%>
	<script type="text/javascript">
	<!--
	parent.closedivframe(1);

	if(parent.homeframe.content.content.location){
		parent.homeframe.content.content.location.href=parent.homeframe.content.content.location;
	}
	alert("新建用户成功！");
	//-->
	</script>
<%}else{ %>
	<script type="text/javascript">
	<!--
	parent.closedivframe(1);
	alert("新建用户失败！");
	//-->
	</script>
<%}%>
