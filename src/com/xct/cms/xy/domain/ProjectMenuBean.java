package com.xct.cms.xy.domain;

import java.util.List;


public class ProjectMenuBean {
	
	private String playname;
	private String playtype;
	private String setstartdate;
	private String startdate;
	private String enddate;
	private String setenddate;
	private String projectderitory;
	private String timecount;
	private List<ClientIpAddress>list;
	
	public ProjectMenuBean(String playname, String playtype,String setstartdate, String startdate, String enddate, String setenddate,
			               String projectderitory, String timecount, List<ClientIpAddress> iplist) {
		this.playname = playname;
		this.playtype = playtype;
		this.setstartdate=setstartdate;
		this.startdate = startdate;
		this.enddate = enddate;
		this.setenddate=setenddate;
		this.projectderitory = projectderitory;
		this.timecount = timecount;
		this.list = iplist;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getPlayname() {
		return playname;
	}
	public void setPlayname(String playname) {
		this.playname = playname;
	}
	public String getPlaytype() {
		return playtype;
	}
	public void setPlaytype(String playtype) {
		this.playtype = playtype;
	}
	public String getProjectderitory() {
		return projectderitory;
	}
	public void setProjectderitory(String projectderitory) {
		this.projectderitory = projectderitory;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getTimecount() {
		return timecount;
	}
	public void setTimecount(String timecount) {
		this.timecount = timecount;
	}
	public List<ClientIpAddress> getList() {
		return list;
	}
	public String getSetenddate() {
		return setenddate;
	}
	public void setSetenddate(String setenddate) {
		this.setenddate = setenddate;
	}
	public String getSetstartdate() {
		return setstartdate;
	}
	public void setSetstartdate(String setstartdate) {
		this.setstartdate = setstartdate;
	}
}
