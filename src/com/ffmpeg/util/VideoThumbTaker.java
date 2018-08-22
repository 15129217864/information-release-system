package com.ffmpeg.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * @author Bashan FFMPEG homepage http://ffmpeg.org/about.html By Google Get
 *         first and last thumb of a video using Java and FFMpeg From
 *         http://www.codereye.com/2010/05/get-first-and-last-thumb-of-video-using.html
 */

public class VideoThumbTaker {//��ȡ��Ƶָ������ʱ���ͼƬ
	protected String ffmpegApp;

	public VideoThumbTaker(String ffmpegApp) {
		this.ffmpegApp = ffmpegApp;
	}

	@SuppressWarnings("unused")
	/****
	 * ��ȡָ��ʱ���ڵ�ͼƬ
	 * 
	 * @param videoFilename:��Ƶ·��
	 * @param thumbFilename:ͼƬ����·��
	 * @param width:ͼƬ��
	 * @param height:ͼƬ��
	 * @param hour:ָ��ʱ
	 * @param min:ָ����
	 * @param sec:ָ����
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void getThumb(String videoFilename, String thumbFilename, int width, int height, int hour, int min,
			float sec) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(ffmpegApp, "-y", "-i", videoFilename, "-vframes", "1", "-ss",
				hour + ":" + min + ":" + sec, "-f", "mjpeg", "-s", width + "*" + height, "-an", thumbFilename);

		Process process = processBuilder.start();

		InputStream stderr = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		String line;
		while ((line = br.readLine()) != null)
			;
		process.waitFor();

		if (br != null)
			br.close();
		if (isr != null)
			isr.close();
		if (stderr != null)
			stderr.close();
	}

	public static void main(String[] args) {
		
		VideoThumbTaker videoThumbTaker = new VideoThumbTaker(System.getProperty("user.dir")+"\\ffmpeg\\64\\ffprobe.exe");
		try {
			videoThumbTaker.getThumb(System.getProperty("user.dir")+"\\video\\201409091525310621087.avi",
					System.getProperty("user.dir")+"\\thumb\\Test.png", 1024,768, 0, 0, 9);
			
//			videoThumbTaker.getThumb(System.getProperty("user.dir")+"\\video\\201611281830057142555.mov",
//					System.getProperty("user.dir")+"\\thumb\\Test.png", 1024,768, 0, 0, 9);
//			videoThumbTaker.getThumb(System.getProperty("user.dir")+"\\video\\201706301534433477840.mpg",
//					System.getProperty("user.dir")+"\\thumb\\Test.png", 1024,768, 0, 0, 9);
			System.out.println("over");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}