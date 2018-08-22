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
import java.util.Date;
import java.util.Vector;
import java.io.File;

/**
@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DOCreatorMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Given arrays containing information on application's files, returns an array of AppFile's created
using that information.
Returns null if none of the AppFile's could be created.

@param vRefs the vRefNums of the files
@param parIDs the parIDs of the files
@param pNames the names of the files. Each name is a Pascal string which occupies 'num' elements
@param num the number of AppFiles to create. The actual number returned may be less than this if
an error occurs for some of the applications.
@param nameLen the length of each name in the 'pNames' array
*/

	static AppFile[] createAppFileArray( int vRefs[], int parIDs[], byte pNames[], int num, int nameLen ) {
		AppFile			retArray[], appFile;
		Vector			tempVector;
		int				i;
		byte			tempName[];

		if ( num <= 0 )
			return null;

		tempVector = new Vector( num, 1 );
		tempName = new byte[ nameLen ];

		for ( i = 0; i < num; i++ ) {
			System.arraycopy( pNames, nameLen * i, tempName, 0, nameLen );

			try {
				appFile = new AppFileMRJ( vRefs[ i ], parIDs[ i ], tempName );
				tempVector.addElement( appFile );
			}
			catch ( Exception e ) {
			}
		}

		if ( tempVector.size() < 1 )
			return null;

		retArray = new AppFile[ tempVector.size() ];

		tempVector.copyInto( retArray );

		return retArray;
	}
}

