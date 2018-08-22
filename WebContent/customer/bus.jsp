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
    
    <title>盐城汽车时刻表</title>
    
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
#selectItem2{background:#FFF;position:absolute;top:0px;left:center;border:1px solid #000;overflow:hidden;width:540px;z-index:1000;}
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
 
  /** function getcity(params){
	   if(params.value==""){
	     ;
	   }else{
	       dataparam="op=bus&opcity=ajax&address="+encodeURI(encodeURI(params.value));
	       $.ajax({
			    type: "POST",
			    url: "<%=basePath%>rq/hotel",
			    data: dataparam,
			    dataType:"text",
			    success: parseData,
			    error:function(){
			           alert("发送失败！"); //网络断了，或者服务挂了
			          },
			    complete: function (XHR, TS) { XHR = null } //释放ajax资源
			});
		}
	}
	  function parseData(data){
	      $("#address2").selectCity("#selectItem2");
		  $("#selectItem2").children("#selectItemCount").children("#selectSub").html(data);
	   }
	   */
	   var paramtemp="${address}";
	   function getCitylist(params){
	     if(params.value!=""&&params.value!=undefined&&paramtemp!=params.value){
	          paramtemp=params.value;
	     	  //alert(params.value);
		      var address=encodeURI(encodeURI(params.value));
		      window.location.href="<%=basePath%>rq/hotel?op=bus&address="+address;
	      }
	   }
       function selectall(){
          window.location.href="<%=basePath%>rq/hotel?op=bus";
       }
       
      function selectlike(){
         var address=$("#address").val();
         var address2=$("#address2").val();
         if(address!=""&address2!=""){
           address=encodeURI(encodeURI(address));
           address2=encodeURI(encodeURI(address2));
         }else{
           alert("请输入前往城市名称！");
           return;
         }
         window.location.href="<%=basePath%>rq/hotel?op=bus&address="+address+"&address2="+address2;
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
				_seft.val($(this).val());
				if($("#address").val()!=""){
				   getCitylist($(this).val());
				}
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
			$("#address2").selectCity("#selectItem2");
		});
    </script>
  </head>
  
  <body bgcolor="#FFFFFF">
    <div style="height: 60px">
    <table  width="88%" align="center" height="40px" border="0">
       <tr>
	       <td>
	         <font color="#3366cc" style="font-size:40px;line-height:50px;font-weight:800;">汽车时刻表</font>
	       </td>
	         <td> 
	            省份：<input type="text" name="address" id="address"  onpropertychange="getCitylist(this)" value="${address }">&nbsp;&nbsp;
	            城市：<input type="text" name="address2" id="address2" value="${address2 }">&nbsp;&nbsp;&nbsp;&nbsp;
	         <input type="button" onclick="selectlike();" value="查  询"/>
	       </td>
	       <td>
	         <input type="button" onclick="selectall();" value="查询所有火车时刻"/>
	       </td>
       </tr>
    </table>
  </div>
   <table width="88%" align="center" border="0" cellpadding="5" cellspacing="2" class="mb10">
        <tr>
          <td align="center" bgcolor="#D6E1ED"><strong>序号</strong></td>
          <td height="28" align="center" bgcolor="#D6E1ED"><strong>起站</strong></td>
          <td align="center" bgcolor="#D6E1ED"><strong>省份</strong></td>
          <td align="center" bgcolor="#D6E1ED"><strong>讫站</strong></td>
          <td align="center" bgcolor="#D6E1ED"><strong>车型</strong></td>
          <td align="center" bgcolor="#D6E1ED"><strong>公里</strong></td>
          <td align="center" bgcolor="#D6E1ED"><strong>班车时刻</strong></td>
        </tr>
        <c:choose>
         <c:when test="${not empty buslist}">
             <c:forEach var="bus" items="${buslist}" varStatus="index">
		          <tr>
		              <td width="5%" height="28" align="center"  bgcolor="#F1F4F9">${index.count}</td>
			          <td width="15%" height="28" align="center" bgcolor="#F1F4F9">盐城汽车客运站</td>
			          <td width="10%" height="28" align="center" bgcolor="#F1F4F9">${bus.province}</td>
			          <td width="10%" height="28" align="center" bgcolor="#F1F4F9">${bus.terminus}</td>
			          <td width="10%" height="28" align="center" bgcolor="#F1F4F9">${bus.bustype}</td>
			          <td width="5%" height="28" align="center" bgcolor="#F1F4F9">${bus.kilometre}</td>
			          <td width="45%" height="28" align="center" bgcolor="#F1F4F9">${bus.bushour}</td>
		        </tr>
		     </c:forEach>
         </c:when>
         <c:otherwise>
          <tr>
            <td  colspan="7" height="28" align="center" bgcolor="#F1F4F9" >暂无班车时刻信息</td>
          </tr>
         </c:otherwise>
        </c:choose>
       
      </table>
      
<div id="selectItem" class="selectItemhidden"> 
	<div id="selectItemAd" class="selectItemtit bgc_ccc"> 
		<h2 id="selectItemTitle" class="selectItemleft">请选择省份</h2> 
		<div id="selectItemClose" class="selectItemright">关闭</div>
	</div> 
	<div id="selectItemCount" class="selectItemcont"> 
		<div id="selectSub"> 
		  <ul>
		    <c:forEach var="province" items="${provincelist}" varStatus="index">
				 <li><input type="checkbox" name="cr${index.count }"  id="cr${index.count }" value="${province }"/><label for="cr${index.count }">${province }</label></li>
			</c:forEach>
		</ul>
		</div> 
	</div> 
</div> 
<div id="selectItem2" class="selectItemhidden"> 
	<div id="selectItemAd" class="selectItemtit bgc_ccc"> 
		<h2 id="selectItemTitle" class="selectItemleft">请选择城市</h2> 
		<div id="selectItemClose" class="selectItemright">关闭</div>
	</div> 
	<div id="selectItemCount" class="selectItemcont"> 
		<div id="selectSub"> 
          <ul>
		   <c:forEach var="city" items="${citylist}" varStatus="index"> 
	            <li><input type="checkbox" name="crr${index.count*2 }"  id="crr${index.count }" value="${city }"/><label for="crr${index.count }">${city }</label></li>
		   </c:forEach>
        </ul>
		</div> 
	</div> 
</div> 
  </body>
</html>
