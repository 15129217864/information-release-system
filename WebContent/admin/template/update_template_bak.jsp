<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Template"/>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String templateid=request.getParameter("templateid");
TemplateDAO templatedao= new TemplateDAO();
ModuleDAO moduledao= new ModuleDAO();
Template template=templatedao.getTemplateById(templateid);
List moduleList=moduledao.getModuleByTemplateId(templateid);
request.setAttribute("template",template);
request.setAttribute("moduleList",moduleList);
 %>

<html>
	<head>
		<title>节目编辑</title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<style type="text/css">

	/* Required CSS classes: must be included in all pages using this script */
	/* Apply the element you want to drag/resize */
	.drsElement {
		 position: absolute;
		 border: 1px solid green;

	}

	/*
	 The main mouse handle that moves the whole element.
	 You can apply to the same tag as drsElement if you want.
	*/
	.drsMoveHandle {
		 height: 20px;
		 background-color: #CCC;
		 cursor: move;
	}

	/*
	 The DragResize object name is automatically applied to all generated
	 corner resize handles, as well as one of the individual classes below.
	 每个div框边上的小框
	*/
	.dragresize {
		 position: absolute;
		 width: 5px;
		 height: 5px;
		 font-size: 1px;
		 background: #EEE;
		 border: 1px solid #333;
	}

	/*
	 Individual corner classes - required for resize support.
	 These are based on the object name plus the handle ID.
	*/
	.dragresize-tl {
		 top: -8px;
		 left: -8px;
		 cursor: nw-resize;
	}
	.dragresize-tm {
		 top: -8px;
		 left: 50%;
		 margin-left: -4px;
		 cursor: n-resize;
	}
	.dragresize-tr {
		 top: -8px;
		 right: -8px;
		 cursor: ne-resize;
	}

	.dragresize-ml {
		 top: 50%;
		 margin-top: -4px;
		 left: -8px;
		 cursor: w-resize;
	}
	.dragresize-mr {
		 top: 50%;
		 margin-top: -4px;
		 right: -8px;
		 cursor: e-resize;
	}

	.dragresize-bl {
		 bottom: -8px;
		 left: -8px;
		 cursor: sw-resize;
	}
	.dragresize-bm {
		 bottom: -8px;
		 left: 50%;
		 margin-left: -4px;
		 cursor: s-resize;
	}
	.dragresize-br {
		 bottom: -8px;
		 right: -8px;
		 cursor: se-resize;
	}
		#divframe{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none}
#massage{border:#6699cc solid; border-width:1px; background:#fff; color:#036; font-size:12px; line-height:150%; display: none}
.header{background:url(/images/device/btn_background.gif);  font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff;	cursor: move;}
 
 </style>
		<script type="text/javascript" src="/js/dragresize_commented.js"></script>
						
		<script type="text/javascript">

    var count= 0 ; 
	var templateCount=0;  
    var maxmodel = 10;
	var diva="null";
	var _zwidth=480;
	var _zheight=360;

	//<![CDATA[
	// Using DragResize is simple!
	// You first declare a new DragResize() object, passing its own name and an object
	// whose keys constitute optional parameters/settings:
	
	var dragresize = new DragResize('dragresize',
	 { 
	 minWidth: 65, minHeight: 18, minLeft: 0, minTop: 0, maxLeft: _zwidth, maxTop: _zheight});

	// Optional settings/properties of the DragResize object are:
	//  enabled: Toggle whether the object is active.
	//  handles[]: An array of drag handles to use (see the .JS file).
	//  minWidth, minHeight: Minimum size to which elements are resized (in pixels).
	//  minLeft, maxLeft, minTop, maxTop: Bounding box (in pixels).

	// Next, you must define two functions, isElement and isHandle. These are passed
	// a given DOM element, and must "return true" if the element in question is a
	// draggable element or draggable handle. Here, I'm checking for the CSS classname
	// of the elements, but you have have any combination of conditions you like:

	dragresize.isElement = function(elm){
	  if (elm.className && elm.className.indexOf('drsElement') > -1)
		 return true;
	};

	dragresize.isHandle = function(elm){
	  if (elm.className && elm.className.indexOf('drsMoveHandle') > -1)
		 return true;
	};

	// You can define optional functions that are called as elements are dragged/resized.
	// Some are passed true if the source event was a resize, or false if it's a drag.
	// The focus/blur events are called as handles are added/removed from an object,
	// and the others are called as users drag, move and release the object's handles.
	// You might use these to examine the properties of the DragResize object to sync
	// other page elements, etc.

	dragresize.ondragfocus = function() { };
	dragresize.ondragstart = function(isResize) { };
	dragresize.ondragmove = function(isResize) { };
	dragresize.ondragend = function(isResize) { };
	dragresize.ondragblur = function() { };

	// Finally, you must apply() your DragResize object to a DOM node; all children of this
	// node will then be made draggable. Here, I'm applying to the entire document.
	dragresize.apply(document);


     //增加元素
    function addUpload(_left,_top,_width,_height) {
        if(count >= maxmodel){    
		   alert("已经超出10个模块！");
		   return;//限制最多maxfile个文件框
		}
        ++count; 
		++templateCount;
		addarea(templateCount);
        var newDiv = "<div class='drsElement drsMoveHandle' id='model" +templateCount+"'"
		+" style='left: "+_left+"; top: "+_top+"; width: "+_width+"; height:"+_height+";background: #b1f3ff; text-align: center;z-index:"+templateCount+100+"'><span style='font-size:12px'>播放区域" +templateCount+" </span></div>";
	elementid="model" +templateCount;
        document.getElementById("fathermodel").insertAdjacentHTML("beforeEnd", newDiv);     
		dragresize.resizeHandleSet(document.getElementById("model" +templateCount),true);
		getAttr(document.getElementById("model" +templateCount));
		document.getElementById("divid").value="model" +templateCount;
     }
	 function addarea(scount){
	 	
	 	var trNode;		
	 	trNode = document.createElement( 'tr' );	
		trNode.id="_tr"+scount;
		trNode.height="25px";
		trNode.style.backgroundColor="red";
		tdNode= document.createElement( 'td' );	
		tdNode.align= "left";
		tdNode.style.cursor="pointer";
		tdNode.onclick=Function("javascript:selectarea('"+scount+"');");
		tdNode.innerHTML = "<span style='font-size:12px;margin-left:10px'>播放区域 "+scount+"</span>";
		trNode.appendChild(tdNode);	
		document.getElementById('indexTable').children(0).appendChild(trNode);	
		changeModelBg(scount);
		
	 }
	 function selectarea(scount){
	 	changeModelBg(scount);
		
		dragresize.select(document.getElementById("model" +scount));
	 }
	 function changeModelBg(scount){
	 	for(j=1;j<=templateCount;j++){
			if(document.getElementById("model"+j)){
				if(j==scount)
				document.getElementById("model"+j).style.backgroundColor="#b1f3ff";
				else
				document.getElementById("model"+j).style.backgroundColor="#ffffff";
			}
			if(document.getElementById("_tr"+j)){
				if(j==scount){
					document.getElementById("_tr"+j).style.backgroundColor="#fcf6ac";
					document.getElementById("_tr"+j).style.fontWeight="bold";
				}
				else{
				document.getElementById("_tr"+j).style.backgroundColor="#ffffff";
				document.getElementById("_tr"+j).style.fontWeight="normal";
				}
			}
		}
	 }
	 function getAttr(obj){
	 	document.getElementById("_left").value=parseInt(obj.style.left)+1;
		  document.getElementById("_top").value=parseInt(obj.style.top)+1;
		   document.getElementById("_width").value=parseInt(obj.style.width);
		    document.getElementById("_height").value=parseInt(obj.style.height);
	 
	 
	 }
	  function delarea(diva){
		diva=diva.replace("model","_tr");
	 	var j=document.getElementById(diva).rowIndex;
		document.getElementById('indexTable').deleteRow(j);
	 }    
	 
     //删除指定元素
     function delUpload() {
    
     
	    diva=dragresize.getModelId();
		if(count==0){
			alert("当前未添加模块！");
			return;
		}
		 if(!confirm("提示信息：确认删除模块？")){
     	return;
     }
		if(document.getElementById(diva)){
			delarea(diva);
			count--;
			document.getElementById(diva).parentNode.removeChild(document.getElementById(diva));  
			for(j=templateCount;j>=0;j--){
				var obj=document.getElementById("model" +j);
				if(obj){
					selectarea(j);
					getAttr(obj);
					return;
				}
			} 
		}
		if(count==0){
			document.getElementById("_left").value="";
		  document.getElementById("_top").value="";
		   document.getElementById("_width").value="";
		    document.getElementById("_height").value="";
			document.getElementById("divid").value="";
		}
		
     }
	function changedreDivAttr(type,_value){
		var r1= /^[0-9]*[1-9][0-9]*$/;
		if(!r1.test(_value)){
			alert("输入值必须为正整数！");
			return;
		}
		var divid=document.getElementById("divid").value;
		if(divid!="" && document.getElementById(divid)){
			if(type=="_left"){
				var _nwidth=document.getElementById("_width").value;
				if(_value>(_zwidth-_nwidth)){
					document.getElementById(divid).style.left=_zwidth-_nwidth;
					document.getElementById("_left").value=_zwidth-_nwidth;
				}else{
					document.getElementById(divid).style.left=_value;
				}
			}
			else if(type=="_top"){
				var _nheight=document.getElementById("_height").value;
				if(_value>(_zheight-_nheight)){
					document.getElementById(divid).style.top=_zheight-_nheight;
					document.getElementById("_top").value=_zheight-_nheight;
				}else{
					document.getElementById(divid).style.top=_value;
				}
			}
			
			else if(type=="_width"){
				var _nleft=document.getElementById("_left").value;
				if(_value>(_zwidth-_nleft)){
					document.getElementById(divid).style.width=_zwidth-_nleft;
					document.getElementById("_width").value=_zwidth-_nleft;
				}else{
					document.getElementById(divid).style.width=_value;
				}
			}
			
			else if(type=="_height"){
				var _ntop=document.getElementById("_top").value;
				if(_value>(_zheight-_ntop)){
					document.getElementById(divid).style.height=_zheight-_ntop;
					document.getElementById("_height").value=_zheight-_ntop;
				}else{
					document.getElementById(divid).style.height=_value;
				}
			}
			
		}
	
	}
function addbackgroud(background_title,backgroudPath) 
{ 
//预览代码，支持 IE6、IE7。 
var newPreview;
if(document.getElementById("viewimgid")){
newPreview=document.getElementById("viewimgid");
}else{newPreview=  new Image();
}
newPreview.src = backgroudPath;
newPreview.id="viewimgid";
newPreview.style.width = _zwidth-2;
newPreview.style.height = _zheight-2;
newPreview.style.display = "block"; 
document.getElementById("fathermodel").appendChild(newPreview);
myform.template_background.value=backgroudPath; 
myform.template_background_title.value=background_title; 
} 
function delbackgroud(){
if(confirm("提示信息：确认删除背景图片？")){
if(document.getElementById("viewimgid")){
 var newPreview=document.getElementById("viewimgid");
document.getElementById("fathermodel").removeChild(newPreview);
myform.template_background.value="";
myform.template_background_title.value="";
}
}
}
function document.onkeydown(){
 	switch(window.event.keyCode){  
	
                     case 37 :
					 fun_a();
					 break;  
					 case 38 :
					 fun_b();  
					  break;  
					 case 39 :
					 fun_c();
					  break;  
					 case 40 :
					 fun_d();
					  break;  
		}
}
function fun_a(){
	var divid=document.getElementById("divid").value;
	var _left=parseInt(document.getElementById("_left").value);
	if(_left==0){
		document.getElementById(divid).style.left=_left-1;
		return;
	}
	if(divid!="" && document.getElementById(divid)){
			document.getElementById(divid).style.left=_left-1;
			document.getElementById("_left").value=_left-1;
	}
}
function fun_b(){
    var divid=document.getElementById("divid").value;
var _top=parseInt(document.getElementById("_top").value);
if(_top==0){
		document.getElementById(divid).style.top=_top-1;
		return;
	}
if(divid!="" && document.getElementById(divid)){
		document.getElementById(divid).style.top=_top-1;
		document.getElementById("_top").value=_top-1;
}
}
function fun_c(){
var divid=document.getElementById("divid").value;
var _left=parseInt(document.getElementById("_left").value);
var _width=parseInt(document.getElementById("_width").value);
if(_left>=(_zwidth-_width)){
		document.getElementById(divid).style.left=_left-1;
		return;
	}
if(divid!="" && document.getElementById(divid)){
		document.getElementById(divid).style.left=_left+1;
		document.getElementById("_left").value=_left+1;
}
}
function fun_d(){
    var divid=document.getElementById("divid").value;
var _top=parseInt(document.getElementById("_top").value);
var _height=parseInt(document.getElementById("_height").value);
if(_top>=(_zheight-_height)){
		document.getElementById(divid).style.top=_top-1;
		return;
	}
if(divid!="" && document.getElementById(divid)){
		document.getElementById(divid).style.top=_top+1;
		document.getElementById("_top").value=_top+1;
}
}
function saveTemplate(templateid){
var str="";
var template_name=myform.template_name.value.replace(/\s/g,"");
for(j=1;j<=templateCount;j++){
var obj=document.getElementById("model"+j);
			if(obj){
				str+=parseInt(obj.style.left)+1+",";
				str+=parseInt(obj.style.top)+1+",";
				str+=parseInt(obj.style.width)+",";
				str+=parseInt(obj.style.height)+"!";
			}
}
if(str==""){
alert("请添加模块！");
return;
}else if(template_name==""){
alert("模板名称不能为空！");
return;
}
document.getElementById("divid").value=str;
myform.template_width.value=_zwidth;
myform.template_height.value=_zheight;
myform.action="/rq/updateTemplate?templateid="+templateid;
parent.listtop.location.href="/admin/template/competence_list_top.jsp";
myform.submit();
}
function onCancel(){
parent.listtop.location.href="/admin/template/competence_list_top.jsp";
parent.content.location.href="/admin/template/templateList.jsp";
}
function selectPhoto(){
document.body.scrollTop = "0px";
document.getElementById("div_iframe").width="350";
document.getElementById("div_iframe").height="300";
document.getElementById("divframe").style.left="240";
document.getElementById("divframe").style.top="0px";
document.getElementById("div_iframe").src="/admin/template/selectbackground.jsp?meidaType=IMAGE";
document.getElementById("titlename").innerHTML="设置背景图片";
document.getElementById("divframe").style.display='block';
document.getElementById("massage").style.display='block';
}
function closedivframe(){
document.getElementById("div_iframe").src="/loading.jsp";
document.getElementById('divframe').style.display="none";
}
</script>
	</head>
	<body style="margin-top:10px ;  font-size:12px;">
		<table width="100%" height="100%" border="0" align="left"
			cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top" width="25%" align="center">
					<form action="/rq/createTemplate" name="myform" method="post"
						enctype="multipart/form-data">
						<input type="hidden" name="template_module" id="divid" />
						<input type="hidden" name="template_width" />
						<input type="hidden" name="template_height" />
						<input type="hidden" name="template_background" />
						<div style="height:360px;width:90%; border:1px solid green;">
						<table cellpadding="0" align="center" cellspacing="0" border="0" width="95%">
							<tr>
								<td height="30" align="center"  colspan="2">
									<strong>模板属性</strong>
								</td>
								
							</tr>
							<tr>
								<td height="40">
									<strong>模板名称：</strong>
								</td>
								<td align="left">
									<input type="text" size="25" name="template_name" value="${template.template_name }" ${template.template_expla=='系统模板'?'readonly=readonly':''}  
										class="button" style=" height: 16px" />
								</td>
							</tr>
							<tr>
								<td height="40">
									<strong>背景图片：</strong>
								</td>
								<td  align="left">
								<input type="text" size="12" name="template_background_title"
										class="button"/>&nbsp;&nbsp;
										<img src="/images/image_folder_close.gif" title="设置背景图片"  style="cursor: pointer;" width="16" height="16" onclick="selectPhoto()"/>&nbsp;&nbsp;
									<img src="/images/del.gif"  title="删除背景图片" style="cursor: pointer;"  width="13" height="13" onclick="delbackgroud()"/>
									
								</td>
							</tr>
							<tr>
								<td height="40" align="right" >
									<strong>模板描述：</strong>
								</td>
								<td>
									<input type="text" size="25" name="template_expla" ${template.template_expla=='系统模板'?'readonly=readonly':''}   value="${template.template_expla }"
										class="button" style=" height: 16px" />
								</td>
							</tr>
							<tr style="display: none">
								<td height="40">
									<strong>屏幕类型：</strong>
								</td>
								<td align="left">
									<input type="radio" name="template_type" value="1" 
										 ${template.template_type==1?'checked':'' } />
									横屏
									<input type="radio" name="template_type" value="2" ${template.template_type==2?'checked':'' }  />
									竖屏
								</td>
							</tr>
							<tr>
								<td height="50" align="center" colspan="2">
								<input type="button" onclick="javascript:onCancel()" value="  取 消  "
						class="button" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" value=" 保存模板 " onclick="saveTemplate('${template.template_id }')"
										class="button" />
								</td>
							</tr>
						</table>
						</div>
					</form>
				</td>
				<td width="47%" height="350px" valign="top" colspan="2">
					<div id="fathermodel"
						style="position:absolute;color:black;width:480px;height:360px; 
             border: 1px solid; background-color:#000000;"></div>
				</td>
				<td valign="top" align="left" style="margin-top:10px;">
					<div style="height:360px;width:120px; border:1px solid green;">
						<div
							style=" font-size:12px; text-align:center; font-weight:bold; height:25px; margin-top:10px">
							模块列表
						</div>
						<table id="indexTable" width="100%" align="center" border="0"
							cellspacing="1" bgcolor="#999999" cellpadding="0">
						</table>
					</div>

				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td height="20px" width="200"  align="left">
				
					<input type="button" onclick="javascript:addUpload(-1,-1,100,100);" value="添加模块"
						class="button" />
					<input type="button" onclick="javascript:delUpload();" value="删除模块"
						class="button" />

				</td>
				<td height="50px" >
					左边：<input type="text" id="_left"
						onchange="changedreDivAttr('_left',this.value)" maxlength="3"
						class="button" style=" height: 16px; width: 30px" />&nbsp;px&nbsp;&nbsp;&nbsp;&nbsp;
					上边：<input type="text" id="_top"
						onchange="changedreDivAttr('_top',this.value)" maxlength="3"
						class="button" style=" height: 16px; width: 30px" />&nbsp;px<br/>
					宽度：<input type="text" id="_width"
						onchange="changedreDivAttr('_width',this.value)" maxlength="3"
						class="button" style=" height: 16px; width: 30px" />&nbsp;px&nbsp;&nbsp;&nbsp;&nbsp; 
					高度：<input type="text" id="_height"
						onchange="changedreDivAttr('_height',this.value)" maxlength="3"
						class="button" style=" height: 16px; width: 30px" />&nbsp;px

				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			

		</table>
	<c:forEach var="module" items="${moduleList}">
		<script type="text/javascript">
			addUpload(${module.m_left-1},${module.m_top-1},${module.m_width},${module.m_height});
		</script>
	
	
	</c:forEach>	
<c:if test="${template.template_background!=''}">
	<script type="text/javascript">
		addbackgroud('${template.template_background }','${template.template_background }');
		</script>
	</c:if>
<div id="divframe">
<div  id="massage">
<table cellpadding="0" cellspacing="0" >
	<tr  height="30px;" class=header><td align="left" style="font-weight: bold" onmousedown=MDown(divframe)><span id="titlename"></span></td>
		<td height="5px" align="right"><a href="javaScript:void(0);" style="color: #000000" onclick="closedivframe();">关闭</a></td></tr>
	<tr><td colspan="2">
	<iframe src="/loading.jsp" scrolling="no" id="div_iframe" name="div_iframe" frameborder="0" ></iframe>
	</td></tr>
</table>
</div>
</div>
	</body>
</html>
