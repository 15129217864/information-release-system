<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Template"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String screentype=request.getParameter("screentype")==null?"1_560_420":request.getParameter("screentype");
TemplateDAO pdao = new TemplateDAO();
List<Template> list=pdao.getAllScreenType("");

request.setAttribute("screenlist",list);
request.setAttribute("screentype",screentype);


 %>
<html>
	<head>
		<title>节目编辑</title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
			<style type="text/css">
	.drsElement {
		 position: absolute;
		 border: 1px solid green;
	}
	.drsMoveHandle {
		 height: 20px;
		 background-color: #CCC;
		 cursor: move;
	}
	.dragresize {
		 position: absolute;
		 width: 7px;
		 height: 7px;
		 font-size: 1px;
		 background: #EEE;
		 border: 1px solid #333;
	}
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
	#divframe{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none }
#massage{border:#6699cc solid; border-width:1px; background:#fff; color:#036; font-size:12px; line-height:150%;  display: none}
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
		var dragresize=null;
		
         //============================================
        var browertype="IE8.0";
        var Sys = {};
        var ua = navigator.userAgent.toLowerCase();
        if (isIE()){
		    var temp="";
            if(ua.indexOf("msie")!=-1){
		       temp =ua.match(/msie ([\d.]+)/)[1];
            }
			if(temp==""){//IE11判断
			   temp=ua.match(/rv:([\d.]+)/)[1];
             }
            Sys.ie = temp
        }else if (document.getBoxObjectFor)
            Sys.firefox = ua.match(/firefox\/([\d.]+)/)[1]
        else if (window.MessageEvent && !document.getBoxObjectFor)
            Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1]
        else if (window.opera)
            Sys.opera = ua.match(/opera.([\d.]+)/)[1]
        else if (window.openDatabase)
            Sys.safari = ua.match(/version\/([\d.]+)/)[1];
        
        if (Sys.ie) browertype='IE:' + Sys.ie;
        if (Sys.firefox) browertype='Firefox:' + Sys.firefox;
        if (Sys.chrome) browertype='Chrome:' + Sys.chrome;
        if (Sys.opera) browertype='Opera:' + Sys.opera;
        if (Sys.safari) browertype='Safari:' + Sys.safari;

     //============================================
        function isIE() { //ie?
			if (!!window.ActiveXObject || "ActiveXObject" in window)
				return true;
			else
				return false;
		}


    var count= 0 ; 
	var templateCount=0;  
    var maxmodel = 10;
	var diva="null";
	var _zwidth=480;
	var _zheight=360;
	var dragresize=null;
	function createdra(maxwidth,maxheight){
		dragresize = new DragResize('dragresize',{ 
		 minWidth: 65, minHeight: 18, minLeft: 0, minTop: 0, maxLeft: maxwidth, maxTop: maxheight});
		dragresize.isElement = function(elm){
		  if (elm.className && elm.className.indexOf('drsElement') > -1)
			 return true;
		};
	
		dragresize.isHandle = function(elm){
		  if (elm.className && elm.className.indexOf('drsMoveHandle') > -1)
			 return true;
		};
		dragresize.ondragfocus = function() { };
		dragresize.ondragstart = function(isResize) { };
		dragresize.ondragmove = function(isResize) { };
		dragresize.ondragend = function(isResize) { };
		dragresize.ondragblur = function() { };
		dragresize.apply(document);
	}
    function addUpload() {
		createdra(_zwidth,_zheight);
        if(count >= maxmodel){    
		   alert("已经超出10个模块！");
		   return;//限制最多maxfile个文件框
		}
        ++count; 
		++templateCount;
		addarea(templateCount);
        var newDiv = "<div class='drsElement drsMoveHandle' id='model" +templateCount+"'"
		+" style='left: -1px; top: -1px; width: 100px; height:100px;background: #b1f3ff; text-align: center;z-index:"+templateCount+100+"'><span style='font-size:12px'>播放区域" +templateCount+" </span></div>";
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
	
        if(browertype.indexOf("IE:")!=-1){
			   if(parseInt(browertype.split(":")[1])<10){
			      document.getElementById('indexTable').children[0].appendChild(trNode);
			   }else{
			      document.getElementById('indexTable').appendChild(trNode);
			   }	
		}else{
		   document.getElementById('indexTable').appendChild(trNode);
		}
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
		if(count==0){
			alert("当前未添加模块！");
			return;
		}
		if(!confirm("提示信息：确认删除模块？")){
     		return;
     	}
     	diva=dragresize.getModelId();
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
      //删除指定元素
     function delUpload1() {
	    diva=dragresize.getModelId();
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
var backgroudPath=myform.template_background.value; 
if(backgroudPath!=""){
	if(confirm("提示信息：确认删除背景图片？")){
		if(document.getElementById("viewimgid")){
		 var newPreview=document.getElementById("viewimgid");
		document.getElementById("fathermodel").removeChild(newPreview);
		myform.template_background.value="";
		myform.template_background_title.value="";
		}
	}
}else{
alert("当前未添加背景图片！");
}
}
/**
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
**/

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
function saveTemplate(){
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
	myform.action="/rq/createTemplate";
	myform.submit();
}

function selectPhoto(){
	document.body.scrollTop = "0px";
	document.getElementById("div_iframe").width="350";
	document.getElementById("div_iframe").height="300";
	document.getElementById("divframe").style.left="90";
	document.getElementById("divframe").style.top="5px";
	document.getElementById("div_iframe").src="/admin/template/selectbackground.jsp?meidaType=IMAGE&t=" + new Date().getTime();
	document.getElementById("titlename").innerHTML="屏幕管理";
	document.getElementById("divframe").style.display='block';
	document.getElementById("massage").style.display='block';
}
function closeBackGroundDiv(){
	document.getElementById("div_iframe").src="/loading.jsp";
	document.getElementById('divframe').style.display="none";
}


function change_screenType(ct){
	if(ct=="0"){
		document.body.scrollTop = "0px";
		document.getElementById("div_iframe").width="480";
		document.getElementById("div_iframe").height="390";
		document.getElementById("divframe").style.left="250";
		document.getElementById("divframe").style.top="40px";
		document.getElementById("div_iframe").src="/admin/template/screen_type_manager.jsp?t=" + new Date().getTime();;
		document.getElementById("titlename").innerHTML="设置背景图片";
		document.getElementById("divframe").style.display='block';
		document.getElementById("massage").style.display='block';
		
		return ;
	}
	if(count>0){
		if(confirm("更换屏幕类型将清除当前所有模块，确认更换？")){
			window.location.href="/admin/template/create_template.jsp?screentype="+ct;
		}else{
			return;
		}	
	}
	screen_s=ct.split("_");
	
	myform.template_type.value=screen_s[0];
	
	document.getElementById("fathermodel").style.width=screen_s[1];
	document.getElementById("fathermodel").style.height=screen_s[2];
	_zwidth=screen_s[1];
	_zheight=screen_s[2];
	
	changediv_position();
	var backgroudPath=myform.template_background.value; 
	var background_title=myform.template_background_title.value; 
	if(backgroudPath!=""){
	  addbackgroud(background_title,backgroudPath);
	}
}
function changediv_position(){
   document.getElementById("fathermodel").style.left=(document.body.offsetWidth-parseInt(document.getElementById("fathermodel").style.width))/2+15;
}
function closedivframe(){
	
	var str="";
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
	 
	  //parent.closedivframe(2);
	  parent.closedivframe1();
	}else{
		if(confirm("提示信息：模板未保存，确认取消？")){
		  //parent.closedivframe(2);
		  parent.closedivframe1();
		}
	}
}
</script>
	</head>
	<body style="margin-top:10px ; font-size:12px;">
		<form action="/rq/createTemplate" name="myform" method="post">
		<table width="1000" height="100%" border="0" align="left"
			cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top"  rowspan="2" width="23%" align="center" >
				
						<input type="hidden" name="template_module" id="divid" />
						<input type="hidden" name="template_width" />
						<input type="hidden" name="template_height" />
						<input type="hidden" name="template_background" />
						<input type="hidden"  name="template_background_title"/>
						<input type="hidden" name="template_type"/>
						<table cellpadding="0" align="center" cellspacing="5" border="0">
							<tr>
								<td><img src="/admin/template/images/add_t.jpg" width="75" title="添加模块"  style="cursor: pointer;"  onclick="addUpload();"/></td>
								<td><img src="/admin/template/images/del_t.jpg" width="75" title="删除模块"  style="cursor: pointer;" onclick="delUpload();"/></td>
							</tr>
							<tr>
								<td><img src="/admin/template/images/add_b.jpg" width="75"  title="设置背景图片"  style="cursor: pointer;"  onclick="selectPhoto()"/></td>
								<td><img src="/admin/template/images/del_b.jpg" width="75" title="删除背景图片" style="cursor: pointer;"   onclick="delbackgroud()"/></td>
							</tr>
						</table><br/><br/>
						<div style="height:300px;width:90%; border:1px solid #6699cc">
						
						
						<table cellpadding="0" align="center" cellspacing="0" border="0" width="100%">
							<tr>
								<td height="30" align="center"  colspan="2">
									<strong>模板属性</strong>
								</td>
								
							</tr>
							<tr>
								<td height="40" align="center" width="60px">
									<strong>名称：</strong>
								</td>
								<td align="left">
									<input type="text" size="12" name="template_name"
										class="button" style="height: 16px" />
								</td>
							</tr>
							<tr>
								<td height="40"  align="center" >
									<strong>描述：</strong>
								</td>
								<td>
									<textarea rows="8" cols="13" name="template_expla"></textarea>
								</td>
							</tr>
							
							
						</table>
						</div>

				</td>
				<td height="20px" width="30%"  align="right" valign="top">
					屏幕：<select style="width: 120" name="screen_type" onchange="change_screenType(this.value)">
							<c:forEach var="t" items="${screenlist}">
								<option value="${t.s_id}_${t.s_width}_${t.s_height}">${t.s_title}</option>
							</c:forEach>
							<option value="0" onclick="change_screenType('0')">屏幕管理</option>
						</select>&nbsp;&nbsp;
					
				</td> 
				<td valign="top"  width="62%" >
					左：<input type="text" id="_left"
						onchange="changedreDivAttr('_left',this.value)" maxlength="3"
						class="button" style=" height: 16px; width: 26px" />&nbsp;
					上：<input type="text" id="_top"
						onchange="changedreDivAttr('_top',this.value)" maxlength="3"
						class="button" style=" height: 16px; width: 26px" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					宽：<input type="text" id="_width"
						onchange="changedreDivAttr('_width',this.value)" maxlength="3"
						class="button" style=" height: 16px; width: 26px" />&nbsp;
					高：<input type="text" id="_height"
						onchange="changedreDivAttr('_height',this.value)" maxlength="3"
						class="button" style=" height: 16px; width: 26px" />&nbsp;
				</td>
				
				<td valign="top" align="left" width="20%"  rowspan="2" style="margin-top:10px;">
					<div style="height:400px;width:140px; border:1px solid #6699cc;">
						<div style=" font-size:12px; text-align:center; font-weight:bold; height:25px; margin-top:10px">
							模块列表
						</div>
						<table id="indexTable" width="100%" align="center" border="0" cellspacing="1" bgcolor="#999999" cellpadding="0"></table>
					</div>
				</td>
			</tr>
			<tr>
				<td width="47%" height="450px" valign="top" style="padding-top: 10px;" colspan="2">&nbsp;
					<div id="fathermodel"
						style="position:absolute;color:black;width:480px;height:360px; 
             border: 1px solid; background-color:#000000;"></div>
				</td>
			</tr>
			<tr>
			            <td class="Line_01"  colspan="4" ></td>
			          </tr>
			
			<tr>
			   <td colspan="4" align="center">
			      <input type="button" value="保存模板" onclick="saveTemplate()" class="button1" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="button" onclick="closedivframe()" value=" 取 消 " class="button1" />
				</td>
			</tr>
		</table>
	</form>
<div id="divframe">
<div  id="massage">
<table cellpadding="0" cellspacing="0" >
	<tr  height="30px;" class=header><td align="left" style="font-weight: bold" onmousedown=MDown(divframe)><span id="titlename"></span></td>
		<td height="5px" align="right"><a href="javaScript:void(0);" style="color: #000000" onclick="closeBackGroundDiv();">关闭</a></td></tr>
	<tr><td colspan="2">
	<iframe src="/loading.jsp" scrolling="no" id="div_iframe" name="div_iframe" frameborder="0" ></iframe>
		
	</td></tr>
</table>
</div>
</div>
<script type="text/javascript">
   change_screenType('${screentype}');
</script>
	</body>
</html>
