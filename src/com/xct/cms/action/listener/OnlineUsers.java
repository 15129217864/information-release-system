package com.xct.cms.action.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.xct.cms.dao.LogsDAO;
import com.xct.cms.domin.Users;

public class OnlineUsers implements HttpSessionAttributeListener{  
	public static int session_user_count=0;  
	  public void attributeAdded(HttpSessionBindingEvent se){   
	  }  
	  public void attributeRemoved(HttpSessionBindingEvent se){  
		  if("lg_user".equals(se.getName())){  
			  Users user=(Users)se.getValue();
			  if(user!=null){
				  session_user_count--;
				new LogsDAO().addlogs(user.getLg_name(), new StringBuffer().append("<span style=color:red>用户").append(user.getLg_name()).append("退出系统！退出IP为：").append(user.getLogin_ip()).append("</span>").toString(), 1);
			  }
		  }  
	  }
	  public void attributeReplaced(HttpSessionBindingEvent se){}  
	}  

