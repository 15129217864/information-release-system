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
Gets the icons for a file or folder.

This is a preliminary class derived from the Windows class of similar name. This class may be replaced
or modified in a future version.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class IconBundleFileNix extends IconBundleNix {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private String			fileName;
	private boolean			bIsDir;
	
	IconBundleFileNix( String fn, boolean bD ) {
		fileName = fn;
		bIsDir = bD;
	}
	
	public int getIcon( int whichIcon, int xform, int align, int pData[] ) {
		int					theErr, width, height;

		checkWhichIconValue( whichIcon );

		width = getIconWidth( whichIcon );
		height = getIconHeight( whichIcon );

		theErr = AppUtilsNix.getFileIcon( fileName, bIsDir, whichIcon, width, height, xform, align, pData );

		return theErr;
	}
}

