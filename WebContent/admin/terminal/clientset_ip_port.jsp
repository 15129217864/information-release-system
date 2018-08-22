<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'clientset_ip_port.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="/css/style.css" type="text/css" />
	<script language="javascript" src="/js/jquery1.4.2.js"></script>
    <script type="text/javascript">
	      function isIP(strIP) {
	      
		    var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
		    var reg = strIP.match(exp);
		    if(reg==null){
		    
		      return false;
		    }else{
		        return true;
		    }
	     }
    
		 function sendpz(){
			var  serviceip=$("#serviceip").val();
			var  serviceport=$("#serviceport").val();
			var  clientip=$("#clientip").val();
		
			if(serviceip==""){
				alert("提示信息：请输入服务端IP！！");
				return ;
			}
			if(!isIP(serviceip)){
				alert("提示信息：请输入正确的服务端IP！！");
				return ;
			}
			if(serviceport==""){
				alert("提示信息：请输入服务端WEB端口！！");
				return ;
			}
			if(clientip==""){
				alert("提示信息：请输入终端端IP！！");
				return ;
			}
			if(!isIP(clientip)){
				alert("提示信息：请输入正确的终端IP！！");
				return ;
			}
			var param="serviceip="+serviceip+"&serviceport="+serviceport+"&clientip="+clientip;
            var msg = "您确定要发送吗？";
			if (confirm(msg)==true){
				$.ajax({
				    type: "POST",
				    url:  "/rq/setiport",
				    dataType:"text",
					data:param,
				    success: function (data){
				      //alert(data);
				      if(data=="OK"){
				        alert("发送成功")
				      }else{
				        alert("发送失败")
				      }
				    },
                    beforeSend: function() {}, //请求发出前的处理函数,参数(XMLHttpRequest对象,返回状态)
				    error:function(XmlHttpRequest,textStatus,errorThrown){  
						            var $errorPage = XmlHttpRequest.responseText;  
									if($errorPage!='')
						              alert($($errorPage).text());  
						          },
				    complete: function (XHR, TS) { XHR = null } //释放ajax资源\
				});
		   }
	    }
	</script>
  </head>
  <body>
      <fieldset style="width:476px; height:286px;border:#6699cc 1 solid;">
			<legend align="center" style="font-size: 13px; color: #1C8EF6; font-weight: bold">配置终端(仅限局域网)</legend>
	  		<form action="" name="form1" method="post">
				   <table cellpadding="1" cellspacing="1" width="420" border="0" >
					   <tr bgcolor="#F5F9FD">
					   	   <td align="left"  width="100%" height="55px">
					   	    服务端IP地址：&nbsp;
					   	   </td>
					   	   <td><input type="text" size="3" name="serviceip" id="serviceip" style="width:300px" value="" />&nbsp;</td>
					   	</tr>
					   	<tr bgcolor="#F5F9FD">
					   	   <td  align="left"  width="100%" height="55px">
					   	    服务端WEB端口：&nbsp;
					   	  </td>
					   	     <td><input type="text" size="3" name="serviceport" id="serviceport" value="80" /></td>
					   </tr>		   
					   <tr  bgcolor="#F5F9FD">
						<td align="left" height="55px">
							 终端IP地址：&nbsp;
						</td>
							 <td><input type="text" size="3" name="clientip" id="clientip"  style="width:300px" value="255.255.255.255" />
						</td>
					  </tr>
					  <tr>
						<td align="center" colspan="2"  height="55px">
							 <font><font color='red'>255.255.255.255</font> 为广播发送。 也可填写IP点对点发送</font>
						</td>
					  </tr>
					  <tr>
						<td align="center" colspan="2" height="55px">
							 <input type="button"  name="send" value="发送到终端" class="button1" onclick="sendpz()"/>
						</td>
					  </tr>
				</table>
	   		</form>
         </fieldset>
  </body>
</html>
