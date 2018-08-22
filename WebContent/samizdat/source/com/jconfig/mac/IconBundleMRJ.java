/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig.mac;

import com.jconfig.*;

/**
Represents a Mac icon suite. Created with the icon suite handle, and calls native code to
get one of the icons in the suite into an array of pixels.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class IconBundleMRJ implements IconBundle {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	
	private int			hSuite;

/**
Create from an icon suite.
@param ste the icon suite handle
*/

	IconBundleMRJ( int ste ) {
		hSuite = ste;
	}

/**
Try to get rid of the icon suite handle.
Of course, not guaranteed to be called.
*/
	
	protected void finalize() throws Throwable {
		AppUtilsMRJ.disposeIconSuite( hSuite, 1 );
		super.finalize();
	}
	
/**
Get the bits of the icon, in Java's ARGB format, into the pData array.
@param whichIcon one of the icon sizes listed in IconBundle.java.
@param xform one of the transform values listed in IconBundle.java
@param align one of the alignment values listed in IconBundle.java
@param pData an array of ints which will hold the Java format ARGB data for the icon
This must have at least width * height elements
*/
	
	public int getIcon( int whichIcon, int xform, int align, int pData[] ) {
		int			w, h;

		checkWhichIconValue( whichIcon );

		if ( whichIcon == ICON_LARGE )
			w = h = 32;
		else
			w = h = 16;

		return AppUtilsMRJ.plotIcon( whichIcon, w, h, hSuite, xform, align, pData );
	}

/**
Throws an IllegalArgumentException if (whichIcon != ICON_LARGE && whichIcon != ICON_SMALL)
*/

	void checkWhichIconValue( int whichIcon ) {	
		if ( whichIcon != ICON_LARGE && whichIcon != ICON_SMALL )
			throw new IllegalArgumentException( "bad whichIcon value: " + whichIcon );
	}

	public int getIconWidth( int whichIcon ) {
		checkWhichIconValue( whichIcon );

		if ( whichIcon == ICON_LARGE )
			return 32;
		else
			return 16;
	}

	public int getIconHeight( int whichIcon ) {
		checkWhichIconValue( whichIcon );

		if ( whichIcon == ICON_LARGE )
			return 32;
		else
			return 16;
	}
}

