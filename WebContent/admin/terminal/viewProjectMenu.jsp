<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ProgramHistoryDAO" />
<jsp:directive.page import="com.xct.cms.utils.UtilDAO" />
<jsp:directive.page import="com.xct.cms.domin.ProgramHistory"/>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	response.setHeader("Cache-Control","no-store"); 
	response.setHeader("Pragrma","no-cache"); 
	response.setDateHeader("Expires",0); 
	
	String centip = request.getParameter("resips") == null ? "": request.getParameter("resips").toString();
	String cntmac = request.getParameter("cntmac") == null ? "": request.getParameter("cntmac").toString();
	
	String opp = request.getParameter("opp") == null ? "0": request.getParameter("opp").toString();
	String nowtime = UtilDAO.getNowtime("yyyy-MM-dd");
	request.setAttribute("search_time",nowtime);
	request.setAttribute("nowtime",nowtime);
	ProgramHistoryDAO hisdao = new ProgramHistoryDAO();
	
	if("1".equals(opp)){
		String dtime = request.getParameter("dtime");
		if(centip.indexOf("!")>-1)centip=centip.split("!")[1];
		String str = " where (program_isloop=3 or program_setdatetime= '" + dtime+ "') and program_issend='2' and program_delid='" + cntmac + "' order by program_SetDate";
		//List<ProgramHistory> historyProgramList = hisdao.getSomeProgramList(str);	
		List<ProgramHistory> historyProgramList = hisdao.getSomeProgramListMenu(str);	
		ProgramHistory programhistory=null;
		if(null!=historyProgramList&&!historyProgramList.isEmpty()){				
			for(int i=0,n=historyProgramList.size();i<n;i++){
				programhistory=historyProgramList.get(i);
				  programhistory.setXystatus("��  ��");
			      if(programhistory.getProgram_typeEn().equals("loop")){
			          programhistory.setXyenddatetime(programhistory.getProgram_PlaySecond()+" ����");
			       }
			      else
			          programhistory.setXyenddatetime(programhistory.getProgram_SetDate().split(" ")[1]+"-"+programhistory.getProgram_EndDate().split(" ")[1]);//�������ʱ��
			     
			}
		}
		request.setAttribute("search_time",dtime);
		request.setAttribute("historyProgramList", historyProgramList); 
	
	}else{
		if(centip.indexOf("!")>-1)centip=centip.split("!")[1];
		String str = " where (program_isloop=3 or program_setdatetime= '" + nowtime+ "') and program_issend='2' and program_delid='" + cntmac + "' order by program_SetDate";
		//List<ProgramHistory> historyProgramList = hisdao.getSomeProgramList(str);	
		List<ProgramHistory> historyProgramList = hisdao.getSomeProgramListMenu(str);	
		ProgramHistory programhistory=null;
		String string="";
		String playclientstring="";
		if(null!=historyProgramList&&!historyProgramList.isEmpty()){
			Map<String,Terminal> terminals=FirstStartServlet.terminalMap;
	    	for (Map.Entry<String, Terminal> terminals_entry : terminals.entrySet()) {
				Terminal terminal=terminals_entry.getValue();
				if(centip.equals(terminal.getCnt_ip())){
					playclientstring=terminal.getCnt_playprojecttring();
					break;
				}
			}
							
			for(int i=0,n=historyProgramList.size();i<n;i++){
				  programhistory=historyProgramList.get(i);
				  String typeen=programhistory.getProgram_typeEn();
				  String jmurl=programhistory.getProgram_JMurl();
				  string=programhistory.getProgram_Name()+"#"+jmurl+"#"+
			      programhistory.getProgram_SetDate()+"#"+typeen+"#"+programhistory.getProgram_PlaySecond()+"#";
			     // System.out.println("playclientstring=="+playclientstring);
			     // System.out.println("string.trim======="+string.trim());
			      if(playclientstring.equals(string.trim())){
			         programhistory.setXystatus("play");
			      }else if("defaultloop".equals(typeen) &&playclientstring.indexOf(jmurl)>0&&playclientstring.indexOf("defaultloop")>0){
			         programhistory.setXystatus("play");
			      }else{
			       programhistory.setXystatus("����");
			      }
			       
			      if(programhistory.getProgram_typeEn().equals("loop")){
			          programhistory.setXyenddatetime(programhistory.getProgram_PlaySecond()+" ����");
			       }
			       else if(programhistory.getProgram_typeEn().equals("defaultloop")){
			          programhistory.setXyenddatetime("ȫ�첥��");
			       }
			      else
			          programhistory.setXyenddatetime(programhistory.getProgram_SetDate().split(" ")[1]+"-"+programhistory.getProgram_EndDate().split(" ")[1]);//�������ʱ��
			      if(programhistory.getProgram_typeEn().equals("active")){
						String nowdatetime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
						if(nowdatetime.compareTo(programhistory.getProgram_EndDate())>=0)
							 programhistory.setXystatus("<span style='color:red'>�ѷ���</span>");
						if(nowdatetime.compareTo(programhistory.getProgram_SetDate())<0)
							 programhistory.setXystatus("����");
				  }
			}
		}
		FirstStartServlet.client_program_menu.remove(centip+"_"+cntmac+"_lv0028");
		FirstStartServlet.client.allsend(cntmac,centip, "lv0028");
		request.setAttribute("historyProgramList", historyProgramList); 
	}
	request.setAttribute("resip",centip);
	request.setAttribute("resipmac",centip+"_"+cntmac);
	request.setAttribute("opp",opp);
	request.setAttribute("cntmac",cntmac);
	
	////////////////////////////�����ǻ�ȡ��������Ŀ��
%>
<html>
	<head>
		<title>My JSP 'viewProjectMenu.jsp' starting page</title>
		<link rel="stylesheet" href="/css/style.css" type="text/css"/>
		<script language="javascript" src="/js/vcommon.js"></script>
		 <script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		   <script type="text/javascript" src="/My97DatePicker/WdatePicker.js"></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<style type="text/css">
		   .showdiv { overflow-y:auto;overflow-x:hidden; height: 300px; width: 90%; border:solid 1px #6699cc; margin-left: 5px; padding-top: 0px} 
	    </style>
		<script type="text/javascript">
		
		   function deleteClientLeftPrograme(tb1,trid,jmurlValue){ 
				if(confirm("��ʾ��Ϣ��ɾ�����ؽ�Ŀ����ͻ��˽�Ŀ��Ҳ��ɾ����ȷ��ɾ����\r\r���������ѭ����Ŀ���Ժ�Ҳ��ɾ����")){
					document.getElementById("showiframeId").src="/admin/terminal/deleteClientMenu.jsp?cmd="+encodeURI(jmurlValue)+"&clentIp=${resip}&cntmac=${cntmac }&t=" + new Date().getTime();
					//alert(document.getElementById("showiframeId").src);
					var i=tb1.parentNode.parentNode.rowIndex;//�õ��кţ�ż��
					document.getElementById('leftTable').deleteRow(i);
				
					var j=document.getElementById(trid+"r").rowIndex;
					document.getElementById('rightTable').deleteRow(j);
					
					//document.getElementById('leftTable').deleteRow(i+1);//ɾ������
					//document.getElementById('rightTable').deleteRow(j+1);//ɾ������
				
					alert("�ն�����ɾ����Ŀ�����Ժ�鿴��");
				}
			}
		
			function deleteClientRightPrograme(tb1,trid,jmurlValue){
				if(confirm("��ʾ��Ϣ��ɾ���ͻ��˽�Ŀ���󱾵ؽ�Ŀ��Ҳ��ɾ����ȷ��ɾ����")){
					document.getElementById("showiframeId").src="/admin/terminal/deleteClientMenu.jsp?cmd="+encodeURI(jmurlValue)+"&clentIp=${resip}&cntmac=${cntmac }&t=" + new Date().getTime();
				
					var i=tb1.parentNode.parentNode.rowIndex;
					document.getElementById('rightTable').deleteRow(i);
					
					var j=document.getElementById(trid+"l").rowIndex;
					document.getElementById('leftTable').deleteRow(j);
					
					//document.getElementById('rightTable').deleteRow(i+1);//ɾ������
					//document.getElementById('leftTable').deleteRow(j+1);//ɾ������
					alert("�ն�����ɾ����Ŀ�����Ժ�鿴��");
				}
			}
			
		    function getHiddenVaule(){

				  var temp="";
				  var lefttablebyid=document.getElementById("leftTable");
				  var lefttabletextvalue=lefttablebyid.getElementsByTagName("input");
		
				  var righttablebyid=document.getElementById("rightTable");
				  var righttabletextvalue=righttablebyid.getElementsByTagName("input");
		
				  for(var i=0;i<lefttabletextvalue.length;i++){
					  for(var j=0;j<righttabletextvalue.length;j++){
						if(lefttabletextvalue[i].type=="hidden"&&righttabletextvalue[j].type=="hidden"){
						   if(lefttabletextvalue[i].value==righttabletextvalue[j].value){
						      temp+=lefttabletextvalue[i].value;
						   }else{
						   
						   } 
						}
					  }
				   }
				   alert(temp);
	        }
	        
	        /////////////////////////�����ǻ�ȡ�ͻ��˽�Ŀ��
    var timer=null;
    var time_num=0;
    var opp=${opp};
    var search_time='${search_time}';
    var nowtime='${nowtime}';
    var table_div="<table width='100%' cellpadding='0' cellspacing='0' border='0'  id='rightTable'>";
    
    function getcntProgramMenu(result_list){
    //alert(result_list.length);
    	if(opp==1&&search_time!=nowtime){
    		table_div+="<tr><td align='center' height='30px' valign='bottom'><span style='color: red'>&nbsp;&nbsp;�ն��޽�Ŀ!</span><br /><br /></td></tr>";
			table_div+="</table>"
    		document.getElementById("cnt_program_menu_id").innerHTML=table_div;
    		clearInterval(timer);
    	}else{
			if(result_list.length==0)
			   time_num++;
			   
			if(result_list.length>0){
				for(i=0;i<result_list.length;i++){
					if(result_list[i].program_Name=='default'){
						 table_div+="<tr><td align='center' height='30px' valign='bottom'>���ڲ���<span style='color: blue'>&nbsp;&nbsp;Ĭ�Ͻ�Ŀ!</span><br /><br /></td></tr>";
						break;
					}else{
						if(result_list[i].xystatus=='play'){
						table_div+="<tr id='"+result_list[i].project_url_datetime+"r' style='background-color:#99CCFF' title='��ǰ���ڲ��Ÿý�Ŀ..'>"
						}else{
						 table_div+="<tr id='"+result_list[i].project_url_datetime+"r' >"
						 }
						 var onclick_str="onclick=deleteClientRightPrograme(this,'"+result_list[i].project_url_datetime+"','"+result_list[i].program_Name+"!"+result_list[i].program_JMurl+"!"+result_list[i].program_SetDate.replace(/\s/g,"_")+"!"+result_list[i].program_type+"!"+result_list[i].program_long+"!')";
						var playdatetime=result_list[i].program_SetDate.replace(/\s/g,"_").split("_")[0];
						var _v="";
						if(nowtime>playdatetime){
							_v="style='color:red' title='��"+playdatetime+"���Ľ�Ŀ'";
						}
						table_div+="<td align='center' width='30%' height='20px' "+_v+" >"+result_list[i].program_Name+"</td>"
						+"<td align='center' width='30%' >"+result_list[i].xyenddatetime+"</td>"
						+"<td align='center' width='10%' >"+result_list[i].xystatus+"</td>"
						+"<td align='center' width='15%' >"+result_list[i].program_typeZh+"</td>"
						+"<td align='center' width='5%' ><a href=javascript:; "+onclick_str+" ><img src='/images/del1.gif' width='10px' border='0' /></a></td>"
						+"<tr><td class='Line_01' colspan='5'></td></tr>";
					}
				}
				table_div+="</table>"
				document.getElementById("cnt_program_menu_id").innerHTML=table_div;
				clearInterval(timer);
			}
			
			if(time_num >50){//��ǰ��8
				table_div+="<tr><td align='center' height='30px' valign='bottom'><span style='color: red'>&nbsp;&nbsp;��ȡ��Ŀ��ʧ��!</span><br /><br /></td></tr>";
				table_div+="</table>"
				document.getElementById("cnt_program_menu_id").innerHTML=table_div;
				clearInterval(timer);
				
			}
		}
    }
   function goMenu(ddd_time){
        var ddd_time=document.getElementById("s_time_id").value;
     	window.location.href="/admin/terminal/viewProjectMenu.jsp?resips=${resip}&cntmac=${cntmac}&opp=1&dtime="+ddd_time;
   }
    timer= setInterval("DwrClass.getcntProgramMenu('${resipmac}','lv0028',getcntProgramMenu)", 1000);
	</script>

	</head>
	<body>
	
		<table width="780" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="2">
					<table border="0" width="100%">
						<tr >
							<td width="51%">
								<table cellpadding="0" cellspacing="0" border="0" width="100%">
									<tr>
										<td colspan="4" style="font-weight: bold; color: #1C8EF6"  height="30px" align="center">
											���ء�${search_time}����Ŀ��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input class="Wdate" size="14" type="text" id="s_time_id"  onfocus="new WdatePicker(this,null,false,'whyGreen')"  value="${search_time}"/>
											&nbsp;&nbsp;<input type="button" value="��ѯ" class="button" onclick="goMenu()"/>
										</td>
									</tr>
									<tr>
										<td width="30%" align="center">
											��Ŀ��
										</td>
										<td width="30%" align="center">
											ʱ��
										</td>
										<td width="10%" align="center">
											״̬
										</td>
										<td width="20%" align="center">
											��������
										</td>
									</tr>
								</table>
							</td>
							<td width="50%">
								<table cellpadding="0" cellspacing="0" border="0" width="100%">
									<tr>
										<td style="font-weight: bold; color: #1C8EF6" colspan="4" height="30px" align="center">
											�նˡ�${search_time}����Ŀ��
										</td>
									</tr>
									<tr>
										<td width="30%" align="center">
											��Ŀ��
										</td>
										<td width="30%" align="center">
											ʱ��
										</td>
										<td width="10%" align="center">
											״̬
										</td>
										<td width="20%" align="center" colspan="2">
											��������
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="50%" valign="top">
					<div class="showdiv" style="width:100%" align="left">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="leftTable">
								      <c:if test="${ empty requestScope.historyProgramList}">
									        <tr>
												<td align="center">
														<span style="color: red">�����޽�Ŀ!</span>
													<br />
												</td>
											</tr>
								       </c:if>
								       <c:forEach var="histroyProgram" items="${historyProgramList}">
								                      <tr id="${histroyProgram.project_url_datetime}l" ${histroyProgram.xystatus=='play'?"style='background-color:#99CCFF' title='��ǰ���ڲ��Ÿý�Ŀ..'":""} >
															<td align="center" width="30%" height="20px" >
																${histroyProgram.program_Name }
															</td>
															<td align="center" width="30%">
																${histroyProgram.xyenddatetime } 
															</td>
															<td align="center" width="10%">
																${histroyProgram.xystatus }
															</td>
															<td align="center" width="15%">
																${histroyProgram.program_typeZh1 }
															</td>
															 <c:if test="${not empty lg_authority}">
												                <c:set var="str" value="${lg_authority}"/>
												                <c:if test="${fn:contains(str,'K')}">
																	<td align="center" width="5%" >
																	   <a href="javascript:;" onclick="deleteClientLeftPrograme(this,'${histroyProgram.project_url_datetime}','${histroyProgram.program_Name}!${histroyProgram.program_JMurl}!${histroyProgram.program_SetDate}!${histroyProgram.program_typeEn}!${histroyProgram.program_PlaySecond}!${histroyProgram.isforover}')"><img src="/images/del1.gif" width="10px" border="0" /></a>
																	</td>
																</c:if>
															</c:if>
													  </tr>
													  <tr>
											             <td class="Line_01" colspan="5"></td>
											          </tr>
							            </c:forEach>
						</table>
					</div>
				</td>
				<td width="50%" valign="top">
					<div class="showdiv"  style="width:100%" align="right" id="cnt_program_menu_id">
						<table width="100%" cellpadding="0" cellspacing="0" border="0"  id="rightTable">
								<tr>
									<td align="center">
													<br />
													<img src="/images/mid_giallo.gif" />&nbsp;���ڻ�ȡ�ն˽�Ŀ�������Ժ�...
													<br />
													<br />
												</td>
								</tr>
							</table>
							
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right" height="50px"><br/>
					<input type="reset" class="button1" value=" �� �� "
						onclick="parent.closedivframe(1);">&nbsp;&nbsp;&nbsp;
						<span style="color: red;width: 200px;margin-right:150px">��ʾ��Ϣ���ն˺�ɫ��ĿΪ���ڽ�Ŀ</span>
						<br/>
				</td>
			</tr>
		</table>

		<div style="display: none;">
			<iframe src="" id="showiframeId"></iframe>
		</div>
	</body>
</html>
