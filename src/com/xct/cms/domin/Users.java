package com.xct.cms.domin;


public class Users implements java.io.Serializable{

	private String lg_name;
	private String lg_password;
	private String name;
	private String email;
	private String last_login_time;
	private String now_login_time;
	private String lg_role;
	private String lg_roleZh;
	private String lg_mac;
	private String authority;
	
	private String login_ip;

	public String getLg_mac() {
		return lg_mac;
	}
	public void setLg_mac(String lg_mac) {
		this.lg_mac = lg_mac;
	}
	public void setLg_roleZh(String lg_roleZh) {
		this.lg_roleZh = lg_roleZh;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}
	public String getLg_name() {
		return lg_name;
	}
	public void setLg_name(String lg_name) {
		this.lg_name = lg_name;
	}
	public String getLg_password() {
		return lg_password;
	}
	public void setLg_password(String lg_password) {
		this.lg_password = lg_password;
	}
	public String getLg_role() {
		return lg_role;
	}
	public String getLg_roleZh() {
		if("1".equals(lg_role)){
			this.lg_roleZh="超级管理员";
		}else if("2".equals(lg_role)){
			this.lg_roleZh="审核员";
		}else if("3".equals(lg_role)){
			this.lg_roleZh="一般用户";
		}
		return lg_roleZh;
	}
	public void setLg_role(String lg_role) {
		this.lg_role = lg_role;
	}
	public String getNow_login_time() {
		return now_login_time;
	}
	public void setNow_login_time(String now_login_time) {
		this.now_login_time = now_login_time;
	}
	public String getLogin_ip() {
		return login_ip;
	}
	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	
	
}
