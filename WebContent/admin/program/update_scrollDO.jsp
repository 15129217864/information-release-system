<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="com.xct.cms.dao.MediaDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Media"/>
<%
String mediaid=request.getParameter("mediaid")==null?"0":request.getParameter("mediaid");
String moduleid=request.getParameter("moduleid")==null?"0":request.getParameter("moduleid");
Media me =new MediaDAO().getMediaBy(mediaid);
String scroll_content=request.getParameter("scroll_content");
scroll_content=URLDecoder.decode(scroll_content,"UTF-8");
//String scroll_content=UtilDAO.getGBK(request.getParameter("scroll_content"));
String filepath=FirstStartServlet.projectpath+me.getFile_path()+me.getFile_name();
Util.writefile(filepath,scroll_content);
UtilDAO utildao = new UtilDAO();
Map map1= utildao.getMap();
map1.put("id",moduleid);
map1.put("m_text",scroll_content);
utildao.updateinfo(map1,"xct_module_temp");	
%>
<script type="text/javascript">
<!--
parent.closeDiv();
 parent.location.reload();    

//-->
</script>
