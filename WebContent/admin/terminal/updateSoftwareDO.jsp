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
			String uploadPath = FirstStartServlet.projectpath + "serverftp\\program_file\\";//ͼƬ�洢·��
			DiskFileUploadEx fu = new DiskFileUploadEx();//��ʼ��
			try {
				fileItemList = fu.parseRequestEx(request);//���ձ�����
				terminalips=request.getParameter("terminalips");
				
				Iterator fileItemListIte = fileItemList.iterator();
				while (fileItemListIte.hasNext()) {
					FileItem file = (FileItem) fileItemListIte.next();
					if (!file.isFormField() && file.getSize() > 0) {
						Thread.sleep(50); ///��ֹ�ϴ��ٶ�̫�죬����ʱ��һ��
						String filename = file.getName();
						
						if(filename.indexOf(".")!=-1){
							realfilename=filename.substring(filename.lastIndexOf("\\")+1);
						}
						if (!"xct.jar".equals(realfilename) &&!realfilename.endsWith(".apk"))  {
							out.println("<script>alert('������Ϣ�������������Ϊjar ���� apk�ļ���');history.go(-1);</script>");
							return;
						}else{
							/////System.out.println(realfilename);
							File filepath = new File(uploadPath);
							if (!filepath.exists()) {
								filepath.mkdir();
							}
							File savedFile = new File(uploadPath + realfilename);
							file.write(savedFile);//����ͼƬ��ָ����Ŀ¼��
						}
					}
				}
			} catch (Exception e) {
				out.println("<script>alert('������Ϣ���ϴ������ļ�ʧ�ܣ�');history.go(-1);</script>");
				return;
			} finally {
				Thread.sleep(1000);
				fu.dispose();//�����ϴ��ļ��������
			}
			request.setAttribute("checkips", terminalips);
			request.getRequestDispatcher("/rq/requestClientOperating?cmd=UPDATESOFTWARE&opp=lv0018").forward(request,response);
		%>
