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
		
		String contextString = "iText��һ���ܹ����ٲ���PDF�ļ���java��⡣iText��java�������ЩҪ���������ı������ͼ�ε�ֻ���ĵ��Ǻ����õġ��������������java Servlet�кܺõĸ��ϡ�ʹ��iText��PDF�ܹ�ʹ����ȷ�Ŀ���Servlet�������";
			   //�� �壬�� �壬�� Բ���� �壬΢ �� �� �ڣ��� �� �壬�� ��
		createRTFContext(FILE_NAME,contextString,"�� ��",26,Color.RED,1,1,1);
		
	}

	public static boolean createRTFContext(String path,String contextString,String fontname,int size,Color color,int isbold,int isitalic,int underline){

		Document document = new Document(PageSize.A4);
		try {
			RtfWriter2.getInstance(document, new FileOutputStream(path));
		
		document.open();

		// ������������
		 //��������  �������������ĵģ����м�������ַ�ǰ��ӿո�   
	    //����д����ʵ���κ�Ľ����ֱ��д��word������Ϊ "��?��?��",����д���о��ܹ��졣   
	    //��д�ְ��д򿪺�word�д򿪲�һ��
		
        //�� �壬�� �壬�� Բ���� �壬΢ �� �� �ڣ��� �� �壬�� ��
		RtfFont contextFont = new RtfFont(fontname, size, Font.BOLD, color);  
		
//		if(isbold==1)
//		  contextFont.setStyle(Font.BOLD);
//		if(isitalic==1)
//		  contextFont.setStyle(Font.ITALIC);
//	    if(underline==1)
//		  contextFont.setStyle(Font.UNDERLINE);
		
//		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);		

		// ����������

//		Font titleFont = new Font(bfChinese, 12, Font.BOLD);

		// ����������

//		Font contextFont = new Font(bfChinese, 10, Font.NORMAL); //fontname,fontsize,fontcolor,isbold, isitalic, isunderline
        
//		Paragraph title = new Paragraph("����");
//		// ���ñ����ʽ���뷽ʽ
//		title.setAlignment(Element.ALIGN_CENTER);
//		title.setFont(titleFont);
//		document.add(title);


		Paragraph context = new Paragraph(contextString);

		// ���ĸ�ʽ�����

		context.setAlignment(Element.ALIGN_LEFT);

		context.setFont(contextFont);
		
//		// ����һ���䣨���⣩�յ�����
//		context.setSpacingBefore(20);
//		// ���õ�һ�пյ�����
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

//		// //�ڱ��ĩβ���ͼƬ
//		Image png = Image.getInstance("c:/xct.jpg");
//		document.add(png);

	}

}
