<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>
<jsp:directive.page import="java.net.URLDecoder"/>

<%
String programurls=request.getParameter("programurls");
 String zuid=request.getParameter("zuid")==null?"1":request.getParameter("zuid");
String programname=request.getParameter("programname")==null?"新复制节目":request.getParameter("programname");

String rqurl=request.getParameter("rqurl");
request.setAttribute("rqurl",rqurl);

try {
	programname=URLDecoder.decode(URLDecoder.decode(programname,"UTF-8"),"UTF-8");
} catch (Exception e) {
	e.printStackTrace();
}

if(programurls!=null&&programurls.indexOf("!")>-1){
	String programurl[] = programurls.split("!");
	for (int i = 0; i < programurl.length; i++) {
		new ProgramDAO().copyProgramB(programname,programurl[i],zuid);
	}
 %>
	<script type="text/javascript">
	parent.closedivframe(2);
	alert("复制节目成功！");
	parent.homeframe.content.content.location.href='${rqurl}';
	</script>

<%}else{%>
	<script type="text/javascript">
	parent.closedivframe(2);
	alert("复制节目失败！");
	</script>
<%}%>

