package com.xct.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Led;

public class LedDao extends DBConnection{
	Logger logger = Logger.getLogger(LogsDAO.class);
    
	public boolean getLedBean(String pno){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = new StringBuffer().append("select * from led  ").append(pno).toString();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			returnResources(rs, pstmt, con);
		}
		return false;
	}
	
	public Led getLedBystr(String str) {
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Led led = null;
		try {
			String sql = new StringBuffer().append("select * from led  " ).append(str).toString();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				led = new Led();
				led.setId(rs.getInt("id"));
				led.setPno(rs.getString("pno"));
				led.setTitle(rs.getString("title"));
				led.setIp(rs.getString("ip"));
				
				led.setX(rs.getInt("x"));
				led.setY(rs.getInt("y"));
				
				led.setWidth(rs.getInt("width"));
				led.setHeight(rs.getInt("height"));
				led.setText(rs.getString("text"));
				led.setFontName(rs.getString("fontName"));
				led.setFontColor(rs.getInt("fontColor"));
				led.setFontSize(rs.getInt("fontsize"));
				
				led.setStunt(rs.getString("stunt"));//显示特技
				led.setPlayspeed(rs.getInt("Playspeed"));//运行速度
				led.setBold(rs.getString("bold"));
				led.setItalic(rs.getString("italic"));
				led.setUnderline(rs.getString("underline"));
				led.setSleeptime(rs.getString("sleeptime"));//停留时间
				
				led.setL_num(rs.getString("l_num"));
				led.setDef_text(rs.getString("def_text"));
				led.setS_time1(rs.getString("s_time1"));
				led.setE_time1(rs.getString("e_time1"));
				led.setS_text1(rs.getString("s_text1"));
				led.setS_time2(rs.getString("s_time2"));
				led.setE_time2(rs.getString("e_time2"));
				led.setS_text2(rs.getString("s_text2"));
				led.setS_time3(rs.getString("s_time3"));
				led.setE_time3(rs.getString("e_time3"));
				led.setS_text3(rs.getString("s_text3"));
				led.setS_time4(rs.getString("s_time4"));
				led.setE_time4(rs.getString("e_time4"));
				led.setS_text4(rs.getString("s_text4"));
				led.setS_time5(rs.getString("s_time5"));
				led.setE_time5(rs.getString("e_time5"));
				led.setS_text5(rs.getString("s_text5"));
			}
		} catch (Exception e) {
			logger.info(new StringBuffer().append("根据<<" ).append( str ).append( ">>获取LED出错！==>" ).append( e.getMessage()).toString());
			e.printStackTrace();
		} finally {
			returnResources(rs, pstmt, con);
		}
		return led;
	}
}
