/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig.win;


import com.jconfig.*;

/**
Returns the icons for a DiskVolume.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class IconBundleVolumeMSVM extends IconBundleMSVM {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private String			driveName;

/**
'dn' is the string representing the volume, e.g., 'c:\'
*/
	
	IconBundleVolumeMSVM( String dn ) {
		driveName = dn;
	}

/**
Get the bits of the icon, in Java's ARGB format, into the pData array.
*/
	
	public int getIcon( int whichIcon, int xform, int align, int pData[] ) {
		FileExtension		exts[];
		FinderInfo			fInfos[];
		int					theErr, width, height;

		checkWhichIconValue( whichIcon );

		width = getIconWidth( whichIcon );
		height = getIconHeight( whichIcon );

		theErr = AppUtilsMSVM.getVolumeIcon( driveName, whichIcon, width, height, xform, align, pData );

		return theErr;
	}
}

