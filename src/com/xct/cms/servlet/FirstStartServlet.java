package com.xct.cms.servlet;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;

import java.sql.Connection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.mina.util.ConcurrentHashSet;

import com.excel.util.OpExcelUtils;
import com.xct.cms.custom.made.dao.WeiFangCourtDao;
import com.xct.cms.dao.TerminalDAO;
import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.ClientCmd;
import com.xct.cms.domin.CntResponse;
import com.xct.cms.domin.Terminal;
import com.xct.cms.observer.WeeHoursCreateProjectXmlObservable;
import com.xct.cms.observer.WeeHoursCreateProjectXmlObserver;
import com.xct.cms.utils.Client;
import com.xct.cms.utils.ConstantUtil;
import com.xct.cms.utils.DownImageFromNet;
import com.xct.cms.utils.InIConfigurationFile;
import com.xct.cms.utils.ProxyUtil;
import com.xct.cms.utils.Util;
import com.xct.cms.utils.UtilDAO;
import com.xct.cms.weather.ShangHaiWeatherCommonage;
import com.xct.cms.xy.dao.ManagerProjectDao;
import com.xct.cms.xy.domain.ClientProjectBean;

public class FirstStartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static String  projectpath="";
	
//	Hashtable，synchronizedMap ,ConcurrentHashMap 线程安全的
	
	public static Map<String,Terminal> RESPONSE_INTERFACE_TERMINALMAP=new ConcurrentHashMap<String, Terminal>();////定义全局变量 存放所有的终端信息，用于返回接口数据
	
	public static Map<String,Terminal> terminalMap=new ConcurrentHashMap<String, Terminal>();   ////定义全局变量 存放所有的终端信息
	public static Map<String,CntResponse> downloadProgramStatus=new ConcurrentHashMap<String, CntResponse>();   ////定义全局变量 存放客户端下载节目的状态
	public static Map<String,String> client_ip_port_map=new ConcurrentHashMap<String,String>();
	public static Map<String,String> client_result_states=new ConcurrentHashMap<String,String>();   ////key: client_ip#cmd    value: cmd_state
	public static Map<String,CntResponse> client_program_menu=new ConcurrentHashMap<String,CntResponse>();    //客户端节目单
	public static Map<String,String>client_ip_version=new ConcurrentHashMap<String,String>(); //装入版本号，通过版本号发送指令的分类，分 86平台，android，单片机
	public static Map<String,String>client_ip_httpport=new ConcurrentHashMap<String,String>();
	
	
	public static Map<String,String> client_result_states_temp=new ConcurrentHashMap<String,String>();   ////key: client_ip#cmd    value: cmd_state
	public static Map<String,String> old_led_text=new ConcurrentHashMap<String,String>();
	
	public static Client client;
	
	private InetAddress inet;
	private TerminalDAO terminaldao;
	private UtilDAO utildao;
//	private OpExcelUtils opexcelutils;
	private String ipaddress ="127.0.0.1";
	 
	 private String windowsdesktop64="C:\\Windows\\SysWOW64\\config\\systemprofile\\Desktop";//64位系统，必须要创建此目录，否则jacob没法读取ppt文档
	 private String windowsdesktop32="C:\\Windows\\System32\\config\\systemprofile\\Desktop";//32位系统，必须要创建此目录，否则jacob没法读取ppt文档
	 Logger logger2 ; 
	 
	 static String restarbatString="";
	 
	 ///////////////
//	 private Led led=new Led();
	 //////////////

	static Logger logger = Logger.getLogger(FirstStartServlet.class);
	 
	public static int INITSTART=0;
	
	public void init() throws ServletException {
		initCmsConfig();
	}
	
	public void initCmsConfig() throws ServletException {
			
		 if(null==System.getProperty("WEB_HOME")){
			 
			 System.setProperty("CMS_VERSION","1.5");//服务端版本号
			 
			 String tomcatverion=getServletConfig().getServletContext().getServerInfo();
			 logger.info(MessageFormat.format("Tomcat 版本号：{0}",tomcatverion));
			 
			 CreateOfficeDesktop();
			 projectpath= getServletConfig().getServletContext().getRealPath("/");
	
			 initWorkhomeParm(projectpath);
			 
			// if(!readServUDaemon())
			   //  closeServUDaemon();//停止FTP服务
			 
			 System.setProperty("PROXY_HOME",new StringBuffer().append(projectpath).append("proxy.properties").toString());//设置代理，为了天气预报通过代理上网获取天气预报和rss获取
			 
			 System.setProperty("XCT_FTP_HOME", new StringBuffer().append(projectpath).append("serverftp/program_file/").toString());
			 
			 System.setProperty("XCT_UDPNATPRINT_FILE", new StringBuffer().append(projectpath).append("udpnatprint.xy").toString());
			 
			 System.setProperty("WEIFANGCOURT_HOME",new StringBuffer().append(projectpath).append("serverftp/program_file/weifangcourt/").toString());
			 
			 if(new File(new StringBuffer().append(projectpath).append("isEnglnishSys.txt").toString()).exists())
				   System.setProperty("ISENGLNISHSYS","YES");//英文版系统需要在控制面板中设置区域和语言，必须下中文包。迪士尼和公司网站用此
			 else
				   System.setProperty("ISENGLNISHSYS","NO"); 
			 
			 String clientport=new StringBuffer().append(projectpath).append("clientport.txt").toString();
			 if(new File(clientport).exists())
			   System.setProperty("CLENTPORT_HOME",":"+Util.getFileValues(clientport));
			 else
			   System.setProperty("CLENTPORT_HOME",":80"); 
			 
			 //获取项目配置
			 opConfSetPropertise();
	
			  //获取节目类型
			 System.setProperty("PROGRAMTYPE",Util.getFileContext(new StringBuffer().append(projectpath).append("programType.xct").toString()));
		
			 logger.info(MessageFormat.format(" WEB_HOME ：{0}", projectpath));
			 logger.info(MessageFormat.format(" Version ： CMSServer {0}",System.getProperty("CMS_VERSION")));
			 
			 utildao= new UtilDAO();
			 utildao.alterColumn("xct_module_temp", "otherparam");//添加某个字段
			 utildao.alterColumn("xct_ipaddress", "client_version");//添加某个字段
			 utildao.alterColumn("xct_ipaddress", "is_day_download");//添加是否白天可以下载节目（1 为可以，0 为不能），迪士尼项目使用
			 utildao.alterColumn("xct_template_temp", "parent_template_id");//添加某个字段
			 utildao.alterColumn("xct_JMPZ", "auditingstatus" ,"int");//添加某个字段 
			 
//			 opexcelutils=new OpExcelUtils();
			 
			 getIpaddress();//获取IP地址，有点问题，如果是多网卡，多个IP地址
			 /////////////////////////////////////////////////////////////
			 ProxyUtil proxyutil= new ProxyUtil();
			 String [] key={"proxyHost","proxyPort","proxyUser","proxyPassword"};
			 proxyutil.writeProperties(key, "xxx");
			 proxyutil.getProxy();//获取上网代理
			 /////////////////////////////////////////////////////////////
	//		 createWeather();//获取天气预报,修改 weather_url.txt 里面的链接地址和城市名称即可获取天气预报
			 //////////////////////////////////////////////////
			 copyDLL();//复制DLL文件
			 //////////////////////////////////////////////
	//		 logger2 = Logger.getLogger("NTlog"); //要和配置文件中设置的名字相同  
			 
			 terminaldao= new TerminalDAO();
			 
			 Timer timer=new Timer();
			 timer.schedule(new TimerTask(){
				 int j=0;
				 public void run() {
					if(j==0){
						j=1;
					    terminaldao.updateCntIsLink2(3,1); //1 为在线，3为 断开
					    j=0;
					 }
				 }
			 }, 20000,15000);
			
			timerlist.add(timer);
			
	         //第一次启动获取数据库所有终端信息
			terminalMap=terminaldao.getALLTerminalMapEnty("");  ///服务启动获取所有终端信息
			
			RESPONSE_INTERFACE_TERMINALMAP=terminaldao.getALLTerminalMapEnty(""); 
			
			terminaldao.updateModuleTempByTemplateIdForAlphaField();
			
			Timer timer4=new Timer();
			//定时更新缓存里面的数据到数据库
			timer4.schedule(new TimerTask(){
	//			int i=0;
				int j=0;
				@Override
				public void run() {
					if(null!=terminalMap){
						if(j==0){
						  j=1;
						  terminaldao.updateCntstatus(terminalMap);
						  j=0;
						}
					}
	//				//康辉会展
	//				if(++i==6){
	//					i=0;
	//					new Thread(new Runnable(){
	//						public void run() {
	//							opexcelutils.createKHXMLServices(new StringBuffer().append(projectpath).append("serverftp/program_file/").toString(),
	//									new StringBuffer().append(projectpath).append("serverftp/program_file/kh-excel/").toString());
	//						}
	//					}).start();
	//				}
				}
			}, 40321,60321);//1分多更新缓存内的数据到数据库
			
			timerlist.add(timer4);
			
			//监听定时开机,发广播-- 需要网卡支持（在Windows系统下使用）
	//		Timer timer5=new Timer();
	//		timer5.schedule(new TimerTask(){ 
	//			@Override
	//			public void run() {
	//				if(terminalMap!=null){
	//					terminaldao.actionTerminal(terminalMap);
	//				}
	//			}
	//		}, 13534,30000);
	//		timerlist.add(timer5);
			///////////////////////////////////
				//开启Apache FTP 服务器
				//String []args={};
				//CommandLine.startFTP(args);
				///*****************************
			    DBConnection dbconnection=new DBConnection();
			    Connection conn=dbconnection.getConection();
			 
				WeeHoursCreateProjectXmlObserver weehourscreateprojectxmlobserver=new WeeHoursCreateProjectXmlObserver();
				final WeeHoursCreateProjectXmlObservable weehourscreateprojectxmlobservable= new WeeHoursCreateProjectXmlObservable();
				weehourscreateprojectxmlobservable.addObserver(weehourscreateprojectxmlobserver);
				utildao.upeateinfo(conn," cnt_islink=3,cnt_nowProgramName='无',cnt_playstyle='CLOSE' "," 1=1 ","xct_ipaddress");  ///启动时把所有连接的终端改成断开
				//utildao.deleteinfo1(common_conn,"1=1", "xct_cnt_response");
				
				/////解除所有锁定节目
				Map map=UtilDAO.getMap();
				map.put("1","1");
				map.put("program_ISloop","0");
				utildao.updateinfo(conn,map,"xct_JMPZ");
				
				dbconnection.returnResources(conn);
				
	//			监听时间,到时到点生成第二天节目单
				
				Timer timer1=new Timer();
				timer1.schedule(new TimerTask(){
	//				String notimedate=UtilDAO.getNowtime("yyyy-MM-dd");
					boolean flag=true;
					boolean flagtemp=true;
					public void run() {	
					
						if(flagtemp){//服务开启时生成节目单，防止在晚上关机的时候出现不生成节目
							flagtemp=false;
							weehourscreateprojectxmlobservable.createMacXml();
							weehourscreateprojectxmlobservable.notifyObservers();
						}
						//======================================================
	//					if(getHour()==6&&getMinute()>50){
	//						killPro("cmd.exe");
	//					}
						
						//================如下暂时注释===========================
						if(getHour()==23&&getMinute()==50){
							flag=true;
							ConstantUtil.CLIENTCMDMAP.clear();
							//-------------------------------------
							client.setKillboolean(false);
							sleepSys(2000);
	//						cancelTimer();
	//						sleepSys(2000);
							restartXctService();//晚上自动重启服务 (可用), 有时重启此服务，好像受权限限制了
						}
						//========================================================
						if(getHour()==0&&getMinute()==5&&flag){//凌晨时 日期变化 ，生成客户端节目单（MAC.xml）ConstantUtil.CLIENTCMDMAP.clear();
							flag=false;
							logger.info(".................准备生成第二天的节目单......................");
							weehourscreateprojectxmlobservable.createMacXml();
							weehourscreateprojectxmlobservable.notifyObservers();
	//						notimedate=UtilDAO.getNowtime("yyyy-MM-dd");
							
	//						getTrainBusPlane();
						}
					}
				}, 61234,54321);
				
				timerlist.add(timer1);
				
				 final ManagerProjectDao managerprojectdao=new ManagerProjectDao();
	//			监听插播节目,到时到点删除插播节目  ,重庆南丁总部 改成点播，不监听了
				Timer timer2=new Timer();
				timer2.schedule(new TimerTask(){
	//				String notimedate=UtilDAO.getNowtime("yyyy-MM-dd");
					String  day="";
					public void run() {
						int hour=getHour();
						if(hour>6&&hour<23){
							if(!Util.INSER_PROGRAM_LIST.isEmpty()){
								if(Util.INSER_PROGRAM_OP==0){
									Util.INSER_PROGRAM_OP=1;
									//查询当天所有插播节目
									List<ClientProjectBean> inser_clientprojectbeanlist= managerprojectdao.getClientForDeleteInsertProject(1);
									for(ClientProjectBean clientprojectbean :inser_clientprojectbeanlist){
										day= UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
										if(day.equals(clientprojectbean.getEnddate())||day.compareTo(clientprojectbean.getEnddate())>0){//比现在时间小的
											 managerprojectdao.deleteInsertProject(clientprojectbean.getJmurl());
											 logger.info(MessageFormat.format(".......删除插播节目单:{0}", clientprojectbean.toString()));
											 Util.INSER_PROGRAM_LIST.remove(new StringBuffer()
													 .append(clientprojectbean.getProgram_delid()).append("_").append(clientprojectbean.getJmurl()).toString());
										}
									}
									Util.INSER_PROGRAM_OP=0;
								}
							}
						}
					}
				}, 21200,25146);
				timerlist.add(timer2);
		//----------------------------------------------------------------------
				
				
				/*Timer timer3=new Timer();
				timer3.schedule(new TimerTask(){
					int i=0;
					@Override
					public void run() {
						++i;
						if(i<4){
							if(ipaddress.equals("127.0.0.1")||ipaddress.equals("0.0.0.0")){
								try {
									inet = InetAddress.getLocalHost();
									ipaddress= inet.getHostAddress();//获取ip地址
								} catch (Exception e) {
									//e.printStackTrace();
									ipaddress="127.0.0.1";
								}
							}
							//局域网测试，广域网要改掉
	//						
	//						System.out.println("端口---->"+Util.localport);
						}else{
							i=6;
						}
						//局域网测试，广域网要改掉
						if(null!=client.getMap()&&!client.getMap().isEmpty())
						   client.allsend("255.255.255.255", "supercommand");
					}
				}, 21370,12345);*/
				
				/////////////////////////////////沙特馆定时发送LED显示屏滚动文字
				/*Timer timer8=new Timer();
				timer8.schedule(new TimerTask(){
					//private int n=0;
					public void run() {
						//led.sendLedInfo(led.getALLLedMap(""));
						 //SaudiHttpClient.sendTicketInfo();
					}
				}, 14262,120000);*/
				
				restarbatString=new StringBuffer().append(projectpath).append("register/restart_xct.bat").toString();
				File restartFile =new File(restarbatString);
				if(!restartFile.exists()){
					String string="net stop XCT3\r\nnet start XCT3\r\nexit";
					writeFiletoSingle(restartFile.getPath(),string,false,false);
				}
				
				client=new Client();////启动UDP线程
				
				/////启动时发UDP包，改主机
	//			String ftpstring="xu0005"; //FTP 检查
	//			String ftpstring="xu0021@"+ipaddress+"-001"+"-001"+"-21"; //FTP 检查
	//			client.allUDPsend("255.255.255.255",ftpstring);//86终端广播，过时不用
				
	//			client.allUDPsend("255.255.255.255","ip:ip地址:端口");//android终端广播，ip:服务端地址:服务端端口
				///////////////////////////////
				
				final Timer timer8=new Timer();
				timer8.schedule(new TimerTask(){
					public void run() {
						updateFtpCollocation();//修改FTP配置和启动FTP
						timer8.cancel();
					}
				}, 10000,10000);
				
				INITSTART=1;
				logger.info("------------------->信息发布服务启动。。。。");
	//			logger2.fatal("-----------fatal-------------->服务启动。。。。"); //写入window事件日志,有点问题
				/////////////如下为客户定制启动//////////////////
				
	//			getTrainBusPlane();
	//			KingPublicHealthAccessDBManager.ManagerICCardInfo();//金山公共卫士
				
				getCourtInfos();//潍坊法院获取华夏法庭数据
				
				//=================================================================
				if(System.getProperty("SENDTYPE").equals(ConstantUtil.PNP)){
					listenClientCmdIsCanel();
				}
		  }
	}
	
	
	public void sleepSys(long l){
		try {
			Thread.sleep(l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
   public void initWorkhomeParm(String filepathtmp){
    	String macdirectory="";
    
    	 if(new File(filepathtmp).exists()){
		     macdirectory=filepathtmp;
    	 }else
			 macdirectory=new File(new File(FirstStartServlet.class.getResource("/").getPath()).getParent()).getParent()+"/";
    	 
        System.setProperty("WEB_HOME", macdirectory);
        System.setProperty("MAC_HOME", new StringBuffer().append(System.getProperty("WEB_HOME")).append("serverftp/program_file/").toString());
        System.setProperty("MAC_HOME_TEMP", new StringBuffer().append(System.getProperty("WEB_HOME")).append("serverftp/program_file/mactemp/").toString());
        File macdirectoryfile=new File(System.getProperty("MAC_HOME"));
		if(!macdirectoryfile.exists())
			macdirectoryfile.mkdirs();
		
		 File macdirectoryfiletemp=new File(System.getProperty("MAC_HOME_TEMP"));
		 if(!macdirectoryfiletemp.exists())
			macdirectoryfiletemp.mkdirs();
	} 
	
	public void opConfSetPropertise(){
		Properties props = new Properties();
		InputStream is;
		try {
			is = new BufferedInputStream(new FileInputStream(new StringBuffer().append(projectpath).append("confset.properties").toString()));
			props.load(is);
			if (is != null)
				is.close();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		 System.setProperty("SENDTYPE", props.getProperty("sendtype").trim());//通信方式：HTTP,UDP,PNP
		 System.setProperty("ISAUDITING", props.getProperty("isauditing").trim());//是否节目审核 ,0 为 不审核，1 需要审核
		 System.setProperty("ISPOINTPLAY", props.getProperty("ispointplay").trim());//是否点播 ,0 为 不显示，1 需要显示
		 
		 ServletContext application=this.getServletContext();   
         application.setAttribute("isauditing", System.getProperty("ISAUDITING"));
         application.setAttribute("ispointplay", System.getProperty("ISPOINTPLAY"));

	}
	
	Timer clientcmdtimer;
	
	public void listenClientCmdIsCanel(){
		
		clientcmdtimer=new Timer();
		//定时更新缓存里面的数据到数据库
		clientcmdtimer.schedule(new TimerTask(){
//			int hour=getHour();
			@Override
			  public void run() {
				new Thread(new Runnable(){
					public void run() {
	//						hour=getHour();
						SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			            //指令超过50秒处理和未到50秒的处理
			            if(null!=ConstantUtil.CLIENTCMDMAP&&ConstantUtil.CLIENTCMDMAP.size()>0){
			            	 
			   			      for (Map.Entry<String, ConcurrentHashSet<ClientCmd>> entry : ConstantUtil.CLIENTCMDMAP.entrySet()){
			   			    	 ConcurrentHashSet<ClientCmd> clientcmdset=entry.getValue();
			   				      for(ClientCmd clientcmd:clientcmdset){
			   				    	  
				   					  if((clientcmd.getIscancel()==0&&clientcmd.getIsoperator()==0)
				   							  &&System.currentTimeMillis()-clientcmd.getIsonlinetime()>=(40*1000)){//50秒内没处理，设置命令撤销
				   						  
				   						  clientcmd.setIscancel(1);// 设置命令撤销
	//			   						  logger.info(MessageFormat.format("***当前时间{0}******终端 {1} 取消指令：{2} ",
	//			   						  			sDateFormat.format(new Date()), entry.getKey(),DESPlusUtil.Get().decrypt(clientcmd.getCmd())));
				   					  }
			   				      }
			   				 }
			   			 //========================================================
			   			 //查出已取消和已执行的指令，为了删除此无效指令。
			   			      List<ClientCmd>clientcmdlist=new ArrayList<ClientCmd>();
			            	  for (Map.Entry<String, ConcurrentHashSet<ClientCmd>> entry : ConstantUtil.CLIENTCMDMAP.entrySet()){
			   				      Set<ClientCmd> clientcmdset=entry.getValue();
			   				      for(ClientCmd clientcmd:clientcmdset){
				   					  if((clientcmd.getIscancel()==1||clientcmd.getIsoperator()==1)){
				   						clientcmdlist.add(clientcmd);
				   					  }
			   				      }
			            	  }
			            	  
			            	  for (ClientCmd clientCmd2 : clientcmdlist) {
			            		  boolean bool=false;
			            		  for (Map.Entry<String, ConcurrentHashSet<ClientCmd>> entry : ConstantUtil.CLIENTCMDMAP.entrySet()){
			            			  Set<ClientCmd> clientcmdset=entry.getValue();
			            	    	  if(null!=clientCmd2){
				            	    	  for(ClientCmd clientcmd : clientcmdset){
				            	    		  if(clientcmd.equals(clientCmd2)
				            	    				  &&(clientCmd2.getIscancel()==1||clientCmd2.getIsoperator()==1)){
				            	    			  
	//												logger.info(MessageFormat.format("&&&&&&&&当前时间{0}******终端 {1} 删除【已超时/已执行】指令：{2} ",
	//					   						  			sDateFormat.format(new Date()), clientcmd.getIpmac(),DESPlusUtil.Get().decrypt(clientcmd.getCmd())));
													ConstantUtil.CLIENTCMDMAP.get(entry.getKey()).remove(clientcmd);
													bool=true;
													break;
											  }
				            	    	  }
				            	    	  if(bool){
				            	    		  break;
				            	    	  }
									  }
			            	      }
			            	 }
			            }
			   		}
				}).start();
			}
		}, 10321,5321);
		timerlist.add(clientcmdtimer);
	}
	
	Timer courttimer;
	
	public void getCourtInfos(){ //潍坊法院获取华夏法庭数据
		
		if(new File(System.getProperty("WEIFANGCOURT_HOME")).exists()){
			final WeiFangCourtDao weifangcourtdao=new WeiFangCourtDao();
			courttimer=new Timer();
			//定时更新缓存里面的数据到数据库
			courttimer.schedule(new TimerTask(){
				int hour=getHour();
				@Override
				public void run() {
					new Thread(new Runnable(){
						public void run() {
							hour=getHour();
							if (hour>7&&hour<18){
						       weifangcourtdao.getAllCourtInfo();
							}
						}
					}).start();
				}
			}, 120321,18321);
			timerlist.add(courttimer);
		}
	}
	
    List<Timer>timerlist=new ArrayList<Timer>();
	
	public void cancelTimer(){
		for (Timer timer : timerlist) {
			if(null!=timer){
				timer.cancel();
			}
		}
	}
	
	public static String restartXctService() { 
		
		File file=new File(restarbatString);
			if(file.exists()){
				logger.info(new StringBuffer().append(" ....自重启服务: ").append(file.getPath()).toString());
				try {
					Runtime.getRuntime().exec(new StringBuffer().append("cmd /c start  ").append(file.getPath()).toString());
				} catch (IOException e) {
					e.printStackTrace();
				} 
				return "ok";
			  }
			return "error";
	}
	
    public static void killPro(String commd){
		
		try {
			Runtime.getRuntime().exec(new StringBuffer().append("taskkill /f /im ").append(commd).toString());
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void CreateOfficeDesktop(){
		
		File file64=new File(windowsdesktop64);
		if(!file64.exists())
			file64.mkdirs();
		File file32=new File(windowsdesktop32);
		if(!file32.exists())
			file32.mkdirs();
	}
	
	 public static  void writeFiletoSingle(String file, String str, boolean flag,boolean newline) {
			
			File f = new File(file);
			if (!f.exists()) {
				File t = f.getParentFile();
				if (!t.exists())
					t.mkdir();
			}
			FileWriter fw;
			try {
				fw = new FileWriter(file, flag);
				if (newline && flag)
					fw.write("\r\n");
				fw.write(str);
				if (!flag && newline)
					fw.write("\r\n");
				fw.close();
			} catch (IOException e) {}
	}
	
	public void getTrainBusPlane(){//盐城获取汽车，火车，飞机时刻表 
//		try {
//			Runtime.getRuntime().exec(System.getProperty("java.home") + "/bin/java -jar "+FirstStartServlet.projectpath+"bustrainplane/bustrainplane.jar");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public static void getUSD(){
		new Thread(new Runnable(){
				public void run() {
					/////////////////////////获取美元指数图片/////////////////////////////////
					String url ="http://image.cngold.org/chart/forex/usd_72_zhishu.png";
			        //String ss=url.substring(url.lastIndexOf("/"));
			        String imagepath=new StringBuffer().append(FirstStartServlet.projectpath).append("images/usd.png").toString();
			    	new DownImageFromNet().getImagmeFromNet(url,imagepath);
			    	//png转jpg 图片
			    	//DownImageFromNet.png2jpg(imagepath+ss,imagepath+"usd.jpg");
			    	logger.info(new StringBuffer().append("获取美元指数图片--->").append(imagepath).toString());
				}
			}).start();
	}
	
	static int hour;
	static int temp;
//	public  void createWeather(){
//		Timer weathertimer=new Timer();
//		weathertimer.schedule(new TimerTask(){
//			boolean flag=true;
//			String cityString="上海#上海";
//			String []citystringarray=null;
//			@Override
//			public void run() {
//				hour =getHour();
//				if(flag||hour>=6&&hour<19){
//					flag=false;
//					if(!utildao.isExistTable("weather_city")){//不存在表时，建表，插数据
//						utildao.createTable("weather_city", getFileValues(projectpath+"admin\\weather_citysql.txt"));
//					}
//					if(utildao.isExistTable("weather_city")){
//						File cityweather=new File(projectpath+"serverftp\\program_file\\weather_city.txt");
//						cityString=Util.getFileValues(cityweather.getPath());
//						
//						if("".equals(cityString)||("NO".equals(cityString))){
//							if(!cityweather.exists()){
//								try {
//									cityweather.createNewFile();
//									writeFiletoSingle(cityweather.getPath(),"上海#上海",false,false);
//								} catch (IOException e) {
//									e.printStackTrace();
//								}
//							}
//						}
//						cityString=(("".equals(cityString)||("NO".equals(cityString)))?"上海#上海":cityString);
//						citystringarray=cityString.split("#");
//						ShangHaiWeatherCommonage.getShWeather(projectpath+"serverftp\\program_file\\",citystringarray[0],citystringarray[1]);//旧版天气预报
//					}else {
//						ShangHaiWeatherCommonage.getShWeather(projectpath+"serverftp\\program_file\\");//旧版天气预报
//					}
//	//				HuiYaWorldWeatherCom.getHuaYaEWorldWeather();//汇亚世界天气
//	//				if(hour<=7||temp==0){
//	//					temp=1;
//	////					局域网测试，广域网要改掉
//	//					client.allsend("255.255.255.255","supercommand"/*"xu888getweather"*/);
//				}
//			}
//		},10000,3631450);//1个小时获取一次天气预报
//	}
	
	public void closeServUDaemon(){
		try {
			 Process p = Runtime.getRuntime().exec("taskkill /f /im ServUDaemon.exe"); 
			 try {
				p.waitFor();
			 } catch (InterruptedException e) {
				e.printStackTrace();
			 } 
		     p.destroy(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getIpaddress(){
		 String iptxt=new StringBuffer().append(projectpath).append("/ip.txt").toString();
		 File ipfile=new File(iptxt);
		 if(!ipfile.exists()){
			 try {
				ipfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		 try {
			inet = InetAddress.getLocalHost();
			ipaddress= inet.getHostAddress();//获取ip地址 ,双网卡如何得知是和终端同一个网段？
		 } catch (Exception e) {
			 ipaddress="127.0.0.1";
			 System.out.println("获取本机IP地址出错！");
		 }
		 if(!ipaddress.equals("127.0.0.1")&&!ipaddress.equals("0.0.0.0"))
			 Util.writeFiletoSingle(iptxt, ipaddress, false, false);
		 else
			 ipaddress= Util.getFileValues(iptxt);
//		 System.out.println("ipaddress------>"+ipaddress);
	}
	
	public boolean readServUDaemon() {

		Process p;
		BufferedReader input=null;
		try {
//			if(LFactroy.newInstance().isLinux())
//			   p = Runtime.getRuntime().exec("ps -a");
//			else
			   p = Runtime.getRuntime().exec("tasklist");

			input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = " ";
			while ((line = input.readLine()) != null) {
//				System.out.println( "---------->"+line+"<--");
				if (line.indexOf("ServUDaemon") != -1) {
					return false;
				}
			}
			input.close();
			if(p!=null)
				p.destroy();
		} catch (Exception e) {
			logger.error("error \"ps  -a \"");
			e.printStackTrace();
		}finally{
			if(input!=null)
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return true;
	}
	
	public void copyDLL(){
//		 String javahome="C:\\Program Files\\Java\\jdk1.6.0_18\\bin\\";
		String javahome=new File(System.getProperty("java.home")).getParentFile().getPath();
		logger.info(new StringBuffer().append("---1------java_home----->").append(javahome).toString());
		
		if(javahome.indexOf("jdk")==-1){
			File[]files=new File(javahome).listFiles();
			String filetemp="";
			for(File file:files){
				filetemp=new StringBuffer().append(file.getPath()).append("/").toString();
				if(filetemp.indexOf("jdk")!=-1){
					javahome=new StringBuffer().append(filetemp).append("bin/").toString();
					break;
				}
			}
		}else{
			javahome=new StringBuffer().append(javahome).append("/bin/").toString();
		}
		logger.info(new StringBuffer().append("---2------java_home----->").append(javahome).toString());

		 String win32="C:/WINDOWS/system32/";
		 String win64= "C:\\Windows\\SysWOW64\\";
		 String dll_path=new StringBuffer().append(projectpath).append("dll/").toString();
		 
		 File file = new File(dll_path);
		 if (!file.exists()) {
			 file.mkdirs();
		 }
		 List filelist=Util.getFileByFilepath(dll_path);
		 if(filelist!=null){
			 for(int i=0;i<filelist.size();i++){
				 if(!new File(new StringBuffer().append(javahome).append(filelist.get(i)).toString()).exists()){
					 Util.copyFile(new StringBuffer().append(dll_path).append(filelist.get(i)).toString(), new StringBuffer().append(javahome).append(filelist.get(i)).toString());
				 }
				 if("Dogjava.dll".equals(filelist.get(i))||"jacob.dll".equals(filelist.get(i))
						 ||"jacob-1.16-x64.dll".equals(filelist.get(i))||"jacob-1.16-x86.dll".equals(filelist.get(i))
						 ||"ListenPlayDll.dll".equals(filelist.get(i))||"TransBin.dll".equals(filelist.get(i))
						 ||"jmupdf32.dll".equals(filelist.get(i))||"jmupdf64.dll".equals(filelist.get(i))
						 /*||"NTEventLogAppender.dll".equals(filelist.get(i))||"NTEventLogAppender.amd64.dll".equals(filelist.get(i))*/){
					 
					 if(!new File(win32+filelist.get(i)).exists()){
						 Util.copyFile(new StringBuffer().append(dll_path).append(filelist.get(i)).toString(), new StringBuffer().append(win32).append(filelist.get(i)).toString());
					 }
					 if(!"Dogjava.dll".equals(filelist.get(i))&&!new File(new StringBuffer().append(win64).append(filelist.get(i)).toString()).exists()){
						 Util.copyFile(new StringBuffer().append(dll_path).append(filelist.get(i)).toString(), new StringBuffer().append(win64).append(filelist.get(i)).toString());
					 }
				 }
			 }
		 }
	}
	
	public void updateFtpCollocation(){//修改FTP配置文件 
		
		 String cmd=projectpath+"Serv-U\\ServUDaemon.exe";
		 String cmd2=projectpath+"Serv-U\\ServUTray.exe";
		 if(new File(cmd).exists()&&readServUDaemon()){
			 String servuinifile=new StringBuffer().append(projectpath).append("Serv-U/ServUDaemon.ini").toString();
			  try {
				String ipaddresstmp=InIConfigurationFile.getProfileString(new StringBuffer().append(projectpath).append("Serv-U/ServUDaemon.ini").toString(), "DOMAINS", "Domain1", new StringBuffer().append(ipaddress).append("||21|xct|1|0|0").toString());
				ipaddresstmp=ipaddresstmp.replace(ipaddresstmp.substring(0,ipaddresstmp.indexOf("|")), ipaddress);
				InIConfigurationFile.setProfileString(servuinifile, "DOMAINS", "Domain1", ipaddresstmp);
				InIConfigurationFile.setProfileString(servuinifile, "Domain1", "User1", "001|1|0");
				
				InIConfigurationFile.setProfileString(servuinifile, "USER=001|1", "Password", "ws8B05DB5A22BDB5877F37E20F2558230C");
				InIConfigurationFile.setProfileString(servuinifile, "USER=001|1", "HomeDir", new StringBuffer().append(projectpath).append("serverftp\\program_file").toString());
				
				InIConfigurationFile.setProfileString(servuinifile, "USER=001|1", "RelPaths", "1");
				InIConfigurationFile.setProfileString(servuinifile, "USER=001|1", "AlwaysAllowLogin", "1");
				InIConfigurationFile.setProfileString(servuinifile, "USER=001|1", "PasswordLastChange", new Date().getTime()+"");
				InIConfigurationFile.setProfileString(servuinifile, "USER=001|1", "TimeOut", "600");
				InIConfigurationFile.setProfileString(servuinifile, "USER", "Access1",new StringBuffer().append(projectpath).append("serverftp\\program_file|RWAMLCDP").toString());
			 } catch (IOException e) {
				 logger.error(new StringBuffer().append("修改FTP 配置文件出错---->").append(e.toString()).toString());
				 e.printStackTrace();
			 }
			 try {
				 Runtime.getRuntime().exec(new StringBuffer().append(cmd2).append("\n").toString());//启动Ser-U 托盘
			    //如下为Serv-U注册为服务的命令
	//			 /g = start server (starts service if installed as service on NT/2000/XP/2003)
	//			 /s = stop server
	//			 /i = install server as a system service (NT/2000/XP only)
	//			 /d = remove server as a system service (NT/2000/XP/2003 only)
				 Runtime.getRuntime().exec(new StringBuffer().append(cmd ).append(" /i").append( "\n").toString());//Serv-U 注册为系统服务
				 Runtime.getRuntime().exec(new StringBuffer().append(cmd ).append(" /g").append( "\n").toString());//启动 Serv-U 服务
				 Runtime.getRuntime().exec(new StringBuffer().append(cmd ).append( "\n").toString());//启动Ser-U 
			 }catch (IOException e) {
				 logger.error(new StringBuffer().append("启动FTP服务出错---->").append(e.toString()).toString());
				 e.printStackTrace();
			 }
		 }
	}
	
	static int getHour() {
		return ((GregorianCalendar) GregorianCalendar.getInstance()).get(Calendar.HOUR_OF_DAY);
	}
    
	int getMinute() {
			return ((GregorianCalendar) GregorianCalendar.getInstance())
					.get(Calendar.MINUTE);
	}
	
	public static String getFileValues(String url) {
		File fl = new File(url);
		if (fl.exists()) {
			StringBuffer strBuffer = new StringBuffer();
			FileReader fileReader = null;
			String currentLine = null;
			BufferedReader bufferedReader =null;
			try {
				fileReader = new FileReader(fl);
				bufferedReader = new BufferedReader(fileReader);
				while ((currentLine = bufferedReader.readLine()) != null) {
					strBuffer.append(currentLine);
				}
				return strBuffer.toString().trim();
			} catch (Exception e) {
                e.printStackTrace();
				logger.error("load file is error!");
			}finally {
				if(null!=bufferedReader) {
					try {
						bufferedReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return "";
    }
	
	public void destroy() {
		super.destroy(); 
	}
}
