package com.xct.cms.custom.made.bean;

public class BusBean {
	
	private String province;
	private String terminus;//�յ�վ
	private String bustype;//����
	private String kilometre;//����
	private String bushour;//�೵ʱ��
	

	public BusBean(String province, String terminus, String bustype, String kilometre, String bushour) {
		super();
		this.province = province;
		this.terminus = terminus;
		this.bustype = bustype;
		this.kilometre = kilometre;
		this.bushour = bushour;
	}
	public String getBushour() {
		return bushour;
	}
	public void setBushour(String bushour) {
		this.bushour = bushour;
	}
	public String getBustype() {
		return bustype;
	}
	public void setBustype(String bustype) {
		this.bustype = bustype;
	}
	public String getKilometre() {
		return kilometre;
	}
	public void setKilometre(String kilometre) {
		this.kilometre = kilometre;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getTerminus() {
		return terminus;
	}
	public void setTerminus(String terminus) {
		this.terminus = terminus;
	}
	
	
}
