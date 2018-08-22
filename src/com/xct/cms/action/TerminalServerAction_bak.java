package com.xct.cms.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.mina.util.ConcurrentHashSet;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.xct.cms.dao.LogsDAO;
import com.xct.cms.dao.ProgramAppDAO;
import com.xct.cms.dao.TerminalDAO;
import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.ClientCmd;
import com.xct.cms.domin.CntResponse;
import com.xct.cms.domin.ProgramApp;
import com.xct.cms.domin.Terminal;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.ConstantUtil;
import com.xct.cms.utils.DESPlusUtil;
import com.xct.cms.utils.ReadMacXmlUtils;
import com.xct.cms.utils.Util;
import com.xct.cms.utils.UtilDAO;
import com.xct.cms.xy.dao.ManagerProjectDao;

public class TerminalServerAction_bak extends Action {
	
	//���ǿɵó���ÿͻ�����ʵIP��ַ�ķ���һ��
	public String getRemortIP(HttpServletRequest request) {  
	    if (request.getHeader("x-forwarded-for") == null) {  
	        return request.getRemoteAddr();  
	    }  
	    return request.getHeader("x-forwarded-for");  
	}  
	
	//��ÿͻ�����ʵIP��ַ�ķ�������
	  public String getIpAddr(HttpServletRequest request) {  
	      String ip = request.getHeader("x-forwarded-for");  
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	          ip = request.getHeader("Proxy-Client-IP");  
	      }  
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	          ip = request.getHeader("WL-Proxy-Client-IP");  
	      }  
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	          ip = request.getRemoteAddr();  
	      }  
	      return ip;  
	  }  
	  
/*	 �����ͨ���˶༶�������Ļ���X-Forwarded-For��ֵ����ֹһ��������һ��IPֵ�������ĸ������������û��˵���ʵIP�أ�

	��������ȡX-Forwarded-For�е�һ����unknown����ЧIP�ַ������磺
	X-Forwarded-For��192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
	�û���ʵIPΪ�� 192.168.1.110*/


	LogsDAO logsdao= new LogsDAO();
	
	Logger logger = Logger.getLogger(TerminalServerAction.class);
	
	String terminal_mac="";
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
	  if(FirstStartServlet.INITSTART==1){//������Ҫ�����ö�ȫ����������ִ������
		  
		 try{
			String revqsring=request.getParameter("q");
		    String rev=(null==revqsring?"":revqsring);
		    //System.out.println("����ǰ"+rev);
		    rev= DESPlusUtil.Get().decrypt(rev);   ///����
		      
//		    if(rev.startsWith("inglv"))
//		       logger.info("���ܺ�---->"+rev);
		      
		    // supercommand%00E040690B80%PLAYER%Ĭ�Ͻ�Ŀ%Ĭ�Ͻ�Ŀ#default#2013-03-15 09:13:26#LOOP#1#%7:0_20:0_1:0%2013-03-15 09:13:49%XCT B/S ���ڰ� 6.1.10
		    
			if(rev.startsWith("lv")||rev.startsWith("inglv")||rev.startsWith("supercommand")){  //�·���Ŀ
				
			    String cnt_ip=request.getRemoteAddr().trim();
				if(rev.startsWith("supercommand")){
					
					String terminal_status="",playName="",desplayProjecttring="";
					String [] revs=rev.split("%");
					if(revs[1].indexOf("#")>-1){
						terminal_mac=revs[1].replace("#", "_").split("_")[0];   //MAC
					}else{
						terminal_mac=revs[1];   //MAC
					}
					terminal_status=revs[2];      //MAC��ַ???
					playName=revs[3];
					if(revs.length>4){
						desplayProjecttring=revs[4];
					}
					if(revs.length>=8){
						FirstStartServlet.client_ip_version.put(new StringBuffer().append(cnt_ip).append("_").append(terminal_mac).toString(), revs[7]);//�汾��
					}
					if(revs.length>=9){
						FirstStartServlet.client_ip_httpport.put(new StringBuffer().append(cnt_ip).append("_").append(terminal_mac).toString(), revs[8]);//�ն�web�˿ں�
					}
					
					//FirstStartServlet.client_ip_port_map.put(terminal_mac, pd.getSocketAddress().toString().substring(1));
					
					TerminalDAO termainaldao= new TerminalDAO();
					int bool=termainaldao.checkIsTerminal(terminal_mac,cnt_ip);
					/*if("192.168.10.107".equals(terminal_ip)){
						System.out.println("�ն˷���==="+terminal_ip +"bool="+bool);
					}*/
					
					if(null!=FirstStartServlet.RESPONSE_INTERFACE_TERMINALMAP.get(terminal_mac)){
						FirstStartServlet.RESPONSE_INTERFACE_TERMINALMAP.get(terminal_mac).setCnt_islink(1);
					}
					
					if(bool==1){
						//�鵽�������д��նˣ����޸Ļ���map�ļ�ֵ
						Map<String,Terminal> terminals=FirstStartServlet.terminalMap;
						Terminal terminal=terminals.get(terminal_mac);
						terminal.setCnt_ip(cnt_ip);
						terminal.setCnt_nowProgramName(playName);
						terminal.setCnt_playstyle(terminal_status);
						terminal.setCnt_islink(1);
						terminal.setCnt_status(terminal.getCnt_status());
//						terminal.setCnt_status("1");
						terminal.setCnt_playprojecttring(desplayProjecttring);
						
//						terminal.setSend_type("HTTP");
						terminal.setSend_type(System.getProperty("SENDTYPE"));
						
						terminal.setIsonlinetime(System.currentTimeMillis());
						terminals.put(terminal_mac, terminal);
						FirstStartServlet.terminalMap.put(terminal_mac, terminal);
						
					}else if(bool==0){//��ע���������ն˻�����
						  
						  Connection conn = new DBConnection().getConection();
						
//						  int map_size=TerminalDAO.getTerminalNum(conn);
//						  if(map_size<1){ ///ֻ������ն˵ĸ���
							  
								String nowtime=UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
								UtilDAO utildao= new UtilDAO();
						   if(!terminal_mac.equals("")&&null!=terminal_mac){//ipconfig ���ܱ�����,��ȡ����MAC��ַ�ͻ�Ϊ��
								if(!utildao.isExistMAC(conn,terminal_mac)){
									logger.info("�����ն�-------------->"+cnt_ip+"___"+terminal_mac);
									Map<String,String> map= UtilDAO.getMap();
									map.put("cnt_ip", cnt_ip);
									map.put("cnt_port", "10001");
									map.put("cnt_mac", terminal_mac);
									map.put("cnt_playstyle", terminal_status);
									map.put("cnt_kjtime", "7");
									map.put("cnt_gjtime", "20");
									map.put("cnt_downtime", "1");
									map.put("cnt_addtime", nowtime);
									map.put("cnt_islink", "1");
									map.put("cnt_zuid", "1");
									map.put("cnt_nowProgramName", playName);
									map.put("cnt_status", "0");
									map.put("cnt_playprojecttring",desplayProjecttring);
									map.put("client_version",revs.length>=8?revs[7]:"VVBS 6.1+");
									map.put("is_day_download","1");
									utildao.saveinfo(conn,map, "xct_ipaddress");
								
									//////////////����µ��ն˵�����
									Terminal terminal=new Terminal();
									terminal.setCnt_ip(cnt_ip);
									terminal.setCnt_mac(terminal_mac);
									terminal.setCnt_nowProgramName(playName);
									terminal.setCnt_playstyle(terminal_status);
									terminal.setCnt_islink(1);
									terminal.setCnt_zuid(1);
									terminal.setCnt_status("0");
									terminal.setCnt_playprojecttring(desplayProjecttring);
									
//									terminal.setSend_type("HTTP");
									terminal.setSend_type(System.getProperty("SENDTYPE"));
									
									terminal.setIsonlinetime(System.currentTimeMillis());
									FirstStartServlet.terminalMap.put(terminal_mac, terminal);
									
									utildao=null;
									terminal=null;
								}
                              }else{
                            	  logger.info("��ע���ն���Ϣ�쳣-------------->"+cnt_ip+"___�ն�MAC��ַδ��ȡ����ipconfig ���ܱ����á�");
							  }
					  }
					
					 //======================����ִ��ָ��===============================================
					 allsendBackCmd(terminal_mac,cnt_ip,response);
					 //===============================================================================
				}else{
						Connection conn =new DBConnection().getConection();
					
	////					 rev==lv0005#*#000000000000#*#lv0005_OK#*#
						String cnt_cmd="",cnt_cmdstatus="",programurl="",cnt_cmdResult="";
						
						String [] revs=rev.split("%");
						cnt_cmd=revs[0];   //����
						terminal_mac=revs[1];      //MAC��ַ
						cnt_cmdstatus=revs[2];   ////����״̬
						
						logger.info("�նˡ�"+cnt_ip+"__"+terminal_mac+"��HTTP��������=====[�ն˷���]======="+rev);
						  
						//FirstStartServlet.client_ip_port_map.put(mac, pd.getSocketAddress().toString().substring(1) );
						String ipmac=new StringBuffer().append(cnt_ip).append("_").append(terminal_mac).toString();
						String client_result_states_key=new StringBuffer().append(ipmac).append("_").append(cnt_cmd).toString();
					   if(rev.startsWith("lv0009")){  //�·���Ŀ
						   
						  logger.info("����lv0009 ���ִ�---------------->"+rev);
						  if(revs.length<4){
							  return null;
						  }
						  programurl=revs[3];
						  List<ProgramApp> programapplist=new ArrayList<ProgramApp>();
						  String[] sttmp=null;
						  sttmp=programurl.split("A");
						  
						  if(sttmp.length>=2){
							  
								String tempstring=sttmp[1];
								String []idstmp=((tempstring.indexOf("!")!=-1)?tempstring.split("!"):new String[]{tempstring});
								for(int i=0,n=idstmp.length;i<n;i++){
									if(!idstmp[i].equals("")&&null!=idstmp[i]){
										if(Util.isNumeric(idstmp[i])){
									       programapplist.add(new ProgramAppDAO().getProgramAppByStr(conn,"where id="+idstmp[i]));
										}else {
											logger.info("TerminalServerAction,���� id ��ѯ<<"+idstmp[i]+">>,��ȡ�ѷ��ͽ�Ŀ����id��������");
										}
									}
								}
							  
								String cnt_programurl=sttmp[0].split("!")[0];
								String [] cnt_programid=sttmp[1].split("!");
								
//								ProgramDAO programdao= new ProgramDAO();
								ManagerProjectDao managerprojectdao=new ManagerProjectDao();
								////////////////*************�����ն����ؽ�Ŀ״̬
								
								Map<String,CntResponse> map=FirstStartServlet.downloadProgramStatus;
								CntResponse cntrp= new CntResponse();
								cntrp.setCnt_ip(cnt_ip);
								cntrp.setCnt_cmd(cnt_cmd);
								cntrp.setCnt_cmdstatus(cnt_cmdstatus);
								cntrp.setCnt_cmdresult(cnt_cmdResult);
								cntrp.setCnt_programurl(cnt_programurl);
								cntrp.setCnt_mac(terminal_mac);
								map.put(cnt_ip+"_"+terminal_mac,cntrp);
								FirstStartServlet.downloadProgramStatus=map;  //��ſͻ������ؽ�Ŀ��״̬
								//////////////////////*************
								ProgramAppDAO programappdao= new ProgramAppDAO();
		
								if(cnt_cmdstatus.equals("lv0009_OK")){
									 ProgramApp programapp;
									 logsdao.addlogs1(conn,"system", "�նˡ�"+TerminalDAO.getTerminalNameByMAC(terminal_mac)+"�����ؽ�Ŀ�ɹ�", 1);
									 for(int i=0,n=programapplist.size();i<n;i++){
										 programapp=programapplist.get(i);
										 managerprojectdao.updateProjectIsSend(conn,2,1,programapp.getProgram_jmurl(),terminal_mac);//  ��ֵ�ĳ� 2 ��ʾ ���سɹ� ,0 ��ʾ����ͽ����Ժ��δ���͵Ľ�Ŀ��
									 }
									 for(int i=0;i<cnt_programid.length;i++){
										 String sql=" update xct_JMApp set program_sendok_terminal=REPLACE(program_sendok_terminal,'#"+terminal_mac+"','')+'#"+terminal_mac+"' where id="+cnt_programid[i];
										 programappdao.UpdateSendState(conn, sql);
									 }
								}else{
									 logsdao.addlogs1(conn,"system", "�նˡ�"+TerminalDAO.getTerminalNameByIp(cnt_ip,terminal_mac)+"�����ؽ�Ŀʧ�ܡ�(ʧ��ԭ��"+cntrp.getCnt_cmdresultZh().replace("<img src='/images/error.gif'  height='16px'>", "").replace("'","")+")", 1);
									 managerprojectdao.updateProjectIsSend(conn,3,1,cnt_programurl,cnt_ip);//  ��ֵ�ĳ� 3 ��ʾ ����ʧ��
									 for(int i=0;i<cnt_programid.length;i++){
										new UtilDAO().deleteinfo(conn,"program_senduser", cnt_programid[i], "xct_JMhistory");
										new ReadMacXmlUtils().deleteMacXml(terminal_mac, cnt_programid[i]);
									 }
									// ProgramApp programapp= programappdao.getProgramAppByStr(" where ");
								}
								ConstantUtil.changeClientCmd(ipmac,"lv0009");
//								allsend(terminal_mac,cnt_ip,"lv_0009");
							 }else {
								 logger.info("���ص��·���Ŀ��ID��ϡ�A������С��2��------------------=>"+programurl);
							 }
					  }else if(rev.startsWith("lv0028")){// �鿴��Ŀ��
						    cnt_cmdResult=revs[3];
							CntResponse cntr= new CntResponse();
							cntr.setCnt_ip(cnt_ip);
							cntr.setCnt_cmd(cnt_cmd);
							cntr.setCnt_cmdstatus(cnt_cmdstatus);
							cntr.setCnt_cmdresult(cnt_cmdResult);
							cntr.setCnt_programurl(programurl);
							cntr.setCnt_ip(cnt_ip);
							cntr.setCnt_mac(terminal_mac);
							FirstStartServlet.client_program_menu.put(client_result_states_key, cntr);
							ConstantUtil.changeClientCmd(ipmac,"lv0028");
//							allsend(terminal_mac,cnt_ip,"lv_0028");
							
					  }else if(rev.startsWith("lv0011")){///����
							cnt_cmd=revs[0];   //����
							cnt_cmdstatus=revs[2];   ////����״̬
							FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
							ConstantUtil.changeClientCmd(ipmac,"lv0011");
							
//							if(!System.getProperty("SENDTYPE").equals(ConstantUtil.PNP))
//							   allsend(terminal_mac,cnt_ip,"lv_0011");
							
					  }else if(rev.startsWith("inglv0009")){///��ʼ���ؽ�Ŀ
							cnt_cmd=revs[0];   //����
							cnt_cmdstatus=revs[2];   ////����״̬
//								System.out.println(cnt_ip+"_"+cnt_cmd+"________"+cnt_cmdstatus);
							FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
							ConstantUtil.changeClientCmd(ipmac,"lv0009");
							allsend(terminal_mac,cnt_ip,"inglv_0009");
	   					
					  }else if(rev.startsWith("inglv_0009")){
							
	   					   ConstantUtil.changeClientCmd(ipmac,"inglv_0009");
							
					  }else if(rev.startsWith("lv0023")){//�޸���������
						cnt_cmd=revs[0];   //����
						cnt_cmdstatus=revs[2];   ////����״̬
						FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	
						ConstantUtil.changeClientCmd(ipmac,"lv0023");
//						allsend(terminal_mac,cnt_ip,"lv_0023");
						
					  }else if(rev.startsWith("lv0012")){//����
						cnt_cmd=revs[0];   //����
						cnt_cmdstatus=revs[2];   ////����״̬
						FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	 
						Terminal terminal= FirstStartServlet.terminalMap.get(terminal_mac);
						terminal.setCnt_playstyle("CLOSE");
						terminal.setCnt_nowProgramName("��");
						FirstStartServlet.terminalMap.put(terminal_mac, terminal);
						ConstantUtil.changeClientCmd(ipmac,"lv0012");
//						allsend(terminal_mac,cnt_ip,"lv_0012");
						
					  }else if(rev.startsWith("lv0013")){//ֹͣ����
						 cnt_cmd=revs[0];   //����
						 cnt_cmdstatus=revs[2];   ////����״̬
						 FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
						 ConstantUtil.changeClientCmd(ipmac,"lv0013");
//						 allsend(terminal_mac,cnt_ip,"lv_0013");
						
					  }else if(rev.startsWith("lv0014")){//ʱ��ͬ��
						  cnt_cmd=revs[0];   //����
						  cnt_cmdstatus=revs[2];   ////����״̬
						  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	
						////////////////////////�޸��������ն�״̬
						  Terminal terminal= FirstStartServlet.terminalMap.get(terminal_mac);
						  terminal.setCnt_islink(3);
						  terminal.setCnt_nowProgramName("��");
						  terminal.setCnt_playstyle("CLOSE");
						  FirstStartServlet.terminalMap.put(terminal_mac, terminal);
						  ConstantUtil.changeClientCmd(ipmac,"lv0014");
//						  allsend(terminal_mac,cnt_ip,"lv_0014");
						  
					  }else if(rev.startsWith("lv0005")){//����
						  cnt_cmd=revs[0];   //����
						  cnt_cmdstatus=revs[2];   ////����״̬
						  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	 
						  ////////////////////////�޸��������ն�״̬
						  Terminal terminal= FirstStartServlet.terminalMap.get(terminal_mac);
						  terminal.setCnt_islink(3);
						  terminal.setCnt_nowProgramName("��");
						  terminal.setCnt_playstyle("CLOSE");
						  FirstStartServlet.terminalMap.put(terminal_mac, terminal);
						  ConstantUtil.changeClientCmd(ipmac,"lv0005");
						  allsend(terminal_mac,cnt_ip,"lv_0005");
						  
					  }else if(rev.startsWith("lv0007")){//�ػ�
						  cnt_cmd=revs[0];   //����
						  cnt_cmdstatus=revs[2];   ////����״̬
						  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
						  ////////////////////////�޸Ĺػ����ն�״̬
						  Terminal terminal= FirstStartServlet.terminalMap.get(terminal_mac);
						  terminal.setCnt_islink(3);
						  terminal.setCnt_nowProgramName("��");
						  terminal.setCnt_playstyle("CLOSE");
						  FirstStartServlet.terminalMap.put(terminal_mac, terminal);
						  ConstantUtil.changeClientCmd(ipmac,"lv0007");
						  allsend(terminal_mac,cnt_ip,"lv_0007");
					  }
					  
					  else if(rev.startsWith("inglv0036")){///�����ն˽�Ŀ ��ʼ���ؽ�Ŀ
							cnt_cmd=revs[0];   //����
							cnt_cmdstatus=revs[2];   ////����״̬
							FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
							ConstantUtil.changeClientCmd(ipmac,"lv0036");
							allsend(terminal_mac,cnt_ip,"inglv_0036");
							
					  }else if(rev.startsWith("inglv_0036")){
							
	   					   ConstantUtil.changeClientCmd(ipmac,"inglv_0036");
							
					  }else if(rev.startsWith("lv0036")){//�����ն˽�Ŀ
						    cnt_cmd=revs[0];   //����
							cnt_cmdstatus=revs[2];   ////����״̬
							FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	
							ConstantUtil.changeClientCmd(ipmac,"inglv0036");
//						    allsend(terminal_mac,cnt_ip,"lv_0036");
					  }
					   
					   ///////////////////ɳ�ع�����LED�����ع���
					  else if(rev.startsWith("lvled01")){//����LED
						    cnt_cmd=revs[0];   //����
							cnt_cmdstatus=revs[2];   ////����״̬
							FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	
							ConstantUtil.changeClientCmd(ipmac,"lvled01");
//						    allsend(terminal_mac,cnt_ip,"lv_led01");
						  
					  }else if(rev.startsWith("lvled02")){//�����ն˽�Ŀ
						    cnt_cmd=revs[0];   //����
							cnt_cmdstatus=revs[2];   ////�ر�LED
							FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	
							ConstantUtil.changeClientCmd(ipmac,"lvled02");
//						    allsend(terminal_mac,cnt_ip,"lv_led02");
						  
					  }else if(rev.startsWith("lvled03")){//LED����֪ͨ
						  cnt_cmd=revs[0];   //����
						  cnt_cmdstatus=revs[2];   ////����״̬
						  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
						  ConstantUtil.changeClientCmd(ipmac,"lvled03");
//						  allsend(terminal_mac,cnt_ip,"lv_led03");   
					  }
					   
					  else if(rev.startsWith("inglv0101")){///���͵㲥��Ŀ ��ʼ���ؽ�Ŀ
							cnt_cmd=revs[0];   //����
							cnt_cmdstatus=revs[2];   ////����״̬
							FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
							ConstantUtil.changeClientCmd(ipmac,"inglv0101");
							allsend(terminal_mac,cnt_ip,"inglv_0101");
							
					  }else if(rev.startsWith("inglv_0101")){
							
	   					   ConstantUtil.changeClientCmd(ipmac,"inglv_0101");
							
					  }else if(rev.startsWith("lv0101")){//���͵㲥��Ŀ
						    cnt_cmd=revs[0];   //����
							cnt_cmdstatus=revs[2];   ////����״̬
							FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	 
							ConstantUtil.changeClientCmd(ipmac,"lv0101");
//						    allsend(terminal_mac,cnt_ip,"lv_0101");
						  
					  }else if(rev.startsWith("lv0102")){//�����ն˽�Ŀ
						    cnt_cmd=revs[0];   //����
							cnt_cmdstatus=revs[2];   ////����״̬
							FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
							ConstantUtil.changeClientCmd(ipmac,"lv0102");
//						    allsend(terminal_mac,cnt_ip,"lv_0102");
					  }
					  else if(rev.startsWith("inglv0037")){///����Ĭ�Ͻ�Ŀ ��ʼ���ؽ�Ŀ
							cnt_cmd=revs[0];   //����
							cnt_cmdstatus=revs[2];   ////����״̬
							FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
							ConstantUtil.changeClientCmd(ipmac,"lv0037");
							allsend(terminal_mac,cnt_ip,"inglv_0037");
							
					  }else if(rev.startsWith("inglv_0037")){
							
	   					   ConstantUtil.changeClientCmd(ipmac,"inglv_0037");
							
					  }else if(rev.startsWith("lv0037")){//����Ĭ�Ͻ�Ŀ
						  cnt_cmd=revs[0];   //����
						  cnt_cmdstatus=revs[2];   ////����״̬
						  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
						  ConstantUtil.changeClientCmd(ipmac,"lv0037");
//						  allsend(terminal_mac,cnt_ip,"lv_0037");
						
					  }else if(rev.startsWith("lv0018")){//�������
						  cnt_cmd=revs[0];   //����
						  cnt_cmdstatus=revs[2];   ////����״̬
						  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	 
	                        ///////////////////////�޸��������ն�״̬
						  Terminal terminal= FirstStartServlet.terminalMap.get(terminal_mac);
						  terminal.setCnt_islink(3);
						  terminal.setCnt_nowProgramName("��");
						  terminal.setCnt_playstyle("CLOSE");
						  FirstStartServlet.terminalMap.put(terminal_mac, terminal);
						  ConstantUtil.changeClientCmd(ipmac,"lv0018");
//						  allsend(terminal_mac,cnt_ip,"lv_0018");
						  
					  }else if(rev.startsWith("lv0024")){//����֪ͨ
						  cnt_cmd=revs[0];   //����
						  cnt_cmdstatus=revs[2];   ////����״̬
						  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
						  ConstantUtil.changeClientCmd(ipmac,"lv0024");
//						  allsend(terminal_mac,cnt_ip,"lv_0024");   
						  
					  }else if(rev.startsWith("lv0029")){//��ʼ��
						  cnt_cmd=revs[0];   //����
						  cnt_cmdstatus=revs[2];   ////����״̬
						  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
						  ConstantUtil.changeClientCmd(ipmac,"lv0029");
//						  allsend(terminal_mac,cnt_ip,"lv_0029");      
						  
					  }else if(rev.startsWith("lv0025")){//ɾ��ĳ����Ŀ
						  ConstantUtil.changeClientCmd(ipmac,"lv0025");
//						  allsend(terminal_mac,cnt_ip,"lv_0025");    
						  
					  }else if(rev.startsWith("lv0019")){//��������
						  ConstantUtil. changeClientCmd(ipmac,"lv0019");
//						  allsend(terminal_mac,cnt_ip,"lv_0019");    
						  
					  }else if(rev.startsWith("inglv0103")){//���͵㲥��Ŀ�ļ� ��ʼ���ؽ�Ŀ
						  cnt_cmd=revs[0];   //����
						  cnt_cmdstatus=revs[2];   ////����״̬
						  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
						  ConstantUtil.changeClientCmd(ipmac,"inglv0103");
						  allsend(terminal_mac,cnt_ip,"inglv_0103");
							
					  }else if(rev.startsWith("lv0103")){//���غ��͵㲥��Ŀ
						  cnt_cmd=revs[0];   //����
					      cnt_cmdstatus=revs[2];   ////����״̬
						  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
						  ConstantUtil.changeClientCmd(ipmac,"lv0103");
//						  allsend(terminal_mac,cnt_ip,"lv_0103");
						  
					  }else if(rev.startsWith("inglv_0103")){
	   					   ConstantUtil.changeClientCmd(ipmac,"inglv_0103");
					  }
			       }
				}
			}catch(Exception e){
				e.printStackTrace();
				logger.info("�ն˷��ʳ���(TerminalServerAction)��------>"+e.getMessage());
			}
		}
		return null;
	}
	
	
	//�ն˷��ʺ󣬷����ն�ָ��
	public void allsendBackCmd(final String terminal_mac,final String cnt_ip,final HttpServletResponse response){
//		new Thread(new Runnable(){
//		public void run() {
			PrintWriter out=null;
			try {
				out = response.getWriter();
				String key=new StringBuffer().append(cnt_ip).append("_").append(terminal_mac).toString();
				
				if(null!=ConstantUtil.CLIENTCMDMAP&&ConstantUtil.CLIENTCMDMAP.size()>0){
				    if(null!=ConstantUtil.CLIENTCMDMAP.get(key)){
				    	
				    	//=====================================================================================
//				    	for (Map.Entry<String, ConcurrentHashSet<ClientCmd>> entry : ConstantUtil.CLIENTCMDMAP.entrySet()){
//			   				  Set<ClientCmd> clientcmdset=entry.getValue();
//						      for(ClientCmd clientcmd:clientcmdset){
//		   						 logger.info("��δִ�е�ָ��%%%%%%%%%%-------3------>"+clientcmd);
//			   				  }
//				        }
				       //=====================================================================================
				    	
		   			   for (Map.Entry<String, ConcurrentHashSet<ClientCmd>> entry : ConstantUtil.CLIENTCMDMAP.entrySet()){
		   				   if(key.equals(entry.getKey())){
		   					   
			   				  Set<ClientCmd> clientcmdset=entry.getValue();
		  				      for(ClientCmd clientcmd:clientcmdset){
		  				    	  
			   					  if(clientcmd.getIscancel()==0&&clientcmd.getIsoperator()==0){//��ȡδȡ����ָ���δִ�е�ָ��
			   						  out.print(clientcmd.getCmd());
			   						  String desString=DESPlusUtil.Get().decrypt(clientcmd.getCmd());
			   						  logger.info(MessageFormat.format("***�ն� {0}����ȡ����δȡ����δִ�С� ��ָ��(���ܺ�)��{1},���ܺ�{2}", key,clientcmd.getCmd(),desString));
			   						  
			   						  ConstantUtil.changeClientCmd(key,desString); //�����Ѿ�ִ�й���ָ��
			   						  break;
			   					  }
			   				  }
		   				   }
		   			   }
			        }
				}
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(null!=out){
					out.close();
				}
			}
//		}
//	}).start();
}
	public void allsend(String terminal_mac,String cnt_ip,String s){ 
		
		FirstStartServlet.client.allsend(terminal_mac,cnt_ip, s);
		
	}
}