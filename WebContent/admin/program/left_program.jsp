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
      <td width="15"><img src="/images/dtreeimg/icon_movie.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="#MOVIE" onclick="goMediaList('MOVIE');" />
      	 ”∆µ(${movie})
      	</a>
      </td>      				      
    </tr>
    <tr>
      <td width="15"><img src="/images/dtreeimg/icon_image.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="#IMAGE" onclick="goMediaList('IMAGE');" />
      	ÕºœÒ(${image})
      	</a>
      </td>      				      
    </tr>
    <tr>
      <td width="15"><img src="/images/dtreeimg/icon_sound.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="#SOUND" onclick="goMediaList('SOUND');" />
      	…˘“Ù(${sound})
      	</a>
      </td>       				      
    </tr>
    <tr>
      <td width="15"><img src="/images/dtreeimg/icon_office.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="#OFFICE" onclick="goMediaList('OFFICE');" />
      	OFFICE(${office})
      	</a>
      </td>      					      
    </tr>
    <tr>
      <td width="15"><img src="/images/dtreeimg/icon_flash.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="#FLASH" onclick="goMediaList('FLASH');" />
      	FLASH(${flash})
      	</a>
      </td>        			      
    </tr>    
    <tr>
      <td width="15"><img src="/images/dtreeimg/icon_etc.gif" width="19" height="19" />&nbsp;</td>
      <td>
      	<a href="#ETC" onclick="goMediaList('ETC');" />
      	∆‰À˚(${etc})
      	</a>
      </td>       				      
    </tr>    

</table>
</body>
</html>
