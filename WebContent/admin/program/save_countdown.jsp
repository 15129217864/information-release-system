<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<%
String moduleid=request.getParameter("moduleid");
String mtype=request.getParameter("mtype");
String otherContent=UtilDAO.getGBK(request.getParameter("count_downdate"));
String filem="";
if("count_down".equals(mtype)){
  filem="count_down"+moduleid+".txt";
}
UtilDAO utildao= new UtilDAO();
Map map= utildao.getMap();
map.put("id",moduleid);
map.put("m_other",filem+"#"+otherContent);
map.put("state","1");
String filepath=FirstStartServlet.projectpath+"serverftp/module_file/"+filem;
Util.writefile(filepath,otherContent);

utildao.updateinfo(map,"xct_module_temp");
request.getRequestDispatcher("/admin/program/addmediaList.jsp?save_state=save_ok&opp=0&moduleid="+moduleid).forward(request,response);

%>
