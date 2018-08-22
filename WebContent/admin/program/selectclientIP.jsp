<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.xy.dao.ConnectionFactory"/>
<jsp:directive.page import="com.xct.cms.utils.GetGroupChange"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Program"/>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
   String username="";
	    String programe_file=request.getParameter("programe_file");//ѡ��Ҫ���͵Ľ�Ŀ����
	    String programefile[] = programe_file.split("!"); 
		ConnectionFactory connectionfactory = new ConnectionFactory();
		GetGroupChange getgroupchange = new GetGroupChange();
		Users user = (Users) request.getSession().getAttribute("lg_user");
		if(null==user){
		     return ;
		 }
	    username=user.getLg_name();
	     getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory.getAllLinkCnt(4),"tree_"+username+".xml");
	    //getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory.getAllIpAddress(" where (cnt_islink=1 or cnt_islink=2) and cnt_status=1 "),"tree_"+username+".xml");//���ɴ���ѡ�����״�˵�
	    ProgramDAO prodap= new ProgramDAO();
		Map<String,List<Program>> programmap= prodap.getProgram2(programefile);
		request.setAttribute("programmap",programmap);
		request.setAttribute("programe_file",programe_file);
		String nowdatetime=UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
		request.setAttribute("nowdate",nowdatetime.split(" ")[0]);
		request.setAttribute("nowtime",nowdatetime.split(" ")[1]);
		request.setAttribute("programe_file",programe_file);
%>
<html>
	<head>
		<title>��Ŀ����</title>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />

		<link rel="stylesheet" href="/admin/checkboxtree/style.css" type="text/css" media="screen" />
	    <link rel="stylesheet" type="text/css" href="/admin/checkboxtree/codebase/dhtmlx.css"/>
	    <script src="/admin/checkboxtree/codebase/dhtmlx.js"></script>
		<script language="javascript" src="/js/vcommon.js"></script>
		<script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<script src="/js/jquery1.4.2.js" type="text/javascript"></script>
        <style type="text/css">
			.parent		{background-color:F0F5F9;background-image:url('/images/title_background.gif'); background-repeat:repeat-X; height:30;}  /* ż������ʽ*/
			.odd		{ background-color:F0F5F9;}  /* ��������ʽ*/
			.selected		{ color:#fff;}
        </style>
       

		<script type="text/javascript">
		       // $(function(){
				//	$('tr.parent').click(function(){   // ��ȡ����
				//			$(this)
				//				.toggleClass("selected")   // ���/ɾ������
				//				.siblings('.child_'+this.id).toggle();  // ����/��ʾ����
				//	}).click();
				//})
				
					function getStrCount(scrstr,armstr){
						 //scrstr Դ�ַ��� armstr �����ַ�
						 var count=0;
						 while(scrstr.indexOf(armstr) >=1 ){
						 
						    scrstr = scrstr.replace(armstr,"")
						    count++;    
						 }
						 return count;
					}
					
					function checkProgram(isold,program_type){
						if(getStrCount(program_type,"2")>1){
							alert("��ʾ��Ϣ��һ��ֻ�ܷ���һ�����岥����Ŀ��");
							return;
						}
						if(isold==1){
							alert("��ʾ��Ϣ����Ŀ�б�����ʱ����ڵĽ�Ŀ�����޸Ĺ��ڽ�Ŀ�Ĳ���ʱ�䣡");
							return;
						}
						DwrClass.getModuleMediaByProgrameUrl('${programe_file}',SendProgram);
					}
					
					function SendProgram(result){
						if(result=='yes'){
							    var checkips=tree.getAllChecked();
						       //alert(checkips);
						        if(checkips==""){
								  alert("��ʾ��Ϣ����ѡ���նˣ�");
								  return  false;
								}
								//if(checkips.indexOf("_")!=-1)checkips=checkips.substring(0,checkips.indexOf("_"));
								//alert(checkips);
								
								var checkipArray=checkips.split(",");
								var reips="";
								for(i=0;i<checkipArray.length;i++){
								   if(checkipArray[i].indexOf("_")!=-1){
									  if(isIP(checkipArray[i].split("_")[0])){
										 reips+="!"+checkipArray[i].replace(",","!");
									  }
								   }
								}
								
								//for(i=0;i<checkipArray.length;i++){
								//        var selectstring=selectSubstring(checkipArray[i]);
								//		if(isIP(selectstring)){
								//			reips+="!"+selectstring.replace(",","!");
								//   	}
								// }
								if(reips=="!" || reips==""){
									alert("��ʾ��Ϣ����ѡ���ն�!!��");
									return false;
								}
							   //alert(reips);
					           projectform.allips.value= reips;
					           DwrClass.checkProgramMenu(reips,'${programe_file}',checkstatus);
					           progressShow();
						}else{
							alert(result);
							return ;
						}
					}
			     
			      function checkstatus(status){
				        
				         progressBarHidden();
				      	 if(status!=''){
				      		document.getElementById("error_id").style.display="block";
				      		error_ips=status;
				      		if(confirm("�ն˽�Ŀ��ʱ���г�ͻ��ȷ�ϼ������ͣ�")){
				      			projectform.action="/rq/aheadwriteproject";
				            	projectform.submit();
				      		}
				      	 }else{
				      		projectform.action="/rq/aheadwriteproject";
				            projectform.submit();
				      	 }
			      }
			      
			      	//��ʱ������»��ߣ�����������֤ip�Ĵ����������
					function selectSubstring(checkip){
						if(checkip.indexOf("_")!=-1)
						   checkip=checkip.substring(0,checkip.indexOf("_"));
						 return checkip;
					}
			      
			      var error_ips="";/////�г�ͻ���ն�IP��ַ
			      function showErrorMenu(){
				     parent.parent.showDiv("�ն�ʱ���ͻ��Ŀ��",800,400,"/admin/program/showErrorProgramMenu.jsp?allips="+error_ips+"&programe_file=${programe_file}&t=" + new Date().getTime());
			      }
			      
			       function oncancel(){
						window.location.href='/rq/programList?left_menu=&zu_id=no'; 
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
				  function deletetype(typeid){
				  	if(confirm("ȷ��ɾ����")){
				  		window.location.href="/admin/program/deletetype.jsp?t_id="+typeid+"&programe_file=${programe_file}";
				  	}
				  }
				   function closedivframe(){
						if(confirm("��ʾ��Ϣ��ȷ��ȡ�����ͽ�Ŀ��")){
							parent.closedivframe(2);
						}
				  }

         function progressBarOpen(){
  			var cWidth = document.body.clientWidth;
  			var cHeight= document.body.clientHeight;
			var divNode = document.createElement( 'div' );	
			divNode.setAttribute("id", "systemWorking");
			var topPx=(cHeight)*0.4-50;
			var defaultLeft=(cWidth)*0.4-50;
			divNode.style.cssText='position:absolute;margin:0;display:none;top:'+topPx+'px;left:'+defaultLeft+';width:215;height:59;background-image:url(/images/wait_background.gif);z-index:9999;text-align:center;padding-top:20'; 
			divNode.innerHTML= "<img src='/images/mid_giallo.gif' align=absmiddle><font style='font-size:12px'>&nbsp;������֤��Ŀ�����Ժ�...</font>";
			document.getElementsByTagName("body")[0].appendChild(divNode);
			 var bgObj=document.getElementById("bgDiv");
         }
           function progressShow() {
			   if(document.all.systemWorking)
				  document.all.systemWorking.style.display = "block";
			   document.getElementById("send_button_id").disabled="disabled";
		  }   
			
		   function progressBarHidden() {
		     if(document.all.systemWorking)
			    document.all.systemWorking.style.display = "none";
			  document.getElementById("send_button_id").disabled="";
		   }
			   
    </script>
	</head>
	<body onload="progressBarOpen();">
					<form id="project" name="projectform" method="post"  action="">
						<input type="hidden" name="allips" value="" />
						<input type="hidden" name="templateid" value="${templateid}" />
						<input type="hidden" name="programe_file" value="${programe_file }"/>
				       <table border="0" width="100%" height="400px" cellpadding="0" cellspacing="0">
				       <tr>
				           <td  valign="top" width="250px" align="left" height="100%">
								  <div id="addBox">
										<div class="taskBtn">
											<fieldset style="width:255px; height:100%px;border:#6699cc 1 solid;">
											<legend align="center" style="font-size: 13px; color: blue; font-weight: bold">ѡ���ն�</legend>
												<div id="treeboxbox_tree" style="float:left;width:250px; height:420px; overflow:auto;"></div>
												<div style="height: 20px" style="float:left;"> 
													 <div id="booksHeader" style="float:left;"></div>
														<table id="booksTable" border="0" width="100%" cellpadding="0"
															cellspacing="0" align="left">
															<tbody id="abc"></tbody>
														</table>
											      </div>
										     </fieldset>
									   </div>
								   </div>
							</td>
							 <td valign="top" width="550px">
							   <table border="0" width="100%" cellpadding="0" cellspacing="0">
									<thead>
										<tr>
											<th width="25%" height="30px">��Ŀ��</th>
											<th width="12%">��������</th>
											<th width="25%">��������</th>
											<th width="19%">����ʱ��</th>
											<th width="9%">ʱ��</th>
											<th width="10%">����</th>
											</tr>
									</thead>
									<tr>
										<td colspan="6">
											<div style="width:100%;height: 400px;  overflow: auto">
												<table border="0" width="100%" cellpadding="0" cellspacing="0">
			
												<c:if test="${not empty programmap}">
										<tbody>
										    <c:set var="i" value="0"/>
										    <c:set var="isold" value="0"></c:set>
										    <c:set var="all_program_type" value=""/>
									   	    <c:forEach items="${programmap}" var="program">
												<tr class="parent" id="row_<c:out value='${i+1}'/>" >
												<td colspan="6" height="25px">&nbsp;&nbsp;��&nbsp;<b>${program.key}</b></td></tr>
												<c:if test="${empty program.value}">
													<tr><td colspan="6" height="25px" style="padding-left: 50px">�ý�Ŀδ���ò������ԣ�Ĭ�ϲ�������Ϊ������ѭ����</td></tr>
												</c:if>
												
												    <c:forEach items="${program.value}" var="ppp">
														<tr class="child_row_<c:out value='${i+1}'/>">
															<td  align="center"  width="25%" height="25px">${program.key}</td>
															<td  align="center"  width="12%">${ppp.play_type_Zh }</td> 
															 <c:set var="all_program_type" value="${all_program_type},${ppp.play_type}"/>
															<c:choose>
															  <c:when test="${ppp.play_type==4}">
															     <td  width="25%" align="center" >ȫ��</td>
															  </c:when>
															  <c:when test="${ppp.play_type==2}">
															     <td  width="25%" align="center" >��ǰʱ��</td>
															  </c:when>
															  <c:otherwise>
															  		<c:if test="${nowdate>ppp.play_end_time}">
															  			<c:set var="isold" value="1"></c:set>
															  		</c:if>
															     	<td  width="25%" align="center" ${nowdate>ppp.play_end_time?'style="color:red" title="ʱ�����"':''} >${ppp.play_start_time} �� ${ppp.play_end_time }</td>
															  </c:otherwise>
															</c:choose>
															<c:choose>
															  <c:when test="${ppp.play_type==1||ppp.play_type==4}">
															     <td  width="19%" align="center" >ȫ��</td>
															  </c:when>
															   <c:when test="${ppp.play_type==2}">
															     <td  width="19%" align="center" >��ǰʱ��</td>
															  </c:when>
															  <c:otherwise>
															 	 <c:if test="${nowdate==ppp.play_end_time&&nowtime>ppp.day_etime1}">
															  			<c:set var="isold" value="1"></c:set>
															  		</c:if>
															  <td  width="19%" align="center"  ${nowdate==ppp.play_end_time&&nowtime>ppp.day_etime1?'style="color:red" title="ʱ�����"':''}>${ppp.day_stime1} - ${ppp.day_etime1}</td>
															  </c:otherwise>
															</c:choose>
															
															<td  width="9%" align="center" >${ppp.play_count }����</td>
															<td  width="10%"align="center" >
																<!-- <a style=" color:blue;${ppp.play_type==2?'display:none;':''}" href="/admin/program/update_opprojecttype.jsp?templateid=${ppp.templateid}&program_name=${program.key}&jmtype_id=${ppp.program_JMid}&type=${ppp.play_type}&jmurl=${ppp.program_JMurl}&programe_files=${programe_file}">�޸�</a>&nbsp; -->
																<a style="color:blue" onclick="deletetype('${ppp.program_JMid}');return false" href="javascript:;">ɾ��</a></td>
														</tr>
													</c:forEach>
										
												 <c:set var="i" value="${i+1}"/>
											</c:forEach>
										</tbody>
								     </c:if>
									</table>
								  </div>
							    </td>
							  </table>
						   </td>
						</tr>
						<tr>
			            <td class="Line_01"  colspan="2"></td>
			          </tr>
						 <tr>
							<td align="left" style="padding-left: 350px" height="50px" colspan="2"  >
							<table width="300px">
								<tr>
									<td width="30%">
									   <input type="button" id="send_button_id" class="button1" onclick="checkProgram('${isold}','${all_program_type}');" value= "��һ��" />
									</td>
									<td width="30%"><input type="button" class="button1" onclick="closedivframe();" value=" ȡ  �� " /></td>
									<td width="40%">&nbsp;<a href="javascript:;" onclick="showErrorMenu()" id="error_id" style="color: red; display: none;">�鿴ʱ���ͻ</a></td>
								</tr>
							</table>
							 </td>
						</tr>
				
				         </table> 
			      </form> 
				 <script language="JavaScript">
  
   /**   ��һ����������treeboxbox_tree��������<DIV>�е�idֵ��Ӧ��
		  �ڶ��������� ���Ŀ��Ϊ100%��
		  ������������ ���ĸ߶�Ϊ100%��
		  ���ĸ������� ���ĸ��ڵ�ID��ֵΪ0��
  **/
		  var tree = new dhtmlXTreeObject("treeboxbox_tree", "100%", "100%", 0);
			//tree.setSkin('dhx_skyblue');
			//tree.setImagePath("/admin/checkboxtree/csh_bluebooks/");
			
			//tree.enableTreeImages("-Icons");//�����Ƿ���ʾͼ��
          //  tree.enableTreeLines("-Lines");//�����Ƿ���ʾ������
           // tree.enableTextSigns("Cross Signs");//�����Ƿ���ʾ������(������)
            
			tree.setImagePath("/admin/checkboxtree/codebase/imgs/dhxtree_skyblue/");
			tree.enableCheckBoxes(1);
			tree.enableThreeStateCheckboxes(true);
			//tree.loadXML("/admin/checkboxtree/tree_<%=username%>.xml");
			tree.load("/admin/checkboxtree/tree_<%=username%>.xml");
			
			// �������ݣ�����д�����ǵ�action����·������
			//tree.loadXML("/org/buildTree.action");
        </script>
  
	</body>
</html>
