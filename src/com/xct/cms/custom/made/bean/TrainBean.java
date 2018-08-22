package com.xct.cms.custom.made.bean;

public class TrainBean {

	private String chenumber;//车次
	private String shifazhang;//始发站
	private String zhongdianzhan;//终点站
	private String cheliangtype;//车辆类型
	private String fazhan;//发站
	private String fashi;//发时
	private String daozhan;//到站
	private String daoshi;//到时
	private String tingzhan;//停站
	private String lishi;//历时
	private String yingzuo;//硬座
	private String ruanzuo;//软座
	private String yingwozhong;//硬卧中
	private String ruanwoxia;//软卧下

	
	public TrainBean(String chenumber, String shifazhang, String zhongdianzhan, String cheliangtype, String fazhan, String fashi, String daozhan, String daoshi, String tingzhan, String lishi, String yingzuo, String ruanzuo, String yingwozhong, String ruanwoxia) {
		super();
		this.chenumber = chenumber;
		this.shifazhang = shifazhang;
		this.zhongdianzhan = zhongdianzhan;
		this.cheliangtype = cheliangtype;
		this.fazhan = fazhan;
		this.fashi = fashi;
		this.daozhan = daozhan;
		this.daoshi = daoshi;
		this.tingzhan = tingzhan;
		this.lishi = lishi;
		this.yingzuo = yingzuo;
		this.ruanzuo = ruanzuo;
		this.yingwozhong = yingwozhong;
		this.ruanwoxia = ruanwoxia;
	}

	public TrainBean() {
		super();
	}
	
	public String getCheliangtype() {
		return cheliangtype;
	}
	public void setCheliangtype(String cheliangtype) {
		this.cheliangtype = cheliangtype;
	}
	public String getChenumber() {
		return chenumber;
	}
	public void setChenumber(String chenumber) {
		this.chenumber = chenumber;
	}
	public String getDaoshi() {
		return daoshi;
	}
	public void setDaoshi(String daoshi) {
		this.daoshi = daoshi;
	}
	public String getDaozhan() {
		return daozhan;
	}
	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
	}
	public String getFashi() {
		return fashi;
	}
	public void setFashi(String fashi) {
		this.fashi = fashi;
	}
	public String getFazhan() {
		return fazhan;
	}
	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
	}
	public String getLishi() {
		return lishi;
	}
	public void setLishi(String lishi) {
		this.lishi = lishi;
	}
	public String getRuanwoxia() {
		return ruanwoxia;
	}
	public void setRuanwoxia(String ruanwoxia) {
		this.ruanwoxia = ruanwoxia;
	}
	public String getRuanzuo() {
		return ruanzuo;
	}
	public void setRuanzuo(String ruanzuo) {
		this.ruanzuo = ruanzuo;
	}
	public String getShifazhang() {
		return shifazhang;
	}
	public void setShifazhang(String shifazhang) {
		this.shifazhang = shifazhang;
	}
	public String getTingzhan() {
		return tingzhan;
	}
	public void setTingzhan(String tingzhan) {
		this.tingzhan = tingzhan;
	}
	public String getYingwozhong() {
		return yingwozhong;
	}
	public void setYingwozhong(String yingwozhong) {
		this.yingwozhong = yingwozhong;
	}
	public String getYingzuo() {
		return yingzuo;
	}
	public void setYingzuo(String yingzuo) {
		this.yingzuo = yingzuo;
	}
	public String getZhongdianzhan() {
		return zhongdianzhan;
	}
	public void setZhongdianzhan(String zhongdianzhan) {
		this.zhongdianzhan = zhongdianzhan;
	}
	
}
