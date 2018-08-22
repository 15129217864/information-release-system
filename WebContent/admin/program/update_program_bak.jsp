<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ProjectInfo" />
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String templatefile = request.getParameter("templatefile");
	String template_img = "/admin/template/notemplate.jpg";
	List list = null;
	ProjectInfo programinfo = null;
	String userName = "";
	String begin_realpate1 = "";
		String projectpath = FirstStartServlet.projectpath;
		String templateXML = projectpath + "/serverftp/program_file/"
		+ templatefile + "/" + templatefile + ".xml";
		begin_realpate1 = templatefile;
		list = ProjectInfo.getPane(templateXML);
		programinfo = ProjectInfo.getProgramInfo(templateXML);
	request.setAttribute("template_img", template_img);
	
%>
<html>
	<head>
		<title>My JSP 'create_program.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script type="text/javascript">
function searchTemplate(template_id){
window.location.href="/admin/program/create_program.jsp?cmd=TEMPLATE&template_id="+template_id;

}
function openimage(opp,imagepath,imagename){
document.body.scrollTop = "0px";
document.getElementById("divframe").style.left="180px";
document.getElementById("divframe").style.top="60px";
if(opp==1){
document.getElementById("div_iframe").src="opp/project_img.jsp?filename=<%=begin_realpate1%>"+imagepath;
document.getElementById("titlename").innerHTML="图片名称："+imagename;
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==2){
document.getElementById("div_iframe").src="opp/project_other.jsp?paneType=video&filename=<%=begin_realpate1%>/"+imagepath;
document.getElementById("titlename").innerHTML="视频名称："+imagename;
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==3){
document.getElementById("div_iframe").src="opp/project_text.jsp?filename=<%=begin_realpate1%>/"+imagepath;
document.getElementById("titlename").innerHTML="滚动文字："+imagename;
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==4){
document.getElementById("div_iframe").src="opp/project_other.jsp?paneType=web&filename=<%=begin_realpate1%>/"+imagepath;
document.getElementById("titlename").innerHTML="网页名称："+imagename;
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==5){
document.getElementById("div_iframe").src="opp/project_other.jsp?paneType=ppt&filename=<%=begin_realpate1%>/"+imagepath;
document.getElementById("titlename").innerHTML="PPT名称："+imagename;
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==6){
document.getElementById("div_iframe").src="opp/project_other.jsp?paneType=word&filename=<%=begin_realpate1%>/"+imagepath;
document.getElementById("titlename").innerHTML="Word名称："+imagename;
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==7){
document.getElementById("div_iframe").src="opp/project_other.jsp?paneType=flash&filename=<%=begin_realpate1%>/"+imagepath;
document.getElementById("titlename").innerHTML="Flash名称："+imagename;
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==8){
document.getElementById("div_iframe").src="opp/update_weather.jsp?filename=<%=begin_realpate1%>/"+imagepath;
document.getElementById("titlename").innerHTML="天气：当前三天天气预报";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==9){
document.getElementById("div_iframe").src="opp/update_foreground.jsp?filename=<%=begin_realpate1%>&infotype="+imagepath+"&foreground="+imagename;
document.getElementById("titlename").innerHTML="LED时钟：修改字体颜色";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==10){
document.getElementById("div_iframe").src="opp/update_background.jsp?filename=<%=begin_realpate1%>&infotype="+imagepath+"&background="+imagename;
document.getElementById("titlename").innerHTML="模拟时钟：修改背景图片";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==11){
document.getElementById("div_iframe").src="opp/update_foreground.jsp?filename=<%=begin_realpate1%>&infotype="+imagepath+"&foreground="+imagename;
document.getElementById("titlename").innerHTML="数字时钟：修改字体颜色";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==12){
document.getElementById("div_iframe").src="opp/updateMusic.jsp?filename=<%=begin_realpate1%>&backmusic="+imagepath;
document.getElementById("titlename").innerHTML="背景音乐：修改背景音乐";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==13){
document.getElementById("div_iframe").src="opp/update_backimage.jsp?filename=<%=begin_realpate1%>&opp=1&background="+imagepath;
document.getElementById("titlename").innerHTML="模板图片：修改模板图片";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}
}
function addproject(opp){
document.body.scrollTop = "0px";
document.getElementById("divframe").style.left="180px";
document.getElementById("divframe").style.top="60px";
if(opp==1){
document.getElementById("div_iframe").src="opp/updateproject_other.jsp?paneType=image&filename=<%=begin_realpate1%>";
document.getElementById("titlename").innerHTML="添加图片";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==2){
document.getElementById("div_iframe").src="opp/updateproject_other.jsp?paneType=video&filename=<%=begin_realpate1%>";
document.getElementById("titlename").innerHTML="添加视频";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==3){
document.getElementById("div_iframe").src="opp/updateproject_other.jsp?paneType=scroll&filename=<%=begin_realpate1%>";
document.getElementById("titlename").innerHTML="添加滚动文字";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==4){
document.getElementById("div_iframe").src="opp/updateMusic.jsp?filename=<%=begin_realpate1%>";
document.getElementById("titlename").innerHTML="添加背景音乐";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==5){
document.getElementById("div_iframe").src="opp/updateproject_other1.jsp?paneType=flash&filename=<%=begin_realpate1%>";
document.getElementById("titlename").innerHTML="添加Flash";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==6){
document.getElementById("div_iframe").src="opp/updateproject_other1.jsp?paneType=word&filename=<%=begin_realpate1%>";
document.getElementById("titlename").innerHTML="添加WORD";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==7){
document.getElementById("div_iframe").src="opp/updateproject_other1.jsp?paneType=ppt&filename=<%=begin_realpate1%>";
document.getElementById("titlename").innerHTML="添加PPT";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==8){
document.getElementById("div_iframe").src="opp/updateproject_other1.jsp?paneType=web&filename=<%=begin_realpate1%>";
document.getElementById("titlename").innerHTML="添加网页";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==9){
document.getElementById("div_iframe").src="opp/updateproject_other1.jsp?paneType=htmltext&filename=<%=begin_realpate1%>";
document.getElementById("titlename").innerHTML="添加滚动文本";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}else if(opp==10){
document.getElementById("div_iframe").src="opp/updateproject_other1.jsp?paneType=excel&filename=<%=begin_realpate1%>";
document.getElementById("titlename").innerHTML="添加EXCEL";
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}
}
var Obj=''
document.onmouseup=MUp
document.onmousemove=MMove

function MDown(Object){
Obj=Object.id
document.all(Obj).setCapture()
pX=event.x-document.all(Obj).style.pixelLeft;
pY=event.y-document.all(Obj).style.pixelTop;
}

function MMove(){
if(Obj!=''){
  document.all(Obj).style.left=event.x-pX;
  document.all(Obj).style.top=event.y-pY;
  }
}

function MUp(){
if(Obj!=''){
  document.all(Obj).releaseCapture();
  Obj='';
  }
}
function SetWinHeight(obj) 
{ 
 var win=obj; 
 if (document.getElementById) 
 { 
  if (win && !window.opera) 
  { 
	if (win.contentDocument && win.contentDocument.body.offsetHeight){  
	win.height = win.contentDocument.body.offsetHeight;  
	}else if(win.Document && win.Document.body.scrollHeight){ 
    win.height = win.Document.body.scrollHeight;
	}
	if (win.contentDocument && win.contentDocument.body.offsetWidth){  
	win.width = win.contentDocument.body.offsetWidth;  
	}else if(win.Document && win.Document.body.scrollWidth){ 
    win.width = win.Document.body.scrollWidth; 
	}
  } 
 } 
}
var checkFlag = true;
function checkAll() {
	var objForm=document.form1;
	for(i=0;i<objForm.length;i++){
		if(objForm[i].type=="checkbox"){
			objForm[i].checked=checkFlag;
		}
	}
	checkFlag=(!checkFlag);
}
function delProject(){
    var hasSelect=false;
	var objForm=document.form1;
	for(i=0;i<objForm.length;i++){
		if(objForm[i].type=="checkbox"){
			if(objForm[i].checked&&objForm[i].name!='selectall'){
			    hasSelect=true;
			}
		}
	}
	if(hasSelect){
	if(confirm("提示：确认删除？")){
        form1.action="/admin/program/opp/deleteProjectDO1.jsp";
	    form1.submit();
	    }
	}else{
	    alert("您没有选择要删除的项！");
	}


}
function closedivframe(){
document.getElementById("div_iframe").src="/loading.jsp";
document.getElementById('divframe').style.visibility="hidden";
}

function saveProgram(){
form1.action="/admin/program/update_save_program.jsp";
form1.submit();
}
function goback(){
parent.listtop.location.href="/admin/program/program_list_top.jsp";
history.go(-1);
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
	</head>
	<body style="margin: 0px;">
		<div id="divframe">
			<div id="massage">
				<table cellpadding="0" cellspacing="0">
					<tr onmousedown=MDown(divframe) height="30px;" class=header>
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
								onload="Javascript:SetWinHeight(this)"></iframe>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<form action="" name="form1" method="post">
			<input type="hidden" name="templatefile" value="<%=templatefile%>" />
			<table border="0" width="100%" height="100%" cellpadding="0"
				cellspacing="0">
				<tr>
					<td align="center" valign="top" height="1px">
					</td>
					<td width="302px" rowspan="2" align="right" valign="top"
						background="/images/menu_bg1.gif"
						style="background-position: left; background-repeat: repeat-y">
						<table cellspacing="0" cellpadding="0" width="99%">
							<tr>
								<td class="TitleBackground">
									模板图片
							</tr>
							<tr>
								<td>
									<img src="${template_img }" width="300" height="200" alt="模板图片" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="TitleBackground" valign="top">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="vline" rowspan="15">
									<br>
								</td>
								<td width="100%" valign="top">
									<table style="font-size: 13px" border="0" height="400"
										width="100%" cellpadding="0" cellspacing="0">
										<%
										if (list == null || list.size() == 0) {
										%>
										<tr>
											<td align="center">
												您选择的节目暂无内容！<input type="button" value=" 返 回 " onclick="goback()"  class="button"/>&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
										<tr>
											<td align="center" height="135">
												&nbsp;
											</td>
										</tr>
										<%
										} else {
										%>
										<tr height="35">
											<td align="center" style="font-weight: bold">
												添加内容

											</td>
										</tr>
										<tr height="25px" bgcolor="#BDC8FB">
											<td style="padding-left: 30px;">
												<span style='width:80px;font-weight: bold'>背景图片</span>
											</td>
										</tr>
										<tr height="25px">
											<td style="padding-left: 70px;">
												背景图片：
												<%
													if ("".equals(programinfo.getProgram_backimage())
													|| "NULL".equals(programinfo.getProgram_backimage())) {
											%>
												无
												<%
											} else {
											%>
												<a href="javaScript:void(0)"
													onclick="openimage(13,'<%=programinfo.getProgram_backimage()%>','0')"><%=programinfo.getProgram_backimage()%>
												</a>
												<%
												}
												%>

											</td>
										</tr>
										<tr height="25px" bgcolor="#BDC8FB">
											<td style="padding-left: 30px;">
												<span style='width:80px;font-weight: bold'>背景音乐</span>
											</td>
										</tr>
										<tr height="25px">
											<td style="padding-left: 70px;">
												背景音乐：
												<span style="color: blue"> <%
												 		if ("".equals(programinfo.getProgram_backmusic())
												 		|| "NULL".equals(programinfo.getProgram_backmusic())) {
												 %>
													无&nbsp;&nbsp;&nbsp;<input type="button" class="button"
														value=" 添 加 " onclick="addproject(4)" /> <%
												 } else {
												 %> <a
													href="javaScript:void(0)" title="点击查看背景音乐"
													onclick="openimage(12,'<%=programinfo.getProgram_backmusic()%>','0')"><%=programinfo.getProgram_backmusic()%>
												</a> <%
 }
 %>
												</span>
											</td>
										</tr>
										<%
													for (int i = 0; i < list.size(); i++) {
													ProjectInfo info = (ProjectInfo) list.get(i);
													List panefilelist = info.getPanefileList();
													int filelist = panefilelist.size();
													request.setAttribute("filelist", filelist);
										%>
										<tr height="25px" bgcolor="#BDC8FB">
											<td style="padding-left: 30px;">
												<%
														if (info.getType().equals("模拟时钟")) {
														out.println("<span style='width:80px;font-weight: bold'>"+info.getType()+"</span>");
												%>
												<span style="color: blue" title="修改背景图片"></span>
												<%
														} else if (info.getType().equals("LED时钟")) {
														out.println("<span style='width:80px;font-weight: bold'>"+info.getType()+"</span>");
												%>
												<span style="color: blue " title="修改字体颜色"><input
														type="button" class="button" value="修改字体颜色"
														onclick="openimage(9,'led','<%=info.getForeground()%>')" />
												</span>
												<%
														} else if (info.getType().equals("数字时钟")) {
														out.println("<span style='width:80px;font-weight: bold'>"+info.getType()+"</span>");
												%>
												<span style="color: blue" title="修改字体颜色"><input
														type="button" class="button" value="修改字体颜色"
														onclick="openimage(11,'tvclock','<%=info.getForeground()%>')" />
												</span>
												<%
														} else if (info.getType().equals("图片")) {
														out.println("<span style='width:80px;font-weight: bold'>"+info.getType()+"</span>");
												%>
												<input type="button" class="button" onclick="addproject(1)"
													value=" 添 加 " />
												<%
														} else if (info.getType().equals("视频")) {
														out.println("<span style='width:80px;font-weight: bold'>"+info.getType()+"</span>");
												%>
												<input type="button" class="button" onclick="addproject(2)"
													value=" 添 加 " />
												<%
														} else if (info.getType().equals("滚动文字")) {
														out.println("<span style='width:80px;font-weight: bold'>"+info.getType()+"</span>");
												%>
												<input type="button" class="button" onclick="addproject(3)"
													value=" 添 加 " />
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="button" class="button" value="修改字体颜色"
													onclick="openimage(9,'scroll','<%=info.getForeground()%>')" />
												<%
														} else if (info.getType().equals("Flash")) {
														out.println("<span style='width:80px;font-weight: bold'>"+info.getType()+"</span>");
												%>
												<input type="button" class="button" onclick="addproject(5)"
													value=" 添 加 " ${filelist>
												0?'style="display: none;"':'' }/>
												<%
														} else if (info.getType().equals("WORD")) {
														out.println("<span style='width:80px;font-weight: bold'>"+info.getType()+"</span>");
												%>
												<input type="button" class="button" onclick="addproject(6)"
													value=" 添 加 " ${filelist>
												0?'style="display: none;"':'' }/>
												<%
														} else if (info.getType().equals("PPT")) {
														out.println("<span style='width:80px;font-weight: bold'>"+info.getType()+"</span>");
												%>
												<input type="button" class="button" onclick="addproject(7)"
													value=" 添 加 " ${filelist>
												0?'style="display: none;"':'' }/>
												<%
														} else if (info.getType().equals("网页")) {
														out.println("<span style='width:80px;font-weight: bold'>"+info.getType()+"</span>");
												%>
												<input type="button" class="button" onclick="addproject(8)"
													value=" 添 加 " ${filelist>
												0?'style="display: none;"':'' }/>
												<%
														} else if (info.getType().equals("滚动文本")) {
														out.println("<span style='width:80px;font-weight: bold'>"+info.getType()+"</span>");
												%>
												<input type="button" class="button" onclick="addproject(9)"
													value=" 添 加 " ${filelist>
												0?'style="display: none;"':'' }/>
												<%
														} else if (info.getType().equals("Excel")) {
														out.println("<span style='width:80px;font-weight: bold'>"+info.getType()+"</span>");
												%>
												<input type="button" class="button" onclick="addproject(10)"
													value=" 添 加 " ${filelist>
												0?'style="display: none;"':'' }/>
												<%
														} else {
														out.println("<span style='width:80px;font-weight: bold'>"+info.getType()+"</span>");
															}
												%>

											</td>

										</tr>
										<tr height="25px">
											<td style="padding-left:70px;">
												<table cellpadding="0" cellspacing="0" width="90%">
													<%
															for (int j = 0; j < panefilelist.size(); j++) {
															String[] panefile = (String[]) panefilelist.get(j);
															if (j == 0) {
													%>
													<tr>
														<%
														} else if (j % 4 == 0) {
														%>
													</tr>
													<tr>
														<%
														}
														%>
														<td>
															<%
															if (info.getType().equals("图片")) {
															%>
															<a href="javaScript:void(0)" title="点击查看图片"
																onclick="openimage(1,'<%=panefile[1]%>','<%=panefile[0]%>')"><%=panefile[0]%>
															</a>
															<input type="checkbox" name="projectlist"
																value="image_<%=panefile[0]%>_<%=panefile[1]%>_<%=userName%>" />
															<%
																	}
																	if (info.getType().equals("视频")) {
															%>
															<a href="javaScript:void(0)" title="点击下载"
																onclick="openimage(2,'<%=panefile[1]%>','<%=panefile[0]%>')"><%=panefile[0]%>
															</a>
															<input type="checkbox" name="projectlist"
																value="video_<%=panefile[0]%>_<%=panefile[1]%>_<%=userName%>" />
															<%
																	}
																	if (info.getType().equals("滚动文本")) {
															%>
															<a href="javaScript:void(0)"
																onclick="openimage(12,'<%=panefile[1]%>','<%=panefile[0]%>')"><%=panefile[0]%>
															</a>
															<input type="checkbox" name="projectlist"
																value="htmltext_<%=panefile[0]%>_<%=panefile[1]%>_<%=userName%>" />

															<%
																	}
																	if (info.getType().equals("滚动文字")) {
															%>
															<a href="javaScript:void(0)" title="点击查看详细信息"
																onclick="openimage(3,'<%=panefile[1]%>','<%=panefile[0]%>')"><%=panefile[0]%>
															</a>
															<input type="checkbox" name="projectlist"
																value="scroll_<%=panefile[0]%>_<%=panefile[1]%>_<%=userName%>" />
															<%
																	}
																	if (info.getType().equals("天气")) {
															%>
															<a href="javaScript:void(0)"
																onclick="openimage(8,'<%=panefile[1]%>','<%=panefile[0]%>')">当前三天天气预报</a>
															<input type="checkbox" name="projectlist"
																value="weather_<%=panefile[0]%>_<%=panefile[1]%>_<%=userName%>" />
															<%
																	}
																	if (info.getType().equals("网页")) {
															%>
															<a href="javaScript:void(0)"
																onclick="openimage(4,'<%=panefile[1]%>','<%=panefile[0]%>')"><%=panefile[0]%>
															</a>
															<input type="checkbox" name="projectlist"
																value="web_<%=panefile[0]%>_<%=panefile[1]%>_<%=userName%>" />
															<%
																	}
																	if (info.getType().equals("PPT") && j == 0) {
															%>
															<a href="javaScript:void(0)"
																onclick="openimage(5,'<%=panefile[1]%>','<%=panefile[0]%>')"><%=panefile[0]%>
															</a>
															<input type="checkbox" name="projectlist"
																value="ppt_<%=panefile[0]%>_<%=panefile[1]%>_<%=userName%>" />
															<%
																	}
																	if (info.getType().equals("WORD")) {
															%>
															<a href="javaScript:void(0)"
																onclick="openimage(6,'<%=panefile[1]%>','<%=panefile[0]%>')"><%=panefile[0]%>
															</a>
															<input type="checkbox" name="projectlist"
																value="word_<%=panefile[0]%>_<%=panefile[1]%>_<%=userName%>" />
															<%
																	}
																	if (info.getType().equals("Excel")) {
															%>
															<a href="javaScript:void(0)"
																onclick="openimage(11,'<%=panefile[1]%>','<%=panefile[0]%>')"><%=panefile[0]%>
															</a>
															<input type="checkbox" name="projectlist"
																value="excel_<%=panefile[0]%>_<%=panefile[1]%>_<%=userName%>" />
															<%
																	}
																	if (info.getType().equals("Flash")) {
															%>
															<a href="javaScript:void(0)"
																onclick="openimage(7,'<%=panefile[1]%>','<%=panefile[0]%>')"><%=panefile[0]%>
															</a>
															<input type="checkbox" name="projectlist"
																value="flash_<%=panefile[0]%>_<%=panefile[1]%>_<%=userName%>" />
															<%
																	}
																	if (info.getType().equals("模拟时钟")) {
															%>
															背景图片：
															<a href="javaScript:void(0)"
																onclick="openimage(10,'<%=panefile[1]%>','<%=panefile[0]%>')"><%=panefile[0]%>
															</a>
															<input type="checkbox" name="projectlist"
																value="word_<%=panefile[0]%>_<%=panefile[1]%>_<%=userName%>" />
															<%
															}
															%>

														</td>
														<%
														}
														%>
													</tr>
												</table>
											</td>
										</tr>
										<%
										}
										%>
										<tr height="25px" bgcolor="#BDC8FB">
											<td></td>
										</tr>
										<tr>
											<td height="25px">
												<table cellpadding="0" cellspacing="0" width="100%">
													<tr>
														<td align="left">
															<input type="checkbox" name="selectall"
																onClick="checkAll()" />
															全选/全不选&nbsp;&nbsp;
															<input type="button" value=" 删 除 " class="button"
																onclick="delProject()" />
															&nbsp;&nbsp;
														</td>

														<td align="right">
															&nbsp;&nbsp;&nbsp;&nbsp;
															<span style="color: green">友情提示：修改节目后将自动保存！</span>&nbsp;
															<input type="button" value=" 返 回 " onclick="window.location.href='/rq/programList?left_menu=&zu_id=no';parent.listtop.location.href='/admin/program/program_list_top.jsp';"  class="button"/>
															&nbsp;&nbsp;&nbsp;
															
													</tr>
												</table>

											</td>
										</tr>
										<%
										}
										%>
										<tr>
											<td>
												&nbsp;
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
									<img src="/images/empty.gif" width="1" height="1">
								</td>
							</tr>
						</table>


					</td>
				</tr>
			</table>
		</form>



	</body>
</html>
