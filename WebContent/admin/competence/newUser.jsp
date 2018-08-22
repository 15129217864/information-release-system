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
			 <!--�����˵�-->
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
						$(this).addClass("selected")            //��ǰ<li>Ԫ�ظ���
							   .siblings().removeClass("selected");  //ȥ������ͬ��<li>Ԫ�صĸ���
			            var index =  $div_li.index(this);  // ��ȡ��ǰ�����<li>Ԫ�� �� ȫ��liԪ���е�������
						$("div.tab_box > div")   	//ѡȡ�ӽڵ㡣��ѡȡ�ӽڵ�Ļ������������������滹��div 
								.eq(index).show()   //��ʾ <li>Ԫ�ض�Ӧ��<div>Ԫ��
								.siblings().hide(); //������������ͬ����<div>Ԫ��
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
					makeSubLayerprogram('��Ŀ����״��ͼ',200,250,e);
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
				e=e||event; //Ϊ�˼��ݻ�������
				
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
				//treeviewobj.removeNode(true);//ֻ��IE���д˷���
	            SubIFLayerprogram.parentNode.removeChild(SubIFLayerprogram);
				
				if(zuidprogram.indexOf("!"+group_id+"!")<0){
					zuidprogram+=group_id+"!";
					var opption=new Option(dpath,group_id);
					opption.title=dpath;
					document.myform.cnt_zuprogram.options.add(opption);
				}else{
					alert("����ӽ�Ŀ��:"+dpath);
				}
				reclick=0;
			}
			function closeSubIFLayerprogram(){
				//SubIFLayerprogram.removeNode(true);//ֻ��IE���д˷���
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
				   alert("��ѡ��Ҫɾ���Ľ�Ŀ��");
			    }
			}
			
			
		/////////////////////////////////////////////////////////////////////
			var SubIFLayer;
			var reclick2=0;
			function popupCategoryWindow(e) {
				if(reclick2==0){
					reclick2=1;
					var tsrc = "/admin/competence/tree_zu.jsp";
					makeSubLayer('�ն�����״��ͼ',200,250,e);
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
				e=e||event; //Ϊ�˼��ݻ�������
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
				//treeviewobj.removeNode(true);//ֻ��IE���д˷���
	             SubIFLayer.parentNode.removeChild(SubIFLayer);
				if(zuid.indexOf("!"+group_id+"!")<0){
					zuid+=group_id+"!";
					var opption=new Option(dpath,group_id);
					opption.title=dpath;
					document.myform.cnt_zu.options.add(opption);
				}else{
					alert("������ն���:"+dpath);
				}
				reclick2=0;
			}
			function closeSubIFLayer(){
				//SubIFLayer.removeNode(true);//ֻ��IE���д˷���
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
				   alert("��ѡ��Ҫɾ�����ն���");
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
					alert("�û�������Ϊ�գ�");
					return;
				}else if (username.length<3 ||!reg1.test(username)){
					alert("�û�������Ϊ3-15λ���ֻ���ĸ����ϣ�");
					return;
				}else if(password==""){
					alert("���벻��Ϊ�գ�");
					return;
				}else if (password.length<6 ||!reg1.test(password)){
					alert("�������Ϊ6-15λ���ֻ���ĸ����ϣ�");
					return;
				}else if(name==""){
					alert("��������Ϊ�գ�");
					return;
				}
				if(checkPass(password)<3){
			          alert("���븴�ӶȲ��������������ã�");
			          myform.password.focus();
			           return  ;
			     }
				
				myform.action="/admin/competence/newUserDO.jsp";
				myform.submit();
			}
		
			//���븴��Ҫ��
			//1�����ȴ���8
	        // 2�������������ĸ��д����ĸСд�����֣������ַ�������������ϡ�
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
			
			function importUserInfo(){//���������û�
				if(document.myform.file.value==""){
					alert("��ʾ��Ϣ����ѡ�����ļ���");
					return ;
				}
				
				if(document.myform.file.value.indexOf(".xls")==-1||document.myform.file.value.indexOf(".xlsx")!=-1){
					alert("��ʾ��Ϣ����ѡ���׺��Ϊ .xls ��ʽ��Excel�ļ���");
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
			<li class="selected">��ɫ</li>
			<li>Ȩ��</li>
			<li>���������û�</li>
		</ul>
	</div>
	<div class="tab_box"> 
	   <div style="width:428px;height:500px;">
			<input type="hidden" name="cnt_zuid"/>
			<input type="hidden" name="cnt_zuidrogram"/>
			<table width="400px" height="500px" border="0" cellpadding="0" cellspacing="0">
					<tr >
						<td width="25%" align="right" height="30px;" class="Bold">
							<span style="color: red;font-size: 15px;">*</span> �û�����
						</td>
						<td colspan="2" width="70%" align="left" >
							<input type="text" name="username" class="text" maxlength="15" />��3-15λ���ֻ���ĸ����ϣ�
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					<tr >
						<td width="25%" align="right" height="30px;" class="Bold">
							<span style="color: red;font-size: 15px;">*</span> �� �룺
						</td>
						<td colspan="2" width="70%" align="left" >
							<input type="password" name="password" class="text" min="6"
								maxlength="15" />��6-15λ���ֻ���ĸ����ϣ�
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					<tr >
						<td width="25%" align="right" height="30px;" class="Bold">
							<span style="color: red;font-size: 15px;">*</span> �� ����
						</td>
						<td colspan="2" width="70%" align="left" >
							<input type="text" name="name" class="text" maxlength="15" />���û���ʵ������
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					<tr >
						<td width="25%" align="right" height="30px;" class="Bold">
							�� �䣺
						</td>
						<td colspan="2" width="70%" align="left" >
							<input type="text" name="email" class="text" maxlength="15" value="��" />�����������ַ��
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					 <tr>
						<td width="25%" align="right" height="30px;" class="Bold">
							�� ɫ��
						</td>
						<td colspan="2" width="70%" align="left" class="Bold">
							<select style="width: 120px" class="button" name="role">
								<option value="2">
									���Ա
								</option>
								<option value="3">
									һ���û�
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					<tr >
						<td width="25%" align="right" height="30px;" class="Bold">
							�����ն��飺
						</td>
						<td width="50%" align="left" class="Bold">
						    <select name="cnt_zu"  style="width: 200;height: 90;" multiple="multiple">
						</select>
						</td>
						<td width="20%">
								<a href="javascript:;" onClick="javascript:popupCategoryWindow(event);"  title="����ն���"><img src="/images/btn_search2.gif" border="0" /></a><br/><br/>
								<a href="javascript:;" onClick="javascript:delrole();" title="ɾ���ն���"><img src="/images/del.gif" width="18" height="18" border="0" /></a>
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					<tr >
						<td width="25%" align="right" height="30px;" class="Bold">
							�����Ŀ�飺
						</td>
						<td width="50%" align="left" class="Bold">
						    <select name="cnt_zuprogram"  style="width: 200;height: 90;" multiple="multiple">
						</select>
						</td>
						<td width="20%">
								<a href="javascript:;" onClick="javascript:popupCategoryWindowprogram(event);"  title="��ӽ�Ŀ��"><img src="/images/btn_search2.gif" border="0" /></a><br/><br/>
								<a href="javascript:;" onClick="javascript:delroleprogram();" title="ɾ����Ŀ��"><img src="/images/del.gif" width="18" height="18" border="0" /></a>
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					<tr >
						<td width="25%" align="right" valign="top" height="40px;" style="color: green" >
							������ʾ��
						</td>
						<td colspan="2" width="70%"  valign="top"  align="left"  style="color: green">
							�����Ա��Ϊ��������Ա���ɱ༭����ˡ����ͽ�Ŀ��<br/>
							��һ���û���ֻ�ɱ༭��Ŀ�����ͽ�Ŀ�����Ա��ˡ�
						</td>
					</tr>
					<tr >
						<td colspan="3" width="100%" align="center" valign="top" height="40px;" style="color: green" >
						  &nbsp;&nbsp;&nbsp;
				       <input type="button" value=" �� �� " class="button1" onClick="addUser()" />
				       &nbsp;&nbsp;&nbsp;
				       <input type="button" value=" ȡ �� " class="button1" onclick="parent.closedivframe(1);" />
						</td>
					</tr>
				</table>
		 </div>
		 <div class="hide">
		     <div class="accordion">
					<h3>�ն˹���</h3>
				    <div>
					    <input type="checkbox" name="authority" checked value="A"/>����&nbsp;&nbsp;<input type="checkbox" checked name="authority" value="B"/>ֹͣ����&nbsp;&nbsp;
					    <input type="checkbox" name="authority" checked value="C"/>��������&nbsp;&nbsp;<input type="checkbox" checked name="authority" value="D"/>�������<br/>
						<input type="checkbox" name="authority" checked value="E"/>����֪ͨ&nbsp;&nbsp;<span style="display: none;"><input type="checkbox" checked name="authority" value="F"/>�ն˳�ʼ��&nbsp;&nbsp;</span>
						<span style="display: none;"><input type="checkbox" name="authority" checked value="G"/>ʱ��ͬ��</span>&nbsp;&nbsp;<input type="checkbox" checked name="authority" value="H"/>����ʱ������&nbsp;&nbsp;<br/>
						<input type="checkbox" name="authority"  value="I"/>ɾ���ն�&nbsp;&nbsp;<input type="checkbox" checked name="authority" value="J"/>�޸��ն���Ϣ&nbsp;&nbsp;
						<input type="checkbox" name="authority"  value="K"/>ɾ����Ŀ(�鿴��Ŀ��ʱ)
					</div>
					<h3>ý���</h3>
					<div>	    
					  <input type="checkbox" name="authority" checked value="L"/>�½�ý��&nbsp;&nbsp;<input type="checkbox" name="authority" checked value="M"/>ɾ��ý��&nbsp;&nbsp;
					</div>
					<h3>ģ�����</h3>
					<div>
					  <input type="checkbox" name="authority" checked value="N"/>�½�ģ��&nbsp;&nbsp;
					  <input type="checkbox" name="authority" checked value="O"/>ɾ��ģ��&nbsp;&nbsp;
					   <input type="checkbox" name="authority" checked value="P"/>�޸�ģ��&nbsp;&nbsp;
					</div>
					<h3>��Ŀ����</h3>
					<div>  
					  <input type="checkbox" name="authority" checked value="Q"/>�½���Ŀ&nbsp;&nbsp;
					  <input type="checkbox" name="authority" checked value="R"/>�޸Ľ�Ŀ&nbsp;&nbsp;
					  <input type="checkbox" name="authority" checked value="S"/>ɾ����Ŀ&nbsp;&nbsp;
					  <input type="checkbox" name="authority" checked value="T"/>���ͽ�Ŀ&nbsp;&nbsp;
					</div>
					<h3>ʱ���</h3>
					<div> 
					  <input type="checkbox" name="authority"  value="U"/>ɾ��(��ѯ��Ŀ)
					</div>
			</div>
			<center>
			    <div>
			       <br/><br/><br/><br/><br/><br/>
				<input type="button" value=" �� �� " class="button1"
					onClick="addUser()" />
				&nbsp;&nbsp;&nbsp;
				<input type="button" value=" ȡ �� " class="button1"
					onclick="parent.closedivframe(1);" />
			       </div>
			</center>
         </div>
	
         <div class="hide">
		     <div class="accordion">
					   <br/>
				      <fieldset style="width:400px; height:500px;border:#6699cc 1 solid;">
							<legend align="center" style="font-size: 13px; color: blue; font-weight: bold">���������û�</legend>
								<%--<form action="" name="importform" method="post" enctype="multipart/form-data">--%>
									
									<table width="400" height="350" border="0" align="center" cellpadding="0"
										cellspacing="8" class="space">
										<tr>
											<td >
												<span style="color: green">������ʾ��ͨ��Excel����룬��������ӻ����û�,�����ն�Ȩ�������ã�(Excel �ļ�����׺������Ϊxls)</span>
											</td>
										</tr>
										<tr>
											<td align="center">
											  <span style="color: red"> Excel ���ģ��淶��</span><br/>
												<img src="/images/importuserinfo.jpg"/>
											</td>
										</tr>
										<tr>
											<td  align="center">
												<span style="width: 70px;">Excel�ļ���</span>
												<input type="file" name="file" class="button" style="width: 220px; height: 20px;" />
											</td>
										</tr>
										<tr>
											<td height="50" class="tail" align="center">
												<input type="button" class="button1" id="xm_button" onclick="importUserInfo()"
													value=" �� �� ">
												
												<input type="reset" class="button1" value=" ȡ �� "
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
