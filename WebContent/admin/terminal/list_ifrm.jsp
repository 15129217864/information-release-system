<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
	
<html>
	<head>
		<title>Home Page</title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script language="JavaScript" src="/js/prototype.js"></script>
		<script language="JavaScript" src="/js/did_common.js"></script>
		<script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<style type="text/css">
		#ToolBar {
			position:absolute;
			bottom:0px;
			right:0px;
			width:99%;
			height:35px;
			padding-top:5px;
			text-align:center;
			background-color:#F5F9FD;
			z-index:2;
			overflow:hidden;
			border: 0px solid red;
			display:none;
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
	var timer;
	function onLoad() {		
		request(1);
	}
	function request(currentpage){
		if(currentpage==""){
		   currentpage=1;
		}
		//progressBarOpen();
		
		var url = "/rq/monitoringAjax?left_cmd=${left_cmd}&zu_id=${zu_id}&terminalstr=${terminalstr}&maxpage=50&pagenum="+currentpage;
		AjaxRequest(url,"form1",requestCallback);
	}
	
	function AjaxRequest(url,p,callback) { 
		try{
		 	var params = Form.serialize($("form1"));// 异常： Uncaught TypeError: Cannot call method 'getElementsByTagName' of null
		 	//alert($(p));
		    var myAjax = new Ajax.Request(url,
		                                    {
		                                        method: 'post',
		                                        parameters: params,
												onComplete: callback
		                                     }
		                                );
		}catch(e){alert(e)}
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
			 //alert("error--------------->"+error);
			trNode.appendChild(tdNode);
			document.getElementById('dataTable').children(0).appendChild(trNode);	
		}else{		
			show(dom);	
		}
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
				document.getElementById("ToolBar").style.display='block';
			}
			   
			if(size > 0){
				var trNode;		
				var startIdx = 0
				var endIdx = 0;
				endIdx = size;
				for(var i=startIdx;i<endIdx;i++){
					trNode = document.createElement( 'tr' );	
					var tag = "";			
					tdNode= document.createElement( 'td' );		
					tdNode.align= "left";
					var elm = dom.getElementsByTagName('terminal_'+i).item(0);

					var terminal_name=elm.getElementsByTagName('terminal_name')[0].childNodes[0].nodeValue;
					var terminal_ip=elm.getElementsByTagName('terminal_ip')[0].childNodes[0].nodeValue;
					
					var terminal_programconfig=elm.getElementsByTagName('terminal_programconfig')[0].childNodes[0].nodeValue;
					var version=terminal_programconfig;
	
					terminal_programconfig=((terminal_programconfig.indexOf("android")!=-1)?"/xctConfig":"");

					var httport=elm.getElementsByTagName('terminal_httport')[0].childNodes[0].nodeValue;
					httport=httport==""?":80":httport;

					var terminal_mac=elm.getElementsByTagName('terminal_mac')[0].childNodes[0].nodeValue;
					
					var terminal_zuname=elm.getElementsByTagName('terminal_zuname')[0].childNodes[0].nodeValue;
									
					
					var terminal_status=elm.getElementsByTagName('terminal_status')[0].childNodes[0].nodeValue;	
					var terminal_programname=elm.getElementsByTagName('terminal_programname')[0].childNodes[0].nodeValue;
					var terminal_playstyle=elm.getElementsByTagName('terminal_playstyle')[0].childNodes[0].nodeValue;
		
                    terminal_programname=decodeURI(terminal_programname);//防止乱码
                    
					var status = "";
					var image_url = "";
					var disabledstr="";
					
					if(terminal_playstyle=="PLAYER"||terminal_playstyle=="PLAY"){
						terminal_playstyle="<span style='color:green'>播放</span>";
					}else if(terminal_playstyle=="DOWNLOAD"){
						terminal_playstyle="<span style='color:green'>下载</span>";
					}else if(terminal_playstyle=="LOADING"){
						terminal_playstyle="<span style='color:green'>加载节目</span>";
					}else{
						terminal_playstyle="<span style='color:red'>停止</span>";
					}
					if(terminal_status=='3'){
						status	="<span style='color:red'>断开</span>";
						disabledstr="disabled";
					}else{
						if(terminal_programname=="NULL"||terminal_programname=="null"){
							status	="<span style='color:red'>休眠</span>";
							terminal_programname="无";
						}else{
							status	="<span style='color:green'>连接</span>";
						}
					}
					//---------------------------------
					tag=tag+" <table width='100%' height='27'  border='0' cellpadding='0'cellspacing='0'>";
					tag=tag+"       <tr class=TableTrBgFF onmouseover=this.className='TableTrBg06' onmouseout=this.className='TableTrBgFF'>";      
					tag=tag+"<td width='3%' align='center'>"+(i+1)+"</td>";     
					tag=tag+"         <td width='13%' align='center'>"+terminal_zuname+"</td>";
					tag=tag+"         <td width='18%' align='center'><a href='javascript:;' title='查看终端详细信息' onClick='javaScript:viewterminal(\""+terminal_name+"\",\""+terminal_mac+"\")'><strong class='DeviceInfoTitle'>"+terminal_name+"</strong></a></td>";
					if(terminal_status=='3'){
						tag=tag+"          <td width='11%' align='center'>"+terminal_ip+"<input type='hidden' id='ip_' value='"+terminal_ip+"'><br></td>";
					}else{
						tag=tag+"          <td width='11%' align='center'><a href='http://"+terminal_ip+httport+terminal_programconfig+"' target='_blank' title='点击进入终端配置系统'>"+terminal_ip+"</a><br/><font color='#AB6F38'>"+terminal_mac+"</font><input type='hidden' id='ip_' value='"+terminal_ip+"'></td>";
					}
					tag=tag+"          <td width='7%' align='center'>"+status+"</td>";
					
					tag=tag+"         <td width='7%' align='center'>"+terminal_playstyle+"</td> ";
					tag=tag+"         <td width='10%' align='center'>"+version+"</td> ";
					tag=tag+"          <td width='14%' align='center'>"+terminal_programname+"</td>"; 
					tag=tag+" <td width='17%' align='center'>";
					if(terminal_status=='3'){
					tag=tag+"				<input type='button' id='active_button_"+terminal_mac+"' class='button' style='width:50px' value='唤 醒'  onclick='activeTerminal(\""+terminal_ip+"\",\""+terminal_mac+"\");'/>";
					//tag=tag+"				<input type='button' class='button' style='width:50px' value='截 屏'  disabled='disabled' />";
					tag=tag+"				<input type='button' id='ping_button_"+terminal_mac+"' class='button'  style='width:90px' value='查看网络'  onclick='pingTerminal(\""+terminal_name+"\",\""+terminal_ip+"\",\""+terminal_mac+"\");'/>";
					}else{
					tag=tag+"				<input type='button' class='button' value='截 屏' "+disabledstr+"  style='width:40px' onclick='guiCamera(\""+terminal_name+"\",\""+terminal_ip+"\",\""+terminal_mac+"\");'/>";
					tag=tag+"				<input type='button' class='button'  value='查看节目单' "+disabledstr+"  style='width:80px'  onclick='viewProjectMenu(\""+terminal_name+"\",\""+terminal_programname+"\",\""+terminal_mac+"\",\""+terminal_ip+"\");'/>	";			
					//tag=tag+"				<input type='button' class='button'  value='发送LED文字' "+disabledstr+"  style='width:80px'  onclick='ledset(\""+terminal_mac+"\",\""+terminal_ip+"\");'/>	";			
					}tag=tag+"			</td>  ";
					tag=tag+"         </tr>";
					tag=tag+" <tr>";
					tag=tag+"             <td class='Line_01' colspan='9'></td>";
					tag=tag+"          </tr>";
					tag=tag+"     </table>";
					tdNode.innerHTML = tag;
					trNode.appendChild(tdNode);

					if(browertype.indexOf("IE:")!=-1){
					   if(parseInt(browertype.split(":")[1])<10){
					      document.getElementById('dataTable').children[0].appendChild(trNode);
					   }else{
					      document.getElementById('dataTable').appendChild(trNode);
					   }	
					}else{
					   document.getElementById('dataTable').appendChild(trNode);
					}
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
					 document.getElementById('dataTable').children[0].appendChild(trNode);
				  }else{
					 document.getElementById('dataTable').appendChild(trNode);
				  }		
				}else{
				   document.getElementById('dataTable').appendChild(trNode);
				}
			}
			startTimer();
		}
		
		function activeTerminal(cnt_ip,mac){
			if(confirm("提示信息：确认唤醒该终端？（只适用于局域网）")){
				document.getElementById("active_button_"+mac).setAttribute("value","正在唤醒..");
				document.getElementById("active_button_"+mac).disabled="disabled";
				DwrClass.actionTerminal(cnt_ip,mac,activeterOK);
			}
		}
		function pingTerminal(cnt_name,cnt_ip,mac){
			document.getElementById("ping_button_"+mac).setAttribute("value","正在ping..");
			document.getElementById("ping_button_"+mac).disabled="disabled";
			DwrClass.pingTerminal2(cnt_name,cnt_ip,mac,pingterOK);
		}
		function pingterOK(result){
			var mac=result.split("_");
			alert(mac[1]);
			document.getElementById("ping_button_"+mac[0]).setAttribute("value","查看网络状态");
			document.getElementById("ping_button_"+mac[0]).disabled="";
		}
		function activeterOK(result){
			var mac=result.split("_");
			alert(mac[1]);
			document.getElementById("active_button_"+mac[0]).setAttribute("value","唤 醒");
			document.getElementById("active_button_"+mac[0]).disabled="";
		}
		function onSel(idnm, len){
			var startIdx = 0;
			var endIdx = len;
			var selTable = document.getElementById(idnm);
			//selTable.style.cssText="border: #336699 2px solid";
			selTable.style.cssText="=\"rder: #FF0000 2px solid;";
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
			timer=setTimeout("request("+current_num+")", 8143);
		}
		function current_page(){
			var current_num=document.getElementById("current_num").value;
			clearTimeout(timer);
			request(current_num);
		}
		function nextpage(){
			var next_num=document.getElementById("next_num").value;
			clearTimeout(timer);
			request(next_num);
		}
		function prepage(){
			var pre_num=document.getElementById("pre_num").value;
			clearTimeout(timer);
			request(pre_num);
		}
		function endpage(){
			var end_num=document.getElementById("end_num").value;
			clearTimeout(timer);
			request(end_num);
		}
		
		function firstpage(){
			clearTimeout(timer);
			request(1);
		}
		
		function guiCamera(cnt_title,cntIp,cntmac){
			parent.guiCamera(cnt_title,cntIp,cntmac);
		}
		function viewProjectMenu(cnt_title,programName,cntmac,cntIp){
			parent.viewProjectMenu(cnt_title,programName,cntmac,"!"+cntIp);
		}
		function viewterminal(cname,cid){
			parent.viewterminal(cname,cid);
		}
		
		function ledset(mac,ip){
		  parent.ledset(mac,ip);
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
</script>

</head>
	<body onLoad="javascript:onLoad();" style="margin: 0px;">
		<form name="form1" id="form1" method="post" action="">
	 		<div style="overflow: auto;height: 100%;position:absolute;width: 100%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="Line_01"></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="dataTable"></table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td align="center" valign="top"></td>
					</tr>
				</table>
				<div style="height: 50px"></div>
		   </div>
		   
		   <input type="hidden" id="list_size" name="list_size">
		   <input type="hidden" id="lstsize" name="lstsize">
		   <input type="hidden" id="page" name="page" />
	   </form>
	   
		 <div id="ToolBar">
			<%--<table width="100%" border="1"  id="page_id" style="margin:auto">
				<tr>
					<td  align="center" style="width:100%;margin:auto;text-align:center;vertical-align:middle;" >--%>
					总共【<span style="color: blue" id="cnt_sum_id"></span>】台终端
						<input type="hidden" id="pre_num"/>
						<input type="hidden" id="next_num"/>
						<input type="hidden" id="end_num"/>
						<input type="hidden" id="current_num"/>
	                   <a href="javascript:;" onclick="javascript:firstpage();return false"><img src="/images/btn_first.gif" border="0"/></a>&nbsp;
					   <a href="javascript:;" onclick="javascript:prepage();return false"><img src="/images/btn_pre.gif" border="0"/></a>
					   &nbsp; <span id="current_show"></span>&nbsp; 
	                   <a href="javascript:;" onclick="javascript:nextpage();return false;"><img src="/images/btn_next.gif" border="0"/></a>&nbsp; 
	                   <a href="javascript:;" onclick="javascript:endpage();return false"><img src="/images/btn_end.gif" border="0"/></a>&nbsp;
	                                       共<span id="total_num"></span>页 
					<%--</td>
				</tr>
		    </table>
		--%></div>
	</body>
</html>
