package com.custom.court.bean;

public class PartyBean {

	private String caseid; //����ID
	private String ssdw; //��λ���ƣ��������棬ԭ��
	private String ssdwtc; //���ϵ�λͳ��        1-ԭ�棬2-����
	private String dsrxm;//����   
	
	
	
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
