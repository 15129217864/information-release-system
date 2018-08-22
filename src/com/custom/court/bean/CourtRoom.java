package com.custom.court.bean;

public class CourtRoom {

	private String courtroomid;

	private String courtroomname;
	
	private String courtroomposition;
	

	public CourtRoom(String courtroomid, String courtroomname,String courtroomposition) {
		super();
		// TODO 自动生成构造函数存根
		this.courtroomid = courtroomid;
		this.courtroomname = courtroomname;
		this.courtroomposition=courtroomposition;
	}

	public CourtRoom() {
		super();
		// TODO 自动生成构造函数存根
	}

	public String getCourtroomid() {
		return courtroomid;
	}

	public void setCourtroomid(String courtroomid) {
		this.courtroomid = courtroomid;
	}

	public String getCourtroomname() {
		return courtroomname;
	}

	public void setCourtroomname(String courtroomname) {
		this.courtroomname = courtroomname;
	}

	@Override
	public String toString() {
		return courtroomid+"__"+courtroomname;
	}

	public String getCourtroomposition() {
		return courtroomposition;
	}

	public void setCourtroomposition(String courtroomposition) {
		this.courtroomposition = courtroomposition;
	}

	
	
}
