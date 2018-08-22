package com.custom.court.bean;

public class PartyBean {

	private String caseid; //案件ID
	private String ssdw; //地位名称，例：被告，原告
	private String ssdwtc; //诉讼地位统称        1-原告，2-被告
	private String dsrxm;//描述   
	
	
	
	public PartyBean(String caseid, String ssdw, String ssdwtc, String dsrxm) {
		super();
		this.caseid = caseid;
		this.ssdw = ssdw;
		this.ssdwtc = ssdwtc;
		this.dsrxm = dsrxm;
	}
	
	public PartyBean() {
		super();
	}
	public String getCaseid() {
		return caseid;
	}
	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}
	public String getDsrxm() {
		return dsrxm;
	}
	public void setDsrxm(String dsrxm) {
		this.dsrxm = dsrxm;
	}
	public String getSsdw() {
		return ssdw;
	}
	public void setSsdw(String ssdw) {
		this.ssdw = ssdw;
	}
	public String getSsdwtc() {
		return ssdwtc;
	}
	public void setSsdwtc(String ssdwtc) {
		this.ssdwtc = ssdwtc;
	}
	
	
	
}
