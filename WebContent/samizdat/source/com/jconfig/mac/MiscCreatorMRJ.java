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

class MiscCreatorMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Returns an array of the FileType's which an application can open.
Returns null if an error occurs.
@param vRef the volume containing the app
@param creator the creator of the app
*/

	static FileType[] getOpenableFileTypes( int vRef, int creator ) {
		int			numRet[], intFileTypes[], theErr, i;
		FileType	retArray[];

		numRet = new int[ 1 ];
		intFileTypes = new int[ 64 ];

		theErr = AppUtilsMRJ.getOpenableFileTypes( vRef, creator, numRet, intFileTypes );
		
		if ( theErr != ErrCodes.ERROR_NONE || numRet[ 0 ] == 0 )
			return null;
			
		retArray = new FileType[ numRet[ 0 ] ];
		for ( i = 0; i < numRet[ 0 ]; i++ )
			retArray[ i ] = new FileType( new FinderInfo( creator, intFileTypes[ i ] ) );
		
		return retArray;
	}
}

