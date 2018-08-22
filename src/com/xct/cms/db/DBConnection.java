package com.xct.cms.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidPooledConnection;

public class DBConnection {
    private static  Logger logger = Logger.getLogger(DBConnection.class);
   
    DruidConnection druidconnection=DruidConnection.getInstance();
    
    public DBConnection() {}
    
    /**
    * 返回druid数据库连接
    * @return
    * @throws SQLException
    */
    public   DruidPooledConnection getConection() {
    	try {
			return druidconnection.getConection();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
	
    public static void main(String[] args) {
			System.out.println(new DBConnection().getConection());
		
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
			logger.info("关闭数据库资源出错！====="+e.getMessage());
			e.printStackTrace();
		}
	}
	public void returnResources(ResultSet rss,PreparedStatement pstmt1) {
		try {
			if (rss != null)
				rss.close();
			if (pstmt1 != null)
				pstmt1.close();
		} catch (SQLException e) {
			logger.info("关闭数据库资源出错！====="+e.getMessage());
			e.printStackTrace();
		}
	}
	public void returnResources(PreparedStatement pstmt1) {
		try {
			if (pstmt1 != null)
				pstmt1.close();
		} catch (SQLException e) { 
			logger.info("关闭数据库资源出错！====="+e.getMessage());
			e.printStackTrace();
		}
	}
	public void returnResources(PreparedStatement pstmt1, Connection conn) {
		try {
			if (pstmt1 != null)
				pstmt1.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			logger.info("关闭数据库资源出错！====="+e.getMessage());
			e.printStackTrace();
		}
	}
	public void returnResources(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) { 
			logger.info("关闭数据库资源出错！====="+e.getMessage());
			e.printStackTrace();
		}
	}
	
}
