<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'restart.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="/css/style.css" type="text/css" />
	<script type="text/javascript">
	function restartTerminal(){
var reips=parent.ipforms.checkips.value;
restartForm.checkips.value=reips;
restartForm.action="/rq/requestClientOperating?cmd=ONDOWN&opp=xu0007";
restartForm.submit();
alert("客户端正在关机，请稍后...");
parent.closedivframe();
}

	</script>
  </head>
  
  <body>
  <table width="260" border="0" align="center" cellpadding="0"
				cellspacing="7" class="space">
				<tr>
					<td height="50px" align="center">
						<span style="color: green" >友情提示：确认关闭选中终端？</span>
					</td>
				</tr>
				<tr>
					<td height="30" class="tail" align="center">
						<input type="button" class="button" onclick="restartTerminal()"
							value=" 关 机 ">
						&nbsp;
						<input type="reset" class="button" value=" 取 消 "
							onclick="parent.closedivframe();">
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>

			</table>
  
  <form action="" name="restartForm" method="post" >
   		<input type="hidden" name="checkips" value=""/>
 </form>  		
  </body>
</html>
