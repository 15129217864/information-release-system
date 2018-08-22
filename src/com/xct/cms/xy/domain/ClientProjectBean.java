package com.xct.cms.xy.domain;


public class ClientProjectBean {
    
	private String projectid;//节目id号
	private String jmid;
	private String playclient;
	private String jmurl; //节目文件夹  ---> program_JMurl
	private String name;//节目名称      ---> program_Name
	private String setdate;//开始时间 （datetime）  ---> program_SetDate
	private String enddate;//结束时间（datetime）   ---> program_EndDate
	private String clientip;//播放器ip(查出终端名称)  ---> program_ip
	private String isloop;//播放类型 循环，定时，插播（int）  ---> program_ISloop  0-循环，1-插播，2-定时
	private String playsecond;//播放多长时间（int）  ---> program_PlaySecond    int
//	private String username;//操作员         ---> program_ggs
	private String playtype;
	private String playtypeZh;
	private String foroverloop;
	private String mac;
	
	private int jmappid;
	
	private String program_delid;//终端MAC文件
	
	public ClientProjectBean(String jmid,String playclient,String jmurl, String name, String setdate, String enddate, String isloop, String playsecond, String clientip,String playtype/*, String username*/,String program_delid) {
		this.jmid=jmid;
		this.playclient=playclient;
		this.jmurl = jmurl;
		this.name = name;
		this.setdate = setdate;
		this.enddate = enddate;
		this.playsecond = playsecond;
		this.isloop = isloop;
		this.clientip = clientip;
		//this.playtype=playtype;
//		this.playtypeZh = playtypeZh;
		this.program_delid=program_delid;
	}
	
	public ClientProjectBean(String jmid,String projectid,String playclient,String jmurl, String name, String setdate, String enddate, String isloop, String playsecond, String clientip,String playtype/*, String username*/,String program_delid) {
		this.jmid=jmid;
		this.projectid=projectid;
		this.playclient=playclient;
		this.jmurl = jmurl;
		this.name = name;
		this.setdate = setdate;
		this.enddate = enddate;
		this.playsecond = playsecond;
		this.isloop = isloop;
		this.clientip = clientip;
		this.playtype=playtype;
//		this.username = username;
		getPlaytype();
	}
	
	public ClientProjectBean(String name,String jmurl,String setdate,String playtype, String playsecond ) {
		
		this.name = name;
		this.jmurl = jmurl;
		this.setdate = setdate;
		this.playtype=playtype;
		this.playsecond = playsecond;
	}
	
	public ClientProjectBean() {
		// TODO Auto-generated constructor stub
	}


	public String getClientip() {
		return clientip;
	}
	public void setClientip(String clientip) {
		this.clientip = clientip;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getIsloop() {
		return isloop;
	}
	public void setIsloop(String isloop) {
		this.isloop = isloop;
	}
	public String getJmurl() {
		return jmurl;
	}
	public void setJmurl(String jmurl) {
		this.jmurl = jmurl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlaysecond() {
		return playsecond;
	}
	public void setPlaysecond(String playsecond) {
		this.playsecond = playsecond;
	}
	public String getSetdate() {
		return setdate;
	}
	public void setSetdate(String setdate) {
		this.setdate = setdate;
	}
//	public String getUsername() {
//		return username;
//	}
//	public void setUsername(String username) {
//		this.username = username;
//	}
	public String getPlayclient() {
		return playclient;
	}
	public void setPlayclient(String playclient) {
		this.playclient = playclient;
	}


	public String getPlaytype() {
		if(isloop.equals("0"))
			this.playtype="loop";
		else if(isloop.equals("1"))
			this.playtype="insert";
		else if(isloop.equals("2"))
			this.playtype="active";
		else if(isloop.equals("3"))
			this.playtype="defaultloop";
		return this.playtype;
	}

	public String getPlaytypebyIsloop(int isloop) {
		String playtype="";
		if(isloop==0)
			playtype="loop";
		else if(isloop==1)
			playtype="insert";
		else if(isloop==2)
			playtype="active";
		else if(isloop==3)
			playtype="defaultloop";
		return playtype;
	}
	public void setPlaytype(String playtype) {
		this.playtype = playtype;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public String getJmid() {
		return jmid;
	}

	public void setJmid(String jmid) {
		this.jmid = jmid;
	}

	public String getPlaytypeZh() {
		if(isloop.equals("0"))
			this.playtypeZh="定时循环";
		else if(isloop.equals("1"))
			this.playtypeZh="插播";
		else if(isloop.equals("2"))
			this.playtypeZh="定时";
		else if(isloop.equals("3"))
			this.playtypeZh="永久循环";
		return this.playtypeZh;
	}

	public void setPlaytypeZh(String playtypeZh) {
		this.playtypeZh = playtypeZh;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ClientProjectBean) {
			ClientProjectBean temp = (ClientProjectBean) obj;
		//	System.out.println(temp.toString()  +"========"+this.toString());
			if (temp.toString().equalsIgnoreCase(this.toString()))
				return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return name + "#" + jmurl + "#" + setdate + "#" + playtype+ "#" + playsecond;
	}
	public boolean equals1(Object obj) {
		if (obj instanceof ClientProjectBean) {
			ClientProjectBean temp = (ClientProjectBean) obj;
			if (temp.toString1().equalsIgnoreCase(this.toString1()))
				return true;
		}
		return false;
	}
	public String toString1() {
		return name + "#" + jmurl  + "#" + playtype+ "#" ;
	}
	

	public String getProgram_delid() {
		return program_delid;
	}

	public void setProgram_delid(String program_delid) {
		this.program_delid = program_delid;
	}

	public String getForoverloop() {
		return foroverloop;
	}

	public void setForoverloop(String foroverloop) {
		this.foroverloop = foroverloop;
	}

	public int getJmappid() {
		return jmappid;
	}

	public void setJmappid(int jmappid) {
		this.jmappid = jmappid;
	}
	
}
