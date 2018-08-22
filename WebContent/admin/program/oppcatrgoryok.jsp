<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.dao.LogsDAO"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Program"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String catrgoryname=UtilDAO.getGBK(request.getParameter("catrgoryname"));
		String zuid=request.getParameter("zu_id");
		String zu_path=request.getParameter("zu_path");
		String cmd=request.getParameter("cmd");
		UtilDAO utildao = new UtilDAO();
		Users user= (Users)request.getSession().getAttribute("lg_user");
		Map map=utildao.getMap();
		if("add".equals(cmd)){
			
			//String username=user.getLg_name();
			map.put("zu_pth", zu_path);
			map.put("zu_name", catrgoryname);
			map.put("zu_type", "2");
			map.put("zu_username", user.getLg_name()+"||");
			utildao.saveinfo(map, "xct_zu");
			request.setAttribute("oppstatus", "addok");
			
		}else if("update".equals(cmd)){
			map.put("zu_id", zuid);
			map.put("zu_name", catrgoryname);
			utildao.updateinfo(map, "xct_zu");
			request.setAttribute("oppstatus", "updateok");
		}else if("DELETE".equals(cmd)){
		
			DBConnection dbc= new DBConnection();
			Connection conn= dbc.getConection();
			 LogsDAO logsdao= new LogsDAO();
			TerminalDAO terdao= new TerminalDAO();
			List<Terminal> media_zulist=terdao.getAllZu(conn," where zu_type=2 order by zu_id");
			List<Terminal> sub_list=terdao.getzu_subListByzu_pth(media_zulist,Integer.parseInt(zuid));
			String del_media_zu="";
			String delmedia="";
			ProgramDAO programdao= new ProgramDAO();
			List<Program> medialist= new ArrayList<Program>();
			for(int i=0;i<sub_list.size();i++){
				Terminal ter= sub_list.get(i);
				
				medialist= programdao.getALLProgram(conn," and xct_JMPZ.program_treeid ="+ter.getZu_id());////根据组获取组下面的所有媒体
				for(int j=0;j<medialist.size();j++){  ///删除媒体文件 
					Program p=medialist.get(j);
					if(p!=null){
						delmedia+=p.getProgram_Name()+"；";
						programdao.deleteinfo(conn,p.getProgram_JMurl()); ///删除组下面的节目
						Util.deleteFile(FirstStartServlet.projectpath+"/serverftp/program_file/"+p.getProgram_JMurl());
					}
				}
				utildao.deleteinfo(conn,"zu_id", ter.getZu_id()+"", "xct_zu");   ///删除子组
				
				del_media_zu+=ter.getZu_name()+"；";
			}
			logsdao.addlogs1(conn,user.getLg_name(), "<span >用户"+user.getLg_name()+"删除了节目组：【"+del_media_zu+"】以及组下面的节目【"+delmedia+"】", 1);
			dbc.returnResources(conn);
			request.setAttribute("oppstatus", "deleteok");
		}
%>
<html>
  <head>   
    <title>oppcatrgoryok</title><script language="javascript" src="/js/vcommon.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <script type="text/javascript">
    <c:if test="${oppstatus=='addok'}">
     parent.closedivframe();
     parent.location.href="/admin/program/program_zu.jsp";
    </c:if>
   <c:if test="${oppstatus=='updateok'}">
    parent.closedivframe();
     parent.location.href="/admin/program/program_zu.jsp";
   </c:if>
   <c:if test="${oppstatus=='deleteok'}">
   parent.parent.content.location.reload();
     window.location.href="/admin/program/program_zu.jsp";
     
   </c:if>
    
    </script>
  </body>
</html>
