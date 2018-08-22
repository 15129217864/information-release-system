package com.xct.cms.servlet;

import javax.servlet.http.*;

import com.xct.cms.dao.LogsDAO;
import com.xct.cms.domin.Users;

import java.util.*;

public class SessionListener implements HttpSessionListener {

	public static HashMap<String,Users> hUserNamemap = new HashMap<String,Users>();// 保存sessionID和username的映射
	/** 以下是实现HttpSessionListener中的方法* */
	public void sessionCreated(HttpSessionEvent se) {}
	
	public static int getSession_user_count() {
		return hUserNamemap.size();
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		String session_id=se.getSession().getId();
		Users user=(Users)se.getSession().getAttribute("lg_user");
		 if(user!=null){
			 new LogsDAO().addlogs(user.getName(), "用户【"+user.getName()+"】退出系统！退出IP为："+user.getLogin_ip()+"", 1);
		 }
		hUserNamemap.remove(session_id);
	}

	public static void isAlreadyEnter(HttpSession session, String sUserName,Users users) {
			
		//boolean flag = false;
		/*if (hUserNamemap.containsValue(sUserName)) {
			// 如果该用户已经登录过，则使上次登录的用户掉线(依据使用户名是否在hUserName中)
			flag = true;
			String key="";
			for(Map.Entry<String,String>entry:hUserNamemap.entrySet()){
				 key = entry.getKey();
				String val = entry.getValue();
				if (val.equals(sUserName)) {
					Users user=(Users)session.getAttribute("lg_user");
					if(user!=null){
						 //new LogsDAO().addlogs(user.getName(), "用户【"+user.getName()+"】在其他地方登录，当前用户被踢出！踢出IP为："+user.getLogin_ip()+"", 1);
						 break;
					}
				}
			}*/
			//hUserNamemap.remove(key);
			//hUserNamemap.put(session.getId(), sUserName);// 添加现在的sessionID和username
//			System.out.println("用户登录过----hUserName = " + sUserName);
		//} else {
			// 如果该用户没登录过，直接添加现在的sessionID和username
			//flag = false;
			hUserNamemap.put(session.getId(), users);
//			System.out.println("用户没登录过----hUserName = " + sUserName);
		//}
		session.setAttribute("lg_user", users);
		session.setAttribute("logined", session.getId());
//		System.out.println("session.getId()----------------->"+session.getId());
		
		//return flag;
	}

	/*	2.其他页面调用SessionListener.isOnline(session),可以判断该用户是否在线.
	 * isOnline-用于判断用户是否在线 @param session HttpSession-登录的用户名称 @return
	 * boolean-该用户是否在线的标志
	 */
	public static boolean isOnline(HttpSession session) {
		boolean flag = true;
		if (hUserNamemap.containsKey(session.getId())) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
}
