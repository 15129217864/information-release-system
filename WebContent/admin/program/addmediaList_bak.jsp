<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO" />
<jsp:directive.page import="com.xct.cms.utils.UtilDAO" />
<jsp:directive.page import="com.xct.cms.domin.Module" />
<jsp:directive.page import="com.xct.cms.dao.MediaDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Media"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	String opp = request.getParameter("opp");
	String moduleid = request.getParameter("moduleid") == null ? "0"
			: request.getParameter("moduleid");
	String mediaids = request.getParameter("mediaids");
	String save_state=request.getParameter("save_state")==null?"":request.getParameter("save_state");
	request.setAttribute("save_state",save_state);
	UtilDAO utildao = new UtilDAO();
	String nowtime=UtilDAO.getNowtime("yyyy年MM月dd日");
	ModuleDAO moduledao = new ModuleDAO();
	Module module = moduledao.getModuleTempByModuleid(Integer
			.parseInt(moduleid));
	if ("2".equals(opp)) {
		String mediatype = module.getM_filetype();
		String mediatypeEN = module.getM_filetypeEN();
		String m_filetypeZN = module.getM_filetypeZN();
		if ("".equals(mediatype)) {
			out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
			out.print("<script>");
			out.print("alert('提示信息：请先选择该播放区域的播放类型.');");
			out.print("document.getElementById('loadid').style.display='none'");
			out.print("</script>");
		}else{
			String mediaArray[] = mediaids.split(",");
			boolean bool = true;
			boolean bool1=true;
			for (int i = 1; i < mediaArray.length; i++) {
				String mtype = mediaArray[i].split("_")[1];////【0】媒体ID，【1】媒体类型
				if (!mtype.equals(mediatypeEN) && "scroll".equals(mediatype)) {
					out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
					out.print("<script>");
					out.print("alert('提示信息：该播放区域的播放类型为：" + m_filetypeZN+ "，只能导入文本文件');");
					out.print("document.getElementById('loadid').style.display='none'");
					out.print("</script>");
					bool = false;
					bool1 = false;
					break;
				}else if (!mtype.equals(mediatypeEN) && "htmltext".equals(mediatype)) {
					out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
					out.print("<script>");
					out.print("alert('提示信息：该播放区域的播放类型为：" + m_filetypeZN+ "，只能导入网页文件');");
					out.print("document.getElementById('loadid').style.display='none'");
					out.print("</script>");
					bool = false;
					bool1 = false;
					break;
				}else if (!mtype.equals(mediatypeEN)) {
					out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
					out.print("<script>");
					out.print("alert('提示信息：该播放区域的播放类型为：" + m_filetypeZN+ "，只能播放" + m_filetypeZN + "文件');");
					out.print("document.getElementById('loadid').style.display='none'");
					out.print("</script>");
					bool = false;
					bool1 = false;
					break;
				}
			}if(bool1){
				if("scroll".equals(mediatype)){
					String mediaid=mediaids.split(",")[1].split("_")[0];
					MediaDAO mediadao= new MediaDAO();
					Media me=mediadao.getMediaBy(mediaid);
					String scrollcontent=Util.readfile(FirstStartServlet.projectpath+me.getFile_path()+me.getFile_name());
					if(scrollcontent.length()>1000){
						out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
						out.print("<script>");
						out.print("alert('提示信息：滚动文本只能为1000字以内');");
						out.print("document.getElementById('loadid').style.display='none'");
						out.print("</script>");
						bool = false;
					
					}else{
					request.setAttribute("isscroll","1");
					request.setAttribute("scrollcontent",scrollcontent);
					}
				}if("htmltext".equals(mediatype)){
					String mediaid=mediaids.split(",")[1].split("_")[0];
					MediaDAO mediadao= new MediaDAO();
					Media me=mediadao.getMediaBy(mediaid);
					String scrollcontent=Util.readfile(FirstStartServlet.projectpath+me.getFile_path()+me.getFile_name());
					request.setAttribute("ishtmltext","1");
					request.setAttribute("htmltextcontent",scrollcontent);
				}
				if("flash".equals(mediatype)){
					int flashnum=1;
					if(mediaArray.length>2){
						flashnum=2;
					}else{
						List mediaList1=moduledao.getMediaByModuleId(Integer.parseInt(moduleid));
						for(int i=0;i<mediaList1.size();i++){
							Module module1=(Module)mediaList1.get(i);
							if("FLASH".equals(module1.getMedia_type())){
								flashnum++;
							}
						}
					}
					if(flashnum>1){
						out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
						out.print("<script>");
						out.print("alert('提示信息：FLASH播放类型只能播放一个FLASH文件，如需更换FLASH，请删除当前FLASH');");
						out.print("document.getElementById('loadid').style.display='none'");
						out.print("</script>");
						bool = false;
					}
				}else if("word".equals(mediatype)){
					int flashnum=1;
					if(mediaArray.length>2){
							flashnum=2;
					}else{
						List mediaList1=moduledao.getMediaByModuleId(Integer.parseInt(moduleid));
						for(int i=0;i<mediaList1.size();i++){
							Module module1=(Module)mediaList1.get(i);
							if("WORD".equals(module1.getMedia_type())){
								flashnum++;
							}
						}
					}
					if(flashnum>1){
						out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
						out.print("<script>");
						out.print("alert('提示信息：WORD播放类型只能播放一个WORD文件，如需更换WORD，请删除当前WORD');");
						out.print("document.getElementById('loadid').style.display='none'");
						out.print("</script>");
						bool = false;
					}
				}else if("ppt".equals(mediatype)){
					int flashnum=1;
					if(mediaArray.length>2){
						flashnum=2;
					}else{
						List mediaList1=moduledao.getMediaByModuleId(Integer.parseInt(moduleid));
						for(int i=0;i<mediaList1.size();i++){
							Module module1=(Module)mediaList1.get(i);
							if("PPT".equals(module1.getMedia_type())){
								flashnum++;
							}
						}
					}
					if(flashnum>1){
						out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
						out.print("<script>");
						out.print("alert('提示信息：PPT播放类型只能播放一个PPT文件，如需更换PPT，请删除当前PPT');");
						out.print("document.getElementById('loadid').style.display='none'");
						out.print("</script>");
						bool = false;
					}
				}else if("excel".equals(mediatype)){
					int flashnum=1;
					if(mediaArray.length>2){
						flashnum=2;
					}else{
						List mediaList1=moduledao.getMediaByModuleId(Integer.parseInt(moduleid));
						for(int i=0;i<mediaList1.size();i++){
							Module module1=(Module)mediaList1.get(i);
							if("EXCEL".equals(module1.getMedia_type())){
								flashnum++;
							}
						}
					}
					if(flashnum>1){
						out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
						out.print("<script>");
						out.print("alert('提示信息：EXCEL播放类型只能播放一个EXCE文件，如需更换EXCE，请删除当前EXCE');");
						out.print("document.getElementById('loadid').style.display='none'");
						out.print("</script>");
						bool = false;
					}
				}
			}
			if (bool) {
				
				for (int i = 1; i < mediaArray.length; i++) {
					String mid = mediaArray[i].split("_")[0]; ////【0】媒体ID，【1】媒体类型
					String sequence=moduledao.getSequenceBymid(moduleid)+"";
					Map map = utildao.getMap();
					map.put("module_id", moduleid);
					map.put("media_id", mid);
					map.put("type", "0");
					map.put("sequence",sequence);
					utildao.saveinfo(map, "xct_module_media");
				}
			}
		}
	} else if ("3".equals(opp)) {
		String mid = request.getParameter("mid");
		utildao.deleteinfo("id", mid, "xct_module_media");
	} else if ("4".equals(opp)) {
		String mediatype = request.getParameter("mediatype");
		String result=moduledao.checkModuleType(Integer.parseInt(moduleid),mediatype,module.getTemplate_id());
			if(!"ok".equals(result)){
				out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;正在验证播放类型...</center>");
				out.print("<script>");
				out.print("alert('提示信息："+result+"');");
				out.print("document.getElementById('loadid').style.display='none'");
				out.print("</script>");
			}else{
			String m_other="";
			String m_text=" ";
			String name="";
			String background="-16776961";
			String foreground="-1";
			String scroll_span="8000";
			String scroll_family="宋体";
			String scroll_style="1";
			String scroll_size="20";
			String scroll_alpha="1.0";
			String week=UtilDAO.getweek();
			if("weather".equals(mediatype)){
				m_other="weather"+moduleid+".xml#"+nowtime+week+"#a0.png##晴#0℃#0℃";
			}else if("clock".equals(mediatype)){
				m_other="#ff0000";
				name="时钟"+moduleid;
				foreground="-65536";
			}else if("scroll".equals(mediatype)){
				m_other="scroll"+moduleid+".txt#ffffff##0000ff";
				name="滚动文字"+moduleid;
				 background="-16776961";
				 foreground="-1";
				 scroll_span="20";
				 scroll_family="宋体";
				 scroll_style="0";
				 scroll_size="40";
			}else if("iptv".equals(mediatype)){
				m_other="iptv"+moduleid+".txt#http://";
				name="流媒体"+moduleid;
			}else if("web".equals(mediatype)){
				m_other="web"+moduleid+".txt#http://";
				name="网页"+moduleid;
			}else if("htmltext".equals(mediatype)){
				m_other="htmltext"+moduleid+".html";
				name="滚动文本"+moduleid;
			}
			Map map = utildao.getMap();
			map.put("id", moduleid);
			map.put("m_filetype", mediatype);
			map.put("m_other",m_other);
			map.put("m_text",m_text);
			map.put("name",name);
			map.put("background",background);
			map.put("foreground",foreground);
			map.put("span",scroll_span);
			map.put("alpha",scroll_alpha);
			map.put("fontName",scroll_family);
			map.put("fontTyle",scroll_style);
			map.put("fontSize",scroll_size);
			map.put("state","0");
			utildao.updateinfo(map, "xct_module_temp");
			utildao.deleteinfo("module_id",moduleid,"xct_module_media");
			out.print("<script>");
			out.print("parent.cleanchkMedia();");
			out.print("</script>");
			module = moduledao.getModuleTempByModuleid(Integer
			.parseInt(moduleid));
			}
	}
	List mediaList = moduledao.getMediaByModuleId(Integer.parseInt(moduleid));
	request.setAttribute("moduleid",moduleid);
	request.setAttribute("mediaList", mediaList);
	request.setAttribute("module", module);
	request.setAttribute("m_filetype", module.getM_filetype());
%>
<html>
	<head>
		<title>My JSP 'addmediaList.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		 <script language="javascript" src="/js/vcommon.js"></script>
		<style type="text/css">
#colorDiv{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none }
#colorMessage{border:#6699cc solid; border-width:1px; background:#fff; color:#036; font-size:12px; line-height:100%;  display: none}
#smallImgDiv{ position:absolute; z-index: 1; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none }
#smallImgMassage{border:#6699cc solid; border-width:1px; background:#fff; color:#036; font-size:12px; line-height:100%;  display: none}
.header{background:url(/images/device/btn_background.gif);  font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff}
#demo { 
    background: #FFF; 
    overflow:hidden; 
    border: 1px dashed #CCC; 
    width: 300px; 
    height:100px;
    position: absolute;
    left:450;
    top:50px; 
    background-color: #${module.alpha=='0.1'?'ffffff':module.scroll_bg};
} 
#demo img { 
    border: 3px solid #F2F2F2; 
} 
#indemo { 
    float: left; 
    width: 1000%; 
} 
#demo1 { 
    float: left; 
    line-height: 100px;
    font-weight:${module.fontTyle=='1'?'bold':'normal' };
    font-style:${module.fontTyle=='2'?'italic':'normal' };
    font-family:${module.fontName=='no'?'宋体':module.fontName };
    font-size:${module.fontSize=='no'?'40':module.fontSize };
    color: #${module.scroll_fg};
} 
#demo2 { 
    float: left; 
    line-height: 100px;
    font-weight:${module.fontTyle=='1'?'bold':'normal' };
	font-style:${module.fontTyle=='2'?'italic':'normal' };
    font-family:${module.fontName=='no'?'宋体':module.fontName };
    font-size:${module.fontSize=='no'?'25':module.fontSize };
    color: #${module.scroll_fg};
}
</style>		
		<script type="text/javascript">
		function delmedia(mid){
			window.location.href="/admin/program/addmediaList.jsp?opp=3&moduleid=<%=moduleid%>&mid="+mid;
		}
		function updateMediaType(mediatype){
			if(confirm("提示信息：确认更改播放类型？")){
			window.location.href="/admin/program/addmediaList.jsp?opp=4&moduleid=<%=moduleid%>&mediatype="+mediatype;
			}else{
			window.location.href="/admin/program/addmediaList.jsp?opp=0&moduleid=<%=moduleid%>";
			}
		}
		function addMediaType(mediatype){
			window.location.href="/admin/program/addmediaList.jsp?opp=4&moduleid=<%=moduleid%>&mediatype="+mediatype;
		}
		function saveWeather(){
		var city=weatherForm.city.value;
			if(city==""){
				alert("城市不能为空!");
				return;
			}
			weatherForm.action="/admin/program/save_weather.jsp?moduleid=${moduleid}";
			weatherForm.submit();
		}
		function changewind(wind){
		var winds=wind.split("#");
		weatherForm.weather_image.value=winds[1]
		weatherForm.weather_wind.value=winds[0]
		document.getElementById("weatherimgid").src="/images/weather_images/"+winds[1];
		}
		function saveScroll(){
			htmlc= scrollForm.scrollContent.value.replace(/\s/g,"");;
			if(htmlc==""){
				alert("滚动文字不能为空！");
				return;
			}
			scrollForm.action="/admin/program/save_scroll.jsp?moduleid=${moduleid}";
			scrollForm.submit();
		}
		function saveOther(mtype){
			oc= otherForm.otherContent.value.replace(/\s/g,"");;
			if(oc==""){
				alert("内容不能为空！");
				return;
			}
			otherForm.action="/admin/program/save_other.jsp?moduleid=${moduleid}&mtype="+mtype;
			otherForm.submit();
		}
		function saveHtmlText(){
			varoEditor=FCKeditorAPI.GetInstance('htmltextContent'); 
			htmlc=(varoEditor.GetXHTML(true));
			if(htmlc==""){
				alert("滚动文本不能为空！");
				return;
			}
			htmltextForm.action="/admin/program/save_htmltext.jsp?moduleid=${moduleid}";
			htmltextForm.submit();
		}
		function showClockColordiv(){
		showDiv("251","175","260","30px","/admin/program/select_color.jsp?moduleid=${moduleid}&type=clock");
		}
		function showScrollFg(){
		showDiv("251","175","260","30px","/admin/program/select_color.jsp?moduleid=${moduleid}&type=scrollfg");
		}
		function showScrollBg(){
		showDiv("251","175","260","30px","/admin/program/select_color.jsp?moduleid=${moduleid}&type=scrollbg");
		}
		function showDiv(fwidth,fheight,left,top,url){
document.body.scrollTop = "0px";
document.getElementById("color_iframe").width=fwidth;
document.getElementById("color_iframe").height=fheight;
document.getElementById("colorDiv").style.left=left;
document.getElementById("colorDiv").style.top=top;
document.getElementById("color_iframe").src=url;
document.getElementById("colorDiv").style.display='block';
document.getElementById("colorMessage").style.display='block';
}
function closeDiv(){
document.getElementById("colorDiv").src="/loading.jsp";
document.getElementById('colorMessage').style.display="none";
}
function getAbsolutePosition(obj) 
{ 
position = new Object(); 
position.x = 0; 
position.y = 0; 
var tempobj = obj; 
while(tempobj!=null && tempobj!=document.body) 
{ 
if(window.navigator.userAgent.indexOf("MSIE")!=-1) 
{ 
position.x += tempobj.offsetLeft; 
position.y += tempobj.offsetTop; 
} 
else if(window.navigator.userAgent.indexOf("Firefox")!=-1) 
{ 
position.x += tempobj.offsetLeft; 
position.y += tempobj.offsetTop; 
} 
tempobj = tempobj.offsetParent 
} 
return position; 
} 

function showimg(event,imgpath){
event = event || window.event;
document.getElementById("div_img").src=imgpath;
document.getElementById("smallImgDiv").style.display='block';
document.getElementById("smallImgMassage").style.display='block';
document.getElementById("smallImgDiv").style.left= document.body.scrollLeft + event.clientX + 10 + "px";
document.getElementById("smallImgDiv").style.top=document.body.scrollTop + event.clientY + 10 + "px";
}
function hiddenimg(){
document.getElementById('smallImgDiv').style.display='none';
document.getElementById("smallImgMassage").style.display='none';
}
function clockon() {
thistime= new Date()
var hours=thistime.getHours()
var minutes=thistime.getMinutes()
var seconds=thistime.getSeconds()
if (eval(hours) <10) {hours="0"+hours}
if (eval(minutes) < 10) {minutes="0"+minutes}
if (seconds < 10) {seconds="0"+seconds}
thistime = hours+":"+minutes+":"+seconds
bgclockshade.innerHTML=thistime
var timer=setTimeout("clockon()",1000)
}
function changeFontSytle(style_no){
if(style_no==1){
document.getElementById('scrollContentid').style.fontWeight="bold";
document.getElementById('scrollContentid').style.fontStyle="normal";

document.getElementById('demo1').style.fontWeight="bold";
document.getElementById('demo1').style.fontStyle="normal";
document.getElementById('demo2').style.fontWeight="bold";
document.getElementById('demo2').style.fontStyle="normal";
}else if(style_no==2){
document.getElementById('scrollContentid').style.fontWeight="normal";
document.getElementById('scrollContentid').style.fontStyle="italic";
document.getElementById('demo1').style.fontWeight="normal";
document.getElementById('demo1').style.fontStyle="italic";
document.getElementById('demo2').style.fontWeight="normal";
document.getElementById('demo2').style.fontStyle="italic";
}else{
document.getElementById('scrollContentid').style.fontWeight="normal";
document.getElementById('scrollContentid').style.fontStyle="normal";
document.getElementById('demo1').style.fontWeight="normal";
document.getElementById('demo1').style.fontStyle="normal";
document.getElementById('demo2').style.fontWeight="normal";
document.getElementById('demo2').style.fontStyle="normal";
}
}
function changecontent(){
var content=document.getElementById('scrollContentid').value;
document.getElementById('demo1').innerHTML=content+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
document.getElementById('demo2').innerHTML=content+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
}
function update_sequence(moduleid,mediaid,sequence,order){
window.location.href="/admin/program/update_media_sequence.jsp?moduleid="+moduleid+"&mediaid="+mediaid+"&sequence="+sequence+"&order="+order;
}
</script>
	</head>

	<body ${m_filetype=='clock'?'onLoad="clockon()"':''}>
		<table cellspacing="0" cellpadding="0" width="100%" border="0">
			<tr height="30px" class="TableTrBg05">
				<td width="30%" colspan="6" align="left">
					<table cellpadding="0" width="100%" height="100%" cellspacing="0" border="0"
						style="background-color:#F5F9FD;">
						<tr>
							<td height="30px" width="150px" align="right">
								当前播放区域播放类型：
							</td>
							<c:choose>
								<c:when test="${module.m_filetype==''}">
									<td width="60px">
										<span style="color: red">无，</span>
									</td>
									<td width="100px">
										请选择播放类型：
									</td>
									<td>
										<select style="width: 100px"
											onchange="addMediaType(this.value)">
											<option value="">
												请选择
											</option>
											<c:forEach var="mtype" items="${module.mtypes}">
											<option ${fn:split(mtype,"#")[0] == module.m_filetype ?'selected':''} value="${fn:split(mtype,"#")[0]}">${fn:split(mtype,"#")[1]}</option>
										</c:forEach>
										</select>
									</td>
								</c:when>
								<c:otherwise>
									<td width="60px">
										<span style="color: green">${module.m_filetypeZN}</span>
									</td>
									<td width="100px">
										更换播放类型：
									</td>
									<td>
										<select style="width: 100px" id="mtypeid"
											onchange="updateMediaType(this.value)">
											<option value="">
												无
											</option>
										<c:forEach var="mtype" items="${module.mtypes}">
											<option ${fn:split(mtype,"#")[0] == module.m_filetype ?'selected':''} value="${fn:split(mtype,"#")[0]}">${fn:split(mtype,"#")[1]}</option>
										</c:forEach>
										</select>
									</td>
								</c:otherwise>
							</c:choose>

						</tr>
						<tr>
							<td class="Line_01" colspan="6"></td>
						</tr>
					</table>
				</td>
			</tr>
			<c:if test="${m_filetype=='iptv' || m_filetype=='web'}">
			<tr>
					<td colspan="3">
					<form name="otherForm" method="post">
						<table cellpadding="0" cellspacing="1" width="360" border="0" bgcolor="#69a3cd"
							style="margin-top: 5px; margin-left: 50px">
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="25" style="font-weight: bold">
									<c:if test="${m_filetype=='iptv'}">
									流媒体链接地址
									</c:if>
									<c:if test="${m_filetype=='web'}">
									网页链接地址
									</c:if>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="25">
									<textarea rows="5" cols="65" name="otherContent">${module.iw_content}</textarea>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="25"><input type="button" value=" 保 存 " onclick="saveOther('${m_filetype}');" class="button"/>
								<c:if test="${save_state=='save_ok'}"><span style="color: red">保存成功！</span></c:if></td>
							</tr>
						</table>
						</form>
					</td>
				</tr>


			</c:if>
			<c:if test="${m_filetype=='weather'}">
				<tr>
					<td colspan="3">
					<form name="weatherForm" method="post">
						<input type="hidden" name="weather_date" value="${module.weather_date}"/>
						<input type="hidden" name="weather_image" value="${module.weather_image }"/>
						<input type="hidden" name="weather_wind" value="${module.weather_wind }"/>
						<table cellpadding="0" cellspacing="1" width="310" border="0" bgcolor="#69a3cd"
							style="margin-top: 5px; margin-left: 50px">
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="25"  style="font-weight: bold">
									${module.weather_date}
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td rowspan="3" align="center">
									<img src="/images/weather_images/${module.weather_image}" id="weatherimgid" width="80" height="80" alt="天气图片" />
								</td>
								<td >
									&nbsp;城市：&nbsp;&nbsp;<input type="text" name="city" value="${module.city }"  class="button" />
								</td>
							</tr>
							<tr  bgcolor="#F5F9FD">	
								<td>
									&nbsp;天气：
									<select style="width: 100px;"  name="wind" onchange="changewind(this.value)">
										<c:forEach var="wind" items="${module.winds}">
											<option ${fn:split(wind,"#")[0] == module.weather_wind ?'selected':''} value="${wind}">${fn:split(wind,"#")[0]}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td>
									&nbsp;温度：
									<select  style="width: 70px;"  name="start_temperature">
										<c:forEach var="temperature" items="${module.temperatures}">
											<option ${module.start_temperature==temperature?'selected':'' } value="${temperature}">${temperature }</option>
										</c:forEach>
									</select>
									~
									<select  style="width: 70px;" name="end_temperature">
										<c:forEach var="temperature" items="${module.temperatures}">
											<option ${module.end_temperature==temperature?'selected':'' } value="${temperature}">${temperature }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="25"><input type="button" value=" 保 存 " onclick="saveWeather();" class="button"/>
								<c:if test="${save_state=='save_ok'}"><span style="color: red">保存成功！</span></c:if></td>
							</tr>
						</table>
						</form>
					</td>
				</tr>


			</c:if>
			<c:if test="${m_filetype=='clock'}">
				<tr>
						<td colspan="3" >
							<table cellpadding="0" cellspacing="1" width="260" border="0" bgcolor="#69a3cd"
							style="margin-top: 2px; margin-left: 50px;">
							<tr bgcolor="#F5F9FD">
								<td align="center" height="25" width="240"  style="font-weight: bold">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时钟
								</td>
								<td width="20">
									<div id="divcolor_view" style="border:solid 1px #000000;background-color:${module.m_other};width: 20px;height: 20px; cursor: pointer; " title="点击修改颜色" onclick="showClockColordiv();"></div>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="100">
								<div id="bgclockshade" style="height: 100px;font-family:Arial;color:${module.m_other};font-size:50px; font-weight:bold; padding-top: 40px"></div>
								</td>
							</tr></table>
				</tr>
			</c:if>
			<c:if test="${m_filetype=='htmltext'}">
				<form name="htmltextForm" method="post">
						<table cellpadding="0" cellspacing="1" width="360" border="0" bgcolor="#69a3cd"
							style="margin-top: 5px; margin-left: 20px">

							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="25">
								<FCK:editor instanceName="htmltextContent"  width="700" height="160"  toolbarSet="Basic" value="${module.m_text}"></FCK:editor>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="25"><input type="button" value=" 保 存 " onclick="saveHtmlText();" class="button"/>
								<c:if test="${save_state=='save_ok'}"><span style="color: red">保存成功！</span></c:if></td>
							</tr>
						</table>
						</form>


			</c:if>
			<c:if test="${m_filetype=='scroll'}">
				<tr>
					<td colspan="3" valign="top">
					<form name="scrollForm" method="post">
						<input type="hidden" name="scrollfg" value="${module.scroll_fg}"/>
						<input type="hidden" name="scrollfgRGB" value="${module.foreground}"/>
						<input type="hidden" name="scrollbg" value="${module.scroll_bg }"/>
						<input type="hidden" name="scrollbgRGB" value="${module.background }"/>
						
						<table cellpadding="0" cellspacing="1" width="420" border="0" bgcolor="#69a3cd"
							style="margin-top: 2px; margin-left: 10px;">
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="7" height="25"  style="font-weight: bold;  " >
									滚动文字</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center"  width="16%">
									<select  style="width: 60px" title="字体" name="fontName" onchange="document.getElementById('scrollContentid').style.fontFamily=this.value;document.getElementById('demo2').style.fontFamily=this.value;document.getElementById('demo1').style.fontFamily=this.value">
										<option value="no" ${module.fontName=='no'?'selected':'' }>字体</option>
										<option value="宋体" ${module.fontName == '宋体'?'selected':'' }>宋体</option>
										<option value="黑体" ${module.fontName=='黑体'?'selected':'' }>黑体</option>
										<option value="楷体" ${module.fontName=='楷体'?'selected':'' }>楷体</option>
										<option value="幼圆" ${module.fontName=='幼圆'?'selected':'' }>幼圆</option>
										<option value="微软雅黑" ${module.fontName=='微软雅黑'?'selected':'' }>微软雅黑</option>
										<option value="Arial" ${module.fontName=='Arial'?'selected':'' }>Arial</option>
									</select>
								</td>
								<td align="center"  width="16%">
									<select style="width: 60px"  name="fontTyle"  onchange="changeFontSytle(this.value)"> 
										<option value="no"  ${module.fontTyle=='no'?'selected':'' }>字体样式</option>
										<option value="0" ${module.fontTyle=='0'?'selected':'' }>正常</option>
										<option value="1" ${module.fontTyle=='1'?'selected':'' }>粗体</option>
										<option value="2" ${module.fontTyle=='2'?'selected':'' }>斜体</option>
									</select>
								</td>
								<td align="center"  width="16%">
									<select style="width: 60px; " name="fontSize" onchange="document.getElementById('demo1').style.fontSize=this.value;document.getElementById('demo2').style.fontSize=this.value">
										<option value="no" ${module.fontSize=='no'?'selected':'' }>字体大小</option>
										<c:forEach var="fsize" items="${module.fontsizes}">
											<option ${fsize == module.fontSize ?'selected':''} value="${fsize}">${fsize}px</option>
										</c:forEach>
									</select>
								</td>
								<td align="center"  width="16%">
									<select style="width: 60px" name="span" onchange="changespan(1,this.value)">
										<option value="no" ${module.span=='no'?'selected':'' }>滚动速度</option>
										<option value="20" ${module.span=='20'?'selected':'' }>正常</option>
										<option value="30" ${module.span=='30'?'selected':'' }>快</option>
										<option value="10" ${module.span=='10'?'selected':'' }>慢</option>
									</select>
								</td><td align="center"  width="16%">
									<select style="width: 60px" name="alpha" onchange="changealpha(this.value)">
										<option value="1.0" ${module.alpha=='1.0'?'selected':'' }>不透明</option>
										<option value="0.1" ${module.alpha=='0.1'?'selected':'' }>透明</option>
									</select>
								</td>
								<td align="center"  width="10%">
									<div id="scroll_fgId" style="border:solid 1px #000000;background-color:#${module.scroll_fg};width: 25px;height: 18px; cursor: pointer; font-family:Arial; font-size: 16; font-weight: bold; " title="字体颜色" onclick="showScrollFg()"></div>
								
								</td>
								<td align="center"  width="10%">
									<div  id="scroll_bgId"   style="border:solid 1px #000000;background-color:#${module.alpha=='0.1'?'ffffff':module.scroll_bg};width: 25px;height: 18px; cursor: pointer;font-family:Arial; font-size: 16; font-weight: bold " title="背景颜色" onclick="showScrollBg()"></div>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="7" height="25">
									<textarea rows="6" cols="75" name="scrollContent" id="scrollContentid" onchange="changecontent()" onfocus="changecontent()" >${module.scroll_content}</textarea>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="7" height="25"><input type="button" value=" 保 存 " onclick="saveScroll();" class="button"/>
								<c:if test="${save_state=='save_ok'}"><span style="color: red">保存成功！</span></c:if></td>
							</tr>
						</table>
						</form>
<div id="demo" title="滚动文字效果"> 
<div id="indemo"> 
<div id="demo1"> 
${module.scroll_content}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div> 
<div id="demo2"></div> 
</div> 
</div> 
<script> 
<!-- 
var speed=20; //数字越大速度越慢 
var tab=document.getElementById("demo"); 
var tab1=document.getElementById("demo1"); 
var tab2=document.getElementById("demo2"); 
function Marquee(){ 
tab2.innerHTML=tab1.innerHTML; 
if(tab2.offsetWidth-tab.scrollLeft<=0) 
tab.scrollLeft-=tab1.offsetWidth 
else{ 
tab.scrollLeft++; 
} 
} 
var MyMar;
function changespan(n,sped){
if(sped=='no'||sped==''){
speed=20;
}else if(sped=='20'){
speed=20;
}else if(sped=='30'){
speed=1;
}else if(sped=='10'){
speed=50;
}
if(n==1){
clearInterval(MyMar);
}
MyMar=setInterval(Marquee,speed); 
}
changespan(0,'${module.span}');

function changealpha(alpha){
if(alpha=='1.0'){
document.getElementById("scroll_bgId").style.backgroundColor="#0000ff";
document.getElementById('demo').style.backgroundColor="#0000ff";
}else if(alpha=='0.1'){
document.getElementById("scroll_bgId").style.backgroundColor="#ffffff";
document.getElementById('demo').style.backgroundColor="#ffffff";
}
}

--> 
</script> 
					</td>
				</tr>
			</c:if>
			<c:if
				test="${m_filetype=='image' || m_filetype=='video' || m_filetype=='flash' || m_filetype=='ppt' || m_filetype=='word'|| m_filetype=='excel'}">
				<c:if test="${empty mediaList}">
					<tr><td style="padding-left: 50px; color: red"><br/>无媒体文件，请添加媒体！</td></tr>
				</c:if>
				<c:forEach var="media" items="${mediaList}">
					<tr height="25px" class="TableTrBg23_"
						onmouseover="this.className='TableTrBg23'"
						onmouseout="this.className='TableTrBg23_'">
						<td width="33%" align="left" style="padding-left: 10px; cursor: pointer"  onmouseover=showimg(event,'${media.slightly_img_path}') onmouseout="hiddenimg()">
							<img src="/images/dtreeimg/${media.media_type }.gif"
								align="absmiddle" />
							&nbsp;&nbsp;${media.media_title }
						</td>
						<td width="15%">
						 <img src="${media.slightly_img_path}" height="20px" onmouseover=showimg(event,'${media.slightly_img_path}') onmouseout="hiddenimg()"/>
						</td>
						<td width="15%">
							${media.media_type }
						</td>
						<td width="15%">
							${media.sequence}
						</td>
						
						<td width="10%">
							<c:choose>
								<c:when test="${media.sequence==1}">
								<img src="/images/icon_close.gif" border="0"/>
										</c:when>
								<c:otherwise>
									<a href="javascript:;" onclick="update_sequence('${moduleid}','${media.id }','${media.sequence}','asc')"><img src="/images/icon_close.gif" border="0"/></a>
							
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${fn:length(mediaList)==media.sequence}">
								<img src="/images/icon_open.gif" border="0"/>
									</c:when>
								<c:otherwise>
								<a href="javascript:;" onclick="update_sequence('${moduleid}','${media.id }','${media.sequence}','desc')"><img src="/images/icon_open.gif" border="0"/></a>
								</c:otherwise>
							</c:choose>
							</td>
							<td width="15%">
							<input type="button" class="button" onclick="delmedia('${media.id }')" value=" 删 除 " />
						</td>
							</tr>
					<tr>
						<td class="Line_01" colspan="6"></td>
					</tr>
				</c:forEach>
				<tr><td height="120px">&nbsp;</td></tr>
			</c:if>
		</table>
		
		<div id="colorDiv">
			<div id="colorMessage">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td >
							<iframe src="/loading.jsp" scrolling="no" id="color_iframe"
								name="color_iframe" frameborder="0"></iframe>
						</td>
					</tr>
				</table>
			</div>
		</div>		
		<div id="smallImgDiv">
			<div id="smallImgMassage">
				<img src="/images/loading.gif" id="div_img" width="150" height="112" /></div>
</div>
		
	</body>
</html>
<c:if test="${isscroll==1}">
<div id="scrolldiv_temp" style="display: none;">${scrollcontent}</div>
<script>
document.getElementById('scrollContentid').value=document.getElementById('scrolldiv_temp').innerHTML;
</script>
</c:if>
<c:if test="${ishtmltext==1}">
<div id="htmltextdiv_temp" style="display: none;">${htmltextcontent}</div>
<script>
document.getElementById('htmltextContent').value=document.getElementById('htmltextdiv_temp').innerHTML;
</script>
</c:if>
