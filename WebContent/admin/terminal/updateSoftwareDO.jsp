<%@ page contentType="text/html; charset=GB2312" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="ahxu.commons.upload.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="java.io.File"%>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
		<%
		response.setCharacterEncoding("gbk");
		List fileItemList = null;
			String realfilename = "";
			String terminalips="!";
			String uploadPath = FirstStartServlet.projectpath + "serverftp\\program_file\\";//图片存储路径
			DiskFileUploadEx fu = new DiskFileUploadEx();//初始化
			try {
				fileItemList = fu.parseRequestEx(request);//接收表单数据
				terminalips=request.getParameter("terminalips");
				
				Iterator fileItemListIte = fileItemList.iterator();
				while (fileItemListIte.hasNext()) {
					FileItem file = (FileItem) fileItemListIte.next();
					if (!file.isFormField() && file.getSize() > 0) {
						Thread.sleep(50); ///防止上传速度太快，导致时间一样
						String filename = file.getName();
						
						if(filename.indexOf(".")!=-1){
							realfilename=filename.substring(filename.lastIndexOf("\\")+1);
						}
						if (!"xct.jar".equals(realfilename) &&!realfilename.endsWith(".apk"))  {
							out.println("<script>alert('错误信息：升级软件必须为jar 或者 apk文件！');history.go(-1);</script>");
							return;
						}else{
							/////System.out.println(realfilename);
							File filepath = new File(uploadPath);
							if (!filepath.exists()) {
								filepath.mkdir();
							}
							File savedFile = new File(uploadPath + realfilename);
							file.write(savedFile);//保存图片到指定的目录下
						}
					}
				}
			} catch (Exception e) {
				out.println("<script>alert('错误信息：上传升级文件失败！');history.go(-1);</script>");
				return;
			} finally {
				Thread.sleep(1000);
				fu.dispose();//接收上传文件后的清理工
			}
			request.setAttribute("checkips", terminalips);
			request.getRequestDispatcher("/rq/requestClientOperating?cmd=UPDATESOFTWARE&opp=lv0018").forward(request,response);
		%>
