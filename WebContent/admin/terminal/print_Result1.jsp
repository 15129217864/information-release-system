<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String cmd=request.getAttribute("cmd")==null?"":request.getAttribute("cmd").toString();
	String ok_str="",error_str="";
	String newopp="";
	String isoperatoring="�ն˻���δִ��ָ�";
	if("UPDATEPROGRAM".equals(cmd)){
		newopp="inglv0036";
		ok_str="�����ն˽�Ŀ�ɹ���";
		error_str="�����ն˽�Ŀʧ�ܣ�";
	}else if("SENDDEFAULTPROGRAM".equals(cmd)){
		newopp="inglv0037";
		ok_str="����Ĭ�Ͻ�Ŀ�ɹ���";
		error_str="����Ĭ�Ͻ�Ŀʧ�ܣ�";
	}else if("SENDDEMANDPROGRAM".equals(cmd)){
		newopp="inglv0101";
		ok_str="���ŵ㲥��Ŀ�ɹ���";
		error_str="���ŵ㲥��Ŀʧ�ܣ�";
	}else if("SENDDEMANDFILEPROGRAM".equals(cmd)){
		newopp="inglv0103";
		ok_str="���͵㲥��Ŀ�ļ��ɹ���";
		error_str="���͵㲥��Ŀ�ļ�ʧ�ܣ�";
	}
	request.setAttribute("newopp",newopp);
 %>

<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		    <script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<script language="javascript" src="/js/vcommon.js"></script>
    <script type="text/javascript">
    
    var timer=null;
    var time_num=0;
   
    var cntips='${resips}';
    var cnt_array=cntips.split("!");
    var cnt_array_num=cnt_array.length;
    timer= setInterval("DwrClass.get_cmd_result('${lg_user.name}','${resips}','${newopp}','','',cnt_result)", 1000);
    var timeout_num=50;
    
    function cnt_result(resultmaps){
		time_num++;
		 var allok_num=0;
		for(i=0;i<cnt_array_num;i++){
			if(document.getElementById(cnt_array[i])){
				if(document.getElementById(cnt_array[i]).className=="deff_ok"){
				    allok_num++;
				 }
			}
		}
		//	alert(allok_num+"====="+cnt_array_num+"---------"+time_num);
		if(allok_num==(cnt_array_num-1)){
				clearInterval(timer);
				setInterval("DwrClass.get_cmd_result('${lg_user.name}','${resips}','${opp}','<%=ok_str%>','<%=error_str%>',cnt_result1)", 1000);
		}
		if(cnt_array_num>10){
			timeout_num=cnt_array_num*3;
		}
		if(time_num<timeout_num){
	    	 for(var ipresult in resultmaps){
	    	 	if(document.getElementById(ipresult)){
		    	 	if(resultmaps[ipresult]=='OK'){
			    		document.getElementById(ipresult).innerHTML="<span style=color:'green'><img src='/images/loading.gif'>���ͳɹ����ͻ�����������...���Ժ�</span>";
			    		document.getElementById(ipresult).className="deff_ok";
		    		}else if(resultmaps[ipresult]=='ERROR'){
			    		document.getElementById(ipresult).innerHTML="<span style=color:'red'><img src='/images/error.gif'  height='16px'>����ʧ�ܣ����������FTP�Ƿ����ӣ�</span>";
			    		document.getElementById(ipresult).className="deff_ok";
		    		}else if(resultmaps[ipresult]=='DOWNLOAD'){
			    		document.getElementById(ipresult).innerHTML="<span style=color:'red'><img src='/images/error.gif'  height='16px'>����ʧ�ܣ��ͻ����������أ�</span>";
		    			document.getElementById(ipresult).className="deff_ok";
		    		}else if(resultmaps[ipresult]=='NOOP'){
			    		document.getElementById(ipresult).innerHTML="<span  style='color: red'><img src='/images/loading.gif'><%=isoperatoring%></span>";
			    		document.getElementById(ipresult).className="deff_ok";
		    		}
	    		}
	    	}
    	}else{
    		for(i=0;i<cnt_array_num;i++){
    			if(document.getElementById(cnt_array[i])){
    				if(document.getElementById(cnt_array[i]).className=="deff"){
		    			document.getElementById(cnt_array[i]).innerHTML="<span  style='color: red'>����ʧ�ܣ�ʧ��ԭ�����糬ʱ����</span>";
		    			DwrClass.addlogs("���ն�"+cnt_array[i]+"<%=error_str%>��ʧ��ԭ�����糬ʱ!��")
		    			document.getElementById(cnt_array[i]).className="deff_ok";
	    			}
    			}
    		}
    	}
    }
   
   function cnt_result1(resultmaps){
		for(var ipresult in resultmaps){
	    	 	if(document.getElementById(ipresult)){
		    	 	if(resultmaps[ipresult]=='OK'){
			    		document.getElementById(ipresult).innerHTML="<span style=color:'green'><%=ok_str%></span>";
		    		}else if(resultmaps[ipresult]=='ERROR'){
			    		document.getElementById(ipresult).innerHTML="<span style=color:'red'><img src='/images/error.gif'  height='16px'><%=error_str%></span>";
		    		}else if(resultmaps[ipresult]=='FTPCLOSE'){
			    		document.getElementById(ipresult).innerHTML="<span style=color:'red'><img src='/images/error.gif'  height='16px'><%=error_str%>��FTP�رգ�</span>";
		    		}else if(resultmaps[ipresult]=='NOEXISTS'){
			    		document.getElementById(ipresult).innerHTML="<span style=color:'red'><img src='/images/error.gif'  height='16px'><%=error_str%>���㲥�ļ������ڣ����ڽ�Ŀ����->���͵㲥�ļ���</span>";
		    		}else if(resultmaps[ipresult]=='NOOP'){
		    		    document.getElementById(ipresult).innerHTML="<span style=color:'red'><img src='/images/error.gif'  height='16px'><%=isoperatoring%></span>";
		    		}
	    		}
	    }
    }
   
    </script>
	</head>

	<body>
	<table align="center" width="100%"  border="0">
			<tr>
				<td align="center" width="50%" colspan="2"><strong>�ն�����</strong></td>
				<td align="center" width="50%" ><strong>״̬</strong></td>
			</tr>
			<tr>
				<td colspan="3" align="center" width="100%">
				<div style="overflow: auto;height: 300px;position:absolute;width: 100%">
					<table align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
						<c:forEach var="ter" items="${t_list}">
							<tr>
								<td  width="5%" height="20px" align="right"><img src="/images/dtreeimg/base.gif"/></td>
								<td  width="45%">��${ter.cnt_name}��</td>
								<td   width="50%" id="${ter.cnt_ip}_${ter.cnt_mac}" class="deff" style="style='word-break:break-all'"><img src="/images/mid_giallo.gif"/>&nbsp;&nbsp;<span  style="color: maroon">���ڷ���,���Ե�...</span></td>
							</tr>
							<tr><td class="Line_01" colspan="3"></td></tr>
						</c:forEach>
					</table>
				</div>
	<div style="height:300px"></div>
	<center><div style="height: 50px;vertical-align: middle;"  ><br/><input type="button" class="button1"  onclick="parent.closedivframe(1);" value=" �ر� ">
</div></center>
				
				</td>
			</tr>
	</table>
	
	
	
		
	</body>
</html>
