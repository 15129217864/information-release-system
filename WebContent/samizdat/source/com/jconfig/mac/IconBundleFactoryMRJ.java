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

import java.io.File;
import java.io.PrintStream;
import java.util.Vector;
import java.io.FileNotFoundException;

/**
Used to obtain the icons for files, volumes, and file types. For example, given the vRefNum of a volume,
calls native code with the vRefNum to obtain a Mac icon suite for the volume, and then creates
an IconBundleMRJ object to hold the icon suite.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class IconBundleFactoryMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private static final int			kDefSelector = 0;

	private IconBundleFactoryMRJ() {
	}

/**
Returns an IconBundle for a file type and creator.
@param vRef the vRefNum of the volume that we search for the file type and creator
@param creator the creator code
@param type the type code
*/
	
	static IconBundle createFromFTAC( int vRef, int creator, int type ) {
		int			theErr, pHSuite[];
		
		pHSuite = new int[ 1 ];

		theErr = AppUtilsMRJ.getFTACIconSuite( vRef, creator, type, kDefSelector, pHSuite );
		if ( theErr != ErrCodes.ERROR_NONE )
			return null;
		
		return new IconBundleMRJ( pHSuite[ 0 ] );
	}

/**
Returns an IconBundle for a volume
@param vRef the vRefNum of the volume
*/
	
	static IconBundle createFromVolume( int vRef ) {
		int			theErr, pHSuite[];
		
		pHSuite = new int[ 1 ];

		theErr = AppUtilsMRJ.getVolumeIconSuite( vRef, kDefSelector, pHSuite );
		if ( theErr != ErrCodes.ERROR_NONE )
			return null;
		
		return new IconBundleMRJ( pHSuite[ 0 ] );
	}

/**
Returns an IconBundle for a file or folder.
@param vRef the vRefNum of the file or folder
@param parID the parID of the file or folder
@param pName the name of the file or folder, as a Pascal string
*/
	
	static IconBundle createFromFile( int vRef, int parID, byte pName[] ) {
		int			theErr, pHSuite[];
		
		pHSuite = new int[ 1 ];

		theErr = AppUtilsMRJ.getFileIconSuite( vRef, parID, pName, kDefSelector, pHSuite );
		if ( theErr != ErrCodes.ERROR_NONE )
			return null;
		
		return new IconBundleMRJ( pHSuite[ 0 ] );
	}
}


