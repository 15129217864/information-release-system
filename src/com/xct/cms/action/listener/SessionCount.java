package com.xct.cms.action.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCount implements HttpSessionListener{  
	  private static int session_count=0;  

	  public void sessionCreated(HttpSessionEvent se){  
		  
		  session_count++;  
		  ///////System.out.println("session´´½¨£º"+new java.util.Date());  
	  }  

	  public void sessionDestroyed(HttpSessionEvent se){  
		  session_count--;  
		 // /////System.out.println("sessionÏú»Ù:"+new java.util.Date());  
	  }  

	  public static int getSessionCount(){  
	 	return(session_count);  
	  }  
	}  

