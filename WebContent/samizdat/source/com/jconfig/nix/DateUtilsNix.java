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
A singleton used to create a DateBundle from a file/folder/drive.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DateUtilsNix {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Returns a DateBundle containing the dates of the given file. Returns null if an error occurs
@param path the full path name
*/

	static DateBundle getFileDateBundle( String filePath ) {
		int				i, theErr, retArray[], datesArray[];

		retArray = new int[ AppUtilsNix.kStatRetArrayLen ];
		datesArray = new int[ AppUtilsNix.kDateBundleArrayLen ];
		for ( i = 0; i < AppUtilsNix.kDateBundleArrayLen; i++ )
			datesArray[ i ] = 0;

		theErr = AppUtilsNix.stat( filePath, retArray, datesArray );
		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "getfdb=" + theErr + " for " + filePath );
			throw new OSException( "can't getFileDateBundle=" + theErr );
		}

		return DateBundle.createFromArray( datesArray );
	}

/**
Sets the DateBundle of a file.
@exception UnimplementedException this method always throws an UnimplementedException
*/

	static void setFileDateBundle( String path, DateBundle newDates ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
Returns a DateBundle containing the dates of the given volume. Returns null if an error occurs
@param driveName the drive name
*/

	static DateBundle getVolumeDateBundle( String driveName ) {
		return getFileDateBundle( driveName );
	}

/**
Sets the DateBundle for a volume.
@exception UnimplementedException this method always throws an UnimplementedException
*/

	static void setVolumeDateBundle( String driveName, DateBundle newDates ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}
}


