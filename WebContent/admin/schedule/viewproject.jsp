<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<jsp:directive.page import="com.xct.cms.xy.dao.ManagerProjectDao"/>
<jsp:directive.page import="com.xct.cms.xy.domain.ClientProjectBean"/>
<jsp:directive.page import="com.xct.cms.utils.UtilDAO"/>
<jsp:directive.page import="com.xct.cms.utils.Util"/>
<jsp:directive.page import="com.xct.cms.dao.ProgramHistoryASC1"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
out.print("<center id='loadid' style='font-size:12px'><br/><br/><br/><img src='/images/mid_giallo.gif'/>&nbsp;������...</center>");
ManagerProjectDao managerprojectdao=new ManagerProjectDao();
String onedate=request.getParameter("onedate")==null?"":request.getParameter("onedate");
String twodate=request.getParameter("twodate")==null?"":request.getParameter("twodate");
String starttime=request.getParameter("starttime")==null?"":request.getParameter("starttime");
String endtime=request.getParameter("endtime")==null?"":request.getParameter("endtime");
String searchstr=Util.getGBK(request.getParameter("searchstr")==null?"":request.getParameter("searchstr"));
String jmids=request.getParameter("jmids")==null?"no":request.getParameter("jmids");//���ɾ����ť��õ�ֵ
//��ѯ��ť
String openstatus=request.getParameter("openstatus")==null?"0":request.getParameter("openstatus");
String isdel=request.getParameter("isdel")==null?"0":request.getParameter("isdel");
String month=request.getParameter("month")==null?"":request.getParameter("month");
String left_menu=request.getParameter("left_menu")==null?"all":request.getParameter("left_menu");
String zuid=request.getParameter("zuid")==null?"":request.getParameter("zuid");
String cnt_mac=request.getParameter("cnt_mac")==null?"":request.getParameter("cnt_mac");
String isdelall=request.getParameter("isdelall")==null?"0":request.getParameter("isdelall");
/////System.out.println("jmid= "+jmid);

int pages=50; //ÿҳ ��ʾ10��
int pagecount=(null==request.getParameter("pagecount")?1:Integer.parseInt(request.getParameter("pagecount")));
int allpages=0;  //��ҳ��
int allpagestemp=0;
/////////////////////////////
	String str="";
	if("ZU".equals(left_menu)&&!"1".equals(zuid)){
	 	str=" and cnt_zuid="+zuid+" ";
	 }else if("CNT".equals(left_menu)){
	     str=" and program_delid ='"+cnt_mac+"' ";
	 }
//System.out.println(str);
if("1".equals(isdel)){ ///ɾ����Ŀʹ��
	if("0".equals(isdelall)){
		if(!"no".equals(jmids)){
				String jmid[] = jmids.split("!");
				for (int i = 1; i < jmid.length; i++) {
				  managerprojectdao.deleteClientProject(Integer.parseInt(jmid[i])); //����ǽ���Ľ�Ŀ���ͱ���Ҫ�ѿͻ��˵Ľ�Ŀͬʱɾ����
				}
		}
	}
}

//////////////////////////////////////
if("1".equals(openstatus)){ ///�����ѯ��ť
		if(!"".equals(month)){  ///����������ϸ��Ϣ���� ��ѯһ��
			String nowYear=UtilDAO.getNowtime("yyyy-MM-dd");
			onedate=nowYear;
			twodate=nowYear;
		}

		//Mysql
        String program_SetDate="date_format(program_SetDate, '%Y-%m-%d')";
        String program_SetDateTime="date_format(program_SetDateTime, '%Y-%m-%d')";
        String sustr1="";
        String sustr2="";
        String sustr3=") ";
        String isloopstr="";
         
        //SQLServer
        //String program_SetDate="convert(nvarchar(50),program_SetDate,14)"; //��ʽΪ�� 20:00:00:000
        //String program_SetDateTime="(convert(nvarchar(50),program_SetDateTime,20)";//��ʽΪ��2018-07-22 20:00:00
        //String sustr1=" 00:00:00";
        //String sustr2=":000";
        //String sustr3=")) ";
       // String isloopstr="program_isloop=3 or ";

		if(onedate.equals(twodate)){
			str +=" and  program_name like '%"+searchstr+"%' and  ("+isloopstr +program_SetDateTime+" = '"+onedate + sustr1+"'  ";
			if(!"".equals(starttime)&&!"".equals(endtime)){
				str +=" and "+program_SetDate+" between '"+starttime + sustr2+"' and '"+endtime + sustr2+"'"+sustr3;
			}else{
				str+=sustr3;
			}
		}else{
			str +=" and  program_name like '%"+searchstr+"%' and  ("+isloopstr +program_SetDateTime+" between '"+onedate + sustr1+"' and '"+twodate + sustr1+"' ";
			if(!"".equals(starttime)&&!"".equals(endtime)){
				str +=" and "+program_SetDate+" between '"+starttime + sustr2+"' and '"+endtime + sustr2+"'"+sustr3;
			}else{
				str+=sustr3;
			}
		}
		if("1".equals(isdel)){ ///ɾ����Ŀʹ��
			if("1".equals(isdelall)){
				managerprojectdao.deletALLeclient(str);
			}
		}
		//System.out.println(str);
		allpagestemp=managerprojectdao.getClientProect_H(str);
		if(allpagestemp%pages==0)//�õ���ҳ�� 
			allpages=allpagestemp/pages;
		else 
			allpages=allpagestemp/pages+1;
		List<ClientProjectBean> clientprojectbeanlist =	managerprojectdao.getClientProect_H2(pages, pagecount,str);
		Collections.sort(clientprojectbeanlist,new ProgramHistoryASC1()); 
		request.setAttribute("allpages", allpages);//��ʾ����ҳ������
		request.setAttribute("pagecount",pagecount);// ��ʾ��ǰ�ڵڼ�ҳ
		
		request.setAttribute("clientprojectbeanlist",clientprojectbeanlist);//��ʾ���������
		//request.setAttribute("searchstr", searchstr);//����������
		//request.setAttribute("onedate", onedate);//��ʼʱ��
	 
}else{////ֱ�ӵ������
	if(!"".equals(month)){  ///����������ϸ��Ϣ���� ��ѯһ��
		String nowYear=UtilDAO.getNowtime("yyyy-MM-dd");
		onedate=nowYear;
		twodate=nowYear;
		
		//--------------------------------------------------------------------------
		//mysql
		 String program_SetDateTime="date_format(program_SetDateTime, '%Y-%m-%d') ";
		 str +=" and "+program_SetDateTime+" = '"+onedate+"'";// or  program_isloop=3 
		 //sqlserver
		// String program_SetDateTime="(convert(nvarchar(50),program_SetDateTime,20)";
		//str +=" and "+program_SetDateTime+" = '"+onedate+" 00:00:00'   or program_isloop=3 ";
		 //--------------------------------------------------------------------------
		 
		 
		//str +=" and (convert(nvarchar(50),program_SetDateTime,20) between '"+onedate+"' and '"+twodate+"' )";
		//str +=" and convert(nvarchar(50),program_SetDateTime,11)  like '"+month+"%'";
		if("1".equals(isdel)){ ///ɾ����Ŀʹ��
			if("1".equals(isdelall)){
				managerprojectdao.deletALLeclient(str);
			}
		}
		//System.out.println("openstatus==>"+str);
		allpages= managerprojectdao.getClientProect_H(str);//��ҳ��
		if(allpages%pages==0)//�õ���ҳ��
		 	allpages=allpages/pages;
		else
			allpages=allpages/pages+1;
		 	List<ClientProjectBean> clientprojectlist=managerprojectdao.getClientProect_H2(pages,pagecount,str);
		 	Collections.sort(clientprojectlist,new ProgramHistoryASC1()); 
			request.setAttribute("clientprojectbeanlist",clientprojectlist);
			request.setAttribute("allpages",allpages);
			request.setAttribute("pagecount",pagecount);
	}else{  // ���ĳ����ʾ����Ϣ
		
		//mysql
		 str +=" and date_format(program_SetDateTime, '%Y-%m-%d') = '"+onedate+"' or program_isloop=3 ";
	    //SQLServer
		//str +=" and (convert(nvarchar(50),program_SetDateTime,20) = '"+onedate+" 00:00:00'   or program_isloop=3)";
	    
		if("1".equals(isdel)){ ///ɾ����Ŀʹ��
			if("1".equals(isdelall)){
				managerprojectdao.deletALLeclient(str);
			}
		}	
		//System.out.println(str);
		allpagestemp=managerprojectdao.getClientProect_H(str);
		if(allpagestemp%pages==0)//�õ���ҳ�� 
			allpages=allpagestemp/pages;
		else 
			allpages=allpagestemp/pages+1;
		List<ClientProjectBean> clientprojectbeanlist =	managerprojectdao.getClientProect_H2(pages, pagecount,str);
		Collections.sort(clientprojectbeanlist,new ProgramHistoryASC1()); 
		request.setAttribute("allpages", allpages);//��ʾ����ҳ������
		request.setAttribute("pagecount",pagecount);// ��ʾ��ǰ�ڵڼ�ҳ
		request.setAttribute("clientprojectbeanlist",clientprojectbeanlist);//��ʾ���������	
	}
}
request.setAttribute("searchstr",searchstr);// ��ʾ��ǰ�ڵڼ�ҳ
request.setAttribute("onedate",onedate);
request.setAttribute("twodate",twodate);
request.setAttribute("starttime",starttime);
request.setAttribute("endtime",endtime);
request.setAttribute("openstatus",openstatus);
%>
<html>
  <head>
    <title>��Ŀ��ѯ</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
			body, td, input {
			    font-family:Arial;
			    font-size:12px;
			}
		    /* ��ʾdiv����ʽ */
			#suggest {
			    width:100px;
			    border:1px solid black;
			    font-size:14px;
			}
			
			/* ��ʾ��Ϣ��긲��ʱ��Ϣ */
			div.over {
			    border:1px solid #999;
			    background:#FFFFCC;
			    cursor:hand;
			}
			
			/* ��ʾ��Ϣ����Ƴ�ʱ��Ϣ */
			div.out {
			    border: 1px solid #FFFFFF;
			    background:#FFFFFF;
			}
			
		#ToolBar {
position:absolute;
bottom:0px;

width:100%;
height:30px;
padding-top:5px;
text-align:center;
background-color:#F5F9FD;
z-index:2;
overflow:hidden;
}
     </style>
     <script type="text/javascript" src="/My97DatePicker/WdatePicker.js"></script>
      <script language="javascript" src="/js/vcommon.js"></script>
     <link rel="stylesheet" href="/css/style.css" type="text/css">
     <script type="text/javascript">
			var xmlHttp;                        //���ڱ���XMLHttpRequest�����ȫ�ֱ���
			var currentInfo = "";               //���ڱ��浱ǰ�û�������Ϣ
			var counter = 1;                    //��ȡ��Ϣ������
			var isReading = true;               //�Ƿ��ڼ����û�����״̬
            var txtInput;
            var divContent;
            
			//���ڴ���XMLHttpRequest����
			function createXmlHttp() {
			    //����window.XMLHttpRequest�����Ƿ����ʹ�ò�ͬ�Ĵ�����ʽ
			    if (window.XMLHttpRequest) {
			       xmlHttp = new XMLHttpRequest();                  //FireFox��Opera�������֧�ֵĴ�����ʽ
			    } else {
			       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");//IE�����֧�ֵĴ�����ʽ
			    }
			}
			
			function clearinput(){
			   document.getElementById("playclient").value="";
			}
			
			 function initPar() {
			 
			      txtInput = document.getElementById("playclient"); 
			      divContent=document.getElementById("suggest");  
			      //���������� ����� �ı�������λ��    
			      setPosition();   
			 } 
			
			 //���������� ����� �ı�������λ��   
			 function setPosition(){ 
			  
			   //  var width = txtInput.offsetWidth;         //�����Ŀ��   
			   //  var left  = getLength("offsetLeft");      //���ƫ��������   
			    // var top   = getLength("offsetTop") + txtInput.offsetHeight;   //����ƫ��������   
			       
			    // divContent.style.left = left + "px";   
			   //  divContent.style.top = top + "px";    
			   //  divContent.style.width = width + "px";   
			 }    
			    
			//��ȡ��Ӧ���Եĳ���    
			 function getLength(attr){  
			   
			     var offset = 0;   
			     var item = txtInput;   
			     while (item) {
			         offset += item[attr];      //ѭ����ȡ������λ��   
			         item = item.offsetParent;  //�����   
			     }    
			     return offset;    
			 }    
			
			//���������ȡ��ʾ��Ϣ
			function getSuggest(info) {
			  if(""!=info){
				    createXmlHttp();                                //����XMLHttpRequest����
				    xmlHttp.onreadystatechange = showSuggest;       //���ûص�����
				    xmlHttp.open("GET", "/rq/selectproject?info="+info+"&timestamp=" + new Date().getTime(), true);
				    xmlHttp.send(null);
			   }
			}
			
			//���������������Ϣ
			function showSuggest() {
			    if (xmlHttp.readyState == 4) {
			        clearSuggest();                             //���������ʾ��Ϣ
			        var suggestsText = xmlHttp.responseText;
			
			        //���������������Ϣ��Ϊ���򴴽��µ�suggest
			        if (suggestsText != "") {
			            var suggests = suggestsText.split("|"); //ʹ�á�|���ָ���ʾ��Ϣ
			            //ѭ��������ʾ��Ϣ����
			            for (var i=0; i<suggests.length; i++) {
			                createSuggest(suggests[i]);         //����ÿ����ʾ��Ϣ
			            }
			            displaySuggest();                       //��ʾ��ʾ��Ϣ
			        } else {
			            hiddenSuggest();                        //������ʾ��Ϣ
			        }
			    }
			}
			
			//������ʾ��Ϣ�ڵ�
			function createSuggest(text) {
			    var sDiv = "<div class='out' onmouseover=\"this.className='over'\"" + " onmouseout = \"this.className='out'\" onclick='setSuggest(this)'>" + text + "</div>";
			    divContent.innerHTML += sDiv;   //���½��ڵ����suggest div
			}
			
			//��Ӧ������¼�����suggest��Ϣд���û��ı���
			function setSuggest(src) {
			    txtInput.value = src.innerHTML;
			    hiddenSuggest();        //������ʾ��Ϣ
			}
			
			//���û��ٴμ�����Ϣʱ�����ô˺������´򿪼���״̬
			function resetReading(info) {
			     getSuggest(info);   
			}
			
			//��ʾ��ʾ��Ϣ
			function displaySuggest() {
			    divContent.style.display = "";
			}
			
			//������ʾ��Ϣ
			function hiddenSuggest() {
			    divContent.style.display = "none";
			}
			
			//�����ʾ��Ϣ
			function clearSuggest() {
			    divContent.innerHTML = "";
			}
			
			function selectjm(){
			
				var onedate=myform.onedate.value;
				var twodate=myform.twodate.value;
			   if(onedate>twodate){
			     alert("����ʱ�䲻�ܱȿ�ʼʱ��С��������ѡ�����ʱ�䣡");
			     return;
			   }
			    parent.parent.menu.location.href="/admin/schedule/left.jsp?month=&opp=1&type=info&onedate="+onedate+"&twodate="+twodate;	
		        progressBarOpen();
			   myform.action="/admin/schedule/viewproject.jsp?openstatus=1&left_menu=<%=left_menu%>&zuid=<%=zuid%>&cnt_mac=<%=cnt_mac%>";
			   myform.submit();
			}
			function deletejm(jmid){
				if(confirm("��ʾ��Ϣ��ȷ��ɾ���ý�Ŀ��")){
				   myform.action="/rq/viewclientproject?jmid="+jmid;
				   myform.submit();
			    }
			}
			
			function deletelocaljm(jmid,builddate,pagecount,onedate){
			
				if(myform.isdelall.checked != true){
					var num = chkNum();
					if(num<1){
						alert("��ѡ����Ҫɾ�����");
						return;
					}
				}
				if(confirm("��ʾ��Ϣ��ȷ��ɾ��ѡ�н�Ŀ��")){
				document.getElementById("delloc").disabled="disabled" ;
					var jmids= getCheckIPs();
					//var openstatus=${openstatus};
					//if(openstatus==1){
					progressBarOpen();
					 	myform.action="/admin/schedule/viewproject.jsp?isdel=1&openstatus=1&jmids="+jmids+"&onedate="+builddate+"&pagecount="+pagecount+"&onedate="+onedate+"&left_menu=<%=left_menu%>&zuid=<%=zuid%>&cnt_mac=<%=cnt_mac%>";;
						myform.submit();
					//}else{
					  //  window.location.href="/admin/schedule/viewproject.jsp?isdel=1&jmids="+jmids+"&onedate="+builddate+"&pagecount="+pagecount+"&onedate="+onedate;
					//}
				  }
			}
			
			
	function all_chk(sel_all){
	  	var cbox   = myform.checkbox;
	  	if(cbox){
		    if(cbox.length){
		    	for(i=0;i<cbox.length;i++){
		    		cbox[i].checked=sel_all;
		    	}
		    }else{
		    	cbox.checked = sel_all;
		    }   
		}
	}
	function chkNum(){
			if(myform && myform.checkbox!=null){
				var forms = myform;
				var num=0;
			
				for(i=0;i<forms.checkbox.length;i++){
					if(forms.checkbox[i].checked == true) num++; 
				}	
				if(forms.checkbox.checked == true)num++;
				return num;
			} else {
				return 0;
			}
	}
	function getCheckIPs(){
		var checkIPs="";
		for(i=0;i<myform.checkbox.length;i++){
			if(myform.checkbox[i].checked == true) {
				ip = myform.checkbox[i].value;
				checkIPs+="!"+ip;
			}
		}
		if(myform.checkbox.checked == true){
		checkIPs+="!"+myform.checkbox.value;
		}
		return checkIPs;
	}
	
	function gopage(pagecount){
	progressBarOpen();
		var openstatus=${openstatus};
		if(openstatus==1){
		 	myform.action="/admin/schedule/viewproject.jsp?openstatus=1&pagecount="+pagecount+"&month=<%=month%>&left_menu=<%=left_menu%>&zuid=<%=zuid%>&cnt_mac=<%=cnt_mac%>";
			 myform.submit();
		}else{
			myform.action="/admin/schedule/viewproject.jsp?pagecount="+pagecount+"&month=<%=month%>&left_menu=<%=left_menu%>&zuid=<%=zuid%>&cnt_mac=<%=cnt_mac%>";
			 myform.submit();
		}
	}
	function gopage1(allnum){
		pagenum=document.getElementById("gopageid").value;
		if(pagenum==""){
			alert("��������תҳ�棡");
			return;
		}
		if(isNaN(pagenum)){
			alert("��תҳ�����Ϊ���֣�");
			document.getElementById("gopageid").value="";
			return;
		}
		if(pagenum>allnum){
		pagenum=allnum;
		}
		gopage(pagenum);
	}
	
 function progressBarOpen(){
		  			var cWidth = document.body.clientWidth;
		  			var cHeight= document.body.clientHeight;
					var divNode = document.createElement( 'div' );	
					divNode.setAttribute("id", "systemWorking");
					var topPx=(cHeight)*0.4;
					var defaultLeft=(cWidth)*0.4;
					divNode.style.cssText='position:absolute;margin:0;top:'+topPx+'px;left:'+defaultLeft+';width:215;height:59;background-image:url(/images/wait_background.gif);text-align:center;padding-top:20'; 
					divNode.innerHTML= "<img src='/images/mid_giallo.gif' align=absmiddle><font style='font-size:12px;' id='p_str'>&nbsp;�����У����Ժ�...</font>";
					divNode.style.display='block';	
					document.getElementsByTagName("body")[0].appendChild(divNode);   		
		       }
     </script>
  </head>
  
  <body onload="initPar()">
  <form id="project" name="myform" method="post" action="">
    <div align="right" style="height: 30px; width: 99%;" >
    	��Ŀ���ƣ�<input size="12" type="text" name="searchstr" style="height: 18px" value="${searchstr}" />&nbsp;&nbsp;&nbsp;&nbsp;
	     �������ڣ�<input class="Wdate" size="11" type="text" name="onedate"  onfocus="new WdatePicker(this,'%Y-%M-%D',true,'whyGreen')"  value="${onedate }"/>&nbsp;��&nbsp;
	     <input class="Wdate" size="11" type="text" name="twodate"   onfocus="new WdatePicker(this,'%Y-%M-%D',true,'whyGreen')"  value="${twodate }"/>&nbsp;&nbsp;&nbsp;&nbsp;
	     ����ʱ�䣺<input class="Wdate" size="8" type="text" name="starttime"  onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')"  value="${starttime }"/>&nbsp;��&nbsp;
	     <input class="Wdate" size="8" type="text" name="endtime"   onfocus="new WdatePicker(this,'%h:%m:00',true,'whyGreen')"  value="${endtime }"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       <input type="button" name="ddd" class="button" value=" ��ѯ " onclick="selectjm()"/> &nbsp;
			    <c:if test="${not empty lg_authority}">
				   <c:set var="str" value="${lg_authority}"/>
				   <c:if test="${fn:contains(str,'U')}">
			            <input type="button" value=" ɾ �� " id="delloc" class="button"  onclick="deletelocaljm('${clientprojectlist.jmid }','${onedate }','${pagecount}','${onedate }')"/>
						<input type="checkbox" name="isdelall" value="1"  />ȫɾ
				</c:if>
			</c:if>
     </div>
	 <div  >
		  <table border="0" width="100%" cellpadding="0" cellspacing="0" >
		      <tr align="center" class="TitleBackground">
		      	<td width="4%">���</td>
		       <td width="15%">�ն�����</td>
		       <td width="10%">�ն�IP</td>
		       <td width="15%">��Ŀ����</td>
		       <td width="16%">��ʼʱ��</td>
		       <td width="17%">����ʱ��</td>
		       <td width="7%">��������</td>
		       <td width="8%">����ʱ��</td>
		       <td  width="8%"><input type="checkbox" name="checkall" onclick="all_chk(this.checked);" />ȫ/��</td>
		     </tr>
		    </table>
		    <div id="content_div_id" style="overflow: auto;position:absolute;width: 100%; ">
		    <c:if test="${empty clientprojectbeanlist}">
		    	<div align="center"><font color="bule"><br/>��Ǹ��û�ҵ�����ѯ�����ݣ�</font></div>
		    </c:if>
		    <table border="0"width="100%" cellpadding="0" cellspacing="0" >
		    	<td width="4%" height="1px"></td>
		       <td width="15%"></td>
		       <td width="10%"></td>
		       <td width="15%"></td>
		       <td width="17%"></td>
		       <td width="17%"></td>
		       <td width="7%"></td>
		       <td width="8%"></td>
		       <td width="7%"></td>
		    <c:set var="i_num" value="1"></c:set>
		         <c:forEach items="${clientprojectbeanlist}" var="clientprojectlist">
				       <tr align="center" class="TableTrBg06_" onmouseover="this.className='TableTrBg06'" onmouseout="this.className='TableTrBg06_'" style="height: 25px">
				          <td height="22px;">${i_num}</td>
				          <td>${clientprojectlist.playclient }</td>
				           <td>${clientprojectlist.clientip }</td>
				          <td>${clientprojectlist.name }</td>
				          <c:choose>
				          	<c:when test="${clientprojectlist.isloop=='3'}">
				          		<td>ÿ��</td>
				         	 	<td>ÿ��</td>
				          	</c:when>
				          	<c:otherwise> 
				          		<td>${clientprojectlist.setdate }</td>
				         	 	<td>${clientprojectlist.enddate }</td>
				         	 </c:otherwise>
				          </c:choose>
				         
				          <td>${clientprojectlist.playtypeZh }</td>
				          <td>${clientprojectlist.playsecond }����</td>
				          <td><input type="checkbox" name="checkbox" value="${clientprojectlist.jmid }" />
				          </td>
				        </tr>
				        <tr><td class="Line_01" colspan="9"></td></tr>
				         <c:set var="i_num" value="${i_num+1}"></c:set>
			       </c:forEach>
			       <tr><td colspan="9" height="20px">&nbsp;</td></tr>
		     </table>
		     </div>
	   </div>
</form>
	<c:if test="${allpages>1}">
 		<!--  <div align="center" id="ToolBar" > -->
			 ��&nbsp;<font color="blue"> ${pagecount }</font>&nbsp;ҳ 
            <c:choose>
				   <c:when test="${pagecount==1 }"> 
				        <font color="gray">��һҳ</font>
				   </c:when>
				   <c:otherwise>
				       <a href="javascript:;" onclick="gopage('${pagecount-1}');return false;"><font color="blue">��һҳ</font></a>
				   </c:otherwise>
			</c:choose>
			 <c:choose>
				   <c:when test="${pagecount ==allpages }"> 
				        <font color="gray">��һҳ</font>
				   </c:when>
				   <c:otherwise>
				       <a href="javascript:;" onclick="gopage('${pagecount+1}');return false;"><font color="blue">��һҳ</font></a>
				   </c:otherwise>
			</c:choose>
			�ܹ�&nbsp;<font color="blue"> ${allpages }</font>&nbsp;ҳ
			<input type="text" style="width: 30px;height: 18px" id="gopageid" value="${pagecount}" style="width: 35px;height: 18;pxime-mode:Disabled" onkeypress="return event.keyCode>=48&&event.keyCode<=57||event.keyCode==46" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" maxlength="5" />
			<input type="button" value="��ת" class="button" onclick="gopage1(${allpages});"/>
	  </c:if>     
  </body>
</html>
<script>
if(document.getElementById("loadid"))
document.getElementById("loadid").style.display="none";
document.getElementById("content_div_id").style.height=document.body.clientHeight-80;
</script>