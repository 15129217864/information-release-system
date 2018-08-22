<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String moduleid=request.getParameter("moduleid");
String type=request.getParameter("type");
request.setAttribute("moduleid",moduleid);
request.setAttribute("type",type);
 %>

<html>
  <head>
    <title>My JSP 'select_color.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<%=basePath%>js/jquery1.4.2.js"> </script>
<script>
var ColorHex=new Array('00','33','66','99','CC','FF')
var SpColorHex=new Array('FF0000','00FF00','0000FF','FFFF00','00FFFF','FF00FF')
var current=null

function intocolor()
{
var colorTable=''
for (i=0;i<2;i++)
 {
  for (j=0;j<6;j++)
   {
    colorTable=colorTable+'<tr height=12>'
    colorTable=colorTable+'<td width=11 style="background-color:#000000">'
    
    if (i==0){
    colorTable=colorTable+'<td width=11 style="background-color:#'+ColorHex[j]+ColorHex[j]+ColorHex[j]+'">'} 
    else{
    colorTable=colorTable+'<td width=11 style="background-color:#'+SpColorHex[j]+'">'} 

    
    colorTable=colorTable+'<td width=11 style="background-color:#000000">'
    for (k=0;k<3;k++)
     {
       for (l=0;l<6;l++)
       {
        colorTable=colorTable+'<td width=11 style="background-color:#'+ColorHex[k+i*3]+ColorHex[l]+ColorHex[j]+'">'
       }
     }
  }
}
colorTable='<table width=253 border="0" cellspacing="0" cellpadding="0" style="border:0px #000000 solid;border-bottom:none;border-collapse: collapse" bordercolor="000000">'
           +'<tr height=30><td colspan=21 bgcolor=#cccccc>'
           +'<table cellpadding="0" cellspacing="1" border="0" width="100%" style="border-collapse: collapse">'
           +'<tr><td width="3" width="25%"><td><input type="text" name="DisColor" size="6" disabled style="border:solid 1px #000000;background-color:#ffff00"></td>'
           +'<td width="3" width="35%"><td><input type="text" name="HexColor" size="7" style="border:inset 1px;font-family:Arial;" value="#000000"></td><td  width="40%" align="right"><a href="javascript:;" onclick="parent.closeDiv();"><img src="/images/del.gif" height="12px" border="0"/></a>&nbsp;</td></tr></table></td></table>'
           +'<table border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="000000" onmouseover="doOver()" onmouseout="doOut()" onclick="doclick()" style="cursor:hand;">'
           +colorTable+'</table>';          
colorpanel.innerHTML=colorTable
}

function doOver() {
      if ((event.srcElement.tagName=="TD") && (current!=event.srcElement)) {
        if (current!=null){current.style.backgroundColor = current._background}     
        event.srcElement._background = event.srcElement.style.backgroundColor
        DisColor.style.backgroundColor = event.srcElement.style.backgroundColor
        HexColor.value = event.srcElement.style.backgroundColor
        event.srcElement.style.backgroundColor = "white"
        current = event.srcElement
      }
}

function doOut() {

    if (current!=null) current.style.backgroundColor = current._background
}

function changeBgColor(colorvalue){
   var pram="op=htmltext&bgcolor="+colorvalue.replace("#",'')+"&moduleid=${moduleid}";
   //alert(pram);
   $.ajax({
	    type: "POST",
	    url: "<%=basePath%>rq/changegbcolor",
	    data: pram,
	    dataType:"text",
	    success: parseData,
	    error:function(){
	            alert("发送失败！"); //网络断了，或者服务挂了
	          },
	    complete: function (XHR, TS) { XHR = null } //释放ajax资源
 });

}

function parseData(data){
 //$("#status").html(data)
 //setTimeout("$('#status').html('')",1000);
}

function formatNO(str,len){     //将数字字符串格式化为指定长度
	 var strLen=str.length;
	 for(i=0;i<len-strLen;i++){
	  str="0"+str;
	 }
	 return str;
}

function convert(r,g,b){
	 if(isNaN(r)|| 255-r<0 ){
	  r=0;
	 }
	 if(isNaN(g)|| 255-g<0 ){
	  g=0;
	 } 
	 if(isNaN(b)|| 255-b<0 ){
	  b=0;
	 } 
	 var hr=formatNO(parseInt(r).toString(16),2);
	 var hg=formatNO(parseInt(g).toString(16),2);
	 var hb=formatNO(parseInt(b).toString(16),2);
	 var result="#"+hr+hg+hb;
	 return result;
}

function doclick(){
	if (event.srcElement.tagName=="TD"){
		var checkcolor=event.srcElement._background;
		
		if(checkcolor.indexOf("rgb(")!=-1){ //有些浏览器返回 rgb(0,255,356)
		    checkcolor=checkcolor.substring(4).replace(")","");
		    var aColor=checkcolor.split(",");
		     checkcolor=convert(aColor[0],aColor[1],aColor[2]);
		     //alert("checkcolor--2--->"+checkcolor);
		}
		//alert("checkcolor--2--->"+checkcolor);
		var ftype='${type}';
		if(ftype=='clock'){
			saveClock(hex2rgb(checkcolor),checkcolor);
			//parent.closeDiv();
		}else if(ftype=='scrollfg'){
			parent.scrollForm.scrollfg.value=checkcolor.replace("#","");
			parent.scrollForm.scrollfgRGB.value=hex2rgb(checkcolor);
			parent.document.getElementById("scroll_fgId").style.backgroundColor=checkcolor;
			parent.document.getElementById('scroll_marquee').style.color=checkcolor;
			parent.closeDiv();
		}else if(ftype=='scrollbg'){
			parent.scrollForm.scrollbg.value=checkcolor.replace("#","");
			parent.scrollForm.scrollbgRGB.value=hex2rgb(checkcolor);
			parent.document.getElementById("scroll_bgId").style.backgroundColor=checkcolor;
			parent.document.getElementById('scroll_marquee').style.backgroundColor=checkcolor;
			parent.closeDiv();
		}else if(ftype=='weatherfg'){
		
			parent.weatherForm.weatherfg.value=checkcolor.replace("#","");
			parent.weatherForm.weatherfgRGB.value=hex2rgb(checkcolor);
			parent.document.getElementById("weather_fgId").style.backgroundColor=checkcolor;
			parent.closeDiv();
		}else if(ftype=='htmltext'){
		
			//parent.htmltextForm.htmltextbg.value=checkcolor.replace("#","");
			parent.document.getElementById("scroll_bgId").style.backgroundColor=checkcolor;
			parent.closeDiv();
			changeBgColor(checkcolor);
		}
	}
}

function changRGBColor(thatstr){
	thatstr=thatstr.replace("rgb","").replace("RGB","").replace("(","").replace(")","");
	var aColor =thatstr.split(",")//thatstr.replace(/(?:||rgb|RGB)*/g,"").split(",");  
	
    var rgbstr="";
    for(var i=0; i<aColor.length; i++){  
    	rgbstr+="_"+aColor[i].trim();
    }  
    alert("rgbstr---->"+rgbstr.substring(1));
    return rgbstr.substring(1);
}

function hex2rgb(a){ //将16进制颜色转换成rgb   
     // alert(a);
	  a=a.substring(1);   
	  a=a.toLowerCase();   
	  b=new Array();   
	  for(x=0;x<3;x++){   
	     b[0]=a.substr(x*2,2);   
	     b[3]="0123456789abcdef";   
	     b[1]=b[0].substr(0,1);   
	     b[2]=b[0].substr(1,1);   
		b[20+x]=b[3].indexOf(b[1])*16+b[3].indexOf(b[2]);   
	 }   
	 return b[20]+"_"+b[21]+"_"+b[22];   
}

function saveClock(clockfgRGB,clockfg){
clockForm.clockfgRGB.value=clockfgRGB;
clockForm.clockfg.value=clockfg;
clockForm.action="/admin/program/save_clock.jsp?moduleid=${moduleid}";
clockForm.submit();

}
</script>
</head>
<body onLoad="intocolor()" style="margin: 0px">
<div id="colorpanel" style="position: absolute;">
</div>
<form name="clockForm" method="post">
<input type="hidden" name="clockfgRGB" />
<input type="hidden" name="clockfg" />
</form>
</html>
