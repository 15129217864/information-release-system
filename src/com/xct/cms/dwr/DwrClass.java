package com.xct.cms.dwr;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.mina.util.ConcurrentHashSet;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;

import com.xct.cms.dao.LogsDAO;
import com.xct.cms.dao.MediaDAO;
import com.xct.cms.dao.ModuleDAO;
import com.xct.cms.dao.ProgramAppDAO;
import com.xct.cms.dao.ProgramDAO;
import com.xct.cms.dao.ProgramHistoryDAO;
import com.xct.cms.dao.TemplateDAO;
import com.xct.cms.dao.TerminalDAO;
import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.ClientCmd;
import com.xct.cms.domin.CntResponse;
import com.xct.cms.domin.Logs;
import com.xct.cms.domin.Media;
import com.xct.cms.domin.Module;
import com.xct.cms.domin.ProgramApp;
import com.xct.cms.domin.ProgramHistory;
import com.xct.cms.domin.Terminal;
import com.xct.cms.domin.Users;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.servlet.SessionListener;
import com.xct.cms.utils.ConstantUtil;
import com.xct.cms.utils.CreatMacXmlUtils;
import com.xct.cms.utils.DESPlusUtil;
import com.xct.cms.utils.DateUtils;
import com.xct.cms.utils.ProgramListCompareter;
import com.xct.cms.utils.UrlEncodeUtil;
import com.xct.cms.utils.UtilDAO;
import com.xct.cms.xy.dao.ManagerProjectDao;
import com.xct.cms.xy.domain.ClientProjectBean;

public class DwrClass {


	/** ��dwr��ȡsession,request,response */ 
//	WebContext webContext = WebContextFactory.get();  
//	HttpSession session = webContext.getSession(); 
//	HttpServletRequest request=webContext.getHttpServletRequest(); 
//	HttpServletResponse response=webContext.getHttpServletResponse(); 
	/**
	 * 
	 * �����ն�Ip��ַ���ͽ�Ŀ
	 * @param clientips
	 * @return
	 */

	String []ipsarray=null;
	Map<String,CntResponse> map=new HashMap<String, CntResponse>();
	private Map<String, String> map2 = new HashMap<String, String>();
	CntResponse cntrp=null;
//	String cntip="";
	String cntmac="";
	private String resip[];
	private String url = "";
	Logger logger = Logger.getLogger(DwrClass.class);
	Terminal terminal=null;
	private List<Terminal> allTerminalList= new ArrayList<Terminal>();
	String mac_home="";
	List<ProgramApp> programapplist=new ArrayList<ProgramApp>();
	ProgramApp programapptmp=null;
	CreatMacXmlUtils creatmacxmlutils=new CreatMacXmlUtils();
	ManagerProjectDao managerprojectdao=new ManagerProjectDao(); 
	
	String nowtime1="";
	String send_username="";
	String mac_strs="";
	
	
	
	public String getProgramNameByUrl(String op,String jmurl){
		ProgramDAO programdao = new ProgramDAO();
		if(op.equals("move_copy")){
				String[] urlarray=jmurl.split("!");
				StringBuffer stringBuffer=new StringBuffer();
				for (String string : urlarray) {
					if(!string.equals("")){
					  StringBuffer sb=new StringBuffer();
					  sb.append(string);
					  sb.insert(0, "'").append("',");
					  stringBuffer.append(sb);
					}
				}
				if(stringBuffer.indexOf(",")!=-1){
				   return programdao.getProgramNameByUrl(stringBuffer.substring(0, stringBuffer.length()-1));
				}
		}
		return "NO";
	}
	
	public String getTemplateNameById(String op,String ids){
		if(op.equals("copy")){
				String[] idsarray=ids.split("!");
				StringBuffer stringBuffer=new StringBuffer();
				for (String string : idsarray) {
					if(!string.equals("")){
					  StringBuffer sb=new StringBuffer();
					  sb.append(string);
					  sb.insert(0, "'").append("',");
					  stringBuffer.append(sb);
					}
				}
				if(stringBuffer.indexOf(",")!=-1){
				   return new TemplateDAO().getTemplageNameByID(stringBuffer.substring(0, stringBuffer.length()-1));
				}
		}
		return "NO";
	}
	
	public Map<String, String> isSendProgramOk(String cnt_ips,String cmd){
		
		String []cntipArray=cnt_ips.split("#");
		Map<String,String> resultresponse= new HashMap<String, String>();
		
		Map<String,String> cnt_states = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : FirstStartServlet.client_result_states.entrySet()) {
			 cnt_states.put(entry.getKey(), entry.getValue());
		}
		//System.out.println("cntipArray==="+cntipArray);
		try{
			if(cntipArray!=null){
				for(int i=0,n=cntipArray.length;i<n;i++){
					//System.out.println("cntipArray[i]==="+cntipArray[i]);
					if(null!=cntipArray[i]&&!"".equals(cntipArray[i])){
						for (Map.Entry<String, String> entry : cnt_states.entrySet()){
							//System.out.println("entry==="+entry);
							if(entry!=null){
	//							System.out.println(entry.getKey()+"<++++++++++++++>"+entry.getValue());
	//							System.out.println(((cntipArray[i]+"_"+cmd)+"<==============>"+(cmd+"_OK")));
								if((cntipArray[i]+"_"+cmd).equals(entry.getKey())&&(cmd+"_OK").equals(entry.getValue())){
									
	//								System.out.println("cntipArray[i]********"+cntipArray[i]+"||cmd*****"+cmd);
									resultresponse.put(cntipArray[i], "OK");
									FirstStartServlet.client_result_states.remove(cntipArray[i]+"_"+cmd);
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.info("���ͽ�Ŀ����ʧ�ܣ�"+e.getMessage());
			e.printStackTrace();
		}
		return resultresponse;
	}
	
	public String restartXctService() { 
		
		File file=new File(FirstStartServlet.projectpath+"/dll/restart_xct.bat");
			if(file.exists()){
//				System.out.println("����XCT����----dwr------->"+file.getPath());
			try {
				Runtime.getRuntime().exec("cmd /c start  "+file.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			} 
			return "ok";
		  }
			return "error";
	}
	
	public void changeAlpha2(String type,String alphaString,String templateid) {
//		System.out.println(type+"__"+alphaString+"___"+templateid);
		new  ModuleDAO().updateModuleTempByTemplateIdForAlpha(type,alphaString,templateid);
	}
	public void changeAlpha(String moduleid,String type,String alphaString,String templateid) {
		new  ModuleDAO().updateModuleTempByTemplateIdForAlpha(moduleid,type,alphaString,templateid);
	}
	
	public String changeDayDownLoad(String flagString,String macstring){
		
		//System.out.println(flagString+"___"+macstring);
		if(new TerminalDAO().updateDayDownLoad(flagString, macstring)){
			Map<String,Terminal> terminals=FirstStartServlet.terminalMap;
			for (Map.Entry<String, Terminal> entry : terminals.entrySet()) {
				 terminal=entry.getValue();
				 if(macstring.equals(terminal.getCnt_mac())){
					 terminal.setIs_day_download(flagString);
					 break;
				 }
			}
		}
		return "ok";
	}

	public List<ClientProjectBean> checkProgramIsExist(String program_ids){
		
		DBConnection dbc=new DBConnection();
		Connection conn = dbc.getConection();
		List<ClientProjectBean>  clientprojectlist=new ArrayList<ClientProjectBean>();
		ProgramAppDAO programappdao= new ProgramAppDAO();
		ManagerProjectDao managerprojectdao=new ManagerProjectDao(); 
		//Mysql
		List<ProgramApp> list=programappdao.getALLProgramAppByStr(conn,"where   LOCATE(program_id,'"+program_ids+"')<>0 ");
		//SQLServer
		//List<ProgramApp> list=programappdao.getALLProgramAppByStr(conn,new StringBuffer().append("where CHARINDEX (program_id,'").append(program_ids).append("')<>0 ").toString());
		ClientProjectBean clientprojectbean=null;
		
		for(int j=0;j<list.size();j++){
			ProgramApp programapp=list.get(j);
			logger.info("...DwrClass.checkProgramIsExist-->"+programapp.getProgram_play_terminal());
	        String []macs= programapp.getProgram_play_terminal().split("#");
	        int type=programapp.getProgram_play_type();
	       
	        	if(type==0||type==3){// ÿ��ͬһ��Ŀֻ��һ�� ѭ����Ŀ������ѭ����Ŀ
	        		 for(int i=1,n=macs.length;i<n;i++){
	        			 clientprojectbean=managerprojectdao.getClientProectForLoop(conn,programapp.getProgram_jmurl(), programapp.getProgram_playdate(), programapp.getProgram_enddate(), macs[i], type,programapp.getProgram_play_typeZh());
	        			 if(null!=clientprojectbean)
	           			   clientprojectlist.add(clientprojectbean);
	        		 }
	        	}else if(type==1){//�岥��Ŀ���������ɾ����
	        		 for(int i=1,n=macs.length;i<n;i++){
	        			 clientprojectbean=managerprojectdao.getClientProectForInsert(conn,macs[i], 1,programapp.getProgram_play_typeZh());
	        			 if(null!=clientprojectbean)
		           			clientprojectlist.add(clientprojectbean);
	        		 }
        		}else{ // ��ʱ��Ŀ
        			 for(int i=1,n=macs.length;i<n;i++){
	        			 clientprojectbean=managerprojectdao.getClientActiveProject(conn,programapp.getProgram_playdate(), programapp.getProgram_enddate(), macs[i], 2);
	        			 if(null!=clientprojectbean)
		           			clientprojectlist.add(clientprojectbean);
        			 }
        	    }
		}
		dbc.returnResources(conn);
		return clientprojectlist;
	}
	
	
    public Map<String, String> sendProgram(String clientips,String username,String ids){
    	 FirstStartServlet.downloadProgramStatus.clear();// �ն����ؽ�Ŀ״̬����
    	  Map<String,String> resultresponse= new HashMap<String, String>();
    	
	      if(clientips.replaceAll("\\s+","").split("@").length>1){
			   try{
					DBConnection dbc=new DBConnection();
					Connection conn = dbc.getConection();
//					conn.setAutoCommit(false);   
	//				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY); 
					/////////////////////������ѭ��ʹ�õ�����
					send_username=username;
					
					//clientips=73@#192.168.10.181_485AB6DE26A7
					logger.info(new StringBuffer().append("...DwrClass.sendProgram....clientips:").append(clientips.replaceAll("\\s+","")).append("-->").append(ids).toString());
					
					TerminalDAO  terminaldao= new TerminalDAO();
					LogsDAO logsdao= new LogsDAO();
					
					allTerminalList=terminaldao.getALLTerminalDAO(conn,"");
					String programid=clientips.replaceAll("\\s+","").split("@")[0];
					
					nowtime1=UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
					String nowdate=UtilDAO.getNowtime("yyyy-MM-dd");
					String nowtime=UtilDAO.getNowtime("HH:mm")+":00";
					
					ProgramAppDAO programappdao= new ProgramAppDAO();
					ProgramApp programapp=programappdao.getProgramAppByStr(conn,new StringBuffer().append("where id=").append(programid).toString());
					
					String []idstmp=ids.split("!");
					for(int i=0,n=idstmp.length;i<n;i++){
						if(!idstmp[i].equals("")&&null!=idstmp[i]){
						  programapplist.add(programappdao.getProgramAppByStr(conn,new StringBuffer().append("where id=").append(idstmp[i]).toString()));
						}
					}
					
					ipsarray=clientips.replaceAll("\\s+","").split("@")[1].split("#");
					String sendstr=new StringBuffer().append("lv0009_").append(programapp.getProgram_jmurl()).append("!").append(programapp.getId()).append("A").append(ids).toString();
			//        System.out.println("sendstr=====================������ "+sendstr+"========"+programapplist.size());
					
					Map<String,Terminal> terminals=FirstStartServlet.terminalMap;
					
					mac_home=System.getProperty("MAC_HOME");
					 
					String sendips="";
					for(int i=1,z=ipsarray.length;i<z;i++){
						if(!"".equals(ipsarray[i])){
							
							if(pnpIsOperator(ipsarray[i]).equals("NO")){//�鿴�Ƿ���δ������ָ��
								resultresponse.put(ipsarray[i], "NOOP");
//								String isoperatoring="�ն˻���δִ��ָ�";
//								logsdao.addlogs1(conn,username, "�û���"+username+"�����նˡ�"+TerminalDAO.getTerminalNameByMAC(terminal.getCnt_mac())+"�� ����ָ�� "+isoperatoring, 1);
								continue;
						    }
							//==========================��鵱ǰ�ն��Ƿ�������=============================
							String []macip=ipsarray[i].split("_");//192.168.10.181_485AB6DE26A7
							int isdownload=0;
							for (Map.Entry<String, Terminal> entry : terminals.entrySet()){
								 terminal=entry.getValue();
								 if(macip[1].equals(terminal.getCnt_mac())
										 && "DOWNLOAD".equals(terminal.getCnt_playstyle())){
									 isdownload=1;
									 break;
								 }
							}
							if(isdownload==1){
								resultresponse.put(ipsarray[i], "DOWNLOAD");
								logger.info(new StringBuffer().append("--------��").append(ipsarray[i]).append(" ���ն˴�������״̬------------->DOWNLOAD").toString());
								continue;
							}
							//=========================================================================
							for(int j=0,m=allTerminalList.size();j<m;j++){
								terminal=allTerminalList.get(j);
								if(terminal.getCnt_mac().equals(macip[1]) &&null!=terminals.get(terminal.getCnt_mac())
										&& terminals.get(terminal.getCnt_mac()).getCnt_islink()!=3){//�ڻ����м�鲻�����ն�
									
									sleepNotice(200);
									//��ѯ���ݿ��Ƿ��д˼�¼���Ѳ�ѯ���ڵ�һ��
				//					if(managerprojectdao.getClientProect(conn,programapp.getProgram_playdate()+" "+programapp.getProgram_playtime(),ipsarray[i])==0){
										if(null!=programapplist && programapplist.size()>0){
											File macxmlfile= new File(new StringBuffer().append(mac_home).append(terminal.getCnt_mac()).append(".xml").toString());
											for(int k=0;k<programapplist.size();k++){
												programapptmp=programapplist.get(k);
												if(programapptmp.getProgram_play_type()==1){  ///�岥ʱ�����ݵ�ǰʱ��ͽ�Ŀʱ�����������Ŀ�Ĳ���ʱ��
													String[] newtime=UtilDAO.gettimeBytime(nowdate+" "+nowtime,programapptmp.getProgram_playlong()).split("#");
													programapptmp.setProgram_playdate(nowdate);
													programapptmp.setProgram_playtime(nowtime);
													programapptmp.setProgram_enddate(newtime[0]);
													programapptmp.setProgram_endtime(newtime[1]);
												}
		//										=================================================
												File macFile2=new File(macxmlfile.getPath());
												StringBuffer sb=new StringBuffer();
												sb.append(macFile2.getParent()).append(File.separator);
												
												if(new File(sb.append(programapptmp.getProgram_jmurl()).toString()).exists()){
													creatmacxmlutils.createProjectMacXml(macxmlfile.getPath(),terminal.getCnt_mac(),programapptmp.getId(), programapptmp.getProgram_name(), programapptmp.getProgram_jmurl(),programapptmp.getProgram_playdate()+" "+programapptmp.getProgram_playtime(), programapptmp.getProgram_enddate()+" "+programapptmp.getProgram_endtime(), programapptmp.getProgram_play_type(), programapptmp.getProgram_playlong()+"");///����ʱֻ����3���Ŀ��
													managerprojectdao.insertProjectParm(conn,/*stmt,*/programapptmp.getId(),programapptmp.getProgram_jmurl(), programapptmp.getProgram_name(),programapptmp.getProgram_playdate(), programapptmp.getProgram_playdate()+" "+programapptmp.getProgram_playtime(), programapptmp.getProgram_enddate()+" "+programapptmp.getProgram_endtime(),programapptmp.getProgram_enddate(), programapptmp.getProgram_playlong()+"", programapptmp.getProgram_play_type(), ipsarray[i],terminal.getCnt_mac(),1);
												}else{
													logger.info("--------��"+terminal.getCnt_ip()+"_"+terminal.getCnt_mac()+" ���ն���Ҫ���� �Ľ�Ŀ�ļ��в����� ---------"+sb.toString());
												}
											}
										}else{
											logger.info("--------��"+terminal.getCnt_ip()+"_"+terminal.getCnt_mac()+" ���ն�δ�ҵ���Ŀ���У��������� "+terminal.getCnt_mac()+".xml �ļ���");
										}
		//								System.out.println("terminal.getIs_day_download()--------DwrClass------>"+terminal.getIs_day_download());
										if( terminal.getIs_day_download().equals("0")){//�����첻���ص����
											resultresponse.put(ipsarray[i], "nodaydownload_save_ok");  
										}else{
											FirstStartServlet.client.allsend(terminal.getCnt_mac(),ipsarray[i].split("_")[0], sendstr);//����ָ��
											resultresponse.put(ipsarray[i], "ok");  //��ʱ����״̬ΪOK
										}
				//					}else{//������ڣ������·��ͣ���Ŀ���Ѿ��ڵ�һ�η���ʱ�����ɣ��˴�ֻ�����·����������
				//						String url = "http://" + ipsarray[i]+ "/NoticeToClientOparator";
				//						String result=HttpRequest.doPost(url, map, "gbk");
				//						resultresponse.put(ipsarray[i], result);
				//					}
								}else{
									//logger.info("--------��"+terminal.getCnt_ip()+"_"+terminal.getCnt_mac()+" ���ն˲����� ���� �ڻ����м�鲻�����ն�-------------");
								}
							}
							sendips+=new StringBuffer().append("#").append(ipsarray[i]).toString();
						}
					}
					UtilDAO utildao = new UtilDAO();
					Map paogrammap =new HashMap();
					String allprogram="";
					for(int k=0;k<programapplist.size();k++){
						programapptmp=programapplist.get(k);
						paogrammap= utildao.getMap();
						paogrammap.put("id", programapptmp.getId()+"");
						paogrammap.put("Program_app_status", "1");
						paogrammap.put("send_user", username);
						paogrammap.put("send_time", nowtime1);
						utildao.updateinfo(conn,paogrammap, "xct_JMApp");
						
						allprogram+=new StringBuffer().append(",").append(programapptmp.getProgram_name()).toString();
					}
//					conn.commit(); 
					for(int j=1,z=ipsarray.length;j<z;j++){
						if(!"".equals(ipsarray[j])){
							String []macip=ipsarray[j].split("_");  
							logsdao.addlogs1(conn,username, "�û���"+username+"�����նˡ�"+TerminalDAO.getTerminalNameByMAC(ipsarray[j])+"("+macip[1]+")�������˽�Ŀ����"+allprogram+"��", 1);
						}
					}
					dbc.returnResources(conn);
					//=====================================================================================================================
					//=====================================================================================================================
					//================================���� �� �� ���ͽ�Ŀʱ�������в������նˣ������Ŀ��======================================
					new Thread(new Runnable() {
						public void run() {
							try{
								DBConnection dbc=new DBConnection();
								Connection conn1 = dbc.getConection();
								
								String nowdate=UtilDAO.getNowtime("yyyy-MM-dd");
								String nowtime=UtilDAO.getNowtime("HH:mm")+":00";
	//							conn1.setAutoCommit(false);   
		//						Statement stmt = conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY); 
								//////////////////////��ӽ�Ŀ�������ݿ�
								for(int i=1,z=ipsarray.length;i<z;i++){
									if(!"".equals(ipsarray[i])){
										String []macip=ipsarray[i].split("_");
										for(int j=0,m=allTerminalList.size();j<m;j++){
											terminal=allTerminalList.get(j);
											if( terminal.getCnt_mac().equals(macip[1])&& null!=FirstStartServlet.terminalMap.get(terminal.getCnt_mac())
													&& FirstStartServlet.terminalMap.get(terminal.getCnt_mac()).getCnt_islink()==3){//�ڻ����м�鲻�����ն�
			//								if( terminal.getCnt_ip().equals(ipsarray[i]) && terminal.getCnt_islink()==3){//��鲻�����ն�
												logger.info(MessageFormat.format("���ͽ�Ŀʱ�������в������ն�--��ӽ�Ŀ�������ݿ�--1-->{0}_{1}_{2}",terminal.getCnt_name(),terminal.getCnt_ip(),terminal.getCnt_mac()));
												
												File macxmlfile= new File(new StringBuffer().append(mac_home).append(terminal.getCnt_mac()).append(".xml").toString());
												if(null!=programapplist && programapplist.size()>0){
													for(int k=0;k<programapplist.size();k++){
														programapptmp=programapplist.get(k);
														if(programapptmp.getProgram_play_type()==1){  ///�岥ʱ�����ݵ�ǰʱ��ͽ�Ŀʱ�����������Ŀ�Ĳ���ʱ��
															String[] newtime=UtilDAO.gettimeBytime(nowdate+" "+nowtime,programapptmp.getProgram_playlong()).split("#");
															programapptmp.setProgram_playdate(nowdate);
															programapptmp.setProgram_playtime(nowtime);
															programapptmp.setProgram_enddate(newtime[0]);
															programapptmp.setProgram_endtime(newtime[1]);
														}
														//=================================================
														File macFile2=new File(macxmlfile.getPath());
														StringBuffer sb=new StringBuffer();
														sb.append(macFile2.getParent()).append(File.separator);
														
														if(new File(sb.append(programapptmp.getProgram_jmurl()).toString()).exists()){
															logger.info(MessageFormat.format("���ͽ�Ŀʱ�������в������ն�--��ӽ�Ŀ�������ݿ�--2-->{0}_{1}_{2}",terminal.getCnt_name(),terminal.getCnt_ip(),terminal.getCnt_mac()));
															creatmacxmlutils.createProjectMacXml(macxmlfile.getPath(),terminal.getCnt_mac(),programapptmp.getId(), programapptmp.getProgram_name(), programapptmp.getProgram_jmurl(),programapptmp.getProgram_playdate()+" "+programapptmp.getProgram_playtime(), programapptmp.getProgram_enddate()+" "+programapptmp.getProgram_endtime(), programapptmp.getProgram_play_type(), programapptmp.getProgram_playlong()+"");///����ʱֻ����3���Ŀ��
															managerprojectdao.insertProjectParm(conn1,/*stmt,*/programapptmp.getId(),programapptmp.getProgram_jmurl(), programapptmp.getProgram_name(),programapptmp.getProgram_playdate(), programapptmp.getProgram_playdate()+" "+programapptmp.getProgram_playtime(), programapptmp.getProgram_enddate()+" "+programapptmp.getProgram_endtime(),programapptmp.getProgram_enddate(), programapptmp.getProgram_playlong()+"", programapptmp.getProgram_play_type(), ipsarray[i],terminal.getCnt_mac(),2);
														}else{
															logger.info("--------��"+terminal.getCnt_ip()+"_"+terminal.getCnt_mac()+" �������в������ն� ��Ҫ���� �Ľ�Ŀ�ļ��в����� :"+sb.toString());
														}
													}
												}
												mac_strs+=new StringBuffer().append("#").append(terminal.getCnt_mac()).toString();
											}
										}
									}
								}
								UtilDAO utildao = new UtilDAO();
								Map paogrammap =new HashMap();
								for(int k=0;k<programapplist.size();k++){
									programapptmp=programapplist.get(k);
									paogrammap= utildao.getMap();
									paogrammap.put("id", programapptmp.getId()+"");
									paogrammap.put("Program_app_status", "1");
									paogrammap.put("send_user", send_username);
									paogrammap.put("send_time", nowtime1);
									paogrammap.put("Program_sendok_terminal", mac_strs);
									utildao.updateinfo(conn1,paogrammap, "xct_JMApp");
								}
	//							conn1.commit();   
								dbc.returnResources(conn1);
							}catch(Exception e){
								logger.info("���ɶϿ��ն˽�Ŀ������"+e.getMessage());
								e.printStackTrace();
							}
						}
					}).start();
					
					//----------------------------���²��ܶ�ȡ���ݿ�����ݣ�������������ݣ������״̬����ʱ����-----------------------------------------------------
					if(null!=terminals&&terminals.size()>0){
						for(int i=1,z=ipsarray.length;i<z;i++){
							if(!"".equals(ipsarray[i])){
								String []macip=ipsarray[i].split("_");
								for (Map.Entry<String, Terminal> entry : terminals.entrySet()){
									Terminal  terminal=(Terminal)entry.getValue();
									if( terminal.getCnt_mac().equals(macip[1])) {
										if(terminal.getCnt_islink()==3){//��鲻�����ն�
											resultresponse.put(ipsarray[i], "save_ok");  //�����߽�Ŀ������ɹ�
											logger.info(MessageFormat.format("���ͽ�Ŀʱ���Ͽ��ն˽�Ŀ������------>{0}_{1}_{2}",terminal.getCnt_name(),terminal.getCnt_ip(),terminal.getCnt_mac()));
										}
									}
								}
							}
						}
					}
					
	//				for(int i=1,z=ipsarray.length;i<z;i++){
	//					for(int j=0,m=allTerminalList.size();j<m;j++){
	//						terminal=allTerminalList.get(j);
	//						if( terminal.getCnt_ip().equals(ipsarray[i])) {
	//							
	//							if(terminal.getCnt_islink()==3){//��鲻�����ն�
	//								resultresponse.put(ipsarray[i], "save_ok");  //�����߽�Ŀ������ɹ�
	//							}
	//						}
	//					}
	//				}
					
			}catch(Exception e){
				e.printStackTrace();
				logger.info("���ͽ�Ŀ����"+e.getMessage());
			}
    	}else{
    		logger.info("clientips=============>δ�ҵ�ѡ����նˣ���");
    	}
		return resultresponse;
	}
	
	public void sleepNotice(long l){
		try {
			Thread.sleep(l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public Map<String,String> clientRequest(String clientips){//�鿴�ն�����״̬
		
	    Map<String,String> resultresponse= new HashMap<String, String>();
	    
		ipsarray=clientips.split("@")[1].split("#");
		
		Map<String,CntResponse> dps = new HashMap<String, CntResponse>();
		for (Map.Entry<String, CntResponse> entry : FirstStartServlet.downloadProgramStatus.entrySet()) {
			dps.put(entry.getKey(), entry.getValue());
		}
		
		if(map!=null){
			for(int i=1,z=ipsarray.length;i<z;i++){
				if(!"".equals(ipsarray[i])){
					//String []macip=ipsarray[i].split("_");
					for (Map.Entry<String, CntResponse> entry : dps.entrySet()) {
						cntmac=entry.getKey();
						cntrp=entry.getValue();
						if(ipsarray[i].equals(cntmac)&&cntrp!=null){
							resultresponse.put(ipsarray[i], cntrp.getCnt_cmdresultZh());//�ն˷���״ֵ̬
							FirstStartServlet.downloadProgramStatus.remove(ipsarray[i]);
						}
					}
				}
			}
		}
		return 	resultresponse;	
	}
	
	public List<ClientProjectBean> checkErrorProgramIsExist(String programid){
		DBConnection dbc=new DBConnection();
		Connection conn = dbc.getConection();
		List<ClientProjectBean>  clientprojectlist=new ArrayList<ClientProjectBean>();
		ProgramAppDAO programappdao= new ProgramAppDAO();
		ManagerProjectDao managerprojectdao=new ManagerProjectDao(); 
		ProgramApp programapp=programappdao.getProgramAppByStr(conn,"where Program_id='"+programid+"'");
		ClientProjectBean clientprojectbean=null;
		if(programapp!=null){
	        //String []ips= programapp.getProgram_play_terminal().split("#");
	        String terminalips=programapp.getProgram_play_terminal();
	        String[] Program_sendok_terminals=programapp.getProgram_sendok_terminal().split("#");
	        for(int i=0;i<Program_sendok_terminals.length;i++){
		        if(!"".equals(Program_sendok_terminals[i])){
		        terminalips=terminalips.replace("#"+Program_sendok_terminals[i],"");
		        }
	        }
	        String[] ips=terminalips.split("#");
	        int type=programapp.getProgram_play_type();
	        for(int i=1,n=ips.length;i<n;i++){
	        	if(!"".equals(ips[i])){
		        	if(type==0||type==3){// ÿ��ͬһ��Ŀֻ��һ�� ѭ����Ŀ������ѭ����Ŀ
		        		 clientprojectbean=managerprojectdao.getClientProectForLoop(conn,programapp.getProgram_jmurl(), programapp.getProgram_playdate(), programapp.getProgram_enddate(), ips[i], type,programapp.getProgram_play_typeZh());
		        	}else if(type==1){//�岥��Ŀ���������ɾ����
	        			 clientprojectbean=managerprojectdao.getClientProectForInsert(conn,ips[i], 1,programapp.getProgram_play_typeZh());
	        		}else{ // ��ʱ��Ŀ
	        			 clientprojectbean=managerprojectdao.getClientActiveProject(conn,programapp.getProgram_playdate(), programapp.getProgram_enddate(), ips[i], 2);        		}
		        	if(null!=clientprojectbean)
	     			   clientprojectlist.add(clientprojectbean);
	        	}
	        }
		}
		dbc.returnResources(conn);
		return clientprojectlist;
	}
	
	public String  deleteProgramHistory(String jmIds){//�˷����б� ���ݿ�����û�أ���ֵ������ͷ����ӡ�
		String [] jmidlist=jmIds.split("#");
		DBConnection dbc=new DBConnection();
		Connection conn = dbc.getConection();
		ProgramHistoryDAO programhistorydao=new ProgramHistoryDAO();
		Map<String,String> map =new HashMap<String, String>();
		for(int i=1;i<jmidlist.length;i++){
			map=programhistorydao.getSomeProgram(conn,"where Program_JMid="+jmidlist[i]);
			for(Map.Entry<String, String>enty: map.entrySet()){
				deleteClientProgram(enty.getValue(),enty.getKey());
			}
			programhistorydao.deleteinfo(conn,"Program_JMid", jmidlist[i], "xct_JMhistory");
		}
		logger.info(new  StringBuffer().append("***********DwrClass.deleteProgramHistory*********").toString());
		dbc.returnResources(conn);
		return "ok";
	}
	
	public void deleteClientProgram(String cmd,String clentIp){
//		FirstStartServlet.client.allsend(clentIp, "lv0025@"+cmd);
		String []clientarray=clentIp.split("_");
		FirstStartServlet.client.allsend(clientarray[1],clientarray[0], "lv0025@"+cmd);
	}
	
	
	
/*	public String  deleteProgramHistory(String jmIds){
		UtilDAO utildao= new UtilDAO();
		String [] jmidlist=jmIds.split("#");
		for(int i=1;i<jmidlist.length;i++){
			utildao.deleteinfo("Program_JMid", jmidlist[i], "xct_JMhistory");
		}
		return "ok";
	}*/
	
	public String checkProgramMedia(String templateid){
		String result="yes";
		ModuleDAO moduledao= new ModuleDAO();
		List moduleList=moduledao.getModuleMediaByTemplateId(templateid);
		for (int i = 0; i < moduleList.size(); i++) {
			Module module = (Module) moduleList.get(i);
			String mtype=module.getM_filetype();
			if("".equals(mtype)){
				result="��ʾ��Ϣ������������"+module.getArea_id()+"��δѡ�񲥷����ͣ���ѡ��";
				break;
			}else{
				 if("scroll".equals(mtype)){
					 if(module.getMediaList().size()==0){
							result="��ʾ��Ϣ������������"+module.getArea_id()+"��δ��ӹ������֣�����ӹ������֣�";
							break;
					 }
					 if(module.getState()==0){
					 	result="��ʾ��Ϣ������������"+module.getArea_id()+"����������δ���棬���ȱ��棡";
						break;
					 }
				}else if("weather".equals(mtype)){
					 if(module.getState()==0){
						result="��ʾ��Ϣ������������"+module.getArea_id()+"������Ԥ��δ���棬���ȱ��棡";
						break;
					 }
				}else if("clock".equals(mtype)||"dateother".equals(mtype)||"date".equals(mtype)||"birthday".equals(mtype)
						||"stock".equals(mtype)||"stockother".equals(mtype)||"wwnotice".equals(mtype)||"wwmilkdrink".equals(mtype)
						||"filialeSell".equals(mtype)||"marketstock".equals(mtype)||"goldtrend".equals(mtype)||"htmeetingnotice".equals(mtype)
						||"nordermeeting".equals(mtype)||"insnquametting".equals(mtype)||"baononglift".equals(mtype)||"weathersimple".equals(mtype)
						||"nbqueuing".equals(mtype)||"kingentranceguard".equals(mtype)||"nbqueuingmore".equals(mtype)){
					result="yes";
				}else if("iptv".equals(mtype)){
					 if(module.getState()==0){
							result="��ʾ��Ϣ������������"+module.getArea_id()+"����ý��δ���棬���ȱ��棡";
							break;
						 }
				}else if("count_down".equals(mtype)){
					 if(module.getState()==0){
							result="��ʾ��Ϣ������������"+module.getArea_id()+"������ʱ����δ���棬���ȱ��棡";
							break;
						 }
				}else if("web".equals(mtype)){
					 if(module.getState()==0){
							result="��ʾ��Ϣ������������"+module.getArea_id()+"����ҳδ���棬���ȱ��棡";
							break;
						 }
				}else if("htmltext".equals(mtype)){
					 if(module.getState()==0){
							result="��ʾ��Ϣ������������"+module.getArea_id()+"�������ı�δ���棬���ȱ��棡";
							break;
						 }
				}
				/////��������
				else if("w_web".equals(mtype)){
					 if(module.getState()==0){
							result="��ʾ��Ϣ������������"+module.getArea_id()+"������--WEBģ��δ���棬���ȱ��棡";
							break;
						 }
				}else if("weatherthree".equals(mtype)){
					 if(module.getState()==0){
							result="��ʾ��Ϣ������������"+module.getArea_id()+"������--����(����)δ���棬���ȱ��棡";
							break;
						 }
				}
				
				/////////////////
				else{
					if(module.getMediaList().size()==0){
					result="��ʾ��Ϣ������������"+module.getArea_id()+"��δ���ý�壬�����ý�壡";
					break;
					}
				}
			}
		}
		return result;
	}
	
	public List<Media> getMediasBystr(String str,String username,int zu_pth){
		List<Media> list=new MediaDAO().getALLMediaDAO(str);
		TerminalDAO terminaldao = new TerminalDAO();
		List meida_zu = terminaldao.getAllZu("where zu_type=1 and zu_pth="+zu_pth+" and zu_username like '%"+username+"||%'");
		for(int i=0;i<meida_zu.size();i++){
			Media media= new Media();
			Terminal terminal_zu= (Terminal)meida_zu.get(i);
			media.setMedia_id(terminal_zu.getZu_pth()+"");/////��ĸ�ID
			media.setZuname(terminal_zu.getZu_name());
			media.setZu_id(terminal_zu.getZu_id());
			media.setMedia_type("Program_zu");
			list.add(media);
		}
		return list;
	}public List<Media> getMediasBystr1(String str){
		List<Media> list=new MediaDAO().getALLMediaDAO(str);
		return list;
	}
	public String getTerminalNumBystr(){//��ȡ�ն˸���״̬����
		
		WebContext webc = WebContextFactory.get();
		HttpServletRequest request = webc.getHttpServletRequest();
		HttpSession session= request.getSession();
		Map<Integer,Terminal> zumap=null;
		if(null!=session.getAttribute("lg_user")){
			if(null!=session.getAttribute("user_cnt_zu_map")){
			   zumap =(Map<Integer,Terminal>)session.getAttribute("user_cnt_zu_map");
			}else {
				zumap=TerminalDAO.getZuListByUsername(((Users)session.getAttribute("lg_user")).getLg_name()); ////��ȡ��¼�û��������ն�����Ϣ
				session.setAttribute("user_cnt_zu_map", zumap);
				if(null==zumap){
				   zumap=new HashMap<Integer, Terminal>();
				   logger.info("------DwrClass---session.getAttribute(user_cnt_zu_map)---->�鼯��Ϊ��");
				}else {
					logger.info("------DwrClass---session.getAttribute(user_cnt_zu_map)---->�鼯��Ϊ�����»�ȡ��");
				}
			}
		    return new TerminalDAO().getTerminalNumBystr(zumap);
		}
		return "0_0_0_0";
	}
	
	public List<Terminal> getTerminalBystr(String str){
		return new TerminalDAO().getALLTerminalDAO(str);
	}
	public List<Logs> getLogsBystr(String str){
		return new LogsDAO().getLogsByStr(str);
	}
	public int getMediaMaxGroup_num(){
		MediaDAO mediadao= new MediaDAO();
		return mediadao.getMaxNum();
	}
	public int orderMedia(int new_order_media_id,int old_order_media_id,int module_id){
		return new ModuleDAO().orderMedia(new_order_media_id,old_order_media_id,module_id);
	}
	public String checkIsLogin(String logined){//�ж��û��Ƿ��Ѿ���¼
		int user_count=SessionListener.getSession_user_count();
	     if (null!=logined&&!SessionListener.hUserNamemap.containsKey(logined)){
				 return "1_"+user_count;
	     }
		 return "0_"+user_count;
	}
	public String updateImagesSpan(String moduleid,String span){
			UtilDAO utildao= new UtilDAO();
			Map<String, String> map=utildao.getMap();
			map.put("id", moduleid);
			map.put("span", span+"000");
			utildao.updateinfo(map, "xct_module_temp");
			return span;
	}
	public String updateModuleSpan(String moduleid,String span){
		UtilDAO utildao= new UtilDAO();
		Map<String, String> map=utildao.getMap();
		map.put("id", moduleid);
		map.put("span", span);
		utildao.updateinfo(map, "xct_module_temp");
		return span;
}
	public void updateProgramStatus(String jmurl,String status){

		UtilDAO utildao= new UtilDAO();
		Map<String, String> map=utildao.getMap();
		map.put("Program_JMurl",jmurl);
		map.put("Program_ISloop",status);
		utildao.updateinfo(map,"xct_JMPZ");
}
	public int updateCntVolume(String checkips,String opp){
		resip = checkips.split("!");
		for (int i = 1; i < resip.length; i++) {
			String []clientarray=resip[i].split("_");
			FirstStartServlet.client.allsend(clientarray[1],clientarray[0], opp);
		}
		return 1;
	}
	public String  actionTerminal(String cnt_ip,String mac){
		boolean bool=UtilDAO.pingServer(cnt_ip, 2000);  //
		if(bool){
			return mac+"_��ǰ�ն���������";
		}else{
			String mac_="";
			for(int i=0;i<mac.length();i++){
				if(i!=0&&i%2==0){
					mac_+=":";
				}
				mac_+=mac.substring(i,i+1);
			}
			return mac+"_"+UtilDAO.actionTerminal(mac_);
		}
	}
	public String  pingTerminal2(String cnt_name,String cnt_ip,String mac){
		boolean bool=UtilDAO.pingServer(cnt_ip, 2000);  //
		if(bool){
			return mac+"_��"+cnt_name+"����������������";
		}else{
			return mac+"_��"+cnt_name+"���������Ӵ���";
		}
	}
	
	public void onkeyscreen(String checkips){///һ������
		resip = checkips.split("!");
		for (int i = 1; i < resip.length; i++) {
			if(!"".equals(resip[i])){
				String []clientarray=resip[i].split("_");
				FirstStartServlet.client.allsend(clientarray[1],clientarray[0], "lv0011");
			}
		}
	}
	
	////����
	public int guiCamera(String cnt_mac,String cnt_ip,String cmd){
		if(pnpIsOperator(new StringBuffer().append(cnt_ip).append("_").append(cnt_mac).toString()).equals("OK")){
			try{
				Map<String,String> cnt_states=FirstStartServlet.client_result_states;
				for (Map.Entry<String, String> entry : cnt_states.entrySet()) {
					if(entry!=null){
						String keytmp=new StringBuffer().append(cnt_ip).append("_").append(cnt_mac).append("_").append(cmd).toString();
						if(entry.getKey().equals(keytmp)&&"lv0011_OK".equals(entry.getValue())){
							ConstantUtil.changeClientCmd(new StringBuffer().append(cnt_ip).append("_").append(cnt_mac).toString(),cmd);
							return 1;
						}else if(entry.getKey().equals(keytmp)&&"lv0011_ERROR".equals(entry.getValue())){
							return 2;
						}
					}
				}
			}catch(Exception e){
				logger.info("��������"+e.getMessage());
				e.printStackTrace();
				return 3;
			}
		}else{
			return 4;
		}
		return 0;
	}
	
	//�鿴�Ƿ���δ������ָ��
	public String pnpIsOperator(String ipmac){
//		String backcode="NO";
//		if(System.getProperty("SENDTYPE").equals(ConstantUtil.PNP)){
//			
//			String key=ipmac;//new StringBuffer().append(cnt_ip).append("_").append(terminal_mac).toString();
//			if(null!=ConstantUtil.CLIENTCMDMAP&&ConstantUtil.CLIENTCMDMAP.size()>0){
//			    if(null!=ConstantUtil.CLIENTCMDMAP.get(key)){
//	   			   for (Map.Entry<String, ConcurrentHashSet<ClientCmd>> entry : ConstantUtil.CLIENTCMDMAP.entrySet()){
//	   				  Set<ClientCmd> clientcmdset=entry.getValue();
//				      for(ClientCmd clientcmd:clientcmdset){
//	   					  if(clientcmd.getIsoperator()==0&&clientcmd.getIscancel()==0){//��ȡδȡ����ָ���δִ�е�ָ��,ֻ�ǵ�һָ�δ���Ƕ�ָ�������
//	   						  backcode="OK";
//	   						  break;
//	   					  }
//	   				  }
//	   			   }
//		        }
//			}
//		}
//		return backcode;
		
		return "OK";
	}
	
	//���ط����������������ͽ�Ŀlv0009��inglv009֮���
	public Map<String, String> get_cmd_result(String lg_name,String cnt_ips,String cmd,String ok_str,String error_str){
		
		Map<String,String> resultresponse= new HashMap<String, String>();
		try{
			String []cntipArray=cnt_ips.split("!");
			Map<String,String> cnt_states = new HashMap<String, String>();
			for (Map.Entry<String, String> entry : FirstStartServlet.client_result_states.entrySet()) {
				 cnt_states.put(entry.getKey(), entry.getValue());
			}
			if(cnt_states!=null){
				Connection conn =new DBConnection().getConection();
				for (Map.Entry<String, String> entry : cnt_states.entrySet()) {
					 if(entry!=null){
						
						for(int i=0,n=cntipArray.length;i<n;i++){
							if(null!=cntipArray[i]&&!"".equals(cntipArray[i])){
								
								String key=entry.getKey();
								
								if(pnpIsOperator(cntipArray[i]).equals("NO")){//�鿴�Ƿ���δ������ָ��
									resultresponse.put(cntipArray[i], "NOOP");
									FirstStartServlet.client_result_states.remove(cntipArray[i]+"_"+cmd);
									String isoperatoring="�ն˻���δִ��ָ�";
									new LogsDAO().addlogs1(conn,lg_name, "�û���"+lg_name+"�����նˡ�"+TerminalDAO.getTerminalNameByMAC(key)+"�� ����ָ�� "+isoperatoring, 1);
								}
								
	//							String ip=key.split("_")[0];
	//							System.out.println(cntipArray[i]+"_"+cmd+"---->"+key+", "+entry.getValue());
								if((cntipArray[i]+"_"+cmd).equals(key)&&(cmd+"_OK").equals(entry.getValue())){
									resultresponse.put(cntipArray[i], "OK");
									FirstStartServlet.client_result_states.remove(cntipArray[i]+"_"+cmd);
									if(cmd.startsWith("lv")){///�����ն˽�Ŀ ��ʼ���ؽ�Ŀ
										new LogsDAO().addlogs1(conn,lg_name, "�û���"+lg_name+"�����նˡ�"+TerminalDAO.getTerminalNameByMAC(key)+"��"+ok_str, 1);
									}
									ConstantUtil.changeClientCmd(cntipArray[i],cmd);
								}else if((cntipArray[i]+"_"+cmd).equals(key)&&(cmd+"_ERROR").equals(entry.getValue())){
									resultresponse.put(cntipArray[i], "ERROR");
									FirstStartServlet.client_result_states.remove(cntipArray[i]+"_"+cmd);
									if(cmd.startsWith("lv")){///�����ն˽�Ŀ ��ʼ���ؽ�Ŀ
										new LogsDAO().addlogs1(conn,lg_name, "�û���"+lg_name+"�����նˡ�"+TerminalDAO.getTerminalNameByMAC(key)+"��"+error_str, 1);
									}
									ConstantUtil.changeClientCmd(cntipArray[i],cmd);
								}else if((cntipArray[i]+"_"+cmd).equals(key)&&(cmd+"_DOWNLOAD").equals(entry.getValue())){
									resultresponse.put(cntipArray[i], "DOWNLOAD");
									FirstStartServlet.client_result_states.remove(cntipArray[i]+"_"+cmd);
									if(cmd.startsWith("lv")){///�����ն˽�Ŀ ��ʼ���ؽ�Ŀ
										new LogsDAO().addlogs1(conn,lg_name, "�û���"+lg_name+"�����նˡ�"+TerminalDAO.getTerminalNameByMAC(key)+"��"+error_str, 1);
									}
									ConstantUtil.changeClientCmd(cntipArray[i],cmd);
								}else if((cntipArray[i]+"_"+cmd).equals(key)&&(cmd+"_FTPCLOSE").equals(entry.getValue())){
									resultresponse.put(cntipArray[i], "FTPCLOSE");
									FirstStartServlet.client_result_states.remove(cntipArray[i]+"_"+cmd);
									if(cmd.startsWith("lv")){///�����ն˽�Ŀ ��ʼ���ؽ�Ŀ
										new LogsDAO().addlogs1(conn,lg_name, "�û���"+lg_name+"�����նˡ�"+TerminalDAO.getTerminalNameByMAC(key)+"��"+error_str, 1);
									}
									ConstantUtil.changeClientCmd(cntipArray[i],cmd);
								}else if((cntipArray[i]+"_"+cmd).equals(key)&&(cmd+"_NOEXISTS").equals(entry.getValue())){
									resultresponse.put(cntipArray[i], "NOEXISTS");
									FirstStartServlet.client_result_states.remove(cntipArray[i]+"_"+cmd);
									if(cmd.startsWith("lv")){///�����ն˽�Ŀ ��ʼ���ؽ�Ŀ
										new LogsDAO().addlogs1(conn,lg_name, "�û���"+lg_name+"�����նˡ�"+TerminalDAO.getTerminalNameByMAC(key)+"��"+error_str, 1);
									}
									ConstantUtil.changeClientCmd(cntipArray[i],cmd);
								}
							}
						}
					}
				}
				conn.close();
			}
		}catch(Exception e){
			logger.info("��ȡ�ն�״̬����"+e.getMessage());
			e.printStackTrace();
		}
		return resultresponse;
	}
	
	
//	public void changeClientCmd(String ipmac,String cmd){
//		if(System.getProperty("SENDTYPE").equals(ConstantUtil.PNP)){
//			String key=ipmac;//new StringBuffer().append(cnt_ip).append("_").append(terminal_mac).toString();
//			if(null!=ConstantUtil.CLIENTCMDMAP&&ConstantUtil.CLIENTCMDMAP.size()>0){
//			    if(null!=ConstantUtil.CLIENTCMDMAP.get(key)){
//	   			   for (Map.Entry<String, ConcurrentHashSet<ClientCmd>> entry : ConstantUtil.CLIENTCMDMAP.entrySet()){
//	   				  Set<ClientCmd> clientcmdset=entry.getValue();
//				      for(ClientCmd clientcmd:clientcmdset){
//	   					  if(clientcmd.getIpmac().equals(ipmac)&&DESPlusUtil.Get().decrypt(clientcmd.getCmd()).startsWith(cmd)){
//	//   						  System.out.println(cmd+"___"+clientcmd.getCmd());
//	   						 clientcmd.setIsoperator(1);//�����Ѿ�����ִ��ָ��ġ�����
//	   					  }
//	   				  }
//	   			   }
//		        }
//			}
//		}
//	}
	
	public void addlogs(String str){
		Connection conn =new DBConnection().getConection();
		new LogsDAO().addlogs1(conn,"system", str, 1);
		if(null!=conn){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getOnlineNum(){
		return SessionListener.getSession_user_count();
	}
	
	public List<ProgramHistory> getcntProgramMenu(String cntipmac,String cmd){
		List<ProgramHistory> list = new ArrayList<ProgramHistory>();
		try{
			Map<String,CntResponse> cnt_program_menu=FirstStartServlet.client_program_menu;
			Map<String,Terminal> terminals=FirstStartServlet.terminalMap;
	//		System.out.println("cnt_program_menu===="+cnt_program_menu);
			for (Map.Entry<String, CntResponse> entry : cnt_program_menu.entrySet()) {
			  if(entry!=null){
				CntResponse cntr=entry.getValue();
				if(entry.getKey().equals(new StringBuffer().append(cntipmac).append("_").append(cmd).toString())&&"lv0028_OK".equals(cntr.getCnt_cmdstatus())){
					String projectmenus=cntr.getCnt_cmdresult();
//					System.out.println("projectmenus====="+projectmenus);
					if("default".equals(projectmenus)){
						ProgramHistory program= new ProgramHistory();
						program.setProgram_Name("default");
						list.add(program);
						ConstantUtil.changeClientCmd(cntipmac,cmd);
					}else{
						////��ȡ��ǰ�ͻ������ڲ��ŵĽ�Ŀ
						String playclientstring="";
						for (Map.Entry<String, Terminal> terminals_entry : terminals.entrySet()) {
							Terminal terminal=terminals_entry.getValue();
							if(cntipmac.equals(new StringBuffer().append(terminal.getCnt_ip()).append("_").append(terminal.getCnt_mac()))){
								playclientstring=terminal.getCnt_playprojecttring();
								break;
							}
						}
						String menuname[]=projectmenus.replace("$$$", "!!!").split("!!!");
						for(int i=0;i<menuname.length;i++){
							ProgramHistory program= new ProgramHistory();
							String[] menus=menuname[i].split("#");
							program.setProgram_Name(UrlEncodeUtil.englishSysCharToBack(menus[0].replace(" ", "")));
							program.setProgram_JMurl(menus[1]);
							program.setProgram_SetDate(menus[2]);
							program.setProgram_type(menus[3]);
							program.setProgram_long(menus[4]);
							
							program.setProject_url_datetime(menus[1]+menus[2].replace(":", "").replace(".0", "").replace(" ", ""));
							String datestring=DateUtils.addDate(menus[2],Integer.parseInt(menus[4]));
							
							if(!"".equals(playclientstring)&&null!=playclientstring&&menuname[i].equals(playclientstring)){
								program.setXystatus("play");    ////���ڲ��Ž�Ŀ
							}else{
								program.setXystatus("��  ��");
							}
							if(menus[3].equals("loop")){
								program.setXyenddatetime(menus[4]+" ����");//�������ʱ��
							}else if(menus[3].equals("defaultloop")){
								program.setXyenddatetime("ȫ�첥��");
								program.setProject_url_datetime(menus[1]+menus[2].split(" ")[0]+"200000");
						       }
							else
							    program.setXyenddatetime(menus[2].split(" ")[1]+"-"+datestring.split(" ")[1]);//�������ʱ��
							
							if(menus[3].equals("active")){
								String nowdatetime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
								if(nowdatetime.compareTo(datestring)>=0)
									program.setXystatus("<span style='color:red'>�ѷ���</span>");
								if(nowdatetime.compareTo(menus[2])<0)
									program.setXystatus("��  ��");//�����ǰ���ڲ��Ŷ�ʱ��Ŀ����ʱ�岥һ����Ŀʱ��������ʾ ��������
							}
							list.add(program);
						}
						ConstantUtil.changeClientCmd(cntipmac,cmd);
					}
				}
			}
		}
		}catch(Exception e){
			logger.info("��ȡ�ն˽�Ŀ������"+e.getMessage());
			e.printStackTrace();
		}
		Collections.sort(list,new ProgramListCompareter());
		return list;
		
	}
	public String checkProgramMenu(String allip,String programe_file){
		String []iparray= allip.replaceAll("\\s+","").split("!");
		String programefile[] = programe_file.replaceAll("\\s+","").split("!"); 
//		System.out.println("DWR-----checkProgramMenu--------->"+allip+"__"+programe_file);
		return new ProgramHistoryDAO().checkProgramMenu(iparray, programefile);
	}
	public String checkProgramMenu2(String allip,String programe_file){
		String []iparray= allip.replaceAll("\\s+","").split("!");
		String programefile = programe_file.replaceAll("\\s+","").replace("!", ""); 
		return new ProgramHistoryDAO().checkProgramMenu2(iparray, programefile);
	}
	 public void  deleteProgramAppBybatch(int batch){
		UtilDAO utildao= new UtilDAO();
		utildao.deleteinfo("batch", batch+"", "xct_JMApp");
	}
	 public String getModuleMediaByProgrameUrl(String programe_file){
				String result="yes";
				String programefile[] = programe_file.split("!"); 
				ModuleDAO moduledao= new ModuleDAO();
				List moduleList=moduledao.getModuleMediaByProgrameUrl(programefile);
				for (int i = 0; i < moduleList.size(); i++) {
					Module module = (Module) moduleList.get(i);
					String mtype=module.getM_filetype();
					if("".equals(mtype)){
						result="������Ϣ����Ŀ��"+module.getProgram_name()+"���ġ���������"+module.getArea_id()+"��δѡ�񲥷����ͣ�";
						break;
					}else{
						 if("scroll".equals(mtype)){
							 if(module.getMediaList().size()==0){
									result="������Ϣ����Ŀ��"+module.getProgram_name()+"���ġ���������"+module.getArea_id()+"��δ��ӹ������֣�����ӹ������֣�";
									break;
							 }
							 if(module.getState()==0){
							 	result="������Ϣ����Ŀ��"+module.getProgram_name()+"���ġ���������"+module.getArea_id()+"����������δ���棬���ȱ��棡";
								break;
							 }
						}else if("weather".equals(mtype)){
							 if(module.getState()==0){
								result="������Ϣ����Ŀ��"+module.getProgram_name()+"���ġ���������"+module.getArea_id()+"������Ԥ��δ���棬���ȱ��棡";
								break;
							 }
						}else if("clock".equals(mtype)||"dateother".equals(mtype)||"date".equals(mtype)||"birthday".equals(mtype)
								||"stock".equals(mtype)||"stockother".equals(mtype)||"wwnotice".equals(mtype)||"wwmilkdrink".equals(mtype)
								||"filialeSell".equals(mtype)||"marketstock".equals(mtype)||"goldtrend".equals(mtype)||"policesubstation".equals(mtype)
								||"htmeetingnotice".equals(mtype)||"nordermeeting".equals(mtype)||"htmeetingnotice".equals(mtype)
								||"baononglift".equals(mtype)||"weathersimple".equals(mtype)
								||"nbqueuing".equals(mtype)||"kingentranceguard".equals(mtype)||"nbqueuingmore".equals(mtype)){
							result="yes";
						}else if("iptv".equals(mtype)){
							 if(module.getState()==0){
									result="������Ϣ����Ŀ��"+module.getProgram_name()+"���ġ���������"+module.getArea_id()+"����ý��δ���棬���ȱ��棡";
									break;
								 }
						}else if("count_down".equals(mtype)){
							 if(module.getState()==0){
									result="������Ϣ����Ŀ��"+module.getProgram_name()+"���ġ���������"+module.getArea_id()+"������ʱ����δ���棬���ȱ��棡";
									break;
								 }
						}else if("web".equals(mtype)){
							 if(module.getState()==0){
									result="������Ϣ����Ŀ��"+module.getProgram_name()+"���ġ���������"+module.getArea_id()+"����ҳδ���棬���ȱ��棡";
									break;
								 }
						}else if("htmltext".equals(mtype)){
							 if(module.getState()==0){
									result="������Ϣ����Ŀ��"+module.getProgram_name()+"���ġ���������"+module.getArea_id()+"�������ı�δ���棬���ȱ��棡";
									break;
								 }
						}
						/////��������
						else if("w_web".equals(mtype)){
							 if(module.getState()==0){
									result="������Ϣ����Ŀ��"+module.getProgram_name()+"���ġ���������"+module.getArea_id()+"������--WEBģ��δ���棬���ȱ��棡";
									break;
								 }
						}else if("weatherthree".equals(mtype)){
							 if(module.getState()==0){
									result="������Ϣ����Ŀ��"+module.getProgram_name()+"���ġ���������"+module.getArea_id()+"������--����(����)δ���棬���ȱ��棡";
									break;
								 }
						}
						else{
							if(module.getMediaList().size()==0){
							result="������Ϣ����Ŀ��"+module.getProgram_name()+"���ġ���������"+module.getArea_id()+"��δ���ý�壬�����ý�壡";
							break;
							}
						}
					}
				}
				return result;
		 
	 }
	
}
