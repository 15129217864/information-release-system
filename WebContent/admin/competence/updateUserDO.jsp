<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<%
String username=request.getParameter("username")==null?"":request.getParameter("username");
String email=Util.getGBK(request.getParameter("email"));
String name=Util.getGBK(request.getParameter("name"));
String role=request.getParameter("role")==null?"":request.getParameter("role");
String cnt_zuid=request.getParameter("cnt_zuid")==null?"!":request.getParameter("cnt_zuid");
String program_zuid=request.getParameter("program_zuid")==null?"!":request.getParameter("program_zuid");
String old_cnt_zuid=request.getParameter("old_cnt_zuid")==null?"!":request.getParameter("old_cnt_zuid");
String []authoritys=request.getParameterValues("authority");
String authority="";
if(null!=authoritys){
	for(int i=0,n=authoritys.length;i<n;i++){
	  authority+=authoritys[i]+"#";
	}
}
UtilDAO utildao= new UtilDAO();
Map map= utildao.getMap();
map.put("lg_name",username);
map.put("lg_role",role);
map.put("name",name);
map.put("email",email);
map.put("lg_authority",authority);//操作权限
boolean bool=utildao.updateinfo(map,"xct_LG");

String[] cnt_zuids=cnt_zuid.split("!");
TerminalDAO terminaldao= new TerminalDAO();
terminaldao.clearUserName(username);
for(int i=0;i<cnt_zuids.length;i++){
	terminaldao.updateZuUserName(cnt_zuids[i],username);
}
/////////////////////
String[] program_zuids=program_zuid.split("!");
terminaldao.clearProgreamUserName(username);
for(int i=0;i<program_zuids.length;i++){
	if(!"".equals(program_zuids[i])){
	terminaldao.updateZuUserName(program_zuids[i],username);
	}
}

if(bool){%>
<script type="text/javascript">
<!--

parent.closedivframe(1);
if(parent.homeframe.content.content.location){

parent.homeframe.content.content.location.href=parent.homeframe.content.content.location;
}
alert("修改用户成功！");
//-->
</script>
<%}else{ %>
<script type="text/javascript">
<!--

parent.closedivframe(1);
alert("修改用户失败！");
//-->
</script>


<%}%>
