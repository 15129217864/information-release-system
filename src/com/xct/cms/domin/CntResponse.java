package com.xct.cms.domin;

public class CntResponse implements java.io.Serializable{

	private static final long serialVersionUID = -234748433303060189L;
	private int id;
	private String cnt_ip;
	private String cnt_mac;
	private String cnt_cmd;
	private String cnt_cmdstatus;
	private String cnt_cmdresult;
	private String cnt_programurl;
	private String cnt_sendtime;
	
	
	public String getCnt_cmdstatus() {
		return cnt_cmdstatus;
	}
	public void setCnt_cmdstatus(String cnt_cmdstatus) {
		this.cnt_cmdstatus = cnt_cmdstatus;
	}
	public String getCnt_cmdresultZh() {
		String cnt_cmdstatusZh="";
		if(cnt_cmdstatus.equals("lv0009_OK")){
			cnt_cmdstatusZh="<span style='color:green'><img src='/images/ok.gif' height='16px'>���سɹ���</span>";
		}else if(cnt_cmdstatus.equals("lv0009_ERROR")){
			cnt_cmdstatusZh="<span style='color:red'><img src='/images/error.gif'  height='16px'>�ͻ��˿����������ػ������ߣ�</span>";
		}else if(cnt_cmdstatus.equals("lv0009_ERROR_PROJECT")){
			cnt_cmdstatusZh="<span style='color:red'><img src='/images/error.gif'  height='16px'>��Ŀ���ز�����������������Ͷϵ絼�µĴ���</span>";
		}else if(cnt_cmdstatus.equals("lv0009_NO_MAC_XML")){
			cnt_cmdstatusZh="<span style='color:red'><img src='/images/error.gif'  height='16px'>û�ҵ�MAC��ַ�����ļ���</span>";
		}else if(cnt_cmdstatus.equals("lv0009_MAC_XML_ERROR")){
			cnt_cmdstatusZh="<span style='color:red'><img src='/images/error.gif'  height='16px'>MAC��ַ�����ļ��д�</span>";
		}else if(cnt_cmdstatus.equals("lv0009_MAC_XML_EMPTY")){
			cnt_cmdstatusZh="<span style='color:red'><img src='/images/error.gif'  height='16px'>��Ŀ��Ϊ�գ�</span>";
		}else if(cnt_cmdstatus.equals("lv0009_timeout")){
			cnt_cmdstatusZh="<span style='color:red'><img src='/images/error.gif'  height='16px'>ʱ�䲻�Ի�ʱ�䳬ʱ��</span>";
		}else if(cnt_cmdstatus.equals("lv0009_ftpclose")){
			cnt_cmdstatusZh="<span style='color:red'><img src='/images/error.gif'  height='16px'>ftp���ô������ftp�����ѹرգ�</span>";
		}else if(cnt_cmdstatus.equals("lv0009_NO_OPEN")){
			cnt_cmdstatusZh="<span style='color:red'><img src='/images/error.gif'  height='16px'>ftp����رգ�</span>";
		}
		return cnt_cmdstatusZh;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCnt_ip() {
		return cnt_ip;
	}
	public void setCnt_ip(String cnt_ip) {
		this.cnt_ip = cnt_ip;
	}
	public String getCnt_cmd() {
		return cnt_cmd;
	}
	public void setCnt_cmd(String cnt_cmd) {
		this.cnt_cmd = cnt_cmd;
	}
	public String getCnt_cmdresult() {
		return cnt_cmdresult;
	}
	
	public void setCnt_cmdresult(String cnt_cmdresult) {
		this.cnt_cmdresult = cnt_cmdresult;
	}
	public String getCnt_sendtime() {
		return cnt_sendtime;
	}
	public void setCnt_sendtime(String cnt_sendtime) {
		this.cnt_sendtime = cnt_sendtime;
	}
	public String getCnt_programurl() {
		return cnt_programurl;
	}
	public void setCnt_programurl(String cnt_programurl) {
		this.cnt_programurl = cnt_programurl;
	}
	public String getCnt_mac() {
		return cnt_mac;
	}
	public void setCnt_mac(String cnt_mac) {
		this.cnt_mac = cnt_mac;
	}
	
}
