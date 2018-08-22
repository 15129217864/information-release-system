<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<jsp:directive.page import="com.xct.cms.domin.Led"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	    Led led= new Led();
	    List<Led> list= led.getALLLed("");
	    request.setAttribute("ledlist",list);
	   String ip= request.getParameter("ip")==null?"":request.getParameter("ip");
	   request.setAttribute("ip",ip);
	    if("".equals(ip)){
		    if(list!=null&list.size()>0){
		    	request.setAttribute("objled",list.get(0));
		    }
	    }else{
	    	request.setAttribute("objled",led.getLedBystr("where ip ='"+ip+"'"));
	    }
%>
<html>
	<head>
		<title>�����������ڰ�--���ŵĿ�</title><script language="javascript" src="/js/vcommon.js"></script>
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
				//var num = chkNum();
				//if(num<1){
				//alert("��ѡ��LED��ʾ����");
				//return;
				//}
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
				if(confirm("��ʾ��Ϣ��ȷ���޸����ݣ�")){
				ledform.action="/admin/program/ledsetDO.jsp";
				ledform.submit();
				document.getElementById("xm_button").disabled="disabled";
				}
		     }
		     function getled(ip){
		     	window.location.href="/admin/program/ledset.jsp?ip="+ip;
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
  <form action="/admin/program/ledsetDO.jsp" method="post" name="ledform">
 
  <table align="center" border="0">
  	<tr>
  		<td width="40%" valign="top"  align="center" >
							<fieldset style="width:150px; height:400px;border:#6699cc 1 solid;">
								<legend align="center" style="font-size: 13px; color: blue; font-weight: bold">LED��ʾ��</legend>
								<div 
									style="width:155px; height:400px;border :0px solid Silver; overflow:auto;">
								<table cellpadding="0" cellspacing="0">
									<c:forEach var="led" items="${ledlist}" >
									<tr><td height="20">
									<input type="hidden"  value="${led.ip}"  name="ledip${led.ip}"/>
									<%--<input type="checkbox"  value="${led.ip}"  name="ledip" ${ip==led.ip?'checked':'' } />
										--%><a href="javascript:;" onclick ="getled('${led.ip}');return false">${led.title}</a></td></tr>
									</c:forEach>
								</table>
								</div>
						      </fieldset>
				</td>
  		<td  align="center" valign="top">
  		<fieldset style="width:430px; height:270px;border:#6699cc 1 solid;">
		    <legend align="center" style="font-size: 13px; color: blue; font-weight: bold">LED��Ϣ</legend>
            <table align="center" border="0" width="430">
			<tr>
				<td height="30">���ƣ�</td>
				<td><input type="text" name="title" value="${objled.title}"  maxlength="15" style="width:100px"/></td>
				<td>IP��ַ��</td>
				<td><input type="text" name="ip" value="${objled.ip}"  maxlength="15" style="width: 100px"/>
				 <input type="hidden"  value="${objled.ip}"  name="reallyledip"/></td>
			</tr>
			<tr>
				<td height="30">���� X��</td>
				<td><input type="text" name="x" value="${objled.x}"  maxlength="4" style="width: 50px" onkeyup="checkisnumber(this);" onafterpaste="checkisnumber(this);"/></td>
				<td>���� Y��</td>
				<td><input type="text" name="y" value="${objled.y}"   maxlength="4" style="width: 50px" onkeyup="checkisnumber(this);" onafterpaste="checkisnumber(this);"/></td>
			</tr>
			<tr>
				<td height="30">��ȣ�</td>
				<td><input type="text" name="width" value="${objled.width}"  maxlength="4" style="width: 50px"  onkeyup="checkisnumber(this);" onafterpaste="checkisnumber(this);"/></td>
				<td>�߶ȣ�</td>
				<td><input type="text" name="height" value="${objled.height}"   maxlength="4" style="width: 50px"  onkeyup="checkisnumber(this);" onafterpaste="checkisnumber(this);"/></td>
			</tr>
			<tr>
				<td height="30">���壺</td>
				<td>
					<select style="width: 80px" name="fontName">
						<option ${objled.fontName=='����'?'selected':'' } value="����">����</option>
						<option ${objled.fontName=='����'?'selected':'' } value="����"} >����</option>
						<option ${objled.fontName=='��Բ'?'selected':'' } value="��Բ" }>��Բ</option>
						<option ${objled.fontName=='������'?'selected':'' } value="������">������</option>
						<option ${objled.fontName=='����'?'selected':'' } value="����"} >����</option>
						<option ${objled.fontName=='����'?'selected':'' } value="����" }>����</option>
						<option ${objled.fontName=='΢���ź�'?'selected':'' } value="΢���ź�">΢���ź�</option>
				
					</select>
				</td>
				<td height="30">�����С��</td>
				<td> 
					 <select style="width: 70px" name="fontsize">
					        <c:forEach begin="10" end="128" var="i" step="2">
					           	 <option ${objled.fontSize==i?'selected':'' } value="${i}">${i}</option>
					        </c:forEach>        
				    </select>
			    </td>
				</tr>
				<tr>
				<td height="30" width="100" >��ʾ�ؼ���</td>
				<td><select style="width: 80px" name="stunt">
						<option ${objled.stunt=='32'?'selected':'' } value="32">��������</option>
						<option ${objled.stunt=='33'?'selected':'' } value="33">��������</option>
						<option ${objled.stunt=='30'?'selected':'' } value="30">��������</option>
						<option ${objled.stunt=='31'?'selected':'' } value="31">��������</option>
					</select>
				</td>
				<td width="100" height="30">&nbsp;�����ٶȣ�</td>
				<td><select style="width: 70px" name="playspeed">
				        <c:forEach begin="1" end="50" var="i">
				           <c:choose>
				            <c:when test="${i==1}">
				           	 <option ${objled.playspeed==i?'selected':'' } value="${i}">${i}(���)</option>
				            </c:when>
				            <c:when test="${i==50}">
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
				<td colspan="4" height="30">
				<input type="checkbox" name="isbold" ${objled.bold=='true'?'checked':'' } value="true"/>����
				<input type="checkbox" name="isitic"  ${objled.italic=='true'?'checked':'' } value="true"/>б��
				<input type="checkbox" name="isunderline" ${objled.underline=='true'?'checked':'' } value="true"/>�»���
				</td>
			</tr>
			<tr>
				<td colspan="4"><textarea style="height: 165" rows="4" cols="80" name="text">${objled.text}</textarea> </td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="button" class="button1" id="xm_button" onclick="updateLedInfo()"
									value=" �޸Ĳ����� ">
								&nbsp;
								<input type="reset" class="button1" value=" ȡ �� "
									onclick="parent.closedivframe(1);">
								&nbsp;&nbsp;&nbsp;&nbsp;
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
