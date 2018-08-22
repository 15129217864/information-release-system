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
    
    <title>酒店介绍</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		li{ list-style: none;}
		.hotelmore{float:left; width:100%; }
		.hotelmore ul{margin-left:0px;}
	</style>
	<script src="/js/jquery1.4.2.js" type="text/javascript"></script>
<script language="javascript"> 
    //如下图片特效
	var e_tp=new Array(); 
	var adNum_elady1=0; 
	var temp='1#2#3#4#5#6#7#8#9#10#11#12#13#14#15#16#21#22'.split('#');
	<c:forEach var="images" items="${introduceimage}"  varStatus="index">
	  e_tp[${index.count-1}]="<%=basePath%>images/customer/${images}"; 
	</c:forEach>
	function elady1_set(){
		if (document.all){
			e_tprotator.filters.revealTrans.Transition=temp[Math.floor(Math.random()*temp.length)];
			e_tprotator.filters.revealTrans.apply(); 
		}
	}
	function elady1_playCo(){if (document.all)e_tprotator.filters.revealTrans.play()}

	function elady1_nextAd(){
		if(adNum_elady1<e_tp.length-1) 
		adNum_elady1++ ;
		else 
		adNum_elady1=0;
		elady1_set();
		document.images.e_tprotator.src=e_tp[adNum_elady1]+"?_="+new Date().getTime();
		elady1_playCo();
		theTimer=setTimeout("elady1_nextAd()", 8000);
	} 
	setTimeout("elady1_nextAd()", 2000);
	
    function getcity(){
    
       dataparam="op=weather";
       $.ajax({
		    type: "POST",
		    url: "<%=basePath%>rq/hotel",
		    data: dataparam,
		    dataType:"text",
		    success: parseData,
		    error:function(){
		           //alert("发送失败！"); //网络断了，或者服务挂了
		          },
		    complete: function (XHR, TS) { XHR = null } //释放ajax资源
		});
	}
	function parseData(data){
	  $("#status").html(data);
	}
	 window.setInterval("getcity()",300000);  
</script>
  </head>
  
  <body bgcolor="#FFFFFF">
     <div style="float:left;width:700px">
     	<img width="700" height="500" name="e_tprotator" style="FILTER: revealTrans(duration=2,transition=22)" src="<%=basePath%>images/customer/3.jpg">
     </div>
     <div style="float: left;width:570px;">
	       <ul style="padding-left: 10px;">
	       <li style='color:#666666'><strong style='font-size:20px; color:#444444'>&nbsp;天气预报 </strong><br/>
	         	<span id="status">${weather}</span>
	        </li>
         ${introduce }
      </div>
  </body>
</html>
