<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.xy.dao.ConnectionFactory"/>
<jsp:directive.page import="com.xct.cms.utils.GetGroupChange"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<%
		ConnectionFactory connectionfactory = new ConnectionFactory();
		GetGroupChange getgroupchange = new GetGroupChange();
		Users user = (Users) request.getSession().getAttribute("lg_user");
		if(null==user){
	       return ;
	    }
		String username=user.getLg_name();
		//getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory
				//.getAllIpAddress(" where (cnt_islink=1 or cnt_islink=2) and cnt_status=1 "),"tree_"+username+".xml");
		getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory.getAllLinkCnt(3),"tree_"+username+".xml");
		String nowtime=Util.getNowtime2();
%>
<html>
	<head>
		<title>My JSP 'restart.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script language="javascript" src="/js/vcommon.js"></script>
		
		<%--<link rel="stylesheet" href="/admin/checkboxtree/style.css" type="text/css" media="screen" />
		<link rel="STYLESHEET" type="text/css"	href="/admin/checkboxtree/dhtmlxtree.css">
		<script language="JavaScript" src="/admin/checkboxtree/dhtmlxcommon.js"></script>
		<script language="JavaScript" src="/admin/checkboxtree/dhtmlxtree.js"></script>
		--%>
		<link rel="stylesheet" type="text/css" href="/admin/checkboxtree/codebase/dhtmlx.css"/>
	    <script src="/admin/checkboxtree/codebase/dhtmlx.js"></script>
	    
		<script type="text/javascript">
			function isIP(strIP) {
				var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
			    var reg = strIP.match(exp);
			    if(reg==null){
			      return false;
			    }else{
			        return true;
			    }
			}
				
			function restartTerminal(){
				if(checkcnt_ips()){
					if(confirm("提示信息：确认重启选择终端？")){
					restartForm.action="/rq/requestClientOperating?cmd=RESTART&opp=lv0005";
					restartForm.submit();
					}
				}
			}
			function downTerminal(){
			if(checkcnt_ips()){
					if(confirm("提示信息：终端关机后需要唤醒或重新通电才会启动，确认关机？")){
					restartForm.action="/rq/requestClientOperating?cmd=ONCLOSE&opp=lv0007";
					restartForm.submit();
					}
				}
			
			}
			
			function clearTerminal(){
				if(checkcnt_ips()){
					if(confirm("提示信息：确认初始化选择终端？")){
					restartForm.action="/rq/clearselectedproject?cmd=CLEAR&opp=lv0029";
					restartForm.submit();
					}
				}
			}
			function timesynTerminal(){
				if(checkcnt_ips()){
					if(confirm("提示信息：确认时间同步选择终端？")){
					restartForm.action="/rq/requestClientOperating?cmd=SYNCHRONIZATION&opp=lv0014@<%=nowtime%>";
					restartForm.submit();
					}
				}
			}
			
			function checkcnt_ips(){
			   
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
				restartForm.checkips.value=reips;
				return true;
			}
	</script>
  </head>
  
  <body>
   <table align="center" border="0" height="400px" >
  	<tr>
  		<td width="40%" valign="top"  align="left" >
			<div id="addBox">
				 <div class="taskBtn">
					<fieldset style="width:225px; height:380px;border:#6699cc 1 solid;">
						<legend align="center" style="font-size: 13px; color: #1C8EF6; font-weight: bold;">选择终端</legend>
						<div id="treeboxbox_tree" style="width:225px; height:370px;border :0px solid Silver; overflow:auto;"></div>
						<div>
							<div id="booksHeader"></div>
						     <table id="booksTable" border="0" width="100%" cellpadding="1" cellspacing="1" bgcolor="#000000">
							      <tbody id="abc" cellpadding="1" cellspacing="1" bgcolor="#FFFFFF"></tbody>
						     </table>
					    </div>
				    </fieldset>
				 </div>
			</div>
		</td>
  		<td  align="center" valign="top">
  		<fieldset style="width:225px; height:90px;border:#6699cc 1 solid;margin-bottom: 3px">
			 <legend align="center" style="font-size: 13px; color:#1C8EF6; font-weight: bold">终端关机</legend>
             <table width="225" height="78px" border="0" align="center" cellpadding="0"
				cellspacing="7" class="space">
				<tr>
					<td height="30px" align="center">
						<span style="color: green" >友情提示：确认关掉选中终端？</span>
					</td>
				</tr>
				<tr>
					<td height="25px" class="tail" align="center">
						<input type="button" class="button1" onclick="downTerminal()"value=" 关 机 ">
						&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
  		<fieldset style="width:225px; height:90px;border:#6699cc 1 solid;margin-bottom: 3px">
							<legend align="center" style="font-size: 13px; color: blue; font-weight: bold">重启终端</legend>
  <table width="225" height="78px" border="0" align="center" cellpadding="0"
				cellspacing="7" class="space">
				<tr>
					<td height="30px" align="center">
						<span style="color: green" >友情提示：确认重启选中终端？</span>
					</td>
				</tr>
				<tr>
					<td height="25px" class="tail" align="center">
						<input type="button" class="button1" onclick="restartTerminal()"
							value=" 重 启 ">
						&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
		
		<fieldset style="width:225px; height:90px;border:#6699cc 1 solid;margin-bottom: 3px">
		    <legend align="center" style="font-size: 13px; color: blue; font-weight: bold">终端初始化</legend>
  			<table width="225" height="78px" border="0" align="center" cellpadding="0" cellspacing="7" class="space">
				<tr>
					<td height="30px" align="center">
						<span style="color: green" >友情提示：确认初始化选中终端？</span>
					</td>
				</tr>
				<tr>
					<td height="25px" class="tail" align="center">
						<input type="button" class="button1" onclick="clearTerminal()" value=" 初始化 "/>
							
						&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
		
<fieldset style="width:225px; height:90px;border:#6699cc 1 solid;margin-bottom: 3px">
							<legend align="center" style="font-size: 13px; color: blue; font-weight: bold">时间同步</legend>
  <table width="225" height="78px" border="0" align="center" cellpadding="0"
				cellspacing="7" class="space">
				<tr>
					<td height="30px" align="center">
						<span style="color: green" >友情提示：确认时间同步选择终端？</span>
					</td>
				</tr>
				<tr>
					<td height="25px" class="tail" align="center">
						<input type="button" class="button1" onclick="timesynTerminal();"
							value=" 时间同步 ">
						&nbsp;
						
					</td>
				</tr>
			</table>
		</fieldset>
		  <form action="" name="restartForm" method="post" >
		   		<input type="hidden" name="checkips" value=""/>
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
