package com.xct.cms.domin;

public class ProgramApp implements java.io.Serializable{

	private String mac;//MAC地址
	
	private int id;
	private String program_id;
	private String program_jmurl;
	private String program_name;
	private String program_playdate;
	private String program_playtime;
	private String program_enddate;
	private String program_endtime;
	private int program_playlong;
	private int program_play_type;
	private String program_play_typeZh;
	private String program_play_typeEn;
	private String program_play_terminal;
	private String program_sendok_terminal;
	private String program_app_userid;
	private String program_app_name;
	private String program_app_time;
	private int program_app_status;
	private String program_app_statusZh;
	
	private int program_treeid;   ///节目组ID
	private String templateid;   ///模板ID
	private int batch;     ///批次号
	
	private String send_user;
	private String send_time;
	
	public String getProgram_app_name() {
		return program_app_name;
	}
	public void setProgram_app_name(String program_app_name) {
		this.program_app_name = program_app_name;
	}
	public int getBatch() {
		return batch;
	}
	public void setBatch(int batch) {
		this.batch = batch;
	}
	public String getProgram_id() {
		return program_id;
	}
	public void setProgram_id(String program_id) {
		this.program_id = program_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getProgram_play_typeEn() {
		program_play_typeEn="";
		if(program_play_type==0){
			program_play_typeEn="loop";
		}else if(program_play_type==1){
			program_play_typeEn="insert";
		}else if(program_play_type==2){
			program_play_typeEn="active";
		}else if(program_play_type==3){
			program_play_typeEn="defaultloop";
		}
		return program_play_typeEn;
	}
	public String getProgram_play_typeZh() {
		program_play_typeZh="";
		if(program_play_type==0){
			program_play_typeZh="定时循环";
		}else if(program_play_type==1){
			program_play_typeZh="插播";//
		}else if(program_play_type==2){
			program_play_typeZh="定时";
		}else if(program_play_type==3){
			program_play_typeZh="永久循环";
		}
		return program_play_typeZh;
	}
	public String getProgram_playtime() {
		return program_playtime;
	}
	public void setProgram_playtime(String program_playtime) {
		this.program_playtime = program_playtime;
	}
	public String getProgram_app_statusZh() {
		program_app_statusZh="";
		if(program_app_status==0){
			program_app_statusZh="<span style='color:maroon'>待审核</span>";
		}else if(program_app_status==1){
			program_app_statusZh="<span style='color:green'>已发送</span>";
		}else if(program_app_status==2){
			program_app_statusZh="<span style='color:red'>审核未通过</span>";
		}else if(program_app_status==3){
			program_app_statusZh="<span style='color:maroon'>待发送</span>";
		}else if(program_app_status==4){
			program_app_statusZh="<span style='color:red'>发送失败</span>";
		}
		return program_app_statusZh;
	}
	public int getProgram_app_status() {
		return program_app_status;
	}
	public void setProgram_app_status(int program_app_status) {
		this.program_app_status = program_app_status;
	}
	public String getProgram_app_time() {
		return program_app_time;
	}
	public void setProgram_app_time(String program_app_time) {
		this.program_app_time = program_app_time;
	}
	public String getProgram_app_userid() {
		return program_app_userid;
	}
	public void setProgram_app_userid(String program_app_userid) {
		this.program_app_userid = program_app_userid;
	}
	public String getProgram_enddate() {
		return program_enddate;
	}
	public void setProgram_enddate(String program_enddate) {
		this.program_enddate = program_enddate;
	}
	public String getProgram_endtime() {
		return program_endtime;
	}
	public void setProgram_endtime(String program_endtime) {
		this.program_endtime = program_endtime;
	}
	public String getProgram_jmurl() {
		return program_jmurl;
	}
	public void setProgram_jmurl(String program_jmurl) {
		this.program_jmurl = program_jmurl;
	}
	public String getProgram_play_terminal() {
		return program_play_terminal;
	}
	public void setProgram_play_terminal(String program_play_terminal) {
		this.program_play_terminal = program_play_terminal;
	}
	public int getProgram_play_type() {
		return program_play_type;
	}
	public void setProgram_play_type(int program_play_type) {
		this.program_play_type = program_play_type;
	}
	public String getProgram_playdate() {
		return program_playdate;
	}
	public void setProgram_playdate(String program_playdate) {
		this.program_playdate = program_playdate;
	}
	public int getProgram_playlong() {
		return program_playlong;
	}
	public void setProgram_playlong(int program_playlong) {
		this.program_playlong = program_playlong;
	}
	public String getProgram_name() {
		return program_name;
	}
	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}
	public String getTemplateid() {
		return templateid;
	}
	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}
	public int getProgram_treeid() {
		return program_treeid;
	}
	public void setProgram_treeid(int program_treeid) {
		this.program_treeid = program_treeid;
	}
	public String getProgram_sendok_terminal() {
		return program_sendok_terminal;
	}
	public void setProgram_sendok_terminal(String program_sendok_terminal) {
		this.program_sendok_terminal = program_sendok_terminal;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getSend_user() {
		return send_user;
	}
	public void setSend_user(String send_user) {
		this.send_user = send_user;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}

}
