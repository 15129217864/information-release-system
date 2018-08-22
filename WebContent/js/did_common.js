//get object-XMLHTTP
function getXmlHttpRequest()
{
	 var xhr = false;
	 try
	 {
	    xhr = new XMLHttpRequest();
	 }
	 catch (e)
	 {
	    try{
	       xhr = new ActiveXObject("Msxml2.XMLHTTP");
	    }catch (e){
	       try{
	          xhr = ActiveXObject("Microsoft.XMLHTTP");
	       }catch (e){         
	       }
	    }
	 }
	 return xhr;
}

function lmenu(val){
	parent.menu.location = val;
}

//date formmat -> String
function toStringDate(vDate,formatData){
	var result;
	if(formatData=='yyyy/mm/dd'){
		result = vDate.getYear()+"/"+zeroPlus((vDate.getMonth()+1))+"/"+zeroPlus(vDate.getDate());
	}else if(formatData=='yyyy-mm-dd'){
		result = vDate.getYear()+"-"+zeroPlus((vDate.getMonth()+1))+"-"+zeroPlus(vDate.getDate());
	}else if(formatData=='yyyymmdd'){
		result = vDate.getYear() + zeroPlus((vDate.getMonth()+1)) + zeroPlus(vDate.getDate());			
	}else if(formatData=='yyyy/mm/dd hh:mm:ss'){
		result = vDate.getYear()+"/"+zeroPlus(vDate.getMonth()+1)+"/"+zeroPlus(vDate.getDate())+" "+zeroPlus(vDate.getHours())+":"+zeroPlus(vDate.getMinutes())+":"+zeroPlus(vDate.getSeconds());			
	}else if(formatData=='yyyy-mm-dd hh:mm:ss'){
		result = vDate.getYear()+"-"+zeroPlus(vDate.getMonth()+1)+"-"+zeroPlus(vDate.getDate())+" "+zeroPlus(vDate.getHours())+":"+zeroPlus(vDate.getMinutes())+":"+zeroPlus(vDate.getSeconds());			
	}
	return result;
}

function zeroPlus(v){
	if(v < 10) return "0"+v
	else return v.toString();
}

function addDay(yyyy,mm,dd,pDay) 
{
	var oDate; 
	dd = dd*1 + pDay*1;
	mm--;
	oDate = new Date(yyyy, mm, dd); 

	return oDate;
}

function IsInstalled() {
	try {
	  var prgobj = new ActiveXObject("AXUPLOADER.AxUploaderCtrl.1");
	  if (prgobj != null) return true;
	  else return false;
	} catch(e) {
	  return false;
	}
}

 function cutLen(str,len) {		
         var l = 0;
         for (var i=0; i<str.length; i++) {
                 l += (str.charCodeAt(i) > 128) ? 2 : 1;
                 if (l > len) return str.substring(0,i) + "...";
         }
         return str;
  }
  
  function progressBarOpen(){
  			var cWidth = document.body.clientWidth;
  			var cHeight= document.body.clientHeight;
			var divNode = document.createElement( 'div' );	
			divNode.setAttribute("id", "systemWorking");
			var topPx=(cHeight)*0.4;
			var defaultLeft=(cWidth)*0.4;
			divNode.style.cssText='position:absolute;margin:0;top:'+topPx+'px;left:'+defaultLeft+';width:215;height:59;background-image:url(/images/wait_background.gif);z-index:9999;text-align:center;padding-top:20'; 
			divNode.innerHTML= "<img src='/images/mid_giallo.gif' align=absmiddle><font>&nbsp;Loading...</font>";
			divNode.style.display='none';	
			document.getElementsByTagName("body")[0].appendChild(divNode);   		
  }
  
function progressShow() {
	document.all.systemWorking.style.display = "block";

}   
function progressBarHidden() {
	document.all.systemWorking.style.display = "none";
}  

  function progressBarOpen_pop(){
			var divNode = document.createElement( 'div' );	
			divNode.setAttribute("id", "systemWorking_pop");
			var topPx=(screen.Height)*0.25;
			var defaultLeft=(screen.Width)*0.16;
			divNode.style.cssText='position:absolute;margin:0;top:'+topPx+'px;left:'+defaultLeft+';width:80;height:20;z-index:9999;text-align:center'; 
			divNode.innerHTML= "<img src='/images/mid_giallo.gif' align=absmiddle><font>&nbsp;Loading...</font>";
			divNode.style.display='none';	
			document.getElementsByTagName("body")[0].appendChild(divNode);   		
  }
  
function progressShow_pop() {
	document.all.systemWorking_pop.style.display = "block";

}   
function progressBarHidden_pop() {
	document.all.systemWorking_pop.style.display = "none";
}

function checkTimeNumber(str,pos) { 
	
    var flag   ="false"; 
    var strNum = 0;
    var limval = 23;
    if(pos!=1) limval = 59;
    if (str.length > 0) { 
		strNum = parseInt(str,10);
		if(strNum<0 || strNum>limval) {
			if(strNum==24) return "00";
			return flag;
		}
		flag = zeroPlus(strNum);
    } 
    return flag; 
}

function checkIpNumber(str) { 
	
    var flag   = false; 
    var strNum = 0;
    var limval = 255;

    if (str.length > 0) { 
		strNum = parseInt(str,10);
		if(strNum<0 || strNum>limval) {
			return flag;
		}
		flag = true;
    } 
    return flag; 
}

function MM_preloadImages() { //v3.0
	var d=document; 
	if(d.images){
		if(!d.MM_p) 
			d.MM_p=new Array();
		var i, j = d.MM_p.length, a = MM_preloadImages.arguments; 
		for(i=0; i<a.length; i++)
			if (a[i].indexOf("#")!=0)
		    { 
		    	d.MM_p[j]=new Image; 
		    	d.MM_p[j++].src=a[i];
		    }
	}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function MM_preloadBgImages() { //v3.0
	var d=document; 
	if(d.images){
		if(!d.MM_p) 
			d.MM_p=new Array();
		var i, j = d.MM_p.length, a = MM_preloadBgImages.arguments; 
		for(i=0; i<a.length; i++)
			if (a[i].indexOf("#")!=0)
		    { 
		    	d.MM_p[j]=new Image; 
		    	d.MM_p[j++].background=a[i];
		    }
	}
}

function MM_swapBgImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.background=x.oSrc;
}

function MM_swapBgImage() { //v3.0
  var i,j=0,x,a=MM_swapBgImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.background; x.background=a[i+2];}
}

function MM_swapSpanColorRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.style=x.oSrc;
}

function MM_swapSpanColor(){
  var i,j=0,x,a=MM_swapSpanColor.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.style; x.style=a[i+2];}
}

function autoBlur(){ 
if(event.srcElement.tagName=="A"||event.srcElement.tagName=="/img") 
document.body.focus(); 
} 
document.onfocusin=autoBlur; 

function getEngMonth(month){
	var monthValue = "";
    if(month) monthValue = month;
    else return "";

	var monthName = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");

    if(monthValue.substring(0,1)=="0" && monthValue!="0")
       monthValue = monthValue.substring(1);
    return monthName[(parseInt(monthValue))-1];	
}


 /*  Function Equivalent to java.net.URLEncoder.encode(String, "gbk")
     Copyright (C) 2002, Cresc Corp.
     Version: 1.0
 */
 function encodeURL(str){
     var s0, i, s, u;
     s0 = "";                // encoded str
     for (i = 0; i < str.length; i++){   // scan the source
         s = str.charAt(i);
         u = str.charCodeAt(i);          // get unicode of the char
         if (s == " "){s0 += "+";}       // SP should be converted to "+"
         else {
             if ( u == 0x2a || u == 0x2d || u == 0x2e || u == 0x5f || ((u >= 0x30) && (u <= 0x39)) || ((u >= 0x41) && (u <= 0x5a)) || ((u >= 0x61) && (u <= 0x7a))){       // check for escape
                 s0 = s0 + s;            // don't escape
             }
             else {                  // escape
                 if ((u >= 0x0) && (u <= 0x7f)){     // single byte format
                     s = "0"+u.toString(16);
                     s0 += "%"+ s.substr(s.length-2);
                 }
                 else if (u > 0x1fffff){     // quaternary byte format (extended)
                     s0 += "%" + (oxf0 + ((u & 0x1c0000) >> 18)).toString(16);
                     s0 += "%" + (0x80 + ((u & 0x3f000) >> 12)).toString(16);
                     s0 += "%" + (0x80 + ((u & 0xfc0) >> 6)).toString(16);
                     s0 += "%" + (0x80 + (u & 0x3f)).toString(16);
                 }
                 else if (u > 0x7ff){        // triple byte format
                     s0 += "%" + (0xe0 + ((u & 0xf000) >> 12)).toString(16);
                     s0 += "%" + (0x80 + ((u & 0xfc0) >> 6)).toString(16);
                     s0 += "%" + (0x80 + (u & 0x3f)).toString(16);
                 }
                 else {                      // double byte format
                     s0 += "%" + (0xc0 + ((u & 0x7c0) >> 6)).toString(16);
                     s0 += "%" + (0x80 + (u & 0x3f)).toString(16);
                 }
             }
         }
     }
     return s0;
 }
 
 /*  Function Equivalent to java.net.URLDecoder.decode(String, "gbk")
     Copyright (C) 2002, Cresc Corp.
     Version: 1.0
 */
 function decodeURL(str){
     var s0, i, j, s, ss, u, n, f;
     s0 = "";                // decoded str
     for (i = 0; i < str.length; i++){   // scan the source str
         s = str.charAt(i);
         if (s == "+"){s0 += " ";}       // "+" should be changed to SP
         else {
             if (s != "%"){s0 += s;}     // add an unescaped char
             else{               // escape sequence decoding
                 u = 0;          // unicode of the character
                 f = 1;          // escape flag, zero means end of this sequence
                 while (true) {
                     ss = "";        // local str to parse as int
                         for (j = 0; j < 2; j++ ) {  // get two maximum hex characters for parse
                             sss = str.charAt(++i);
                             if (((sss >= "0") && (sss <= "9")) || ((sss >= "a") && (sss <= "f"))  || ((sss >= "A") && (sss <= "F"))) {
                                 ss += sss;      // if hex, add the hex character
                             } else {--i; break;}    // not a hex char., exit the loop
                         }
                     n = parseInt(ss, 16);           // parse the hex str as byte
                     if (n <= 0x7f){u = n; f = 1;}   // single byte format
                     if ((n >= 0xc0) && (n <= 0xdf)){u = n & 0x1f; f = 2;}   // double byte format
                     if ((n >= 0xe0) && (n <= 0xef)){u = n & 0x0f; f = 3;}   // triple byte format
                     if ((n >= 0xf0) && (n <= 0xf7)){u = n & 0x07; f = 4;}   // quaternary byte format (extended)
                     if ((n >= 0x80) && (n <= 0xbf)){u = (u << 6) + (n & 0x3f); --f;}         // not a first, shift and add 6 lower bits
                     if (f <= 1){break;}         // end of the utf byte sequence
                     if (str.charAt(i + 1) == "%"){ i++ ;}                   // test for the next shift byte
                     else {break;}                   // abnormal, format error
                 }
             s0 += String.fromCharCode(u);           // add the escaped character
             }
         }
     }
     return s0;
 }

function calBytes(str)
{
  var tcount = 0;

  var tmpStr = new String(str);
  var temp = tmpStr.length;

  var onechar;
  for (k=0; k<temp; k++ )
  {
    onechar = tmpStr.charAt(k);
    if (escape(onechar).length > 4)
    {
      tcount += 2;
    }
    else
    {
      tcount += 1;
    }
  }

  return tcount;
}

function toUpper(comp) {
    var strNew = "";
    var str = comp.toString();
    for( i=0 ; i<str.length; i++ ) {
        if( str.charAt(i) >= 'a' && str.charAt(i) <= 'z' )
            strNew = strNew + str.charAt(i).toUpperCase();
        else
            strNew = strNew + str.charAt(i);
    }
    return strNew;
}

function toLower(comp) {
    var strNew = "";
    var str = comp.toString();
    for( i=0 ; i<str.length; i++ ) {
        if( str.charAt(i) >= 'A' && str.charAt(i) <= 'Z' )
            strNew = strNew + str.charAt(i).toLowerCase();
        else
            strNew = strNew + str.charAt(i);
    }
    return strNew;
}

function fc_chk_byte(aro_name,ari_max){
   var ls_str     = aro_name.value; 
   var li_str_len = ls_str.length; 

   var li_max      = ari_max; 
   var i           = 0;
   var li_byte     = 0;
   var li_len      = 0;
   var ls_one_char = "";
   var ls_str2     = "";

   for(i=0; i< li_str_len; i++)
   {
      ls_one_char = ls_str.charAt(i);

      if (escape(ls_one_char).length > 4){
         li_byte += 2;
      }else{
         li_byte++;
      }

      if(li_byte <= li_max){
         li_len = i + 1;
      }
   }
   
   if(li_byte > li_max){
      alert( li_max + " check a length. (limited 100) ");
      ls_str2 = ls_str.substr(0, li_len);
      aro_name.value = ls_str2;
      
   }
   aro_name.focus();   
}

function getMediaType(val){
	var fext = toUpper(val);
	if(fext == "AVI"|| fext == "MPG"|| fext == "WMV"|| fext == "ASF"){
		return "MOVIE";
	}else if(fext == "MP3"|| fext == "WAV"|| fext == "WMA"|| fext == "OGG"){
		return "SOUND";
	}else if(fext == "BMP"|| fext == "JPG"|| fext == "PCX"|| fext == "GIF"|| fext == "PNG"){
		return "IMAGE";
	}else if(fext == "PPT"|| fext == "XLS"|| fext == "DOC"|| fext == "PPTX"|| fext == "XLSX"|| fext == "DOCX"){
		return "OFFICE";
	}else if(fext == "SWF"){
		return "FLASH";
	}else if(fext == "PDF"){
		return "PDF";
	}else if(fext == "LFD"){
		return "LFD";
	}else{
		return "ETC";
	}
}

function getTrimlr(v){
	return v.replace(/^\s+|\s+$/g,"");
}

function setNum(obj){
	 var val=obj.value;
	 var re=/[^0-9]/gi;
	 obj.value=val.replace(re,"");
}

function nvl(input){
	if(input == null || input == "null" )
		return "";
	return input;
}

function nvl(input,replace_str){
	if(input == null || input == "null")
		return replace_str;
	return input;
}

//RGB값을 BGR로 변경하여 반환한다.
function replaceRGB(color_val){
	var ret_color = "";
	ret_color = color_val.substr(4,2) + color_val.substr(2,2) + color_val.substr(0,2);
	return ret_color;
}

var dir0 = new Array( "CutBlackFade","CutBlueFade","CutGrayFade","CutGreenFade","CutOrangeFade","CutPurpleFade","CutRedFade","CutWhiteFade","CutYellowFade","PinWheel",
"QuadrantIn","Blocks","Center","Enigma","EnigmaReverse","Quadrant","RandomBlocks","SpinningFan","SpiralIn","SquareDance",
"Swiss","Turnaround","Ccccut","Cut","Fluorescent","Quake","BouncingIris","CenterZoom","Contraction","CornerFlyOn",
"CrossCheck","Implosion","InYoFace","Kyoto","Memory","MirrorMaze","MirrorMazeRing","Presenting","PsychoStretch","QuatroFlipCoin",
"RandomGrowth","Recollection","Snails","Swarm","TotalRecall","WhiteHole","WhiteHoleLarge","ZoomFade","ZoomUp")

var dir4 = new Array( "ArcWiper","Canvas","Erosion","Floodlights","Fractured","MandelBrot","RippleWash","SlidingStar","Smokescreen","TheBlob",
"Vortex","WarmFronts","WetWiper","Clasp","ClaspSmear","CurtainDown","DoubleRollOn","DoubleSlam","DoubleVision","PushMe",
"PushMoreStrips","PushMoreWave","PushStrips","RollOn","ScaleIn","ScatterIn","ScrollClosed","ScrollDivide","ScrollIn","Slam",
"SlatsClosed8","SlatsCrash","SlatsTogether2","SlatsTogether4","Slicer","SplitSweep","Sweep","SweepSlicer","Trislide","TrislideLR",
"UnrollStripts","BasketWeave","BlindsFantasy1","BlindsFantasy2","BlindsFantasy3","ClosingBlinds","Closure","CornerSlice","Cornor","Curtain",
"DiagonalBlocks","DiagonalStrips","MiniQuadrant","PaintDrip","Peel","PremiereReveal","ScaleTheWall","SmallBlinds","SmoothWall","SpiralBlock",
"SplineWave","Split","SplitBlinds","Stacker","Striper","Stripes","SuperStripper","TimeSlice","WideBlinds","Wipe",
"XmasTree","ZoomSpiral","ArcIn","BouncingBlinds","CornerStretch","CornerZoom","CornerZoom","DualZoom","FlipFour","GlideIn",
"MiddleStretch","MultiScale","Premiere","PremiereEdges","QuarterBounceOn","QuatroFlipCoin","SlideStretch","SlimZoom","Stretch","StripFoldOut",
"SwimIn","Troika")

var dir8 = new Array( "Rub","NewImproved","OurSponsor")

function chk_direction(e_name){
	var flag = "true";
	var rtn  = "0";
	for(i=0;i<dir4.length;i++){
		if(dir4[i] == e_name){
			flag = "false";
			rtn  = "4";
			break;
		}
	}
	if(flag=="true"){
		for(i=0;i<dir8.length;i++){
			if(dir8[i] == e_name){
				rtn  = "8";
				break;
			}
		}		
	}
	return rtn;
}

function checkkeycode(t,msg){

	//특수문자 입력제한 UnderBar(_)와 Dash(-)는 허용
	if((t.keyCode > 32 && t.keyCode < 45)|| (t.keyCode > 45 && t.keyCode < 48) || (t.keyCode > 57 && t.keyCode < 65)||(t.keyCode > 90 && t.keyCode < 95)||(t.keyCode == 96) || (t.keyCode >= 123 && t.keyCode <= 126)){
	   t.returnValue = false;
   		alert(msg);
  	}
  	return true;
 }
 
 function checkDirectoryCode(t,msg){

	//특수문자 입력제한 UnderBar(_), Dash(-), (:), (\), (.) 허용
	if((t.keyCode > 32 && t.keyCode < 45)|| (t.keyCode == 47 ) || (t.keyCode > 58 && t.keyCode < 65)||(t.keyCode == 91)|| (t.keyCode > 92 && t.keyCode < 95) ||(t.keyCode == 96 )|| (t.keyCode >= 123 && t.keyCode <= 126) ){
	   t.returnValue = false;
   		alert(msg);
  	}
  	return true;
 }
 
 function checkURLCode(t,msg){

	//특수문자 입력제한 UnderBar(_), Dash(-), (:), (\), (/) 허용
	if((t.keyCode > 32 && t.keyCode < 45)|| (t.keyCode > 58 && t.keyCode < 65)||(t.keyCode == 91)|| (t.keyCode > 92 && t.keyCode < 95) ||(t.keyCode == 96 )|| (t.keyCode >= 123 && t.keyCode <= 126) ){
	   t.returnValue = false;
   		alert(msg);
  	}
  	return true;
 } 

  function checkIPCode(t,msg){
	//숫자입력 및 (.) 허용
	if((t.keyCode >= 48 && t.keyCode <= 57) || (t.keyCode == 46) ){
	   
  	}else{
  		t.returnValue = false;
  		alert(msg);
  	}
  	return true;
 } 
 
  function checkSpecialChar(t,msg){
    //특수문자 입력제한 ('), (") 만 제외하고 모든 문자 허용
 	if(t.keyCode == 39 || t.keyCode == 34){
	    t.returnValue = false;
   		alert(msg);
  	}
  	return true;
 } 

  function validateMIString(strText){
    //문자열에 특수문자  ('), (") 가 포함되어 있는지 체크함 포함되어 있으면 FALSE.
	if(strText.indexOf("'") != -1 || strText.indexOf("\"") != -1) {
	    return false;
  	}
  	return true;
 } 
 
 function checkNumber(t,msg){

    //숫자만  허용
	if(t.keyCode >= 48 && t.keyCode <= 57 ){
	   
  	}else{
  		t.returnValue = false;
  		alert(msg);
  	}  	
  	return true;
  } 
  
 function checkNumberNoMsg(t){

    //숫자만  허용
	if(t.keyCode >= 48 && t.keyCode <= 57 ){
	   
  	}else{
  		t.returnValue = false;
  	}  	
  	return true;
  } 
 
 function checkTextLength(t, t_max, msg){
 	var t_str = t.value;
 	var t_str_len = t_str.length;
	
 	var l_max = t_max;
 	var i = 0;
 	var l_byte = 0;
 	var l_len = 0;
 	var l_one_char = "";
 	var l_str2 = "";
 	
 	for(i=0; i< t_str_len;i++)
 	{
 		//한글자 추출
 		l_one_char = t_str.charAt(i);
 		
 		//한글이면 2를 더한다.
 		if(escape(l_one_char).length > 4)
 		{
	 		l_byte += 2;
 		}
 		
 		//그밖의 경우는 1을 더한다.
 		else
 		{
 			l_byte++;
 		}
 		
 		//전체 크기가 l_max를 넘지 않으면
 		if(l_byte <= l_max)
 		{
 			l_len = i + 1;
 		}
 	}
 	
 	//전체 길이를 초과하면
 	if(l_byte > l_max )
 	{
 		alert(msg);
 		l_str2 = t_str.substr(0, l_len);
 		t.value = l_str2;
 	}
 	
 	t.focus();
 }

 function goCategory(url,groupId){
 	var ltype = parent.content.list_body.document.fm1.ltype.value;
 	parent.content.location = url+"/cms/cmslist.do?jtype=INIT&stype=CATEGORY&ltype="+ltype+"&group_id="+groupId;
 } 
 
 