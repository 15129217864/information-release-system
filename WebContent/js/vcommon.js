var Obj=''
document.onmouseup=MUp
document.onmousemove=MMove
function MDown(Object){
Obj=Object.id
document.getElementById(Obj).setCapture()
pX=event.x-document.getElementById(Obj).style.pixelLeft;
pY=event.y-document.getElementById(Obj).style.pixelTop;
}
function MMove(){
if(Obj!=''){
	document.getElementById(Obj).style.left=event.x-pX;
	if(event.y-pY<0){
		  document.getElementById(Obj).style.top=0;
	}else{
		  document.getElementById(Obj).style.top=event.y-pY;
	 }
  }
}
function MUp(){
if(Obj!=''){
  document.getElementById(Obj).releaseCapture();
  Obj='';
  }
}
document.onkeydown = function(){
   if(event.keyCode==116) {
   		if(confirm("刷新页面将退出系统！确认退出？")){
    		parent.parent.parent.parent.parent.parent.parent.location.href="/logout.jsp";
    	}else{
   	event.keyCode=0;
    	event.returnValue = false;
    	}
    }
}
//document.oncontextmenu = function() {event.returnValue = false;}
