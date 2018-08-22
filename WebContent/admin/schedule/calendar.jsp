<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String left_menu=request.getParameter("left_menu");
String type=request.getParameter("type");
String zuid=request.getParameter("zuid")==null?"":request.getParameter("zuid");
String cnt_mac=request.getParameter("cnt_mac")==null?"":request.getParameter("cnt_mac");
String nowdate=UtilDAO.getNowtime("yyyy-M-d");
request.setAttribute("nowdate",nowdate);
 %>
<html>
  <head>
    <title>日程表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <script language="javascript" src="/js/vcommon.js"></script>
    <link rel="stylesheet" type="text/css" href="/wbox/wbox.css" />
    <link rel="stylesheet" href="/css/style.css" type="text/css">
	<style type="text/css">
		/* 页面基本样式 */
		body, td, input {
		    font-family:Arial;
		    font-size:12px;

		}
		
		/* 日程表格样式 */
		#calTable {
		    border-collapse:collapse;
		    border:2px solid #C3D9FF;
		}
		
		/* 每日单元格样式 */
		td.calBox {
		    border:2px solid #CCDDEE;
		    width:118;
		    height:55px;
		    vertical-align:top;
		}
		
		/* 每日单元格内日期样式 */
		td.calBox div.date {
		    background:#E8EEF7;
		    font-size:16px;
		    padding:2px;
		 
		}.date {
		    background:#E8EEF7;
		    font-size:16px;
		    padding:2px;
		 
		}
		
		/* 每日单元格内周六周日样式 */
		td.sat div.date, td.sun div.date{
		    color:red;
		}
		
		/* 今日样式 */
		td.calBox div.today {
		    background:#FF9900;
		}.today {
		    background:#FF9900;
		}
		
		/* 周标识样式 */
		td.day {
		    text-align:center;
		    background:#C3D9FF;
		    border:1px solid #CCDDEE;
		    color:#112ABB;
		}
		
		/* 当前显示的年月样式 */
		#dateInfo {
		    font-weight:bold;
		    margin:3px;
		}
		
		/* 添加任务div样式 */
		#addBox {
		    display:none;
		    position:absolute;
		    width:100px;
		    border:1px solid #000;
		    height:100px;
		    background:#FFFF99;
		    padding:10px;
		}
		
		/* 添加任务内日期样式 */
		#taskDate {
		    height:30px;
		    font-weight:bold;
		    padding:3px;
		}
		
		/* 按钮样式 */
		.taskBtn {
		    margin:10px;
		}
		
		/* 编辑任务div样式 */
		#editBox {
		    display:none;
		    position:absolute;
		    width:100px;
		    border:1px solid #000;
		    height:40px;
		    background:#99FF99;
		    padding:10px;
		}
		
		/* 任务样式 */
		div.task {
		    width:118;
		    height:85px;
		    overflow:hidden;
		    white-space:nowrap;
		    border:0px solid #FF9900;
		    color:#000;
		    
		   
		}/* 任务样式 */
		div.task1 {
		    width:118;
		    height:85px;
		    overflow:hidden;
		    white-space:nowrap;
		    border:2px solid #FF9900;
		    color:#000;
		   
		}
		
		/* 定时循环 */
		div.type_info_0 {
		    overflow:hidden;
		    white-space:nowrap;
		     background-color:#fff7d7;
		      border-bottom :1px solid #fad163;
		    margin-bottom:1px;
		    font-size: 12px;
		    color: #1f2223;
		}
		/* 插播 */
		div.type_info_1 {
		    overflow:hidden;
		    white-space:nowrap;
		    background-color:#f793b4;
		    border-bottom :1px solid #e67399;
		    margin-bottom:1px;
		    font-size: 12px;
		    color: #1f2223;
		}/* 定时 */
		div.type_info_2 {
		    overflow:hidden;
		    white-space:nowrap;
		    background-color:#b8f457;
		    border-bottom :1px solid #88c13c;
		    margin-bottom:1px;
		    font-size: 12px;
		    color: #1f2223;
		}/* 永久循环 */
		div.type_info_3 {
		    overflow:hidden;
		    white-space:nowrap;
		    background-color:#a7dae9;
		    border-bottom :1px solid #03adde;
		    margin-bottom:1px;
		    font-size: 12px;
		    color: #1f2223;
		}
		div.type_info_click {
		    overflow:hidden;
		    white-space:nowrap;
		    font-size: 12px;
		    background-color:#3640ad;
		    color: #ffffff;
		}
		.more_a:hover{
			color: blue;
			text-decoration: underline;
			
		}
		.more_a1:link{
			font-size:16px;
			width: 80px;
			font-weight:bold;
		}.more_a1:hover{
			color: red;
			font-weight:bold;
			font-size:16px;
			text-decoration: underline;
		}
		
		
    </style>
	<script type="text/javascript" src="/js/jquery1.4.2.js"></script>
	<script type="text/javascript" src="/js/wbox.js"></script> 
	<script type="text/javascript">
		var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);    //每月天数
		var today = new Today();    //今日对象
		var year = today.year;      //当前显示的年份
		var month = today.month;    //当前显示的月份
		
		//页面加载完毕后执行fillBox函数
		
			<%
		Calendar   cal   =   Calendar.getInstance(); 
		String   month   =   (cal.get(Calendar.MONTH))+"";
		String monthnum=request.getParameter("month");
			if(monthnum==null||"".equals(monthnum)){
				monthnum=month;
			}
		
		request.setAttribute("monthnum",Integer.parseInt(monthnum)+1);
		if(!"".equals(monthnum)){
			%>
			year = today.year;
		    month = <%=monthnum%>;
		    $(function() {
		    fillBox();
		});
			<%
		}else{
		%>
			$(function() {
		    fillBox();
		});
			<%
		}
		
		%>
		//今日对象
		function Today() {
		    this.now = new Date();
		    this.year = this.now.getFullYear();
		    this.month = this.now.getMonth();
		    this.day = this.now.getDate();
		}
		
		//根据当前年月填充每日单元格
		function fillBox() {
		    updateDateInfo();                   //更新年月提示
		    $("td.calBox").empty();             //清空每日单元格
		
		    var dayCounter = 1;                 //设置天数计数器并初始化为1
		    var cal = new Date(year, month, 1); //以当前年月第一天为参数创建日期对象
		    var startDay = cal.getDay();        //计算填充开始位置
		    //计算填充结束位置
		    var endDay = startDay + getDays(cal.getMonth(), cal.getFullYear()) - 1;
		
		    //如果显示的是今日所在月份的日程，设置day变量为今日日期
		    var day = -1;
		    if (today.year == year && today.month == month) {
		        day = today.day;
		    }
		
		    //从startDay开始到endDay结束，在每日单元格内填入日期信息
		    for (var i=startDay; i<=endDay; i++) {
		        if (dayCounter==day) {
		            $("#calBox" + i).html("<div class='date today' id='" + year + "-" + (month + 1) + "-" + dayCounter + "'><table width='95%' border='0'  cellpadding='0' cellspacing='0'><tr><td  class='date today'><a href='javascript:;'  class='more_a1' title='点击查看当天详细信息'  onclick=openTasks('" + year + "-" + (month + 1) + "-" + dayCounter + "') >" + dayCounter + "</a></td><td align='right'><a href='javascript:;' style='display: none;' id='more_a_" + year + "-" + (month + 1) + "-" + dayCounter + "' class='more_a' onclick=openTasks('" + year + "-" + (month + 1) + "-" + dayCounter + "') >更多</a></td></tr></table></div>");
		        } else {
		            $("#calBox" + i).html("<div class='date' id='" + year + "-" + (month + 1) + "-" + dayCounter + "'> <table width='95%' border='0'  cellpadding='0' cellspacing='0'><tr><td  class='date'><a href='javascript:;'   class='more_a1' title='点击查看当天详细信息'  onclick=openTasks('" + year + "-" + (month + 1) + "-" + dayCounter + "') >" + dayCounter + "</a></td><td align='right'><a href='javascript:;' style='display: none;' id='more_a_" + year + "-" + (month + 1) + "-" + dayCounter + "' class='more_a'  onclick=openTasks('" + year + "-" + (month + 1) + "-" + dayCounter + "') >更多</a></td></tr></table></div>");
		        }
		        dayCounter++;
		    }
		    getTasks();//从服务器获取任务信息
		}
		
		//从服务器获取任务信息
		function getTasks() {
		progressShow();
		    $.getJSON("/rq/getscedcule",               //服务器页面地址
		        {
		            action: "getTasks",  //action参数
		            left_menu: "<%=left_menu%>",
		            zuid: "<%=zuid%>",
		            cnt_mac: "<%=cnt_mac%>",            
		            month: year + "-" + (month + 1), //年月参数
		            timeStamp: new Date().getTime()
		        },
		        function(json) {                    //回调函数
		            //遍历JSON数组，建立任务信息
		            $(json).each(function(i){
		            	//alert(json[i].builddate+json[i].task+json[i].id);
		            	
		                  buildTask(json[i].builddate, json[i].id, json[i].task,json[i].isloop);
		                 // buildTask(json[i].builddate);
		            });
		              progressBarHidden();
		        }
		    );
		  
		}
		var builddatetemp="boy";
		//根据日期、任务编号、任务内容在页面上创建任务节点
		function buildTask(buildDate,id,task,isloop){
		     //alert(builddatetemp);
		     var chnum=0;
		     if(document.getElementById("task" + buildDate)){
		       chnum=document.getElementById("task" + buildDate).childNodes.length;
		     }
			 if(chnum<5){
		     	if(document.getElementById("task" + buildDate)){
			     	//alert("task" + buildDate);
			     	 $("#task" + buildDate).append("<div class='type_info_"+isloop+"' title='"+task+"'>"+task+"</div>");
			     }else{
				    if(builddatetemp!=buildDate){
				    	if(buildDate=='${nowdate}'){
				    		$("#" + buildDate).parent().append("<div id='task" + buildDate + "' class='task1' ><div class='type_info_"+isloop+"' title='"+task+"'>"+task+"</div></div>");
				    	}else{
				       		$("#" + buildDate).parent().append("<div id='task" + buildDate + "' class='task' ><div class='type_info_"+isloop+"' title='"+task+"'>"+task+"</div></div>");
				    	}
				    } builddatetemp=buildDate;
			    }
		    }if(chnum==5){
		    	document.getElementById("more_a_"+buildDate).style.display="block";
		    }
		   
		}
		
		function openTasks(datetemp){
		progressShow();
		  var onedate=datetemp;
		  var strtmp=onedate.split("-");
		  var month=(strtmp[1]<10?"0"+strtmp[1]:strtmp[1]);
		  var day=(strtmp[2]<10?"0"+strtmp[2]:strtmp[2]);
		  onedate=strtmp[0]+"-"+month+"-"+day;
		  parent.deviceTop.location.href="/admin/schedule/ter_header.jsp?month=<%=month%>&left_menu=<%=left_menu%>&zuid=<%=zuid%>&cnt_mac=<%=cnt_mac%>&type=info";
		  window.location.href="viewproject.jsp?onedate="+onedate+"&twodate="+onedate+"&cnt_mac=<%=cnt_mac%>&left_menu=<%=left_menu%>&zuid=<%=zuid%>";
		  parent.parent.menu.location.href="/admin/schedule/left.jsp?month=&opp=1&type=info&onedate="+onedate+"&twodate="+onedate+"";
		 // $("#task"+datetemp.id.substr(4)).wBox({requestType:"iframe",iframeWH:{width:850,height:500},target:"viewproject.jsp?onedate="+onedate+"&twodate="+onedate});
		}
		
		function selectforver(){
		   $("#selectforever").wBox({requestType:"iframe",iframeWH:{width:800,height:500},target:"viewforeverproject.jsp"});
		}
		
		//判断是否闰年返回每月天数
		function getDays(month, year) {
		    if (1 == month) {
		        if (((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400)) {
		            return 29;
		        } else {
		            return 28;
		        }
		    } else {
		        return daysInMonth[month];
		    }
		}
		
		//显示上月日程
		function prevMonth() {
		    builddatetemp="boy";
		    if ((month - 1) < 0) {
		        month = 11;
		        year--;
		    } else {
		        month--;
		    }
		    fillBox();              //填充每日单元格
		}
		
		//显示下月日程
		function nextMonth() {
		    builddatetemp="boy";
		    if((month + 1) > 11) {
		        month = 0;
		        year++;
		    } else {
		        month++;
		    }
		    fillBox();              //填充每日单元格
		}
		
		//显示本月日程
		function thisMonth() {
		    builddatetemp="boy";
		    year = today.year;
		    month = today.month;
		    fillBox();              //填充每日单元格
		}
		 function progressBarOpen(){
		  			var cWidth = document.body.clientWidth;
		  			var cHeight= document.body.clientHeight;
					var divNode = document.createElement( 'div' );	
					divNode.setAttribute("id", "systemWorking");
					var topPx=(cHeight)*0.2;
					var defaultLeft=(cWidth)*0.3;
					divNode.style.cssText='position:absolute;margin:0;top:'+topPx+'px;left:'+defaultLeft+';width:215;height:59;background-image:url(/images/wait_background.gif);text-align:center;padding-top:20'; 
					divNode.innerHTML= "<img src='/images/mid_giallo.gif' align=absmiddle><font style='font-size:12px;' id='p_str'>&nbsp;加载中，请稍后...</font>";
					divNode.style.display='none';	
					document.getElementsByTagName("body")[0].appendChild(divNode);   		
		       }
		       function progressShow() {
			   if(document.all.systemWorking)
				  document.all.systemWorking.style.display = "block";
			   }   
			
			   function progressBarHidden() {
			    if(document.all.systemWorking)
				  document.all.systemWorking.style.display = "none";
			   }
		
		//更新年月提示
		function updateDateInfo() {
		    $("#dateInfo").html(year + "年" + (month + 1) + "月");
		}
		function gomonth(month){
		window.location.href="/admin/schedule/calendar.jsp?month="+month+"&left_menu=<%=left_menu%>&zuid=<%=zuid%>&cnt_mac=<%=cnt_mac%>";
		parent.deviceTop.location.href="/admin/schedule/ter_header.jsp?month="+month+"&left_menu=<%=left_menu%>&zuid=<%=zuid%>&cnt_mac=<%=cnt_mac%>&type=<%=type%>";
		parent.parent.menu.location.href="/admin/schedule/left.jsp?month="+month+"&opp=1&type=<%=type%>";	
		}
	  parent.deviceTop.location.href="/admin/schedule/ter_header.jsp?month=<%=month%>&left_menu=<%=left_menu%>&zuid=<%=zuid%>&cnt_mac=<%=cnt_mac%>&type=cal";
	parent.parent.menu.location.href="/admin/schedule/left.jsp?month=<%=month%>&opp=1&type=cal";	
		
     </script>
  </head>
  
  <body onload="progressBarOpen();">
  <div  style="float:left;">
    <!-- 日历表格 -->
	<table id="calTable" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<input type="button" value="上月" onclick="prevMonth()" class="button">
		        <input type="button" value="本月" onclick="thisMonth()" class="button">
		        <input type="button" value="下月" onclick="nextMonth()" class="button">
			</td>
			<td>
				  <select style="width: 80px;"   class="button"  name="cnt_downtime" onchange="gomonth(this.value);">
				<c:forEach begin="1" end="12" var="btmp" step="1">
					<option value="${btmp-1 }" ${btmp==monthnum?'selected':''}>${btmp}月</option>
				</c:forEach>
				</select>
			</td>
		    <td colspan="2" height="40px" align="center">
		         <span id="dateInfo" style="font-size: 18px; font-weight: bold"></span>
		    </td>
		     <td colspan="3" height="40px" align="right">
		        <table cellpadding="2" cellspacing="0">
					<tr>
						<td><div style="border: 1px solid #000; width: 20px;height: 5px;background-color: #f793b4">&nbsp;</div></td>
						<td>插播</td>
						<td><div style="border: 1px solid #000; width: 20px;height: 5px;background-color: #b8f457">&nbsp;</div></td>
						<td>定时</td>
						<td><div style="border: 1px solid #000; width: 20px;height: 5px;background-color: #fff7d7">&nbsp;</div></td>
						<td>定时循环</td>
						<td><div style="border: 1px solid #000; width: 20px;height: 5px;background-color: #a7dae9">&nbsp;</div></td>
						<td>永久循环</td>
					</tr>
				</table>
		    </td>
		</tr>
		<tr>
		    <td class="day" height="35px" >周日</td>
		    <td class="day" >周一</td>
		    <td class="day" >周二</td>
		    <td class="day" >周三</td>
		    <td class="day" >周四</td>
		    <td class="day" >周五</td>
		    <td class="day" >周六</td>
		</tr>
		<tr>
		    <td class="calBox sun" id="calBox0"></td>
		    <td class="calBox" id="calBox1"></td>
		    <td class="calBox" id="calBox2"></td>
		    <td class="calBox" id="calBox3"></td>
		    <td class="calBox" id="calBox4"></td>
		    <td class="calBox" id="calBox5"></td>
		    <td class="calBox sat" id="calBox6"></td>
		</tr>
		<tr>
		    <td class="calBox sun" id="calBox7"></td>
		    <td class="calBox" id="calBox8"></td>
		    <td class="calBox" id="calBox9"></td>
		    <td class="calBox" id="calBox10"></td>
		    <td class="calBox" id="calBox11"></td>
		    <td class="calBox" id="calBox12"></td>
		    <td class="calBox sat" id="calBox13"></td>
		</tr>
		<tr>
		    <td class="calBox sun" id="calBox14"></td>
		    <td class="calBox" id="calBox15"></td>
		    <td class="calBox" id="calBox16"></td>
		    <td class="calBox" id="calBox17"></td>
		    <td class="calBox" id="calBox18"></td>
		    <td class="calBox" id="calBox19"></td>
		    <td class="calBox sat" id="calBox20"></td>
		</tr>
		<tr>
		    <td class="calBox sun" id="calBox21"></td>
		    <td class="calBox" id="calBox22"></td>
		    <td class="calBox" id="calBox23"></td>
		    <td class="calBox" id="calBox24"></td>
		    <td class="calBox" id="calBox25"></td>
		    <td class="calBox" id="calBox26"></td>
		    <td class="calBox sat" id="calBox27"></td>
		</tr>
		<tr>
		    <td class="calBox sun" id="calBox28"></td>
		    <td class="calBox" id="calBox29"></td>
		    <td class="calBox" id="calBox30"></td>
		    <td class="calBox" id="calBox31"></td>
		    <td class="calBox" id="calBox32"></td>
		    <td class="calBox" id="calBox33"></td>
		    <td class="calBox sat" id="calBox34"></td>
		</tr>
		<tr>
		    <td class="calBox sun" id="calBox35"></td>
		    <td class="calBox" id="calBox36"></td>
		    <td class="calBox" id="calBox37"></td>
		    <td class="calBox" id="calBox38"></td>
		    <td class="calBox" id="calBox39"></td>
		    <td class="calBox" id="calBox40"></td>
		    <td class="calBox sat" id="calBox41"></td>
		</tr>
	 </table><div style="height: 50px"></div>
	 </div>
	 
	 <div id="selectforever" style="height: 100px;padding: 50px,50px;display: none"><input type="button" value="查询永久循环节目" onclick="selectforver()" class="button"></div>
  </body>
</html>
