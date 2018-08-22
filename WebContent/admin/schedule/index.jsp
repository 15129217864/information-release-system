<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<% 
String left_menu=request.getParameter("left_menu")==null?"all":request.getParameter("left_menu");
String type=request.getParameter("type")==null?"cal":request.getParameter("type");
String zuid=request.getParameter("zuid")==null?"":request.getParameter("zuid");
String cnt_mac=request.getParameter("cnt_mac")==null?"":request.getParameter("cnt_mac");
String onedate=request.getParameter("onedate")==null?"":request.getParameter("onedate");
String twodate=request.getParameter("twodate")==null?"":request.getParameter("twodate");
String openstatus=request.getParameter("openstatus")==null?"0":request.getParameter("openstatus");
Calendar   cal   =   Calendar.getInstance(); 
String   m   =   (cal.get(Calendar.MONTH))+"";
String month=request.getParameter("month")==null?m:request.getParameter("month");
String content_url="/admin/schedule/calendar.jsp?left_menu="+left_menu+"&zuid="+zuid+"&cnt_mac="+cnt_mac+"&month="+month+"&type="+type;
if("info".equals(type))
content_url="viewproject.jsp?month="+month+"&left_menu="+left_menu+"&zuid="+zuid+"&cnt_mac="+cnt_mac+"&onedate="+onedate+"&twodate="+twodate+"&openstatus="+openstatus;
%>
<html>
	<head>
		<title>多媒体信息发布系统-终端管理</title><script language="javascript" src="/js/vcommon.js"></script>
		<link rel="stylesheet" href="/css/style2.css" type="text/css">
		
		<%if("all".equals(left_menu)){ %>
		<script language="JavaScript">
			parent.menu.location.href="/admin/schedule/left.jsp?month=<%=month %>&type=<%=type%>";	
			</script>
		<%}%>
	</head>
	<FRAMESET rows="58,*" cols="*" border="0">
		<FRAME noresize scrolling="no" frameBorder="0"
			src="/admin/schedule/ter_header.jsp?type=<%=type %>&left_menu=<%=left_menu %>&cnt_mac=<%=cnt_mac %>&zuid=<%=zuid %>&month=<%=month %>" name="deviceTop" />
		<FRAME frameBorder="0" scrolling="auto" frameBorder="0"
			src="<%=content_url%>"
			marginheight="0" marginwidth="0" name="content" />
	</FRAMESET>

</html>