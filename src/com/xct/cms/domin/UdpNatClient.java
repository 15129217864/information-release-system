package com.xct.cms.domin;

public class UdpNatClient {

	private String ip;
	private String mac;
	private String port;
	
	public UdpNatClient() {
		super();
	}
	public UdpNatClient(String ip, String mac, String port) {
		super();
		this.ip = ip;
		this.mac = mac;
		this.port = port;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	
	
	
	
}
