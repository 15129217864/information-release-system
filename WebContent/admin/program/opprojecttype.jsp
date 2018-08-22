<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramHistoryDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Program"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    //System.out.println(request.getParameter("program_name"));
	String program_nametemp=request.getAttribute("program_name")==null?"":request.getAttribute("program_name").toString();
	String program_name =request.getParameter("program_name"); //UtilDAO.getUTF(request.getParameter("program_name") == null ? program_nametemp: java.net.URLDecoder.decode(request.getParameter("program_name"),"UTF-8"));
	String program_file = request.getParameter("program_file") == null ? "": request.getParameter("program_file");
	String templateid=request.getParameter("templateid") == null ? "5": request.getParameter("templateid");
    int i=  new ProgramHistoryDAO().getPlayProgramTimeCount(templateid,program_file,program_name); // 获取节目时长
    if(i==0)i=1;
	request.setAttribute("playsecond",i);
	
	ProgramDAO prodap= new ProgramDAO();
	String programefile[]= {"",program_file};
	Map<String,List<Program>> programmap= prodap.getProgram2(programefile);
	request.setAttribute("programmap",programmap);
	request.setAttribute("program_file",program_file);
    request.setAttribute("program_name",program_name);
    %>
<html>
	<head>
		<title>节目配置</title>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script type="text/javascript" src="/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" src="/js/vcommon.js"></script>
		<script type="text/javascript">
                   var hour=0;
                   var mmm=0;
                   var year=0;
                    var month=0;
                     var day=0;
		         function getNowTime(){   
		    		    //取得当前时间   
					    var now= new Date();   
					     year=now.getFullYear();   
					     month=(now.getMonth()+1)>9?(now.getMonth()+1):"0"+(now.getMonth()+1);   
					     day=now.getDate()>9?now.getDate():"0"+now.getDate();   
					     hour=now.getHours()>9?now.getHours():"0"+now.getHours(); 
					     mmm=now.getMinutes()>9?now.getMinutes():"0"+now.getMinutes();  
					     sss=now.getSeconds()>9?now.getSeconds():"0"+now.getSeconds();    
					    var nowdate=year+"-"+month+"-"+day; 
		                var nowtime=hour+":"+mmm+":00";   
					    document.getElementById("startdateid").value = nowdate;
					    document.getElementById("enddateid").value = nowdate;
				        document.getElementById("starttimeid").value = nowtime;
				 	}  
				  
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		     
		     
				  function getTimecount(date1,date2,time1,time2){//计算时间差
						var t1=date1+" "+time1;
						var t2=date2+" "+time2;
						//alert(date1+" "+time1+"===="+date2+" "+time2);
						var addm=Math.ceil((Date.parse(date1+" "+time2)-Date.parse(t1))/1000/60);//时间差(单位：秒
						//alert(addm);
						//var d=Math.floor(addm/60/24);
						//alert("d=="+d);
						//var m=addm-d*60*24
						//alert(m);
						//alert(m);
						return addm;
				  }
		
		function onSendProgram(){

		    var startdate=projectform.startdate.value;//替换字符，变成标准格式  
		    var enddate=projectform.enddate.value;//替换字符，变成标准格式 
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
		           selectedIndex=projectform.playtype[i].value;
		           break;
		        }
		    }
		    var ddd=getTimecount(year+"/"+month+"/"+day,year+"/"+month+"/"+day,hour+":"+mmm+":"+sss,"23:59:59");	
		    if (selectedIndex ==1){
			        if(minute==""){
			            alert("请输入播放时长！");
			            return;
			    	 }else if(minute<1){
			           alert("播放时长必须大于1分钟！");
			            return;
			    	 }else if(minute>ddd){
			            alert("播放时长必须小于当天24点！");
			            return;
			    	 }
			    	 if($("loop_type_for_id2").checked==true){//针对 定时循环 节目
						var d1 = startdate+" "+startime;  
						var d2 = enddate+" "+endtime;
						if(startdate.replace("-","/") > enddate.replace("-","/")){   
							alert("节目结束播放日期必须要大于开始播放日期！");
						    return;
						}
					}
				    projectform.timecount.value=minute;
				    
			}if (selectedIndex ==2){
			        if(minute==""){
			            alert("请输入播放时长！");
			            return;
			    	 }else if(minute<1){
			           alert("播放时长必须大于1分钟！");
			            return;
			    	 }else if(minute>ddd){
			            alert("播放时长必须小于当天24点！");
			            return;
			    	 }
			    	   projectform.timecount.value=minute;
			}
			else if(selectedIndex ==3){//定时节目判断
						if(startime==""){
				            alert("请检查第一选项开始播放时间！");
				             return;
				    	}else if( endtime==""){
				    	 	alert("请检查第一选项结束播放时间！");
				            return;
				    	}
				    	
				    	if((twostartime==""&&twoendtime!="")||(twostartime!=""&&twoendtime=="")){
				    	    alert("请检查第二选项的开始播放时间或结束播放时间！");
				            return;
				    	}
				    	if((threestartime==""&&threeendtime!="")||(threestartime!=""&&threeendtime=="")){
				    	    alert("请检查第三选项的开始播放时间或结束播放时间！");
				            return;
				    	}
				    	
				    	
						if(startime >= endtime
						        ||((twostartime!=""&&twoendtime!="")&&twostartime >= twoendtime)
						        ||((threestartime!=""&&threeendtime!="")&&threestartime >= threeendtime)){   
							alert("节目结束播放时间必须要大于开始播放时间！");
					    	return;
						}
						timecounts=getTimecount(startdate.replace("-","/"),enddate.replace("-","/"),startime,endtime);
						//if(timecounts<=1){
					   //    alert("节目播放时间必须要大于一分钟！");
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
						   //   alert("请检查第二选项节目播放时间必须要大于一分钟！");
					      //    return false;
						  // }
						   projectform.timecount2.value=timecounts2;
						   //alert("timecounts2==="+timecounts2);
						}
						
						
						if(threestartime!=""&&threeendtime!=""){
						    timecounts3=getTimecount(startdate.replace("-","/"),enddate.replace("-","/"),threestartime,threeendtime);
		                   // if(timecounts3<=1){
						   //   alert("请检查第三选项节目播放时间必须要大于一分钟！");
					       //   return false;
						  // }
						   projectform.timecount3.value=timecounts3;
						   //alert("timecounts3==="+timecounts3);
		                }
			}
			//projectform.action="/admin/program/sendprogram.jsp";
			projectform.action="/rq/aheadcreateproject";
			projectform.submit();
	   }
			
	   function $(id){
	     return document.getElementById(id);
	   }
			
	   function oninsert(obj){//startone  starttwo  
	   
	        if(obj==2){ //插播
	            projectform.startandend.value=$("startdateid").value;

				$("enddateid").value=document.getElementById("startdateid").value;
				//$("endtwo").style.display="none";
				//$("startone").style.display="none";
				//$("starttwo").style.display="block";
				//$("forever").style.display="none";
				//$("twostart").style.display="none";
				//$("twoend").style.display="none";
				//$("threestart").style.display="none";
				//$("threeend").style.display="none";
				
				$("loop_type_tr_id").style.display="none";
				$("play_length_tr_id").style.display="";
				$("data_time_tr_id").style.display="none";
				$("active_tr_id").style.display="none";
	        }else if(obj==1){//循环
	            projectform.startandend.value="";
				//$("enddateid").disabled="";
				//$("startdateid").disabled="";
				$("enddateid").value=document.getElementById("startdateid").value;
				//$("endone").style.display="block";
				//$("endtwo").style.display="none";
				//$("startone").style.display="none";
				//$("starttwo").style.display="block";
				//$("forever").style.display="block";
				//$("twostart").style.display="none";
				//$("twoend").style.display="none";
			
				//$("threestart").style.display="none";
				//$("threeend").style.display="none";
				
				
				$("loop_type_tr_id").style.display="";
				$("play_length_tr_id").style.display="";
				$("data_time_tr_id").style.display="none";
				$("active_tr_id").style.display="none";
				$("loop_type_for_id").checked="checked";
				
		    }else if(obj==3){//定时
			    getNowTime();
			    projectform.startandend.value="";
				//$("startdateid").disabled="";
				//$("enddateid").disabled="";
				$("enddateid").value=document.getElementById("startdateid").value;
				
				//$("endone").style.display="";
				//$("endtwo").style.display="";
				
				//$("twostart").style.display="";
				//$("twoend").style.display="";
			
				//$("threestart").style.display="";
				//$("threeend").style.display="";

				//$("startone").style.display="";
				//$("starttwo").style.display="none";
				
				//$("forever").style.display="none";
				
				$("loop_type_tr_id").style.display="none";
				$("play_length_tr_id").style.display="none";
				$("data_time_tr_id").style.display="";
				$("active_tr_id").style.display="";
				
				
				
			}
		}
		function showdate_time_tr(){
		
		
		}
	function deletetype(typeid){
	  	if(confirm("确认删除？")){
	  		window.location.href="/admin/program/deletetype1.jsp?t_id="+typeid+"&programe_file=${program_file}&program_name=${programname==''?program_name:programname}&templateid=<%=templateid%>";
	  	}
	  }
	function newSendProject(type_isnull){
		if(type_isnull==1){
			alert("当前节目未设置播放属性，请先添加节目属性！");
			return ;
		}
		//alert(parent.homeframe.content.content.location.href);
		//alert(parent.location.href);
		parent.closedivframe(1);
		parent.homeframe.content.content.showDiv(2,"发送节目",900,500,"/admin/program/selectclientIP.jsp?programe_file=!${program_file}&t=" + new Date().getTime());
		
		}
	 function closedivframe(){
		parent.closedivframe(2);
	}
	function update_program_type(e,type_id,p_name){
parent.parent.showDiv("【"+p_name+"】节目属性修改",400,200,"/admin/program/update_program_type.jsp?programe_file=${program_file}&program_name=${programname==''?program_name:programname}&templateid=<%=templateid%>&type_id="+type_id+"&t=" + new Date().getTime());
}
    </script>
	</head>
	<body onload="getNowTime();">
		<table width="100%" height="100%" align="center" border="0" cellpadding="10" cellspacing="0">
			<tr>
				<td>
				<table border="0" width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<th width="10%" height="30px">播放类型</th>
								<th width="30%">播放日期</th>
								<th width="20%">播放时间</th>
								<th width="10%">时长</th>
								<th width="10%">操作</th>
								</tr>
						<tr>
						
			            <td class="Line_01" colspan="5"></td>
			          </tr>
			          <c:set var="programname" value=""></c:set>
						<tr>	<td colspan="6">
								<div style="width:100%;height: 150px;  overflow: auto">
									<table border="0" width="100%" cellpadding="0" cellspacing="0">
									<c:set var="type_isnull" value="0"></c:set>
									 <c:if test="${empty programmap}">
						   	    	<c:set var="type_isnull" value="1"></c:set>
									<tr><td colspan="5" style="color: red" align="center">当前节目未设置播放属性！</td></tr>
									</c:if>
						   	    <c:forEach items="${programmap}" var="program">
						   	    <c:if test="${empty program.value}">
						   	    	<c:set var="type_isnull" value="1"></c:set>
									<tr><td colspan="5" style="color: red" align="center">当前节目未设置播放属性！</td></tr>
									</c:if>
									    <c:forEach items="${program.value}" var="ppp">
									    <c:set var="programname" value="${program.key}"></c:set>
											<tr >
												<td  align="center" height="20px"  width="10%">${ppp.play_type_Zh }</td> 
												<c:choose>
												  <c:when test="${ppp.play_type==4}">
												     <td  width="30%" align="center" >全年</td>
												  </c:when>
												  <c:when test="${ppp.play_type==2}">
												     <td  width="30%" align="center" >当前时间</td>
												  </c:when>
												  <c:otherwise>
												     <td  width="30%" align="center" >${ppp.play_start_time} ～ ${ppp.play_end_time }</td>
												  </c:otherwise>
												</c:choose>
												<c:choose>
												  <c:when test="${ppp.play_type==1||ppp.play_type==4}">
												     <td  width="20%" align="center" >全天</td>
												  </c:when>
												   <c:when test="${ppp.play_type==2}">
												     <td  width="20%" align="center" >当前时间</td>
												  </c:when>
												  <c:otherwise>
												  <td  width="20%" align="center" >${ppp.day_stime1} - ${ppp.day_etime1}</td>
												  </c:otherwise>
												</c:choose>
												
												<td  width="10%" align="center" >${ppp.play_count }分钟</td>
												<td  width="10%"align="center" >
													<!-- <a style="color:blue" href="/admin/program/update_opprojecttype.jsp?templateid=${ppp.templateid}&program_name=${program.key}&jmtype_id=${ppp.program_JMid}&type=${ppp.play_type}&jmurl=${ppp.program_JMurl}&programe_files=${programe_file}">修改</a>&nbsp;&nbsp; -->
													 <a style="color:blue" href="javascript:;" onclick="update_program_type(this,'${ppp.program_JMid}','${programname}');">修改</a>&nbsp;&nbsp; 
													<a style="color:blue" onclick="deletetype('${ppp.program_JMid}');return false" href="javascript:;">删除</a></td>
											</tr>
											<tr>
			            <td class="Line_01" colspan="5"></td>
			          </tr>
										</c:forEach>
								</c:forEach>
									</table>	</div>
							</td>
						</tr>
						
						
				</table>
				
				</td>
			</tr>
			<tr>
				<td width="60%"  align="center" valign="top"  >
					<form id="project" name="projectform" method="post"  action="">
						
						<input type="hidden" name="playname" value="${programname==''?program_name:programname}" />
						<input type="hidden" name="projectdirectory" value="<%=program_file%>" />
						<input type="hidden" name="timecount" value="" />
						<input type="hidden" name="timecount2" value="" />
						<input type="hidden" name="timecount3" value="" />
						<input type="hidden" name="allips" value="" />
						<input type="hidden" name="startandend" value="" />
						<input type="hidden" name="program_treeid" value="0" />
						<input type="hidden" name="templateid" value="<%=templateid%>" />
						
						<fieldset style="width: 450px;  height: 320px;border:#6699cc 1 solid;" >
						<legend align="center" style="font-size: 13px;font-weight: bold">添加节目属性</legend>
						
						  <table style="width: 430px; height: 300px;" border="0" cellpadding="0" cellspacing="0">
							  <tr>
								<td style="color:blue;font-size: 12px;" height="40px" colspan="2" >
									节目名称：
									<font color="red">${programname==''?program_name:programname}</font>
								</td>
							 </tr>
							 <tr>
								<td style="color:blue;" height="40px" width="100px">
									节目播放类型：
								</td>
								<td width="320px">
									<input type="radio" name="playtype" checked value="1"  onclick="oninsert(1);"/>
									循环
									
									<input type="radio" name="playtype" value="3"  onclick="oninsert(3);"/>
									定时
									
								</td>
							</tr>
							<tr>
								<td style="color:blue;" height="30px"  colspan="2">
									节目时间配置：
								</td>
							</tr>
							<tr id="loop_type_tr_id">
								<td height="30px"   colspan="2">
									 <input type="radio" name="loop_type" id="loop_type_for_id" value="1" checked="checked"  onclick="$('data_time_tr_id').style.display='none';"/>&nbsp;永久循环
								     <input type="radio" name="loop_type"  id="loop_type_for_id2"  value="2"  onclick="$('data_time_tr_id').style.display='';"/>&nbsp;定时循环
								</td>
							</tr>
							<tr style="display: none" id="data_time_tr_id">
								<td  colspan="2" align="center" width="100%" height="40px" " >
								      <div  style="float:left;width:176px;text-align: left;">
											播放日期：
											<input class="Wdate button" size="11"  type="text"
												name="startdate" readonly="readonly" id="startdateid"
												onfocus="new WdatePicker(this)" MINDATE="#Year#-#Month#-#Day#" value="" />
												~
									  </div>
									  <div id="endone" style="float:left;">  
											<input class="Wdate button" size="11"  type="text"
												name="enddate" readonly="readonly" id="enddateid"
												onfocus="new WdatePicker(this)" MINDATE="#Year#-#Month#-#Day#"
												value="" />
									  </div>
								</td>
							</tr>
							<tr  id="play_length_tr_id">
								<td height="40px"  colspan="2" >
									   <div id="starttwo" >
									       播放时长：
									      <input maxlength="5" value="${playsecond}" onkeyup="this.value=this.value.replace(/\D/g,'');"  onafterpaste="this.value=this.value.replace(/\D/g,'');"
									       style="border:#183ead 1px solid;background-color:#ddddff" size="2" type="text"  name="minute" id="minuteid"/>&nbsp;&nbsp;分钟&nbsp;&nbsp;
									   </div>
									</td>
							</tr>   
							<tr style="display: none" id="active_tr_id">
								<td height="80px"  colspan="2" >
									<table width="100%" height="100%" cellpadding="0" cellspacing="0">
										<tr>
											<td width="65">每&nbsp;&nbsp;天：</td>
											<td align="left"><input class="Wdate button" size="11"  type="text" 
												name="starttime"  id="starttimeid"
												onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')" value="" />&nbsp;~</td>
											<td><input class="Wdate button" size="11" type="text"  
												name="endtime" id="endtimeid"
												onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')" value="" /></td>
											<td><font color="red">(*&nbsp;每天此时段播放节目)</font></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td><input class="Wdate button" size="11"  type="text" 
												name="twostarttime"  id="twostartid"
												onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')" value="" />&nbsp;~</td>
											<td><input class="Wdate button" size="11" type="text"  
												name="twoendtime" id="twoendid"
												onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')" value="" /></td>
											<td><font color="red">(*&nbsp;每天此时段播放节目)</font></td>
										</tr>
									<tr>
											<td>&nbsp;</td>
											<td><input class="Wdate button" size="11"  type="text" 
												name="threestarttime"  id="threestartid"
												onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')" value="" />&nbsp;~</td>
											<td><input class="Wdate button" size="11" type="text"  
												name="threeendtime" id="threeendid"
												onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')" value="" /></td>
											<td><font color="red">(*&nbsp;每天此时段播放节目)</font></td>
										</tr>
									</table>
									    
								</td>
							</tr>
							<tr>
								<td  colspan="2">
								</td>
							</tr>
							<tr>
								<td align="center" valign="top" height="50px"  colspan="2">
								<input type="button" class="button1" onclick="newSendProject(${type_isnull});" value=" 发送节目 " />&nbsp;&nbsp;&nbsp;
									<input type="button" class="button1" onclick="onSendProgram();" value=" 添  加 " />
									&nbsp;&nbsp;&nbsp;
									<input type="button" class="button1" onclick="closedivframe();" value=" 取  消 " />
								</td>
							</tr>
						</table>
					 </fieldset>
				   </form>
				</td>
				
	  	    </tr>
		</table>
	<c:if test="${addtype=='addok'}">
		<script type="text/javascript">
		if(parent.homeframe.content.content.location){
			parent.homeframe.content.content.location.reload();
		}
		alert("添加节目属性成功！");
		
		</script>
	</c:if>	
	<c:if test="${deltype=='delok'}">
		<script type="text/javascript">
		if(parent.homeframe.content.content.location){
			parent.homeframe.content.content.location.reload();
		}
		alert("删除节目属性成功！");</script>
	</c:if>	
	</body>
</html>
