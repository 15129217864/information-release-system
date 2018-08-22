<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="org.dom4j.Document"/>
<jsp:directive.page import="org.dom4j.Element"/>
<jsp:directive.page import="org.dom4j.DocumentHelper"/>
<jsp:directive.page import="org.dom4j.io.XMLWriter"/>
<jsp:directive.page import="org.dom4j.io.OutputFormat"/>
<jsp:directive.page import="java.io.FileWriter"/>
<jsp:directive.page import="java.io.IOException"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="java.awt.Color"/>
<%
String weather_date=UtilDAO.getGBK(request.getParameter("weather_date"));
String weather_image=UtilDAO.getGBK(request.getParameter("weather_image"));
String city=UtilDAO.getGBK(request.getParameter("city"));
String weather_wind=UtilDAO.getGBK(request.getParameter("weather_wind"));
String start_temperature=UtilDAO.getGBK(request.getParameter("start_temperature"));
String end_temperature=UtilDAO.getGBK(request.getParameter("end_temperature"));

String moduleid=request.getParameter("moduleid");
String weatherfg=request.getParameter("weatherfg");
String weatherfgRGB=request.getParameter("weatherfgRGB");

Document document = DocumentHelper.createDocument();
Element weatherElement = document.addElement("weather");
Element weather_imageElement=weatherElement.addElement("weather_image1");
weather_imageElement.setText(weather_image);
Element indexElement=weatherElement.addElement("index");
indexElement.setText("1");
Element back_imageElement=weatherElement.addElement("back_image");
back_imageElement.setText("1");
Element font_colorElement=weatherElement.addElement("font_color");
font_colorElement.setText("-52429");
Element weather_windElement=weatherElement.addElement("weather_wind");
weather_windElement.setText(weather_wind);
Element weather_dateElement=weatherElement.addElement("weather_date");
weather_dateElement.setText(weather_date);
Element weather_temperatureElement=weatherElement.addElement("weather_temperature");
weather_temperatureElement.setText(start_temperature+"¡«"+end_temperature);
Element cityElement=weatherElement.addElement("city");
cityElement.setText(city);
XMLWriter writer;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("gbk");
		try {
			writer = new XMLWriter(new FileWriter(FirstStartServlet.projectpath+"serverftp/module_file/weather"+moduleid+".xml"), format);
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
UtilDAO utildao= new UtilDAO();
String weather="weather"+moduleid+".xml#"+weather_date+"#"+weather_image+"#"+city+"#"+weather_wind+"#"+start_temperature+"#"+end_temperature+"#"+weatherfg;
String foreground="-1";
if(weatherfgRGB.indexOf("_")<0){
 foreground=weatherfgRGB;
}else{
String[] fgs=weatherfgRGB.split("_");
Color color1= new Color(Integer.parseInt(fgs[0]),Integer.parseInt(fgs[1]),Integer.parseInt(fgs[2]));
 foreground=color1.getRGB()+"";
}

Map map= utildao.getMap();
map.put("id",moduleid);
map.put("name","ÌìÆøÔ¤±¨"+moduleid);
map.put("m_other",weather);
map.put("foreground",foreground);
map.put("state","1");
utildao.updateinfo(map,"xct_module_temp");		

request.getRequestDispatcher("/admin/program/addmediaList.jsp?save_state=save_ok&opp=0&moduleid="+moduleid).forward(request,response);
%>
