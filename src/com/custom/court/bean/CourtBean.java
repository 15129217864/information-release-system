package com.custom.court.bean;

public class CourtBean {
	
	private String caseid;//����id
	
	private String casecode;//������
    
	private String trialplanid;//����id
	
	private String casenumber;// ����

	private String courtroomid;// ��ͥ����id
	
	private String courtroomname;// ��ͥ����

	private String startdate;// ��ͥ���ڣ�yyyy-MM-dd��

	private String starttime;// ��ͥʱ�� ��hh:mm:ss��

//	private String judgename;// ���󷨹٣����г�

//	private String party;// �����ˣ����棬ԭ��
	
	private String trialstatus;//ͥ��״̬:2��5Ϊ��ͥ��1Ϊ�ȴ���3Ϊ������4Ϊ��ͥ
	
    private String undertakename;//�а���
    
//    private String judge2name;//����Ա1
    
//    private String judge3name;//����Ա2
    
    private String casetypename;//�������ͣ����£�����
	
//    private String conceptname;//����
	
	public CourtBean(String caseid,String casecode,String trialplanid, String casenumber, String courtroomid, String courtroomname,
			String startdate, String starttime,String trialstatus,String undertakename,	String casetypename) {
	
		
		super();
		this.caseid=caseid;
		this.casecode = casecode;
		this.trialplanid=trialplanid;
		this.casenumber = casenumber;
		this.courtroomid = courtroomid;
		this.courtroomname = courtroomname;
		this.startdate = startdate;
		this.starttime = starttime;
		this.trialstatus=trialstatus;
		this.undertakename=undertakename;
		this.casetypename=casetypename;
	}

	public CourtBean() {
		super();
	}

	public String getCasecode() {
		return casecode;
	}

	public void setCasecode(String casecode) {
		this.casecode = casecode;
	}

	public String getCourtroomname() {
		return courtroomname;
	}

	public void setCourtroomname(String courtroomname) {
		this.courtroomname = courtroomname;
	}


	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getCourtroomid() {
		return courtroomid;
	}

	public void setCourtroomid(String courtroomid) {
		this.courtroomid = courtroomid;
	}

	public String getCasenumber() {
		return casenumber;
	}

	public void setCasenumber(String casenumber) {
		this.casenumber = casenumber;
	}
	
	
	public String getCaseid() {
		return caseid;
	}

	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}

	@Override
	public String toString() {
		
		StringBuffer stringbuffer=new StringBuffer().append(this.casecode).append(",")
		                  .append(this.casenumber).append(",")
		                  .append(this.courtroomid).append(",")
		                  .append(this.courtroomname).append(",")
		                  .append(this.startdate).append(",")
		                  .append(this.starttime).append(",")
		                  .append(this.trialstatus)
		                  ;
		

		return stringbuffer.toString();
	}

	public String getTrialstatus() {
		return trialstatus;
	}

	public void setTrialstatus(String trialstatus) {
		this.trialstatus = trialstatus;
	}

	public String getTrialplanid() {
		return trialplanid;
	}

	public void setTrialplanid(String trialplanid) {
		this.trialplanid = trialplanid;
	}

	public String getUndertakename() {
		return undertakename;
	}

	public void setUndertakename(String undertakename) {
		this.undertakename = undertakename;
	}

	public String getCasetypename() {
		return casetypename;
	}

	public void setCasetypename(String casetypename) {
		this.casetypename = casetypename;
	}

	
}
