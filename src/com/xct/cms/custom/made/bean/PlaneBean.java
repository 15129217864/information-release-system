package com.xct.cms.custom.made.bean;

public class PlaneBean {

	private String airline;//����
	private String flight;//����
	private String planetype;//����
	private String planecycle;//ִ�а��
	private String flightstarttime;//���ʱ��
	private String flightendtime;//����ʱ��
	private int inorout;//���ۣ�����
	
	
	public PlaneBean(String airline, String flight, String planetype, String planecycle, String flightstarttime, String flightendtime, int inorout) {
		super();
		// TODO �Զ����ɹ��캯�����
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
		// TODO �Զ����ɹ��캯�����
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
