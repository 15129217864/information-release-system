package com.xct.cms.domin;

public class Logs implements java.io.Serializable{

	private static final long serialVersionUID = 4741184068649334721L;
	
	private int logid;
	private String loguser;
	private String logdate;
	private String logtime;
	private String loglog;
	private int logdel;
	
	
	public int getLogid() {
		return logid;
	}
	public void setLogid(int logid) {
		this.logid = logid;
	}
	public String getLoguser() {
		return loguser;
	}
	public void setLoguser(String loguser) {
		this.loguser = loguser;
	}
	public String getLogdate() {
		return logdate;
	}
	public void setLogdate(String logdate) {
		this.logdate = logdate;
	}
	public String getLogtime() {
		return logtime;
	}
	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}
	public String getLoglog() {
		return loglog;
	}
	public void setLoglog(String loglog) {
		this.loglog = loglog;
	}
	public int getLogdel() {
		return logdel;
	}
	public void setLogdel(int logdel) {
		this.logdel = logdel;
	}
	
	
	
}
