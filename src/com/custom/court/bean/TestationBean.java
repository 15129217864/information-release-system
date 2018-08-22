package com.custom.court.bean;

public class TestationBean {

	
	private String testationname;//证人              多人时以汉字逗号隔开
	
	private int litigationtype;//诉讼地位统称        1-原告，2-被告
	
	public TestationBean(String testationname, int litigationtype) {
		super();
		this.testationname = testationname;
		this.litigationtype = litigationtype;
	}
	public TestationBean() {
		super();
		// TODO 自动生成构造函数存根
	}
	public int getLitigationtype() {
		return litigationtype;
	}
	public void setLitigationtype(int litigationtype) {
		this.litigationtype = litigationtype;
	}
	public String getTestationname() {
		return testationname;
	}
	public void setTestationname(String testationname) {
		this.testationname = testationname;
	}

	
	
}
