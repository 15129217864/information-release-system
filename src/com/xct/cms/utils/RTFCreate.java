package com.xct.cms.utils;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfFont;

public class RTFCreate {

	private static final String FILE_NAME = "c:/led.rtf";

	public static void main(String[] args) {
		
		String contextString = "iText是一个能够快速产生PDF文件的java类库。iText的java类对于那些要产生包含文本，表格，图形的只读文档是很有用的。它的类库尤其与java Servlet有很好的给合。使用iText与PDF能够使你正确的控制Servlet的输出。";
			   //宋 体，黑 体，幼 圆，楷 体，微 软 雅 黑，新 宋 体，隶 书
		createRTFContext(FILE_NAME,contextString,"宋 体",26,Color.RED,1,1,1);
		
	}

	public static boolean createRTFContext(String path,String contextString,String fontname,int size,Color color,int isbold,int isitalic,int underline){

		Document document = new Document(PageSize.A4);
		try {
			RtfWriter2.getInstance(document, new FileOutputStream(path));
		
		document.open();

		// 设置中文字体
		 //设置字体  字体名称是中文的，在中间的中文字符前后加空格，   
	    //这种写法是实验多次后的结果，直接写在word中体现为 "华?行?楷",这种写法感觉很怪异。   
	    //在写字板中打开和word中打开不一样
		
        //宋 体，黑 体，幼 圆，楷 体，微 软 雅 黑，新 宋 体，隶 书
		RtfFont contextFont = new RtfFont(fontname, size, Font.BOLD, color);  
		
//		if(isbold==1)
//		  contextFont.setStyle(Font.BOLD);
//		if(isitalic==1)
//		  contextFont.setStyle(Font.ITALIC);
//	    if(underline==1)
//		  contextFont.setStyle(Font.UNDERLINE);
		
//		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);		

		// 标题字体风格

//		Font titleFont = new Font(bfChinese, 12, Font.BOLD);

		// 正文字体风格

//		Font contextFont = new Font(bfChinese, 10, Font.NORMAL); //fontname,fontsize,fontcolor,isbold, isitalic, isunderline
        
//		Paragraph title = new Paragraph("标题");
//		// 设置标题格式对齐方式
//		title.setAlignment(Element.ALIGN_CENTER);
//		title.setFont(titleFont);
//		document.add(title);


		Paragraph context = new Paragraph(contextString);

		// 正文格式左对齐

		context.setAlignment(Element.ALIGN_LEFT);

		context.setFont(contextFont);
		
//		// 离上一段落（标题）空的行数
//		context.setSpacingBefore(20);
//		// 设置第一行空的列数
//		context.setFirstLineIndent(20);

		
			document.add(context);
			document.close();
			return true;
		} catch (DocumentException e) {
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

//		// //在表格末尾添加图片
//		Image png = Image.getInstance("c:/xct.jpg");
//		document.add(png);

	}

}
