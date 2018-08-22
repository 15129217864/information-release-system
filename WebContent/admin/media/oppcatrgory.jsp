<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@page import="com.xct.cms.domin.Terminal"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.xct.cms.dao.TerminalDAO"%>
<%
String zuid=request.getParameter("zuid")==null?"1":request.getParameter("zuid");
String opptype=request.getParameter("opptype")==null?"add":request.getParameter("opptype");
String zu_pth=request.getParameter("zu_pth")==null?"0":request.getParameter("zu_pth");
request.setAttribute("zu_pth",zu_pth);
String zu_path="";
if("add".equals(opptype)){
	int zu_id=Integer.parseInt(zuid);
	TerminalDAO terminaldao= new TerminalDAO();
	List meida_zu=terminaldao.getAllZu("where zu_type=1");
	zu_path+= terminaldao.getzu_pathByzuID(meida_zu,zu_id);
	Terminal t1= new Terminal();
	t1.setIs_share(0);
	request.setAttribute("t1",t1);
}else if("update".equals(opptype)){
 	int zu_id=Integer.parseInt(zuid);
	TerminalDAO terminaldao= new TerminalDAO();
	List meida_zu=terminaldao.getAllZu("where zu_type=1");
	zu_path+= terminaldao.getzu_pathByzuID(meida_zu,zu_id);
	Terminal t1= new Terminal();
	for(int i=0;i<meida_zu.size();i++){
		Terminal t= (Terminal)meida_zu.get(i);
			if(zu_id==t.getZu_id()){
				t1=t;
				break;
			}
	
	}
	request.setAttribute("t1",t1);
}
 %>

<html>
  <head>
    <title>My JSP 'oppcatrgory.jsp' starting page</title><script language="javascript" src="/js/vcommon.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
<style type="text/css">
td{font-size: 12px;}
</style>
<script type="text/javascript">
function addcategory(){
   if(from1.catrgoryname.value.replace(/(^\s*)|(\s*$)/g, "")==""){
        alert("组名不能为空！");
        return ;
     }
	from1.action="/rq/oppcatrgory";
	from1.submit();
}

</script>
  </head>
  
  <body>
  <form action="" name="from1" method="post">
  <input type="hidden" name="zu_path" value="<%=zuid %>"/>
  <input type="hidden" name="cmd" value="<%=opptype %>"/>
   <input type="hidden" name="zu_id" value="${t1.zu_id }"/>
    <table  width="160" height="90" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr> 
    		<td colspan="2"><%=zu_path %>></td>
    	
    	</tr>
    	<tr>
    		<td width="50px" align="right">组名：</td>
    		<td><input type="text"  class="button" size="12" name="catrgoryname" value="${t1.zu_name }"/></td>
    	</tr>
    	
    		<c:choose>
    			<c:when test="${zu_pth==1||zu_pth==-1}">
    			<tr>
    		<td width="50px" align="right">共享：</td>
    		<td>
    				<input type="radio" ${t1.is_share==0?'checked':'' } value="0"  name="is_share" />否<input type="radio" ${t1.is_share==1?'checked':'' } value="1" name="is_share"/>是 
    			</td>
    	</tr>
    			</c:when>
    			<c:otherwise><tr>
    		<td colspan="2">
    				<input type="hidden" value="0" name="is_share"/>
    				</td>
    	</tr>
    			</c:otherwise>
    		</c:choose>
    		
    	<tr>
    		<td colspan="2" align="center">
    		<%if("add".equals(opptype)){ %>
    			<input type="button" value="添 加"  class="button" onclick="javascript:addcategory()"/>
    		<%}else{ %>	
    		<input type="button" value="编 辑"  class="button" onclick="javascript:addcategory()"/>
    			<%} %>
    			&nbsp;&nbsp;&nbsp;<input type="button" value="取 消"   class="button" onclick="javascript:parent.closedivframe();"/></td>
    	</tr>
    </table>
    </form>
  </body>
</html>
