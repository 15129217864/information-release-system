package com.xct.cms.db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

public class DruidConnection {

    private static  Logger logger = Logger.getLogger(DruidConnection.class);
    
    private static DruidConnection dbPoolConnection = null;
    private static DruidDataSource druidDataSource = null;
    static {
        initDBSource();
    }
 
    public DruidConnection() {
	
	}

	/**
     * ��ʼ�����ӳ�
     */
    private static final void initDBSource() {
        Properties properties  = new Properties();
        FileInputStream in = null;
        try {
            // ���������ļ�
            String websiteURL = new StringBuffer().append(null==System.getProperty("WEB_HOME")?System.getProperty("user.dir")+"\\":System.getProperty("WEB_HOME")).append("druid.properties").toString();
            in = new FileInputStream(websiteURL);
            properties.load(in);
            druidDataSource = (DruidDataSource)DruidDataSourceFactory.createDataSource(properties); //DruidDataSrouce����ģʽ
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	try {
        		if (null != in){
        			in.close();
        		}
    		} catch (Exception e) {
    		  e.printStackTrace();
    		}
        }
    }
    /**
    * ���ݿ����ӳص���
    * @return
    */
    public static  DruidConnection getInstance(){
	    if (null == dbPoolConnection){
	        dbPoolConnection = new DruidConnection();
	    }
	     return dbPoolConnection;
    }
    
    /**
    * ����druid���ݿ�����
    * @return
    * @throws SQLException
    */
    public   DruidPooledConnection getConection() {
    	try {
			return druidDataSource.getConnection();
		} catch (SQLException e) {
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
			logger.info("�ر����ݿ���Դ����====="+e.getMessage());
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
			logger.info("�ر����ݿ���Դ����====="+e.getMessage());
			e.printStackTrace();
		}
	}
	public void returnResources(PreparedStatement pstmt1) {
		try {
			if (pstmt1 != null)
				pstmt1.close();
		} catch (SQLException e) { 
			logger.info("�ر����ݿ���Դ����====="+e.getMessage());
			e.printStackTrace();
		}
	}public void returnResources(PreparedStatement pstmt1, Connection conn) {
		try {
			if (pstmt1 != null)
				pstmt1.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			logger.info("�ر����ݿ���Դ����====="+e.getMessage());
			e.printStackTrace();
		}
	}public void returnResources(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) { 
			logger.info("�ر����ݿ���Դ����====="+e.getMessage());
			e.printStackTrace();
		}
	}
	

}
