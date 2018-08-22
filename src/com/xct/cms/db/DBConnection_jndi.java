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

public class DBConnection_jndi { //����ʹ�ã���Ҫ��tomcat /conf /server.xml  �������ݿ�����
	protected Connection con = null;
	Context ctx = null;
	DataSource ds = null;
	Properties props = new Properties();
	InputStream is = getClass().getResourceAsStream("/conf.properties");
	Logger logger = Logger.getLogger(DBConnection_jndi.class);
	public DBConnection_jndi() {
		super();
		// ����JNDI����Դ��
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/default");
		} catch (Exception e) {
			logger.info("��ʼ�����ӳس���====="+e.getMessage());
		}
	}
	// webҳ���õ�����Դ,�ڿ���̨���ܲ��ԣ�ֻ����webҳ�����
	public Connection getConection() {
		if (ds != null) {
			try {
				con = ds.getConnection();
			} catch (SQLException e) {
				logger.info("��ȡ���ӳ����ӳ���====="+e.getMessage());
			}
		} else {
			try {
				props.load(is);
				if(is!=null)
				  is.close();
			} catch (Exception e) {
				logger.info("��ʼ��conf.properties����====="+e.getMessage());
			}
			// /////System.out.println("���ӳ�����ʧ��...");
			String classname = props.getProperty("dbdriver");
			String url = props.getProperty("local_url");
			String username = props.getProperty("local_use");
			String password = props.getProperty("local_pas");
			try {
				Class.forName(classname);
				con = DriverManager.getConnection(url, username, password);
			} catch (Exception e2) {
				e2.printStackTrace();
				logger.info("conf.properties�������ݿ����====="+e2.getMessage());
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
			logger.info("�ر����ݿ���Դ����====="+e.getMessage());
			e.printStackTrace();
		}
	}public void returnResources(ResultSet rss,PreparedStatement pstmt1) {
		try {
			if (rss != null)
				rss.close();
			if (pstmt1 != null)
				pstmt1.close();
		} catch (SQLException e) {
			logger.info("�ر����ݿ���Դ����====="+e.getMessage());
			e.printStackTrace();
		}
	}public void returnResources(PreparedStatement pstmt1) {
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

	public static void main(String[] args) {
		
		System.out.println(new DBConnection_jndi().getConection());
	}

	
}
