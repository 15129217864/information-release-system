<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.UsersDAO"/>

<%
String username=request.getParameter("userids")==null?"":request.getParameter("userids");
Users user= new UsersDAO().getUserBystr(" where lg_name='"+username+"'");
request.setAttribute("user",user);

 %>
<html>
	<head>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="Pragma" content="no-cache" />
		<title>New Content</title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />

		<script language="JavaScript" type="text/JavaScript">
function updateUser(){
newpassword=myform.newpassword.value.replace(/\s/g,"");
renewpassword=myform.renewpassword.value.replace(/\s/g,"");
if(newpassword==""){
	alert("新密码不能为空！");
	return;
}
if(renewpassword==""){
	alert("确认新密码不能为空！");
	return;
}if(renewpassword!=newpassword){
	alert("两次密码不一致！");
	return;
}
if(checkPass(newpassword)<3){
    alert("密码复杂度不够，请重新设置！");
    myform.newpassword.focus();
     return  ;
}

myform.action="/admin/competence/resetPasswordDO.jsp";
myform.submit();
}
//密码复杂要求：
//1、长度大于8
// 2、密码必须是字母大写，字母小写，数字，特殊字符中任意三个组合。
function checkPass(s){
	  if(s.length < 6){
	      return 0;
	  }
	  var ls = 0;

	 if(s.match(/([a-z])+/)){
	     ls++;
	 }
	 if(s.match(/([0-9])+/)){
	     ls++;  
	 }
	 if(s.match(/([A-Z])+/)){
	     ls++;
	  }
	  if(s.match(/[^a-zA-Z0-9]+/)){
        ls++;
      }
	  return ls
}
</script>
	</head>

	<body>
		<form   method="post" name="myform" id="myform">
		<table width="300" height="200" border="0" cellpadding="0" cellspacing="0">
		<tr   class="TableTrBg06" >
					<td  width="40%" align="right" height="25px;" class="Bold">
						姓名：
					</td>
					<td  width="60%"  align="left"  class="Bold">
						${user.name}
					</td>
				</tr>
				<tr>
            <td class="Line_01" colspan="2"></td>
          </tr>
				<tr   class="TableTrBg06" >
					<td  width="40%" align="right" height="25px;" class="Bold">
						用户名：
					</td>
					<td  width="60%"  align="left"  class="Bold">
						<input type="hidden" name="username" value="${user.lg_name}"/>
						${user.lg_name }
					</td>
				</tr>
				<tr>
            <td class="Line_01" colspan="2"></td>
          </tr>
          
				<tr class="TableTrBg06">
					<td  align="right" height="25px;" class="Bold">
						新 密 码：
					</td>
					<td  align="left" class="Bold">
						<input type="password" name="newpassword" class="button" style="width: 120px" maxlength="15"/>
					</td>
				</tr>  
				<tr>
            <td class="Line_01" colspan="2"></td>
          </tr>
				<tr class="TableTrBg06">
					<td   align="right" height="25px;" class="Bold">
						确认新密码：
					</td>
					<td   align="left" class="Bold">
						<input type="password" name="renewpassword" class="button" style="width: 120px" maxlength="15"/>
					</td>
				</tr>  
				<tr>
            <td class="Line_01" colspan="2"></td>
          </tr>
				<tr class="TableTrBg06">
					<td colspan=2 align="center"  height="50px;" >
						&nbsp;&nbsp;&nbsp;<input type="button" value=" 修 改 " class="button1" onClick="updateUser()" />
						&nbsp;&nbsp;&nbsp;<input type="button" value=" 取 消 " class="button1" onclick="parent.closedivframe(1);" />
					</td>
				</tr>
			</table>

		</form>
	</body>
</html>
