<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
    <%@page import="com.xct.cms.domin.Terminal"%>
<%@page import="com.xct.cms.dao.TerminalDAO"%>
<%
String zuid=request.getParameter("zuid")==null?"1":request.getParameter("zuid");
String opptype=request.getParameter("opptype")==null?"add":request.getParameter("opptype");
String zu_path="";
if("add".equals(opptype)){
	int zu_id=Integer.parseInt(zuid);
	TerminalDAO terminaldao= new TerminalDAO();
	List meida_zu=terminaldao.getAllZu("where zu_type=2");
	zu_path+= terminaldao.getzu_pathByzuID(meida_zu,zu_id);
}else if("update".equals(opptype)){
 	int zu_id=Integer.parseInt(zuid);
	TerminalDAO terminaldao= new TerminalDAO();
	List meida_zu=terminaldao.getAllZu("where zu_type=2");
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
	 <script language="javascript" src="/js/vcommon.js"></script>
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
	from1.action="oppcatrgoryok.jsp";
	from1.submit();
}
</script>
  </head>
  
  <body>
  <form action="" name="from1" method="post">
  <input type="hidden" name="zu_path" value="<%=zuid %>"/>
  <input type="hidden" name="cmd" value="<%=opptype %>"/>
   <input type="hidden" name="zu_id" value="${t1.zu_id }"/>
    <table  width="100%" height="70" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
    		<td colspan="2">&nbsp;&nbsp;<%=zu_path %>></td>
    	</tr>
    	<tr>
    		<td width="30%" align="right">组名：</td>
    		<td width="70%"><input type="text"  class="button" style="width: 90%" name="catrgoryname" value="${t1.zu_name }"/></td>
    	</tr>
    	<tr>
    		<td colspan="2" align="center">&nbsp;&nbsp;
    		<%if("add".equals(opptype)){ %>
    			<input type="button" value="添 加"  class="button" onclick="javascript:addcategory()"/>
    		<%}else{ %>	
    		<input type="button" value="编 辑"  class="button" onclick="javascript:addcategory()"/>
    			<%} %>
    			&nbsp;&nbsp;&nbsp;
    			<input type="button" value="取 消"   class="button" onclick="javascript:parent.closedivframe();"/></td>
    	</tr>
    </table>
    </form>
  </body>
</html>
