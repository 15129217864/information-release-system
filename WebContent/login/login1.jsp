<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<title>蔚来城信息发布系统</title>
	<link href="/login/f/index.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<style type="text/css">
			<!--
			body {
				background-color: #FFFFFF;
			}
			-->
	   </style>
	<script language="javascript" type="text/JavaScript">
		var checkSubmitFlg = false; 
		
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
		  checkSubmitFlg = true; 
		  myform.submit();
		} 
		function goKeypress(){
			if (event.keyCode == 13)
				myform.submit();
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
		//alert(getHeader());
	</script>
	</head>
	<body bgcolor="#ffffff">
		<div id="all">
		<div id="middle">
			<div id="form">
				<form action="/rq/login" method="POST" name="myform">
					<div id="form1">
						<font id="font">
							用户名：<input type="text" name="lg_name" onkeypress="goKeypress();"style="width:140px;height:18px;"  /><br />
							密&nbsp;&nbsp;码：<input type="password" name="lg_password" onkeypress="goKeypress();" style="width:140px;height:18px;margin-top:3px;"  />
						</font>					</div>
				  <div id="form2">
				  	<a href="javascript:;" onclick="login();return false"><img src="/login/f/lonin.gif" border="0"/></a>
				  </div>
				</form>
			</div>
		</div>
	</div>
	</body>


</html>
<c:if test="${requestScope.loginstate=='loginerror'}">
	<script>alert('${requestScope.logininfo}');</script>
</c:if>