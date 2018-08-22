package com.xct.cms.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import com.xct.cms.dwr.DwrClass;
import com.xct.cms.observer.WeeHoursCreateProjectXmlObserver;
import com.xct.cms.xy.dao.ManagerProjectDao;
import com.xct.cms.xy.domain.ClientProjectBean;

public class CreatMacXmlUtils {

	  Logger logger = Logger.getLogger(CreatMacXmlUtils.class);
	
	  public void createProjectMacXml(String macfile,String mac,int jmappid,String playname,String directoryname,
			                          String starttime,String endtime,int playtype,String count){
		  
		  logger.info(MessageFormat.format("---CreatMacXmlUtils.createProjectMacXml--：{0},{1},{2},{3},{4},{5},{6}",mac,playname,directoryname,starttime, endtime, playtype,new File(macfile).getName()));
		  
		  WeeHoursCreateProjectXmlObserver over=new WeeHoursCreateProjectXmlObserver();
		  
//		  ManagerProjectDao managerprojectdao=new ManagerProjectDao();
		  
          //////////////////////////////////--以下是晚上定时生成节目单的方法--///////////////////////////////
		  //加入插播，定时，循环节目
//		  List<ClientProjectBean>clientprojectbeanlist=managerprojectdao.createMacXmlOnWeeHours(/*new SimpleDateFormat("yyyy-MM-dd").format(new Date()),*/true);
//		  Util.deleteFile(macfile);
//		  for(ClientProjectBean clientprojectbean:clientprojectbeanlist){
//			   if(mac.equals(clientprojectbean.getPlayclient())){
//					File newmacfile=new File(macfile);
//					if(!newmacfile.exists()){
//						over.createMacXml(newmacfile.getPath(),clientprojectbean);
//					}else{
//						over.addMacXml(newmacfile.getPath(),clientprojectbean);
//					}
//			   }
//		  }
//		  
//		  //加入永久循环节目
//		  List<ClientProjectBean> clientprojectbeanlistforerver=managerprojectdao.createMacXmlOnWeeHours(/*new SimpleDateFormat("yyyy-MM-dd").format(new Date()),*/false);/*createMacXmlOnWeeHoursForeverProject();*/
//		  for(ClientProjectBean clientprojectbean:clientprojectbeanlistforerver){
//			  
//			   if(mac.equals(clientprojectbean.getPlayclient())){
//					File newmacfile=new File(macfile);
////					String datestring=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//					if(!newmacfile.exists()){
////						clientprojectbean.setSetdate(datestring+" "+clientprojectbean.getSetdate().split(" ")[1]);
////						clientprojectbean.setEnddate(datestring+" "+clientprojectbean.getEnddate().split(" ")[1]);
//						over.createMacXml(newmacfile.getPath(),clientprojectbean);
//					}else{
////						clientprojectbean.setSetdate(datestring+" "+clientprojectbean.getSetdate().split(" ")[1]);
////						clientprojectbean.setEnddate(datestring+" "+clientprojectbean.getEnddate().split(" ")[1]);
//						over.addMacXml(newmacfile.getPath(),clientprojectbean);
//					}
//			   }
//			}
		  //////////////////////////////////--以上是晚上定时生成节目单的方法--///////////////////////////////
		  
		    String	[]setdatetime=(starttime.split(" ")[0]).split("-");
		    String  []enddatetime=(endtime.split(" ")[0]).split("-");
		    String	startdatetemp=(starttime.split(" ")[1]);
		    String	enddatetemp=endtime.split(" ")[1];
		    String datetmp;
		    ClientProjectBean cb;
		    File newmacfile;
		    if(playtype==3){//永久循环
					datetmp=UtilDAO.getNowtime("yyyy-MM-dd");
					cb=new ClientProjectBean();
					cb.setName(playname);
		    		cb.setJmurl(directoryname);
		    		cb.setSetdate(datetmp+" "+startdatetemp);
		    		cb.setEnddate(datetmp+" "+enddatetemp);
		    		cb.setIsloop(playtype+"");
		    		cb.setPlaytype(cb.getPlaytypebyIsloop(playtype));
		    		cb.setPlaysecond(count);
		    		cb.setForoverloop("y");
		    		cb.setJmappid(jmappid);
		    		newmacfile=new File(macfile);
		    		if(!newmacfile.exists()){
		    		    over.createMacXml(macfile,cb);
		    		}else{
		    		    over.addMacXml1(macfile,cb);//永久循环
		    		}
		    }else{
					 //请注意月份是从0-11
			        Calendar start = Calendar.getInstance();
		//	        start.set(2009, 1, 28);
			        start.set(Integer.parseInt(setdatetime[0]), Integer.parseInt(setdatetime[1])-1, Integer.parseInt(setdatetime[2]));
			        
			        Calendar end = Calendar.getInstance();
		//	        end.set(2009, 2, 25);
			        end.set(Integer.parseInt(enddatetime[0]), Integer.parseInt(enddatetime[1])-1, Integer.parseInt(enddatetime[2]));
			        
			        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			        String everday="";
			       
		//	        String isloop="0";
//			        Calendar todaycalendar = Calendar.getInstance();
			        
			        int i=0;
			        while(start.compareTo(end) <= 0){
			        	
//			        	logger.info("---CreatMacXmlUtils.createProjectMacXml--->(start.compareTo(todaycalendar)>=0),if it is true , going-->"+(start.compareTo(todaycalendar)>=0)+"__播放类型(1为插播): "+playtype);
			        	//playtype= 0 为循环，1 为插播，2 为定时
			        	if(playtype==0||playtype==1||playtype==2/*||start.compareTo(todaycalendar)>=0*/){ 
				        	    if(playtype==1){//加入插播节目
				        	    	logger.info(MessageFormat.format("******CreatMacXmlUtils.createProjectMacXml***有插播节目:{0},{1}", mac,directoryname));
				        	    	Util.INSER_PROGRAM_LIST.addIfAbsent(new StringBuffer().append(mac).append("_").append(directoryname).toString());
				        	    }
			        		
					        	++i;
					        	if(i>=31){
					        		break;
					        	}
					        	everday=format.format(start.getTime());
					            //打印每天    
					        	  cb=new ClientProjectBean();
				//	    		  if(playtype.equals("loop"))
				//	    			  isloop="0"; 
				//	    		  if(playtype.equals("insert"))
				//	    			  isloop="1";
				//	    		  if(playtype.equals("active"))
				//	    			  isloop="2";
					    		  
					    		  cb.setName(playname);
					    		  cb.setJmurl(directoryname);
					    		  
					    		  cb.setSetdate(new StringBuffer().append(everday).append(" ").append(startdatetemp).toString());
					    		  cb.setEnddate(new StringBuffer().append(everday).append(" ").append(enddatetemp).toString());
					    		  
					    		  cb.setIsloop(playtype+"");
					    		  cb.setPlaytype(cb.getPlaytypebyIsloop(playtype));
					    		  cb.setPlaysecond(count);
					    		  cb.setForoverloop("n");
					    		  cb.setJmappid(jmappid);
					    		  newmacfile=new File(macfile);
					    		  if(!newmacfile.exists()){
					    			 over.createMacXml(macfile,cb);
					    		  }else{
					    			 over.addMacXml(macfile,cb);
					    		  }
					        }
			                start.set(Calendar.DATE, start.get(Calendar.DATE) + 1); //循环，每次天数加1
			          }
		      }
	  }
	  
	  //获得根据当天的日期计算N个工作日后日期是哪天
      public static  Date getAfterDate(int n){ 
		  Calendar c = Calendar.getInstance(); 
		  c.add(Calendar.DAY_OF_MONTH, n); 
		  return c.getTime(); 
	  }
	  
	  public void addProjectMacXml(String macfile,String playname,String directoryname,
              String starttime,String endtime,String playtype,String count){
		
		       try {
					SAXReader saxreader =new SAXReader();
					Document document=saxreader.read(macfile);
					List elmentList = document.selectNodes("/project/list");
					boolean bool=true;
					for(int i=0;i<elmentList.size();i++){
						Element element=(Element)elmentList.get(i);
						String directName=element.attributeValue("directName");
						String beginTimer=element.attributeValue("beginTimer");
						if(directName.equals(directoryname) && beginTimer.equals(starttime)){
							bool =false;
						}
					}
					if(bool){
						Element elment=document.getRootElement();
						elment.addElement("list")
						.addAttribute("userName",playname)
						.addAttribute("directName",directoryname)
						.addAttribute("beginTimer",starttime)
						.addAttribute("endTimer",endtime)
						.addAttribute("type",playtype)
						.addAttribute("length", count);
						try {
							OutputFormat format = OutputFormat.createPrettyPrint();
							format.setEncoding("GBK");
							XMLWriter output = new XMLWriter(new FileWriter(new File(macfile)), format);
							output.write(document);
							output.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
			   } catch (DocumentException e) {
				  e.printStackTrace();
			   }
	 }

	  
}
