<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Template"/>

<%
String zuid=request.getParameter("zuid");
String programe=UtilDAO.getGBK(request.getParameter("programe"));
String templateid=request.getParameter("templateid");
String bgsoundpath=request.getParameter("bgsoundpath");
TemplateDAO templdatedao= new TemplateDAO();
ModuleDAO moduledao= new ModuleDAO();

UtilDAO utildao= new UtilDAO();
String nowtime=UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
String jmurl=UtilDAO.getNowtime("yyyyMMddHHmmss");
///////更新背景音乐
Map map1=UtilDAO.getMap();
map1.put("template_id",templateid);
map1.put("template_backmusic",bgsoundpath);
map1.put("type","1");
utildao.updateinfo(map1,"xct_template_temp");
//////生成节目配置文件
Template template=templdatedao.getTemplateTempById(templateid);
List moduleList=moduledao.getModuleMediaByTemplateId(templateid);
template.setVersion("5.5");
template.setProgramname(programe);
template.setRotate("0");
template.setProgramePath(FirstStartServlet.projectpath+"serverftp/program_file/"+jmurl);
template.setProgramUrl(jmurl);
templdatedao.saveTemplate(template, moduleList);

////添加一个新节目到数据库
Users user = (Users) request.getSession().getAttribute("lg_user");
Map map=UtilDAO.getMap();
map.put("program_JMurl",jmurl);
map.put("program_Name",programe); 
map.put("program_SetDateTime",nowtime); 
map.put("program_adduser",user.getLg_name()); 
map.put("program_status","0");
map.put("program_treeid",zuid);
map.put("templateid",templateid);
utildao.saveinfo(map,"xct_JMPZ");
%>
<script type="text/javascript">
<!--
alert("新建节目成功！");
parent.listtop.location.href="/admin/program/program_list_top.jsp";
window.location.href="/rq/programList?left_menu=&zu_id=<%=zuid%>";
//-->
</script>
