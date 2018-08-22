package com.xct.cms.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.gson.domain.CmsServiceInfo;

public class CmsServiceInfoAction  extends Action {
	
	Logger logger = Logger.getLogger(CmsServiceInfoAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String mac=request.getParameter("mac");
//		System.out.println("mac========>"+mac);
		PrintWriter out=null;
		Gson gson = new Gson();
		CmsServiceInfo cmsserviceinfo = new CmsServiceInfo();
		try {
			out=response.getWriter();
		    if(null!=mac) {
			    cmsserviceinfo.setCode("0");
			    cmsserviceinfo.setMessage("成功");
			    cmsserviceinfo.getData().setInterversion(System.getProperty("CMS_VERSION"));
		        String json = gson.toJson(cmsserviceinfo);
				out.println(json);
			}else {
				cmsserviceinfo.setCode("2");
			    cmsserviceinfo.setMessage("MAC地址 为  null");
		        String json = gson.toJson(cmsserviceinfo);
				out.println(json);
			}
		} catch (Exception e) {
		    cmsserviceinfo.setCode("1");
		    cmsserviceinfo.setMessage("服务出现异常：失败！");
	        String json = gson.toJson(cmsserviceinfo);
			out.println(json);
			e.printStackTrace();
		}finally {
			out.close();
		}
		return null;
	}
}
