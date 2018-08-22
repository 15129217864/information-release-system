<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<jsp:directive.page import="com.xct.cms.xy.dao.ManagerProjectDao"/>
<jsp:directive.page import="com.xct.cms.xy.domain.ClientProjectBean"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String jmid=request.getParameter("jmid")==null?"no":request.getParameter("jmid");//点击删除按钮后得到值
ManagerProjectDao managerprojectdao=new ManagerProjectDao();
if(!"no".equals(jmid))
  managerprojectdao.deleteClientProject(Integer.parseInt(jmid)); //没做ftp文件配置删除


List<ClientProjectBean> clientprojectlist=managerprojectdao.getForEverLoopProject();
request.setAttribute("clientprojectlist",clientprojectlist);


%> 
<html>
  <head>
    <title>My JSP 'viewforeverproject.jsp' starting page</title>
    <script language="javascript" src="/js/vcommon.js"></script>
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
		    /* 提示div的样式 */
			#suggest {
			    width:100px;
			    border:1px solid black;
			    font-size:14px;
			}
			
			/* 提示信息鼠标覆盖时信息 */
			div.over {
			    border:1px solid #999;
			    background:#FFFFCC;
			    cursor:hand;
			}
			
			/* 提示信息鼠标移出时信息 */
			div.out {
			    border: 1px solid #FFFFFF;
			    background:#FFFFFF;
			}
     </style>
      <link rel="stylesheet" href="/css/style.css" type="text/css">
      <script type="text/javascript">
        function deleteforeverjm(jmid){
			if(confirm("提示信息：确认删除该节目？")){
			   myform.action="/admin/schedule/viewforeverproject.jsp?jmid="+jmid;
			   myform.submit();
			}
		}
      </script>
  </head>
  
  <body>
 	 <div>
 	 <form id="project" name="myform" method="post" action="">
		  <table border="0"width="100%" cellpadding="0" cellspacing="0" >
		      <tr align="center" class="TitleBackground">
		       <td>终端名称</td>
		       <td>终端IP</td>
		       <td>节目名称</td>
		       <td>播放类型</td>
		       <td>播放时长</tdf>
		       <c:if test="${not empty lg_authority}">
					<c:set var="str" value="${lg_authority}"/>
						<c:if test="${fn:contains(str,'U')}">
		       			    <td>操作</td>
		           	    </c:if>
		        </c:if>
		     </tr>
		     <c:choose>
		           <c:when test="${clientprojectlist!=null&&!empty clientprojectlist}">
			            <c:forEach items="${clientprojectlist}" var="clientprojectlist">
						       <tr align="center" >
						          <td height="30px;">${clientprojectlist.playclient }</td>
						          <td>${clientprojectlist.clientip }</td>
						          <td>${clientprojectlist.name }</td>
						          <td>${clientprojectlist.isloop }</td>
						          <td>${clientprojectlist.playsecond }</td>
						          
						          <c:if test="${not empty lg_authority}">
									   <c:set var="str" value="${lg_authority}"/>
									   <c:if test="${fn:contains(str,'U')}">
							          		<td><input type="button" value="删除" class="button"  onclick="deleteforeverjm('${clientprojectlist.jmid }')"/></td>
							          	</c:if>
		        	              </c:if>
						          
						        </tr>
						        <tr><td class="Line_01" colspan="8"></td></tr>
				          </c:forEach>
			       </c:when>
			       <c:otherwise>
				     <tr><font color="bule">抱歉，没找到您查询的数据！</font></tr>
		           </c:otherwise>
		        </c:choose>
		   </table>
		   </form>
	   </div>
  </body>
</html>
