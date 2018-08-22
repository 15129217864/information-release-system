<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <title>My JSP 'audit.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
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
		bindSrc('treeviewPopup',tsrc);
	}
}
function bindSrc(objname,source){

	var obj = document.getElementById(objname);
	obj.src = source;
}
function closeSubIFLayer(){
	//SubIFLayer.removeNode(true);//只有IE才有此方法
	SubIFLayer.parentNode.removeChild(SubIFLayer);
	reclick=0;
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
	   height=h; 
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
function selectCategory(group_id,dpath){
	var treeviewobj = document.getElementById('divTreeview');
	//treeviewobj.removeNode(true);//只有IE才有此方法
	SubIFLayer.parentNode.removeChild(SubIFLayer);
	document.fm1.terminal_zu.value    = group_id;
	document.fm1.zu_name.value       = dpath;
	reclick=0;
}
function auditSubmit(){
	var cnt_name=fm1.cnt_name.value.replace(/\s/g,"");
	var cnt_zu_name=fm1.zu_name.value.replace(/\s/g,"");
	if(cnt_name==""){
		alert("请输入终端显示名称！");
		return;
	}else if(cnt_zu_name==""){
		alert("请选择终端所属组！");
		return;
	}
	document.fm1.action="/rq/auditCnt";
	document.fm1.submit();
}
</script>
  </head>
  <body>

  <form action="" name="fm1" id="fm1" method="post">
    <table width="450px" height="300px" border="0" cellpadding="0" cellspacing="0" >
    	<tr>
    		<td align="right">终端显示名称：</td>
    		<td colspan="5"><input type="text" class="button"  size="30" name="cnt_name" maxlength="20"/><span style="color: red">&nbsp;*</span></td>
    	</tr>
    	<tr>
    		<td align="right">所属组：</td>
    		<td colspan="5">
    		<input type="hidden" name="terminal_zu" value="1"/>
    		<input type="text" size="30" name="zu_name" readonly="readonly" class="button"  value=""/><span style="color: red">&nbsp;*&nbsp;</span>
    		<a href="javascript:;" onClick="javascript:popupCategoryWindow(event);"><img src="/images/btn_search2.gif" border="0" /></a></td>
    	</tr>
    	<tr>
    		<td align="right">终端IP地址：</td>
    		<td colspan="5">${terminal.cnt_ip }</td>
    	</tr>
    	<tr>
    		<td align="right">终端MAC地址：</td>
    		<td colspan="5">
    		<input type="hidden" name="terminal_mac" class="button"  value="${terminal.cnt_mac }"/>${terminal.cnt_mac }</td>
    	</tr>
    	<tr>
    		<td width="25%" align="right">开始时间：</td>
    		<td width="15%" >
				<select style="width: 60px" name="cnt_kjtime" class="button" >
					<option value="1">01:00</option>
					<option value="2">02:00</option>
					<option value="3">03:00</option>
					<option value="4">04:00</option>
					<option value="5">05:00</option>
					<option value="6">06:00</option>
					<option value="7"  selected="selected">07:00</option>
					<option value="8">08:00</option>
					<option value="9">09:00</option>
					<option value="10">10:00</option>
					<option value="11">11:00</option>
					<option value="12">12:00</option>
					<option value="13">13:00</option>
					<option value="14">14:00</option>
					<option value="15">15:00</option>
					<option value="16">16:00</option>
					<option value="17">17:00</option>
					<option value="18">18:00</option>
					<option value="19">19:00</option>
					<option value="20">20:00</option>
					<option value="21">21:00</option>
					<option value="22">22:00</option>
					<option value="23">23:00</option>
				</select>
	
			</td>
    		<td width="15%" align="right">关机时间：</td>
    		<td width="15%" ><select style="width: 60px" class="button"  name="cnt_gjtime">
					<option value="1">01:00</option>
					<option value="2">02:00</option>
					<option value="3">03:00</option>
					<option value="4">04:00</option>
					<option value="5">05:00</option>
					<option value="6">06:00</option>
					<option value="7">07:00</option>
					<option value="8">08:00</option>
					<option value="9">09:00</option>
					<option value="10">10:00</option>
					<option value="11">11:00</option>
					<option value="12">12:00</option>
					<option value="13">13:00</option>
					<option value="14">14:00</option>
					<option value="15">15:00</option>
					<option value="16">16:00</option>
					<option value="17">17:00</option>
					<option value="18">18:00</option>
					<option value="19">19:00</option>
					<option value="20"  selected="selected">20:00</option>
					<option value="21">21:00</option>
					<option value="22">22:00</option>
					<option value="23">23:00</option>
				</select></td>
    		<td width="15%" align="right">更新时间：</td>
    		<td width="15%" ><select style="width: 60px" class="button"  name="cnt_downtime">
					<option  selected="selected" value="1">01:00</option>
					<option value="2">02:00</option>
					<option value="3">03:00</option>
					<option value="4">04:00</option>
					<option value="5">05:00</option>
					<option value="6">06:00</option>
					<option value="7">07:00</option>
					<option value="8">08:00</option>
					<option value="9">09:00</option>
					<option value="10">10:00</option>
					<option value="11">11:00</option>
					<option value="12">12:00</option>
					<option value="13">13:00</option>
					<option value="14">14:00</option>
					<option value="15">15:00</option>
					<option value="16">16:00</option>
					<option value="17">17:00</option>
					<option value="18">18:00</option>
					<option value="19">19:00</option>
					<option value="20">20:00</option>
					<option value="21">21:00</option>
					<option value="22">22:00</option>
					<option value="23">23:00</option>
				</select></td>
    	</tr>
    	<tr>
    		<td align="right">终端描述：</td>
    		<td colspan="5"><input type="text" size="47"  class="button"  maxlength="200" name="cnt_miaoshu"/></td>
    	</tr>
    	<tr><td colspan="6" align="center" height="50px">
    		<input type="button" onclick="auditSubmit();" class="button1"  value= " 审 核 " />
    		<input type="button" value= " 取 消 " class="button1" onclick="parent.closedivframe(1);"/>
    	</td></tr>
    </table>
    </form>
  </body>
   
</html>
  <c:if test="${auditstatus==1}">
  <script>
    parent.closedivframe(1);
    alert("终端审核成功！");
    parent.content.parentlocationreload();

  	</script>
  </c:if>
 