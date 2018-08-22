<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.xct.cms.domin.Template"/>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="java.util.Map"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String opp=request.getParameter("opp")==null?"0":request.getParameter("opp");
String s_id=request.getParameter("s_id")==null?"0":request.getParameter("s_id");
UtilDAO utildao = new UtilDAO();
TemplateDAO tdao= new TemplateDAO();
if("add".equals(opp)){//���
String s_title=utildao.getGBK(request.getParameter("s_title"));
int all_width=Integer.parseInt(request.getParameter("all_width")==null?"0":request.getParameter("all_width"));
int all_height=Integer.parseInt(request.getParameter("all_height")==null?"0":request.getParameter("all_height"));
int s_width=all_width;
int s_height=all_height;
if(all_width>640 && all_height>420){
s_width=640;
s_height=(all_height*640)/all_width;
if(s_height>420){
s_width=(s_width*420)/s_height;
s_height=420;
}
}else if(all_width>640 && all_height<=420){
s_width=640;
s_height=(all_height*640)/all_width;
}else if(all_width<=640 && all_height>420){
s_height=420;
s_width=(all_width*420)/all_height;
}

Map map=utildao.getMap();
map.put("s_title",s_title);
map.put("s_width",s_width+"");
map.put("s_height",s_height+"");
map.put("all_width",all_width+"");
map.put("all_height",all_height+"");
map.put("s_type","0");
utildao.saveinfo(map,"screen_type");
request.setAttribute("opptype","addok");
}else if("upd".equals(opp)){//�޸�
		String s_title=utildao.getGBK(request.getParameter("s_title"));
		int all_width=Integer.parseInt(request.getParameter("all_width")==null?"0":request.getParameter("all_width"));
		int all_height=Integer.parseInt(request.getParameter("all_height")==null?"0":request.getParameter("all_height"));
		
		int s_width=all_width;
		int s_height=all_height;
		if(all_width>640 && all_height>420){
		s_width=640;
		s_height=(all_height*640)/all_width;
		if(s_height>420){
		s_width=(s_width*420)/s_height;
		s_height=420;
		}
		}else if(all_width>640 && all_height<=420){
		s_width=640;
		s_height=(all_height*640)/all_width;
		}else if(all_width<=640 && all_height>420){
		s_height=420;
		s_width=(all_width*420)/all_height;
		}
		
		
		Map map=utildao.getMap();
		map.put("id",s_id);
		map.put("s_title",s_title);
		map.put("s_width",s_width+"");
		map.put("s_height",s_height+"");
		map.put("all_width",all_width+"");
		map.put("all_height",all_height+"");
		utildao.updateinfo(map,"screen_type");
		opp="0";
		request.setAttribute("opptype","updok");

}else if("upd_search".equals(opp)){//�޸�

		Template templdate=tdao.getScreenTypeBySid(s_id);
		request.setAttribute("templdate",templdate);
}else if("del".equals(opp)){//ɾ��
utildao.deleteinfo("id",s_id,"screen_type");
request.setAttribute("opptype","delok");
}


request.setAttribute("opp",opp);
request.setAttribute("s_id",s_id);
List<Template> list= tdao.getAllScreenType("");
request.setAttribute("list",list);
%>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma"  content="no-cache"/> 
<link rel="stylesheet" href="/css/style.css" type="text/css" />
	<style type="text/css">
	.template_div{width: 100%;height: 290;; border: #6699cc 0 solid;  padding-top: 0px; padding-left: 0px;  overflow:auto ;}
	</style>
</head>

<body bottommargin="0" topmargin="0" leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0" bgcolor="white">
<script language="JavaScript">
<!--//
function saveScreen_type(opp,sid){
s_title=myform.s_title.value;
all_width=myform.all_width.value;
all_height=myform.all_height.value;
if(s_title==""){
alert("��������Ļ���ƣ�");
return ;
}if(all_width==""){
alert("��������Ļ��ȣ�");
return ;
}if(all_height==""){
alert("��������Ļ��ȣ�");
return ;
}
if(opp=="upd_search"){
opp="upd";
}else {
opp="add";
}
myform.action="/admin/template/screen_type_manager.jsp?s_id="+sid+"&opp="+opp;
myform.submit();
}

//-->
</script>
<table cellpadding="5" cellspacing="0" cellpadding="10" height="100%"  border="0" width="100%">
<tr>
	<td align="center" width="100%" valign="top" height="50%">		
	<div style="width:100%;height: 150px;  overflow: auto">
	<table width="90%" border="0" cellpadding="0" cellspacing="0">
		<tr>
				<th width="30%" align="center" height="30px">��Ļ����</th>
				<th width="25%" align="center">��Ļ���</th>
				<th width="25%" align="center">��Ļ�߶�</th>
				<th width="20%" align="center">����</th>
		</tr>
		<tr><td class="Line_01" colspan="4"></td></tr>
		<c:forEach var="tem" items="${list}">
			<tr>
				<td align="center" height="20px">${tem.s_title }</td>
				<td align="center">${tem.all_width } px</td>
				<td align="center">${tem.all_height } px</td>
				<td align="center">
					<c:choose>
						<c:when test="${tem.s_type==1 }">
							��
						</c:when>
						<c:otherwise>
						<a href="javascript:;" style="color:blue" onclick="window.location.href='/admin/template/screen_type_manager.jsp?s_id=${tem.s_id}&opp=upd_search'">�޸�</a>&nbsp;&nbsp;
						<a href="javascript:;" style="color:blue" onclick="window.location.href='/admin/template/screen_type_manager.jsp?s_id=${tem.s_id}&opp=del'">ɾ��</a>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr><td class="Line_01" colspan="4"></td></tr>
		</c:forEach>
	</table>
	</div>		
	</td>
</tr>
<tr>
		<td align="center" width="100%" valign="top" height="50%">	
		<fieldset style="width: 400px;  height: 180px;border:#6699cc 1 solid;" >
						<legend align="center" style="font-size: 13px;font-weight: bold">${opp=='upd'||opp=='upd_search'?'�޸�':'���'}��Ļ</legend>
						<form action="" name="myform" method="post">
		<table width="300" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="40px">��Ļ���ƣ�</td>
				<td colspan="3"><input type="text" name="s_title" style="width: 200px;height: 20px" value="${templdate.s_title }"/></td>
			</tr>
			<tr>
				<td height="40px">��Ļ��ȣ�</td>
				<td><input type="text" name="all_width" value="${templdate.all_width }" style="width: 50px;height: 20px;pxime-mode:Disabled" onkeypress="return event.keyCode>=48&&event.keyCode<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" maxlength="4" />(px)</td>
				<td>��Ļ�߶ȣ�</td>
				<td><input type="text" name="all_height"  value="${templdate.all_height }" style="width: 50px;height: 20px;pxime-mode:Disabled" onkeypress="return event.keyCode>=48&&event.keyCode<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" maxlength="4" />(px)</td>
			</tr>
			<tr>
				<td colspan="4" align="center" height="50px" >
					<input type="button" value="${opp=='upd'||opp=='upd_search'?'�޸�':'���'}" onclick="saveScreen_type('${opp}','${s_id}')"
										class="button1" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" onclick="javascript:parent.closeBackGroundDiv()" value=" ȡ �� "
						class="button1" />
				</td>
			</tr>
		</table></form>
		</fieldset>
	</td>
</tr>
</table>
</body>
</html>
<c:if test="${opptype=='addok'}">
<script>
alert("�����Ļ�ɹ���");
</script>
</c:if>
<c:if test="${opptype=='updok'}">
<script>
alert("�޸���Ļ�ɹ���");
</script>
</c:if>
<c:if test="${opptype=='delok'}">
<script>
alert("ɾ����Ļ�ɹ���");
</script>
</c:if>




