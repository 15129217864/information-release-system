<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%
String keyvalue=request.getParameter("keyvalue")==null?"":request.getParameter("keyvalue");
if(!"".equals(keyvalue)){
	int result=new UtilDAO().regKey(keyvalue);
	String resultStr="ע��ʧ�ܣ���δ֪����";
	if(result==1){
		 resultStr="ע��ɹ���";
	}if(result==2){
		resultStr="ע��ʧ�ܣ�����������Чע���룩";
	}
	request.setAttribute("regresult",resultStr);
	request.setAttribute("regok","ok");
	request.getRequestDispatcher("/admin/sysmanager/syskey.jsp").forward(request,response);
}
%>
