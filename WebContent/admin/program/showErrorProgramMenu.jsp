<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ProgramHistoryDAO"/>
<jsp:directive.page import="com.xct.cms.domin.ProgramHistory"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Program"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<html>
	<head>
		<title>��Ŀ����</title>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script language="javascript" src="/js/vcommon.js"></script>
	</head>
<body>
<center>
<div style="width: 750px;height: 400px;overflow: auto">
<table width="100%" border="0">
	<tr><td></td></tr>
<%
String allip=request.getParameter("allips");   //�ն�IP��ַ���ԡ�!���ָ�
String programe_file=request.getParameter("programe_file");//ѡ��Ҫ���͵Ľ�Ŀ���ƣ��ԡ�!���ָ�
String []iparray= allip.split("!");
String programefile[] = programe_file.split("!"); 
List<ProgramHistory> error_list=new ProgramHistoryDAO().showErrorProgramMenu(iparray, programefile);
Map<String,Terminal> cnt_map=FirstStartServlet.terminalMap;
Terminal t= new Terminal();
for(int i=0;i<error_list.size();i++){
ProgramHistory newMenu=error_list.get(i);
t=cnt_map.get(newMenu.getProgram_delid());
if(t!=null){
out.println("<tr><td align='left'>�նˡ�<span style='color:red'>"+t.getCnt_name()+"</span>���ڡ�<span style='color:blue'>"+newMenu.getProgram_SetDateTime()+"</span>�� ��<span style='color:blue'>"+newMenu.getProgram_SetDate()+"~"+newMenu.getProgram_EndDate()+"</span>���Ѳ��š�<span style='color:blue'>"+newMenu.getProgram_typeZh1()+"</span>����Ŀ��<span style='color:blue'>"+newMenu.getProgram_Name()+"</span>��</td></tr>");
out.println("<tr><td class='Line_01' ></td></tr>");
}
}
%>
</table>
</div>
<table width="100%">
	<tr><td width="100%"  align="center" height="50px"><input type="button" class="button1" onclick="parent.closedivframe3();" value=" ȷ �� " /></td></tr>
</table>
</center>
	</body>
</html>