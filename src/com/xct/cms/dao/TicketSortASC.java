package com.xct.cms.dao;

import java.util.Comparator;

import com.xct.cms.httpclient.Ticket;

public class TicketSortASC  implements Comparator{
	public int compare(Object o1, Object o2) {
		Ticket ww1 = (Ticket) o1;
		Ticket ww2 = (Ticket) o2;
		if ((ww1.getScrNoDesc().substring(3)).compareTo(ww2.getScrNoDesc().substring(3))> 0)
			return 1;
		else
			return 0;
	}

} 