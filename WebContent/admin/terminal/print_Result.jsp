<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String cmd=request.getAttribute("cmd")==null?"":request.getAttribute("cmd").toString();
String ok_str="",error_str="";
String isoperatoring="�ն˻���δִ��ָ�";

if("ONDOWN".equals(cmd)){
	ok_str="������������ɹ����ն������ߣ�";
	error_str="�ն�����ʧ�ܣ�";
}else if("STOPONDOWN".equals(cmd)){
	ok_str="����ֹͣ��������ɹ����ն���ֹͣ���ߣ�";
	error_str="�ն�ֹͣ����ʧ�ܣ�";
}else if("RESTART".equals(cmd)){
	ok_str="������������ɹ����ն�����������";
	error_str="�ն�����ʧ�ܣ�";
}else if("SENDNOTICE".equals(cmd)){
	//String notice_content=request.getAttribute("notice_content")==null?"":request.getAttribute("notice_content").toString();
	ok_str="��������֪ͨ�ɹ���"; 
	error_str="��������֪ͨʧ�ܣ�"; 
}else if("SENDLEDNOTICE".equals(cmd)){
	//String notice_content=request.getAttribute("notice_content")==null?"":request.getAttribute("notice_content").toString();
	ok_str="����LED����֪ͨ�ɹ���"; 
	error_str="����LED����֪ͨʧ�ܣ�"; 
}
else if("CLEAR".equals(cmd)){
	ok_str="���ͳ�ʼ���ɹ���";
	error_str="���ͳ�ʼ��ʧ�ܣ�";
}else if("SYNCHRONIZATION".equals(cmd)){
	ok_str="����ʱ��ͬ���ɹ���";
	error_str="����ʱ��ͬ��ʧ�ܣ�";
}else if("UPDATE_DOWNLOAD_START_END".equals(cmd)){
	ok_str="��������ʱ�����óɹ���";
	error_str="��������ʱ������ʧ�ܣ�";
}else if("UPDATESOFTWARE".equals(cmd)){
	ok_str="������������ɹ���";
	error_str="�����������ʧ�ܣ�";
}else if("ONCLOSE".equals(cmd)){
	ok_str="���͹ػ�����ɹ���";
	error_str="���͹ػ�����ʧ�ܣ�";
}else if("DELETEDEMANDPROGRAM".equals(cmd)){
	ok_str="ɾ���㲥��Ŀ�ɹ���";
	error_str="ɾ���㲥��Ŀʧ�ܣ�";
}else if("STARTLED".equals(cmd)){
	ok_str="�����ն�LED���ɹ���";
	error_str="�����ն�LED��ʧ�ܣ�";
}else if("CLOSELED".equals(cmd)){
	ok_str="�ر��ն�LED���ɹ���";
	error_str="�ر��ն�LED��ʧ�ܣ�";
}
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
		}
		if(cnt_array_num>10){
			timeout_num=cnt_array_num*3;
		}
		if(time_num<timeout_num){
	    	 for(var ipresult in resultmaps){
	    	 	if(document.getElementById(ipresult)){
		    	 	if(resultmaps[ipresult]=='OK'){
			    		document.getElementById(ipresult).innerHTML="<span  style='color: green'><%=ok_str%></span>";
			    		document.getElementById(ipresult).className="deff_ok";
		    		}else if(resultmaps[ipresult]=='ERROR'){
			    		document.getElementById(ipresult).innerHTML="<span  style='color: red'><%=error_str%></span>";
			    		document.getElementById(ipresult).className="deff_ok";
		    		}else if(resultmaps[ipresult]=='NOOP'){
			    		document.getElementById(ipresult).innerHTML="<span  style='color: red'><%=isoperatoring%></span>";
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
    timer= setInterval("DwrClass.get_cmd_result('${lg_user.name}','${resips}','${opp}','<%=ok_str%>','<%=error_str%>',cnt_result)", 3000);
    
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
