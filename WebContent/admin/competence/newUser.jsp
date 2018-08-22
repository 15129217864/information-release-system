<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<html>
	<head>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="Pragma" content="no-cache" />
		<title>New Content</title>
		<script language="javascript" src="/js/vcommon.js"></script>
        <script src="/js/jquery1.4.2.js" type="text/javascript"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
        <style type="text/css">
             *{ margin:0; padding:0;}
              body { margin: 1px auto; font:12px Arial, Helvetica, sans-serif; color:#666;}
	         .tab { width:401px; height: 550px;margin:1px;}
			 .tab_menu { clear:both;}
			 .tab_menu li { float:left; text-align:center; cursor:pointer; list-style:none; padding:1px 6px; margin-right:4px; background:#F1F1F1; border:1px solid #898989; border-bottom:none;}
			 .tab_menu li.hover { background:#DFDFDF;}
			 .tab_menu li.selected { color:#FFF; background:#6D84B4;}
			 .tab_box { clear:both; border:0px solid #898989; height:350px;}
			 .hide{display:none}
			 <!--伸缩菜单-->
			   .accordion {
					width: 425px;
					height:500px;
					border-bottom: solid 1px #c4c4c4;
				}
				.accordion h3 {
					background: #e9e7e7  no-repeat right -51px;
					padding: 7px 15px;
					margin: 0;
					font: bold 120%/100% Arial, Helvetica, sans-serif;
					border: solid 1px #c4c4c4;
					border-bottom: none;
					cursor: pointer;
				}
				.accordion h3:hover {
					background-color: #e3e2e2;
				}
				.accordion h3.active {
					background-position: right 5px;
				}
				.accordion div {
					margin: 0;
					padding: 10px 15px 20px;
					border-left: solid 1px #c4c4c4;
					border-right: solid 1px #c4c4c4;
				}
        </style>
		<script language="JavaScript" type="text/JavaScript">
		    $(document).ready(function(){
				$(".accordion h3:first").addClass("active");
				$(".accordion div:not(:first)").hide();
				$(".accordion h3").click(function(){
					$(this).next("div").slideToggle("fast")
					.siblings("div:visible").slideUp("fast");
					$(this).toggleClass("active");
					$(this).siblings("h3").removeClass("active");
				});
			});
		   	$(function(){
				    var $div_li =$("div.tab_menu ul li");
				    $div_li.click(function(){
						$(this).addClass("selected")            //当前<li>元素高亮
							   .siblings().removeClass("selected");  //去掉其它同辈<li>元素的高亮
			            var index =  $div_li.index(this);  // 获取当前点击的<li>元素 在 全部li元素中的索引。
						$("div.tab_box > div")   	//选取子节点。不选取子节点的话，会引起错误。如果里面还有div 
								.eq(index).show()   //显示 <li>元素对应的<div>元素
								.siblings().hide(); //隐藏其它几个同辈的<div>元素
					}).hover(function(){
						$(this).addClass("hover");
					},function(){
						$(this).removeClass("hover");
					})
				})
		   /////////////////////////////////////////////////////////////////////
		   var SubIFLayerprogram;
		   	var reclick=0;
		   function popupCategoryWindowprogram(e) {
				if(reclick==0){
					reclick=1;
					var tsrc = "/admin/competence/programtree_zu.jsp";
					makeSubLayerprogram('节目组树状视图',200,250,e);
					bindSrc('treeviewPopupprogram',tsrc);
				}
			}
			function bindSrcprogram(objname,source){
				var obj = document.getElementById(objname);
				obj.src = source;
			}
			function makeSubLayerprogram(title,w,h,e){ 
				SubIFLayerprogram=document.createElement("div"); 
				SubIFLayerprogram.id = 'divTreeviewprogram';
				SubIFLayerprogram.style.cssText="width:"+w+";height:"+h+";z-index:999;position:absolute;top:-4000;left:-4000;border:1px solid #2A5D90;padding:0 0 0 0;color:white;background-color:F5FBFF"; 
			
				SubIFLayerprogram.innerHTML=""
					+"<table width='100%' border='0'><tr><td width=93% align=center><b>"+title+"</b></td><td align=right width=5%><a href='javascript:;' onclick='closeSubIFLayerprogram()'><img src='/images/icon_popupclose.gif' width='16' height='16' border='0'></td></tr></table>"
					+"";
			
				with(SubIFLayerprogram.appendChild(document.createElement("iframe"))){ 
				   id ='treeviewPopupprogram';
				   src='about:blank'; 
				   frameBorder=0; 
				   marginWidth=0;
				   marginHeight=0;
				   marginHeight=0;	   
				   width=w; 
				   height=h-30; 
				   scrolling='yes';
				}//with 
			
				document.body.appendChild(SubIFLayerprogram); 
				e=e||event; //为了兼容火狐浏览器
				
				with(SubIFLayerprogram.style){ 
				 left = e.clientX-w-12;
				 top  = e.clientY-115;
				 width=w; 
				 height=h; 
				};//with 
			} 
			
			var zuidprogram="!";
			var zuprogram="";
			function selectCategoryprogram(group_id,dpath){
				var treeviewobj = document.getElementById('divTreeviewprogram');
				//treeviewobj.removeNode(true);//只有IE才有此方法
	            SubIFLayerprogram.parentNode.removeChild(SubIFLayerprogram);
				
				if(zuidprogram.indexOf("!"+group_id+"!")<0){
					zuidprogram+=group_id+"!";
					var opption=new Option(dpath,group_id);
					opption.title=dpath;
					document.myform.cnt_zuprogram.options.add(opption);
				}else{
					alert("已添加节目组:"+dpath);
				}
				reclick=0;
			}
			function closeSubIFLayerprogram(){
				//SubIFLayerprogram.removeNode(true);//只有IE才有此方法
				SubIFLayerprogram.parentNode.removeChild(SubIFLayerprogram);
				reclick=0;
			}
			
		   function delroleprogram(){
				var selectIndex = myform.cnt_zuprogram.options.selectedIndex;
				var zuidd=myform.cnt_zuprogram.value;
				if(selectIndex!=-1){
				   myform.cnt_zuprogram.options[selectIndex] = null;
				   zuidprogram=zuidprogram.replace("!"+zuidd,"");
				}else{
				   alert("请选中要删除的节目组");
			    }
			}
			
			
		/////////////////////////////////////////////////////////////////////
			var SubIFLayer;
			var reclick2=0;
			function popupCategoryWindow(e) {
				if(reclick2==0){
					reclick2=1;
					var tsrc = "/admin/competence/tree_zu.jsp";
					makeSubLayer('终端组树状视图',200,250,e);
					bindSrc('treeviewPopup',tsrc);
				}
			}
			function bindSrc(objname,source){
				var obj = document.getElementById(objname);
				obj.src = source;
			}
			function makeSubLayer(title,w,h,e){ 
				SubIFLayer=document.createElement("div"); 
				SubIFLayer.id = 'divTreeview';
				SubIFLayer.style.cssText="width:"+w+";height:"+h+";z-index:999;position:absolute;top:-4000;left:-4000;border:1px solid #2A5D90;padding:0 0 0 0;color:white;background-color:F5FBFF"; 
			
				SubIFLayer.innerHTML=""
					+"<table width='100%' border='0'><tr><td width=93% align=center><b>"+title+"</b></td><td align=right width=5%><a href='javascript:;' onclick='closeSubIFLayer()'><img src='/images/icon_popupclose.gif' width='16' height='16' border='0'></td></tr></table>"
					+"";
			
				with(SubIFLayer.appendChild(document.createElement("iframe"))){ 
				 
				   id ='treeviewPopup';
				   src='about:blank'; 
				   frameBorder=0; 
				   marginWidth=0;
				   marginHeight=0;
				   marginHeight=0;	   
				   width=w; 
				   height=h-30; 
				   scrolling='yes';
				}//with 
			
				document.body.appendChild(SubIFLayer); 
				e=e||event; //为了兼容火狐浏览器
				with(SubIFLayer.style){ 
				 left = e.clientX-w-12;
				 top  = e.clientY-20;
				 width=w; 
				 height=h; 
				};//with 
			} 
			var zuid="!";
			var zu="";
			function selectCategory(group_id,dpath){
				var treeviewobj = document.getElementById('divTreeview');
				//treeviewobj.removeNode(true);//只有IE才有此方法
	             SubIFLayer.parentNode.removeChild(SubIFLayer);
				if(zuid.indexOf("!"+group_id+"!")<0){
					zuid+=group_id+"!";
					var opption=new Option(dpath,group_id);
					opption.title=dpath;
					document.myform.cnt_zu.options.add(opption);
				}else{
					alert("已添加终端组:"+dpath);
				}
				reclick2=0;
			}
			function closeSubIFLayer(){
				//SubIFLayer.removeNode(true);//只有IE才有此方法
				SubIFLayer.parentNode.removeChild(SubIFLayer);
				reclick2=0;
			}
			
		   function delrole(){
				var selectIndex = myform.cnt_zu.options.selectedIndex;
				var zuidd=myform.cnt_zu.value;
				if(selectIndex!=-1){
				   myform.cnt_zu.options[selectIndex] = null;
				   zuid=zuid.replace("!"+zuidd,"");
				}else{
				   alert("请选中要删除的终端组");
			    }
			}
		///////////////////////////////////////////////////////////////////////	
			function addUser(){
				document.myform.cnt_zuid.value =zuid;
				document.myform.cnt_zuidrogram.value =zuidprogram;
				var username=myform.username.value.replace(/\s/g,"");
				var password=myform.password.value.replace(/\s/g,"");
				var name=myform.name.value.replace(/\s/g,"");

				var reg1 = /^[a-zA-Z0-9_][a-zA-Z0-9_]+/;
				if(username==""){
					alert("用户名不能为空！");
					return;
				}else if (username.length<3 ||!reg1.test(username)){
					alert("用户名必须为3-15位数字或字母的组合！");
					return;
				}else if(password==""){
					alert("密码不能为空！");
					return;
				}else if (password.length<6 ||!reg1.test(password)){
					alert("密码必须为6-15位数字或字母的组合！");
					return;
				}else if(name==""){
					alert("姓名不能为空！");
					return;
				}
				if(checkPass(password)<3){
			          alert("密码复杂度不够，请重新设置！");
			          myform.password.focus();
			           return  ;
			     }
				
				myform.action="/admin/competence/newUserDO.jsp";
				myform.submit();
			}
		
			//密码复杂要求：
			//1、长度大于8
	        // 2、密码必须是字母大写，字母小写，数字，特殊字符中任意三个组合。
			function checkPass(s){
				  if(s.length < 6){
				      return 0;
				  }
				  var ls = 0;

				 if(s.match(/([a-z])+/)){
				     ls++;
				 }
				 if(s.match(/([0-9])+/)){
				     ls++;  
				 }
				 if(s.match(/([A-Z])+/)){
				     ls++;
				  }
				  if(s.match(/[^a-zA-Z0-9]+/)){
			        ls++;
			      }
				  return ls
			}
			////////////////////////////////////////////////////////////
			function uploadBegin(){	
				winstyle = "dialogWidth=360px; dialogHeight:300px; center:yes";
				window.showModelessDialog("/progressbar.jsp",window,winstyle);
			}
			
			function importUserInfo(){//批量导入用户
				if(document.myform.file.value==""){
					alert("提示信息：请选择导入文件！");
					return ;
				}
				
				if(document.myform.file.value.indexOf(".xls")==-1||document.myform.file.value.indexOf(".xlsx")!=-1){
					alert("提示信息：请选择后缀名为 .xls 格式的Excel文件！");
					return ;
				}
				uploadBegin();
		        document.myform.encoding = "multipart/form-data";//IE
		        //document.myform.encoding = = "multipart/form-data";//chrome
				document.myform.action="/admin/competence/importMoreUserinfo.jsp";
				document.myform.submit();
				document.getElementById("xm_button").disabled="disabled";
			}
		</script>
</head>
<body>
	 <form method="post" name="myform" id="myform">
<div class="tab">
	<div class="tab_menu">
		<ul>
			<li class="selected">角色</li>
			<li>权限</li>
			<li>批量导入用户</li>
		</ul>
	</div>
	<div class="tab_box"> 
	   <div style="width:428px;height:500px;">
			<input type="hidden" name="cnt_zuid"/>
			<input type="hidden" name="cnt_zuidrogram"/>
			<table width="400px" height="500px" border="0" cellpadding="0" cellspacing="0">
					<tr >
						<td width="25%" align="right" height="30px;" class="Bold">
							<span style="color: red;font-size: 15px;">*</span> 用户名：
						</td>
						<td colspan="2" width="70%" align="left" >
							<input type="text" name="username" class="text" maxlength="15" />（3-15位数字或字母的组合）
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					<tr >
						<td width="25%" align="right" height="30px;" class="Bold">
							<span style="color: red;font-size: 15px;">*</span> 密 码：
						</td>
						<td colspan="2" width="70%" align="left" >
							<input type="password" name="password" class="text" min="6"
								maxlength="15" />（6-15位数字或字母的组合）
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					<tr >
						<td width="25%" align="right" height="30px;" class="Bold">
							<span style="color: red;font-size: 15px;">*</span> 姓 名：
						</td>
						<td colspan="2" width="70%" align="left" >
							<input type="text" name="name" class="text" maxlength="15" />（用户真实姓名）
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					<tr >
						<td width="25%" align="right" height="30px;" class="Bold">
							邮 箱：
						</td>
						<td colspan="2" width="70%" align="left" >
							<input type="text" name="email" class="text" maxlength="15" value="无" />（常用邮箱地址）
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					 <tr>
						<td width="25%" align="right" height="30px;" class="Bold">
							角 色：
						</td>
						<td colspan="2" width="70%" align="left" class="Bold">
							<select style="width: 120px" class="button" name="role">
								<option value="2">
									审核员
								</option>
								<option value="3">
									一般用户
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					<tr >
						<td width="25%" align="right" height="30px;" class="Bold">
							管理终端组：
						</td>
						<td width="50%" align="left" class="Bold">
						    <select name="cnt_zu"  style="width: 200;height: 90;" multiple="multiple">
						</select>
						</td>
						<td width="20%">
								<a href="javascript:;" onClick="javascript:popupCategoryWindow(event);"  title="添加终端组"><img src="/images/btn_search2.gif" border="0" /></a><br/><br/>
								<a href="javascript:;" onClick="javascript:delrole();" title="删除终端组"><img src="/images/del.gif" width="18" height="18" border="0" /></a>
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					<tr >
						<td width="25%" align="right" height="30px;" class="Bold">
							管理节目组：
						</td>
						<td width="50%" align="left" class="Bold">
						    <select name="cnt_zuprogram"  style="width: 200;height: 90;" multiple="multiple">
						</select>
						</td>
						<td width="20%">
								<a href="javascript:;" onClick="javascript:popupCategoryWindowprogram(event);"  title="添加节目组"><img src="/images/btn_search2.gif" border="0" /></a><br/><br/>
								<a href="javascript:;" onClick="javascript:delroleprogram();" title="删除节目组"><img src="/images/del.gif" width="18" height="18" border="0" /></a>
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					<tr >
						<td width="25%" align="right" valign="top" height="40px;" style="color: green" >
							友情提示：
						</td>
						<td colspan="2" width="70%"  valign="top"  align="left"  style="color: green">
							“审核员”为二级管理员，可编辑、审核、发送节目。<br/>
							“一般用户”只可编辑节目，发送节目需审核员审核。
						</td>
					</tr>
					<tr >
						<td colspan="3" width="100%" align="center" valign="top" height="40px;" style="color: green" >
						  &nbsp;&nbsp;&nbsp;
				       <input type="button" value=" 添 加 " class="button1" onClick="addUser()" />
				       &nbsp;&nbsp;&nbsp;
				       <input type="button" value=" 取 消 " class="button1" onclick="parent.closedivframe(1);" />
						</td>
					</tr>
				</table>
		 </div>
		 <div class="hide">
		     <div class="accordion">
					<h3>终端管理</h3>
				    <div>
					    <input type="checkbox" name="authority" checked value="A"/>休眠&nbsp;&nbsp;<input type="checkbox" checked name="authority" value="B"/>停止休眠&nbsp;&nbsp;
					    <input type="checkbox" name="authority" checked value="C"/>其他设置&nbsp;&nbsp;<input type="checkbox" checked name="authority" value="D"/>软件升级<br/>
						<input type="checkbox" name="authority" checked value="E"/>文字通知&nbsp;&nbsp;<span style="display: none;"><input type="checkbox" checked name="authority" value="F"/>终端初始化&nbsp;&nbsp;</span>
						<span style="display: none;"><input type="checkbox" name="authority" checked value="G"/>时间同步</span>&nbsp;&nbsp;<input type="checkbox" checked name="authority" value="H"/>音量时间设置&nbsp;&nbsp;<br/>
						<input type="checkbox" name="authority"  value="I"/>删除终端&nbsp;&nbsp;<input type="checkbox" checked name="authority" value="J"/>修改终端信息&nbsp;&nbsp;
						<input type="checkbox" name="authority"  value="K"/>删除节目(查看节目单时)
					</div>
					<h3>媒体库</h3>
					<div>	    
					  <input type="checkbox" name="authority" checked value="L"/>新建媒体&nbsp;&nbsp;<input type="checkbox" name="authority" checked value="M"/>删除媒体&nbsp;&nbsp;
					</div>
					<h3>模板管理</h3>
					<div>
					  <input type="checkbox" name="authority" checked value="N"/>新建模板&nbsp;&nbsp;
					  <input type="checkbox" name="authority" checked value="O"/>删除模板&nbsp;&nbsp;
					   <input type="checkbox" name="authority" checked value="P"/>修改模板&nbsp;&nbsp;
					</div>
					<h3>节目管理</h3>
					<div>  
					  <input type="checkbox" name="authority" checked value="Q"/>新建节目&nbsp;&nbsp;
					  <input type="checkbox" name="authority" checked value="R"/>修改节目&nbsp;&nbsp;
					  <input type="checkbox" name="authority" checked value="S"/>删除节目&nbsp;&nbsp;
					  <input type="checkbox" name="authority" checked value="T"/>发送节目&nbsp;&nbsp;
					</div>
					<h3>时间表</h3>
					<div> 
					  <input type="checkbox" name="authority"  value="U"/>删除(查询节目)
					</div>
			</div>
			<center>
			    <div>
			       <br/><br/><br/><br/><br/><br/>
				<input type="button" value=" 添 加 " class="button1"
					onClick="addUser()" />
				&nbsp;&nbsp;&nbsp;
				<input type="button" value=" 取 消 " class="button1"
					onclick="parent.closedivframe(1);" />
			       </div>
			</center>
         </div>
	
         <div class="hide">
		     <div class="accordion">
					   <br/>
				      <fieldset style="width:400px; height:500px;border:#6699cc 1 solid;">
							<legend align="center" style="font-size: 13px; color: blue; font-weight: bold">批量导入用户</legend>
								<%--<form action="" name="importform" method="post" enctype="multipart/form-data">--%>
									
									<table width="400" height="350" border="0" align="center" cellpadding="0"
										cellspacing="8" class="space">
										<tr>
											<td >
												<span style="color: green">友情提示：通过Excel表格导入，可批量添加基本用户,管理终端权限需配置！(Excel 文件名后缀名必须为xls)</span>
											</td>
										</tr>
										<tr>
											<td align="center">
											  <span style="color: red"> Excel 表格模板规范：</span><br/>
												<img src="/images/importuserinfo.jpg"/>
											</td>
										</tr>
										<tr>
											<td  align="center">
												<span style="width: 70px;">Excel文件：</span>
												<input type="file" name="file" class="button" style="width: 220px; height: 20px;" />
											</td>
										</tr>
										<tr>
											<td height="50" class="tail" align="center">
												<input type="button" class="button1" id="xm_button" onclick="importUserInfo()"
													value=" 导 入 ">
												
												<input type="reset" class="button1" value=" 取 消 "
													onclick="parent.closedivframe(1);">
												&nbsp;&nbsp;&nbsp;&nbsp;
												<span style="color: red"></span> &nbsp;&nbsp;&nbsp;&nbsp;
												<span style="color: red" id="times"></span>
											</td>
										</tr>
									</table>
								<!--  </form>-->
					</fieldset>
			 </div>
		</div>
	</div>
</div>
		</form>
	</body>
</html>
