<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>

<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Program"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramHistoryDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String program_name = UtilDAO.getUTF(request.getParameter("program_name") == null ? "": request.getParameter("program_name"));
	
	String type=request.getParameter("type") == null ? "": request.getParameter("type");
	String templateid=request.getParameter("templateid");
	String programe_files = request.getParameter("programe_files") == null ? "": request.getParameter("programe_files");
	ProgramDAO programdao= new ProgramDAO();
	if("3".equals(type)){
		String jmurl=request.getParameter("jmurl") == null ? "": request.getParameter("jmurl");
		List<Program> programlist=programdao.getProgram_typeBystr(" where program_JMurl='"+jmurl+"' and play_type=3");
		 if(programlist!=null){
			for(int i=0;i<programlist.size();i++){
				Program pp=programlist.get(i);
				request.setAttribute("program_JMurl",pp.getProgram_JMurl());
				request.setAttribute("play_start_time",pp.getPlay_start_time());
				request.setAttribute("play_end_time",pp.getPlay_end_time());
				request.setAttribute("day_stime"+i,pp.getDay_stime1());
				request.setAttribute("day_etime"+i,pp.getDay_etime1());
			}
		}
		int i=  new ProgramHistoryDAO().getPlayProgramTimeCount(templateid,jmurl,program_name); // ��ȡ��Ŀʱ��
		request.setAttribute("playsecond",i);
	}else{
		String jmtype_id=request.getParameter("jmtype_id")==null?"0":request.getParameter("jmtype_id");
		Program program=programdao.getProgram_type(jmtype_id);
		
	    if(program!=null){
		request.setAttribute("playsecond",program.getPlay_count());
		request.setAttribute("program_JMurl",program.getProgram_JMurl());
		
		request.setAttribute("play_start_time",program.getPlay_start_time());
		request.setAttribute("play_end_time",program.getPlay_end_time());
		request.setAttribute("day_stime0",program.getDay_stime1());
		request.setAttribute("day_etime0",program.getDay_etime1());
		request.setAttribute("jmtype_id",jmtype_id);
		}
	}
	request.setAttribute("templateid",templateid);
	request.setAttribute("type",type);
request.setAttribute("programe_files",programe_files);
%>
<html>
	<head>
		<title>��Ŀ����</title>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<link rel="STYLESHEET" type="text/css" href="/admin/checkboxtree/dhtmlxtree.css">
		<link rel="stylesheet" href="/admin/checkboxtree/style.css" type="text/css" media="screen" />
		<script language="JavaScript" src="/admin/checkboxtree/dhtmlxcommon.js"></script>
		<script language="JavaScript" src="/admin/checkboxtree/dhtmlxtree.js"></script>
		<script type="text/javascript" src="/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" src="/js/vcommon.js"></script>
		<script type="text/javascript">
                   var hour=0;
                   var mmm=0;
                   var year=0;
                    var month=0;
                     var day=0;
		             window.onload = function getNowTime(){   
		    		    //ȡ�õ�ǰʱ��   
					    var now= new Date();   
					     year=now.getYear();   
					     month=(now.getMonth()+1)>9?(now.getMonth()+1):"0"+(now.getMonth()+1);   
					     day=now.getDate()>9?now.getDate():"0"+now.getDate();   
					     hour=now.getHours()>9?now.getHours():"0"+now.getHours(); 
					     mmm=now.getMinutes()>9?now.getMinutes():"0"+now.getMinutes();  
					    // sss=now.getSeconds()>9?now.getSeconds():"0"+now.getSeconds();    
					    var nowdate=year+"-"+month+"-"+day; 
		                var nowtime=hour+":"+mmm+"00";   
					  
					   
				       // document.getElementById("starttime").value = nowtime;
				 	}  
		           
		            function check(){
		               var taskInfo=projectform.allips.value;
		               if (taskInfo=="") {
		                    alert("��ѡ���ն˷��ͽ�Ŀ��");
		                    return false;
		               }
		               
		              var startdate=projectform.startdate.value;//�滻�ַ�����ɱ�׼��ʽ  
		              var enddate=projectform.enddate.value;//�滻�ַ�����ɱ�׼��ʽ 
		              var startime=projectform.starttime.value;
		              var endtime=projectform.endtime.value;
		                 
		              var timecounts=getTimecount(startdate.replace("-","/"),enddate.replace("-","/"),startime,endtime);
		                
		              if(timecounts<=1){
		                  alert("��Ŀ����ʱ�����Ҫ����һ���ӣ�");
		                  return false;
		               }else{
		                  projectform.timecount.value=timecounts;
		               }
		               
						var d1 = new Date(Date.parse(startdate+" "+startime));  
						alert(startdate+" "+startime);
						var d2 = new Date(Date.parse(startdate+" "+endtime));
						alert(startdate+" "+endtime);
						if(d1>d2){   
						    alert("��Ŀ���Ž���ʱ�����Ҫ���ڿ�ʼʱ�䣡");
		                    return false;
						}
		            }
		            
		        var xmlHttp;                        //���ڱ���XMLHttpRequest�����ȫ�ֱ���
		        //���ڴ���XMLHttpRequest����
				function createXmlHttp() {
				    //����window.XMLHttpRequest�����Ƿ����ʹ�ò�ͬ�Ĵ�����ʽ
				    if (window.XMLHttpRequest) {
				       xmlHttp = new XMLHttpRequest();                  //FireFox��Opera�������֧�ֵĴ�����ʽ
				    } else {
				       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");//IE�����֧�ֵĴ�����ʽ
				    }
				}
				
				//��ȡ������Ϣ
				function getisExitisProject(projectparm) {
				    createXmlHttp();                                //����XMLHttpRequest����
				    xmlHttp.onreadystatechange = opproject;        //���ûص�����
				    xmlHttp.open("POST", "/rq/checkProject?timestamp=" + new Date().getTime(), true);  //����post����
				    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				    xmlHttp.send(projectparm);
				}
			
				//������״̬д��ҳ��
				function opproject() {
				    if (xmlHttp.readyState == 4) { 
				    
				       clearPreviousResult();    
				        //����õ�״̬����д��ҳ��
				        var resposenresult= xmlHttp.responseXML;
				        var clientproject;
				        var clientname="";
				        var playname="";
				        var starttime="";
				        var endtime="";
				        var playtype="";
				        var playcount="";
				        var clientprojects=resposenresult.getElementsByTagName("clientproject");
				        
				        if(clientprojects.length>0){
				           addTableHeader();
				        }
				      // alert(clientprojects.length);
				        for(var i=0;i<clientprojects.length;i++){
				            clientproject =clientprojects[i];
				            
				            clientname=clientproject.getElementsByTagName("clientname")[0].firstChild.nodeValue;
				            playname=clientproject.getElementsByTagName("playname")[0].firstChild.nodeValue;
				            starttime=clientproject.getElementsByTagName("starttime")[0].firstChild.nodeValue;
				            endtime=clientproject.getElementsByTagName("endtime")[0].firstChild.nodeValue;
				            playtype=clientproject.getElementsByTagName("playtype")[0].firstChild.nodeValue;
				            playcount=clientproject.getElementsByTagName("playcount")[0].firstChild.nodeValue;
				            
				            addTableRow(clientname,playname,starttime,endtime,playtype,playcount);
				        }
				        
						if(null!=clientproject){
					         var clientprojectheader=document.createElement("<a>");
					         var clientprojectText=document.createTextNode("�ն��Ѵ��ڴ˽�Ŀ,���Ҫ���Ŵ˽�Ŀ������ɾ���ն��ϵĽ�Ŀ");
					         clientprojectheader.appendChild(clientprojectText);
					
					         document.getElementById("booksHeader").appendChild(clientprojectheader);
							 document.getElementById("booksTable").setAttribute("border","0");
						     openClientBox(document.getElementById("text1"));
						   }
				    }
				}
					//�����ǰ����Ľ��
					function clearPreviousResult(){
				
						var booksHeader = document.getElementById("booksHeader");
						if(booksHeader.hasChildNodes()){
							booksHeader.removeChild(booksHeader.childNodes[0]);
						}
						var booksBody = document.getElementById("abc");
						while(booksBody.childNodes.length > 0){
							booksBody.removeChild(booksBody.childNodes[0]);
						}
					}
				   function addTableRow(clientname,playname,starttime,endtime,playtype,playcount){
				   
		              var row =document.createElement("tr");
		              var cell=createCellWithText(clientname);
		              row.appendChild(cell);
		              cell=createCellWithText(playname);
		              row.appendChild(cell);
		              cell=createCellWithText(starttime);
		              row.appendChild(cell);
		              cell=createCellWithText(endtime);
		              row.appendChild(cell);
		              cell=createCellWithText(playtype);
		              row.appendChild(cell);
		              cell=createCellWithText(playcount +" ����");
		              row.appendChild(cell);
		              document.getElementById("abc").appendChild(row);
		          }
				
				  function addTableHeader(){
				     var row =document.createElement("tr");
				     var cell=createCellWithText("�ն�����");
				     row.appendChild(cell);
				     cell=createCellWithText("��Ŀ����");
				     row.appendChild(cell);
				     cell=createCellWithText("��ʼʱ��");
				     row.appendChild(cell);
				     cell=createCellWithText("����ʱ��");
				     row.appendChild(cell);
				     cell=createCellWithText("��������");
				     row.appendChild(cell);   
				     cell=createCellWithText("����ʱ��");
				     row.appendChild(cell);
				     document.getElementById("abc").appendChild(row);
				  }
				  
				  function createCellWithText(text){
				     var cell=document.createElement("td");
				     var textnode=document.createTextNode(text);
				     cell.appendChild(textnode);
				     return cell;
				  }
				  function getvalues(){
			        
			           var checkips=tree.getAllChecked();
			           if(checkips==""){
						  alert("��ʾ��Ϣ����ѡ���նˣ�");
						  return  false;
						}
						var checkipArray=checkips.split(",");
						var reips="";
						for(i=0;i<checkipArray.length;i++){
								if(isIP(checkipArray[i])){
									reips+="!"+checkipArray[i].replace(",","!");
								}
						  }
						if(reips=="!" || reips==""){
							alert("��ʾ��Ϣ����ѡ���նˣ�");
							return false;
						}
			            projectform.allips.value= reips;
			            var startdate=projectform.startdate.value;//�滻�ַ�����ɱ�׼��ʽ  
		                var enddate=projectform.enddate.value;//�滻�ַ�����ɱ�׼��ʽ  
		                var startime=projectform.starttime.value;
		                var endtime=projectform.endtime.value;
		                var playtype="";
		                for(var i=0;i<projectform.playtype.length;i++){//��ѡ��ťȡֵҪѭ������
		                  if (projectform.playtype[i].checked)                        
		           			  playtype= projectform.playtype[i].value;
		                }
			            var parm="allips="+projectform.allips.value+"&projectdirectory="+projectform.projectdirectory.value+"&starttime="+startdate+" "+startime+"&endtime="+enddate+" "+endtime+"&playtype="+playtype;
			            getisExitisProject(parm);
			            //closeAddBox();
			            return  true;
			        }
				  
				  function isIP(strIP) {
					    var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
					    var reg = strIP.match(exp);
					    if(reg==null){
					      return false;
					    } else{
					        return true;
					    }
				  }
				  
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		     
		     
				  function getTimecount(date1,date2,time1,time2){//����ʱ���
						var t1=date1+" "+time1;
						var t2=date2+" "+time2;
						//alert(date1+" "+time1+"===="+date2+" "+time2);
						var addm=Math.ceil((Date.parse(date1+" "+time2)-Date.parse(t1))/1000/60);//ʱ���(��λ����
						//alert(addm);
						//var d=Math.floor(addm/60/24);
						//alert("d=="+d);
						//var m=addm-d*60*24
						//alert(m);
						//alert(m);
						return addm;
				  }
		     
		       
				  function stopforwardinserandloop(){
				  
						var myAlert = document.getElementById("alert"); 
						//var reg = document.getElementById("content"); 
						var mClose = document.getElementById("close"); 
						
						myAlert.style.display = "block"; 
						myAlert.style.position = "absolute"; 
						myAlert.style.top = "30%"; 
						myAlert.style.left = "50%"; 
						myAlert.style.marginTop = "-75px"; 
						myAlert.style.marginLeft = "-150px";
						
						mybg = document.createElement("div"); 
						mybg.setAttribute("id","mybg"); 
						mybg.style.background = "#000000"; 
						mybg.style.width = "100%"; 
						mybg.style.height = "100%"; 
						mybg.style.position = "absolute"; 
						mybg.style.top = "0"; 
						mybg.style.left = "0"; 
						mybg.style.zIndex = "500"; 
						mybg.style.opacity = "0.3"; 
						mybg.style.filter = "Alpha(opacity=30)"; 
						document.body.appendChild(mybg);
						
						document.body.style.overflow = "hidden"; 
						mClose.onclick = function() {
						 
						myAlert.style.display = "none"; 
						mybg.style.display = "none"; 
						} 
					}
					
					function stopforwardactive(){
					
						var myAlert2 = document.getElementById("alert2"); 
						var mClose2 = document.getElementById("close2"); 
						
						myAlert2.style.display = "block"; 
						myAlert2.style.position = "absolute"; 
						myAlert2.style.top = "30%"; 
						myAlert2.style.left = "25%"; 
						myAlert2.style.marginTop = "-75px"; 
						myAlert2.style.marginLeft = "-150px";
						
						mybg = document.createElement("div"); 
						mybg.setAttribute("id","mybg"); 
						mybg.style.background = "#000000"; 
						mybg.style.width = "100%"; 
						mybg.style.height = "100%"; 
						mybg.style.position = "absolute"; 
						mybg.style.top = "0"; 
						mybg.style.left = "0"; 
						mybg.style.zIndex = "500"; 
						mybg.style.opacity = "0.3"; 
						mybg.style.filter = "Alpha(opacity=30)"; 
						document.body.appendChild(mybg);
						
						document.body.style.overflow = "hidden"; 
						mClose2.onclick = function() {
							myAlert2.style.display = "none"; 
							mybg.style.display = "none"; 
						} 
					}
		
		
		function onSendProgram(){

		    var startdate=projectform.startdate.value;//�滻�ַ�����ɱ�׼��ʽ  
		    var enddate=projectform.enddate.value;//�滻�ַ�����ɱ�׼��ʽ 
		    //------------
		    var startime=projectform.starttime.value;
		    var endtime=projectform.endtime.value;
		    
		    var twostartime=projectform.twostarttime.value;
		    var twoendtime=projectform.twoendtime.value;
		    
		    var threestartime=projectform.threestarttime.value;
		    var threeendtime=projectform.threeendtime.value;
		    //--------------
		    var minute=projectform.minute.value;
		    
		    var selectedIndex = 0;
		    var timecounts=0;
		     var timecounts2=0;
		      var timecounts3=0;
			for (i=0; i<projectform.playtype.length; i++) {
		        if (projectform.playtype[i].checked) {
		           if(projectform.playtype[i].value=="1"){
		            	selectedIndex = 1;
		            }else if(projectform.playtype[i].value=="3"){
		            	selectedIndex = 3;
		            }else{
		            selectedIndex = 2;
		            }
		            break;
		        }
		    }
		    if (selectedIndex ==1 ||selectedIndex ==2){
				var ddd=getTimecount(year+"/"+month+"/"+day,year+"/"+month+"/"+day,hour+":"+mmm+":00","23:59:59");	 
			        if(minute==""){
			            alert("�����벥��ʱ����");
			            return;
			    	// }else if(minute<1){
			       //     alert("����ʱ���������1���ӣ�");
			        //    return;
			    	 }else if(minute>ddd){
			            alert("����ʱ������С�ڵ���24�㣡");
			            return;
			    	 }
			    	 if($("endone").style.display=="block"){//��� ѭ�� ��Ŀ
						var d1 = startdate+" "+startime;  
						var d2 = enddate+" "+endtime;
						if(startdate.replace("-","/") > enddate.replace("-","/")){   
							alert("��Ŀ�����������ڱ���Ҫ���ڿ�ʼ�������ڣ�");
						    return;
						}
					}
				    projectform.timecount.value=minute;
				    
			}
			else if(selectedIndex ==3){//��ʱ��Ŀ�ж�
				    //getNowTime();//�˴�����ȡֵ�ᵼ�¸��趨��ֵ���ϵͳ��ǰ��ʱ�������
				    
				    if($("startone").style.display=="block"||$("endone").style.display=="block"){
						if(startime==""){
				            alert("�����һѡ�ʼ����ʱ�䣡");
				             return;
				    	}else if( endtime==""){
				    	 	alert("�����һѡ���������ʱ�䣡");
				            return;
				    	}
				    	
				    	if((twostartime==""&&twoendtime!="")||(twostartime!=""&&twoendtime=="")){
				    	    alert("����ڶ�ѡ��Ŀ�ʼ����ʱ����������ʱ�䣡");
				            return;
				    	}
				    	if((threestartime==""&&threeendtime!="")||(threestartime!=""&&threeendtime=="")){
				    	    alert("�������ѡ��Ŀ�ʼ����ʱ����������ʱ�䣡");
				            return;
				    	}
				    	
				    	
						if(startime >= endtime
						        ||((twostartime!=""&&twoendtime!="")&&twostartime >= twoendtime)
						        ||((threestartime!=""&&threeendtime!="")&&threestartime >= threeendtime)){   
							alert("��Ŀ��������ʱ�����Ҫ���ڿ�ʼ����ʱ�䣡");
					    	return;
						}
						timecounts=getTimecount(startdate.replace("-","/"),enddate.replace("-","/"),startime,endtime);
						//if(timecounts<=1){
					   //    alert("��Ŀ����ʱ�����Ҫ����һ���ӣ�");
					   //    return false;
					  //  }
		                projectform.timecount.value=timecounts;
						//alert("timecounts==="+timecounts);
						
					// var twostartime=projectform.twostarttime.value;
		                 //var twoendtime=projectform.twoendtime.value;
		    
		              //   var threestartime=projectform.threestarttime.value;
		                // var threeendtime=projectform.threeendtime.value;
						
						if(twostartime!=""&&twoendtime!=""){
						   timecounts2=getTimecount(startdate.replace("-","/"),enddate.replace("-","/"),twostartime,twoendtime);
						  // if(timecounts2<=1){
						   //   alert("����ڶ�ѡ���Ŀ����ʱ�����Ҫ����һ���ӣ�");
					      //    return false;
						  // }
						   projectform.timecount2.value=timecounts2;
						 //  alert("timecounts2==="+timecounts2);
						}
						
						
						if(threestartime!=""&&threeendtime!=""){
						    timecounts3=getTimecount(startdate.replace("-","/"),enddate.replace("-","/"),threestartime,threeendtime);
		                   // if(timecounts3<=1){
						   //   alert("�������ѡ���Ŀ����ʱ�����Ҫ����һ���ӣ�");
					       //   return false;
						  // }
						   projectform.timecount3.value=timecounts3;
						 //  alert("timecounts3==="+timecounts3);
		                }
		                
					}
			}
			//projectform.action="/admin/program/sendprogram.jsp";
			projectform.action="/rq/updateaheadcreateproject";
			projectform.submit();
	   }
	   
	   function oncancel(){
			//parent.listtop.location.href="/admin/program/program_list_top.jsp";
			//parent.content.location.href="/rq/programList";
			//history.go(-1);
			window.location.href="/admin/program/selectclientIP.jsp?programe_file=${programe_files}";
		}
			
	   function $(id){
	     return document.getElementById(id);
	   }
			
	   function oninsert(obj){//startone  starttwo  
	   
	        if(obj==2){ //�岥
	            projectform.startandend.value=$("startdateid").value;

				//$("enddateid").value=document.getElementById("startdateid").value;
				$("endtwo").style.display="none";
				$("startone").style.display="none";
				$("starttwo").style.display="block";
				//$("forever").style.display="none";
				$("twostart").style.display="none";
				$("twoend").style.display="none";
				$("threestart").style.display="none";
				$("threeend").style.display="none";
	        }else if(obj==1){//ѭ��
	            projectform.startandend.value="";
				$("enddateid").disabled="";
				$("startdateid").disabled="";
				//$("enddateid").value=document.getElementById("startdateid").value;
				$("endone").style.display="block";
				$("endtwo").style.display="none";
				$("startone").style.display="none";
				$("starttwo").style.display="block";
				//$("forever").style.display="block";
				$("twostart").style.display="none";
				$("twoend").style.display="none";
			
				$("threestart").style.display="none";
				$("threeend").style.display="none";
		    }else if(obj==4){//����ѭ��
		        projectform.startandend.value=$("startdateid").value;
				//$("enddateid").value=document.getElementById("startdateid").value;
				$("endtwo").style.display="none";
				$("startone").style.display="none";
				$("starttwo").style.display="block";
				
				$("twostart").style.display="none";
				$("twoend").style.display="none";
			
				$("threestart").style.display="none";
				$("threeend").style.display="none";
				
			}else if(obj==3){//��ʱ
			    getNowTime();
			    projectform.startandend.value="";
				$("startdateid").disabled="";
				$("enddateid").disabled="";
				//$("enddateid").value=document.getElementById("startdateid").value;
				
				$("endone").style.display="block";
				$("endtwo").style.display="block";
				
				$("twostart").style.display="block";
				$("twoend").style.display="block";
			
				$("threestart").style.display="block";
				$("threeend").style.display="block";

				$("startone").style.display="block";
				$("starttwo").style.display="none";
				
				//$("forever").style.display="none";
			}
		}
					   function closedivframe(){
if(confirm("��ʾ��Ϣ��ȷ��ȡ�����ͽ�Ŀ��")){
	parent.closedivframe(2);
}
}
    </script>
	</head>
	<body onload="getNowTime();">
		<table width="100%" height="100%" align="center" border="0" cellpadding="10" cellspacing="0">
			<tr>
				<td width="60%"  background="/images/menu_bg1.gif" style="background-position: right; background-repeat: repeat-y" align="center" valign="top"  >
					<form id="project" name="projectform" method="post"  action="">
					
					<input type="hidden" name="jm_typeid" value="${jmtype_id}" />
					<input type="hidden" name="programe_file" value="${programe_files}" />
						<input type="hidden" name="projectdirectory" value="${program_JMurl}" />
						<input type="hidden" name="timecount" value="" />
						<input type="hidden" name="timecount2" value="" />
						<input type="hidden" name="timecount3" value="" />
						<input type="hidden" name="allips" value="" />
						<input type="hidden" name="startandend" value="" />
						<input type="hidden" name="templateid" value="${templateid}"/>
						<input type="hidden" name="program_treeid" value="0" />
						<div style="height: 30px;color:blue; font-weight: bold" >��Ŀ�����޸�</div>
						<fieldset style="width: 450px;  height: 400px;border:#6699cc 1 solid;" >
						<table style="width: 400px; height: 355px;" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td style="color:blue;font-size: 12px;" height="30px" >
									��ǰҪ���ŵĽ�Ŀ��
									<font color="red"><%=program_name%></font>
								</td>
							</tr>
							<tr>
								<td style="color:blue;" height="30px">
									��Ŀ�������ͣ�
								</td>
							</tr>
							<tr>
								<td height="30px">
									<input type="radio" name="playtype" id="ss1" checked value="1"  onclick="oninsert(1);"/>
									ѭ��
									<input type="radio" name="playtype" id="ss2"  value="2" onclick="oninsert(2);" />
									�岥
									<input type="radio" name="playtype" id="ss3"  value="3"  onclick="oninsert(3);"/>
									��ʱ
									
								</td>
							</tr>
							<tr>
								<td style="color:blue;" height="30px">
									��Ŀʱ�����ã�
								</td>
							</tr>
							<tr>
								<td height="30px">
								      <div  style="float:left">
											�������ڣ�
											<input class="Wdate button" size="11"  type="text"
												name="startdate" readonly="readonly" id="startdateid"
												onfocus="new WdatePicker(this)" MINDATE="#Year#-#Month#-#Day#"
												value="${play_start_time}" />&nbsp;&nbsp;&nbsp;&nbsp;
									  </div>
									  <div id="endone" style="float:left">  
											~&nbsp;&nbsp;&nbsp;&nbsp;
											<input class="Wdate button" size="11"  type="text"
												name="enddate" readonly="readonly" id="enddateid"
												onfocus="new WdatePicker(this)" MINDATE="#Year#-#Month#-#Day#"
												value="${play_end_time}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									  </div>
								</td>
							</tr>
							<tr>
								<td height="30px">
								       <div id="forever" style="height:30px; display: none;">
								          <input type="radio" name="playtype" value="4" id="ss4"   onclick="oninsert(4);"/>&nbsp;����ѭ��
								       </div>
									   <div id="starttwo" style="display:block">
									       ����ʱ����
									      <input maxlength="5" value="${playsecond}" onkeyup="this.value=this.value.replace(/\D/g,'');"  onafterpaste="this.value=this.value.replace(/\D/g,'');"
									       style="border:#183ead 1px solid;background-color:#ddddff" size="2" type="text"  name="minute" id="minuteid"/>&nbsp;&nbsp;����&nbsp;&nbsp;
									   </div>
									   
									    <div id="startone" style="float:left; display:none">
											ÿ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�죺
											<input class="Wdate button" size="8"  type="text" 
												name="starttime"  id="starttimeid"
												onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')" value="${day_stime0}" />
									   </div>
									   <div id="endtwo" style="display:none">
									        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input class="Wdate button" size="8" type="text"  
												name="endtime" id="endtimeid"
												onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')" value="${day_etime0}" />&nbsp;&nbsp;<font color="red">(*&nbsp;ÿ���ʱ�β��Ž�Ŀ)</font>
									    </div>
									    
									    <br/>
									    <div id="twostart" style="float:left; display:none">
									        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input class="Wdate button" size="8"  type="text" 
												name="twostarttime"  id="twostartid"
												onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')" value="${day_stime1}" />
									   </div>
									   <div id="twoend" style="display:none">
									        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input class="Wdate button" size="8" type="text"  
												name="twoendtime" id="twoendid"
												onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')" value="${day_etime1}" />&nbsp;&nbsp;<font color="red">(*&nbsp;ÿ���ʱ�β��Ž�Ŀ)</font>
									    </div>
									    
									     <br/>
									     <div id="threestart" style="float:left; display:none">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input class="Wdate button" size="8"  type="text" 
												name="threestarttime"  id="threestartid"
												onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')" value="${day_stime2}" />
									   </div>
									   <div id="threeend" style="display:none">
									        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input class="Wdate button" size="8" type="text"  
												name="threeendtime" id="threeendid"
												onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')" value="${day_etime2}" />&nbsp;&nbsp;<font color="red">(*&nbsp;ÿ���ʱ�β��Ž�Ŀ)</font>
									    </div>
				
								</td>
							</tr>
							<tr>
								<td align="center" height="50px">
									<input type="button" class="button1" onclick="onSendProgram();" value=" ��   �� " />
									&nbsp;&nbsp;&nbsp;
									<input type="button" class="button1" onclick="oncancel();" value=" ȡ  �� " />
								</td>
							</tr>
						</table>
					 </fieldset>
				   </form>
				</td>
				
	  	    </tr>
		</table>
	<script type="text/javascript">
oninsert(${type});
document.getElementById("ss${type}").checked="checked";
</script>	
	</body>
</html>
