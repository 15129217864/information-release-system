<%@ page language="java" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="com.xct.cms.domin.Led"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="java.util.Timer"/>
<jsp:directive.page import="java.util.TimerTask"/>
<%!private Map<String,Led> ledmap1=new HashMap<String, Led>(); %>
<%
String title=Util.getGBK(request.getParameter("title"));
String ip=request.getParameter("ip");
String width=request.getParameter("width");
String height=request.getParameter("height");
String fontName=Util.getGBK(request.getParameter("fontName"));
String fontColor=request.getParameter("fontColor");
String text=Util.getGBK(request.getParameter("text"));
String[] ledips=request.getParameterValues("ledip");
if(ledips!=null&&ledips.length>0){
	UtilDAO utildao= new UtilDAO();
	Map map= utildao.getMap();
	
	if(ledips.length==1){
		map.put("ip",ledips[0]);
		//map.put("ip",ip);
		//map.put("title",title);
		//map.put("width",width);
		//map.put("height",height);
		map.put("text",text);
		map.put("fontName",fontName);
		map.put("fontColor",fontColor);
		utildao.updateinfo(map,"led");
		
		Led led= new Led();
		led.setIp(ip) ;
		led.setTitle(title) ;
		led.setWidth(Integer.parseInt(width)) ;
		led.setHeight(Integer.parseInt(height)) ;
		led.setText(text) ;
		led.setFontName(fontName) ;
		led.setFontColor(Integer.parseInt(fontColor)) ;
		led.setPlayspeed(15);
		
		led.sendLedByled(led,1); 
		led.runtime();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			//System.out.println("---Thread.sleep(1000---->"+e.getMessage());
		}
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
		utildao.updateinfo(conn,map,"led");
		
		ipsss+=ledips[i]+",";
	}
	Led led= new Led();
	ledmap1=led.getALLLedMap(" where  CHARINDEX (ip,'"+ipsss+"')<>0 ");
	dbconn.returnResources(conn);
	
//	new Thread(new Runnable() {
	//		public void run() {
				//Led led= new Led();
					led.sendLedInfo(ledmap1);
		//	}
		//}).start();
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			//System.out.println("---Thread.sleep(1000---->"+e.getMessage());
		}
	}
}




%>
<script type="text/javascript">
<!--
window.location.href="/admin/program/ledset.jsp";
//-->
</script>