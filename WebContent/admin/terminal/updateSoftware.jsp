<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.xy.dao.ConnectionFactory"/>
<jsp:directive.page import="com.xct.cms.xy.domain.Group"/>
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
		//	.getAllIpAddress(" where (cnt_islink=1 or cnt_islink=2) and cnt_status=1 "),"tree_"+username+".xml");
		getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory.getAllLinkCnt(3),"tree_"+username+".xml");
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
					
		<link rel="stylesheet" href="/admin/checkboxtree/style.css"
			type="text/css" media="screen" />
			<link rel="stylesheet" type="text/css" href="/admin/checkboxtree/codebase/dhtmlx.css"/>
	    <script src="/admin/checkboxtree/codebase/dhtmlx.js"></script>
		<script type="text/javascript">
function uploadBegin(){	
winstyle = "dialogWidth=360px; dialogHeight:300px; center:yes";
window.showModelessDialog("/progressbar.jsp",window,winstyle);//�ȸ������Chrome����֧��showModalDialog
}

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

function softwareUpdate(){
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
		if(reips=="!" ||reips==""){
				alert("��ʾ��Ϣ����ѡ���նˣ�");
				return ;
		}
	
	 //if(myform.onkey_all.checked != true){
		if(myform.file.value==""){
			alert("��ʾ��Ϣ����ѡ�������ļ���");
			return ;
		}
		myform.action="/admin/terminal/updateSoftwareDO.jsp?terminalips="+reips;
		uploadBegin();
		myform.submit();
	//}else{
	//	updateSoftfrom.checkips.value=reips;
	//	updateSoftfrom.action="/rq/requestClientOperating?cmd=UPDATESOFTWARE&opp=lv0018";
	//	updateSoftfrom.submit();
	//}
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
							<fieldset style="width:225px; height:310px;border:#6699cc 1 solid;">
							<legend align="center" style="font-size: 13px; color: blue; font-weight: bold">ѡ���ն�</legend>
							<div id="treeboxbox_tree"
								style="width:225px; height:300px;border :0px solid Silver; overflow:auto;"></div>
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
  		<fieldset style="width:225px; height:300px;border:#6699cc 1 solid;">
							<legend align="center" style="font-size: 13px; color: blue; font-weight: bold">�������</legend>
		<form action="" name="myform" method="post"
			enctype="multipart/form-data">
			<table width="215" height="290px" border="0" align="center" cellpadding="0"
				cellspacing="7" class="space">
				<tr>
					<td>
						<span style="color: green">������ʾ��ͨ�������������������<br/><br/>��������µĹ��ܣ�</span>
					</td>
				</tr>
				<tr>
					<td>
						<span style="width: 70px;">�����ļ���</span><br/><br/>
						<input type="file" name="file" class="button"
							style="width: 220px; height: 20px;" />

					</td>
				</tr>
				<tr><td><%--һ��������<input type="checkbox" name="onkey_all" value="1"/>--%>&nbsp;&nbsp;&nbsp;</td></tr>
				<tr>
					<td height="50" class="tail" align="center">
						<input type="button" class="button1" id="xm_button" onclick="softwareUpdate()"
							value=" �� �� ">
						&nbsp;
						<input type="reset" class="button1" value=" ȡ �� "  onclick="parent.closedivframe(1);">
							
						&nbsp;&nbsp;&nbsp;&nbsp;
						<span style="color: red"></span> &nbsp;&nbsp;&nbsp;&nbsp;
						<span style="color: red" id="times"></span>
					</td>
				</tr>

			</table>


		</form>
		</fieldset>
  		</td>
  	</tr>
  </table>
  <form action="" name="updateSoftfrom" method="post" >
			   		<input type="hidden" name="checkips" value=""/>
			</form>  	
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
