<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.MediaDAO"/>
<jsp:directive.page import="com.xct.cms.utils.Pager"/>
<jsp:directive.page import="com.xct.cms.utils.PageDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
		out.println("<div id='Layer1' style='top:85px;'><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><table align='center' style='font-size:12px'> <tr><td><img src='/images/mid_giallo.gif'/>&nbsp;&nbsp;&nbsp;正在加载数据....请稍候！</td></tr></table></div>");
		
		String leftcmd=request.getParameter("leftcmd")==null?"":request.getParameter("leftcmd");
		String zu_id=request.getParameter("zu_id")==null?"":request.getParameter("zu_id");
		String type=request.getParameter("type")==null?"":request.getParameter("type");
		String moduleid=request.getParameter("moduleid");
		String top_order_id=request.getParameter("top_order_id")==null?"":request.getParameter("top_order_id");
		String top_time=request.getParameter("top_time")==null?"":request.getParameter("top_time");
		 
		request.setAttribute("moduleid",moduleid);   
		MediaDAO mediadao = new MediaDAO();
		List list= new ArrayList();
		String str="";
		
		String order_str="";
		if("1".equals(top_order_id)){
			order_str=" order by media_title";
		}else{
			order_str=" order by create_date desc";
		}
		//Mysql
		String time_str = " and  create_date > (select date_sub(now(),interval "+top_time+" day))";
		//SQLServer
		//String time_str=" and  create_date > (select getdate()-"+top_time+")";

		if("TYPE_ZU".equals(leftcmd)){  //点组名称
			str=" and  xct_media.zu_id='"+(zu_id.equals("no")?"":zu_id)+"' "+time_str+"   "+order_str;
		}else if("MEDIA".equals(leftcmd)){  //点系统文件夹
			str=" and media_type ='"+type+"' "+time_str+"   "+order_str;
		}else if("TITLE_ORDER".equals(leftcmd)){  //名称排序
			str="  order by media_title";
		}else{
			str=" and media_type !='TEXT' "+time_str+"   "+order_str;
		}
		//System.out.println(str);
		list=mediadao.getALLMediaDAO(str);
		
		
		if(list!=null&&list.size()>0){
			int pagenum =Integer.parseInt(request.getParameter("pagenum")==null?"1":request.getParameter("pagenum"));
			Pager pager= new Pager(list.size(),pagenum,50); 
			List list3= new PageDAO().getPageList(list, pager.getCurrentPage(), pager.getPageSize());
			request.setAttribute("pager", pager);
			request.setAttribute("medias", list3);
			request.setAttribute("media_num", list3.size());
		}

		request.setAttribute("leftcmd",leftcmd);
		request.setAttribute("zu_id",zu_id);
		request.setAttribute("type",type);
		request.setAttribute("moduleid",moduleid);
		request.setAttribute("top_order_id",top_order_id);
		request.setAttribute("top_time",top_time);
%>

<html>
  <head>
    <title>My JSP 'select_media.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="/css/style.css" type="text/css" />
	<link rel="stylesheet" href="/css/dtree.css" type="text/css" />
	<script language="javascript" src="/js/vcommon.js"></script>
	<script language="JavaScript" src="/js/dtree.js"></script>
	<script language="JavaScript" src="/js/did_common.js"></script>
	<style type="text/css">
	</style>
	<script type="text/javascript">
	function all_chk(sel_all){
		var ckform = document.fm1;
	  	var cbox   = ckform.media_list;
	  	if(cbox){
		    if(cbox.length){
		    	for(i=0;i<cbox.length;i++){
		    		cbox[i].checked=sel_all;
		    		if(sel_all)
		    		document.getElementById("divok_"+i).style.display='block';
		    		else 
		    		document.getElementById("divok_"+i).style.display='none';
		    	}
		    }else{
		    	cbox.checked = sel_all;
		    	if(sel_all)
		    		document.getElementById("divok_0").style.display='block';
		    	else 
		    		document.getElementById("divok_0").style.display='none';
		    }   
		}
	}

	function chkMedia(){
		var ckform = document.fm1;
		var cbox   = ckform.media_list;
		if(cbox){
			var num=0;
			for(i=0;i<cbox.length;i++){
				if(cbox[i].checked == true) num++; 
			}
			if(cbox.checked == true)num++;
			
			return num;
		}else{
			return 0;
		}
	}


function getMedias(){

	var ckform = document.fm1;
	var cbox   = ckform.media_list;
	var mediaids="";
	for(i=0;i<cbox.length;i++){
		if(cbox[i].checked == true) {
			mediaids +=","+cbox[i].value;
		}
	}
	if(cbox.checked){
		mediaids+=","+cbox.value;
	}
	return mediaids;
}

function addmedia(){
	if(chkMedia()<1){
		alert("请至少选择一个媒体");
		return;
	}
	medias=getMedias();
	//alert(parent.parent.div_iframe2.location.href);
	parent.parent.div_iframe2.addmediaframe.location.href="/admin/program/addmediaList.jsp?opp=2&moduleid=${moduleid}&mediaids="+medias;
	parent.parent.closedivframe3();

}
function checkedia(obj){
	var check_box_obj=document.getElementById("media_list_"+obj);
	if(check_box_obj){
		if(check_box_obj.checked){
			check_box_obj.checked=false;
			document.getElementById("divok_"+obj).style.display='none';
		}else{
		check_box_obj.checked=true;
		document.getElementById("divok_"+obj).style.display='block';
		}
	}
}
function gopage(page_num){
	window.location.href="/admin/program/show_media.jsp?moduleid=${moduleid}&zu_id=${zu_id}&leftcmd=${leftcmd}&type=${type}&top_order_id=${top_order_id}&top_time=${top_time}&pagenum="+page_num;
}

	</script>
	</head>
	
  
  <body> 
  <form name='fm1'>
   <table width="100%" height="100%" border="0">
  
   <c:if test="${empty medias}">
   		<tr>
   		<td valign="top" >暂无媒体文件！</td>
   	</tr>
   </c:if>
   	<tr>
   		<td valign="top" width="100%">
   			<table cellpadding="5" cellspacing="5" border="0"  >
   			<c:set var="tr_num" value="0"></c:set>
   				 <c:forEach var="media" items="${medias}">
   				 	<c:if test="${tr_num==0}">
   				 	<tr></c:if>
   				 	<c:if test="${tr_num!=0&&(tr_num%5)==0}"></tr><tr></c:if>
   				 	<td>
   				 	<div style="border: 1px solid #cccccc" onclick="checkedia('${tr_num}')" title="${media.media_title}"  onmouseover="this.style.borderColor='blue'" id="m_div_${tr_num}"  onmouseout="this.style.borderColor='#cccccc'">
	   				 <div style="position: absolute;margin-left: 5px;display: none;" id="divok_${tr_num}"><img src="/images/checkok.png"/></div>
	   				 	<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
	   				 		<tr><td>
		   				 		<input type="checkbox" style="display: none;"   name="media_list" id="media_list_${tr_num}" value="${media.media_id}_${media.media_type}" />
		   				 		<div style="position: absolute; padding-left:0px; border: 0px solid blue;width: 18px;height: 18px">
		   				 			<img src="/images/dtreeimg/${media.media_type}.gif" height="18" width="18px"/>
		   				 		</div>
		   				 		<img src="${media.slightly_img_path }${media.slightly_img_name }" border="0" alt="${media.media_title}" width="102" height="80" />
	   				 		</td></tr>
	   				 		<tr><td height="25px;">
	   				 			<div style="padding-left: 0px;width: 100px;white-space:nowrap;overflow:hidden;">${media.media_title}</div>
	   				 		</td></tr>
	   				 	</table>
   				 	</div>
   				 	</td>
   				 <c:set var="tr_num" value="${tr_num+1}"></c:set>
   				 </c:forEach>
   			</tr>
   			</table>
   		</td>
   	</tr>
   	<c:if test="${pager.totalPage>1}">
   <tr><td>
   	 <table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" valign="top"align="right">
                  <a href="javascript:;" onclick="gopage(1);return false;"><img src="/images/btn_first.gif" border="0"/></a>&nbsp;
				  <a href="javascript:;" onclick="gopage('${pager.currentPage-1 }');return false;"><img src="/images/btn_pre.gif" border="0"/></a>&nbsp; ${pager.currentPage }&nbsp; 
                  <a href="javascript:;" onclick="gopage('${pager.currentPage+1 }');return false;"><img src="/images/btn_next.gif" border="0"/></a>&nbsp; 
                  <a href="javascript:;" onclick="gopage('${pager.end }');return false;"><img src="/images/btn_end.gif" border="0"/></a>&nbsp;
                  共${pager.totalPage }页  
				</td>
			</tr>
		</table>
   </td></tr></c:if>
   </table>
   </form>
  
  </body>
</html>
<script type="text/javascript">
	document.getElementById("Layer1").style.display="none";
</script>