<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ProgramAppDAO"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.xct.cms.utils.Pager"/>
<jsp:directive.page import="com.xct.cms.utils.PageDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String left_menu=request.getParameter("left_menu");
String str="";
int app_status=0;
String show_str="待审核";
String color_str="maroon";
if("AUDITSEND".equals(left_menu)){
app_status=3;
show_str="待发送";
color_str="maroon";
}else if("NOTAUDIT".equals(left_menu)){
app_status=2;
show_str="审核未通过";
color_str="red";
}else if("ISSEND".equals(left_menu)){
app_status=1;
show_str="已发送";
color_str="green";
str+=" and program_play_terminal=program_sendok_terminal ";
}else if("SENDERROR".equals(left_menu)){
app_status=1;
show_str="发送失败";
color_str="red";
str+=" and program_play_terminal<>program_sendok_terminal ";
}
request.setAttribute("left_menu", left_menu);
request.setAttribute("color_str", color_str);
request.setAttribute("show_str", show_str);
ProgramAppDAO programdao= new ProgramAppDAO();
Users user = (Users) request.getSession().getAttribute("lg_user");

if(user!=null){
	if("2".equals(user.getLg_role())){  ///审核员
		TerminalDAO terminaldao = new TerminalDAO();
	
		//Mysql ,LOCATE 函数 判断 第一次出现的位置
		String allzuid=terminaldao.getAllZuID("where zu_type=2 and (LOCATE ('"+user.getLg_name()+"||',zu_username)>0) ");
		str+=" and (LOCATE (CAST(program_treeid AS varchar(50)),'"+allzuid+"')>0) ";
		
		//SQLServer,PATINDEX 函数 判断 第一次出现的位置
		//String allzuid=terminaldao.getAllZuID("where zu_type=2 and (PATINDEX ('%"+user.getLg_name()+"||%',zu_username)>0) ");
		//str+=" and (CharIndex (CAST(program_treeid AS varchar(50)),'"+allzuid+"')>0) ";
	
	}else if("3".equals(user.getLg_role())){  ///一般用户
		str+=" and program_app_userid='"+user.getLg_name()+"'";
	}

}




	List list= programdao.getGroupByBatch( str+" and  program_app_status= "+app_status);
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
function onCreateProgram(){

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
function sendProgram(program_name,program_file){
parent.listtop.location.href="/admin/program/programtop2.jsp";
window.location.href="/admin/program/opprojecttype.jsp?program_name="+program_name+"&program_file="+program_file;
}
function auditSent(batch){
showDiv1(2,"发送节目",900,500,"/admin/program/auditSendProgram.jsp?batch="+batch+"&oppp=1");
}
function showterminal(program_id){
showDiv1(1,"查看播放终端",400,100,"/admin/program/showTerminal.jsp?program_id="+program_id);
}
function gopage(pagenum){
window.location.href="/admin/program/auditprogramList.jsp?pagenum="+pagenum;
}
function viewProgram(jmtitle,jmurl,templateid,_width,_height){
var cnt_height=parent.parent.parent.parent.document.body.clientHeight;
			var cnt_width=parent.parent.parent.parent.document.body.clientWidth;
			var pjh=(cnt_height/_height).toFixed(1)-0.1;
		   showDiv1(1,"节目预览："+jmtitle,_width*pjh,_height*pjh,"/admin/program/viewProgram.jsp?jmurl="+jmurl+"&templateid="+templateid+"&scale="+pjh);
		

	//showDiv1("节目预览："+jmtitle,"720","540","/admin/program/viewProgram.jsp?jmurl="+jmurl+"&templateid="+templateid+"&scale=1.5");
}

function showDiv1(num,title,fwidth,fheight,url){
	parent.parent.parent.parent.document.body.scrollTop = "0px";
			parent.parent.parent.parent.document.getElementById("div_iframe"+num).width=fwidth;
			parent.parent.parent.parent.document.getElementById("div_iframe"+num).height=fheight;
			parent.parent.parent.parent.document.getElementById("divframe"+num).style.left=(parent.parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";
			parent.parent.parent.parent.document.getElementById("divframe"+num).style.top=(parent.parent.parent.parent.document.body.clientHeight - fheight) / 3 + "px";
			parent.parent.parent.parent.document.getElementById("div_iframe"+num).src=url;
			parent.parent.parent.parent.document.getElementById("titlename"+num).innerHTML=title;
			parent.parent.parent.parent.document.getElementById("divframe"+num).style.display='block';
			parent.parent.parent.parent.document.getElementById("massage"+num).style.display='block';
			parent.parent.parent.parent.document.getElementById("bgDiv").style.visibility='visible';
}
function showDiv(title,fwidth,fheight,top,url){
			document.getElementById("div_iframe").width=fwidth;
			document.getElementById("div_iframe").height=fheight;
			document.getElementById("divframe").style.left=100;
			document.getElementById("divframe").style.top=top+20;
			document.getElementById("div_iframe").src=url;
			document.getElementById("titlename").innerHTML=title;
			document.getElementById("divframe").style.visibility='visible';
			document.getElementById("massage").style.visibility='visible';
}
function getProgramList(e,no,batch){
var   x   =   e.offsetLeft,   y   =   e.offsetTop;   
    while(e=e.offsetParent) 
    { 
       x   +=   e.offsetLeft;   
       y   +=   e.offsetTop;
    } 



showDiv("批次号为【"+no+"-"+batch+"】的节目列表",700,100,y,"/admin/program/showProgramListByBatch.jsp?batch="+batch);
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
.a_link:hover{color: blue; text-decoration: underline;}
</style>

</head>
<body >
<form action="" name="ifrm_Form">
<!-- list -->
  <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="table1">
    <tr>
      <td>
      <c:if test="${empty requestScope.programList}">
<br/>
<center>暂无${show_str}节目！</center>
</c:if>
      <c:forEach var="programcla" items="${requestScope.programList}">

	   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0" >
        <tr  class="TableTrBg06_" onmouseover="this.className='TableTrBg06'" onmouseout="this.className='TableTrBg06_'" height="35px">
          <td width="5%"><input type="checkbox" name="checkbox" value="${programcla.batch }" ></td>             
          <td width="20%"><a href="javascript:;" title="查看节目列表" onclick="getProgramList(this,'${fn:replace(fn:replace(fn:replace(programcla.program_app_time, "-", ""),":","")," ","")}','${programcla.batch }');return false" class="a_link">${fn:replace(fn:replace(fn:replace(programcla.program_app_time, "-", ""),":","")," ","")}-${programcla.batch}</a></td>
          <td width="15%">${left_menu=='ISSEND'?programcla.send_user:programcla.program_app_name }</td>
          <td width="20%">${left_menu=='ISSEND'?programcla.send_time:programcla.program_app_time }</td>
          <td width="10%"><span style='color:${color_str}'>${show_str}</span></td>
          <td width="15%"><input type="button" class="button" onclick="showterminal('${programcla.batch }')" value="查看播放终端"/></td>
          <td width="15%"><input type="button" class="button" ${(left_menu=='NOTAUDIT' ||left_menu=='ISSEND')?'disabled':'' } ${(lg_user.lg_role==3 &&left_menu=='AUDIT')?'disabled':'' }  onclick="auditSent('${programcla.batch }')" value=" 发 送 "/></td>    
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