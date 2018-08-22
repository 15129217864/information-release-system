<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.xct.cms.dao.LogsDAO"/>
<jsp:directive.page import="com.xct.cms.utils.PageDAO"/>
<jsp:directive.page import="com.xct.cms.utils.Pager"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String left_menu=request.getParameter("left_menu")==null?"OPPLOGS":request.getParameter("left_menu");
String logtype=request.getParameter("logtype")==null?"1":request.getParameter("logtype");
String str="";
if("OPPLOGS".equals(left_menu)){
	str=" where  logdel=0 and logtype=1 ";
}else if("SYSLOGS".equals(left_menu)){
	str=" where  logdel=0 and logtype=0 ";
}
LogsDAO logsdao= new LogsDAO();
List<String> logsdate= logsdao.getALLLogDate(str);

if(logsdate!=null&&logsdate.size()>0){
				int pagenum =Integer.parseInt(request.getParameter("pagenum")==null?"1":request.getParameter("pagenum"));
				Pager pager= new Pager(logsdate.size(),pagenum,10); 
				List list3= new PageDAO().getPageList(logsdate, pager.getCurrentPage(), pager.getPageSize());
				request.setAttribute("pager", pager);
				request.setAttribute("logsdate",list3);
			}
%>
<html>
  <head>
    <title>My JSP 'logdate.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  <link rel="stylesheet" href="/css/style.css" type="text/css" />
<script type="text/javascript">
parent.listtop.location.href="/admin/sysmanager/competence_list_top.jsp";	

function all_chk(sel_all){
	var ckform = document.ifrm_Form;
  	var cbox   = ckform.checkbox;
  	if(cbox){
	    if(cbox.length){
	    	for(i=0;i<cbox.length;i++){
	    		ckform.checkbox[i].checked=sel_all;
	    	}
	    }else{
	    	ckform.checkbox.checked = sel_all;
	    }   
	}
}
function gopage(ldate,logtype,left_menu){
window.location.href="/admin/sysmanager/loginfo.jsp?left_menu="+left_menu+"&logdate="+ldate+"&logtype="+logtype;
}
function gopage1(pagenum,logtype,left_menu){
window.location.href="/admin/sysmanager/logdate.jsp?left_menu="+left_menu+"&pagenum="+pagenum+"&logtype="+logtype;
}
</script>
  </head>
  
  <body>
  	<form action="" name="ifrm_Form">
    <table  width="100%"  border="0" cellspacing="0" cellpadding="0" id="table1">
          <c:if test="${empty logsdate}">
<br/>
<center>暂无日志！</center>
</c:if>
    	<c:forEach var="ldate" items="${logsdate}">
    		<tr onmouseover="this.className='TableTrBgF0'" onmouseout="this.className=''" height="35" >
    		 <td width="1%">&nbsp;</td>
		      <td width="9%" align="center"><input type="checkbox" name="checkbox" value="${ldate }" ></td>
		      <td width="20%" class="InfoTitle"><a href="javascript:;" onclick="gopage('${ldate }','<%=logtype %>','<%=left_menu %>');return false;">${ldate }</a></td>
		     <td  width="78%" class="InfoTitle"></td>
</tr> <tr>
            <td class="Line_01" colspan="4"></td>
          </tr>
    	</c:forEach>
    </table>
</form>
     <br/>
     <c:if test="${pager.totalPage>1}">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" valign="top"align="right">
                  <a href="javascript:;" onclick="gopage1(1,'<%=logtype %>','<%=left_menu %>');return false;"><img src="/images/btn_first.gif" border="0"/></a>&nbsp;
				  <a href="javascript:;" onclick="gopage1(${pager.currentPage-1 },'<%=logtype %>','<%=left_menu %>');return false;"><img src="/images/btn_pre.gif" border="0"/></a>&nbsp; ${pager.currentPage }&nbsp; 
                  <a href="javascript:;" onclick="gopage1(${pager.currentPage+1 },'<%=logtype %>','<%=left_menu %>');return false;"><img src="/images/btn_next.gif" border="0"/></a>&nbsp; 
                  <a href="javascript:;" onclick="gopage1(${pager.end },'<%=logtype %>','<%=left_menu %>');return false;"><img src="/images/btn_end.gif" border="0"/></a>&nbsp;
                  共${pager.totalPage }页 
				</td>
			</tr>
</table></c:if>
  </body>
</html>
