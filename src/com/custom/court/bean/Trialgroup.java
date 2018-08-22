package com.custom.court.bean;

public class Trialgroup {//审判组织信息视图

	private String trialplanid;//排期id
	
	private String js;//角色 3为审判长，4为审判员，6为人民陪审员，7为助理审判员，9为书记员
	
	private String stmc;// 实体名称
	
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
