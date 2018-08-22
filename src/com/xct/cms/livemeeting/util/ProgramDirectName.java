package com.xct.cms.livemeeting.util;


public class ProgramDirectName {

	private String programaddress;//JSHM-->地点
	private String programmeetingname;//SYZ-->会议名
	private String programmeeting;//CZSJ-->主题内容
	
	private String programyear;//CZRBM--->年月日
	private String programstart;//CZSJD--->开始时间
	private String programend;// CZR-->结束时间
	
	private String programdept;//dept-->部门
	private String programremark;// remark--->备注
	private String datetimer;
	private String hand_dept;
	private String createname;
	
	@Override
	public String toString() {
		// TODO 自动生成方法存根
		return this.datetimer+"#"+this.programaddress+"#"+this.programmeetingname+"#"+this.programmeeting;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProgramDirectName) {
			ProgramDirectName t = (ProgramDirectName) obj;
			if (this.datetimer.equals(t.datetimer) 
					&& this.programaddress.equals(t.programaddress)
					&& this.programmeetingname.equals(t.programmeetingname) 
					&& this.programmeeting.equals(t.programmeeting)) {
				return true;
			}
		}
		return false;
	}
	public String getDatetimer() {
		return datetimer;
	}
	
	public void setDatetimer(String datetimer) {
		this.datetimer = datetimer;
	}

	
	public String getProgramdept() {
		return programdept;
	}
	public void setProgramdept(String programdept) {
		this.programdept = programdept;
	}
	
	public String getProgramremark() {
		return programremark;
	}
	
	public void setProgramremark(String programremark) {
		this.programremark = programremark;
	}


	public String getProgramaddress() {
		return programaddress;
	}


	public void setProgramaddress(String programaddress) {
		this.programaddress = programaddress;
	}


	public String getProgramend() {
		return programend;
	}


	public void setProgramend(String programend) {
		this.programend = programend;
	}


	public String getProgrammeeting() {
		return programmeeting;
	}


	public void setProgrammeeting(String programmeeting) {
		this.programmeeting = programmeeting;
	}


	public String getProgramstart() {
		return programstart;
	}


	public void setProgramstart(String program) {
		this.programstart = programstart;
	}


	public String getProgramyear() {
		return programyear;
	}


	public void setProgramyear(String programyear) {
		this.programyear = programyear;
	}


	public String getProgrammeetingname() {
		return programmeetingname;
	}


	public void setProgrammeetingname(String programmeetingname) {
		this.programmeetingname = programmeetingname;
	}

	public String getHand_dept() {
		return hand_dept;
	}

	public void setHand_dept(String hand_dept) {
		this.hand_dept = hand_dept;
	}

	public String getCreatename() {
		return createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname;
	}

	


}
