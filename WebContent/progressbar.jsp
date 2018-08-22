<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<html>
<head>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
<title>文件上传进度</title>
<script language="javascript" src="/js/vcommon.js"></script>
<script language="javascript">

function GetProgressDetail()
{
	var funid=(new Date()).getTime();
    var detail = new ActiveXObject("Microsoft.XMLHTTP");
    detail.open("GET","/rq/progressdetail?funid="+funid,false);
    detail.send();
	var detail_info=detail.responseText;
	var start=detail_info.indexOf("<detail-start>");
	var end=detail_info.indexOf("</detail-start>");
	if(start!=-1 && end!=-1){
		detail_info=detail_info.substring(start+14,end);
		var detail_info_array=detail_info.split("||");
		var fileName=detail_info_array[0];
		
		if(fileName.length>18) fileName="..."+fileName.slice(fileName.length-19);
		setMessageContent("正在上传："+fileName);
		progressBar.UpdateToPersent(detail_info_array[1]);
		progressBar.SetProgressTextValue(detail_info_array[1]+"%");
		setUploadDetail(detail_info_array[2],
						detail_info_array[3],
						detail_info_array[4],
						detail_info_array[5],
						detail_info_array[6]);
	}
}
function closeWindow(){
 		if(confirm("提示信息：正在上传媒体，确认停止上传？")){
	 		parent.div_iframe1.document.execCommand("Stop");
	 		parent.closedivframe(1);
	 		parent.div_iframe1.document.getElementById("saveid").disabled="" ;
	 		parent.div_iframe1.isclose=0;
 		}
 }
 function setMessageContent(mes){
 	document.all.messageContent.innerHTML=mes;
 }
 function setUploadDetail(speed,readTotalSize,totalSize,remainTime,totalTime){
 	document.all.speed.innerText=speed;
	document.all.readTotalSize.innerText=readTotalSize;
	document.all.totalSize.innerText=totalSize;
	document.all.remainTime.innerText=remainTime;
	document.all.totalTime.innerText=totalTime;
 }
 //以下为进度条对象代码
 function xyProgressLG(xyID){

  this.xyProgressID = 'oProgress' + Math.random().toString().substr(2, 10) + xyID;
  this.max = 0;
  this.min = 0;
 
  this.width = 100;
  this.height = 16;
  this.currPos = 0;
  this.outerBorderColor = "#ffffff";
  this.outerBackColor = "white";
  this.innerBorderColor = "";
  this.innerBackColor = "blue";
  this.TextColor = "red";

  this.SetProgressTop = xySetProgressTop;
  this.SetProgressLeft = xySetProgressLeft;
  this.SetProgressWidth = xySetProgressWidth;
  this.SetProgressHeight = xySetProgressHeight;
  this.SetProgressBorderColor = xySetProgressBorderColor;
  this.SetProgressForeBorderColor = xySetProgressForeBorderColor;
  this.SetProgressBackColor = xySetProgressBackColor;
  this.SetProgressForeColor = xySetProgressForeColor;
  this.EnableProgressText = xyEnableProgressText;
  this.SetProgressTextFontSize = xySetProgressTextFontSize;
  this.SetProgressTextFontColor = xySetProgressTextFontColor;
  this.SetProgressTextFontFamily = xySetProgressTextFontFamily;
  this.SetProgressTextValue=xySetProgressTextValue;

  this.SetProgressMax = xySetProgressMax;
  this.SetProgressMin = xySetProgressMin;
  this.UpdatePosition = xyUpdatePosition;
  this.UpdateToPosition = xyUpdateToPosition;
  this.UpdatePersent = xyUpdatePersent;
  this.UpdateToPersent = xyUpdateToPersent;

  xyProgressInit(this.xyProgressID);
  return this.xyProgressID;
 }

 function xyProgressInit(xyID){
  var div = document.createElement("div");
  div.id = xyID;
  div.style.left = "100px";
  div.style.top = "100px";
  document.getElementById("uploadstatus").appendChild(div);

  var pro = document.createElement("div");
  pro.style.left = "0px";
  pro.style.top = "0px";
  pro.style.fontSize= "0px";
  pro.style.width = "100px";
  pro.style.height = "16px";
  pro.style.border = "1px solid #cccccc";
  pro.style.background = "white";
  pro.style.zIndex = "100";
  div.appendChild(pro);

  var proInner = document.createElement("div");
  proInner.id = xyID+"_Inner";
  proInner.style.left = "0px";
  proInner.style.top = "0px";
  proInner.style.fontSize= "0px";
  proInner.style.width = "0px";
  proInner.style.height = "16px";
  proInner.style.border = "1px solid #cccccc";
  proInner.style.background = "blue";
  pro.style.zIndex = "102";
  pro.appendChild(proInner);

  var text = document.createElement("div");
  text.style.position = "absolute";
  text.style.left = "285px";
  text.style.top = "30px";
  text.style.fontSize= "14px";
  text.style.width = "30px";
  text.style.height = "16px";
  text.style.zIndex = "103";
  text.style.color="red";
  text.style.fontWeight="bolder"; 
  div.appendChild(text);
 }
 function xySetProgressTop(pStyleTop){ //设置top值
  try{
   var e = document.all(this.xyProgressID);
   e.style.top = pStyleTop;
  }catch(e){
   alert(e.description);
  }
 }
 function xySetProgressLeft(pStyleLeft){ //设置left值
  try{
   var e = document.all(this.xyProgressID);
   e.style.left = pStyleLeft;
  }catch(e){
   alert(e.description);
  }
 }
 function xySetProgressWidth(pWidth){ //设置width值
  try{
   var e = document.all(this.xyProgressID);
   e.style.width = pWidth;
   eOuter = e.children(0);
   eOuter.style.width = pWidth;
   this.width = pWidth;
  }catch(e){
   alert(e.description);
  }
 }
 function xySetProgressHeight(pHeight){ //设置height值
  try{
   var e = document.all(this.xyProgressID);
   e.style.height = pHeight;
   eOuter = e.children(0);
   eInner = e.children(0).children(0);
   eOuter.style.height = pHeight;
   eInner.style.height = pHeight;
   this.height = pHeight;
  }catch(e){
   alert(e.description);
  }
 }
 function xySetProgressBorderColor(pColor){ //设置外边框border的color值
  try{
   var e = document.all(this.xyProgressID);
   e = e.children(0);
   e.style.border = "1px solid "+pColor;
  }catch(e){
   alert(e.description);
  }
 }
 function xySetProgressForeBorderColor(pColor){  //设置内边框border的color值
  try{
   var e = document.all(this.xyProgressID);
   e = e.children(0).children(0);
   e.style.border = "1px solid "+pColor;
  }catch(e){
   alert(e.description);
  }
 }
 function xySetProgressBackColor(pColor){  //设置外边框背影色
  try{
   var e = document.all(this.xyProgressID);
   e = e.children(0);
   e.style.background = pColor;
  }catch(e){
   alert(e.description);
  }
 }
 function xySetProgressForeColor(pColor){ //设置内边框行进条色
  try{
   var e = document.all(this.xyProgressID);
   e = e.children(0).children(0);
   e.style.background = pColor;
  }catch(e){
   alert(e.description);
  }
 }
 function xyEnableProgressText(bEnable){ //是否显示文字的开关，默认是true，显示
  try{
   var e = document.all(this.xyProgressID);
   eText = e.children(1);
   if(bEnable){
    eText.style.display = "none";
   }else{
    eText.style.display = "";
   }
   var pos = e.style.width;
   var sel = eText.style.width;
   pos = (pos-sel)/2;
   eText.style.left = pos;
  }catch(e){
   alert(e.description);
  }
 }
 function xySetProgressTextFontSize(pSize){ //设置显示文字的大小
  try{
   var e = document.all(this.xyProgressID);
   e = e.children(1);
   e.style.fontSize = pSize;
  }catch(e){
   alert(e.description);
  }
 }
 function xySetProgressTextFontColor(pColor){ //设置显示文字的颜色
  try{
   var e = document.all(this.xyProgressID);
   e = e.children(1);
   e.style.color = pColor;
  }catch(e){
   alert(e.description);
  }
 }
 function xySetProgressTextFontFamily(pFamily){ //设置显示文字的字体
  try{
   var e = document.all(this.xyProgressID);
   e = e.children(1);
   e.style.fontFamily = pFamily;
  }catch(e){
   alert(e.description);
  }
 }
 function xySetProgressMax(pMax){ //进度条表示的最大长度
  this.max = pMax;
 }
 function xySetProgressMin(pMin){ //进度条表示的最小长度
  this.min = pMin;
 }
 function xySetProgressTextValue(pText){ //设置显示文字的内容
   try{
   var e = document.all(this.xyProgressID);
   e = e.children(1);
   e.innerHTML=pText;
   }
   catch(e){
   alert(e.description);
  }
 }
 function xyUpdatePosition(pPosition){   /*将progress增长或减小pPosition(表示长度),其中,0<pPosition<max*/
  try{
   var max = this.max;
   var min = this.min;
   var e = document.all(this.xyProgressID);
   if(pPosition>max){
    return -1;
   }
   var len = this.width;
   var pos = this.currPos+(pPosition/(max-min))*len;
   var eProgress = e.children(0).children(0);
   if(pos>=this.width){
    eProgress.style.width = this.width-4;
    this.currPos = pos;
   }else if(pos<=0){
    eProgress.style.width = 0;
    this.currPos = pos;
   }else{
    eProgress.style.width = pos;
    this.currPos = pos;
   }
   return this.currPos;
  }catch(e){
   alert(e.description);
   return -1;
  }
 }
 function xyUpdateToPosition(pPosition){   /*将progress更新到pPosition位置(表示长度),其中,min<pPosition<max*/
  try{
   var max = this.max;
   var min = this.min;
   var e = document.all(this.xyProgressID);
   if(pPosition<min||pPosition>max){
    return -1;
   }
   var len = this.width;
   var pos = ((pPosition-min)/(max-min))*len;
   eProgress = e.children(0).children(0);
   if(pos>=this.width){
    eProgress.style.width = this.width-4;
    this.currPos = pos;
   }else if(pos<=0){
    eProgress.style.width = 0;
    this.currPos = pos;
   }else{
    eProgress.style.width = pos;
    this.currPos = pos;
   }
   return this.currPos;
  }catch(e){
   alert(e.description);
   return -1;
  }
 }
 function xyUpdatePersent(pPersent){   /*从当前位置增长或减小(+\-)pPersent%(html进度条长度),pPersent大于零:增长,否则可以小于零,减小*/
  try{
   var e = document.all(this.xyProgressID);
   e = e.children(0).children(0);
   var len = this.width;
   var pos = this.currPos;
   len = len*pPersent/100;
   pos += len;
   if(pos>this.width||pos<0){
    return -1;
   }
   e.style.width = pos;
   this.currPos = pos;
   return this.currPos;
  }catch(e){
   alert(e.description);
   return -1;
  }
 }
 function xyUpdateToPersent(pPersent){   /*从当前位置增长或减小到pPersent%(html进度条长度)*/
  try{
   var e = document.all(this.xyProgressID);
   e = e.children(0).children(0);
   var len = this.width;
   var pos = len*pPersent/100;
   if(pos>this.width||pos<0){
    return -1;
   }
   e.style.width = pos;
   this.currPos = pos;
   return this.currPos;
  }catch(e){
   alert(e.description);
   return -1;
  }
 }

 function xyNewID(){
  var d = new Date();
  var t = "oDraw_"+d.getTime().toString();
  return t;
 }

 //-->
</script>
<style type="text/css">
td{font-size: 11px;  font-family: arial}
</style>
</head>

<body  style="margin-left: 15px; margin-top: 5px;" >
<table width="310" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td colspan="3" height="25px;">信息:<label id="messageContent"></label></td>
	</tr>
	<tr>
		<td colspan="3" height="20px;" id="uploadstatus"></td>
	</tr>
	<tr>
		<td  height="20px;">已上传：<label id="readTotalSize"></label>M</td>
		<td>上传速度：<label id="speed"></label> K/S</td>
		<td >总共：<label id="totalSize"></label>M</td>
	</tr>
	<tr>
		<td height="20px;">总时间：<label id="totalTime"></label></td>
		<td>剩余时间：<label id="remainTime"></label></td>
		<td></td>
	</tr>
	<tr>
		<td colspan="3" height="20px">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="3" height="25px;">
			<!--<input name="isStop" type="checkbox"  class="button"  id="isStop" value="0">关闭同时停止上传   -->
			<!-- <input name="cancle" type="button"   class="button"  id="cancle" value=" 停止上传 " onClick="closeWindow();"> -->
			&nbsp;正在上传软件，请稍后...
		</td>
	</tr>
</table>

<script language="javascript">
var progressBar = new xyProgressLG("upload");
 progressBar.SetProgressLeft(5);
 progressBar.SetProgressTop(25);
 progressBar.SetProgressWidth(260);
 progressBar.SetProgressHeight(12);
 progressBar.SetProgressBackColor("#ffffff");
 progressBar.SetProgressForeColor("green");
 progressBar.SetProgressTextFontColor("#000000");
 progressBar.SetProgressMax(260);
 progressBar.SetProgressMin(0);
 setMessageContent("正在进行连接...");
 window.setInterval("GetProgressDetail()",1000);
</script>
</body>
</html>
