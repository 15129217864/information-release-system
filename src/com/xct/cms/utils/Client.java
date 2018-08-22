package com.xct.cms.utils;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.mina.util.ConcurrentHashSet;

import com.xct.cms.dao.LogsDAO;
import com.xct.cms.dao.ProgramAppDAO;
import com.xct.cms.dao.ProgramDAO;
import com.xct.cms.dao.TerminalDAO;
import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.ClientCmd;
import com.xct.cms.domin.CntResponse;
import com.xct.cms.domin.ProgramApp;
import com.xct.cms.domin.Terminal;
import com.xct.cms.domin.UdpNatClient;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.xy.dao.ManagerProjectDao;

public class Client extends Thread {
	
	Logger logger = Logger.getLogger(Client.class);
	private DatagramSocket ds;
	private int in;
	private int out;
	private boolean killboolean;
	LogsDAO logsdao= new LogsDAO();
	File udpnatfile=null;
	
	public Client() { //�鿴�˿� netstat -an 101.226.249.85
		in=10003;
		out=10001;
		killboolean=true;
		this.setName("XCTWEB-UDP");
		initSocket();
		this.setPriority(Thread.MAX_PRIORITY);
		udpnatfile=new File(System.getProperty("XCT_UDPNATPRINT_FILE"));
		this.start();
	}
	public DatagramSocket getDatagramSocket(){
		return ds;
	}
	private void initSocket() {
		try {
			ds = new DatagramSocket(in);
		} catch (SocketException e) {
			logger.info("��ʼ��UDP�˿�10003���� ====="+e.getMessage());
			e.printStackTrace();
		}
	}
	private void read() {
		
		if(null!=ds){
			byte[] db = new byte[1024];
			String rev = "";
			DatagramPacket pd = new DatagramPacket(db, db.length);//���ݰ�
			try {
				ds.receive(pd);
			} catch (Exception e) {
				logger.info("��ȡ���ݰ�����=ds.receive(pd)===="+e.getMessage());
			}
			rev = new String(db, 0, pd.getLength());
			
			//System.out.println("receiver server message as:" + rev);
			//System.out.println("pd.getAddress()----------> "+pd.getAddress().getHostAddress());
			
			 if(!"".equals(rev)&&null!=pd){
			    if(rev.startsWith("udpnat_")){//UDP NAT ���ռ���Ϣ 
					String [] revs=rev.split("_");
					String socktinfo=pd.getSocketAddress().toString().substring(1);
					String [] socketarray=socktinfo.split(":");
					if(udpnatfile.exists()){
					  logger.info(MessageFormat.format("rev*******[udpnat]**********>{0}__{1}", rev,socktinfo));
					}
					String mac=revs[1];
					if(null!=mac){
					  ConstantUtil.UDPNATMAP
					            .put(mac, 
					            		new UdpNatClient(socketarray[0],mac,socketarray[1]));
					}
			    }else if(rev.startsWith("backudpnat_")){// ����˷���UDPָ��󣬽��ܷ���ָ�� //���� backudpnat_%86F8E77E2796%lv0028__101.81.238.204:21276
			    	String [] revs=rev.split("%");
			    	String socktinfo=pd.getSocketAddress().toString().substring(1);
					String [] socketarray=socktinfo.split(":");
					logger.info(MessageFormat.format("rev*******[backudpnat]**����ָ��󷵻ص�ָ��********>{0}__{1}", rev,socktinfo));
					String mac=revs[1];
					if(null!=mac){
					  ConstantUtil.UDPNATMAP.put(mac, new UdpNatClient(socketarray[0],mac,socketarray[1]));
					}
				}else{
			        receiver(pd,rev); //��������
				}
			 }
		}
	}
	
    Map<String,String>map=new HashMap<String,String>();
    
	public void receiver(DatagramPacket pd,String rev) {
		
		//System.out.println("����ǰ"+rev);
	   rev= DESPlusUtil.Get().decrypt(rev);   ///����UDP
		  // System.out.println("���ܺ�"+rev);
		
	   if(rev.startsWith("lv")||rev.startsWith("inglv")||rev.startsWith("supercommand")){  //�·���Ŀ
//		  String cnt_ip=pd.getAddress().getHostAddress();
		  String cnt_ip=pd.getSocketAddress().toString().substring(1).split(":")[0]; // pd.getSocketAddress()=> /192.168.10.179:1616
		  
		  if(rev.startsWith("supercommand")){
				//logger.info("�ն�"+cnt_ip+"����=>:rev============"+rev);
				  ////supercommand%0026220B7733%PLAYER%Ĭ�Ͻ�Ŀ%Ĭ�Ͻ�Ŀ#default#2011-05-26 18:27:36#LOOP#1#n%
				  ////supercommand%0026220B7733%PLAYER%Ĭ�Ͻ�Ŀ$$$Ĭ�Ͻ�Ŀ#default#2011-05-26 18:27:36#LOOP#1#n%
				String terminal_mac="",terminal_status="",playName="",desplayProjecttring="";
				String [] revs=rev.split("%");
				
				if(revs[1].indexOf("#")>-1){
					terminal_mac=revs[1].replace("#", "_").split("_")[0];   //MAC
				}else{
					terminal_mac=revs[1];   //MAC
				}
				terminal_status=revs[2];      //MAC��ַ
				playName=revs[3];
				desplayProjecttring=revs[4];
				
				if(null!=terminal_mac&&null!= pd.getSocketAddress()){
					//���UDP����Ϣ
					FirstStartServlet.client_ip_port_map.put(terminal_mac, pd.getSocketAddress().toString().substring(1));
					
					int bool=(null==terminal_mac||null==FirstStartServlet.terminalMap.get(terminal_mac))?0:1;//new TerminalDAO().checkIsTerminal(terminal_mac,cnt_ip);
					/*if("192.168.10.107".equals(terminal_ip)){
						System.out.println("�ն˷���==="+terminal_ip +"bool="+bool);
					}*/
					if(bool==1){
						Map<String,Terminal> terminals=FirstStartServlet.terminalMap;
						Terminal terminal=terminals.get(terminal_mac);
						terminal.setCnt_ip(cnt_ip);
						terminal.setCnt_nowProgramName(playName);
						terminal.setCnt_playstyle(terminal_status);
						terminal.setCnt_islink(1);
						terminal.setCnt_playprojecttring(desplayProjecttring);
						terminal.setSend_type("UDP");
						terminals.put(terminal_mac, terminal);
						FirstStartServlet.terminalMap.put(terminal_mac, terminal);
					}else if(bool==0){
						
						Connection conn = new DBConnection().getConection();
						UtilDAO utildao= new UtilDAO();
						String nowtime=UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
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
						
						utildao.saveinfo(conn,map, "xct_ipaddress");
						if(null!=conn){
							try {
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
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
						terminal.setSend_type("UDP");
						FirstStartServlet.terminalMap.put(terminal_mac, terminal);
					}
				     map.put(pd.getAddress().getHostAddress(), cnt_ip);
				     
				}else{
//					System.out.println("*******terminal_mac****��"+cnt_ip+"��***is "+terminal_mac);
//					System.out.println("*******pd.getSocketAddress()******��"+cnt_ip+"��**is "+pd.getSocketAddress());
				}
		   }else{
					//System.out.println("�ն�"+cnt_ip+"����=>:rev============"+rev);
				logger.info("�նˡ�"+cnt_ip+"��UDP��������============"+rev);
	////			 rev==lv0005#*#000000000000#*#lv0005_OK#*#
				String cnt_cmd="",mac="",cnt_cmdstatus="",programurl="",cnt_cmdResult="";
				String [] revs=rev.split("%");
				cnt_cmd=revs[0];   //����
				mac=revs[1];      //MAC��ַ
				cnt_cmdstatus=revs[2];   ////����״̬
				  
				FirstStartServlet.client_ip_port_map.put(mac, pd.getSocketAddress().toString().substring(1) );
				
			   if(rev.startsWith("lv0009")){  //�·���Ŀ
					  if(revs.length<4){
						  return;
					  }
					  programurl=revs[3];
					  List<ProgramApp> programapplist=new ArrayList<ProgramApp>();
					  String[] sttmp=null;
					  sttmp=programurl.split("A");
						String tempstring=sttmp[1];
						String []idstmp=((tempstring.indexOf("!")!=-1)?tempstring.split("!"):new String[]{tempstring});
						ProgramAppDAO programappdao=new ProgramAppDAO();
						
						Connection conn =new DBConnection().getConection();
						
						for(int i=0,n=idstmp.length;i<n;i++){
							if(!idstmp[i].equals("")&&null!=idstmp[i])
							  programapplist.add(programappdao.getProgramAppByStr(conn,"where id="+idstmp[i]));
						}
					  
						String cnt_programurl=sttmp[0].split("!")[0];
						String [] cnt_programid=sttmp[1].split("!");
						
						ProgramDAO programdao= new ProgramDAO();
						ManagerProjectDao managerprojectdao=new ManagerProjectDao();
						////////////////*************�����ն����ؽ�Ŀ״̬
						
						Map<String,CntResponse> map=FirstStartServlet.downloadProgramStatus;
						CntResponse cntrp= new CntResponse();
						cntrp.setCnt_ip(cnt_ip);
						cntrp.setCnt_cmd(cnt_cmd);
						cntrp.setCnt_cmdstatus(cnt_cmdstatus);
						cntrp.setCnt_cmdresult(cnt_cmdResult);
						cntrp.setCnt_programurl(cnt_programurl);
						map.put(cnt_ip,cntrp);
						FirstStartServlet.downloadProgramStatus=map;
						//////////////////////*************
						if(cnt_cmdstatus.equals("lv0009_OK")){
							 ProgramApp programapp;
							 logsdao.addlogs1(conn,"system", "�նˡ�"+TerminalDAO.getTerminalNameByIp(cnt_ip,mac)+"�����ؽ�Ŀ�ɹ�", 1);
							 for(int i=0,n=programapplist.size();i<n;i++){
								 programapp=programapplist.get(i);
								 managerprojectdao.updateProjectIsSend(conn,2,1,programapp.getProgram_jmurl(),cnt_ip);//  ��ֵ�ĳ� 2 ��ʾ ���سɹ� ,0 ��ʾ����ͽ����Ժ��δ���͵Ľ�Ŀ��
							 }
							 for(int i=0;i<cnt_programid.length;i++){
								 String sql=" update xct_JMApp set program_sendok_terminal=program_sendok_terminal+'#"+cnt_ip+"' where id="+cnt_programid[i];
								 programappdao.UpdateSendState(conn, sql);
							 }
						}else{
							 logsdao.addlogs1(conn,"system", "�նˡ�"+TerminalDAO.getTerminalNameByIp(cnt_ip,mac)+"�����ؽ�Ŀʧ�ܡ�(ʧ��ԭ��"+cntrp.getCnt_cmdresultZh().replace("<img src='/images/error.gif'  height='16px'>", "").replace("'","")+")", 1);
							 managerprojectdao.updateProjectIsSend(conn,3,1,cnt_programurl,cnt_ip);//  ��ֵ�ĳ� 3 ��ʾ ����ʧ��
							 for(int i=0;i<cnt_programid.length;i++){
								new UtilDAO().deleteinfo(conn,"program_senduser", cnt_programid[i], "xct_JMhistory");
								new ReadMacXmlUtils().deleteMacXml(mac, cnt_programid[i]);
							 }
							// ProgramApp programapp= programappdao.getProgramAppByStr(" where ");
						}
						//=====================================================
						if(null!=conn){
							try {
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						//=====================================================
						allsend(mac,cnt_ip,"lv_0009");
					    
				  }else if(rev.startsWith("lv0028")){// �鿴��Ŀ��
					    cnt_cmdResult=revs[3];
						CntResponse cntr= new CntResponse();
						cntr.setCnt_ip(cnt_ip);
						cntr.setCnt_cmd(cnt_cmd);
						cntr.setCnt_cmdstatus(cnt_cmdstatus);
						cntr.setCnt_cmdresult(cnt_cmdResult);
						cntr.setCnt_programurl(programurl);
						cntr.setCnt_ip(cnt_ip);
						FirstStartServlet.client_program_menu.put(cnt_ip+"_"+cnt_cmd, cntr);
						allsend(mac,cnt_ip,"lv_0028");
				  }else if(rev.startsWith("lv0011")){///����
					cnt_cmd=revs[0];   //����
					cnt_cmdstatus=revs[2];   ////����״̬
					FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);
					allsend(mac,cnt_ip,"lv_0011");
				  }else if(rev.startsWith("inglv0009")){///��ʼ���ؽ�Ŀ
					cnt_cmd=revs[0];   //����
					cnt_cmdstatus=revs[2];   ////����״̬
					FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);
					allsend(mac,cnt_ip,"inglv_0009");
				  }else if(rev.startsWith("lv0023")){//�޸���������
					cnt_cmd=revs[0];   //����
					cnt_cmdstatus=revs[2];   ////����״̬
					FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);	 
					allsend(mac,cnt_ip,"lv_0023");
				  }else if(rev.startsWith("lv0012")){//����
					cnt_cmd=revs[0];   //����
					cnt_cmdstatus=revs[2];   ////����״̬
					FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);	 
					allsend(mac,cnt_ip,"lv_0012");
					Terminal terminal= FirstStartServlet.terminalMap.get(mac);
					terminal.setCnt_playstyle("CLOSE");
					terminal.setCnt_nowProgramName("��");
					FirstStartServlet.terminalMap.put(mac, terminal);
				  }else if(rev.startsWith("lv0013")){//ֹͣ����
					cnt_cmd=revs[0];   //����
					cnt_cmdstatus=revs[2];   ////����״̬
					FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);	 
					allsend(mac,cnt_ip,"lv_0013");
				  }else if(rev.startsWith("lv0014")){//ʱ��ͬ��
					cnt_cmd=revs[0];   //����
					cnt_cmdstatus=revs[2];   ////����״̬
					FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);	 
					allsend(mac,cnt_ip,"lv_0014");
		////////////////////////�޸��������ն�״̬
					  Terminal terminal= FirstStartServlet.terminalMap.get(mac);
					  terminal.setCnt_islink(3);
					  terminal.setCnt_nowProgramName("��");
					  terminal.setCnt_playstyle("CLOSE");
					  FirstStartServlet.terminalMap.put(mac, terminal);
				  }else if(rev.startsWith("lv0005")){//����
					  cnt_cmd=revs[0];   //����
						cnt_cmdstatus=revs[2];   ////����״̬
						FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);	 
					  allsend(mac,cnt_ip,"lv_0005");
					  
					  ////////////////////////�޸��������ն�״̬
					  Terminal terminal= FirstStartServlet.terminalMap.get(mac);
					  terminal.setCnt_islink(3);
					  terminal.setCnt_nowProgramName("��");
					  terminal.setCnt_playstyle("CLOSE");
					  FirstStartServlet.terminalMap.put(mac, terminal);
					  
					  
				  }else if(rev.startsWith("lv0007")){//�ػ�
					  cnt_cmd=revs[0];   //����
						cnt_cmdstatus=revs[2];   ////����״̬
						FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);	 
					  allsend(mac,cnt_ip,"lv_0007");
					  ////////////////////////�޸Ĺػ����ն�״̬
					  Terminal terminal= FirstStartServlet.terminalMap.get(mac);
					  terminal.setCnt_islink(3);
					  terminal.setCnt_nowProgramName("��");
					  terminal.setCnt_playstyle("CLOSE");
					  FirstStartServlet.terminalMap.put(mac, terminal);
				  }
				   ///////////////////ɳ�ع�����LED�����ع���
				  else if(rev.startsWith("lvled01")){//����LED
					  cnt_cmd=revs[0];   //����
						cnt_cmdstatus=revs[2];   ////����״̬
						FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);	 
					  allsend(mac,cnt_ip,"lv_led01");
				  }else if(rev.startsWith("lvled02")){//�����ն˽�Ŀ
					  cnt_cmd=revs[0];   //����
						cnt_cmdstatus=revs[2];   ////�ر�LED
						FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);	 
					  allsend(mac,cnt_ip,"lv_led02");
				  }else if(rev.startsWith("lvled03")){//LED����֪ͨ
					  cnt_cmd=revs[0];   //����
					  cnt_cmdstatus=revs[2];   ////����״̬
					FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);	 
					  allsend(mac,cnt_ip,"lv_led03");   
				  }
				   
				   //////////////////////
				  
				  else if(rev.startsWith("inglv0036")){///�����ն˽�Ŀ ��ʼ���ؽ�Ŀ
						cnt_cmd=revs[0];   //����
						cnt_cmdstatus=revs[2];   ////����״̬
						FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);
						allsend(mac,cnt_ip,"inglv_0036");
				  }else if(rev.startsWith("lv0036")){//�����ն˽�Ŀ
					  cnt_cmd=revs[0];   //����
						cnt_cmdstatus=revs[2];   ////����״̬
						FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);	 
					  allsend(mac,cnt_ip,"lv_0036");
				  }else if(rev.startsWith("inglv0037")){///����Ĭ�Ͻ�Ŀ ��ʼ���ؽ�Ŀ
						cnt_cmd=revs[0];   //����
						cnt_cmdstatus=revs[2];   ////����״̬
						FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);
						allsend(mac,cnt_ip,"inglv_0037");
				  }else if(rev.startsWith("lv0037")){//����Ĭ�Ͻ�Ŀ
					cnt_cmd=revs[0];   //����
					cnt_cmdstatus=revs[2];   ////����״̬
					FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);	 
					allsend(mac,cnt_ip,"lv_0037");
				  }else if(rev.startsWith("lv0018")){//�������
					cnt_cmd=revs[0];   //����
					cnt_cmdstatus=revs[2];   ////����״̬
					FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);	 
					allsend(mac,cnt_ip,"lv_0018");
		////////////////////////�޸��������ն�״̬
					  Terminal terminal= FirstStartServlet.terminalMap.get(mac);
					  terminal.setCnt_islink(3);
					  terminal.setCnt_nowProgramName("��");
					  terminal.setCnt_playstyle("CLOSE");
					  FirstStartServlet.terminalMap.put(mac, terminal);
				  }else if(rev.startsWith("lv0024")){//����֪ͨ
					  cnt_cmd=revs[0];   //����
					  cnt_cmdstatus=revs[2];   ////����״̬
					FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);	 
					  allsend(mac,cnt_ip,"lv_0024");   
				  }else if(rev.startsWith("lv0029")){//��ʼ��
					  cnt_cmd=revs[0];   //����
					  cnt_cmdstatus=revs[2];   ////����״̬
					  FirstStartServlet.client_result_states.put(cnt_ip+"_"+cnt_cmd, cnt_cmdstatus);	 
					  allsend(mac,cnt_ip,"lv_0029");      
				  }else if(rev.startsWith("lv0025")){//ɾ��ĳ����Ŀ
					  allsend(mac,cnt_ip,"lv_0025");    
				  }else if(rev.startsWith("lv0019")){//��������
					  allsend(mac,cnt_ip,"lv_0019");    
				  }
			   }
		}
	}
	
	public void allsend(String mac,String cnt_ip,String cmd){ // ��������  
		
		Map<String,Terminal> terminals=FirstStartServlet.terminalMap;
		String send_typetmp="";
		try{
			 for(Map.Entry<String, Terminal>enty: terminals.entrySet()){
				Terminal t=enty.getValue();
				if(null!=t){
					String send_type=t.getSend_type();
					send_type=(null==send_type?"UDP":send_type);
					
					if(ConstantUtil.HTTP.equals(send_type)){ //������ר��
						send_typetmp=send_type;
//						if(cnt_ip.equals(t.getCnt_ip())){
						if(mac.equals(t.getCnt_mac())){
							cmd=UrlEncodeUtil.englishSysCharTo(cmd);
							String message=MessageFormat.format("���նˡ�{0}������{1}����=====[HTTP]======={2}",cnt_ip,send_type,cmd);
							logger.info(message);
							cmd= DESPlusUtil.Get().encrypt(cmd);   ///�������
							sendHTTP(mac,cnt_ip,cmd);
							break;
						}
					}else if(ConstantUtil.UDP.equals(send_type)){//���� �� ����������
						send_typetmp=send_type;
						if(null!=ConstantUtil.UDPNATMAP.get(mac)){
							UdpNatClient udpnatclient=ConstantUtil.UDPNATMAP.get(mac);
							String message=MessageFormat.format("���նˡ�{0} {1} {2}������{3}����=====[UDP]======={4}",
									                  udpnatclient.getIp(),udpnatclient.getPort(),udpnatclient.getMac(),send_type,cmd);
							logger.info(message);
							cmd= DESPlusUtil.Get().encrypt(cmd);
							sendUDP2(mac, cmd);
	//						FirstStartServlet.client.sendUDP(t.getCnt_mac(), s); //2016��11��֮ǰʹ��
							break;
						}
					}else if(ConstantUtil.PNP.equals(send_type)){//�����ã����ǲ���ָ�ֻ����ָ��ȴ��ն�����ȡָ��
						if(mac.equals(t.getCnt_mac())){
						    String key=new StringBuffer().append(cnt_ip).append("_").append(mac).toString();
						    String cmdtmp= DESPlusUtil.Get().encrypt(cmd);
						    
							if(null!=ConstantUtil.CLIENTCMDMAP){
							   if(null!=ConstantUtil.CLIENTCMDMAP.get(key)){
		   						    ConstantUtil.CLIENTCMDMAP.get(key).add(new ClientCmd(key, cmdtmp, 0,0,System.currentTimeMillis()));
						       }else{
						    	   ConcurrentHashSet<ClientCmd>set=new ConcurrentHashSet<ClientCmd>();
								   set.add(new ClientCmd(key, cmdtmp, 0,0,System.currentTimeMillis()));
								   ConstantUtil.CLIENTCMDMAP.put(key,set);
							   }
//							   String message=MessageFormat.format("^^^���ն� {0}�����桿����,�ȴ��ն˻�ȡ===[PNP]==>����ǰ��{1},���ܺ�{2}",key,cmd,cmdtmp);
							   String cntname=(null==FirstStartServlet.terminalMap.get(mac))?"null*":FirstStartServlet.terminalMap.get(mac).getCnt_name();
//							   String message=MessageFormat.format("^^^���ն� {0}�����桿����,�ȴ��ն˻�ȡ===[PNP]==>����ǰ��{1},���ܺ�{2}",key,cmd,cmdtmp);
							   String message=MessageFormat.format("^^^���նˡ� {0},{1}�������桿����,�ȴ��ն˻�ȡ===[PNP]==>��{2}",cntname,key,cmd);
							   logger.info(message);
							   
							   //===================//��ʼ������ɾ��ĳ����Ŀ����ʱ��ɾ���岥�����ڵı�ʶ����==========================
							   
							   if(cmd.startsWith("lv0025")){//lv0025@��ֵ׼��#20110510145654#2011-05-20 15:10:46#loop#56#y@y
								   String cmd_lv0025 = cmd.split("@")[1];
								   String program[] = cmd_lv0025.split("#");
								   if(program[3].equals("insert")){
									   String  []keyarr=key.split("_");// key = 223.166.241.148_CCCA960B52B
									   for(String macdir:Util.INSER_PROGRAM_LIST){
										   String []macdirarr=macdir.split("_"); //macdir = CCCA960B52B_20180623120000
										   if(macdirarr[0].equals(keyarr[1]) && macdirarr[1].equals(program[1])){
											   logger.info(MessageFormat.format("******lv0025***ɾ���岥��ʶ:{0}",macdir));
											   Util.INSER_PROGRAM_LIST.remove(macdir);
										   }
									   }
								   }
							   }
							   if(cmd.equals("lv0029")){//��ʼ������ʱ��ɾ���岥�����ڵı�ʶ����
								   String  []keyarr=key.split("_");// key = 223.166.241.148_CCCA960B52B
								   for(String macdir:Util.INSER_PROGRAM_LIST){
									   String []macdirarr=macdir.split("_"); //macdir = CCCA960B52B_20180623120000
									   if(macdirarr[0].equals(keyarr[1])){
										   logger.info(MessageFormat.format("******lv0029***ɾ���岥��ʶ:{0}",macdir));
										   Util.INSER_PROGRAM_LIST.remove(macdir);
									   }
								   }
							   }
							   
//							   for (Map.Entry<String, ConcurrentHashSet<ClientCmd>> entry : ConstantUtil.CLIENTCMDMAP.entrySet()){
//					   				  Set<ClientCmd> clientcmdset=entry.getValue();
//								      for(ClientCmd clientcmd:clientcmdset){
//				   						 logger.info("��δִ�е�ָ��%%%%%%%%%%-------1------>"+clientcmd);
//					   				  }
//						        }
					       }
						}
			       }
				}
			 }
		}catch(Exception e){
			if("HTTP".equals(send_typetmp)||"UDP".equals(send_typetmp)){
		    	logger.info("���նˡ�"+cnt_ip+"����������ʧ��======="+send_typetmp+"====="+cmd+"====����[allsend()]"+e.getMessage());
			}
			e.printStackTrace();
		}
	}
	
	public void sendHTTP(String mac,String cnt_ip,String s){   //����HTTP����
		
		String port=System.getProperty("CLENTPORT_HOME");
		String url = "";
		try{
			Map<String, String> map = new HashMap<String, String>();
			map.put("op", s);
			
			String version=FirstStartServlet.client_ip_version.get(new StringBuffer().append(cnt_ip.trim()).append("_").append(mac).toString());//��ð汾��
			String portmp=FirstStartServlet.client_ip_httpport.get(new StringBuffer().append(cnt_ip.trim()).append("_").append(mac).toString());//����ն��豸��http�˿ں�
			
			port=(null==portmp?port:portmp);
			url = new StringBuffer("http://").append(cnt_ip).append(port).append("/NoticeToClientOparator").toString();
			
			if(null!=version&&version.indexOf("android")!=-1){
//			   port=null==portmp?":8080":portmp;
			   url = new StringBuffer("http://").append(cnt_ip).append(port).append("/xctConfig/NoticeToClientOparator").toString();
			}
			HttpRequest.doPost(url, map, "gbk");
//			System.out.println(url+"/"+s);
//			logger.info(url+"/"+s);
		}catch(Exception e){
			logger.info("���նˡ�"+cnt_ip+"������HTTP����ʧ��==>"+url+"/"+s+" ===>"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void sendUDP(String mac,String s){  ///����UDP����
		
		String ip="";
		try {
			String ip_ports[]=FirstStartServlet.client_ip_port_map.get(mac).split(":");
			ip=ip_ports[0];
			int port=Integer.parseInt(ip_ports[1]);
			byte data[] = s.getBytes();
			DatagramPacket dp = new DatagramPacket(data, data.length);
				
			dp.setAddress(InetAddress.getByName(ip));
			dp.setPort(port);
			ds.send(dp);
		}catch (Exception e) {
			logger.info("���նˡ�"+ip+"������UDP����ʧ��============"+s+"===="+e.getMessage());
			e.printStackTrace();
		}
	}
	
    public void sendUDP2(String mac,String s){  ///����UDP����,UDP��
		
		String ip="";
		try {
			UdpNatClient udpnatclient=ConstantUtil.UDPNATMAP.get(mac);
			ip=udpnatclient.getIp();
			int port=Integer.parseInt(udpnatclient.getPort());
			byte data[] = s.getBytes();
			DatagramPacket dp = new DatagramPacket(data, data.length);
			dp.setAddress(InetAddress.getByName(ip));
			dp.setPort(port);
			ds.send(dp);
		}catch (Exception e) {
			logger.info("���նˡ�"+ip+"������UDP����ʧ��============"+s+"===="+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public  void StartLEDjar(String pama) {
			
			String edit = System.getProperty("java.home") + "/bin/java -jar "
					+ FirstStartServlet.projectpath + "led/LEDControl_SX.jar "+pama;
			try {
				System.out.println(edit);
					Process pro =Runtime.getRuntime().exec(edit);
					//���»ᵼ�¹��𣿣�
	/*				try {
						pro.waitFor();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}*/
			} catch (IOException e) {
				e.printStackTrace();
			}
	   }
	  
	

	public  void send(InetAddress ip, String s) {
		try {
			byte[] data = s.getBytes();
			DatagramPacket dp = new DatagramPacket(data, data.length);
			dp.setAddress(ip);
			dp.setPort(out);
			ds.send(dp);
		} catch (Exception e) {
			logger.info("send message is error!====="+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void allUDPsend(String temp,String s){ // UDP�㲥
//      String temp = "255.255.255.255";
		try {
			s= DESPlusUtil.Get().encrypt(s);   ///�������
			byte data[] = s.getBytes();
			DatagramPacket dp = new DatagramPacket(data, data.length);
			dp.setAddress(InetAddress.getByName(temp));
			dp.setPort(out);
			ds.send(dp);
		} catch (UnknownHostException e) {
			logger.info("==1==="+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("==2==="+e.getMessage());
			e.printStackTrace();
		}
}
	
	public void send(InetAddress ip, String s, int port) {
		try {
			byte[] data = s.getBytes();
			DatagramPacket dp = new DatagramPacket(data, data.length);
			dp.setAddress(ip);
			dp.setPort(port);
			ds.send(dp);

		} catch (Exception e) {
			logger.info("send message is error!====="+e.getMessage());
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			
			if(!isKillboolean())
				break;
			try {
				sleep(50);
			} catch (InterruptedException e) {
				logger.info("send message is error!====="+e.getMessage());
				e.printStackTrace();
			}
			read();
		}
	}

	public boolean isKillboolean() {
		return killboolean;
	}

	public void setKillboolean(boolean killboolean) {
		this.killboolean = killboolean;
	}

	public Map<String, String> getMap() {
		return map;
	}
}
