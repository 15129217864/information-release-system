package com.xct.cms.utils;

import java.util.Comparator;

public class WantMeetingRoomComparatorForSort implements Comparator {

	public int compare(Object o1, Object o2) {
		String ww1 = (String) o1;
		String ww2 = (String) o2;
		if ((ww1).compareTo(ww2) < 0)
			return 1;
		else
			return 0;
	}
}
