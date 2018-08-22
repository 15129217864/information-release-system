<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.domin.Led"/>
<jsp:directive.page import="com.xct.cms.dao.LedDao"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	   LedDao leddao= new LedDao();
	   String ip= request.getParameter("ip")==null?"127.0.0.1":request.getParameter("ip");
	   String mac= request.getParameter("mac")==null?"FFFFFFFFFFFF":request.getParameter("mac");
	   Led ledobj=leddao.getLedBystr("where pno ='"+ip+"'");
	   if(null==ledobj){
	     ledobj=new Led(ip,"led����**","127.0.0.1", 0,0, 128, 16, "TEST LED...", "����", 1, 12,"4", 3, "false", "0", "0x0252");
	   }
	   request.setAttribute("objled",ledobj);
%>
<html>
	<head>
		<title>����--����BX-5M2����ͨѶ</title><script language="javascript" src="/js/vcommon.js"></script>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<link rel="STYLESHEET" type="text/css" href="/admin/checkboxtree/dhtmlxtree.css">
		<link rel="stylesheet"  type="text/css" media="screen"  href="/admin/checkboxtree/style.css"/>
			
		<script language="JavaScript" src="/admin/checkboxtree/dhtmlxcommon.js"></script>
		<script language="JavaScript" src="/admin/checkboxtree/dhtmlxtree.js"></script>
		<script type="text/javascript">
			function isIP(strIP) {
		      var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
			    var reg = strIP.match(exp);
			    if(reg==null) {
			       return false;
			    } else{
			        return true;
			    }
		     }
	
			function updateLedInfo(){
				
				var title=ledform.title.value;
				var ip=ledform.ip.value;
				var width=ledform.width.value;
				var height=ledform.height.value;
				var text=ledform.text.value;
				
				if(title==""){
					alert("���Ʋ���Ϊ�գ�");
					return;
				}if(ip==""){
					alert("IP��ַ����Ϊ�գ�");
					return;
				}if(width==""){
					alert("��Ȳ���Ϊ�գ�");
					return;
				}if(height==""){
					alert("�߶Ȳ���Ϊ�գ�");
					return;
				}if(text==""){
					alert("��ʾ���ݲ���Ϊ�գ�");
					return;
				}
				if(confirm("��ʾ��Ϣ��ȷ�Ϸ������ݣ�")){
					ledform.action="/admin/program/ledshow/ledsetDO.jsp";
					ledform.submit();
					document.getElementById("xm_button").disabled="disabled";
				}
		     }
		     function getled(ip){
		     	window.location.href="/admin/program/ledshow/ledset.jsp?ip="+ip;
		     }
           function chkNum(){
			if(ledform && ledform.ledip!=null){
				var num=0;
			
				for(i=0;i<ledform.ledip.length;i++){
					if(ledform.ledip[i].checked == true) num++; 
				}	
				if(ledform.ledip.checked == true)num++;
				return num;
			} else {
				return 0;
			}
		}
		
		function checkisnumber(thistext){
		      
		     if(thistext.value.length==1){
		     	thistext.value=thistext.value.replace(/[^0-9]/g,'');
		     }else{
		     	thistext.value=thistext.value.replace(/\D/g,'');
		     }
		   }

	</script>
  </head>
  <body>
  <form action="" method="post" name="ledform">
 
  <table align="center" border="0">
  	<tr>
  		<td  align="center" valign="top">
  		<fieldset style="width:540px; height:270px;border:#6699cc 1 solid;">
		    <legend align="center" style="font-size: 13px; color: blue; font-weight: bold">LED��Ϣ</legend>
            <table align="center" border="0" width="530">
			<tr>
				<td height="30" align="right">���ƣ�</td>
				<td><input type="text" name="title" value="${objled.title}"  maxlength="15" style="width:100px"/></td>
				<td align="right">IP��ַ��</td>
				<td><input type="text" name="ip" value="${objled.ip}"  maxlength="15" style="width: 100px"/>
				 <input type="hidden"  value="${objled.pno}!${param.mac}"  name="pno"/></td>
			</tr>
			<tr>
				<td height="30" align="right">���� X��</td>
				<td><input type="text" name="x" value="${objled.x}"  maxlength="4" style="width: 50px" onkeyup="checkisnumber(this);" onafterpaste="checkisnumber(this);"/></td>
				<td align="right">���� Y��</td>
				<td><input type="text" name="y" value="${objled.y}"   maxlength="4" style="width: 50px" onkeyup="checkisnumber(this);" onafterpaste="checkisnumber(this);"/></td>
			</tr>
			<tr>
				<td height="30" align="right">��ȣ�</td>
				<td><input type="text" name="width" value="${objled.width}"  maxlength="4" style="width: 50px"  onkeyup="checkisnumber(this);" onafterpaste="checkisnumber(this);"/></td>
				<td align="right">�߶ȣ�</td>
				<td><input type="text" name="height" value="${objled.height}"   maxlength="4" style="width: 50px"  onkeyup="checkisnumber(this);" onafterpaste="checkisnumber(this);"/></td>
			</tr>
			<tr>
				<td height="30" align="right">���壺</td>
				<td>
					<select style="width: 80px" name="fontName">
						<option ${objled.fontName=='����'?'selected':'' } value="����">����</option>
						<option ${objled.fontName=='����'?'selected':'' } value="����"} >����</option>
						<option ${objled.fontName=='��Բ'?'selected':'' } value="��Բ" }>��Բ</option>
						<option ${objled.fontName=='������'?'selected':'' } value="������">������</option>
						<option ${objled.fontName=='����_GB2312'?'selected':'' } value="����_GB2312"} >����_GB2312</option>
						<option ${objled.fontName=='����'?'selected':'' } value="����" }>����</option>
						<option ${objled.fontName=='΢���ź�'?'selected':'' } value="΢���ź�">΢���ź�</option>
					</select>
				</td>
				<td height="30" align="right">�����С��</td>
				<td> 
					 <select style="width: 70px" name="fontsize">
					        <c:forEach begin="10" end="128" var="i" step="2">
					           	 <option ${objled.fontSize==i?'selected':'' } value="${i}">${i}</option>
					        </c:forEach>        
				    </select>
			    </td>
				</tr>
				<tr>
				<td height="30" width="100"  align="right">��ʾ�ؼ���</td>
				<td><select style="width: 80px" name="stunt">
						<option ${objled.stunt=='0'?'selected':'' } value="0">�����ʾ</option>
						<option ${objled.stunt=='1'?'selected':'' } value="1">��̬</option>
						<option ${objled.stunt=='2'?'selected':'' } value="2">���ٴ��</option>
						<option ${objled.stunt=='3'?'selected':'' } value="3">�����ƶ�</option>
						<option ${objled.stunt=='4'?'selected':'' } value="4">�������� </option>
						<option ${objled.stunt=='5'?'selected':'' } value="5">�����ƶ�</option>
						<option ${objled.stunt=='6'?'selected':'' } value="6">��������</option>
						<option ${objled.stunt=='7'?'selected':'' } value="7">��˸</option>	
						<option ${objled.stunt=='8'?'selected':'' } value="8">Ʈѩ</option>
						<option ${objled.stunt=='9'?'selected':'' } value="9">ð��</option>
						<option ${objled.stunt=='A'?'selected':'' } value="A">�м��Ƴ�</option>
						<option ${objled.stunt=='B'?'selected':'' } value="B">��������</option>		
						<option ${objled.stunt=='C'?'selected':'' } value="C">���ҽ�������</option>
						<option ${objled.stunt=='D'?'selected':'' } value="D">���½�������</option>
						<option ${objled.stunt=='E'?'selected':'' } value="E">����պ�</option>
						<option ${objled.stunt=='F'?'selected':'' } value="F">�����</option>
						<option ${objled.stunt=='10'?'selected':'' } value="10">��������</option>
						<option ${objled.stunt=='11'?'selected':'' } value="11">��������</option>
						<option ${objled.stunt=='12'?'selected':'' } value="12">��������</option>
						<option ${objled.stunt=='13'?'selected':'' } value="13">��������</option>	
						<option ${objled.stunt=='14'?'selected':'' } value="14">��������</option>
						<option ${objled.stunt=='15'?'selected':'' } value="15">��������</option>
						<option ${objled.stunt=='16'?'selected':'' } value="16">��������</option>
						<option ${objled.stunt=='17'?'selected':'' } value="17">��������</option>
						<option ${objled.stunt=='18'?'selected':'' } value="18">���ҽ�����Ļ</option>
						<option ${objled.stunt=='19'?'selected':'' } value="19">���½�����Ļ</option>
						<option ${objled.stunt=='1A'?'selected':'' } value="1A">��ɢ����</option>
						<option ${objled.stunt=='1B'?'selected':'' } value="1B">ˮƽ��ҳ</option>		
						<option ${objled.stunt=='1C'?'selected':'' } value="1C">��ֱ��ҳ</option>
						<option ${objled.stunt=='1D'?'selected':'' } value="1D">������Ļ</option>
						<option ${objled.stunt=='1E'?'selected':'' } value="1E">������Ļ</option>
						<option ${objled.stunt=='1F'?'selected':'' } value="1F">������Ļ</option>
						<option ${objled.stunt=='20'?'selected':'' } value="20">������Ļ</option>
						<option ${objled.stunt=='21'?'selected':'' } value="21">���ұպ�</option>
						<option ${objled.stunt=='22'?'selected':'' } value="22">���ҶԿ�</option>
						<option ${objled.stunt=='23'?'selected':'' } value="23">���±պ�</option>	
						<option ${objled.stunt=='24'?'selected':'' } value="24">���¶Կ�</option>
						<option ${objled.stunt=='25'?'selected':'' } value="25">�����ƶ�</option>
						<option ${objled.stunt=='26'?'selected':'' } value="26">��������</option>
						<option ${objled.stunt=='27'?'selected':'' } value="27">�����ƶ�</option>
						<option ${objled.stunt=='28'?'selected':'' } value="28">��������</option>
					</select>
				</td>
				<td width="100" height="30"  align="right">&nbsp;�����ٶȣ�</td>
				<td><select style="width: 70px" name="playspeed">
				        <c:forEach begin="1" end="64" var="i">
				           <c:choose>
				            <c:when test="${i==1}">
				           	 		<option ${objled.playspeed==i?'selected':'' } value="${i}">${i}(���)</option>
				            </c:when>
				            <c:when test="${i==64}">
				            		<option ${objled.playspeed==i?'selected':'' } value="${i}">${i}(����)</option>
				            </c:when>
				            <c:otherwise>
				           	 		<option ${objled.playspeed==i?'selected':'' } value="${i}">${i}</option>
				            </c:otherwise>
				           </c:choose>
				        </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td height="30" width="100"  align="right">LED���ͺţ�</td>
				<td><select style="width: 80px" name="l_num">
						<option ${objled.l_num=='0x0051'?'selected':'' } value="0x0051">BX-5AT</option>
						<option ${objled.l_num=='0x0151'?'selected':'' } value="0x0151">BX-5A0</option>
						<option ${objled.l_num=='0x0251'?'selected':'' } value="0x0251">BX-5A1</option>
						<option ${objled.l_num=='0x0651'?'selected':'' } value="0x0651">BX-5A1&WiFi</option>
						<option ${objled.l_num=='0x0351'?'selected':'' } value="0x0351">BX-5A2</option>
						<option ${objled.l_num=='0x1351'?'selected':'' } value="0x1351">BX-5A2&RF</option>
						<option ${objled.l_num=='0x0751'?'selected':'' } value="0x0751">BX-5A2&WiFi</option>
						<option ${objled.l_num=='0x0451'?'selected':'' } value="0x0451">BX-5A3</option>
						<option ${objled.l_num=='0x0551'?'selected':'' } value="0x0551">BX-5A4</option>
						<option ${objled.l_num=='0x1551'?'selected':'' } value="0x1551">BX-5A4&RF</option>
						<option ${objled.l_num=='0x0851'?'selected':'' } value="0x0851">BX-5A4&WiFi</option>
						<option ${objled.l_num=='0x0052'?'selected':'' } value="0x0052">BX-5M1</option>		
						<option ${objled.l_num=='0x0252'?'selected':'' } value="0x0252">BX-5M2</option>
						<option ${objled.l_num=='0x0352'?'selected':'' } value="0x0352">BX-5M3</option>
						<option ${objled.l_num=='0x0452'?'selected':'' } value="0x0452">BX-5M4</option>
						<option ${objled.l_num=='0x0055'?'selected':'' } value="0x0055">BX-5UT</option>
						<option ${objled.l_num=='0x0155'?'selected':'' } value="0x0155">BX-5U0</option>
						<option ${objled.l_num=='0x0255'?'selected':'' } value="0x0255">BX-5U1</option>
						<option ${objled.l_num=='0x0355'?'selected':'' } value="0x0355">BX-5U2</option>
						<option ${objled.l_num=='0x0455'?'selected':'' } value="0x0455">BX-5U3</option>
						<option ${objled.l_num=='0x0555'?'selected':'' } value="0x0555">BX-5U4</option>
						<option ${objled.l_num=='0x0254'?'selected':'' } value="0x0254">BX-5E2</option>
						<option ${objled.l_num=='0x0354'?'selected':'' } value="0x0354">BX-5E3</option>
						<option ${objled.l_num=='0x0140'?'selected':'' } value="0x0140">BX-4T1</option>
						<option ${objled.l_num=='0x0240'?'selected':'' } value="0x0240">BX-4T2</option>
						<option ${objled.l_num=='0x0340'?'selected':'' } value="0x0340">BX-4T3</option>
						<option ${objled.l_num=='0x0141'?'selected':'' } value="0x0141">BX-4A1</option>
						<option ${objled.l_num=='0x0241'?'selected':'' } value="0x0241">BX-4A2</option>		
						<option ${objled.l_num=='0x0341'?'selected':'' } value="0x0341">BX-4A3</option>
						<option ${objled.l_num=='0x1041'?'selected':'' } value="0x1041">BX-4AQ</option>	
						<option ${objled.l_num=='0x0041'?'selected':'' } value="0x0041">BX-4A</option>
						<option ${objled.l_num=='0x0445'?'selected':'' } value="0x0445">BX-4UT</option>
						<option ${objled.l_num=='0x0045'?'selected':'' } value="0x0045">BX-4U0</option>
						<option ${objled.l_num=='0x0145'?'selected':'' } value="0x0145">BX-4U1</option>
						<option ${objled.l_num=='0x0245'?'selected':'' } value="0x0245">BX-4U2</option>	
						<option ${objled.l_num=='0x0545'?'selected':'' } value="0x0545">BX-4U2X</option>
						<option ${objled.l_num=='0x0345'?'selected':'' } value="0x0345">BX-4U3</option>
						<option ${objled.l_num=='0x0242'?'selected':'' } value="0x0242">BX-4M0</option>
						<option ${objled.l_num=='0x0142'?'selected':'' } value="0x0142">BX-4M1</option>		
						<option ${objled.l_num=='0x0042'?'selected':'' } value="0x0042">BX-4M</option>
						<option ${objled.l_num=='0x0C42'?'selected':'' } value="0x0C42">BX-4MC</option>	
						<option ${objled.l_num=='0x0043'?'selected':'' } value="0x0043">BX-4C</option>
						<option ${objled.l_num=='0x0144'?'selected':'' } value="0x0144">BX-4E1</option>
						<option ${objled.l_num=='0x0044'?'selected':'' } value="0x0044">BX-4E</option>		
						<option ${objled.l_num=='0x0844'?'selected':'' } value="0x0844">BX-4EL</option>
						<option ${objled.l_num=='0x0010'?'selected':'' } value="0x0010">BX-3T</option>	
						<option ${objled.l_num=='0x0021'?'selected':'' } value="0x0021">BX-3A1</option>
						<option ${objled.l_num=='0x0022'?'selected':'' } value="0x0022">BX-3A2</option>
						<option ${objled.l_num=='0x0020'?'selected':'' } value="0x0020">BX-3A</option>
						<option ${objled.l_num=='0x0030'?'selected':'' } value="0x0030">BX-3M</option>
					</select>
				</td>
				<td width="100" height="30"  align="right">&nbsp;ͣ��ʱ��(0.5s)��</td>
				<td><select style="width: 70px" name="sleeptime">
				        <c:forEach begin="0" end="10" var="i">
				           	 <option ${objled.sleeptime==i?'selected':'' }  value="${i}">${i}</option>
				        </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="4" height="30"  align="left">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="checkbox" name="isbold" ${objled.bold=='true'?'checked':'' } value="true"/>����
				</td>
			</tr>
			<tr>
				<td colspan="4"  align="center"><textarea style="height:170" rows="4" cols="100" name="text">${objled.text}</textarea> </td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="button" class="button1" id="xm_button" onclick="updateLedInfo()"
									value=" ���� ">
								&nbsp;
								<input type="reset" class="button1" value=" ȡ �� "
									onclick="parent.closedivframe(1);">
								<span style="color: red">${empty param.isok?'':(param.isok eq 'ok'?'���ͳɹ���':'����ʧ�ܣ�')}</span>
				</td>
			</tr>						
		  </table>	
      </fieldset>
  		</td>
  	</tr>
  </table>
</form>
	</body>
</html>
