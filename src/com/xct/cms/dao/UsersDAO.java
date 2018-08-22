package com.xct.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.xct.cms.db.DBConnection;
import com.xct.cms.domin.Users;
import com.xct.cms.utils.StringMD5Util;
import com.xct.cms.utils.UtilDAO;

public class UsersDAO extends DBConnection  {
	
	Logger logger = Logger.getLogger(UsersDAO.class);
	public Users login(String lg_name,String lg_password){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Users user=null;
		try{
			String sql="select * from xct_LG where lg_name=? and lg_password=?";
//			System.out.println(lg_name+"__******___"+lg_password);//888__******___0A113EF6B61820DAA5611C870ED8D5EE
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, lg_name);
			pstmt.setString(2, lg_password);
			rs=pstmt.executeQuery();
			if(rs.next()){
				user = new Users();
				String nowtime=UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
				user.setLg_name(rs.getString("lg_name"));
				user.setLg_password(rs.getString("lg_password"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setLast_login_time(rs.getString("last_login_time"));
				user.setLg_role(rs.getString("lg_role"));
				user.setAuthority(rs.getString("lg_authority"));
				user.setNow_login_time(nowtime);
				String sql2="update xct_LG set last_login_time=? where lg_name=?";
				pstmt=con.prepareStatement(sql2);
				pstmt.setString(1, nowtime);
				pstmt.setString(2, lg_name);
				pstmt.executeUpdate();
			}
		}catch(Exception e){
			logger.error("用户登录失败！====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return user;	
	}
	public Users getUserBystr(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		Users user=null;
		try{
			String sql="select * from xct_LG " +str;
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				user = new Users();
				user.setLg_name(rs.getString("lg_name"));
				user.setLg_password(rs.getString("lg_password"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setLast_login_time(rs.getString("last_login_time"));
				user.setLg_role(rs.getString("lg_role"));
				user.setAuthority(rs.getString("lg_authority"));
			}
		}catch(Exception e){
			logger.error("用户登录失败！====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return user;	
	}
	public boolean checkUser(String lg_name){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try{
			String sql="select * from xct_LG where lg_name=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, lg_name);
			rs=pstmt.executeQuery();
			if(rs.next()){
				return true;
			}
		}catch(Exception e){
			logger.error("检查用户名失败！====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return false;	
	}
	public List<Users> getAllUsers(String str){
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		List<Users> userlist=new ArrayList<Users>();
		try{
			String sql="select * from xct_LG  "+str;
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				Users user = new Users();
				user.setLg_name(rs.getString("lg_name"));
				user.setLg_password(rs.getString("lg_password"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setLast_login_time(rs.getString("last_login_time"));
				user.setLg_role(rs.getString("lg_role"));
				user.setAuthority(rs.getString("lg_authority"));
				userlist.add(user);
			}
		}catch(Exception e){
			logger.error("用户登录失败！====="+e.getMessage());
			e.printStackTrace();
		}
		finally{
			returnResources(rs,pstmt,con);
		}
		return userlist;	
	}
	
	
///////////////////////////////////以下方法WEBSERVERS使用
	/**
	 * 添加用户
	 * @param lg_name  用户名
	 * @param lg_password  密码
	 * @param name  姓名
	 * @param email  EMAIL
	 * @return  0--失败，1--成功，2---用户名存在
	 */
	public int addUserInfo(String lg_name,String lg_password,String name,String email){
			boolean bool=checkUser(lg_name);////检查用户是否存在
			if(bool){
				return 2;////  用户名存在
			}else{
				UtilDAO utildao= new UtilDAO();
				String md5password=StringMD5Util.getMD5String(lg_password);
				Map map= utildao.getMap();
				map.put("lg_name",lg_name);
				map.put("lg_password",md5password);
				map.put("name",name);
				map.put("email",email);
				map.put("last_login_time","");
				map.put("lg_role","2");
				map.put("lg_authority","A#B#C#D#E#F#G#H#I#J#K#L#M#N#O#P#Q#R#S#T#U#");//操作权限
				if(utildao.saveinfo(map,"xct_LG")){
					return 1;   //添加用户成功
				}else{
					return 0;   //添加用户失败
				}
			}
	}
	public boolean addUserInfo(List<String>list){
		String  []string=null;
		String lg_name;
		String lg_password;
		String name;
		String email="123456@163.com";
		String last_login_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String lg_role="2";
		String lg_authority="A#B#C#D#E#F#G#H#J#L#M#N#O#P#Q#R#S#T#";
		boolean flag=true;
		
		String selectsql="select lg_name from xct_LG where lg_name=?";
		
		String insertsql="insert into xct_LG (lg_name,lg_password,name,email,last_login_time,lg_role,lg_authority) values(?,?,?,?,?,?,?)";
		
		String updatesql="update xct_LG set lg_password=?,name=?,email=?,last_login_time=?,lg_role=?,lg_authority=? where lg_name=?";
		
		Connection con = super.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			for(String s:list){//12345###12345###张三
				string=s.split("###");
				if(string.length==3){
					lg_name=string[0];
					
					pstmt = con.prepareStatement(selectsql);
					pstmt.setString(1,lg_name);
					rs=pstmt.executeQuery();
					if(rs.next()){
						pstmt = con.prepareStatement(updatesql);
						lg_password=StringMD5Util.getMD5String(string[1]);
						name=string[2];
						pstmt.setString(1, lg_password);
						pstmt.setString(2, name);
						pstmt.setString(3,email);
						pstmt.setString(4, last_login_time);
						pstmt.setString(5, lg_role);
						pstmt.setString(6, lg_authority);
						pstmt.setString(7,lg_name);
						if(pstmt.executeUpdate()<0){
							flag=false;
						}
						if(!flag)
							break;
					}else{
						lg_password=StringMD5Util.getMD5String(string[1]);
						name=string[2];
						pstmt = con.prepareStatement(insertsql);
						pstmt.setString(1,lg_name);
						pstmt.setString(2, lg_password);
						pstmt.setString(3, name);
						pstmt.setString(4,email);
						pstmt.setString(5, last_login_time);
						pstmt.setString(6, lg_role);
						pstmt.setString(7, lg_authority);
						if(pstmt.executeUpdate()<0){
							flag=false;
						}
						if(!flag)
							break;
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			returnResources(rs,pstmt,con);
		}
	
		return flag;
	}
	
	/**
	 * 修改用户
	 * @param lg_name  用户名
	 * @param lg_password  密码
	 * @param name  姓名
	 * @param email  EMAIL
	 * @return  0--失败，1--成功
	 */
	public int updateUserInfo(String lg_name,String lg_password,String name,String email){
			UtilDAO utildao= new UtilDAO();
			String md5password=StringMD5Util.getMD5String(lg_password);
			Map map= utildao.getMap();
			map.put("lg_name",lg_name);
			map.put("lg_password",md5password);
			map.put("name",name);
			map.put("email",email);
			if(utildao.updateinfo(map,"xct_LG")){
				return 1;   //修改用户成功
			}else{
				return 0;   //修改用户失败
			}
	}
	/**
	 * 删除用户
	 * @param lg_name  用户名
	 * @return  0--失败，1--成功
	 */
	public int deleteUserInfo(String lg_name){
		UtilDAO utildao= new UtilDAO();
		DBConnection dbc= new DBConnection();
		Connection con = dbc.getConection();
		utildao.deleteinfo(con,"lg_name",lg_name,"xct_LG");
		utildao.upeateinfo(con,"zu_username=replace(zu_username,'||"+lg_name+"', '')","1=1","xct_zu");
		dbc.returnResources(con);
		return 1;
	}
///////////////////////////////////////
}
