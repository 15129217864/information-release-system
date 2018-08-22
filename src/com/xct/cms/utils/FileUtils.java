package com.xct.cms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtils {

	/**
	 * ����һ��Ŀ¼������Ŀ¼���ļ�������һ��Ŀ¼
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFolder(File src, File dest) {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// �ݹ鸴��
				copyFolder(srcFile, destFile);
			}
		} else {
			FileInputStream input;
			try {
				input = new FileInputStream(src);
				FileOutputStream output = new FileOutputStream(dest);
				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = input.read(b)) != -1) {
					output.write(b, 0, len);
				}
				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//System.out.println("[from "+src.getAbsolutePath()+" ,to "+dest.getAbsolutePath()+"]");
		}
	}
	
	/**
	 * ����һ���ļ�������һ��������λ��
	 * 
	 * @param from
	 *            ���������ļ�
	 * @param to
	 *            ����������ļ�
	 */
	public static boolean copyTo(String from, String to) {

		File f = new File(from);
		if (f.exists()) {

			if (f.isFile()) {

				File Dirto = new File(to).getParentFile();
				if (!Dirto.exists())
					Dirto.mkdir();

				FileInputStream input;
				try {
					input = new FileInputStream(f);
					FileOutputStream output = new FileOutputStream(to);
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}

			} else {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

}
