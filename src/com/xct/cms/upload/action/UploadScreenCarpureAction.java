package com.xct.cms.upload.action;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.gson.domain.CmsServiceInfo;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.Base64ToImageUtil;
import com.xct.cms.utils.DESPlusUtil;

public class UploadScreenCarpureAction  extends Action{ //����ͼƬ�ϴ�

	Logger logger = Logger.getLogger(UploadScreenCarpureAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
	
		String uploadPath = new StringBuffer().append(FirstStartServlet.projectpath ).append("serverftp\\program_file\\screen\\").toString();//ͼƬ�洢·��
		String screenbase64str=request.getParameter("screenbase64str");
		if(null!=screenbase64str) {
			PrintWriter out=null;
			Gson gson = new Gson();
			CmsServiceInfo cmsserviceinfo = new CmsServiceInfo();
			screenbase64str=DESPlusUtil.Get().decrypt(screenbase64str);
			try {
				out=response.getWriter();
				if(!"".equals(screenbase64str)) {
//					logger.info("screenbase64str==>"+screenbase64str);
					String mac=request.getParameter("mac");
//					logger.info("mac==>"+mac);
					
					String imagepath=new StringBuffer().append(uploadPath).append(mac).append(".jpg").toString();
					Base64ToImageUtil.generateImageFromBase64Str(screenbase64str.replaceAll("\\s+",""),imagepath);
					cmsserviceinfo.setCode("0");
					cmsserviceinfo.setMessage("�ɹ�");
					out.println(gson.toJson(cmsserviceinfo));
				 }else {
					cmsserviceinfo.setCode("2");
					cmsserviceinfo.setMessage("�����쳣��ʧ�ܣ�");
					String json=gson.toJson(cmsserviceinfo);
					logger.info(new StringBuffer().append("....��ͼ�����쳣��").append(json).toString());
					out.println(gson.toJson(cmsserviceinfo));
				 }
			} catch (Exception e) {
				cmsserviceinfo.setCode("1");
				cmsserviceinfo.setMessage("��������쳣��ʧ�ܣ�");
				String json=gson.toJson(cmsserviceinfo);
				logger.info(new StringBuffer().append("....��ͼ��������쳣��").append(json).toString());
				out.println(json);
				e.printStackTrace();
			}finally {
				if(null!=out)
				  out.close();
			}
		}else {
			String realfilename = "";
			//���������ļ�����
			DiskFileItemFactory fac = new DiskFileItemFactory();    
			//����servlet�ļ��ϴ����
			ServletFileUpload upload = new ServletFileUpload(fac);   
			
			upload.setSizeMax(-1);//�������ϴ��Ĵ�С
			upload.setFileSizeMax(-1);//�������ϴ��Ĵ�С
			upload.setHeaderEncoding("utf-8");
	//		fac.setSizeThreshold(1 * 1024 * 1024);
			List fileItemList = null;
			PrintWriter out=null;
			try {
				out=response.getWriter();
				fileItemList = upload.parseRequest(request);//���ձ����ݣ������о�������: Timeout attempting to read data from the socket
				
				Iterator fileItemListIte = fileItemList.iterator();
				while (fileItemListIte.hasNext()) {
					FileItem file = (FileItem) fileItemListIte.next();
					if (!file.isFormField() && file.getSize() > 0) {
						String filename = file.getName();
						
	//					if(filename.indexOf(".")!=-1){
	//						realfilename=filename.substring(filename.lastIndexOf("\\")+1);
	//					}
						File filepath = new File(uploadPath);
						if (!filepath.exists()) {
							filepath.mkdir();
						}
						File savedFile = new File(new StringBuffer().append(uploadPath ).append( filename).toString());
						file.write(savedFile);//����ͼƬ��ָ����Ŀ¼��
						out.println("ok");
	//					System.out.println("����ͼƬ�ϴ��ɹ���======>"+filename);
					}
				}
			} catch (Exception e) {
				out.println("error");
				e.printStackTrace();
			}finally {
				out.close();
			}
		}
		return null;
	}
}
