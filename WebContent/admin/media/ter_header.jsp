<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String title = request.getParameter("title");
	String left_menu = request.getParameter("left_menu");
	String zu_id = request.getParameter("zu_id");
	String type = request.getParameter("type") == null ? "" : request.getParameter("type");
%>
<html>
	<head>
		<title>My JSP 'ter_header.jsp' starting page</title>
		<script language="javascript" src="/js/vcommon.js"></script>
		<script type="text/javascript" src="/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script language="JavaScript" src="/js/prototype.js"></script>
		<script language="JavaScript" src="/js/did_common.js"></script>
		<script language="JavaScript" src="/js/common.js"></script>
		<script type="text/javascript">

function chkNum(){
			if(content.document.fm1 && content.document.fm1.checkbox!=null){
				var forms = content.fm1;
				var num=0;
			
				for(i=0;i<forms.checkbox.length;i++){
					if(forms.checkbox[i].checked == true) num++; 
				}	
				if(forms.checkbox.checked == true)num++;
				return num;
			} else {
				return 0;
			}
		}

function getCheckIPs(){
	var forms = content.document.fm1
	var checkIPs="";
	for(i=0;i<forms.checkbox.length;i++){
		if(forms.checkbox[i].checked == true)  {
			checkIPs+="!"+forms.checkbox[i].value;
		}
	}if(forms.checkbox.checked == true){
	checkIPs+="!"+forms.checkbox.value;
	}
	return checkIPs;
}
function newMedia(){
	content.newMedia();
}
function deleteMedia(){
	var num = chkNum();
	if(num<1){
		alert("请选择需要删除的媒体！");
		return;
	}
	if(confirm("提示信息：确认删除选中媒体？")){
		var checkIPs= getCheckIPs();
		var rqurl=escape(content.location.href);
		document.getElementById("header_iframe").src="/admin/media/deleteMedia.jsp?mediaIds="+checkIPs+"&rqurl="+rqurl;
	}

}
function moveMedia(){
	var num = chkNum();
	if(num<1){
		alert("请选择需要移动的媒体！");
		return;
	}
	var checkIPs= getCheckIPs();
	content.showDivFrame("移动媒体","/admin/media/update_type.jsp?mediaIds="+checkIPs,"300","260");
}

function serchMedia(){
var mediastr=document.getElementById("mediastr").value;
var startdate=headForm.startdate.value;
var enddate=headForm.enddate.value;
content.location.href="/admin/media/searchMedia.jsp?mediastr="+mediastr+"&startdate="+startdate+"&enddate="+enddate;
}
function space_manager(){
content.space_manager();
}
function downvlc(){
 window.location.href="/download_vlc.jsp";
}

function goKeypress(){
			if (event.keyCode == 13)
				serchMedia();
		}
</script>

	</head>

	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<form name="headForm" id="headForm" method="post"
			onsubmit="return false;">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="30px">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
							background="/images/device/btn_background.gif">
							<tr>
								<td>
									<table 
										border="0" cellspacing="0" cellpadding="0">
										<tr>
										<c:set var="str" value="${lg_authority}"/>
	            							<c:if test="${fn:contains(str,'L')}">
												<td id="new" onmouseout="MM_swapBgImgRestore()"
													onmouseover="MM_swapBgImage('new','','/images/device/btn_vnc_over.gif',1)"
													background="/images/device/btn_vnc.gif" width="114"
													height="30" align="center" onclick="newMedia();"
													style="cursor:hand">
													&nbsp;&nbsp;&nbsp;
													<span style="font-size:8pt;"> 新建媒体 </span>
												</td>
											</c:if>
											<%--<td id="edit" onmouseout="MM_swapBgImgRestore()" 
			onmouseover="MM_swapBgImage('edit','','/images/btn_edit_over.gif',1)" 
			background="/images/btn_edit.gif" width="70" height="30" align="center" onclick="goInsert();" style="cursor:hand">
			&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size:8pt;" >
			编辑
			</span></td>
			--%>
								<c:if test="${fn:contains(str,'M')}">
											<td id="del" onmouseout="MM_swapBgImgRestore()"
												onmouseover="MM_swapBgImage('del','','/images/device/btn_vnc_over.gif',1)"
												background="/images/device/btn_vnc.gif" width="114"
												height="30" align="center" onclick="deleteMedia();"
												style="cursor:hand">
												&nbsp;&nbsp;&nbsp;
												<span style="font-size:8pt;"> 删除媒体 </span>
											</td>
											<td id="move" onmouseout="MM_swapBgImgRestore()"
												onmouseover="MM_swapBgImage('move','','/images/device/btn_vnc_over.gif',1)"
												background="/images/device/btn_vnc.gif" width="114"
												height="30" align="center" onclick="moveMedia();"
												style="cursor:hand">
												&nbsp;&nbsp;&nbsp;
												<span style="font-size:8pt;"> 移动媒体 </span>
											</td>
											</c:if>
											<c:if test="${sessionScope.lg_user.lg_role==1}">
												<td id="me" onmouseout="MM_swapBgImgRestore()"
													onmouseover="MM_swapBgImage('me','','/images/device/btn_vnc_over.gif',1)"
													background="/images/device/btn_vnc.gif" width="114"
													height="30" align="center" onclick="space_manager();"
													style="cursor:hand">
													&nbsp;&nbsp;&nbsp;
													<span style="font-size:8pt;"> 磁盘信息 </span>
												</td>
											</c:if>
											<td width="1" height="30" ></td>
										</tr>
									</table>

								</td>
								<td align="right" width="*">
									<table border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align='right'>上传时间:
												<input class=" button" size="8" type="text"
													name="startdate" onfocus="new WdatePicker(this)"
													 />~
												<input class=" button" size="8" type="text"
													name="enddate" onfocus="new WdatePicker(this)"
													 />
											</td>
											<td align='right'>&nbsp;&nbsp;&nbsp;标题:
												<input type="text" id="mediastr" name="mediastr" size="10"
													value="" maxlength="100"  onkeypress="goKeypress();">
											</td>
											<td>
												&nbsp;
											</td>
											<td width="60" height="18" align='center'
												onclick="serchMedia();"
												background='/images/device/btn_search_blk.gif'
												style='cursor:hand'>
												<span style="font:7pt;color:white;"> 搜 索 </span>
											</td>

										</tr>
									</table>
								</td>
							</tr>
						</table>
	</td>
  </tr>
  <tr>
    <td height="25px" >
          <table width="100%" height="32" border="0" cellspacing="0" cellpadding="0" class="TitleBackgroundBg">
				<tr>
					<td class="TitleSmall">
						&nbsp;&nbsp;
						<%
							if ("MOVIE".equals(left_menu)) {
								out.println("媒体库 > 媒体");
							} else if ("TYPE_ZU".equals(left_menu)) {
								out.println("媒体库 > 类别");
							} else if ("TITLE_ASC".equals(left_menu)) {
								out.println("媒体库 > 名称排序 > 升序");
							} else if ("TITLE_DESC".equals(left_menu)) {
								out.println("媒体库 > 名称排序 > 降序");
							} else {
								out.println("媒体库 > ");
							}
						%>
					</td>
				</tr>
			</table>
	</td>
  </tr>
  <tr>
    <td height="25px"><table width="100%" border="0" height="100%" cellspacing="0" cellpadding="0"
							class="TitleBackgroundBg">
							<tr>
								<td class="TabBackground">
									<table border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="100" height="25" border="0" id="btn_monitoring"
												align="center" background='/images/device/tab_01_over.gif'>
												<div style='cursor:hand'>
													媒体列表
												</div>
											</td>
										</tr>
									</table>
								</td>
								<td  width="30%">
									<a href="javascript:;" style="color: #1C8EF6" title="点击下载" onclick="downvlc()">视频预览工具：点击下载（下载后默认安装）</a>
								</td>
							</tr>
						</table>
		</td>
  </tr>
  <tr>
    <td height="30px">
        <iframe src="/admin/media/media_list_top.jsp" width="100%" height="30" scrolling="no" name="listtop" frameborder="0"></iframe>
    </td>
  </tr>
  <tr>
    <td valign="top">
	    <iframe  name="content" src="/rq/mediaList?left_menu=<%=left_menu%>&zu_id=<%=zu_id%>&type=<%=type%>" frameBorder="0" height="100%" scrolling="no" width="100%" >
		</iframe>
    </td>
  </tr>
</table>
</form>
		<div style="display: none;">
			<iframe src="/loading.jsp" height="0px" scrolling="no" id="header_iframe" name="header_iframe" frameborder="0"></iframe>
		</div>

	</body>
</html>
