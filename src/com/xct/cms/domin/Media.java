package com.xct.cms.domin;

import com.xct.cms.utils.DESPlusUtil;

public class Media implements java.io.Serializable{
	
	private int id;
	private String media_id;
	private String media_title;
	private String media_title_s;////只显示媒体名称的前5个字符
	private String media_type;
	private int zu_id;
	private int group_num;
	private String m_play_time;
	private String m_resolution;
	private String file_name;
	private long file_size;
	private String file_path;
	private String slightly_img_name;
	private long slightly_img_size;
	private String slightly_img_path;
	private String userid;
	private String username;
	private String create_date;
	private String last_date;
	private String filePathEncrypt;///媒体路径加密
	private String fileNameEncrypt;///媒体名称加密
	private String[] allmedia_type;
	private String[] allmedia_type1; ////不包含  “其他”类别
	private String zuname;
	
	private int is_exist; ////是否存在该文件  1，存在， 0 不存在
	
	public int getIs_exist() {
		return is_exist;
	}
	public void setIs_exist(int is_exist) {
		this.is_exist = is_exist;
	}
	public String getFileNameEncrypt() {
		try {
			this.fileNameEncrypt= DESPlusUtil.Get().encrypt(media_title);
		} catch (Exception e) {
		}
		return fileNameEncrypt;
	}
	public void setFileNameEncrypt(String fileNameEncrypt) {
		this.fileNameEncrypt = fileNameEncrypt;
	}
	public String getZuname() {
		return zuname;
	}
	public void setZuname(String zuname) {
		this.zuname = zuname;
	}
	public String getSlightly_img_name() {
		return slightly_img_name;
	}
	public void setSlightly_img_name(String slightly_img_name) {
		this.slightly_img_name = slightly_img_name;
	}

	public long getSlightly_img_size() {
		return slightly_img_size;
	}
	public void setSlightly_img_size(long slightly_img_size) {
		this.slightly_img_size = slightly_img_size;
	}
	public String getSlightly_img_path() {
		return slightly_img_path;
	}
	public void setSlightly_img_path(String slightly_img_path) {
		this.slightly_img_path = slightly_img_path;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getMedia_title() {
		return media_title;
	}
	public void setMedia_title(String media_title) {
		this.media_title = media_title;
	}
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}
	public int getZu_id() {
		return zu_id;
	}
	public void setZu_id(int zu_id) {
		this.zu_id = zu_id;
	}
	public String getM_play_time() {
		return m_play_time;
	}
	public void setM_play_time(String m_play_time) {
		this.m_play_time = m_play_time;
	}
	public String getM_resolution() {
		return m_resolution;
	}
	public void setM_resolution(String m_resolution) {
		this.m_resolution = m_resolution;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	
	
	public long getFile_size() {
		return file_size;
	}
	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getLast_date() {
		return last_date;
	}
	public void setLast_date(String last_date) {
		this.last_date = last_date;
	}
	public String[] getAllmedia_type() {
		this.allmedia_type=new String[] {"MOVIE#视频","FLASH#FLASH","IMAGE#图像","SOUND#声音","PPT#PPT","WORD#WORD","EXCEL#EXCEL","TEXT#文本","WEB#网页","OTHER#其他"};
		return allmedia_type;
	}
	public void setAllmedia_type(String[] allmedia_type) {
		this.allmedia_type = allmedia_type;
	}
	public String getFilePathEncrypt() {
		try {
			this.filePathEncrypt= DESPlusUtil.Get().encrypt(file_path+file_name);
		} catch (Exception e) {
		}
		return filePathEncrypt;
	}
	public void setFilePathEncrypt(String filePathEncrypt) {
		this.filePathEncrypt = filePathEncrypt;
	}
	public String[] getAllmedia_type1() {
		this.allmedia_type1=new String[] {"MOVIE#视频","FLASH#FLASH","IMAGE#图像","PPT#PPT","WORD#WORD","EXCEL#EXCEL","TEXT#文本","WEB#网页"};
		return allmedia_type1;
	}
	public void setAllmedia_type1(String[] allmedia_type1) {
		this.allmedia_type1 = allmedia_type1;
	}
	public int getGroup_num() {
		return group_num;
	}
	public void setGroup_num(int group_num) {
		this.group_num = group_num;
	}
	public String getMedia_title_s() {
		String s=this.media_title;
		int ll=0;
		if(s!=null&&s.length()>0){
			for(int i=0;i<s.length();i++){
				if(s.charAt(i)<255){
					ll++;
				}else{
					ll=ll+2;
				}
			}
		}
		if(s.length()>9){
			this.media_title_s=s.substring(0,10);
		}else{
			this.media_title_s=s;
		}
		return media_title_s;
	}
	public void setMedia_title_s(String media_title_s) {
		this.media_title_s = media_title_s;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
}
