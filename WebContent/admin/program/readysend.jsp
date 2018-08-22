<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<title>发送节目</title><script language="javascript" src="/js/vcommon.js"></script>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script type="text/javascript">
        var xmlHttp;                        //用于保存XMLHttpRequest对象的全局变量
        var refreshTime = 1000*2;         //自动刷新时间间隔，目前为1秒
        var isRefreshing = false;           //是否处于自动刷新过程中
        //用于创建XMLHttpRequest对象
		function createXmlHttp() {
		    //根据window.XMLHttpRequest对象是否存在使用不同的创建方式
		    if (window.XMLHttpRequest) {
		       xmlHttp = new XMLHttpRequest();                  //FireFox、Opera等浏览器支持的创建方式
		    } else {
		       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");//IE浏览器支持的创建方式
		    }
		}
		
		var i=0;
		//获取下载信息
		function getNewDownloadStatus() {
		    
		   //如果已在刷新过程中，直接返回，取消操作
		    if (isRefreshing) {
		        return;
		    }
		    isRefreshing = true; 
		    createXmlHttp();                                //创建XMLHttpRequest对象
		    xmlHttp.onreadystatechange = writeDownloadStatus;        //设置回调函数
		    xmlHttp.open("GET", "/rq/downloadstatus?timestamp=" + new Date().getTime()+"&catchcount="+(i++), true);  //发送GET请求
		    xmlHttp.send(null);
		}
		
		//var count=0;
		
		//将最新状态写入页面
		function writeDownloadStatus() {
		     
		    if (xmlHttp.readyState == 4) {
		        isRefreshing = false;      
		        //将获得的状态遍历写入页面
		        var responseresult=xmlHttp.responseText
		        var result = eval("(" + responseresult + ")");
		        for (var download in result) {
		            //++count;
		            document.getElementById(download).innerHTML = result[download];
		            //if(count==${ipcount}){
			        //   clearInterval(timer);
			        //   count=0;
			       // }	
		        }
		    }
		}
		
		var timer=null;
		
       function sendclient(){//发送节目
       
       
          timer= setInterval("getNewDownloadStatus()", refreshTime);
          document.getElementById("sendclient").disabled="disabled";
          
       }
       
       function cannel(){//取消发送
          clearInterval(timer);
          i=0;
          timer= setInterval("getNewDownloadStatus()", refreshTime);
       }
       function onllload(){
       	parent.listtop.location.href="/admin/program/programtop2.jsp";
       }
       function goback(){
       window.location.href="/admin/program/auditprogramList.jsp";
       parent.listtop.location.href="/admin/program/program_list_top3.jsp";
       		
       }
     
     </script>
	</head>
	<body style="margin-top: 30px" onload="onllload();">
		<center>
			<fieldset style="width:90%; margin-top: 30px;">
				<table border="0" width="100%" align="center">
					<tr>
						<td style="font-size: 13px;">
							<span style="font-weight: bold; ">节目名称：</span> ${playname }
						</td>
						<td style="font-size: 13px;">
							<span style="font-weight: bold">播放时长：</span>  ${timecount }&nbsp;分钟
						</td>
						<c:if test="${playtype ==2}">
							<td style="font-size: 13px;">
								<span style="font-weight: bold">节目播放类型：</span>  定时
							</td>
						</c:if>
						<c:if test="${playtype ==1}">
							<td style="font-size: 13px;">
								<span style="font-weight: bold">节目播放类型：</span> 插播
							</td>
						</c:if>
						<c:if test="${playtype ==0}">
							<td style="font-size: 13px;">
								<span style="font-weight: bold">节目播放类型：</span> 定时循环
							</td>
						</c:if>
						<c:if test="${playtype ==3}">
							<td style="font-size: 13px;">
								<span style="font-weight: bold">节目播放类型：</span> 永久循环
							</td>
						</c:if>
					</tr>
					<tr>
						<td colspan="3">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="3" style="font-size: 13px;">
							<span style="font-weight: bold">播放时间：</span>  ${startdate } - ${enddate }
						</td>
					</tr>
					<tr>
						<td colspan="3" >
							&nbsp;
							<div style="height:250px;overflow:auto;">
								<table border="0" width="100%" cellpadding="0" cellspacing="0"
									>
									<tr align="center"  class="TitleBackground">
										<td height="30px" style="font-weight: bold">
											终端名称
										</td>
										<td style="font-weight: bold">
											终端IP
										</td>
										<td style="font-weight: bold">
											下载状态
										</td>
									</tr>
									<c:if test="${!empty allip}">
										<c:set var="i" value="0"></c:set>
										<c:set var="classnm"></c:set>

										<c:forEach items="${allip}" var="client">
											<c:set var="i" value="${i+1}"></c:set>
											<c:choose>
												<c:when test="${i%2==0}">
													<c:set var="classnm" value="TableTrBg06"></c:set>
												</c:when>
												<c:otherwise>
													<c:set var="classnm" value="TableTrBg05"></c:set>
												</c:otherwise>
											</c:choose>
											<tr align="center" class="${classnm }">
												<td  height="20px">
													${client.cntname }
												</td>
												<td>
													${client.cntip }
												</td>
												<td id="${client.cntip }">
													等待发送
												</td>
											</tr>
											
											          <tr>
											            <td class="Line_01" colspan="3"></td>
											          </tr>
											
										</c:forEach>
									</c:if>
								</table>
							</div>
							<div align="center">
							<input type="button" onclick="goback();" class="button"
									value="  返 回  " />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" onclick="sendclient();" class="button"
									id="sendclient" value="发送到终端" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" onclick="cannel();" class="button"
									value=" 重新发送 " />
							</div>
						</td>
					</tr>
				</table>
			</fieldset>
		</center>
	</body>
</html>
