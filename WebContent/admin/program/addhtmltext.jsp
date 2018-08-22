<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:directive.page import="com.xct.cms.dao.ModuleDAO" />
<jsp:directive.page import="com.xct.cms.domin.Module" />
<jsp:directive.page import="com.xct.cms.db.DBConnection"/>
<jsp:directive.page import="java.sql.Connection"/>
<%@page import="com.xct.cms.utils.CKEditorConfigUtil"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    if(null==request.getAttribute("ophtmltext")){
		String moduleid = request.getParameter("moduleid") == null ? "0": request.getParameter("moduleid");
		DBConnection dbconn= new DBConnection();
		Connection conn=dbconn.getConection();
	    ModuleDAO moduledao = new ModuleDAO();
		Module module = moduledao.getModuleTempByModuleid(conn,Integer.parseInt(moduleid));
	    request.setAttribute("module",module);
	    request.setAttribute("moduleid",moduleid);
	    
	    request.setAttribute("foreground",module.getForeground());//暂定foreground 为是否为静态文本
	    
	    request.setAttribute("htmltextbackimg",request.getParameter("htmltextbackimg"));
	    String htmltextbg=request.getParameter("htmltextbg");
	    request.setAttribute("htmltextbg",htmltextbg);
	    
	    request.setAttribute("ckedtorconfig",new CKEditorConfigUtil().getCKEditorConfig());
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addhtmltext.jsp' starting page</title>
    
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
		<script type="text/javascript" src="/js/ckeditor/ckeditor.js"></script>
		
		<style type="text/css">
			#colorDiv{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none }
			#colorMessage{border:#6699cc solid; border-width:1px; background:#fff; color:#036; font-size:12px; line-height:100%;  display: none}
			#divframe{ position:absolute; z-index: 999; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none }
			#smallImgDiv{ position:absolute; z-index: 1; filter:dropshadow(color=#666666,offx=3,offy=3,positive=2); display: none }
			#smallImgMassage{border:#6699cc solid; border-width:1px; background:#fff; color:#036; font-size:12px; line-height:100%;  display: none}
			.header{background:url(/images/device/btn_background.gif);  font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff}
			.media_llist_ul{ list-style: none; padding-left: 0px;margin-left: 0px;}
			.media_llist_ul li{ height: 25px; line-height: 25px; width:100%; padding: 0; list-style: none;cursor: move; border-bottom:  1px solid #c6ddf1}
			.li_div1{float: left; width:300px;}
			.li_div2{float: left; width: 150px;}
			.li_div3{float: left; width: 150px;}
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
		$(function () {
		    //CKEDITOR.replace('htmltextContent', { height: '240px', width: '552px' });
		 })
	     function saveHtmlText(){
	     
	       	var isupscrolltext;
	        
	        var chkObjs = document.getElementsByName("isupscrolltext");
             for(var i=0;i<chkObjs.length;i++){
                 if(chkObjs[i].checked){
                    isupscrolltext=chkObjs[i].value;
                 }
             }
	        var htmltext=CKEDITOR.instances.htmltextContent.getData();
	        htmltext=htmltext.replace(/\s/ig,'');
	        //alert(htmltext);
	      
			if(htmltext==""){
				alert("文本不能为空！");
				return;
			}
	        htmltextForm.action="/rq/addhtmltext?moduleid=${moduleid}&htmltextbg=${htmltextbg}&isupscrolltext="+isupscrolltext;
	        htmltextForm.submit();
	     }
	     
         function jumpback(){
           
           <c:choose>
	           <c:when test="${ophtmltext eq 'save_ok'}">
	               
	                parent.parent.div_iframe2.addmediaframe.location.href="/admin/program/addmediaList.jsp?moduleid=${moduleid}&save_state=save_ok";
	                parent.parent.closedivframe3();
	           </c:when>
	           <c:otherwise>;</c:otherwise>
           </c:choose>
         }
         window.onload=jumpback;
	</script>

  </head>
  <style>
  *{margin:0;padding:0}
  </style>
  <body>
  	<form name="htmltextForm" method="post" action="" >

	    <table width="1024" height="530" align="center" border="0" bgcolor="#69a3cd" >
			<tr bgcolor="#F5F9FD">
				<td align="center"  height="40">
				    <input type="radio" name="isupscrolltext" value="1" ${(foreground eq '1'||empty foreground )?'checked':'' }/>超过高度自动滚动
					&nbsp;&nbsp;<input type="radio" name="isupscrolltext" value="0" ${foreground eq '0'?'checked':'' }/>静态文本&nbsp;&nbsp;
					<input type="button" value=" 保 存 " onclick="saveHtmlText();" class="button1"/>
				</td>
			</tr>
			<tr bgcolor="#F5F9FD">
				<td align="center" colspan="2" >
					<textarea cols="80" id="htmltextContent" name="htmltextContent" rows="10">${module.m_text}</textarea>
					<ckeditor:replace replace="htmltextContent" basePath="ckeditor/"  config="${ckedtorconfig}" />
				 </td>
			</tr>
	    </table>
	</form>
		
  </body>
</html>
