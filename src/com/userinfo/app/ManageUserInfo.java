package com.userinfo.app;

import com.xct.cms.dao.UsersDAO;

public class ManageUserInfo {
	/**
	 * 
	 * @param lg_name  �û���
	 * @param lg_password  ����
	 * @param name  ����
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
	
	public int updateUserInfo(String lg_name,String lg_password,String name,String email){//���ݵ�¼��(�����ʼ�)���ڸ���ϵͳ�޸��û���Ϣ
		if(!"".equals(lg_name)&&!"".equals(lg_password)&&!"".equals(name)){
			UsersDAO userinfodao=new UsersDAO(); 
			  return userinfodao.updateUserInfo(lg_name, lg_password, name, email);}
		return 0;
	}
	
	public int deleteUserInfo(String lg_name){//���ݵ�¼��(�����ʼ�)���ڸ���ϵͳɾ���û���Ϣ
		if(!"".equals(lg_name)){
			UsersDAO userinfodao=new UsersDAO();
			return userinfodao.deleteUserInfo(lg_name);
		}
		return 0;
	}
}
