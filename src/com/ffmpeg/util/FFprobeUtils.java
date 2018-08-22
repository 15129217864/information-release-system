package com.ffmpeg.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.ffmpeg.util.json.Videofile;
import com.google.gson.Gson;

//通过ffprobe 获取视频信息，包括宽高，播放时长，文件大小， 文件格式等等。
public class FFprobeUtils {
	
   public static void main(String[] args) {
		
		String videofile=System.getProperty("user.dir")+"\\video\\201706301534433477840.mpg";
//		String videofile=System.getProperty("user.dir")+"\\video\\201611281830057142555.mov";
//		String videofile=System.getProperty("user.dir")+"\\video\\201409091525310621087.avi";

		try {
			getInfo4Json(System.getProperty("user.dir")+"\\ffmpeg\\64\\ffprobe.exe",videofile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

   //通过ffprobe 获取视频信息
	public static void getInfo4Json(String ffprobeApp,String videoFilename) throws Exception {
		
//		ffprobe -v quiet -print_format json -show_format -show_streams #{source}
		
		ProcessBuilder processBuilder = new ProcessBuilder(ffprobeApp, "-v", "quiet","-print_format", "json","-show_format", "-show_streams", videoFilename);

		Process process = processBuilder.start();

		InputStream stderr = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		String line;
		
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
//		System.out.println(sb.toString());
		Gson gson = new Gson();
		Videofile videofile = gson.fromJson(sb.toString(), Videofile.class);
		
		System.out.println("宽："+videofile.getStreams().get(0).getWidth()+"，高: "+videofile.getStreams().get(0).getHeight());
		System.out.println("文件大小："+getFileSize(Long.parseLong(videofile.getFormat().getSize()))
		               +"， 播放时长"+ generateTime(Long.parseLong((String.valueOf(((int)Double.parseDouble(videofile.getFormat().getDuration())))))));
	}
	 //============================================================================
	/**
     * 将毫秒转时分秒
     *
     * @param time
     * @return
     */
    public static String generateTime2(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02dh%02d:%02ds", hours, minutes, seconds) : String.format("%02dm%02ds", minutes, seconds);
    }
 
    /**
     * 将秒转时分秒
     * @param time
     * @return
     */
    public static String generateTime(long totalSeconds) {
    	long seconds = totalSeconds % 60;
    	long minutes = (totalSeconds / 60) % 60;
    	long hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }
    
    
    public static String getFileSize(long size) {
		//如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
		if (size < 1024) {
			return String.valueOf(size) + "B";
		} else {
			size = size / 1024;
		}
		//如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
		//因为还没有到达要使用另一个单位的时候
		//接下去以此类推
		if (size < 1024) {
			return String.valueOf(size) + "KB";
		} else {
			size = size / 1024;
		}
		if (size < 1024) {
			//因为如果以MB为单位的话，要保留最后1位小数，
			//因此，把此数乘以100之后再取余
			size = size * 100;
			return String.valueOf((size / 100)) + "."
					+ String.valueOf((size % 100)) + "MB";
		} else {
			//否则如果要以GB为单位的，先除于1024再作同样的处理
			size = size * 100 / 1024;
			return String.valueOf((size / 100)) + "."
					+ String.valueOf((size % 100)) + "GB";
		}
	}
}
