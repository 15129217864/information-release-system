<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>上传媒体</title>
<link href="uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/uploadify/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/uploadify/swfobject.js"></script>
<script type="text/javascript" src="/uploadify/jquery.uploadify.v2.1.0.min.js"></script>
<script language="javascript" src="/js/vcommon.js"></script>
<script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<script type="text/javascript">

var fileTypeExtstmp='';
<c:choose>
	<c:when test="${browser eq 'chrome'}">
	  fileTypeExtstmp='*.*';
	</c:when>
	<c:otherwise>
	  fileTypeExtstmp='*.png;*.gif;*.jpg;*.jpeg;*.bmp;*.txt;*.mp3;*.wav;*.wma;*.swf;*.ppt;*.pptx;*.doc;*.docx;*.xls;*.xlsx;*.pdf;*.html;*.htm;*.mpg;*.wmv;*.rm;*.avi;*.vob;*.mpa;*.asf;*.rmvb;*.mpeg;*.mvb;*.mov;*.3gp;*.mp4;*.asx;*.flv;*.dat;*.mkv;*.exe';
	</c:otherwise>
</c:choose>

//请小心升级 flash 控件，有中文乱码的 bug现在的flash 最新版本，11.8.800.168 和 11.9 等有 bug，
//如果升级后，在使用 swfuploader、uploadify 等文件上传控件时，如果文件名是中文，就会出现中文乱码,会导致Object error
$(document).ready(function() {
	$("#uploadify").uploadify({
		'uploader'       : '/uploadify/uploadify.swf',
		'script'         : '/UploadPhotoServlet',
		'cancelImg'      : '/uploadify/cancel.png',
		'buttonImg'		 : '/uploadify/5.gif',
		'folder'         : '/photos',
		'queueID'        : 'fileQueue',
		'auto'           : false,
		'multi'          : true,
		'wmode'			 : 'transparent',
		'sizeLimit'      : '10737418240', //限制文件的大小, 1G=1073741824 。fileSizeLimit
		'simUploadLimit' : 1,   //每次最大上传文件数
		'fileExt':fileTypeExtstmp,
		'fileDesc'		 : '图片,声音,FLASH,视频,OFFICE,文本,网页',
		'onAllComplete'  :function(event,data) {
			alert(data.filesUploaded +'个文件上传成功!');
			//alert(parent.homeframe.content.deviceTop.content.location.href);
			//parent.homeframe.content.deviceTop.content.location.href=http://127.0.0.1:8080/rq/mediaList?left_menu=&zu_id=no&type=
			parent.homeframe.content.deviceTop.content.location.reload();
		}
	});
});

function uploadMedia(){
	DwrClass.getMediaMaxGroup_num(mediaMediaMaxGroup_num);
}

function mediaMediaMaxGroup_num(num){
	var group_id_value=document.getElementById("group_id").value;
	var fileQueuein=document.getElementById("fileQueue").innerHTML;
	if(group_id_value==""){
		alert("请选择文件类别！");
		return;
	}if(fileQueuein==""){
		alert("请选择上传文件！");
		return;
	}
	
	$('#uploadify').uploadifySettings('scriptData',{'group_id':group_id_value,'group_num':num});
	$('#uploadify').uploadifyUpload(); 
	//document.getElementById("saveid").disabled="disabled" ;
}

function closedivframe(){
	var fileQueuein=document.getElementById("fileQueue").innerHTML;
	if(fileQueuein==""){
		parent.closedivframe(2);
	}else{
		if(confirm("提示信息：确认取消上传？")){
			jQuery('#uploadify').uploadifyClearQueue();
			parent.closedivframe(2);
		}
	}
}

function cancelUpload1(attid,hh){
	if(confirm("提示信息：确认取消上传该文件？")){
		jQuery(attid).uploadifyCancel(hh);
	}
}

var saveflag = true;
var fmt_msg = '不允许使用特殊字符(\',\")。';
var fmt_desc_msg = '该说明太长（最多：100 个字母）。';
var fmt_root = '根';
var isclose=0;
var reclick=0;

function onLoad() {
	var val = " >ROOT";
	val = val.replace("ROOT",fmt_root);
	document.fm1.group_id.value = "0";
	document.fm1.group_id_pth.value    = val;
	document.fm1.title.focus();
}
function popupCategoryWindow(e) {
	if(reclick==0){
		reclick=1;
		var tsrc = "/rq/searchTerminal?cmd=NEWMEDIA_ZU";
		makeSubLayer('类别树状视图',150,250,e);
		bindSrc('treeviewPopup',tsrc);
	}
}

function selectCategory(group_id,dpath){
	var treeviewobj = document.getElementById('divTreeview');
	//treeviewobj.removeNode(true);//只有IE才有此方法
	SubIFLayer.parentNode.removeChild(SubIFLayer);
	
	document.getElementById("group_id").value = group_id;
	document.getElementById("group_id_pth").value = chgRoot(dpath);
	reclick=0;
}
function chgRoot(val){
	val = val.replace("ROOT",fmt_root);
	if(val==null || val=="")
		return '>根';
	else
		return val;
}
<!-- 
function closeIFLayer(){
	if(IFLayer){
		var obj     = parent.document.getElementById('divPopup');
		if(obj!=null && obj!="null"){
			var aobj          = divPopup.document.getElementById('aobj');	
			var ifrm_filelist = divPopup.document.getElementById('ifrm_filelist');	
			if(aobj){
				aobj.src = "";
			}else if(ifrm_filelist){
				ifrm_filelist.src = "";
			}
		}
		//IFLayer.removeNode(true);		
		IFLayer.parentNode.removeChild(IFLayer);
		reclick=0;
	}
}

function onUploadTerminate(){
	var aobj = divPopup.document.getElementById('aobj');
	aobj.document.fm1.Uploader.UploadCancel();
	setTimeout("rloadTerminate()",100);	
	//closeL();
}

function rloadTerminate(){
	var aobj = divPopup.document.getElementById('aobj');
	if(aobj.document.forms[0]){
		var rtn;
		if(aobj.document.forms[0])
		 	rtn = aobj.document.forms[0].Uploader.UploadStatus;
		if(rtn=="Cancel"){
			parent.parent.list_top.goDeleteFtpMetaInfo(aobj.document.forms[0].Uploader.UploadRepositoryUUID)
		}else{
			if(rtn == "Fail"){
				goList();
				closeL();
			}else{
				setTimeout("rloadTerminate()",100);		
			}
		}
	}
}

function closeSubIFLayer(){
	//SubIFLayer.removeNode(true);//只有IE才有此方法
	SubIFLayer.parentNode.removeChild(SubIFLayer);
	reclick=0;
}


var SubIFLayer;
function makeSubLayer(title,w,h,e){ 
	
	SubIFLayer=document.createElement("div"); 
	SubIFLayer.id = 'divTreeview';
	SubIFLayer.style.cssText="width:"+w+";height:"+h+";z-index:999;position:absolute;top:-4000;left:-4000;border:1px solid #2A5D90;padding:0 0 0 0;color:white;background-color:F5FBFF"; 

	SubIFLayer.innerHTML=""
		+"<table width='100%' border='0'><tr><td width=93% align=center><b>"+title+"</b></td><td align=right width=5%><a href='#SubIFLayer' onclick='closeSubIFLayer()'><img src='/images/icon_popupclose.gif' width='16' height='16' border='0'></td></tr></table>"
		+"";

	with(SubIFLayer.appendChild(document.createElement("iframe"))) 
	{ 
	   id ='treeviewPopup';
	   src='about:blank'; 
	   frameBorder=0; 
	   marginWidth=0;
	   marginHeight=0;
	   marginHeight=0;	   
	   width=w; 
	   height=h-30; 
	   scrolling='yes';
	}//with 

	document.body.appendChild(SubIFLayer); 
	
	e=e||event; //为了兼容火狐浏览器
	
	with(SubIFLayer.style){ 
	 left = event.clientX-w-20;
	 top  = 20;
	 width=w; 
	 height=h; 
	};//with 
} 



function closeCheckIFLayer(){
	//CheckIFLayer.removeNode(true);
		CheckIFLayer.parentNode.removeChild(CheckIFLayer);
		reclick=0;
}

function closeLoginIFLayer(){
	//LoginIFLayer.removeNode(true);
		LoginIFLayer.parentNode.removeChild(LoginIFLayer);
		reclick=0;
}

function bindSrc(objname,source){

	var obj = document.getElementById(objname);
	obj.src = source;
}


//-->
</script>
<SCRIPT type=text/javascript><!--
var i_flash;
// Netscape 
if (navigator.plugins) {
for (var i=0; i < navigator.plugins.length; i++) {
if (navigator.plugins[i].name.toLowerCase().indexOf("shockwave flash") >= 0) {
i_flash = true;}
}
}
// --></SCRIPT>
<SCRIPT type=text/vbscript><!--
//IE
on error resume next
set f = CreateObject("ShockwaveFlash.ShockwaveFlash")
if IsObject(f) then
i_flash = true
end if
// --></SCRIPT> 

<style type="text/css">
.inputcss
{
	color:#333333;
	font-family: "Tahoma"; 
	font-size: 12px; 
	border:solid 1px #CCCCCC;
}
.buttoncss
{
	color:#333333;
	font-family: "Tahoma"; 
	font-size: 12px; 
	background-color:#FFFFFF;
	border:solid 1px #CCCCCC;
}
.uploadifyQueueItem {
	font: 11px Verdana, Geneva, sans-serif;
	border: #6699cc 1 solid;
	background-color: #F5F5F5;
	margin: 3px;
	padding: 10px;
	width: 100%;
}
.uploadifyError {
	border: #6699cc 1 solid !important;
	background-color: #FDE5DD !important;
}
.uploadifyQueueItem .cancel {
	float: right;
}
.uploadifyProgress {
	background-color: #FFFFFF;
	border-top: 1px solid #808080;
	border-left: 1px solid #808080;
	border-right: 1px solid #C5C5C5;
	border-bottom: 1px solid #C5C5C5;
	margin-top: 10px;
	width: 100%;
}
.uploadifyProgressBar {
	background-color: #0099FF;
	width: 1px;
	height: 3px;
}
</style>
</head>
<body>
	<table style="width: 60%;" align="center" border="0">
		<tr>
			<td>
				<div style="float:left;">文件类别：
				     <input type="text" class="button"  name="group_id_pth" id="group_id_pth" class="text_input" size="15" height="18"  readonly  />
					<a href="#popup1" onClick="javascript:popupCategoryWindow(event);">
					   <img src="/images/btn_search2.gif"  border="0" align="top" style="margin-top:0px"/>
					</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				<div style="float:none;"><input type="file" name="uploadify" id="uploadify" align="top"/></div>
				<input type="hidden" class="button"  name="group_id" id="group_id" />
				<font color="green">友情提示：单个素材文件不要超过2.5G</font>
				<!-- 友情提示：如果页面未显示“选择文件”按钮，请下载安装FLASH插件 -->
			</td>
		</tr>
		<TR>
			<TD colspan="2"><div id="fileQueue" style="width: 550px;height: 250px; border: #6699cc 1 solid; overflow-y:scroll;overflow-x:auto ;"></div></TD>
		</TR>
		<tr>
			<td colspan="2" align="center"  height="35">
			&nbsp;&nbsp;&nbsp;<input type="button" value="开始上传" class="button1" id="saveid" onClick="uploadMedia()" />
			&nbsp;&nbsp;&nbsp;<input type="button" value="取消上传" class="button1"  onClick="closedivframe()" />
			</td>
		</tr>
	</table>
		
</body>
</html>
<SCRIPT type=text/javascript><!--
if (i_flash) {
} else {
alert("您的电脑未安装Flash插件，不能上传媒体，请下载安装后再试！");
}
// --></SCRIPT>