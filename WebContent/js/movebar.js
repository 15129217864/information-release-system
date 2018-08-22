var firstIframe; //상위 iframe
var secondIframe; //하위 iframe
var minHeight = "0"; //최소 height값
var mouseDown;
function makeMoveBar(){
	objectBar = document.createElement("div"); 
	objectBar.id = "movebar";
	objectBar.style.cssText="position:absolute;border:solid 0px #0000;cursor:n-resize;background-image:URL('../images/bar_line_bg.gif');width:100%;height:11px"; 
	objectBar.className="movebar";
	objectBar.align="center";
	//objectBar.innerHTML="<img src=\"../images/bar_line.gif\" />";
	objectBar.onmousedown=function(){ dragBegin(event) };
	document.body.appendChild(objectBar); 
}

function showBar(bartop){	
	objectBar.style.top=bartop;
	objectBar.style.display="";	
}

//iframe scroll Yes or No
function scrollYN(tar,Cval){
	var o = document.getElementById(tar);
	var frameBody = o.contentWindow.document.body;
    var Hval      = frameBody.scrollHeight + (frameBody.offsetHeight-frameBody.clientHeight);	
	if(Hval > Cval){
		frameBody.scroll = "yes";
	}else{
		frameBody.scroll = "no";	
	}
}

//mouse down
function dragBegin(event) {	
	var element = document.getElementById('movebar'); 	//movebar
	element.dragging = true		
	element.diffy = event.y - element.offsetTop
	
	mouseDown = true; 	
}


//mouse move   //event, target iframe, calculate y point
function drag(event,tar,caly) {
	//move bar
	var element = document.getElementById('movebar');
	if (!element.dragging) return;	

    var moveTop =0;
	if(tar != null){ //iframe event
		 var elTar = document.getElementById(tar);
		 if(caly==null)return;
		 moveTop = (elTar.offsetTop + caly) - element.diffy	
	}else{
		 moveTop = event.y - element.diffy
	}	
	
	var moveDiff = parseInt(element.style.top)-moveTop;	 //move y
	
    var elementIf = document.getElementById(firstIframe);
    var elementVIf = document.getElementById(secondIframe); 
	var ifCval = parseInt(elementIf.style.height) -  moveDiff;
	var ifVCval = parseInt(elementVIf.style.height) + moveDiff;
	
    if(ifCval < minHeight){
    	return;
    	
    }else if(ifVCval < minHeight){
		return;
    }else{
    	element.style.top = moveTop;             
    }	
	
	
    scrollYN(firstIframe,ifCval);	             
    scrollYN(secondIframe,ifVCval);
    
    //iframe1        
    elementIf.style.height= ifCval;
    //iframe2      
    elementVIf.style.height= ifVCval;
}

//mouse up
function dragEnd() {
	//alert("end");
	var element = document.getElementById('movebar');
	element.dragging = false;
}