<%@ page language="java" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="com.xct.cms.domin.Led"/>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="com.xct.cms.httpclient.SaudiHttpClient"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.utils.DESPlusUtil"/>
<%
	//陕西咸阳串口版--灵信的卡
String title=Util.getGBK(request.getParameter("title"));
String ip=request.getParameter("ip");
String reallyledip=request.getParameter("reallyledip");
 
String x=request.getParameter("x");
String y=request.getParameter("y");
 
String width=request.getParameter("width");
String height=request.getParameter("height");
String fontName=Util.getGBK(request.getParameter("fontName")); 
String fontsize=request.getParameter("fontsize");
String fontColor=request.getParameter("fontColor")==null?"255":request.getParameter("fontColor");
 
String text=Util.getGBK(request.getParameter("text"));

String stunt=request.getParameter("stunt");
String playspeed=request.getParameter("playspeed");

String isbold=request.getParameter("isbold")==null?"false":"true";
String isitic=request.getParameter("isitic")==null?"false":"true";
String isunderline=request.getParameter("isunderline")==null?"false":"true";

String l_num=request.getParameter("l_num")==null?"":request.getParameter("l_num");
String def_text=Util.getGBK(request.getParameter("def_text"));

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

String[] ledips={reallyledip};

//String[] ledips=request.getParameterValues("ledip");
if(ledips!=null&&ledips.length>0){
	UtilDAO utildao= new UtilDAO();
	Map map= utildao.getMap();
	
	if(ledips.length==1){
	    
		map.put("ip",ledips[0]);
		map.put("ip",ip);
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
		
		map.put("sleeptime",0);
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
		
		utildao.updateinfo(map,"led");
		
	Led led= new Led();
	String ledtxt=ip.replace(".","")+".rtf";//陕西延安
	//String ledtxt=ip.replace(".","")+".txt";
	//System.out.println("发送LED文字长度----------> "+text.length());

	StringBuffer sb=new StringBuffer("SendSingleLineText");
	
	//**************************************************************************
	/**如下是山东济宁老蔡 网口发送文字到LED屏*/
	//sb.append("#").append(ip).append("#").append(x).append("#").append(y).append("#").append(width).append("#")
	//.append(height).append("#").append(fontName).append("#").append(fontsize).append("#").append(fontColor).append("#")
	//.append(isbold).append("#").append(isitic).append("#").append(isunderline).append("#").append(Integer.parseInt(stunt)).append("#")
	//.append(Integer.parseInt(playspeed)).append("#").append(ledtxt);
  // //SendSingleLineText#192.168.10.200#0#0#128#16#幼圆#12#255#false#false#false#32#20#led.txt
  //	 System.out.println("----->"+sb.toString());
	//led.writeFiletoSingle(FirstStartServlet.projectpath+"led/"+ledtxt,text,false,false);
	//FirstStartServlet.client.StartLEDjar(sb.toString());
	//----------------------------------------------------------------------------------
	
	//----------------------------------------------------------------------------------
	/**如下式陕西延安 串口发送文字到LED屏*/
	
	sb.append("#").append(x).append("#").append(y).append("#").append(width).append("#").append(height).append("#").append(fontName).append("#")
	.append(fontsize).append("#").append(fontColor).append("#").append(isbold).append("#").append(isitic).append("#").append(isunderline).append("#")
	.append(Integer.parseInt(stunt)).append("#").append(Integer.parseInt(playspeed)).append("#").append(ledtxt);
    //SendSingleLineText#0#0#128#16#幼圆#12#255#false#false#false#32#20#led.txt
	//led.writeFiletoSingle(FirstStartServlet.projectpath+"serverftp/program_file/"+ledtxt,text,false,false);
	String rtfpath=FirstStartServlet.projectpath+"serverftp/program_file/"+ledtxt;
	if(com.xct.cms.utils.RTFCreate.createRTFContext(rtfpath,text,fontName,Integer.parseInt(fontsize),java.awt.Color.RED,1,1,1)){
		//System.out.println("lv0089@"+sb.toString());
		FirstStartServlet.client.sendHTTP(ip, DESPlusUtil.Get().encrypt("lv0089@"+sb.toString()));
	}else{
	   //System.out.println("---生成RTF文件失败，停止发送led文字------");
	}
	
	//*******************************************************************************************************
%>
<script type="text/javascript">
<!--
window.location.href="/admin/program/ledset.jsp?ip=<%=ip%>";
//-->
</script>
<%}else{
	DBConnection dbconn=new DBConnection();
	Connection conn=dbconn.getConection();
	String ipsss="";
	for(int i=0;i<ledips.length;i++){
		map= utildao.getMap();
		map.put("ip",ledips[i]);
		map.put("text",text);
		map.put("fontName",fontName);
		map.put("fontColor",fontColor);
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
		
		utildao.updateinfo(conn,map,"led");
		
		ipsss+=ledips[i]+",";
	}
	Led led= new Led();
	Map<String,Led> ledmap1=led.getALLLedMap(" where  CHARINDEX (ip,'"+ipsss+"')<>0 ");
	dbconn.returnResources(conn);
	
	SaudiHttpClient.sendTicketInfoBymap(ledmap1);
	}
}




%>
<script type="text/javascript">
<!--
window.location.href="/admin/program/ledset.jsp";
//-->
</script>