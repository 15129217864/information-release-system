<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>盐城旅游交通信息查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	  <style type="text/css">
*{margin:0;padding:0;list-style-type:none;}
a,img{border:0;}
body{font:12px/180% Arial, Helvetica, sans-serif,"宋体";}
a{color:#333;text-decoration:none;}
a:hover{color:#3366cc;text-decoration:underline;}
.demopage{width:100%;margin:0 auto;}
/* tabbox */
.tabbox{width:100%;margin:5px auto;position:relative;height:600;}
.tabbox .tabbtn{height:60px;background:url(<%=basePath%>images/tabbg.gif) repeat ;border-left:solid 1px #ddd;border-right:solid 1px #ddd;}
.tabbox .tabbtn li{float:left;position:relative;margin:0 0 0 -1px;}
.tabbox .tabbtn li a,.tabbox .tabbtn li span{display:block;float:left;height:50px;line-height:50px;overflow:hidden;width:330px;text-align:center;padding-top:8px; font-size:40px;cursor:pointer;}
.tabbox .tabbtn li.current{border-left:solid 1px #d5d5d5;border-right:solid 1px #d5d5d5;border-top:solid 1px #c5c5c5;}
.tabbox .tabbtn li.current a,.tabbox .tabbtn li.current span{border-top:solid 2px #ff6600;height:60px;line-height:50px;background:#fff;color:#3366cc;font-weight:800;}
.tabbox .tabcon{padding:0px;border-width:0 1px 1px 1px;border-color:#ddd;border-style:solid;}
</style>

<script type="text/javascript" src="<%=basePath%>js/jquery1.4.2.js"></script>
<script type="text/javascript">

//tab plugins 插件
$(function(){
//iframe高度随内容自动调整 ,没起作用
	//$('#frameheight').load( 
	//	function(){ 
	//	    var heightemp=$(this).contents().find("body").height();
	//		alert(heightemp);
	//		$(this).height(heightemp); 
	//	} 
	//); 
	//选项卡鼠标滑过事件
	$('#clicktab .tabbtn li').click(function(){
		TabSelect("#clicktab .tabbtn li", "#clicktab .tabcon", "current", $(this))
	});
	$('#clicktab .tabbtn li').eq(0).trigger("click");

	function TabSelect(tab,con,addClass,obj){
		var $_self = obj;
		var $_nav = $(tab);
		$_nav.removeClass(addClass),
		$_self.addClass(addClass);
		var $_index = $_nav.index($_self);
		var $_con = $(con);
		$_con.hide(),
		$_con.eq($_index).show();
	}

});
//iframe高度随内容自动调整 ,没起作用
function loadheight(){
	$("#frameheight").load(function() { 
	  $(this).height($(this).contents().height()); 
	}); 
}
//iframe高度随内容自动调整 ,没起作用
function adjustIFramesHeightOnLoad(iframe) {

	var iframeHeight = Math.min(iframe.contentWindow.window.document.documentElement.scrollHeight, iframe.contentWindow.window.document.body.scrollHeight);

	$("#frameheight").height(iframeHeight);
}
</script>
 </head>

  <body bgcolor="#FFFFFF">
 <div class="demopage">
		<div class="tabbox" id="clicktab">
			<ul class="tabbtn">
				<li class="current"><span>酒店介绍</span></li>
				<li><span>盐城航班时刻表</span></li>
				<li><span>盐城火车时刻表</span></li>
				<li><span>盐城汽车时刻表</span></li>
			</ul>
			<div class="tabcon">
				<iframe src="<%=basePath%>rq/hotel?op=introduce" id="frameheight" width="100%" height="100%"></iframe>
			</div>
			<div class="tabcon">
				<iframe src="<%=basePath%>rq/hotel?op=plane" id="frameheight"  width="100%" height="100%"></iframe>
			</div>
			<div class="tabcon">
				<iframe src="<%=basePath%>rq/hotel?op=train"  id="frameheight" width="100%" height="100%"></iframe>
			</div>
			<div class="tabcon">
				<iframe src="<%=basePath%>rq/hotel?op=bus" id="frameheight"  width="100%" height="100%"></iframe>
			</div>
		</div>
	</div>
 </body>
</html>

