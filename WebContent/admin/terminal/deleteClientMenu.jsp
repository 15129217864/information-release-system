<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.utils.HttpRequest"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramHistoryDAO"/>
<jsp:directive.page import="com.xct.cms.domin.ProjectBean"/>
<jsp:directive.page import="com.xct.cms.utils.ReadMacXmlUtils"/>
<%
//String cmd=Util.getGBK(request.getParameter("cmd")).replace("!","#");
String cmd=request.getParameter("cmd").replace("!","#");
//System.out.println(cmd);
String clentIp=request.getParameter("clentIp");
String mactmp=request.getParameter("cntmac");
String cntmac=FirstStartServlet.projectpath+"serverftp\\program_file\\"+mactmp+".xml";
String cmds[]=cmd.replace("_"," ").split("#");
FirstStartServlet.client.allsend(mactmp,clentIp, "lv0025@"+cmd.replace("_"," "));

ProgramHistoryDAO historydao= new ProgramHistoryDAO();
int isloop=0;
if("loop".equals(cmds[3])){
	isloop=0;
}else if("insert".equals(cmds[3])){
	isloop=1;
}else if("active".equals(cmds[3])){
	isloop=2;
}else if("defaultloop".equals(cmds[3])){
	isloop=3;
} 

String str="where program_Name='"+cmds[0]+"' and program_JMurl='"+cmds[1]+"' and program_SetDate='"+cmds[2]+"' "+
	"and program_ISloop="+isloop+" and program_PlaySecond='"+cmds[4]+"' and  program_ip='"+clentIp+"' and program_delid='"+mactmp+"'";
if(isloop==3){
	str=" where  program_JMurl='"+cmds[1]+"' "+"and program_ISloop="+isloop+" and  program_ip='"+clentIp+"' and program_delid='"+mactmp+"'";
}

//System.out.println(str);
  historydao.deleteJMhistory(str);
//把ftp上的mac命名的xml文件修改
new ReadMacXmlUtils().getProjectFromMacXml(cntmac,new ProjectBean(cmds[0],cmds[1],cmds[2],cmds[3],cmds[4]),"n",false); 
%>
