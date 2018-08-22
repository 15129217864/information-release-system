package com.xct.cms.xy.domain;

public class CntResponse {

	private int id;
	private String cnt_ip;
	private String cnt_cmd;
	private String cnt_cmdstatus;
	private String cnt_cmdresult;
	private String cnt_sendtime;
	public String getCnt_cmdstatus() {
		return cnt_cmdstatus;
	}
	public void setCnt_cmdstatus(String cnt_cmdstatus) {
		this.cnt_cmdstatus = cnt_cmdstatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCnt_ip() {
		return cnt_ip;
	}
	public void setCnt_ip(String cnt_ip) {
		this.cnt_ip = cnt_ip;
	}
	public String getCnt_cmd() {
		return cnt_cmd;
	}
	public void setCnt_cmd(String cnt_cmd) {
		this.cnt_cmd = cnt_cmd;
	}
	public String getCnt_cmdresult() {
		return cnt_cmdresult;
	}
	public void setCnt_cmdresult(String cnt_cmdresult) {
		this.cnt_cmdresult = cnt_cmdresult;
	}
	public String getCnt_sendtime() {
		return cnt_sendtime;
	}
	public void setCnt_sendtime(String cnt_sendtime) {
		this.cnt_sendtime = cnt_sendtime;
	}
	
}
