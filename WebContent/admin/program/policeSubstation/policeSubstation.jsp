<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'policeSubstation.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
		td{
			border:1px solid #CCC;
			font-size:20px;
		}
		#biaoge td input{ width:90% }
	</style>
<script src="/js/jquery1.4.2.js" type="text/javascript"></script>
<script type="text/javascript">
//实现表格可编辑
	   $(document).ready(function(){
			//取出所有的单元格,除第一行的菜单以外
			var tds=$("tr:gt(0) td");
			//alert(tds.length);
			//给每个单元格注册一个单击事件
			tds.next().click(tdClick);
	   });
	
	   function tdClick(){
			if($(this).children().attr("type")!="button"){
			   //得到该单元格的对象
			   var td=$(this);
				//取出改单元格的内容
				var tdText=td.text();
				//alert(tdText);	
				//清空改单元格内容
				td.empty();//或者td.html("");也可以
				//创建一个input文本框
				var input=$("<input>");
				//将原来的值赋值给input文本框
				input.attr("value",tdText).attr("type","text");
				//给文本框注册一个keyup事件
				input.keyup(function(event){
					 var myevent=event||window.event;
					//判断是否是回车键安县
					if(myevent.keyCode==13){
						//将当前输入的信息保存下来
						var inputnode=$(this);
						//文本框的值
						var inputText=inputnode.val();
						//清空改td中的内容
						var mytd=inputnode.parent();
						mytd.empty();
						//将文本框值赋给td
						mytd.html(inputText);	
						//让td重新拥有单击事件
						mytd.click(tdClick);
					}
				});
				input.blur(function(){
					var mytd=$(this).parent();
					var inputText=$(this).val();
					mytd.empty();
					mytd.html(inputText);
					mytd.click(tdClick);
				});
				//将文本框追加给单元格
				td.append(input);
				//文本框高亮选中
				var inputdom=input.get(0);
				inputdom.select();
				
				//td.html(input.val());
				//一处该单元格的单击事件
				td.unbind("click");
		   }else{
		        var textarray=$(this).prevAll();//查找当前元素之前所有的同辈元素
				for(var i=0,n=textarray.length;i<n;i++){
				  if(i!=n-1){
				    $(textarray[i]).empty();
				  }
				}
		   }
	  }
      function getAll(){
      
       if($("#clientip").val()=="0"){
         alert("请选择终端！");
         return ;
       }
	     var tds=$("tr:gt(0) td");
		 var stingbuffer="";
		 var temp=6;
		 var tempstring="";
	     for(var i=0,n=tds.length;i<n;i++){//6,12,18,24
		   if(i==0||i%temp!=0){
		     tempstring=$(tds[i]).text().replace(/\s/g,'');
		     stingbuffer+=tempstring==""?"no!":encodeURI(encodeURI(tempstring))+"!";
		   }else{
		     stingbuffer=stingbuffer.substring(0,stingbuffer.length-1)+"@"
		     temp=temp+7;
		   }
		 }
		// alert(stingbuffer.substring(0,stingbuffer.length-1));
		 ajaxComm("op=send&clientip="+$("#clientip").val()+"&oparams="+stingbuffer.substring(0,stingbuffer.length-1));
	  }
	   
	  function ajaxComm(pram,url){
	     //alert(pram);
		 $.ajax({
			    type: "POST",
			    url: "<%=basePath%>rq/sendpolicesubstation",
			    data: pram,
			    dataType:"text",
			    success: parseData,
			    error:function(){
			            alert("发送失败！"); //网络断了，或者服务挂了
			          },
			    complete: function (XHR, TS) { XHR = null } //释放ajax资源
		 });
	}
	
	function parseData(data){
	   //alert(data);
	   status(data)
	}
	function status(data){
	  $("#status").html(data)
	  setTimeout("$('#status').html('')",1000);
	}
	function  changeip(){
	  var parames="op=list&clientip="+$("#clientip").val();
	  window.location.href="<%=basePath%>rq/sendpolicesubstation?"+parames;
	}
	
</script>
</head>
<body>
<input type="button" value="发送" onclick="getAll()"/>
<select id="clientip" name="clientip" onchange="changeip()">
  <option value="0">==请选择终端==</option>
  <c:forEach var="clientbean" items="${clientlist}">
      <option value="${clientbean.key }" ${clientiptemp==clientbean.key?'selected':''}>${clientbean.value }</option>
  </c:forEach>
</select><span id="status" style="color: red"></span>
<table id="biaoge"  width="1000" style="text-align:center; width:1000px; border:1px solid #CCC; border-collapse:collapse;">
	<tr id="titles" style="background-color:#92D4EE">
	    <td width="60"><b>序号</b></td>
    	<td width="200"><b>讯问室</b></td>
        <td width="120"><b>办案警官</b></td>
        <td width="120"><b>警员编号</b></td>
        <td width="350"><b>案件性质</b></td>
        <td width="300"><b>犯罪嫌疑人</b></td>
		<td width="100"><b>操作</b></td>
    </tr>
      <c:if test="${empty policesubstationlist}">
	     <tr>
		    <td colspan="7" style="font-size: 30px;color: red"><b>====请选择下拉列表内的终端进行操作====</b></td>
	     </tr>
     </c:if>
    <c:forEach var="policesubstation" items="${policesubstationlist}">
       <tr>
	        <td>${policesubstation.seqno}</td>
	    	<td>${policesubstation.roomno}</td>
	        <td>${policesubstation.policename}</td>
	        <td>${policesubstation.casecode}</td>
	        <td>${policesubstation.casecharacter}</td>
	        <td>${policesubstation.suspect}</td>  
			<td><input type="button" value="清空"></td>
       </tr>
    </c:forEach>
 
</table>
<br />
<br />
<br />
<br />
<br />
</body>
</html>
