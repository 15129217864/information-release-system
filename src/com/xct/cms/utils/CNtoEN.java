package com.xct.cms.utils;


/**
 * @author Owner
 *
 */
public class CNtoEN {

	private static final int GB_SP_DIFF = 160;

	private static final int[] secPosvalueList = { 1601, 1637, 1833, 2078,
			2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730,
			3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5600 };

	private static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'w', 'x', 'y', 'z' };

	/**
	 * @param oriStr  文件名
	 * @return  反回将中文文件名转化成以拼音开头的文件名 如当中有空将被去出
	 */
	public static String getLetter(String oriStr) {
//		String str = oriStr;
		String str = oriStr.replaceAll(" ","");
		StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;

		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			temp = new char[] { ch };
			byte[] uniCode = new String(temp).getBytes();

			if (uniCode[0] < 128 && uniCode[0] > 0) {
				buffer.append(temp);
			} else {
				buffer.append(convert(uniCode));
			}
		}
		return buffer.toString();
	}
	
   

	private static char convert(byte[] bytes) {

		char result = '-';
		int secPosvalue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= GB_SP_DIFF;
		}
		secPosvalue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
			if (secPosvalue >= secPosvalueList[i]
					&& secPosvalue < secPosvalueList[i + 1]) {
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}

}
