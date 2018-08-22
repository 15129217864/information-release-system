<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.domin.Program"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String type_id=request.getParameter("type_id")==null?"":request.getParameter("type_id");
		Program p=new ProgramDAO().getProgram_type(type_id);
		
		String programe_file=request.getParameter("programe_file");
String templateid=request.getParameter("templateid");
	String program_name = request.getParameter("program_name");
		request.setAttribute("p",p);
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
		             window.onload = function getNowTime(){   

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
		function onSendProgram(jmid,type){
			if(type==3){
				 var startdate=projectform.startdate.value;//替换字符，变成标准格式  
			    var enddate=projectform.enddate.value;//替换字符，变成标准格式 
			    //------------
			    var startime=projectform.starttime.value;
			    var endtime=projectform.endtime.value;
				
				timecounts=getTimecount(startdate.replace("-","/"),enddate.replace("-","/"),startime,endtime);
						//if(timecounts<=1){
					   //    alert("节目播放时间必须要大于一分钟！");
					   //    return false;
					  //  }
		                projectform.minute.value=timecounts;
			}
		
		
			projectform.action="/admin/program/update_program_typeDO.jsp?programe_file=<%=programe_file%>&program_name=<%=program_name%>&templateid=<%=templateid%>&jmid="+jmid;
			projectform.submit();
	   }
    </script>
	</head>
	<body >
		<table width="100%" height="200" align="center" border="0" cellpadding="10" cellspacing="0">
	
			<tr>
				<td width="60%"  align="center" valign="top"  >
					<form id="project" name="projectform" method="post"  action="">
						<fieldset style="width: 350px ; height: 200px;border:#6699cc 1 solid;" >
						<legend align="center" style="font-size: 13px;font-weight: bold">修改节目属性</legend>
						<table style="width:  350px; height: 200px;" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td style="color:blue;" height="40px" width="100px">
									当前播放类型：
								</td>
								<td width="320px">
									${p.play_type_Zh }<input type="hidden" value="${p.play_type }" name="typeid"/>
								</td>
							</tr>
							<tr  style="${(p.play_type==2||p.play_type==4)?'display:none':'' };">
								<td height="40px"  colspan="2">
								      <div  style="float:left">
											播放日期：
											<input class="Wdate button" size="11"  type="text"
												name="startdate" readonly="readonly" id="startdateid"
												onfocus="new WdatePicker(this)" MINDATE="#Year#-#Month#-#Day#"
												value="${p.play_start_time }" />&nbsp;&nbsp;&nbsp;&nbsp;
									  </div>
									  <div id="endone" style="float:left">  
											~&nbsp;&nbsp;&nbsp;&nbsp;
											<input class="Wdate button" size="11"  type="text"
												name="enddate" readonly="readonly" id="enddateid"
												onfocus="new WdatePicker(this)" MINDATE="#Year#-#Month#-#Day#"
												value="${p.play_end_time }" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									  </div>
								</td>
							</tr>
							<tr   style="${p.play_type==3?'display:none':'' };">
								<td height="40px"  colspan="2" >
									   <div id="starttwo" >
									       播放时长：
									      <input maxlength="5" value="${p.play_count }" onkeyup="this.value=this.value.replace(/\D/g,'');"  onafterpaste="this.value=this.value.replace(/\D/g,'');"
									       style="border:#183ead 1px solid;background-color:#ddddff" size="2" type="text"  name="minute" id="minuteid"/>&nbsp;&nbsp;分钟&nbsp;&nbsp;
									   </div>
									</td>
							</tr>   
							<tr  style="${p.play_type==3?'':'display:none' };">
								<td height="40px"  colspan="2">
								      <div  style="float:left">
											每&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;天：
											<input class="Wdate button" size="11"  type="text" 
												name="starttime"  id="starttimeid"
												onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')" value="${p.day_stime1 }" />&nbsp;&nbsp;&nbsp;&nbsp;
									  </div>
									  <div id="endone" style="float:left">  
											~&nbsp;&nbsp;&nbsp;&nbsp;
											<input class="Wdate button" size="11" type="text"  
												name="endtime" id="endtimeid"
												onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')" value="${p.day_etime1 }" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									  </div>
								</td>
							</tr>
							<tr>
								<td  colspan="2">
								</td>
							</tr>
							<tr>
								<td align="center" valign="top" height="50px"  colspan="2">
									<input type="button" class="button1" onclick="onSendProgram('${p.program_JMid}','${p.play_type}');" value=" 修 改 " />
									&nbsp;&nbsp;&nbsp;
									<input type="button" class="button1" onclick="parent.closedivframe3();" value=" 取  消 " />
								</td>
							</tr>
						</table>
					 </fieldset>
				   </form>
				</td>
				
	  	    </tr>
		</table>
	<c:if test="${addtype=='updateok'}">
		<script type="text/javascript">
		if(parent.homeframe.content.content.location){
			parent.homeframe.content.content.location.reload();
		}
		alert("添加节目属性成功！");
		
		</script>
	</c:if>	
	</body>
</html>
