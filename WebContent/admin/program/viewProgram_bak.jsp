<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Template"/>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String jmurl=request.getParameter("jmurl");
String templateid=request.getParameter("templateid");
String scale=request.getParameter("scale")==null?"1":request.getParameter("scale");
TemplateDAO templatedao= new TemplateDAO();
ModuleDAO moduledao= new ModuleDAO();
Template template=templatedao.getTemplateTempById(templateid);
List moduleList=moduledao.getModuleMediaByTemplateId(templateid);
request.setAttribute("jmurl",jmurl);
request.setAttribute("template",template);
request.setAttribute("scale",scale);
request.setAttribute("moduleList",moduleList);
 %>
<html>
	<head>
		<title>多媒体信息发布系统-节目预览</title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<style type="text/css">
	.drsElement {
		 position: absolute;
	}
	.drsMoveHandle {
		 height: 20px;
	}
	.dragresize {
		 position: absolute;
		 width: 5px;
		 height: 5px;
		 font-size: 1px;
	}
	.dragresize-tl {
		 top: -8px;
		 left: -8px;
	}
	.dragresize-tm {
		 top: -8px;
		 left: 50%;
		 margin-left: -4px;
	}
	.dragresize-tr {
		 top: -8px;
		 right: -8px;
	}

	.dragresize-ml {
		 top: 50%;
		 margin-top: -4px;
		 left: -8px;
	}
	.dragresize-mr {
		 top: 50%;
		 margin-top: -4px;
		 right: -8px;
	}
	.dragresize-bl {
		 bottom: -8px;
		 left: -8px;
	}
	.dragresize-bm {
		 bottom: -8px;
		 left: 50%;
		 margin-left: -4px;
	}
	.dragresize-br {
		 bottom: -8px;
		 right: -8px;
	}
 </style>
		<script type="text/javascript" src="/js/dragresize_commented.js"></script>
		<script type="text/javascript">

    var count= 0 ; 
    var maxmodel = 10;
	var diva="null";
	var _zwidth=${template.template_width*scale};
	var _zheight=${template.template_height*scale};

    function addUpload(module_id,_left,_top,_width,_height) {
        if(count >= maxmodel){    
		   alert("已经超出10个模块！");
		   return;//限制最多maxfile个文件框
		}
        var newDiv = "<div class='drsElement drsMoveHandle' id='model" +module_id+"'"
		+" style='left: "+_left+"; top: "+_top+"; width: "+_width+"; height:"+_height+"px;text-align: center;z-index:"+module_id+100+"'><span style='font-size:12px'>播放区域" +module_id+" </span></div>";
	elementid="model" +module_id;
        document.getElementById("fathermodel").insertAdjacentHTML("beforeEnd", newDiv);     
     }
	 
function viewImg(backgroudPath) 
{ 
var newPreview=new Image();
newPreview.src = backgroudPath;
newPreview.style.width = _zwidth;
newPreview.style.height = _zheight;
newPreview.style.display = "block"; 
document.getElementById("fathermodel").appendChild(newPreview);
} 
function SetWinHeight(obj){  
var win=obj;  
if (document.getElementById){  
if (win && !window.opera){   
if (win.contentDocument && win.contentDocument.body.offsetHeight)  
win.height = win.contentDocument.body.offsetHeight;   
else if(win.Document && win.Document.body.scrollHeight)  
 win.height = win.Document.body.scrollHeight; 
 } 
 } 
 }

</script>
	</head>
	<body style="margin:0px ;  font-size:12px;" >
	<bgsound src="${template.template_backmusic}" loop="-1"  />
<div id="fathermodel" style="position:absolute;color:black;width:${template.template_width*scale};height:${template.template_height*scale}; 
            background-color:#ffffff;"></div>
             <c:if test="${template.template_background!=''}">
	<script type="text/javascript">
		viewImg('${template.template_background }');
		</script>
	</c:if>
	<c:forEach var="module" items="${moduleList}">
		<script type="text/javascript">
			addUpload(${module.id},${(module.m_left)*scale},${(module.m_top)*scale},${module.m_width*scale},${module.m_height*scale});
		</script>
		<c:if test="${module.m_filetype=='scroll'}"><!--滚动文字效果  -->
<style type="text/css">
#demo { 
    background: #FFF; 
    overflow:hidden; 
    width: 100%; 
    height:100%; 
    background-color: #${module.alpha=='0.1'?'':module.scroll_bg};
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
    padding-top:3px;
    line-height: ${module.m_height*scale}px;
    font-weight:${module.fontTyle=='1'?'bold':'normal' };
    font-style:${module.fontTyle=='2'?'italic':'normal' };
    font-family:${module.fontName=='no'?'宋体':module.fontName };
    font-size:${module.fontSize}px;
    color: #${module.scroll_fg};
} 
#demo2 { 
    float: left; 
     padding-top:3px;
    line-height: ${module.m_height*scale}px;
    font-weight:${module.fontTyle=='1'?'bold':'normal' };
	font-style:${module.fontTyle=='2'?'italic':'normal' };
    font-family:${module.fontName=='no'?'宋体':module.fontName };
    font-size:${module.fontSize}px;
    color: #${module.scroll_fg};
}
</style>
<div id="scroll_model" style="display: none; word-wrap:break-word;width:${module.m_width*scale}px;">${module.scroll_content}</div>
<script> 
var scroll_div='<div id="demo" title="滚动文字效果"> <div id="indemo"> <div id="demo1">'+document.getElementById("scroll_model").innerHTML+'<div style="width:576px"></div></div>'+ 
'<div id="demo2"></div></div></div>';

document.getElementById("model"+${module.id}).innerHTML=scroll_div;
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
speed=15;
}else if(sped=='20'){
speed=15;
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
--> 
</script> 
		</c:if>
<c:if test="${module.m_filetype=='video'}"><!--视频  -->
<script> 
var video_Aletter=new Array();
<c:set var="videonum" value="0"></c:set>
<c:forEach var="video" items="${module.mediaList}">
video_Aletter[${videonum}]="${video.slightly_img_path}${video.slightly_img_name}";
<c:set var="videonum" value="${videonum+1}"></c:set>
</c:forEach>
var video_i=0;
function video_letter()
{
  if (video_i==video_Aletter.length){video_i=0};
  document.getElementById("model"+${module.id}).innerHTML='<img  src = '+video_Aletter[video_i]+' width="${module.m_width*scale}" height="${module.m_height*scale}">';
  video_i++;
  setTimeout("video_letter()",'${module.span}');
}
video_letter();
</script>
</c:if>
<c:if test="${module.m_filetype=='clock'}"><!--时钟效果  -->	
<script>

function clockon() {
thistime= new Date()
var hours=thistime.getHours()
var minutes=thistime.getMinutes()
var seconds=thistime.getSeconds()
if (eval(hours) <10) {hours="0"+hours}
if (eval(minutes) < 10) {minutes="0"+minutes}
if (seconds < 10) {seconds="0"+seconds}
thistime = hours+":"+minutes+":"+seconds
var clock_div='<div id="clock_div" title="时钟效果" style="width:100%; height:100%;">'+
'<div id="bgclockshade"  style="width:100%; height:100%;font-family:Arial;color:${module.m_other};font-size:${module.m_height<30?module.m_height*(scale/1.5):30}px; font-weight:bold;line-height: ${module.m_height*scale}px;"></div>';
document.getElementById("model"+${module.id}).innerHTML=clock_div;
bgclockshade.innerHTML=thistime;
var timer=setTimeout("clockon()",1000)
}
clockon();
</script>
</c:if>

<c:if test="${module.m_filetype=='image'}"><!--图片效果  -->	
<script> 
var Aletter=new Array();
<c:set var="imgnum" value="0"></c:set>
<c:forEach var="image" items="${module.mediaList}">
Aletter[${imgnum}]="${image.slightly_img_path}${image.slightly_img_name}";
<c:set var="imgnum" value="${imgnum+1}"></c:set>
</c:forEach>
var i=0;
function letter()
{
  if (i==Aletter.length) i=0;
  document.getElementById("model"+${module.id}).innerHTML='<img  src = '+Aletter[i]+' width="${module.m_width*scale}" height="${module.m_height*scale}" >';
  ++i;
  setTimeout("letter()",'${module.span}');
}
letter();
</script>
</c:if>
<c:if test="${module.m_filetype=='htmltext'}"><!--滚动文本  -->	
 
<style type="text/css"> 
<!-- 
#htmltext_demo { 
    overflow:hidden; 
    height: ${module.m_height*scale}px;
    width:${module.m_width*scale}px;
     word-wrap:break-word;
    text-align: left; 
    float: left; 
     line-height: 25px;
} 
#htmltext_demo img { 
    border: 3px solid #F2F2F2; 
    display: block; 
   
} 
--> 
</style>
<div id="htmltext_model" style="display: none; word-wrap:break-word;width:${module.m_width*scale}px;">${module.m_text}</div>
<script>
var htmltext_div='<div id="htmltext_demo"> <div id="htmltext_demo1" style="word-wrap:break-word;width:${module.m_width*scale}px;">'+document.getElementById("htmltext_model").innerHTML+'<div style="height:${module.m_height*scale/2}px"></div></div><div id="htmltext_demo2" style="word-wrap:break-word;width:${module.m_width*scale}px;"></div> </div> ';
document.getElementById("model"+${module.id}).innerHTML=htmltext_div;
<!-- 
var htmltext_speed=20; //数字越大速度越慢 
var htmltext_tab=document.getElementById("htmltext_demo"); 
var htmltext_tab1=document.getElementById("htmltext_demo1"); 
var htmltext_tab2=document.getElementById("htmltext_demo2"); 
htmltext_tab2.innerHTML=htmltext_tab1.innerHTML+'<div style="height:${module.m_height*scale/2}px"></div>';
function htmltext_Marquee(){ 
if(htmltext_tab2.offsetTop-htmltext_tab.scrollTop<=0)
htmltext_tab.scrollTop-=htmltext_tab1.offsetHeight
else{ 
htmltext_tab.scrollTop++ 
} 
} 
var htmltext_MyMar=setInterval(htmltext_Marquee,htmltext_speed); 
--> 
</script> 
</c:if>
<c:if test="${module.m_filetype=='web'}"><!--网页效果   -->
<script>
var webdiv='<div><iframe src="${module.iw_content}" frameborder="0" width="${module.m_width*scale}px"  scrolling="no" height="${module.m_height*scale}px"></iframe></div>';
document.getElementById("model"+${module.id}).innerHTML=webdiv;
</script> 
</c:if>
<c:if test="${module.m_filetype=='flash'}"><!--FALSH效果  -->
<script> 
var flash_Aletter=new Array();
<c:set var="flashnum" value="0"></c:set>
<c:forEach var="flash" items="${module.mediaList}">
flash_Aletter[${flashnum}]="${flash.slightly_img_path}${flash.slightly_img_name}";
<c:set var="flashnum" value="${flashnum+1}"></c:set>
</c:forEach>
var flash_i=0;
function flash_letter()
{
  if (flash_i==flash_Aletter.length){flash_i=0};
  document.getElementById("model"+${module.id}).innerHTML='<img  src = '+flash_Aletter[flash_i]+' width="${module.m_width*scale}" height="${module.m_height*scale}">';
  flash_i++;
  setTimeout("flash_letter()",'${module.span}');
}
flash_letter();
</script>
</c:if>

<c:if test="${module.m_filetype=='ppt'}"><!--PPT效果  -->
<script> 
var ppt_Aletter=new Array();
<c:set var="flashnum" value="0"></c:set>
<c:forEach var="ppt_png" items="${module.pptfile}">
ppt_Aletter[${flashnum}]="/serverftp/program_file/${jmurl}/${jmurl}ppt/${ppt_png}";
<c:set var="flashnum" value="${flashnum+1}"></c:set>
</c:forEach>
var ppt_i=0;
function ppt_letter()
{
  if (ppt_i==ppt_Aletter.length){ppt_i=0};
  document.getElementById("model"+${module.id}).innerHTML='<img  src = '+ppt_Aletter[ppt_i]+' width="${module.m_width*scale}" height="${module.m_height*scale}">';
  ppt_i++;
  setTimeout("ppt_letter()",'${module.span}');
}
ppt_letter();
</script>
</c:if>
<c:if test="${module.m_filetype=='word'}"><!--word效果   -->
<style type="text/css"> 
<!-- 
#word_demo { 
    overflow:hidden; 
    height: ${module.m_height*scale}px;
    width:${module.m_width*scale}px;
    text-align: center; 
    float: left; 
} 
#word_demo img { 
    border: 3px solid #F2F2F2; 
    display: block; 
} 
--> 
</style>
<div id="word_model" style="display: none;"><iframe src="/serverftp/program_file/${jmurl}/${jmurl}word/word2003.html"  frameborder="0" width="100%"  scrolling="no" height="100%" id="word_win" name="word_main"  onload="Javascript:SetWinHeight(this)"></iframe></div>
<script>
var word_div='<div id="word_demo"> <div id="word_demo1">'+document.getElementById("word_model").innerHTML+'<div style="height:${module.m_height*scale-100}px"></div></div><div id="word_demo2"></div> </div> ';
document.getElementById("model"+${module.id}).innerHTML=word_div;
<!-- 
var word_speed=1; //数字越大速度越慢 
var word_tab=document.getElementById("word_demo"); 
var word_tab1=document.getElementById("word_demo1"); 
var word_tab2=document.getElementById("word_demo2"); 
word_tab2.innerHTML=word_tab1.innerHTML+'<div style="height:${module.m_height*scale/2}px"></div>';
function word_Marquee(){ 
if(word_tab2.offsetTop-word_tab.scrollTop<=0)
word_tab.scrollTop-=word_tab1.offsetHeight
else{ 
word_tab.scrollTop++ 
} 
} 
var word_MyMar=setInterval(word_Marquee,word_speed); 
--> 
</script> 
</c:if>
<c:if test="${module.m_filetype=='excel'}"><!--excel效果   -->
<style type="text/css"> 
<!-- 
#excel_demo { 
    overflow:hidden; 
    height: ${module.m_height*scale}px;
    width:${module.m_width*scale}px;
    text-align: center; 
    float: left; 
} 
#excel_demo img { 
    border: 3px solid #F2F2F2; 
    display: block; 
} 
--> 
</style>
<div id="excel_model" style="display: none;"><iframe src="/serverftp/program_file/${jmurl}/${jmurl}excel/excel2003.html"  frameborder="0" width="100%"  scrolling="no" height="100%" id="excel_win" name="excel_main"  onload="Javascript:SetWinHeight(this)"></iframe></div>
<script>
var excel_div='<div id="excel_demo"> <div id="excel_demo1">'+document.getElementById("excel_model").innerHTML+'<div style="height:${module.m_height*scale-50}px"></div></div><div id="excel_demo2"></div> </div> ';
document.getElementById("model"+${module.id}).innerHTML=excel_div;
<!-- 
var excel_speed=20; //数字越大速度越慢 
var excel_tab=document.getElementById("excel_demo"); 
var excel_tab1=document.getElementById("excel_demo1"); 
var excel_tab2=document.getElementById("excel_demo2"); 
excel_tab2.innerHTML=excel_tab1.innerHTML+'<div style="height:${module.m_height*scale/2}px"></div>';
function excel_Marquee(){ 
if(excel_tab2.offsetTop-excel_tab.scrollTop<=0)
excel_tab.scrollTop-=excel_tab1.offsetHeight
else{ 
excel_tab.scrollTop++ 
} 
} 
var excel_MyMar=setInterval(excel_Marquee,excel_speed); 
--> 
</script> 
</c:if>
<c:if test="${module.m_filetype=='iptv'}"><!--流媒体效果   -->
<script> 
document.getElementById("model"+${module.id}).innerHTML='<img  src ="/images/iptv.gif" >';
</script> 
</c:if>

<c:if test="${module.m_filetype=='weather'}"><!--天气预报效果   -->
<div id="weater_div" style="display: none;"><table cellpadding="0" cellspacing="1" width="220px" height="150px" border="0"  align="center">
							<tr>
								<td align="center" colspan="2" height="25"  style="color: red;font-weight: bold;font-size:20px;  font-weight:bold;line-height: 35px;">${module.weather_date}</td>
							</tr>
							<tr>
								<td rowspan="3" align="center" width="30%">
									<img src="/images/weather_images/${module.weather_image}" id="weatherimgid" width="80" height="80" alt="天气图片" />
								</td>
								<td width="60%" style="color: red;font-size:25px;  font-weight:bold;line-height: 35px;" align="center">${module.city }</td>
							</tr>
							<tr>
								<td style="color: red;font-size:20px;  font-weight:bold;line-height: 35px;" align="center">${module.start_temperature}~${module.end_temperature}</td>
							</tr>
							<tr >	
								<td style="color: red;font-size:20px;  font-weight:bold;line-height: 35px;" align="center">${module.weather_wind}</td>
							</tr>
							</table></div>



<script> 
document.getElementById("model"+${module.id}).innerHTML=document.getElementById("weater_div").innerHTML;
</script> 
</c:if>
	</c:forEach>

	</body>
</html>

