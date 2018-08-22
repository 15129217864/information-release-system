package com.xct.cms.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class CreateTemplateForm extends ActionForm {

	private String template_name;
	private String template_background;
	private String template_type;
	private String template_module;
	private int template_width;
	private int template_height;
	private String template_expla;
	
	public String getTemplate_expla() {
		return template_expla;
	}
	public void setTemplate_expla(String template_expla) {
		this.template_expla = template_expla;
	}
	public int getTemplate_height() {
		return template_height;
	}
	public void setTemplate_height(int template_height) {
		this.template_height = template_height;
	}
	public int getTemplate_width() {
		return template_width;
	}
	public void setTemplate_width(int template_width) {
		this.template_width = template_width;
	}
	
	public String getTemplate_background() {
		return template_background;
	}
	public void setTemplate_background(String template_background) {
		this.template_background = template_background;
	}
	public String getTemplate_module() {
		return template_module;
	}
	public void setTemplate_module(String template_module) {
		this.template_module = template_module;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public String getTemplate_type() {
		return template_type;
	}
	public void setTemplate_type(String template_type) {
		this.template_type = template_type;
	}
	
	
}
