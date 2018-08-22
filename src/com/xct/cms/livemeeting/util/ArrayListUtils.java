package com.xct.cms.livemeeting.util;

import java.util.ArrayList;

public class ArrayListUtils<E> extends ArrayList<E>  {
	
	private static final long serialVersionUID = 6100537859914339507L;

	public boolean add(E e) {
		if (e instanceof ProgramDirectName) {
			ProgramDirectName d = (ProgramDirectName) e;
			for (int i = 0; i < super.size(); i++) {
				ProgramDirectName t = (ProgramDirectName) super.get(i);
				if (d.equals(t)) {//��ͬ���ԣ�ͳ����������ƴ���ַ����޸�����
					t.setProgramdept(t.getProgramdept()+"," + d.getProgramdept());
					return true;
				}
			}
		}
		return super.add(e);
	}

}
