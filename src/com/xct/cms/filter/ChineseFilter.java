package com.xct.cms.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import com.xct.cms.utils.Util;

public class ChineseFilter implements Filter {
	Logger logger = Logger.getLogger(ChineseFilter.class);
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) {
//		System.out.println("---------->"+request.getLocalPort());
		
		Util.localport=request.getLocalPort()+"";
		try{
		request.setCharacterEncoding("gbk");
		response.setCharacterEncoding("gbk");
		chain.doFilter(request,response);
		}catch(Exception e){
			//logger.info("¹ýÂËÆ÷³ö´í£¡====="+e.getMessage());
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
