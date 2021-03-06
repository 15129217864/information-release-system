/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.xct.cms.custom.made.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.dao.PoliceSubstationDao;
import com.xct.cms.domin.PoliceSubstationBean;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.DESPlusUtil;

/** 
 * MyEclipse Struts
 * Creation date: 12-02-2012
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class SendpolicesubstationAction extends Action {
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		String clientip=null;
		String op=request.getParameter("op");
		String oparams=request.getParameter("oparams");
		List<PoliceSubstationBean> policeSubstationBeanList=new ArrayList<PoliceSubstationBean>();
		oparams=(null==oparams?"":oparams);
		PoliceSubstationDao policesubstationdao=new PoliceSubstationDao();
		if(!policesubstationdao.isExistTable())
			policesubstationdao.createTable();
		
		request.setAttribute("clientlist", policesubstationdao.getClientsMap());
		
		if(op.equals("send")){
			
			String[] clientiptmp=request.getParameter("clientip").split("_");
			clientip=clientiptmp[0];
			String mac=clientiptmp[1];
			
			String statuString="����ʧ�ܣ�";
			if(!"".equals(oparams)){
		       String[] paramaray=null;
		       String string="";
				try {
					string = URLDecoder.decode(oparams, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				paramaray=string.split("@");
		        for(String s:paramaray){
	    		  String [] sarray=s.split("!");
	    		  policeSubstationBeanList.add(new PoliceSubstationBean(sarray[1],sarray[0], sarray[3], sarray[2], sarray[4], sarray[5], clientip));
		        }
			}
			if(!policeSubstationBeanList.isEmpty()){
				if(policesubstationdao.insertOrupdate(policeSubstationBeanList)){
					if(policesubstationdao.CreateXml(policesubstationdao.getPoliceSubstationsOrderBySeqno(clientip),mac)){
					   statuString="���ͳɹ�";
				       String  s= DESPlusUtil.Get().encrypt("lv0091");   ///�������
					   FirstStartServlet.client.sendHTTP(mac,clientip, s);
					}
				}
			}
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(statuString);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(op.equals("list")){
		
			if(null!=clientip){
			   request.setAttribute("policesubstationlist",policesubstationdao.getPoliceSubstations(clientip));
			   request.setAttribute("clientiptemp",clientip);
			}
			return mapping.findForward("list");
		}
		return null;
	}
}