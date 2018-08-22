package com.xct.cms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.xct.cms.domin.Users;

public class LoginFilter implements Filter {
	
	Logger logger = Logger.getLogger(LoginFilter.class);
	
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
			
		HttpServletRequest httpreq = (HttpServletRequest) request;
		HttpServletResponse httpres = (HttpServletResponse) response;
		//==================================================================
		Users user = (Users) httpreq.getSession().getAttribute("lg_user");
		if (null == user) {
//			logger.info("httpreq.getRequestURI()====>"+httpreq.getRequestURI());
			httpres.getWriter().print("<script language=javascript>parent.parent.parent.parent.location.href= '/index.jsp'; </script>");
		    return;
		}
		chain.doFilter(request,response);
	}
	
	public void destroy() {}
	public void init(FilterConfig arg0) throws ServletException {}
}
