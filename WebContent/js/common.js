<!--

var SpaceEditorProInitialized = false;

function goSelect(){
    DeviceSNListActionForm.submit();
}


function isBlank(str)
{
    if ( str.replace(/ /gi,"") == "" ) return true ;
    else return false ;
}

function clickshow(num) {
    for (i=1;i<=3;i++) {
        menu=eval("document.all.block"+i+".style");
        line=eval("document.all.line"+i+".style");
        if (num==i){
            if(menu.display=="block"){
                menu.display="none";
                line.display="none";
            }
            else {
                menu.display="block";
                line.display="block";
            }
        }
        else {
            menu.display="none";
            line.display="none";
        }
    }
}

function clickEditRadio(index) {
	if (index == 1) {
		if(confirm(Lang.MoveToTextArea)) {
			document.all.editSpan1.style.display = "block";
			document.all.editSpan2.style.display = "none";
			if (SpaceEditorProInitialized) {
				document.forms[0].content.value = SpaceEditor.GetBodyText();
			}
		} else {
			document.forms[0].EditRadio[1].checked = true;
		}
	} else {
		document.all.editSpan1.style.display = "none";
		document.all.editSpan2.style.display = "block";
		if (SpaceEditorProInitialized) {
			// 웹에디터가 초기화 되고  "편집기 입력"을 두번째 클릭했을 때 수행되는 부분
			// 즉, "편집기 입력 -> 텍스트 입력 -> 편집기 입력"으로 선택했을 경우에는 OnInitDocument() 이벤트가
			// 발생하지 않으므로 여기에서 setTimeout() 함수를 사용하여 SpaceEditorPro 객체가 만들어지고 0.5초후에
			// textarea에 있던 값을 웹에디터에 set한다.
			var contents = document.forms[0].content.value;
			document.forms[0].content.value = contents.replaceAll("\n", "<BR/>");
			SpaceEditor.SetDocumentEndcodeHTML("");
			setTimeout("SpaceEditor.SetDocumentEndcodeHTML(document.forms[0].content.value)", 500);
		}
	}
}

function submitContent(host, port, upload_page) {

	// 첨부 파일 관련 select 객체가 있는경우
	if (document.forms[0].attachList) {
		document.forms[0].attachList.options[0] = null;	// 0번째에 설명하는 option은 제거
	
		for (var i=0; i < document.forms[0].attachList.length; i++) { 	// 0번째 skip
			document.forms[0].attachList.options[i].selected = true;
		}
	}

	if (document.forms[0].EditRadio[1].checked == true) {
		// 웹 에디터를 사용하는 경우
		document.forms[0].content.value = SpaceEditor.GetDocumentHTML();

		SpaceEditor.ClearParam();
		SpaceEditor.SetHostInfo( host, port, upload_page, document.cookie );
		SpaceEditor.FormSubmit( document.forms[0] );
		
	} else {
		document.forms[0].target = upload_page;
	
		// textarea를 사용하는 경우
		document.forms[0].submit();
	}
}

function loadContent(content) {
	if (content.length > 5 && content.ltrim().substring(0, 5) == "<HTML") {
		document.all.editSpan1.style.display = "none";
		document.all.editSpan2.style.display = "block";
		document.forms[0].EditRadio[1].checked = true;

		// 값 입력은 OnInitDocument()에서 함
	} else {
		document.forms[0].content.value = content;
	}
}

function resetInput() {
//	document.all.editSpan1.style.display = "block";
//	document.all.editSpan2.style.display = "none";
	document.forms[0].reset();
}

String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
}
String.prototype.ltrim = function() {
	return this.replace(/^\s+/,"");
}
String.prototype.rtrim = function() {
	return this.replace(/\s+$/,"");
}


// 웹에디터가 처음 초기화될 때 호출된다. 
// 즉, 두번째 "편집기 입력"을 선택했을 경우에는 호출되지 않는다.
function OnInitDocument() {
	SpaceEditorProInitialized = true;
	var contents = document.forms[0].content.value;
	SpaceEditor.SetDocumentEndcodeHTML(contents.replaceAll("\n", "<BR/>"));
}

// 문자열을 한번 바꾼다.
function replace(msrc,sstr,rstr) {
       var idx,sleft,sright;

       msrc+="";
       sstr+="";
       rstr+="";
       idx=msrc.indexOf(sstr);
       if (idx > -1) {
              sleft = msrc.substring(0,idx) + rstr;
              sright = msrc.substring(idx+sstr.length);
              return sleft + replace(sright,sstr,rstr);
       } else {
              return msrc;
       }
}

// 스트링의 str1에 해당하는 문자열을 str2로 바꾼다.
// 사용법.
// var str = 문자열.replaceAll("a", "1");  <-- 문자열 내에서 'a'를 찾아 전부 '1'로 바꾸라는 의미
String.prototype.replaceAll = function(str1, str2)
{
  var temp_str = "";

  if (this.trim() != "" && str1 != str2)
  {
    temp_str = this.trim();

    while (temp_str.indexOf(str1) > -1)
    {
      temp_str = temp_str.replace(str1, str2);
    }
  }

  return temp_str;
}

//--
