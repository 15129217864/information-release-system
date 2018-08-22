/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig.nix;


import com.jconfig.*;

/**
Superclass for the Nix classes which implement IconBundle.

This is a preliminary class derived from the Windows class of similar name. This class may be replaced
or modified in a future version.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class IconBundleNix implements IconBundle {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	
	IconBundleNix() {
	}

/**
Get the bits of the icon, in Java's ARGB format, into the pData array.
Subclasses should override.
*/
	
	public int getIcon( int whichIcon, int xform, int align, int pData[] ) {
		return -1;
	}

/**
Returns the width of the indicated icon.
*/

	public int getIconWidth( int whichIcon ) {
		checkWhichIconValue( whichIcon );

		if ( whichIcon == ICON_LARGE )
			return 32;
		else
			return 16;
	}

/**
Returns the height of the indicated icon.
*/

	public int getIconHeight( int whichIcon ) {
		checkWhichIconValue( whichIcon );

		if ( whichIcon == ICON_LARGE )
			return 32;
		else
			return 16;
	}

/**
If 'whichIcon' is not ICON_LARGE or ICON_SMALL, throws an IllegalArgumentException.
*/

	void checkWhichIconValue( int whichIcon ) {	
		if ( whichIcon != ICON_LARGE && whichIcon != ICON_SMALL )
			throw new IllegalArgumentException( "bad whichIcon value: " + whichIcon );
	}
}

