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
		<title>���ͽ�Ŀ</title><script language="javascript" src="/js/vcommon.js"></script>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script type="text/javascript">
        var xmlHttp;                        //���ڱ���XMLHttpRequest�����ȫ�ֱ���
        var refreshTime = 1000*2;         //�Զ�ˢ��ʱ������ĿǰΪ1��
        var isRefreshing = false;           //�Ƿ����Զ�ˢ�¹�����
        //���ڴ���XMLHttpRequest����
		function createXmlHttp() {
		    //����window.XMLHttpRequest�����Ƿ����ʹ�ò�ͬ�Ĵ�����ʽ
		    if (window.XMLHttpRequest) {
		       xmlHttp = new XMLHttpRequest();                  //FireFox��Opera�������֧�ֵĴ�����ʽ
		    } else {
		       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");//IE�����֧�ֵĴ�����ʽ
		    }
		}
		
		var i=0;
		//��ȡ������Ϣ
		function getNewDownloadStatus() {
		    
		   //�������ˢ�¹����У�ֱ�ӷ��أ�ȡ������
		    if (isRefreshing) {
		        return;
		    }
		    isRefreshing = true; 
		    createXmlHttp();                                //����XMLHttpRequest����
		    xmlHttp.onreadystatechange = writeDownloadStatus;        //���ûص�����
		    xmlHttp.open("GET", "/rq/downloadstatus?timestamp=" + new Date().getTime()+"&catchcount="+(i++), true);  //����GET����
		    xmlHttp.send(null);
		}
		
		//var count=0;
		
		//������״̬д��ҳ��
		function writeDownloadStatus() {
		     
		    if (xmlHttp.readyState == 4) {
		        isRefreshing = false;      
		        //����õ�״̬����д��ҳ��
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
		
       function sendclient(){//���ͽ�Ŀ
       
       
          timer= setInterval("getNewDownloadStatus()", refreshTime);
          document.getElementById("sendclient").disabled="disabled";
          
       }
       
       function cannel(){//ȡ������
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
							<span style="font-weight: bold; ">��Ŀ���ƣ�</span> ${playname }
						</td>
						<td style="font-size: 13px;">
							<span style="font-weight: bold">����ʱ����</span>  ${timecount }&nbsp;����
						</td>
						<c:if test="${playtype ==2}">
							<td style="font-size: 13px;">
								<span style="font-weight: bold">��Ŀ�������ͣ�</span>  ��ʱ
							</td>
						</c:if>
						<c:if test="${playtype ==1}">
							<td style="font-size: 13px;">
								<span style="font-weight: bold">��Ŀ�������ͣ�</span> �岥
							</td>
						</c:if>
						<c:if test="${playtype ==0}">
							<td style="font-size: 13px;">
								<span style="font-weight: bold">��Ŀ�������ͣ�</span> ��ʱѭ��
							</td>
						</c:if>
						<c:if test="${playtype ==3}">
							<td style="font-size: 13px;">
								<span style="font-weight: bold">��Ŀ�������ͣ�</span> ����ѭ��
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
							<span style="font-weight: bold">����ʱ�䣺</span>  ${startdate } - ${enddate }
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
											�ն�����
										</td>
										<td style="font-weight: bold">
											�ն�IP
										</td>
										<td style="font-weight: bold">
											����״̬
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
													�ȴ�����
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
									value="  �� ��  " />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" onclick="sendclient();" class="button"
									id="sendclient" value="���͵��ն�" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" onclick="cannel();" class="button"
									value=" ���·��� " />
							</div>
						</td>
					</tr>
				</table>
			</fieldset>
		</center>
	</body>
</html>
