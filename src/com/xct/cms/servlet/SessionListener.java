package com.xct.cms.servlet;

import javax.servlet.http.*;

import com.xct.cms.dao.LogsDAO;
import com.xct.cms.domin.Users;

import java.util.*;

public class SessionListener implements HttpSessionListener {

	public static HashMap<String,Users> hUserNamemap = new HashMap<String,Users>();// ����sessionID��username��ӳ��
	/** ������ʵ��HttpSessionListener�еķ���* */
	public void sessionCreated(HttpSessionEvent se) {}
	
	public static int getSession_user_count() {
		return hUserNamemap.size();
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		String session_id=se.getSession().getId();
		Users user=(Users)se.getSession().getAttribute("lg_user");
		 if(user!=null){
			 new LogsDAO().addlogs(user.getName(), "�û���"+user.getName()+"���˳�ϵͳ���˳�IPΪ��"+user.getLogin_ip()+"", 1);
		 }
		hUserNamemap.remove(session_id);
	}

	public static void isAlreadyEnter(HttpSession session, String sUserName,Users users) {
			
		//boolean flag = false;
		/*if (hUserNamemap.containsValue(sUserName)) {
			// ������û��Ѿ���¼������ʹ�ϴε�¼���û�����(����ʹ�û����Ƿ���hUserName��)
			flag = true;
			String key="";
			for(Map.Entry<String,String>entry:hUserNamemap.entrySet()){
				 key = entry.getKey();
				String val = entry.getValue();
				if (val.equals(sUserName)) {
					Users user=(Users)session.getAttribute("lg_user");
					if(user!=null){
						 //new LogsDAO().addlogs(user.getName(), "�û���"+user.getName()+"���������ط���¼����ǰ�û����߳����߳�IPΪ��"+user.getLogin_ip()+"", 1);
						 break;
					}
				}
			}*/
			//hUserNamemap.remove(key);
			//hUserNamemap.put(session.getId(), sUserName);// ������ڵ�sessionID��username
//			System.out.println("�û���¼��----hUserName = " + sUserName);
		//} else {
			// ������û�û��¼����ֱ��������ڵ�sessionID��username
			//flag = false;
			hUserNamemap.put(session.getId(), users);
//			System.out.println("�û�û��¼��----hUserName = " + sUserName);
		//}
		session.setAttribute("lg_user", users);
		session.setAttribute("logined", session.getId());
//		System.out.println("session.getId()----------------->"+session.getId());
		
		//return flag;
	}

	/*	2.����ҳ�����SessionListener.isOnline(session),�����жϸ��û��Ƿ�����.
	 * isOnline-�����ж��û��Ƿ����� @param session HttpSession-��¼���û����� @return
	 * boolean-���û��Ƿ����ߵı�־
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
