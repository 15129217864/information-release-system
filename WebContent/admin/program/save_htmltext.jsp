<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<%
String moduleid=request.getParameter("moduleid");
String htmltextContent=UtilDAO.getGBK(request.getParameter("htmltextContent"));
String htmltextbg=request.getParameter("htmltextbg");
String bg=request.getParameter("bg");
bg=(bg==null?"/images/dtreeimg/IMAGE1.gif":bg);

//System.out.println("bg-------------->"+bg);
UtilDAO utildao= new UtilDAO();
Map map= utildao.getMap();
map.put("id",moduleid);
map.put("m_other","htmltext"+moduleid+".html");
map.put("background",null==htmltextbg?"-1":htmltextbg);
map.put("m_text",htmltextContent);
map.put("state","1");
String filepath=FirstStartServlet.projectpath+"serverftp/module_file/htmltext"+moduleid+".html";
Util.writefile(filepath,"<style>body{margin:0px;};</style><div  style='white-space:normal; word-break:break-all;background-color:#"+htmltextbg+"'>"+htmltextContent+"</div>");

utildao.updateinfo(map,"xct_module_temp");

String oflag="yes";
if(bg.endsWith("admin/program/")){
   bg="/images/dtreeimg/IMAGE1.gif";
   oflag="no";
}
request.setAttribute("oflag",oflag);
//System.out.println("oflag-------------->"+oflag);
request.getRequestDispatcher("/admin/program/addmediaList.jsp?save_state=save_ok&opp=0&moduleid="+moduleid+"&bg="+bg.trim()).forward(request,response);

%>
