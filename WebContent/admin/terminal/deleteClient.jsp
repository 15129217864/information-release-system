<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.xy.dao.ConnectionFactory"/>
<jsp:directive.page import="com.xct.cms.utils.GetGroupChange"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<%
		 ConnectionFactory connectionfactory = new ConnectionFactory();
		 GetGroupChange getgroupchange = new GetGroupChange();
		 Users user = (Users) request.getSession().getAttribute("lg_user");
		 if(null==user){
		     return ;
		 }
		 String username=user.getLg_name();
		//getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory
		//		.getAllIpAddress(" where cnt_status=1"),"tree_"+username+"2.xml");
		getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory.getAllLinkCnt(4),"tree_"+username+"2.xml");
%> 
<html>
	<head>
		<title>My JSP 'clearproject.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<link rel="stylesheet" href="/admin/checkboxtree/style.css"	type="text/css" media="screen" />
		<link rel="stylesheet" type="text/css" href="/admin/checkboxtree/codebase/dhtmlx.css"/>
	    <script src="/admin/checkboxtree/codebase/dhtmlx.js"></script>
		<script type="text/javascript">
	function isIP(strIP) {
	    var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
	    var reg = strIP.match(exp);
	    if(reg==null)
	    {
	      return false;
	    }
	    else
	    {
	        return true;
	    }
    }
	
function deleteClient(){
	var checkips=tree.getAllChecked();
    if(checkips==""){
		alert("��ʾ��Ϣ����ѡ���նˣ�");
		return ;
    }
	var checkipArray=checkips.split(",");
	var reips="";
	var tmp="";
	
	for(var i=0;i<checkipArray.length;i++){
	
	    tmp=checkipArray[i];
		//if(checkipArray[i].indexOf("_")!=-1){//�������IP��ַ��ͬ��MAC��ͬ������£������⣬Ҳ����˵ֵ�����ظ�,������»���
		//	tmp=tmp.substring(0,tmp.indexOf("_"));
		//}
	    if(tmp.indexOf("_")!=-1){
			if(isIP(tmp.split("_")[0])){
			    reips+="!"+tmp.replace(",","!");
			}
		}
	}
	if(reips=="!" || reips==""){
		alert("��ʾ��Ϣ����ѡ���նˣ���");
		return ;
	}
	clearprojectForm.checkips.value=reips;
	clearprojectForm.action="/admin/terminal/deleteClientDO.jsp";
	clearprojectForm.submit();
}

	</script>
  </head>
  
  <body>
   <table align="center" border="0">
  	<tr>
  		<td width="40%" valign="top"  align="left" >
					<div id="addBox">
						<div class="taskBtn">
							<fieldset style="width:225px; height:280px;border:#6699cc 1 solid;">
							<legend align="center" style="font-size: 13px; color: #1C8EF6; font-weight: bold">ѡ���ն�</legend>
							<div id="treeboxbox_tree"
								style="width:225px; height:270px;border :0px solid Silver; overflow:auto;"></div>
							<div >
								<div id="booksHeader"></div>
								<table id="booksTable" border="0" width="100%" cellpadding="1"
									cellspacing="1" bgcolor="#000000">
												  <tbody id="abc" cellpadding="1" cellspacing="1" bgcolor="#FFFFFF"></tbody>
											</table>
						          </div>
						          </fieldset>
						    </div>
						</div>
				</td>
  		<td  align="center" valign="top">
  		<fieldset style="width:215px; height:250px;border:#6699cc 1 solid;">
			<legend align="center" style="font-size: 13px; color: #1C8EF6; font-weight: bold">ɾ���ն�</legend>
            <table width="215" height="250px" border="0" align="center" cellpadding="0"
				cellspacing="7" class="space">
				<tr>
					<td height="50px" align="center">
						<span style="color: green" >������ʾ��ȷ��ɾ���նˣ�</span>
					</td>
				</tr>
				<tr>
					<td height="30px" class="tail" align="center">
						<input type="button" class="button1" onclick="deleteClient()"
							value=" ɾ �� ">
						&nbsp;
						<input type="reset" class="button1" value=" ȡ �� "
							onclick="parent.closedivframe(1);">
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
  <form action="" name="clearprojectForm" method="post" >
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
			tree.load("/admin/checkboxtree/tree_<%=username%>2.xml");
			
			// �������ݣ�����д�����ǵ�action����·������
			//tree.loadXML("/org/buildTree.action");
        </script>
	</body>
</html>
