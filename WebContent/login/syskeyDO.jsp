<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<%
String keyvalue=request.getParameter("keyvalue")==null?"":request.getParameter("keyvalue");
if(!"".equals(keyvalue)){
	int result=new UtilDAO().regKey(keyvalue);
	String resultStr="×¢²áÊ§°Ü£¡£¨Î´Öª´íÎó£©";
	if(result==1){
		 resultStr="×¢²á³É¹¦£¡ÇëÖØÐÂµÇÂ½£¡";
		 request.setAttribute("regok","ok");
	}
	else if(result==2||result==4){
		resultStr="×¢²áÊ§°Ü£¡£¨ÇëÊäÈëÓÐÐ§×¢²áÂë£©";
		request.setAttribute("regok","error");
		request.setAttribute("regint","3");
	}else{
		resultStr="´ËÊÚÈ¨ÂëÒÑ¹ýÆÚ£¡ÇëÊäÈëÓÐÐ§ÊÚÈ¨Âë£¡";
		request.setAttribute("regok","error");
		request.setAttribute("regint","3");
	}
	request.setAttribute("regresult",resultStr);
	
	request.getRequestDispatcher("/login/login.jsp").forward(request,response);
}
%>
