<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
 
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>多媒体信息发布系统</title>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
	</head>
	<body style="margin: 0px">
		<table   id="tableid" border="0" height="100%" cellpadding="0" cellspacing="0">
			<tr>
			    <td colspan="2"  height="68px" >
			       <IFRAME  scrolling="no" height="68px"  frameBorder="0" src="/admin/header.jsp" 
			       name="topframe" id="topframeid" ></IFRAME>
				</td>
			</tr>
			<tr>
				<td>  	
					<IFRAME scrolling="no" width="100%"  height="100%" frameBorder="0" src="/admin/left.jsp"
						marginheight="0" marginwidth="0" name="menu" id="menuid" ></IFRAME>
				</td>
				<td>
					<IFRAME   height="100%" scrolling="no" frameBorder="0"
						src="/admin/terminal/index.jsp"
						marginheight="0" marginwidth="0" id="contentid" name="content" ></IFRAME>
				</td>
			</tr>
		</table>
	</body>
	
</html>
  
<SCRIPT LANGUAGE="JavaScript"> 
var cnt_width=document.body.clientWidth;
var left_width=216;
if(cnt_width<=1024){
left_width=150;
cnt_width=1020;
}
document.getElementById("menuid").width=left_width;
document.getElementById("tableid").width=cnt_width;
document.getElementById("topframeid").width=cnt_width;
document.getElementById("contentid").width=cnt_width-left_width;

</SCRIPT> 
  <c:if test="${not empty loginedinfo}">
   <script type="text/javascript" src="/js/ymPrompt.js"></script>
	<link rel="stylesheet" id='skin' type="text/css" href="/js/skin/dmm-green/ymPrompt.css" />
	 <script type="text/javascript">
	     var temp="${loginedinfo==0?'明':loginedinfo}";
	     var tempmessages="授权码将于 <font color='red' size='3'><b>"+temp+"</b></font> 日后过期，请联系供应商索取新的授权码！<br/><br/>获取授权码后，用管理员身份登录，登录后点击导航栏 <font color='red' size='3'><b>系统管理</b></font> 输入授权码注册";
	     
	     var itmp=0;
	     var timer;
	     var  alertmessage=function (){
	       ymPrompt.alert({title:'授权码注册提示',message:tempmessages,width:450,height:200});
	       if(++itmp>=2)
	         window.clearInterval(timer);
	     };
	     timer=window.setInterval("alertmessage()",10000);
	</script>
 </c:if> 