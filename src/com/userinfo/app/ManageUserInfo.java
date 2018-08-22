package com.userinfo.app;

import com.xct.cms.dao.UsersDAO;

public class ManageUserInfo {
	/**
	 * 
	 * @param lg_name  用户名
	 * @param lg_password  密码
	 * @param name  姓名
	 * @param email  EMAIL
	 * @return
	 */
	public int addUserInfo(String lg_name,String lg_password,String name,String email){
		if(!"".equals(lg_name)&&!"".equals(lg_password)&&!"".equals(name)){
			UsersDAO userinfodao=new UsersDAO();
			 return userinfodao.addUserInfo(lg_name, lg_password, name, email);
		}
		return 0;
	}
	
	public int updateUserInfo(String lg_name,String lg_password,String name,String email){//根据登录名(电子邮件)，在各子系统修改用户信息
		if(!"".equals(lg_name)&&!"".equals(lg_password)&&!"".equals(name)){
			UsersDAO userinfodao=new UsersDAO(); 
			  return userinfodao.updateUserInfo(lg_name, lg_password, name, email);}
		return 0;
	}
	
	public int deleteUserInfo(String lg_name){//根据登录名(电子邮件)，在各子系统删除用户信息
		if(!"".equals(lg_name)){
			UsersDAO userinfodao=new UsersDAO();
			return userinfodao.deleteUserInfo(lg_name);
		}
		return 0;
	}
}
