<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.UsersDAO"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<%
String username=request.getParameter("userids")==null?"":request.getParameter("userids");
Users user= new UsersDAO().getUserBystr(" where lg_name='"+username+"'");
TerminalDAO terminaldao= new TerminalDAO();
List list= terminaldao.getAllZu(" where zu_type=0 ");
String zuid="!";
String zuname="";
String zutitle="";
for(int i=0;i<list.size();i++){
	Terminal terminal_zu=(Terminal)list.get(i);
	if(terminal_zu!=null&&terminal_zu.getZu_username().indexOf(username+"||")>0){
		zuid+=terminal_zu.getZu_id()+"!";
		zutitle=terminaldao.getzu_pathByzuID(list,terminal_zu.getZu_id());
		zuname+="<option value='"+terminal_zu.getZu_id()+"' title='"+zutitle+"'>"+zutitle+"</option>";
	}
}

///////////////
List list2= terminaldao.getAllZu(" where zu_type=2 ");
String zuid2="!";
String zuname2="";
String zutitle2="";
for(int i=0;i<list2.size();i++){
	Terminal terminal_zu=(Terminal)list2.get(i);
	if(terminal_zu!=null&&terminal_zu.getZu_username().indexOf(username+"||")>0){
		zuid2+=terminal_zu.getZu_id()+"!";
		zutitle2=terminaldao.getzu_pathByzuID(list2,terminal_zu.getZu_id());
		zuname2+="<option value='"+terminal_zu.getZu_id()+"' title='"+zutitle2+"'>"+zutitle2+"</option>";
	}
}

request.setAttribute("zuid",zuid);
request.setAttribute("zuname",zuname);
request.setAttribute("zuid2",zuid2);
request.setAttribute("zuname2",zuname2);
request.setAttribute("user",user);
request.setAttribute("authoritys",user.getAuthority());

 %>
<html>
	<head>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="Pragma" content="no-cache" />
		<title>New Content</title>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script language="javascript" src="/js/vcommon.js"></script>
		<script src="/js/jquery1.4.2.js" type="text/javascript"></script>
        <style type="text/css">
             *{ margin:0; padding:0;}
              body { margin: 1px auto; font:12px Arial, Helvetica, sans-serif; color:#666;}
	         .tab { width:401px;margin:1px;}
			 .tab_menu { clear:both;}
			 .tab_menu li { float:left; text-align:center; cursor:pointer; list-style:none; padding:1px 6px; margin-right:4px; background:#F1F1F1; border:1px solid #898989; border-bottom:none;}
			 .tab_menu li.hover { background:#DFDFDF;}
			 .tab_menu li.selected { color:#FFF; background:#6D84B4;}
			 .tab_box { clear:both; border:0px solid #898989; height:305px;}
			 .hide{display:none}
			 <!--伸缩菜单-->
			   .accordion {
					width: 403px;
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
			function closeSubIFLayerprogram(){
				//SubIFLayerprogram.removeNode(true);//只有IE才有此方法
				SubIFLayerprogram.parentNode.removeChild(SubIFLayerprogram);
				reclick2=0;
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
			//////////////////////////////
		
			var SubIFLayer;
			var reclick2=0;
			function popupCategoryWindow(e) {
				if(reclick2==0){
					reclick2=1;
					var tsrc = "/admin/competence/tree_zu.jsp";
					makeSubLayer('组树状视图',200,250,e);
					bindSrc('treeviewPopup',tsrc);
				}
			}
			function bindSrc(objname,source){
			
				var obj = document.getElementById(objname);
				obj.src = source;
			}
			function closeSubIFLayer(){
				//SubIFLayer.removeNode(true); //只有IE才有此方法
				SubIFLayer.parentNode.removeChild(SubIFLayer);
				reclick2=0;
				reclick=0;
			}
			function makeSubLayer(title,w,h,e){ 
				SubIFLayer=document.createElement("div"); 
				SubIFLayer.id = 'divTreeview';
				SubIFLayer.style.cssText="width:"+w+";height:"+h+";z-index:999;position:absolute;top:-4000;left:-4000;border:1px solid #2A5D90;padding:0 0 0 0;color:white;background-color:F5FBFF"; 
			
				SubIFLayer.innerHTML=""
					+"<table width='100%' border='0'><tr><td width=93% align=center><b>"+title+"</b></td><td align=right width=5%><a href='javascript:;' onclick='closeSubIFLayer()'><img src='/images/icon_popupclose.gif' width='16' height='16' border='0'></td></tr></table>"
					+"";
			
				with(SubIFLayer.appendChild(document.createElement("iframe"))) 
				{ 
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
			var zuid="${zuid}";
			
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
				reclick=0;
			}
			
			/////
			var zuidprogram="${zuid2}";	
			function selectCategoryprogram(group_id,dpath){
				var treeviewobj = document.getElementById('divTreeviewprogram');
				//treeviewobj.removeNode(true);
				SubIFLayerprogram.parentNode.removeChild(SubIFLayerprogram);
				if(zuidprogram.indexOf("!"+group_id+"!")<0){
					zuidprogram+=group_id+"!";
					var opption=new Option(dpath,group_id);
					opption.title=dpath;
					document.myform.cnt_zuprogram.options.add(opption);
				}else{
					alert("已添加节目组:"+dpath);
				}
				reclick2=0;
				reclick=0;
			}
			
			function updateUser(){
				document.myform.cnt_zuid.value    =zuid;
				document.myform.program_zuid.value    =zuidprogram;
				myform.action="/admin/competence/updateUserDO.jsp";
				myform.submit();
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
	</script>
	</head>

	<body>
		<form   method="post" name="myform" id="myform">
	<div class="tab">
	<div class="tab_menu">
		<ul>
			<li class="selected">角色</li>
			<li>权限</li>
		</ul>
	</div>
	<div class="tab_box"> 
	   <div >		
			<input type="hidden" name="old_cnt_zuid" value="${zuid }"/>
			<input type="hidden" name="cnt_zuid" value="${zuid }"/>
			
			<input type="hidden" name="program_zuid" value="${zuid2 }"/>
			<table width="400" height="280" border="0" cellpadding="0" cellspacing="0">
			
					<tr>
						<td  width="25%" align="right" height="30px;" class="Bold">
							用户名：&nbsp;&nbsp;&nbsp;
						</td>
						<td  colspan="2" width="75%"  align="left"  class="Bold">
							<input type="hidden" name="username" value="${user.lg_name}"/>
							${user.lg_name }
						</td>
					</tr>
					<tr>
			            <td class="Line_01" colspan="3"></td>
			          </tr>
			          <tr    >
							<td   align="right" height="25px;" class="Bold">
								姓名：&nbsp;&nbsp;&nbsp;
							</td>
							<td  colspan="2"   align="left"  class="Bold">
								<input type="text" name="name" value="${user.name}"/>
							</td>
						</tr>
						<tr>
		            <td class="Line_01" colspan="3"></td>
		          </tr>
				  <tr>
						<td   align="right" height="30px;" class="Bold">
							邮箱：&nbsp;&nbsp;&nbsp;
						</td>
						<td  colspan="2" align="left"  class="Bold">
							<input type="text" name="email" value="${user.email}"/>
						</td>
					</tr>
					<tr>
	            <td class="Line_01" colspan="3"></td>
	             </tr>
			     <tr>
						<td   align="right" height="30px;" class="Bold">
							角色：&nbsp;&nbsp;&nbsp;
						</td>
						<td  colspan="2"  align="left" class="Bold">
							<select style="width: 120px" class="button" name="role">
								<option value="2" ${user.lg_role=='2'?'selected':''}>审核员</option>
								<option value="3"${user.lg_role=='3'?'selected':''}>一般用户</option>
							</select>
						</td>
					</tr>
				<tr>
	            <td class="Line_01" colspan="3"></td>
	          </tr>
			  <tr>
						<td width="25%" align="right" height="30px;" class="Bold">
							管理终端组：&nbsp;&nbsp;&nbsp;
						</td>
						<td width="50%" align="left" class="Bold">
						<select name="cnt_zu"  style="width: 200;height: 80;" multiple="multiple">
							${zuname }
						</select></td>
						<td  width="25%">
								<a href="javascript:;" onClick="javascript:popupCategoryWindow(event);" title="添加终端组"><img src="/images/btn_search2.gif" border="0" /></a><br/><br/>
								<a href="javascript:;" onClick="javascript:delrole();" title="删除终端组"><img src="/images/del.gif" width="18" height="18" border="0" /></a>
						</td>
					</tr>
					<tr>
						<td class="Line_01" colspan="3"></td>
					</tr>
					<tr >
						<td width="25%" align="right" height="30px;" class="Bold">
							管理节目组：&nbsp;&nbsp;&nbsp;
						</td>
						<td width="50%" align="left" class="Bold">
						    <select name="cnt_zuprogram"  style="width: 200;height: 90;" multiple="multiple">
						    ${zuname2}
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
						<td  align="right" height="30px;" class="Bold">
							最后登录时间：&nbsp;&nbsp;&nbsp;
						</td>
						<td  colspan="2" align="left" class="Bold">
							${user.last_login_time }
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
				</table>
           </div>
           <div class="hide">
		     <div class="accordion">
					<h3>终端管理</h3>
				    <div>
				       <c:if test="${not empty authoritys}">
				             <c:set var="str" value="${authoritys}"/>

						     <input type="checkbox" name="authority" ${fn:contains(str,'A')?'checked':''} value="A"/>休眠&nbsp;&nbsp;
				 
						    <input type="checkbox"  name="authority" ${fn:contains(str,'B')?'checked':''} value="B"/>停止休眠&nbsp;&nbsp;
				         
				              
						    <input type="checkbox" name="authority" ${fn:contains(str,'C')?'checked':''} value="C"/>其他设置&nbsp;&nbsp;
				        
				              
						    <input type="checkbox"  name="authority" ${fn:contains(str,'D')?'checked':''} value="D"/>软件升级<br/>
				      
				              
							<input type="checkbox" name="authority"  ${fn:contains(str,'E')?'checked':''} value="E"/>文字通知&nbsp;&nbsp;
				          
				              
							<span style="display: none;"><input type="checkbox"  name="authority" ${fn:contains(str,'F')?'checked':''} value="F"/>终端初始化&nbsp;&nbsp;</span>
				         
				              
							<span style="display: none;"><input type="checkbox" name="authority" ${fn:contains(str,'G')?'checked':''} value="G"/>时间同步&nbsp;&nbsp;</span>
				          
				              
							<input type="checkbox" name="authority" ${fn:contains(str,'H')?'checked':''} value="H"/>音量时间设置&nbsp;&nbsp;<br/>
				         
				              
							<input type="checkbox" name="authority" ${fn:contains(str,'I')?'checked':''} value="I"/>删除终端&nbsp;&nbsp;
				       
				              
							<input type="checkbox"  name="authority" ${fn:contains(str,'J')?'checked':''} value="J"/>修改终端信息&nbsp;&nbsp;
				           
							<input type="checkbox" name="authority" ${fn:contains(str,'K')?'checked':''} value="K"/>删除节目(查看节目单时)
				       </c:if>
					</div>
					<h3>媒体库</h3>
					<div>	 
				      <c:if test="${not empty authoritys}">
				             <c:set var="str" value="${authoritys}"/>
					  <input type="checkbox" name="authority" ${fn:contains(str,'L')?'checked':''} value="L"/>新建媒体&nbsp;&nbsp;
					    
					  <input type="checkbox" name="authority" ${fn:contains(str,'M')?'checked':''} value="M"/>删除媒体&nbsp;&nbsp;
					 </c:if>
					</div>
					<h3>模板管理</h3>
					<div>
					  <c:if test="${not empty authoritys}">
				             <c:set var="str" value="${authoritys}"/>
						  <input type="checkbox" name="authority" ${fn:contains(str,'N')?'checked':''} value="N"/>新建模板&nbsp;&nbsp;
						 
						  <input type="checkbox" name="authority" ${fn:contains(str,'O')?'checked':''} value="O"/>删除模板&nbsp;&nbsp;
						 
						   <input type="checkbox" name="authority" ${fn:contains(str,'P')?'checked':''} value="P"/>修改模板&nbsp;&nbsp;
					    </c:if>
					</div>
					<h3>节目管理</h3>
					<div>  
					  <c:if test="${not empty authoritys}">
				             <c:set var="str" value="${authoritys}"/>
						  <input type="checkbox" name="authority" ${fn:contains(str,'Q')?'checked':''} value="Q"/>新建节目&nbsp;&nbsp;
						     
						  <input type="checkbox" name="authority" ${fn:contains(str,'R')?'checked':''} value="R"/>修改节目&nbsp;&nbsp;
						    
						  <input type="checkbox" name="authority" ${fn:contains(str,'S')?'checked':''} value="S"/>删除节目&nbsp;&nbsp;
						 
						  <input type="checkbox" name="authority" ${fn:contains(str,'T')?'checked':''} value="T"/>发送节目&nbsp;&nbsp;
					  </c:if>
					</div>
					<h3>时间表</h3>
					<div> 
					  <c:if test="${not empty authoritys}">
				             <c:set var="str" value="${authoritys}"/>
					  <input type="checkbox" name="authority" ${fn:contains(str,'U')?'checked':''} value="U"/>删除(查询节目)
					  </c:if>
					</div>
			</div>
         </div>
	</div>
</div>
     <center>
	    <div style="margin-top:100px;margin-bottom:10px">
                &nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;<input type="button" value=" 修 改 " class="button1" onClick="updateUser()" />
				&nbsp;&nbsp;&nbsp;<input type="button" value=" 取 消 " class="button1" onclick="parent.closedivframe(1);" />
         </div>
         <br/>
     </center>
		</form>
	</body>
</html>
