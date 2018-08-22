package com.xct.cms.utils;

import com.google.gson.Gson;
import com.gson.domain.CmsServiceInfo;

public class GsonUtils {

	public static void main(String[] args) {
		    CmsServiceInfo cmsserviceinfo = new CmsServiceInfo();
		    cmsserviceinfo.setCode("1");
		    cmsserviceinfo.setMessage("³É¹¦");
//		    cmsserviceinfo.getData().setInterversion("123456");
	        Gson gson = new Gson();
	        String json = gson.toJson(cmsserviceinfo);
	        System.out.println("json = " + json);
	}
}
