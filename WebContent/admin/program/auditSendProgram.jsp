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
    out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;������...</center>");
			
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
	ips0=(String[])ipset.toArray(new String[ipset.size()]);//�����ظ�����
	
	String tempstring="";
    for(int j=0,n=ips0.length;j<n;j++){
	   tempstring+="#"+ips0[j];
	}
    send_ips=tempstring;
    //=============================================================
	
	String oppp=request.getParameter("oppp")==null?"0":request.getParameter("oppp");  
	//�ж��Ǵ��Ǹ�ҳ������ģ�  1�������ҳ�棨/admin/program/auditprogramList.jsp�� �������ص�/rq/programList?left_menu=&zu_id=no
	request.setAttribute("oppp",oppp);
	
	DBConnection dbc= new DBConnection();
	Connection conn=dbc.getConection();
	
	if("1".equals(oppp)){ //���Ա��˷�����
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
	List <Terminal> appiplist=new ArrayList<Terminal>(); ///��ǰ�����ն�
	List <Terminal> appiplist_n=new ArrayList<Terminal>();///��ǰ�������ն�
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
					if(terminal.getCnt_mac().equals(macip[1])&&terminal.getCnt_islink()!=3){//��ֹ��ͬMAC��IPһ�������
					   if(null!=terminal.getCnt_name()){//��ֹ���ߵ�δ����ն˵ĳ���
							appiplist.add(terminal);
							if(online_ips.indexOf(terminal.getCnt_mac())==-1){
							   online_ips+=new StringBuffer().append("#").append(terminal.getCnt_ip()).append("_").append(terminal.getCnt_mac()).toString();//��ȡ���ߵ��նˣ��������ߵ�
							}
						}else{
						  // if(null!=terminal)	
						  //    System.out.println("--------auditSendProgram.jsp--------���ߵ��ն˵��ն�ip:--------------"+terminal.getCnt_ip());
						  // else
						 //     System.out.println("--------auditSendProgram.jsp--------���ߵ��ն˵��ն�����Ϊnull--------------");
						}
					}else if(terminal.getCnt_mac().equals(macip[1])&&terminal.getCnt_islink()==3){
					//}else if(terminal.getCnt_ip().equals(ips[i])&&terminal.getCnt_islink()==3){
					   if(null!=terminal.getCnt_name())//��ֹ�����ߵ�δ����ն˵ĳ���
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
		<title>���ͽ�Ŀ</title>
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
			   
		       function sendclient(){//���ͽ�Ŀ
		       		issendp=1;
		        	document.getElementById("p_str").innerHTML="������֤��Ŀ�����Ժ�...";
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
					         	   document.getElementById(ips[i]).innerHTML="<span style=color:'green'><img src='/images/loading.gif'>���ڷ���,���Ե�...</span>";	
					         	}
					        }
					        document.getElementById("p_str").innerHTML="���ڷ��ͽ�Ŀ�����Ժ�..."; //���õ����Ľ��ȿ����ʾ����
					        
					        DwrClass.sendProgram('${programid}@${clentips}','${lg_user.name }','${ids}',sendProgramStatus); //����д��Ŀ���ͷ��ͽ�Ŀָ��ķ���
					        
					        document.getElementById("sendclient").disabled="disabled";
					        document.getElementById("pro_id").disabled="disabled";
				        }else{
				            alert("δ�ҵ�ѡ����ն��б�");
				        }
			  			
			       }else{
			         //��ʾ�ն��д����ͽ�Ŀ����ͬһ����Ŀ���ܷ�������
					      var programlist="<form action='' name='ifrm_Form'><div style='height:320px; overflow:auto;'><table width='100%'  border='0'  cellpadding='0' cellspacing='0'>";
					      for(var i=0;i<clientprojectlist.length;i++){
					      	 	programlist+="<tr style='background-color:#F5F9FD;' onmouseover=this.style.backgroundColor='#DAE8F5' onmouseout=this.style.backgroundColor='#F5F9FD'><td width='1%'><input type='checkbox' name='checkbox' value='"+clientprojectlist[i].jmid+"'/></td>"+
					      	 	"<td  width='16%' align='center' >"+clientprojectlist[i].playclient+"</td>"+
					      	 	"<td  width='15%' align='center'>"+clientprojectlist[i].clientip+"</td>"+
					      	 	"<td  width='16%' align='center'>"+clientprojectlist[i].name+"</td>"+
					      	 	"<td  width='16%' align='center'>"+clientprojectlist[i].setdate+"</td>"+
					      	 	"<td  width='16%' align='center'>"+clientprojectlist[i].enddate+"</td>"+
					      	 	"<td  width='8%' align='center'>"+clientprojectlist[i].playtypeZh+"</td>"+
					      	 	"<td  width='12%' align='center'>"+clientprojectlist[i].playsecond+"����</td>"+
					      	 	"</tr><tr><td class='Line_01' colspan='8'></td></tr>";
					      }
					    programlist+="</div></table></form>"
					    document.getElementById("showprogramid").innerHTML=programlist;
					    document.getElementById("divframe").style.left= "50px";
						document.getElementById("divframe").style.top="0px";
						document.getElementById("titlename").innerHTML="�ն˽�Ŀ���Ѵ��ڴ˲������͵Ľ�Ŀ���߲���ʱ���ͻ������ɾ���ն˽�Ŀ��";
					    document.getElementById("divframe").style.visibility='visible';
					    document.getElementById("massage").style.visibility='visible';
				    }
		        }
		       
		        var senderrorarray=new Array();
		        
		        var sendips='${online_ips}';    // �����ն�IP
		        var online_ippps='${online_ips}';
		        
		        function sendProgramStatus(resultmaps){
		        
			        for(var ipresult in resultmaps){//����mapֵ������
			              if(resultmaps[ipresult]=='NOOP'){
			                 if(document.getElementById(ipresult))
			              	   document.getElementById(ipresult).innerHTML="<span style='color:red'><img src='/images/error.gif'  height='16px'>�ն˻���δִ��ָ�</span>";	
			              }
			              if(resultmaps[ipresult]=='DOWNLOAD'){
			              	sendips=sendips.replace("#"+ipresult,"");   //���˵��������ص��ն�IP
			              	sendErrorProgramIps+="#"+ipresult;
			              	if(document.getElementById(ipresult))
			              	   document.getElementById(ipresult).innerHTML="<span style='color:red'><img src='/images/error.gif'  height='16px'>����ʧ�ܣ��ͻ����������ؽ�Ŀ�����Ժ����ԣ�</span>";	
			              	//senderrorarray.push(ipresult);
			              }
			              //alert(resultmaps[ipresult]); 
			              if(resultmaps[ipresult]=='save_ok'){
			              	if(document.getElementById(ipresult))
			              	  document.getElementById(ipresult).innerHTML="<span style='color:green'><img src='/images/ok.gif'>��Ŀ������ɹ����ͻ��˿������أ�</span>";	
			              }
			              if(resultmaps[ipresult]=='nodaydownload_save_ok'){
			              	if(document.getElementById(ipresult))
			              	  document.getElementById(ipresult).innerHTML="<span style='color:green'><img src='/images/ok.gif'>��Ŀ������ɹ����ͻ�����䶨ʱ���أ�</span>";	
			              }
			        }
			        if(online_ippps!=''&&sendips==''){  ///�����ն˶����������
			        	 progressBarHidden();
			        	 document.getElementById("resendid").disabled="";
			        }else if(online_ippps==''){
			           document.getElementById("systemWorking").innerHTML="<br/><span style='color:green;'>��Ŀ����ɹ���</span>";
			        }else{
			        	 //////////////////*******************�����Ƿ��ͳɹ�������������������
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
							//////////////////*******************����ˢ������״̬��ɨ������״̬������ʾ������������������
			        		timer=setInterval("DwrClass.clientRequest('${programapp.program_jmurl}@"+sendips+"',clientRequesttatus)", 1200);
						}
						//alert(ipresult+"******"+(resultmaps[ipresult]));
						if(time_num<120){//
					    	 for(var ipresult in resultmaps){
					    	 	if(resultmaps[ipresult]=='OK'){
						    		document.getElementById(ipresult).innerHTML="<span style=color:'green'><img src='/images/loading.gif'>���ͳɹ����ͻ�����������...���Ժ�</span> "+time_num;	
			              			document.getElementById("tishiid").style.display="block";
						    		document.getElementById(ipresult).className="deff_ok";
					    		}
					    	}
				    	}else{//��ʱ90�� ����
				    		for(i=0;i<cnt_array_num;i++){
				    			if(document.getElementById(cnt_array[i])){
				    				if(document.getElementById(cnt_array[i]).className=="deff"){//��ʱ���ͣ����ֻ�û����̨���˴�Ҳ��ʾ����
						    			document.getElementById(cnt_array[i]).innerHTML="<span style=color:'red'><img src='/images/error.gif' height='16px'>����ʧ�ܣ���ʱ50�룬�����������ӣ�</span>";
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
							    sendErrorProgramIps+="#"+ipresult;   ///����ʧ�ܵ��ն�
							   // senderrorarray.push(ipresult);
							    //ȥ���������ظ�������
							  // senderrorarray=arrayrepeat(senderrorarray);
					 		}
					 	}
					 }
				 }
		
		       function cannel(){//ȡ������
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
					divNode.innerHTML= "<div style='margin-top:20px'><img src='/images/mid_giallo.gif' align=absmiddle><font style='font-size:12px;' id='p_str'>&nbsp;���ڷ��ͽ�Ŀ�����Ժ�...</font></div>";
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
			
			   function arrayrepeat(arr){//ȥ�������ظ�Ԫ��
			  	 if(arr!=''){
			        return arr.reverse().join(",").match( /([^,]+)(?!.*\1)/ig).reverse();
			     }
			   }
			
			   function reSendProgram(){ //���·�������ʧ�ܵĽ�Ŀ
					clearInterval(timer);
					//var arraytemp=arrayrepeat(senderrorarray);
					//for(var i=0,n=arraytemp.length;i<n;i++){
					  //sendErrorProgramIps+="#"+arraytemp[i];
					//}
					//arraytemp.length=0;
					//senderrorarray.length=0;//�������
					var sendErrorIpsArray=sendErrorProgramIps.split("@");
					sendips=sendErrorIpsArray[1];
					var ips=sendips.split("#");
					for(var i=1;i<ips.length;i++){
					   	document.getElementById(ips[i]).innerHTML="<span style=color:'green'><img src='/images/loading.gif'>�������·���,���Ե�...</span>";	
						document.getElementById(ips[i]).className="deff";
						time_num=0;
					}
					progressShow();
					DwrClass.sendProgram(sendErrorProgramIps,'${lg_user.name}','${ids}',resendProgramStatus); 
					
		       }
		       
		        function resendProgramStatus(resultmaps){
		        	for(var ipresult in resultmaps){//����mapֵ������
			              if(resultmaps[ipresult]=='DOWNLOAD'){
			              	sendips=sendips.replace("#"+ipresult,"");   //���˵��������ص��ն�IP
			              	sendErrorProgramIps+="#"+ipresult;
			              	document.getElementById(ipresult).innerHTML="<span style='color:red'><img src='/images/error.gif'  height='16px'>����ʧ�ܣ��ͻ����������ؽ�Ŀ�����Ժ����ԣ�</span>";	
			              	//senderrorarray.push(ipresult);
			              }
			        }
			        if(sendips==''){  ///�����ն˶����������
			        	 progressBarHidden();
			        	 document.getElementById("resendid").disabled="";
			        }else{
			        	 //////////////////*******************�����Ƿ��ͳɹ�������������������
			      	 timer1=setInterval("DwrClass.isSendProgramOk('"+sendErrorProgramIps+"','inglv0009',issendok)", 1000);
			        }
			        
		            sendErrorProgramIps="${programid}@";
		        
		          // progressBarHidden();
			     //  for(var ipresult in resultmaps){ 
			          //    if(resultmaps[ipresult]==null){
			       //       	  document.getElementById(ipresult).innerHTML="<span style=color:'red'><img src='/images/error.gif'  height='25px'>���·���ʧ��,���������Ƿ����ӣ�</span>";
			       //       	  document.getElementById("resendid").disabled="";
			       //       	   senderrorarray.push(ipresult);
			       //       }else if(resultmaps[ipresult]=='DOWNLOAD'){
			      //        	document.getElementById(ipresult).innerHTML="<span style='color:red'><img src='/images/error.gif'  height='16px'>���·���ʧ�ܣ��ͻ����������ؽ�Ŀ�����Ժ����ԣ�</span>";	
			      //        	  senderrorarray.push(ipresult);
			       //       }else{
			       //       	  document.getElementById(ipresult).innerHTML="<span style=color:'green'><img src='/images/loading.gif'>���ͳɹ�,�ͻ�����������...</span>";	
			        //      }
			      // }
			       //////////////////*******************����ˢ������״̬������������������
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
						alert("ѡ��Ҫɾ�����");
						return;
				    }
					if(confirm("ȷ��ɾ��ѡ�еĽ�Ŀ����")){
						DwrClass.deleteProgramHistory(checkids,deleteProgramHistoryOk); 
					}
			  }
			  function deleteProgramHistoryOk(status){
					if("ok"==status){
					   alert("ɾ����Ŀ���ɹ���");
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
								onclick="closedivframe1();">�ر�</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table width="800px;" border="0" cellpadding="0" cellspacing="0">
								<tr   class="TitleBackground">
									<td width="1%"><input type="checkbox" onclick="all_chk(this.checked);"/></td>
									<td style="font-weight: bold" width="16%" align="center" height="25px">
										�ն�����
									</td>
									<td style="font-weight: bold" width="15%" align="center">
										�ն�&nbsp;&nbsp;IP
									</td>
									<td  style="font-weight: bold" width="16%" align="center">
										��Ŀ����
									</td>
									<td style="font-weight: bold" width="16%" align="center">
										��ʼʱ��
									</td>
									<td  style="font-weight: bold" width="16%" align="center">
										����ʱ��
									</td>
									<td style="font-weight: bold" width="8%" align="center">
										��������
									</td>
									<td style="font-weight: bold" width="12%" align="center">
										����ʱ��
									</td>
								</tr>
								<tr style='background-color:#F5F9FD;'>
									<td colspan="8" id="showprogramid" width="100%">
										
									</td>
								</tr>
								<tr style='background-color:#F5F9FD;'>
									<td colspan="8" height="40px">
										&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button1" onclick="deleteProgramHistory()" value=" ɾ �� "/>
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
											���
										</td>
										<td width="20%" style="font-weight: bold">
											�ն�����
										</td>
										<td style="font-weight: bold" width="20%" >
											�ն�IP
										</td>
										<td style="font-weight: bold" width="20%" >
											����״̬
										</td>
										<td style="font-weight: bold" width="34%" >
											����״̬
										</td>
									</tr>
							</table>
							<div style="height:350px;overflow:auto;">
								<table border="0" width="100%" cellpadding="0" cellspacing="0">
									<c:if test="${!empty appiplist}">
										<tr class="TitleBackground">
											<td  style="font-weight: bold" colspan="5">��ǰ�����նˣ�</td>
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
													�ȴ�����
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
											<td  style="font-weight: bold"  colspan="5">��ǰ�Ͽ��նˣ�</td>
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
													��ǰ�����أ���������
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
									   value="  ��һ��  " ${oppp==1?'disabled':''} id="pro_id" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							
								<input type="button" onclick="sendclient();" class="button1" id="sendclient" value="���͵��ն�"  />
									
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" onclick="reSendProgram(); this.disabled='disabled'" class="button1" value=" ���·��� " disabled="disabled" id="resendid"/>
									
									<span style="color: green;display: none;margin-top: 20px" id="tishiid" >
									&nbsp;&nbsp;������ʾ����ǰ�Ͽ��ͻ��˻Ὺ���Զ����½�Ŀ�������ն��������ؽ�Ŀ�����뿪��ҳ��ͻ��˻�������ؽ�Ŀ��
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
