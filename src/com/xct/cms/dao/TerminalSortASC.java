package com.xct.cms.dao;

import java.util.Comparator;

import com.xct.cms.domin.Terminal;

public class TerminalSortASC  implements Comparator{
	public int compare(Object o1, Object o2) {
		Terminal ww1 = (Terminal) o1;
		Terminal ww2 = (Terminal) o2;
		boolean bool =(null!=ww1&&null!=ww2)&&(null!=ww1.getCnt_name()&&null!=ww2.getCnt_name());
		if (bool&&(ww1.getCnt_name()).compareTo(ww2.getCnt_name()) > 0)
			return 1;
		else
			return 0;
	}

} 