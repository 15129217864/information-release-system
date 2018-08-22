package com.xct.cms.custom.made.bean;

public class PlaneBean {

	private String airline;//航线
	private String flight;//航班
	private String planetype;//机型
	private String planecycle;//执行班次
	private String flightstarttime;//起飞时间
	private String flightendtime;//到达时间
	private int inorout;//出港，进港
	
	
	public PlaneBean(String airline, String flight, String planetype, String planecycle, String flightstarttime, String flightendtime, int inorout) {
		super();
		// TODO 自动生成构造函数存根
		this.airline = airline;
		this.flight = flight;
		this.planetype = planetype;
		this.planecycle = planecycle;
		this.flightstarttime = flightstarttime;
		this.flightendtime = flightendtime;
		this.inorout = inorout;
	}
	public PlaneBean() {
		super();
		// TODO 自动生成构造函数存根
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getFlight() {
		return flight;
	}
	public void setFlight(String flight) {
		this.flight = flight;
	}
	public String getFlightendtime() {
		return flightendtime;
	}
	public void setFlightendtime(String flightendtime) {
		this.flightendtime = flightendtime;
	}
	public String getFlightstarttime() {
		return flightstarttime;
	}
	public void setFlightstarttime(String flightstarttime) {
		this.flightstarttime = flightstarttime;
	}
	public int getInorout() {
		return inorout;
	}
	public void setInorout(int inorout) {
		this.inorout = inorout;
	}
	public String getPlanecycle() {
		return planecycle;
	}
	public void setPlanecycle(String planecycle) {
		this.planecycle = planecycle;
	}
	public String getPlanetype() {
		return planetype;
	}
	public void setPlanetype(String planetype) {
		this.planetype = planetype;
	}
	
	
}
