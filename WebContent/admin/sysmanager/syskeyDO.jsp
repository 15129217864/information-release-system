<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%
String keyvalue=request.getParameter("keyvalue")==null?"":request.getParameter("keyvalue");
if(!"".equals(keyvalue)){
	int result=new UtilDAO().regKey(keyvalue);
	String resultStr="×¢²áÊ§°Ü£¡£¨Î´Öª´íÎó£©";
	if(result==1){
		 resultStr="×¢²á³É¹¦£¡";
	}if(result==2){
		resultStr="×¢²áÊ§°Ü£¡£¨ÇëÊäÈëÓÐÐ§×¢²áÂë£©";
	}
	request.setAttribute("regresult",resultStr);
	request.setAttribute("regok","ok");
	request.getRequestDispatcher("/admin/sysmanager/syskey.jsp").forward(request,response);
}
%>
