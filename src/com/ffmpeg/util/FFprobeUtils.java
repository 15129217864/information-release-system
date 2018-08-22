package com.ffmpeg.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.ffmpeg.util.json.Videofile;
import com.google.gson.Gson;

//ͨ��ffprobe ��ȡ��Ƶ��Ϣ��������ߣ�����ʱ�����ļ���С�� �ļ���ʽ�ȵȡ�
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

   //ͨ��ffprobe ��ȡ��Ƶ��Ϣ
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
		
		System.out.println("��"+videofile.getStreams().get(0).getWidth()+"����: "+videofile.getStreams().get(0).getHeight());
		System.out.println("�ļ���С��"+getFileSize(Long.parseLong(videofile.getFormat().getSize()))
		               +"�� ����ʱ��"+ generateTime(Long.parseLong((String.valueOf(((int)Double.parseDouble(videofile.getFormat().getDuration())))))));
	}
	 //============================================================================
	/**
     * ������תʱ����
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
     * ����תʱ����
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
		//����ֽ�������1024����ֱ����BΪ��λ�������ȳ���1024����3λ��̫��������
		if (size < 1024) {
			return String.valueOf(size) + "B";
		} else {
			size = size / 1024;
		}
		//���ԭ�ֽ�������1024֮������1024�������ֱ����KB��Ϊ��λ
		//��Ϊ��û�е���Ҫʹ����һ����λ��ʱ��
		//����ȥ�Դ�����
		if (size < 1024) {
			return String.valueOf(size) + "KB";
		} else {
			size = size / 1024;
		}
		if (size < 1024) {
			//��Ϊ�����MBΪ��λ�Ļ���Ҫ�������1λС����
			//��ˣ��Ѵ�������100֮����ȡ��
			size = size * 100;
			return String.valueOf((size / 100)) + "."
					+ String.valueOf((size % 100)) + "MB";
		} else {
			//�������Ҫ��GBΪ��λ�ģ��ȳ���1024����ͬ���Ĵ���
			size = size * 100 / 1024;
			return String.valueOf((size / 100)) + "."
					+ String.valueOf((size % 100)) + "GB";
		}
	}
}
