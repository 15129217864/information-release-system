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
			String uploadPath = FirstStartServlet.projectpath + "serverftp\\program_file\\";//ͼƬ�洢·��
			DiskFileUploadEx fu = new DiskFileUploadEx();//��ʼ��
			try {
				fileItemList = fu.parseRequestEx(request);//���ձ�����
				File savedFile =null;
				Iterator fileItemListIte = fileItemList.iterator();
				while (fileItemListIte.hasNext()) {
					FileItem file = (FileItem) fileItemListIte.next();
					if (!file.isFormField() && file.getSize() > 0) {
						Thread.sleep(10); ///��ֹ�ϴ��ٶ�̫�죬����ʱ��һ��
						String filename = file.getName();
						
						if(filename.indexOf(".")!=-1){
							realfilename=filename.substring(filename.lastIndexOf("\\")+1);
						}
						//System.out.println(filename);
						if (!filename.endsWith(".xls")) {
							out.println("<script>alert('������Ϣ��Excel�ļ��������� xls Ϊ��׺��');history.go(-1);</script>");
							bool=false;
							return;
						}else{
							/////System.out.println(realfilename);
							File filepath = new File(uploadPath);
							if (!filepath.exists()) {
								filepath.mkdir();
							}
							 savedFile = new File(uploadPath + realfilename);
							file.write(savedFile);//����ͼƬ��ָ����Ŀ¼��
						}
					}
				}
				OpExcelUtils opexcelutils=new OpExcelUtils();
				bool=new UsersDAO().addUserInfo(opexcelutils.getImportUserinfo(savedFile.getPath()));
				
			} catch (Exception e) {
				//out.println("<script>alert('������Ϣ���ϴ������ļ�excel����ļ�ʧ�ܣ�');history.go(-1);</script>");
				e.printStackTrace();
				bool=false;
			} finally {
				Thread.sleep(1000);
				fu.dispose();//�����ϴ��ļ��������     13390760601@189.cn
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
				alert("���������û��ɹ���");
				//-->
			</script>
			<%}else{ %>
				<script type="text/javascript">
				<!--
				parent.closedivframe(1);
				alert("����ʧ�ܣ�");
				//-->
				</script>
		  <%}%>
