<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.utils.DESPlusUtil"/>

<%

String filename=request.getParameter("templatefile");
/*String zuid=request.getParameter("zuid");
String programName1=request.getParameter("programName");
String programName=DESPlusUtil.Get().decrypt(programName1);
UtilDAO utildao= new UtilDAO();
String nowtime=utildao.getNowtime("yyyy-MM-dd HH:mm:ss");
Map map=utildao.getMap();
map.put("program_JMurl",filename); 
map.put("program_Name",programName); 
map.put("program_SetDateTime",nowtime); 
map.put("program_adduser","888"); 
map.put("program_treeid",zuid); 
utildao.saveinfo(map,"xct_JMPZ");
String programpath=FirstStartServlet.projectpath;
Util.copyFolder(programpath+"/admin/temp_program/"+filename,programpath+"/serverftp/program_file/"+filename);
*/
%>
<script type="text/javascript">
<!--
alert("修改节目成功！");
parent.listtop.location.href="/admin/program/program_list_top.jsp";
window.location.href="/admin/program/update_program.jsp?templatefile=<%=filename%>";
//-->
</script>
