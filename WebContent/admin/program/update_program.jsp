<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO" />
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO" />
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO" />
<jsp:directive.page import="com.xct.cms.domin.Program"/>
<jsp:directive.page import="com.xct.cms.domin.Media"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Template"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
 	ProgramDAO programdao = new ProgramDAO();
 	ModuleDAO moduledao = new ModuleDAO();
 	TerminalDAO terminaldao = new TerminalDAO();
 	TemplateDAO templatedao= new TemplateDAO();
 	String templatefile = request.getParameter("templatefile");
 	String opp=request.getParameter("opp")==null?"0":request.getParameter("opp");
 	Users user=(Users)request.getSession().getAttribute("lg_user");
 	if(null==user){
 	     return ;
 	 }
 	DBConnection dbconn= new DBConnection();
 	Connection conn=dbconn.getConection();
 	Program program=programdao.getProgram1(conn,"and  program_JMurl='"+templatefile+"'");
 	if(program==null){
 %>
	<script type="text/javascript">
		<!--
		parent.closedivframe(2);
		if(parent.homeframe.content.content.location){
			parent.homeframe.content.content.location.reload();
		}
		alert("无效的节目！");
		//-->
   </script>
	
	<%
			}if("0".equals(opp)&&program.getProgram_ISloop()==1){
		%>
	<!--<script type="text/javascript">
		<!--
		parent.closedivframe(2);
		if(parent.homeframe.content.content.location){
			parent.homeframe.content.content.location.reload();
		}
		alert("当前节目正在被修改，已锁定！");
		//-->
	</script>  -->
	
	<%
		}
		String programName=program.getProgram_Name();
		String zuname=program.getZu_name();
		String jmurl=program.getProgram_JMurl();
		String templateid = program.getTemplateid();
		List zuList = terminaldao.getAllZu(conn,"where zu_type=1 and  zu_username like '%"+user.getLg_name()+"||%' and zu_pth=1");
		//System.out.println("where zu_type=1 and zu_username like '%"+user.getLg_name()+"||%' and zu_pth=1");
		List moduleList = moduledao.getModuleTempByTemplateId(conn,templateid);
		Template template=templatedao.getTemplateTempById(conn,templateid); 
		
		/////////////////////锁定当前节目
		UtilDAO utildao= new UtilDAO();
		Map map=UtilDAO.getMap();
		map.put("program_JMurl",templatefile);
		map.put("program_ISloop","1");
		utildao.updateinfo(conn,map,"xct_JMPZ");
	///////////////////////////////	
		List<com.xct.cms.domin.Module>modulelist=new ModuleDAO().getModuleTempByTemplateIdForBackimge(templateid);
		for(com.xct.cms.domin.Module module:modulelist){
		  if(module.getM_filetype().equals("marketstock")){
		  	    request.setAttribute("marketstockbackimg", module.getAlpha());
		  }
		  if(module.getM_filetype().equals("htmltext")){
		      	request.setAttribute("htmltextbackimg", module.getAlpha());
		  }
		   if(module.getM_filetype().equals("goldtrend")){
		      	request.setAttribute("ch_en", module.getAlpha());
		  }
		}
		///////////////////////////////	
		
		dbconn.returnResources(conn);
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
		<title>My JSP 'update_program.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>
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
    background-color: #cccccc;
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
document.getElementById("divframe").style.left="200";
document.getElementById("divframe").style.top="110px";
document.getElementById("div_iframe").src="/admin/program/selectbackground.jsp?meidaType=SOUND";
document.getElementById("titlename").innerHTML="设置背景音乐";
document.getElementById("divframe").style.display='block';
document.getElementById("massage").style.display='block';
}
function closeBackGroundDiv(){
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
	document.getElementById("addmediaframe").src="/admin/program/addmediaList.jsp?opp=0&moduleid="+moduleid+"&templateid=${templateid}";
	showTemplateView(moduleid);
	}
function showTemplateView(moduleid){
document.getElementById("viewTemplate_frame").src="/admin/program/viewTemplateTemp.jsp?templateid=${templateid }&scale=0.37&moduleid="+moduleid+"&templateid=${templateid}";
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
		form1.action="/admin/program/update_programDO.jsp?templateid=${templateid}";
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
			divNode.style.cssText='position:absolute;margin:0;top:'+topPx+'px;left:'+defaultLeft+';width:215;height:59;background-image:url(/images/wait_background.gif);z-index:9999;text-align:center;'; 
			divNode.innerHTML= "<div style='margin-top:20px'><img src='/images/mid_giallo.gif' align=absmiddle><font style='font-size:13px;'>&nbsp;正在保存节目，请稍后...</font></div>";
			document.getElementsByTagName("body")[0].appendChild(divNode);
			var bgObj=document.getElementById("bgDiv");
			bgObj.style.width = document.body.offsetWidth + "px";
			bgObj.style.height = screen.height + "px";
			bgObj.style.display = "block";   		
  }
function cancel(){
parent.closedivframe3();
}
function saveAs(){

}
function closedivframe(){
	if(confirm("提示信息：节目未保存，确认取消？")){
		DwrClass.updateProgramStatus('${jmurl}','0');
		parent.closedivframe(2);
	}
}
function updateTemplate(jmurl,templateid,programname){
	var cnt_height=parent.parent.parent.parent.document.body.clientHeight*0.8;
	var cnt_width=parent.parent.parent.parent.document.body.clientWidth*0.6;
	parent.showDiv("修改节目 [ "+programname+" ] 的模板",cnt_width,cnt_height,"/admin/program/update_programTemplate.jsp?jmurl="+jmurl+"&templateid="+templateid+"&programname="+programname);
}

function closeGetBackGroundImageDiv(imgtitle,imgpath){
		    //alert(imgtitle+"_update_program__"+imgpath);
		
		   if(imgtitle=="htmltext"){
		     document.getElementById("htmltextbackimg").value=imgpath;
		     //alert(document.getElementById("htmltextbackimg").value);
		    }
		   if(imgtitle=="marketstock"){
		       document.getElementById("marketstockbackimg").value=imgpath;
			//alert(document.getElementById("marketstockbackimg").value);
			}
}

function changech_en(string){
		  document.getElementById("ch_en").value=strtemp;
}
</script>
	</head>
	<body style="margin: 0px;">
		<form action="" name="form1" method="post">
			<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td width="260px" valign="top" background="/images/menu_bg1.gif"
									style="background-position: right; background-repeat: repeat-y">
						<table border="0"  cellspacing="0" cellpadding="0" width="100%" height="100%">
							<tr>
								<td height="170px">
									<table cellspacing="0" cellpadding="0"
										style="margin-left: 10px; font-weight: bold" width="90%" border="0">
										<%--<tr>
										    <td colspan="2" height="12px" width="220px">
												<font color="red" size="2">注：节目名称修改后，建议此节目重新发送一次，和终端保持一致！</font>
											</td>
										</tr>
										--%><tr>
											<td height="30px" width="70px">
												节目名称：
											</td>
											<td width="100px">
												<input type="text" name="programe" maxlength="20" readonly="readonly"
													class="button" value="${programName}" />
											</td>
										</tr>
										<tr>
											<td height="30px"  width="70px">
												节 目 组：
											</td>
											<td>${zuname}
												<input type="hidden" name="zuname" id="zuname"
													readonly="readonly" class="button" value="${zuname}" />
											</td>
										</tr>
										<tr>
											<td height="30px"  width="70px">
												背景音乐：
											</td>
											<td>
												<input type="text" name="bgsoundname" maxlength="20"
													class="button" value="${bgsoundpath}" style="width: 80px" />
												<img src="/images/dtreeimg/SOUND.gif" title="设置背景音乐"
													style="cursor: pointer;" width="16px" height="16px"
													onclick="selectPhoto()" />
												&nbsp;&nbsp;
												<img src="/images/del.gif" title="删除背景音乐"
													style="cursor: pointer;" width="13px" height="13px"
													onclick="delbackgroud()" />
											</td>
										</tr>
										<tr>
											<td colspan="2" align="center" height="40px">
											<input type="button" value=" 取 消 "
													onclick="javascript:closedivframe();" class="button1" />&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="button" value=" 保 存 " id="subid"
													onclick="javascript:saveProgram();" class="button1" />&nbsp;&nbsp;&nbsp;&nbsp;
												<!-- <input type="button" value=" 另存为.. "
													onclick="javascript:saveAs();" class="button" /> -->
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
							  <td valign="top" align="center" >
								<fieldset style="width: 97%;border: #6699cc 1 solid; ">
								<legend align="center">模板预览效果</legend>
									<iframe
										src=""
										scrolling="no" id="viewTemplate_frame" name="viewTemplate_frame" width="243" height="190" frameborder="0"></iframe>
								</fieldset>
								<input type="button" value=" 修改模板 " 
													onclick="javascript:updateTemplate('${jmurl}','${templateid}','${programName}');" class="button1" />&nbsp;&nbsp;&nbsp;&nbsp;
								</td>

							</tr></table>


					</td>

					<td valign="top">
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
														<td width="15%" style="padding-left: 10px; font-weight: bold">操作</td>
													</tr>
											</table>
											<iframe src="/admin/program/addmediaList.jsp?opp=0&moduleid=${nomoduleid }&templateid=${templateid}" height="100%" width="100%" scrolling="auto" id="addmediaframe" name="addmediaframe" frameborder="0"></iframe>
											
											</td>
										</tr>
									</table>
					</td>
				</tr>
			</table>
            <input type="hidden" name="zuid" value="${zuid}" />
			<input type="hidden" name="templateid" value="${templateid}"/>
			<input type="hidden" name="jmurl" value="${jmurl}" />
			<input type="hidden" name="bgsoundpath" value="${bgsoundpath}"/>
			<input type="hidden" id="htmltextbackimg" name="htmltextbackimg" value="${htmltextbackimg }"/>
			<input type="hidden" id="marketstockbackimg" name="marketstockbackimg" value="${marketstockbackimg }"/>
			<input type="hidden" id="ch_en" name="ch_en" value="${ch_en }"/>
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
								onclick="closeBackGroundDiv();">关闭</a>
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
<div id="bgDiv">
</div>
	</body>