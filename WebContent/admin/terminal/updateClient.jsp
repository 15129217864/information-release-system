<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String cntmac=request.getParameter("cntmac")==null?"":request.getParameter("cntmac");
String updateok=request.getParameter("updateok")==null?"0":request.getParameter("updateok");
Terminal terminal=new TerminalDAO().getTerminalBystr(" and  cnt_mac='"+cntmac+"'");
request.setAttribute("terminal",terminal);

//String startdatetime=terminal.getCnt_kjtime().indexOf(":")==-1?terminal.getCnt_kjtime()+":0":terminal.getCnt_kjtime();
//String enddatetime=terminal.getCnt_gjtime().indexOf(":")==-1?terminal.getCnt_gjtime()+":0":terminal.getCnt_gjtime();
//String downatetime=terminal.getCnt_downtime().indexOf(":")==-1?terminal.getCnt_downtime()+":0":terminal.getCnt_downtime();



//request.setAttribute("cnt_kjtime",startdatetime.split(":")[0]);
//request.setAttribute("sleepstartmunite",startdatetime.split(":")[1]);


//request.setAttribute("cnt_gjtime",enddatetime.split(":")[0]);
//request.setAttribute("sleependmunite",enddatetime.split(":")[1]);

//request.setAttribute("cnt_downtime",downatetime.split(":")[0]);
//request.setAttribute("cnt_downtimeminute",downatetime.split(":")[1]);

request.setAttribute("cntmac",cntmac);
request.setAttribute("updateok",updateok);
 %>
<html>
  <head>
    <title>updateClient.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script language="javascript" src="/js/vcommon.js"></script>
	<link rel="stylesheet" href="/css/style.css" type="text/css" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<style type="text/css">
	td{font-size: 12px;}
</style>
<script type="text/javascript">

var SubIFLayer;
var reclick=0;
function popupCategoryWindow(e) {
	if(reclick==0){
		reclick=1;
		var tsrc = "/rq/searchTerminal?cmd=AUDIT";
		makeSubLayer('组树状视图',200,200,e);
		//alert("popupCategoryWindow()------------1");
		bindSrc('treeviewPopup',tsrc);
		//alert("popupCategoryWindow()------------2");
	}
}
function bindSrc(objname,source){
	var obj = document.getElementById(objname);
	obj.src = source;
}

function closeSubIFLayer(){
	//alert("closeSubIFLayer");
	//SubIFLayer.removeNode(true); //只有IE才有此方法
	SubIFLayer.parentNode.removeChild(SubIFLayer);
	reclick=0;
}
function makeSubLayer(title,w,h,e){ 
	SubIFLayer=document.createElement("div"); //
	SubIFLayer.id = 'divTreeview';
	SubIFLayer.style.cssText="width:"+w+";height:"+h+";z-index:999;position:absolute;top:-4000;left:-4000;border:1px solid #2A5D90;padding:0 0 0 0;color:white;background-color:F5FBFF"; 

	SubIFLayer.innerHTML=""
		+"<table width='100%' border='0'><tr><td width=93% align=center><b>"+title+"</b></td><td align=right width=5%><a href='javascript:;' onclick='closeSubIFLayer()'><img src='/images/icon_popupclose.gif' width='16' height='16' border='0'></td></tr></table>"
		+"";
		
	with(SubIFLayer.appendChild(document.createElement("iframe"))){ 
	
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
	};
} 

function selectCategory(group_id,dpath){
	var treeviewobj = document.getElementById('divTreeview');
	//treeviewobj.removeNode(true); //只有IE才有此方法
	SubIFLayer.parentNode.removeChild(SubIFLayer);
	document.fm1.terminal_zu.value    = group_id;
	document.fm1.zu_name.value       = dpath;
	document.fm1.zu_name.title       = dpath;
	reclick=0;
}

function updateSubmit(cntmac){
	var cnt_name=fm1.cnt_name.value.replace(/\s/g,"");
	if(cnt_name==""){
		alert("请输入终端显示名称！");
		return
    }
	document.fm1.action="/admin/terminal/updateClientDO.jsp?cntmac="+cntmac;
	document.fm1.submit();
}
</script>
  </head>
  <body>

  
  <form action="" name="fm1" method="post">
    <input type="hidden" name="iptmp" value="${terminal.cnt_ip }"/>
    <input type="hidden" name="link_status" value="${terminal.cnt_islink}"/>
    <table  align="center" border="0" cellpadding="0" cellspacing="0" >
		    	<tr>
		    		<td align="right">终端显示名称：</td>
		    		<td  height="40px" ><input type="text" class="button" size="30" name="cnt_name" maxlength="20" value="${terminal.cnt_name}"/>
		    		<span style="color: red">&nbsp;*</span>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">所属组：</td>
		    		<td height="40px" >
		    		<input type="hidden" name="terminal_zu" value="${terminal.zu_id}"/>
		    		<input type="text" size="30" name="zu_name" class="button"  value="${terminal.zu_name}" title="${terminal.zu_name}"/>
		    		<span style="color: red">&nbsp;*</span>
		    		<a href="javascript:;" onClick="javascript:popupCategoryWindow(event);"><img src="/images/btn_search2.gif" border="0" /></a></td>
		    	</tr>
		    	<tr>
		    		<td align="right">终端IP地址：</td>
		    		<td height="40px" >${terminal.cnt_ip }</td>
		    	</tr>
		    	<tr>
		    		<td align="right">终端MAC地址：</td>
		    		<td height="40px" >
		    		<input type="hidden" name="terminal_mac" class="button"  value="${terminal.cnt_mac }"/>${terminal.cnt_mac }</td>
		    	</tr>
		    	<!-- 
		    	 <c:if test="${terminal.cnt_islink==1||terminal.cnt_islink==2}">
				    	<tr>
				    		<td width="25%" align="right">播放开始时间：</td>
				    		<td height="40px" >
									<select style="width: 40px" name="cnt_kjtime" class="button" >
									    <c:forEach begin="1" end="23" var="b" step="1">
											  <option value="${b }" ${b==cnt_kjtime?'selected':''}>${b }</option>
									    </c:forEach>
									</select>点
									<select name="sleepstartmunite" style="display: none;">
										<c:forEach begin="0" end="59" var="btmp" step="1">
												<option value="${btmp }" ${btmp==sleepstartmunite?'selected':''}>${btmp}</option>
									   </c:forEach>
									</select>&nbsp;
									
							</td>
						</tr>
						<tr>
				    		<td width="10%" align="right">播放结束时间：</td>
				    		<td height="40px" >
				    		    <select style="width: 40px" class="button"  name="cnt_gjtime">
									 <c:forEach begin="1" end="23" var="b" step="1">
										 <option value="${b }" ${b==cnt_gjtime?'selected':''}>${b }</option>
								    </c:forEach>
								</select>点
								<select name="sleependmunite" style="display: none;">
									<c:forEach begin="0" end="59" var="btmp" step="1">
										<option value="${btmp }" ${btmp==sleependmunite?'selected':''}>${btmp}</option>
								   </c:forEach>
								</select> &nbsp;
							</td>
				    	</tr>
				    	
				    	<tr>
				    	  	<td width="25%" align="right">节目更新时间：</td>
				    		<td height="40px" > 
				    		   <select style="width: 40px" class="button"  name="cnt_downtime">
								   <c:forEach begin="1" end="23" var="b" step="1">
										  <option value="${b }" ${b==cnt_downtime?'selected':''}>${b }</option>
								    </c:forEach>
								</select>点
								<select name="cnt_downtimeminute" style="display: none;">
									<c:forEach begin="0" end="59" var="btmp" step="1">
										<option value="${btmp }" ${btmp==cnt_downtimeminute?'selected':''}>${btmp}</option>
								   </c:forEach>
								</select> &nbsp;
							</td>
				    	</tr>
		    	</c:if>
		    	 -->
		    	<tr>
		    		<td align="right">终端描述：</td>
		    		<td height="40px" ><input type="text" size="47"  class="button"  maxlength="200" name="cnt_miaoshu" value="${terminal.cnt_miaoshu}"/></td>
		    	</tr>
		    	<tr>
			    	<td align="center" height="50px" colspan="2">
			    		<input type="button" onclick="updateSubmit('${cntmac}');" class="button1"  value= " 修改终端 " />
			    		&nbsp;&nbsp;&nbsp;
			    		<input type="button" value= "  取 消  " class="button1" onclick="parent.closedivframe(1);"/>
			    	</td>
		    	</tr>
        </table>
    </form>
  </body>
 <c:if test="${update_type=='ok'}">
 <script type="text/javascript">
 parent.closedivframe(1);
 	alert("修改终端信息成功！");
 	parent.homeframe.content.deviceContent.list_ifrm.current_page();
	
 </script>
 </c:if>
</html>
 