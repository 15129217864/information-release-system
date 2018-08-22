<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<jsp:directive.page import="com.jconfig.Trace"/>
<jsp:directive.page import="com.jconfig.FileRegistry"/>
<jsp:directive.page import="com.jconfig.DiskObject"/>
<jsp:directive.page import="java.io.File"/>
<jsp:directive.page import="java.text.NumberFormat"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.dao.MediaDAO"/>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<%! 
public String numformat(float num){
float i=1024*1024*1024; 
float j=1024*1024;

String  str="0 MB";
NumberFormat   numFormat   =   NumberFormat.getNumberInstance();   
numFormat.setMaximumFractionDigits(1); 
if(num>i){
	str=numFormat.format(num/i)+" G"; 
}else{
	str=numFormat.format(num/j)+" MB"; 
}
	return str;
} %>
<%!public static Long getSize(String strName) {   
	Long TotalSize = 0L;   
	File f = new File(strName);   
	if (f.isFile())   
		return f.length();   
	else {   
		if (f.isDirectory()) {   
			File[] contents = f.listFiles();   
			for (int i = 0; i < contents.length; i++) {   
				if (contents[i].isFile())   
					TotalSize += contents[i].length();   
				else {   
					if (contents[i].isDirectory())   
						TotalSize += getSize(contents[i].getPath());   
					}   
				}   
			}   
		}   
        return TotalSize;   
    }
 %>
<%
String projectpath=FirstStartServlet.projectpath;
String mediapath=projectpath+"\\mediafile\\";
//File file=new File(projectpath+"samizdat\\jconfig\\");
//		�趨������Ϣ���
		//Trace.setDestination(Trace.TRACE_SYSOUT);
//		��ʼ���ļ�ϵͳ
		//FileRegistry.initialize(file,0);
//		�������̶��󣬿����Ǵ�����������Ŀ¼���ļ�
		//DiskObject diskObj=FileRegistry.createDiskObject(new File("D:\\"), 0); 
		File win = new File(projectpath.substring(0,projectpath.indexOf("\\")));
		long totalSpace=0;
		long freeSpace=0;
		 String   total_str="",free_str="",use_str="";
		//if(diskObj!=null){
//		��ô������� 
		 totalSpace=win.getTotalSpace();
		
           total_str   =   numformat(totalSpace);   
//		��ô��̿��ÿռ�
		 freeSpace=win.getFreeSpace();
		   free_str   =   numformat(freeSpace);
		//}
		long useSpace=totalSpace-freeSpace;
		   use_str   =   numformat(useSpace);
		
		/////////////////////����ý����ܴ�С

		long mediaSpace=getSize(mediapath);;
		String   media_str   =   numformat(mediaSpace);
		
		String nowtime=UtilDAO.getNowtime("yyyy-MM-dd");
		long day_mediaSpace=new MediaDAO().getMediaSizeByStr(" where create_date like '"+nowtime+"%'");
		String day_media_str= numformat(day_mediaSpace);
		
		
		//System.out.println("Free space = " + win.getFreeSpace());
  //System.out.println("Usable space = " + win.getUsableSpace());
  //System.out.println("Total space = " + win.getTotalSpace());
		
%>
<html>
  <head>
    
    <title>My JSP 'space_manager.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script language="javascript" src="/js/vcommon.js"></script>
		<script type="text/javascript">
function closedivframe(){
	parent.closedivframe(2);
}
		</script>
  </head>
  
  <body>
  <table width="90%" align="center">
  
  	<tr><td height="25">��ǰ�����ܴ�С��<span style="color: blue;font-weight: bold"><%=total_str %></span></td></tr>
  	<tr><td height="25">��ǰ�������ã�<span style="color: red;font-weight: bold"><%=use_str %></span></td></tr>
  	<tr><td height="25">��ǰ���̿��ô�С��<span style="color: green;font-weight: bold"><%=free_str %></span></td></tr>
  	<tr><td height="25">��ǰý���·����<span style="color: red;"><%=mediapath %></span></td></tr>
  	<tr><td height="25">��ǰý����С��<span style="color: red;font-weight: bold"><%=media_str %></span></td></tr>
  	<tr><td height="25">�����ܹ��ϴ�ý���С��<span style="color: red;font-weight: bold"><%=day_media_str %></span></td></tr>
  	<tr><td height="25" align="center"><span style="color: green">������ʾ��Ϊ�˺����Ӧ�ô��̿ռ䣬�붨������ý����ļ���</span></td></tr>
  	<tr>
			<td colspan="2" align="center"  height="35">
			&nbsp;&nbsp;&nbsp;<input type="button" value=" �� �� " class="button1"  onClick="closedivframe()" />
			</td>
		</tr>
  </table>
  
  
    
   
  </body>
</html>
