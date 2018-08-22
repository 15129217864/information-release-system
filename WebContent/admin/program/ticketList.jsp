<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.httpclient.SaudiHttpClient"/>
<jsp:directive.page import="com.xct.cms.httpclient.Ticket"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
//Ticket t=new SaudiHttpClient().getAnalyticJson(0,40,"20110930");
//request.setAttribute("ticketlist",t.getResRoot());

request.setAttribute("ticketlist",new ArrayList());
//request.setAttribute("ticket",t);
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
function ledset(){
	showDivFrame("LED显示设置","/admin/program/ledset.jsp?timeStamp=" + new Date().getTime(),"650","300");
}

function showDivFrame(title,url,fwidth,fheight){
			parent.parent.parent.parent.document.body.scrollTop = "0px";
			parent.parent.parent.parent.document.getElementById("div_iframe1").width=fwidth;
			if(fheight!=""){
				parent.parent.parent.parent.document.getElementById("div_iframe1").height=fheight;
			}
			parent.parent.parent.parent.document.getElementById("divframe1").style.left=(parent.parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";;
			parent.parent.parent.parent.document.getElementById("divframe1").style.top=(parent.parent.parent.parent.document.body.clientHeight - fheight) / 3-80+"px";
			parent.parent.parent.parent.document.getElementById("div_iframe1").src=url;
			parent.parent.parent.parent.document.getElementById("titlename1").innerHTML=title;
			parent.parent.parent.parent.document.getElementById("divframe1").style.display='block';
			parent.parent.parent.parent.document.getElementById("massage1").style.display='block';
			parent.parent.parent.parent.document.getElementById("bgDiv").style.visibility='visible';
}
</script>
<style type="text/css">
#divframe{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2);   display: none }
#massage{border:#6699cc solid; border-width:1 1 1 1; background:#fff; color:#036; font-size:12px; line-height:150%;  display: none }
.header{background:url(/images/device/btn_background.gif);  font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff;cursor: move;}
</style>

</head>
<body>
			<form action="" name="ifrm_Form">
			<!-- list -->
			  <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="table1">
			    <tr>
			      <td>
			      <c:if test="${empty requestScope.ticketlist}">
			<br/>
			<center>暂无信息！</center>
			</c:if>
			      <c:forEach var="t" items="${requestScope.ticketlist}">
				   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0" >
			        <tr  class="TableTrBg06_" onmouseover="this.className='TableTrBg06'" onmouseout="this.className='TableTrBg06_'" height="35px">
			          <td width="3%" align="center">
			          	<input type="checkbox" name="checkbox" value="${t.scrNo }" >
			          </td>             
			          <td width="13%">${t.stadNoDesc }</td>
			          <td width="12%">${t.limitName }</td>
			          <td width="16%">${t.scrNoDesc }</td>
			          <td width="12%">${t.enterDate }</td>
			          <td width="11%">${t.setNum }</td> 
			          <td width="11%">${t.ocuNum }</td> 
			          <td width="11%">${t.useNum }</td> 
			          <td width="11%">${t.enableNum }</td>        
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
							<td align="center" valign="top">
							总共【<span style="color: blue">${program_sum}</span>】个节目
			                  <a href="javascript:;" onclick="gopage('${left_menu }','${zu_id }',1);return false;"><img src="/images/btn_first.gif" border="0"/></a>&nbsp;
							  <a href="javascript:;" onclick="gopage('${left_menu }','${zu_id }','${pager.currentPage-1 }');return false;"><img src="/images/btn_pre.gif" border="0"/></a>&nbsp; ${pager.currentPage }&nbsp; 
			                  <a href="javascript:;" onclick="gopage('${left_menu }','${zu_id }','${pager.currentPage+1 }');return false;"><img src="/images/btn_next.gif" border="0"/></a>&nbsp; 
			                  <a href="javascript:;" onclick="gopage('${left_menu }','${zu_id }','${pager.end }');return false;"><img src="/images/btn_end.gif" border="0"/></a>&nbsp;
			                  共${pager.totalPage }页 
							</td>
							<td width="100px">&nbsp;</td>
						</tr>
					</table>
					</c:if>
			<div id="divframe">
			<div  id="massage">
			<table cellpadding="0" cellspacing="0" >
				<tr  height="30px;" class=header  onmousedown=MDown(divframe)><td align="left" style="font-weight: bold"><span id="titlename"></span></td>
					<td height="15px" align="right"><a href="javaScript:void(0);" style="color: #000000" onclick="closedivframe();">关闭</a></td></tr>
				<tr><td colspan="2">
				<iframe src="/loading.jsp" scrolling="no" id="div_iframe"  name="div_iframe" frameborder="0" ></iframe>
				</td></tr>
			</table>
			</div>
			</div>
	<iframe src="/loading.jsp" id="display_iframe" width="0px;" height="0px" style="display: none;"></iframe>
</body>
</html>
<script>
document.getElementById("loadid").style.display="none";
</script>