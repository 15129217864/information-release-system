package com.xct.cms.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ArrayList;
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

import com.google.gson.Gson;
import com.gson.domain.CmsServiceInfo;
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

public class TerminalServerAction extends Action {
	
	//于是可得出获得客户端真实IP地址的方法一：
	public String getRemortIP(HttpServletRequest request) {  
	    if (request.getHeader("x-forwarded-for") == null) {  
	        return request.getRemoteAddr();  
	    }  
	    return request.getHeader("x-forwarded-for");  
	}  
	
	//获得客户端真实IP地址的方法二：
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
	  
/*	 　如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？

	　　答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。如：
	X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
	用户真实IP为： 192.168.1.110*/

	LogsDAO logsdao= new LogsDAO();
	
	Logger logger = Logger.getLogger(TerminalServerAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		synchronized(TerminalServerAction.class){
		    return doAction( mapping,  form, request,  response);
		}
	}
	
	public  ActionForward doAction(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
	  if(FirstStartServlet.INITSTART==1){//服务需要的配置都全部启动后，再执行如下
		 String cnt_ip=request.getRemoteAddr().trim();
		 try{
			String revqsring=request.getParameter("q");
		    String rev=(null==revqsring?"":revqsring);
		    //System.out.println("解密前"+rev);
		    rev= DESPlusUtil.Get().decrypt(rev);   ///解密
		      
//		    if(rev.startsWith("inglv"))
//		       logger.info("解密后---->"+rev);
		      
		    // supercommand%00E040690B80%PLAYER%默认节目%默认节目#default#2013-03-15 09:13:26#LOOP#1#%7:0_20:0_1:0%2013-03-15 09:13:49%B/S 大众版 6.1.10
		   
		    
			if(rev.startsWith("lv")||rev.startsWith("inglv")||rev.startsWith("supercommand")){  //下发节目
				
				String terminal_mac="";
				
//				logger.info("解密后---->"+rev);
			    
				if(rev.startsWith("supercommand")){
					
					String terminal_status="",playName="",desplayProjecttring="";
					String [] revs=rev.split("%");
					if(revs[1].indexOf("#")>-1){
						terminal_mac=revs[1].replace("#", "_").split("_")[0];   //MAC
					}else{
						terminal_mac=revs[1];   //MAC
					}
					terminal_status=revs[2];      //MAC地址???
					playName=revs[3];
					if(revs.length>4){
						desplayProjecttring=revs[4];
					}
					if(revs.length>=8){
						FirstStartServlet.client_ip_version.put(new StringBuffer().append(cnt_ip).append("_").append(terminal_mac).toString(), revs[7]);//版本号
					}
					if(revs.length>=9){
						FirstStartServlet.client_ip_httpport.put(new StringBuffer().append(cnt_ip).append("_").append(terminal_mac).toString(), revs[8]);//终端web端口号
					}
					
					//FirstStartServlet.client_ip_port_map.put(terminal_mac, pd.getSocketAddress().toString().substring(1));
					
					int boolflag=(null==terminal_mac||null==FirstStartServlet.terminalMap.get(terminal_mac))?0:1;//Util.checkIsTerminal(terminal_mac,cnt_ip);
					
					/*if("192.168.10.107".equals(terminal_ip)){
						System.out.println("终端访问==="+terminal_ip +"bool="+bool);
					}*/
					
					if(null!=FirstStartServlet.RESPONSE_INTERFACE_TERMINALMAP.get(terminal_mac)){
						FirstStartServlet.RESPONSE_INTERFACE_TERMINALMAP.get(terminal_mac).setCnt_islink(1);
					}
					if(boolflag==1){
						//查到缓存中有此终端，则修改缓存map的键值
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
						
					}else if(boolflag==0){//新注册上来的终端机处理
						 
//						  int map_size=TerminalDAO.getTerminalNum(conn);
//						  if(map_size<1){ ///只能添加终端的个数
						  
						   if(!terminal_mac.equals("") && null!=terminal_mac){//ipconfig 可能被禁用,获取到的MAC地址就会为空
							   
//							    boolean bool2=false;
//								if(null!=Util.TIMEROUT_TERMINALMAP.get(terminal_mac)
//										&&(System.currentTimeMillis()-Util.TIMEROUT_TERMINALMAP.get(terminal_mac))>1000){
//									bool2=true;
//								}else{
//									Util.TIMEROUT_TERMINALMAP.put(terminal_mac,System.currentTimeMillis());
//								}
								 UtilDAO utildao= new UtilDAO();
								if(/*bool2 && */!utildao.isExistMAC(/*conn,*/terminal_mac)){
								
									Map<String,String> map= UtilDAO.getMap();
									map.put("cnt_ip", cnt_ip);
									map.put("cnt_port", "10001");
									map.put("cnt_mac", terminal_mac);
									map.put("cnt_playstyle", terminal_status);
									map.put("cnt_kjtime", "7");
									map.put("cnt_gjtime", "20");
									map.put("cnt_downtime", "1");
									map.put("cnt_addtime", UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss"));
									map.put("cnt_islink", "1");
									map.put("cnt_zuid", "1");
									map.put("cnt_nowProgramName", playName);
									map.put("cnt_status", "0");
									map.put("cnt_playprojecttring",desplayProjecttring);
									map.put("client_version",revs.length>=8?revs[7]:"BS 1.2+");
									map.put("is_day_download","1");
									
									if(utildao.saveinfo2(/*conn,*/map, "xct_ipaddress")){
										
										logger.info(new StringBuffer().append("新增终端-->").append(cnt_ip).append("_").append(terminal_mac).toString());
										//////////////添加新的终端到缓存
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
										Util.TIMEROUT_TERMINALMAP.put(terminal_mac, System.currentTimeMillis());
									}
								}
                              }else{
                            	  logger.info(new StringBuffer().append("新注册终端信息异常---->").append(cnt_ip).append("_终端MAC地址未获取到，ipconfig 可能被禁用。").toString());
							  }
					    }
						//======================返回执行指令===============================================
						allsendBackCmd(terminal_mac,cnt_ip,response);
						//===============================================================================
					}else{
							String [] revs=rev.split("%");
							
							if(null!=rev&&rev.length()>2){
								
			////					 rev==lv0005#*#000000000000#*#lv0005_OK#*#
								String cnt_cmd="",cnt_cmdstatus="",programurl="",cnt_cmdResult="";
								
								cnt_cmd=revs[0];   //命令
								terminal_mac=revs[1];      //MAC地址
								cnt_cmdstatus=revs[2];   ////返回状态
								
								String cntname=(null==FirstStartServlet.terminalMap.get(terminal_mac))?"null*":FirstStartServlet.terminalMap.get(terminal_mac).getCnt_name();
								logger.info(new StringBuffer().append("终端【").append(cntname).append(":").append(cnt_ip).append("_").append(terminal_mac).append("】HTTP访问命令==[终端返回]==").append(rev).toString());
								  
								//FirstStartServlet.client_ip_port_map.put(mac, pd.getSocketAddress().toString().substring(1) );
								String ipmac=new StringBuffer().append(cnt_ip).append("_").append(terminal_mac).toString();
								String client_result_states_key=new StringBuffer().append(ipmac).append("_").append(cnt_cmd).toString();
								
							   if(rev.startsWith("lv0009")){  //下发节目
								   
								  logger.info(new StringBuffer().append("接收lv0009 的字串-->").append(rev).toString());
								  if(revs.length<4){
									  return null;
								  }
								  programurl=revs[3];
								  List<ProgramApp> programapplist=new ArrayList<ProgramApp>();
								  String[] sttmp=null;
								  sttmp=programurl.split("A");//lv0009%71CB365009A%lv0009_NO_MAC_XML%71CB365009A.xml ,此次的A字母，会出现分割不对
								  
								  if(sttmp.length>=2){
									  
									  Connection conn = new DBConnection().getConection();
									  
										String tempstring=sttmp[1];
										String []idstmp=((tempstring.indexOf("!")!=-1)?tempstring.split("!"):new String[]{tempstring});
										ProgramAppDAO programappdao=new ProgramAppDAO();
										for(int i=0,n=idstmp.length;i<n;i++){
											if(!idstmp[i].equals("")&&null!=idstmp[i]){
												if(Util.isNumeric(idstmp[i])){
											       programapplist.add(programappdao.getProgramAppByStr(conn,new StringBuffer().append("where id=").append(idstmp[i]).toString()));
												}else {
//													logger.info("TerminalServerAction,根据 id 查询<<"+idstmp[i]+">>,获取已发送节目出错，id不是整数");
												}
											}
										}
										String cnt_programurl=sttmp[0].split("!")[0];
										String [] cnt_programid=sttmp[1].split("!");
										////////////////*************更新终端下载节目状态
										Map<String,CntResponse> map=FirstStartServlet.downloadProgramStatus;
										CntResponse cntrp= new CntResponse();
										cntrp.setCnt_ip(cnt_ip);
										cntrp.setCnt_cmd(cnt_cmd);
										cntrp.setCnt_cmdstatus(cnt_cmdstatus);
										cntrp.setCnt_cmdresult(cnt_cmdResult);
										cntrp.setCnt_programurl(cnt_programurl);
										cntrp.setCnt_mac(terminal_mac);
										map.put(cnt_ip+"_"+terminal_mac,cntrp);
										FirstStartServlet.downloadProgramStatus=map;  //存放客户端下载节目的状态
									
										if(cnt_cmdstatus.equals("lv0009_OK")){
											 ProgramApp programapp;
											 logsdao.addlogs1(conn,"system", new StringBuffer().append("终端【").append(TerminalDAO.getTerminalNameByMAC(terminal_mac)).append("】下载节目成功").toString(), 1);
											 ManagerProjectDao managerprojectdao=new ManagerProjectDao();
											 for(int i=0,n=programapplist.size();i<n;i++){
												 programapp=programapplist.get(i);
												 managerprojectdao.updateProjectIsSend(conn,2,1,programapp.getProgram_jmurl(),terminal_mac);//  把值改成 2 表示 下载成功 ,0 表示今天和今天以后的未发送的节目，
											 }
											 for(int i=0;i<cnt_programid.length;i++){
												 String sql=new StringBuffer().append("update xct_JMApp set program_sendok_terminal=REPLACE(program_sendok_terminal,'#").append(terminal_mac).append("','')+'#").append(terminal_mac).append("' where id=").append(cnt_programid[i]).toString();
												 programappdao.UpdateSendState(conn, sql);
											 }
										}else{
											 logsdao.addlogs1(conn,"system", new StringBuffer().append("终端【").append(TerminalDAO.getTerminalNameByIp(cnt_ip,terminal_mac)).append("】下载节目失败。(失败原因：").append(cntrp.getCnt_cmdresultZh().replace("<img src='/images/error.gif'  height='16px'>", "").replace("'","")).append(")").toString(), 1);
											 ManagerProjectDao managerprojectdao=new ManagerProjectDao();
											 managerprojectdao.updateProjectIsSend(conn,3,1,cnt_programurl,cnt_ip);//  把值改成 3 表示 下载失败
											 for(int i=0;i<cnt_programid.length;i++){
												new UtilDAO().deleteinfo(conn,"program_senduser", cnt_programid[i], "xct_JMhistory");
												new ReadMacXmlUtils().deleteMacXml(terminal_mac, cnt_programid[i]);
											 }
											// ProgramApp programapp= programappdao.getProgramAppByStr(" where ");
										}
                                        if(null!=conn){
										   conn.close();
										}
										ConstantUtil.changeClientCmd(ipmac,"lv0009");
		//								allsend(terminal_mac,cnt_ip,"lv_0009");
									 }else {
//										 logger.info("返回的下发节目的ID组合【A】长度小于2,=>"+programurl);
									 }
							  }else if(rev.startsWith("lv0028")){// 查看节目单
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
									
							  }else if(rev.startsWith("lv0011")){///截屏
									cnt_cmd=revs[0];   //命令
									cnt_cmdstatus=revs[2];   ////返回状态
									FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
									ConstantUtil.changeClientCmd(ipmac,"lv0011");
									
		//							if(!System.getProperty("SENDTYPE").equals(ConstantUtil.PNP))
		//							   allsend(terminal_mac,cnt_ip,"lv_0011");
									
							  }else if(rev.startsWith("inglv0009")){///开始下载节目
									cnt_cmd=revs[0];   //命令
									cnt_cmdstatus=revs[2];   ////返回状态
		//								System.out.println(cnt_ip+"_"+cnt_cmd+"________"+cnt_cmdstatus);
									FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
									ConstantUtil.changeClientCmd(ipmac,"lv0009");
//									allsend(terminal_mac,cnt_ip,"inglv_0009");
			   					
							  }else if(rev.startsWith("inglv_0009")){
									
			   					   ConstantUtil.changeClientCmd(ipmac,"inglv_0009");
									
							  }else if(rev.startsWith("lv0023")){//修改其他设置
								cnt_cmd=revs[0];   //命令
								cnt_cmdstatus=revs[2];   ////返回状态
								FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	
								ConstantUtil.changeClientCmd(ipmac,"lv0023");
		//						allsend(terminal_mac,cnt_ip,"lv_0023");
								
							  }else if(rev.startsWith("lv0012")){//休眠
								cnt_cmd=revs[0];   //命令
								cnt_cmdstatus=revs[2];   ////返回状态
								FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	 
								Terminal terminal= FirstStartServlet.terminalMap.get(terminal_mac);
								terminal.setCnt_playstyle("CLOSE");
								terminal.setCnt_nowProgramName("无");
								FirstStartServlet.terminalMap.put(terminal_mac, terminal);
								ConstantUtil.changeClientCmd(ipmac,"lv0012");
		//						allsend(terminal_mac,cnt_ip,"lv_0012");
								
							  }else if(rev.startsWith("lv0013")){//停止休眠
								 cnt_cmd=revs[0];   //命令
								 cnt_cmdstatus=revs[2];   ////返回状态
								 FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
								 ConstantUtil.changeClientCmd(ipmac,"lv0013");
		//						 allsend(terminal_mac,cnt_ip,"lv_0013");
								
							  }else if(rev.startsWith("lv0014")){//时间同步
								  cnt_cmd=revs[0];   //命令
								  cnt_cmdstatus=revs[2];   ////返回状态
								  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	
								////////////////////////修改重启后终端状态
								  Terminal terminal= FirstStartServlet.terminalMap.get(terminal_mac);
								  terminal.setCnt_islink(3);
								  terminal.setCnt_nowProgramName("无");
								  terminal.setCnt_playstyle("CLOSE");
								  FirstStartServlet.terminalMap.put(terminal_mac, terminal);
								  ConstantUtil.changeClientCmd(ipmac,"lv0014");
		//						  allsend(terminal_mac,cnt_ip,"lv_0014");
								  
							  }else if(rev.startsWith("lv0005")){//重启
								  cnt_cmd=revs[0];   //命令
								  cnt_cmdstatus=revs[2];   ////返回状态
								  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	 
								  ////////////////////////修改重启后终端状态
								  Terminal terminal= FirstStartServlet.terminalMap.get(terminal_mac);
								  terminal.setCnt_islink(3);
								  terminal.setCnt_nowProgramName("无");
								  terminal.setCnt_playstyle("CLOSE");
								  FirstStartServlet.terminalMap.put(terminal_mac, terminal);
								  ConstantUtil.changeClientCmd(ipmac,"lv0005");
//								  allsend(terminal_mac,cnt_ip,"lv_0005");
								  
							  }else if(rev.startsWith("lv0007")){//关机
								  cnt_cmd=revs[0];   //命令
								  cnt_cmdstatus=revs[2];   ////返回状态
								  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
								  ////////////////////////修改关机后终端状态
								  Terminal terminal= FirstStartServlet.terminalMap.get(terminal_mac);
								  terminal.setCnt_islink(3);
								  terminal.setCnt_nowProgramName("无");
								  terminal.setCnt_playstyle("CLOSE");
								  FirstStartServlet.terminalMap.put(terminal_mac, terminal);
								  ConstantUtil.changeClientCmd(ipmac,"lv0007");
//								  allsend(terminal_mac,cnt_ip,"lv_0007");
							  }
							  
							  else if(rev.startsWith("inglv0036")){///更新终端节目 开始下载节目
									cnt_cmd=revs[0];   //命令
									cnt_cmdstatus=revs[2];   ////返回状态
									FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
									ConstantUtil.changeClientCmd(ipmac,"lv0036");
//									allsend(terminal_mac,cnt_ip,"inglv_0036");
									
							  }else if(rev.startsWith("inglv_0036")){
									
			   					   ConstantUtil.changeClientCmd(ipmac,"inglv_0036");
									
							  }else if(rev.startsWith("lv0036")){//更新终端节目
								    cnt_cmd=revs[0];   //命令
									cnt_cmdstatus=revs[2];   ////返回状态
									FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	
									ConstantUtil.changeClientCmd(ipmac,"inglv0036");
		//						    allsend(terminal_mac,cnt_ip,"lv_0036");
							  }
							   
							   ///////////////////沙特馆新增LED屏开关功能
							  else if(rev.startsWith("lvled01")){//开启LED
								    cnt_cmd=revs[0];   //命令
									cnt_cmdstatus=revs[2];   ////返回状态
									FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	
									ConstantUtil.changeClientCmd(ipmac,"lvled01");
		//						    allsend(terminal_mac,cnt_ip,"lv_led01");
								  
							  }else if(rev.startsWith("lvled02")){//更新终端节目
								    cnt_cmd=revs[0];   //命令
									cnt_cmdstatus=revs[2];   ////关闭LED
									FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	
									ConstantUtil.changeClientCmd(ipmac,"lvled02");
		//						    allsend(terminal_mac,cnt_ip,"lv_led02");
								  
							  }else if(rev.startsWith("lvled03")){//LED文字通知
								  cnt_cmd=revs[0];   //命令
								  cnt_cmdstatus=revs[2];   ////返回状态
								  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
								  ConstantUtil.changeClientCmd(ipmac,"lvled03");
		//						  allsend(terminal_mac,cnt_ip,"lv_led03");   
							  }
							   
							  else if(rev.startsWith("inglv0101")){///发送点播节目 开始下载节目
									cnt_cmd=revs[0];   //命令
									cnt_cmdstatus=revs[2];   ////返回状态
									FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
									ConstantUtil.changeClientCmd(ipmac,"inglv0101");
//									allsend(terminal_mac,cnt_ip,"inglv_0101");
									
							  }else if(rev.startsWith("inglv_0101")){
									
			   					   ConstantUtil.changeClientCmd(ipmac,"inglv_0101");
									
							  }else if(rev.startsWith("lv0101")){//发送点播节目
								    cnt_cmd=revs[0];   //命令
									cnt_cmdstatus=revs[2];   ////返回状态
									FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	 
									ConstantUtil.changeClientCmd(ipmac,"lv0101");
		//						    allsend(terminal_mac,cnt_ip,"lv_0101");
								  
							  }else if(rev.startsWith("lv0102")){//更新终端节目
								    cnt_cmd=revs[0];   //命令
									cnt_cmdstatus=revs[2];   ////返回状态
									FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
									ConstantUtil.changeClientCmd(ipmac,"lv0102");
		//						    allsend(terminal_mac,cnt_ip,"lv_0102");
							  }else if(rev.startsWith("inglv0037")){///发送默认节目 开始下载节目
									cnt_cmd=revs[0];   //命令
									cnt_cmdstatus=revs[2];   ////返回状态
									FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
									ConstantUtil.changeClientCmd(ipmac,"lv0037");
//									allsend(terminal_mac,cnt_ip,"inglv_0037");
									
							  }else if(rev.startsWith("inglv_0037")){
									
			   					   ConstantUtil.changeClientCmd(ipmac,"inglv_0037");
									
							  }else if(rev.startsWith("lv0037")){//发送默认节目
								  cnt_cmd=revs[0];   //命令
								  cnt_cmdstatus=revs[2];   ////返回状态
								  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
								  ConstantUtil.changeClientCmd(ipmac,"lv0037");
		//						  allsend(terminal_mac,cnt_ip,"lv_0037");
								
							  }else if(rev.startsWith("lv0018")){//软件升级
								  cnt_cmd=revs[0];   //命令
								  cnt_cmdstatus=revs[2];   ////返回状态
								  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);	 
			                        ///////////////////////修改重启后终端状态
								  Terminal terminal= FirstStartServlet.terminalMap.get(terminal_mac);
								  terminal.setCnt_islink(3);
								  terminal.setCnt_nowProgramName("无");
								  terminal.setCnt_playstyle("CLOSE");
								  FirstStartServlet.terminalMap.put(terminal_mac, terminal);
								  ConstantUtil.changeClientCmd(ipmac,"lv0018");
		//						  allsend(terminal_mac,cnt_ip,"lv_0018");
								  
							  }else if(rev.startsWith("lv0024")){//文字通知
								  cnt_cmd=revs[0];   //命令
								  cnt_cmdstatus=revs[2];   ////返回状态
								  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
								  ConstantUtil.changeClientCmd(ipmac,"lv0024");
		//						  allsend(terminal_mac,cnt_ip,"lv_0024");   
								  
							  }else if(rev.startsWith("lv0029")){//初始化
								  cnt_cmd=revs[0];   //命令
								  cnt_cmdstatus=revs[2];   ////返回状态
								  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
								  ConstantUtil.changeClientCmd(ipmac,"lv0029");
		//						  allsend(terminal_mac,cnt_ip,"lv_0029");      
								  
							  }else if(rev.startsWith("lv0025")){//删除某个节目
								  ConstantUtil.changeClientCmd(ipmac,"lv0025");
		//						  allsend(terminal_mac,cnt_ip,"lv_0025");    
								  
							  }else if(rev.startsWith("lv0019")){//增减音量
								  ConstantUtil. changeClientCmd(ipmac,"lv0019");
		//						  allsend(terminal_mac,cnt_ip,"lv_0019");    
								  
							  }else if(rev.startsWith("inglv0103")){//发送点播节目文件 开始下载节目
								  cnt_cmd=revs[0];   //命令
								  cnt_cmdstatus=revs[2];   ////返回状态
								  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
								  ConstantUtil.changeClientCmd(ipmac,"inglv0103");
//								  allsend(terminal_mac,cnt_ip,"inglv_0103");
									
							  }else if(rev.startsWith("lv0103")){//返回后发送点播节目
								  cnt_cmd=revs[0];   //命令
							      cnt_cmdstatus=revs[2];   ////返回状态
								  FirstStartServlet.client_result_states.put(client_result_states_key, cnt_cmdstatus);
								  ConstantUtil.changeClientCmd(ipmac,"lv0103");
		//						  allsend(terminal_mac,cnt_ip,"lv_0103");
								  
							  }else if(rev.startsWith("inglv_0103")){
			   					  ConstantUtil.changeClientCmd(ipmac,"inglv_0103");
							  }
							  allBackSend("0",/*rev,*/"成功",response);
						}else{
							 String mess=new StringBuffer().append("返回命令字串异常(TerminalServerAction)--->").append(terminal_mac).append("_").append(cnt_ip).toString();
							 allBackSend("1",/*mess,*/"返回命令字串异常",response);
							 logger.info(mess);
						}
			        }
				}
			}catch(Exception e){
				allBackSend("1",/*new StringBuffer().append(cnt_ip).append("_").append(e.getMessage()).toString(),*/"终端访问出错",response);
				logger.info(new StringBuffer().append("终端访问出错(TerminalServerAction)！--->").append(e.getMessage()).toString());
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	//终端访问后，返回终端指令
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
//		   						 logger.info("还未执行的指令%%%%%%%%%%-------3------>"+clientcmd);
//			   				  }
//				        }
				       //=====================================================================================
				    	
		   			   for (Map.Entry<String, ConcurrentHashSet<ClientCmd>> entry : ConstantUtil.CLIENTCMDMAP.entrySet()){
		   				   if(key.equals(entry.getKey())){
		   					   
			   				  Set<ClientCmd> clientcmdset=entry.getValue();
		  				      for(ClientCmd clientcmd:clientcmdset){
		  				    	  
			   					  if(clientcmd.getIscancel()==0&&clientcmd.getIsoperator()==0){//获取未取消的指令并且未执行的指令
			   						  out.print(clientcmd.getCmd());
			   						  String desString=DESPlusUtil.Get().decrypt(clientcmd.getCmd());
//			   						  logger.info(MessageFormat.format("***终端 {0}【获取】‘未取消且未执行’ 的指令(加密后)：{1},解密后：{2}", key,clientcmd.getCmd(),desString));
			   						  String cntname=(null==FirstStartServlet.terminalMap.get(terminal_mac))?"null*":FirstStartServlet.terminalMap.get(terminal_mac).getCnt_name();
			   						  logger.info(MessageFormat.format("***终端【{0},({1})】【获取】‘未取消且未执行’ 的指令：{2}",cntname, key,desString));
			   						  ConstantUtil.changeClientCmd(key,desString); //设置已经执行过的指令
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
	public void allBackSend(String code,/*String cmd ,*/String message,final HttpServletResponse response){ 
		PrintWriter out=null;
		try {
			out = response.getWriter();
			Gson gson = new Gson();
			CmsServiceInfo cmsserviceinfo = new CmsServiceInfo();
			cmsserviceinfo.setCode(code);
			cmsserviceinfo.setMessage(message);
//			cmsserviceinfo.getData().setCmd(cmd);
			String json=gson.toJson(cmsserviceinfo);
			out.print(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.close();
			}
		}
	}
	
	public void allsend(String terminal_mac,String cnt_ip,String s){ 
		FirstStartServlet.client.allsend(terminal_mac,cnt_ip, s);
	}
}