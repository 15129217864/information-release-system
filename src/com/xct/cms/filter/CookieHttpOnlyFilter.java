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
 * �����⵽�Ựcookie��ȱ��HttpOnly���Ե�����
 * ����Cookie��Secure���ԡ�
 * ����Cookie��HttpOnly���ԡ�
 * https://blog.csdn.net/qq_31080089/article/details/54378011
 * 
 * https://blog.csdn.net/fall10/article/details/51969889,  tomcat 6 ����
 * https://blog.csdn.net/a19881029/article/details/27536917
 * 
 *����취��tomcat/conf/ ���ҵ�context.xml�޸�<Context useHttpOnly="true">,����ΪApache��������
 refer ��http://tomcat.apache.org/tomcat-6.0-doc/config/context.html

 */
public class CookieHttpOnlyFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		//==================================================================
//		����X-Frame-Options��Ӧͷ
//		����������ѡ��ֵ��
//		DENY
//		SAMEORIGIN
//		ALLOW-FROM origin
//		��ֵΪDENYʱ���������ܾ���ǰҳ������κ�frameҳ�棻��ֵΪSAMEORIGIN����frameҳ��ĵ�ַֻ��ΪͬԴ�����µ�ҳ�棻��ֵΪALLOW-FROM������Զ�������frame���ص�ҳ���ַ��
		resp.addHeader("x-frame-options","SAMEORIGIN"); 
//		resp.addHeader("X-XSS-Protection"," 0"); //����XSS����
		resp.addHeader("X-XSS-Protection","1;mode=block"); //����XSS����
		resp.addHeader("X-Content-Type-Options", "nosniff");
		resp.addHeader("Content-Security-Policy","img-src 'self'");
//		resp.addHeader("Content-Security-Policy","default-src 'self'");//��������������,��ע��
		//====================================================================
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
//			 for(Cookie cookie : cookies){
//			   //tomcat7 ֧�ָ����ԣ�tomcat6 ��֧��
//			   cookie.setHttpOnly(true);
//			 }
			//==================================================================
			for (Cookie cookie : cookies) {
			    //Servlet 2.5��֧����Cookie��ֱ������HttpOnly����???���������
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
