package com.custom.court.bean;

public class CourtBean {
	
	private String caseid;//案件id
	
	private String casecode;//案件名
    
	private String trialplanid;//排期id
	
	private String casenumber;// 案号

	private String courtroomid;// 法庭房间id
	
	private String courtroomname;// 法庭名称

	private String startdate;// 开庭日期（yyyy-MM-dd）

	private String starttime;// 开庭时间 （hh:mm:ss）

//	private String judgename;// 主审法官，审判长

//	private String party;// 当事人，被告，原告
	
	private String trialstatus;//庭审状态:2或5为开庭，1为等待，3为结束，4为休庭
	
    private String undertakename;//承办人
    
//    private String judge2name;//审判员1
    
//    private String judge3name;//审判员2
    
    private String casetypename;//案件类型，民事，刑事
	
//    private String conceptname;//案由
	
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
