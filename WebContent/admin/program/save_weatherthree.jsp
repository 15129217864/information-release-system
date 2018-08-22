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
String moduleid=request.getParameter("moduleid");
String city=UtilDAO.getGBK(request.getParameter("city"));
String w_image1=UtilDAO.getGBK(request.getParameter("w_image1"));
String w_start_temperature1=UtilDAO.getGBK(request.getParameter("w_start_temperature1"));
String w_end_temperature1=UtilDAO.getGBK(request.getParameter("w_end_temperature1"));

String w_image2=UtilDAO.getGBK(request.getParameter("w_image2"));
String w_start_temperature2=UtilDAO.getGBK(request.getParameter("w_start_temperature2"));
String w_end_temperature2=UtilDAO.getGBK(request.getParameter("w_end_temperature2"));

String w_image3=UtilDAO.getGBK(request.getParameter("w_image3"));
String w_start_temperature3=UtilDAO.getGBK(request.getParameter("w_start_temperature3"));
String w_end_temperature3=UtilDAO.getGBK(request.getParameter("w_end_temperature3"));

String weatherfg=request.getParameter("weatherfg");
String weatherfgRGB=request.getParameter("weatherfgRGB");


Document document = DocumentHelper.createDocument();
Element weatherElement = document.addElement("weather");
Element row1Element=weatherElement.addElement("row");
row1Element.setAttributeValue("id","1");
row1Element.setAttributeValue("city",city);
row1Element.setAttributeValue("image",w_image1);
row1Element.setAttributeValue("temperature",w_start_temperature1+"～"+w_end_temperature1);


Element row2Element=weatherElement.addElement("row");
row2Element.setAttributeValue("id","2");
row2Element.setAttributeValue("city",city);
row2Element.setAttributeValue("image",w_image2);
row2Element.setAttributeValue("temperature",w_start_temperature2+"～"+w_end_temperature2);


Element row3Element=weatherElement.addElement("row");
row3Element.setAttributeValue("id","3");
row3Element.setAttributeValue("city",city);
row3Element.setAttributeValue("image",w_image3);
row3Element.setAttributeValue("temperature",w_start_temperature3+"～"+w_end_temperature3);



XMLWriter writer;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("gbk");
		try {
			writer = new XMLWriter(new FileWriter(FirstStartServlet.projectpath+"serverftp/module_file/want-weather.xml"), format);
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
UtilDAO utildao= new UtilDAO();
/////////weather1221.xml#上海#1#a0.png#0℃#0℃#2#a0.png#0℃#0℃#3#a0.png#0℃#0℃
String weather="want-weather.xml#"+city+"#1#"+w_image1+"#"+w_start_temperature1+"#"+w_end_temperature1+"#2#"+w_image2+"#"+w_start_temperature2+"#"+w_end_temperature2+"#3#"+w_image3+"#"+w_start_temperature3+"#"+w_end_temperature3+"#"+weatherfg;
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
map.put("name","天气(多天)"+moduleid);
map.put("m_other",weather);
map.put("foreground",foreground);
map.put("state","1");
utildao.updateinfo(map,"xct_module_temp");		

request.getRequestDispatcher("/admin/program/addmediaList.jsp?save_state=save_ok&opp=0&moduleid="+moduleid).forward(request,response);
%>
