package com.xct.cms.custom.made.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.custom.court.bean.CourtBean;
import com.custom.court.bean.CourtRoom;
import com.custom.court.bean.PartyBean;
import com.custom.court.bean.TestationBean;
import com.custom.court.bean.Trialgroup;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class WeiFangCourtDao {
	
	static String homepath;
	
	static{
		  homepath=getOSPath()+"/";
	}
	
	Connection con = null;
	Properties props = new Properties();
	
	 public static String getOSPath()
	  {
	    String location = null;
	    try {
	      location = URLDecoder.decode(WeiFangCourtDao.class.getProtectionDomain()
	        .getCodeSource().getLocation().getPath(), "UTF-8");

	      File parent = new File(location).getParentFile();
	      if (parent.getName().equalsIgnoreCase("lib")) {
	        parent = parent.getParentFile();
	      }
	      location = parent.getPath().replace("\\", "/");
	      if (!(location.endsWith("/"))) {
	        location = location + "/";
	      }
	    }
	    catch (UnsupportedEncodingException e)
	    {
	    	e.printStackTrace();
	      return null;
	    }

	    return location;
	  }
	 String classname ;
	 String url ;
	 String username;
	 String password;
	 
	public WeiFangCourtDao() {
		
		InputStream is =null;
		try {
			 is = getClass().getResourceAsStream("/weifangcourt_db.properties");
				 //new BufferedInputStream(new FileInputStream(homepath+"weifangcourt_db.properties"));
			props.load(is);
			if(is!=null)
			  is.close();
		} catch (Exception e) {
//			System.out.println("��ʼ��weifangcourt_db.properties����====="+e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				if(null!=is)
				  is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		 classname = props.getProperty("dbClassName");
		 url = props.getProperty("dbUrl");
		 username = props.getProperty("username");
		 password = props.getProperty("password");
	}



	public Connection getConection() {
		
			try {
				Class.forName(classname);
				con = DriverManager.getConnection(url, username, password);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		return con;
	}
	
	public void returnResources(ResultSet rss,PreparedStatement pstmt1, Connection conn) {
		try {
			if (rss != null)
				rss.close();
			if (pstmt1 != null)
				pstmt1.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}	
	public static  Date getAfterDate(int n){ 
		 
		  Calendar c = Calendar.getInstance(); 
		  c.add(Calendar.YEAR, n); 
		  return c.getTime(); 
	}
	
//	SELECT * FROM view_litigation //��ѯ�������ϵ�λ
	
	public static void main(String[] args) {
		
////		 System.out.println(homepath+"___"+new SimpleDateFormat("yyyy").format(getAfterDate(1)));
//		 
//		WeiFangCourtDao weifangcourtdao=	new WeiFangCourtDao();
//		 
////		 System.out.println(dbconneciton.getConection());
//		
//		weifangcourtdao.getCourtRoomMessages();//ͥ�󷿼���Ϣ
//		 
//		weifangcourtdao.getCourtMessages();//��ͥ��Ϣ
//		
//		 //=================================================================================================
//		// ��ȡ������(ͨ��caseid��ѯ)��Ϋ����Ժ���� 6.0�汾
//		weifangcourtdao.getPartyMessages();
//		 
////		 ��ȡ 3�� ���г� 4������Ա��6����������Ա��7����������Ա ��9�� Ϊ���Ա(ͨ������id��ѯ) Ϋ����Ժ����6.0�汾
//		 // ��ɫ������3�� ���г� 4������Ա��6����������Ա��7����������Ա ��9�� Ϊ���Ա
//		weifangcourtdao.getPeopleJuryMessages();
//		 
//		weifangcourtdao.getAllLitigationPositions();//��ȡ�������ϵ�λ
		
		new WeiFangCourtDao().getAllCourtInfo();
	}

	public  void getAllCourtInfo(){
		
//		 System.out.println(homepath+"___"+new SimpleDateFormat("yyyy").format(getAfterDate(1)));
		 
//		WeiFangCourtDao dbconneciton=	new WeiFangCourtDao();
		 
//		 System.out.println(dbconneciton.getConection());
		
		 Connection conn=getConection();
		
		 getCourtRoomMessages(conn);//ͥ�󷿼���Ϣ
		 
		 getCourtMessages(conn);//��ͥ��Ϣ
		
		 //=================================================================================================
		// ��ȡ������(ͨ��caseid��ѯ)��Ϋ����Ժ���� 6.0�汾
		 getPartyMessages(conn);
		 
//		 ��ȡ 3�� ���г� 4������Ա��6����������Ա��7����������Ա ��9�� Ϊ���Ա(ͨ������id��ѯ) Ϋ����Ժ����6.0�汾
		 // ��ɫ������3�� ���г� 4������Ա��6����������Ա��7����������Ա ��9�� Ϊ���Ա
		 getPeopleJuryMessages(conn);
		 
		 getTrialgroups(conn);
		 
		 getAllLitigationPositions(conn);//��ȡ�������ϵ�λ
		 
		 returnResources(null,null,conn);
	}
	
	public void getAllLitigationPositions(Connection conn){
		
		List<String> litigationlist=getLitigationPosition(conn);
		String jsonstring=createLitigationJson(litigationlist);
		
		if(null!=jsonstring&&!jsonstring.equals("")&&jsonstring.startsWith("[")&&jsonstring.endsWith("]"))
           writeFiletoSingle(System.getProperty("WEIFANGCOURT_HOME")+"LitigationPosition.json",jsonstring,false,false);
		
//         System.out.println(jsonstring);
	}
	
   public void getTrialgroups(Connection conn){ 
		
		List<Trialgroup>trialgrouplist=getTrialgroup(conn);
		String jsonstring=createTrialgroupJson(trialgrouplist);
		
		if(null!=jsonstring&&!jsonstring.equals("")&&jsonstring.startsWith("[")&&jsonstring.endsWith("]"))
           writeFiletoSingle(System.getProperty("WEIFANGCOURT_HOME")+"Trialgroup.json",jsonstring,false,false);
		
//         System.out.println(jsonstring);
	}
	
	
	
	//��ȡ��ͥ������Ϣ
	public void getCourtRoomMessages(Connection connection){
		
		List<CourtRoom> courtroomlist=getCourtRoomInfo(connection);
		String jsonstring=createCourtRoomJson(courtroomlist);
		
		if(null!=jsonstring&&!jsonstring.equals("")&&jsonstring.startsWith("[")&&jsonstring.endsWith("]"))
           writeFiletoSingle(System.getProperty("WEIFANGCOURT_HOME")+"CourtRoom.json",jsonstring,false,false);
		
//         System.out.println(jsonstring);
	}
	
	//��ȡ��ͥ��Ϣ
    public void getCourtMessages(Connection conn){
		
    	List<CourtBean> courtlist=getCourtInfo(conn);
    	String jsonstring=createCourtInfoJson(courtlist);
    	if(null!=jsonstring&&!jsonstring.equals("")&&jsonstring.startsWith("[")&&jsonstring.endsWith("]"))
    	   writeFiletoSingle(System.getProperty("WEIFANGCOURT_HOME")+"CourtInfo.json",jsonstring,false,false);
//    	System.out.println(jsonstring);
	}
    
//  ���� caseid ��ȡԭ����֤��
    public void getPartyMessages(Connection conn){//"A91EE628_C75B_E8F6_92CA_C8A1952DDA27"
    	
    	String jsonstring=createPartyJson(getPartyInfo(caseid_sqlstr, conn));
    	if(null!=jsonstring&&!jsonstring.equals("")&&jsonstring.startsWith("[")&&jsonstring.endsWith("]"))
    	   writeFiletoSingle(System.getProperty("WEIFANGCOURT_HOME")+"PartyInfo.json",jsonstring,false,false);
//    	System.out.println(jsonstring);
    }
     
//  ���� ����id ��ȡ����Ա
    public void getPeopleJuryMessages(Connection conn){//"A91EE628_C75B_E8F6_92CA_C8A1952DDA27"
    	
    	String jsonstring=createPeopleJuryJson(getPeopleJuryInfo(trialplanid_sqlstr,conn));
    	if(null!=jsonstring&&!jsonstring.equals("")&&jsonstring.startsWith("[")&&jsonstring.endsWith("]"))
    	   writeFiletoSingle(System.getProperty("WEIFANGCOURT_HOME")+"PeopleJury.json",jsonstring,false,false);
//    	System.out.println(jsonstring);
    }
    
    //**********************************���� Ϋ����Ժ����6.0�汾**************************************88
//  ��������id ��ȡ����Ա
    public void getPeopleJuryMessages2(String trialplanid){//"A91EE628_C75B_E8F6_92CA_C8A1952DDA27"
    	
    	String jsonstring=createPeopleJuryJson2(getPeopleJuryInfo2(trialplanid));
    	if(null!=jsonstring&&!jsonstring.equals("")&&jsonstring.startsWith("[")&&jsonstring.endsWith("]"))
    	   writeFiletoSingle(System.getProperty("WEIFANGCOURT_HOME")+"PeopleJury.json",jsonstring,false,false);
//    	System.out.println(jsonstring);
    }
    
//  ��������caseid ��ȡԭ����֤��
    public void getTestationMessages(String caseid){//"A91EE628_C75B_E8F6_92CA_C8A1952DDA27"
    	
    	String jsonstring=createTestationJson(getTestationInfo((caseid)));
    	if(null!=jsonstring&&!jsonstring.equals("")&&jsonstring.startsWith("[")&&jsonstring.endsWith("]"))
    	   writeFiletoSingle(System.getProperty("WEIFANGCOURT_HOME")+"Testation.json",jsonstring,false,false);
//    	System.out.println(jsonstring);
    }
	
 
   String  trialplanid_sqlstr="'0'";
   String  caseid_sqlstr="'0'";
   
   public List<CourtBean> getCourtInfo(Connection conn){
	    
	    StringBuffer sb=new StringBuffer();
	   //��ͼ���Ѿ�������ʱ��˳��
		String sql="SELECT caseid,casecode,trialplanid,casenumber,courtroomid,courtroomname,startdate,starttime,trialstatus,undertakename,casetypename FROM view_caseinfo where startdate=";  
		sql= sb.append(sql).append("'").append(/*"2015-03-16"*/new SimpleDateFormat("yyyy-MM-dd").format(new Date())).append("'").toString();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CourtBean> courtlist=new ArrayList<CourtBean>();
		
		List<String>trialplanid_list=new ArrayList<String>();
		   
		List<String>caseid_list=new ArrayList<String>();
		
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				
				String caseid=rs.getString("caseid");
				String trialplanid=rs.getString("trialplanid");
				
				trialplanid_list.add(trialplanid);
				caseid_list.add(caseid);
				
				courtlist.add(new CourtBean(caseid,rs.getString("casecode"),trialplanid,rs.getString("casenumber"),
											rs.getString("courtroomid"),rs.getString("courtroomname"),
											rs.getString("startdate"),rs.getString("starttime"),
											rs.getString("trialstatus"),rs.getString("undertakename"),
											rs.getString("casetypename")));
			}
			
//			===============================================================================
			//ƴ�Ӳ�ѯ�ִ�
			StringBuffer  trialplanid_sbffer=new StringBuffer("");
			StringBuffer  caseid_sbffer=new StringBuffer("");
			
			for (String string : trialplanid_list) {
				trialplanid_sbffer.append("'").append(string).append("'").append(",");
			}
            for (String string : caseid_list) {
            	caseid_sbffer.append("'").append(string).append("'").append(",");
			}
            if(trialplanid_sbffer.toString().endsWith(",")){
            	trialplanid_sqlstr=trialplanid_sbffer.substring(0,trialplanid_sbffer.lastIndexOf(","));
            }
            if(caseid_sbffer.toString().endsWith(",")){
            	caseid_sqlstr=caseid_sbffer.substring(0,caseid_sbffer.lastIndexOf(","));
            }
			//===============================================================================
//			System.out.println(courtlist.size());
		}catch (SQLException e) {
			e.printStackTrace(System.out);
		}finally{
		    returnResources(rs,pstmt,null);
		}
		return courtlist;
	}
   
   
   //	������ͥ��Ϣ JSON
   public String createCourtInfoJson(List<CourtBean>list){
		 
   	    JSONArray array=new JSONArray();
    	JSONObject object=new JSONObject();
    	
    	for (CourtBean bean : list) {
    		object.put("caseid",  bean.getCaseid());//������(����)
    		object.put("casecode",  bean.getCasecode());//������(����)
    		object.put("trialplanid",  bean.getTrialplanid());//����id
			object.put("casenumber", bean.getCasenumber());// �������
			object.put("courtroomid",  bean.getCourtroomid());// ��ͥ����id
			object.put("courtroomname", bean.getCourtroomname());// ��ͥ����
			object.put("startdate",  bean.getStartdate());// ��ͥ���ڣ�yyyy-MM-dd��
			object.put("starttime", bean.getStarttime());// ��ͥʱ�� ��hh:mm:ss��
//			object.put("judgename",  ("".equals(bean.getJudgename())||null==bean.getJudgename())?"":bean.getJudgename());// ���󷨹٣����г�
//			object.put("party", bean.getParty());// �����ˣ�ԭ�棬����
			object.put("trialstatus",bean.getTrialstatus());//ͥ��״̬:2��5Ϊ��ͥ��1Ϊ�ȴ���3Ϊ������4Ϊ��ͥ
			object.put("undertakename",("".equals(bean.getUndertakename())||null==bean.getUndertakename())?"":bean.getUndertakename());//�а���
			
//			object.put("judge2name",("".equals(bean.getJudge2name())||null==bean.getJudge2name())?"":bean.getJudge2name());//����Ա1
//			object.put("judge3name",("".equals(bean.getJudge3name())||null==bean.getJudge3name())?"":bean.getJudge3name());//����Ա2
			object.put("casetypename",("".equals(bean.getCasetypename())||null==bean.getCasetypename())?"":bean.getCasetypename());//�������ͣ����£�����
//			object.put("conceptname",("".equals(bean.getConceptname())||null==bean.getConceptname())?"":bean.getConceptname());//����
			array.add(object);
		}
		return array.toString();
   }
   
   // ========================================================================
   
   public List<CourtRoom> getCourtRoomInfo(Connection conn){
		  
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<CourtRoom> courtroomlist=new ArrayList<CourtRoom>();
		String sql="select courtroomid,courtroomname,courtroomposition from view_courtroom";
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				courtroomlist.add(new CourtRoom(rs.getString("courtroomid"),rs.getString("courtroomname"),rs.getString("courtroomposition")));
			}
//			System.out.println(courtroomlist.size());
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}finally{
		    returnResources(rs,pstmt,null);
		}
		return courtroomlist;
	}
   
// ��ȡ�������ϵ�λ
   public List<Trialgroup> getTrialgroup(Connection conn){
		  
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Trialgroup> trialgrouplist=new ArrayList<Trialgroup>();
		String sql="SELECT trialplanid,js,stmc FROM view_trialgroup where js='6' or js='7'";
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				trialgrouplist.add(new Trialgroup(rs.getString("trialplanid"),rs.getString("js"),rs.getString("stmc")));
			}
//			System.out.println(courtroomlist.size());
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}finally{
		    returnResources(rs,pstmt,null);
		}
		return trialgrouplist;
	}
   
   
   //��ȡ�������ϵ�λ
    public List<String> getLitigationPosition(Connection conn){
		  
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<String> litigationlist=new ArrayList<String>();
		String sql="SELECT ssdw,ssdwtc FROM view_litigation";
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				litigationlist.add(new StringBuffer().append(rs.getString("ssdw"))/*.append("#").append(rs.getString("ssdwtc"))*/.toString());
			}
//			System.out.println(courtroomlist.size());
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}finally{
		    returnResources(rs,pstmt,null);
		}
		return litigationlist;
	}
  
    public String createLitigationJson(List<String> litigationlist){
		 
    	JSONArray array=new JSONArray();
     	JSONObject object=new JSONObject();
     	
     	for (String litigation : litigationlist) {
	     		object.put("LitigationPosition", litigation);
				array.add(object);
     	}
		return array.toString();
    }
    
    public String createTrialgroupJson(List<Trialgroup> trialgrouplist){
		 
    	JSONArray array=new JSONArray();
     	JSONObject object=new JSONObject();
     	
     	for (Trialgroup trialgroup : trialgrouplist) {
	     		object.put("trialplanid", trialgroup.getTrialplanid());
	     		object.put("role", trialgroup.getJs());
	     		object.put("entityname", trialgroup.getStmc());
				array.add(object);
     	}
		return array.toString();
    }
    
     //	������Ժ���� JSON
    public String createCourtRoomJson(List<CourtRoom>list){
		 
    	JSONArray array=new JSONArray();
     	JSONObject object=new JSONObject();
     	for (CourtRoom bean : list) {
			object.put("courtroomid", bean.getCourtroomid());
			object.put("courtroomname",  bean.getCourtroomname());
			object.put("courtroomposition",  bean.getCourtroomposition());
			array.add(object);
		}
		return array.toString();
    }
    
//========================================================================
    
    //����Ա��Ϣ 6.0
    public  List<String>  getPeopleJuryInfo(String trialplanid,Connection conn){
    	
    	List<String>list=new ArrayList<String>();
    	
    	
        String sql="SELECT trialplanid, js,stmc FROM view_trialgroup WHERE js IN(3,4) AND  trialplanid in("+trialplanid+") ORDER BY js";
 		PreparedStatement pstmt = null;
 		ResultSet rs = null;
// 		System.out.println("getPeopleJuryInfo----------------->"+sql);
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				StringBuffer sb=new StringBuffer();
				sb.append(rs.getString("trialplanid")).append("#").append(rs.getString("stmc"));
				list.add(sb.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}finally{
		    returnResources(rs,pstmt,null);
		}
		
        return list;
    }
    /**
     *  trialplanid         ����ID
        peoplejury         ��������Ա    ����ʱ�Ժ��ֶ��Ÿ���
     */
    //����Ա��Ϣ4.0
    public  List<String>  getPeopleJuryInfo2(String trialplanid){
    	
    	List<String>list=new ArrayList<String>();
        String sql="SELECT  trialplanid,peoplejury FROM view_peoplejury where trialplanid='"+trialplanid+"'";
        Connection conn = null;
 		PreparedStatement pstmt = null;
 		ResultSet rs = null;
 		conn=getConection();
 		String peoplejury="";
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				peoplejury=rs.getString("peoplejury");
				peoplejury=peoplejury.replaceAll("��",",").replaceAll("��",",");
				list.add(rs.getString("trialplanid")+"#"+peoplejury);
			}
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}finally{
		    returnResources(rs,pstmt,conn);
		}
        return list;
    }
    
    //	��������Ա JSON
    public String createPeopleJuryJson(List<String> peoplejurylist){
		 
    	JSONArray array=new JSONArray();
     	JSONObject object=new JSONObject();
     	
     	for (String peoplejury : peoplejurylist) {
	     	String []strarry=peoplejury.split("#");
	 		if(strarry.length==2){
	     		object.put("trialplanid", strarry[0]);
				object.put("peoplejury", strarry[1]);
				array.add(object);
	 		}
     	}
		return array.toString();
    }
    
    //	��������Ա JSON
    public String createPeopleJuryJson2(List<String>list){
		 
    	JSONArray array=new JSONArray();
     	JSONObject object=new JSONObject();
     	for (String string : list) {
     		String []strarry=string.split("#");
     		if(strarry.length==2){
	     		object.put("trialplanid", strarry[0]);
				object.put("peoplejury", strarry[1]);
				array.add(object);
     		}
		}
		return array.toString();
    }
    
    
    //�������� 
    public List<String> getCaseType() {
    	List<String>list=new ArrayList<String>();
    	
    	String sql="SELECT *  FROM view_casetype";
    	return list;
	}
//========================================================================
    /**
     *  caseid              ����ID
        ssdw           ��λ���ƣ��������棬ԭ��
        ssdwtc         ���ϵ�λͳ��        1-ԭ�棬2-����
        dsrxm          ����    
     * 
     */
    //֤�ˣ�ԭ��ͱ��� ��Ϣ ȫ�ڴ˲�� Ϋ����Ժ����6.0�汾
    public List<PartyBean> getPartyInfo(String caseid,Connection conn){
    	
    	 
    	List<PartyBean>list=new ArrayList<PartyBean>();
    	
         String sql="SELECT caseid,ssdw,ssdwtc,dsrxm FROM  view_party WHERE ssdw!='-' and ssdw!='δָ��' and caseid in("+caseid+")";
   
//         System.out.println("getPartyInfo----------------->"+sql);
         
 		 PreparedStatement pstmt = null;
 		 ResultSet rs = null;
		 try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
			   list.add(new PartyBean(rs.getString("caseid"),rs.getString("ssdw"),rs.getString("ssdwtc"),rs.getString("dsrxm")));
			}
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}finally{
		    returnResources(rs,pstmt,null);
		}
        return list;
    }
    
    //	���������ˣ� ԭ�桢���桢֤�� JSON
    public String createPartyJson(List<PartyBean>list){
		 
    	JSONArray array=new JSONArray();
     	JSONObject object=new JSONObject();
     	for (PartyBean bean : list) {
     		
     		object.put("caseid", bean.getCaseid());
			object.put("litigationtype", bean.getSsdwtc());
			object.put("partyname", bean.getSsdw());
			object.put("partyndesc", bean.getDsrxm());
			array.add(object);
		}
		return array.toString();
    }
    
    
   /**
    *  caseid              ����ID
       trialplanid         ����ID
       testationname        ֤��              ����ʱ�Ժ��ֶ��Ÿ���
       litigationtype     ���ϵ�λͳ��        1-ԭ�棬2-����
    * 
    */
    //ԭ��ͱ���֤����Ϣ,��ȫ�Ƕ��Ÿ���
    public List<TestationBean> getTestationInfo(String caseid){
    	
    	 
    	List<TestationBean>list=new ArrayList<TestationBean>();
    	
         String sql="SELECT caseid,trialplanid,testationname,litigationtype FROM view_testation where caseid='"+caseid+"'";
   
  		 Connection conn = null;
 		 PreparedStatement pstmt = null;
 		 ResultSet rs = null;
 		 conn=getConection();
		 try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
			   list.add(new TestationBean(rs.getString("testationname"),rs.getInt("litigationtype")));
			}
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}finally{
		    returnResources(rs,pstmt,conn);
		}
        return list;
    }
    
    //	������ԭ�汻��֤�� JSON
    public String createTestationJson(List<TestationBean>list){
		 
    	JSONArray array=new JSONArray();
     	JSONObject object=new JSONObject();
     	for (TestationBean bean : list) {
     		
			object.put("litigationtype", bean.getLitigationtype());
			object.put("testationname", bean.getTestationname());
			array.add(object);
		}
		return array.toString();
    }
    //=====================================================================
	 
	 //����JSON
   public static void analyzeJson(String json){
		   
		JSONArray array = JSONArray.fromObject(json);       
		for(int i = 0; i < array.size(); i++){
	        JSONObject jsonObject = array.getJSONObject(i);
	        System.out.println(jsonObject.get("meetingid"));
	        System.out.println(jsonObject.get("time"));
	        System.out.println(jsonObject.get("topic"));
	        System.out.println(jsonObject.get("roomid"));
	        System.out.println(jsonObject.get("room"));
	    }
	}
	
	public  boolean isBoolIP(String ipAddress){
		String ip="(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
 	}
	
	 public   void writeFiletoSingle(String file, String str, boolean flag,boolean newline) {
			
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
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	
}
