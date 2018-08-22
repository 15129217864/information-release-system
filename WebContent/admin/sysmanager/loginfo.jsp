<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.LogsDAO" />
<jsp:directive.page import="com.xct.cms.domin.Logs" />
<jsp:directive.page import="com.xct.cms.utils.Pager" />
<jsp:directive.page import="com.xct.cms.utils.PageDAO" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
			String left_menu = request.getParameter("left_menu") == null ? "OPPLOGS"
			: request.getParameter("left_menu");
	String logdate = request.getParameter("logdate") == null ? "00"
			: request.getParameter("logdate");
	String logtype = request.getParameter("logtype") == null ? "1"
			: request.getParameter("logtype");
	LogsDAO logsdao = new LogsDAO();
	List<Logs> loginfos = logsdao
			.getLogsByStr(" where  logdel=0 and logdate='"
			+ logdate + "' and logtype=" + logtype);
	if (loginfos != null && loginfos.size() > 0) {
		int pagenum = Integer
		.parseInt(request.getParameter("pagenum") == null ? "1"
				: request.getParameter("pagenum"));
		Pager pager = new Pager(loginfos.size(), pagenum, 50);
		List list3 = new PageDAO().getPageList(loginfos, pager
		.getCurrentPage(), pager.getPageSize());
		request.setAttribute("pager", pager);
		request.setAttribute("loginfos", list3);
	}
%>

<html>
	<head>
		<title>My JSP 'loginfo.jsp' starting page</title>
		<script language="javascript" src="/js/vcommon.js"></script>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script type="text/javascript">
parent.deviceTop.location.href="/admin/sysmanager/ter_header1.jsp?logdate=<%=logdate%>&logtype=<%=logtype%>";
function goback(){
parent.deviceTop.location.href="/admin/sysmanager/ter_header.jsp";
window.location.href='logdate.jsp?left_menu=<%=left_menu%>&logtype=<%=logtype%>';
}
function gopage(logdate,logtype,pagenum){
window.location.href="/admin/sysmanager/loginfo.jsp?logdate="+logdate+"&logtype="+logtype+"&pagenum="+pagenum;
}
</script>
	</head>

	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			id="table1">
			<tr>
				<td height="30px" colspan="4" align="left">
					<input type="button" class="button" onclick="goback();"
						value=" ·µ »Ø " />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td class="Line_01" colspan="4"></td>
			</tr>
			<c:forEach var="loginfo" items="${loginfos}">
				<tr onmouseover="this.className='TableTrBg22'"
					onmouseout="this.className=''" height=20" >
					<td width="1%" height="25px;">
						&nbsp;
					</td>
					<!--  <td width="5%" align="center"><input type="checkbox" name="sel_all" value="checkbox" onclick="all_chk(this.checked);"></td>-->
					<td width="10%" class="InfoTitle">
						${loginfo.loguser }
					</td>
					<td width="15%" class="InfoTitle">
						${loginfo.logdate } ${loginfo.logtime }
					</td>
					<td width="74%" align="left">
					<div id= 'DivtxtName'   style= 'white-space:normal; display:block;width:100%; word-break:break-all'> 
						${loginfo.loglog }
						</div>
					</td>
				</tr>
				<tr>
					<td class="Line_01" colspan="4"></td>
				</tr>
			</c:forEach>
		</table>
		<br />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="5%"></td>
				<td align="center" valign="top" width="95%">
					<a href="javascript:;"
						onclick="gopage('<%=logdate%>','<%=logtype%>',1);return false;"><img
							src="/images/btn_first.gif" border="0" />
					</a>&nbsp;
					<a href="javascript:;"
						onclick="gopage('<%=logdate%>','<%=logtype%>',${pager.currentPage-1 });return false;"><img
							src="/images/btn_pre.gif" border="0" />
					</a>&nbsp; ${pager.currentPage }&nbsp;
					<a href="javascript:;"
						onclick="gopage('<%=logdate%>','<%=logtype%>',${pager.currentPage+1 });return false;"><img
							src="/images/btn_next.gif" border="0" />
					</a>&nbsp;
					<a href="javascript:;"
						onclick="gopage('<%=logdate%>','<%=logtype%>',${pager.end });return false;"><img
							src="/images/btn_end.gif" border="0" />
					</a>&nbsp; ¹²${pager.totalPage }Ò³
				</td>
			</tr>
		</table>
		<script type="text/javascript">
parent.listtop.location.href="/admin/sysmanager/competence_list_top1.jsp";	
</script>
	</body>
</html>
