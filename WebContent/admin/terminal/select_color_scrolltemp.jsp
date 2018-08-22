<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
request.setAttribute("type",request.getParameter("type"));
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
<script>
var ColorHex=new Array('00','33','66','99','CC','FF')
var SpColorHex=new Array('FF0000','00FF00','0000FF','FFFF00','00FFFF','FF00FF')
var current=null

function intocolor(){

	var colorTable=''
	for (i=0;i<2;i++){
	  for (j=0;j<6;j++){
	    colorTable=colorTable+'<tr height=12>'
	    colorTable=colorTable+'<td width=11 style="background-color:#000000"></td>'
	    
	    if (i==0){
	       colorTable=colorTable+'<td width=11 style="background-color:#'+ColorHex[j]+ColorHex[j]+ColorHex[j]+'"></td>' 
	    }else{
	       colorTable=colorTable+'<td width=11 style="background-color:#'+SpColorHex[j]+'"></td>'
	    } 
	    
	    colorTable=colorTable+'<td width=11 style="background-color:#000000"></td>'
	    for (k=0;k<3;k++){
	       for (l=0;l<6;l++){
	        colorTable=colorTable+'<td width=11 style="background-color:#'+ColorHex[k+i*3]+ColorHex[l]+ColorHex[j]+'"></td>'
	       }
	     }
	  }
	}
	colorTable=colorTable+'</tr>';
	colorTable='<table width=253 border="0" cellspacing="0" cellpadding="0" style="border:1px #000000 solid;border-bottom:none;border-collapse: collapse" bordercolor="000000">'
	           +'<tr height=30><td colspan=21 bgcolor=#cccccc>'
	           +'<table cellpadding="0" cellspacing="1" border="0" width="100%" >'
	           +'<tr><td width="3" ><td><input type="text" name="DisColor" size="6" disabled style="border:solid 1px #000000;background-color:#ffff00"></td>'
	           +'<td width="3" ><td><input type="text" name="HexColor" size="7" readonly style="border:inset 1px;font-family:Arial;" value="#000000"></td><td  width="40%" align="right"><a href="javascript:;" onclick="parent.closeDiv();"><img src="/images/del.gif" height="12px" border="0"/></a>&nbsp;</td></tr></table></td></table>'
	           +'<table border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="000000" onmouseover="doOver(event)" onmouseout="doOut()" onclick="doclick(event)" style="cursor:hand;">'
	           +colorTable+'</table>';          
	colorpanel.innerHTML=colorTable
}

  function doOver(e) {
	  e=e||event; //为了兼容火狐浏览器
	  var browser='${browser}';
	  var obj=e.srcElement;
	  if(browser=='firefox'){
		  obj=e.target;
	  }
      if ((obj.tagName=="TD") && (current!=obj)) {
	        if (current!=null){
	        	current.style.backgroundColor = current._background
	        }     
	        obj._background = obj.style.backgroundColor
	        DisColor.style.backgroundColor = obj.style.backgroundColor
	        HexColor.value = obj.style.backgroundColor
	        obj.style.backgroundColor = "white"
	        current = obj;
      }
  }

function doOut() {

    if (current!=null) 
    	current.style.backgroundColor = current._background
}

function doclick(e){
	  e=e||event; //为了兼容火狐浏览器
	  var browser='${browser}';
	  var obj=e.srcElement;
	  if(browser=='firefox'){
		  obj=e.target;
	  }
	 //alert(obj.tagName);
	 if (obj.tagName=="TD"){
		var checkcolor=obj._background;
		var ftype='${type}';
		if(ftype=='scrollfg'){
			
			parent.form1.scrollfg.value=checkcolor.replace("#","");
			//parent.form1.scrollfgRGB.value=hex2rgb(checkcolor); //过时浏览器使用
			parent.form1.scrollfgRGB.value=changRGBColor(checkcolor);
			parent.document.getElementById("scroll_fgId").style.backgroundColor=checkcolor;
			//parent.document.getElementById('demo1').style.color=checkcolor;
			//parent.document.getElementById('demo2').style.color=checkcolor;
			parent.closeDiv();
		}else if(ftype=='scrollbg'){
			parent.form1.scrollbg.value=checkcolor.replace("#","");
			//parent.form1.scrollbgRGB.value=hex2rgb(checkcolor);//过时浏览器使用
			parent.form1.scrollbgRGB.value=changRGBColor(checkcolor);
			parent.document.getElementById("scroll_bgId").style.backgroundColor=checkcolor;
			//parent.document.getElementById('demo').style.backgroundColor=checkcolor;
			parent.closeDiv();
		}
	}
}

//RGB 转16进制
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

function changRGBColor(thatstr){
	thatstr=thatstr.replace("rgb","").replace("RGB","").replace("(","").replace(")","");
	var aColor =thatstr.split(",")//thatstr.replace(/(?:||rgb|RGB)*/g,"").split(",");  
	
    var rgbstr="";
    for(var i=0; i<aColor.length; i++){  
    	rgbstr+="_"+aColor[i].trim();
    }  
    //alert("rgbstr---->"+rgbstr.substring(1));
    return rgbstr.substring(1);
}

//老板版本
function hex2rgb(a){ //将16进制颜色转换成rgb   
	alert(a);
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
  //alert(b[20]+"_"+b[21]+"_"+b[22])
  return b[20]+"_"+b[21]+"_"+b[22];   
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
