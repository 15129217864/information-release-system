package com.ffmpeg.util;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

/**
 * 获取视频的信息 FFMPEG homepage http://ffmpeg.org/about.html
 */
public class VideoInfo {// 获取视频信息
	// ffmpeg路径
	private String ffmpegApp;
	// 视频时
	private int hours;
	// 视频分
	private int minutes;
	// 视频秒
	private float seconds;
	// 视频width
	private int width;
	// 视频height
	private int heigt;

	public VideoInfo() {
	}

	public VideoInfo(String ffmpegApp) {
		this.ffmpegApp = ffmpegApp;
	}

	public static void main(String[] args) {
		
//		String videofile=System.getProperty("user.dir")+"\\video\\201706301534433477840.mpg";
//		String videofile=System.getProperty("user.dir")+"\\video\\201611281830057142555.mov";
		String videofile=System.getProperty("user.dir")+"\\video\\201409091525310621087.avi";
		
		VideoInfo videoInfo = new VideoInfo(System.getProperty("user.dir")+"\\ffmpeg\\64\\ffmpeg.exe");
		try {
			videoInfo.getInfo(videofile);
			System.out.println(videoInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void getInfo(String videoFilename) throws IOException, InterruptedException {
		
		String tmpFile = videoFilename + ".tmp.png";
		ProcessBuilder processBuilder = new ProcessBuilder(ffmpegApp, "-y", "-i", videoFilename, "-vframes", "1", "-ss",
				"0:0:0", "-an", "-vcodec", "png", "-f", "rawvideo", "-s", "100*100", tmpFile);

		Process process = processBuilder.start();

		InputStream stderr = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		String line;
		// 打印 sb，获取更多信息。 如 bitrate、width、heigt
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		new File(tmpFile).delete();

//		System.out.println("video info:" + sb);
		Pattern pattern = Pattern.compile("Duration: (.*?),"); //获得时长
		Matcher matcher = pattern.matcher(sb);

		if (matcher.find()) {
			String time = matcher.group(1);
			calcTime(time);
		}

		pattern = Pattern.compile("w:\\d+ h:\\d+");//获得视频大小,新版本用正则表达式获取不正确，没有出现 w 和 h
		matcher = pattern.matcher(sb);

		if (matcher.find()) {
			String wh = matcher.group();
//			System.out.println("wh===>" + wh);
			// w:100 h:100
			String[] strs = wh.split("\\s+");
			if (strs != null && strs.length == 2) {
				width = Integer.parseInt(strs[0].split(":")[1]);
				heigt = Integer.parseInt(strs[1].split(":")[1]);
			}
		}

		process.waitFor();
		if (br != null)
			br.close();
		if (isr != null)
			isr.close();
		if (stderr != null)
			stderr.close();
	}

	private void calcTime(String timeStr) {
		String[] parts = timeStr.split(":");
		hours = Integer.parseInt(parts[0]);
		minutes = Integer.parseInt(parts[1]);
		seconds = Float.parseFloat(parts[2]);
	}

	 //timelen格式:"00:00:10.68"  
    private static int getVideoTimelen(String timelen){  
        int min=0;  
        String strs[] = timelen.split(":");  
        if (strs[0].compareTo("0") > 0) {  
            min+=Integer.valueOf(strs[0])*60*60;//秒  
        }  
        if(strs[1].compareTo("0")>0){  
            min+=Integer.valueOf(strs[1])*60;  
        }  
        if(strs[2].compareTo("0")>0){  
            min+=Math.round(Float.valueOf(strs[2]));  
        }  
        return min;  
    }  
	
	public String getFfmpegApp() {
		return ffmpegApp;
	}

	public void setFfmpegApp(String ffmpegApp) {
		this.ffmpegApp = ffmpegApp;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public float getSeconds() {
		return seconds;
	}

	public void setSeconds(float seconds) {
		this.seconds = seconds;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigt() {
		return heigt;
	}

	public void setHeigt(int heigt) {
		this.heigt = heigt;
	}
	
	public String toString() {
		return "time=>" + hours + ":" + minutes + ":" + seconds + ", width = " + width + ", height= " + heigt;
	}

}