package com.xct.cms.domin;

import java.util.ArrayList;
import java.util.List;

public class Module implements java.io.Serializable{
	
	private String program_name;
	private int id;
	private int area_id;
	private int m_left;
	private int m_top;
	private int m_width;
	private int m_height;
	private String m_filetype;
	private String m_other;
	private String m_text;
	private String template_id;
	private String m_play_time;
	
	private int module_id;
	private int type;
	
	private String otherparam;
	
	private String media_id;
	private String media_title;
	private String media_type;
	
	private String m_filetypeEN;
	private String m_filetypeZN;
	private int state;
	private String slightly_img_path;
	//	/*****���������ļ�ʹ�õ�����
	private String name;
	private String background;
	private String foreground;
	private String span;
	private String fontName;
	private String fontTyle;
	private String fontSize;
	private String alpha;
////**********����Ԥ��ʹ������
	private String weatherfile;
	private String weather_image;
	private String index;
	private String back_image;
	private String font_color;
	private String weather_wind;
	private String weather_date;
	private String start_temperature;
	private String end_temperature;
	private String city;
//////********��������ʹ������
	private String scroll_file;
	private String scroll_fg;
	private String scroll_bg;
	private String scroll_content;
	private String []fontsizes;
/////**********��ý�����ҳ��������
	private String iwfile;   
	private String iw_content;  //��ҳ��ַ
	private String iptv_path;  //��ý���ַ
	private String count_downtxt;//����ʱ��ֹ����
	
	//////***************PPTʹ������
	private List<String> pptfile =new ArrayList<String>();
	
////***��������
	private String [] winds;
	private String [] temperatures;
	private String [] mtypes;
	private int sequence;///����ʹ��
	private List<Media> mediaList;
	
	
	/////////************************������������
	////////////webģ��
	private String w_webContent1;
	private String w_webContent2;
	private String w_webContent3;
	private String w_webContent4;
	private String w_webContent5;
	////////////���������죩
	/////weather1221.xml#�Ϻ�#1#a0.png#0��#0��#2#a0.png#0��#0��#3#a0.png#0��#0��
	private String [] w_temperatures;
	private String w_weatherfile;
	private String w_city;
	private String w_id1;
	private String w_id2;
	private String w_id3;
	private String w_image1;
	private String w_image2;
	private String w_image3;
	private String w_start_temperature1;
	private String w_start_temperature2;
	private String w_start_temperature3;
	private String w_end_temperature1;
	private String w_end_temperature2;
	private String w_end_temperature3;
	private String w_font_color;
	
////////////////��Ʊ
	private String stock_file;
	private int isadd;
	private String now_price;
	private String now_Increase;
	private String now_Increase_percent;
	
	
	
	
	
public Module() {
		super();
		// TODO Auto-generated constructor stub
	}

	/////////////////���õ��ļ�����
	private String[] want_files;
	
	public String[] getWant_files() {
		if(m_other.indexOf("#")>-1){
			String ss=m_other.split("#")[0];
			if(ss.indexOf("@")>-1){
				this.want_files=ss.split("@");
			}
		}
		return want_files;
	}

	public void setWant_files(String[] want_files) {
		this.want_files = want_files;
	}

	public int getIsadd() {
		if(m_other.indexOf("#")>-1){
			String ss=m_other.split("#")[1].substring(0,1);
			if("+".equals(ss)){
				this.isadd=1;
			}else{
				this.isadd=0;
			}
		}
		return isadd;
	}

	public void setIsadd(int isadd) {
		this.isadd = isadd;
	}

	public String getStock_file() {
		if(m_other.indexOf("#")>-1){
			this.stock_file=m_other.split("#")[0];
		}
		return stock_file;
	}

	public void setStock_file(String stock_file) {
		this.stock_file = stock_file;
	}

	public String getNow_Increase() {
		if(m_other.indexOf("#")>-1){
			this.now_Increase=m_other.split("#")[2];
		}
		return now_Increase;
	}

	public void setNow_Increase(String now_Increase) {
		this.now_Increase = now_Increase;
	}

	public String getNow_Increase_percent() {
		if(m_other.indexOf("#")>-1){
			this.now_Increase_percent=m_other.split("#")[3];
		}
		return now_Increase_percent;
	}

	public void setNow_Increase_percent(String now_Increase_percent) {
		this.now_Increase_percent = now_Increase_percent;
	}

	public String getNow_price() {
		if(m_other.indexOf("#")>-1){
			this.now_price=m_other.split("#")[1].substring(1);
		}
		return now_price;
	}

	public void setNow_price(String now_price) {
		this.now_price = now_price;
	}

	public String[] getW_temperatures() {
		this.w_temperatures= new String[] {"-20��","-19��","-18��","-17��","-16��","-15��","-14��","-13��","-12��",
				"-11��","-10��","-9��","-8��","-7��","-6��","-5��","-4��","-3��","-2��","-1��","0��",
				"1��","2��","3��","4��","5��","6��","7��","8��","9��","10��","11��","12��","13��","14��","15��","16��","17��","18��","19��","20��",
				"20��","21��","22��","23��","24��","25��","26��","27��","28��","29��","30��",
				"30��","31��","32��","33��","34��","35��","36��","37��","38��","39��","40��"};
		
		return w_temperatures;
	}

	public void setW_temperatures(String[] w_temperatures) {
		this.w_temperatures = w_temperatures;
	}

	public String getW_city() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_city=m_other.split("#")[1];
		}
		return w_city;
	}

	public void setW_city(String w_city) {
		this.w_city = w_city;
	}

	public String getW_end_temperature1() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_end_temperature1=m_other.split("#")[5];
		}
		return w_end_temperature1;
	}

	public void setW_end_temperature1(String w_end_temperature1) {
		this.w_end_temperature1 = w_end_temperature1;
	}

	public String getW_end_temperature2() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_end_temperature2=m_other.split("#")[9];
		}
		return w_end_temperature2;
	}

	public void setW_end_temperature2(String w_end_temperature2) {
		this.w_end_temperature2 = w_end_temperature2;
	}

	public String getW_end_temperature3() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_end_temperature3=m_other.split("#")[13];
		}
		return w_end_temperature3;
	}

	public void setW_end_temperature3(String w_end_temperature3) {
		this.w_end_temperature3 = w_end_temperature3;
	}

	public String getW_id1() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_id1=m_other.split("#")[2];
		}
		return w_id1;
	}

	public void setW_id1(String w_id1) {
		this.w_id1 = w_id1;
	}

	public String getW_id2() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_id2=m_other.split("#")[6];
		}
		return w_id2;
	}

	public void setW_id2(String w_id2) {
		this.w_id2 = w_id2;
	}

	public String getW_id3() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_id3=m_other.split("#")[10];
		}
		return w_id3;
	}

	public void setW_id3(String w_id3) {
		this.w_id3 = w_id3;
	}

	public String getW_image1() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_image1=m_other.split("#")[3];
		}
		return w_image1;
	}

	public void setW_image1(String w_image1) {
		this.w_image1 = w_image1;
	}

	public String getW_image2() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_image2=m_other.split("#")[7];
		}
		return w_image2;
	}

	public void setW_image2(String w_image2) {
		this.w_image2 = w_image2;
	}

	public String getW_image3() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_image3=m_other.split("#")[11];
		}
		return w_image3;
	}

	public void setW_image3(String w_image3) {
		this.w_image3 = w_image3;
	}

	public String getW_start_temperature1() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_start_temperature1=m_other.split("#")[4];
		}
		return w_start_temperature1;
	}

	public void setW_start_temperature1(String w_start_temperature1) {
		this.w_start_temperature1 = w_start_temperature1;
	}

	public String getW_start_temperature2() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_start_temperature2=m_other.split("#")[8];
		}
		return w_start_temperature2;
	}

	public void setW_start_temperature2(String w_start_temperature2) {
		
		this.w_start_temperature2 = w_start_temperature2;
	}

	public String getW_start_temperature3() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_start_temperature3=m_other.split("#")[12];
		}
		return w_start_temperature3;
	}

	public void setW_start_temperature3(String w_start_temperature3) {
		this.w_start_temperature3 = w_start_temperature3;
	}

	public String getW_weatherfile() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_weatherfile=m_other.split("#")[0];
		}
		return w_weatherfile;
	}

	public void setW_weatherfile(String w_weatherfile) {
		this.w_weatherfile = w_weatherfile;
	}

	public String getW_webContent1() {
		if(m_other.indexOf("#")>-1){
			String content=m_other.split("#")[1];
			this.w_webContent1=content.split("@")[0];
		}else{
			this.w_webContent1="http://";
		}
		return w_webContent1;
	}

	public void setW_webContent1(String content1) {
		w_webContent1 = content1;
	}

	public String getW_webContent2() {
		if(m_other.indexOf("#")>-1){
			String content=m_other.split("#")[1];
			this.w_webContent2=content.split("@")[1];
		}else{
			this.w_webContent2="http://";
		}
		return w_webContent2;
	}

	public void setW_webContent2(String content2) {
		w_webContent2 = content2;
	}

	public String getW_webContent3() {
		if(m_other.indexOf("#")>-1){
			String content=m_other.split("#")[1];
			this.w_webContent3=content.split("@")[2];
		}else{
			this.w_webContent3="http://";
		}
		return w_webContent3;
	}

	public void setW_webContent3(String content3) {
		w_webContent3 = content3;
	}

	public String getW_webContent4() {
		if(m_other.indexOf("#")>-1){
			String content=m_other.split("#")[1];
			this.w_webContent4=content.split("@")[3];
		}else{
			this.w_webContent4="http://";
		}
		return w_webContent4;
	}

	public void setW_webContent4(String content4) {
		w_webContent4 = content4;
	}

	public String getW_webContent5() {
		if(m_other.indexOf("#")>-1){
			String content=m_other.split("#")[1];
			this.w_webContent5=content.split("@")[4];
		}else{
			this.w_webContent5="http://";
		}
		return w_webContent5;
	}

	public void setW_webContent5(String content5) {
		w_webContent5 = content5;
	}

	public int getArea_id() {
		return area_id;
	}

	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}

	public List<Media> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<Media> mediaList) {
		this.mediaList = mediaList;
	}

	public String getAlpha() {
		return alpha;
	}

	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public String getFontTyle() {
		return fontTyle;
	}

	public void setFontTyle(String fontTyle) {
		this.fontTyle = fontTyle;
	}

	public String getForeground() {
		return foreground;
	}

	public void setForeground(String foreground) {
		this.foreground = foreground;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpan() {
		return span;
	}

	public void setSpan(String span) {
		this.span = span;
	}

	public String getM_filetypeZN() {
		if("image".equals(m_filetype)){
			this.m_filetypeZN="ͼƬ";
		}else if("video".equals(m_filetype)){
			this.m_filetypeZN="��Ƶ";
		}else if("flash".equals(m_filetype)){
			this.m_filetypeZN="FLASH";
		}else if("scroll".equals(m_filetype)){
			this.m_filetypeZN="��������";
		}else if("ppt".equals(m_filetype)){
			this.m_filetypeZN="PPT";
		}else if("word".equals(m_filetype)){
			this.m_filetypeZN="WORD";
		}else if("excel".equals(m_filetype)){
			this.m_filetypeZN="EXCEL";
		}else if("iptv".equals(m_filetype)){
			this.m_filetypeZN="��ý��";
		}else if("weather".equals(m_filetype)){
			this.m_filetypeZN="����Ԥ��";
		}else if("clock".equals(m_filetype)){
			this.m_filetypeZN="ʱ��";
		}else if("htmltext".equals(m_filetype)){
			this.m_filetypeZN="�����ı�";
		}else if("dateother".equals(m_filetype)){
			this.m_filetypeZN="����";
			
		}else if("pdf".equals(m_filetype)){
			this.m_filetypeZN="PDF";
			
		}else if("policesubstation".equals(m_filetype)){
			this.m_filetypeZN="Ѷ������Ϣ";
			
		}else if("exe".equals(m_filetype)){
			this.m_filetypeZN="EXE";
			
		}else if("marketstock".equals(m_filetype)){
			this.m_filetypeZN="����,������Ʒ,���";
			
		}else if("khmeetingnotice".equals(m_filetype)){
			this.m_filetypeZN="���Ի�չ���";
			
		}else if("goldtrend".equals(m_filetype)){
			this.m_filetypeZN="�ƽ�����ͼ";
		
		}else if("backgroundmusic".equals(m_filetype)){
			this.m_filetypeZN="����";
		}else if("htmeetingnotice".equals(m_filetype)){
			this.m_filetypeZN="����Ԥ����Ϣ";
			
		}else if("nordermeeting".equals(m_filetype)){
			this.m_filetypeZN="��Ԥ��������";
			
		}else if("insnquametting".equals(m_filetype)){
			this.m_filetypeZN="������Ϣ";//�ֶ�������߾ֻ���
			
		}else if("baononglift".equals(m_filetype)){
			this.m_filetypeZN="��¡����";//
		}else if("weathersimple".equals(m_filetype)){
			this.m_filetypeZN="�������Ԥ��";//
		}else if("kingentranceguard".equals(m_filetype)){
			this.m_filetypeZN="IC��ˢ����Ϣ";//
		}else if("nbqueuing".equals(m_filetype)){
			this.m_filetypeZN="�Ŷӽк���Ϣ";//����
		}else if("nbqueuingmore".equals(m_filetype)){
			this.m_filetypeZN="�Ŷӽк�(�ۺ���)";//����
			
		}else if("count_down".equals(m_filetype)){
			this.m_filetypeZN="���յ���ʱ";
		}
		
		
		
		//else if("web".equals(m_filetype)){
			//this.m_filetypeZN="��ҳ";
		//}  ��������������һ��WEB�����ﲻ��Ҫ
		//////////��������
		/*else if("web".equals(m_filetype)){
			this.m_filetypeZN="����--WEBģ��";
		}else if("dateother".equals(m_filetype)){
			this.m_filetypeZN="����--����";
		}else if("date".equals(m_filetype)){
			this.m_filetypeZN="����--����(5¥)";
		}else if("weatherthree".equals(m_filetype)){
			this.m_filetypeZN="����--����(����)";
		}else if("birthday".equals(m_filetype)){
			this.m_filetypeZN="����--Ա������";
		}else if("stock".equals(m_filetype)){
			this.m_filetypeZN="����--��Ʊ";
		}else if("stockother".equals(m_filetype)){
			this.m_filetypeZN="����--��Ʊ��no-k��";
		}else if("wwnotice".equals(m_filetype)){
			this.m_filetypeZN="����--����֪ͨ";
		}else if("wwmilkdrink".equals(m_filetype)){
			this.m_filetypeZN="����--��������";
		}else if("filialeSell".equals(m_filetype)){
			this.m_filetypeZN="����--�ֹ�˾��������";
		}*/
		///////////////////////
		
		
		else{
			this.m_filetypeZN="��";
		}
		
		
		return m_filetypeZN;
	}

	public void setM_filetypeZN(String m_filetypezn) {
		m_filetypeZN = m_filetypezn;
	}

	public String getM_filetypeEN() {
		if("image".equals(m_filetype)){
			this.m_filetypeEN="IMAGE";
		}else if("video".equals(m_filetype)){
			this.m_filetypeEN="MOVIE";
		}else if("flash".equals(m_filetype)){
			this.m_filetypeEN="FLASH";
		}else if("scroll".equals(m_filetype)){
			this.m_filetypeEN="TEXT";
		}else if("htmltext".equals(m_filetype)){
			this.m_filetypeEN="WEB";
		}else if("ppt".equals(m_filetype)){
			this.m_filetypeEN="PPT";
		}else if("word".equals(m_filetype)){
			this.m_filetypeEN="WORD";
		}else if("excel".equals(m_filetype)){
			this.m_filetypeEN="EXCEL";
			
		}else if("pdf".equals(m_filetype)){
			this.m_filetypeEN="PDF";
			
		}else if("policesubstation".equals(m_filetype)){
			this.m_filetypeZN="POLICESUBSTATION";
			
		}else if("exe".equals(m_filetype)){
			this.m_filetypeEN="EXE";
		}else if("marketstock".equals(m_filetype)){
			this.m_filetypeEN="MARKETSTOCK";
		}else if("khmeetingnotice".equals(m_filetype)){
			this.m_filetypeEN="KHMEETINGNOTICE";
			
		}else if("goldtrend".equals(m_filetype)){
			this.m_filetypeEN="GOLDTREND";
		}
		else if("backgroundmusic".equals(m_filetype)){
			this.m_filetypeEN="BACKGROUNDMUSIC";
			
		}else if("htmeetingnotice".equals(m_filetype)){
			this.m_filetypeEN="HTMEETINGNOTICE";
			
		}else if("nordermeeting".equals(m_filetype)){//����������Ԥ�������� 
			this.m_filetypeEN="NORDERMEETING";
			
		}else if("insnquametting".equals(m_filetype)){//��������Ԥ�������� 
			this.m_filetypeEN="NORDERMEETING";
			
		}else if("baononglift".equals(m_filetype)){// ��¡����
			this.m_filetypeEN="BAONONGLIFT";
		}else if("weathersimple".equals(m_filetype)){// ��������Ԥ��
			this.m_filetypeEN="WEATHERSIMPLE";
			
		}else if("nbqueuing".equals(m_filetype)){// �����Ŷӽк� 
			this.m_filetypeEN="NBQUEUING";
		}else if("nbqueuingmore".equals(m_filetype)){// �����Ŷӽк� ���ۺ�����
			this.m_filetypeEN="NBQUEUINGMORE";
			
		}else if("kingentranceguard".equals(m_filetype)){//��ɽ��������ˢ����Ϣ
			this.m_filetypeEN="KINGENTRANCEGUARD";
			
		}else if("count_down".equals(m_filetype)){//��ɽ�����������յ���ʱ
			this.m_filetypeEN="COUNT_DOWN";
		}
		
		return m_filetypeEN;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getM_filetype() {
		return m_filetype;
	}
	public void setM_filetype(String m_filetype) {
		this.m_filetype = m_filetype;
	}
	public int getM_height() {
		return m_height;
	}
	public void setM_height(int m_height) {
		this.m_height = m_height;
	}
	public int getM_left() {
		return m_left;
	}
	public void setM_left(int m_left) {
		this.m_left = m_left;
	}
	public int getM_top() {
		return m_top;
	}
	public void setM_top(int m_top) {
		this.m_top = m_top;
	}
	public int getM_width() {
		return m_width;
	}
	public void setM_width(int m_width) {
		this.m_width = m_width;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public void setM_filetypeEN(String m_filetypeEN) {
		this.m_filetypeEN = m_filetypeEN;
	}

	public String getM_other() {
		return m_other;
	}

	public void setM_other(String m_other) {
		this.m_other = m_other;
	}

	public String getBack_image() {
		return back_image;
	}

	public void setBack_image(String back_image) {
		this.back_image = back_image;
	}

	public String getCity() {
		if("weather".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.city=m_other.split("#")[3];
		}
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFont_color() {
		if("weather".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.font_color=m_other.split("#")[7];
		}
		return font_color;
	}

	public void setFont_color(String font_color) {
		this.font_color = font_color;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getWeather_date() {
		if("weather".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.weather_date=m_other.split("#")[1];
		}
		return weather_date;
	}

	public void setWeather_date(String weather_date) {
		
		this.weather_date = weather_date;
	}

	public String getWeather_image() {
		if("weather".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.weather_image=m_other.split("#")[2];
		}
		return weather_image;
	}

	public void setWeather_image(String weather_image) {
		
		this.weather_image = weather_image;
	}

	public String getWeather_wind() {
		if("weather".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.weather_wind=m_other.split("#")[4];
		}
		return weather_wind;
	}

	public void setWeather_wind(String weather_wind) {
		this.weather_wind = weather_wind;
	}

	public String getWeatherfile() {
		if("weather".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.weatherfile=m_other.split("#")[0];
		}
		return weatherfile;
	}

	public void setWeatherfile(String weatherfile) {
		this.weatherfile = weatherfile;
	}

	public String getEnd_temperature() {
		if("weather".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.end_temperature=m_other.split("#")[6];
		}
		return end_temperature;
	}

	public void setEnd_temperature(String end_temperature) {
		this.end_temperature = end_temperature;
	}

	public String getStart_temperature() {
		if("weather".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.start_temperature=m_other.split("#")[5];
		}
		return start_temperature;
	}

	public void setStart_temperature(String start_temperature) {
		this.start_temperature = start_temperature;
	}

	public String[] getWinds() {
		this.winds= new String[] {"��#a0.png","��ת����#a15.png","��ת������#a22.png","����#a1.png","����ת��#a17.png","����ת��#a12.png","����ת��#a23.png","��#a11.png","��ת����#a16.png",
				"С��#a2.png","С��ת����#a14.png","С��ת����#a13.png","����#a8.png","����#a6.png","����#a3.png","������#a20.png","������ת����#a21.png","����#a19.png","���ѩ#a5.png","Сѩ#a10.png","��ѩ#a7.png","��ѩ#a9.png","��ѩ#a18.png","����#a4.png"};
		return winds;
	}
	public String getWeatherImagesByName(String name){
		String[] ws=getWinds();
		String images="a0.png";
		for(int i=0;i<ws.length;i++){
			String ss[]=ws[i].split("#");
			if(name.equals(ss[0])){
				images=ss[1];
				break;
			}
		}
		return images;
	}
	public void setWinds(String[] winds) {
		this.winds = winds;
	}

	public String[] getTemperatures() {
		this.temperatures= new String[] {"-20��","-19��","-18��","-17��","-16��","-15��","-14��","-13��","-12��",
				"-11��","-10��","-9��","-8��","-7��","-6��","-5��","-4��","-3��","-2��","-1��","0��",
				"1��","2��","3��","4��","5��","6��","7��","8��","9��","10��","11��","12��","13��","14��","15��","16��","17��","18��","19��","20��",
				"20��","21��","22��","23��","24��","25��","26��","27��","28��","29��","30��",
				"30��","31��","32��","33��","34��","35��","36��","37��","38��","39��","40��"};
		
		return temperatures;
	}

	public void setTemperatures(String[] temperatures) {
		this.temperatures = temperatures;
	}

	public String getScroll_bg() {
		if("scroll".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.scroll_bg=m_other.split("#")[1];
		}
		return scroll_bg;
	}

	public void setScroll_bg(String scroll_bg) {
		this.scroll_bg = scroll_bg;
	}

	public String getScroll_content() {
		this.scroll_content=m_text;
		return scroll_content;
	}

	public void setScroll_content(String scroll_content) {
		this.scroll_content = scroll_content;
	}


	public String getScroll_fg() {
		if("scroll".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.scroll_fg=m_other.split("#")[0];
		}
		return scroll_fg;
	}

	public void setScroll_fg(String scroll_fg) {
		this.scroll_fg = scroll_fg;
	}
	public String[] getFontsizes() {
		this.fontsizes= new String[] {"25","26","28","30","32","34","36","38","40","42","44","46","48","50","52","54","56","58","60"};
		return fontsizes;
	}

	public void setFontsizes(String[] fontsizes) {
		this.fontsizes = fontsizes;
	}

	public String getScroll_file() {
		if("scroll".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.scroll_file=m_other.split("#")[0];
		}
		return scroll_file;
	}

	public void setScroll_file(String scroll_file) {
		this.scroll_file = scroll_file;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String[] getMtypes() {
		
	this.mtypes=System.getProperty("PROGRAMTYPE").split(",");
		
//		this.mtypes= new String[] {"video#��Ƶ","image#ͼƬ","ppt#PPT","clock#ʱ��","scroll#��������"};
				
		
//		this.mtypes= new String[] {"video#��Ƶ","iptv#��ý��","image#ͼƬ","flash#FLASH","ppt#PPT","word#WORD","excel#EXCEL","weather#����Ԥ��","clock#ʱ��",/*"dateother#����",*/
//				"scroll#��������","htmltext#�����ı�","web#��ҳ","backgroundmusic#����","pdf#PDF"
//				,"count_down#���յ���ʱ"
		
	
				//,"nbqueuing#�Ŷӽк���Ϣ","nbqueuingmore#�Ŷӽк�(�ۺ���)"
//				"kingentranceguard#IC��ˢ����Ϣ",
//				"baononglift#����ģ��","weathersimple#����Ԥ��(���)"
//				"insnquametting#������Ϣ",//�ֶ�������߻���
				//"htmeetingnotice#����Ԥ����Ϣ",//��������
				//"nordermeeting#��Ԥ��������",//��������
				
				/*"exe#EXEִ���ļ�","policesubstation#Ѷ������Ϣ","khmeetingnotice#���Ի�չ���" ,"marketstock#����,������Ʒ,���","goldtrend#�ƽ�����ͼ,"*/ 
				
				//,"web#����--WEBģ��","dateother#����--����","date#����--����(5¥)","weatherthree#����--����(����)","birthday#����--Ա������","stock#����--��Ʊ",
				//"stockother#����--��Ʊ��no-k��","wwnotice#����--����֪ͨ","wwmilkdrink#����--��������","filialeSell#����--�ֹ�˾��������"
				
//		};
		return mtypes;
	}
	
	
	public String  getMtypeTitle(String mtype){
		String mtypetitle="δ֪����";
		String []ms=getMtypes();
		for(int i=0;i<ms.length;i++){
			String [] m=ms[i].split("#");
			if(m[0].equals(mtype)){
				mtypetitle=m[1];
				break;
			}
		}
		return mtypetitle;
	}

	public void setMtypes(String[] mtypes) {
		this.mtypes = mtypes;
	}

	public String getIw_content() {
		if(m_other.indexOf("#")>-1){
			String sss[]=m_other.split("#")[1].split("@");
			for(int i=0;i<sss.length;i++){
				if(!"http://".equals(sss[i])){
					this.iw_content=sss[i];
					break;
				}
			}
			
		}
		return iw_content;
	}

	public void setIw_content(String iw_content) {
		this.iw_content = iw_content;
	}

	public String getIwfile() {
		if(m_other.indexOf("#")>-1){
			this.iwfile=m_other.split("#")[0];
		}
		return iwfile;
	}

	public void setIwfile(String iwfile) {
		this.iwfile = iwfile;
	}

	public String getM_text() {
		return m_text;
	}

	public void setM_text(String m_text) {
		this.m_text = m_text;
	}

	public String getSlightly_img_path() {
		return slightly_img_path;
	}

	public void setSlightly_img_path(String slightly_img_path) {
		this.slightly_img_path = slightly_img_path;
	}

	public List<String> getPptfile() {
		if(m_other.indexOf("#")>-1){
			String[] pptArray=m_other.split("#");
			for(int i=0;i<pptArray.length;i++){
				if(!"".equals(pptArray[i])){
					this.pptfile.add(pptArray[i]);
				}
			}
		}
		return pptfile;
	}

	public void setPptfile(List<String> pptfile) {
		this.pptfile = pptfile;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getW_font_color() {
		if("weatherthree".equals(m_filetype) && m_other.indexOf("#")>-1){
			this.w_font_color=m_other.split("#")[14];
		}
		return w_font_color;
	}

	public void setW_font_color(String w_font_color) {
		this.w_font_color = w_font_color;
	}

	public String getProgram_name() {
		return program_name;
	}

	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}

	public String getIptv_path() {
		if(m_other.indexOf("#")>-1){
			this.iptv_path=m_other.split("#")[1];
		}
		return iptv_path;
	}

	public void setIptv_path(String iptv_path) {
		this.iptv_path = iptv_path;
	}

	public String getCount_downtxt() {
		if(m_other.indexOf("#")>-1){
			this.count_downtxt=m_other.split("#")[1];
		}
		return count_downtxt;
	}

	public void setCount_downtxt(String count_downtxt) {
		this.count_downtxt = count_downtxt;
	}

	public int getModule_id() {
		return module_id;
	}

	public void setModule_id(int module_id) {
		this.module_id = module_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOtherparam() {
		return otherparam;
	}

	public void setOtherparam(String otherparam) {
		this.otherparam = otherparam;
	}

	public String getM_play_time() {
		return m_play_time;
	}

	public void setM_play_time(String m_play_time) {
		this.m_play_time = m_play_time;
	}

	



	
	
}
