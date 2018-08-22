<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>

<%
String programurls=request.getParameter("programurls")==null?"!":request.getParameter("programurls");
request.setAttribute("programurls",programurls);
String rqurl=request.getParameter("rqurl");
request.setAttribute("rqurl",rqurl);
 %>
<html>
  <head>
    <title>My JSP 'copyprogram.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<script language="javascript" src="/js/vcommon.js"></script>
<script type='text/javascript' src='/dwr/interface/DwrClass.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type="text/javascript">

function oprogramName(){
	   DwrClass.getProgramNameByUrl('move_copy','${programurls}',showProgramName)
}

function showProgramName(programName){
  document.getElementById("proName").innerHTML=programName;
}
setTimeout('oprogramName()',100);

function closedivframe(){
parent.closedivframe(2);
}
function copyprogram(programIds){

	var zuid=document.getElementById("zuid").value;
	if(zuid==""){
		alert("请选择组类别！");
		return;
	}
	
	var programname=document.getElementById("programname").value; 
	
    zuid=zuid.replace(/\s/g,"");
    
	var reg = /^(\w|[\u4E00-\u9FA5])*$/;
	
	if(programname.replace(/\s/g,"")==""){
		alert("请输入节目名称！");
		return;
	}else if(programname.match(reg)==null){ 
		alert("节目名称只允许为下划线、英文、数字和汉字的组合。");    
		document.getElementById("programname").value="";
	    return;
	} else if(zuid==""||zuid=="0"){
		alert("请选择节目组！");
		return;
	}
	programname=encodeURI(encodeURI(programname));
	var requrl=escape('${rqurl}');
	document.getElementById("status").style.display='block';
	window.location.href="/admin/program/copyProgramDO.jsp?programurls="+programIds+"&zuid="+zuid+"&programname="+programname+"&rqurl="+requrl;
}

var saveflag = true;
var fmt_msg = '不允许使用特殊字符(\',\")。';
var fmt_desc_msg = '该说明太长（最多：100 个字母）。';
var fmt_root = '根';
var isclose=0;
var reclick=0;
function popupCategoryWindow(e) {
	if(reclick==0){
		reclick=1;
		var tsrc = "/admin/program/create_program_zu.jsp";
		makeSubLayer('类别树状视图',150,230,e);
		bindSrc('treeviewPopup',tsrc);
	}
}

function selectCategory(group_id,dpath){
	
	var treeviewobj = document.getElementById('divTreeview');
	//treeviewobj.removeNode(true);	//只有IE才有此方法
	SubIFLayer.parentNode.removeChild(SubIFLayer);
	
	document.getElementById("group_id").value= group_id;
	
	document.getElementById("group_id_pth").value = chgRoot(dpath);
	reclick=0;
}
function chgRoot(val){
	val = val.replace("ROOT",fmt_root);
	if(val==null || val=="")
		return '>根';
	else
		return val;
}

function closeIFLayer(){
	if(IFLayer){
		var obj     = parent.document.getElementById('divPopup');
		if(obj!=null && obj!="null"){
			var aobj          = divPopup.document.getElementById('aobj');	
			var ifrm_filelist = divPopup.document.getElementById('ifrm_filelist');	
			if(aobj){
				aobj.src = "";
			}else if(ifrm_filelist){
				ifrm_filelist.src = "";
			}
		}
		//IFLayer.removeNode(true);	//只有IE才有此方法
		IFLayer.parentNode.removeChild(IFLayer);
		//IFLayer = null;
		reclick=0;
	}
}

function onUploadTerminate(){
	var aobj = divPopup.document.getElementById('aobj');
	aobj.document.fm1.Uploader.UploadCancel();
	setTimeout("rloadTerminate()",100);	
	//closeL();
}

function rloadTerminate(){
	var aobj = divPopup.document.getElementById('aobj');
	if(aobj.document.forms[0]){
		var rtn;
		if(aobj.document.forms[0])
		 	rtn = aobj.document.forms[0].Uploader.UploadStatus;
		if(rtn=="Cancel"){
			parent.parent.list_top.goDeleteFtpMetaInfo(aobj.document.forms[0].Uploader.UploadRepositoryUUID)
		}else{
			if(rtn == "Fail"){
				goList();
				closeL();
			}else{
				setTimeout("rloadTerminate()",100);		
			}
		}
	}
}

function closeSubIFLayer(){
	//SubIFLayer.removeNode(true); //只有IE才有此方法
	SubIFLayer.parentNode.removeChild(SubIFLayer);
	reclick=0;
}

var SubIFLayer;

function makeSubLayer(title,w,h,e){ 

	SubIFLayer=document.createElement("div"); 
	SubIFLayer.id = 'divTreeview';
	SubIFLayer.style.cssText="width:"+w+";height:"+h+";z-index:999;position:absolute;top:-4000;left:-4000;border:1px solid #2A5D90;padding:0 0 0 0;color:white;background-color:F5FBFF"; 

	SubIFLayer.innerHTML=""
		+"<table width='100%' border='0'><tr><td width=93% align=center><b>"+title+"</b></td><td align=right width=5%><a href='#SubIFLayer' onclick='closeSubIFLayer()'><img src='/images/icon_popupclose.gif' width='16' height='16' border='0'></td></tr></table>"
		+"";

	with(SubIFLayer.appendChild(document.createElement("iframe"))) 
	{ 
	   id ='treeviewPopup';
	   src='about:blank'; 
	   frameBorder=0; 
	   marginWidth=0;
	   marginHeight=0;
	   marginHeight=0;	   
	   width=w; 
	   height=h-76; 
	   scrolling='yes';
	}//with 

	document.body.appendChild(SubIFLayer); 
	e=e||event; //为了兼容火狐浏览器
	with(SubIFLayer.style){ 
	 left = e.clientX-w-10;
	 top  = 30;//76;
	 width=w; 
	 height=h; 
	};//with 
} 



function closeCheckIFLayer(){
	//CheckIFLayer.removeNode(true);
	CheckIFLayer.parentNode.removeChild(CheckIFLayer);
}

function closeLoginIFLayer(){
	//LoginIFLayer.removeNode(true);
	LoginIFLayer.parentNode.removeChild(LoginIFLayer);
}

function bindSrc(objname,source){

	var obj = document.getElementById(objname);
	obj.src = source;
}
</script>
  </head>
  
  <body>

    <table width="260px" align="center" height="250px">
         <tr>
            <td  height="55" align="right">当前节目名称：</td>
			<td colspan="2" align="left" id="proName"  style="color: green;font-weight: bold;"></td>
		 </tr>
         <tr>
    		<td  height="55"  align="right">&nbsp;节目名称：</td>
    		<td  align="left"">
    		  <input type="text" class="button"  name="programname" id="programname" class="text_input" size="20" height="18"    />
			</td>
    	</tr>
    	<tr>
    		<td  height="55"  align="right">复  制  到：</td>
    		<td><input type="text" class="button"  name="zuname" id="zuname" class="text_input" size="15" height="18"  readonly  />
					<a href="#popup1" onClick="javascript:popupCategoryWindow(event);">
					   <img src="/images/btn_search2.gif"  border="0" align="top" style="margin-top:0px"/>
					    <input type="hidden" name="zuid" id="zuid" /></td>
    	</tr>
    	<tr>
			<td colspan="2" align="center" >
			&nbsp;&nbsp;&nbsp;<input type="button" value=" 复 制 " class="button1" id="saveid" onClick="copyprogram('${programurls}')" />
			&nbsp;&nbsp;&nbsp;<input type="button" value=" 取 消 " class="button1"  onClick="closedivframe()" />
			
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center" >
			   <div id="status" style="display: none"><img src='/images/mid_giallo.gif' align=absmiddle/><font style='font-size:12px;color: green'>&nbsp;正在复制节目，请稍候...</font></div>
			</td>
		</tr>
    </table>
  </body>
</html>
