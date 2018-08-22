package com.xct.cms.httpclient;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.xct.cms.dao.TicketSortASC;
import com.xct.cms.domin.Led;
import com.xct.cms.utils.UtilDAO;


public class SaudiHttpClient {
	
	 Properties props = new Properties();
	InputStream is = getClass().getResourceAsStream("/conf.properties");
	 Logger logger = Logger.getLogger(SaudiHttpClient.class);
	  /**  
     * MD5 加密  
     */   
    public static String getMD5Str(String str) {   
    	
        MessageDigest messageDigest = null;   
   
        try {   
            messageDigest = MessageDigest.getInstance("MD5");   
   
            messageDigest.reset();   
   
            messageDigest.update(str.getBytes("UTF-8"));  
            
        } catch (NoSuchAlgorithmException e) {   
        	
//            System.out.println("NoSuchAlgorithmException caught!");   
            System.exit(-1);   
        } catch (UnsupportedEncodingException e) {   
            e.printStackTrace();   
        }   
   
        byte[] byteArray = messageDigest.digest();   
   
        StringBuffer md5StrBuff = new StringBuffer();   
   
        for (int i = 0; i < byteArray.length; i++) {               
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)   
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));   
            else   
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));   
        }   
   
        return md5StrBuff.toString();   
    }
	
	public SaudiHttpClient() {
		super();
		try {
			props.load(is);
			if(is!=null)
			  is.close();
		} catch (Exception e) {
			logger.info("读取票务信息URL出错！====="+e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
	
		System.out.println(("8:0～9:0").compareTo("09:00～10:00")<0);
		
		System.out.println("上午 8:0～9:0".length());
		System.out.println("上午 09:00～10:00".length());
		
		/*String targetURL = null;
		targetURL = "http://219.233.194.188:9205/expoServer/findTickLimitByDate.action"; // servleturl
		// 若没有commons-codec-1.4-bin.zip, 这里将会出错
		PostMethod filePost = new PostMethod(targetURL); 
		
//		如果PostMethod提交的是中文字符，需要加上相应的编码格式
		filePost.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");     
												
		try {
			*//**
			 * txnOrg:任意值
			 * start：开始行数，要取第几行的数据 .(start从0开始 ,每页都是20条数据:start=0 resNum=20.点下一页的时候，就应该是start=20 resNum=20)
			 * resNum：每页记录数，查询多少数据
			 * enterDate：当天日期 ，格式： 20110911
			 * transCode=501001  ：  请求交易代码（501001 值不变）
			 * 其他：如果不对的话，查看响应的报文 ，txnResp是应答码 它可以告诉你是什么错 
			 *//*
			String jsonstring="{\"start\"=\"0\",\"resNum\"=\"20\",\"enterDate\"=\"20110928\",\"transCode\"=\"501001\"}";
			String signstring=getMD5Str(jsonstring+"+HuatengExpo");//  md5的加密格式是  jsonstring+HuatengExpo   做一个md5加密
			System.out.println(signstring);
			
//			 通过以下方法可以模拟页面参数POST提交
			NameValuePair[] param = { new NameValuePair("jsonObj",jsonstring), new NameValuePair("sign",signstring)} ;   
			filePost.setRequestBody(param);
			HttpClient client = new HttpClient();
			
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
					
			int status = client.executeMethod(filePost);
			if (status == HttpStatus.SC_OK) {//200 返回成功
				System.out.println("提交成功");
				.getBytes("ISO-8859-1"),"GBK"
				System.out.println(new String(filePost.getResponseBodyAsString()));
			} else {
				System.out.println("提交失败--------->"+status);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}*/
		
		//Ticket t=new SaudiHttpClient().getAnalyticJson(0,20,"20111026");
		//List<Ticket> ticketlist= t.getResRoot();
		String showstr=SaudiHttpClient.showTicketStr("20111027");
		System.out.println(showstr);
		//System.out.println(updateStr("旅客您好：{上午}{中午}{下午}",showstr,"801"));
		//sendTicketInfo();
		
		//String sss="团队换票时间8:00-17:00受理窗口为12、13、14、15、16、17号窗口,个人票售票时间11:00-17:00售票窗口为1、2、4、5、6、8号窗口。17:30停止入馆,参观时间9:00-18:00。周一闭馆(节假日除外)。75岁以上老人和三级及以上肢残人士可到1号绿色窗口购票。60岁以上老人、现役军人、残疾人士、在校学生、身高超过1.3米的儿童可购优惠票,购买和使用优惠票需提供相关证件。一人一票,当日单次出入。1.3米以下儿童免费,每名成人游客可免费携带一名身高不足1.3米(含1.3米)的儿童,超过的人数应购买优惠票。";	
		//System.out.println(sss.length());
	}
	public static void sendTicketInfo(){////发送售票厅LED滚动文字
		/// 获取票务信息
		String nowtime=UtilDAO.getNowtime("yyyyMMdd");
		String newstr=SaudiHttpClient.showTicketStr(nowtime);
		///获取数据库的信息
		Map<String,Led> ledmap=new Led().getALLLedMap(15,"");  //查询数据库前11条数据
		
		String ledkey="";
		String text="";
		String pno="0";
		Led led= new Led();
		for (Map.Entry<String, Led> entry : ledmap.entrySet()) {
			if (entry != null) {
				ledkey=entry.getKey();
				led = entry.getValue();
				pno=led.getPno();
				if(pno.equals("12") ||pno.equals("13")){
					text=newLedText(led);
					led.setText(text);
				}else{
					text=updateStr(led.getText(),newstr,led.getL_num());
					if("".equals(text)){
						led.setText(led.getDef_text());
					}else{
						led.setText(text);
					}
				}
				ledmap.put(ledkey, led);
			}
		}
		led.sendLedInfo(ledmap);
	}
	
	public static  void sendTicketInfoBymap(Map<String,Led> ledmap){
		/// 获取票务信息
		String nowtime=UtilDAO.getNowtime("yyyyMMdd");
		String newstr=SaudiHttpClient.showTicketStr(nowtime);
		///获取数据库的信息
		String ledkey="";
		String text="";
		int pno=0;
		Led led= new Led();
		for (Map.Entry<String, Led> entry : ledmap.entrySet()) {
			if (entry != null) {
				ledkey=entry.getKey();
				led = entry.getValue();
//				pno=led.getPno();
//				if(pno==12 || pno==13){
//					text=newLedText(led);
//					led.setText(text);
//				}else{
//					text=updateStr(led.getText(),newstr,led.getL_num());
//					if("".equals(text)){
//						led.setText(led.getDef_text());
//					}else{
//						led.setText(text);
//					}
//				}
				ledmap.put(ledkey, led);
			}
		}
		led.sendLedInfo(ledmap);
	}
	
	public static String newLedText(Led led){
		String text=" ";
		if(led !=null){
		 String s_time1=led.getS_time1();
		 String s_time2=led.getS_time2();
		 String s_time3=led.getS_time3();
		 String s_time4=led.getS_time4();
		 String s_time5=led.getS_time5();
		 String e_time1=led.getE_time1();
		 String e_time2=led.getE_time2();
		 String e_time3=led.getE_time3();
		 String e_time4=led.getE_time4();
		 String e_time5=led.getE_time5();
		 
		 String s_text1=led.getS_text1();
		 String s_text2=led.getS_text2();
		 String s_text3=led.getS_text3();
		 String s_text4=led.getS_text4();
		 String s_text5=led.getS_text5();
		 String nowtime=UtilDAO.getNowtime("HH:mm:ss");
		 //System.out.println("111");
		 if(s_time1!=null&&!"".equals(s_time1) && e_time1!=null&&!"".equals(e_time1) && s_text1!=null&&!"".equals(s_text1)){
			// System.out.println("222");
			 if(s_time1.compareTo(nowtime)<=0 && e_time1.compareTo(nowtime)>0){
				// System.out.println("333");
				 text=s_text1;
			 }
		 }if(s_time2!=null&&!"".equals(s_time2) && e_time2!=null&&!"".equals(e_time2) && s_text2!=null&&!"".equals(s_text2)){
			 if(s_time2.compareTo(nowtime)<=0 && e_time2.compareTo(nowtime)>0){
				 text=s_text2;
			 }
		 }if(s_time3!=null&&!"".equals(s_time3) && e_time3!=null&&!"".equals(e_time3) && s_text3!=null&&!"".equals(s_text3)){
			 if(s_time3.compareTo(nowtime)<=0 && e_time3.compareTo(nowtime)>0){
				 text=s_text3;
			 }
		 }if(s_time4!=null&&!"".equals(s_time4) && e_time4!=null&&!"".equals(e_time4) && s_text4!=null&&!"".equals(s_text4)){
			 if(s_time4.compareTo(nowtime)<=0 && e_time4.compareTo(nowtime)>0){
				 text=s_text4;
			 }
		 }if(s_time5!=null&&!"".equals(s_time5) && e_time5!=null&&!"".equals(e_time5) && s_text5!=null&&!"".equals(s_text5)){
			 if(s_time5.compareTo(nowtime)<=0 && e_time5.compareTo(nowtime)>0){
				 text=s_text5;
			 }
		 }
		}
		 
		return text;
	}
	
	
	public static String updateStr(String oldstr,String newstr,String l_num){
		///oldstr="{上午}{中午}{下午}{晚间}按时发生大幅";
		try{
		String nowtime=UtilDAO.getNowtime("HH:mm");
		String str="";
		String is_t="有票";
		String[] newstrs=newstr.split("!");
		if(oldstr.indexOf("{上午}")>-1){
			String[] s_strs=newstrs[0].split("#");
			int i=0;
			str="上午：";
			if(nowtime.compareTo("10:00")<0){
				String [] tts=s_strs[0].split("_");
				if(tts[1].compareTo(l_num)<0){
					is_t="无票";
				}else{
					is_t="有票";
				}
				str+=tts[0]+"："+is_t+"，";
				i=1;
			}if(nowtime.compareTo("11:00")<0){
				String [] tts=s_strs[1].split("_");
				if(tts[1].compareTo(l_num)<0){
					is_t="无票";
				}else{
					is_t="有票";
				}
				str+=tts[0]+"："+is_t+"，";
				i=1;
			}if(nowtime.compareTo("12:00")<0){
				String [] tts=s_strs[2].split("_");
				if(tts[1].compareTo(l_num)<0){
					is_t="无票";
				}else{
					is_t="有票";
				}
				str+=tts[0]+"："+is_t+"，";
				i=1;
			}
			if(i==0){
				str=str.replace("上午：", "");
			}else{
				str=str.substring(0,str.length()-1)+"；";
			}
			oldstr=oldstr.replace("{上午}", str);
		}if(oldstr.indexOf("{中午}")>-1){
			String[] s_strs=newstrs[1].split("#");
			int i=0;
			str="中午：";
			if(nowtime.compareTo("13:00")<0){
				String [] tts=s_strs[0].split("_");
				if(tts[1].compareTo(l_num)<0){
					is_t="无票";
				}else{
					is_t="有票";
				}
				str+=tts[0]+"："+is_t+"，";
				i=1;
			}if(nowtime.compareTo("14:00")<0){
				String [] tts=s_strs[1].split("_");
				if(tts[1].compareTo(l_num)<0){
					is_t="无票";
				}else{
					is_t="有票";
				}
				str+=tts[0]+"："+is_t+"，";
				i=1;
			}
			if(i==0){
				str=str.replace("中午：", "");
			}else{
				str=str.substring(0,str.length()-1)+"；";
			}
			oldstr=oldstr.replace("{中午}", str);
		}if(oldstr.indexOf("{下午}")>-1){
			String[] s_strs=newstrs[2].split("#");
			int i=0;
			str="下午：";
			
			if(nowtime.compareTo("15:00")<0){
				String [] tts=s_strs[0].split("_");
				if(tts[1].compareTo(l_num)<0){
					is_t="无票";
				}else{
					is_t="有票";
				}
				str+=tts[0]+"："+is_t+"，";
				i=1;
			}if(nowtime.compareTo("16:00")<0){
				String [] tts=s_strs[1].split("_");
				if(tts[1].compareTo(l_num)<0){
					is_t="无票";
				}else{
					is_t="有票";
				}
				str+=tts[0]+"："+is_t+"，";
				i=1;
			}if(nowtime.compareTo("17:00")<0){
				String [] tts=s_strs[2].split("_");
				if(tts[1].compareTo(l_num)<0){
					is_t="无票";
				}else{
					is_t="有票";
				}
				str+=tts[0]+"："+is_t+"，";
				i=1;
			}
			if(i==0){
				str=str.replace("下午：", "");
			}else{
				str=str.substring(0,str.length()-1)+"；";
			}
			oldstr=oldstr.replace("{下午}", str);
		}if(oldstr.indexOf("{晚间}")>-1){
			String[] s_strs=newstrs[3].split("#");
			int i=0;
			str="晚间：";
			if(nowtime.compareTo("18:00")<0){
				String [] tts=s_strs[0].split("_");
				if(tts[1].compareTo(l_num)<0){
					is_t="无票";
				}else{
					is_t="有票";
				}
				str+=tts[0]+"："+is_t+"，";
				i=1;
			}if(nowtime.compareTo("19:00")<0){
				String [] tts=s_strs[1].split("_");
				if(tts[1].compareTo(l_num)<0){
					is_t="无票";
				}else{
					is_t="有票";
				}
				str+=tts[0]+"："+is_t+"，";
				i=1;
			}if(nowtime.compareTo("20:00")<0){
				String [] tts=s_strs[2].split("_");
				if(tts[1].compareTo(l_num)<0){
					is_t="无票";
				}else{
					is_t="有票";
				}
				str+=tts[0]+"："+is_t+"，";
				i=1;
			} 
			if(i==0){
				str=str.replace("晚间：", "");
			}else{
				str=str.substring(0,str.length()-1)+"；";
			}
			oldstr=oldstr.replace("{晚间}", str);
		}
		}catch(Exception e){
			return "";
		}
		return oldstr;
	}
	
	
	public static String showTicketStr(String date){
		Ticket t=SaudiHttpClient.getAnalyticJson(0,20,date);
		List<Ticket> ticketlist= t.getResRoot();
		if(ticketlist!=null){
		Collections.sort(ticketlist,new TicketSortASC()); 
		}
		String showstr="";
		int z_i=0;
		int x_i=0;
		int w_i=0;
		//System.out.println(ticketlist);
		if(ticketlist!=null && ticketlist.size()>0){
			for(int i=0;i<ticketlist.size();i++){
				t=ticketlist.get(i);
					String scrNoDesc=t.getScrNoDesc();
					int enablenum=Integer.parseInt(t.getEnableNum());
					if("公用库".equals(t.getLimitName())){
					if(scrNoDesc.indexOf("上午")>-1){
						showstr+=scrNoDesc.replace("上午 ", "");
						showstr+="_"+enablenum+"#";
					}else if(scrNoDesc.indexOf("中午")>-1){
						if(z_i==0&&showstr.length()>0){
							showstr=showstr.substring(0,showstr.length()-1)+"!"+scrNoDesc.replace("中午 ", "");
						}else{
							showstr+=scrNoDesc.replace("中午 ", "");
						}
						z_i++;
						showstr+="_"+enablenum+"#";
					}else if(scrNoDesc.indexOf("下午")>-1){
						if(x_i==0&&showstr.length()>0){
							showstr=showstr.substring(0,showstr.length()-1)+"!"+scrNoDesc.replace("下午 ", "");
						}else{
							showstr+=scrNoDesc.replace("下午 ", "");
						}
						x_i++;
						showstr+="_"+enablenum+"#";
					}else if(scrNoDesc.indexOf("晚间")>-1){
						if(w_i==0&&showstr.length()>0){
							showstr=showstr.substring(0,showstr.length()-1)+"!"+scrNoDesc.replace("晚间 ", "");
						}else{
							showstr+=scrNoDesc.replace("晚间 ", "");
						}
						w_i++;	
						showstr+="_"+enablenum+"#";
					}}
					
					
			}
			showstr=showstr.substring(0,showstr.length()-1)+"!";
		}
		
		return showstr;
	}
	
	public  String linkjson(int start,int resNum,String enterDate){
		
		String targetURL =props.getProperty("ticket_url");
		
		// 若没有commons-codec-1.4-bin.zip, 这里将会出错
		PostMethod filePost = new PostMethod(targetURL); 
		
//		如果PostMethod提交的是中文字符，需要加上相应的编码格式
		filePost.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");     
												
		try {
			/**
			 * txnOrg:任意值
			 * start：开始行数，要取第几行的数据 .(start从0开始 ,每页都是20条数据:start=0 resNum=20.点下一页的时候，就应该是start=20 resNum=20)
			 * resNum：每页记录数，查询多少数据
			 * enterDate：当天日期 ，格式： 20110911
			 * transCode=501001  ：  请求交易代码（501001 值不变）
			 * 其他：如果不对的话，查看响应的报文 ，txnResp是应答码 它可以告诉你是什么错 
			 */
			String jsonstring="{\"start\"=\""+start+"\",\"resNum\"=\""+resNum+"\",\"enterDate\"=\""+enterDate+"\",\"transCode\"=\"501001\"}";
			String signstring=getMD5Str(jsonstring+"+HuatengExpo");//  md5的加密格式是  jsonstring+HuatengExpo   做一个md5加密
			
//			 通过以下方法可以模拟页面参数POST提交
			NameValuePair[] param = { new NameValuePair("jsonObj",jsonstring), new NameValuePair("sign",signstring)} ;   
			filePost.setRequestBody(param);
			HttpClient client = new HttpClient();
			
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
					
			int status = client.executeMethod(filePost);
			if (status == HttpStatus.SC_OK) {//200 返回成功
				return new String(filePost.getResponseBodyAsString());
			} else {
				System.out.println("提交失败--------->"+status);
			}
		} catch (Exception ex) {
			logger.info("获取票务信息出错"+ex.getMessage());
		} finally {
			filePost.releaseConnection();
		}
		return "";
	}
	
	public static Ticket getAnalyticJson(int start,int resNum,String enterDate){

		Ticket ticket= new Ticket();
		String jsonString=new SaudiHttpClient().linkjson(start,resNum,enterDate);   //获取票务信息
		if(!"".equals(jsonString)){
			JSONObject jb = JSONObject.fromObject(jsonString); ////解析票务信息
			
			ticket.setResNum(jb.get("resNum").toString());  //记录数
			ticket.setTotalProperty(jb.get("totalProperty").toString()); //总数
			ticket.setTxnResp(jb.get("txnResp").toString());//应答码
			
			JSONArray array=jb.getJSONArray("resRoot");   ///票务信息数组
			//System.out.println(array);
			JSONObject jb2=null;
			List<Ticket> list= new ArrayList<Ticket>();
			Ticket resRoot=null;
			for(int i=0;i<array.size();i++){  
				jb2 =JSONObject.fromObject(array.get(i));
				if(checkTime(jb2.get("scrNoDesc").toString())){//会出现时间为空，故加此判断，2012-04-01 出现  "～",导致异常
					resRoot=new Ticket();
					resRoot.setEnableNum(jb2.get("enableNum").toString());
					resRoot.setEnterDate(jb2.get("enterDate").toString());
					resRoot.setLimitName(jb2.get("limitName").toString());
					resRoot.setLimitNo(jb2.get("limitNo").toString());
					resRoot.setOcuNum(jb2.get("ocuNum").toString());
					resRoot.setScrNo(jb2.get("scrNo").toString());
					resRoot.setScrNoDesc(jb2.get("scrNoDesc").toString().equals("上午 9:00～10:00")?"上午 09:00～10:00":jb2.get("scrNoDesc").toString());
					resRoot.setSetNum(jb2.get("setNum").toString());
					resRoot.setStadNo(jb2.get("stadNo").toString());
					resRoot.setStadNoDesc(jb2.get("stadNoDesc").toString());
					resRoot.setUseNum(jb2.get("useNum").toString());
					list.add(resRoot);
				}
			}
			ticket.setResRoot(list);
		}
		return ticket;
	}
	
	public static boolean checkTime(String string){
		if(string.trim().equals("～"))
			return false;
		int lengthtempI=string.trim().length();
		if(lengthtempI>=10&&lengthtempI<=14)
			return true;
		return false;
	}
}
