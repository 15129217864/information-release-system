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
			// �������Ͱ� �ʱ�ȭ �ǰ�  "������ �Է�"�� �ι�° Ŭ������ �� ����Ǵ� �κ�
			// ��, "������ �Է� -> �ؽ�Ʈ �Է� -> ������ �Է�"���� �������� ��쿡�� OnInitDocument() �̺�Ʈ��
			// �߻����� �����Ƿ� ���⿡�� setTimeout() �Լ��� ����Ͽ� SpaceEditorPro ��ü�� ��������� 0.5���Ŀ�
			// textarea�� �ִ� ���� �������Ϳ� set�Ѵ�.
			var contents = document.forms[0].content.value;
			document.forms[0].content.value = contents.replaceAll("\n", "<BR/>");
			SpaceEditor.SetDocumentEndcodeHTML("");
			setTimeout("SpaceEditor.SetDocumentEndcodeHTML(document.forms[0].content.value)", 500);
		}
	}
}

function submitContent(host, port, upload_page) {

	// ÷�� ���� ���� select ��ü�� �ִ°��
	if (document.forms[0].attachList) {
		document.forms[0].attachList.options[0] = null;	// 0��°�� �����ϴ� option�� ����
	
		for (var i=0; i < document.forms[0].attachList.length; i++) { 	// 0��° skip
			document.forms[0].attachList.options[i].selected = true;
		}
	}

	if (document.forms[0].EditRadio[1].checked == true) {
		// �� �����͸� ����ϴ� ���
		document.forms[0].content.value = SpaceEditor.GetDocumentHTML();

		SpaceEditor.ClearParam();
		SpaceEditor.SetHostInfo( host, port, upload_page, document.cookie );
		SpaceEditor.FormSubmit( document.forms[0] );
		
	} else {
		document.forms[0].target = upload_page;
	
		// textarea�� ����ϴ� ���
		document.forms[0].submit();
	}
}

function loadContent(content) {
	if (content.length > 5 && content.ltrim().substring(0, 5) == "<HTML") {
		document.all.editSpan1.style.display = "none";
		document.all.editSpan2.style.display = "block";
		document.forms[0].EditRadio[1].checked = true;

		// �� �Է��� OnInitDocument()���� ��
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


// �������Ͱ� ó�� �ʱ�ȭ�� �� ȣ��ȴ�. 
// ��, �ι�° "������ �Է�"�� �������� ��쿡�� ȣ����� �ʴ´�.
function OnInitDocument() {
	SpaceEditorProInitialized = true;
	var contents = document.forms[0].content.value;
	SpaceEditor.SetDocumentEndcodeHTML(contents.replaceAll("\n", "<BR/>"));
}

// ���ڿ��� �ѹ� �ٲ۴�.
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

// ��Ʈ���� str1�� �ش��ϴ� ���ڿ��� str2�� �ٲ۴�.
// ����.
// var str = ���ڿ�.replaceAll("a", "1");  <-- ���ڿ� ������ 'a'�� ã�� ���� '1'�� �ٲٶ�� �ǹ�
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
