package com.xct.cms.utils;

public class Color {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	public static int[] Color_RGB(int rgb_color){
		java.awt.Color color=new java.awt.Color(rgb_color);
		int [] colors= new int [3];
		colors[0]=color.getRed();
		colors[1]=color.getGreen();
		colors[2]=color.getBlue();
		return colors;
	}

}
