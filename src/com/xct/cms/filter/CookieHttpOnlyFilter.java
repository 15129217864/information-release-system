package com.xct.cms.filter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 解决检测到会话cookie中缺少HttpOnly属性的问题
 * 开启Cookie的Secure属性。
 * 开启Cookie的HttpOnly属性。
 * https://blog.csdn.net/qq_31080089/article/details/54378011
 * 
 * https://blog.csdn.net/fall10/article/details/51969889,  tomcat 6 配置
 * https://blog.csdn.net/a19881029/article/details/27536917
 * 
 *处理办法在tomcat/conf/ 下找到context.xml修改<Context useHttpOnly="true">,以下为Apache官网描述
 refer ：http://tomcat.apache.org/tomcat-6.0-doc/config/context.html

 */
public class CookieHttpOnlyFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		//==================================================================
//		设置X-Frame-Options响应头
//		它有三个可选的值：
//		DENY
//		SAMEORIGIN
//		ALLOW-FROM origin
//		当值为DENY时，浏览器会拒绝当前页面加载任何frame页面；若值为SAMEORIGIN，则frame页面的地址只能为同源域名下的页面；若值为ALLOW-FROM，则可以定义允许frame加载的页面地址。
		resp.addHeader("x-frame-options","SAMEORIGIN"); 
//		resp.addHeader("X-XSS-Protection"," 0"); //禁用XSS保护
		resp.addHeader("X-XSS-Protection","1;mode=block"); //开启XSS保护
		resp.addHeader("X-Content-Type-Options", "nosniff");
		resp.addHeader("Content-Security-Policy","img-src 'self'");
//		resp.addHeader("Content-Security-Policy","default-src 'self'");//这样配置有问题,先注释
		//====================================================================
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
//			 for(Cookie cookie : cookies){
//			   //tomcat7 支持该属性，tomcat6 不支持
//			   cookie.setHttpOnly(true);
//			 }
			//==================================================================
			for (Cookie cookie : cookies) {
			    //Servlet 2.5不支持在Cookie上直接设置HttpOnly属性???，好像可以
				String value = cookie.getValue();
				StringBuilder builder = new StringBuilder();
				builder.append("JSESSIONID=").append(value).append("; ");
				builder.append("Secure; ");
				builder.append("HttpOnly; ");
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.HOUR, 1);
				Date date = cal.getTime();
				Locale locale = Locale.CHINA;
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", locale);
				builder.append("Expires=" + sdf.format(date));
				resp.setHeader("Set-Cookie", builder.toString());
			}
		}else{
//			System.out.println("*******Cookie Array is null********");
			StringBuilder builder = new StringBuilder();
			builder.append(new StringBuilder().append("JSESSIONID=").append(req.getSession().getId()).append("; ").toString());
			builder.append("Secure; ");
			builder.append("HttpOnly; ");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR, 1);
			Date date = cal.getTime();
			Locale locale = Locale.CHINA;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", locale);
			builder.append("Expires=" + sdf.format(date));
			resp.setHeader("Set-Cookie", builder.toString());
			//==================================================================
		}
		filterChain.doFilter(request, response);
	
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

	public void destroy() {}

}
