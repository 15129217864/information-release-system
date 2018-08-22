<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@page import="java.net.URLDecoder"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="java.util.UUID"/>
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
//String scroll_content=UtilDAO.getGBK(request.getParameter("scroll_content"));
String scroll_content=request.getParameter("scroll_content");
scroll_content=URLDecoder.decode(scroll_content,"UTF-8");
//System.out.println("scroll_content=========>"+scroll_content);
String newscroll_name = UtilDAO.getNowtime("yyyyMMddHHmmssSSS")+".txt";
String filepath=FirstStartServlet.projectpath+"mediafile/text/"+newscroll_name;
Util.writefile(filepath,scroll_content);
UtilDAO utildao = new UtilDAO();
int zuid=new TerminalDAO().newzu("1","sys_text","1",user.getLg_name()+"||");

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
						map1.put("m_text",scroll_content);
						utildao.updateinfo(map1,"xct_module_temp");		

%>
<script type="text/javascript">
<!--
parent.closeDiv();
 parent.location.href="/admin/program/addmediaList.jsp?opp=0&moduleid=<%=moduleid%>";  

//-->
</script>
