package com.xct.cms.utils;

import java.util.ArrayList;
import java.util.List;

public class PageDAO {
	public List getPageList(List list, int pagenum,int pagesize){
		List list1=new ArrayList();
		///////System.out.println(pagenum+" "+pagesize);
		///////System.out.println("list.size() ="+list.size());
		int max=pagesize*pagenum;
		if(list.size()<max){
			max=list.size();
		}
		///////System.out.println("max ="+max);
		for(int i=(pagenum-1)*pagesize;i<max;i++){
		list1.add(list.get(i));
		
		}
		///////System.out.println("list1.size()="+list1.size());
		return list1;
	}
}
