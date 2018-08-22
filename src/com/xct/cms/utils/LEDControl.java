package com.xct.cms.utils;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;


public class LEDControl {
	public  static int AddProgram(int pno, int jno, int playTime){
		int revalue=0;
		try {
			JNative jnative = new JNative("ListenPlayDll","AddProgram");
			jnative.setRetVal(Type.INT);
			jnative.setParameter(0, Type.INT,pno+"");
			jnative.setParameter(1, Type.INT,jno+"");
			jnative.setParameter(2, Type.INT,playTime+"");
			 jnative.invoke();
			 revalue= Integer.parseInt(jnative.getRetVal());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return revalue;
	}

	public  static int AddControl(int pno, int DBColor){
		int revalue=0;
		try {
			JNative jnative = new JNative("ListenPlayDll","AddControl");
			jnative.setRetVal(Type.INT);
			jnative.setParameter(0, Type.INT,pno+"");
			jnative.setParameter(1, Type.INT,DBColor+"");
			 jnative.invoke();
			 revalue= Integer.parseInt(jnative.getRetVal());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return revalue;
	}

	public native static int SetSerialPortPara(int pno, int comno, int baud);

	public native static int AddLnTxtArea(int pno, int jno, int qno, int left,
			int top, int width, int height, String LnFileName, int PlayStyle,
			int Playspeed, int times);

	public native static int AddFileArea(int pno, int jno, int qno, int left,
			int top, int width, int height);

	public native static int AddFile(int pno, int jno, int qno, int mno,
			String fileName, int width, int height, int playstyle,
			int QuitStyle, int playspeed, int delay, int MidText);

	// 计时
	public native static int AddTimerArea(int pno, int jno, int qno, int left,
			int top, int width, int height, int fontColor, String fontName,
			int fontSize, int fontBold, int mode, int format, int year,
			int week, int month, int day, int hour, int minute, int second);

	// 数字时钟
	public native static int AddDClockArea(int pno, int jno, int qno, int left,
			int top, int width, int height, int fontColor, String fontName,
			int fontSize, int fontBold, int mode, int format, int spanMode,
			int hour, int minute);

	public native static int SetNetPara();

	public  static int SendControl(int PNO, int SendType, int hwd){
		int revalue=0;
		try {
			JNative jnative = new JNative("ListenPlayDll","SendControl");
			jnative.setRetVal(Type.INT);
			jnative.setParameter(0, Type.INT,PNO+"");
			jnative.setParameter(1, Type.INT,SendType+"");
			jnative.setParameter(1, Type.INT,hwd+"");
			 jnative.invoke();
			 revalue= Integer.parseInt(jnative.getRetVal());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return revalue;
		
	}

	public native static int SetOrderPara(int pno, String diskName);

	public native static int AddFileString(int pno, int jno, int qno, int mno,
			String str, String fontname, int fontsize, int fontcolor,
			boolean bold, boolean italic, boolean underline, int align,
			int width, int height, int playstyle, int QuitStyle, int playspeed,
			int delay, int MidText);

	public  static int SetTransMode(int TransMode, int ConType){
		int revalue=0;
		try {
			System.loadLibrary("ListenPlayDll");
			JNative jnative = new JNative("ListenPlayDll","SetTransMode");
			jnative.setRetVal(Type.INT);
			jnative.setParameter(0, Type.INT,TransMode+"");
			jnative.setParameter(1, Type.INT,ConType+"");
			 jnative.invoke();
			 revalue= Integer.parseInt(jnative.getRetVal());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return revalue;
	}

	public  static int SetNetworkPara(int pno, String ip){
		int revalue=0;
		try {
			JNative jnative = new JNative("ListenPlayDll","SetNetworkPara");
			jnative.setRetVal(Type.INT);
			jnative.setParameter(0, Type.INT,pno+"");
			jnative.setParameter(1, Type.STRING,ip);
			 jnative.invoke();
			 revalue= Integer.parseInt(jnative.getRetVal());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return revalue;
		
	}

	public  static void StartSend(){
		try {
			JNative jnative = new JNative("ListenPlayDll","StartSend");
			jnative.invoke();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public native static int SetProgramTimer(int pno, int jno, int TimingModel,
			int WeekSelect, int startSecond, int startMinute, int startHour,
			int startDay, int startMonth, int startWeek, int startYear,
			int endSecond, int endMinute, int endHour, int endDay,
			int endMonth, int endWeek, int endYear);

	public  static int AddLnTxtString(int pno, int jno, int qno,
			int left, int top, int width, int height, String text,
			String fontname, int fontsize, int fontcolor, boolean bold,
			boolean italic, boolean underline, int PlayStyle, int Playspeed,
			int times){
		int revalue=0;
		try {
			JNative jnative = new JNative("ListenPlayDll","AddLnTxtString");
			jnative.setRetVal(Type.INT);
			jnative.setParameter(0, Type.INT,pno+"");
			jnative.setParameter(1, Type.INT,jno+"");
			jnative.setParameter(2, Type.INT,qno+"");
			jnative.setParameter(3, Type.INT,left+"");
			jnative.setParameter(4, Type.INT,top+"");
			jnative.setParameter(5, Type.INT,width+"");
			jnative.setParameter(6, Type.INT,height+"");
			jnative.setParameter(7, Type.STRING,text+"");
			jnative.setParameter(8, Type.STRING,fontname+"");
			jnative.setParameter(9, Type.INT,fontsize+"");
			jnative.setParameter(10, Type.INT,fontcolor+"");
			jnative.setParameter(11, Type.STRING,bold+"");
			jnative.setParameter(12, Type.STRING,italic+"");
			jnative.setParameter(13, Type.STRING,underline+"");
			jnative.setParameter(14, Type.INT,PlayStyle+"");
			jnative.setParameter(15, Type.INT,Playspeed+"");
			jnative.setParameter(16, Type.INT,times+"");
			 jnative.invoke();
			 revalue= Integer.parseInt(jnative.getRetVal());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return revalue;
		
		
		
	}

	public native static int AddQuitText(int pno, int jno, int qno, int left,
			int top, int width, int height, int FontColor, String fontName,
			int fontSize, boolean b, boolean c, boolean d, String text);

	public native static int AddDClockArea(int pno, int jno, int qno, int left,
			int top, int width, int height, int fontColor, String fontName,
			int fontSize, int fontBold, int Italic, int Underline, int year,
			int week, int month, int day, int hour, int minute, int second,
			int TwoOrFourYear, int HourShow, int format, int spanMode,
			int Advacehour, int Advaceminute);

	public native static int SetTest(int pno, int value);

	public native static int AdjustTime(int PNO);

	public native static int SetPower(int PNO, int power);

	public native static int SetHardPara(int PNO, int Sign, int Mirror,
			int ScanStyle, int LineOrder, int cls, int RGChange, int zhangKong);

	public native static int GetHardPara(int PNO, String FilePath);

	public native static int SearchController(String filePath, String IPAddress);

	public native static int ComSearchController(int PNO, int ComNo,
			int BaudRate, String filePath);

	public native static int SetRemoteNetwork(int pno, String macAddress,
			String ip, String gateway, String subnetmask);

	public native static int SetPowerTimer(int pno, int bTimer, int startHour1,
			int startMinute1, int endHour1, int endMinute1, int startHour2,
			int startMinute2, int endHour2, int endMinute2, int startHour3,
			int startMinute3, int endHour3, int endMinute3);

	public native static int SetBrightnessTimer(int pno, int bTimer,
			int startHour1, int startMinute1, int endHour1, int endMinute1,
			int brightness1, int startHour2, int startMinute2, int endHour2,
			int endMinute2, int brightness2, int startHour3, int startMinute3,
			int endHour3, int endMinute3, int brightness3);

	public native static int SendScreenPara(int pno, int DBColor, int width,
			int height);

	public native static int SetTimingLimit(int pno, int FSecond, int FMinute,
			int FHour, int FDay, int FMonth, int FWeek, int FYear);

	public native static int CancelTimingLimit(int pno);

	public native static int GenerateFile(int PNO, String buffer);

	public static void SendFileArea() {
		LEDControl jc = new LEDControl();
		jc.SetTransMode(3, 3);
		jc.SetSerialPortPara(1, 1, 9600);
		jc.StartSend();
		int j = jc.AddControl(1, 2);
		j = jc.AddProgram(1, 1, 0);
		String path = "c:\\1.rtf";
		jc.AddFileArea(1, 1, 1, 0, 0, 128, 32);
		jc.AddFile(1, 1, 1, 1, path, 320, 96, 32, 255, 20, 10, 1);
		jc.SendControl(1, 1, 0);
	}

	public static void SendQuietArea() {
		LEDControl jc = new LEDControl();
		jc.SetTransMode(3, 3);
		jc.SetSerialPortPara(1, 1, 9600);
		jc.StartSend();
		int j = jc.AddControl(1, 2);
		jc.AddProgram(1, 1, 0);
		String fontname = "宋体";
		String text = "每日灵粮";
		jc.AddQuitText(1, 1, 1, 0, 0, 128, 32, 255, fontname, 24, false, false,
				false, text);
		jc.SendControl(1, 1, 0);
	}

	public static void SendMulTiText() {
		LEDControl jc = new LEDControl();
		jc.SetTransMode(1, 3);
		jc.SetNetworkPara(1, "192.168.1.10");
		jc.StartSend();
		int j = jc.AddControl(1, 2);
		jc.AddProgram(1, 1, 0);
	    jc.AddFileArea(1, 1, 1, 0, 0, 192, 32);
		jc.AddFileString(1, 1, 1, 1, "静止文本", "宋体", 12, 255, false, false,
				false, 1, 128, 32, 32, 255, 20, 1, 2);
		jc.SendControl(1, 1, 0);
	}

	public static void SendSingleLineText() {
		LEDControl jc = new LEDControl();
		jc.SetTransMode(1, 3);
		jc.SetNetworkPara(1, "192.168.1.10");

		jc.StartSend();
		int j = jc.AddControl(1, 2);
		jc.AddProgram(1, 1, 0);
		///8个小屏  192X32
		///三个大屏  128 X48
		
		System.out.println(jc.AddLnTxtString(1, 1, 2, 0, 0, 192, 32, "欢迎光临", "宋体", 20, 255,
				false, false, false, 32, 10, 1));
		
		System.out.println(jc.SendControl(1, 1, 0));
	}

	public static void SendScreen() {
		LEDControl jc = new LEDControl();
		jc.SetTransMode(1, 3);
		//jc.SetSerialPortPara(1, 1, 9600);
		jc.SetNetworkPara(1, "192.168.1.10");
		jc.SendScreenPara(1, 2, 128, 32);
	}

	public static void main(String[] args) {
		/****
		 * 发送屏参
		 **/

		//SendScreen();

		/*******
		 * 
		 * //发送文件
		 */
		// SendFileArea();

		/********* 发送静止文本 **********/
		// SendQuietArea();

		/********** 多行文本 *************/
		 //SendMulTiText();
		/********** 发送单行文本 ******************/
	/*	
		LEDControl led= new LEDControl();
		System.out.println(led.SetTransMode(1,3));
		System.out.println(led.SetNetworkPara(1, "192.168.1.10"));
		led.StartSend();
		System.out.println(led.AddControl(1, 2));
		System.out.println(led.AddProgram(1, 1, 0));
		System.out.println(led.AddLnTxtString(1, 1, 2, 0, 0, 192, 32, "欢迎光临", "宋体", 20, 255,
				false, false, false, 32, 10, 1));
		System.out.println(led.SendControl(1, 1, 0));
		//LEDControl.SendSingleLineText();
*/
		
	
	
	}
}