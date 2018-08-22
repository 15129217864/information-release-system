package com.xct.cms.domin;

public class ProgramHistory implements java.io.Serializable{

	private int program_JMid;
	private String program_JMurl;
	private String program_Name;
	private String program_SetDateTime;
	private int program_Sethour;
	private int program_Setminute;
	private String program_SetDate;
	private String program_EndDate;
	private String program_EndDateTime;
	private int program_Endhour;
	private int program_Endminute;
	private int program_ISloop;
	private int program_PlaySecond;
	private String program_lr;
	private String project_url_datetime;
	private String program_senduser;
	private String isforover;
	
	public String getIsforover() {
		return isforover;
	}
	public void setIsforover(String isforover) {
		this.isforover = isforover;
	}
	public String getProgram_senduser() {
		return program_senduser;
	}
	public void setProgram_senduser(String program_senduser) {
		this.program_senduser = program_senduser;
	}
	public ProgramHistory() {
		
	}
	private String program_lx;
	private int program_playcount;
	private String program_ggs;
	private String program_ip;
	private String program_delid;
	private int program_issend;
	private String program_downtime;
	private String  program_type;
	private String program_typeZh;
	private String program_typeZh1;
	private String program_typeEn;
	private String program_long;
	
	private String xyenddatetime;//结束时间
	private String xystatus;  //播放状态
	
	public String getProgram_long() {
		return program_long;
	}
	public void setProgram_long(String program_long) {
		this.program_long = program_long;
	}
	public String getProgram_typeZh() {
		program_typeZh="";
		if("loop".equals(program_type)){
			program_typeZh="定时循环";
		}else if("insert".equals(program_type)){
			program_typeZh="插播";
		}else if("active".equals(program_type)){
			program_typeZh="定时";
		}else if("defaultloop".equals(program_type)){
			program_typeZh="永久循环";
		}
		return program_typeZh;
	}
	public String getProgram_typeZh1() {
		program_typeZh1="";
		if(program_ISloop==0){
			program_typeZh1="定时循环";
		}else if(program_ISloop==1){
			program_typeZh1="插播";
		}else if(program_ISloop==2){
			program_typeZh1="定时";
		}else if(program_ISloop==3){
			program_typeZh1="永久循环";
		}
		return program_typeZh1;
	}
	public String getProgram_typeEn() {
		program_typeEn="";
		if(program_ISloop==0){
			program_typeEn="loop";
		}else if(program_ISloop==1){
			program_typeEn="insert";
		}else if(program_ISloop==2){
			program_typeEn="active";
		}else if(program_ISloop==3){
			program_typeEn="defaultloop";
		}
		return program_typeEn;
	}
	public String getProgram_type() {
		return program_type;
	}
	public void setProgram_type(String program_type) {
		this.program_type = program_type;
	}
	public int getProgram_JMid() {
		return program_JMid;
	}
	public void setProgram_JMid(int program_JMid) {
		this.program_JMid = program_JMid;
	}
	public String getProgram_JMurl() {
		return program_JMurl;
	}
	public void setProgram_JMurl(String program_JMurl) {
		this.program_JMurl = program_JMurl;
	}
	public String getProgram_Name() {
		return program_Name;
	}
	public void setProgram_Name(String program_Name) {
		this.program_Name = program_Name;
	}
	public String getProgram_SetDateTime() {
		return program_SetDateTime;
	}
	public void setProgram_SetDateTime(String program_SetDateTime) {
		this.program_SetDateTime = program_SetDateTime;
	}
	public int getProgram_Sethour() {
		return program_Sethour;
	}
	public void setProgram_Sethour(int program_Sethour) {
		this.program_Sethour = program_Sethour;
	}
	public int getProgram_Setminute() {
		return program_Setminute;
	}
	public void setProgram_Setminute(int program_Setminute) {
		this.program_Setminute = program_Setminute;
	}
	public String getProgram_SetDate() {
		return program_SetDate;
	}
	public void setProgram_SetDate(String program_SetDate) {
		this.program_SetDate = program_SetDate;
	}
	public String getProgram_EndDate() {
		return program_EndDate;
	}
	public void setProgram_EndDate(String program_EndDate) {
		this.program_EndDate = program_EndDate;
	}
	public String getProgram_EndDateTime() {
		return program_EndDateTime;
	}
	public void setProgram_EndDateTime(String program_EndDateTime) {
		this.program_EndDateTime = program_EndDateTime;
	}
	public int getProgram_Endhour() {
		return program_Endhour;
	}
	public void setProgram_Endhour(int program_Endhour) {
		this.program_Endhour = program_Endhour;
	}
	public int getProgram_Endminute() {
		return program_Endminute;
	}
	public void setProgram_Endminute(int program_Endminute) {
		this.program_Endminute = program_Endminute;
	}
	public int getProgram_ISloop() {
		return program_ISloop;
	}
	public void setProgram_ISloop(int program_ISloop) {
		this.program_ISloop = program_ISloop;
	}
	public int getProgram_PlaySecond() {
		return program_PlaySecond;
	}
	public void setProgram_PlaySecond(int program_PlaySecond) {
		this.program_PlaySecond = program_PlaySecond;
	}
	public String getProgram_lr() {
		return program_lr;
	}
	public void setProgram_lr(String program_lr) {
		this.program_lr = program_lr;
	}
	public String getProgram_lx() {
		return program_lx;
	}
	public void setProgram_lx(String program_lx) {
		this.program_lx = program_lx;
	}
	public int getProgram_playcount() {
		return program_playcount;
	}
	public void setProgram_playcount(int program_playcount) {
		this.program_playcount = program_playcount;
	}
	public String getProgram_ggs() {
		return program_ggs;
	}
	public void setProgram_ggs(String program_ggs) {
		this.program_ggs = program_ggs;
	}
	public String getProgram_ip() {
		return program_ip;
	}
	public void setProgram_ip(String program_ip) {
		this.program_ip = program_ip;
	}
	public String getProgram_delid() {
		return program_delid;
	}
	public void setProgram_delid(String program_delid) {
		this.program_delid = program_delid;
	}
	public int getProgram_issend() {
		return program_issend;
	}
	public void setProgram_issend(int program_issend) {
		this.program_issend = program_issend;
	}
	public String getProgram_downtime() {
		return program_downtime;
	}
	public void setProgram_downtime(String program_downtime) {
		this.program_downtime = program_downtime;
	}
	public String getXyenddatetime() {
		return xyenddatetime;
	}
	public void setXyenddatetime(String xyenddatetime) {
		this.xyenddatetime = xyenddatetime;
	}
	public String getXystatus() {
		return xystatus;
	}
	public void setXystatus(String xystatus) {
		this.xystatus = xystatus;
	}
	public String getProject_url_datetime() {
		return project_url_datetime;
	}
	public void setProject_url_datetime(String project_url_datetime) {
		this.project_url_datetime = project_url_datetime;
	}
	
	
}
