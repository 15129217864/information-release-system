<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Module"/>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%

String opp=request.getParameter("opp")==null?"0":request.getParameter("opp");
String moduleid=request.getParameter("moduleid");
if("1".equals(opp)){
String style_s=request.getParameter("style_s")==null?"0":request.getParameter("style_s");
UtilDAO utildao= new UtilDAO();
Map map= utildao.getMap();
map.put("id",moduleid);
map.put("fontName",style_s.replace(",","#"));
utildao.updateinfo(map,"xct_module_temp");
request.setAttribute("updatestate","updateok");
}


ModuleDAO mdao= new ModuleDAO();
Module module = mdao.getModuleTempByModuleid(Integer.parseInt(moduleid));

String[] img_style=module.getFontName().split("#");
request.setAttribute("img_style",img_style);

request.setAttribute("moduleid",moduleid);
%>

<html>
  <head>
    <title>My JSP 'select_media.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="/css/style.css" type="text/css" />
	<script language="javascript" src="/js/vcommon.js"></script>
	<link rel="stylesheet" href="/css/style.css" type="text/css" />
	<link rel="STYLESHEET" type="text/css" href="/admin/checkboxtree/dhtmlxtree.css">
	<link rel="stylesheet" href="/admin/checkboxtree/style.css" type="text/css" media="screen" />
	<script language="JavaScript" src="/admin/checkboxtree/dhtmlxcommon.js"></script>
	<script language="JavaScript" src="/admin/checkboxtree/dhtmlxtree.js"></script>
	<style type="text/css">
	.template_div{width: 100%;height: 100%;; border: #6699cc 0 solid;  padding-top: 10px; padding-left: 15px;  overflow:auto ;}
	</style>
	<script type="text/javascript">

	function view_img_style(){
		var checkips=tree.getAllChecked();
	    if(checkips==""){
			alert("提示信息：请选择终端！");
			return ;
	     }
		document.getElementById("div_iframe1").src="/admin/program/play_img.jsp?moduleid=${moduleid}&opp=1&style_s="+checkips;
	}
	function save_img_style(){
		var checkips=tree.getAllChecked();
	    if(checkips==""){
			alert("提示信息：请选择终端！");
			return ;
	    }
	    window.location.href="/admin/program/change_img_style.jsp?opp=1&moduleid=${moduleid}&style_s="+checkips;
	
	}
	</script>
	</head>
	
  
  <body> 
   <table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
   	<tr>
   		<td valign="top" width="20%" background="/images/menu_bg1.gif"
									style="background-position: right; background-repeat: repeat-y">
   		<div class="template_div" id="template_div">
<div id="treeboxbox_tree" style="width:235px; height:400px;border :0px solid Silver;"></div>
   		</div>
   		</td>
   		<td valign="top" width="80%">
   		 <table width="100%" height="100%" border="0">
   		 <tr><td height="30px" width="100%">
   	<table cellpadding="0" cellspacing="5" border="0" width="100%" height="100%">
   				<tr>
   					<td height="30px" ><input type="button" class="button1" onclick="view_img_style()" value=" 预 览 " />
   					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: green">友情提示：多个特效系统会随机播放。<span style="color: red">（高清图片不支持切换效果）</span></span>
   					</td>
   					
   				</tr></table>
   </td></tr>
   <tr><td class="Line_01" ></td></tr>
   		<tr><td>
   		<iframe src="/admin/program/play_img.jsp?moduleid=${moduleid}" width="100%" height="90%" scrolling="no" id="div_iframe1"  name="div_iframe1" frameborder="0" ></iframe> 
		</td></tr></table>
		</td>
   	</tr>
   	 <tr><td class="Line_01" colspan="2" ></td></tr>
   	<tr><td height="30px;" align="center" colspan="2" >
   	<input type="button" class="button1" onclick="save_img_style()" value=" 确 定 " />&nbsp;&nbsp;&nbsp;&nbsp;
   	<input type="button" class="button1" onclick="parent.closedivframe3();" value=" 取 消 " /></td></tr>
   </table>
  </body>
  
  
    <script language="JavaScript">
			tree = new dhtmlXTreeObject("treeboxbox_tree", "100%", "100%", 0);
			tree.setSkin('dhx_skyblue');
			tree.setImagePath("/admin/checkboxtree/csh_bluebooks/");
			tree.enableCheckBoxes(1);
			tree.enableThreeStateCheckboxes(true);
			tree.loadXML("/admin/checkboxtree/img_style.xml");
        </script>
        
        <script type="text/javascript">
        
         var secs = 1;
		    for(i=1;i<=secs;i++) { 
		        window.setTimeout("update(" + i + ")", i * 100);
		    }
			function update(num) { 
				if(num == secs) { 
<c:forEach var="i_s" items="${img_style}">
tree.setCheck('${i_s}',true);
</c:forEach>
<c:if test="${updatestate=='updateok'}">alert("提示：图片切换样式修改成功！");</c:if>
				}
			}
        </script>
        
</html>


