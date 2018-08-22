package com.xct.cms.xy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.xct.cms.db.DBConnection;
import com.xct.cms.utils.UtilDAO;

public class getStaksDao extends DBConnection {

	Logger logger = Logger.getLogger(getStaksDao.class);
	 //��ȡĿ���·�������Ϣ
   public String getTasks(String month,String str) {
        JSONArray array = new JSONArray();      //�½�JSON�������
        //Mysql
        String sql = "select distinct  a.program_SetDateTime, a.program_JMid,a.program_Name,a.program_ISloop,a.program_SetDate,a.program_EndDate from xct_JMhistory a,xct_ipaddress b where a.program_delid=b.cnt_mac  and (( year(program_SetDateTime) = ? and month(program_SetDateTime)  = ? ) or (program_isloop=3 and program_SetDateTime<= '"+month+"-28'))  "+str+" limit 500";   //����SQL���
        //SQLServer
//       String sql = "select distinct  top 500 a.program_SetDateTime, a.program_JMid,a.program_Name,a.program_ISloop,a.program_SetDate,a.program_EndDate from xct_JMhistory a,xct_ipaddress b where a.program_delid=b.cnt_mac  and (( year(program_SetDateTime) = ? and month(program_SetDateTime)  = ? ) or (program_isloop=3 and program_SetDateTime<= '"+month+"-28'))  "+str;   //����SQL���
        Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		JSONObject obj=null;
        try {
            pstmt = con.prepareStatement(sql); //����sql����PreparedStatement
            pstmt.setString(1, month.split("-")[0]);   //���ò���
            pstmt.setString(2, month.split("-")[1]);   //���ò���
            rs = pstmt.executeQuery();
            String date="";
            int id=0;
            String task="";
            int isloop=0;
            String starttime="";
            String endtime="";
            while (rs.next()) {
                //ʹ�����ݿ���������JSON���󣬲����뵽JSON������
            	date=new SimpleDateFormat("yyyy-M-d").format(rs.getTimestamp(1).getTime());
            	id=rs.getInt(2);
            	task=rs.getString(3);
            	isloop=rs.getInt(4);
            	starttime=new SimpleDateFormat("HH:mm").format(rs.getTimestamp(5).getTime());
            	endtime=new SimpleDateFormat("HH:mm").format(rs.getTimestamp(6).getTime());
            	
                if(isloop==3){
                	for(int i=1;i<32;i++){
                		obj = new JSONObject();
                		obj.put("builddate", month+"-"+i);
    	                obj.put("id", id);
    	                obj.put("task", "&nbsp;"+task);
    	                obj.put("isloop", isloop);
    	                array.add(obj);
                	}
                }else{
                	obj = new JSONObject();
	                obj.put("builddate", date);
	                obj.put("id", id);
	                obj.put("task", starttime+"~"+endtime+"&nbsp;"+task);
	                obj.put("isloop", isloop);
	                array.add(obj);
                }
              
            }
        } catch (SQLException e) {
        	logger.info("��ȡĿ���·�������Ϣ����====="+e.getMessage());
        	e.printStackTrace();
        } finally {
        	returnResources(rs,pstmt,con);
        }
        return array.toString();
    }
}
