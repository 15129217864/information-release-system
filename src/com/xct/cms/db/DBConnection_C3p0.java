package com.xct.cms.db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.DataSources;

public class DBConnection_C3p0 {
    private static final String JDBC_DRIVER = "driverClass";
    private static final String JDBC_URL = "jdbcUrl";
    private static  Logger logger = Logger.getLogger(DBConnection_C3p0.class);
    private static DataSource ds;
    /**
     * 初始化连接池代码块
     */
    static {
        initDBSource();
    }
 
    public DBConnection_C3p0() {
	
	}

	/**
     * 初始化c3p0连接池
     */
    private static final void initDBSource() {
        Properties c3p0Pro = new Properties();
        try {
            // 加载配置文件
            String websiteURL = new StringBuffer().append(null==System.getProperty("WEB_HOME")?System.getProperty("user.dir")+"\\":System.getProperty("WEB_HOME")).append("c3p0.properties").toString();
            FileInputStream in = new FileInputStream(websiteURL);
            c3p0Pro.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        String drverClass = c3p0Pro.getProperty(JDBC_DRIVER);
//        System.out.println("drverClass=====>"+drverClass);
        if (drverClass != null) {
            try {
                // 加载驱动类
                Class.forName(drverClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
 
        Properties jdbcpropes = new Properties();
        Properties c3propes = new Properties();
        for (Object key : c3p0Pro.keySet()) {
            String skey = (String) key;
            if (skey.startsWith("c3p0.")) {
                c3propes.put(skey, c3p0Pro.getProperty(skey));
            } else {
                jdbcpropes.put(skey, c3p0Pro.getProperty(skey));
            }
        }
//        System.out.println("JDBC_URL=====>"+c3p0Pro.getProperty(JDBC_URL));
        try {
            // 建立连接池
            DataSource unPooled = DataSources.unpooledDataSource(c3p0Pro.getProperty(JDBC_URL), jdbcpropes);
            ds = DataSources.pooledDataSource(unPooled, c3propes);
 
        } catch (SQLException e) {
            e.printStackTrace();
        	logger.info("初始化连接池出错！====="+e.getMessage());
        }
    }
 
    /**
     * 获取数据库连接对象
     * 
     * @return 数据连接对象
     * @throws SQLException
     */
    public  synchronized Connection getConection() {
        Connection conn=null;
		try {
			conn = ds.getConnection();
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return conn;
    }
	
    public static void main(String[] args) {
			System.out.println(new DBConnection_C3p0().getConection());
		
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
	}public void returnResources(PreparedStatement pstmt1, Connection conn) {
		try {
			if (pstmt1 != null)
				pstmt1.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			logger.info("关闭数据库资源出错！====="+e.getMessage());
			e.printStackTrace();
		}
	}public void returnResources(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) { 
			logger.info("关闭数据库资源出错！====="+e.getMessage());
			e.printStackTrace();
		}
	}
	
}
