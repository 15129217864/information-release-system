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
					alert("��ʾ��Ϣ����ѡ���նˣ�");
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
						alert("��ʾ��Ϣ����ѡ���նˣ�");
						return ;
				}
				pForm.checkips.value=reips;////@FLASH��Ŀ#20110729112008#2011-08-09 09:49:00#loop#10#
				pForm.action="/rq/requestClientOperating?cmd=SENDDEMANDPROGRAM&opp=lv0101@${program.program_Name}!${jmurl}!${nowtime}!loop!100!";
				pForm.submit();
				document.getElementById("xm_button").disabled="disabled";
		     }
			function closedivframe(){
				if(confirm("��ʾ��Ϣ��ȷ��ȡ�����͵㲥��Ŀ��")){
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
								<legend align="center" style="font-size: 13px; color: blue; font-weight: bold">ѡ���ն�</legend>
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
		    <legend align="center" style="font-size: 13px; color: blue; font-weight: bold">���͵㲥��Ŀ</legend>
            <table width="225" height="250" border="0" align="center" cellpadding="0"
				cellspacing="7" class="space">
				<tr>
					<td height="100px" align="center" style="color: green">��Ŀ���ƣ�${program.program_Name }
					</td>
				</tr>
				<tr>
					<td height="30" class="tail" align="center">
						<input type="button" class="button1" id="xm_button" onclick="sendDemandProgram(1)"
							value=" ���͵㲥 ">
						&nbsp;
						<input type="reset" class="button1" value=" ȡ �� "
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
