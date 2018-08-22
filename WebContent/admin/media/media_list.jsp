<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
	<head>
		<title>list</title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script language="javascript" src="/js/did_common.js"></script>
		
		<style  type="text/css"> 
.InfoTitle {
	color: #606979; 
	text-align: center; 
	font-weight: bold; 
	font-size: 9pt;
}
.download_a:hover{
	color: blue;
	text-decoration: underline;
}
		#ToolBar {
position:absolute;
bottom:0px;
right:16px;
width:100%;
height:30px;
padding-top:10px;
margin-right:20px;
text-align:center;
background-color:#F5F9FD;
z-index:2;
overflow:hidden;
}
</style>

<script language="JavaScript" type="text/JavaScript">

function cmsPg(strUrl){
	parent.document.ifrm_list.location = strUrl;
}
function newMedia(){
	showDivFrame("�½�ý��","/admin/media/newMedia.jsp","600","350");
}
function space_manager(){
	showDivFrame("���̹���","/admin/media/space_manager.jsp","500","350");
}
function showDivFrame(title,url,fwidth,fheight){
	        //alert("media_list.jsp");
			parent.parent.parent.parent.document.body.scrollTop = "0px";
			parent.parent.parent.parent.document.getElementById("div_iframe2").width=fwidth;
			parent.parent.parent.parent.document.getElementById("div_iframe2").height=fheight;
			parent.parent.parent.parent.document.getElementById("divframe2").style.left=(parent.parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";;
			parent.parent.parent.parent.document.getElementById("divframe2").style.top=(parent.parent.parent.parent.document.body.clientHeight - fheight) / 4+"px";
			parent.parent.parent.parent.document.getElementById("div_iframe2").src=url;
			parent.parent.parent.parent.document.getElementById("titlename2").innerHTML=title;
			parent.parent.parent.parent.document.getElementById("divframe2").style.display='block';
			parent.parent.parent.parent.document.getElementById("massage2").style.display='block';
			parent.parent.parent.parent.document.getElementById("bgDiv").style.visibility='visible';
}

function closedivframe(){

	if(div_iframe.isclose==1){ 

		if(confirm("��ʾ��Ϣ�������ϴ�ý�壬�ر�ҳ�潫ֹͣ�ϴ���ȷ�Ϲرգ�")){
			document.getElementById("div_iframe").src="/loading.jsp";
			document.getElementById('divframe').style.display="none";
		}
	}else{
	   
		document.getElementById("div_iframe").src="/loading.jsp";
		document.getElementById('divframe').style.display="none";
		parent.parent.parent.parent.closedivframe();
	}
}

function closedivframe1(){
	document.getElementById('divframe').style.display="none";
}

function all_chk(sel_all){
	var ckform = document.fm1;
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
function gopage(left_menu,zu_id,type,pagenum){
window.location.href="/rq/mediaList?left_menu="+left_menu+"&zu_id="+zu_id+"&type="+type+"&pagenum="+pagenum;
}
function download_media(filepath){
window.location.href="/admin/media/download.jsp?p="+filepath;
}
function update_type(media_id){
showDivFrame("�ƶ�ý��","/admin/media/update_type.jsp?mid="+media_id,"300","260");
}
function view_media(media_title,media_type,filepath){

	showDivFrame("Ԥ��ý�塾"+media_title+"��","/admin/media/view_media.jsp?media_type="+media_type+"&filepath="+filepath,"800","500");
}
</script>
		<style type="text/css">
#divframe {
	position: absolute;
	z-index: 999;
	filter: dropshadow(color = #666666, offx = 3, offy = 3, positive = 2);
	display: none;
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
	display: none;
}

#massage {
	border: #6699cc solid;
	border-width: 1 1 1 1;
	background: #fff;
	color: #036;
	font-size: 12px;
	line-height: 150%;
	display: none;
}

.header {
	background: url(/images/device/btn_background.gif);
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	padding: 3 5 0 5;
	cursor: move;
}
</style>
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<form name='fm1'>
 <div style="overflow: auto;height: 100%;position:absolute;width: 100%">
<c:if test="${empty requestScope.medias}">
<br/>
<center>����ý���ļ���</center>
</c:if>
			<c:forEach var="media" items="${requestScope.medias}">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" >
					<tr >
						<td>
							<table width="100%" height="27" border="0" cellpadding="0"
								cellspacing="0">
								<tr   class="TableTrBg06_" onmouseover="this.className='TableTrBg06'" onmouseout="this.className='TableTrBg06_'">
									<td width="10">
										&nbsp;
									</td>
									<td width="15">
									<c:if test="${sessionScope.lg_user.lg_name==media.userid || sessionScope.lg_user.lg_role==1}">
										<input type='checkbox' name='checkbox' value="${media.media_id}" />
									</c:if>
									</td>
									<td width="120" align="center">
										<table width="86" height="86" border="0" cellpadding="0"
											cellspacing="1" background="/images/cms/aa">
											<tr>
												
													<c:choose>
														<c:when test="${media.is_exist==1}">
														<td valign="top">
															<table width="80" height="84" border="0" cellpadding="0"
																cellspacing="0">
																<tr>
																	<td width="10">
																		&nbsp;
																	</td>
																	<td>
																		<img
																			src="${media.slightly_img_path }${media.slightly_img_name }"
																			border="0" alt="" width="100" height="80" />
																	</td>
																</tr>
															</table>
															</td>
														</c:when>
														<c:otherwise>
														<td valign="middle">
															<span style="color: red">�ļ������ڣ�</span>
															</td>
														</c:otherwise>
													</c:choose>
											</tr>
										</table>
									</td>
									<td width="25">
										&nbsp;
									</td>
									<td width="200" align="left" style="word-break: break-all;${media.is_exist==0?'color: red':''}">
										<strong>${media.media_title}</strong>
									</td>
									<td width="450">
										<table border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="250">
													<table border="0" cellspacing="1" cellpadding="0">
													<tr>
															<td>
																<img src="/images/bullet_01.gif">
																�ϴ��û�
															</td>
															<td >
																:&nbsp;${media.username }
															</td>
															<td>

															</td>
														</tr>
														<tr>
															<td>
																<img src="/images/bullet_01.gif">
																�ļ����
															</td>
															<td >
																:&nbsp;${media.zuname }
															</td>
															<td>

															</td>
														</tr>
														<tr>
															<td>
																<img src="/images/bullet_01.gif">
																�ļ�����
															</td>
															<td>
																:&nbsp;${media.media_type }
															</td>
															<td>
																
															</td>
														</tr>
														<c:if test="${media.media_type=='MOVIE'}">
														<tr>
															<td>
																<img src="/images/bullet_01.gif">
																����ʱ��
															</td>
															<td>
																:&nbsp;
																<fmt:formatNumber type="number" value="${(media.m_play_time -media.m_play_time%(1000*60))/(1000*60)}" pattern="0"/>:
																<fmt:formatNumber type="number" value="${media.m_play_time/1000%60}" pattern="0"/>
															</td>
															<td></td>
														</tr>
														</c:if>
													</table>
												</td>
												<td width="5">
													&nbsp;
												</td>
												<td width="*" valign="top">
													<table border="0" cellspacing="1" cellpadding="0">
														<tr>
															<td>
																<img src="/images/bullet_01.gif">
																�ļ���С
															</td>
															<td>
																:&nbsp;
																<c:choose>
																	<c:when test="${media.file_size>1024*1024}">
																		<fmt:formatNumber type="number" value="${media.file_size/1024/1024}" pattern="0.0"/>M  
																	</c:when>
																	<c:otherwise>
																	<fmt:formatNumber type="number" value="${media.file_size/1024}" pattern="0.0"/>K
																	</c:otherwise>
																</c:choose>
															</td>
															<td></td>
														</tr>

														<tr>
															<td>
																<img src="/images/bullet_01.gif">
																�ļ���ʽ
															</td>
															<td>
																:&nbsp;${fn:split(media.file_name,".")[1]}
															</td>
															<td></td>
														</tr>
														<tr>
															<td>
																<img src="/images/bullet_01.gif">
																���ʱ��
															</td>
															<td>
																:&nbsp;${media.create_date }
															</td>
															<td></td>
															
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
									<td width="50" align="left" style="word-break: break-all">
									<c:if test="${media.is_exist==1}">
										<c:if test="${media.media_type=='MOVIE'||media.media_type=='IMAGE'||media.media_type=='SOUND'||media.media_type=='FLASH'||media.media_type=='TEXT'||media.media_type=='WEB'}">
											<a href="javascript:;" class="download_a" onclick="view_media('${media.media_title}','${media.media_type}','${media.file_path}${media.file_name}')">Ԥ��</a>
											<br/><br/>
										</c:if>
												<a href="javascript:;" class="download_a" onclick="download_media('${media.filePathEncrypt}')">����</a>
										</c:if>
									</td>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="Line_01"></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</c:forEach>

		</form>
<div style="height: 50px"></div>
</div>

		<!---------------------------------------------    Page start      -------------------------------------->
		<c:if test="${pager.totalPage>0}">
		<div id="ToolBar">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
			
				<td align="center" valign="top">
				�ܹ���<span style="color: blue">${media_sum}</span>����ý��
                  <a href="javascript:;" onclick="gopage('${left_menu }','${zu_id }','${type }',1);return false;"><img src="/images/btn_first.gif" border="0"/></a>&nbsp;
				  <a href="javascript:;" onclick="gopage('${left_menu }','${zu_id }','${type }','${pager.currentPage-1 }');return false;"><img src="/images/btn_pre.gif" border="0"/></a>&nbsp; ${pager.currentPage }&nbsp; 
                  <a href="javascript:;" onclick="gopage('${left_menu }','${zu_id }','${type }','${pager.currentPage+1 }');return false;"><img src="/images/btn_next.gif" border="0"/></a>&nbsp; 
                  <a href="javascript:;" onclick="gopage('${left_menu }','${zu_id }','${type }','${pager.end }');return false;"><img src="/images/btn_end.gif" border="0"/></a>&nbsp;
                  ��${pager.totalPage }ҳ  
				</td>
				<td width="100px">&nbsp;</td>
			</tr>
		</table>
		</div>
		</c:if>
		<br/><br/><br/>
		<!---------------------------------------------    Page End      ---------------------------------------->
		<div id="divframe">
			<div id="massage">
				<table cellpadding="0" cellspacing="0">
					<tr height="30px;" class=header  onmousedown=MDown(divframe)>
						<td align="left" style="font-weight: bold">
							<span id="titlename"></span>
						</td>
						<td height="5px" align="right">
							<a href="javaScript:void(0);" style="color: #000000"
								onclick="closedivframe();">�ر�</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<iframe src="/loading.jsp" scrolling="no" id="div_iframe"
								name="div_iframe" frameborder="0" width="600" height="330"></iframe>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>
