<%@ page language="java" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="com.xct.cms.domin.Led"/>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="com.xct.cms.httpclient.SaudiHttpClient"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.utils.DESPlusUtil"/>
<jsp:directive.page import="com.xct.cms.dao.LedDao"/>
<%
//高邮--仰邦BX-5M2网口通讯
String pno=request.getParameter("pno");
String title=Util.getGBK(request.getParameter("title"));
String ip=request.getParameter("ip");//LED条屏ip地址
String x=request.getParameter("x");
String y=request.getParameter("y");
 
String width=request.getParameter("width");
String height=request.getParameter("height");
String fontName=Util.getGBK(request.getParameter("fontName")); 
String fontsize=request.getParameter("fontsize");
String fontColor=request.getParameter("fontColor")==null?"1":request.getParameter("fontColor");
 
String text=Util.getGBK(request.getParameter("text"));

String stunt=request.getParameter("stunt");
String playspeed=request.getParameter("playspeed");

String isbold=request.getParameter("isbold")==null?"false":"true";
String isitic=request.getParameter("isitic")==null?"false":"true";
String isunderline=request.getParameter("isunderline")==null?"false":"true";

String l_num=request.getParameter("l_num")==null?"":request.getParameter("l_num");
String def_text=Util.getGBK(request.getParameter("def_text"));
String sleeptime=request.getParameter("sleeptime")==null?"0":request.getParameter("sleeptime");

String s_time1=request.getParameter("s_time1")==null?"":request.getParameter("s_time1");
String s_time2=request.getParameter("s_time2")==null?"":request.getParameter("s_time2");
String s_time3=request.getParameter("s_time3")==null?"":request.getParameter("s_time3");
String s_time4=request.getParameter("s_time4")==null?"":request.getParameter("s_time4");
String s_time5=request.getParameter("s_time5")==null?"":request.getParameter("s_time5");

String e_time1=request.getParameter("e_time1")==null?"":request.getParameter("e_time1");
String e_time2=request.getParameter("e_time2")==null?"":request.getParameter("e_time2");
String e_time3=request.getParameter("e_time3")==null?"":request.getParameter("e_time3");
String e_time4=request.getParameter("e_time4")==null?"":request.getParameter("e_time4");
String e_time5=request.getParameter("e_time5")==null?"":request.getParameter("e_time5");

String s_text1=Util.getGBK(request.getParameter("s_text1"));
String s_text2=Util.getGBK(request.getParameter("s_text2"));
String s_text3=Util.getGBK(request.getParameter("s_text3"));
String s_text4=Util.getGBK(request.getParameter("s_text4"));
String s_text5=Util.getGBK(request.getParameter("s_text5"));
String clientip=pno.split("!")[0];
String mac=pno.split("!")[1];
//String[] ledips=request.getParameterValues("ledip");
if(ip!=null&&ip.length()>0){
       LedDao leddao=new LedDao();
       
		    UtilDAO utildao= new UtilDAO();
		    Map map= utildao.getMap();
			map.put("pno",clientip);//终端机的ip地址
			map.put("ip",ip);//LED条屏的ip地址
			map.put("title",title);
			map.put("x",x);
			map.put("y",y);
			map.put("width",width);
			map.put("height",height);
			map.put("text",text);
			map.put("fontName",fontName);
			map.put("fontColor",fontColor);
			map.put("fontsize",fontsize);
			
			map.put("stunt",stunt);
			map.put("playspeed",playspeed);
			
			map.put("bold",isbold);
			map.put("italic",isitic);
			map.put("underline",isunderline);
			
			map.put("sleeptime",sleeptime);
			map.put("l_num",l_num);
			map.put("def_text",def_text);
			
			map.put("s_time1",s_time1);
			map.put("s_time2",s_time2);
			map.put("s_time3",s_time3);
			map.put("s_time4",s_time4);
			map.put("s_time5",s_time5);
			map.put("e_time1",e_time1);
			map.put("e_time2",e_time2);
			map.put("e_time3",e_time3);
			map.put("e_time4",e_time4);
			map.put("e_time5",e_time5);
			map.put("s_text1",s_text1);
			map.put("s_text2",s_text2);
			map.put("s_text3",s_text3);
			map.put("s_text4",s_text4);
			map.put("s_text5",s_text5);
			if(leddao.getLedBean(" where pno='"+clientip+"'")){
			  utildao.updateinfo(map,"led");
			}else{
			  utildao.saveinfo(map,"led");
			}
	
			Led led= new Led();
			String ledtxt=mac+".txt";
			//System.out.println("发送LED文字长度----------> "+text.length());
		
			StringBuffer sb=new StringBuffer(ip);
	
			//----------------------------------------------------------------------------------
			/**如下式陕西延安 串口发送文字到LED屏*/
			
			sb.append("@").append(x).append("@").append(y).append("@").append(width).append("@").append(height).append("@").append(fontName).append("@")
			.append(fontsize).append("@").append(isbold.equals("true")?"1":"0").append("@").append(fontColor).append("@")
			.append(stunt).append("@").append(playspeed).append("@").append(sleeptime).append("@").append(ledtxt).append("@").append(l_num);
			
		    //lv0090$192.168.10.199@0@0@128@16@宋体@12@0@1@4@3@0@ledshowtext.txt@0x0252
		    
			led.writeFiletoSingle(FirstStartServlet.projectpath+"serverftp/program_file/ledshow/"+ledtxt,text,false,false);
			//System.out.println("lv0090#"+sb.toString());
			FirstStartServlet.client.sendHTTP(clientip, DESPlusUtil.Get().encrypt("lv0090#"+sb.toString()));
		//*******************************************************************************************************
		
		}%>
		<script type="text/javascript">
	<!-- 
	
	window.location.href="/admin/program/ledshow/ledset.jsp?ip=<%=clientip%>&mac=<%=mac%>&isok=ok";
	//-->
	</script>
