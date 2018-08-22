<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="java.io.*, 
				jxl.Workbook,
				jxl.WorkbookSettings,
				jxl.write.Label,
				jxl.write.WritableSheet,
				jxl.write.WritableWorkbook,
				jxl.write.WriteException,
 java.util.Locale"%>
<jsp:directive.page import="com.xct.cms.dao.LogsDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Logs"/>
<% 
String logtype=request.getParameter("logtype")==null?"2":request.getParameter("logtype");
String logdates=request.getParameter("logdates");
String logdatearray[] = logdates.split("!");
LogsDAO logsdao= new  LogsDAO();
String str="where  logdel=0 and logtype="+logtype+" and ";
for (int i = 1; i < logdatearray.length; i++) {
str+="logdate='"+logdatearray[i]+"' or ";
}
List loglist=logsdao.getLogsByStr(str.substring(0,str.length()-3));

            File tempfile=null;
      try { 
            WorkbookSettings ws;
                WritableWorkbook w;
                WritableSheet wsheet;
                tempfile=File.createTempFile(String.valueOf(System.currentTimeMillis()),".xls");  
                
                ws = new WorkbookSettings();
                ws.setLocale(new Locale("zh-cn", "CH"));
                w = Workbook.createWorkbook(tempfile, ws);
                wsheet = w.createSheet("日志信息", 0);				              
                wsheet.addCell(new Label(0, 0, "操作用户"));   
	            wsheet.addCell(new Label(1, 0, "操作时间"));   
	            wsheet.addCell(new Label(2, 0, "操作内容"));   
		for(int i=0;i<loglist.size();i++){
		 		Logs log=(Logs)loglist.get(i);
				wsheet.addCell(new Label(0, i+1, log.getLoguser()));   
	        	wsheet.addCell(new Label(1, i+1, log.getLogdate()+" "+log.getLogtime()));  
	        	String loginfo=log.getLoglog();
	        	String loginfo1=loginfo;
	        	if(loginfo.indexOf("<")!=-1 && loginfo.indexOf(">")!=-1){
	        		 String loginfo2=loginfo.substring(0,loginfo.lastIndexOf("<"));
	        		 loginfo1=loginfo2.substring(loginfo2.lastIndexOf(">")+1);
	        	}
	        	wsheet.addCell(new Label(2, i+1, loginfo1));  
	  	}                  
		w.write();
  	  w.close();  
            } catch (WriteException ex) { 
            } catch (Exception e) {  
            }
            response.reset();
            response.setHeader("Content-disposition",
                "attachment; filename=" + tempfile.getName());
            response.setContentType("application/octet-stream");     
         
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            try {
                bis = new BufferedInputStream(new FileInputStream(tempfile));
                bos = new BufferedOutputStream(response.getOutputStream());

                byte[] buff = new byte[2048];
                int bytesRead;

                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
                 bos.flush();
            } catch (final IOException e) {
                /////System.out.println("IOException." + e);
            	e.printStackTrace();
            } finally {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            }
			out.clear(); out = pageContext.pushBody(); 
%>
