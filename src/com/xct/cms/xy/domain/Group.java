package com.xct.cms.xy.domain;

public class Group {

	private int zu_id; //组id	
	private int zu_pth;//组父id
	private String zu_name;//组名

	public Group(int zu_id, int zu_pth, String zu_name) {
		this.zu_id = zu_id;
		this.zu_pth = zu_pth;
		this.zu_name = zu_name;
	}
	public int getZu_id() {
		return zu_id;
	}
	public void setZu_id(int zu_id) {
		this.zu_id = zu_id;
	}
	public String getZu_name() {
		return zu_name;
	}
	public void setZu_name(String zu_name) {
		this.zu_name = zu_name;
	}
	public int getZu_pth() {
		return zu_pth;
	}
	public void setZu_pth(int zu_pth) {
		this.zu_pth = zu_pth;
	}
	
}
