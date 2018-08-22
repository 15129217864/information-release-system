<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ProgramAppDAO"/>
<jsp:directive.page import="com.xct.cms.domin.ProgramApp"/>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
			
	Users user = (Users) request.getSession().getAttribute("lg_user");
	String programid=(String)request.getAttribute("programid");
	String send_ips=(String)request.getAttribute("send_ips");
	//String send_macs=(String)request.getAttribute("send_macs");
	
	//=============================================================
	String []ips0= send_ips.split("#");
    Set<String>ipset=new HashSet<String>();
	for(int j=0,n=ips0.length;j<n;j++){
	   if(!ips0[j].equals("")){
	      ipset.add(ips0[j]);
	   }
	}
	ips0=(String[])ipset.toArray(new String[ipset.size()]);//过滤重复？？
	
	String tempstring="";
    for(int j=0,n=ips0.length;j<n;j++){
	   tempstring+="#"+ips0[j];
	}
    send_ips=tempstring;
    //=============================================================
	
	String oppp=request.getParameter("oppp")==null?"0":request.getParameter("oppp");  
	//判断是从那个页面过来的，  1：待审核页面（/admin/program/auditprogramList.jsp） 其他返回到/rq/programList?left_menu=&zu_id=no
	request.setAttribute("oppp",oppp);
	
	DBConnection dbc= new DBConnection();
	Connection conn=dbc.getConection();
	
	if("1".equals(oppp)){ //审核员审核发送用
	   String  batch=request.getParameter("batch");
	   if(null!=batch){
		  StringBuffer ids=new StringBuffer("");
		   List<ProgramApp> plist= new ProgramAppDAO().getALLProgramAppByStr( conn,new StringBuffer().append(" where  batch=").append(batch).append(" order by program_playdate ,program_playtime ").toString());
           for(int i=0,n=plist.size();i<n;i++){
           		ProgramApp pa= plist.get(i);
        		ids.append(new StringBuffer().append(pa.getId()).append("!").toString());
        		programid=pa.getProgram_id();
           }
          // String []macs= send_ips.split("#");
          // send_ips="";
          // for(int i=0,n=macs.length;i<n;i++){
	     //      Terminal t=FirstStartServlet.terminalMap.get(macs[i]);
	     //      	if(t!=null){
	     //      		send_ips+="#"+t.getCnt_ip();
	     //      	}
         //  }
           request.setAttribute("ids",ids.append("!").toString());
        }
        //System.out.println(send_ips);
	}
	
	ProgramApp programapp=new ProgramAppDAO().getProgramAppByStr(conn,new StringBuffer().append("where program_id='").append(programid).append("'").toString());
	
	//TerminalDAO  terminaldao= new TerminalDAO();
	//List<Terminal> allTerminalList=terminaldao.getALLTerminalDAO(conn,"  order by cnt_ip");
	
	dbc.returnResources(conn);
	
	Map<String,Terminal> cnt_map=FirstStartServlet.terminalMap;
	List <Terminal> appiplist=new ArrayList<Terminal>(); ///当前在线终端
	List <Terminal> appiplist_n=new ArrayList<Terminal>();///当前不在线终端
	Terminal terminal=null;
        String []ips= send_ips.split("#");
        String online_ips="";
        
        for(Map.Entry<String, Terminal>enty: cnt_map.entrySet()){
       // for(int j=0;j<allTerminalList.size();j++){
		   terminal=enty.getValue();
			for(int i=0,n=ips.length;i<n;i++){
			   if(!"".equals(ips[i])){
				    String [] macip=ips[i].split("_");
				   //System.out.println(terminal.getCnt_ip()+"----"+ips[i]);
					if(terminal.getCnt_mac().equals(macip[1])&&terminal.getCnt_islink()!=3){//防止不同MAC，IP一样的情况
					   if(null!=terminal.getCnt_name()){//防止在线的未审核终端的出现
							appiplist.add(terminal);
							if(online_ips.indexOf(terminal.getCnt_mac())==-1){
							   online_ips+=new StringBuffer().append("#").append(terminal.getCnt_ip()).append("_").append(terminal.getCnt_mac()).toString();//获取在线的终端，包括休眠的
							}
						}else{
						  // if(null!=terminal)	
						  //    System.out.println("--------auditSendProgram.jsp--------在线的终端的终端ip:--------------"+terminal.getCnt_ip());
						  // else
						 //     System.out.println("--------auditSendProgram.jsp--------在线的终端的终端名称为null--------------");
						}
					}else if(terminal.getCnt_mac().equals(macip[1])&&terminal.getCnt_islink()==3){
					//}else if(terminal.getCnt_ip().equals(ips[i])&&terminal.getCnt_islink()==3){
					   if(null!=terminal.getCnt_name())//防止不在线的未审核终端的出现
						 appiplist_n.add(terminal);
					}
				}
        	}
		}
		TerminalDAO terminaldao= new TerminalDAO();
		request.setAttribute("appiplist",terminaldao.getSortList(appiplist,"ASC"));
		request.setAttribute("appiplist_n",terminaldao.getSortList(appiplist_n,"ASC"));
		
		request.setAttribute("clentips",send_ips);
		//request.setAttribute("clentmacs",send_macs);
		
		request.setAttribute("online_ips",online_ips);
		request.setAttribute("programapp",programapp);
		request.setAttribute("programid",programapp.getId());
		
		//String cnt_ip[]=cnt_ips.split("#");
		//for (int i = 1; i < cnt_ip.length; i++) {
			//FirstStartServlet.downloadProgramStatus.remove(cnt_ip[i]);
		//}
%>
<html>
	<head>
		<title>发送节目</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<script type="text/javascript">
	  
			   var timer=null;
			   var timer1=null;
			   var sendErrorProgramIps="${programid}@";
			   var issendp=0;
			   
		       function sendclient(){//发送节目
		       		issendp=1;
		        	document.getElementById("p_str").innerHTML="正在验证节目，请稍后...";
		        	progressShow();
			        DwrClass.checkProgramIsExist('${all_program_ids}',checkProgram); 
			       //checkProgram("");
		       }
		       
		       function checkProgram(clientprojectlist){
		        	
			       if(clientprojectlist==null || clientprojectlist==""){
			       
			       		var clentips='${clentips}';
			       		if(clentips!=""){
					        var ips=clentips.split("#");
					        for(var i=1;i<ips.length;i++){
					        	if(document.getElementById(ips[i])){
					         	   document.getElementById(ips[i]).innerHTML="<span style=color:'green'><img src='/images/loading.gif'>正在发送,请稍等...</span>";	
					         	}
					        }
					        document.getElementById("p_str").innerHTML="正在发送节目，请稍后..."; //设置弹出的进度框的提示文字
					        
					        DwrClass.sendProgram('${programid}@${clentips}','${lg_user.name }','${ids}',sendProgramStatus); //真正写节目单和发送节目指令的方法
					        
					        document.getElementById("sendclient").disabled="disabled";
					        document.getElementById("pro_id").disabled="disabled";
				        }else{
				            alert("未找到选择的终端列表！");
				        }
			  			
			       }else{
			         //显示终端有此类型节目或者同一个节目不能发送两次
					      var programlist="<form action='' name='ifrm_Form'><div style='height:320px; overflow:auto;'><table width='100%'  border='0'  cellpadding='0' cellspacing='0'>";
					      for(var i=0;i<clientprojectlist.length;i++){
					      	 	programlist+="<tr style='background-color:#F5F9FD;' onmouseover=this.style.backgroundColor='#DAE8F5' onmouseout=this.style.backgroundColor='#F5F9FD'><td width='1%'><input type='checkbox' name='checkbox' value='"+clientprojectlist[i].jmid+"'/></td>"+
					      	 	"<td  width='16%' align='center' >"+clientprojectlist[i].playclient+"</td>"+
					      	 	"<td  width='15%' align='center'>"+clientprojectlist[i].clientip+"</td>"+
					      	 	"<td  width='16%' align='center'>"+clientprojectlist[i].name+"</td>"+
					      	 	"<td  width='16%' align='center'>"+clientprojectlist[i].setdate+"</td>"+
					      	 	"<td  width='16%' align='center'>"+clientprojectlist[i].enddate+"</td>"+
					      	 	"<td  width='8%' align='center'>"+clientprojectlist[i].playtypeZh+"</td>"+
					      	 	"<td  width='12%' align='center'>"+clientprojectlist[i].playsecond+"分钟</td>"+
					      	 	"</tr><tr><td class='Line_01' colspan='8'></td></tr>";
					      }
					    programlist+="</div></table></form>"
					    document.getElementById("showprogramid").innerHTML=programlist;
					    document.getElementById("divframe").style.left= "50px";
						document.getElementById("divframe").style.top="0px";
						document.getElementById("titlename").innerHTML="终端节目单已存在此播放类型的节目或者播放时间冲突，请先删除终端节目！";
					    document.getElementById("divframe").style.visibility='visible';
					    document.getElementById("massage").style.visibility='visible';
				    }
		        }
		       
		        var senderrorarray=new Array();
		        
		        var sendips='${online_ips}';    // 所有终端IP
		        var online_ippps='${online_ips}';
		        
		        function sendProgramStatus(resultmaps){
		        
			        for(var ipresult in resultmaps){//返回map值，遍历
			              if(resultmaps[ipresult]=='NOOP'){
			                 if(document.getElementById(ipresult))
			              	   document.getElementById(ipresult).innerHTML="<span style='color:red'><img src='/images/error.gif'  height='16px'>终端还有未执行指令！</span>";	
			              }
			              if(resultmaps[ipresult]=='DOWNLOAD'){
			              	sendips=sendips.replace("#"+ipresult,"");   //过滤掉正在下载的终端IP
			              	sendErrorProgramIps+="#"+ipresult;
			              	if(document.getElementById(ipresult))
			              	   document.getElementById(ipresult).innerHTML="<span style='color:red'><img src='/images/error.gif'  height='16px'>发送失败，客户端正在下载节目，请稍后再试！</span>";	
			              	//senderrorarray.push(ipresult);
			              }
			              //alert(resultmaps[ipresult]); 
			              if(resultmaps[ipresult]=='save_ok'){
			              	if(document.getElementById(ipresult))
			              	  document.getElementById(ipresult).innerHTML="<span style='color:green'><img src='/images/ok.gif'>节目单保存成功，客户端开机下载！</span>";	
			              }
			              if(resultmaps[ipresult]=='nodaydownload_save_ok'){
			              	if(document.getElementById(ipresult))
			              	  document.getElementById(ipresult).innerHTML="<span style='color:green'><img src='/images/ok.gif'>节目单保存成功，客户端晚间定时下载！</span>";	
			              }
			        }
			        if(online_ippps!=''&&sendips==''){  ///所有终端都在下载情况
			        	 progressBarHidden();
			        	 document.getElementById("resendid").disabled="";
			        }else if(online_ippps==''){
			           document.getElementById("systemWorking").innerHTML="<br/><span style='color:green;'>节目保存成功！</span>";
			        }else{
			        	 //////////////////*******************监听是否发送成功。。。。。。。。。
			      	   timer1=setInterval("DwrClass.isSendProgramOk('"+sendips+"','inglv0009',issendok)", 1000);
			        }
				 }
				 var time_num=0;
				 var cnt_array=sendips.split("#");
				 
				 var cnt_array_num=cnt_array.length;
				 
				  function issendok(resultmaps){
						time_num++;
						var allok_num=0;
						for(i=0;i<cnt_array_num;i++){
			    			if(document.getElementById(cnt_array[i])){
			    				if(document.getElementById(cnt_array[i]).className=="deff_ok"){
			    				   allok_num++;
			    				}
			    			}
				    	}
				    	//alert(allok_num+"===="+(cnt_array_num-1));
						if(allok_num==(cnt_array_num-1)){
							 clearInterval(timer1);
							 progressBarHidden();
							//////////////////*******************激活刷新下载状态，扫描下载状态，并显示。。。。。。。。。
			        		timer=setInterval("DwrClass.clientRequest('${programapp.program_jmurl}@"+sendips+"',clientRequesttatus)", 1200);
						}
						//alert(ipresult+"******"+(resultmaps[ipresult]));
						if(time_num<120){//
					    	 for(var ipresult in resultmaps){
					    	 	if(resultmaps[ipresult]=='OK'){
						    		document.getElementById(ipresult).innerHTML="<span style=color:'green'><img src='/images/loading.gif'>发送成功，客户端正在下载...请稍候！</span> "+time_num;	
			              			document.getElementById("tishiid").style.display="block";
						    		document.getElementById(ipresult).className="deff_ok";
					    		}
					    	}
				    	}else{//超时90秒 处理
				    		for(i=0;i<cnt_array_num;i++){
				    			if(document.getElementById(cnt_array[i])){
				    				if(document.getElementById(cnt_array[i]).className=="deff"){//有时发送，出现还没到后台，此处也显示出来
						    			document.getElementById(cnt_array[i]).innerHTML="<span style=color:'red'><img src='/images/error.gif' height='16px'>发送失败，超时50秒，请检查网络连接！</span>";
						             	sendErrorProgramIps+="#"+cnt_array[i];
						             	document.getElementById("resendid").disabled="";
						             	document.getElementById(cnt_array[i]).className="deff_ok";
						             	//senderrorarray.push(cnt_array[i]);
					    			}
				    			}
				    		}
				    	}
				    }
	
				function clientRequesttatus(resultmaps){
					
					 for(var ipresult in resultmaps){ 
					 	//alert(resultmaps[ipresult]);
				       if(resultmaps[ipresult]!=""){
					 		document.getElementById(ipresult).innerHTML=resultmaps[ipresult];
					 		if(resultmaps[ipresult].indexOf("green")==-1){
							    document.getElementById("resendid").disabled="";
							    sendErrorProgramIps+="#"+ipresult;   ///发送失败的终端
							   // senderrorarray.push(ipresult);
							    //去除数组中重复的数据
							  // senderrorarray=arrayrepeat(senderrorarray);
					 		}
					 	}
					 }
				 }
		
		       function cannel(){//取消发送
		          clearInterval(timer);
		          i=0;
		          timer= setInterval("getNewDownloadStatus()", refreshTime);
		       }
		       
		       function progressBarOpen(){
		  			var cWidth = document.body.clientWidth;
		  			var cHeight= document.body.clientHeight;
					var divNode = document.createElement( 'div' );	
					divNode.setAttribute("id", "systemWorking");
					var topPx=(cHeight)*0.4;
					var defaultLeft=(cWidth)*0.4;
					divNode.style.cssText='position:absolute;margin:0;top:'+topPx+'px;left:'+defaultLeft+';width:215;height:59;background-image:url(/images/wait_background.gif);text-align:center;'; 
					divNode.innerHTML= "<div style='margin-top:20px'><img src='/images/mid_giallo.gif' align=absmiddle><font style='font-size:12px;' id='p_str'>&nbsp;正在发送节目，请稍后...</font></div>";
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
			
			   function arrayrepeat(arr){//去除数组重复元素
			  	 if(arr!=''){
			        return arr.reverse().join(",").match( /([^,]+)(?!.*\1)/ig).reverse();
			     }
			   }
			
			   function reSendProgram(){ //重新发送下载失败的节目
					clearInterval(timer);
					//var arraytemp=arrayrepeat(senderrorarray);
					//for(var i=0,n=arraytemp.length;i<n;i++){
					  //sendErrorProgramIps+="#"+arraytemp[i];
					//}
					//arraytemp.length=0;
					//senderrorarray.length=0;//数组清空
					var sendErrorIpsArray=sendErrorProgramIps.split("@");
					sendips=sendErrorIpsArray[1];
					var ips=sendips.split("#");
					for(var i=1;i<ips.length;i++){
					   	document.getElementById(ips[i]).innerHTML="<span style=color:'green'><img src='/images/loading.gif'>正在重新发送,请稍等...</span>";	
						document.getElementById(ips[i]).className="deff";
						time_num=0;
					}
					progressShow();
					DwrClass.sendProgram(sendErrorProgramIps,'${lg_user.name}','${ids}',resendProgramStatus); 
					
		       }
		       
		        function resendProgramStatus(resultmaps){
		        	for(var ipresult in resultmaps){//返回map值，遍历
			              if(resultmaps[ipresult]=='DOWNLOAD'){
			              	sendips=sendips.replace("#"+ipresult,"");   //过滤掉正在下载的终端IP
			              	sendErrorProgramIps+="#"+ipresult;
			              	document.getElementById(ipresult).innerHTML="<span style='color:red'><img src='/images/error.gif'  height='16px'>发送失败，客户端正在下载节目，请稍后再试！</span>";	
			              	//senderrorarray.push(ipresult);
			              }
			        }
			        if(sendips==''){  ///所有终端都在下载情况
			        	 progressBarHidden();
			        	 document.getElementById("resendid").disabled="";
			        }else{
			        	 //////////////////*******************监听是否发送成功。。。。。。。。。
			      	 timer1=setInterval("DwrClass.isSendProgramOk('"+sendErrorProgramIps+"','inglv0009',issendok)", 1000);
			        }
			        
		            sendErrorProgramIps="${programid}@";
		        
		          // progressBarHidden();
			     //  for(var ipresult in resultmaps){ 
			          //    if(resultmaps[ipresult]==null){
			       //       	  document.getElementById(ipresult).innerHTML="<span style=color:'red'><img src='/images/error.gif'  height='25px'>重新发送失败,请检查网络是否连接！</span>";
			       //       	  document.getElementById("resendid").disabled="";
			       //       	   senderrorarray.push(ipresult);
			       //       }else if(resultmaps[ipresult]=='DOWNLOAD'){
			      //        	document.getElementById(ipresult).innerHTML="<span style='color:red'><img src='/images/error.gif'  height='16px'>重新发送失败，客户端正在下载节目，请稍后再试！</span>";	
			      //        	  senderrorarray.push(ipresult);
			       //       }else{
			       //       	  document.getElementById(ipresult).innerHTML="<span style=color:'green'><img src='/images/loading.gif'>发送成功,客户端正在下载...</span>";	
			        //      }
			      // }
			       //////////////////*******************激活刷新下载状态。。。。。。。。。
			     //  setInterval("DwrClass.clientRequest('${programapp.program_jmurl}@${clentips}',clientRequesttatus)", 2000);
			    } 
			    
				function closedivframe(){
					document.getElementById('divframe').style.visibility="hidden";
					document.getElementById("massage").style.visibility='hidden';
				}
				
				function all_chk(sel_all){
					var ckform = document.ifrm_Form;
				  	var cbox   = ckform.checkbox;
				  	if(cbox){
					    if(cbox.length){
					    	for(i=0;i<cbox.length;i++){
					    		ckform.checkbox[i].checked=sel_all;
					    	}
					    }else{
					    	ckform.checkbox.checked = sel_all;
					    }   
					}
			   }
			   function chk_checkbox(){
					var forms = document.ifrm_Form;
					var checkids="";
					
					for(i=0;i<forms.checkbox.length;i++){
						if(forms.checkbox[i].checked == true) {
							id = forms.checkbox[i].value;
							checkids+="#"+id;
						}
					}
					if(forms.checkbox.checked == true){
					   checkids+="#"+forms.checkbox.value;
					}
					return checkids;
			   }
			   function deleteProgramHistory(){
				    var checkids=chk_checkbox();
					if(checkids==""){
						alert("选择要删除的项！");
						return;
				    }
					if(confirm("确认删除选中的节目单？")){
						DwrClass.deleteProgramHistory(checkids,deleteProgramHistoryOk); 
					}
			  }
			  function deleteProgramHistoryOk(status){
					if("ok"==status){
					   alert("删除节目单成功！");
				     }
				    closedivframe1();
			  }
			function closedivframe(){
				parent.closedivframe(2);
				if(${oppp}==1){
					if(parent.homeframe.content.content.location){
						parent.homeframe.content.content.location.reload();
					}
				}else{
					if(issendp==0){
						DwrClass.deleteProgramAppBybatch('${batch }');
					}
				}
			}
			function closedivframe1(){
			  document.getElementById("divframe").style.visibility='hidden';
			  document.getElementById("massage").style.visibility='hidden';
			}
	</script>
	<style type="text/css">
		#divframe {
			position: absolute;
			z-index: 999;
			filter: dropshadow(color = #666666, offx = 3, offy = 3, positive = 2);
			visibility: hidden
		}
		#mask {
			position: absolute;
			top: 0;
			left: 0;
			width: expression(body . scrollWidth);
			height: expression(body . scrollHeight);
			background: #000000;
			filter: ALPHA(opacity = 60);
			z-index: 1;
			visibility: hidden
		}
		#massage {
			border: #6699cc solid;
			border-width: 1 1 1 1;
			background: #fff;
			color: #036;
			font-size: 12px;
			line-height: 150%;
			visibility: hidden
		}
		.header {
			background: url(/images/device/btn_background.gif);
			font-family: Verdana, Arial, Helvetica, sans-serif;
			font-size: 12px;
			padding: 3 5 0 5;
			color: #fff
		}
	</style>     
     
	</head>
	<body  onload="progressBarOpen();">
	<div id="divframe">
			<div id="massage">
				<table cellpadding="0" cellspacing="0">
					<tr height="30px;" class=header>
						<td align="left" style="font-weight: bold">
							<span id="titlename"></span>
						</td>
						<td height="5px" align="right">
							<a href="javaScript:void(0);" style="color: #000000"
								onclick="closedivframe1();">关闭</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table width="800px;" border="0" cellpadding="0" cellspacing="0">
								<tr   class="TitleBackground">
									<td width="1%"><input type="checkbox" onclick="all_chk(this.checked);"/></td>
									<td style="font-weight: bold" width="16%" align="center" height="25px">
										终端名称
									</td>
									<td style="font-weight: bold" width="15%" align="center">
										终端&nbsp;&nbsp;IP
									</td>
									<td  style="font-weight: bold" width="16%" align="center">
										节目名称
									</td>
									<td style="font-weight: bold" width="16%" align="center">
										开始时间
									</td>
									<td  style="font-weight: bold" width="16%" align="center">
										结束时间
									</td>
									<td style="font-weight: bold" width="8%" align="center">
										播放类型
									</td>
									<td style="font-weight: bold" width="12%" align="center">
										播放时长
									</td>
								</tr>
								<tr style='background-color:#F5F9FD;'>
									<td colspan="8" id="showprogramid" width="100%">
										
									</td>
								</tr>
								<tr style='background-color:#F5F9FD;'>
									<td colspan="8" height="40px">
										&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button1" onclick="deleteProgramHistory()" value=" 删 除 "/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>	
		<center>
			<fieldset style="width:90%; margin-top: 10px;border: #6699cc 1 solid;">
				<table border="0" width="100%" align="center">
					<tr>
						<td colspan="3" >
							<table border="0" width="100%" cellpadding="0" cellspacing="0">
									<tr align="center"  class="TitleBackground">
										<td height="30px" width="6%" style="font-weight: bold">
											序号
										</td>
										<td width="20%" style="font-weight: bold">
											终端名称
										</td>
										<td style="font-weight: bold" width="20%" >
											终端IP
										</td>
										<td style="font-weight: bold" width="20%" >
											连接状态
										</td>
										<td style="font-weight: bold" width="34%" >
											下载状态
										</td>
									</tr>
							</table>
							<div style="height:350px;overflow:auto;">
								<table border="0" width="100%" cellpadding="0" cellspacing="0">
									<c:if test="${!empty appiplist}">
										<tr class="TitleBackground">
											<td  style="font-weight: bold" colspan="5">当前连接终端：</td>
										</tr>
										<c:set var="i" value="0"></c:set>
										<c:forEach items="${appiplist}" var="appip">
											<c:set var="i" value="${i+1}"></c:set>
											<tr align="center" class="TableTrBg06_" style="height: 25px" onmouseover="this.className='TableTrBg06'" onmouseout="this.className='TableTrBg06_'" >
												<td  height="20px"  width="6%" >
													${i }
												</td>
												<td   width="20%" >
													${appip.cnt_name }<input type="hidden" id="${appip.cnt_ip }v" value="0"/>
												</td>
												<td  width="20%" >
													${appip.cnt_ip }<br/><font color='#AB6F38'>${appip.cnt_mac }</font>
												</td>
												<td  width="20%" >
													${appip.cnt_islink_zh }
												</td>
												<td id="${appip.cnt_ip }_${appip.cnt_mac }" class="deff"  width="34%" >
													等待发送
												</td>
											</tr>
											<tr>
											   <td class="Line_01" colspan="5"></td>
										    </tr>
											
										</c:forEach>
									</c:if>
								</table>
								<table border="0" width="100%" cellpadding="0" cellspacing="0">
									<c:if test="${!empty appiplist_n}">
										<tr class="TitleBackground">
											<td  style="font-weight: bold"  colspan="5">当前断开终端：</td>
										</tr>
										<c:set var="i" value="0"></c:set>
										<c:forEach items="${appiplist_n}" var="appip">
											<c:set var="i" value="${i+1}"></c:set>
											<tr align="center" class="TableTrBg06_" style="height: 25px" onmouseover="this.className='TableTrBg06'" onmouseout="this.className='TableTrBg06_'" >
												<td  height="20px"  width="6%" >
													${i }
												</td>
												<td   width="20%" >
													${appip.cnt_name }
												</td>
												<td  width="20%" >
													${appip.cnt_ip }<br/><font color='#AB6F38'>${appip.cnt_mac }</font>
												</td>
												<td  width="20%" >
													${appip.cnt_islink_zh }
												</td>
												<td  id="${appip.cnt_ip }_${appip.cnt_mac }"  width="34%" style="color:maroon"  >
													当前不下载，开机下载
												</td>
											</tr>
											<tr>
											   <td class="Line_01" colspan="5"></td>
										    </tr>
											
										</c:forEach>
									</c:if>
								</table>
								
							</div>
							<div align="center" style="height: 40px;" id="tishidiv"><br/>
							    <input type="button" onclick="DwrClass.deleteProgramAppBybatch('${batch }');history.go(-1)" class="button1"
									   value="  上一步  " ${oppp==1?'disabled':''} id="pro_id" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							
								<input type="button" onclick="sendclient();" class="button1" id="sendclient" value="发送到终端"  />
									
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" onclick="reSendProgram(); this.disabled='disabled'" class="button1" value=" 重新发送 " disabled="disabled" id="resendid"/>
									
									<span style="color: green;display: none;margin-top: 20px" id="tishiid" >
									&nbsp;&nbsp;友情提示：当前断开客户端会开机自动更新节目，在线终端正在下载节目，您离开本页面客户端会继续下载节目！
									</span>
							</div>
						</td>
					</tr>
				</table>
			</fieldset><br/><br/>
		</center>
		<script>
			document.getElementById('loadid').style.display='none';
		</script>
			
	</body>
</html>
