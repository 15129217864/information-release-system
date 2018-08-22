package com.xct.cms.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {
	
    //转出后高清,但会有毛刺
    public static void FormatJpg(final String formimag){
   	 
//   	    System.out.println(new SimpleDateFormat("hh:mm:ss").format(new Date())+" Image convert start---1-->"+formimag);
   	 
		int targetW=1920;
        int targetH=1080;
   	 
		File _file = new File(formimag); 
		Image source;
		try {
			source = javax.imageio.ImageIO.read(_file);
	 		int width = source.getWidth(null); 
	 		int height = source.getHeight(null); 
	 		double sx = (double) targetW /width;
	 		double sy = (double) targetH /height;
	 
	 		if (sx > sy) {
	 			sx = sy;
	 			targetW = (int) (sx * width);
	 		} else {
	 			sy = sx;
	 			targetH = (int) (sy * height);
	 		}
	 		
			if(width>1920){
				
				BufferedImage tag = new BufferedImage(targetW, targetH,BufferedImage.TYPE_INT_RGB);
				Graphics2D g = tag.createGraphics();
				g.drawImage(source, 0, 0, targetW, targetH, null); 
				g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
				FileOutputStream out = new FileOutputStream(formimag); // 
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				
				JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tag);
				param.setQuality(70f, true);
				encoder.encode(tag, param);
				out.flush();
				out.close();
				g.dispose();
//				System.out.println(new SimpleDateFormat("hh:mm:ss").format(new Date())+" Image convert success---2-->"+formimag);
				/*encoder.encode(tag); // JPEG
				 out.close();*/
			}
			source.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
     public static void FormatJpg2(String formimag) throws Exception {

		File _file = new File(formimag); // 
		Image src = javax.imageio.ImageIO.read(_file); // Image
		
		int width = src.getWidth(null); // 
		int height = src.getHeight(null); // 
		if(formimag.endsWith("jpg")||formimag.endsWith( "JPG")){
			if(width>=3000){
				BufferedImage tag = new BufferedImage(width / 3, height / 3,BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(src, 0, 0, width / 3, height / 3, null); // 
				FileOutputStream out = new FileOutputStream(formimag); // 
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(tag); // JPEG
				out.close();
			}else if(width>=2000){
				BufferedImage tag = new BufferedImage(width / 2, width / 2,BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(src, 0, 0, width / 2,width / 2, null); // 
				FileOutputStream out = new FileOutputStream(formimag); // 
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				
				JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tag);
				param.setQuality(70f, true);
				encoder.encode(tag, param);
				out.flush();
				out.close();
				
				/*encoder.encode(tag); // JPEG
				 out.close();*/
			}
	    }
	}
     


public static BufferedImage resize(BufferedImage source, int targetW,int targetH) {
	
	// targetWtargetH
	int type = source.getType();
	BufferedImage target = null;
	double sx = (double) targetW / source.getWidth();
	double sy = (double) targetH / source.getHeight();
	// targetWtargetH
	// if else
	if (sx > sy) {
		sx = sy;
		targetW = (int) (sx * source.getWidth());
	} else {
		sy = sx;
		targetH = (int) (sy * source.getHeight());
	}
	if (type == BufferedImage.TYPE_CUSTOM) { // handmade
		ColorModel cm = source.getColorModel();
		WritableRaster raster = cm.createCompatibleWritableRaster(targetW,targetH);
		boolean alphaPremultiplied = cm.isAlphaPremultiplied();
		target = new BufferedImage(cm, raster, alphaPremultiplied, null);
	} else
		target = new BufferedImage(targetW, targetH, type);
	
	Graphics2D g = target.createGraphics();
	// smoother than exlax:
	g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
	g.dispose();
	return target;
}

	public static void saveImageAsJpg(String fromFileStr, String saveToFileStr,int width, int hight) throws Exception {
		
		File fromFile = new File(fromFileStr);
		BufferedImage srcImage = ImageIO.read(fromFile);
		
		if(srcImage.getWidth()>1920){
			// String ex =
			// fromFileStr.substring(fromFileStr.indexOf("."),fromFileStr.length());
			String imgType = "JPEG";
			if (fromFileStr.toLowerCase().endsWith(".png")) {
				imgType = "PNG";
			}
			// System.out.println(ex);
			File saveFile = new File(saveToFileStr);
			
			if (width > 0 || hight > 0) {
				srcImage = resize(srcImage, width, hight);
			}
			ImageIO.write(srcImage, imgType, saveFile);
		}
	}
	
	
	public static void ConvertImage(String filepath){
		
		File file=new File(filepath);
		if(file.exists()&&file.isDirectory()){
			File []tempfile=file.listFiles();
			List<String>list=formatList(tempfile);
			
			for (String string : list) {
				FormatJpg(string);
			}
		}
	}

	public static List<String> formatList(File[] filepath) {
		
		String[] str=	new String[] { ".gif", ".bmp", ".jpg", ".png", ".GIF",".BMP", ".JPG", ".PNG" };
		List<String>list=new ArrayList<String>();
		
		if (str != null && str.length > 0) {
			for (int i = 0,n=filepath.length; i < n; i++) {
				String file = filepath[i].getPath().trim();
				if (isBeanType(str, file)) {
					list.add(file);
				}
			}
		}
		return list;
	}

	static boolean isBeanType(String[] str, String file_Type) {
		for (int i = 0; i < str.length; i++) {
			if (file_Type.endsWith(str[i])){
				return true;
			}
		}
		return false;

	}
	
	public static void main(String[] args) {
		
		if(args.length>0){
			ConvertImage(args[0]);
		}else {
			System.out.println("no args");
		}
		
//		try {
//		// 1(from),2(to),3(),4()
//			saveImageAsJpg("c:/20121023171441781391.jpg", "c:/cdbig.jpg",	1920, 1080);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			FormatJpg("c:/20121023171441781391.jpg");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
			FormatJpg("c:/20121023171441781391.jpg");
			FormatJpg("c:/20131011102211578210.jpg");
			FormatJpg("c:/201310111021303282772.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
