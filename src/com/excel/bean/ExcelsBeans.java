package com.excel.bean;

import java.util.ArrayList;
import java.util.List;

public class ExcelsBeans {

	private String title;
	private String updatetime;
	private String colomncounts;
	private String span;
	private List<String> cellslist;

	public ExcelsBeans(String title, String updatetime,String colomncounts,String span, List<String> cellslist) {
		super();
		// TODO 自动生成构造函数存根
		this.title = title;
		this.updatetime = updatetime;
		this.colomncounts = colomncounts;
		this.span=span;
		this.cellslist = cellslist;
	}
	public ExcelsBeans() {
		super();
		// TODO 自动生成构造函数存根
	}
	public List<String> getCellslist() {
		return cellslist;
	}
	public void setCellslist(List<String> cellslist) {
		this.cellslist = cellslist;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getColomncounts() {
		return colomncounts;
	}
	public void setColomncounts(String colomncounts) {
		this.colomncounts = colomncounts;
	}
	public String getSpan() {
		return span;
	}
	public void setSpan(String span) {
		this.span = span;
	}

}
