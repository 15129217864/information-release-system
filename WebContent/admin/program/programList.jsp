<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title></title>
		<link rel="stylesheet" href="/css/style.css" type="text/css" />
		<script language="JavaScript" src="/js/jquery1.4.2.js"></script>
<script language="javascript" src="/js/vcommon.js"></script>
<script language="JavaScript" type="text/JavaScript">

 		parent.listtop.location.href="/admin/program/program_list_top.jsp";
 		
		function showDiv(num,title,fwidth,fheight,url){
		  
			parent.parent.parent.document.body.scrollTop = "0px";
			parent.parent.parent.document.getElementById("div_iframe"+num).width=fwidth;
			parent.parent.parent.document.getElementById("div_iframe"+num).height=fheight;
			parent.parent.parent.document.getElementById("divframe"+num).style.left=(parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";
			parent.parent.parent.document.getElementById("divframe"+num).style.top="2px";
			parent.parent.parent.document.getElementById("div_iframe"+num).src=url;
			parent.parent.parent.document.getElementById("titlename"+num).innerHTML=title;
			parent.parent.parent.document.getElementById("divframe"+num).style.display='block';
			parent.parent.parent.document.getElementById("massage"+num).style.display='block';
			parent.parent.parent.document.getElementById("bgDiv").style.visibility='visible';
		}
		function showDiv2(num,title,fwidth,fheight,url,topstr){
			parent.parent.parent.document.body.scrollTop = "0px";
			parent.parent.parent.document.getElementById("div_iframe"+num).width=fwidth;
			parent.parent.parent.document.getElementById("div_iframe"+num).height=fheight;
			parent.parent.parent.document.getElementById("divframe"+num).style.left=(parent.parent.parent.document.body.clientWidth - fwidth) / 2 + "px";
			parent.parent.parent.document.getElementById("divframe"+num).style.top=topstr;
			parent.parent.parent.document.getElementById("div_iframe"+num).src=url;
			parent.parent.parent.document.getElementById("titlename"+num).innerHTML=title;
			parent.parent.parent.document.getElementById("divframe"+num).style.display='block';
			parent.parent.parent.document.getElementById("massage"+num).style.display='block';
			parent.parent.parent.document.getElementById("bgDiv").style.visibility='visible';
		}
		function closedivframe(){
		   
			document.getElementById("div_iframe").src="/loading.jsp";
			document.getElementById('divframe').style.display="none";
		}
		
		function all_chk(sel_all){
			var ckform = document.ifrm_Form;
		  	var cbox   = ckform.checkbox;
		  	if(cbox){
			    if(cbox.length){
			    	for(i=0;i<cbox.length;i++){
			    		ckform.checkbox[i].checked=sel_all;
			    	}
			    }else{
			    	ckform.checkbox.checked = sel_all;
			    }   
			}
		}
		function sendProgram(program_name,program_file,templateid){
		    //showDiv(1,"��Ŀ���ԣ�",650,550,"/admin/program/opprojecttype.jsp?program_file="+program_file+"&program_name="+program_name+"&templateid="+templateid+"&t=" + new Date().getTime());
		    showDiv(2,"��Ŀ��"+program_name+"����������",650,550,"/admin/program/opprojecttype.jsp?program_file="+program_file+"&program_name="+encodeURI(program_name)+"&templateid="+templateid+"&t=" + new Date().getTime());
		}
		function viewProgram(jmtitle,jmurl,templateid,_width,_height){
			var cnt_height=parent.parent.parent.document.body.clientHeight;
			var cnt_width=parent.parent.parent.document.body.clientWidth;
			var ss=0.2;
			if(_width>600){
			  ss=0.5;
			}
			var pjh=(cnt_height/_height).toFixed(1)-ss;
		   showDiv(1,"��ĿԤ����"+jmtitle,_width*pjh,_height*pjh,"/admin/program/viewProgram.jsp?jmurl="+jmurl+"&templateid="+templateid+"&scale="+pjh+"&t=" + new Date().getTime());
		}
		function onSendDemandProgram(jmurl){
			showDiv(1,"���͵㲥��Ŀ",500,350,"/admin/program/sendDemandProgram.jsp?programe_file="+jmurl+"&t=" + new Date().getTime());
		}
		function onInsertProgram(jmurl){
			 var isauditing='${isauditing}';
			if(isauditing==1){
			    if(checkAuditingStatus(jmurl))
				  showDiv(2,"���Ͳ岥��Ŀ",900,500,"/admin/program/selectclientIP2.jsp?programe_file="+jmurl+"&t=" + new Date().getTime());
				 else
				  alert("��Ǹ,��ǰ��Ŀδ��ˣ�");
			}else{
				 showDiv(2,"���Ͳ岥��Ŀ",900,500,"/admin/program/selectclientIP2.jsp?programe_file="+jmurl+"&t=" + new Date().getTime());
			}
		}
		function gopage(left_menu,zuid,pagenum){
		  window.location.href="/rq/programList?left_menu="+left_menu+"&zu_id="+zuid+"&pagenum="+pagenum;
		}
		function toppagerefresh(){
		   if(${refreshtop=='ok'})
		     parent.listtop.location.href="/admin/program/program_list_top.jsp";
		}
		function onlock(jmurl){
			if(confirm("���������")){
				document.getElementById("display_iframe").src="/admin/program/onlock.jsp?jmurl="+jmurl;
			}
		}
		
		function auditingProgram(jmurl,i){
			if(confirm("ȷ�����ͨ���˽�Ŀ��")){
				var param="programe_file="+jmurl+"&op=update&t=" + new Date().getTime();
				$.ajax({
				    type: "POST",
				    url:  "/rq/jmauditingstatus",
				    dataType:"text",
					data:param,
				    success: function (data){
				      //alert(data);
				      if(data=="1"){
				       	  $("#auditingstatus"+i).html("<font color='green'>�����</font>");
			              $("#insertid"+i).show();
				      }else{
				        alert("���ʧ��")
				      }
				    },
                    beforeSend: function() {}, //���󷢳�ǰ�Ĵ�����,����(XMLHttpRequest����,����״̬)
				    error:function(XmlHttpRequest,textStatus,errorThrown){  
			            var $errorPage = XmlHttpRequest.responseText;  
						if($errorPage!='')
			              alert($($errorPage).text());  
			          },
				    complete: function (XHR, TS) { XHR = null; } //�ͷ�ajax��Դ\
				});
			}
		}
		
		function downloadProgram(jmurl){
		    window.location.href="/rq/downloadprogram?programe_file="+jmurl+"&t=" + new Date().getTime();
		}
		
		 function checkAuditingStatus(jmurl){
            var bool=false;
            var param="programe_file="+jmurl+"&op=getstatus&t=" + new Date().getTime();
            //alert(param);
            $.ajax({
				    type: "POST",
				    url:  "/rq/jmauditingstatus",
				    dataType:"text",
					data:param,
					async:false,
				    success: function (data){
				      //alert(data);
				      if(data=="1"){
				       	 bool=true;
				      }else{
				        bool=false;
				       // alert("��Ǹ,��ǰ��Ŀδ��ˣ�");
				      }
				    },
                    beforeSend: function() {}, //���󷢳�ǰ�Ĵ�����,����(XMLHttpRequest����,����״̬)
				    error:function(XmlHttpRequest,textStatus,errorThrown){  
			            var $errorPage = XmlHttpRequest.responseText;  
						if($errorPage!='')
			              alert($($errorPage).text());  
			          },
				    complete: function (XHR, TS) { XHR = null; } //�ͷ�ajax��Դ\
				});
				return bool;
        }
		
</script>
<style type="text/css">
#divframe{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2);   display: none }
#massage{border:#6699cc solid; border-width:1 1 1 1; background:#fff; color:#036; font-size:12px; line-height:150%;  display: none }
.header{background:url(/images/device/btn_background.gif);  font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff;cursor: move;}
</style>

</head>
<body onload="toppagerefresh()">

	<c:choose>
   	  <c:when test="${other==3}">
   	    <script type="text/javascript">
   	    	parent.closedivframe(2);
			alert("��Ŀ�ϴ��ɹ���������Ա���...");
		</script>
   	  </c:when>
   	  <c:otherwise>
			<form action="" name="ifrm_Form">
			<!-- list -->
			  <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="table1">
			    <tr>
			      <td>
			      <c:if test="${empty requestScope.programList}">
			<br/>
			<center>���޽�Ŀ��</center>
			</c:if>
			      <c:forEach var="programcla" items="${requestScope.programList}" varStatus="status">
				   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0" >
			        <tr  class="TableTrBg06_" onmouseover="this.className='TableTrBg06'" onmouseout="this.className='TableTrBg06_'" height="35px">
			          <td width="3%" align="center">
			          		<%--<c:choose>
			          		<c:when test="${programcla.program_ISloop==1}">
			          			<c:choose>
			          				<c:when test="${sessionScope.lg_user.lg_role==1}">
			          					<img src="/images/lock.png" width="18" title="��ǰ��Ŀ���ڱ���${programcla.program_lx}���޸ģ�������������ɽ�����" style="cursor: pointer;" onclick="onlock('${programcla.program_JMurl }');"/>
			          				</c:when>
			          				<c:when test="${programcla.program_lx==sessionScope.lg_user.name}">
			          					<img src="/images/lock.png" width="18" title="��ǰ��Ŀ�ѱ�������������ɽ�����" style="cursor: pointer;" onclick="onlock('${programcla.program_JMurl }');"/>
			          				</c:when>
			          				<c:otherwise>
			          					<img src="/images/lock.png" width="18" title="��ǰ��Ŀ���ڱ���${programcla.program_lx}���޸ģ���������"/>
			          				</c:otherwise>
			          			</c:choose>
			          			
			          		</c:when>
			          		<c:otherwise>
			          		   --%>
			          		   <input type="checkbox" name="checkbox" value="${programcla.program_JMurl }_${programcla.isSet_play_type==0?'0':'1'}" >
			            <%--
			          		</c:otherwise>
			          	</c:choose>
			          --%>
			          </td>             
			          <td width="22%"><a href="javascript:;" title="Ԥ����Ŀ" onClick="viewProgram('${programcla.program_Name }','${programcla.program_JMurl }','${programcla.templateid }',${programcla.template_width },${programcla.template_height });return false"><strong>${programcla.program_Name }</strong></a></td>
			          <td width="10%">${programcla.program_JMurl }</td>
			          <td width="10%">${programcla.zu_name }</td>
			          <td width="15%">${programcla.program_SetDateTime }</td>
			          <td width="10%">${programcla.program_adduser }</td>  
			          <td width="5%" >
			             <c:if test="${isauditing eq '1' }">
				             <c:choose>
		          				 <c:when test="${programcla.auditingstatus eq '0' ||programcla.auditingstatus eq ''}">
					                <strong id="auditingstatus${status.index+1}"><font color='red'>�����</font></strong>
					             </c:when>
		          				<c:otherwise>
		          					<strong id="auditingstatus${status.index+1}"><font color='green'>�����</font></strong>
		          				</c:otherwise>
		          			  </c:choose>
	          			  </c:if>
			          </td>  
			           <td width="35%">
			            <c:choose>
				              <c:when test="${isauditing eq '1' }">
						           <c:choose>
			          				 <c:when test="${programcla.auditingstatus eq '0' ||programcla.auditingstatus eq ''}">
						                <input type="button" value="�� ��" id="insertid${status.index+1}" style="display:none" class="button" onclick="onInsertProgram('${programcla.program_JMurl }');"/> 
						             </c:when>
			          				<c:otherwise>
			          					<input type="button" value="�� ��" id="insertid${status.index+1}"  class="button" onclick="onInsertProgram('${programcla.program_JMurl }');"/> 
			          				</c:otherwise>
			          			  </c:choose> 
	          			       </c:when>
				                <c:otherwise>
		          					<input type="button" value="�� ��" id="insertid${status.index+1}"  class="button" onclick="onInsertProgram('${programcla.program_JMurl }');"/> 
				                </c:otherwise>
			                </c:choose>
			                
						    <input type="button" value="��Ŀ��������" style="width: 90px"  class="button" onclick="sendProgram('${programcla.program_Name }','${programcla.program_JMurl }','${programcla.templateid }');"/>
				            <c:if test="${isauditing eq '1' }">
					            <c:if test="${sessionScope.lg_user.lg_role==1||sessionScope.lg_user.lg_role==2}">
					                <input type="button" value="���"  class="button" onclick="auditingProgram('${programcla.program_JMurl }',${status.index+1});"/>
					            </c:if>
				            </c:if>
				            <input type="button" value="����"  class="button" onclick="downloadProgram('${programcla.program_JMurl }');"/>
				            
				            <!--<input type="button" value=" Ԥ �� "  class="button" onclick="viewProgram('${programcla.program_Name }','${programcla.program_JMurl }','${programcla.templateid }',${programcla.template_width },${programcla.template_height });"/> -->
							<c:if test="${ispointplay eq '1' }">
				               <input type="button" value="�㲥"  class="button" onclick="onSendDemandProgram('${programcla.program_JMurl }');"/>
				            </c:if>
						</td>       
			         </tr>
			      </table>
			        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td class="Line_01"></td>
			          </tr>
			        </table>
				  	</c:forEach>				       
			      </td>
			    </tr>
			  </table>
			</form>
			<c:if test="${pager.totalPage>0}">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="center" valign="top">
							�ܹ���<span style="color: blue">${program_sum}</span>������Ŀ
			                  <a href="javascript:;" onclick="gopage('${left_menu }','${zu_id }',1);return false;"><img src="/images/btn_first.gif" border="0"/></a>&nbsp;
							  <a href="javascript:;" onclick="gopage('${left_menu }','${zu_id }','${pager.currentPage-1 }');return false;"><img src="/images/btn_pre.gif" border="0"/></a>&nbsp; ${pager.currentPage }&nbsp; 
			                  <a href="javascript:;" onclick="gopage('${left_menu }','${zu_id }','${pager.currentPage+1 }');return false;"><img src="/images/btn_next.gif" border="0"/></a>&nbsp; 
			                  <a href="javascript:;" onclick="gopage('${left_menu }','${zu_id }','${pager.end }');return false;"><img src="/images/btn_end.gif" border="0"/></a>&nbsp;
			                  ��${pager.totalPage }ҳ 
							</td>
							<td width="100px">&nbsp;</td>
						</tr>
					</table>
					</c:if>
			<div id="divframe">
			<div  id="massage">
			<table cellpadding="0" cellspacing="0" >
				<tr  height="30px;" class=header  onmousedown=MDown(divframe)><td align="left" style="font-weight: bold"><span id="titlename"></span></td>
					<td height="15px" align="right"><a href="javaScript:void(0);" style="color: #000000" onclick="closedivframe();">�ر�</a></td></tr>
				<tr><td colspan="2">
				<iframe src="/loading.jsp" scrolling="no" id="div_iframe"  name="div_iframe" frameborder="0" ></iframe>
				</td></tr>
			</table>
			</div>
			</div>

         </c:otherwise>
	</c:choose>
	<iframe src="/loading.jsp" id="display_iframe" width="0px;" height="0px" style="display: none;"></iframe>
</body>
</html>