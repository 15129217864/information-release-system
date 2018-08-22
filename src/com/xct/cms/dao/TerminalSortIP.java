package com.xct.cms.dao;

import java.util.Comparator;

import com.xct.cms.domin.Terminal;

public class TerminalSortIP  implements Comparator{
	public int compare(Object o1, Object o2) {
		Terminal ww1 = (Terminal) o1;
		Terminal ww2 = (Terminal) o2;
		long ip1=Long.parseLong(ww1.getCnt_ip().replace(".", ""));
		long ip2=Long.parseLong(ww2.getCnt_ip().replace(".", ""));
		if (ip1>ip2)
			return 1;
		else
			return 0;
	}
} 