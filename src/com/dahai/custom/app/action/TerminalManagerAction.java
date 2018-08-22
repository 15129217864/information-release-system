package com.dahai.custom.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;
import com.xct.cms.dao.ProgramAppDAO;
import com.xct.cms.domin.ProgramApp;
import com.xct.cms.domin.Terminal;
import com.xct.cms.servlet.FirstStartServlet;


public class TerminalManagerAction extends Action {//�ﺣ���ɽӿ�
	
	//��ȡ�ն��嵥
	 public String ClientsList(Map<String,Terminal>terminalmap){
	    	JSONArray array=new JSONArray();
	    	JSONObject object=new JSONObject();
		    if(null!=terminalmap){
		    	if(!terminalmap.isEmpty()){
					for (Map.Entry<String,Terminal>mapEntry:terminalmap.entrySet()) {
						Terminal terminal=mapEntry.getValue();
						object.put("mac", mapEntry.getKey());
						object.put("client_name", terminal.getCnt_name());
						object.put("client_ip", terminal.getCnt_ip());
						object.put("client_status",  terminal.getCnt_islink());//1 ���ӣ�3���Ͽ���NULL������
						object.put("play_status",  terminal.getCnt_playstyle());//PLAYER:���ţ�  DOWNLOAD�����أ�  LOADING�����أ� CLOSE��ֹͣ
						object.put("program_name",  terminal.getCnt_nowProgramName());
						object.put("program_type",  terminal.getCnt_playprojecttring().split("#")[3]);
						array.add(object);
					}
		    	}else{
		    		object.put("code",  "ERROR");
		    		array.add(object);
		    	}
		    }
				
			return array.toString();
	    }
	 
//	��ȡĳ���ն˽�Ŀ�嵥
	 public String ProgramsList(List<ProgramApp>list){
		 
	    	JSONArray array=new JSONArray();
	    	JSONObject object=new JSONObject();
		    if(null!=list){
			   if(!list.isEmpty()){
					for (ProgramApp programApp:list) {
						object.put("id", programApp.getId());//MAC
						object.put("mac", programApp.getMac());//MAC��ַ
						object.put("program_name", programApp.getProgram_name());//��Ŀ����
						object.put("create_time", programApp.getProgram_app_time());//����ʱ��
						object.put("create_username",  programApp.getProgram_app_userid());//������
						object.put("beginTime",  programApp.getProgram_playdate()+" "+programApp.getProgram_playtime());//��ʼʱ��
						object.put("endTime",  programApp.getProgram_enddate()+" "+programApp.getProgram_endtime());//����ʱ��
						object.put("play_type",  programApp.getProgram_play_typeZh());//��������
						object.put("play_duration",  programApp.getProgram_playlong());//ʱ��
						array.add(object);
					}
			   }else {
				   object.put("code",  "ERROR");
				   array.add(object);
			   }
		   }
			return array.toString();
	    }
		
	 
	 String status="";
	 
	 public String SendOPOrder(String macString,final String ipString, final String orderString){
		 
		FirstStartServlet.client.allsend(macString,ipString, orderString);//����ָ��
		
		final String keString=ipString+"_"+orderString;
		Thread thread=new Thread(new Runnable(){//�ȴ�ָ��
			int i=0;
			public void run() {
				while(true){
				     status=FirstStartServlet.client_result_states.get(keString);	
					 try {
						Thread.sleep(1000);
					 } catch (InterruptedException e) {
						e.printStackTrace();
					 }
//					 System.out.println("status-------1------------->"+status);
					 if(null!=status||i++>=5)//5�볬ʱ
					    break;
				}
			}
		});
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//		System.out.println("status-------2------------->"+status);
		JSONArray array=new JSONArray();
		JSONObject object=new JSONObject();
		if(null!=status){
			
	    	object.put("ip",  ipString);
			if(status.indexOf("OK")!=-1){
				object.put("code",  "OK");
			}else{
				object.put("code",  "ERROR");
			}
			status="";
			FirstStartServlet.client_result_states.remove(keString);
			array.add(object);
		}else {
			object.put("code",  "ERROR");
			array.add(object);
		}
		return array.toString();
	 }
	
	String pushString="";
	
//	http://192.168.10.133/rq/terminalmanager?op=sleep&mac=206A8A756BE8&ip=192.168.10.130
	
	//http://127.0.0.1:8085/rq/terminalmanager?op=clientslist
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("GBK");
			response.setContentType("text/html");
			
			response.setCharacterEncoding("UTF-8");
			
			String opString=request.getParameter("op");
			String macString=request.getParameter("mac");
			String ipString=request.getParameter("ip");
			
			if(opString.equals("clientslist")){//�ն��嵥
				pushString=ClientsList(FirstStartServlet.terminalMap);
				
			}else if(opString.equals("programslist")){//ĳ�ն˵���Ľ�Ŀ���嵥
//	http://192.168.10.133/rq/terminalmanager?op=programslist&mac=222C44BC65A2&beginTime=2014-03-10_10:09:08&endTime=2014-03-10_18:09:08
				
				String begintimeString=request.getParameter("beginTime");
				String endtimeString=request.getParameter("endTime");
				if(null!=macString&&null!=begintimeString&&null!=endtimeString)
				   pushString=ProgramsList(new ProgramAppDAO().getOnlyProgramApp(macString,begintimeString,endtimeString));
				else {
					pushString="[{\"code\":\"ERROR\"}]";
				}
			}else if(opString.equals("shutdown")){//�ػ� lv0007
				pushString=SendOPOrder(macString,ipString, "lv0007");
				
			}else if(opString.equals("restart")){//���� lv0005
				pushString=SendOPOrder(macString,ipString, "lv0005");
				
			}else if(opString.equals("sleep")){//���� lv0012
				pushString=SendOPOrder(macString,ipString, "lv0012");
				
			}else if(opString.equals("rouse")){// ֹͣ���� lv0013
				pushString=SendOPOrder(macString,ipString, "lv0013");
			}
			PrintWriter out=response.getWriter();
			out.println(pushString);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
