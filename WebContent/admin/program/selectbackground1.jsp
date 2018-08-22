<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.MediaDAO" />
<jsp:directive.page import="com.xct.cms.utils.Pager"/>
<jsp:directive.page import="com.xct.cms.utils.PageDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String zuid = request.getParameter("zuid") == null ? "1" : request.getParameter("zuid");//
	String meidaType=request.getParameter("meidaType")==null?"NO":request.getParameter("meidaType");
	String type=request.getParameter("sstype")==null?"NO":request.getParameter("sstype");	
	String templateid=request.getParameter("templateid");
	request.setAttribute("sstype", type);
	request.setAttribute("meidaType", type);
	
	request.setAttribute("templateid", templateid);
	
	MediaDAO mediadao = new MediaDAO();
	String str="";
	if(!"IMAGE".equals(zuid)&&!"SOUND".equals(zuid)){
		str=" and xct_zu.zu_id="+ zuid;
	}
	List mediaList = mediadao.getALLMediaDAO(str+"  and media_type='"+meidaType+"'");
			if(mediaList!=null&&mediaList.size()>0){
				int pagenum =Integer.parseInt(request.getParameter("pagenum")==null?"1":request.getParameter("pagenum"));
				Pager pager= new Pager(mediaList.size(),pagenum,12); 
				List list3= new PageDAO().getPageList(mediaList, pager.getCurrentPage(), pager.getPageSize());
				request.setAttribute("pager", pager);
				request.setAttribute("mediaList", list3);
	}
%>
<html>
	<head>
		<title>My JSP 'selectbackground1.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<style type="text/css">
body{background-color:#F5F9FD;
	 
	scrollbar-face-color:#FFFFFF;
	scrollbar-highlight-color:#FFFFFF;
	scrollbar-3dlight-color:#4A6E90;
	scrollbar-shadow-color:#4A6E90;
	scrollbar-darkshadow-color:#FFFFFF;
    scrollbar-arrow-color:#4D6185;
	scrollbar-track-color:#F6F6F6;
	font-size: 12px;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
a{text-decoration:none; color:#333333; font-size: 12px;}
a:hover{text-decoration:underline; color:#333333; font-size: 12px;}
#divframe{ position:absolute; z-index: 999;  visibility:hidden}
#massage{border:#6699cc solid; border-width:1 1 1 1; background:#fff; color:#036; font-size:12px; line-height:150%; visibility:hidden}
</style>
		<script type="text/javascript">
		function checkimg(imgtitle,imgpath){
		
            //alert(imgtitle+"_ii_"+imgpath);
        
			parent.parent.closeGetBackGroundImageDiv(imgtitle,imgpath,'${templateid}');
			parent.parent.removebackimg();
		
		}
		
		function checkmusic(imgtitle,imgpath){
		
           // alert(imgtitle+"__"+imgpath);
          
			parent.parent.addbackgroud(imgtitle,imgpath);
			parent.parent.closeBackGroundDiv();
			
		}

		function showimg(e,imgpath){
			var  y    =    e.offsetTop;   
			     while(e=e.offsetParent) 
			     { 
			        y    +=    e.offsetTop;
			     } 
			     if(y+50>150)y=y-10;
			     else y=10;
			     if(y+50>document.body.clientHeight-150)y=document.body.clientHeight-150;
			     
			document.body.scrollTop = "0px";
			document.getElementById("divframe").style.left="38px";
			document.getElementById("divframe").style.top=y;
			document.getElementById("div_img").src=imgpath;
			document.getElementById("divframe").style.visibility='visible';
			document.getElementById("massage").style.visibility='visible';
		}
		function hiddenimg(){
			document.getElementById('divframe').style.visibility="hidden";
			document.getElementById("massage").style.visibility='hidden';
		}

</script>
	</head>

	<body style="margin: 5px;">
		<input type="hidden" id="checkMediaId" />
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
			<c:if test="${empty mediaList}">
   ÎÞÄÚÈÝ£¡
   </c:if>
			<c:forEach var="media" items="${mediaList}">
				<tr>
					<td height="20px" valign="bottom" style="padding-left: 10px">
					 <c:choose>
					 <c:when test="${meidaType eq 'NO'}">
					 <img src="/images/dtreeimg/${media.media_type}.gif" align="absmiddle" height="18px"
							style="cursor: pointer;"
							 onclick="javascript:checkmusic('${media.media_title}','${media.file_path}${media.file_name}')" title="${media.media_title}"/>&nbsp;<a href="javascript:;"
							onclick="javascript:checkmusic('${ media.media_title}','${media.file_path}${media.file_name}')">${media.media_title}</a>
					    
					 </c:when>
					 <c:otherwise>
					    <img src="/images/dtreeimg/${media.media_type}.gif" align="absmiddle" height="18px"
							style="cursor: pointer;"
							 onclick="javascript:checkimg('${sstype}','${media.file_path}${media.file_name}')" title="${media.media_title}"/>&nbsp;<a href="javascript:;"
							onclick="javascript:checkimg('${ sstype}','${media.file_path}${media.file_name}')">${media.media_title}</a>
					 </c:otherwise>
					 </c:choose>
						
					</td>
				</tr>
			</c:forEach>

		</table>
		<c:if test="${pager.end>1}">
		<table width="80%" height="30px" align="center" border="0" style="font-size: 12px" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" valign="bottom"align="right">
                  <a href="/admin/program/selectbackground1.jsp?zuid=<%=zuid %>&meidaType=<%=meidaType %>&meidaType=${sstype}&pagenum=1"><img src="/images/btn_first.gif" border="0"/></a>
				 <a href="/admin/program/selectbackground1.jsp?zuid=<%=zuid %>&meidaType=<%=meidaType %>&meidaType=${sstype}&pagenum=${pager.currentPage-1 }"><img src="/images/btn_pre.gif" border="0"/></a>&nbsp;${pager.currentPage }
                  <a href="/admin/program/selectbackground1.jsp?zuid=<%=zuid %>&meidaType=<%=meidaType %>&meidaType=${sstype}&pagenum=${pager.currentPage+1 }"><img src="/images/btn_next.gif" border="0"/></a>
                  <a href="/admin/program/selectbackground1.jsp?zuid=<%=zuid %>&meidaType=<%=meidaType %>&meidaType=${sstype}&pagenum=${pager.end }"><img src="/images/btn_end.gif" border="0"/></a>
                  ¹²${pager.totalPage }Ò³ 
				</td>
			</tr>
		</table>
		</c:if>
		<div id="divframe">
			<div id="massage">
				<img src="/images/mid_giallo.gif" id="div_img" width="150" height="100" /></div>
		</div>
	</body>
</html>
