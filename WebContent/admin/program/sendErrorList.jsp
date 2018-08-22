<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.utils.Pager"/>
<jsp:directive.page import="com.xct.cms.utils.PageDAO"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramAppDAO"/>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.domin.ProgramApp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	
	ProgramAppDAO programdao= new ProgramAppDAO();
	Users usere=(Users)request.getSession().getAttribute("lg_user");
	String str=" program_app_userid='"+usere.getLg_name()+"' and ";
	if("1".equals(usere.getLg_role())){
		str="";
	}
	String nowtime=UtilDAO.getNowtime("yyyy-MM-dd");
	
	//Mysql
	List<ProgramApp> list= programdao.getALLProgramAppByStr("where "+str+" program_app_time>'"+nowtime+"' and program_app_status=1  and LENGTH(program_sendok_terminal)<>LENGTH(program_play_terminal) order by id desc");
	//SQLServer
	//List<ProgramApp> list= programdao.getALLProgramAppByStr("where "+str+" program_app_time>'"+nowtime+"' and program_app_status=1  and datalength(program_sendok_terminal)<>datalength(program_play_terminal) order by id desc");
	
	if(list!=null&&list.size()>0){
		int pagenum =Integer.parseInt(request.getParameter("pagenum")==null?"1":request.getParameter("pagenum"));
		Pager pager= new Pager(list.size(),pagenum,20); 
		List list3= new PageDAO().getPageList(list, pager.getCurrentPage(), pager.getPageSize());
		request.setAttribute("pager", pager);
		request.setAttribute("programList", list3);
	}

 %>
<html>
<head>
<title></title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
					
<script language="JavaScript" type="text/JavaScript">
function showSendErrorTerminal(program_id){
showDiv1("查看播放终端",300,300,"/admin/program/showSendErrorTerminal.jsp?program_id="+program_id);

}
function SetWinHeight(obj)
{ 
 var win=obj; 
 if (document.getElementById) 
 { 
  if (win && !window.opera) 
  { 
	if (win.contentDocument && win.contentDocument.body.offsetHeight){  
	win.height = win.contentDocument.body.offsetHeight;  
	}else if(win.Document && win.Document.body.scrollHeight){ 
    win.height = win.Document.body.scrollHeight;
	}
  } 
 } 
}
function closedivframe(){
document.getElementById("div_iframe").src="/loading.jsp";
document.getElementById('divframe').style.visibility="hidden";
}
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
function reSendprogram(program_id){
parent.listtop.location.href="/admin/program/programtop2.jsp";
window.location.href="/admin/program/reSendErrorProgram.jsp?program_id="+program_id+"&oppp=2";
}
function gopage(pagenum){
window.location.href="/admin/program/sendprogramList.jsp?pagenum="+pagenum;
}

function viewProgram(jmtitle,jmurl,templateid,_width,_height){
var cnt_height=parent.parent.parent.parent.document.body.clientHeight;
			var cnt_width=parent.parent.parent.parent.document.body.clientWidth;
			var pjh=(cnt_height/_height).toFixed(1)-0.1;
		   showDiv1("节目预览："+jmtitle,_width*pjh,_height*pjh,"/admin/program/viewProgram.jsp?jmurl="+jmurl+"&templateid="+templateid+"&scale="+pjh);
		

	//showDiv1("节目预览："+jmtitle,"720","540","/admin/program/viewProgram.jsp?jmurl="+jmurl+"&templateid="+templateid+"&scale=1.5");
}

function showDiv1(title,fwidth,fheight,url){
	parent.parent.parent.parent.document.body.scrollTop = "0px";
			parent.parent.parent.parent.document.getElementById("div_iframe1").width=fwidth;
			parent.parent.parent.parent.document.getElementById("div_iframe1").height=fheight;
			parent.parent.parent.parent.document.getElementById("divframe1").style.left=(parent.parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";
			parent.parent.parent.parent.document.getElementById("divframe1").style.top=(parent.parent.parent.parent.document.body.clientHeight - fheight) / 3 + "px";
			parent.parent.parent.parent.document.getElementById("div_iframe1").src=url;
			parent.parent.parent.parent.document.getElementById("titlename1").innerHTML=title;
			parent.parent.parent.parent.document.getElementById("divframe1").style.display='block';
			parent.parent.parent.parent.document.getElementById("massage1").style.display='block';
			parent.parent.parent.parent.document.getElementById("bgDiv").style.visibility='visible';
			parent.parent.parent.parent.document.getElementById("bgDiv").style.visibility='visible';
}
</script>
<style type="text/css">
#divframe {
	position: absolute;
	z-index: 999;
	filter: dropshadow(color = #666666, offx = 3, offy = 3, positive = 2);
	visibility: hidden
}

#mask {
	position: absolute;
	top: 0;
	left: 0;
	width: expression(body . scrollWidth);
	height: expression(body . scrollHeight);
	background: #000000;
	filter: ALPHA(opacity = 60);
	z-index: 1;
	visibility: hidden
}

#massage {
	border: #6699cc solid;
	border-width: 1 1 1 1;
	background: #fff;
	color: #036;
	font-size: 12px;
	line-height: 150%;
	visibility: hidden
}

.header {
	background: url(/images/device/btn_background.gif);
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	padding: 3 5 0 5;
	color: #fff;
			cursor: move;
}
</style>

</head>
<body >
<!-- list -->
  <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="table1">
    <tr>
      <td>
      <c:if test="${empty requestScope.programList}">
<br/>
<center>暂无节目！</center>
</c:if>
      <c:forEach var="programcla" items="${requestScope.programList}">
	   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0" >
        <tr  class="TableTrBg06_" onmouseover=this.className="TableTrBg06" onmouseout=this.className="TableTrBg06_" height="35px">     
          <td width="17%"><a href="javascript:;" title="预览节目" onclick="viewProgram('${programcla.program_name }','${programcla.program_jmurl }','${programcla.templateid }',600,450);"><strong>${programcla.program_name }</strong></a></td>
          <td width="12%">${programcla.program_jmurl }</td>
          <td width="13%">${programcla.program_playdate } ${programcla.program_playtime } </td>
          <td width="13%">${programcla.program_enddate } ${programcla.program_endtime }</td>
          <td width="7%">${programcla.program_play_typeZh }</td>  
          <td width="8%">${programcla.program_playlong }分钟</td>  
           <td width="8%">${programcla.program_app_userid }</td>  
          <td width="12%"><input type="button" class="button" onclick="showSendErrorTerminal('${programcla.id }')" value="发送失败终端"/></td>
          <td width="10%"><input type="button" class="button" onclick="reSendprogram('${programcla.program_id }')" value="重新发送"/></td>
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
<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" valign="top"align="right">
                  <a href="javascript:;" onclick="gopage(1);return false;"><img src="/images/btn_first.gif" border="0"/></a>&nbsp;
				  <a href="javascript:;" onclick="gopage(${pager.currentPage-1 });return false;"><img src="/images/btn_pre.gif" border="0"/></a>&nbsp; ${pager.currentPage }&nbsp; 
                  <a href="javascript:;" onclick="gopage(${pager.currentPage+1 });return false;"><img src="/images/btn_next.gif" border="0"/></a>&nbsp; 
                  <a href="javascript:;" onclick="gopage(${pager.end });return false;"><img src="/images/btn_end.gif" border="0"/></a>&nbsp;
                  共${pager.totalPage }页 
				</td>
			</tr>
		</table>
<div id="divframe">
			<div id="massage">
				<table cellpadding="0" cellspacing="0">
					<tr height="30px;" class=header  onmousedown=MDown(divframe)>
						<td align="left" style="font-weight: bold">
							<span id="titlename"></span>
						</td>
						<td height="5px" align="right">
							<a href="javaScript:void(0);" style="color: #000000"
								onclick="closedivframe();">关闭</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<iframe src="/loading.jsp" scrolling="no" id="div_iframe"
								name="div_iframe" frameborder="0"
								onload="Javascript:SetWinHeight(this)" width="300"></iframe>
							
						</td>
					</tr>
				</table>
			</div>
		</div>
</body>
</html>