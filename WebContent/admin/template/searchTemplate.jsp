<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.xct.cms.utils.Pager"/>
<jsp:directive.page import="com.xct.cms.utils.PageDAO"/>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String templatestr=UtilDAO.getGBK(request.getParameter("templatestr"));
TemplateDAO templatedao= new TemplateDAO();
List list=templatedao.getAlltemplate(" where template_name like '%"+templatestr+"%'");

if(list!=null&&list.size()>0){
				int pagenum =Integer.parseInt(request.getParameter("pagenum")==null?"1":request.getParameter("pagenum"));
				Pager pager= new Pager(list.size(),pagenum,10); 
				List list3= new PageDAO().getPageList(list, pager.getCurrentPage(), pager.getPageSize());
				request.setAttribute("pager", pager);
				request.setAttribute("templateList", list3);
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
parent.listtop.location.href="/admin/template/programtop1.jsp";
window.location.href='/admin/template/update_template.jsp?templateid='+templateid;
}

function viewtemplate(template_id,template_width,template_height,e){
var  y    =    e.offsetTop;   
     while(e=e.offsetParent) 
     { 
        y    +=    e.offsetTop;
     } 
     if(parseInt(y)>150)y=parseInt(y)-150;
     else y=10;
     if(parseInt(y)>document.body.clientHeight-400)y=document.body.clientHeight-400;
showDivFrame("/admin/template/viewTemplate.jsp?templateid="+template_id,template_width,template_height,y);
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
function gopage(pagenum,templatestr){
window.location.href="/admin/template/searchTemplate.jsp?pagenum="+pagenum+"&templatestr="+templatestr;
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
<center>未找到关键字“<span  style="color:red"><%=templatestr %></span>”的模板！</center>
</c:if>
	<c:set var="i" value="0"></c:set>
	<c:set var="classnm"></c:set>
      <c:forEach var="template" items="${templateList}">
       <table width="100%"  height="50" border="0" cellpadding="0" cellspacing="0"  >
        <tr onmouseover="this.className='TableTrBgF0'" onmouseout="this.className=''">
          <td width="5%" align="center" >
          <c:if test="${(template.template_create_name==sessionScope.lg_user.lg_name ||lg_user.lg_role==1) && template.template_expla!='系统模板'}">
          	<input type="checkbox" name="checkbox" value="${template.template_id }" >
          </c:if>&nbsp;
          </td>             
          <td width="15%" onmouseover ="viewtemplate('${template.template_id }','${template.template_width+10}','${template.template_height+10}',this);this.style.color='blue'" onmouseout="closedivframe();this.style.color='#000000'" style="cursor: pointer" align="center"><strong >${template.template_name }</strong></td>
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
          <td width="15%" align="center" ><input type="button" value=" 修改模板 " onclick="updateTemplate('${template.template_id }');" class="button"/></td>  
            
         </tr>
      </table>
	  	</c:forEach>				       
      </td>
    </tr>
  </table>
</form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" valign="top"align="right">
                  <a href="javascript:;" onclick="gopage(1,'<%=templatestr %>');return false;"><img src="/images/btn_first.gif" border="0"/></a>&nbsp;
				 <a href="javascript:;" onclick="gopage(${pager.currentPage-1 },'<%=templatestr %>');return false;"><img src="/images/btn_pre.gif" border="0"/></a>&nbsp; ${pager.currentPage }&nbsp; 
                  <a href="javascript:;" onclick="gopage(${pager.currentPage+1 },'<%=templatestr %>');return false;"><img src="/images/btn_next.gif" border="0"/></a>&nbsp; 
                  <a href="javascript:;" onclick="gopage(${pager.end },'<%=templatestr %>');return false;"><img src="/images/btn_end.gif" border="0"/></a>&nbsp;
                  共${pager.totalPage }页 
				</td>
			</tr>
		</table>
</body>
</html>