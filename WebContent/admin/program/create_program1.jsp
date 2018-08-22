<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	TemplateDAO templatedao = new TemplateDAO();
	List templateList = templatedao.getAlltemplate(" order by id desc");
	request.setAttribute("templatelist", templateList);
	
%>

<html>
	<head>
		<title>My JSP 'create_program.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<link rel="stylesheet" href="/css/dtree.css" type="text/css" />
		<script language="JavaScript" src="/js/dtree.js"></script>
<script type="text/javascript">
function searchTemplate(template){
	var templates=template.split("_");
	document.getElementById("templatefile").value=templates[2];
	document.getElementById("templateid").value=templates[0];
	document.getElementById("template_img").src="/admin/template/"+templates[2]+"/"+templates[1];
	document.getElementById("template_img_href").href="/admin/template/"+templates[2]+"/"+templates[1];
}
function nextCreateProgram(){
	var programname=form1.programe.value;
	var zuid=form1.zuid.value.replace(/\s/g,"");
	var templateid=form1.templateid.value.replace(/\s/g,"");
	var reg = /^(\w|[\u4E00-\u9FA5])*$/;
	if(templateid==0){
	alert("请选择模板！");
	return
	}else if(programname.replace(/\s/g,"")==""){
	alert("请输入节目名称！");
	return;
	}else if(programname.match(reg)==null){ 
	alert("节目名称只允许为下划线、英文、数字和汉字的组合。");    
	form1.programe.value="";
	return;
	} else if(zuid==""||zuid=="0"){
	alert("请选择节目组！");
	return;
	}
	//alert(templateid);
	form1.action="/admin/program/create_program1DO.jsp";
	form1.submit();
}
var SubIFLayer;
var reclick=0;

function popupCategoryWindow(e) {
	if(reclick==0){
		reclick=1;
		var tsrc = "/admin/program/create_program_zu.jsp";
		makeSubLayer('类别树状视图',200,250,e);
		bindSrc('treeviewPopup',tsrc);
	}
}

function makeSubLayer(title,w,h,e){ 
	
	SubIFLayer=document.createElement("div"); 
	SubIFLayer.id = 'divTreeview';
	SubIFLayer.style.cssText="width:"+w+";height:"+h+";z-index:999;position:absolute;top:-4000;left:-4000;border:1px solid #2A5D90;padding:0 0 0 0;color:white;background-color:F5FBFF"; 

	SubIFLayer.innerHTML=""
		+"<table width='100%' border='0'><tr><td width=93% align=center><b>"+title+"</b></td><td align=right width=5%><a href='javascript:;' onclick='closeSubIFLayer()'><img src='/images/icon_popupclose.gif' width='16' height='16' border='0'></td></tr></table>"
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
	 top  = e.clientY-10;
	 width=w; 
	 height=h; 
	};//with 
} 
function bindSrc(objname,source){
	var obj = document.getElementById(objname);
	obj.src = source;
}
function closeSubIFLayer(){
	//SubIFLayer.removeNode(true); //只有IE才有此方法
	SubIFLayer.parentNode.removeChild(SubIFLayer);
	reclick=0;
}
function showTemplate(template_id){
form1.templateid.value=template_id;
document.getElementById("div_iframe").src="/admin/program/viewTemplate.jsp?templateid="+template_id+"&scale=0.8&t=" + new Date().getTime();

}
 function closedivframe(){
	parent.closedivframe(2);
}
</script>
<style type="text/css">
.template_fieldset{width: 175px;height: 100%; border: #6699cc 1 solid;  padding-top: 10px; margin-left: 20px}
.template_div{width: 175px;height: 95%; border: #6699cc 0 solid;  padding-top: 10px; padding-left: 20px; overflow: auto;}
</style>
	</head>
	<body style="margin: 0px;">
		<form action="" name="form1" method="post">
		<input type="hidden" name="zuid" id="zuid" />
		<input type="hidden" name="templateid" id="template_id"/>
			<table width="100%" height="100%" cellpadding="0" cellspacing="0" 	border="0">
				<tr>
					<td width="240" valign="top"  rowspan="2" background="/images/menu_bg1.gif" style="background-position: right; background-repeat: repeat-y">
					<fieldset class="template_fieldset">
					<legend align="center" >选择模板</legend>
					<div  class="template_div">
					   <script language="javascript">
							content = new dTree('content'); 
							var init_tree = null;
							var num_one_url="";
							content.clearCookie();	
							content.add('1',-1,"所有模板","javascript:;",'','','');
							<c:set var="num" value="0"></c:set>
							<c:forEach var="template" items="${templatelist}">
								<c:set var="num" value="${num+1}"></c:set>
								<c:if test="${num==1}">
								   num_one_url='${template.template_id}';
								</c:if>
								content.add('${template.template_id}','1',' ${template.template_name }',"javascript:showTemplate('${template.template_id}');",'','','/images/dtreeimg/img.gif');
							</c:forEach> 
							 document.write(content); 
							 var selected_content = content.getSelected();
							if(selected_content == null  || selected_content == "")
							content.s(0);			                    
						 
		               </script>
		    </div>
					</fieldset>
					
					
					</td>
					<td>
						<table cellspacing="0"  cellpadding="0" style="margin-left: 30px;margin-top: 30px" width="100%" border="0">
							<tr>
								<td height="30px" width="10%">
									节目名称：
								</td>
								<td colspan="2"  width="90%" style="color: red">
									<input type="text" name="programe" maxlength="20" class="button" />&nbsp;&nbsp;&nbsp;提示信息：节目名称只允许为下划线、英文、数字和汉字的组合
								</td>
							</tr>
							<tr>
								<td height="30px" width="10%">
									节 目 组：
								</td>
								<td width="200px" width="30%">
									<input type="text" name="zuname" id="zuname" readonly="readonly"   class="button" />
									<a href="javascript:;" onClick="javascript:popupCategoryWindow(event);" >
							<img src="/images/btn_search2.gif" width="18" height="18"
								border="0" align="middle" onfocus="this.blur()"> </a>
								</td>
								<td  width="60%">
								<input type="button" value=" 下一步 " onclick="javascript:nextCreateProgram()"  class="button1" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" value=" 取  消 " onclick="closedivframe();"  class="button1" />
								</td>
							</tr>
							
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" width="100%" border="0">
							<tr>
								<td class="TitleBackground">
									模板预览
							</tr>
							<tr>
								<td>
									<iframe src="/loading.jsp" scrolling="no" width="600" height="370" id="div_iframe" name="div_iframe" frameborder="0" ></iframe>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>

		<script type="text/javascript">
			document.getElementById("div_iframe").src="/admin/program/viewTemplate.jsp?templateid="+num_one_url+"&scale=0.8&t=" + new Date().getTime();
			form1.templateid.value=num_one_url;
		</script>
		
	</body>
</html>
