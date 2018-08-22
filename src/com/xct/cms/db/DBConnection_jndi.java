package com.xct.cms.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;


//<Context path="" docBase="xctSer" debug="5" reloadable="true" crossContext="true">
//<Resource name="jdbc/default" auth="Container" type="javax.sql.DataSource"
//		 maxActive="100" maxIdle="30" maxWait="10000"
//		 username="sa" password="asdf" driverClassName="com.microsoft.sqlserver.jdbc.SQLServerDriver"
// url="jdbc:sqlserver://localhost:1433;SelectMethod=cursor;DatabaseName=xctweb"/>
//</Context>

public class DBConnection_jndi { //最早使用，需要在tomcat /conf /server.xml  配置数据库连接
	protected Connection con = null;
	Context ctx = null;
	DataSource ds = null;
	Properties props = new Properties();
	InputStream is = getClass().getResourceAsStream("/conf.properties");
	Logger logger = Logger.getLogger(DBConnection_jndi.class);
	public DBConnection_jndi() {
		super();
		// 查找JNDI数据源名
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/default");
		} catch (Exception e) {
			logger.info("初始化连接池出错！====="+e.getMessage());
		}
	}
	// web页面用的数据源,在控制台不能测试，只能在web页面测试
	public Connection getConection() {
		if (ds != null) {
			try {
				con = ds.getConnection();
			} catch (SQLException e) {
				logger.info("获取连接池链接出错！====="+e.getMessage());
			}
		} else {
			try {
				props.load(is);
				if(is!=null)
				  is.close();
			} catch (Exception e) {
				logger.info("初始化conf.properties出错！====="+e.getMessage());
			}
			// /////System.out.println("连接池连接失败...");
			String classname = props.getProperty("dbdriver");
			String url = props.getProperty("local_url");
			String username = props.getProperty("local_use");
			String password = props.getProperty("local_pas");
			try {
				Class.forName(classname);
				con = DriverManager.getConnection(url, username, password);
			} catch (Exception e2) {
				e2.printStackTrace();
				logger.info("conf.properties链接数据库出错！====="+e2.getMessage());
			}
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
			logger.info("关闭数据库资源出错！====="+e.getMessage());
			e.printStackTrace();
		}
	}public void returnResources(ResultSet rss,PreparedStatement pstmt1) {
		try {
			if (rss != null)
				rss.close();
			if (pstmt1 != null)
				pstmt1.close();
		} catch (SQLException e) {
			logger.info("关闭数据库资源出错！====="+e.getMessage());
			e.printStackTrace();
		}
	}public void returnResources(PreparedStatement pstmt1) {
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

	public static void main(String[] args) {
		
		System.out.println(new DBConnection_jndi().getConection());
	}

	
}
