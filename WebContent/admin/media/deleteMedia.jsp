<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.domin.Media"/>
<jsp:directive.page import="com.xct.cms.dao.MediaDAO"/>
<jsp:directive.page import="com.xct.cms.dao.LogsDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<%
String mediaIds=request.getParameter("mediaIds");
String rqurl=request.getParameter("rqurl");
UtilDAO utildao= new UtilDAO();
LogsDAO logsdao= new LogsDAO();
 Users user = (Users) request.getSession().getAttribute("lg_user");
 DBConnection dbc= new DBConnection();
 Connection con = dbc.getConection();
 if(mediaIds!=null&&mediaIds.indexOf("!")>-1){
	String mediaids[] = mediaIds.split("!");
	String prograname=FirstStartServlet.projectpath;
	String del_str="";
	for (int i = 0; i < mediaids.length; i++) {
		Media media= new MediaDAO().getMediaBy(con,mediaids[i]);
		if(media!=null){
			utildao.deleteinfo(con,"media_id",mediaids[i],"xct_media");
			Util.deleteFile(prograname+media.getFile_path()+media.getFile_name());
			if(media.getSlightly_img_size()!=0){
				Util.deleteFile(prograname+media.getSlightly_img_path()+media.getSlightly_img_name());
			}
			del_str+="【"+media.getMedia_type()+"文件："+media.getMedia_title()+"】；";
		}
	}
	logsdao.addlogs1(con,user.getName(), "用户【"+user.getName()+"】删除了"+del_str, 1);
}
	
dbc.returnResources(con);
%>
<script type="text/javascript">
<!--
alert("删除媒体成功！");
parent.content.location.href="<%=rqurl%>";
//-->
</script>
