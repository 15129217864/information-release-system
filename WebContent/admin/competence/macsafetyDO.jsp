<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.dao.UsersDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<%
String username=request.getParameter("username")==null?"":request.getParameter("username");
String mac1=request.getParameter("mac1").trim()==null?"":request.getParameter("mac1").trim().replace(" ","");
String mac2=request.getParameter("mac2").trim()==null?"":request.getParameter("mac2").trim().replace(" ","");
String mac3=request.getParameter("mac3").trim()==null?"":request.getParameter("mac3").trim().replace(" ","");

UtilDAO utildao= new UtilDAO();
UsersDAO userdao= new UsersDAO();
Users checkUser=userdao.getUserBystr("where lg_name='"+username+"'");

if("".equals(mac1)){
%>
<script type="text/javascript">
	<!--
	history.go(-1);
	alert("第一项不能为空！");
	//-->
</script>
<%
}else{
   String strtmp="";
   if(null!=checkUser.getLg_mac()&&!checkUser.getLg_mac().equals("")){
		List<String> list=Arrays.asList(checkUser.getLg_mac().split("#"));
		Set<String>set=new HashSet<String>(list);
		
		if(!"".equals(mac1))
		  set.add(mac1);
		
		if(!"".equals(mac2))
		  set.add(mac2);
		
		if(!"".equals(mac3))
		  set.add(mac3);

		Object []str=(Object [])set.toArray();
		for(int i=0,n=str.length;i<n;i++){
		  if(!str[i].equals(""))
		    strtmp+=str[i]+"#";
		}
	}else{
	   if(!"".equals(mac1))
		  strtmp+=mac1+"#";
		
		if(!"".equals(mac2))
		  strtmp+=mac2+"#";
		
		if(!"".equals(mac3))
		  strtmp+=mac3;
	}
	Map map= utildao.getMap();
	map.put("lg_name",username);
	map.put("lg_mac",strtmp);
	boolean bool=utildao.updateinfo(map,"xct_LG");
	if(bool){%>
		<script type="text/javascript">
		<!--
		parent.closedivframe();
		alert("添加MAC成功！");
		parent.location.href="/admin/competence/userList.jsp";
		//-->
		</script>
	<%}else{ %>
		<script type="text/javascript">
		<!--
		
		parent.closedivframe();
		alert("添加MAC失败！");
		//-->
		</script>
	<%}
	}%>
