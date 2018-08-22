package com.gson.domain;

public class CmsServiceInfo {

	private String code;
	private String message;
	public CmsData data=new CmsData();
	
	public CmsServiceInfo() {
		super();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public CmsData getData() {
		return data;
	}
	public void setData(CmsData data) {
		this.data = data;
	}
	
	
	
}