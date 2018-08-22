package com.xct.cms.domin;

public class InspectionQuarantineBean {
	
	private String task;//会议名称
	private String builddate;//会议开始日期
	private String buidstime;//会议开始时间
	private String buidetime;//会议结束时间
	private String meetingdept;//借用部门
	private String attendman;//会议室名称
//	private String content;//会议内容，包括联系人，电话，手机等相关信息
//	private String scroolltext;//显示屏滚动文字
	
	
	public InspectionQuarantineBean(String task, String builddate, String buidstime, String buidetime, String meetingdept, String attendman/*,String content, String scroolltext*/) {
		super();
		this.task = (task==null?"":task);
		this.builddate = (builddate==null?"":builddate);
		this.buidstime =( buidstime==null?"":buidstime);
		this.buidetime = (buidetime==null?"":buidetime);
		this.meetingdept = (meetingdept==null?"":meetingdept);
		this.attendman=(attendman==null?"":attendman);
//		this.content = (content==null?"":content);
//		this.scroolltext = (scroolltext==null?"":scroolltext);
	}
	
	
	
	public InspectionQuarantineBean() {
		super();
	}
	public String getBuidetime() {
		return buidetime;
	}
	public void setBuidetime(String buidetime) {
		this.buidetime = buidetime;
	}
	public String getBuidstime() {
		return buidstime;
	}
	public void setBuidstime(String buidstime) {
		this.buidstime = buidstime;
	}
	public String getBuilddate() {
		return builddate;
	}
	public void setBuilddate(String builddate) {
		this.builddate = builddate;
	}
//	public String getContent() {
//		return content;
//	}
//	public void setContent(String content) {
//		this.content = content;
//	}
	public String getMeetingdept() {
		return meetingdept;
	}
	public void setMeetingdept(String meetingdept) {
		this.meetingdept = meetingdept;
	}
//	public String getScroolltext() {
//		return scroolltext;
//	}
//	public void setScroolltext(String scroolltext) {
//		this.scroolltext = scroolltext;
//	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getAttendman() {
		return attendman;
	}
	public void setAttendman(String attendman) {
		this.attendman = attendman;
	}
}
