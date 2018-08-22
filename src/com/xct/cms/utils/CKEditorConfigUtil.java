package com.xct.cms.utils;

import com.ckeditor.CKEditorConfig;

public class CKEditorConfigUtil {

	StringBuffer configbuffer=new StringBuffer();
	
	
	
	public CKEditorConfigUtil() {
		init2();
	}

	public CKEditorConfig getCKEditorConfig(){
		 CKEditorConfig ckeditorconfig = new CKEditorConfig();
		 ckeditorconfig.addConfigValue("width","1020px");
		 ckeditorconfig.addConfigValue("height","480px");
		 ckeditorconfig.addConfigValue("toolbar",configbuffer.toString());
		 ckeditorconfig.addConfigValue("font_names",
				 new StringBuffer().append("ËÎÌå/ËÎÌå;ºÚÌå/ºÚÌå;·ÂËÎ/·ÂËÎ_GB2312;¿¬Ìå/¿¬Ìå_GB2312;Á¥Êé/Á¥Êé;Ó×Ô²/Ó×Ô²;Î¢ÈíÑÅºÚ/Î¢ÈíÑÅºÚ;")
				                   .append("Arial;Comic Sans MS;Courier New;Georgia;Lucida Sans Unicode;Tahoma;Times New Roman;Trebuchet MS;Verdana").toString());
		 
		 //ckeditorconfig.addConfigValue("toolbar","[[ 'Source', '-', 'Bold', 'Italic' ]]");
	     return ckeditorconfig;
	}

    public void init2(){
		
		configbuffer.append("[");
		//configbuffer.append("{ name: 'document', items : [ 'Source' ] },");//,'-','Templates'
		
		//configbuffer.append("{ name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },");
		
		configbuffer.append("{ name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript' ] },");//,'-','RemoveFormat'
		
		configbuffer.append("{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent',");//,'-','Blockquote','CreateDiv'
		
		configbuffer.append("'-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock' ] },");
		
		//configbuffer.append("'-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock' ,'-','BidiLtr','BidiRtl'] },");
		
		//configbuffer.append("{ name: 'links', items : [ 'Link','Unlink','Anchor' ] },");
		//configbuffer.append("'/',");
		
		//configbuffer.append("{ name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe' ] },");
		
		//configbuffer.append("'/',");
//		configbuffer.append("{ name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },");
		
		configbuffer.append("{ name: 'styles', items : [ 'Format','Font','FontSize' ] },");
		configbuffer.append("{ name: 'colors', items : [ 'TextColor','BGColor' ] },");
		//configbuffer.append("{ name: 'tools', items : [ 'Maximize' ] }");
		
//		configbuffer.append("{ name: 'tools', items : [ 'Maximize', 'ShowBlocks','-','About' ] }");
		
		configbuffer.append("]");
	}
	
	public void init(){
		
		configbuffer.append("[");
		configbuffer.append("{ name: 'document', items : [ 'Source','-','Templates' ] },");
		//configbuffer.append("{ name: 'document', items : [ 'Source','-' ,'Save','NewPage','DocProps','Preview','Print','-', 'Templates' ] },");
		
		configbuffer.append("{ name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },");
		
		//configbuffer.append("{ name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },");
		//configbuffer.append("{ name: 'forms', items : [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton',  'HiddenField' ] },");
		
		
		configbuffer.append("{ name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },");
		configbuffer.append("{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv',");
		configbuffer.append("'-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock' ] },");
		
		//configbuffer.append("'-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock' ,'-','BidiLtr','BidiRtl'] },");
		
		configbuffer.append("{ name: 'links', items : [ 'Link','Unlink','Anchor' ] },");
		configbuffer.append("'/',");
		
		configbuffer.append("{ name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe' ] },");
		
		//configbuffer.append("'/',");
//		configbuffer.append("{ name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },");
		
		configbuffer.append("{ name: 'styles', items : [ 'Format','Font','FontSize' ] },");
		configbuffer.append("{ name: 'colors', items : [ 'TextColor','BGColor' ] },");
		configbuffer.append("{ name: 'tools', items : [ 'Maximize' ] }");
		
//		configbuffer.append("{ name: 'tools', items : [ 'Maximize', 'ShowBlocks','-','About' ] }");
		
		configbuffer.append("]");
	}
}

/**
CKEditorConfig settings = new CKEditorConfig();
settings.addConfigValue("toolbar","[[ 'Source', '-', 'Bold', 'Italic' ]]");

µÈÍ¬ÓÚ

CKEditorConfig settings = new CKEditorConfig();
List<List<String>> list = new ArrayList<List<String>>();
List<String> subList = new ArrayList<String>();
subList.add("Source");
subList.add("-");
subList.add("Bold");
subList.add("Italic");
list.add(subList);
settings.addConfigValue("toolbar", list);
**/

/**
CKEditorConfig settings = new CKEditorConfig();
settings.addConfigValue("toolbar","[{name: 'document', items: ['Source', '-', 'NewPage']},'/', {name: 'styles', items: ['Styles','Format']} ]");

µÈÍ¬ÓÚ

CKEditorConfig settings = new CKEditorConfig();
List<Object> mainList = new ArrayList<Object>();                
HashMap<String, Object> toolbarSectionMap = new HashMap<String, Object>();
List<String> subList = new ArrayList<String>();
subList.add("Source");
subList.add("-");
subList.add("NewPage");    
toolbarSectionMap.put("name", "document");    
toolbarSectionMap.put("items", subList);    
mainList.add(toolbarSectionMap);        
mainList.add("/");
toolbarSectionMap = new HashMap<String, Object>();
subList = new ArrayList<String>();
subList.add("Styles");                
subList.add("Format");
toolbarSectionMap.put("name", "styles");    
toolbarSectionMap.put("items", subList);
mainList.add(toolbarSectionMap);                
settings.addConfigValue("toolbar", mainList);

**/
