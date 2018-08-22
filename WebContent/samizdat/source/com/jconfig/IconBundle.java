/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig;

/**
Represents a pair of related icons, a small icon and a large icon.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface IconBundle {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
A large 32-bit color icon.
*/

	public static final int		ICON_LARGE = 1;

/**
A small 32-bit color icon.
*/

	public static final int		ICON_SMALL = 2;

/**
An alignment value.
*/

	public static final int		ICON_ALIGN_NONE = 0x0;
	
/**
An alignment value.
*/

	public static final int		ICON_ALIGN_VCENTER = 0x1;
	
/**
An alignment value.
*/

	public static final int		ICON_ALIGN_TOP = 0x2;
	
/**
An alignment value.
*/

	public static final int		ICON_ALIGN_BOTTOM = 0x3;
	
/**
An alignment value.
*/

	public static final int		ICON_ALIGN_HCENTER = 0x4;
	
/**
An alignment value.
*/

	public static final int		ICON_ALIGN_LEFT = 0x8;
	
/**
An alignment value.
*/

	public static final int		ICON_ALIGN_RIGHT = 0xC;
	
/**
A transform value.
*/

	public static final int		ICON_CHANGE_NONE				= 0x0;

/**
A transform value.
*/

	public static final int		ICON_CHANGE_DISABLED			= 0x1;

/**
A transform value.
*/

	public static final int		ICON_CHANGE_OFFLINE			= 0x2;
		
/**
A transform value.
*/

	public static final int		ICON_CHANGE_OPEN				= 0x3;
		
/**
A transform value.
*/

	public static final int		ICON_CHANGE_LABEL1			= 0x0100;
		
/**
A transform value.
*/

	public static final int		ICON_CHANGE_LABEL2			= 0x0200;
		
/**
A transform value.
*/

	public static final int		ICON_CHANGE_LABEL3			= 0x0300;
		
/**
A transform value.
*/

	public static final int		ICON_CHANGE_LABEL4			= 0x0400;
		
/**
A transform value.
*/

	public static final int		ICON_CHANGE_LABEL5			= 0x0500;

/**
A transform value.
*/

	public static final int		ICON_CHANGE_LABEL6			= 0x0600;
		
/**
A transform value.
*/

	public static final int		ICON_CHANGE_LABEL7			= 0x0700;
		
/**
A transform value.
*/

	public static final int		ICON_CHANGE_SELECTED			= 0x4000;

/**
Puts the RGB pixel data for the indicated icon into the given array. If no error occurs, return 0;
otherwise, a non-zero error code is returned.
@param whichIcon one of the previously listed icon styles.
@param xform one of the transform values listed above
@param align one of the alignment values listed above
@param pData an array which will be filled with the appropriate values. This array must be exactly
equal in size to the width times the height of the desired icon.
*/

	int getIcon( int whichIcon, int xform, int align, int pData[] );

/**
Returns the width of the indicated icon.
@param whichIcon one of the previously listed icon styles.
*/

	int getIconWidth( int whichIcon );

/**
Returns the height of the indicated icon.
@param whichIcon one of the previously listed icon styles.
*/

	int getIconHeight( int whichIcon );
}
