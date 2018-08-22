package com.xct.cms.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.domin.Users;
import com.xct.cms.utils.DateUtils;
import com.xct.cms.utils.UtilDAO;

public class AheadCreateProjectAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session =request.getSession();
		String templateid=request.getParameter("templateid") == null ? "5": request.getParameter("templateid");
		
		Users user=(Users)session.getAttribute("lg_user");
//		String playname=UtilDAO.getUTF(request.getParameter("playname")); //在2003系统上是乱码
		String playname=request.getParameter("playname");
		
        String projectdirectory=request.getParameter("projectdirectory");//播放的节目名称
        
        String startdate=request.getParameter("startdate")==null?"":request.getParameter("startdate");
        String enddate=request.getParameter("enddate")==null?"":request.getParameter("enddate");
        
        String starttime=request.getParameter("starttime")==null?"":request.getParameter("starttime");
        String endtime=request.getParameter("endtime")==null?"":request.getParameter("endtime");
        
        String twostarttime=request.getParameter("twostarttime")==null?"":request.getParameter("twostarttime");
        String twoendtime=request.getParameter("twoendtime")==null?"":request.getParameter("twoendtime");
        String threestarttime=request.getParameter("threestarttime")==null?"":request.getParameter("threestarttime");
        String threeendtime=request.getParameter("threeendtime")==null?"":request.getParameter("threeendtime");
        
        String playtype=request.getParameter("playtype");//播放类型 0，1，2，3 playtype
        String loop_type=request.getParameter("loop_type");//播放类型 0，1，2，3 playtype
        if("1".equals(playtype)&&"1".equals(loop_type)){
        	playtype="4";
        }
		String timecount=request.getParameter("timecount");  //播放时长
		String timecount2=request.getParameter("timecount2");  //播放时长2
		String timecount3=request.getParameter("timecount3");  //播放时长3
		
		UtilDAO utildao= new  UtilDAO();
	    Map map= utildao.getMap();
	    map.put("program_JMurl", projectdirectory);
	    map.put("play_type", playtype);
	    map.put("templateid", templateid);
		if("1".equals(playtype)){///定时循环
			 starttime=new SimpleDateFormat("HH:mm").format(new Date())+":00";
	         String[] datestring= DateUtils.addDate(startdate+" "+starttime,Integer.parseInt(timecount)).split(" ");
	         endtime=datestring[1];
			map.put("play_start_time", startdate);
			map.put("play_end_time", enddate);
			map.put("play_count", timecount);
			map.put("day_stime1", starttime);
			map.put("day_etime1", endtime);
			utildao.saveinfo(map, "xct_JMPZ_type");
		}
		else if("2".equals(playtype)){ ////插播
             startdate =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	           starttime=new SimpleDateFormat("HH:mm").format(new Date())+":00";
	           String[] datestring= DateUtils.addDate(startdate+" "+starttime,Integer.parseInt(timecount)).split(" ");
	           enddate=datestring[0];
	           endtime=datestring[1];
			
			map.put("play_start_time", startdate);
			map.put("play_end_time", startdate);
			map.put("play_count", timecount);
			map.put("day_stime1", starttime);
			map.put("day_etime1", endtime);
			utildao.saveinfo(map, "xct_JMPZ_type");
         }else if("3".equals(playtype)){///定时
			if(!"".equals(starttime)&&!"".equals(enddate)){
				Map map1= utildao.getMap();
			    map1.put("program_JMurl", projectdirectory);
			    map1.put("play_type", playtype);
				map1.put("play_start_time", startdate);
				map1.put("play_end_time", enddate);
				map1.put("play_count", timecount);
				map1.put("day_stime1", starttime);
				map1.put("day_etime1", endtime);
				map1.put("templateid", templateid);
				utildao.saveinfo(map1, "xct_JMPZ_type");
			}
			if(!"".equals(twostarttime)&&!"".equals(twoendtime)){
				Map map1= utildao.getMap();
			    map1.put("program_JMurl", projectdirectory);
			    map1.put("play_type", playtype);
				map1.put("play_start_time", startdate);
				map1.put("play_end_time", enddate);
				map1.put("play_count", timecount2);
				map1.put("day_stime1", twostarttime);
				map1.put("day_etime1", twoendtime);
				map1.put("templateid", templateid);
				utildao.saveinfo(map1, "xct_JMPZ_type");
			}if(!"".equals(threestarttime)&&!"".equals(threeendtime)){
				Map map1= utildao.getMap();
			    map1.put("program_JMurl", projectdirectory);
			    map1.put("play_type", playtype);
				map1.put("play_start_time", startdate);
				map1.put("play_end_time", enddate);
				map1.put("play_count", timecount3);
				map1.put("day_stime1", threestarttime);
				map1.put("day_etime1", threeendtime);
				map1.put("templateid", templateid);
				utildao.saveinfo(map1, "xct_JMPZ_type");
			}
			
		}else if("4".equals(playtype)){///永久循环
             startdate =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
             starttime=new SimpleDateFormat("HH:mm").format(new Date())+":00";
	         String[] datestring= DateUtils.addDate(startdate+" "+starttime,Integer.parseInt(timecount)).split(" ");
	         enddate=datestring[0];
	         endtime=datestring[1];
			map.put("play_start_time", startdate);
			map.put("play_end_time", startdate);
			map.put("play_count", timecount);
			map.put("day_stime1", starttime);
			map.put("day_etime1", endtime);
			utildao.saveinfo(map, "xct_JMPZ_type");
		}
		request.setAttribute("addtype", "addok");
		
//		if(null!=set){
//		   System.out.println(playname+"-------"+set.size());
//		}
	    try {
	    	request.setAttribute("program_name", playname);
			request.getRequestDispatcher(new StringBuffer().append("/admin/program/opprojecttype.jsp?program_file=").append(projectdirectory).append("&templateid=").append(templateid).toString()).forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
//		/-------------------------------------------------------------------------------------------------
		//return  mapping.findForward("OK");
	}
}
