<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<%
String keyvalue=request.getParameter("keyvalue")==null?"":request.getParameter("keyvalue");
if(!"".equals(keyvalue)){
	int result=new UtilDAO().regKey(keyvalue);
	String resultStr="ע��ʧ�ܣ���δ֪����";
	if(result==1){
		 resultStr="ע��ɹ��������µ�½��";
		 request.setAttribute("regok","ok");
	}
	else if(result==2||result==4){
		resultStr="ע��ʧ�ܣ�����������Чע���룩";
		request.setAttribute("regok","error");
		request.setAttribute("regint","3");
	}else{
		resultStr="����Ȩ���ѹ��ڣ���������Ч��Ȩ�룡";
		request.setAttribute("regok","error");
		request.setAttribute("regint","3");
	}
	request.setAttribute("regresult",resultStr);
	
	request.getRequestDispatcher("/login/login.jsp").forward(request,response);
}
%>
