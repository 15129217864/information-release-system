package com.xct.cms.domin;

public class Template implements java.io.Serializable{

	private int id;
	private String template_id;
	private String template_name;
	private int template_type;
	private int template_width;
	private int template_height;
	private String template_expla;
	private String template_background;
	private String template_backmusic;
	private String template_create_name;
	private String create_time;
	private int type;
	///*****生成配置文件使用的属性
	private String version;
	private String programname;
	private String rotate;
	private String programePath;
	private String programUrl;
	
	//////*********模板屏幕类型属性
	private int s_id;
	private String s_title;
	private int s_width;
	private int s_height;
	private int all_width;
	private int all_height;
	private int s_type;
	private String htmltextbackimg;//滚动文本的背景图片路径
	private String marketstockbackimg;//张家港股市，大宗商品
	private String ch_enString;
	
	public int getS_type() {
		return s_type;
	}
	public void setS_type(int s_type) {
		this.s_type = s_type;
	}
	public int getAll_height() {
		return all_height;
	}
	public void setAll_height(int all_height) {
		this.all_height = all_height;
	}
	public int getAll_width() {
		return all_width;
	}
	public void setAll_width(int all_width) {
		this.all_width = all_width;
	}
	public int getS_height() {
		return s_height;
	}
	public void setS_height(int s_height) {
		this.s_height = s_height;
	}
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public String getS_title() {
		return s_title;
	}
	public void setS_title(String s_title) {
		this.s_title = s_title;
	}
	public int getS_width() {
		return s_width;
	}
	public void setS_width(int s_width) {
		this.s_width = s_width;
	}
	public String getProgramUrl() {
		return programUrl;
	}
	public void setProgramUrl(String programUrl) {
		this.programUrl = programUrl;
	}
	public String getProgramePath() {
		return programePath;
	}
	public void setProgramePath(String programePath) {
		this.programePath = programePath;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getRotate() {
		return rotate;
	}
	public void setRotate(String rotate) {
		this.rotate = rotate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTemplate_type() {
		return template_type;
	}
	public void setTemplate_type(int template_type) {
		this.template_type = template_type;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getTemplate_create_name() {
		return template_create_name;
	}
	public void setTemplate_create_name(String template_create_name) {
		this.template_create_name = template_create_name;
	}
	
	public String getTemplate_background() {
		return template_background;
	}
	public void setTemplate_background(String template_background) {
		this.template_background = template_background;
	}
	public String getTemplate_backmusic() {
		return template_backmusic;
	}
	public void setTemplate_backmusic(String template_backmusic) {
		this.template_backmusic = template_backmusic;
	}
	public String getTemplate_expla() {
		return template_expla;
	}
	public void setTemplate_expla(String template_expla) {
		this.template_expla = template_expla;
	}
	
	public int getTemplate_height() {
		return template_height;
	}
	public void setTemplate_height(int template_height) {
		this.template_height = template_height;
	}
	public int getTemplate_width() {
		return template_width;
	}
	public void setTemplate_width(int template_width) {
		this.template_width = template_width;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProgramname() {
		return programname;
	}
	public void setProgramname(String programname) {
		this.programname = programname;
	}
	public String getHtmltextbackimg() {
		return htmltextbackimg;
	}
	public void setHtmltextbackimg(String htmltextbackimg) {
		this.htmltextbackimg = htmltextbackimg;
	}
	public String getMarketstockbackimg() {
		return marketstockbackimg;
	}
	public void setMarketstockbackimg(String marketstockbackimg) {
		this.marketstockbackimg = marketstockbackimg;
	}
	public String getCh_enString() {
		return ch_enString;
	}
	public void setCh_enString(String ch_enString) {
		this.ch_enString = ch_enString;
	}
	
	
}
