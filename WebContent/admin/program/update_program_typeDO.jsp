<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="java.text.SimpleDateFormat"/>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String programe_file=request.getParameter("programe_file");
    String templateid=request.getParameter("templateid");
	String program_name = request.getParameter("program_name");

	String jmid=request.getParameter("jmid") == null ? "": request.getParameter("jmid");
	String typeid=request.getParameter("typeid") == null ? "": request.getParameter("typeid");
	String startdate=request.getParameter("startdate")==null?"":request.getParameter("startdate");
    String enddate=request.getParameter("enddate")==null?"":request.getParameter("enddate");
    String starttime=request.getParameter("starttime")==null?"":request.getParameter("starttime");
    String endtime=request.getParameter("endtime")==null?"":request.getParameter("endtime");
    String timecount=request.getParameter("minute");  //²¥·ÅÊ±³¤
    UtilDAO utildao= new  UtilDAO();
    
	if(typeid.equals("4")||typeid.equals("2")){
	   String today=new SimpleDateFormat("yyyy-MM-dd").format(new Date()); 
	   String todaytime=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())+":00"; 
	   startdate=today;
	   enddate=today;
	   starttime=new SimpleDateFormat("HH:mm").format(new Date())+":00";
	   endtime=UtilDAO.gettimeBytime(todaytime,Integer.parseInt(timecount)).split("#")[1];
	}
	Map map1= UtilDAO.getMap();
	map1.put("id",jmid);
	        
				map1.put("play_start_time", startdate);
				map1.put("play_end_time", enddate);
				map1.put("play_count", timecount);
				map1.put("day_stime1", starttime);
				map1.put("day_etime1", endtime);
				utildao.updateinfo(map1, "xct_JMPZ_type");
				
%>
<script>
parent.div_iframe2.location.href="/admin/program/opprojecttype.jsp?program_file=<%=programe_file%>&program_name=<%=program_name%>&templateid=<%=templateid%>";
parent.closedivframe3();

</script>