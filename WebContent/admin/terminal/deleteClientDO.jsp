<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.dao.LogsDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.dao.TerminalDAO"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.domin.Terminal"/>
<%
String resips = request.getParameter("checkips") == null ? "!": request.getParameter("checkips");
//System.out.println("resips---------->"+resips);
String resip[]=resips.split("!");
UtilDAO utildao=new UtilDAO();
DBConnection dbconn= new DBConnection();
Connection conn=dbconn.getConection();

Map<String,Terminal> terminals=new HashMap<String,Terminal>();

for (Map.Entry<String, Terminal> entry :FirstStartServlet.terminalMap.entrySet()) {
	terminals.put(entry.getKey(), entry.getValue());
}

Users user=(Users)request.getSession().getAttribute("lg_user");

String delstr="";
for (int i = 1; i < resip.length; i++) {
	for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
		Terminal tt=entry.getValue();
		if(tt!=null){
		    String []clientarray=resip[i].split("_");
			if(clientarray[1].equals(tt.getCnt_mac())
			          &&(null==tt.getCnt_status()||!tt.getCnt_status().equals("0"))){
			          
				String terminalmac=entry.getKey();
				delstr+="��"+tt.getCnt_name()+"����";
				FirstStartServlet.terminalMap.remove(terminalmac);
				utildao.deleteinfo(conn,"cnt_mac", terminalmac, "xct_ipaddress",user.getName());//ɾ�����ݿ����д��ն˵Ľ�Ŀ
			}
		}
	}
}

LogsDAO logsdao= new LogsDAO();
logsdao.addlogs1(conn,user.getName(), "<span >�û���"+user.getName()+"��ɾ�����նˣ�"+delstr, 1);
dbconn.returnResources(conn);
%>
<script type="text/javascript">
	
	parent.closedivframe(1);
	alert("ɾ���ն˳ɹ���");
	if(parent.parent.homeframe.content.content)
         parent.parent.homeframe.content.content.location.reload();
   
</script>