package com.xct.cms.dao;

import java.util.Comparator;

import com.xct.cms.domin.ProgramHistory;
import com.xct.cms.xy.domain.ClientIpAddress;
import com.xct.cms.xy.domain.ClientProjectBean;

public class ProgramHistoryASC  implements Comparator{
	public int compare(Object o1, Object o2) {
		ClientProjectBean ww1 = (ClientProjectBean) o1;
		ClientProjectBean ww2 = (ClientProjectBean) o2;
		if ((ww1.getSetdate()).compareTo(ww2.getSetdate()) > 0)
			return 1;
		else
			return 0;
	}

} 