<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="java.util.UUID"/>
<jsp:directive.page import="com.xct.cms.utils.*"/>
 <% 
 Users user = (Users) request.getSession().getAttribute("lg_user");
if (user == null) {%>
<script type="text/javascript">
<!--
alert("SESSION过期，请重新登录！");
parent.parent.parent.parent.location.href="/index.jsp"
//-->
</script>
<%return;}

String moduleid=request.getParameter("moduleid")==null?"0":request.getParameter("moduleid");
String rss_content=UtilDAO.getGBK(request.getParameter("rss_content"));
String newscroll_name = UtilDAO.getNowtime("yyyyMMddHHmmssSSS")+".txt";
String filepath=FirstStartServlet.projectpath+"mediafile/text/"+newscroll_name;
String url="http://app.abchina.com/rateinfo/RateSearch.aspx?id=1";//张家港国泰外汇网址
boolean foreignexchangeflag=false;
if(rss_content.trim().equals(url))
 foreignexchangeflag=ForeignExchange.foreignExchangeToTxt(url,"/foreignexchanger.xml",filepath);

boolean bool=UtilDAO.getRSS(rss_content,filepath);
 
if(bool||foreignexchangeflag){
	String scrollcontent=UtilDAO.rss_content;
    if(foreignexchangeflag)
   	   scrollcontent=ForeignExchange.FOREIGSTRING;
    
	UtilDAO utildao = new UtilDAO();
	int zuid=new TerminalDAO().newzu("1","sys_text","1",user.getLg_name());
	Map<String ,String> mediamap=UtilDAO.getMap();
							String nowtime1=UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
							String meidaid="media."+ UUID.randomUUID().toString();
							mediamap.put("media_id", meidaid);
							mediamap.put("media_title", "scroll_"+newscroll_name);
							mediamap.put("media_type", "TEXT");
							mediamap.put("zu_id", zuid+"");
							mediamap.put("group_num", "0");
							mediamap.put("m_play_time", "8000");
							mediamap.put("file_name", newscroll_name);
							mediamap.put("file_size", "100");
							mediamap.put("file_path", "/mediafile/text/");
							mediamap.put("slightly_img_name", "text_slightly.gif");
							mediamap.put("slightly_img_path", "/mediafile/");
							mediamap.put("slightly_img_size", "0");
							mediamap.put("create_date", nowtime1);
							mediamap.put("last_date", nowtime1);
							mediamap.put("userid", user.getLg_name());
							utildao.saveinfo(mediamap, "xct_media");
							
	ModuleDAO moduledao = new ModuleDAO();						
	String sequence=moduledao.getSequenceBymid(moduleid)+"";
						Map map = utildao.getMap();
						map.put("module_id", moduleid);
						map.put("media_id", meidaid);
						map.put("type", "0");
						map.put("sequence",sequence);
						utildao.saveinfo(map, "xct_module_media");		
						
	Map map1= utildao.getMap();
	map1.put("id",moduleid);
	map1.put("m_text",scrollcontent);
	utildao.updateinfo(map1,"xct_module_temp");	
	%>
	<script type="text/javascript">
	<!--
	parent.closeDiv();
	 parent.location.href="/admin/program/addmediaList.jsp?opp=0&moduleid=<%=moduleid%>";  
	
	//-->
	</script>
	<%	
}else{
	%>
	<link rel="stylesheet" href="/css/style.css" type="text/css" />
	 <table width="280" height="100%" style="font-size: 12px;" border="0" align="center">
	 <tr  height="70%" valign="middle" align="center">
	 <td>
	<img src="/images/del.gif" height="12px" border="0"/>&nbsp;&nbsp;<span style="color: red;">解析RSS地址出错，<br/>请检查网络或RSS地址是否正确！</span>
	</td></tr>
	<tr >
	  		<td  colspan="2" align="center">
	  		<input type="button" value="返 回"  id="sub_id" class="button" onclick="history.go(-1); "/>&nbsp;&nbsp;
	  		<input type="button" value="取 消" class="button" onclick="parent.closeDiv();"/></td>
	  		
	  	</tr>
	</table>
	<%
}					
%>

