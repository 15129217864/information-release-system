<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Module"/>
<%
String moduleid=request.getParameter("moduleid");
ModuleDAO mdao= new ModuleDAO();
Module module = mdao.getModuleTempByModuleid(Integer.parseInt(moduleid));
request.setAttribute("module",module);

%>
<html>
  <head>
	<title></title>
	<script language="javascript" src="/js/vcommon.js"></script>
	<style type="text/css">
#demo { 
    background: #FFF; 
    width: 100%; 
    height:${module.m_height*scale}px;
   overflow:hidden;
	white-space:nowrap;
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
    white-space:nowrap;
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
    white-space:nowrap;
}
</style>
  </head>
  <body STYLE="background-color:transparent"> 
</body>
<div id="scroll_model" style="display: none; word-wrap:break-word;width:${module.m_width*scale}px;">${module.scroll_content}</div>
<div id="scrool_id"  style="width:100%; height:100%;"></div>

<script> 
var scroll_div='<div id="demo" title="滚动文字效果"> <div id="indemo"> <div id="demo1">'+document.getElementById("scroll_model").innerHTML+'<div style="width:576px"></div></div>'+ 
'<div id="demo2"></div></div></div>';

document.getElementById("scrool_id").innerHTML=scroll_div;
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
speed=5;
}else if(sped=='20'){
speed=5;
}else if(sped=='30'){
speed=1;
}else if(sped=='10'){
speed=30;
}
if(n==1){
clearInterval(MyMar);
}
MyMar=setInterval(Marquee,speed); 
}
changespan(0,'${module.span}');
--> 
</script> 