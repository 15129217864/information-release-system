<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<html>
	<head>
		<title>Home Page</title>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<style type="text/css">
			#divframe{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2);  visibility:hidden}
			#mask{ position:absolute; top:0; left:0; width:expression(body.scrollWidth); height:expression(body.scrollHeight); background:#000000; filter:ALPHA(opacity=60); z-index:1; visibility:hidden}
			#massage{border:#6699cc solid; border-width:1 1 1 1; background:#fff; color:#036; font-size:12px; line-height:150%; visibility:hidden}
			.header{background:url(/images/device/btn_background.gif);  font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff;cursor: move;}
		</style>
		<script language="JavaScript" src="/js/prototype.js"></script>
		<script language="JavaScript" src="/js/did_common.js"></script>
		<script language="javascript" src="/js/vcommon.js"></script>
		<script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
				<style type="text/css">
		#ToolBar {
			position:absolute;
			bottom:0px;
			right:16px;
			width:100%;
			height:30px;
			padding-top:5px;
			text-align:center;
			background-color:#F5F9FD;
			z-index:2;
			overflow:hidden;
		}
		
		</style>
		<script language="JavaScript">
		
		//============================================
        var browertype="IE8.0";
        var Sys = {};
        var ua = navigator.userAgent.toLowerCase();
        var s;
        (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
        (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
        (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
        (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
        (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;

        //以下进行测试
        if (Sys.ie) browertype='IE:' + Sys.ie;
        if (Sys.firefox) browertype='Firefox:' + Sys.firefox;
        if (Sys.chrome) browertype='Chrome:' + Sys.chrome;
        if (Sys.opera) browertype='Opera:' + Sys.opera;
        if (Sys.safari) browertype='Safari:' + Sys.safari;

     //============================================
		
				var selBorderStyle = "border: #FF0000 2px solid";
				var unselBoderStyle = "border:#f5f9fd 2px solid";
				var cnt_link_ips="";
				var timer;
				function onLoad() {
					request(1);
				}
				//view
				function request(currentpage){
					if(currentpage==""){
					   currentpage=1;
					}
					cnt_link_ips="";
					//progressBarOpen();//loading...		
					var url = "/rq/monitoringAjax?left_cmd=${left_cmd}&zu_id=${zu_id}&terminalstr=${terminalstr}&maxpage=15&pagenum="+currentpage;
					AjaxRequest(url,"form1",requestCallback);
				}
				
				function AjaxRequest(url,p,callback) { 
					try{
					 	var params = Form.serialize($("form1"));
					    var myAjax = new Ajax.Request(url,
				                                    {
				                                        method: 'post', 
				                                        parameters: params, 										                                   
														onComplete: callback										
				                                     }
				                                );
					}catch(e){}
				}
				
				function requestCallback(xmlhttp){	
						del(); 
						var dom = xmlhttp.responseXML;		
						if(dom.getElementsByTagName('error').item(0) != null){
							 var error = dom.getElementsByTagName('error').item(0).text;	
							 var trNode = document.createElement( 'tr' );	
							 tdNode= document.createElement( 'td' );	
							 tdNode.align= "center";	
							  tdNode.height= "50px";					 
							 tdNode.innerHTML = error;
							trNode.appendChild(tdNode);
							document.getElementById('dataTable').children(0).appendChild(trNode);	
						}else{				
							show(dom);	
						}
				}
				
				function goListTop(val){
					parent.deviceTop.goList("LIST",val);
				}
				
				function previewCapture(dsn){
					var tsrc  = "/index.jsp";
					makeMainLayerCenter('111',600,400);
					bindSrc('divPopup',tsrc);
				}
				
				function show(dom){
				
							var size = 0;	
							if(dom.getElementsByTagName('size').item(0) != null){
								 size=dom.getElementsByTagName('size')[0].childNodes[0].nodeValue;	
							}
							document.getElementById("pre_num").value=dom.getElementsByTagName('pre_num')[0].childNodes[0].nodeValue;
							document.getElementById("next_num").value=dom.getElementsByTagName('next_num')[0].childNodes[0].nodeValue;
							document.getElementById("end_num").value=dom.getElementsByTagName('end_num')[0].childNodes[0].nodeValue;
							document.getElementById("current_show").innerHTML=dom.getElementsByTagName('current_num')[0].childNodes[0].nodeValue;
							var total_num=dom.getElementsByTagName('total_num')[0].childNodes[0].nodeValue;
							document.getElementById("total_num").innerHTML=total_num; 
							document.getElementById("current_num").value=dom.getElementsByTagName('current_num')[0].childNodes[0].nodeValue;
							document.getElementById("list_size").value = size;
							document.getElementById("cnt_sum_id").innerHTML = dom.getElementsByTagName('cnt_size')[0].childNodes[0].nodeValue;
							if(total_num>0){
								document.getElementById("page_id").style.display='block';
							}
							if(size > 0){
								var trNode;		
								var startIdx = 0
								var endIdx = 0;
								endIdx = size;
								
								for(var i=startIdx;i<endIdx;i++){	
								
									var gubun =i%3;	
									if(gubun==0){					 
									 	trNode = document.createElement( 'tr' );	
									}
									var tag = "";			
									tdNode= document.createElement( 'td' );		
									tdNode.align= "left";
									var elm = dom.getElementsByTagName('terminal_'+i).item(0);
									var terminal_name=elm.getElementsByTagName('terminal_name')[0].childNodes[0].nodeValue;
									var terminal_ip=elm.getElementsByTagName('terminal_ip')[0].childNodes[0].nodeValue;
									var terminal_mac=elm.getElementsByTagName('terminal_mac')[0].childNodes[0].nodeValue;					
									var terminal_status=elm.getElementsByTagName('terminal_status')[0].childNodes[0].nodeValue;					
									var terminal_programname=elm.getElementsByTagName('terminal_programname')[0].childNodes[0].nodeValue;
									var status = "";
									var image_url = "";		
									var onc_src="";
									if(terminal_status=='3'){
										image_url = "/images/PowerOff.gif";	
										status	="<span style='color:red'>断开</span>";
									}else{ 
										cnt_link_ips+="!"+terminal_ip+"_"+terminal_mac;
										if(terminal_programname=="NULL"||terminal_programname=="null"){
											status	="<span style='color:red'>休眠</span>";
											terminal_programname="无";
										}else{
											status	="<span style='color:green'>连接</span>";
											image_url = "/serverftp/program_file/screen/"+terminal_mac+".jpg?t="+ new Date().getTime();
											onc_src="onClick=\"showbigScreen('"+terminal_name+"','"+image_url+"');return false\" title=\"点击看大图\"" ;
										}
										//image_url = "/images/OffTheAir.gif";
									}
									tag=tag+"	      <div style=\"overflow:auto;\" id=\"overDiv\"> ";				
									tag=tag+"         <table width=\"268\" height=\"186\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
									tag=tag+"	<tr>";
									
									tag=tag+"	  <td><img src=\"/images/table_corner_01.gif\" width=\"2\" height=\"2\"></td>";
									tag=tag+"	  <td class=\"Table_top\"></td>";
									tag=tag+"	  <td><img src=\"/images/table_corner_02.gif\" width=\"2\" height=\"2\"></td>";
									tag=tag+"	</tr>";
									tag=tag+"	<tr>";
									tag=tag+"	  <td class=\"Table_left\"></td>";
									tag=tag+"	  <td width=\"100%\" align=\"center\">";
									tag=tag+"	  <table width=\"250\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
									tag=tag+"		<tr>";
									tag=tag+"		  <td>";
									tag=tag+"		  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"3\">";
									tag=tag+"			  <tr>";
									tag=tag+"				<td width=\"20\"><img src=\"/images/device/icon_device.gif\" width=\"15\"  ></td>";
									
									tag=tag+"				<td class=\"DeviceInfoTitle\"  height=\"25\"  title='查看终端详细信息'  onClick='javaScript:viewterminal(\""+terminal_name+"\",\""+terminal_mac+"\")' style='cursor:hand'>终端名称："+terminal_name+" </td>";
									tag=tag+"			  </tr>";
									
									tag=tag+"		  </table>";
									tag=tag+"		  </td>";
									tag=tag+"		</tr>";
									tag=tag+"		<tr>";
									tag=tag+"		  <td>";
									tag=tag+"		  <table width=\"100%\" cellpadding=\"3\" border=\"0\" cellspacing=\"1\" onClick=\"javaScript:onSel('"+"id_"+i+"','"+size+"');\" id=\"id_"+i+"\" style=border:#f5f9fd 2px solid\" >";
									tag=tag+"			  <tr>";
									tag=tag+"				<td>IP地址："+terminal_ip+"</td><td>"+status+"</td>";
									tag=tag+"			  </tr>";
													
									tag=tag+"			  <tr>";
									tag=tag+"				<td align=\"center\" colspan=\"2\">";
									tag=tag+"					<a href=\"javascript:;\" "+onc_src+" ><img src='"+image_url+"' height=\"95\"  onerror=\"javascript:this.src='/images/OffTheAir.gif'\" border=\"0\" /></a>";
									tag=tag+"				</td>";
									tag=tag+"			  </tr>";
									
									tag=tag+"			  <tr>";
									tag=tag+"				<td  colspan=\"2\">";
									tag=tag+"				<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
									tag=tag+"					<tr>";
									tag=tag+"					  <td width=\"25\"><img src=\"/images/device/icon_content.gif\" width=\"21\" height=\"14\"></td>";
									tag=tag+"					  <td>当前节目："+terminal_programname+"</td>";
									tag=tag+"					</tr>";
									tag=tag+"				</table>";
									tag=tag+"				</td>";
									tag=tag+"			  </tr>";
									tag=tag+"		  </table>";
									tag=tag+"		  </td>";
									tag=tag+"		</tr>";
									tag=tag+"	  </table>";
									tag=tag+"	  </td>";
									tag=tag+"	  <td class=\"Table_right\"></td>";
									tag=tag+"	</tr>";
									tag=tag+"	<tr>";
									tag=tag+"	  <td><img src=\"/images/table_corner_03.gif\" width=\"2\" height=\"2\"></td>";
									tag=tag+"	  <td class=\"Table_bottom\"></td>";
									tag=tag+"	  <td><img src=\"/images/table_corner_04.gif\" width=\"2\" height=\"2\"></td>";
									tag=tag+"	</tr>";
									tag=tag+"   </table>";
									tag=tag+"   </div>";
				
									tdNode.innerHTML = tag;
						            //alert(tag);
									trNode.appendChild(tdNode);
									if(gubun == 2 || i==(size-1)){
										if(browertype.indexOf("IE:")!=-1){
										  if(parseInt(browertype.split(":")[1])<10){
									 	    document.getElementById('dataTable').children(0).appendChild(trNode);
								 	      }else{
								 	        document.getElementById('dataTable').appendChild(trNode);
								 	      }	
									 	}else{
										    document.getElementById('dataTable').appendChild(trNode);
										}
									}
								}				
								if ((size%3) > 1){
									tdNode = document.createElement( 'td' );	
									tdNode.width = "260";
									trNode.appendChild(tdNode);
								}
						}else{				
								var trNode  = document.createElement( 'tr' );			
								var tdNode  = document.createElement( 'td' );	
								var tdNodeBs= document.createElement( 'td' );
								tdNodeBs.innerHTML = "&nbsp;";	
								tdNodeBs.width = "45%";
								tdNode.innerHTML   = '未找到数据。';	
								trNode.appendChild(tdNodeBs);
								trNode.appendChild(tdNode);		
								if(browertype.indexOf("IE:")!=-1){
									  if(parseInt(browertype.split(":")[1])<10){
								 	    document.getElementById('dataTable').children(0).appendChild(trNode);
							 	      }else{
							 	        document.getElementById('dataTable').appendChild(trNode);
							 	      }	
							 	}else{
								    document.getElementById('dataTable').appendChild(trNode);
								}
						}
						startTimer();
				}
				
				//select device
				function onSel(idnm, len){
					var startIdx = 0;
					var endIdx = len;
					var selTable = document.getElementById(idnm);
					//selTable.style.cssText="border: #336699 2px solid";
					selTable.style.cssText="border: #FF0000 2px solid;";
					for(var i=startIdx;i<endIdx;i++){	
					//	for(i=0;i<len;i++){
						if(idnm != "id_"+i){
							ot = document.getElementById("id_"+i);
							if(ot == null) break; 
							ot.style.cssText="border:#f5f9fd 2px solid;";
						}
					}
				}
				
				//VNC control
				function onVnc(){
					var seldevice = document.getElementById("sel_device").value;
					var selip = document.getElementById("sel_ip").value;
					if(seldevice==""){
						alert('ss');
						return "";
					}else if(selip==""){
						alert('ff');
						return "";
					}else{
						return selip;
					}
				}
				
				
				function startTimer() {	
					var current_num=document.getElementById("current_num").value;
					if(current_num==""){
						current_num=1;
					}
					timer=setTimeout("request("+current_num+")", 15671);
				}
				function nextpage(){
				   document.body.scrollTop = "0px";
					var next_num=document.getElementById("next_num").value;
					clearTimeout(timer);
					request(next_num);
				}
				function prepage(){
				    document.body.scrollTop = "0px";
					var pre_num=document.getElementById("pre_num").value;
					clearTimeout(timer);
					request(pre_num);
				}
				function endpage(){
				    document.body.scrollTop = "0px";
					var end_num=document.getElementById("end_num").value;
					clearTimeout(timer);
					request(end_num);
				}
				function onUpdateSoftware(checkIPs){
				   cntip="";
				   showDivFrame("终端软件升级","/admin/terminal/updateSoftware.jsp?timeStamp=" + new Date().getTime(),"480","300");
				}
				function onRestart(checkIPs){
				    cntip="";
				    showDivFrame("其他设置","/admin/terminal/restart.jsp?timeStamp=" + new Date().getTime(),"480","400");
				}
				function showbigScreen(cnt_name,filepath){
				    showDivFrame("终端【"+cnt_name+"】的截屏图片","/admin/terminal/view_screen.jsp?filepath="+filepath+"&timeStamp=" + new Date().getTime(),"700","500");
				}
				function onDown(checkIPs){
				   cntip="";
				   showDivFrame("终端休眠","/admin/terminal/ondown.jsp?timeStamp=" + new Date().getTime(),"480","300");
				}
				function onStartcnt(checkIPs){
				   cntip="";
				   showDivFrame("停止休眠","/admin/terminal/startcnt.jsp?timeStamp=" + new Date().getTime(),"480","300");
				}
				function sendNotice(checkIPs){
				    cntip="";
				    showDivFrame("发布文字通知","/admin/terminal/sendNotice.jsp?timeStamp=" + new Date().getTime(),"750","320");
				}
				function ledManager(){
				     cntip="";
				     showDivFrame("LED管理","/admin/terminal/ledManager.jsp?timeStamp=" + new Date().getTime(),"480","300");
				}
				function clearPrograme(checkIPs){
				     cntip="";
				     showDivFrame("节目初始化","/admin/terminal/clearproject.jsp?timeStamp=" + new Date().getTime(),"480","300");
				}
				function timeSynchronization(){
				    cntip="";
				    showDivFrame("时间同步","/admin/terminal/clientTimeSynchronization.jsp?timeStamp=" + new Date().getTime(),"480","300");
				}
				function updateDownloadStartEnd(){
				    cntip="";
				    showDivFrame("开关机设置","/admin/terminal/update_download_start_end.jsp?timeStamp=" + new Date().getTime(),"530","300");
				}
				function deleteClient(){
				    cntip="";
				    showDivFrame("删除终端","/admin/terminal/deleteClient.jsp?timeStamp=" + new Date().getTime(),"480","300");
				}
				
				var cntip;
				
				function guiCamera(cnt_title,cntIp,cntMac){
				    cntip=cntIp;
					document.getElementById("div_iframe").src="/loading.jsp";
					showDivFrame("终端【"+cnt_title+"】当前截屏图片","/admin/terminal/guiCamera.jsp?cntIp="+cntIp+"&cnt_mac="+cntMac+"&t=" + new Date().getTime(),"700","500");
				}
				function viewProjectMenu(programName,cntmac,cntIp){
				  cntip="";
				  showDivFrame("终端【"+cntIp.replace("!","")+"】节目单","/admin/terminal/viewProjectMenu.jsp?resips="+cntIp+"&cntmac="+cntmac+"&t=" + new Date().getTime(),"800","400");
				}
				function viewterminal(cname,cid){
				   cntip="";
				   showDivFrame("终端【"+cname+"】详细信息","/rq/viwe?cmd=viweOk&cid="+cid+"&t=" + new Date().getTime(),"600","300");
				}
				
				function showDivFrame(title,url,fwidth,fheight){
				
					parent.parent.parent.parent.document.body.scrollTop = "0px";
					parent.parent.parent.parent.document.getElementById("div_iframe1").width=fwidth;
					if(fheight!=""){
						parent.parent.parent.parent.document.getElementById("div_iframe1").height=fheight;
					}
					parent.parent.parent.parent.document.getElementById("divframe1").style.left=(parent.parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";;
					parent.parent.parent.parent.document.getElementById("divframe1").style.top=(parent.parent.parent.parent.document.body.clientHeight - fheight) / 3+"px";
					parent.parent.parent.parent.document.getElementById("div_iframe1").src=url;
					parent.parent.parent.parent.document.getElementById("titlename1").innerHTML=title;
					parent.parent.parent.parent.document.getElementById("divframe1").style.display='block';
					parent.parent.parent.parent.document.getElementById("massage1").style.display='block';
					parent.parent.parent.parent.document.getElementById("bgDiv").style.display='block';
					parent.parent.parent.parent.document.getElementById("bgDiv").style.visibility='visible';
		
		       }
				
				function SetWinHeight(obj){
					 var win=obj; 
					 if (document.getElementById) {
						  if (win && !window.opera){ 
							if (win.contentDocument && win.contentDocument.body.offsetHeight){  
							    win.height = win.contentDocument.body.offsetHeight;  
							}else if(win.Document && win.Document.body.scrollHeight){ 
						        win.height = win.Document.body.scrollHeight;
							}
						  } 
					 } 
				}
				var timer5;
				function onkeyscreen(){
					//alert(cnt_link_ips);
					DwrClass.onkeyscreen(cnt_link_ips);
					document.getElementById("onkeyscreen_id").style.display="none";
					timer5=setTimeout("showonkeyscreen()", 25000);
				}
				function showonkeyscreen(){
					document.getElementById("onkeyscreen_id").style.display="block";
					clearTimeout(timer5);
				}
				function closedivframe(){

					document.getElementById("div_iframe").src="/loading.jsp";
					document.getElementById("divframe").style.display='none';
					document.getElementById("divframe").style.visibility="hidden";
					onLoad();
				}
				function firstpage(){
				    document.body.scrollTop = "0px";
					clearTimeout(timer);
					request(1);
				}
				
				function del(){
	
					var tableObj = document.getElementById('dataTable');
					if(browertype.indexOf("IE:")!=-1){
						  if(parseInt(browertype.split(":")[1])<10){
						    if(tableObj.children[0]!=undefined){
								var nodeList =tableObj.children[0].childNodes;	 
							    var len = nodeList.length;
							    for(var i=0;i<len;i++){
								    tableObj.deleteRow(0);	
								}		
							}
						 }else{
							 var len = tableObj.childNodes.length;
						      for(var i=0;i<len;i++){  
							    tableObj.deleteRow(0);	
							 }
						}
				   }else{
						  var len = tableObj.childNodes.length;
					      for(var i=0;i<len;i++){      
						    tableObj.deleteRow(0);	
						 }
					}
				}

				
				var s =0;
				var myGlobalHandlers = {					
						onCreate: function(){
							if(s==0){	
								Element.show('systemWorking');
								Ajax.activeRequestCount++;
							}
							s=1;			
						},
					
						onComplete: function() {
							if(s==1){	
								Element.hide('systemWorking');
								Ajax.activeRequestCount--;
							}
						}
				};
				Ajax.Responders.register(myGlobalHandlers);
				
			window.onscroll = function () { 
				var div = document.getElementById("onkeyscreen_id"); 
				div.style.top = document.body.scrollTop; 
			}
			window.onresize = window.onscroll; 
</script>

	</head>
	
	<body onLoad="javascript:onLoad();">
	<div id="onkeyscreen_id" style="position:absolute; top:0px; left:0px; width:20px; height:59px; z-index:6; ">
	<a href="javascript:;" onclick="onkeyscreen();"><img src="/images/s.jpg" border="0" alt="一键截屏"/></a></div>
		<form name="form1" id="form1" method="post" action="">
			<input type="hidden" id="list_size" name="list_size">
			<input type="hidden" id="lstsize" name="lstsize">
			<input type="hidden" id="page" name="page" />
         <div style="overflow: auto;height: 100%;position:absolute;width: 100%">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="Line_01"></td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="10" cellpadding="10" id="dataTable"></table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="center" valign="top">
					</td>
				</tr>
			</table>
			<div style="height: 50px"></div>
			</div>
			<div id="ToolBar">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"  id="page_id" style="display: none;">
			<tr>
				<td align="center" valign="top"align="right">
				总共【<span style="color: blue" id="cnt_sum_id"></span>】台终端
					<input type="hidden" id="pre_num"/>
					<input type="hidden" id="next_num"/>
					<input type="hidden" id="end_num"/>
					<input type="hidden" id="current_num"/>
                  <a href="javascript:;" onclick="javascript:firstpage();"><img src="/images/btn_first.gif" border="0"/></a>&nbsp;
				 <a href="javascript:;" onclick="javascript:prepage();"><img src="/images/btn_pre.gif" border="0"/></a>
				 &nbsp; <span id="current_show"></span>&nbsp; 
                  <a href="javascript:;" onclick="javascript:nextpage();"><img src="/images/btn_next.gif" border="0"/></a>&nbsp; 
                  <a href="javascript:;" onclick="javascript:endpage();"><img src="/images/btn_end.gif" border="0"/></a>&nbsp;
                                         共<span id="total_num"></span>页 
				</td>
				<td width="100px">&nbsp;</td>
			</tr>
		</table>
		</div>
		</form>
		<div id="divframe">
			<div  id="massage">
					<table cellpadding="0" cellspacing="0" >
						<tr  height="30px;" class=header  onmousedown=MDown(divframe)><td align="left" style="font-weight: bold"><span id="titlename"></span></td>
							<td height="5px" align="right"><a href="javaScript:void(0);" style="color: #000000" onclick="closedivframe();">关闭</a></td></tr>
						<tr>
						<td colspan="2">
						   <iframe src="/loading.jsp" scrolling="no" id="div_iframe" onload="SetWinHeight(this)"  name="div_iframe" frameborder="0" ></iframe>
						</td>
						</tr>
					</table>
			</div>
		</div>
		
	</body>
</html>
