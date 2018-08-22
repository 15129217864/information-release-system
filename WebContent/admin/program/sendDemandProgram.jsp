<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.xy.dao.ConnectionFactory"/>
<jsp:directive.page import="com.xct.cms.utils.GetGroupChange"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Program"/>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	    ConnectionFactory connectionfactory = new ConnectionFactory();
		GetGroupChange getgroupchange = new GetGroupChange();
		Users user = (Users) request.getSession().getAttribute("lg_user");
		 String username=user.getLg_name(); 
		 getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory.getAllLinkCnt(3),"tree_"+username+".xml");
		 String jmurl=request.getParameter("programe_file");
		ProgramDAO programdao= new ProgramDAO();
		Program program=programdao.getProgram(" and program_JMurl='"+jmurl+"'");
		request.setAttribute("program",program);
		request.setAttribute("jmurl",jmurl);
		request.setAttribute("nowtime",UtilDAO.getNowtime("yyyy-MM-dd 11:00:00"));
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
		<link rel="stylesheet"  type="text/css" media="screen"  href="/admin/checkboxtree/style.css"/>
	    <link rel="stylesheet" type="text/css" href="/admin/checkboxtree/codebase/dhtmlx.css"/>
	    <script language="javascript" src="/js/vcommon.js"></script>
	    <script src="/admin/checkboxtree/codebase/dhtmlx.js"></script>
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
	
			function sendDemandProgram(){
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
				if(reips=="!" || reips==""){
						alert("提示信息：请选择终端！");
						return ;
				}
				pForm.checkips.value=reips;////@FLASH节目#20110729112008#2011-08-09 09:49:00#loop#10#
				pForm.action="/rq/requestClientOperating?cmd=SENDDEMANDPROGRAM&opp=lv0101@${program.program_Name}!${jmurl}!${nowtime}!loop!100!";
				pForm.submit();
				document.getElementById("xm_button").disabled="disabled";
		     }
			function closedivframe(){
				if(confirm("提示信息：确认取消发送点播节目？")){
					parent.closedivframe(1);
				}
			}
	</script>
  </head>
  <body>
   <table align="center" border="0" width="100%">
  	<tr>
  		<td width="40%" valign="top"  align="left" >
					<div id="addBox">
						<div class="taskBtn">
							<fieldset style="width:225px; height:300px;border:#6699cc 1 solid;">
								<legend align="center" style="font-size: 13px; color: blue; font-weight: bold">选择终端</legend>
								<div id="treeboxbox_tree"
									style="width:225px; height:290px;border :0px solid Silver; overflow:auto;"></div>
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
  		<fieldset style="width:225px; height:300px;border:#6699cc 1 solid;">
		    <legend align="center" style="font-size: 13px; color: blue; font-weight: bold">发送点播节目</legend>
            <table width="225" height="250" border="0" align="center" cellpadding="0"
				cellspacing="7" class="space">
				<tr>
					<td height="100px" align="center" style="color: green">节目名称：${program.program_Name }
					</td>
				</tr>
				<tr>
					<td height="30" class="tail" align="center">
						<input type="button" class="button1" id="xm_button" onclick="sendDemandProgram(1)"
							value=" 发送点播 ">
						&nbsp;
						<input type="reset" class="button1" value=" 取 消 "
							onclick="closedivframe();">
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		    <form action="" name="pForm" method="post" >
			   		<input type="hidden" name="checkips" value=""/>
			</form>  		
      </fieldset>
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
