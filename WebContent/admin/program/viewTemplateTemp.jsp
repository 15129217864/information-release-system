<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Template"/>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String templateid=request.getParameter("templateid");
String scale=request.getParameter("scale")==null?"1":request.getParameter("scale");
String moduleid=request.getParameter("moduleid")==null?"1":request.getParameter("moduleid");
TemplateDAO templatedao= new TemplateDAO();
ModuleDAO moduledao= new ModuleDAO();
Template template=templatedao.getTemplateTempById(templateid);
List moduleList=moduledao.getModuleTempByTemplateId(templateid);
request.setAttribute("template",template);
request.setAttribute("scale",scale);
request.setAttribute("moduleList",moduleList);
request.setAttribute("moduleid",moduleid);
%>
<html>
	<head>
	<title>节目编辑</title><script language="javascript" src="/js/vcommon.js"></script>
	<link rel="stylesheet" href="/css/style.css" type="text/css" />
	<style type="text/css">
	.drsElement {
		position: absolute;
		border: 1px solid green;
		overflow:hidden; text-overflow:ellipsis; white-space:nowrap;}
	.drsMoveHandle {
		 height: 20px;
		 background-color: #CCC;
		 cursor: move;}
	.dragresize {
		 position: absolute;
		 width: 5px;
		 height: 5px;
		 font-size: 1px;
		 background: #EEE;
		 border: 1px solid #333;}
	.dragresize-tl {
		 top: -8px;
		 left: -8px;
		 cursor: nw-resize;}
	.dragresize-tm {
		 top: -8px;
		 left: 50%;
		 margin-left: -4px;
		 cursor: n-resize;}
	.dragresize-tr {
		 top: -8px;
		 right: -8px;
		 cursor: ne-resize;}
	.dragresize-ml {
		 top: 50%;
		 margin-top: -4px;
		 left: -8px;
		 cursor: w-resize;}
	.dragresize-mr {
		 top: 50%;
		 margin-top: -4px;
		 right: -8px;
		 cursor: e-resize;}
	.dragresize-bl {
		 bottom: -8px;
		 left: -8px;
		 cursor: sw-resize;}
	.dragresize-bm {
		 bottom: -8px;
		 left: 50%;
		 margin-left: -4px;
		 cursor: s-resize;}
	.dragresize-br {
		 bottom: -8px;
		 right: -8px;
		 cursor: se-resize;}
 </style>
	<script type="text/javascript">
    var count= 0 ; 
	var templateCount=0;  
    var maxmodel = 10;
	var diva="null";
	var _zwidth=${template.template_width*scale};
	var _zheight=${template.template_height*scale};
    function addUpload(_left,_top,_width,_height,module_bgcolor) {
        if(count >= maxmodel){    
		   alert("已经超出10个模块！");
		   return;//限制最多maxfile个文件框
		}
		++templateCount;
        var newDiv = "<div class='drsElement drsMoveHandle' id='model" +templateCount+"'"
		+" style='left: "+_left+"; top: "+_top+"; width: "+_width+"; height:"+_height+";background: "+module_bgcolor+"; text-align: center;z-index:"+templateCount+100+"'><span style='font-size:12px'>播放区域" +templateCount+" </span></div>";
	elementid="model" +templateCount;
        document.getElementById("fathermodel").insertAdjacentHTML("beforeEnd", newDiv);     
}
function viewImg(backgroudPath) { 
var newPreview=new Image();
newPreview.src = backgroudPath;
newPreview.style.width = _zwidth-2;
newPreview.style.height = _zheight-2;
newPreview.style.display = "block"; 
document.getElementById("fathermodel").appendChild(newPreview);
}
</script>
	</head>
	<body style="margin:5px ;font-size:12px;">

<div id="fathermodel" style="position:absolute;color:black;width:${template.template_width*scale};height:${template.template_height*scale};border: 1px solid; background-color:#ffffff;"></div>
    <c:if test="${template.template_background!=''}">
	<script type="text/javascript">
		viewImg('${template.template_background }');
	</script>
	</c:if>
	<c:forEach var="module" items="${moduleList}">
		<c:choose>
			<c:when test="${moduleid==module.id}">
				<c:set var="module_bgcolor" value="#b1f3ff" />
			</c:when>
			<c:otherwise>
				<c:set var="module_bgcolor" value="#ffffff" />
			</c:otherwise>
		</c:choose>
		<script type="text/javascript">
			addUpload(${module.m_left>5?(module.m_left-1)*scale:(module.m_left-1)*scale-1},${module.m_top==0?(module.m_top)*scale-1:(module.m_top)*scale},${module.m_width*scale},${module.m_height*scale+1},'${module_bgcolor}');
		</script>
	</c:forEach>
	</body>
</html>
