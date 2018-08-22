<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ProgramDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Program"/>
<jsp:directive.page import="com.xct.cms.utils.CreatMacXmlUtils"/>
<jsp:directive.page import="java.io.File"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.jspsmart.upload.SmartUpload"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	    String programe_file=request.getParameter("programe_file");//选择要发送的节目名称
	    String programefile[] = programe_file.split("!"); 
	    ProgramDAO prodap= new ProgramDAO();
	    DBConnection dbc= new DBConnection();
	    Connection con = dbc.getConection();
	     
	     
	    Map<String,List<Program>> programmap= prodap.getProgram3(con,"","",programefile);
	    CreatMacXmlUtils creatmacxmlutils=new CreatMacXmlUtils();
	    String  mac_home=System.getProperty("MAC_HOME");
	    if(null!=programmap&&programmap.size()!=0){
	        	List<Program>list;
	        	Program projectbean;
	        	
	        	Util.deleteFile(mac_home+"usb.xml");
	        	File macxmlfile= new File(mac_home+"usb.xml");
	        	for(Map.Entry<String,List<Program>>maptmp:programmap.entrySet()){
	        		list=maptmp.getValue();
	        		for(int i=0,n=list.size();i<n;i++){
	        			projectbean=list.get(i);
	        			int type=0; // 循环
	     		        int play_type=projectbean.getPlay_type();
	     				if(play_type==2){
	     					type=1; //插播
	     				}else if(play_type==3){
	     					type=2; //定时
	     				}else if(play_type==4){
	//     					playtype="loop"; //永久循环
	     				    type=3;
	     				}
						creatmacxmlutils.createProjectMacXml(macxmlfile.getPath(),"",0, projectbean.getProgram_Name(), projectbean.getProgram_JMurl(),
						projectbean.getPlay_start_time()+" "+projectbean.getDay_stime1(), projectbean.getPlay_end_time()+" "+projectbean.getDay_etime1(), 
						type, projectbean.getPlay_count()+"");  
	        		}
	        	}
	     }
	     
	     for(int i=0;i<programefile.length;i++){
	     	if(!"".equals(programefile[i])){
	     		Util.createFile(mac_home+"export_program");
	     		Util.copyFolder(mac_home+programefile[i],mac_home+"export_program\\"+programefile[i]);
	     		Util.copyFile(mac_home+"usb.xml",mac_home+"export_program\\"+"usb.xml");
	     	}
	     }
	     Util.zip(mac_home+"xct_usb.zip",mac_home+"export_program");
	     Util.deleteFile(mac_home+"export_program");
	     
%>
<script>
window.location.href="/admin/program/selectclientIP5.jsp?programe_file=<%=programe_file%>&opp=5";
</script>