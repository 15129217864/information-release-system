<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="java.awt.Color"/>
<%
String moduleid=request.getParameter("moduleid");
String clockfgRGB=UtilDAO.getGBK(request.getParameter("clockfgRGB"));
String clockfg=UtilDAO.getGBK(request.getParameter("clockfg"));

String[] bgs=clockfgRGB.split("_");
Color color= new Color(Integer.parseInt(bgs[0]),Integer.parseInt(bgs[1]),Integer.parseInt(bgs[2]));
String  foreground=color.getRGB()+"";
/////System.out.println(moduleid);
UtilDAO utildao= new UtilDAO();
Map map= utildao.getMap();
map.put("id",moduleid);
map.put("m_other",clockfg);
map.put("name","时钟"+moduleid);
map.put("foreground",foreground);
map.put("state","1");
utildao.updateinfo(map,"xct_module_temp");

%>
<script>
parent.location.href="/admin/program/addmediaList.jsp?opp=0&moduleid=<%=moduleid%>";

</script>