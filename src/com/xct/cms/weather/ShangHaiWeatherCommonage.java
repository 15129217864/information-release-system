package com.xct.cms.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.xct.cms.servlet.FirstStartServlet;
import com.xct.cms.utils.UrlEncodeUtil;

public class ShangHaiWeatherCommonage {
	
    static Logger logger = Logger.getLogger(ShangHaiWeatherCommonage.class);

	public static String getShangHaiWather(String context,String weaherxml) {

		    boolean flag = true;
		    String contentString = "";
		    String netstring=context;
			netstring=WeatherUtils.subString("<tbody>","</tbody>",netstring.replace("&",""));
			contentString=StringUtils.deleteWhitespace(netstring);
//			System.out.println(netstring);
			
			if(flag&&null!=netstring){
				WeatherUtils.writeFiletoSingle(weaherxml, WeatherUtils.headerxml+netstring, false, false);
			}
			return contentString;
	}
	
/*	public static String getShanghaiWeather(String weatherfile,String daystring){
		
		if(new File(weatherfile).exists()){
			
			String one="";
			String two="";
			String three="";
			String four="";
			String five="";
			
			SAXReader saxreader=new SAXReader();
			try {
				Document document=saxreader.read(new File(weatherfile));
				Element root=document.getRootElement();
				List list=root.selectNodes("/tbody/tr");
				if(null!=list&&!list.isEmpty()){
					Element element=(Element)list.get(0);
					List childlist=element.selectNodes("td");
					for(int i=0,n=childlist.size();i<n;i++){
						
						Element e =(Element)childlist.get(i);
						if(i==1)
							one=e.getStringValue();//��������
						if(i==2)
							two=e.getStringValue();//����
						if(i==3)
							three=e.getStringValue();//����
						if(i==4)
							four=e.getStringValue();//����¶�
						if(i==7)
							five=e.getStringValue();//����¶�
		//				System.out.println(e.getStringValue());
					}
					daystring=daystring+two+"���£�"+five+"��"+four+"������"+three;
					
		//			System.out.println(one);
		//			System.out.println(two);
		//			System.out.println(three);
		//			System.out.println(four);
		//			System.out.println(five);
		//			System.out.println(daystring);
					
					return daystring;
				}
			} catch (DocumentException e) {
				 logger.info(WeatherUtils.getTrace(e));
	//			e.printStackTrace();
				return "";
			}
		}
		return "";
	}*/
	
/*	public static String getShWeatherString(String weathertxt,String weatherfile1,String weatherfile2,String weatherfile3){
		String stirng=getShanghaiWeather(weatherfile1,WeatherUtils.today_weather)+getShanghaiWeather(weatherfile2,WeatherUtils.tomorrow_weather)+getShanghaiWeather(weatherfile3,WeatherUtils.the_day_after_tomorrow_weather);
		WeatherUtils.writeFiletoSingle(weathertxt, stirng, false, false);
		return stirng;
	}*/
	
	public static String getShanghaiWeather(String weatherfile,String daystring,String city){
		boolean flag=true;
		boolean bool=false;
		boolean booltmp=false;
		boolean superbool=false;
		if(new File(weatherfile).exists()){
			
			String one="";
			String two="";
			String three="";
			String four="";
			String five="";
			
			SAXReader saxreader=new SAXReader();
			try {
				Document document=saxreader.read(new File(weatherfile));
				Element root=document.getRootElement();
				List list=root.selectNodes("/tbody/tr");
				if(null!=list&&!list.isEmpty()){
					for(int j=0,m=list.size();j<m;j++){
						Element element=(Element)list.get(j);
						List childlist=element.selectNodes("td");
						for(int i=0,n=childlist.size();i<n;i++){
							
							 Element e =(Element)childlist.get(i);
							 
							 if(i==0){
								one=e.getStringValue();//��������
								if(one.equals(city)){
									bool=true;
								}
							 }
							 if(bool){
								if(i==1){
								  if(e.getStringValue().equals(city)){
									  superbool=true;
								  }
								}
								if(!superbool){
									if(i==1)
										  two=e.getStringValue();//����
									if(i==2)
										three=e.getStringValue();//����
									if(i==3)
										four=e.getStringValue();//����¶�
									if(i==6){
										five=e.getStringValue();//����¶�
										flag=false;
										break;
									}
								}else{
									if(i==2)
										two=e.getStringValue();//����
									if(i==3)
										three=e.getStringValue();//����
									if(i==4)
										four=e.getStringValue();//����¶�
									if(i==7){
										five=e.getStringValue();//����¶�
										flag=false;
										break;
									}
								}
							 }
						}
						if(!flag){
							daystring=daystring+two+"���£�"+five+"��"+four+"������"+three;
							break;
						 }
					}
				}
				if(flag){
					return null;
				}else{
					return daystring;
				}
			} catch (DocumentException e) {
				 logger.info(WeatherUtils.getTrace(e));
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public static String getShWeatherString(String weathertxt,String weatherfile1,String weatherfile2,String weatherfile3,String city){
		
		String today_weather=getShanghaiWeather(weatherfile1,WeatherUtils.today_weather,city);
		String tomorrow_weather=getShanghaiWeather(weatherfile2,WeatherUtils.tomorrow_weather,city);
		String the_day_after_tomorrow_weather=getShanghaiWeather(weatherfile3,WeatherUtils.the_day_after_tomorrow_weather,city);
		
		String stirng=null;
		if(null!=today_weather&&null!=tomorrow_weather&&null!=the_day_after_tomorrow_weather){
			stirng=today_weather+tomorrow_weather+the_day_after_tomorrow_weather;
			stirng=UrlEncodeUtil.englishSysCharTo(stirng);
			WeatherUtils.writeFiletoSingle(weathertxt, stirng, false, false);
		}
		
		/*String stirng=getShanghaiWeather(weatherfile1,WeatherUtils.today_weather,city)+getShanghaiWeather(weatherfile2,WeatherUtils.tomorrow_weather,city)+getShanghaiWeather(weatherfile3,WeatherUtils.the_day_after_tomorrow_weather,city);
		WeatherUtils.writeFiletoSingle(weathertxt, stirng, false, false);*/
		return stirng;
	}
	
	public static List<String> getShiCuanCity(){
		
		List<String> list=new ArrayList<String>();
		try {
			SAXReader saxreader=new SAXReader();
			Document document =saxreader.read(FirstStartServlet.projectpath+"sc-weatherutils.xml");
			List citylist=document.selectNodes("/weather/city");
			Element element;
			for(int i=0,n=citylist.size();i<n;i++){
				element=(Element)citylist.get(i);
//				System.out.println(element.attributeValue("city"));
				list.add(element.attributeValue("city"));
			}
//			System.out.println(list.size());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return list;
	}
    
	 public static List<String> getText(String saveurl) {
			
			List<String>list=new ArrayList<String>();
			String linestring="";
//			String saveurl = "c://weather.txt";
			String encoding = "GBK"; // �ַ�����
			try {

				File f = new File(saveurl);

				if (f.isFile() && f.exists()) {

					InputStreamReader read = new InputStreamReader(
							new FileInputStream(f), encoding);

					BufferedReader in = new BufferedReader(read);
					String line;
					while ((line = in.readLine()) != null) {
						linestring+=line;
						if(!line.equals(""))
						   list.add(line);
					}
					read.close();
				}
	          
			} catch (Exception e) {
				e.printStackTrace();
			}
			 return list;
		}
	 
	 public static String readWeatherFromNet(String url){
		  
			String curLine = "";
			StringBuffer context = new StringBuffer("");
			URL server;
			BufferedReader reader = null;
			String netstring="";
			
			//////////////// ���������ô������������////////////////////////////////////
//			Properties   prop   =   System.getProperties();      
			
/*			System.getProperties().put("proxySet", "true"); 
      //	����http����Ҫʹ�õĴ���������ĵ�ַ
			System.getProperties().setProperty("http.proxyHost", "10.38.18.224"); //����������������IP��156.5.80.49�����ճ��ݣ�10.38.18.224
			// ����http����Ҫʹ�õĴ���������Ķ˿� 
			System.getProperties().setProperty("http.proxyPort", "808"); //����������������˿ڣ�8080�����ճ��ݣ�808
*/			
			
      //����Ҫ�õ� �û��������룬����ע��ȡ��   
/*          System.getProperties().setProperty("http.proxyUser","www"); //�û���     
			System.getProperties().setProperty("http.proxyPassword","www");  //����
*/
     ///////////////////////////////////////////////////////////////////////////////
			
			try {
				server = new URL(url);
				reader = new BufferedReader(new InputStreamReader(server.openStream()/*, "UTF-8"*/));
				while ((curLine = reader.readLine()) != null) {
					context.append(curLine).append("\r\n");
				}
				netstring=context.toString();
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return netstring;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return netstring;
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
						return netstring;
					}
				}
			}
			return netstring;
	    }
	
	 
	 public static  void getShWeather(String homepath,String sheng,String city){
			
			try {
				sheng=URLEncoder.encode(sheng,"GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		
			String one = "http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng="+sheng+"&day=0&dpc=1";
			String two = "http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng="+sheng+"&day=1&dpc=1";
			String three = "http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng="+sheng+"&day=2&dpc=1";
			
			
		    String weatherfile1 = homepath + "temp_weather1.xml";
		    String weatherfile2 = homepath + "temp_weather2.xml";
			String weatherfile3 = homepath + "temp_weather3.xml";
//			String weahertxt=WeatherUtils.homepath + "xy-weather.txt";//��������
			String weahertxt=homepath + "weather.txt";//���ڰ�����
			
			String oneweather=readWeatherFromNet(one);
			String twoweather=readWeatherFromNet(two);
			String threeweather=readWeatherFromNet(three);
			
			if(!oneweather.equals("")&&null!=oneweather
					&&!twoweather.equals("")&&null!=twoweather
					&&!threeweather.equals("")&&null!=threeweather){
				
				getShangHaiWather(oneweather,weatherfile1);
				getShangHaiWather(twoweather,weatherfile2);
				getShangHaiWather(threeweather,weatherfile3);
		
				logger.info(getShWeatherString(weahertxt,weatherfile1,weatherfile2,weatherfile3,city));//�˴�ֻҪ������������
			}else{
				logger.info(oneweather);
			}
		}
	
	public static void getShWeather(String homepath){ 
		
		List<String>list=getText(homepath +"weather_url.txt");
		scweatherxml=homepath +"sc-weather.xml";
		String one=list.get(1).split("->")[1].trim();
		String two=list.get(2).split("->")[1].trim();
		String three=list.get(3).split("->")[1].trim();
		//�Ϻ�����Ԥ��
//		String one="http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%C9%CF%BA%A3&day=0&dpc=1";//����
//		String two="http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%C9%CF%BA%A3&day=1&dpc=1";//����
//		String three="http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%C9%CF%BA%A3&day=2&dpc=1";//����
//----------------------------------------------------------------------------------------------------------------
//		�人��������Ԥ��
//		String one="http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%BA%FE%B1%B1&day=0&dpc=1";//����
//		String two="http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%BA%FE%B1%B1&day=1&dpc=1";//����
//		String three="http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%BA%FE%B1%B1&day=2&dpc=1";//����
//		----------------------------------------------------------------------------------------------------------	
//		��������Ԥ��
//		String one="http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%D6%D8%C7%EC&day=0&dpc=1";//����
//		String two="http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%D6%D8%C7%EC&day=1&dpc=1";//����
//		String three="http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%D6%D8%C7%EC&day=2&dpc=1";//����
//		----------------------------------------------------------------------------------------------------------	
		//��������Ԥ��
//		String one="http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%B1%B1%BE%A9&day=0&dpc=1";//����
//		String two="http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%B1%B1%BE%A9&day=1&dpc=1";//����
//		String three="http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%B1%B1%BE%A9&day=2&dpc=1";//����
//----------------------------------------------------------------------------------------------------------------
		
	    //�Ĵ�����
//	    String one = "http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%CB%C4%B4%A8&day=0&dpc=1";
//	    String two = "http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%CB%C4%B4%A8&day=1&dpc=1";
//	    String three ="http://php.weather.sina.com.cn/search_sheng.php?f=1&sheng=%CB%C4%B4%A8&day=2&dpc=1";
		
	    String weatherfile1 = /*WeatherUtils.homepath*/homepath + "temp_weather1.xml";
	    String weatherfile2 = /*WeatherUtils.homepath*/homepath + "temp_weather2.xml";
		String weatherfile3 = /*WeatherUtils.homepath*/homepath + "temp_weather3.xml";
//		String weahertxt=WeatherUtils.homepath + "xy-weather.txt";//��������
		String weahertxt=/*WeatherUtils.homepath*/homepath + "weather.txt";//���ڰ�����
	
		String oneweather=WeatherUtils.readWeatherFromNet(one);
		String twoweather=WeatherUtils.readWeatherFromNet(two);
		String threeweather=WeatherUtils.readWeatherFromNet(three);
		
		if(!oneweather.equals("")&&null!=oneweather
				&&!twoweather.equals("")&&null!=twoweather
				&&!threeweather.equals("")&&null!=threeweather){
			
			getShangHaiWather(oneweather,weatherfile1);
			getShangHaiWather(twoweather,weatherfile2);
			getShangHaiWather(threeweather,weatherfile3);
			
			getShWeatherString(weahertxt,weatherfile1,weatherfile2,weatherfile3,/*"�Ϻ�"*/list.get(0).split("->")[1].trim());
			
		/*
		 *��ȡ�Ĵ����ص�����
		 * 	List<String>list= getShiCuanCity();
			Document document=DocumentHelper.createDocument();
			Element element=document.addElement("weather");
			Element subelement;
			for(int i=0,n=list.size();i<n;i++){
//			    System.out.println(list.get(i));
				subelement=element.addElement("city");
				subelement.addAttribute("city", list.get(i)).addAttribute("text", getShanghaiWeather(weatherfile1,WeatherUtils.today_weather,list.get(i))+getShanghaiWeather(weatherfile2,WeatherUtils.tomorrow_weather,list.get(i))+getShanghaiWeather(weatherfile3,WeatherUtils.the_day_after_tomorrow_weather,list.get(i))) ;
			}
			OutputFormat outputformat=OutputFormat.createPrettyPrint();
			XMLWriter xmlwriter;
			try {
				xmlwriter = new XMLWriter(new FileWriter(new File(scweatherxml)),outputformat);
				outputformat.setEncoding("GBK");
				xmlwriter.write(document);
				xmlwriter.close();
				System.out.println("------get weather is OK ------>����Ԥ����ȡ�ɹ�");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}*/
		}else{
			//System.out.println(oneweather);
		}
	}
	
	private static String scweatherxml;
	
	public static void main(String[] args) {
		
//		getShWeather("c://temp/");
//		getShiCuanCity();
		new ShangHaiWeatherCommonage().getShWeather("c:/","�Ϻ�","�Ϻ�");
	}

}
