<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO" />
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO" />
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO" />
<jsp:directive.page import="com.xct.cms.domin.Program"/>
<jsp:directive.page import="com.xct.cms.domin.Media"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Template"/>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
	ProgramDAO programdao = new ProgramDAO();
	ModuleDAO moduledao = new ModuleDAO();
	TerminalDAO terminaldao = new TerminalDAO();
	TemplateDAO templatedao= new TemplateDAO();
	String templatefile = request.getParameter("templatefile");
Users user=(Users)request.getSession().getAttribute("lg_user");
	Program program=programdao.getProgram("and  program_JMurl='"+templatefile+"'");
	String programName=program.getProgram_Name();
	String zuname=program.getZu_name();
	String jmurl=program.getProgram_JMurl();
	String templateid = program.getTemplateid();
	List zuList = terminaldao.getAllZu("where zu_type=1 and  zu_username like '%"+user.getLg_name()+"||%' and zu_pth=1");
	//System.out.println("where zu_type=1 and zu_username like '%"+user.getLg_name()+"||%' and zu_pth=1");
	List moduleList = moduledao.getModuleTempByTemplateId(templateid);
	Template template=templatedao.getTemplateTempById(templateid); 
	
String[] sysMedias= new Media().getAllmedia_type1();
	request.setAttribute("templateid", templateid);
	request.setAttribute("zuname", zuname);
	request.setAttribute("zuid", program.getProgram_treeid());
	request.setAttribute("zuList", zuList);
	request.setAttribute("programName", programName);
	request.setAttribute("moduleList", moduleList);
	request.setAttribute("jmurl",jmurl);
	request.setAttribute("sysMedias",sysMedias);
	request.setAttribute("bgsoundpath",template.getTemplate_backmusic());
%>
<html>
	<head>
		<title>My JSP 'create_program.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<link rel="stylesheet" href="/css/dtree.css" type="text/css" />
		<script language="JavaScript" src="/js/dtree.js"></script>
		<script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		
		<style type="text/css">
.template_fieldset{width: 200;height: 100%; border: #6699cc 1 solid;  padding-top: 10px; margin-left: 5px}
.me_css{width: 100%;height: 87%; border: #6699cc 0 solid; overflow-y:scroll;overflow-x:none;}
.template_div{width: 200;height: 450px;; border: #6699cc 0 solid;  padding-top: 10px; padding-left: 15px;  overflow-y:scroll;overflow-x:auto ;}
#divframe{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none }
#massage{border:#6699cc solid; border-width:1px; background:#fff; color:#036; font-size:12px; line-height:100%;  display: none}
#smallImgDiv{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none }
#smallImgMassage{border:#6699cc solid; border-width:1px; background:#fff; color:#036; font-size:12px; line-height:100%;  display: none}
#lodingDiv{ position:absolute; z-index: 999; display: none }
#lodingMassage{color:#036; font-size:12px; line-height:100%;  display: none}
.header{background:url(/images/device/btn_background.gif);  font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff;	cursor: move;}
     #tabsF {
      float:left;
      width:100%;
      font-size:93%;
      line-height:normal;
   
    
      }
    #tabsF ul {
	margin:0;
	padding:0px 10px 0 0px;
	list-style:none;
      }
    #tabsF li {
      display:inline;
      margin:0;
      padding:0;
      }
    .tbals_a1 {
      float:left;
      background:url("/images/tableftF.gif") no-repeat left top;
      margin:0;
      padding:0 0 0 4px;
      text-decoration:none;
      }
    .tbals_a2 {
      float:left;
      background:url("/images/tableftF.gif") no-repeat 0% -42px;
      margin:0;
      padding:0 0 0 4px;
      text-decoration:none;
      }
    .tables_span1 {
      float:left;
      display:block;
      background:url("/images/tabrightF.gif") no-repeat right top;
      padding:5px 10px 4px 6px;
      color:#000000;
      }
     .tables_span2 {
      float:left;
      display:block;
      background:url("/images/tabrightF.gif") no-repeat 100% -42px;
      padding:5px 10px 4px 6px;
      color:#000000;
      }
       #bgDiv {
    display: none;
    position: absolute;
    top: 0px;
    left: 0px;
    right:0px;
    background-color: #777;
    filter:progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75)
    opacity: 0.6;
}
    /* Commented Backslash Hack hides rule from IE5-Mac \*/
    #tabsF a span {float:none;}
    /* End IE5-Mac hack */
</style>
		<script type="text/javascript">
		var checkModule;
function selectPhoto(){
document.body.scrollTop = "0px";
document.getElementById("div_iframe").width="350";
document.getElementById("div_iframe").height="300";
document.getElementById("divframe").style.left="300";
document.getElementById("divframe").style.top="0px";
document.getElementById("div_iframe").src="/admin/program/selectbackground.jsp?meidaType=SOUND";
document.getElementById("titlename").innerHTML="设置背景音乐";
document.getElementById("divframe").style.display='block';
document.getElementById("massage").style.display='block';
}
function closedivframe(){
document.getElementById("div_iframe").src="/loading.jsp";
document.getElementById('divframe').style.display="none";
}
function addbackgroud(background_title,backgroudPath) 
{ 
form1.bgsoundname.value=background_title; 
form1.bgsoundpath.value=backgroudPath; 
} 
function delbackgroud(){
if(confirm("提示信息：确认删除背景音乐？")){
form1.bgsoundname.value=""; 
form1.bgsoundpath.value=""; 
}
}
function selectMedia(){
if(chkMedia()<1){
alert("请至少选择一个媒体");
return;
}
medias=getMedias();
document.getElementById("addmediaframe").src="/admin/program/addmediaList.jsp?opp=2&moduleid="+checkModule+"&mediaids="+medias;
}
function chkMedia(){
			if(form1.mediaes!=null){
				var num=0;
				for(i=0;i<form1.mediaes.length;i++){
					if(form1.mediaes[i].checked == true) num++; 
				}	
				if(form1.mediaes.checked == true)num++;
				return num;
			} else {
				return 0;
			}
}
function cleanchkMedia(){
	var cbox   = form1.mediaes;
	if(cbox){
	    if(cbox.length){
	    	for(i=0;i<cbox.length;i++){
	    		cbox[i].checked=false;
	    	}
	    }else{
	    	cbox.checked = false;
	    }   
	}
}
function getMedias(){
var mediaids="";
for(i=0;i<form1.mediaes.length;i++){
	if(form1.mediaes[i].checked == true) {
		mediaids +=","+form1.mediaes[i].value;
	}
}
if(form1.mediaes.checked){
mediaids+=","+form1.mediaes.value;
}
return mediaids;
}
function showArea(areaid,moduleid,modulenum){
checkModule=moduleid;
	for(m=1;m<=modulenum;m++){
		if(areaid==m){
			document.getElementById("a_"+m).className="tbals_a2";
			document.getElementById("span_"+m).className="tables_span2";
		}else{
			document.getElementById("a_"+m).className="tbals_a1";
			document.getElementById("span_"+m).className="tables_span1";
		}
	}
	document.getElementById("addmediaframe").src="/admin/program/addmediaList.jsp?opp=0&moduleid="+moduleid;
	showTemplateView(moduleid);
	}
function showTemplateView(moduleid){
document.getElementById("viewTemplate_frame").src="/admin/program/viewTemplateTemp.jsp?templateid=${templateid }&scale=0.43&moduleid="+moduleid;
}



function showimg(event,imgpath){
 event = event || window.event;
document.body.scrollTop = "0px";
document.getElementById("smallImgDiv").style.left=document.body.scrollLeft + event.clientX + 10 + "px";
document.getElementById("smallImgDiv").style.top=document.body.scrollTop + event.clientY + 10 + "px";
document.getElementById("div_img").src=imgpath;
document.getElementById("smallImgDiv").style.display='block';
document.getElementById("smallImgMassage").style.display='block';
}
function hiddenimg(){
document.getElementById('smallImgDiv').style.display='none';
document.getElementById("smallImgMassage").style.display='none';
}
function showlodingimg(event){
fleft=document.body.scrollLeft + event.clientX + 10;
ftop=document.body.scrollTop + event.clientY + 10;
document.body.scrollTop = "0px";
document.getElementById("lodingDiv").style.left=fleft;
document.getElementById("lodingDiv").style.top=ftop;
document.getElementById("lodingDiv").style.display='block';
document.getElementById("lodingMassage").style.display='block';
}
function hiddenlodingimg(){
document.getElementById('lodingDiv').style.display='none';
document.getElementById("lodingMassage").style.display='none';
}
function saveProgram(){
DwrClass.checkProgramMedia('${templateid}',returnCheckResults);
}
function returnCheckResults(result){
if(result=='yes'){
document.getElementById("subid").disabled="disabled";
progressBarOpen();

form1.action="/admin/program/update_programDO.jsp";
form1.submit();
}else{
alert(result);
}
}
function progressBarOpen(){
  			var cWidth = document.body.clientWidth;
  			var cHeight= document.body.clientHeight;
			var divNode = document.createElement( 'div' );	
			divNode.setAttribute("id", "systemWorking");
			var topPx=(cHeight)*0.4-50;
			var defaultLeft=(cWidth)*0.4-50;
			divNode.style.cssText='position:absolute;margin:0;top:'+topPx+'px;left:'+defaultLeft+';width:215;height:59;background-image:url(/images/wait_background.gif);z-index:9999;text-align:center;padding-top:20'; 
			divNode.innerHTML= "<img src='/images/mid_giallo.gif' align=absmiddle><font style='font-size:13px'>&nbsp;正在保存节目，请稍后...</font>";
			document.getElementsByTagName("body")[0].appendChild(divNode);
			 var bgObj=document.getElementById("bgDiv");
 bgObj.style.width = document.body.offsetWidth + "px";
 bgObj.style.height = screen.height + "px";
bgObj.style.display = "block";   		
  }
function cancel(){
if(confirm("提示信息：确认取消修改节目？")){
parent.listtop.location.href="/admin/program/program_list_top.jsp";
window.location.href="/rq/programList?left_menu=&zu_id=no";
}
}
function saveAs(){

}
</script>
	</head>
	<body style="margin: 0px;">
		<form action="" name="form1" method="post">
			<input type="hidden" name="zuid" value="${zuid}" />
			<input type="hidden" name="templateid" value="${templateid}" />
			<input type="hidden" name="jmurl" value="${jmurl}" />
			<input type="hidden" name="bgsoundpath" value="${bgsoundpath}" />
			<table width="100%" height="100%" cellpadding="0" cellspacing="0"
				border="0">
				<tr>
					<td width="210" valign="top" rowspan="2">
						<fieldset class="template_fieldset">
							<legend align="center">
								选择媒体
							</legend>
							<div class="template_div" id="template_div">
<script language="javascript">
cnum=0;
content = new dTree('content'); 
var init_tree = null;
if(init_tree == true)	content.clearCookie();
content.add('1',-1,"我的文件夹","javascript:;",'','','');
<c:forEach var="meidazu" items="${zuList}">
content.add('${meidazu.zu_id}','${meidazu.zu_pth}','<span onmousedown=getmemedia(event,"${meidazu.zu_id}")>${meidazu.zu_name }</span>',"javascript:;",'','','/images/dtreeimg/folder.gif');
</c:forEach> 
<c:set var="sysnum" value="1"></c:set>
content.add('sys0',-1,"系统文件夹","javascript:;",'','','');
<c:forEach var="sysmedia" items="${sysMedias}">
<c:set var="sysnum" value="${sysnum+1}"></c:set>
content.add('sys_${sysnum}','sys0','<span onmousedown=getsysmedia(event,"${fn:split(sysmedia,"#")[0]}","sys_${sysnum}")>${fn:split(sysmedia,"#")[1]}</span>',"javascript:;",'','','/images/dtreeimg/${fn:split(sysmedia,"#")[0]}.gif');
</c:forEach> 
 document.write(content); 
 var selected_content = content.getSelected();
if(selected_content == null  || selected_content == "")
content.s(0);

function getmemedia(event,zuid){
showlodingimg(event);
DwrClass.getMediasBystr(" and xct_zu.zu_id="+zuid+" order by group_num desc,media_title",'${sessionScope.lg_user.lg_name}',zuid,writeMeida)
cnum=zuid;
}
function getsysmedia(event,mediatype,num){
showlodingimg(event);
cnum=num;
DwrClass.getMediasBystr1(" and media_type='"+mediatype+"' order by group_num desc,media_title ",writeMeida);
}
function writeMeida(medias){
hiddenlodingimg();
content.clearCookie();	
for(var i=0;i<medias.length;i++){
	if(medias[i].media_type=='xct_zu'){
	content.add(medias[i].zu_id,medias[i].media_id,'<span onmousedown=getmemedia(event,"'+medias[i].zu_id+'")>'+medias[i].zuname+'</span>',"javascript:;",'','','/images/dtreeimg/folder.gif');
	}else{
		if(medias[i].is_exist==1){
			content.add('media'+medias[i].id,cnum,'<input type=checkbox value="'+medias[i].media_id+'_'+medias[i].media_type+'" name="mediaes" id="c'+medias[i].id+'"/><label for="c'+medias[i].id+'" onmouseover=showimg(event,"'+medias[i].slightly_img_path+medias[i].slightly_img_name+'") onmouseout="hiddenimg()" title="'+medias[i].media_title+'" >'+medias[i].media_title+'</label>','javascript:;','','','/images/dtreeimg/'+medias[i].media_type+'.gif');
		}
	}
}
document.getElementById("template_div").innerHTML=content;
 var selected_content = content.getSelected();
if(selected_content == null  || selected_content == "")
content.s(0);
}
		    </script>
							</div>
							
						</fieldset>
					</td>
					<td background="/images/menu_bg1.gif" width="60"
						style="background-position: right; background-repeat: repeat-y"><br/><br/><br/>
						<a href="javascript:;"  onclick="selectMedia();"><img src="/images/insert_play_list.gif" height="25" width="55" border="0" alt="添加媒体"/></a>
						</td>
					<td valign="top">
						<table border="0"  cellspacing="0" cellpadding="0" width="100%" height="100%">
							<tr>
								<td height="40%" background="/images/menu_bg1.gif"
									style="background-position: right; background-repeat: repeat-y">
									<table cellspacing="0" cellpadding="0"
										style="margin-left: 30px; font-weight: bold" width="80%" border="0">
										<tr>
											<td height="30px" width="100px">
												节目名称：
											</td>
											<td>${programName}
												<input type="hidden" name="programe" maxlength="20"
													class="button" value="${programName}" />
											</td>
										</tr>
										<tr>
											<td height="30px">
												节 目 组：
											</td>
											<td>${zuname}
												<input type="hidden" name="zuname" id="zuname"
													readonly="readonly" class="button" value="${zuname}" />
											</td>
										</tr>
										<tr>
											<td height="30px" width="100px">
												背景音乐：
											</td>
											<td>
												<input type="text" name="bgsoundname" maxlength="20"
													class="button" value="${bgsoundpath}" />
												<img src="/images/image_folder_close.gif" title="设置背景音乐"
													style="cursor: pointer;" width="16" height="16"
													onclick="selectPhoto()" />
												&nbsp;&nbsp;
												<img src="/images/del.gif" title="删除背景音乐"
													style="cursor: pointer;" width="13" height="13"
													onclick="delbackgroud()" />

											</td>

										</tr>
										<tr>
											<td colspan="2" align="center" height="40">
											<input type="button" value=" 取 消 "
													onclick="javascript:cancel();" class="button" />&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="button" value=" 保 存 " id="subid"
													onclick="javascript:saveProgram();" class="button" />&nbsp;&nbsp;&nbsp;&nbsp;
												<!-- <input type="button" value=" 另存为.. "
													onclick="javascript:saveAs();" class="button" /> -->
											</td>
										</tr>
									</table>
								</td>
								<td style=" border-bottom: #6699cc 1 solid;">
									<iframe
										src=""
										scrolling="no" id="viewTemplate_frame" width="350" height="190" frameborder="0"></iframe>
								</td>

							</tr>
							<tr>
							<tr>
								<td colspan="2" height="60%" valign="top">
									<table cellspacing="0" height="100%" cellpadding="0" width="100%" border="0">
										<tr>
											<td valign="top" height="26">
												<div id="tabsF">
													<ul>
														<c:set var="i" value="0" />
														<c:set var="nomoduleid" value="1" />
														<c:forEach var="module" items="${moduleList}">
															<c:set var="i" value="${i+1}" />
															<c:if test="${i==1}">
																<c:set var="nomoduleid" value="${module.id }" />
																<script>
																checkModule=${module.id };
																showTemplateView('${module.id }');
																</script>
															</c:if>
															<li>
																<a href="javascript:;" title="播放区域${i}" id="a_${i}" class="${i==1?'tbals_a2':'tbals_a1' }"
																	onclick="showArea(${i},'${module.id }',${fn:length(moduleList)});cleanchkMedia();"><span id="span_${i}" class="${i==1?'tables_span2':'tables_span1'} ">播放区域${i}</span>
																</a>
															</li>
														</c:forEach>
													</ul>
												</div>
											</td>
										</tr>
											
										<tr>
											<td valign="top" >
											<table cellspacing="0" cellpadding="0" height="100%" width="100%" border="0" class="TitleBackground" >
													<tr >
														<td width="30%" style="padding-left: 30px; font-weight: bold">媒体名称</td>
														<td width="15%" style="padding-left: 0px; font-weight: bold">缩略图</td>
														<td width="15%"  style="padding-left: 0px; font-weight: bold">媒体类型</td>
														<td width="15%" style="padding-left: 10px; font-weight: bold">播放顺序</td>
														<td width="10%" style="padding-left: 10px; font-weight: bold">排序</td>
														<td width="15%" style="padding-left: 10px; font-weight: bold">操作</td>
													</tr>
											</table>
											<iframe src="/admin/program/addmediaList.jsp?opp=0&moduleid=${nomoduleid }" height="220" width="100%" scrolling="yes" id="addmediaframe" name="addmediaframe" frameborder="0"></iframe>
											
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>


					</td>
				</tr>


			</table>

		</form>

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
								name="div_iframe" frameborder="0"></iframe>

						</td>
					</tr>
				</table>
			</div>
		</div>
<div id="smallImgDiv">
			<div id="smallImgMassage">
				<img src="/images/loading2.gif" id="div_img" width="150" height="112" /></div>
</div>
<div id="lodingDiv">
	<div id="lodingMassage">
				<img src="/images/loading2.gif"  width="18" height="18" />加载中...
	</div>
</div>
<div id="bgDiv">
</div>
	</body>