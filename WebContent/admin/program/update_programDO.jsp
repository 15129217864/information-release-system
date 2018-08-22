<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Template"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.dao.LogsDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	Users user = (Users) request.getSession().getAttribute("lg_user");
if(null==user){
   return ;
}

String zuid=request.getParameter("zuid");
String zuname=UtilDAO.getGBK(request.getParameter("zuname"));
String programe=UtilDAO.getGBK(request.getParameter("programe"));
String jmurl=request.getParameter("jmurl");
String templateid=request.getParameter("templateid");
String bgsoundpath=request.getParameter("bgsoundpath");
String htmltextbackimg=request.getParameter("htmltextbackimg");
String marketstockbackimg=request.getParameter("marketstockbackimg");
//String ch_enchange=request.getParameter("ch_en");
//System.out.println("request.getParameter(ch_en)---------update----------->"+ch_enchange);
//System.out.println("request.getParameter(htmltextbackimg)--------update----1111-------->"+request.getParameter("htmltextbackimg"));
//System.out.println("request.getParameter(marketstockbackimg)--------update----2222-------->"+request.getParameter("marketstockbackimg"));

TemplateDAO templdatedao= new TemplateDAO();
ModuleDAO moduledao= new ModuleDAO();
DBConnection dbconn= new DBConnection();
Connection conn=dbconn.getConection();
Util.deleteFile(FirstStartServlet.projectpath+"/serverftp/program_file/"+jmurl);
UtilDAO utildao= new UtilDAO();
///////更新背景音乐
Map map1=UtilDAO.getMap();
map1.put("template_id",templateid);
map1.put("template_backmusic",bgsoundpath);
utildao.updateinfo(conn,map1,"xct_template_temp");
//////生成节目配置文件

	
Template template=templdatedao.getTemplateTempById(conn,templateid);
List moduleList=moduledao.getModuleMediaByTemplateId(conn,templateid);
        for (int i = 0; i < moduleList.size(); i++) {
		com.xct.cms.domin.Module module = (com.xct.cms.domin.Module) moduleList.get(i);
		String mtype=module.getM_filetype();
		if("htmltext".equals(mtype) ){
	      if(null!=htmltextbackimg&&!"".equals(htmltextbackimg))
		    moduledao.updateModuleTempByTemplateIdForAlpha("htmltext",htmltextbackimg,templateid);
		}
		if("marketstock".equals(mtype)){
		  if(null!=marketstockbackimg&&!"".equals(marketstockbackimg))
		    moduledao.updateModuleTempByTemplateIdForAlpha("marketstock",marketstockbackimg,templateid);
		}
	}
	  
template.setVersion("6.5");
template.setProgramname(programe);
template.setRotate("0");
template.setProgramePath(FirstStartServlet.projectpath+"serverftp/program_file/"+jmurl);
template.setProgramUrl(jmurl);
template.setHtmltextbackimg(htmltextbackimg);
template.setMarketstockbackimg(marketstockbackimg);
templdatedao.saveTemplate(template, moduleList);

////添加一个新节目到数据库
Map map=UtilDAO.getMap();
map.put("program_JMurl",jmurl);
map.put("program_Name",programe); 
map.put("program_treeid",zuid);
map.put("program_ISloop","0");
map.put("auditingstatus","0");
map.put("templateid",templateid);
utildao.updateinfo(conn,map,"xct_JMPZ");
new LogsDAO().addlogs1(conn,user.getName(), "用户【"+user.getName()+"】修改了节目组【"+zuname+"】下面的节目：【"+programe+"】", 1);
dbconn.returnResources(conn);

request.setAttribute("auditingstatus","0");
%>
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<script language="javascript" src="/js/vcommon.js"></script>
<style>
<!--
#divframe{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none }
#massage{border:#6699cc solid; border-width:1px; background:#fff; color:#036; font-size:12px; line-height:100%;  display: none}
.header{background:url(/images/device/btn_background.gif);  font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff;	cursor: move;}
 
-->
</style>
<script type="text/javascript">
<!--
function updateprogrem(){
var cnt_height=parent.parent.parent.parent.document.body.clientHeight;
var cnt_width=parent.parent.parent.parent.document.body.clientWidth;
template_width=700;
template_height=310;
parent.showDiv1("更新终端节目",template_width,template_height,"/admin/program/update_program_in_cnt.jsp?jmurl=<%=jmurl%>");

}
function closedivframe(){
window.location.href="/rq/programList?left_menu=&zu_id=<%=zuid%>";
parent.listtop.location.href="/admin/program/programtop1.jsp?updateprogram=ok";
}
//-->
</script>
<body>

<div id="divframe">
			<div id="massage">
				<table cellpadding="0" cellspacing="0">
					<tr height="30px;" class=header  onmousedown=MDown(divframe)>
						<td align="left" style="font-weight: bold">
							<span id="titlename"></span>
						</td>
						<td height="5px" align="right">
							<a href="javaScript:void(0);" style="color: #000000"
								onclick="closedivframe();">关闭</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<iframe src="/loading.jsp" scrolling="no" id="div_iframe"
								name="div_iframe" frameborder="0"></iframe>

						</td>
					</tr>
				</table>
			</div>
		</div>

</body>
<script type="text/javascript">

parent.closedivframe(2);
if(parent.homeframe.content.content.location){
  parent.homeframe.content.content.location.reload();
}
if(confirm("节目修改成功！是否立即更新到终端？")){
	  updateprogrem();
}

 //如下是需要审核时使用，每次更新都重新设置为待审核状态
/* <c:if test="${auditingstatus=='1'}">
	if(confirm("节目修改成功！是否立即更新到终端？")){
	  updateprogrem();
	}
</c:if>  */

</script>
