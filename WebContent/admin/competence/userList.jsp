<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.xct.cms.dao.UsersDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.utils.Pager"/>
<jsp:directive.page import="com.xct.cms.utils.PageDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String left_menu=request.getParameter("left_menu");
String str="";
if("TITLE_ASC".equals(left_menu)){
str="order by lg_name";
}else if("TITLE_DESC".equals(left_menu)){
str="order by lg_name desc";
}
List<Users> usereList= new UsersDAO().getAllUsers(str);
if(usereList!=null&&usereList.size()>0){
				int pagenum =Integer.parseInt(request.getParameter("pagenum")==null?"1":request.getParameter("pagenum"));
				Pager pager= new Pager(usereList.size(),pagenum,20); 
				List list3= new PageDAO().getPageList(usereList, pager.getCurrentPage(), pager.getPageSize());
				request.setAttribute("pager", pager);
				request.setAttribute("userList",list3);
			}
request.setAttribute("left_menu",left_menu);


%>

<html>
  <head>
    <title>My JSP 'userList.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>
    <link rel="stylesheet" href="/css/style.css" type="text/css" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
function closedivframe(){
document.getElementById("div_iframe").src="/loading.jsp";
document.getElementById('divframe').style.visibility="hidden";
}
function onAddUser(){
showDivFrame("新建用户","/admin/competence/newUser.jsp","415","450");
}
function showDivFrame(title,url,fwidth,fheight){
	        parent.parent.parent.document.body.scrollTop = "0px";
			parent.parent.parent.document.getElementById("div_iframe1").width=fwidth;
			parent.parent.parent.document.getElementById("div_iframe1").height=fheight;
			parent.parent.parent.document.getElementById("divframe1").style.left=(parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";;
			parent.parent.parent.document.getElementById("divframe1").style.top=(parent.parent.parent.document.body.clientHeight - fheight) / 3+"px";
			parent.parent.parent.document.getElementById("div_iframe1").src=url;
			parent.parent.parent.document.getElementById("titlename1").innerHTML=title;
			parent.parent.parent.document.getElementById("divframe1").style.display='block';
			parent.parent.parent.document.getElementById("massage1").style.display='block';
			parent.parent.parent.document.getElementById("bgDiv").style.visibility='visible';
}
function onUpdateUser(userid){
showDivFrame("修改用户权限","/admin/competence/updateUser.jsp?userids="+userid,"400","450");
}
function onUpdatePassword(userid){
showDivFrame("修改用户密码","/admin/competence/resetPassword.jsp?userids="+userid,"300","200");
}
function onMacSafety(userid){
showDivFrame("MAC安全认证","/admin/competence/macsafety.jsp?userids="+userid,"300","200");
}

function all_chk(sel_all){
	var ckform = document.ifrm_Form;
  	var cbox   = ckform.checkbox;
  	if(cbox){
	    if(cbox.length){
	    	for(i=0;i<cbox.length;i++){
	    		ckform.checkbox[i].checked=sel_all;
	    	}
	    }else{
	    	ckform.checkbox.checked = sel_all;
	    }   
	}
}
function gopage(left_menu,pagenum){
window.location.href="/admin/competence/userList.jsp?left_menu="+left_menu+"&pagenum="+pagenum;
}
</script>
		<style type="text/css">
#divframe {
	position: absolute;
	z-index: 999;
	filter: dropshadow(color = #666666, offx = 3, offy = 3, positive = 2);
	visibility: hidden
}

#mask {
	position: absolute;
	top: 0;
	left: 0;
	width: expression(body . scrollWidth);
	height: expression(body . scrollHeight);
	background: #000000;
	filter: ALPHA(opacity = 60);
	z-index: 1;
	visibility: hidden
}

#massage {
	border: #6699cc solid;
	border-width: 1 1 1 1;
	background: #fff;
	color: #036;
	font-size: 12px;
	line-height: 150%;
	visibility: hidden
}

.header {
	background: url(/images/device/btn_background.gif);
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	padding: 3 5 0 5;
	
	cursor: move;
}
</style>

  </head>
  
  <body>
   	<form action="" name="ifrm_Form">
<!-- list -->
  <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="table1">
    <tr>
      <td>
      <c:if test="${empty requestScope.userList}">
<br/>
<center>暂无用户！</center>
</c:if>
      <c:forEach var="user" items="${requestScope.userList}">
	  <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0" >
        <tr  onmouseover="this.className='TableTrBgF0'" onmouseout="this.className=''" height="35" >
		      <td width="5%"  align="center">&nbsp;<c:if test="${user.lg_name!='888' }"><input type="checkbox" name="checkbox" value="${user.lg_name }" ></c:if></td>
		      <td width="10%" align="center" >&nbsp;${user.name }</td>
		      <td width="20%" align="center" >&nbsp;${user.lg_name }</td>
		      <td width="20%" align="center" >&nbsp;${user.email }</td>
		      <td width="15%" align="center" >&nbsp;${user.last_login_time }</td>
		      <td width="10%" align="center">&nbsp;${user.lg_roleZh }</td>
		      <td width="20%" align="center" >
			  	   <!-- <input type="button" class="button" value="MAC安全认证" onclick="onMacSafety('${user.lg_name }')" />&nbsp;&nbsp;&nbsp; -->
				<input type="button" class="button" ${user.lg_role==1?'disabled':'' }  value=" 修改用户 " onclick="onUpdateUser('${user.lg_name }')" />&nbsp;&nbsp;
				   <input type="button" class="button" value=" 密码重置 " onclick="onUpdatePassword('${user.lg_name }')" />
			   </td>
         </tr>
      </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="Line_01"></td>
          </tr>
        </table>
	  	</c:forEach>				       
      </td>
    </tr>
  </table>
</form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" valign="top"align="right">
                  <a href="javascript:;" onclick="gopage('${left_menu}',1);return false;" ><img src="/images/btn_first.gif" border="0"/></a>&nbsp;
				  <a href="javascript:;" onclick="gopage('${left_menu}',${pager.currentPage-1 });return false;"><img src="/images/btn_pre.gif" border="0"/></a>&nbsp; ${pager.currentPage }&nbsp; 
                  <a href="javascript:;" onclick="gopage('${left_menu}',${pager.currentPage+1 });return false;"><img src="/images/btn_next.gif" border="0"/></a>&nbsp; 
                  <a href="javascript:;" onclick="gopage('${left_menu}',${pager.end });return false;"><img src="/images/btn_end.gif" border="0"/></a>&nbsp;
                  共${pager.totalPage }页 
				</td>
			</tr>
</table>
<div id="divframe">
			<div id="massage">
				<table cellpadding="0" cellspacing="0">
					<tr height="30px;" class=header  onmousedown=MDown(divframe)>
						<td align="left" style="font-weight: bold">
							<span id="titlename"></span>
						</td>
						<td height="5px" align="right">
							<a href="javaScript:void(0);" style="color: #000000"
								onclick="closedivframe();">关闭</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<iframe src="/loading.jsp" scrolling="no" id="div_iframe"
								name="div_iframe" frameborder="0"
								></iframe>
						</td>
					</tr>
				</table>
			</div>
		</div>
  </body>
</html>
