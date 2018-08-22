package com.xct.cms.utils;

import java.util.Comparator;

import com.xct.cms.domin.ProgramHistory;

public class ProgramListCompareter implements Comparator{

	public int compare(Object o1, Object o2) {
		ProgramHistory programhistory1=(ProgramHistory)o1;
		ProgramHistory programhistory2=(ProgramHistory)o2;
		/////System.out.println();
		if (programhistory1.getProgram_SetDate().compareTo(programhistory2.getProgram_SetDate()) > 0)
			return 1;
		else
			return 0;
		
	}

}
