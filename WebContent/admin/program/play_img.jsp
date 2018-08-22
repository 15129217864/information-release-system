<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Module"/>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String moduleid=request.getParameter("moduleid");
String opp=request.getParameter("opp")==null?"0":request.getParameter("opp");
ModuleDAO mdao= new ModuleDAO();
List<Module> mediaList = mdao.getMediaByModuleId(Integer.parseInt(moduleid));
Module module = mdao.getModuleTempByModuleid(Integer.parseInt(moduleid));
if("0".equals(opp)){
String[] img_style=module.getFontName().split("#");
request.setAttribute("img_style",img_style);
}else{
String style_s=request.getParameter("style_s")==null?"0":request.getParameter("style_s");
String[] img_style=style_s.split(",");
request.setAttribute("img_style",img_style);
}
request.setAttribute("module",module);
request.setAttribute("first_img",mediaList.size()>0?mediaList.get(0).getWeatherfile()+"?t="+new Date().getTime():"");
//System.out.println("------------------------>"+mediaList.get(0).getWeatherfile()+"?t="+new Date().getTime());
request.setAttribute("mediaList",mediaList);
%>

<html>
  <head>
    <title>My JSP 'select_media.jsp' starting page</title>
	<link rel="stylesheet" href="/css/style.css" type="text/css"/>
	<script language="javascript" src="/js/vcommon.js"></script>
	</head>
  
<body>
<script language="javascript"> 
var style_array=new Array(); 
<c:set var="m" value="0"></c:set>
<c:forEach var="i_s" items="${img_style}">
style_array[${m}]="${i_s}"; <c:set var="m" value="${m+1}"></c:set></c:forEach>


var e_tp=new Array(); 
var adNum_elady1=0; 
<c:set var="i" value="0"></c:set>
<c:forEach var="media" items="${mediaList}">
e_tp[${i}]="${media.weatherfile}"; <c:set var="i" value="${i+1}"></c:set></c:forEach>


function elady1_set(){
	if (document.all){
	    var itmp=Math.floor(Math.random()*style_array.length); 
		//if(itmp>=17&&itmp<=20)itmp=10;
        //alert(itmp);//17,18,19,20 斜向特效底部会出现白边
		e_tprotator.filters.revealTrans.Transition=style_array[itmp];
		e_tprotator.filters.revealTrans.apply(); 
	}
}
function elady1_playCo(){
	if (document.all)
	  e_tprotator.filters.revealTrans.play()
}
function elady1_nextAd(){
	if(adNum_elady1<e_tp.length-1) 
	  adNum_elady1++ ;
	else 
	  adNum_elady1=0;
	elady1_set();
	document.getElementById("e_p_id").src=e_tp[adNum_elady1];
	elady1_playCo();
	theTimer=setTimeout("elady1_nextAd()", ${module.span});
}
</script>
<img  width="100%" height="100%"  id="e_p_id" name="e_tprotator" style="FILTER: revealTrans(duration=2,transition=22)" src="${first_img}">
<script type="text/javascript">
elady1_nextAd();
</script>
</body>
</html>


