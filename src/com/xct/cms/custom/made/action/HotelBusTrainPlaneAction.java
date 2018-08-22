package com.xct.cms.custom.made.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sun.xml.bind.v2.model.core.ID;
import com.xct.cms.custom.made.bean.BusBean;
import com.xct.cms.custom.made.bean.PlaneBean;
import com.xct.cms.custom.made.bean.TrainBean;
import com.xct.cms.custom.made.dao.TBPCrudUtilDao;
import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.Util;

public class HotelBusTrainPlaneAction extends Action {
	
//	public final static String TRAIN="http://www.yctelecom.com.cn/bmfw/bmfw_train_time1.asp";//盐城火车时刻表
//	
//	public final static String BUS="http://www.yctelecom.com.cn/bmfw/bmfw_bus2.asp";//盐城汽车时刻表
//	
//	public final static String OUTPLANE="http://www.yccas.com/hangbanxx_1.asp?wz=%B3%F6%B8%DB%BA%BD%B0%E0";//盐城飞机时刻（出港航班）
//	
//	public final static String INPLANE="http://www.yccas.com/hangbanxx_1.asp?wz=%BD%F8%B8%DB%BA%BD%B0%E0";//盐城飞机时刻（进港航班）
//
//	public final static String HOTELINSTR="http://www.9tour.cn/guoqing/104489/";//	酒店介绍，加酒店图片混拼显示
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		TBPCrudUtilDao tbpcrudutildao=new TBPCrudUtilDao();
		String op = request.getParameter("op");
		String opcity=request.getParameter("opcity");
//		System.out.println("-------------------"+op);
		if (null != op&&!op.isEmpty()) {
			if ("introduce".equals(op)) {
				introduce(request);
			} else if ("bus".equals(op)) {
				if("ajax".equals(opcity)){
					PrintWriter out;
					try {
						out = response.getWriter();
						String address=request.getParameter("address");
						if(null!=address){
							try {
								address=URLDecoder.decode(address,"UTF-8");
								System.out.println("address---------------->"+address);
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}
						List<String>list=tbpcrudutildao.SelectBusTerminus(address);
						StringBuffer sBuffer=new StringBuffer();
						int i=list.size()+10;
						for(String s:list){
							++i;
							sBuffer.append("<input type='checkbox' name='cr"+i+"'  id='cr"+i+"' value='"+s+"'/><label for='cr"+i+"'>"+s+"</label>");
						}
						System.out.println(sBuffer.toString());
						out.print(sBuffer.toString());
						out.flush();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				}else{
					bus(tbpcrudutildao,request);
				}
			} else if ("train".equals(op)) {
				train(tbpcrudutildao,request);
			} else if ("plane".equals(op)) {
				plane(tbpcrudutildao,request);
			}else if ("weather".equals(op)) {
				PrintWriter out;
				try {
					out = response.getWriter();
					String weather=Util.getFileValues(FirstStartServlet.projectpath+"bustrainplane/weather.txt");
				    if(weather.indexOf("#")!=-1){
				    	weather=weather.replaceAll("#","<br/><br/>").replaceAll("气温：", "&nbsp;&nbsp;&nbsp;&nbsp;").replaceAll("风力：", "&nbsp;&nbsp;&nbsp;&nbsp;");
				    }else {
						weather="获取天气预报异常";
					}
//				    System.out.println("weather--------->"+weather);
					out.print(weather);
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			return mapping.findForward(op);
		}
		return mapping.findForward("index");
	}

	public void introduce(HttpServletRequest request) {
		
		String introduceimage=FirstStartServlet.projectpath+"images/customer/";
	    String introduce=Util.getFileValues(FirstStartServlet.projectpath+"bustrainplane/hotelinstr.txt");
	    String weather=Util.getFileValues(FirstStartServlet.projectpath+"bustrainplane/weather.txt");
	    if(weather.indexOf("#")!=-1){
	    	weather="&nbsp;&nbsp;"+weather.replaceAll("#","<br/><br/>&nbsp;&nbsp;").replaceAll("气温：", "&nbsp;&nbsp;&nbsp;&nbsp;").replaceAll("风力：", "&nbsp;&nbsp;&nbsp;&nbsp;");
	    }else {
			weather="获取天气预报异常";
		}
	    File file=new File(introduceimage);
	    List<String>list=new ArrayList<String>();
	    if(file.exists()){
	    	File[]filetempFiles=file.listFiles();
	    	for(File file2:filetempFiles){
	    		if(isJpg(file2.getName())){
	    		   list.add(file2.getName());
	    		}
	    	}
	    }
	    request.setAttribute("introduce", introduce.replaceAll("12px", "20px"));
	    request.setAttribute("weather", weather);  
	    request.setAttribute("introduceimage", list);
	}
	
	public boolean isJpg(String string) {
		if(string.toLowerCase().endsWith(".jpg")||string.toLowerCase().endsWith(".png")
				||string.toLowerCase().endsWith(".gif")||string.toLowerCase().endsWith(".bmp")){
			return true;
		}
		return false;
	}

	public void bus(TBPCrudUtilDao tbpcrudutildao,HttpServletRequest request) {
		
		String address=request.getParameter("address");
		if(null!=address){
			try {
				address=URLDecoder.decode(address,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		String address2=request.getParameter("address2");
		if(null!=address2){
			try {
				address2=URLDecoder.decode(address2,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		 if(null!=address){
			 
			 request.setAttribute("address",address);
			 if(null!=address2){
				 request.setAttribute("address2",address2);
				 request.setAttribute("buslist", tbpcrudutildao.SelectBus(" 1=1 and terminus like '%"+address2+"%' order by province desc"));
			 }else{
				 request.setAttribute("buslist", tbpcrudutildao.SelectBus(" 1=1 and province like '%"+address+"%' order by province desc"));
			 }
			 request.setAttribute("citylist",tbpcrudutildao.SelectBusTerminus(address));
			 
		 }else{
			 request.setAttribute("buslist", tbpcrudutildao.SelectBus(" 1=1 order by province desc"));
		 }
		 request.setAttribute("provincelist", tbpcrudutildao.SelectBusProvince());
	}

	public void train(TBPCrudUtilDao tbpcrudutildao,HttpServletRequest request) {
		
		String address=request.getParameter("address");
		if(null!=address){
			try {
				address=URLDecoder.decode(address,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			request.setAttribute("trainlist", tbpcrudutildao.SelectTrain(" 1=1 and daozhan='"+address+"' order by fashi"));
		}else {
			request.setAttribute("trainlist", tbpcrudutildao.SelectTrain(" 1=1 order by fashi"));
		}
		request.setAttribute("citylist",  new ArrayList<String>(tbpcrudutildao.SelectTrainDaoZhan()));
		request.setAttribute("address",address);
		 
	}

	public void plane(TBPCrudUtilDao tbpcrudutildao,HttpServletRequest request) {
		
		String inorout=request.getParameter("inorout");
		inorout=(null==inorout?"2":inorout);
		String address=request.getParameter("address");
		if(null!=address){
			try {
				address=URLDecoder.decode(address,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			request.setAttribute("planelist", tbpcrudutildao.SelectPlane(" inorout="+inorout+" and airline like '%"+address+"'"));//出港 2， 进港 1
		}else {
			request.setAttribute("planelist", tbpcrudutildao.SelectPlane(" inorout="+inorout));//出港 2， 进港 1
		}
		request.setAttribute("inorout", inorout);
		request.setAttribute("citylist", new ArrayList<String>(tbpcrudutildao.SelectPlaneAirline()));
		request.setAttribute("address",address);
	}
}