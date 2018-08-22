<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String left_menu=request.getParameter("left_menu");
String type=request.getParameter("type");
String zuid=request.getParameter("zuid");
String cnt_mac=request.getParameter("cnt_mac");
String month=request.getParameter("month");
%>
<html>
  <head>
    
    <title>My JSP 'ter_header.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
    <link rel="stylesheet" href="/css/style.css" type="text/css" />
    <script language="javascript" src="/js/jquery1.4.2.js"></script>
    <script language="javascript" src="/js/vcommon.js"></script>
  <script type="text/javascript">
    function goList(val ,iflag){
			if(iflag==1){
				$("#btn_list").attr({"background":"/images/device/tab_01_over.gif"});
				$("#btn_monitoring").attr({"background":"/images/device/tab_02.gif"});
			}else if(iflag==2){
				$("#btn_list").attr({"background":"/images/device/tab_01.gif"});
				$("#btn_monitoring").attr({"background":"/images/device/tab_02_over.gif"});
			}
			if(val=="info"){
				parent.content.location.href="viewproject.jsp?month=<%=month%>&left_menu=<%=left_menu%>&zuid=<%=zuid%>&cnt_mac=<%=cnt_mac%>";
			}else if(val=="cal"){
				parent.content.location.href="/admin/schedule/calendar.jsp?month=<%=month%>&left_menu=<%=left_menu%>&zuid=<%=zuid%>&cnt_mac=<%=cnt_mac%>&type="+val;
			}	
			parent.parent.menu.location.href="/admin/schedule/left.jsp?month=<%=month%>&opp=1&type="+val;	
        }
</script>
  </head>
  
 <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">	
  <table width="100%"  border="0" cellpadding="0" cellspacing="0" background="/images/device/btn_background.gif">
    <tr>
		<td  style="color: #2E6CA5;font-weight:bold;" height="33px">&nbsp;&nbsp;
		</td>
        </tr>
      </table>  
     <table width="100%"  border="0" cellspacing="0" cellpadding="0" >
    <tr>
	  <td class="TabBackground">
	  <table border="0" cellspacing="0" cellpadding="0">
        <tr> 
        <%if("cal".equals(type)){ %>
          
		   <td width="100" height="25"  id="btn_list"  align="center" onclick="JavaScript:goList('cal',1);" background='/images/device/tab_01_over.gif'>
		   		<div style='cursor:hand'>日 历</div>
		   </td>
		    <td width="100" height="25" id="btn_monitoring"  align="center" onclick="JavaScript:goList('info',2);" background='/images/device/tab_01.gif'>
           		<div style='cursor:hand'>详细信息</div>
           </td>
        <%}else{ %>
           
		   <td width="100" height="25"  id="btn_list"  align="center" onclick="JavaScript:goList('cal',1);" background='/images/device/tab_01.gif'>
		   		<div style='cursor:hand'>日 历</div>
		   </td>
		   <td width="100" height="25" id="btn_monitoring" align="center" onclick="JavaScript:goList('info',2);"  background='/images/device/tab_01_over.gif'>
           		<div >详细信息</div> 
           </td>
       <%} %>
        </tr>
      </table>
      </td>
      <td align="right">
     </td>
    </tr>
  </table> 
</body> 

</html>
