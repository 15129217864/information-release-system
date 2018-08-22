<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>

<%
String mediaIds=request.getParameter("mediaIds")==null?"!":request.getParameter("mediaIds");
request.setAttribute("mediaIds",mediaIds);
 %>
<html>
  <head>
    <title>My JSP 'update_type.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script language="javascript" src="/js/vcommon.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<script type="text/javascript">
function closedivframe(){
	parent.closedivframe(2);
}
function updateMedia(media_id){
	var group_id_value=document.getElementById("group_id").value;
	if(group_id_value==""){
		alert("请选择文件类别！");
		return;
	}
	window.location.href="/admin/media/update_typeDO.jsp?mediaIds="+media_id+"&group_id="+group_id_value;
}

var saveflag = true;
var fmt_msg = '不允许使用特殊字符(\',\")。';
var fmt_desc_msg = '该说明太长（最多：100 个字母）。';
var fmt_root = '根';
var isclose=0;
var reclick=0;
function popupCategoryWindow(e) {
	if(reclick==0){
		reclick=1;
		var tsrc = "/rq/searchTerminal?cmd=NEWMEDIA_ZU";
		makeSubLayer('类别树状视图',150,230,e);
		bindSrc('treeviewPopup',tsrc);
	}
}

function selectCategory(group_id,dpath){
	var treeviewobj = document.getElementById('divTreeview');
	//treeviewobj.removeNode(true);
	SubIFLayer.parentNode.removeChild(SubIFLayer);
	document.getElementById("group_id").value    = group_id;
	
	document.getElementById("group_id_pth").value       = chgRoot(dpath);
	reclick=0;
}
function chgRoot(val){
	val = val.replace("ROOT",fmt_root);
	if(val==null || val=="")
		return '>根';
	else
		return val;
}

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
		//IFLayer = null;
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
	//SubIFLayer.removeNode(true);
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
	 left = e.clientX-w-10;
	 top  = 20;
	 width=w; 
	 height=h; 
	};//with 
} 

function closeCheckIFLayer(){
	//CheckIFLayer.removeNode(true);
	CheckIFLayer.parentNode.removeChild(CheckIFLayer);
}

function closeLoginIFLayer(){
	//LoginIFLayer.removeNode(true);
	LoginIFLayer.parentNode.removeChild(LoginIFLayer);
}

function bindSrc(objname,source){
	var obj = document.getElementById(objname);
	obj.src = source;
}
</script>
  </head>
  
  <body>
    <table width="230" align="center" height="250px">
    	<tr>
    		<td  height="55">移动到：</td>
    		<td><input type="text" class="button"  name="group_id_pth" id="group_id_pth" class="text_input" size="15" height="18"  readonly  />
					<a href="#popup1" onClick="javascript:popupCategoryWindow(event);">
					   <img src="/images/btn_search2.gif"  border="0" align="top" style="margin-top:0px"/>
					   <input type="hidden" class="button"  name="group_id" id="group_id" /></td>
    	</tr>
    	<tr>
			<td colspan="2" align="center" >
			<input type="button" value=" 移 动 " class="button1" id="saveid" onClick="updateMedia('${mediaIds}')" />
			&nbsp;&nbsp;&nbsp;<input type="button" value=" 取 消 " class="button1"  onClick="closedivframe()" />
			</td>
		</tr>
    </table>
  </body>
</html>
