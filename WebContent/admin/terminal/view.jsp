<%@ page language="java" contentType="text/html; charset=gbk"  
pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<title>view.jsp</title>
   <style type="text/css">   
      .box{font:9pt "宋体"; position: absolute;background:#EAEEF4}   
      .a3{width:30;border:1;text-align:center}
      input{border:#006600 1px solid; background-color:#EAEEF4}
	  button{background-color:#EAEEF4}
   </style>   
   <link rel="stylesheet" href="/css/style.css" type="text/css" />
   <script type="text/javascript" src="/js/jquery1.4.2.js"></script>
   <script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
   <script type='text/javascript' src='/dwr/engine.js'></script>
   <script type='text/javascript' src='/dwr/util.js'></script>
   <script language="javascript" src="/js/vcommon.js"></script>
   <script language="JavaScript"> 
    
	   function showdiv(){
			var menu = document.getElementById("itemopen"); 
			var mmm =document.getElementById("mmm");
			
			var newX   =   window.event.x +mmm.scrollLeft;   
			var newY   =   window.event.y +mmm.scrollTop;   
			if(mmm.style.display =="none"){  
			   mmm.style.display ="block";
			}
			if(menu.style.display =="none"){  
			   menu.style.display ="block";
			 }
			menu.style.pixelLeft =newX;  
			menu.style.pixelTop=newY;
	   }
	   
	   function closediv(){
	     	var menu =document.getElementById("itemopen");
	     	var mmm =document.getElementById("mmm"); 
	     	if(mmm.style.display =="block")
			   mmm.style.display ="none";
			if(menu.style.display ="block")  
			  menu.style.display ="none";
	   }
	  
	  	var nextip=0;
		function mask(obj){
			obj.value=obj.value.replace(/[^\d]/g,'')
			key1=event.keyCode
			if (key1==37 || key1==39){
				obj.blur();
				nextip=parseInt(obj.name.substr(2))
				nextip=key1==37?nextip-1:nextip+1;
				//nextip=nextip>=5?1:nextip
				//nextip=nextip<=0?4:nextip
	            if(nextip>12)
				  nextip=1;
				//eval("ip"+nextip+".focus()");
				document.getElementById("ip"+nextip).focus();
			} 
	
			if(obj.value.length>=3){ 
				if(parseInt(obj.value)>=256 || parseInt(obj.value)<=0){
				
					alert(parseInt(obj.value)+"IP地址错误！")
					obj.value=""
					obj.focus()
					return false;
				}else{
					obj.blur();
					nextip=parseInt(obj.name.substr(2))+1;
					//nextip=nextip>=5?1:nextip
					//nextip=nextip<=0?4:nextip
					 if(nextip>12)
				        nextip=1;
					document.getElementById("ip"+nextip).focus();
				}
			}
		}
	
		function mask_c(obj){//粘帖操作
		   clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))
		}
	
		function updateclinetip(){
		
			  var strtemp="";
			  var ipstring="";
			  var ipbool=false;
			  var inputdom=document.getElementById("itemopen");
			  var textcount=inputdom.getElementsByTagName("input");
			  //alert(textcount.length);
			  
			  for(i=0;i<textcount.length-3;i++){//textcount.length-3 --->把一个隐藏域和2个按钮去掉
					ipstring=document.getElementById("ip"+(i+1)).value;
					if(ipstring==""){
					   ipbool=true;
					}
					strtemp+=ipstring+"."
					if(i==3||i==7)
					  strtemp=strtemp.substr(0,strtemp.length-1)+"!";
			  }
	
			  if(ipbool)
				alert("IP地址、子网掩码、默认网关 每一项都不能为空！");
	          else{
	             var ipaddress=updateclientform.ipaddresses.value;
	             updateclientform.action= "/rq/updateclientip?opp=xu0022&ipaddress="+ipaddress+"+&ips="+strtemp.substr(0,strtemp.length-1);
	             updateclientform.submit();
			     alert("终端正在修改IP地址...");
		         parent.closedivframe();
	          }
		}
		function updateclinetinfo(cntmac){
			window.location.href="/admin/terminal/updateClient.jsp?cntmac="+cntmac;
		}
		
		function change(obj,macstr){
		  if(obj.checked){
		   // alert(obj.value+"___"+macstr);
		     DwrClass.changeDayDownLoad(obj.value,macstr,backstirng);
		  }else{
		     DwrClass.changeDayDownLoad("0",macstr,backstirng);
		  }
		}
		function backstirng(data){
		    //alert(data);
		}
  </script>
</head>
<body style="margin-top: 2px" >

		 <table width="100%" height="300"  border="0" cellpadding="0" cellspacing="5" >
			<tr>
			  <td width="25%" align="center"><table width="215" height="24"  border="0" cellpadding="0" cellspacing="0">
				<tr>
				  <td background="/images/device/device_title_back.gif" class="DeviceTitle">名称：${terminal.cnt_name }</td>
				</tr>
			  </table></td>
			  <td rowspan="2" valign="top">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				  <tr>
					<td><!---------------------------------------------  GeneralInfo Start    ------------------------------------------>
						<table width="100%"  border="0" cellspacing="0" cellpadding="0">
						  <tr  class="TitleBackground">
							<td class="InfoTitle2">终端IP</td>							
							<td class="InfoTitle2">MAC地址</td>
							<td class="InfoTitle2">连接状态</td>
						  </tr>
						  <tr>
							<td colspan="9" class="Line_01"></td>
						  </tr>
						  <tr class="TableTrBg07">
						    <td>${terminal.cnt_ip}<%--&nbsp;&nbsp;&nbsp;
						    <c:if test="${terminal.cnt_islink!=3}">
						    <img src="/images/updateip.gif" alt="修改IP地址"  onclick="javaScript:showdiv()" style="cursor: pointer"/>
						    </c:if>
						    --%></td>						    
							<td>${terminal.cnt_mac}</td>
							<td>${terminal.cnt_islink_zh}</td>
						  </tr>
						  <tr>
							<td colspan="9" height="20"></td>
						  </tr>
						  <tr>
							<td colspan="9" class="Line_01" ></td>
						  </tr>
						  
						  <tr  class="TableTrBg07">
							<td  colspan="3" class="InfoTitle2">终端描述：&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: green;margin-left: 100px">白天下载:&nbsp;&nbsp;<input type="checkbox" name="day_download" onclick="change(this,'${terminal.cnt_mac}');" value="1" ${terminal.is_day_download eq '1'?'checked':''}/></span></td>
							<td colspan="8"   align="left">${terminal.cnt_miaoshu }</td>
						  </tr>
						  <tr>
							<td colspan="9" class="Line_01"></td>
						  </tr>
						</table>
						<!---------------------------------------------  GeneralInfo Start    ------------------------------------------>
					</td>
				  </tr>
				</table>
		        </td>
			</tr>
		<tr valign="top">
		<td height="100%"  align="center" >
               <table width="215" height="106" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><img src="/images/table_corner_01.gif" width="2" height="2"></td>
                  <td class="Table_top"></td>
                  <td><img src="/images/table_corner_02.gif" width="2" height="2"></td>
                </tr>
                <tr>
                  <td class="Table_left"></td>
                  <td width="264" align="center"><table width="200" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td align="center" class="InfoTableTrBg02"><table border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="25"><img src="/images/device/icon_content.gif" width="21" height="14"></td>
                            <td>当前节目：${terminal.cnt_nowProgramName=='null'?"无":terminal.cnt_nowProgramName }</td>
                          </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td><table width="100%" border="0" cellpadding="3" cellspacing="1" >
                          <tr>
                            <td align="center">
							 <table width="104" height="104" border="0" cellpadding="0" cellspacing="2" background="/images/device/device_background.gif">
                                <tr>
                               <c:set var="image_url"></c:set>
                                <c:choose>
                                	<c:when test="${terminal.cnt_islink==1 or terminal.cnt_islink==2 }">
                                		<c:set var="image_url" value="/images/OffTheAir.gif"></c:set>
                                	</c:when>
                                	<c:otherwise>
                                		<c:set var="image_url" value="/images/PowerOff.gif"></c:set>
                                	</c:otherwise>
                                </c:choose>
                                  <td background="${image_url }" width="97" height="97" >
                                  <table border=0 height="100%" width="100%">
                                  <tr><td valign="bottom" align="center">
                                  ${terminal.cnt_islink_zh}
                                  
                                  </td></tr>
                                  </table>
                                  </td>
                               
                                 </tr>
                            </table></td>
                          </tr>
                          
                      </table></td>
                    </tr>
                  </table></td>
                  <td class="Table_right"></td>
                </tr>
                <tr>
                  <td><img src="/images/table_corner_03.gif" width="2" height="2"></td>
                  <td class="Table_bottom"></td>
                  <td><img src="/images/table_corner_04.gif" width="2" height="2"></td>
                </tr>
              </table>
			 </td>
		   </tr>
		   <tr>
		   	<td colspan="2" height="100" valign="top">
					<table cellpadding="0" cellspacing="0" width="100%">
					<tr  class="TitleBackground">
							<td class="InfoTitle2" height="30px">播放状态</td>
							<td class="InfoTitle2">开机时间</td>
							  <td class="InfoTitle2">关机时间</td>
							  <td class="InfoTitle2">下载时间</td>
							  <td class="InfoTitle2">注册时间</td>
					</tr>
					<tr>
							<td colspan="9" class="Line_01"></td>
						  </tr>
					<tr class="TableTrBg07">
					<td>${terminal.cnt_playstyle_zh }</td>
							 <td>${terminal.cnt_kjtime }</td>
						  <td>${terminal.cnt_gjtime }</td>
						  <td>${terminal.cnt_downtime }</td>
						  <td>${terminal.cnt_addtime }</td>
						  </tr>
						  <tr>
							<td colspan="9" class="Line_01"></td>
						  </tr>
						  <tr class="TableTrBg07">
					<td colspan="6"align="center" height="50px">
					 <c:if test="${not empty lg_authority}">
		                <c:set var="str" value="${lg_authority}"/>
		                <c:if test="${fn:contains(str,'J')}">
					       <input type="button"  class="button1" onclick="updateclinetinfo('${terminal.cnt_mac}');" value=" 修 改 "/>
					    </c:if>
					</c:if>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="reset" class="button1" value=" 关 闭 "
						onclick="parent.closedivframe(1);"></td>
						  </tr>
						
					</table>

				</td>
		   </tr>
		</table>
						<div id="mmm" style="display:none;">
							   <div id="itemopen" align="center" class="box" style="width:250px;display:none;padding-top:10px;border:#6699cc solid; border-width:1 1 1 1;">
								    <form action="" method="post" name="updateclientform">
								         <div style="float:left;padding: 4px" >IP&nbsp;&nbsp;&nbsp;&nbsp;地址：</div> 
									     <div style="border-width:1;border-color:balck;border-style:solid;width:145;font-size:9pt">
									        <input type="hidden" value="${terminal.cnt_ip}" name="ipaddresses"/>
										    <input type="text" name="ip1" id="ip1" maxlength="3" class="a3" onkeyup="mask(this)" onbeforepaste="mask_c()"/>.
											<input type="text" name="ip2" id="ip2" maxlength="3" class="a3" onkeyup="mask(this)" onbeforepaste="mask_c()"/>.
											<input type="text" name="ip3" id="ip3" maxlength="3" class="a3" onkeyup="mask(this)" onbeforepaste="mask_c()"/>.
											<input type="text" name="ip4" id="ip4" maxlength="3" class="a3" onkeyup="mask(this)" onbeforepaste="mask_c()"/>
									     </div>
									     <br/>
									     <div style="float:left;padding: 4px" >子网掩码：</div> 
										 <div style="border-width:1;border-color:balck;border-style:solid;width:145;font-size:9pt">
											    <input type="text" name="ip5" id="ip5" maxlength="3" class="a3" onkeyup="mask(this)" onbeforepaste="mask_c()"/><big>.</big>
												<input type="text" name="ip6" id="ip6" maxlength="3" class="a3" onkeyup="mask(this)" onbeforepaste="mask_c()"/><big>.</big>
												<input type="text" name="ip7" id="ip7" maxlength="3" class="a3" onkeyup="mask(this)" onbeforepaste="mask_c()"/><big>.</big>
												<input type="text" name="ip8" id="ip8" maxlength="3" class="a3" onkeyup="mask(this)" onbeforepaste="mask_c()"/>
										 </div><br/>
										  <div style="float:left;padding: 4px" >默认网关：</div> 
										 <div style="border-width:1;border-color:balck;border-style:solid;width:145;font-size:9pt">
										    <input type="text" name="ip9" id="ip9" maxlength="3" class="a3" onkeyup="mask(this)" onbeforepaste="mask_c()"/><big>.</big>
											<input type="text" name="ip10" id="ip10" maxlength="3" class="a3" onkeyup="mask(this)" onbeforepaste="mask_c()"/><big>.</big>
											<input type="text" name="ip11" id="ip11" maxlength="3" class="a3" onkeyup="mask(this)" onbeforepaste="mask_c()"/><big>.</big>
											<input type="text" name="ip12" id="ip12" maxlength="3" class="a3" onkeyup="mask(this)" onbeforepaste="mask_c()"/>
										 </div>
										 <br/>
										<div align="center"><input type="button"  class="button1" onclick="updateclinetip();" value=" 修 改 "/>
										&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  class="button1" onclick="closediv();" value=" 关 闭 "/></div>	
								    </form>
							  </div>
					</div>
</body>
</html>
