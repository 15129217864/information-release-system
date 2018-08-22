package com.xct.cms.db;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.util.UUID;

import org.apache.log4j.Logger;

public class BuildEnterpriseID /*extends DBConnection */{
	
	Logger logger = Logger.getLogger(BuildEnterpriseID.class);
	
	public synchronized String buildEid(String tablename, String clumename) {
		
		return new StringBuffer(clumename).append(".").append(UUID.randomUUID().toString()).toString();
		
		/*
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			String sql = "select max(" + clumename + ") from " + tablename
					+ " ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			String str1 = "";
			if (rs.next()) {
				String eid = rs.getString(1);
				str1 = getMaxChar(eid, 1);
			}
			return str1;
		} catch (Exception e) {
			logger.info("给"+tablename+"表创建ID出错====="+e.getMessage());
		} finally {
			returnResources(rs,pstmt,con);
		}
		return "0";
	*/}

	public static void main(String[] args) {
		 /////System.out.println(new BuildEnterpriseID().buildEid("xct_media", "media_id"));
	}

	public synchronized static String getMaxChar(String maxChar, int i) {
		if (null == maxChar || "".equals(maxChar)) {
			return "m0000001";
		} else {
			char chr = maxChar.charAt(0);
			String bro = maxChar.substring(i);
			if ("9999999".equals(bro)) {
				int ch = chr + 1;
				char che = (char) ch;
				return che + "0000001";
			} else {
				Integer num = new Integer("1" + bro);
				return chr + ""
						+ String.valueOf(num.intValue() + 1).substring(1) + "";
			}

		}
	}
}
