<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.servlet.SessionListener"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%
List<Users> onlineList=new ArrayList<Users>();
          for(Map.Entry<String,Users>entry:SessionListener.hUserNamemap.entrySet()){
				onlineList.add(entry.getValue());
		}
		//UsersDAO userdao= new UsersDAO();
		//List<Users> list=userdao.getAllUsers(" WHERE (CHARINDEX(lg_name, '"+online_user+"') <> 0)");
request.setAttribute("userlist",onlineList);
           %>  
<html> 
	<head>
		<meta http-equiv="Cache-Control" content="no-cache" /> 
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="Pragma" content="no-cache" />
		<title>在线用户数</title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
	</head>

	<body>
		<form   method="post" name="myform" id="myform">
		<table width="400" height="200" border="0" cellpadding="0" cellspacing="0" class="TitleBackground">
		<tr  >
					<td  width="33%" class="InfoTitle" height="25px;">
						姓名
					</td>
					<td  width="33%"  align="center"  class="InfoTitle">
						登陆IP
					</td>
					<td  width="33%"  align="center"  class="InfoTitle">
						登陆时间
					</td>
				</tr>
				<tr>
            <td class="Line_01" colspan="3"></td>
          </tr>
           <c:forEach var="user" items="${requestScope.userlist}">
           	<tr   class="TableTrBg22" >
					<td  width="33%" align="center" height="20px;" class="Bold">
						${user.name }
					</td>
					<td  width="33%" align="center" height="20px;" class="Bold">
						${user.login_ip }
					</td>
					<td  width="33%"  align="center"  class="Bold">
						${user.last_login_time }
					</td>
				</tr>
				<tr>
            <td class="Line_01" colspan="3"></td>
           </c:forEach>
          
				
          </tr>
				
				<tr class="TableTrBg06">
					<td colspan=3 align="center"  height="50px;" >
						&nbsp;&nbsp;&nbsp;<input type="button" value=" 关 闭 " class="button1" onclick="parent.closedivframe(1);" />
					</td>
				</tr>
			</table>

		</form>
	</body>
</html>
