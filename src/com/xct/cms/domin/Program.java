package com.xct.cms.domin;

public class Program implements java.io.Serializable{

	private int program_JMid;
	private String program_JMurl;
	private String program_Name;
	private String program_SetDateTime;
	private int program_ISloop;   ////是否被锁定
	private int program_PlaySecond;
	private String program_lr;
	private String program_lx;
	
	private int program_playcount;
	
	private String program_ggs;
	private String program_ip;
	private String program_downtime;
	private String program_adduser;
	private int program_status;
	private int program_treeid;
	private String templateid;
	private String zu_name;
	
	private int auditingstatus=0;  //节目审核状态   0为 未审核，1 为 已审核
	
	private int template_width;
	private int template_height;
	
	private int isSet_play_type;
	////////////////////////  节目属性
	private int play_type;   // 0  循环 ，1插播 ，2 定时  3 永久循环
	private String play_type_Zh;
	private String  play_start_time;
	private String  play_end_time;
	private String  play_count;
	private String  day_stime1;
	private String  day_etime1;
	
	
	private int program_isdel;
	
	public int getIsSet_play_type() {
		return isSet_play_type;
	}

	public void setIsSet_play_type(int isSet_play_type) {
		this.isSet_play_type = isSet_play_type;
	}


	public String getPlay_type_Zh() {
		if(play_type==1){
			this.play_type_Zh="定时循环";
		}else if(play_type==2){
			this.play_type_Zh="插播";
		}else if(play_type==3){
			this.play_type_Zh="定时";
		}else if(play_type==4){
			this.play_type_Zh="永久循环";
		}
		return play_type_Zh;
	}

	public void setPlay_type_Zh(String play_type_Zh) {
		this.play_type_Zh = play_type_Zh;
	}
	
	public String getDay_etime1() {
		return day_etime1;
	}
	public void setDay_etime1(String day_etime1) {
		this.day_etime1 = day_etime1;
	}
	public String getDay_stime1() {
		return day_stime1;
	}
	public void setDay_stime1(String day_stime1) {
		this.day_stime1 = day_stime1;
	}
	public String getPlay_count() {
		return play_count;
	}
	public void setPlay_count(String play_count) {
		this.play_count = play_count;
	}
	public String getPlay_end_time() {
		return play_end_time;
	}
	public void setPlay_end_time(String play_end_time) {
		this.play_end_time = play_end_time;
	}
	public String getPlay_start_time() {
		return play_start_time;
	}
	public void setPlay_start_time(String play_start_time) {
		this.play_start_time = play_start_time;
	}
	public int getPlay_type() {
		return play_type;
	}
	public void setPlay_type(int play_type) {
		this.play_type = play_type;
	}
	public int getTemplate_height() {
		return template_height;
	}
	public void setTemplate_height(int template_height) {
		this.template_height = template_height;
	}
	public int getTemplate_width() {
		return template_width;
	}
	public void setTemplate_width(int template_width) {
		this.template_width = template_width;
	}
	public String getZu_name() {
		return zu_name;
	}
	public void setZu_name(String zu_name) {
		this.zu_name = zu_name;
	}
	public String getProgram_adduser() {
		return program_adduser;
	}
	public void setProgram_adduser(String program_adduser) {
		this.program_adduser = program_adduser;
	}
	public String getProgram_downtime() {
		return program_downtime;
	}
	public void setProgram_downtime(String program_downtime) {
		this.program_downtime = program_downtime;
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
	public int getProgram_ISloop() {
		return program_ISloop;
	}
	public void setProgram_ISloop(int program_ISloop) {
		this.program_ISloop = program_ISloop;
	}
	
	public int getProgram_status() {
		return program_status;
	}public String getProgram_status_title() {
		String status_title="";
		if(program_status==0){
			status_title="";
		}else{
			
		}
		return status_title;
	}
	public void setProgram_status(int program_status) {
		this.program_status = program_status;
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
	public String getProgram_Name() {
		return program_Name;
	}
	public void setProgram_Name(String program_Name) {
		this.program_Name = program_Name;
	}
	public int getProgram_playcount() {
		return program_playcount;
	}
	public void setProgram_playcount(int program_playcount) {
		this.program_playcount = program_playcount;
	}
	public int getProgram_PlaySecond() {
		return program_PlaySecond;
	}
	public void setProgram_PlaySecond(int program_PlaySecond) {
		this.program_PlaySecond = program_PlaySecond;
	}
	public String getProgram_SetDateTime() {
		return program_SetDateTime;
	}
	public void setProgram_SetDateTime(String program_SetDateTime) {
		this.program_SetDateTime = program_SetDateTime;
	}
	public int getProgram_treeid() {
		return program_treeid;
	}
	public void setProgram_treeid(int program_treeid) {
		this.program_treeid = program_treeid;
	}
	public String getTemplateid() {
		return templateid;
	}
	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public int getProgram_isdel() {
		return program_isdel;
	}

	public void setProgram_isdel(int program_isdel) {
		this.program_isdel = program_isdel;
	}

	public int getAuditingstatus() {
		return auditingstatus;
	}

	public void setAuditingstatus(int auditingstatus) {
		this.auditingstatus = auditingstatus;
	}

	
	
}
