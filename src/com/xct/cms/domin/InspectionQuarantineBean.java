package com.xct.cms.domin;

public class InspectionQuarantineBean {
	
	private String task;//��������
	private String builddate;//���鿪ʼ����
	private String buidstime;//���鿪ʼʱ��
	private String buidetime;//�������ʱ��
	private String meetingdept;//���ò���
	private String attendman;//����������
//	private String content;//�������ݣ�������ϵ�ˣ��绰���ֻ��������Ϣ
//	private String scroolltext;//��ʾ����������
	
	
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
