<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%> 
<html>
	<head>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="Pragma" content="no-cache" />
		<title>多媒体信息发布系统-用户登录</title>
		<script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<style type="text/css">
			<!--
			body {
				background-color: #FFFFFF;
			}
			-->
	   </style>
	<script src="/js/jsencrypt.min.js"></script>
	<script language="javascript" type="text/JavaScript">
		var checkSubmitFlg = false; 
		var publickey="${publickey}";
		function login() {
			if (checkSubmitFlg == true) { 
		　　 return; 
		　　 } 
		  if(myform.lg_name.value == "") {
		    alert('请输入登录用户名！');
		    return;
		  }
		  if(myform.lg_password.value == "") {
		    alert('请输入登录密码！');
		    return;
		  }
		   //================================================
		  var encrypt = new JSEncrypt();
   		  encrypt.setPublicKey(publickey);
		  myform.uid.value=encrypt.encrypt(myform.lg_name.value);
		  myform.upwd.value=encrypt.encrypt(myform.lg_password.value);
		  
		  var nameNode=document.getElementById("lg_name");
		  nameNode.parentNode.removeChild(nameNode);
		  var pwdNode=document.getElementById("lg_password");
		  pwdNode.parentNode.removeChild(pwdNode);
		  
		  //alert(myform.lg_password.value);
		  //=================================================
		   checkSubmitFlg = true; 
		  // alert(myform.broswerflag.value);
		   myform.submit();
		}
		
		function onsyskey(){
		  	keyvalue=myform.keyvalue.value;
		  	if(keyvalue==""){
		  		alert("请输入有效授权密钥！");
		  		returen ;
		  	}
			myform.action="/login/syskeyDO.jsp";
			myform.submit();
		 } 
		
		function goKeypress(){
			if (event.keyCode == 13){
				//alert(myform.broswerflag.value);
				//myform.submit();
				login();
			}
		}
		//-------------------------------------------------
		//加密
	    var	f23={}
		f23.ts="8ABC7DLO5MN6Z9EFGdeJfghijkHIVrstuvwWSTUXYabclmnopqKPQRxyz01234"
		f23.s52e=function(n){
			var nl=n.length,t=[],a,b,c,x,m=function(y){t[t.length]=f23.ts.charAt(y)},N=f23.ts.length,N2=N*N,N5=N*5
			for(x=0;x<nl;x++){
				a=n.charCodeAt(x)
				if(a<N5)m(Math.floor(a/N)),m(a%N)
				else m(Math.floor(a/N2)+5),m(Math.floor(a/N)%N),m(a%N)
			}
			var s=t.join("")
			return String(s.length).length+String(s.length)+s
		}
        //解密
		f23.s52d=function(n){
			var c=n.charAt(0)*1
			if(isNaN(c))return ""
			c=n.substr(1,c)*1
			if(isNaN(c))return ""
			var nl=n.length,t=[],a,f,b,x=String(c).length+1,m=function(y){return f23.ts.indexOf(n.charAt(y))},N=f23.ts.length
			if(nl!=x+c)return ""
			while(x<nl){
				a=m(x++)
				if(a<5)f=a*N+m(x)
				else f=(a-5)*N*N+m(x)*N+m(x+=1)
				t[t.length]=String.fromCharCode(f)
				x++
			}
			return t.join("")
		}
		
	   function getHeader(){
		   var fso,f,str;
		   var ForReading = 1, ForWriting = 2;
		   fso = new ActiveXObject("Scripting.FileSystemObject");
           //写文件
		   f = fso.OpenTextFile("c:\\mac", ForWriting, true);
		   f.Write(f23.s52e("1234567890987654321"));
		   f.Close();
		   
           //读文件
           if(fso.FileExists("c:\\mac")){
		      f = fso.OpenTextFile("c:\\mac", ForReading);
			  str=f23.s52d(f.ReadAll());
			  f.Close();
		      return str;
		   }else
		      return "no";
		}
		window.onload=function(){
		    var r='${regint}';
		    if(r==3){
		       myform.keyvalue.focus();
		    }else{
	    	   myform.lg_name.focus();
	    	}
		}
		//alert(getHeader());
	</script>
	</head>
	<body bgcolor="#ffffff">
		<form action="/rq/login" method="POST" name="myform">
			<table align="center" width="95%" height="95%" border="0"
				cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<table width="616" height="384" border="0"
							background="/images/login/menubg.jpg" align="center"
							cellpadding="0" cellspacing="0">
							<tr>
								<td height="60%"></td>
							</tr>
							<tr>
								<td height="40%" colspan="3">
								<c:choose>
									<c:when test="${regint==3}">
									<table width="98%" border="0" cellspacing="7" cellpadding="0">
										<tr>
											<td class="login-text" width="90px"></td>
											<td class="login-text">
												<img src="/images/login/bullet_01.gif" width="20" height="8">
												授权密钥：
											</td>
											<td>
												<input type="text" name="keyvalue" class="text_input_login"
													onkeypress="goKeypress();" size="42">
											</td>
											<td background="/images/login/btn_login.gif" width="68"
												height="22" align="center" onclick="onsyskey();"
												style="cursor:pointer;">
												<table width="100%" border="0">
													<tr>
														<td>
															&nbsp;
														</td>
														<td align="center">
															<span style="color:white;font-size:7pt;"> 注 册 </span>
														</td>
													</tr>
												</table>
											</td>
											<td width="68" height="22" align="center">

											</td>
										</tr>
									</table>
									</c:when>
									<c:otherwise>
									<table width="95%" border="0" cellspacing="7" cellpadding="0">
										<tr>
											<td class="login-text" width="90px"></td>
											<td class="login-text" width="90px" align="right">
												<img src="/images/login/bullet_01.gif" width="16" height="8">
												<font color="white">用户名：</font>
											</td>
											<td>
												<input type="text" name="lg_name" id="lg_name" class="text_input_login"
													 size="8">
											</td>
											<td class="login-text" width="90px"  align="right">
												<img src="/images/login/bullet_01.gif" width="16" height="8">
												<font color="white">密 码：</font>
											</td>
											<td>
												<input type="password" name="lg_password" id="lg_password"  autocomplete="off" 
													class="text_input_login" onkeypress="goKeypress();"
													size="8">
											</td>

											<td background="/images/login/btn_login.gif" width="68"
												height="22" align="center" onclick="login();"
												style="cursor:pointer;">
												<table width="100%" border="0">
													<tr>
														<td>
															&nbsp;
														</td>
														<td align="center">
															<span style="color:white;font-size:7pt;">登录</span>
														</td>
													</tr>
												</table>
											</td>
											<td width="68" height="22" align="center">
                                                 <input type="hidden" name="broswerflag" id="broswerflag" value='ie'/>
                                                 <input type="hidden" name="logintoken" id="logintoken" value='${logintoken}'/>
                                                 <input type="hidden" name="publickey" id="publickey" value='${publickey}'/>
                                                 <input type="hidden" name="uid" id="uid"/>
                                                 <input type="hidden" name="upwd" id="upwd"/>
											</td>
										</tr>
									</table>
									</c:otherwise>
								</c:choose>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>

</html>
<c:if test="${requestScope.loginstate=='loginerror'}">
	<script>alert('${requestScope.logininfo}'); window.location.href='/';</script>
</c:if>
<c:if test="${empty logintoken}">
	<script>alert('${requestScope.logininfo}'); window.location.href='/'; </script>
</c:if>
<c:if test="${regok=='ok'}">
<script>alert('${regresult}')</script>
</c:if>
<c:if test="${regok=='error'}">
<script>alert('${regresult}')</script>
</c:if>
    <script type="text/javascript">
    
        function isIE() { //ie?
			if (!!window.ActiveXObject || "ActiveXObject" in window)
				return true;
			else
				return false;
		}
    
        var Sys = {};
        var ua = navigator.userAgent.toLowerCase();
       // alert(ua);
       
        if (isIE()){
		    var temp="";
            if(ua.indexOf("msie")!=-1){
		       temp =ua.match(/msie ([\d.]+)/)[1];
            }
			if(temp==""){//IE11判断
			   temp=ua.match(/rv:([\d.]+)/)[1];
            }
            Sys.ie = temp;
            
        }else if (ua.indexOf("firefox")>0/*||document.getBoxObjectFor*/)
            Sys.firefox = ua.match(/firefox\/([\d.]+)/)[1]
       // else if (window.MessageEvent && !document.getBoxObjectFor)
        else if (ua.indexOf("chrome")>0/*window.MessageEvent && !document.getBoxObjectFor*/)
            Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1]
        else if (ua.indexOf("safari")>0/*||window.openDatabase*/)
            Sys.safari = ua.match(/version\/([\d.]+)/)[1];
        else if (ua.indexOf("safari")>0/*window.opera*/)
            Sys.opera = ua.match(/opera.([\d.]+)/)[1];
        

        if(Sys.ie && parseInt(Sys.ie)<parseInt('7.0')){
        	myform.broswerflag.value='ie';
        	alert('您使用的浏览器是IE: '+Sys.ie+'，为了更好的体验本系统，请使用 IE10 及以上版本访问本系统');
        }
        if(Sys.firefox){
        	myform.broswerflag.value='firefox';
        	alert('您使用的浏览器是Firefox: '+Sys.firefox+'，为了更好的体验本系统，请使用 IE10 及以上版本访问本系统');
        }
        if(Sys.chrome){
        	myform.broswerflag.value='chrome';
        	alert('您使用的浏览器是Chrome: '+Sys.chrome+'，为了更好的体验本系统，请使用 IE10 及以上版本访问本系统');
        }
        if(Sys.safari){
        	myform.broswerflag.value='safari';
        	alert('您使用的浏览器是Safari: '+Sys.safari+'，为了更好的体验本系统，请使用 IE10 及以上版本访问本系统');
        }
        if(Sys.opera){
        	myform.broswerflag.value='opera';
        	alert('您使用的浏览器是Opera: '+Sys.opera+'，为了更好的体验本系统，请使用 IE10 及以上版本访问本系统');
        }
         
    </script>