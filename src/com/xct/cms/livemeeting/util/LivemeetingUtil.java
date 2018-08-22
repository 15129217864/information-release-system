package com.xct.cms.livemeeting.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.linsoft.xml.dom4j.Dom4j;
import com.xct.cms.domin.InspectionQuarantineBean;
import com.xct.cms.servlet.FirstStartServlet;

public class LivemeetingUtil {

public void saveXML(List<ProgramDirectName> panelList,String xmlfilepath) {
		
		String filepath =xmlfilepath+"lh-notice.xml";
		
		File workHome = new File(filepath);
		if (!workHome.exists()){
			try {
				workHome.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		Document document = DocumentHelper.createDocument();

		Element booksElement = document.addElement("notice");

		booksElement.addAttribute("time","时间")
		        .addAttribute("address", "地点")
				.addAttribute("meetingname","会议名")
				.addAttribute("meetingcontext","会议内容")
				.addAttribute("department", "出席部门")
				.addAttribute("createname", "创建人")
				.addAttribute("hand_dept", "标示");
				
				
		for (int i = 0; i < panelList.size(); i++) {
			
			ProgramDirectName beans = panelList.get(i);

			Element element = booksElement.addElement("row");
		
			writePaneBeanXML(element,beans);
		}
		XMLWriter writer;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("gbk");
		try {
			writer = new XMLWriter(new FileWriter(filepath), format);
			writer.write(document);
			writer.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	
   public static Element writePaneBeanXML(Element element, ProgramDirectName bean) {
		
		element.addAttribute("time", bean.getDatetimer());
		element.addAttribute("address", bean.getProgramaddress());
		element.addAttribute("meetingname", bean.getProgrammeetingname());
		element.addAttribute("meetingcontext", bean.getProgrammeeting());
		element.addAttribute("department",bean.getProgramdept()==null?" ":bean.getProgramdept());
		element.addAttribute("createname", bean.getCreatename());
		element.addAttribute("hand_dept", bean.getHand_dept()==null?"1":bean.getHand_dept());
		return element;
	}
   
   public Map<String,String>getAllMeetingRoom(){
		
		Map<String,String> map=new HashMap<String,String>();
		String sql="select room_name from meeting_room";
		Connection con = getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				map.put(rs.getString("room_name"),rs.getString("room_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
   
   
// -----------以下是浦东检疫检疫局会议------------------------------------------------   
	
	public List<InspectionQuarantineBean>getInspectionQuarantineBeanList(){
		
		String sql="select distinct task,builddate,buidstime,buidetime,meetingdept,attendman from schedule where checkid <>4  and operatorid='3' and ";
		
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		
		SimpleDateFormat sd2=new SimpleDateFormat("yyyy-M-d");
		Date date=new Date();
		List<InspectionQuarantineBean> list=new ArrayList<InspectionQuarantineBean>();
		Connection con =getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sqltmp=new StringBuffer("builddate='").append(sd.format(date)).append("' or builddate='").append(sd2.format(date)).append("'").append("  order by buidstime").toString();
		sql=new StringBuffer(sql).append(sqltmp).toString();
		try {
//			System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new InspectionQuarantineBean(rs.getString("task"),rs.getString("builddate"),rs.getString("buidstime"),rs.getString("buidetime"),
													  rs.getString("meetingdept"),rs.getString("attendman")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		for(InspectionQuarantineBean iqb:list){
//			System.out.println(iqb.getTask());
//		}
		return list;
	}
	
	public void saveInspectionQuarantineXML(List<InspectionQuarantineBean> panelList,String path ) {
		
//		System.out.println("---------------------------->"+panelList.size());
		
		Document document = DocumentHelper.createDocument();
		String filepath =path+"pdiq-notice.xml";
		
		if(null!=panelList&&!panelList.isEmpty()){
			
			Element booksElement = document.addElement("notice");
	
			booksElement.addAttribute("time","时间")
			        .addAttribute("attendman", "地点")
					.addAttribute("task","会议名")
//					.addAttribute("content","会议内容")
					.addAttribute("meetingdept", "借用部门")
					/*.addAttribute("scroolltext", "滚动文字")*/;
					
					
			for (int i = 0; i < panelList.size(); i++) {
				
				InspectionQuarantineBean beans = panelList.get(i);
	
				Element element = booksElement.addElement("row");
			
				writePaneBeanXML(element,beans);
			}
			
		}else{
			Element booksElement = document.addElement("notice");
			
			booksElement.addAttribute("time","时间")
			        .addAttribute("attendman", "地点")
					.addAttribute("task","会议名")
//					.addAttribute("content","会议内容")
					.addAttribute("meetingdept", "借用部门")
					/*.addAttribute("scroolltext", "滚动文字")*/;
			
		}
		XMLWriter writer;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("gbk");
		try {
			writer = new XMLWriter(new FileWriter(filepath), format);
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
  public static Element writePaneBeanXML(Element element, InspectionQuarantineBean bean) {
		
		element.addAttribute("time", new StringBuffer(bean.getBuilddate()).append(" ").append(bean.getBuidstime()).append("-").append(bean.getBuidetime()).toString());
		element.addAttribute("attendman", bean.getAttendman());
		element.addAttribute("task", bean.getTask());
//		element.addAttribute("content", bean.getContent());
		element.addAttribute("meetingdept",bean.getMeetingdept());
//		element.addAttribute("scroolltext", bean.getScroolltext());
		return element;
	}
   
//-----------------------------------------------------------   
   public  synchronized List<ProgramDirectName> getAllProgramMeetingSanLing(String datetime) {
		
		Map<String,String>map=getAllMeetingRoom();
		List<ProgramDirectName> list =new ArrayListUtils<ProgramDirectName> ();
//		String sql = "select distinct a.dept_nme,d.meeting_time, d.starttime,d.endtime,e.floor,e.room_name,d.meeting_name,d.meeting_title,d.hand_dept,d.create_name"
//					+" from  depts a,users b, meeting d,meeting_room e  "
//					+" where  e.room_id=d.meeting_room_id and b.dept=a.dept_id and d.meeting_time='"+datetime+"' order by d.starttime ";

		String sql = "select distinct meeting_name,meeting_title,meeting_time,starttime,endtime,create_name,dept_nme,room_name,hand_dept "

					+"from meeting,meeting_room,users,depts "
			
					+"where meeting.meeting_room_id=meeting_room.room_id and meeting.create_username=users.username and users.dept=depts.dept_id "
			
					+"and meeting_time='"+datetime+"' order by starttime ";
		
//		System.out.println(sql);
		Connection con = getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ProgramDirectName vdn = null;
			StringBuffer strb=null;
			while (rs.next()){
				strb=new StringBuffer();
			
			    String str=strb.append(rs.getString("meeting_time")).append(" ").append(rs.getString("starttime")).append("-").append(rs.getString("endtime")).toString();
				
				vdn = new ProgramDirectName();
				vdn.setDatetimer(str);//
				vdn.setProgramaddress(/*rs.getString("floor")+" 楼 "+*/rs.getString("room_name"));// 
				map.remove(rs.getString("room_name"));//存在就删除
				vdn.setProgrammeetingname(rs.getString("meeting_name"));//
				vdn.setProgrammeeting(rs.getString("meeting_title"));// 
				vdn.setProgramdept(rs.getString("dept_nme"));// 
				vdn.setCreatename(rs.getString("create_name"));//会议创建人
				vdn.setHand_dept(rs.getString("hand_dept"));
				list.add(vdn);
			
			}
			if(!map.isEmpty()){
				for(Map.Entry<String,String> s:map.entrySet()){
					vdn = new ProgramDirectName();
					vdn.setDatetimer("");
					vdn.setProgramaddress(s.getValue());
					vdn.setProgrammeetingname("");
					vdn.setProgrammeeting("no");
					vdn.setProgramdept("no");
					vdn.setHand_dept("99");
					vdn.setCreatename("no");
					list.add(vdn);
				}
				map.clear();
			}
			return list;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
   
   public String[] stringip = { "" };
	public void getcong() {

		Dom4j dom = new Dom4j(FirstStartServlet.projectpath+"connectip.xml");
		Document document = dom.getDocument();
		List list = document.selectNodes("/connectips/connectip");
		Element e = (Element) list.get(0);
		String[] best = { e.attributeValue("dbClassName"),
				          e.attributeValue("dbUrl"),
				          e.attributeValue("username"),
				          e.attributeValue("password") };
		stringip = best;
	}
   public  Connection getConn() {
		getcong();
		Connection con = null;
//		try {
//			 props.load(is);
//			 String classname = props.getProperty("dbdriver");
//			 String url = props.getProperty("local_url");
//			 String username = props.getProperty("local_use");
//			 String password = props.getProperty("local_pas");
		 
		    try {
				Class.forName(stringip[0]);
			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//				 JOptionPane.showMessageDialog(null,"1请检查配置文件(connectip.xml)或者网络连接，请重启！！","数据库连接有误",JOptionPane.ERROR_MESSAGE);
				 System.out.println(e.getMessage());
			}
		    try {
				con = DriverManager.getConnection(stringip[1], stringip[2], stringip[3]);
			} catch (SQLException e) {
//				 e.printStackTrace();
				 System.out.println(e.getMessage());
//				 JOptionPane.showMessageDialog(null,"2请检查配置文件(connectip.xml)或者网络连接，请重启！！","数据库连接有误",JOptionPane.ERROR_MESSAGE);
			}
		return con;
	}
   public static void main(String[] args) {
	   LivemeetingUtil livemeetingutil=new LivemeetingUtil();
	   livemeetingutil.saveXML(livemeetingutil.getAllProgramMeetingSanLing(new SimpleDateFormat("yyyy-MM-dd").format(new Date())),"c:/");
   }
	
}
