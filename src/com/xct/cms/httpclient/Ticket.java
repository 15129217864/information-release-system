package com.xct.cms.httpclient;

import java.util.List;


public class Ticket {

	private String enableNum; ///������
	private String enterDate; ///�������
	private String limitName; ///��ȿ�����
	private String limitNo; ///��ȿ���
	private String ocuNum; ///������
	private String scrNo; ///���α��
	private String scrNoDesc; ///��������
	private String setNum; ///������
	private String stadNo; ///���ݱ��
	private String stadNoDesc; ///��������
	private String useNum; ///������
	
	private String resNum;  //��ǰ��¼��
	private String totalProperty;  //����
	private String txnResp;  //Ӧ����
	
	private List<Ticket> resRoot;  ///Ʊ����Ϣ����
	
	
	
	public List<Ticket> getResRoot() {
		return resRoot;
	}
	public void setResRoot(List<Ticket> resRoot) {
		this.resRoot = resRoot;
	}
	public String getEnableNum() {
		return enableNum;
	}
	public void setEnableNum(String enableNum) {
		this.enableNum = enableNum;
	}
	public String getEnterDate() {
		return enterDate;
	}
	public void setEnterDate(String enterDate) {
		this.enterDate = enterDate;
	}
	public String getLimitName() {
		return limitName;
	}
	public void setLimitName(String limitName) {
		this.limitName = limitName;
	}
	public String getLimitNo() {
		return limitNo;
	}
	public void setLimitNo(String limitNo) {
		this.limitNo = limitNo;
	}
	public String getOcuNum() {
		return ocuNum;
	}
	public void setOcuNum(String ocuNum) {
		this.ocuNum = ocuNum;
	}
	public String getResNum() {
		return resNum;
	}
	public void setResNum(String resNum) {
		this.resNum = resNum;
	}
	public String getScrNo() {
		return scrNo;
	}
	public void setScrNo(String scrNo) {
		this.scrNo = scrNo;
	}
	public String getScrNoDesc() {
		return scrNoDesc;
	}
	public void setScrNoDesc(String scrNoDesc) {
		this.scrNoDesc = scrNoDesc;
	}
	public String getSetNum() {
		return setNum;
	}
	public void setSetNum(String setNum) {
		this.setNum = setNum;
	}
	public String getStadNo() {
		return stadNo;
	}
	public void setStadNo(String stadNo) {
		this.stadNo = stadNo;
	}
	public String getStadNoDesc() {
		return stadNoDesc;
	}
	public void setStadNoDesc(String stadNoDesc) {
		this.stadNoDesc = stadNoDesc;
	}
	public String getTotalProperty() {
		return totalProperty;
	}
	public void setTotalProperty(String totalProperty) {
		this.totalProperty = totalProperty;
	}
	public String getTxnResp() {
		return txnResp;
	}
	public void setTxnResp(String txnResp) {
		this.txnResp = txnResp;
	}
	public String getUseNum() {
		return useNum;
	}
	public void setUseNum(String useNum) {
		this.useNum = useNum;
	}
	
	
	
}
