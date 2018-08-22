package com.xct.cms.custom.made.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.dao.TerminalDAO;
import com.xct.cms.domin.Terminal;
public class WeiFangCourtAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String param=request.getParameter("oparam");
		PrintWriter out=null;
		try {
			out = response.getWriter();
			if(param.equals("ClientInfo")){
	            String jsonvalue="[]";
	            
	            List<Terminal>list=new TerminalDAO().getALLTerminalDAO("  where cnt_status=1");
	            
	            jsonvalue=createClientInfoJson(list);
	            
//			    System.out.println("jsonvalue--------->"+jsonvalue);
	            
				out.print(jsonvalue);
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.close();
			}
		}
		return null;
	}
	
	 public String createClientInfoJson(List<Terminal>list){
		 
	   	    JSONArray array=new JSONArray();
	    	JSONObject object=new JSONObject();
	    	
	    	for (Terminal bean : list) {
	    		object.put("cntip",  bean.getCnt_ip());//ip華硊
	    		object.put("cntmac",  bean.getCnt_mac());//MAC華硊
	    		object.put("cntname",  bean.getCnt_name());//笝傷靡備華硊
	    		
	    		array.add(object);
	    	}
	    	return array.toString();
	 }
}
