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

import com.jconfig.ErrCodes;
import com.jconfig.OSException;
import com.jconfig.DateBundle;
import com.jconfig.Trace;
import com.jconfig.UnimplementedException;

/**
A singleton used to create a DateBundle from a file/folder/drive.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DateUtilsMSVM {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Returns a DateBundle containing the dates of the given file. Returns null if an error occurs
@param path the full path name
*/

	static DateBundle getFileDateBundle( String path ) {
		int			theErr, datesArray[], i;

		datesArray = new int[ AppUtilsMSVM.kDateBundleArrayLen ];
		for ( i = 0; i < AppUtilsMSVM.kDateBundleArrayLen; i++ )
			datesArray[ i ] = 0;

		theErr = AppUtilsMSVM.getFileDate( path, datesArray );
		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "getfdb=" + theErr + " for " + path );
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
		int			theErr, datesArray[], i;

		datesArray = new int[ AppUtilsMSVM.kDateBundleArrayLen ];
		for ( i = 0; i < AppUtilsMSVM.kDateBundleArrayLen; i++ )
			datesArray[ i ] = 0;

		theErr = AppUtilsMSVM.getVolumeDate( driveName, datesArray );
		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "getvdb=" + theErr + " for " + driveName );
			throw new OSException( "can't getVolumeDateBundle=" + theErr );
		}
		
		return DateBundle.createFromArray( datesArray );
	}

/**
Sets the DateBundle for a volume.
@exception UnimplementedException this method always throws an UnimplementedException
*/

	static void setVolumeDateBundle( String driveName, DateBundle newDates ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

	private DateUtilsMSVM() {}
}


