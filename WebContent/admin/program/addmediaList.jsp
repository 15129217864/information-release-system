<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO" />
<jsp:directive.page import="com.xct.cms.utils.UtilDAO" />
<jsp:directive.page import="com.xct.cms.domin.Module" />
<jsp:directive.page import="com.xct.cms.dao.MediaDAO"/>
<jsp:directive.page import="com.xct.cms.domin.Media"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="com.xct.cms.servlet.FirstStartServlet"/>
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
//out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
	String opp = request.getParameter("opp");
	String moduleid = request.getParameter("moduleid") == null ? "0": request.getParameter("moduleid");
	String mediaids = request.getParameter("mediaids");
	String templateid=request.getParameter("templateid");
	request.setAttribute("templateid",templateid);
	request.setAttribute("mediaids",mediaids);
	
	String bg=request.getParameter("bg");
	request.setAttribute("bg",bg);
	request.setAttribute("opp",opp);
	request.setAttribute("mdate",new Date().getTime());
	//System.out.println(opp+"___"+bg);
	
	String save_state=request.getParameter("save_state")==null?"":request.getParameter("save_state");
	request.setAttribute("save_state",save_state);
	UtilDAO utildao = new UtilDAO();
	String nowtime=UtilDAO.getNowtime("yyyy年MM月dd日");
	ModuleDAO moduledao = new ModuleDAO();
	DBConnection dbconn= new DBConnection();
	Connection conn=dbconn.getConection();
	int images_span=8;
	Module module = moduledao.getModuleTempByModuleid(conn,Integer.parseInt(moduleid));
			
	if ("2".equals(opp)) {
		String mediatype = module.getM_filetype();
		String mediatypeEN = module.getM_filetypeEN();
		String m_filetypeZN = module.getM_filetypeZN();
		if ("".equals(mediatype)) {
			out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
			out.print("<script>");
			out.print("alert('提示信息：请先选择该播放区域的播放类型.');");
			out.print("document.getElementById('loadid').style.display='none'");
			out.print("</script>");
		}else{
		    //System.out.println(moduleid+"<--------------->"+mediaids);
			String mediaArray[] = mediaids.split(",");
			boolean bool = true;
			boolean bool1=true;
			for (int i = 1; i < mediaArray.length; i++) {
				String mtype = mediaArray[i].split("_")[1];////【0】媒体ID，【1】媒体类型
			
				if (!mtype.equals(mediatypeEN) && "scroll".equals(mediatype)) {
					out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
					out.print("<script>");
					out.print("alert('提示信息：该播放区域的播放类型为：" + m_filetypeZN+ "，只能导入文本文件');");
					out.print("document.getElementById('loadid').style.display='none'");
					out.print("</script>");
					bool = false;
					bool1 = false;
					break;
				}else if (!mtype.equals(mediatypeEN)  && "web".equals(mediatype)) {
					out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
					out.print("<script>");
					out.print("alert('提示信息：该播放区域的播放类型为：" + m_filetypeZN+ "，只能导入网页文件');");
					out.print("document.getElementById('loadid').style.display='none'");
					out.print("</script>");
					bool = false;
					bool1 = false;
					break;
				}else if (!mtype.equals(mediatypeEN)&& "khmeetingnotice".equals(mediatype)) {//
				   // System.out.println(mtype+" **************** "+mediatypeEN);
				    mtype=mediatypeEN;
					break;
				}else if (!mtype.equals(mediatypeEN)&& "backgroundmusic".equals(mediatype)) {//
				
				    //System.out.println(mtype+" **************** "+mediatypeEN);
				    mtype=mediatypeEN;
					break;
				}else if (!mtype.equals(mediatypeEN)&& "policesubstation".equals(mediatype)) {//奉浦派出所
				    mtype=mediatypeEN;
					break;
				}else if (!mtype.equals(mediatypeEN)&& "htmeetingnotice".equals(mediatype)) {//会议预订系统显示，大连会议 
				    mtype=mediatypeEN;
					break;
				}else if (!mtype.equals(mediatypeEN)&& "insnquametting".equals(mediatype)) {//浦东检验检疫会议 
				    mtype=mediatypeEN;
					break;
				}else if (!mtype.equals(mediatypeEN)&& "nordermeeting".equals(mediatype)) {//联合利华非预定会议预订系统显示
				    mtype=mediatypeEN;
					break;
				}else if (!mtype.equals(mediatypeEN)) {
					out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
					out.print("<script>");
					out.print("alert('提示信息：该播放区域的播放类型为：" + m_filetypeZN+ "，只能播放" + m_filetypeZN + "文件');");
					out.print("document.getElementById('loadid').style.display='none'");
					out.print("</script>");
					bool = false;
					bool1 = false;
					break;
				}
			}
		if(bool1){
			if("scroll".equals(mediatype)){
					String mediaid=mediaids.split(",")[1].split("_")[0];
					MediaDAO mediadao= new MediaDAO();
					Media me=mediadao.getMediaBy(conn,mediaid);
					String scrollcontent=Util.readfile(FirstStartServlet.projectpath+me.getFile_path()+me.getFile_name());
					if(scrollcontent.length()>1000){
						out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
						out.print("<script>");
						out.print("alert('提示信息：滚动文本只能为1000字以内');");
						out.print("document.getElementById('loadid').style.display='none'");
						out.print("</script>");
						bool = false;
					}else{
						Map map= utildao.getMap();
						map.put("id",moduleid);
						map.put("m_text",scrollcontent);
						utildao.updateinfo(conn,map,"xct_module_temp");
						request.setAttribute("isscroll","1");
						request.setAttribute("scrollcontent",scrollcontent);
					}
				}else if("htmltext".equals(mediatype)){
					String mediaid=mediaids.split(",")[1].split("_")[0];
					MediaDAO mediadao= new MediaDAO();
					Media me=mediadao.getMediaBy(conn,mediaid);
					String scrollcontent=Util.readfile(FirstStartServlet.projectpath+me.getFile_path()+me.getFile_name());
					request.setAttribute("ishtmltext","1");
					request.setAttribute("htmltextcontent",scrollcontent);
					bool = false;
				}else if("flash".equals(mediatype)){
					int flashnum=1;
					if(mediaArray.length>2){
						flashnum=2;
					}else{
						List mediaList1=moduledao.getMediaByModuleId(conn,Integer.parseInt(moduleid));
						for(int i=0;i<mediaList1.size();i++){
							Module module1=(Module)mediaList1.get(i);
							if("FLASH".equals(module1.getMedia_type())){
								flashnum++;
							}
						}
					}
					if(flashnum>1){
						out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
						out.print("<script>");
						out.print("alert('提示信息：FLASH播放类型只能播放一个FLASH文件，如需更换FLASH，请删除当前FLASH');");
						out.print("document.getElementById('loadid').style.display='none'");
						out.print("</script>");
						bool = false;
					}
				}else if("word".equals(mediatype)){
					int flashnum=1;
					if(mediaArray.length>2){
					   flashnum=2;
					}else{
						List mediaList1=moduledao.getMediaByModuleId(conn,Integer.parseInt(moduleid));
						for(int i=0;i<mediaList1.size();i++){
							Module module1=(Module)mediaList1.get(i);
							if("WORD".equals(module1.getMedia_type())){
								flashnum++;
							}
						}
					}
					if(flashnum>1){
						out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
						out.print("<script>");
						out.print("alert('提示信息：WORD播放类型只能播放一个WORD文件，如需更换WORD，请删除当前WORD');");
						out.print("document.getElementById('loadid').style.display='none'");
						out.print("</script>");
						bool = false;
					}
				}else if("ppt".equals(mediatype)){
					int flashnum=1;
					if(mediaArray.length>2){
						flashnum=2;
					}else{
						List mediaList1=moduledao.getMediaByModuleId(conn,Integer.parseInt(moduleid));
						for(int i=0;i<mediaList1.size();i++){
							Module module1=(Module)mediaList1.get(i);
							if("PPT".equals(module1.getMedia_type())){
								flashnum++;
							}
						}
					}
					if(flashnum>1){
						out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
						out.print("<script>");
						out.print("alert('提示信息：PPT播放类型只能播放一个PPT文件，如需更换PPT，请删除当前PPT');");
						out.print("document.getElementById('loadid').style.display='none'");
						out.print("</script>");
						bool = false;
					}
				}else if("excel".equals(mediatype)){
					int flashnum=1;
					if(mediaArray.length>2){
						flashnum=2;
					}else{
						List mediaList1=moduledao.getMediaByModuleId(conn,Integer.parseInt(moduleid));
						for(int i=0;i<mediaList1.size();i++){
							Module module1=(Module)mediaList1.get(i);
							if("EXCEL".equals(module1.getMedia_type())){
								flashnum++;
							}
						}
					}
					if(flashnum>1){
						out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
						out.print("<script>");
						out.print("alert('提示信息：EXCEL播放类型只能播放一个EXCE文件，如需更换EXCE，请删除当前EXCE');");
						out.print("document.getElementById('loadid').style.display='none'");
						out.print("</script>");
						bool = false;
					}
				}else if("exe".equals(mediatype)){
					int flashnum=1;
					if(mediaArray.length>2){
						flashnum=2;
					}else{
						List mediaList1=moduledao.getMediaByModuleId(conn,Integer.parseInt(moduleid));
						for(int i=0;i<mediaList1.size();i++){
							Module module1=(Module)mediaList1.get(i);
							if("EXE".equals(module1.getMedia_type())){
								flashnum++;
							}
						}
					}
					if(flashnum>1){
						out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
						out.print("<script>");
						out.print("alert('提示信息：EXE播放类型只能播放一个EXE文件，如需更换EXE，请删除当前EXE');");
						out.print("document.getElementById('loadid').style.display='none'");
						out.print("</script>");
						bool = false;
					}
				}else if("pdf".equals(mediatype)){
					int flashnum=1;
					if(mediaArray.length>2){
						flashnum=2;
					}else{
						List mediaList1=moduledao.getMediaByModuleId(conn,Integer.parseInt(moduleid));
						for(int i=0;i<mediaList1.size();i++){
							Module module1=(Module)mediaList1.get(i);
							if("PDF".equals(module1.getMedia_type())){
								flashnum++;
							}
						}
					}
					if(flashnum>1){
						out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;加载中...</center>");
						out.print("<script>");
						out.print("alert('提示信息：PDF播放类型只能播放一个PDF文件，如需更换PDF，请删除当前PDF');");
						out.print("document.getElementById('loadid').style.display='none'");
						out.print("</script>");
						bool = false;
					}
				}
			}
			if (bool) {
				//存储图片或者视频等多文件类型
				for (int i = 1; i < mediaArray.length; i++) {
					String mid = mediaArray[i].split("_")[0]; ////【0】媒体ID，【1】媒体类型
					String sequence=moduledao.getSequenceBymid(conn,moduleid)+"";
					Map map = utildao.getMap();
					map.put("module_id", moduleid);
					map.put("media_id", mid); 
					map.put("type", "0");
					map.put("sequence",sequence);
					utildao.saveinfo(conn,map, "xct_module_media");
				}
			}
		}
	} else if ("3".equals(opp)) {
		String mid = request.getParameter("mid")==null?"0":request.getParameter("mid");
		moduledao.delMedia_sequence(Integer.parseInt(mid),Integer.parseInt(moduleid));
		utildao.deleteinfo(conn,"id", mid, "xct_module_media");
	} else if ("4".equals(opp)) {
	
		String mediatype = request.getParameter("mediatype");
		String result=moduledao.checkModuleType(Integer.parseInt(moduleid),mediatype,module.getTemplate_id());
		if(result.startsWith("flash_ok")){
				out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;正在验证播放类型...</center>");
				out.print("<script>");
				out.print("alert('提示信息："+result.split("#")[1]+"');");
				out.print("document.getElementById('loadid').style.display='none'");
				out.print("</script>");
		}
		if(!result.startsWith("flash_ok")&&!"ok".equals(result)){
				out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;正在验证播放类型...</center>");
				out.print("<script>");
				out.print("alert('提示信息："+result+"');");
				out.print("document.getElementById('loadid').style.display='none'");
				out.print("</script>");
		}else{
			String m_other="";
			String m_text=" ";
			String name="";
			String background="-16776961";
			String foreground="-1";
			String scroll_span="11000";
			String scroll_family="黑体";
			String scroll_style="1";
			String scroll_size="20";
			String scroll_alpha="1.0";
			String week=UtilDAO.getweek();
			
			if("weather".equals(mediatype)){
				String content=Util.readfile(FirstStartServlet.projectpath+"\\serverftp\\program_file\\weather.txt");
				String oday="#a0.png#上海#晴#0℃#0℃#ffffff";
				name="天气预报"+moduleid;
				if(content.indexOf("#")>-1){
					String[] days=content.split("#");
					String todaydate=days[0].replaceAll("今天：","").replaceAll("气温：","#").replaceAll("风力：","#").replaceAll("～","#");
					if(todaydate.indexOf("#")>-1){
						String[] ss=todaydate.split("#");
						oday="#"+module.getWeatherImagesByName(ss[0])+"#上海#"+ss[0]+"#"+ss[1]+"#"+ss[2]+"#ffffff";
					}
					//m_other="want-weather.xml#上海#"+oday+tday+sday+"#ffffff";
					m_other="weather"+moduleid+".xml#"+nowtime+week+oday;
				}else{
				   m_other="weather"+moduleid+".xml#"+nowtime+week+oday;
				}
			}else if("image".equals(mediatype)){
				scroll_family="1#2#3#4#5#6#7#8#9#10#11#12#13#14#15#16#21#22";
			}else if("clock".equals(mediatype)){
				m_other="#ff0000";
				name="时钟"+moduleid;
				 scroll_size="40";
				foreground="-65536";
				
			}else if("count_down".equals(mediatype)){//节日倒计时
				m_other="countdown"+moduleid+".txt#"+new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				name="倒计时"+moduleid;
				scroll_size="45";
				foreground="-65536";
				
			}else if("nbqueuing".equals(mediatype)){//宁波显示排队叫号信息
				m_other="#ff0000";
				name="排队叫号"+moduleid;
				scroll_size="20";
				foreground="-65536";
				scroll_span="10000";
			}else if("nbqueuingmore".equals(mediatype)){//宁波显示排队叫号信息(综合屏)
				m_other="#ff0000";
				name="排队叫号(综合屏)"+moduleid;
				scroll_size="64";
				foreground="-1";
				scroll_span="11000";
			}else if("kingentranceguard".equals(mediatype)){//金山公共卫生刷卡信息
				m_other="#ff0000";
				name="ICCard"+moduleid;
				scroll_size="20";
				foreground="-65536";
				scroll_span="10000";
			}else if("baononglift".equals(mediatype)){//宝隆电梯模块 ，
				m_other="#ff0000";
				name="宝隆电梯"+moduleid;
				 scroll_size="20";
				foreground="-65536";
			}else if("weathersimple".equals(mediatype)){//宝隆简单天气预报
				m_other="#ff0000";
				name="简洁天气预报"+moduleid;
				foreground="-65536";
				scroll_size="16";
			}else if("scroll".equals(mediatype)){
				m_other="ffffff#0000ff";
				name="滚动文字"+moduleid;
				 background="-16776961";
				 foreground="-1";
				 scroll_span="20";
				 scroll_family="宋体";
				 scroll_style="1";
				 scroll_size="40";
			}else if("iptv".equals(mediatype)){
				m_other="iptv"+moduleid+".txt#http://";
				name="流媒体"+moduleid;
			}else if("web".equals(mediatype)){
				m_other="web"+moduleid+".txt#http://@http://@http://@http://@http://";
				name="WEB模块"+moduleid;
			}else if("htmltext".equals(mediatype)){
			   
				m_other="htmltext"+moduleid+".html";
				scroll_span="40";
				name="滚动文本"+moduleid;
				
			}else if("nordermeeting".equals(mediatype)){
				foreground="1";
				m_other="htmltext"+moduleid+".html";
				background="-1";
				name="非预定会议室"+moduleid;
				
			}else if("marketstock".equals(mediatype)){//张家港国泰集团（股市，大宗商品，外汇）
				m_other="marketstock"+moduleid+".html";
				scroll_span="30";
				name="滚动文本"+moduleid;
				
			}else if("insnquametting".equals(mediatype)){//浦东检验检疫局 
				scroll_family="微软雅黑";
		        scroll_size="90";
				name="检验检疫会议"+moduleid;

			}else if("excel".equals(mediatype)){
				scroll_span="40";
				name="EXCEL"+moduleid;
			}else if("word".equals(mediatype)){
				scroll_span="30";
				name="WORD"+moduleid;
			}else if("exe".equals(mediatype)){
				scroll_span="40";
				name="EXE"+moduleid;
			}else if("khmeetingnotice".equals(mediatype)){//康辉会展
			
				scroll_span="8000";
				name="康辉表格"+moduleid;
			}else if("backgroundmusic".equals(mediatype)){//康辉会展
			
				scroll_span="8000";
				name="音乐文件"+moduleid;
			}else if("policesubstation".equals(mediatype)){//康辉会展
			
				scroll_span="8000";
				name="讯问信息"+moduleid;
				
			}else if("goldtrend".equals(mediatype)){//黄金走势图
				scroll_span="40";
				name="GOLDTREND"+moduleid;//else if("image".equals(mediatype)){
				//scroll_span="30000";
			//}
			//////旺旺定制
			}else if("dateother".equals(mediatype)){
				m_other="#ff0000";
				name="日期"+moduleid;
				scroll_size="28";
				foreground="-65536";
			}else if("date".equals(mediatype)){
				m_other="#ff0000";
				name="旺旺--日期(5楼)"+moduleid;
				foreground="-65536";
			
			}else if("weatherthree".equals(mediatype)){
				String content=Util.readfile(FirstStartServlet.projectpath+"\\serverftp\\program_file\\weather.txt");
				name="旺旺--天气(多天)"+moduleid;
				if(content.indexOf("#")>-1){
					String[] days=content.split("#");
					String oday="1#a0.png#0°#0°#";
					String tday="2#a0.png#0°#0°#";
					String sday="3#a0.png#0°#0°";
					String todaydate=days[0].replaceAll("今天：","").replaceAll("气温：","#").replaceAll("风力：","#").replaceAll("～","#").replaceAll("℃","°");
					String tomorrowdate=days[1].replaceAll("明天：","").replaceAll("气温：","#").replaceAll("风力：","#").replaceAll("～","#").replaceAll("℃","°");
					String aftertomorrowdate=days[2].replaceAll("后天：","").replaceAll("气温：","#").replaceAll("风力：","#").replaceAll("～","#").replaceAll("℃","°");
					if(todaydate.indexOf("#")>-1){
						String[] ss=todaydate.split("#");
						oday="1#"+module.getWeatherImagesByName(ss[0])+"#"+ss[1]+"#"+ss[2]+"#";
					}
					if(tomorrowdate.indexOf("#")>-1){
						String[] ss=tomorrowdate.split("#");
						tday="2#"+module.getWeatherImagesByName(ss[0])+"#"+ss[1]+"#"+ss[2]+"#";
					}
					if(aftertomorrowdate.indexOf("#")>-1){
						String[] ss=aftertomorrowdate.split("#");
						sday="3#"+module.getWeatherImagesByName(ss[0])+"#"+ss[1]+"#"+ss[2];
					}
					m_other="want-weather.xml#上海#"+oday+tday+sday+"#ffffff";
				}else{
					m_other="want-weather.xml#上海#1#a0.png#0°#0°#2#a0.png#0°#0°#3#a0.png#0°#0°#ffffff";
				}
			}else if("stock".equals(mediatype)){
				name="旺旺--股票"+moduleid;
				String content=Util.readfile(FirstStartServlet.projectpath+"\\serverftp\\program_file\\DB\\want_stock.txt");
				m_other="want_stock.txt#"+content;
			}else if("stockother".equals(mediatype)){
				name="旺旺--股票（no-k）"+moduleid;
				String content=Util.readfile(FirstStartServlet.projectpath+"\\serverftp\\program_file\\DB\\want_stock.txt");
				m_other="want_stock.txt#"+content;
			}else if("birthday".equals(mediatype)){
				name="旺旺--员工生日"+moduleid;
				//String content=Util.readfile(FirstStartServlet.projectpath+"\\serverftp\\program_file\\DB\\want-birthday.xml");
				m_other="want-birthday.xml@want-birthdayconf.xml#11";
			}else if("wwnotice".equals(mediatype)){
				name="旺旺--会议通知"+moduleid;
				//String content=Util.readfile(FirstStartServlet.projectpath+"\\serverftp\\program_file\\DB\\ww-meetingnotice.xml");
				m_other="ww-meetingnotice.xml#11";
			}else if("filialeSell".equals(mediatype)){
				name="旺旺--分公司销售排名"+moduleid;
				//String content=Util.readfile(FirstStartServlet.projectpath+"\\serverftp\\program_file\\DB\\want-filialeSellThree.xml");
				m_other="want-filialeSellconf.xml@want-filialeSellOne.xml@want-filialeSellTwo.xml@want-filialeSellThree.xml#11";
			}else if("wwmilkdrink".equals(mediatype)){
				name="旺旺--乳饮渠道"+moduleid;
				//String content=Util.readfile(FirstStartServlet.projectpath+"\\serverftp\\program_file\\DB\\want-filialeSellThree.xml");
				m_other="want-milkdrinkchannel.xml@want-milkdrinkconf.xml#11";
			}
			//System.out.println("addmediaList.jsp------1------>"+mediatype);
			if(null!=mediatype&&!"".equals(mediatype)){
				Map map = utildao.getMap();
				map.put("id", moduleid);
				map.put("m_filetype", mediatype);
				map.put("m_other",m_other);
				map.put("m_text",m_text);
				map.put("name",name);
				map.put("background",background);
				map.put("foreground",foreground);
				map.put("span",scroll_span);
				map.put("alpha",scroll_alpha);
				map.put("fontName",scroll_family);
				map.put("fontTyle",scroll_style);
				map.put("fontSize",scroll_size);
				map.put("state","0");
				utildao.updateinfo(conn,map, "xct_module_temp");
				utildao.deleteinfo(conn,"module_id",moduleid,"xct_module_media");
			}else{
			   //System.out.println("addmediaList.jsp------2------>"+mediatype);
			}
			out.print("<script>");
			out.print("parent.cleanchkMedia();");
			out.print("</script>");
			module = moduledao.getModuleTempByModuleid(conn,Integer.parseInt(moduleid));
		}
	}
	
	List mediaList = moduledao.getMediaByModuleId(conn,Integer.parseInt(moduleid));
	
	if(mediaList!=null&&mediaList.size()>0){
		Module image_module=(Module)mediaList.get(0);
		if(image_module!=null&&"IMAGE".equals(image_module.getMedia_type())){
			images_span=Integer.parseInt(image_module.getSpan())/1000;
		}
	}
	dbconn.returnResources(conn);
	request.setAttribute("images_span",images_span);
	request.setAttribute("moduleid",moduleid);
	request.setAttribute("mediaList", mediaList);
	request.setAttribute("module", module);
	request.setAttribute("m_filetype", module.getM_filetype());
%>
<html>
	<head>
		<title>My JSP 'addmediaList.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script language="javascript" src="/js/vcommon.js"></script>
		 <script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<script type='text/javascript' src='/js/jquery1.4.2.js'></script>
		<script type="text/javascript" src="/My97DatePicker/WdatePicker.js"></script>
		<style type="text/css">
		
#colorDiv{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none }
#colorMessage{border:#6699cc solid; border-width:1px; background:#fff; color:#036; font-size:12px; line-height:100%;  display: none}
#divframe{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none }
#smallImgDiv{ position:absolute; z-index: 1; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none }
#smallImgMassage{border:#6699cc solid; border-width:1px; background:#fff; color:#036; font-size:12px; line-height:100%;  display: none}
.header{background:url(/images/device/btn_background.gif);  font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff}
.media_llist_ul{ list-style: none; padding-left: 0px;margin-left: 0px;}
.media_llist_ul li{ height: 25px; line-height: 25px; width:100%; padding: 0; list-style: none;cursor: move; border-bottom:  1px solid #c6ddf1}
.li_div1{float: left; width: 330px;}
.li_div2{float: left; width: 150px;}
.li_div3{float: left; width: 120px;}
.li_div4{float: left; width: 120px;}
#demo { 
    background: #FFF; 
    overflow:hidden; 
    border: 1px dashed #CCC; 
    width: 100%; 
    height:60px;
    left:10;
    top:35px; 
    background-color: #${module.alpha=='0.1'?'ffffff':module.scroll_bg};
} 
#demo img { 
    border: 3px solid #F2F2F2; 
} 
#indemo { 
    float: left; 
    width: 1000%; 
} 
#demo1 { 
    float: left; 
    line-height: 60px;
    font-weight:${module.fontTyle=='1'?'bold':'normal' };
    font-style:${module.fontTyle=='2'?'italic':'normal' };
    font-family:${module.fontName=='no'?'宋体':module.fontName };
    font-size:${module.fontSize=='no'?'40':module.fontSize };
    color: #${module.scroll_fg};
} 
#demo2 { 
    float: left; 
    line-height: 60px;
    font-weight:${module.fontTyle=='1'?'bold':'normal' };
	font-style:${module.fontTyle=='2'?'italic':'normal' };
    font-family:${module.fontName=='no'?'宋体':module.fontName };
    font-size:${module.fontSize=='no'?'25':module.fontSize };
    color: #${module.scroll_fg};
}
#scroll_bimggId{
    <c:choose>
      <c:when test="${bg!=''&& bg!=null}">
          background-image:url(bg);
      </c:when>
      <c:otherwise>
		  background-image:url(${module.alpha!='1.0'?module.alpha:'/images/dtreeimg/IMAGE1.gif'});
      </c:otherwise>
    </c:choose>
}
	#removebg{
		background-image:url('/images/error.gif');
		display:${module.alpha!='1.0'?'block':'none'} ;
	}
</style>		
		<script type="text/javascript">
		function delmedia(mid){
			if(confirm("提示信息：确认删除媒体？")){
			  window.location.href="/admin/program/addmediaList.jsp?opp=3&moduleid=<%=moduleid%>&mid="+mid+"&templateid=${templateid}";
			}
		}
		function updateMediaType(mediatype){
			if(confirm("提示信息：确认更改播放类型？")){
			  window.location.href="/admin/program/addmediaList.jsp?opp=4&moduleid=<%=moduleid%>&mediatype="+mediatype+"&templateid=${templateid}";
			}else{
			  window.location.href="/admin/program/addmediaList.jsp?opp=0&moduleid=<%=moduleid%>&templateid=${templateid}";
			}
		}
		function addMediaType(mediatype){
			window.location.href="/admin/program/addmediaList.jsp?opp=4&moduleid=<%=moduleid%>&mediatype="+mediatype+"&templateid=${templateid}";
		}
		function saveWeather(){
		var city=weatherForm.city.value;
			if(city==""){
				alert("城市不能为空!");
				return;
			}
			weatherForm.action="/admin/program/save_weather.jsp?moduleid=${moduleid}&templateid=${templateid}";
			weatherForm.submit();
		}
		function saveWeatherthree(){
		var city=weatherForm.city.value;
			if(city==""){
				alert("城市不能为空!");
				return;
			}
			weatherForm.action="/admin/program/save_weatherthree.jsp?moduleid=${moduleid}&templateid=${templateid}";
			weatherForm.submit();
		}
		function changewind(wind){
			var winds=wind.split("#");
			weatherForm.weather_image.value=winds[1]
			weatherForm.weather_wind.value=winds[0]
			document.getElementById("weatherimgid").src="/images/weather_images/"+winds[1];
		}
		function changewind1(wind,num){
			var winds=wind.split("#");
			document.getElementById("w_image"+num).value=winds[1];
			document.getElementById("weatherimgid"+num).src="/images/weather_images/"+winds[1];
		}
		function saveScroll(){
			    var scroll_list=scrollForm.scroll_list.value;
				if(scroll_list==0){
					alert("滚动文字不能为空！");
					return;
				}
				scrollForm.action="/admin/program/save_scroll.jsp?moduleid=${moduleid}&templateid=${templateid}";
				scrollForm.submit();
		}
		function saveOther(mtype){
			oc= otherForm.otherContent.value.replace(/\s/g,"");
			if(oc==""){
				alert("内容不能为空！");
				return;
			}
			otherForm.action="/admin/program/save_other.jsp?moduleid=${moduleid}&mtype="+mtype+"&templateid=${templateid}";
			otherForm.submit();
		}
		function saveCountDown(mtype){
			oc= otherForm.count_downdate.value.replace(/\s/g,"");
			if(oc==""){
				alert("内容不能为空！");
				return;
			}
			otherForm.action="/admin/program/save_countdown.jsp?moduleid=${moduleid}&mtype="+mtype+"&templateid=${templateid}";
			otherForm.submit();
		}
		
		function saveHtmlText(){
			varoEditor=FCKeditorAPI.GetInstance('htmltextContent'); 
			htmlc=(varoEditor.GetXHTML(true));
			if(htmlc==""){
				alert("编辑区文本不能为空！");
				return;
			}
			
			var bg=document.getElementById("div_img").src;
			//alert("bg-=-=-=-=>"+bg);
			htmltextForm.action="/admin/program/save_htmltext.jsp?moduleid=${moduleid}&bg="+bg+"&templateid=${templateid}";
			htmltextForm.submit();
		}
		//添加修改多行文本
		function addupdatehtmltext(){
		   
		    var htmltextbg='${htmltextbg}';
		    parent.parent.showDiv("添加多行文本",1024,560,"/admin/program/addhtmltext.jsp?moduleid=${moduleid}&templateid=${templateid}&mediaids=${mediaids}&htmltextbg="+htmltextbg);
		}
		
		function close_addupdatehtmltext(){
		  //alert("刷新-=-=>");
		  window.location.reload();
		}
		
		function saveW_web(){
			add_num=0;
			oc1=w_webForm.w_webContent1.value.replace(/\s/g,"");
			//oc2=w_webForm.w_webContent2.value.replace(/\s/g,"");
			//oc3=w_webForm.w_webContent3.value.replace(/\s/g,"");
			//oc4=w_webForm.w_webContent4.value.replace(/\s/g,"");
			//oc5=w_webForm.w_webContent5.value.replace(/\s/g,"");
			if(oc1=="http://")add_num++;
			//if(oc2=="http://")add_num++;
			//if(oc3=="http://")add_num++;
			//if(oc4=="http://")add_num++;
			//if(oc5=="http://")add_num++;
			
			if(oc1==""){
				alert("必须填写一个WEB地址！");
				return;
			}
			w_webForm.action="/admin/program/save_w_web.jsp?moduleid=${moduleid}&templateid=${templateid}";
			w_webForm.submit();
		
		}
		function showClockColordiv(){
		showDiv("251","175","260","30px","/admin/program/select_color.jsp?moduleid=${moduleid}&type=clock");
		}
		function showScrollFg(){
		showDiv("255","175","440","150px","/admin/program/select_color.jsp?moduleid=${moduleid}&type=scrollfg");
		}
		function showScrollBg(){
		showDiv("255","175","470","150px","/admin/program/select_color.jsp?moduleid=${moduleid}&type=scrollbg");
		}
		function showScrollBg1(){
			showDiv("255","175","470","40px","/admin/program/select_color.jsp?moduleid=${moduleid}&type=htmltext");
		}
		function showWeatherFg(left,top){
		showDiv("255","175",left,top,"/admin/program/select_color.jsp?moduleid=${moduleid}&type=weatherfg");
		}
		function newScroll(){
		showDiv("300","155","200","30","/admin/program/new_scroll.jsp?moduleid=${moduleid}");
		}
		function newrss(){
		showDiv("300","105","200","30","/admin/program/new_rss.jsp?moduleid=${moduleid}");
		}
		function update_scroll(mediaid){
			showDiv("300","155","200","30","/admin/program/update_scroll.jsp?mediaid="+mediaid+"&moduleid=${moduleid}");
		}
		function add_media(moduleid,filetype){
			var cnt_height=parent.parent.parent.parent.document.body.clientHeight*0.85;
		
		parent.parent.showDiv("添加媒体",800,cnt_height,"/admin/program/select_media.jsp?moduleid="+moduleid+"&filetype="+filetype);
		}
		function chanange_style(moduleid){
			parent.parent.showDiv("图片切换样式",800,500,"/admin/program/change_img_style.jsp?moduleid="+moduleid);
		}
		
		function showDiv(fwidth,fheight,left,top,url){
			document.body.scrollTop = "0px";
			document.getElementById("color_iframe").width=fwidth;
			document.getElementById("color_iframe").height=fheight;
			document.getElementById("colorDiv").style.left=left;
			document.getElementById("colorDiv").style.top=top;
			document.getElementById("color_iframe").src=url;
			document.getElementById("colorDiv").style.display='block';
			document.getElementById("colorMessage").style.display='block';
		}
		
		
function closeDiv(){
	document.getElementById("colorDiv").src="/loading.jsp";
	document.getElementById('colorMessage').style.display="none";
}

function ch_enchange(moduleid,type,alpha){
  DwrClass.changeAlpha(moduleid,type,alpha,'${templateid}');
  parent.changech_en(alpha);
  if(alpha==1.0)//黄金/人民币
    document.getElementById("goldtrend_iframe").src="http://www.kitco.cn/cn/metals/gold/t24_au_cny_gram_163x111.gif?t="+new Date().getTime();
  else//黄金/美元
    document.getElementById("goldtrend_iframe").src="http://www.kitco.cn/cn/live_charts/t24_au_ch_usoz_163x111.gif?t="+new Date().getTime();
}

function getAbsolutePosition(obj) 
{ 
position = new Object(); 
position.x = 0; 
position.y = 0; 
var tempobj = obj; 
while(tempobj!=null && tempobj!=document.body) 
{ 
if(window.navigator.userAgent.indexOf("MSIE")!=-1) 
{ 
position.x += tempobj.offsetLeft; 
position.y += tempobj.offsetTop; 
} 
else if(window.navigator.userAgent.indexOf("Firefox")!=-1) 
{ 
position.x += tempobj.offsetLeft; 
position.y += tempobj.offsetTop; 
} 
tempobj = tempobj.offsetParent 
} 
return position; 
} 

function showimg(event,imgpath){
if(imgpath==""){
  document.getElementById("scroll_bimggId").setAttribute("onmouseover",null);//移除带参数的js事件
}else{
event = event || window.event;
	document.getElementById("div_img").src=imgpath;
	//document.getElementById("test").innerHTML=imgpath;
	document.getElementById("smallImgDiv").style.display='block';
	document.getElementById("smallImgMassage").style.display='block';
	document.getElementById("smallImgDiv").style.left= document.body.scrollLeft + event.clientX + 10 + "px";
	document.getElementById("smallImgDiv").style.top=document.body.scrollTop + event.clientY + 10 + "px";
}
}
function hiddenimg(){
document.getElementById('smallImgDiv').style.display='none';
document.getElementById("smallImgMassage").style.display='none';
}
function clockon() {
thistime= new Date()
var year=thistime.getYear();
var month=thistime.getMonth()+1;
var day=thistime.getDate();

var hours=thistime.getHours()
var minutes=thistime.getMinutes()
var seconds=thistime.getSeconds()

if (eval(month) <10) {month="0"+month}
if (eval(day) <10) {day="0"+day}
if (eval(hours) <10) {hours="0"+hours}
if (eval(minutes) < 10) {minutes="0"+minutes}
if (seconds < 10) {seconds="0"+seconds}
if('${m_filetype}'=='date'){
	thistime=year+"/"+month+"/"+day+" "+hours+":"+minutes+":"+seconds;
	w_date_id.innerHTML=thistime
}else if('${m_filetype}'=='dateother'){
	thistime=year+"/"+month+"/"+day;
	dateother_id.innerHTML=thistime;
}else{
	thistime =hours+":"+minutes+":"+seconds;
    var	thisday=year+"年"+month+"月"+day+"日";
	bgclockshade.innerHTML=thistime;
	bgclockshade2.innerHTML=thisday;
}


var timer=setTimeout("clockon()",1000)
}

function changeFontSytle(style_no){
	if(style_no==1){
		document.getElementById('scrollContentid').style.fontWeight="bold";
		document.getElementById('scrollContentid').style.fontStyle="normal";
		
		document.getElementById('demo1').style.fontWeight="bold";
		document.getElementById('demo1').style.fontStyle="normal";
		document.getElementById('demo2').style.fontWeight="bold";
		document.getElementById('demo2').style.fontStyle="normal";
	}else if(style_no==2){
		document.getElementById('scrollContentid').style.fontWeight="normal";
		document.getElementById('scrollContentid').style.fontStyle="italic";
		document.getElementById('demo1').style.fontWeight="normal";
		document.getElementById('demo1').style.fontStyle="italic";
		document.getElementById('demo2').style.fontWeight="normal";
		document.getElementById('demo2').style.fontStyle="italic";
	}else{
		document.getElementById('scrollContentid').style.fontWeight="normal";
		document.getElementById('scrollContentid').style.fontStyle="normal";
		document.getElementById('demo1').style.fontWeight="normal";
		document.getElementById('demo1').style.fontStyle="normal";
		document.getElementById('demo2').style.fontWeight="normal";
		document.getElementById('demo2').style.fontStyle="normal";
	}
}
function changecontent(){
	var content=document.getElementById('scrollContentid').value;
	document.getElementById('demo1').innerHTML=content+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	document.getElementById('demo2').innerHTML=content+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
}
function update_sequence(moduleid,mediaid,sequence,order){
	window.location.href="/admin/program/update_media_sequence.jsp?moduleid="+moduleid+"&mediaid="+mediaid+"&sequence="+sequence+"&order="+order+"&templateid=${templateid}";
}
function updateImagesSpanOk(span){
	alert("提示信息：图片切换时间已修改为 "+span+" 秒");
}
	function selectPhoto(type){
			document.body.scrollTop = "0px";
			document.getElementById("div_iframe").width="350";
			document.getElementById("div_iframe").height="300";
			document.getElementById("divframe").style.left="200";
			document.getElementById("divframe").style.top="5px";
			document.getElementById("div_iframe").src="/admin/program/selectbackground.jsp?meidaType=IMAGE&type="+type+"&templateid=${templateid}";
			document.getElementById("titlename").innerHTML="设置多行文本背景";
			document.getElementById("divframe").style.display='block';
			
		}
		function removebackimg(){
		  document.getElementById("removebg").style.display='block';
		  
		}
		function closeBackGroundDiv(){
			document.getElementById("div_iframe").src="/loading.jsp";
			document.getElementById('divframe').style.display="none";
		}
		function closeGetBackGroundImageDiv(imgtitle,imgpath){
		     
		    parent.closeGetBackGroundImageDiv(imgtitle,imgpath);
		    DwrClass.changeAlpha2(imgtitle,imgpath,'${templateid}');
			document.getElementById("div_iframe").src="/loading.jsp";
			document.getElementById("divframe").style.display="none";
			document.getElementById("div_img").src=imgpath;
		    get(imgpath);
		}
		function get(imgpath){
		    //alert(imgpath);  
		    //alert(document.getElementById("scroll_bimggId"));
		    document.getElementById("scroll_bimggId").style.background="url("+imgpath+")";
			document.getElementById("scroll_bimggId").setAttribute("onmouseover",null);//移除带参数的js事件
			document.getElementById("scroll_bimggId").setAttribute("onmouseover",function(){showimg(event,imgpath)});////添加带参数的js事件
		}
		
		function removebackimage(type){
		  //alert(type);
		  DwrClass.changeAlpha2(type,"1.0",'${templateid}');
		  document.getElementById("scroll_bimggId").style.background="url('/images/dtreeimg/IMAGE1.gif')";
		  document.getElementById("scroll_bimggId").setAttribute("onmouseover",null);//移除带参数的js事件
		         
		  parent.closeGetBackGroundImageDiv(type,"");
		  document.getElementById("removebg").style.display="none";
		  document.getElementById("div_img").src="";
		}
</script>
	</head>

	<body>
		<table cellspacing="0" cellpadding="0" width="100%" border="0">
			<tr height="40px" class="TableTrBg05" >
				<td width="100%" colspan="8" align="left" style="background-color:#F5F9FD;">
					<table cellpadding="0"  height="100%" cellspacing="0" border="0"
						style="background-color:#F5F9FD;">
						
						<tr style=" height:30px;">
						<td width="100px" align="center">
							<c:if
								test="${m_filetype=='scroll' || m_filetype=='image' || m_filetype=='video' || m_filetype=='flash' 
								|| m_filetype=='ppt' || m_filetype=='word'|| m_filetype=='excel'||m_filetype=='backgroundmusic'||m_filetype=='pdf'
								|| m_filetype=='exe'|| m_filetype=='khmeetingnotice'||m_filetype=='policesubstation'}">
								<a href="javascript:;"  onclick="add_media('${moduleid}','${m_filetype}');"><img src="/images/add_media.jpg"  width="80" height="31" border="0" alt="添加媒体"/></a>
							</c:if>
						</td><!-- 1 -->
						<td  width="90px" colspan="2" align="right">
								当前播放类型：
						</td><!-- 2 -->
							<c:choose>
								<c:when test="${module.m_filetype==''}">
									<td width="60px">
										<span style="color: red">无，</span>
									</td><!-- 3 -->
									<td width="100px">
										请选择播放类型：
									</td><!-- 4 -->
									<td>
										<select style="width: 100px"
											onchange="addMediaType(this.value)">
											<option value="">
												请选择
											</option>
											<c:forEach var="mtype" items="${module.mtypes}">
											<option ${fn:split(mtype,"#")[0] == module.m_filetype ?'selected':''} value="${fn:split(mtype,"#")[0]}">${fn:split(mtype,"#")[1]}</option>
										</c:forEach>
										</select>
									</td> 
								</c:when>
								<c:otherwise>
									<td width="80px">
										<span style="color: green;font-weight: bold;font-size: 13px;">${module.m_filetypeZN}</span>
									</td>
									<td width="90px">
										更换播放类型：
									</td>
									<td>
										<select style="width: 100px" id="mtypeid"
											onchange="updateMediaType(this.value)">
											<option value="">
												无
											</option>
										<c:forEach var="mtype" items="${module.mtypes}">
											<option ${fn:split(mtype,"#")[0] == module.m_filetype ?'selected':''} value="${fn:split(mtype,"#")[0]}">${fn:split(mtype,"#")[1]}</option>
										</c:forEach>
										</select>
									</td><!-- 8-->
								</c:otherwise>
							</c:choose>
							<td width="260" align="left">
								<c:if test="${module.m_filetypeEN=='IMAGE' && not empty mediaList}">
									&nbsp;&nbsp;图片切换时间：<input type="text" name="iamges_span" value="${images_span}" onchange="DwrClass.updateImagesSpan('${moduleid}',this.value,updateImagesSpanOk);" style="width: 30px;height: 20;pxime-mode:Disabled" onkeypress="return event.keyCode>=48&&event.keyCode<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" maxlength="3" /> 秒
									&nbsp;&nbsp;<input type="button" class="button" onclick="chanange_style('${moduleid}')" value="切换样式" />
								</c:if>
								<c:if test="${m_filetype=='htmltext'||m_filetype=='marketstock'}">
									<div   >
										<div style="float: left;margin-left:15px;">速度：
											<select onchange="DwrClass.updateModuleSpan('${moduleid}',this.value);">
												<option value="20">快</option>
												<option value="30" selected="selected">正常</option>
												<option value="40">慢</option>
											</select>
										</div>
										<div  id="scroll_bgId"   style="float:left;margin-left:5px;border:solid 1px #000000;background-color:#${module.background};width: 23px;height: 18px; cursor: pointer;" title="背景颜色" onclick="showScrollBg1();"></div>
										<div  id="scroll_bimggId"   style="float:left;margin-left:5px;border:solid 1px #000000;width: 23px;height: 18px; cursor: pointer;" title="背景图片"  onmouseover="showimg(event,'${module.alpha!='1.0'?module.alpha:''}')" onmouseout="hiddenimg()" onclick="selectPhoto('${m_filetype}')"></div>
									    <div  id="removebg"  style="float:left;margin-left:5px;width: 16px;height: 16px; cursor: pointer;" title="删除背景图片" onclick="removebackimage('${m_filetype}')"></div>
									<div>
								</c:if>
							</td>
						</tr>
						
						
						<tr>
							<td colspan="2">
								<div id="divframe">
									<div id="massage">
										<table cellpadding="0" cellspacing="0">
											<tr height="30px;" class=header  onmousedown=MDown(divframe)>
												<td align="left" style="font-weight: bold">
													<span id="titlename"></span>
												<br></td>
												<td height="5px" align="right">
													<a href="javaScript:void(0);" style="color: #000000" onclick="closeBackGroundDiv();">关闭</a><br>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<iframe src="/loading.jsp" scrolling="no" id="div_iframe" name="div_iframe" frameborder="0"></iframe>
												<br></td>
											</tr>
										</table>
									</div>
								</div><br>
						    </td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="Line_01" colspan="8"></td>
			</tr>
			<c:if test="${m_filetype=='iptv'}">
			<tr>
					<td colspan="3"  align="center" style="padding-top: 50px" >
					<form name="otherForm" method="post">
						<table cellpadding="0" cellspacing="1" width="360" border="0" bgcolor="#69a3cd"
							style="margin-top: 5px; margin-left: 50px">
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="40" style="font-weight: bold">
									<c:if test="${m_filetype=='iptv'}">
									流媒体链接地址
									</c:if>
									
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="25">
									<textarea rows="5" cols="65" name="otherContent">${module.iptv_path}</textarea>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="50"><input type="button" value=" 保 存 " onclick="saveOther('${m_filetype}');" class="button1"/>
								<c:if test="${save_state=='save_ok'}"><span style="color: red">保存成功！</span></c:if></td>
							</tr>
						</table>
						</form>
					</td>
				</tr>
			</c:if>
			<c:if test="${m_filetype=='count_down'}">
		 	   <tr>
					<td colspan="3"  align="center" style="padding-top: 50px" >
					  <form name="otherForm" method="post">
						<table cellpadding="0" cellspacing="1" width="360" border="0" bgcolor="#69a3cd"
							style="margin-top: 5px; margin-left: 50px">
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="40" style="font-weight: bold">
									请选择倒计时截止日期：
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="25">
									<input class="Wdate button" size="11"  type="text"
												name="count_downdate" readonly="readonly" id="startdateid"
												onfocus="new WdatePicker(this)" MINDATE="#Year#-#Month#-#Day#"
												value="${module.count_downtxt}" />
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="50"><input type="button" value=" 保 存 " onclick="saveCountDown('${m_filetype}');" class="button1"/>
								<c:if test="${save_state=='save_ok'}"><span style="color: red">保存成功！</span></c:if></td>
							</tr>
						</table>
						</form>
					</td>
				</tr>
			</c:if>
			<c:if test="${m_filetype=='weather'}">
				<tr>
					<td colspan="3"  align="center" style="padding-top: 50px" >
					<form name="weatherForm" method="post">
					<input type="hidden" name="weatherfg" value="${module.font_color}"/>
						<input type="hidden" name="weatherfgRGB" value="${module.foreground}"/>
						<input type="hidden" name="weather_date" value="${module.weather_date}"/>
						<input type="hidden" name="weather_image" value="${module.weather_image }"/>
						<input type="hidden" name="weather_wind" value="${module.weather_wind }"/>
						<table cellpadding="0" cellspacing="1" width="310" border="0" bgcolor="#69a3cd"
							style="margin-top: 5px; margin-left: 50px">
							<%--<tr bgcolor="#F5F9FD">
								<td align="center" colspan="3" height="40"  style="font-weight: bold">
									${module.weather_date}
								</td>
							</tr>
							--%><tr bgcolor="#F5F9FD">
								<td rowspan="4" align="center">
									<img src="/images/weather_images/${module.weather_image}" id="weatherimgid" width="80" height="80" alt="天气图片" />
								</td>
								<td  height="30px" align="right">
									&nbsp;城市：
								</td>
								<td>&nbsp;&nbsp;<input type="text" name="city" value="${module.city }"  class="button" /></td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td height="30px" align="right">&nbsp;字体颜色：</td>
								<td>
									<div id="weather_fgId" style="margin-left: 10px;border:solid 1px #000000;background-color:#${module.font_color};width: 25px;height: 18px; cursor: pointer; font-family:Arial; font-size: 16; font-weight: bold; " title="字体颜色" onclick="showWeatherFg(420,130)"></div>
								</td>
							</tr>
							<tr  bgcolor="#F5F9FD">
								<td height="30px" align="right">&nbsp;天气：</td>
								<td>
									<select style="width: 100px;"  name="wind" onchange="changewind(this.value)">
										<c:forEach var="wind" items="${module.winds}">
											<option ${fn:split(wind,"#")[0] == module.weather_wind ?'selected':''} value="${wind}">${fn:split(wind,"#")[0]}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td height="30px" align="right">&nbsp;温度：</td>
								<td>
									<select  style="width: 70px;"  name="start_temperature">
										<c:forEach var="temperature" items="${module.temperatures}">
											<option ${module.start_temperature==temperature?'selected':'' } value="${temperature}">${temperature }</option>
										</c:forEach>
									</select>
									~
									<select  style="width: 70px;" name="end_temperature">
										<c:forEach var="temperature" items="${module.temperatures}">
											<option ${module.end_temperature==temperature?'selected':'' } value="${temperature}">${temperature }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="3" height="50"><input type="button" value=" 保 存 " onclick="saveWeather();" class="button1"/>
								<c:if test="${save_state=='save_ok'}"><span style="color: red">保存成功！</span></c:if></td>
							</tr>
						</table>
						</form>
					</td>
				</tr>


			</c:if>
			<c:if test="${m_filetype=='clock'}">
				<tr>
						<td colspan="3" align="center" style="padding-top: 50px" >
							<table cellpadding="0" cellspacing="1" width="280" border="0" bgcolor="#69a3cd"
							style="margin-top: 2px; margin-left: 50px;">
							<tr bgcolor="#F5F9FD">
								<td align="center" height="25" width="240"  style="font-weight: bold">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时钟
								</td>
								<td width="20">
									<div id="divcolor_view" style="border:solid 1px #000000;background-color:${module.m_other};width: 20px;height: 20px; cursor: pointer; " title="点击修改颜色" onclick="showClockColordiv();"></div>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="120">
								<div id="bgclockshade" style="height: 70px;font-family:Arial;color:${module.m_other};font-size:60px; font-weight:bold; padding-top: 40px"></div>
								<div id="bgclockshade2" style="height: 70px;font-family:Arial;color:${module.m_other};font-size:36px; font-weight:bold; padding-top: 10px"></div>
								</td>
							</tr></table>
				</tr>
			</c:if>
			<c:if test="${m_filetype=='htmltext'}">
			            <tr >
							<td colspan="9" align="center" style="padding-top:5px;">
							     <c:choose>
								      <c:when test="${fn:trim(module.m_text)!=''||save_state eq 'save_ok'}">
								      
								              <ul id="outer_wrap" class="media_llist_ul">
													<li id="${media.id}">
													    <div class="li_div1">
															<img src="/images/dtreeimg/namesort.gif" align="absmiddle" />&nbsp;&nbsp;多行文本
														</div>
														<div class="li_div2">
														   <img src="\mediafile\text_slightly.gif" height="20px" />
														</div>
														<div class="li_div3">
															HtmlText
														</div>
								
														<div class="li_div4">
						                                   <div style="clear:both"><input type="button" name="htmltextbutton" value="修改" class="button" onclick="addupdatehtmltext();"/></div>
														</div>
												</li>
											</ul>
									 </c:when>
		                              <c:otherwise>
											<div style=" cursor: pointer;clear:both	"><input type="button" name="htmltextbutton" value="添加文本" class="button1"  onclick="addupdatehtmltext();"/></div>
		                              </c:otherwise>
	                             </c:choose>
						    </td>
						</tr>
			
				<!--<form name="htmltextForm" method="post">
					<input type="hidden" name="htmltextbg" value="${module.background}"/>
					<input type="hidden" id="htmltextbackimg" name="htmltextbackimg" value="${module.alpha}"/>
					<table cellpadding="0" cellspacing="1" width="480" align="center" border="0" bgcolor="#69a3cd" style="margin-top: 5px; margin-left: 20px; ">
						
						<tr bgcolor="#F5F9FD">
							<td align="center" colspan="2" height="25">
							   <FCK:editor instanceName="htmltextContent"  width="700" height="320" toolbarSet="Basic" value="${module.m_text}"></FCK:editor>
							</td>
						</tr>
						<tr bgcolor="#F5F9FD">
							<td align="center" colspan="2" height="40"><input type="button" value=" 保 存 " onclick="saveHtmlText();" class="button1"/>
							<c:if test="${save_state=='save_ok'}"><span style="color: red">保存成功！</span></c:if></td>
						
						</tr>
					</table>
				</form>-->
			</c:if>
			<c:if test="${m_filetype=='nordermeeting'}">
				<tr><td align="center">
				       <form name="htmltextForm" method="post">
						
						<table cellpadding="0" cellspacing="1" width="480" align="center" border="0" bgcolor="#69a3cd" style="margin-top: 5px; margin-left: 20px; ">
							
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="25">
								   <FCK:editor instanceName="htmltextContent"  width="700" height="320" toolbarSet="Basic" value="${module.m_text}"></FCK:editor>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="40"><input type="button" value=" 保 存 " onclick="saveHtmlText();" class="button1"/>
								<c:if test="${save_state=='save_ok'}"><span style="color: red">保存成功！</span></c:if></td>
							
							</tr>
						</table>
					</form>
				</td></tr>
			</c:if>
			
			<c:if test="${m_filetype=='marketstock'}">
			<tr><td align="center">
			         <input type="hidden" name="htmltextbg" value="${module.background}"/>
					<input type="hidden" id="marketstockbackimg" name="marketstockbackimg" value="${module.alpha}"/>
				<iframe src="\serverftp\program_file\all.html?t=${mdate }" scrolling="yes" width="80%" height="285"   name="marketstock_iframe" id="marketstock_iframe" frameborder="0"></iframe>
			</td></tr>
			</c:if>
			<c:if test="${m_filetype=='goldtrend'}">
			<tr><td align="center">
			    <input type="radio" name="ch_en" value="1.0" ${module.alpha==1.0?'checked=checked':''} onclick="ch_enchange('${module.id}','${m_filetype}',1.0)"/>人民币/克&nbsp;
			    <input type="radio" name="ch_en" value="0.1" ${module.alpha==0.1?'checked=checked':''} onclick="ch_enchange('${module.id}','${m_filetype}',0.1)"/>美元/盎司
			    <br/>
			    <iframe src="${module.alpha==1.0?'http://www.kitco.cn/cn/metals/gold/t24_au_cny_gram_163x111.gif':'http://www.kitco.cn/cn/live_charts/t24_au_ch_usoz_163x111.gif'}" scrolling="no" width="183" height="140"   name="goldtrend_iframe" id="goldtrend_iframe" frameborder="0"></iframe>
				
			</td></tr>
			</c:if>
			
			<c:if test="${m_filetype=='nbqueuing'}">
			   <tr><td align="center">
				<iframe src="\images\nbqueuing.jpg" scrolling="no" width="600" height="150"   name="nbqueuing_iframe" id="nbqueuing_iframe" frameborder="0"></iframe>
			</td></tr>
			</c:if>
			
			<c:if test="${m_filetype=='nbqueuingmore'}">
			   <tr><td align="center">
				<iframe src="\images\QueuingM.jpg" scrolling="no" width="385" height="260"   name="nbqueuingmore_iframe" id="nbqueuingmore_iframe" frameborder="0"></iframe>
			</td></tr>
			</c:if>
			
			<c:if test="${m_filetype=='kingentranceguard'}">
			   <tr><td align="center">
				<iframe src="\images\kingentranceguard.jpg" scrolling="no" width="372" height="400"   name="kingentranceguard_iframe" id="kingentranceguard_iframe" frameborder="0"></iframe>
			</td></tr>
			</c:if>
			
			<c:if test="${m_filetype=='baononglift'}">
			<tr><td align="center">
			   
				<iframe src="\images\lift.jpg" scrolling="no" width="172" height="400"   name="baononglift_iframe" id="baononglift_iframe" frameborder="0"></iframe>
			</td></tr>
			</c:if>
			
			<c:if test="${m_filetype=='weathersimple'}">
			<tr><td align="center">
				<iframe src="\images\simpleweather.png" scrolling="no" width="300" height="115"   name="weathersimple_iframe" id="weathersimple_iframe" frameborder="0"></iframe>
			</td></tr>
			</c:if>
			
			<c:if test="${m_filetype=='htmeetingnotice'}">
				<tr><td align="center">
					<iframe src="/rq/livemeeting" scrolling="no" width="650" height="350"   name="livemeeting_iframe" id="livemeeting_iframe" frameborder="0"></iframe>
				</td></tr>
			</c:if>
			<c:if test="${m_filetype=='insnquametting'}">
				<tr><td align="center">
					<iframe src="/rq/livemeeting?op=${m_filetype}" scrolling="no" width="650" height="350"   name="livemeeting_iframe" id="livemeeting_iframe" frameborder="0"></iframe>
				</td></tr>
			</c:if>
			
			<c:if test="${m_filetype=='scroll'}">
				<tr>
					<td colspan="6" valign="top">
					<form name="scrollForm" method="post">
						<input type="hidden" name="scrollfg" value="${module.scroll_fg}"/>
						<input type="hidden" name="scrollfgRGB" value="${module.foreground}"/>
						<input type="hidden" name="scrollbg" value="${module.scroll_bg }"/>
						<input type="hidden" name="scrollbgRGB" value="${module.background }"/>
						<input type="hidden"  name="scroll_list"  value="${fn:length(mediaList)}"/>
						<table cellpadding="2" cellspacing="0" width="720" border="0" 
							style="margin-top: 2px; margin-left: 10px;border: 1px solid #69a3cd;">
							<tr><td height="60px" colspan="4" style="border-bottom : 1px solid #69a3cd;" title="滚动文字效果">
								<marquee id="scroll_marquee" bgcolor=#${module.alpha=='0.1'?'ffffff':module.scroll_bg}
								 scrollamount=3 height="60px" style="line-height: 60px;
								 font-weight:${module.fontTyle=='1'?'bold':'normal' };
								 font-style:${module.fontTyle=='2'?'italic':'normal' };
								  font-family:${module.fontName=='no'?'宋体':module.fontName };
								   font-size:${module.fontSize=='no'?'40':module.fontSize };
								    color: #${module.scroll_fg};" 
								 >${module.scroll_content}</marquee>
							</td>
							<td colspan="3" style="border-bottom : 1px solid #69a3cd;">
								<table border="0" cellpadding="0" cellspacing="0" width="90%" height="100%">
									<tr>
										<td align="center"><input type="button" value="文字录入" onclick="newScroll();" class="button"/></td>
										<td rowspan="2"><input type="button" value=" 保 存 " onclick="saveScroll();" class="button1"/><br/>
								<c:if test="${save_state=='save_ok'}"><span style="color: red">保存成功！</span></c:if></td>
									</tr>
									<tr>
										<td align="center"><input type="button" value="RSS导入" onclick="newrss();" class="button"/></td>
									</tr>
								</table>
								

							</td>
						</tr>
							<tr>
								<td align="center"  width="16%">字体：
									<select  style="width: 60px" title="字体" name="fontName" onchange="changestyle(1,this.value)">
										<option value="no" ${module.fontName=='no'?'selected':'' }>字体</option>
										<option value="宋体" ${module.fontName == '宋体'?'selected':'' }>宋体</option>
										<option value="黑体" ${module.fontName=='黑体'?'selected':'' }>黑体</option>
										<option value="楷体" ${module.fontName=='楷体'?'selected':'' }>楷体</option>
										<option value="幼圆" ${module.fontName=='幼圆'?'selected':'' }>幼圆</option>
										<option value="微软雅黑" ${module.fontName=='微软雅黑'?'selected':'' }>微软雅黑</option>
										<option value="Arial" ${module.fontName=='Arial'?'selected':'' }>Arial</option>
										<option value="Times New Roman" ${module.fontName=='Times New Roman'?'selected':'' }>Times New Roman</option>
									</select>
								</td>
								<td align="center"  width="18%">样式：
									<select style="width: 60px"  name="fontTyle"  onchange="changestyle(2,this.value)"> 
										<option value="no"  ${module.fontTyle=='no'?'selected':'' }>字体样式</option>
										<option value="0" ${module.fontTyle=='0'?'selected':'' }>正常</option>
										<option value="1" ${module.fontTyle=='1'?'selected':'' }>粗体</option>
										<option value="2" ${module.fontTyle=='2'?'selected':'' }>斜体</option>
									</select>
								</td>
								<td align="center"  width="18%">大小：
									<select style="width: 60px; " name="fontSize" onchange="changestyle(3,this.value)">
										<option value="no" ${module.fontSize=='no'?'selected':'' }>字体大小</option>
										<c:forEach var="fsize" items="${module.fontsizes}">
											<option ${fsize == module.fontSize ?'selected':''} value="${fsize}">${fsize}px</option>
										</c:forEach>
									</select>
								</td>
								<td align="center"  width="20%">速度：
									<select style="width: 60px" name="span" onchange="changespan(this.value)">
										<option value="no" ${module.span=='no'?'selected':'' }>滚动速度</option>
										<option value="20" ${module.span=='20'?'selected':'' }>正常</option>
										<option value="10" ${module.span=='10'?'selected':'' }>快</option>
										<option value="30" ${module.span=='30'?'selected':'' }>慢</option>
									</select>
								</td><td align="center"  width="20%">是否透明：
									<select style="width: 60px" name="alpha"  onchange="changealpha(this.value)">
										<option value="1.0" ${module.alpha=='1.0'?'selected':'' }>不透明</option>
										<option value="0.1" ${module.alpha=='0.1'?'selected':'' }>透明</option>
									</select>
								</td>
								<td align="center"  width="5%">
									<div id="scroll_fgId" style="border:solid 1px #000000;background-color:#${module.scroll_fg};width: 25px;height: 18px; cursor: pointer; font-family:Arial; font-size: 16; font-weight: bold; " title="字体颜色" onclick="showScrollFg()"></div>
								
								</td>
								<td align="center"  width="5%">
									<div  id="scroll_bgId" ${module.alpha=='0.1'?'disabled="disabled"':''}  style="border:solid 1px #000000;background-color:#${module.alpha=='0.1'?'ffffff':module.scroll_bg};width: 25px;height: 18px; cursor: pointer;font-family:Arial; font-size: 16; font-weight: bold " title="背景颜色" onclick="showScrollBg()"></div>
								</td>
							</tr>
						</table>
						</form>

						<script> 
							<!-- 
							var speed=20; //数字越大速度越慢 
							function changespan(sped){
								if(sped=='no'||sped==''){
									speed=3;
								}else if(sped=='20'){
									speed=3;
								}else if(sped=='30'){
									speed=5;
								}else if(sped=='10'){
									speed=2;
								}
								document.getElementById("scroll_marquee").scrollAmount=speed;
							}
							changespan('${module.span}');
							function changealpha(alpha){
								if(alpha=='1.0'){
									document.getElementById("scroll_bgId").style.backgroundColor="#0000ff";
									document.getElementById('scroll_marquee').style.backgroundColor="#0000ff";
									document.getElementById("scroll_bgId").disabled="";
								}else if(alpha=='0.1'){
									document.getElementById("scroll_bgId").style.backgroundColor="#ffffff";
									document.getElementById("scroll_bgId").disabled="disabled";
									document.getElementById('scroll_marquee').style.backgroundColor="#ffffff";
								}
							}
							function changestyle(n,style_){
								if(n==1){
									document.getElementById('scroll_marquee').style.fontFamily=style_;
								}else if(n==2){
									if(style_==1){
										document.getElementById('scroll_marquee').style.fontWeight="bold";
										document.getElementById('scroll_marquee').style.fontStyle="normal";
									}else if(style_==2){
										document.getElementById('scroll_marquee').style.fontStyle="italic";
										document.getElementById('scroll_marquee').style.fontWeight="normal";
									}else{
										document.getElementById('scroll_marquee').style.fontStyle="normal";
										document.getElementById('scroll_marquee').style.fontWeight="normal";
									}
								}else if(n==3){
									document.getElementById('scroll_marquee').style.fontSize=style_;
								}
							}
						
						--> 
						</script> 
					</td>
				</tr>
			</c:if>
			<c:if
				test="${m_filetype=='scroll' || m_filetype=='image' || m_filetype=='video' || m_filetype=='flash' || m_filetype=='backgroundmusic' 
				|| m_filetype=='ppt' || m_filetype=='word'|| m_filetype=='excel'|| m_filetype=='exe'|| m_filetype=='khmeetingnotice'
				||m_filetype=='policesubstation'||m_filetype=='pdf'}">
				<c:if test="${empty mediaList}">
					<tr><td style="padding-left: 50px; color: red"><br/>无媒体文件，请添加媒体！</td></tr>
				</c:if>
				<tr>
					<td colspan="6">
						<ul id="outer_wrap" class="media_llist_ul">
							<c:forEach var="media" items="${mediaList}">
								<li id="${media.id}">
								    <div class="li_div1">
										<img src="/images/dtreeimg/${media.media_type }.gif" align="absmiddle" />
										&nbsp;&nbsp;${media.media_title }
									</div>
									<div class="li_div2">
									   <img src="${media.slightly_img_path}" height="20px" onmouseover=showimg(event,'${media.slightly_img_path}') onmouseout="hiddenimg()"/>
									</div>
									<div class="li_div3">
										${media.media_type }
									</div>
		
									<div class="li_div4">
										<c:if test="${m_filetype=='scroll'}">
											<input type="button" class="button" onclick="update_scroll('${media.media_id }')" value="修 改" />
										</c:if>
										<input type="button" class="button" onclick="delmedia('${media.id }')" value="删 除" />
								   </div>
							   </li>
							</c:forEach>
						</ul>
					</td>
				</tr>
				
				<tr><td height="200px">&nbsp;</td></tr>
			</c:if>
			
			
			<!-------------------------------------------------------------->
			<!-- 旺旺定制---------------------------------------------------->
			
			<c:if test="${m_filetype=='web'}">
				<form name="w_webForm" method="post">
						<table cellpadding="1" align="center" cellspacing="1" width="600" border="0" bgcolor="#F5F9FD"
							style="margin-top: 5px; margin-left: 20px">
							<tr><td>
							<fieldset style="width: 97%;border: #6699cc 1 solid; ">
							<legend align="center">${module.m_filetypeZN}</legend>
							<table cellpadding="5px">
							<tr bgcolor="#F5F9FD">
								<td align="center"  height="25">
									1、<input type="text"  name="w_webContent1" maxlength="100" value="${module.w_webContent1}" style="width: 500px" >
								</td>
							</tr><%--
							<tr bgcolor="#F5F9FD">
								<td align="center"  height="25">
									2、<input type="text"  name="w_webContent2" maxlength="100" value="${module.w_webContent2}" style="width: 500px" >
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center"  height="25">
									3、<input type="text"  name="w_webContent3" maxlength="100" value="${module.w_webContent3}" style="width: 500px" >
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center"  height="25">
									4、<input type="text"  name="w_webContent4" maxlength="100" value="${module.w_webContent4}" style="width: 500px" >
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="left" colspan="2" height="25">
									5、<input type="text"  name="w_webContent5" maxlength="100" value="${module.w_webContent5}" style="width: 500px" >
								</td>
							</tr>
							--%><tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="50"><input type="button" value=" 保 存 " onclick="saveW_web();" class="button1"/>
								<c:if test="${save_state=='save_ok'}"><span style="color: red">保存成功！</span></c:if></td>
							</tr>
							</table>
							</fieldset>
							</td></tr>
							
						</table>
						</form>
			</c:if>
			
			<c:if test="${m_filetype=='date'}">
				<tr>
						<td colspan="3"  align="center" style="padding-top: 50px" >
							<table cellpadding="0" cellspacing="1" width="460" border="0" bgcolor="#69a3cd"
							style="margin-top: 2px; margin-left: 50px;">
							<tr bgcolor="#F5F9FD">
								<td align="center" height="25" width="440"  style="font-weight: bold">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;旺旺--日期(5楼)
								</td>
								<td width="20">
									<div id="divcolor_view" style="border:solid 1px #000000;background-color:${module.m_other};width: 20px;height: 20px; cursor: pointer; " title="点击修改颜色" onclick="showClockColordiv();"></div>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="100">
								<div id="w_date_id" style="height: 100px;font-family:Arial;color:${module.m_other};font-size:40px; font-weight:bold; padding-top: 40px"></div>
								</td>
							</tr></table>
				</tr>
			</c:if>
			<c:if test="${m_filetype=='dateother'}">
				<tr>
						 <td colspan="3"  align="center" style="padding-top: 50px" >
							<table cellpadding="0" cellspacing="1" width="260" border="0" bgcolor="#69a3cd"
							style="margin-top: 2px; margin-left: 50px;">
							<tr bgcolor="#F5F9FD">
								<td align="center" height="25" width="240"  style="font-weight: bold">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期
								</td>
								<td width="20">
									<div id="divcolor_view" style="border:solid 1px #000000;background-color:${module.m_other};width: 20px;height: 20px; cursor: pointer; " title="点击修改颜色" onclick="showClockColordiv();"></div>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="100">
								<div id="dateother_id" style="height: 100px;font-family:Arial;color:${module.m_other};font-size:40px; font-weight:bold; padding-top: 40px"></div>
								</td>
							</tr></table>
				</tr>
			</c:if>
			
			<c:if test="${m_filetype=='weatherthree'}">
				<tr>
					<td colspan="3" align="center">
					<form name="weatherForm" method="post">
						<input type="hidden" name="weatherfg" value="${module.w_font_color}"/>
						<input type="hidden" name="weatherfgRGB" value="${module.foreground}"/>
						<input type="hidden" name="w_image1" id="w_image1" value="${module.w_image1}"/>
						<input type="hidden" name="w_image2" id="w_image2" value="${module.w_image2}"/>
						<input type="hidden" name="w_image3" id="w_image3" value="${module.w_image3}"/>
						<fieldset style="width: 450; height:350px;border: #6699cc 1 solid; ">
							<legend align="center">${module.m_filetypeZN}</legend><br/>
						<table cellpadding="0" cellspacing="1" align="center" width="310" border="0" bgcolor="#69a3cd"
							style="margin-top: 5px; margin-left: 50px">
							<tr bgcolor="#F5F9FD">
								<td align="right"  height="25"  style="font-weight: bold">
									城市：&nbsp;
								</td>
								<td   height="25"  style="font-weight: bold">
									&nbsp;&nbsp;<input type="text" name="city" value="${module.w_city }"  class="button" />
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="right"  height="25"  style="font-weight: bold">
									字体颜色：&nbsp;</td>
								<td   height="25"  style="font-weight: bold">
								<div id="weather_fgId" style="margin-left: 10px;border:solid 1px #000000;background-color:#${module.w_font_color};width: 25px;height: 18px; cursor: pointer; font-family:Arial; font-size: 16; font-weight: bold; " title="字体颜色" onclick="showWeatherFg(360,120)"></div>
									
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td rowspan="3" align="center">
									<img src="/images/weather_images/${module.w_image1}" id="weatherimgid1" width="80" height="80" alt="天气图片" />
								</td>
								<td style="font-weight: bold">
									今天 
								</td>
							</tr>
							<tr  bgcolor="#F5F9FD">	
								<td>
									&nbsp;天气：
									<select style="width: 100px;"  onchange="changewind1(this.value,1)">
										<c:forEach var="wind" items="${module.winds}">
											<option ${fn:split(wind,"#")[1] == module.w_image1 ?'selected':''} value="${wind}">${fn:split(wind,"#")[0]}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td>
									&nbsp;温度：
									<select  style="width: 70px;"  name="w_start_temperature1">
										<c:forEach var="temperature" items="${module.w_temperatures}">
											<option ${module.w_start_temperature1==temperature?'selected':'' } value="${temperature}">${temperature }</option>
										</c:forEach>
									</select>
									~
									<select  style="width: 70px;" name="w_end_temperature1">
										<c:forEach var="temperature" items="${module.w_temperatures}">
											<option ${module.w_end_temperature1==temperature?'selected':'' } value="${temperature}">${temperature }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td rowspan="3" align="center">
									<img src="/images/weather_images/${module.w_image2}" id="weatherimgid2" width="80" height="80" alt="天气图片" />
								</td>
								<td style="font-weight: bold">
									明天
								</td>
							</tr>
							<tr  bgcolor="#F5F9FD">	
								<td>
									&nbsp;天气：
									<select style="width: 100px;"  onchange="changewind1(this.value,2)">
										<c:forEach var="wind" items="${module.winds}">
											<option ${fn:split(wind,"#")[1] == module.w_image2 ?'selected':''} value="${wind}">${fn:split(wind,"#")[0]}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td>
									&nbsp;温度：
									<select  style="width: 70px;"  name="w_start_temperature2">
										<c:forEach var="temperature" items="${module.w_temperatures}">
											<option ${module.w_start_temperature2==temperature?'selected':'' } value="${temperature}">${temperature }</option>
										</c:forEach>
									</select>
									~
									<select  style="width: 70px;" name="w_end_temperature2">
										<c:forEach var="temperature" items="${module.w_temperatures}">
											<option ${module.w_end_temperature2==temperature?'selected':'' } value="${temperature}">${temperature }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td rowspan="3" align="center">
									<img src="/images/weather_images/${module.w_image3}" id="weatherimgid3" width="80" height="80" alt="天气图片" />
								</td>
								<td style="font-weight: bold">
									后天
								</td>
							</tr>
							<tr  bgcolor="#F5F9FD">	
								<td>
									&nbsp;天气：
									<select style="width: 100px;"  onchange="changewind1(this.value,3)">
										<c:forEach var="wind" items="${module.winds}">
											<option ${fn:split(wind,"#")[1] == module.w_image3 ?'selected':''} value="${wind}">${fn:split(wind,"#")[0]}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td>
									&nbsp;温度：
									<select  style="width: 70px;"  name="w_start_temperature3">
										<c:forEach var="temperature" items="${module.w_temperatures}">
											<option ${module.w_start_temperature3==temperature?'selected':'' } value="${temperature}">${temperature }</option>
										</c:forEach>
									</select>
									~
									<select  style="width: 70px;" name="w_end_temperature3">
										<c:forEach var="temperature" items="${module.w_temperatures}">
											<option ${module.w_end_temperature3==temperature?'selected':'' } value="${temperature}">${temperature }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr bgcolor="#F5F9FD">
								<td align="center" colspan="2" height="25"><input type="button" value=" 保 存 " onclick="saveWeatherthree();" class="button"/>
								<c:if test="${save_state=='save_ok'}"><span style="color: red">保存成功！</span></c:if></td>
							</tr>
						</table></fieldset>
						</form>
					</td>
				</tr>


			</c:if>
			
			<c:if test="${m_filetype=='stock'}">
				<tr>
						<td colspan="3" align="center" >
							<fieldset style="width: 500; height:250px; border: #6699cc 1 solid; ">
							<legend align="center">${module.m_filetypeZN}${m_filetype}</legend><br/>
							<table cellpadding="0" cellspacing="0" border="0" width="400px" height="200px" background="/admin/program/want/stock.jpg">
								<tr>
									<td height="20px" width="200">&nbsp;</td>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td height="60px">&nbsp;</td>
									<td valign="bottom"  colspan="2" align="center"><span  style="font-size: 35px;height: 50px;padding-top: 30px;font-family: 黑体;color:${module.isadd==1?'green':red}">${module.now_price}</span></td>
									
								</tr>
								<tr>
									<td height="65px">&nbsp;</td>
									<td  valign="bottom" align="center"><span  style="font-size: 25px;height: 50px;padding-top: 30px;font-family: 黑体;color:${module.isadd==1?'green':red}">${module.isadd==1?'↑':'↓'}${module.now_Increase}</span></td>
									<td valign="bottom" align="center"><span  style="font-size: 25px;height: 50px;padding-top: 30px;font-family: 黑体;color:${module.isadd==1?'green':red}">${module.isadd==1?'↑':'↓'}${module.now_Increase_percent}</span></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
							</table>
							</fieldset>
							</td>
				</tr>
			</c:if>
			<c:if test="${m_filetype=='stockother'}">
				<tr>
						<td colspan="3" align="center"  >
							<fieldset style="width: 450; height:150px;border: #6699cc 1 solid; ">
							<legend align="center">${module.m_filetypeZN}</legend><br/>
							<table cellpadding="0" cellspacing="0" border="0" width="350px" height="100px" background="/admin/program/want/stock_no_k.jpg">
								<tr>
									<td height="10px" width="150">&nbsp;</td>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td height="30px">&nbsp;</td>
									<td valign="bottom"  colspan="2" align="center"><span  style="font-size: 25px;height: 30px;padding-top: 10px;font-family: 黑体;color:${module.isadd==1?'green':red}">${module.now_price}</span></td>
									
								</tr>
								<tr>
									<td height="40px">&nbsp;</td>
									<td  valign="bottom" align="center"><span  style="font-size: 25px;height: 30px;padding-top: 10px;font-family: 黑体;color:${module.isadd==1?'green':red}">${module.isadd==1?'↑':'↓'}${module.now_Increase}</span></td>
									<td valign="bottom" align="center"><span  style="font-size: 25px;height: 30px;padding-top: 10px;font-family: 黑体;color:${module.isadd==1?'green':red}">${module.isadd==1?'↑':'↓'}${module.now_Increase_percent}</span></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
							</table>
							</fieldset>
							</td>
				</tr>
			</c:if>
			
			<c:if test="${m_filetype=='birthday'}">
				<tr>
						<td colspan="3" align="center"  >
							<fieldset style="width: 500; height:150px;border: #6699cc 1 solid; ">
							<legend align="center">${module.m_filetypeZN}</legend><br/>
							<table cellpadding="0" cellspacing="0" border="0" width="400px" height="100px">
								<tr>
									<td height="80px" colspan="3" width="400px" background="/admin/program/want/birthday.jpg" >&nbsp;</td>
								</tr>
								<tr>
									<td  colspan="3">
										<div style="width: 100%; height: 200px">
											<table cellpadding="0" cellspacing="1" bgcolor="#80afe7" border="0" width="400px" height="100px">
												<tr bgcolor="#F5F9FD">
													<td width="30%" align="center">姓名</td>
													<td width="30%" align="center">出生日期</td>
													<td width="30%" align="center">部门</td>
												</tr>
												<tr bgcolor="#F5F9FD">
													<td width="30%" align="center">姓名</td>
													<td width="30%" align="center">出生日期</td>
													<td width="30%" align="center">部门</td>
												</tr>
												<tr bgcolor="#F5F9FD">
													<td width="30%" align="center">姓名</td>
													<td width="30%" align="center">出生日期</td>
													<td width="30%" align="center">部门</td>
												</tr>
												<tr bgcolor="#F5F9FD">
													<td width="30%" align="center">姓名</td>
													<td width="30%" align="center">出生日期</td>
													<td width="30%" align="center">部门</td>
												</tr>
												<tr bgcolor="#F5F9FD">
													<td width="30%" align="center">姓名</td>
													<td width="30%" align="center">出生日期</td>
													<td width="30%" align="center">部门</td>
												</tr>
												<tr bgcolor="#F5F9FD">
													<td width="30%" align="center">姓名</td>
													<td width="30%" align="center">出生日期</td>
													<td width="30%" align="center">部门</td>
												</tr>
												<tr bgcolor="#F5F9FD">
													<td width="30%" align="center">姓名</td>
													<td width="30%" align="center">出生日期</td>
													<td width="30%" align="center">部门</td>
												</tr>
												<tr bgcolor="#F5F9FD">
													<td width="30%" align="center">姓名</td>
													<td width="30%" align="center">出生日期</td>
													<td width="30%" align="center">部门</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
							</fieldset>
							</td>
				</tr>
			</c:if>
			
			<c:if test="${m_filetype=='wwnotice'}">
				<tr>
						<td colspan="3" align="center"  >
							<fieldset style="width: 550; height:350px;border: #6699cc 1 solid; ">
							<legend align="center">${module.m_filetypeZN}</legend><br/>
							<table cellpadding="0" cellspacing="0" border="0" width="500px" height="281px" background="/admin/program/want/want-meeting.jpg">
								<tr>
									<td height="10px" width="150">&nbsp;</td>
									<td colspan="2">&nbsp;</td>
								</tr>
								
							</table>
							</fieldset>
							</td>
				</tr>
			</c:if>
			<c:if test="${m_filetype=='filialeSell'}">
				<tr>
						<td colspan="3" align="center"  >
							<fieldset style="width: 550; height:350px;border: #6699cc 1 solid; ">
							<legend align="center">${module.m_filetypeZN}</legend><br/>
							<table cellpadding="0" cellspacing="0" border="0" width="500px" height="302px" background="/admin/program/want/filialeSell.jpg">
								<tr>
									<td height="10px" width="150">&nbsp;</td>
									<td colspan="2">&nbsp;</td>
								</tr>
								
							</table>
							</fieldset>
							</td>
				</tr>
			</c:if>
			<c:if test="${m_filetype=='wwmilkdrink'}">
				<tr>
						<td colspan="3" align="center"  >
							<fieldset style="width: 550; height:350px;border: #6699cc 1 solid; ">
							<legend align="center">${module.m_filetypeZN}</legend><br/>
							<table cellpadding="0" cellspacing="0" border="0" width="500px" height="302px" background="/admin/program/want/wwmilkdrink.jpg" style="background-repeat: no-repeat; background-position: center;">
								<tr>
									<td height="10px" width="150">&nbsp;</td>
									<td colspan="2">&nbsp;</td>
								</tr>
								
							</table>
							</fieldset>
							</td>
				</tr>
			</c:if>
				<!-- 旺旺定制-----------------------end----------------------------->
		</table>
		
		<div id="colorDiv">
			<div id="colorMessage">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td >
							<iframe src="/loading.jsp" scrolling="no" id="color_iframe"
								name="color_iframe" frameborder="0"></iframe>
						</td>
					</tr>
				</table>
			</div>
		</div>		
		<div id="smallImgDiv">
			<div id="smallImgMassage">
				<img src="" id="div_img" width="150" height="112" /></div>
</div>
		
	</body>
</html>
<c:if test="${isscroll==1}">
<div id="scrolldiv_temp" style="display: none;">${scrollcontent}</div>
<script>
document.getElementById("scroll_marquee").innerHTML=document.getElementById('scrolldiv_temp').innerHTML;
</script>
</c:if>
<c:if test="${ishtmltext==1}">
<div id="htmltextdiv_temp" style="display: none;">${htmltextcontent}</div>
<script>
document.getElementById('htmltextContent').value=document.getElementById('htmltextdiv_temp').innerHTML;
</script>
</c:if>
<c:if test="${m_filetype=='clock' || m_filetype=='date'|| m_filetype=='dateother'}">
<script>
clockon();
</script>
</c:if>
<script type="text/javascript">
        function get(imgpath){
		   //alert(imgpath);

		        document.getElementById("scroll_bimggId").style.background="url("+imgpath+")";
		    <c:if test="${oflag!='no'}">
				document.getElementById("scroll_bimggId").setAttribute("onmouseover",null);//移除带参数的js事件
				document.getElementById("scroll_bimggId").setAttribute("onmouseover",function(){showimg(event,imgpath)});////添加带参数的js事件
				document.getElementById("removebg").style.display="block";
			</c:if>
		}
         <c:if test="${bg!=''&& bg!=null}">
		      get('${bg}');
         </c:if>

    function $(id){
        return document.getElementById(id);
    }

	function getMousePos(e){
			return {
				x : e.pageX || e.clientX + document.body.scrollLeft,
				y : e.pageY || e.clientY + document.body.scrollTop
			}
	}

	function getElementPos(el){
			return {
				x : el.offsetParent ? el.offsetLeft + arguments.callee(el.offsetParent)['x'] : el.offsetLeft,
				y : el.offsetParent ? el.offsetTop + arguments.callee(el.offsetParent)['y'] : el.offsetTop
			}
	}

	function getElementSize(el){
			return {
				width : el.offsetWidth,
				height : el.offsetHeight
			}
	}
	document.onselectstart = function(){
		return false;
	}
	<!--调整列表顺序-->
		var MOVE = {};
		MOVE.isMove = false;
		var div = document.createElement('div');
		var before_li;
		div.style.width  = '100%';
		div.style.height = '1px';
		div.style.fontSize = '0';
		div.style.background = 'red';
		var outer_wrap = $('outer_wrap');
		outer_wrap.onmousedown = function(event){
		    var lis = outer_wrap.getElementsByTagName('li');
			for(var i = 0; i < lis.length; i++){
				lis[i]['pos']  = getElementPos(lis[i]);
				lis[i]['size'] = getElementSize(lis[i]);
			}

			event = event || window.event;
			var t = event.target || event.srcElement;
			t=t.parentNode;
			if(t.tagName.toLowerCase() == 'li'){
				var p = getMousePos(event);
				var el = t.cloneNode(true);
				el.style.position = 'absolute';
				el.style.left = t.pos.x + 'px';
				el.style.top  = t.pos.y + 'px';
				el.style.width   = t.size.width + 'px';
				el.style.height  = t.size.height + 'px';
				el.style.border  = '1px solid red';
				el.style.background = '#d4d4d4';
				el.style.opacity = '0.7';
				document.body.appendChild(el);
				document.onmousemove = function(event){
					event = event || window.event;
					var current = getMousePos(event);
					el.style.left =t.pos.x + current.x - p.x + 'px';
					el.style.top  =t.pos.y + current.y - p.y+ 'px';
					//document.body.style.cursor = 'move';
					for(var i = 0; i < lis.length; i++){
						if(current.x > lis[i]['pos']['x'] 
							&& current.x < lis[i]['pos']['x'] + lis[i]['size']['width'] 
							&& current.y > lis[i]['pos']['y'] 
							&& current.y < lis[i]['pos']['y'] + lis[i]['size']['height']/1.2){
							if(t != lis[i]){
								MOVE.isMove = true;
								outer_wrap.insertBefore(div,lis[i]);
									before_li=lis[i];
							}
						}else if(current.x > lis[i]['pos']['x'] 
								&& current.x < lis[i]['pos']['x'] + lis[i]['size']['width'] 
								&& current.y > lis[i]['pos']['y'] + lis[i]['size']['height']/1.2
								&& current.y < lis[i]['pos']['y'] + lis[i]['size']['height']){
							if(t != lis[i]){
								MOVE.isMove = true;
								outer_wrap.insertBefore(div,lis[i].nextSibling);
								before_li=lis[i].nextSibling;
							}
						}
					}
				}

				document.onmouseup = function(event){

					event = event || window.event;
					document.onmousemove = null;
					if(MOVE.isMove){
						//alert(t.id); alert(before_li.id);
						if(before_li.id!=""){
							DwrClass.orderMedia(t.id,before_li.id,${moduleid})
							outer_wrap.replaceChild(t,div);
						}
						MOVE.isMove = false;
					}
					document.body.removeChild(el);
					el = null;
					document.body.style.cursor = 'normal';
					document.onmouseup = null;
				}
				
			}
		}
</script>