package com.xct.cms.utils;


import com.jmupdf.enums.ImageType;
import com.jmupdf.exceptions.DocException;
import com.jmupdf.exceptions.DocSecurityException;
import com.jmupdf.page.Page;
import com.jmupdf.page.PagePixels;
import com.jmupdf.page.PageRenderer;
import com.jmupdf.pdf.PdfDocument;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

public class Pdf2Image{

	public boolean PDF2JPGPixels(String pdffilepath,String formatpath){
		   File pdfdirctory=new File(formatpath);
		   if(!pdfdirctory.exists())
			   pdfdirctory.mkdirs();
		   
		   File file=new File(pdffilepath);
		   String filenameString=file.getName();
		   PdfDocument pdfDoc=null;
		   DecimalFormat df=new DecimalFormat("000");
		   PagePixels pp = null;
		   Page page=null;
		   try{
			   if(file.exists()){
			      pdfDoc = new PdfDocument(file.getPath(), "");
			      for (int i = 0; i < pdfDoc.getPageCount(); ++i) {
			    	  page = pdfDoc.getPage(i+1);
				      pp = new PagePixels(page);
				      pp.setRotation(0);
				      pp.drawPage(null, pp.getX0(), pp.getY0(), pp.getX1(), pp.getY1());
			          ImageIO.write(pp.getImage(), "PNG", new File(new StringBuffer(formatpath).append("/")
			        		  .append(filenameString.substring(0, filenameString.indexOf("."))).append(df.format(i)).append(".png").toString()));
			      }
			      return true;
			   }
		    } catch (DocException e) {
		      e.printStackTrace();
		    } catch (DocSecurityException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }finally{
		    	if(null!=pp)
		    		pp.dispose();
		    	if(null!=pdfDoc)
			      pdfDoc.dispose();
		    }
		   return false;
		}
	
	public boolean PDF2JPG_ARGB(String pdffilepath,String formatpath){
		   File pdfdirctory=new File(formatpath);
		   if(!pdfdirctory.exists())
			   pdfdirctory.mkdirs();
		   
		   File file=new File(pdffilepath);
		   String filenameString=file.getName();
		   PdfDocument pdfDoc=null;
		   PageRenderer render=null;
		   DecimalFormat df=new DecimalFormat("000");
		   try{
			   if(file.exists()){
			      pdfDoc = new PdfDocument(file.getPath(), "");
			      render = new PageRenderer(2.0F, -1, ImageType.IMAGE_TYPE_ARGB);
			      for (int i = 0; i < pdfDoc.getPageCount(); ++i) {
			        render.setPage(pdfDoc.getPage(i + 1));
			        render.render(true);
			        ImageIO.write(render.getImage(), "PNG", new File(new StringBuffer(formatpath).append("/")
			        .append(filenameString.substring(0, filenameString.indexOf("."))).append(df.format(i)).append(".png").toString()));
			      }
			      return true;
			   }
		    } catch (DocException e) {
		      e.printStackTrace();
		    } catch (DocSecurityException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }finally{
		    	if(null!=render)
		    	 render.dispose();
		    	if(null!=pdfDoc)
			      pdfDoc.dispose();
		    }
		   return false;
		}
	
	public boolean PDF2JPG_RGB(String pdffilepath,String formatpath){
		   File pdfdirctory=new File(formatpath);
		   if(!pdfdirctory.exists())
			   pdfdirctory.mkdirs();
		   
		   File file=new File(pdffilepath);
		   String filenameString=file.getName();
		   PdfDocument pdfDoc=null;
		   PageRenderer render=null;
		   DecimalFormat df=new DecimalFormat("000");
		   try{
			   if(file.exists()){
			      pdfDoc = new PdfDocument(file.getPath(), "");
			      render = new PageRenderer(2.0F, -1, ImageType.IMAGE_TYPE_RGB);
			      for (int i = 0; i < pdfDoc.getPageCount(); ++i) {
			        render.setPage(pdfDoc.getPage(i + 1));
			        render.render(true);
			        ImageIO.write(render.getImage(), "JPG", new File(new StringBuffer(formatpath).append("/")
			        .append(filenameString.substring(0, filenameString.indexOf("."))).append(df.format(i)).append(".jpg").toString()));
			      }
			      return true;
			   }
		    } catch (DocException e) {
		      e.printStackTrace();
		    } catch (DocSecurityException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }finally{
		    	if(null!=render)
		    	 render.dispose();
		    	if(null!=pdfDoc)
			      pdfDoc.dispose();
		    }
		   return false;
		}
	
	public boolean PDF2JPG(String pdffilepath,String formatpath,String zipfilepath){
	   
	   File file=new File(pdffilepath);
	   String filenameString=file.getName();
	   PdfDocument pdfDoc=null;
	   PageRenderer render=null;
	   DecimalFormat df=new DecimalFormat("000");
	   try{
		   if(file.exists()){
		      pdfDoc = new PdfDocument(file.getPath(), "");
		      render = new PageRenderer(2.0F, -1, ImageType.IMAGE_TYPE_RGB);
		      for (int i = 0; i < pdfDoc.getPageCount(); ++i) {
		        render.setPage(pdfDoc.getPage(i + 1));
		        render.render(true);
		        ImageIO.write(render.getImage(), "JPG", new File(new StringBuffer(formatpath)
		        .append(filenameString.substring(0, filenameString.indexOf("."))).append(df.format(i)).append(".jpg").toString()));
		      }
		      
		      Util.fileToZip(zipfilepath+"/pdf2jpg.zip", formatpath);
		      return true;
		   }
	    } catch (DocException e) {
	      e.printStackTrace();
	    } catch (DocSecurityException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }finally{
	    	if(null!=render)
	    	 render.dispose();
	    	if(null!=pdfDoc)
		      pdfDoc.dispose();
	    }
	   return false;
	}
  public static void main(String[] args){
	  new Pdf2Image().PDF2JPG("C:\\TTRPdfToJpg\\1f.pdf", "C:\\TTRPdfToJpg\\fomatimage\\", "C:\\TTRPdfToJpg\\");
  }

 
}
