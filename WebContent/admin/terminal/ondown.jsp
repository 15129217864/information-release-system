<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.xy.dao.ConnectionFactory"/>
<jsp:directive.page import="com.xct.cms.utils.GetGroupChange"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<%
	    ConnectionFactory connectionfactory = new ConnectionFactory();
		GetGroupChange getgroupchange = new GetGroupChange();
		Users user = (Users) request.getSession().getAttribute("lg_user");
		 String username=user.getLg_name(); 
		//getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory
				//.getAllIpAddress(" where  cnt_nowProgramName!='NULL' and cnt_playstyle!='CLOSE' and  cnt_islink in(1,2) and cnt_status=1 "),"tree_"+username+".xml");
		getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory.getAllLinkCnt(1),"tree_"+username+".xml");
%>
<html>
	<head>
		<title>My JSP 'restart.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<link rel="STYLESHEET" type="text/css" href="/admin/checkboxtree/dhtmlxtree.css">
		<link rel="stylesheet"  type="text/css" media="screen"  href="/admin/checkboxtree/style.css"/>
			
		<script language="JavaScript" src="/admin/checkboxtree/dhtmlxcommon.js"></script>
		<script language="JavaScript" src="/admin/checkboxtree/dhtmlxtree.js"></script>
		<script type="text/javascript">
			function isIP(strIP) {
		      var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
			    var reg = strIP.match(exp);
			    if(reg==null) {
			       return false;
			    } else{
			        return true;
			    }
		     }
	
			function restartTerminal(){
				var checkips=tree.getAllChecked();
				if(checkips==""){
					alert("提示信息：请选择终端！");
					return ;
				}
				var checkipArray=checkips.split(",");
				var reips="";
				for(i=0;i<checkipArray.length;i++){
					if(isIP(checkipArray[i])){
						reips+="!"+checkipArray[i].replace(",","!");
					}
			    }
				if(reips=="!" || reips==""){
						alert("提示信息：请选择终端！");
						return ;
				}
				restartForm.checkips.value=reips;
				restartForm.action="/rq/requestClientOperating?cmd=ONDOWN&opp=lv0012";
				restartForm.submit();
				document.getElementById("xm_button").disabled="disabled";
		     }
	</script>
  </head>
  <body>
   <table align="center" border="0">
  	<tr>
  		<td width="40%" valign="top"  align="left" >
					<div id="addBox">
						<div class="taskBtn">
							<fieldset style="width:225px; height:270px;border:#6699cc 1 solid;">
								<legend align="center" style="font-size: 13px; color: #1C8EF6; font-weight: bold">选择终端</legend>
								<div id="treeboxbox_tree"
									style="width:225px; height:270px;border :0px solid Silver; overflow:auto;"></div>
								       <div >
											<div id="booksHeader"></div>
											<table id="booksTable" border="0" width="100%" cellpadding="1"
												cellspacing="1" bgcolor="#000000">
												 <tbody id="abc" ></tbody>
											</table>
							             </div>
						      </fieldset>
						</div>
					</div>
				</td>
  		<td  align="center" valign="top">
  		<fieldset style="width:225px; height:240px;border:#6699cc 1 solid;">
		    <legend align="center" style="font-size: 13px; color: #1C8EF6; font-weight: bold">终端休眠</legend>
            <table width="225" height="250" border="0" align="center" cellpadding="0"
				cellspacing="7" class="space">
				<tr>
					<td height="50px" align="center">
						<span style="color: green" >友情提示：确认选中终端休眠？</span>
					</td>
				</tr>
				<tr>
					<td height="50" class="tail" align="center">
						<input type="button" class="button1" id="xm_button" onclick="restartTerminal()"
							value=" 休 眠 ">
						&nbsp;
						<input type="reset" class="button1" value=" 取 消 "
							onclick="parent.closedivframe(1);">
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		    <form action="" name="restartForm" method="post" >
			   		<input type="hidden" name="checkips" value=""/>
			</form>  		
      </fieldset>
  		</td>
  	</tr>
  </table>
        <script language="JavaScript">
			tree = new dhtmlXTreeObject("treeboxbox_tree", "100%", "100%", 0);
			tree.setSkin('dhx_skyblue');
			tree.setImagePath("/admin/checkboxtree/csh_bluebooks/");
			tree.enableCheckBoxes(1);
			tree.enableThreeStateCheckboxes(true);			
			tree.loadXML("/admin/checkboxtree/tree_<%=username%>.xml");
        </script>
	</body>
</html>
