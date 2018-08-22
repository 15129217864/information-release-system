package com.xct.cms.domin;


public class ProjectBean implements java.io.Serializable{

	private String userName, directName,beginTimer,endTimer, type,length;
	private int jmappid;
	private String startdate,starttime,enddate,endtimetime,templateid;
	
	public ProjectBean(String userName, String directName, String startdate, String enddate,String starttime,  String endtimetime, String type, String length,String templateid) {
		
		this.userName = userName;
		this.directName = directName;
		this.type = type;
		this.length = length;
		this.startdate = startdate;
		this.starttime = starttime;
		this.enddate = enddate;
		this.endtimetime = endtimetime;
		this.templateid=templateid;
	}

	public int getJmappid() {
		return jmappid;
	}

	public void setJmappid(int jmappid) {
		this.jmappid = jmappid;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}


	public String getEndtimetime() {
		return endtimetime;
	}

	public void setEndtimetime(String endtimetime) {
		this.endtimetime = endtimetime;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public ProjectBean(String userName, String directName, String beginTimer, String type, String length) {
		this.userName = userName;
		this.directName = directName;
		this.beginTimer = beginTimer;
		this.type = type;
		this.length = length;
	}
	public ProjectBean(String userName, String directName, String beginTimer, String type, String length,int jmappid) {
		this.userName = userName;
		this.directName = directName;
		this.beginTimer = beginTimer;
		this.type = type;
		this.length = length;
		this.jmappid=jmappid;
	}
	public ProjectBean(String userName, String directName, String beginTimer,String endTimer, String type, String length) {
		this.userName = userName;
		this.directName = directName;
		this.beginTimer = beginTimer;
		this.endTimer=endTimer;
		this.type = type;
		this.length = length;
	}

	public String getProjectType(){
		if(this.type.equals("defaultloop"))
			return "永久循环";
		if(this.type.equals("loop"))
			return "定时循环";
		if(this.type.equals("insert"))
			return "插播";
		if(this.type.equals("active"))
			return "定时";
		return "";
	}
	
	public String getBeginTimer() {
		return beginTimer;
	}

	public void setBeginTimer(String beginTimer) {
		this.beginTimer = beginTimer;
	}

	public String getDirectName() {
		return directName;
	}

	public void setDirectName(String directName) {
		this.directName = directName;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProjectBean) {
			ProjectBean temp = (ProjectBean) obj;
			if (temp.toString().equalsIgnoreCase(this.toString()))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return userName + "#" + directName + "#" + beginTimer + "#" + type+ "#" + length;
	}

	public String getEndTimer() {
		return endTimer;
	}

	public void setEndTimer(String endTimer) {
		this.endTimer = endTimer;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}
}
