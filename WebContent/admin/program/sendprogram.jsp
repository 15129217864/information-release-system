<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="java.util.regex.Pattern"/>
<jsp:directive.page import="java.util.regex.Matcher"/>
<jsp:directive.page import="java.text.DateFormat"/>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="com.xct.cms.domin.Users"/>
<jsp:directive.page import="com.xct.cms.utils.DateUtils"/>
<%
		Users usere=(Users)request.getSession().getAttribute("lg_user");
		String playname=UtilDAO.getUTF(request.getParameter("playname"));
        String allip=request.getParameter("allips"); //ip
        String projectdirectory=request.getParameter("projectdirectory");//播放的节目名称
        String playtype=request.getParameter("playtype");//播放类型 0，1，2，3 playtype
        String templateid=request.getParameter("templateid") == null ? "5": request.getParameter("templateid");
        String startdate=request.getParameter("startdate");
        String starttime=request.getParameter("starttime");
        String enddate=request.getParameter("enddate");
        String endtime=request.getParameter("endtime");
        
        String startandend=request.getParameter("startandend");
        if(null!=startandend&&!"".equals(startandend)){
           enddate=startandend;
        }
        
		String timecount=request.getParameter("timecount");  //播放时长
		
		if(playtype.equals("loop")){
		    String[] datestring= DateUtils.addDate(startdate+" "+starttime,Integer.parseInt(timecount)).split(" ");
	        endtime=datestring[1];
		}
		
        if(playtype.equals("insert")||playtype.equals("foreverloop")){//只存在播放当天
          
               startdate=enddate;//页面的 $("startdateid").disabled="disabled"; 拿不到值，只能取 enddate 给startdate
           
	           starttime=new SimpleDateFormat("HH:mm:ss").format(new Date());
	           String[] datestring= DateUtils.addDate(startdate+" "+starttime,Integer.parseInt(timecount)).split(" ");
	           enddate=datestring[0];
	           endtime=datestring[1];
         }

		String ip = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
		Pattern macPattern = Pattern.compile(ip);
		UtilDAO utildao = new UtilDAO();
        String nowtime =UtilDAO.getNowtime("yyyy-MM-dd HH:mm:ss");
        //String nowtime1=UtilDAO.getNowtime("yyyy-MM-dd");
        
		Matcher macMatcher;
        String []iparray= allip.split("!");
        String newallips="";
        for(int i=0,n=iparray.length;i<n;i++){
        	macMatcher = macPattern.matcher(iparray[i]);
    		if (macMatcher.find()) {
				 newallips+="#"+macMatcher.group(0);
    		}
        }
        int type=0; // 循环
		if(playtype.equals("insert")){
			type=1; //插播
		}else if(playtype.equals("active")){
			type=2; //定时
		}else if(playtype.equals("defaultloop")){
			//playtype="loop"; //永久循环
		    type=3;
		}
		
	//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //Date d1 = df.parse(startdate+" "+starttime);
    //Date d2 = df.parse(startdate+" "+endtime);
    //long diff = d2.getTime() - d1.getTime();
    //long play_time = diff / (60 * 1000);
    
    String program_id=utildao.buildId("xct_JMApp","program_id","v.0000001");
        Map map= utildao.getMap();
        map.put("program_id",program_id);
        map.put("program_jmurl",projectdirectory);
        map.put("program_name",playname);
        map.put("program_playdate",startdate);
        map.put("program_playtime",starttime);
        map.put("program_enddate",enddate);
        map.put("program_endtime",endtime);
       // map.put("program_playlong",play_time+"");
        map.put("program_playlong",timecount+"");
        map.put("program_play_type",type+"");
        map.put("program_play_terminal",newallips);
        map.put("program_sendok_terminal","");
        map.put("program_app_userid",usere.getLg_name());
        map.put("program_app_time",nowtime);
        map.put("program_app_status","0");
        
        map.put("program_treeid","0");
        map.put("templateid",templateid);
	boolean bool=utildao.saveinfo(map,"xct_JMApp");
    if("1".equals(usere.getLg_role()) 
				    || "2".equals(usere.getLg_role())
				    || "3".equals(usere.getLg_role())){
		%>
		<script type="text/javascript">
		   <!--
			parent.deviceTop.location.href="/admin/program/ter_header2.jsp?title=&left_menu=&zu_id=";
			window.location.href="/admin/program/auditSendProgram.jsp?programid=<%=program_id%>";
		  //-->
		</script>
	<%}else{%>
		
		<script type="text/javascript">
		  <!--
			alert("节目上传成功，请待审核员审核...");
			parent.deviceTop.location.href="/admin/program/ter_header2.jsp?title=&left_menu=&zu_id=";
			parent.listtop.location.href="/admin/program/program_list_top2.jsp";
			window.location.href="/admin/program/sendprogramList.jsp";
		  //-->
		</script>
	<%}%>