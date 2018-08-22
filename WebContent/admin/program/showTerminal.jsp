<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramAppDAO"/>
<jsp:directive.page import="com.xct.cms.domin.ProgramApp"/>
<jsp:directive.page import="com.xct.cms.utils.Pager"/>
<jsp:directive.page import="com.xct.cms.utils.PageDAO"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String batch=request.getParameter("program_id");
ProgramAppDAO programappdao= new ProgramAppDAO();
ProgramApp app= programappdao.getProgramAppByStr(" where batch="+batch);
String [] terminalips=app.getProgram_play_terminal().split("#");
TerminalDAO terminaldao= new TerminalDAO();
List<Terminal> terminallist= new ArrayList<Terminal>();
for(int i=1,z=terminalips.length; i<z;i++){
terminallist.add(terminaldao.getTerminalBystr(" and cnt_mac='"+terminalips[i]+"'"));
}
if(terminallist!=null&&terminallist.size()>0){
				int pagenum =Integer.parseInt(request.getParameter("pagenum")==null?"1":request.getParameter("pagenum"));
				Pager pager= new Pager(terminallist.size(),pagenum,10); 
				List list3= new PageDAO().getPageList(terminallist, pager.getCurrentPage(), pager.getPageSize());
				request.setAttribute("pager", pager);
				request.setAttribute("terminallist1", list3);
			}
request.setAttribute("program_id",batch);

%>
<html>
  <head>
    <title>My JSP 'showTerminal.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script type="text/javascript">
		function gopage(program_id,pagenum){
		window.location.href="/admin/program/showTerminal.jsp?program_id="+program_id+"&pagenum="+pagenum;
		}
		function closedivframe(){
			parent.closedivframe(1);
			
		}
		</script>
  </head>
  
  <body>
  <table cellpadding="0" cellspacing="0" border="0" width="400px">
  	<tr  class="TitleBackground">
  		<td align="center">�ն�����</td>
  		<td align="center">�ն�IP</td>
  		<td align="center">����״̬</td>
  	</tr>
  	<c:if test="${empty terminallist1}">
  	<tr  >
  		<td align="center" colspan="3" height="30">�����ն���Ϣ</td>
  	</tr>
  	
  	</c:if>
   <c:forEach var="terminal" items="${terminallist1}">
   	<tr  class="TableTrBg23_"
						onmouseover="this.className='TableTrBg23'"
						onmouseout="this.className='TableTrBg23_'">
  		<td align="center">${terminal.cnt_name}</td>
  		<td align="center">${terminal.cnt_ip}</td>
  		<td align="center">${terminal.cnt_islink_zh}</td>
  	</tr>
  	<tr>
						<td class="Line_01" colspan="4"></td>
					</tr>
   </c:forEach>
    <tr>
    <td align="center" colspan="3" height="20">
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="right" valign="top">
                  <a href="javascript:;" onclick="gopage('${program_id}',1);return false;"><img src="/images/btn_first.gif" border="0"/></a>&nbsp;
				  <a href="javascript:;" onclick="gopage('${program_id}','${pager.currentPage-1 }');return false;"><img src="/images/btn_pre.gif" border="0"/></a>&nbsp; ${pager.currentPage }&nbsp; 
                  <a href="javascript:;" onclick="gopage('${program_id}','${pager.currentPage+1 }');return false;"><img src="/images/btn_next.gif" border="0"/></a>&nbsp; 
                  <a href="javascript:;" onclick="gopage('${program_id}','${pager.end }');return false;"><img src="/images/btn_end.gif" border="0"/></a>&nbsp;
                  ��${pager.totalPage }ҳ 
				</td>
			</tr>
		</table>
    </td></tr>
   <tr><td align="center" colspan="3"  height="30"><input type="button" class="button1" onclick="closedivframe();" value=" ȷ �� "/></td></tr>
     </table>
  </body>
</html>