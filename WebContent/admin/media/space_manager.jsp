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
//		设定错误信息输出
		//Trace.setDestination(Trace.TRACE_SYSOUT);
//		初始化文件系统
		//FileRegistry.initialize(file,0);
//		创建磁盘对象，可以是磁盘驱动器、目录或文件
		//DiskObject diskObj=FileRegistry.createDiskObject(new File("D:\\"), 0); 
		File win = new File(projectpath.substring(0,projectpath.indexOf("\\")));
		long totalSpace=0;
		long freeSpace=0;
		 String   total_str="",free_str="",use_str="";
		//if(diskObj!=null){
//		获得磁盘容量 
		 totalSpace=win.getTotalSpace();
		
           total_str   =   numformat(totalSpace);   
//		获得磁盘可用空间
		 freeSpace=win.getFreeSpace();
		   free_str   =   numformat(freeSpace);
		//}
		long useSpace=totalSpace-freeSpace;
		   use_str   =   numformat(useSpace);
		
		/////////////////////计算媒体库总大小

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
  
  	<tr><td height="25">当前磁盘总大小：<span style="color: blue;font-weight: bold"><%=total_str %></span></td></tr>
  	<tr><td height="25">当前磁盘已用：<span style="color: red;font-weight: bold"><%=use_str %></span></td></tr>
  	<tr><td height="25">当前磁盘可用大小：<span style="color: green;font-weight: bold"><%=free_str %></span></td></tr>
  	<tr><td height="25">当前媒体库路径：<span style="color: red;"><%=mediapath %></span></td></tr>
  	<tr><td height="25">当前媒体库大小：<span style="color: red;font-weight: bold"><%=media_str %></span></td></tr>
  	<tr><td height="25">今日总共上传媒体大小：<span style="color: red;font-weight: bold"><%=day_media_str %></span></td></tr>
  	<tr><td height="25" align="center"><span style="color: green">友情提示：为了合理的应用磁盘空间，请定期清理媒体库文件！</span></td></tr>
  	<tr>
			<td colspan="2" align="center"  height="35">
			&nbsp;&nbsp;&nbsp;<input type="button" value=" 关 闭 " class="button1"  onClick="closedivframe()" />
			</td>
		</tr>
  </table>
  
  
    
   
  </body>
</html>
