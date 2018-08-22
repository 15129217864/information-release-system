<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="java.awt.Color"/>
<%
String moduleid=request.getParameter("moduleid");
String scroll_family=UtilDAO.getGBK(request.getParameter("fontName"));
String scroll_style=request.getParameter("fontTyle");
String scroll_size=request.getParameter("fontSize");
String scroll_span=request.getParameter("span");
String scroll_alpha=request.getParameter("alpha");
String scrollfg=request.getParameter("scrollfg");
String scrollfgRGB=request.getParameter("scrollfgRGB");
String scrollbg=request.getParameter("scrollbg");
String scrollbgRGB=request.getParameter("scrollbgRGB");
String foreground="";
String background="";
if(scrollbgRGB.indexOf("_")<0){
    background=scrollbgRGB;
}else{
	String[] bgs=scrollbgRGB.split("_");
	//System.out.println("scrollbgRGB---->"+scrollbgRGB);
	Color color= new Color(Integer.parseInt(bgs[0]),Integer.parseInt(bgs[1]),Integer.parseInt(bgs[2]));
	background=color.getRGB()+"";
 }
if(scrollfgRGB.indexOf("_")<0){
   foreground=scrollfgRGB;
}else{
  String[] fgs=scrollfgRGB.split("_");
  Color color1= new Color(Integer.parseInt(fgs[0]),Integer.parseInt(fgs[1]),Integer.parseInt(fgs[2]));
  foreground=color1.getRGB()+"";
}

String m_other=scrollfg+"#"+scrollbg;
UtilDAO utildao= new UtilDAO();
Map map= utildao.getMap();
map.put("id",moduleid);
map.put("m_other",m_other);
map.put("name","滚动文字"+moduleid);
map.put("background",background);
map.put("foreground",foreground);
map.put("span",scroll_span);
map.put("fontName",scroll_family);
map.put("fontTyle",scroll_style);
map.put("fontSize",scroll_size);
map.put("alpha",scroll_alpha);
map.put("state","1");
utildao.updateinfo(map,"xct_module_temp");
request.getRequestDispatcher("/admin/program/addmediaList.jsp?save_state=save_ok&opp=0&moduleid="+moduleid).forward(request,response);

%>
