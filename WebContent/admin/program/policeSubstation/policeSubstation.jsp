<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'policeSubstation.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
		td{
			border:1px solid #CCC;
			font-size:20px;
		}
		#biaoge td input{ width:90% }
	</style>
<script src="/js/jquery1.4.2.js" type="text/javascript"></script>
<script type="text/javascript">
//ʵ�ֱ��ɱ༭
	   $(document).ready(function(){
			//ȡ�����еĵ�Ԫ��,����һ�еĲ˵�����
			var tds=$("tr:gt(0) td");
			//alert(tds.length);
			//��ÿ����Ԫ��ע��һ�������¼�
			tds.next().click(tdClick);
	   });
	
	   function tdClick(){
			if($(this).children().attr("type")!="button"){
			   //�õ��õ�Ԫ��Ķ���
			   var td=$(this);
				//ȡ���ĵ�Ԫ�������
				var tdText=td.text();
				//alert(tdText);	
				//��ոĵ�Ԫ������
				td.empty();//����td.html("");Ҳ����
				//����һ��input�ı���
				var input=$("<input>");
				//��ԭ����ֵ��ֵ��input�ı���
				input.attr("value",tdText).attr("type","text");
				//���ı���ע��һ��keyup�¼�
				input.keyup(function(event){
					 var myevent=event||window.event;
					//�ж��Ƿ��ǻس�������
					if(myevent.keyCode==13){
						//����ǰ�������Ϣ��������
						var inputnode=$(this);
						//�ı����ֵ
						var inputText=inputnode.val();
						//��ո�td�е�����
						var mytd=inputnode.parent();
						mytd.empty();
						//���ı���ֵ����td
						mytd.html(inputText);	
						//��td����ӵ�е����¼�
						mytd.click(tdClick);
					}
				});
				input.blur(function(){
					var mytd=$(this).parent();
					var inputText=$(this).val();
					mytd.empty();
					mytd.html(inputText);
					mytd.click(tdClick);
				});
				//���ı���׷�Ӹ���Ԫ��
				td.append(input);
				//�ı������ѡ��
				var inputdom=input.get(0);
				inputdom.select();
				
				//td.html(input.val());
				//һ���õ�Ԫ��ĵ����¼�
				td.unbind("click");
		   }else{
		        var textarray=$(this).prevAll();//���ҵ�ǰԪ��֮ǰ���е�ͬ��Ԫ��
				for(var i=0,n=textarray.length;i<n;i++){
				  if(i!=n-1){
				    $(textarray[i]).empty();
				  }
				}
		   }
	  }
      function getAll(){
      
       if($("#clientip").val()=="0"){
         alert("��ѡ���նˣ�");
         return ;
       }
	     var tds=$("tr:gt(0) td");
		 var stingbuffer="";
		 var temp=6;
		 var tempstring="";
	     for(var i=0,n=tds.length;i<n;i++){//6,12,18,24
		   if(i==0||i%temp!=0){
		     tempstring=$(tds[i]).text().replace(/\s/g,'');
		     stingbuffer+=tempstring==""?"no!":encodeURI(encodeURI(tempstring))+"!";
		   }else{
		     stingbuffer=stingbuffer.substring(0,stingbuffer.length-1)+"@"
		     temp=temp+7;
		   }
		 }
		// alert(stingbuffer.substring(0,stingbuffer.length-1));
		 ajaxComm("op=send&clientip="+$("#clientip").val()+"&oparams="+stingbuffer.substring(0,stingbuffer.length-1));
	  }
	   
	  function ajaxComm(pram,url){
	     //alert(pram);
		 $.ajax({
			    type: "POST",
			    url: "<%=basePath%>rq/sendpolicesubstation",
			    data: pram,
			    dataType:"text",
			    success: parseData,
			    error:function(){
			            alert("����ʧ�ܣ�"); //������ˣ����߷������
			          },
			    complete: function (XHR, TS) { XHR = null } //�ͷ�ajax��Դ
		 });
	}
	
	function parseData(data){
	   //alert(data);
	   status(data)
	}
	function status(data){
	  $("#status").html(data)
	  setTimeout("$('#status').html('')",1000);
	}
	function  changeip(){
	  var parames="op=list&clientip="+$("#clientip").val();
	  window.location.href="<%=basePath%>rq/sendpolicesubstation?"+parames;
	}
	
</script>
</head>
<body>
<input type="button" value="����" onclick="getAll()"/>
<select id="clientip" name="clientip" onchange="changeip()">
  <option value="0">==��ѡ���ն�==</option>
  <c:forEach var="clientbean" items="${clientlist}">
      <option value="${clientbean.key }" ${clientiptemp==clientbean.key?'selected':''}>${clientbean.value }</option>
  </c:forEach>
</select><span id="status" style="color: red"></span>
<table id="biaoge"  width="1000" style="text-align:center; width:1000px; border:1px solid #CCC; border-collapse:collapse;">
	<tr id="titles" style="background-color:#92D4EE">
	    <td width="60"><b>���</b></td>
    	<td width="200"><b>Ѷ����</b></td>
        <td width="120"><b>�참����</b></td>
        <td width="120"><b>��Ա���</b></td>
        <td width="350"><b>��������</b></td>
        <td width="300"><b>����������</b></td>
		<td width="100"><b>����</b></td>
    </tr>
      <c:if test="${empty policesubstationlist}">
	     <tr>
		    <td colspan="7" style="font-size: 30px;color: red"><b>====��ѡ�������б��ڵ��ն˽��в���====</b></td>
	     </tr>
     </c:if>
    <c:forEach var="policesubstation" items="${policesubstationlist}">
       <tr>
	        <td>${policesubstation.seqno}</td>
	    	<td>${policesubstation.roomno}</td>
	        <td>${policesubstation.policename}</td>
	        <td>${policesubstation.casecode}</td>
	        <td>${policesubstation.casecharacter}</td>
	        <td>${policesubstation.suspect}</td>  
			<td><input type="button" value="���"></td>
       </tr>
    </c:forEach>
 
</table>
<br />
<br />
<br />
<br />
<br />
</body>
</html>
