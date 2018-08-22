<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.dao.LogsDAO"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
		String catrgoryname=UtilDAO.getGBK(request.getParameter("catrgoryname"));//南京军区调用UtilDAO.getUTF() 乱码 
		String zuid=request.getParameter("zu_id")==null?"0":request.getParameter("zu_id");
		String zu_path=request.getParameter("zu_path");
		String cmd=request.getParameter("cmd");
		UtilDAO utildao = new UtilDAO();
		Users user= (Users)request.getSession().getAttribute("lg_user");
		Map map=utildao.getMap();
		if("add".equals(cmd)){
			
			//String username=user.getLg_name();
			map.put("zu_pth", zu_path);
			map.put("zu_name", catrgoryname);
			map.put("zu_type", "0");
			map.put("zu_username", user.getLg_name()+"||");
			utildao.saveinfo(map, "xct_zu");
			request.setAttribute("oppstatus", "addok");
		}else if("update".equals(cmd)){
			map.put("zu_id", zuid);
			map.put("zu_name", catrgoryname);
			utildao.updateinfo(map, "xct_zu");
			request.setAttribute("oppstatus", "updateok");
			Map<String,Terminal> terminals=FirstStartServlet.terminalMap;
			Terminal terminal=null;
			String key="";
			for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
	 			key=entry.getKey();
				terminal=entry.getValue();
				if(zuid.equals(terminal.getCnt_zuid()+"")){
					terminal.setZu_name(catrgoryname);
					FirstStartServlet.terminalMap.put(key,terminal);
				}
			}
		}else if("DELETE".equals(cmd)){
			DBConnection dbc= new DBConnection();
			Connection conn= dbc.getConection();
			 LogsDAO logsdao= new LogsDAO();
			TerminalDAO terdao= new TerminalDAO();
			List<Terminal> cnt_zulist=terdao.getAllZu(conn," where zu_type=0 or zu_type=99 order by zu_id");
			List<Terminal> sub_list=terdao.getzu_subListByzu_pth(cnt_zulist,Integer.parseInt(zuid));
			
			//====================================================================================
			terdao.updateClientZuid(1,Integer.parseInt(zuid));//统一放到根目录下
			//====================================================================================
			for (Map.Entry<String, Terminal> entry : FirstStartServlet.terminalMap.entrySet()) {
				Terminal terminal=entry.getValue();
				if(terminal.getCnt_zuid()==Integer.parseInt(zuid)){
				    FirstStartServlet.terminalMap.get(entry.getKey()).setCnt_zuid(1);
				    FirstStartServlet.terminalMap.get(entry.getKey()).setZu_name("根");
				}
			}
			//====================================================================================
			String del_cnt_zu="";
			for(int i=0;i<sub_list.size();i++){
				Terminal ter= sub_list.get(i);
				//utildao.deleteinfo(conn,"cnt_zuid",ter.getZu_id()+"","xct_ipaddress"); ///删除组下面的终端
				utildao.deleteinfo(conn,"zu_id", ter.getZu_id()+"", "xct_zu");   ///删除子组
				
				del_cnt_zu+=ter.getZu_name()+",";
			}
			logsdao.addlogs1(conn,user.getLg_name(), "<span >用户"+user.getLg_name()+"删除了终端组：【"+del_cnt_zu+"】以及组下面的所有终端", 1);
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
<script type="text/javascript">
function gosub(init_tree){
	var forms = document.fm1;
	forms.cmd.value = 'TERMINAL';
	forms.init_tree.value = init_tree;
	forms.action = "/rq/searchTerminal";
	forms.submit();	
}
</script>
  </head>
  
  <body>
  <form name=fm1 method="post">
  <input type=hidden name="jtype"/>
<input type="hidden" name="subtype" id="subtype" value="" >		
<input type="hidden" name="cmd" id="cmd"  value="" >	
<input type="hidden" name="init_tree" id="init_tree"  value="" >
  </form>
   <script type="text/javascript">
    <c:if test="${oppstatus=='addok'}">
     parent.closedivframe();
     parent.frefresh();
    </c:if>
   <c:if test="${oppstatus=='updateok'}">
    parent.closedivframe();
    parent.frefresh();
   </c:if>
   <c:if test="${oppstatus=='deleteok'}">
     gosub(false);
   </c:if>
    
    </script>
  </body>
</html>
