package com.excel.bean;

public class NoticeBeanKH {

	private String cell1;
	private String cell2;
	private String cell3;
	private String cell4;
	private String cell5;
	private String tilte;
	private String endtitle;
	private int span;
	private String cel_list[];
	
	public String[] getCel_list() {
		return cel_list;
	}

	public void setCel_list(String[] cel_list) {
		this.cel_list = cel_list;
	}



	public String getCell5() {
		return cell5;
	}

	public void setCell5(String cell5) {
		this.cell5 = cell5;
	}

	public NoticeBeanKH() {
		
	}



	

	public NoticeBeanKH(String cell1, String cell2, String cell3, String tilte, String endtitle, int span, String[] cel_list) {
		super();
		// TODO 自动生成构造函数存根
		this.cell1 = cell1;
		this.cell2 = cell2;
		this.cell3 = cell3;
		this.tilte = tilte;
		this.endtitle = endtitle;
		this.span = span;
		this.cel_list = cel_list;
	}

	public NoticeBeanKH(String cell1, String cell2, String cell3, String cell4, String tilte, String endtitle, int span, String[] cel_list) {
		super();
		// TODO 自动生成构造函数存根
		this.cell1 = cell1;
		this.cell2 = cell2;
		this.cell3 = cell3;
		this.cell4 = cell4;
		this.tilte = tilte;
		this.endtitle = endtitle;
		this.span = span;
		this.cel_list = cel_list;
	}

	public NoticeBeanKH(String cell1, String cell2, String cell3, String cell4, String cell5, String tilte, String endtitle, int span, String[] cel_list) {
		super();
		// TODO 自动生成构造函数存根
		this.cell1 = cell1;
		this.cell2 = cell2;
		this.cell3 = cell3;
		this.cell4 = cell4;
		this.cell5 = cell5;
		this.tilte = tilte;
		this.endtitle = endtitle;
		this.span = span;
		this.cel_list = cel_list;
	}

	public String getEndtitle() {
		return endtitle;
	}

	public void setEndtitle(String endtitle) {
		this.endtitle = endtitle;
	}

	public String getTilte() {
		return tilte;
	}

	public void setTilte(String tilte) {
		this.tilte = tilte;
	}

	public String getCell1() {
		return cell1;
	}
	
	public void setCell1(String cell1) {
		this.cell1 = cell1;
	}
	
	public String getCell2() {
		return cell2;
	}
	public void setCell2(String cell2) {
		this.cell2 = cell2;
	}
	public String getCell3() {
		return cell3;
	}
	public void setCell3(String cell3) {
		this.cell3 = cell3;
	}
	public String getCell4() {
		return cell4;
	}
	public void setCell4(String cell4) {
		this.cell4 = cell4;
	}

	public int getSpan() {
		return span;
	}

	public void setSpan(int span) {
		this.span = span;
	}
	
	
}
