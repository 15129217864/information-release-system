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
    
    <title>盐城火车时刻表</title>
    
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
 
       function selectall(){
          window.location.href="<%=basePath%>rq/hotel?op=train";
       }
       
      function selectlike(){
         var address=$("#address").val();
         if(address!=""){
           address=encodeURI(encodeURI(address));
         }else{
           alert("请输入前往城市名称！");
           return;
         }
         window.location.href="<%=basePath%>rq/hotel?op=train&address="+address;
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
	          <font color="#3366cc" style="font-size:40px;line-height:50px;font-weight:800;">火车时刻表</font>
	       </td>
	       <td>
	         前往城市(终点站)：<input type="text" name="address" id="address" value="${address }">&nbsp;&nbsp;&nbsp;&nbsp;
	         <input type="button" onclick="selectlike();" value="查  询"/>
	       </td>
	       <td>
	         <input type="button" onclick="selectall();" value="查询所有火车时刻"/>
	       </td>
       </tr>
    </table>
  </div>
     <table width="88%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FF8800">
		<tr bgcolor="#FFAC75">
			<td width="200" height="26"><div align="center" class="style9">车次</div></td>
			<td width="100"><div align="center" class="style9">始发站</div></td>
			<td width="100"><div align="center" class="style9">终点站</div></td>
			<td width="75"><div align="center" class="style9">车辆类型</div></td>
			<td width="100"><div align="center" class="style9">发站</div></td>
			<td width="40"><div align="center" class="style9">发时</div></td>
			<td width="100"><div align="center" class="style9">到站</div></td>
			<td width="40"><div align="center" class="style9">到时</div></td>
			<td width="40"><div align="center" class="style9">停站</div></td>
			<td width="40"><div align="center" class="style9">历时</div></td>
			<td width="40"><div align="center" class="style9">硬座</div></td>
			<td width="40"><div align="center" class="style9">软座</div></td>
			<td width="90"><div align="center" class="style9">硬卧中</div></td>
			<td width="80"><div align="center" class="style9">软卧下</div></td>
		</tr>
		   <c:choose>
		   <c:when test="${not empty trainlist}">
             <c:forEach var="train" items="${trainlist}" varStatus="index">
				<tr bgcolor="#FFFFFF">
					<td height="26"><div align="center">${train.chenumber }</div></td>
					<td><div align="center">${train.shifazhang }</div></td>
					<td><div align="center">${train.zhongdianzhan}</div></td>
					<td><div align="center">${train.cheliangtype}</div></td>
					<td><div align="center">${train.fazhan}</div></td>
					<td><div align="center">${train.fashi}</div></td>
					<td><div align="center">${train.daozhan}</div></td>
					<td><div align="center">${train.daoshi}</div></td>
					<td><div align="center">${train.tingzhan}</div></td>
					<td><div align="center">${train.lishi}</div></td>
					<td><div align="center">${train.yingzuo}</div></td>
					<td><div align="center">${train.ruanzuo}</div></td>
					<td><div align="center">${train.yingwozhong}</div></td>
					<td><div align="center">${train.ruanwoxia}</div></td>
				</tr>
		    </c:forEach>
         </c:when>
         <c:otherwise>
          <tr>
            <td  colspan="5" height="28" align="center" bgcolor="#FFFFFF" >暂无火车时刻信息</td>
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
