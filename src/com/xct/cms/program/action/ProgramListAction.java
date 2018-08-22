/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.xct.cms.program.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.dao.ProgramDAO;
import com.xct.cms.dao.TerminalDAO;
import com.xct.cms.domin.Terminal;
import com.xct.cms.domin.Users;
import com.xct.cms.utils.PageDAO;
import com.xct.cms.utils.Pager;

/** 
 * MyEclipse Struts
 * Creation date: 08-28-2010
 * 
 * XDoclet definition:
 * @struts.action scope="request" validate="true"
 */
public class ProgramListAction extends Action {
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String left_menu=request.getParameter("left_menu")==null?"":request.getParameter("left_menu");
		String zu_id=request.getParameter("zu_id")==null?"":request.getParameter("zu_id");
		 Users user = (Users) request.getSession().getAttribute("lg_user");
		ProgramDAO programdao = new ProgramDAO();
		List list=new ArrayList();
		String str="";
		if(!"1".equals(user.getLg_role())){
			//TerminalDAO terminaldao = new TerminalDAO();
			Map<Integer,Terminal> allzu=TerminalDAO.getZuListByUsername1("2",user.getLg_name());
			String allzuid="";
			for (Map.Entry<Integer, Terminal> entry : allzu.entrySet()) {
				Terminal terminal=entry.getValue();
				if(terminal.getZu_type()==1){
					allzuid+=terminal.getZu_id()+"|";
				}
			}
			//String allzuid=terminaldao.getAllZuID("where zu_type=2 and (PATINDEX ('%"+user.getLg_name()+"||%',zu_username)>0) ");
			//Mysql
			 str=" and ( LOCATE(CAST(program_treeid AS varchar(50)),'"+allzuid+"')>0) ";
			
			//SQLServer
			//str=" and (CharIndex (CAST(program_treeid AS varchar(50)),'"+allzuid+"')>0) ";
		}
		if("PROGRAM_ZU".equals(left_menu)){
			list= programdao.getALLProgram(" and program_treeid='"+(zu_id.equals("no")?"":zu_id) +"'  order by program_JMid desc");
		}else if ("TITLE_ASC".equals(left_menu)){
			list= programdao.getALLProgram("  "+str+"  order by program_Name asc");
		}else if("TITLE_DESC".equals(left_menu)){
			list= programdao.getALLProgram("  "+str+" order by program_Name desc");
			
		}else{
			list= programdao.getALLProgram(" "+str+"  order by program_JMid desc");
		}
		if(list!=null&&list.size()>0){
			int pagenum =Integer.parseInt(request.getParameter("pagenum")==null?"1":request.getParameter("pagenum"));
			Pager pager= new Pager(list.size(),pagenum,20); 
			List list3= new PageDAO().getPageList(list, pager.getCurrentPage(), pager.getPageSize());
			request.setAttribute("pager", pager);
			request.setAttribute("programList", list3);
			request.setAttribute("program_sum", list.size());
		}
		request.setAttribute("left_menu", left_menu);
		request.setAttribute("zu_id", zu_id);
		return mapping.findForward("programs");
	}
}