package com.xct.cms.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.dao.ProgramAppDAO;
import com.xct.cms.dao.ProgramDAO;
import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Program;
import com.xct.cms.domin.Terminal;
import com.xct.cms.domin.Users;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.UtilDAO;

public class AheadWriteProjectAction extends Action {
	
	Logger logger = Logger.getLogger(AheadWriteProjectAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session =request.getSession();
		Users user=(Users)session.getAttribute("lg_user");
		String allip=request.getParameter("allips");
		String iscb=request.getParameter("iscb");
		String playcunt=request.getParameter("playcunt");
	    String programe_file=request.getParameter("programe_file");//选择要发送的节目名称
	    String programefile[] = programe_file.split("!"); 
	    
	    ProgramDAO prodap= new ProgramDAO();
	    
	    DBConnection dbc= new DBConnection();
	    Connection con = dbc.getConection();
	    try{
			//Map<String,List<Program>> programmap= prodap.getProgram3(con,programefile);
			Map<String,List<Program>> programmap= prodap.getProgram3(con,iscb,playcunt,programefile);
			
			//Map<String,List<ProjectBean>>projectlistmap=(Map<String,List<ProjectBean>>)session.getAttribute("projectlistmap");
			 
			UtilDAO utildao = new UtilDAO();
	        String nowtime =UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
	        //String nowtime1=UtilDAO.getNowtime("yyyy-MM-dd");
	        
	        String []iparray= allip.replaceAll("\\s+","").split("!");
	        String newallips="";
	        String newallmacs="";
	        
	        for(int i=0,n=iparray.length;i<n;i++){
	        	if(!"".equals(iparray[i])){
		        	String []macip=iparray[i].split("_");
	    			for (Map.Entry<String, Terminal> ter : FirstStartServlet.terminalMap.entrySet()) {
	    				Terminal t=ter.getValue();
	    				if(null!=t&&macip[1].equals(t.getCnt_mac())){
	    					   newallips+=new StringBuffer().append("#").append(macip[0]).append("_").append(t.getCnt_mac()).toString();
	    					   newallmacs+=new StringBuffer().append("#").append(t.getCnt_mac()).toString();
	    				}
		    		}
	        	}
	        }
//	        String ip = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
//	        Pattern macPattern = Pattern.compile(ip);
//	        Matcher macMatcher;
//	        for(int i=0,n=iparray.length;i<n;i++){
//	        	macMatcher = macPattern.matcher(iparray[i].split("_")[0]);
//	    		if (macMatcher.find()) {
//	    			for (Map.Entry<String, Terminal> ter : FirstStartServlet.terminalMap.entrySet()) {
//	    				Terminal t=ter.getValue();
//	    				if(t!=null&&macMatcher.group(0).equals(t.getCnt_ip())){
//	    					if(newallips.indexOf(macMatcher.group(0))==-1)//不把重复IP地址的加入
//	    					   newallips+="#"+macMatcher.group(0);
//	    					
//	    					newallmacs+="#"+t.getCnt_mac();
//	    				}
//	    			}
//	    		}
//	        }
	       
	        if(null!=programmap&&programmap.size()!=0){
	        	
	        	String program_id = "";
	        	String all_program_ids="";
	        	List<Program>list;
	        	Program projectbean;
	        	List<String>listids=new ArrayList<String>();
	        	ProgramAppDAO programappdao=new ProgramAppDAO();
	        	int batch=programappdao.getMaxbatch(con);
	        	for(Map.Entry<String,List<Program>>maptmp:programmap.entrySet()){
	        		list=maptmp.getValue();
	        		for(int i=0,n=list.size();i<n;i++){
	        			Map  map= utildao.getMap();
	        			projectbean=list.get(i);
	        			program_id=utildao.buildId(con,"xct_JMApp","program_id","v.0000001");
	        			all_program_ids+="#"+program_id;
	     		        map.put("program_id",program_id);
	     		        map.put("program_jmurl",projectbean.getProgram_JMurl());
	     		        map.put("program_name",projectbean.getProgram_Name());
	     		        map.put("program_playdate",projectbean.getPlay_start_time());
	     		        map.put("program_playtime",projectbean.getDay_stime1());
	     		        map.put("program_enddate",projectbean.getPlay_end_time());
	     		        map.put("program_endtime",projectbean.getDay_etime1());
	     		        map.put("program_playlong",projectbean.getPlay_count()+"");
	     		        
	     		        int type=0; // 循环
	     		        int play_type=projectbean.getPlay_type();
	     				if(play_type==2){
	     					type=1; //插播
	     				}else if(play_type==3){
	     					type=2; //定时
	     				}else if(play_type==4){
	//     					playtype="loop"; //永久循环
	     				    type=3;
	     				}
	     		        map.put("program_play_type",type+"");
	     		        map.put("program_play_terminal",newallmacs);
	     		        map.put("program_sendok_terminal","");
	     		        map.put("program_app_userid",user.getLg_name());
	     		        map.put("program_app_time",nowtime);
	     		        map.put("program_app_status","0");
	     		        
	     		        map.put("program_treeid",projectbean.getProgram_treeid()+"");
	     		        map.put("templateid",projectbean.getTemplateid());
	     		        map.put("batch", batch+"");
	     			    /*boolean bool=*/utildao.saveinfo(con,map,"xct_JMApp");
	     			    listids.add(programappdao.getProgramAppByStrProgramid(con,program_id));
	        		}
	        	}
	        	dbc.returnResources(con);///////////关闭连接
	        
	        	StringBuffer ids=new StringBuffer("");
	        	request.setAttribute("programid", program_id);//最后一个节目的VID
	        	for(int i=0,n=listids.size();i<n;i++){
	        		ids.append(new StringBuffer().append(listids.get(i)).append("!").toString());
	        	}
	            request.setAttribute("programmap",programmap);
	            request.setAttribute("all_program_ids", all_program_ids);////所有节目的VID
	            request.setAttribute("batch", batch);   ////添加的批次号
	            request.setAttribute("send_ips", newallips);  ///发送节目的终端 ,ip地址和mac地址组合
	            logger.info(new StringBuffer().append("send_ips--->").append(newallips).toString());
//	            request.setAttribute("send_macs", newallmacs);  //
	            
	            request.setAttribute("ids",ids.toString());
	            if("1".equals(user.getLg_role()) || "2".equals(user.getLg_role())){
	        	  return  mapping.findForward("OK");
	            }else{
	              request.setAttribute("other", "3");
	        	  return  mapping.findForward("OTHER");
	            }
	        }else{
	        	return  mapping.findForward("ERROR");
	        }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	try {
				if(null!=con && !con.isClosed()){
				   dbc.returnResources(con);///////////关闭连接
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	    return null;
	}
}
