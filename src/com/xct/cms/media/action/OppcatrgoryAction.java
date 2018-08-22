package com.xct.cms.media.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.dao.LogsDAO;
import com.xct.cms.dao.MediaDAO;
import com.xct.cms.dao.TerminalDAO;
import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Media;
import com.xct.cms.domin.Terminal;
import com.xct.cms.domin.Users;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.Util;
import com.xct.cms.utils.UtilDAO;

public class OppcatrgoryAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			
		String catrgoryname=request.getParameter("catrgoryname");
		String zuid=request.getParameter("zu_id");
		String zu_path=request.getParameter("zu_path");
		String cmd=request.getParameter("cmd");
		String is_share=request.getParameter("is_share");
		
		UtilDAO utildao = new UtilDAO();
		 Users user = (Users) request.getSession().getAttribute("lg_user");
		Map map=utildao.getMap();
		if("add".equals(cmd)){
			
			map.put("zu_pth", zu_path);
			map.put("zu_name", catrgoryname);
			map.put("zu_type", "1");
			map.put("zu_username", user.getLg_name()+"||");
			map.put("is_share", is_share);
			utildao.saveinfo(map, "xct_zu");
			request.setAttribute("oppstatus", "addok");
		}else if("update".equals(cmd)){
			map.put("zu_id", zuid);
			map.put("zu_name", catrgoryname);
			map.put("is_share", is_share);
			utildao.updateinfo(map, "xct_zu");
			request.setAttribute("oppstatus", "updateok");
		}else if("DELETE".equals(cmd)){
			DBConnection dbc= new DBConnection();
			Connection conn= dbc.getConection();
			 LogsDAO logsdao= new LogsDAO();
			TerminalDAO terdao= new TerminalDAO();
			List<Terminal> media_zulist=terdao.getAllZu(conn," where zu_type=1 order by zu_id");
			List<Terminal> sub_list=terdao.getzu_subListByzu_pth(media_zulist,Integer.parseInt(zuid));
			String del_media_zu="";
			String delmedia="";
			MediaDAO mediadao= new MediaDAO();
			List<Media> medialist= new ArrayList<Media>();
			String prograname=FirstStartServlet.projectpath;
			for(int i=0;i<sub_list.size();i++){
				Terminal ter= sub_list.get(i);
				
				medialist= mediadao.getALLMediaDAO(conn," and xct_zu.zu_id ="+ter.getZu_id());////根据组获取组下面的所有媒体
				for(int j=0;j<medialist.size();j++){  ///删除媒体文件
					Media media=medialist.get(j);
					if(media!=null){
						delmedia+=media.getMedia_title()+"；";;
						Util.deleteFile(prograname+media.getFile_path()+media.getFile_name());
					}
				}
				
				utildao.deleteinfo(conn,"zu_id",ter.getZu_id()+"","xct_media"); ///删除组下面的媒体
				utildao.deleteinfo(conn,"zu_id", ter.getZu_id()+"", "xct_zu");   ///删除子组
				
				del_media_zu+=ter.getZu_name()+"；";
			}
			logsdao.addlogs1(conn,user.getLg_name(), "<span >用户"+user.getLg_name()+"删除了媒体组：【"+del_media_zu+"】以及组下面的媒体【"+delmedia+"】", 1);
			dbc.returnResources(conn);

			request.setAttribute("oppstatus", "deleteok");
		}
		
		return mapping.findForward("oppok");
	}

}
