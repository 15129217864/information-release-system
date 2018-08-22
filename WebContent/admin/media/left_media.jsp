<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<html>
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma"  content="no-cache"/> 
<head>
<title></title><script language="javascript" src="/js/vcommon.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<style>
body {
	background-image: url(/images/menu_bg.gif);
}
</style>
<script language="JavaScript" src="/script/did_common.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function goMediaList(val){
	var obj  = parent.parent.content;
	var tsrc = "/admin/media/index.jsp?left_menu=MEDIA&title=MEDIA&zu_id=&type="+val;
	obj.location = tsrc;
}
//-->
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<br>
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="30" height="30"><img src="/images/dtreeimg/MOVIE.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="javascript:;" onclick="goMediaList('MOVIE');" />
      	视频&nbsp;(${movie})
      	</a>
      </td>      				      
    </tr>
     <tr>
      <td width="30" height="30"><img src="/images/dtreeimg/FLASH.gif" width="19" height="19" /></td>
      <td>
      	<a href="javascript:;" onclick="goMediaList('FLASH');" />
      	FLASH&nbsp;(${flash})
      	</a>
      </td>      				      
    </tr>
    <tr>
      <td width="30" height="30"><img src="/images/dtreeimg/IMAGE.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="javascript:;" onclick="goMediaList('IMAGE');" />
      	图像&nbsp;(${image})
      	</a>
      </td>      				      
    </tr>
    <tr>
      <td width="30" height="30"><img src="/images/dtreeimg/SOUND.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="javascript:;" onclick="goMediaList('SOUND');" />
      	声音&nbsp;(${sound})
      	</a>
      </td>       				      
    </tr>
    <tr>
      <td width="30" height="30"><img src="/images/dtreeimg/PPT.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="javascript:;" onclick="goMediaList('PPT');" />
      	PPT&nbsp;(${ppt})
      	</a>
      </td>      					      
    </tr>
    <tr>
      <td width="30" height="30"><img src="/images/dtreeimg/WORD.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="#WORD" onclick="goMediaList('WORD');" />
      	WORD&nbsp;(${word})
      	</a>
      </td>      					      
    </tr>
    <tr>
      <td width="30" height="30"><img src="/images/dtreeimg/EXCEL.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="javascript:;" onclick="goMediaList('EXCEL');" />
      	EXCEL&nbsp;(${excel})
      	</a>
      </td>        			      
    </tr> 
     <tr>
      <td width="30" height="30"><img src="/images/dtreeimg/TEXT.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="#TEXT" onclick="goMediaList('TEXT');" />
      	文本&nbsp;(${text})
      	</a>
      </td>        			      
    </tr>    
     <tr>
      <td width="30" height="30"><img src="/images/dtreeimg/WEB.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="javascript:;" onclick="goMediaList('WEB');" />
      	网页&nbsp;(${web})
      	</a>
      </td>        			      
    </tr>       
    <tr>
      <td width="30" height="30"><img src="/images/dtreeimg/OTHER.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="javascript:;" onclick="goMediaList('OTHER');" />
      	其他&nbsp;(${other})
      	</a>
      </td>       				      
    </tr>    

</table>
</body>
</html>
