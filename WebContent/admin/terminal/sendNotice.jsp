<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.xy.dao.ConnectionFactory"/>
<jsp:directive.page import="com.xct.cms.utils.GetGroupChange"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
			ConnectionFactory connectionfactory = new ConnectionFactory();
			GetGroupChange getgroupchange = new GetGroupChange();
		 Users user = (Users) request.getSession().getAttribute("lg_user");
		 String username=user.getLg_name();
		//getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory
			//	.getAllIpAddress(" where (cnt_islink=1 or cnt_islink=2) and cnt_status=1 "),"tree_"+username+".xml");
			getgroupchange.getProjectXmlByUsername(username, 0, connectionfactory.getAllLinkCnt(1),"tree_"+username+".xml");
%>
<html>
  <head> 
    <title>My JSP 'sendNotice.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
	
		<link rel="stylesheet" href="/admin/checkboxtree/style.css"
			type="text/css" media="screen" />
			<style type="text/css">
				#colorDiv{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none }
				#colorMessage{border:#6699cc solid; border-width:1px; background:#fff; color:#036; font-size:12px; line-height:100%;  display: none}
				#smallImgDiv{ position:absolute; z-index: 1; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none }
				#smallImgMassage{border:#6699cc solid; border-width:1px; background:#fff; color:#036; font-size:12px; line-height:100%;  display: none}
				.header{background:url(/images/device/btn_background.gif);  font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff}
			</style>
			
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
		function sendnotice(){
		
			var checkips=tree.getAllChecked();
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
		    var re1 = /(\<.[^\<]*\>)/g;
			form1.notice_content.value=form1.notice_content.value.replace(/"/g,"").replace(/'/g,"").replace(re1,"").replace(/\s/g,"");
			var content=form1.notice_content.value;
			var checkvalue=1;
			var minutes=form1.minutes.value;
			if(reips=="!" || reips==""){
				alert("��ʾ��Ϣ����ѡ���նˣ�");
				return ;
			}else
			 if(content==""){
				alert("��ʾ��Ϣ������������֪ͨ���ݣ�");
				booltmp=false ;
				return ;
			}
			if(minutes==""){
			    alert("��ʾ��Ϣ�������벥��ʱ����");
				return ;
			}
			
			for (i=0; i<form1.p_type.length; i++) {
		        if (form1.p_type[i].checked) {
		           checkvalue=form1.p_type[i].value;
		         }
		     }
			if(checkvalue==1){
				if(content.length<300){
					form1.opp.value="lv0024@"+minutes+"###"+content;
				}else{
					alert("��ʾ��Ϣ������֪ͨ�������Ϊ300���֣�");
					return ;
				}
				form1.cmd.value="SENDNOTICE";
			}else if(checkvalue==2){
				if(content.length<300){
					form1.opp.value="lvled03@"+minutes+"###"+content;  ///����LED��������
				}else{
					alert("��ʾ��Ϣ������֪ͨ�������Ϊ300���֣�");
					return ;
				}
				form1.cmd.value="SENDLEDNOTICE";
			}
			form1.action="/rq/sendNoticeaction";
			form1.submit();
		}
		
		function preview(){
		
			var fontName=form1.fontName.value;
			var fontTyle=form1.fontTyle.value;
			var fontSize=form1.fontSize.value;
			var span=form1.span.value;
			var alpha=form1.alpha.value;
			var scrollfgRGB=form1.scrollfgRGB.value;
			var scrollbgRGB=form1.scrollbgRGB.value;
		    var re1 = /(\<.[^\<]*\>)/g;
			form1.notice_content.value=form1.notice_content.value.replace(/"/g,"").replace(/'/g,"").replace(re1,"").replace(/\s/g,"");
			var content=form1.notice_content.value;
			
		    if(content==""){
				alert("��ʾ��Ϣ������������֪ͨ���ݣ�");
				booltmp=false ;
				return ;
		    }else{
		       alert(fontName+"!"+fontTyle+"!"+fontSize+"!"+span+"!"+alpha+"!"+scrollfgRGB+"!"+scrollbgRGB+"!"+content);
		    }
		}
		
		function mask(obj){
		   obj.value=obj.value.replace(/[^\d]/g,'')
		   if(obj.value.length>3)
		      obj.value=parseInt(obj.value.substr(0,3));
		}
	
		function mask_c(obj){//ճ������
		   clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))
		}
		
		function goKeypress(){
			if (event.keyCode == 13)
				sendnotice();
        }
        
        function showScrollFg(){
			showDiv("251","190","440","60px","/admin/terminal/select_color_scrolltemp.jsp?type=scrollfg");
		}
		function showScrollBg(){
			showDiv("251","190","460","60px","/admin/terminal/select_color_scrolltemp.jsp?type=scrollbg");
		}
		function showDiv(fwidth,fheight,left,top,url){
			document.body.scrollTop = "0px";
			document.getElementById("color_iframe").width=fwidth;
			document.getElementById("color_iframe").height=fheight;
			document.getElementById("colorDiv").style.left=left;
			document.getElementById("colorDiv").style.top=top;
			document.getElementById("color_iframe").src=url;
			document.getElementById("colorDiv").style.display='block';
			document.getElementById("colorMessage").style.display='block';
		}
		function closeDiv(){
			document.getElementById("colorDiv").src="/loading.jsp";
			document.getElementById('colorMessage').style.display="none";
		}
		function change_p(checkvalue){
			if(checkvalue==1){
				document.getElementById("scroll_bgId").style.display="";
				document.getElementById("alphaid").style.display="";
			}else if(checkvalue==2){
				document.getElementById("scroll_bgId").style.display="none";
				document.getElementById("alphaid").style.display="none";
			}
		}
		
</script>
  </head>
  <body>
  <table align="center" border="0">
  	<tr>
  		<td width="40%" valign="top"  align="center" >
			<div id="addBox">
					<div class="taskBtn">
							<fieldset style="width:250px; height:290px;border:#6699cc 1 solid;">
								<legend align="center" style="font-size: 13px; color: #1C8EF6; font-weight: bold">ѡ���ն�</legend>
								<div id="treeboxbox_tree" style="width:250px; height:280px;border :0px solid Silver; overflow:auto;"></div>
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
  		<td  align="center">
	  		<fieldset style="width:470px; height:290px;border:#6699cc 1 solid;">
					<legend align="center" style="font-size: 13px; color: #1C8EF6; font-weight: bold">����֪ͨ����</legend>
			  		<form action="" name="form1" method="post">
					         <input type="hidden" name="cmd" value="SENDNOTICE"/> 
						     <input type="hidden" name="opp"/> 
						     <input type="hidden" name="checkips" value="" />
						     
						     <input type="hidden" name="scrollfg" value=""/>
							 <input type="hidden" name="scrollfgRGB" value="-1"/>
							 <input type="hidden" name="scrollbg" value=""/>
							 <input type="hidden" name="scrollbgRGB" value="-16777012"/>
						     
						   <table cellpadding="1" cellspacing="1" width="420" border="0" >
							
							   	<tr bgcolor="#F5F9FD"><td colspan="8" align="left"  width="100%">����&nbsp;<input type="text" size="3" name="minutes" value="5" onkeyup="mask(this)" onbeforepaste="mask_c()" onkeypress="goKeypress()"/>&nbsp;����&nbsp;&nbsp;<font color="red">(300��)</font></td></tr>		   
							   	<tr bgcolor="#F5F9FD"><td colspan="8"> &nbsp;</td></tr>
							   	<tr bgcolor="#F5F9FD">
									<td align="center"  width="65">
										<select  style="width: 65px" title="����" name="fontName">
											<option value="����" selected="selected">����</option>
											<option value="����">����</option>
											<option value="����">����</option>
											<option value="��Բ">��Բ</option>
											<option value="΢���ź�">΢���ź�</option>
											<option value="Arial">Arial</option>
										</select>
									</td>
									<td align="center"  width="72">
										<select style="width: 72px"  name="fontTyle"> 
										
											<option value="0">����</option>
											<option value="1" selected="selected">����</option>
											<option value="2">б��</option>
										</select>
									</td>
									<td align="center"  width="72">
										<select style="width: 72px; " name="fontSize">
											
											<c:forEach begin="20" end="80" var="b" step="2">
												  <option value="${b }" ${b==40?'selected':'' }>${b }</option>
										    </c:forEach>
										</select>
									</td>
									<td align="center"  width="80px">
										<select style="width: 80px" name="span">
											<option value="10">��</option>
											<option value="20" selected="selected">����(�ٶ�)</option>
											<option value="30">��</option>
										</select>
									</td><td align="center"  width="62px" id="alphaid">
										<select style="width: 60px" name="alpha" >
											<option value="1.0" selected="selected">��͸��</option>
											<option value="0.1">͸��</option>
										</select>
									</td>
									<td align="center"  width="30">
										<div id="scroll_fgId" style="border:solid 1px #000000;background-color:#ffffff;width: 25px;height: 18px; cursor: pointer; font-family:Arial; font-size: 16; font-weight: bold; " title="������ɫ" onclick="showScrollFg()"></div>
									
									</td>
									<td align="center"  width="30">
										<div  id="scroll_bgId"   style="border:solid 1px #000000;background-color:#0000ff;width: 25px;height: 18px; cursor: pointer;font-family:Arial; font-size: 16; font-weight: bold " title="������ɫ" onclick="showScrollBg()"></div>
									</td>
									<td >&nbsp;</td>
							</tr>
							<tr>
								<td align="center" colspan="8" height="55px">
									<textarea rows="9" cols="81" name="notice_content" id="scrollContentid" onkeypress="goKeypress()"></textarea>
								</td>
							</tr>
							<tr>
							<td colspan="2" style="display: none">&nbsp;&nbsp;��ͨ��<input type="radio" checked="checked" value="1" name="p_type" onclick="change_p(this.value)"/>
										LED��<input type="radio"  value="2"   name="p_type"  onclick="change_p(this.value)"/></td>
							<td  height="40px" colspan="8"><input type="button" value=" �� �� " class="button1" onclick="sendnotice()"/>&nbsp;
							   	<input type="button" value=" ȡ �� " class="button1" onclick="parent.closedivframe(1);" />&nbsp;
							   	<!-- <input type="button" value=" Ԥ �� " class="button1" onclick="preview()"/> -->
							   	
							   	</td>
							</tr>
						</table>
			   		</form>
	          </fieldset>
  		</td>
  	</tr>
  </table>
  		<div id="colorDiv">
			<div id="colorMessage">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td >
							<iframe src="" scrolling="no" id="color_iframe"
								name="color_iframe" frameborder="0"></iframe>
						</td>
					</tr>
				</table>
			</div>
		</div>		
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
