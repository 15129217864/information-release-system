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
			form1.checkips.value=reips;
			var sleepstarttime=document.getElementById("sleepstarttime").value;
			var sleependtime=document.getElementById("sleependtime").value;
			var downloadtime=document.getElementById("downloadtime").value;
			
			var sleepstarttime_minutes=document.getElementById("sleepstarttime_minutes").value;
			var sleependtime_minutes=document.getElementById("sleependtime_minutes").value;
			var downloadtime_minutes=document.getElementById("downloadtime_minutes").value;
		
			if(reips=="!" || reips==""){
				alert("��ʾ��Ϣ����ѡ���նˣ�");
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
			DwrClass.updateCntVolume(reips,"lvmute",cancelCntVolumeok);
		  
		}
		
		function addCntVolume(){
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
			DwrClass.updateCntVolume(reips,"lv0019@+1",addvolumeok);
		}
		function decreaseCntVolume(){
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
			DwrClass.updateCntVolume(reips,"lv0019@-1",decreasevolumeok);
		}
		function cancelCntVolumeok(opp){
		  alert("���þ��������ɹ���");
		}
		function addvolumeok(opp){
		  alert("���������ɹ���");
		}
		function decreasevolumeok(opp){
		  alert("��С�����ɹ���");
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
									ѡ���ն�
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
							��������
						</legend>
						<table border="0" width="250" align="center">
								<tr>
									<td height="40px" width="50%">
										&nbsp;&nbsp;&nbsp;�������ƣ�
									</td>
									<td  width="25%">
										<input type="button" value="����" onclick="cancelCntVolume()" class="button"/>
									</td>
									<td  width="25%">
										<input type="button" value="����" onclick="addCntVolume()" class="button"/>
									</td>
									<td  width="25%"><input type="button" value="��С" onclick="decreaseCntVolume()" class="button"/></td>
								</tr>
								</table>
						</fieldset>
						<form action="" name="form1" method="post">
							<input type="hidden" name="cmd" value="UPDATE_DOWNLOAD_START_END" />
							<input type="hidden" name="opp" />
							<input type="hidden" name="checkips" value="" />
							
					<fieldset style="width:265px; height:205px;border:#6699cc 1 solid;">
						<legend align="center" style="font-size: 13px; color: #1C8EF6; font-weight: bold">
							�޸��ն� ��ʱ����ʱ�䡢���ػ�ʱ��
						</legend>
							<table border="0" width="250" align="center">
								
								<tr>
									<td height="35px">
										&nbsp;&nbsp;&nbsp;����ʱ�䣺
									</td>
									<td>
										<select id="sleepstarttime">
											<c:forEach begin="1" end="23" var="a" step="1">
												<option value="${a }" ${a==7?'selected':''}>
													${a }
												</option>
											</c:forEach>
										</select> ��
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
										&nbsp;&nbsp;&nbsp;�ػ�ʱ�䣺
									</td>
									<td>
										<select id="sleependtime" >
											<c:forEach begin="1" end="23" var="b" step="1">
												<option value="${b }" ${b==20?'selected':''}>
													${b }
												</option>
											</c:forEach>
										</select> ��
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
										&nbsp;&nbsp;&nbsp;��ʱ����ʱ�䣺
									</td>
									<td>
										<select id="downloadtime">
											<c:forEach begin="1" end="23" var="c" step="1">
												<option value="${c }" ${c==1?'selected':''}>
													${c }
												</option>
											</c:forEach>
										</select> ��
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
									<span style="color: red" >ע�⣺��ʱ����ʱ������ڿ���ʱ��֮��</span>
									</td>
								</tr>
								<tr>
									<td align="center" height="40px" colspan="3">
										<input type="button" value=" �� �� " class="button1"
											onclick="update()" />
										&nbsp;
										<input type="button" value=" ȡ �� " class="button1"
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
