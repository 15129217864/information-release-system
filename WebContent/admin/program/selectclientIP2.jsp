<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.xy.dao.ConnectionFactory"/>
<jsp:directive.page import="com.xct.cms.utils.GetGroupChange"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Program"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
   String username="";
		ConnectionFactory connectionfactory = new ConnectionFactory();
		GetGroupChange getgroupchange = new GetGroupChange();
		Users user = (Users) request.getSession().getAttribute("lg_user");
	    username=user.getLg_name();
	    getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory.getAllLinkCnt(3),"tree_"+username+".xml");
	    //getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory.getAllIpAddress(" where (cnt_islink=1 or cnt_islink=2) and cnt_status=1 "),"tree_"+username+".xml");//���ɴ���ѡ�����״�˵�
		 String jmurl=request.getParameter("programe_file");
		ProgramDAO programdao= new ProgramDAO();
		
		Program program=programdao.getProgram(" and program_JMurl='"+jmurl+"'");
		request.setAttribute("program",program);
		request.setAttribute("programe_file","!"+jmurl);
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
				//	$('tr.parent').click(function(){   // ��ȡ��ν�ĸ���
				//			$(this)
				//				.toggleClass("selected")   // ���/ɾ������
				//				.siblings('.child_'+this.id).toggle();  // ����/��ʾ��ν������
				//	}).click();
				//})
function getStrCount(scrstr,armstr)
{ //scrstr Դ�ַ��� armstr �����ַ�
 var count=0;
 while(scrstr.indexOf(armstr) >=1 )
 {
    scrstr = scrstr.replace(armstr,"")
    count++;    
 }
 return count;
}

function checkProgram(){
	DwrClass.getModuleMediaByProgrameUrl('${programe_file}',SendProgram);
}

function SendProgram(result){
	if(result=='yes'){
		 var checkips=tree.getAllChecked();
			           if(checkips==""){
						  alert("��ʾ��Ϣ����ѡ���նˣ�");
						  return  false;
						}
						var checkipArray=checkips.split(",");
						var reips="";
						for(i=0;i<checkipArray.length;i++){
						   if(checkipArray[i].indexOf("_")!=-1){
							  if(isIP(checkipArray[i].split("_")[0])){
								 reips+="!"+checkipArray[i].replace(",","!");
							  }
						   }
						}
						if(reips=="!" || reips==""){
							alert("��ʾ��Ϣ����ѡ���նˣ�");
							return false;
						}
						playcunt=projectform.playcunt.value;
						
						if(playcunt==""){
							alert("��ʾ��Ϣ��������岥ʱ����");
							return false;
						}
						
			            projectform.allips.value= reips;
			            DwrClass.checkProgramMenu2(reips,'${programe_file}',checkstatus);
			            progressShow();
	}else{
		alert(result);
		return ;
	}
}
			      var error_ips="";/////�г�ͻ���ն�IP��ַ
			      function showErrorMenu(){
				parent.parent.showDiv("�ն�ʱ���ͻ��Ŀ��",800,400,"/admin/program/showErrorProgramMenu.jsp?allips="+error_ips+"&programe_file=${programe_file}&t=" + new Date().getTime());
			      
			      }
			      function checkstatus(status){
			      progressBarHidden();
			      	if(status!=''){
			      		alert(status);
			      		return ;
			      	}else{
			      		projectform.action="/rq/aheadwriteproject?iscb=cb";
			            projectform.submit();
			      	}
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
							 <td valign="top"  width="80%">
										<table border="0" width="100%" cellpadding="0" cellspacing="0">
						
						<tr>
							<td colspan="6">
								<div style="width:100%;height: 400px;  overflow: auto">
									<table border="0" width="100%" cellpadding="0" cellspacing="0">
									   <tbody>
											<tr class="parent" id="row_1" >
											<td colspan="6" height="25px">��&nbsp;<b>��Ŀ���ƣ�${program.program_Name }</b></td></tr>
											<tr><td colspan="6" height="45px" style="padding-left: 50px">�岥ʱ����<input type="text" size="5" name="playcunt" value="5"  style="width: 55px;height: 18;pxime-mode:Disabled" onkeypress="return event.keyCode>=48&&event.keyCode<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" maxlength="3" /> ����</td></tr>
											<tr><td colspan="6" height="45px" style="padding-left: 50px;color: blue">��������Ҫ�岥��ʱ����</td></tr>
											<tr><td colspan="6" height="45px" style="padding-left: 50px;color: blue">˵�����岥��Ŀ�����ȼ�����ߣ��岥��ἰʱ���ţ�</td></tr>
									  </tbody>
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
									<td width="30%"><input type="button" id="send_button_id" class="button1" onclick="checkProgram();" value= "��һ��" /></td>
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
