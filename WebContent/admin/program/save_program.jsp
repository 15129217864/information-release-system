<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.TemplateDAO"/>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Template"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.dao.LogsDAO"/>

<%
	String zuid=request.getParameter("zuid");
	String zuname=UtilDAO.getGBK(request.getParameter("zuname"));
	String programe=UtilDAO.getGBK(request.getParameter("programe"));
	String templateid=request.getParameter("templateid");
	String bgsoundpath=request.getParameter("bgsoundpath");
	String htmltextbackimg=request.getParameter("htmltextbackimg");
	String marketstockbackimg=request.getParameter("marketstockbackimg");
//	String ch_enchange=request.getParameter("ch_en");

	
	TemplateDAO templdatedao= new TemplateDAO();
	ModuleDAO moduledao= new ModuleDAO();
	DBConnection dbconn= new DBConnection();
	Connection conn=dbconn.getConection();
	UtilDAO utildao= new UtilDAO();
	String nowtime=UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
	String jmurl=UtilDAO.getNowtime("yyyyMMddHHmmss");
	///////更新背景音乐
	Map map1=UtilDAO.getMap();
	map1.put("template_id",templateid);
	map1.put("template_backmusic",bgsoundpath);
	map1.put("type","1");
	utildao.updateinfo(conn,map1,"xct_template_temp");
	//////生成节目配置文件
	Template template=templdatedao.getTemplateTempById(conn,templateid);
	List moduleList=moduledao.getModuleMediaByTemplateId(conn,templateid);
	for (int i = 0; i < moduleList.size(); i++) {
		com.xct.cms.domin.Module module = (com.xct.cms.domin.Module) moduleList.get(i);
		String mtype=module.getM_filetype();
		if("htmltext".equals(mtype) ){
	      if(null!=htmltextbackimg)
		    moduledao.updateModuleTempByTemplateIdForAlpha("htmltext",htmltextbackimg,templateid);
		}
		if("marketstock".equals(mtype)){
		    if(null!=marketstockbackimg)
		      moduledao.updateModuleTempByTemplateIdForAlpha("marketstock",marketstockbackimg,templateid);
		}
	}
	  
	  
	template.setVersion("6.6");
	template.setProgramname(programe);
	template.setRotate("0");
	template.setProgramePath(FirstStartServlet.projectpath+"serverftp/program_file/"+jmurl);
	template.setProgramUrl(jmurl);
	template.setHtmltextbackimg(htmltextbackimg);
	template.setMarketstockbackimg(marketstockbackimg);
	//System.out.println("request.getParameter(ch_en)---------update----------->"+ch_enchange);
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
	map.put("program_isdel","0");
	utildao.saveinfo(conn,map,"xct_JMPZ");
	
	new LogsDAO().addlogs1(conn,user.getName(), "用户【"+user.getName()+"】在节目组【"+zuname+"】下面创建了节目：【"+programe+"】", 1);
	//new ProgramDAO().deleteNoSaveProgram(conn);/////////删除未保存的节目
	dbconn.returnResources(conn);
%>
<script type="text/javascript">
	<!--
	parent.closedivframe(2);
	if(parent.homeframe.content.content.location){
		parent.homeframe.content.content.location.reload();
	}
	//-->
</script>
