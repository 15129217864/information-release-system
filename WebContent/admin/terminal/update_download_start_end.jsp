<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.xy.dao.ConnectionFactory" />
<jsp:directive.page import="com.xct.cms.utils.GetGroupChange" />
<jsp:directive.page import="com.xct.cms.domin.Users" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	ConnectionFactory connectionfactory = new ConnectionFactory();
	GetGroupChange getgroupchange = new GetGroupChange();
	Users user = (Users) request.getSession().getAttribute("lg_user");
	if(null==user){
       return ;
    }
	String username = user.getLg_name();
	//getgroupchange.getProjectXmlByUsername(username,0,connectionfactory.getAllIpAddress(" where (cnt_islink=1 or cnt_islink=2) and cnt_status=1 "),"tree_" + username + ".xml");
	getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory.getAllLinkCnt(3),"tree_"+username+".xml");
%>
<html>
	<head>
		<title>My JSP 'update_download_start_end.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
	
		<script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/admin/checkboxtree/style.css" type="text/css" media="screen" /> 
		<link rel="stylesheet" type="text/css" href="/admin/checkboxtree/codebase/dhtmlx.css"/>
	    <script src="/admin/checkboxtree/codebase/dhtmlx.js"></script>
		<script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<script type="text/javascript">
		function isIP(strIP) {
		   var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
		    var reg = strIP.match(exp);
		    if(reg==null){
		      return false;
		    } else{
		        return true;
		    }
		}
		function update(){
			var checkips=tree.getAllChecked();
			if(checkips==""){
					alert("提示信息：请选择终端！");
					return ;
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
			form1.checkips.value=reips;
			var sleepstarttime=document.getElementById("sleepstarttime").value;
			var sleependtime=document.getElementById("sleependtime").value;
			var downloadtime=document.getElementById("downloadtime").value;
			
			var sleepstarttime_minutes=document.getElementById("sleepstarttime_minutes").value;
			var sleependtime_minutes=document.getElementById("sleependtime_minutes").value;
			var downloadtime_minutes=document.getElementById("downloadtime_minutes").value;
		
			if(reips=="!" || reips==""){
				alert("提示信息：请选择终端！");
				return ;
			}	
			form1.opp.value="lv0023@"+downloadtime+":"+downloadtime_minutes+"#"+sleependtime+":"+sleependtime_minutes+"#"+sleepstarttime+":"+sleepstarttime_minutes;
			//alert(form1.opp.value);
			//form1.opp.value="lv0023@"+downloadtime+"#"+sleependtime+"#"+sleepstarttime;
			//alert(form1.opp.value);
			form1.action="/rq/updatedownloadstartend";
			form1.submit();
		}
		
		function cancelCntVolume(){
		    var checkips=tree.getAllChecked();
			if(checkips==""){
						alert("提示信息：请选择终端！");
						return ;
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
			DwrClass.updateCntVolume(reips,"lvmute",cancelCntVolumeok);
		  
		}
		
		function addCntVolume(){
			var checkips=tree.getAllChecked();
			if(checkips==""){
						alert("提示信息：请选择终端！");
						return ;
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
			DwrClass.updateCntVolume(reips,"lv0019@+1",addvolumeok);
		}
		function decreaseCntVolume(){
			var checkips=tree.getAllChecked();
			if(checkips==""){
				alert("提示信息：请选择终端！");
				return ;
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
			DwrClass.updateCntVolume(reips,"lv0019@-1",decreasevolumeok);
		}
		function cancelCntVolumeok(opp){
		  alert("设置静音操作成功！");
		}
		function addvolumeok(opp){
		  alert("增加音量成功！");
		}
		function decreasevolumeok(opp){
		  alert("减小音量成功！");
		}
</script>
	</head>
	<body>
		<table align="center" border="0"> 
			<tr>
				<td width="40%" valign="top" align="left">
					<div id="addBox">
						<div class="taskBtn">
							<fieldset
								style="width:225px; height:280px;border:#6699cc 1 solid;">
								<legend align="center"
									style="font-size: 13px; color: #1C8EF6; font-weight: bold">
									选择终端
								</legend>
								<div id="treeboxbox_tree"
									style="width:225px; height:270px;border :0px solid Silver; overflow:auto;"></div>
								<div>
									<div id="booksHeader"></div>
									<table id="booksTable" border="0" width="100%" cellpadding="1"
										cellspacing="1" bgcolor="#000000">
										<tbody id="abc" cellpadding="1" cellspacing="1"
											bgcolor="#FFFFFF"></tbody>
									</table>
								</div>
							</fieldset>
						</div>
					</div>
				</td>
				<td align="center" >
					<fieldset style="width:265px; height:40px;border:#6699cc 1 solid;margin-bottom: 10px">
						<legend align="center"
							style="font-size: 13px; color: #1C8EF6; font-weight: bold">
							音量控制
						</legend>
						<table border="0" width="250" align="center">
								<tr>
									<td height="40px" width="50%">
										&nbsp;&nbsp;&nbsp;音量控制：
									</td>
									<td  width="25%">
										<input type="button" value="静音" onclick="cancelCntVolume()" class="button"/>
									</td>
									<td  width="25%">
										<input type="button" value="增加" onclick="addCntVolume()" class="button"/>
									</td>
									<td  width="25%"><input type="button" value="减小" onclick="decreaseCntVolume()" class="button"/></td>
								</tr>
								</table>
						</fieldset>
						<form action="" name="form1" method="post">
							<input type="hidden" name="cmd" value="UPDATE_DOWNLOAD_START_END" />
							<input type="hidden" name="opp" />
							<input type="hidden" name="checkips" value="" />
							
					<fieldset style="width:265px; height:205px;border:#6699cc 1 solid;">
						<legend align="center" style="font-size: 13px; color: #1C8EF6; font-weight: bold">
							修改终端 定时下载时间、开关机时间
						</legend>
							<table border="0" width="250" align="center">
								
								<tr>
									<td height="35px">
										&nbsp;&nbsp;&nbsp;开机时间：
									</td>
									<td>
										<select id="sleepstarttime">
											<c:forEach begin="1" end="23" var="a" step="1">
												<option value="${a }" ${a==7?'selected':''}>
													${a }
												</option>
											</c:forEach>
										</select> ：
									</td>
									<td>
										<select id="sleepstarttime_minutes">
											<c:forEach begin="0" end="60" var="a" step="1">
												<option value="${a }" ${a==0?'selected':''}>
													${a }
												</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td height="35px">
										&nbsp;&nbsp;&nbsp;关机时间：
									</td>
									<td>
										<select id="sleependtime" >
											<c:forEach begin="1" end="23" var="b" step="1">
												<option value="${b }" ${b==20?'selected':''}>
													${b }
												</option>
											</c:forEach>
										</select> ：
									</td>
									<td>
										<select id="sleependtime_minutes" >
											<c:forEach begin="0" end="60" var="a" step="1">
												<option value="${a }" ${a==0?'selected':''}>
													${a }
												</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td height="35px">
										&nbsp;&nbsp;&nbsp;定时下载时间：
									</td>
									<td>
										<select id="downloadtime">
											<c:forEach begin="1" end="23" var="c" step="1">
												<option value="${c }" ${c==1?'selected':''}>
													${c }
												</option>
											</c:forEach>
										</select> ：
									</td>
									<td>
										<select id="downloadtime_minutes" >
											<c:forEach begin="0" end="60" var="a" step="1">
												<option value="${a }" ${a==0?'selected':''}>
													${a }
												</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td align="left" height="20px" colspan="3">
									<span style="color: red" >注意：定时下载时间必须在开机时间之后。</span>
									</td>
								</tr>
								<tr>
									<td align="center" height="40px" colspan="3">
										<input type="button" value=" 修 改 " class="button1"
											onclick="update()" />
										&nbsp;
										<input type="button" value=" 取 消 " class="button1"
											onclick="parent.closedivframe(1);" />
									</td>
								</tr>
							</table>
					</fieldset>
				</form>
				</td> 
			</tr>
		</table>
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
