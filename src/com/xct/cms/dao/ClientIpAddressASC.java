package com.xct.cms.dao;

import java.util.Comparator;

import com.xct.cms.xy.domain.ClientIpAddress;

public class ClientIpAddressASC  implements Comparator{
	public int compare(Object o1, Object o2) {
		ClientIpAddress ww1 = (ClientIpAddress) o1;
		ClientIpAddress ww2 = (ClientIpAddress) o2;
		if ((ww1.getCntname()).compareTo(ww2.getCntname()) > 0)
			return 1;
		else
			return 0;
	}
} 