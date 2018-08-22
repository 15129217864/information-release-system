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

class FileIteratorMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	static int iterateVolume( int vRef, DiskFilter filter, int flags, int maxToIterate ) {
		int				numRet[], dirIDArray[], theErr;
		byte			buffer[];

		numRet = new int[ 1 ];
		dirIDArray = new int[ 1 ];
		buffer = new byte[ maxToIterate * AppUtilsMRJ.kIterateEntrySize ];

		theErr = AppUtilsMRJ.iterateVolumeContents( vRef, dirIDArray, numRet, buffer, maxToIterate, flags );
		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "DVM.ivc, theErr=" + theErr );
			return theErr;
		}

		theErr = runDiskFilter( filter, flags, vRef, dirIDArray[ 0 ], numRet[ 0 ], buffer );
		if ( theErr != ErrCodes.ERROR_NONE )
			Trace.println( "DVM.rdf, theErr=" + theErr );
		
		buffer = null;
		
		return theErr;
	}

	static int iterateFolder( int vRef, int parID, byte pName[], DiskFilter filter, int flags, int maxToIterate ) {
		int				numRet[], dirIDArray[], theErr;
		byte			buffer[];

		numRet = new int[ 1 ];
		dirIDArray = new int[ 1 ];
		buffer = new byte[ maxToIterate * AppUtilsMRJ.kIterateEntrySize ];

		theErr = AppUtilsMRJ.iterateContents( vRef, parID, pName, dirIDArray, numRet, buffer, maxToIterate, flags );
		if ( theErr != ErrCodes.ERROR_NONE )
			return theErr;
		
		theErr = runDiskFilter( filter, flags, vRef, dirIDArray[ 0 ], numRet[ 0 ], buffer );
		if ( theErr != ErrCodes.ERROR_NONE )
			Trace.println( "FIM.iF, theErr=" + theErr );
		
		buffer = null;
		
		return theErr;
	}

/**
Iterates over the objects in the given directory, and presents them to a DiskFilter object.
Information on each of the objects is stored in a byte array in the format described in the 'iterateContents' method
For each object, an appropriate object will be created (DiskFileMRJ, etc.) and then presented to the DiskFilter's
visit() method.

@param filter the DiskFilter
@param flags ignored; set to 0
@param vRef the volume containing the files.
@param dirID the dirID of the folder containing the files
@param numEntries the number of files
@param buffer contains the entries for the files, 
*/

	private static int runDiskFilter( DiskFilter filter, int flags, int vRef, int dirID,
										int numEntries, byte buffer[] ) {
		DiskObject		diskObj;
		int				theErr, curPos, curI, bufferSize;
		byte			tempName[];

		if ( filter == null || buffer == null )
			throw new IllegalArgumentException( "filter or buffer is null" );	//	return ErrCodes.ERROR_PARAM;

		tempName = new byte[ AppUtilsMRJ.kIterateNameSize ];

		bufferSize = buffer.length;
		diskObj = null;

		for ( curPos = 0, curI = 0; curI < numEntries; curI++, curPos += AppUtilsMRJ.kIterateEntrySize ) {
			diskObj = null;

			if ( curPos + AppUtilsMRJ.kIterateTypeOffset >= bufferSize )
				break;
			
			System.arraycopy( buffer, curPos, tempName, 0, AppUtilsMRJ.kIterateNameSize );
			//objType = (int) ( 0xFF & buffer[ curPos + AppUtilsMRJ.kIterateTypeOffset ] );
			
			try {
				diskObj = DOFactoryMRJ.createDiskObject( vRef, dirID, tempName );
			}
			catch ( Exception e ) {
				diskObj = null;
			}
			
			if ( diskObj != null ) {
				if ( !filter.visit( diskObj ) )
					break;
			}
		}
		
		return ErrCodes.ERROR_NONE;
	}
}
