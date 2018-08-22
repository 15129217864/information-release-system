<%@ page contentType="text/html; charset=GB2312" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="ahxu.commons.upload.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="java.io.File"%>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.excel.util.OpExcelUtils"/>
<jsp:directive.page import="com.xct.cms.dao.UsersDAO"/>
		<%
		boolean bool=false;
		response.setCharacterEncoding("gbk");
		List fileItemList = null;
			String realfilename = "";
			String uploadPath = FirstStartServlet.projectpath + "serverftp\\program_file\\";//图片存储路径
			DiskFileUploadEx fu = new DiskFileUploadEx();//初始化
			try {
				fileItemList = fu.parseRequestEx(request);//接收表单数据
				File savedFile =null;
				Iterator fileItemListIte = fileItemList.iterator();
				while (fileItemListIte.hasNext()) {
					FileItem file = (FileItem) fileItemListIte.next();
					if (!file.isFormField() && file.getSize() > 0) {
						Thread.sleep(10); ///防止上传速度太快，导致时间一样
						String filename = file.getName();
						
						if(filename.indexOf(".")!=-1){
							realfilename=filename.substring(filename.lastIndexOf("\\")+1);
						}
						//System.out.println(filename);
						if (!filename.endsWith(".xls")) {
							out.println("<script>alert('错误信息：Excel文件名必须以 xls 为后缀！');history.go(-1);</script>");
							bool=false;
							return;
						}else{
							/////System.out.println(realfilename);
							File filepath = new File(uploadPath);
							if (!filepath.exists()) {
								filepath.mkdir();
							}
							 savedFile = new File(uploadPath + realfilename);
							file.write(savedFile);//保存图片到指定的目录下
						}
					}
				}
				OpExcelUtils opexcelutils=new OpExcelUtils();
				bool=new UsersDAO().addUserInfo(opexcelutils.getImportUserinfo(savedFile.getPath()));
				
			} catch (Exception e) {
				//out.println("<script>alert('错误信息：上传升级文件excel表格文件失败！');history.go(-1);</script>");
				e.printStackTrace();
				bool=false;
			} finally {
				Thread.sleep(1000);
				fu.dispose();//接收上传文件后的清理工     13390760601@189.cn
			}
			
		if(bool){	
		  %>
			<script type="text/javascript">
				<!--
				parent.closedivframe(1);
				if(parent.homeframe.content.content.location){
				   // alert(parent.homeframe.content.content.location.href);
					parent.homeframe.content.content.location.reload();
				}
				parent.homeframe.content.content.location.reload();
				alert("批量导入用户成功！");
				//-->
			</script>
			<%}else{ %>
				<script type="text/javascript">
				<!--
				parent.closedivframe(1);
				alert("导入失败！");
				//-->
				</script>
		  <%}%>
