<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>�ϴ�ý��</title>
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

//��С������ flash �ؼ�������������� bug���ڵ�flash ���°汾��11.8.800.168 �� 11.9 ���� bug��
//�����������ʹ�� swfuploader��uploadify ���ļ��ϴ��ؼ�ʱ������ļ��������ģ��ͻ������������,�ᵼ��Object error
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
		'sizeLimit'      : '10737418240', //�����ļ��Ĵ�С, 1G=1073741824 ��fileSizeLimit
		'simUploadLimit' : 1,   //ÿ������ϴ��ļ���
		'fileExt':fileTypeExtstmp,
		'fileDesc'		 : 'ͼƬ,����,FLASH,��Ƶ,OFFICE,�ı�,��ҳ',
		'onAllComplete'  :function(event,data) {
			alert(data.filesUploaded +'���ļ��ϴ��ɹ�!');
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
		alert("��ѡ���ļ����");
		return;
	}if(fileQueuein==""){
		alert("��ѡ���ϴ��ļ���");
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
		if(confirm("��ʾ��Ϣ��ȷ��ȡ���ϴ���")){
			jQuery('#uploadify').uploadifyClearQueue();
			parent.closedivframe(2);
		}
	}
}

function cancelUpload1(attid,hh){
	if(confirm("��ʾ��Ϣ��ȷ��ȡ���ϴ����ļ���")){
		jQuery(attid).uploadifyCancel(hh);
	}
}

var saveflag = true;
var fmt_msg = '������ʹ�������ַ�(\',\")��';
var fmt_desc_msg = '��˵��̫������ࣺ100 ����ĸ����';
var fmt_root = '��';
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
		makeSubLayer('�����״��ͼ',150,250,e);
		bindSrc('treeviewPopup',tsrc);
	}
}

function selectCategory(group_id,dpath){
	var treeviewobj = document.getElementById('divTreeview');
	//treeviewobj.removeNode(true);//ֻ��IE���д˷���
	SubIFLayer.parentNode.removeChild(SubIFLayer);
	
	document.getElementById("group_id").value = group_id;
	document.getElementById("group_id_pth").value = chgRoot(dpath);
	reclick=0;
}
function chgRoot(val){
	val = val.replace("ROOT",fmt_root);
	if(val==null || val=="")
		return '>��';
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
	//SubIFLayer.removeNode(true);//ֻ��IE���д˷���
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
	
	e=e||event; //Ϊ�˼��ݻ�������
	
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
				<div style="float:left;">�ļ����
				     <input type="text" class="button"  name="group_id_pth" id="group_id_pth" class="text_input" size="15" height="18"  readonly  />
					<a href="#popup1" onClick="javascript:popupCategoryWindow(event);">
					   <img src="/images/btn_search2.gif"  border="0" align="top" style="margin-top:0px"/>
					</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				<div style="float:none;"><input type="file" name="uploadify" id="uploadify" align="top"/></div>
				<input type="hidden" class="button"  name="group_id" id="group_id" />
				<font color="green">������ʾ�������ز��ļ���Ҫ����2.5G</font>
				<!-- ������ʾ�����ҳ��δ��ʾ��ѡ���ļ�����ť�������ذ�װFLASH��� -->
			</td>
		</tr>
		<TR>
			<TD colspan="2"><div id="fileQueue" style="width: 550px;height: 250px; border: #6699cc 1 solid; overflow-y:scroll;overflow-x:auto ;"></div></TD>
		</TR>
		<tr>
			<td colspan="2" align="center"  height="35">
			&nbsp;&nbsp;&nbsp;<input type="button" value="��ʼ�ϴ�" class="button1" id="saveid" onClick="uploadMedia()" />
			&nbsp;&nbsp;&nbsp;<input type="button" value="ȡ���ϴ�" class="button1"  onClick="closedivframe()" />
			</td>
		</tr>
	</table>
		
</body>
</html>
<SCRIPT type=text/javascript><!--
if (i_flash) {
} else {
alert("���ĵ���δ��װFlash����������ϴ�ý�壬�����ذ�װ�����ԣ�");
}
// --></SCRIPT>