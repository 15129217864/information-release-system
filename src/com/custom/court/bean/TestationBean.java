package com.custom.court.bean;

public class TestationBean {

	
	private String testationname;//֤��              ����ʱ�Ժ��ֶ��Ÿ���
	
	private int litigationtype;//���ϵ�λͳ��        1-ԭ�棬2-����
	
	public TestationBean(String testationname, int litigationtype) {
		super();
		this.testationname = testationname;
		this.litigationtype = litigationtype;
	}
	public TestationBean() {
		super();
		// TODO �Զ����ɹ��캯�����
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
