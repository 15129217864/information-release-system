package com.xct.cms.xy.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.utils.DESPlusUtil;
import com.xct.cms.xy.dao.ConnectionFactory;

public class NoticeOnLineActionAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	
			String cmd=request.getParameter("cmd");//
			String cmdResult=request.getParameter("cmdResult");
		
			if(cmd.equals("xu0028")){
				String [] project=(cmdResult.split("@")[1]).split("###");
				for(int i=0,n=project.length;i<n;i++){
					String [] projecttemp=project[i].split("#");
					for(int j=0,m=projecttemp.length;j<m;j++){
						///////System.out.println(projecttemp[j]);
					}
				}
			 }
		return null;
	}
}