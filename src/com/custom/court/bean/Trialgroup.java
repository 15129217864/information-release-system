package com.custom.court.bean;

public class Trialgroup {//������֯��Ϣ��ͼ

	private String trialplanid;//����id
	
	private String js;//��ɫ 3Ϊ���г���4Ϊ����Ա��6Ϊ��������Ա��7Ϊ��������Ա��9Ϊ���Ա
	
	private String stmc;// ʵ������
	
	public Trialgroup() {
		super();
	}

	public Trialgroup(String trialplanid, String js, String stmc) {
		super();
		this.trialplanid = trialplanid;
		this.js = js;
		this.stmc = stmc;
	}

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public String getStmc() {
		return stmc;
	}

	public void setStmc(String stmc) {
		this.stmc = stmc;
	}

	public String getTrialplanid() {
		return trialplanid;
	}

	public void setTrialplanid(String trialplanid) {
		this.trialplanid = trialplanid;
	}
}
