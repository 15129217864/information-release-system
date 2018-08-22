package com.xct.cms.xy.domain;

public class ClientIpAddress {
	private String cntzuid;
	private String cntip;
	private String cntname;
	private String cntmac;
	
	public ClientIpAddress(String cntzuid, String cntip, String cntname,String cntmac) {
		this.cntzuid = cntzuid;
		this.cntip = cntip;
		this.cntname = cntname;
		this.cntmac=cntmac;
	}
	
	public String getCntip() {
		return cntip;
	}
	public void setCntip(String cntip) {
		this.cntip = cntip;
	}
	public String getCntname() {
		return cntname;
	}
	public void setCntname(String cntname) {
		this.cntname = cntname;
	}
	public String getCntzuid() {
		return cntzuid;
	}
	public void setCntzuid(String cntzuid) {
		this.cntzuid = cntzuid;
	}
	public String getCntmac() {
		return cntmac;
	}
	public void setCntmac(String cntmac) {
		this.cntmac = cntmac;
	}
}
