<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
	<head>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="Pragma" content="no-cache" />
		<link rel="stylesheet" href="/css/dtree.css" type="text/css" />
	</head>
	<script language="javascript" src="/js/vcommon.js"></script>
	<script language="JavaScript" src="/js/dtree.js"></script>
	<script language="JavaScript" src="/js/did_common.js"></script>
	<script language="JavaScript"> 
		function onLoad(){
			//parent.slide('sub2');
		}
		function fcreate(){
		var selectzuid=fm1.zu_id.value;
		var zu_pth=fm1.zu_pth.value;
		oppcategory("添加组",selectzuid,"add",zu_pth);
		}
		 
		function fdestory(){
		var selectzuid=fm1.zu_id.value;
		var zu_pth=fm1.zu_pth.value;
		var zu_username=fm1.zu_username.value;
		if(zu_pth==-1){
		alert('由于该类别为根类别，因此无法进行删除。');
		return;
		}else if(${sessionScope.lg_user.lg_role}!=1&&zu_username!='${sessionScope.lg_user.lg_name}||' ){
		alert('由于该类别不是您创建，因此无法进行删除。');
		return;
		}
		if(confirm("提示信息：子组和子组下面的所有媒体也将一起删除，请慎重！确认删除？")){
		fm1.action="/rq/oppcatrgory";
		fm1.submit();
		}
		}
		 
		function fedit(){
		var selectzuid=fm1.zu_id.value;
		var zu_pth=fm1.zu_pth.value;
		var zu_username=fm1.zu_username.value;
		if(zu_pth==-1){
		alert('由于该类别为根类别，因此无法进行编辑。');
		}else if(${sessionScope.lg_user.lg_role}!=1&&zu_username!='${sessionScope.lg_user.lg_name}||'){
		alert('由于该类别不是您创建，因此无法进行编辑。');
		}else{
		oppcategory("编辑组",selectzuid,"update",zu_pth);
		}
		
		}
		 
		function frefresh(){
			parent.reflag = false;
			//parent.mflag1 = true;
			parent.mflag2 = true;
			//parent.mflag3 = true;
			//parent.gosub(1);
			//parent.gosub(3);
			//parent.gosub(2);	
			parent.reflag = true;	
		}
		function goPageLink(zuid,zu_pth,zu_username){	
			fm1.zu_id.value=zuid;
			fm1.zu_pth.value=zu_pth;
			fm1.zu_username.value=zu_username;
			parent.parent.content.location.href = "/admin/media/index.jsp?left_menu=TYPE_ZU&title=TYPE_ZU&zu_id="+zuid;
		}
		function oppcategory(title,selectzuid,opptype,zu_pth){
		document.body.scrollTop = "0px";
		document.getElementById("div_iframe").src="/admin/media/oppcatrgory.jsp?zuid="+selectzuid+"&opptype="+opptype+"&zu_pth="+zu_pth;
		document.getElementById("divframe").style.left="10px";
		document.getElementById("divframe").style.top="36px";
		document.getElementById("titlename").innerHTML=title;
		document.getElementById('divframe').style.display="block";
		document.getElementById("divframe").style.visibility='visible';
		document.getElementById("massage").style.visibility='visible';
		
		}
		
		function closedivframe(){
		document.getElementById("div_iframe").src="/loading.jsp";
		document.getElementById('divframe').style.display="none";
		document.getElementById('divframe').style.visibility="hidden";
		}
		function gohome(){
		parent.parent.content.location.href = "/admin/media/index.jsp";
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
	color: #fff
}
</style>
	<body leftmargin="0" topmargin="10" marginwidth="0" marginheight="0">
		<form name="fm1" method="POST">
			<input type="hidden" name="zu_id" value="1"/>
			<input type="hidden" name="zu_username" value=""/>
			<input type="hidden" name="zu_pth" value="-1"/>
			<input type="hidden" name="cmd" value="DELETE"/>
			<table width="150" border="0" cellpadding="0" cellspacing="0"  style="margin-bottom: 10px">
				<tr>
					<td width="25" height="25">
						<a href="javascript:frefresh();" onMouseOut="MM_swapImgRestore()"
							onMouseOver="MM_swapImage('schedule1','','/images/leftmenu/content1_over.gif',1)"><img
								src="/images/leftmenu/content1_on.gif" name="schedule1"
								width="25" height="25" border="0" title="刷新"> </a>
					</td>
					<td width="25" height="25">
						<a href="javascript:fcreate();" onMouseOut="MM_swapImgRestore()"
							onMouseOver="MM_swapImage('schedule2','','/images/leftmenu/schedule2_over.gif',1)"><img
								src="/images/leftmenu/schedule2_on.gif" name="schedule2"
								width="25" height="25" border="0" title="添加"> </a>
					</td>
					<td width="25" height="25">
						<a href="javascript:fdestory();" onMouseOut="MM_swapImgRestore()"
							onMouseOver="MM_swapImage('schedule3','','/images/leftmenu/schedule3_over.gif',1)"><img
								src="/images/leftmenu/schedule3_on.gif" name="schedule3"
								width="25" height="25" border="0" title="删除"> </a>
					</td>
					<td width="25" height="25">
						<a href="javascript:fedit();" onMouseOut="MM_swapImgRestore()"
							onMouseOver="MM_swapImage('schedule4','','/images/leftmenu/folder_modify_over.gif',1)"><img
								src="/images/leftmenu/folder_modify_on.gif" name="schedule4"
								width="25" height="25" border="0" title="编辑"> </a>
					</td>
					<td width="25" height="25">
						&nbsp;
					</td>
				</tr>
			</table>
    
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" >
				<tr>
					<td width="5">
						&nbsp;
					</td>
					<td>
						<script language="javascript">
							content = new dTree('content'); 
							var init_tree = null;
					        //if(init_tree == true)	
					        content.clearCookie();	
					        //--------------------------------------------------------------------------------------------
							content.add(1,-1,'我的文件夹',"JavaScript:gohome();",'','','/images/dtreeimg/folder.gif');
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
								
								content.add('${mediazu.zu_id}','${mediazu.zu_pth}','<span title=${title_str}>${mediazu.zu_name}</span>',"JavaScript:goPageLink('${mediazu.zu_id}','${mediazu.zu_pth}','${mediazu.zu_username}');",'','','/images/dtreeimg/folder${mediazu.is_share==1?'1':''}.gif');
							
							</c:forEach>
								
							content.add('${mediazu.zu_id}','${mediazu.zu_pth}','<span title=${title_str}>${mediazu.zu_name}</span>',"JavaScript:goPageLink('${mediazu.zu_id}','${mediazu.zu_pth}','${mediazu.zu_username}');",'','','/images/dtreeimg/folder${mediazu.is_share==1?'1':''}.gif');
							//--------------------------------------------------------------------------------------------
							document.write(content); 
					        var selected_content = content.getSelected();
						    if(selected_content == null  || selected_content == "")
						    	content.s(0); 
					    </script>
					</td>
				</tr>
			</table>
		</form>
		<div id="divframe">
			<div id="massage">
				<table cellpadding="0" cellspacing="0">
					<tr height="20px;" class=header>
						<td align="center" style="font-weight: bold">
							<span id="titlename"></span>
						</td>
					</tr>
					<tr>
						<td>
							<iframe src="/loading.jsp" scrolling="no" id="div_iframe"
								name="div_iframe" frameborder="0" width="170px" height="100px"></iframe>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>

