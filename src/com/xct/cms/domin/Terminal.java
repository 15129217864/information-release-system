package com.xct.cms.domin;

public class Terminal implements java.io.Serializable{
	
	private int cnt_id;
	private String cnt_name;
	private String cnt_ip;
	private String cnt_port;
	private String cnt_mac;
	private String cnt_miaoshu;
	private String cnt_playstyle;
	private String cnt_playstyle_zh;
	
	private String cnt_kjtime;
	private String cnt_gjtime;
	private String cnt_downtime;
	private String cnt_addtime;
	private int cnt_islink;
	private String cnt_islink_zh;
	private String cnt_username;
	private int cnt_zuid;
	private String cnt_nowProgramName;
	private String cnt_status;
	private String cnt_playprojecttring;//当前节目播放的详细配置
	private String client_version;//终端版本号
	private String is_day_download;//判断是否白天下载。1 为可以下载，0 为不能下载
	
	private int zu_id;
	private int zu_pth;
	private String zu_name;
	private int zu_type;
	private String zu_username;
	private int is_share;
	
	
	private long isonlinetime=0l; //在线时间
	
	private String send_type;   ///// 发送数据包的类型   1、HTTP 2、UDP
	
	public String getSend_type() {
		return send_type;
	}
	public void setSend_type(String send_type) {
		this.send_type = send_type;
	}
	public int getIs_share() {
		return is_share;
	}
	public void setIs_share(int is_share) {
		this.is_share = is_share;
	}
	public int getZu_type() {
		return zu_type;
	}
	public void setZu_type(int zu_type) {
		this.zu_type = zu_type;
	}
	public String getZu_username() {
		return zu_username;
	}
	public void setZu_username(String zu_username) {
		this.zu_username = zu_username;
	}
	public void setCnt_islink_zh(String cnt_islink_zh) {
		this.cnt_islink_zh = cnt_islink_zh;
	}
	public String getCnt_status() {
		return cnt_status;
	}
	public void setCnt_status(String cnt_status) {
		this.cnt_status = cnt_status;
	}
	public int getZu_id() {
		return zu_id;
	}
	public void setZu_id(int zu_id) {
		this.zu_id = zu_id;
	}
	public int getZu_pth() {
		return zu_pth;
	}
	public void setZu_pth(int zu_pth) {
		this.zu_pth = zu_pth;
	}
	public String getZu_name() {
		return zu_name;
	}
	public void setZu_name(String zu_name) {
		this.zu_name = zu_name;
	}
	public Terminal() {
		super();
	}
	public int getCnt_id() {
		return cnt_id;
	}
	public void setCnt_id(int cnt_id) {
		this.cnt_id = cnt_id;
	}
	public String getCnt_name() {
		return cnt_name;
	}
	public void setCnt_name(String cnt_name) {
		this.cnt_name = cnt_name;
	}
	public String getCnt_ip() {
		return cnt_ip;
	}
	public void setCnt_ip(String cnt_ip) {
		this.cnt_ip = cnt_ip;
	}
	public String getCnt_port() {
		return cnt_port;
	}
	public void setCnt_port(String cnt_port) {
		this.cnt_port = cnt_port;
	}
	public String getCnt_mac() {
		return cnt_mac;
	}
	public void setCnt_mac(String cnt_mac) {
		this.cnt_mac = cnt_mac;
	}
	public String getCnt_miaoshu() {
		return cnt_miaoshu;
	}
	public void setCnt_miaoshu(String cnt_miaoshu) {
		this.cnt_miaoshu = cnt_miaoshu;
	}
	public String getCnt_playstyle() {
		return cnt_playstyle;
	}
	public void setCnt_playstyle(String cnt_playstyle) {
		this.cnt_playstyle = cnt_playstyle;
	}
	public String getCnt_kjtime() {
		return cnt_kjtime;
	}
	public void setCnt_kjtime(String cnt_kjtime) {
		this.cnt_kjtime = cnt_kjtime;
	}
	public String getCnt_gjtime() {
		return cnt_gjtime;
	}
	public void setCnt_gjtime(String cnt_gjtime) {
		this.cnt_gjtime = cnt_gjtime;
	}
	public String getCnt_downtime() {
		return cnt_downtime;
	}
	public void setCnt_downtime(String cnt_downtime) {
		this.cnt_downtime = cnt_downtime;
	}
	public String getCnt_addtime() {
		return cnt_addtime;
	}
	public void setCnt_addtime(String cnt_addtime) {
		this.cnt_addtime = cnt_addtime;
	}
	
	public int getCnt_islink() {
		return cnt_islink;
	}
	public void setCnt_islink(int cnt_islink) {
		this.cnt_islink = cnt_islink;
	}
	public String getCnt_username() {
		return cnt_username;
	}
	public void setCnt_username(String cnt_username) {
		this.cnt_username = cnt_username;
	}
	
	public int getCnt_zuid() {
		return cnt_zuid;
	}
	public void setCnt_zuid(int cnt_zuid) {
		this.cnt_zuid = cnt_zuid;
	}
	public String getCnt_nowProgramName() {
		return cnt_nowProgramName;
	}
	public void setCnt_nowProgramName(String cnt_nowProgramName) {
		this.cnt_nowProgramName = cnt_nowProgramName;
	}
	public String getCnt_islink_zh() {
		if(cnt_islink==3){
			this.cnt_islink_zh="<span style='color:red'>断开</span>";
		}else{
			if("NULL".equals(cnt_nowProgramName.toUpperCase())){
				this.cnt_islink_zh="<span style='color:red'>休眠</span>";
			}else{
				this.cnt_islink_zh="<span style='color:green'>连接</span>";
			}
		}
		return cnt_islink_zh;
	}
	public String getCnt_playprojecttring() {
		return cnt_playprojecttring;
	}
	public void setCnt_playprojecttring(String cnt_playprojecttring) {
		this.cnt_playprojecttring = cnt_playprojecttring;
	}
	public String getCnt_playstyle_zh() {
		if("PLAYER".equals(cnt_playstyle)||"PLAY".equals(cnt_playstyle)){
			this.cnt_playstyle_zh="<span style='color:green'>播放</span>";
		}else if("DOWNLOAD".equals(cnt_playstyle)){
			this.cnt_playstyle_zh="<span style='color:green'>下载</span>";
		}else if("LOADING".equals(cnt_playstyle)){
			this.cnt_playstyle_zh="<span style='color:green'>加载节目</span>";
		}else{
			this.cnt_playstyle_zh="<span style='color:red'>停止</span>";
		}
		return cnt_playstyle_zh;
	}
	public void setCnt_playstyle_zh(String cnt_playstyle_zh) {
		this.cnt_playstyle_zh = cnt_playstyle_zh;
	}
	public String getClient_version() {
		return client_version;
	}
	public void setClient_version(String client_version) {
		this.client_version = (null==client_version?"未知":client_version);
	}
	public String getIs_day_download() {
		return is_day_download;
	}
	public void setIs_day_download(String is_day_download) {
		this.is_day_download = is_day_download;
	}
	public long getIsonlinetime() {
		return isonlinetime;
	}
	public void setIsonlinetime(long isonlinetime) {
		this.isonlinetime = isonlinetime;
	}
	@Override
	public String toString() {
		return new StringBuffer().append(cnt_mac).append("_")
				.append(cnt_ip).append("_")
				.append(cnt_nowProgramName).append("_")
				.append(cnt_playstyle).append("_")
				.append(cnt_islink).append("_")
				.append(cnt_playprojecttring).append("_")
				.append(client_version).toString();
	}
	
	
}
