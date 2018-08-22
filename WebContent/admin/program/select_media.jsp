<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Media"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String moduleid=request.getParameter("moduleid");
Users user = (Users) request.getSession().getAttribute("lg_user");
if(null==user){
   return ;
}

TerminalDAO terminaldao= new TerminalDAO();
String str="and (zu_username ='"+user.getLg_name()+"||' or is_share=1)";
if("1".equals(user.getLg_role())){
	str="";
}
List meida_zu=terminaldao.getAllZu(" where zu_type=1 "+str+" and zu_name <>'sys_text' order by is_share  ");
String[] sysMedias= new Media().getAllmedia_type1();
request.setAttribute("meida_zu", meida_zu);
request.setAttribute("sysMedias",sysMedias);
request.setAttribute("moduleid",moduleid);
%>

<html>
  <head>
    <title>My JSP 'select_media.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="/css/style.css" type="text/css" />
	<link rel="stylesheet" href="/css/dtree.css" type="text/css" />
	<script language="javascript" src="/js/vcommon.js"></script>
	<script language="JavaScript" src="/js/dtree.js"></script>
	<script language="JavaScript" src="/js/did_common.js"></script>
	<style type="text/css">
	.template_div{width: 90%;height: 520px; border: #6699cc 0 solid;  padding-top: 10px; padding-left: 15px;  overflow:auto ;}
	</style>
	<script type="text/javascript">
	var zuid=0;
	var leftcmd=0;
	var top_order_id=2;   ///0默认
	var top_time=30;   //默认一个月
	var type=0;  //leftcmd=MEDIA 才存在
	function goPageLink(zu_id){
		zuid=zu_id;
		leftcmd="TYPE_ZU";
		gopage();
	}
	function goPageLink1(type_){
		leftcmd="MEDIA";
		type=type_;
		gopage();
	}
	function orderby(order_id){
		top_order_id=order_id;
		gopage();
	}
	function gotime(time_num){
		top_time=time_num;
		gopage();
	}
	function gopage(){
		document.getElementById("div_iframe1").src="/admin/program/show_media.jsp?moduleid=${moduleid}&zu_id="+zuid+"&leftcmd="+leftcmd+"&type="+type+"&top_order_id="+top_order_id+"&top_time="+top_time;
	}
	function addmedia(){
	    div_iframe1.addmedia();
    }
	</script>
	</head>
	
  
  <body> 
   <table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
   	<tr>
   		<td valign="top" width="20%" background="/images/menu_bg1.gif"
									style="background-position: right; background-repeat: repeat-y">
   		<div class="template_div" id="template_div">
   			<script language="javascript">
						cnum=0;
						content = new dTree('content'); 
						var init_tree = null;
						content.clearCookie();
						content.add(1,-1,'我的文件夹',"JavaScript:ifrm_cms.goPageLink('1','-1');",'','menu','/images/dtreeimg/device_folder_close.gif');
						<c:set var="sessionlogname" value="${sessionScope.lg_user.lg_name}||"></c:set>
													<c:forEach var="mediazu" items="${meida_zu}">
													<c:set var="title_str" value=""></c:set>
												<c:choose>
													<c:when test="${mediazu.zu_username==sessionlogname}">
														<c:set var="title_str" value="我的${mediazu.is_share==1?'共享':''}文件夹"></c:set>
													</c:when>
													<c:otherwise>
														<c:set var="title_str" value="用户【${fn:replace(mediazu.zu_username, '||', '')}】创建的${mediazu.is_share==1?'共享':'私有'}文件夹"></c:set>
													</c:otherwise>
												</c:choose>
						content.add('${mediazu.zu_id}','${mediazu.zu_pth}','<span onclick="goPageLink(${mediazu.zu_id})" title=${title_str}>${mediazu.zu_name}</span>',"JavaScript:;",'','menu','/images/dtreeimg/folder${mediazu.is_share==1?'1':''}.gif');
						</c:forEach> 
						<c:set var="sysnum" value="1"></c:set>
						content.add('sys0',-1,"系统文件夹","javascript:;",'','','');
						<c:forEach var="sysmedia" items="${sysMedias}">
						<c:set var="sysnum" value="${sysnum+1}"></c:set>
						content.add('sys_${sysnum}','sys0','<span onclick=goPageLink1("${fn:split(sysmedia,"#")[0]}")>${fn:split(sysmedia,"#")[1]}</span>',"JavaScript:;",'','menu','/images/dtreeimg/${fn:split(sysmedia,"#")[0]}.gif');
						</c:forEach> 
						 document.write(content); 
						 var selected_content = content.getSelected();
						if(selected_content == null  || selected_content == "")
						content.s(0);
			</script>
   		</div>
   		</td>
   		<td valign="top" width="80%">
   		 <table width="100%" height="100%" border="0">
   		 <tr><td height="30px" width="100%">
   	<table cellpadding="0" cellspacing="5" border="0" width="100%" height="100%">
   				<tr>
   					<td height="30px" width="80px"><input type="checkbox" name="all"  onclick="div_iframe1.all_chk(this.checked)"/>全选/反选</td>
   					<td  width="100px"><select style="width: 100px" onchange="orderby(this.value)">
   							<option value="0">媒体排序</option>
   							<option value="1" >按文件名</option>
   							<option value="2" selected="selected">按上传时间</option>
   						</select></td>
   					<td  width="100px">
   						<select style="width: 100px" onchange="gotime(this.value)">
   							<option value="3" >最近三天</option>
   							<option value="30" selected="selected">最近一个月</option>
   							<option value="90">最近三个月</option>
   							<option value="182">最近半年</option>
   							<option value="365">最近一年</option>
   							<option value="2000">全部</option>
   						</select>
   					</td>
   					<td align="right">&nbsp;</td>
   				</tr></table>
   </td></tr>
   <tr><td class="Line_01" ></td></tr>
   		<tr><td>
   		<iframe src="" width="100%" height="440px" scrolling="auto" id="div_iframe1"  name="div_iframe1" frameborder="0" ></iframe> 
		</td></tr></table>
		</td>
   	</tr>
   	 <tr><td class="Line_01" colspan="2" ></td></tr>
   	<tr><td height="30px;" align="center" colspan="2" >
   	<input type="button" class="button1" onclick="addmedia();" value=" 添 加 " />&nbsp;&nbsp;&nbsp;&nbsp;
   	<input type="button" class="button1" onclick="parent.closedivframe3();" value=" 取 消 " /></td></tr>
   </table>
  </body>
</html>
<script type="text/javascript">
<!--
gopage();
//-->
</script>
