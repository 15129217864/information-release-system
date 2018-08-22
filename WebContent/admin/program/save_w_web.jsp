<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<%
String moduleid=request.getParameter("moduleid");
String w_webContent1=UtilDAO.getGBK(request.getParameter("w_webContent1"));
String w_webContent2=UtilDAO.getGBK(request.getParameter("w_webContent2"));
String w_webContent3=UtilDAO.getGBK(request.getParameter("w_webContent3"));
String w_webContent4=UtilDAO.getGBK(request.getParameter("w_webContent4"));
String w_webContent5=UtilDAO.getGBK(request.getParameter("w_webContent5"));
String filem="web"+moduleid+".txt";
String content="";
if(!"http://".equals(w_webContent1)&&!w_webContent1.isEmpty()){
	content+=w_webContent1+"##@#";
} if(!"http://".equals(w_webContent2)&&!w_webContent2.isEmpty()){
	content+=w_webContent2+"##@#";
} if(!"http://".equals(w_webContent3)&&!w_webContent3.isEmpty()){
	content+=w_webContent3+"##@#";
} if(!"http://".equals(w_webContent4)&&!w_webContent4.isEmpty()){
	content+=w_webContent4+"##@#";
} if(!"http://".equals(w_webContent5)&&!w_webContent5.isEmpty()){
	content+=w_webContent5+"##@#";
}
String content11=w_webContent1+"@"+w_webContent2+"@"+w_webContent3+"@"+w_webContent4+"@"+w_webContent5;

content=content.substring(0,content.length()-4);

UtilDAO utildao= new UtilDAO();
Map map= utildao.getMap();
map.put("id",moduleid);
map.put("m_other",filem+"#"+content11);
map.put("state","1");
String filepath=FirstStartServlet.projectpath+"serverftp/module_file/"+filem;
Util.writefile(filepath,content);

utildao.updateinfo(map,"xct_module_temp");
request.getRequestDispatcher("/admin/program/addmediaList.jsp?save_state=save_ok&opp=0&moduleid="+moduleid).forward(request,response);

%>
