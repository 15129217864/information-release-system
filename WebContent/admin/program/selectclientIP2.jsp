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
	    //getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory.getAllIpAddress(" where (cnt_islink=1 or cnt_islink=2) and cnt_status=1 "),"tree_"+username+".xml");//生成带复选框的树状菜单
		 String jmurl=request.getParameter("programe_file");
		ProgramDAO programdao= new ProgramDAO();
		
		Program program=programdao.getProgram(" and program_JMurl='"+jmurl+"'");
		request.setAttribute("program",program);
		request.setAttribute("programe_file","!"+jmurl);
%>
<html>
	<head>
		<title>节目配置</title>
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
			.parent		{background-color:F0F5F9;background-image:url('/images/title_background.gif'); background-repeat:repeat-X; height:30;}  /* 偶数行样式*/
			.odd		{ background-color:F0F5F9;}  /* 奇数行样式*/
			.selected		{ color:#fff;}
        </style>
       

		<script type="text/javascript">
		       // $(function(){
				//	$('tr.parent').click(function(){   // 获取所谓的父行
				//			$(this)
				//				.toggleClass("selected")   // 添加/删除高亮
				//				.siblings('.child_'+this.id).toggle();  // 隐藏/显示所谓的子行
				//	}).click();
				//})
function getStrCount(scrstr,armstr)
{ //scrstr 源字符串 armstr 特殊字符
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
						  alert("提示信息：请选择终端！");
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
							alert("提示信息：请选择终端！");
							return false;
						}
						playcunt=projectform.playcunt.value;
						
						if(playcunt==""){
							alert("提示信息：请输入插播时长！");
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
			      var error_ips="";/////有冲突的终端IP地址
			      function showErrorMenu(){
				parent.parent.showDiv("终端时间冲突节目单",800,400,"/admin/program/showErrorProgramMenu.jsp?allips="+error_ips+"&programe_file=${programe_file}&t=" + new Date().getTime());
			      
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
				  	if(confirm("确认删除？")){
				  		window.location.href="/admin/program/deletetype.jsp?t_id="+typeid+"&programe_file=${programe_file}";
				  	}
				  }
				   function closedivframe(){
					if(confirm("提示信息：确认取消发送节目？")){
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
			divNode.innerHTML= "<img src='/images/mid_giallo.gif' align=absmiddle><font style='font-size:12px'>&nbsp;正在验证节目，请稍后...</font>";
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
														<legend align="center" style="font-size: 13px; color: blue; font-weight: bold">选择终端</legend>
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
											<td colspan="6" height="25px">※&nbsp;<b>节目名称：${program.program_Name }</b></td></tr>
											<tr><td colspan="6" height="45px" style="padding-left: 50px">插播时长：<input type="text" size="5" name="playcunt" value="5"  style="width: 55px;height: 18;pxime-mode:Disabled" onkeypress="return event.keyCode>=48&&event.keyCode<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" maxlength="3" /> 分钟</td></tr>
											<tr><td colspan="6" height="45px" style="padding-left: 50px;color: blue">请输入需要插播的时长。</td></tr>
											<tr><td colspan="6" height="45px" style="padding-left: 50px;color: blue">说明：插播节目的优先级别最高，插播后会及时播放！</td></tr>
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
									<td width="30%"><input type="button" id="send_button_id" class="button1" onclick="checkProgram();" value= "下一步" /></td>
									<td width="30%"><input type="button" class="button1" onclick="closedivframe();" value=" 取  消 " /></td>
									<td width="40%">&nbsp;<a href="javascript:;" onclick="showErrorMenu()" id="error_id" style="color: red; display: none;">查看时间冲突</a></td>
								</tr>
							</table>
							 </td>
						</tr>
				
				         </table> 
			      </form> 
				 <script language="JavaScript">
  
   /**   第一个参数：“treeboxbox_tree”必须与<DIV>中的id值对应；
		  第二个参数： 树的宽度为100%；
		  第三个参数： 树的高度为100%；
		  第四个参数： 树的根节点ID的值为0；
  **/
		  var tree = new dhtmlXTreeObject("treeboxbox_tree", "100%", "100%", 0);
			//tree.setSkin('dhx_skyblue');
			//tree.setImagePath("/admin/checkboxtree/csh_bluebooks/");
			
			//tree.enableTreeImages("-Icons");//设置是否显示图标
          //  tree.enableTreeLines("-Lines");//设置是否显示连接线
           // tree.enableTextSigns("Cross Signs");//设置是否显示交叉线(即横线)
            
			tree.setImagePath("/admin/checkboxtree/codebase/imgs/dhxtree_skyblue/");
			tree.enableCheckBoxes(1);
			tree.enableThreeStateCheckboxes(true);
			//tree.loadXML("/admin/checkboxtree/tree_<%=username%>.xml");
			tree.load("/admin/checkboxtree/tree_<%=username%>.xml");
			
			// 加载数据，这里写入我们的action访问路径即可
			//tree.loadXML("/org/buildTree.action");
        </script>
	</body>
</html>
