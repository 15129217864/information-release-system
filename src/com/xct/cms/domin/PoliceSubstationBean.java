package com.xct.cms.domin;

public class PoliceSubstationBean {//奉浦派出所

 private    int id;
 private	String seqno;
 private	String roomno;
 private	String casecode;//编号
 private	String policename; //警员
 private	String casecharacter;//案件性质
 private	String suspect;//犯罪嫌疑人
 private    String clientip;//终端IP地址
 
public PoliceSubstationBean(int id,String seqno, String roomno, String casecode, String policename, String casecharacter, String suspect, String clientip) {
	super();
	this.id = id;
	this.seqno= seqno;
	this.roomno = roomno.equals("no")?"":roomno;
	this.casecode = casecode.equals("no")?"":casecode;
	this.policename = policename.equals("no")?"":policename;
	this.casecharacter = casecharacter.equals("no")?"":casecharacter;
	this.suspect = suspect.equals("no")?"":suspect;
	this.clientip = clientip;
}

public PoliceSubstationBean(String roomno,String seqno, String casecode, String policename, String casecharacter, String suspect, String clientip) {
	super();
	this.seqno= seqno;
	this.roomno = roomno.equals("no")?"":roomno;
	this.casecode = casecode.equals("no")?"":casecode;
	this.policename = policename.equals("no")?"":policename;
	this.casecharacter = casecharacter.equals("no")?"":casecharacter;
	this.suspect = suspect.equals("no")?"":suspect;
	this.clientip = clientip.equals("no")?"":clientip;
}

public PoliceSubstationBean(String roomno, String casecode, String policename, String casecharacter, String suspect) {
	super();
	this.roomno = roomno.equals("no")?"":roomno;
	this.casecode = casecode.equals("no")?"":casecode;
	this.policename = policename.equals("no")?"":policename;
	this.casecharacter = casecharacter.equals("no")?"":casecharacter;
	this.suspect = suspect.equals("no")?"":suspect;
}

public String getCasecharacter() {
	return casecharacter;
}
public void setCasecharacter(String casecharacter) {
	this.casecharacter = casecharacter;
}
public String getCasecode() {
	return casecode;
}
public void setCasecode(String casecode) {
	this.casecode = casecode;
}

public String getPolicename() {
	return policename;
}
public void setPolicename(String policename) {
	this.policename = policename;
}
public String getRoomno() {
	return roomno;
}
public void setRoomno(String roomno) {
	this.roomno = roomno;
}
public String getSuspect() {
	return suspect;
}
public void setSuspect(String suspect) {
	this.suspect = suspect;
}
public String getClientip() {
	return clientip;
}
public void setClientip(String clientip) {
	this.clientip = clientip;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getSeqno() {
	return seqno;
}

public void setSeqno(String seqno) {
	this.seqno = seqno;
}
 
}
