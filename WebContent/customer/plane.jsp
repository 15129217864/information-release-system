<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>盐城航班时刻表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css"> 
		body{font-size:20px;}
		*{margin: 0px;padding: 0px}
		.selectItemcont{padding:8px;}
		#selectItem{background:#FFF;position:absolute;top:0px;left:center;border:1px solid #000;overflow:hidden;width:540px;z-index:1000;}
		
		.selectItemtit{line-height:30px;height:30px;margin:1px;padding-left:12px;}
		.bgc_ccc{background:#E88E22;}
		.selectItemleft{float:left;margin:0px;padding:0px;font-size:20px;font-weight:bold;color:#fff;}
		.selectItemright{float:right;cursor:pointer;color:#fff;}
		.selectItemcls{clear:both;font-size:0px;height:0px;overflow:hidden;}
		.selectItemhidden{display:none;}
		.selectItemhidden ul li {float: left;width:108px;padding: 8px 0px;display: block;}
.selectItemhidden input{vertical-align: middle;margin-bottom: 4px}
#selectItem ul li {width:170px}
	</style> 
	<script src="/js/jquery1.4.2.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/jquery.bgiframe.js"></script>
    <script type="text/javascript">
      function change(params){
        window.location.href="<%=basePath%>rq/hotel?op=plane&inorout="+params.value;
      }
       
       function selectall(){
          window.location.href="<%=basePath%>rq/hotel?op=plane&inorout=2";
       }
       
      function selectlike(){
         var address=$("#address").val();
         if(address!=""){
           address=encodeURI(encodeURI(address));
         }else{
           alert("请输入前往城市名称！");
           return;
         }
         window.location.href="<%=basePath%>rq/hotel?op=plane&inorout=2&address="+address;
      }
       
jQuery.fn.selectCity = function(targetId) {
	var _seft = this;
	var targetId = $(targetId);

	this.click(function(){
		var A_top = $(this).offset().top + $(this).outerHeight(true);  //  1
		var A_left =  $(this).offset().left;
		targetId.bgiframe();
		targetId.show().css({"position":"absolute","top":A_top+"px" ,"left":A_left+"px"});
	});

	targetId.find("#selectItemClose").click(function(){
		targetId.hide();
	});

	targetId.find("#selectSub :checkbox").click(function(){		
		targetId.find(":checkbox").attr("checked",false);
		$(this).attr("checked",true);	
		_seft.val( $(this).val() );
		targetId.hide();
	});

	$(document).click(function(event){
		if(event.target.id!=_seft.selector.substring(1)){
			targetId.hide();	
		}
	});

	targetId.click(function(e){
		e.stopPropagation();
	});

    return this;
}
 
$(function(){
	$("#address").selectCity("#selectItem");

	//$("#address2").selectCity("#selectItem2");


});
    </script>
  </head>
  
  <body bgcolor="#FFFFFF">
  <div style="height: 60px">
    <table  width="88%" align="center" height="40px" border="0">
       <tr>
	       <td>
	          <font color="#3366cc" style="font-size:40px;line-height:50px;font-weight:800;">出港航班</font>
	         <!--  <select name="inorout" onchange="change(this);">
	           <option value="2" ${inorout eq '2'?'selected':'' }>出港航班</option>
	           <option value="1" ${inorout eq '1'?'selected':'' }>进港航班</option>
	         </select>-->
	           </td>
     
	       <td>
	         前往城市：<input type="text" name="address" id="address" value="${address }">&nbsp;&nbsp;&nbsp;&nbsp;
	         <input type="button" onclick="selectlike();" value="查  询"/>
	       </td>
	       <td>
	         <input type="button" onclick="selectall();" value="查询所有航班"/>
	       </td>
       </tr>
    </table>
  </div>
   <table width="88%" align="center" border="0" cellpadding="5" cellspacing="2" class="mb10">
        <tr>
          <td align="center" bgcolor="#D6E1ED"><strong>序号</strong></td>
          <td height="28" align="center" bgcolor="#D6E1ED"><strong>航线</strong></td>
          <td align="center" bgcolor="#D6E1ED"><strong>航班号</strong></td>
          <td align="center" bgcolor="#D6E1ED"><strong>机型</strong></td>
          <td align="center" bgcolor="#D6E1ED"><strong>执行班次</strong></td>
          <td align="center" bgcolor="#D6E1ED"><strong>起飞时间</strong></td>
          <td align="center" bgcolor="#D6E1ED"><strong>到达时间</strong></td>
        </tr>
        <c:choose>
         <c:when test="${not empty planelist}">
             <c:forEach var="plane" items="${planelist}" varStatus="index">
		          <tr>
		              <td width="5%" height="28" align="center"  bgcolor="#F1F4F9">${index.count}</td>
			          <td width="18%" height="28" align="center" bgcolor="#F1F4F9">${plane.airline}</td>
			          <td width="15%" height="28" align="center" bgcolor="#F1F4F9">${plane.flight}</td>
			          <td width="15%" height="28" align="center" bgcolor="#F1F4F9">${plane.planetype}</td>
			          <td width="14%" height="28" align="center" bgcolor="#F1F4F9">${plane.planecycle}</td>
			          <td width="14%" height="28" align="center" bgcolor="#F1F4F9">${plane.flightstarttime}</td>
			          <td width="19%" height="28" align="center" bgcolor="#F1F4F9">${plane.flightendtime}</td>
		        </tr>
		     </c:forEach>
         </c:when>
         <c:otherwise>
          <tr>
            <td  colspan="5" height="28" align="center" bgcolor="#F1F4F9" >暂无航班信息</td>
          </tr>
         </c:otherwise>
        </c:choose>
       
      </table>
      <div id="selectItem" class="selectItemhidden"> 
	<div id="selectItemAd" class="selectItemtit bgc_ccc"> 
		<h2 id="selectItemTitle" class="selectItemleft">请选择城市</h2> 
		<div id="selectItemClose" class="selectItemright">关闭</div>
	</div> 
	<div id="selectItemCount" class="selectItemcont"> 
		<div id="selectSub"> 
		 <ul>
		    <c:forEach var="city" items="${citylist}" varStatus="index">
				<li><input type="checkbox" name="cr${index.count }"  id="cr${index.count }" value="${city }"/><label for="cr${index.count }">${city }</label></li>
			</c:forEach>
		</ul>
		</div> 
	</div> 
</div> 
  </body>
</html>
