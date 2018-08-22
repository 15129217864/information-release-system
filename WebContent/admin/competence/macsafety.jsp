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
			function addMac(){
				myform.action="/admin/competence/macsafetyDO.jsp";
				myform.submit();
			}
		</script>
	</head>
	<body>
		<form   method="post" name="myform" id="myform">
		<table width="300" height="200" border="0" cellpadding="0" cellspacing="0">
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
						MAC地址1：
					</td>
					<td  align="left" class="Bold">
						<input type="text" name="mac1" class="button" style="width: 120px" maxlength="17"/>
					</td>
				</tr>
				<tr>
            <td class="Line_01" colspan="2"></td>
          </tr>
				<tr class="TableTrBg06">
					<td  align="right" height="25px;" class="Bold">
						MAC地址2：
					</td>
					<td  align="left" class="Bold">
						<input type="text" name="mac2" class="button" style="width: 120px" maxlength="17"/>
					</td>
				</tr>  
				<tr>
            <td class="Line_01" colspan="2"></td>
          </tr>
				<tr class="TableTrBg06">
					<td   align="right" height="25px;" class="Bold">
						MAC地址3：
					</td>
					<td   align="left" class="Bold">
						<input type="text" name="mac3" class="button" style="width: 120px" maxlength="17"/>
					</td>
				</tr>  
				<tr>
            <td class="Line_01" colspan="2"></td>
          </tr>
				<tr class="TableTrBg06">
					<td colspan=2 align="center"  height="50px;" >
						&nbsp;&nbsp;&nbsp;<input type="button" value=" 添 加 " class="button" onClick="addMac()" />
						&nbsp;&nbsp;&nbsp;<input type="button" value=" 取 消 " class="button" onclick="javascript:parent.closedivframe();" />
					</td>
				</tr>
			</table>

		</form>
	</body>
</html>
