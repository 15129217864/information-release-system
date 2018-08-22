<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<%
Users user = (Users) request.getSession().getAttribute("lg_user");
if(user==null){
response.sendRedirect(response.encodeRedirectURL("/index.jsp"));
return; 
}%>
<html>
  <head>
    <title>播放器配置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="styles.css">
<style type="text/css">
body { font-family:Verdana; font-size:14px; margin:0;text-align:center; background: #e4e4e4}
#content { float:right; width:200px; height:200px; background-repeat: no-repeat;}
td { font-size: 12px; line-height: 18px; text-decoration: none; }
input.button { font-size: 12px; font-style: normal; font-weight: normal; font-variant: normal; border: 1px solid #183ead; line-height: normal; background-color: #ddddff; color: #000000; height: 18px; }
input.text { font-size: 12px; font-style: normal; line-height: normal; font-weight: normal; font-variant: normal; height: 18px; border: 1px solid #183ead; color: #000000; padding-right: 3px; padding-left: 3px; }
td.title { font-size: 12px; color: #FFFFFF; background-color: #6699FF; height: 24px; }
td.tail { font-size: 12px; height: 25px; }
td.hline { background-color: #FF9933; height: 1px; color: #FF9933; }
td.vline { color: #FF9933; background-color: #FF9933; width: 1px; }
.space { margin-top: 10px; margin-bottom: 10px; } table { background-color: #F5F5F5; }
select.list { font-size: 12px; font-style: normal; line-height: normal; font-weight: normal; font-variant: normal; height: 18px; color: #000000; border: 1px solid #183ead; }
input.buttonface { font-size: 12px; font-style: normal; font-weight: normal; font-variant: normal; border: 1px solid #183ead; line-height: normal; background-color: #ddddff; color: #000000; height: 18px; }
td.top { font-size: 12px; line-height: 18px; text-decoration: none; vertical-align: top; }
input.textspecial { font-size: 12px; font-style: normal; line-height: normal; font-weight: normal; font-variant: normal; height: 18px; border: 1px solid #183ead; color: #000000; } 
</style>
		<script type="text/javascript">
function closedivframe(){
	parent.closedivframe(2);
}
		</script>
  </head>
  
  <body><br/>
    <table width="400" border="0" cellspacing="0" cellpadding="0">
    <tr>
											<td class="hline">
												</td>
										</tr>
						<tr>
							<td colspan="2">
								<table width="402" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="vline" rowspan="15">
											<br>
										</td>
										<td width="400">
											<table width="410" border="0" align="center" cellpadding="0"
												cellspacing="10" class="space">
												<tr>
													<td>
														对不起，您访问的页面出现错误！请稍后再试！
													</td>
												</tr>
											</table>
										</td>
										<td class="vline" rowspan="15">
											<br>
										</td>
									</tr>
									<tr>
											<td class="hline">
												</td>
										</tr>
										
								</table>
							</td>
						</tr>
					</table><br/>
  </body>
</html>