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
@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DateUtilsMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Returns a DateBundle containing the dates of the given file. Returns null if an error occurs

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
*/

	static DateBundle getFileDateBundle( int vRef, int parID, byte pName[] ) {
		int			theErr, datesArray[], i;

		datesArray = new int[ AppUtilsMRJ.kDateBundleArrayLen ];
		for ( i = 0; i < AppUtilsMRJ.kDateBundleArrayLen; i++ )
			datesArray[ i ] = 0;

		theErr = AppUtilsMRJ.getFileDate( vRef, parID, pName, datesArray );
		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "getfdb=" + theErr + " for " + JUtils.pascalBytesToString( pName, 0 ) );
			throw new OSException( "can't getFileDateBundle=" + theErr );
		}
		
		return DateBundle.createFromArray( datesArray );
	}

/**
Sets the DateBundle of a file. Currently unimplemented.

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@exception UnimplementedException this method always throws an UnimplementedException
*/

	static void setFileDateBundle( int vRef, int parID, byte pName[], DateBundle newDates ) {
		int			theErr, datesArray[];

		datesArray = newDates.toArray();

		theErr = AppUtilsMRJ.setFileDate( vRef, parID, pName, datesArray );

		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "setfdb=" + theErr + " for " + JUtils.pascalBytesToString( pName, 0 ) );
			throw new OSException( "can't setFileDateBundle=" + theErr );
		}
	}

/**
Sets the DateBundle of the given volume. Not implemented.

@param vRef the vRefNum of the volume
@exception UnimplementedException this method always throws an UnimplementedException
*/

	static void setVolumeDateBundle( int vRef, DateBundle newDates ) {
		int			theErr, datesArray[];

		datesArray = newDates.toArray();

		theErr = AppUtilsMRJ.setVolumeDate( vRef, datesArray );

		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "setvdb=" + theErr + " for " + vRef );
			throw new OSException( "can't setVolumeDateBundle=" + theErr );
		}
	}

/**
Returns a DateBundle containing the dates of the given volume. Returns null if an error occurs

@param vRef the vRefNum of the volume
*/

	static DateBundle getVolumeDateBundle( int vRef ) {
		int			theErr, datesArray[], i;

		datesArray = new int[ AppUtilsMRJ.kDateBundleArrayLen ];
		for ( i = 0; i < AppUtilsMRJ.kDateBundleArrayLen; i++ )
			datesArray[ i ] = 0;

		theErr = AppUtilsMRJ.getVolumeDate( vRef, datesArray );
		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "getvdb=" + theErr + " for " + vRef );
			throw new OSException( "can't getVolumeDateBundle=" + theErr );
		}
		
		return DateBundle.createFromArray( datesArray );
	}
}


