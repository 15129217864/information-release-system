<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.xct.cms.utils.Pager"/>
<jsp:directive.page import="com.xct.cms.utils.PageDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String left_menu=request.getParameter("left_menu");
String str="";
if("TITLE_ASC".equals(left_menu)){
str="order by template_name";
}else if("TITLE_DESC".equals(left_menu)){
str="order by template_name desc";
}else {
str=" order by id desc";
}
TemplateDAO templatedao= new TemplateDAO();
List list=templatedao.getAlltemplate(str);

if(list!=null&&list.size()>0){
				int pagenum =Integer.parseInt(request.getParameter("pagenum")==null?"1":request.getParameter("pagenum"));
				Pager pager= new Pager(list.size(),pagenum,8); 
				List list3= new PageDAO().getPageList(list, pager.getCurrentPage(), pager.getPageSize());
				request.setAttribute("pager", pager);
				request.setAttribute("templateList", list3);
				request.setAttribute("template_sum", list.size());
			}

 %>
<html>
<head>
<title></title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
<script language="JavaScript" type="text/JavaScript">
function all_chk(sel_all){
	var ckform = document.ifrm_Form;
  	var cbox   = ckform.checkbox;
  	if(cbox){
	    if(cbox.length){
	    	for(i=0;i<cbox.length;i++){
	    		ckform.checkbox[i].checked=sel_all;
	    	}
	    }else{
	    	ckform.checkbox.checked = sel_all;
	    }   
	}
}
function updateTemplate(templateid){
	var cnt_height=parent.parent.parent.document.body.clientHeight;
	var cnt_width=parent.parent.parent.document.body.clientWidth;
	template_width=1000;
	parent.content.showDiv(2,"修改模板",template_width,cnt_height-100,"/admin/template/update_template.jsp?templateid="+templateid+"&t=" + new Date().getTime());
}

function viewtemplate(template_id,template_width,template_height,e){
		var cnt_height=parent.parent.parent.document.body.clientHeight;
			var cnt_width=parent.parent.parent.document.body.clientWidth;
			var pjh=(cnt_height/template_height).toFixed(1)-0.4;


showDiv(1,"模板预览",template_width*pjh,template_height*pjh,"/admin/template/viewTemplate.jsp?templateid="+template_id+"&scale="+pjh+"&t=" + new Date().getTime());
}
function showDiv(num,title,fwidth,fheight,url){
			//alert("templatelist.jsp");
			parent.parent.parent.document.body.scrollTop = "0px";
			parent.parent.parent.document.getElementById("div_iframe"+num).width=fwidth;
			parent.parent.parent.document.getElementById("div_iframe"+num).height=fheight;
			parent.parent.parent.document.getElementById("divframe"+num).style.left=(parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";;
			parent.parent.parent.document.getElementById("divframe"+num).style.top="2px";
			parent.parent.parent.document.getElementById("div_iframe"+num).src=url;
			parent.parent.parent.document.getElementById("titlename"+num).innerHTML=title;
			parent.parent.parent.document.getElementById("divframe"+num).style.display='block';
			parent.parent.parent.document.getElementById("massage"+num).style.display='block';
			parent.parent.parent.document.getElementById("bgDiv").style.visibility='visible';
		}




function showDivFrame(url,fwidth,fheight,_top){
document.body.scrollTop = "0px";
document.getElementById("div_iframe").width=fwidth;
document.getElementById("div_iframe").height=fheight;
document.getElementById("divframe").style.left="190px";
document.getElementById("divframe").style.top=_top;
document.getElementById("div_iframe").src=url;
document.getElementById("divframe").style.visibility='visible';
document.getElementById("massage").style.visibility='visible';
}
function closedivframe(){
document.getElementById("div_iframe").src="/loading.jsp";
document.getElementById('divframe').style.visibility="hidden";
}
function gopage(pagenum){
window.location.href="/admin/template/templateList.jsp?pagenum="+pagenum;
}
</script>
<style type="text/css">
#divframe{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2);  visibility:hidden}
#massage{ background:#fff; color:#036; font-size:12px; line-height:150%; visibility:hidden}
</style>
</head>
<body >
<div id="divframe">
<div  id="massage">
<table cellpadding="0" cellspacing="0" >
	<tr><td colspan="2">
	<iframe src="/loading.jsp" scrolling="no" id="div_iframe" name="div_iframe" frameborder="0" ></iframe>
	</td></tr>
</table>
</div>
</div>
<form action="" name="ifrm_Form">
<!-- list -->
  <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="table1">
    <tr>
      <td>
            <c:if test="${empty requestScope.templateList}">
<br/>
<center>暂无模板！</center>
</c:if>
      <c:forEach var="template" items="${templateList}">

	   <table width="100%"  height="50" border="0" cellpadding="0" cellspacing="0"  >
        <tr onmouseover="this.className='TableTrBgF0'" onmouseout="this.className=''">
          <td width="5%" align="center" >
          <c:if test="${(template.template_create_name==sessionScope.lg_user.lg_name ||lg_user.lg_role==1) && template.template_expla!='系统模板'}">
          	<input type="checkbox" name="checkbox" value="${template.template_id }" >
          </c:if>&nbsp;
          </td>             
          <td width="15%"  style="cursor: pointer" align="center" onclick="viewtemplate('${template.template_id }','${template.template_width+10}','${template.template_height+10}',this);" title="预览模板"><strong >${template.template_name }</strong></td>
          <td width="20%" align="center">${template.template_expla }</td>
          <td width="15%" align="center">
          <c:choose>
          	<c:when test="${template.template_background!='' }">

          		<img src="${fn:split(template.template_background,".")[0]}_slightly.jpg" width="100px"  height="50px"/>
          	</c:when>
          	<c:otherwise>无背景图片</c:otherwise>
          </c:choose>
          
          
          </td>
           
          <td width="15%" align="center" >${template.template_create_name }</td>
          <td width="15%" align="center" >${template.create_time }</td>
          <c:if test="${not empty lg_authority}">
					   <c:set var="str" value="${lg_authority}"/>
				   <c:if test="${fn:contains(str,'P')}">
                     <td width="15%" align="center" >
                     	<input type="button" value=" 修改 " onclick="updateTemplate('${template.template_id }');"  ${(template.template_create_name==sessionScope.lg_user.lg_name ||lg_user.lg_role==1)?'':'disabled'} class="button"/>
                     <input type="button" value=" 预览 " onclick="viewtemplate('${template.template_id }','${template.template_width+10}','${template.template_height+10}',this);" class="button"/></td>  
                   </c:if>
          </c:if>
         </tr>
      </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="Line_01"></td>
          </tr>
        </table>
	  	</c:forEach>
      </td>
    </tr>
  </table>
</form>
<c:if test="${pager.totalPage>0}">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" valign="top"align="right">
					总共【<span style="color: blue">${template_sum}</span>】个模板
                  <a href="javascript:;" onclick="gopage(1);return false;"><img src="/images/btn_first.gif" border="0"/></a>&nbsp;
				 <a href="javascript:;" onclick="gopage(${pager.currentPage-1 });return false;"><img src="/images/btn_pre.gif" border="0"/></a>&nbsp; ${pager.currentPage }&nbsp; 
                  <a href="javascript:;" onclick="gopage(${pager.currentPage+1 });return false;"><img src="/images/btn_next.gif" border="0"/></a>&nbsp; 
                  <a href="javascript:;" onclick="gopage(${pager.end });return false;"><img src="/images/btn_end.gif" border="0"/></a>&nbsp;
                  共${pager.totalPage }页 
				</td>
				<td width="100px">&nbsp;</td>
			</tr>
		</table></c:if>
</body>
</html>