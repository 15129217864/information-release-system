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
import java.util.Vector;

/**
Used to find applications given a creator code (e.g., 'MSWD').
Calls native code to do most of the work.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppFinderMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private static final int		kFindAPPLMultipleMaxNameLen = 64;

	private static IToolboxLock		lockObject = null;

/**
Currently does nothing. The arguments have the same significance as those to the FileRegistry.initialize() method.
*/

	AppFinderMRJ( File curDir, int creator ) {
	}

	static void setLockObject( IToolboxLock lock ) {
		lockObject = lock;
	}

	static IToolboxLock getLockObject() {
		return lockObject;
	}

/**
Searches for one or more applications with the given creator code.
@param maxToReturn if this is 1, calls findAPPLSingle. Otherwise, calls findAPPLMultiple.
@param creator the creator of the application to search for.
@param flags the search level, defined in FileRegistry: GETAPPS_SEARCH1, etc.
0 means no searching, any other value means do a search
Of course, searching takes longer than values cached in the desktop database.
However, searching may be an alternative to suggesting the user rebuilds their desktop.
*/

	public AppFile[] findAPPL( int creator, int maxToReturn, int flags ) {
		if ( maxToReturn < 1 )
			throw new IllegalArgumentException( "maxToReturn < 1" );
		else if ( maxToReturn == 1 )
			return findAPPLSingle( creator, flags );
		else
			return findAPPLMultiple( creator, maxToReturn, flags );
	}

/**
Find one app with the given creator.
Calls call_nFindAPPLSingle, and creates an AppFile if an app was found.
@param creator the app's creator
@param flags see findAPPL()
*/

	private AppFile[] findAPPLSingle( int creator, int flags ) {
		AppFile			retArray[], appFile;
		byte			pName[];
		int				vRefAndParID[], theErr;
		
		pName = new byte[ AppUtilsMRJ.kPNameLen ];
		vRefAndParID = new int[ AppUtilsMRJ.kRefPairLen ];
		
		theErr = call_nFindAPPLSingle( creator, vRefAndParID, pName, flags );
		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "AppFinderMRJ.findAPPLSingle: native err=" + theErr + " for " + JUtils.intToAscii( creator ) );
			return null;
		}
		
		try {
			appFile = DOFactoryMRJ.createAppFile( vRefAndParID[ AppUtilsMRJ.kVRefOffset ],
													vRefAndParID[ AppUtilsMRJ.kParIDOffset ],
													pName );
		}
		catch ( Exception e ) {
			Trace.println( "AppFinderMRJ.findAPPLSingle: can't create app file " + e );
			return null;
		}

		retArray = new AppFile[ 1 ];
		retArray[ 0 ] = appFile;

		return retArray;
	}
	
/**
Find more than one copies of an app with the given creator.
Calls call_nFindAPPLMultiple to do the work.

@param creator the creator of the app
@param maxToReturn the maximum number of apps to find
@param flags see findAPPL()
*/

	private AppFile[] findAPPLMultiple( int creator, int maxToReturn, int flags ) {
		AppFile			retArray[], appFile;
		Vector			tempVector;
		byte			pNames[];
		int				vRefs[], parIDs[], numRet[], theErr, i;
		
		pNames = new byte[ kFindAPPLMultipleMaxNameLen * maxToReturn ];
		vRefs = new int[ maxToReturn ];
		parIDs = new int[ maxToReturn ];
		numRet = new int[ 1 ];

		theErr = call_nFindAPPLMultiple( creator, vRefs, parIDs, pNames, maxToReturn, flags, numRet );
		if ( theErr != ErrCodes.ERROR_NONE || numRet[ 0 ] == 0 ) {
			Trace.println( "AppFinderMRJ.findAPPLMultiple: native err=" + theErr + " for " + JUtils.intToAscii( creator ) );
			return null;
		}

		return DOFactoryMRJ.createAppFileArray( vRefs, parIDs, pNames, numRet[ 0 ], kFindAPPLMultipleMaxNameLen );
	}

	private static int call_nFindAPPLSingle( int creator, int vRefAndParID[], byte pName[], int flags ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			return nFindAPPLSingle( creator, vRefAndParID, pName, flags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int call_nFindAPPLMultiple( int creator, int vRefs[], int parIDs[], byte pNames[], int maxToReturn, int flags, int numReturned[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			return nFindAPPLMultiple( creator, vRefs, parIDs, pNames, maxToReturn, flags, numReturned );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

/**
Find one matching app.
@param creator the app's creator
@param vRefAndParID the app's vRef will be placed at vRefAndParID[ 0 ],
and the parID will be placed at vRefAndParID[ 1 ]
@param pName the app's name will be placed in this array as a Pascal string.
Must have at least AppUtilsMRJ.kPNameLen elements.
@param flags currently this value is ignored
*/

	private static native int nFindAPPLSingle( int creator, int vRefAndParID[], byte pName[], int flags );

/**
Find multiple matching app.
@param creator the app's creator
@param vRefs each app's vRef will be placed at successive locations in this array
@param parIDs each app's parID will be placed at successive locations in this array
@param pName each app's name will be placed at successive locations in this array as a Pascal string.
Each name will consume kFindAPPLMultipleMaxNameLen elements, and this array must have at least
(kFindAPPLMultipleMaxNameLen * maxToReturn) elements
@param maxToReturn the maximum number of apps to return
@param flags currently, if this is 0 only standard searching is performed.
if this is not zero, a more extensive search is performed.
@param numReturned the number of apps returned will be placed at numReturned[ 0 ]
*/

	private static native int nFindAPPLMultiple( int creator, int vRefs[], int parIDs[], byte pNames[],
														int maxToReturn, int flags, int numReturned[] );

/**
Calls each of the native methods with invalid arguments. Used to test if there are link problems.
*/

	static void testLink() {
		int			err;

		try {
			err=nFindAPPLSingle( 0, null, null, 0 );
			Trace.println( "nFindAPPLSingle error value " + err );
		}
		catch ( Exception e ) {
			Trace.println( "problems: " + e );
			e.printStackTrace( Trace.getOut() );
		}
		catch ( Error er ) {
			Trace.println( "problems: " + er );
			er.printStackTrace( Trace.getOut() );
		}

		try {
			err=nFindAPPLMultiple( 0, null, null, null, 0, 0, null );
			Trace.println( "nFindAPPLMultiple error value " + err );
		}
		catch ( Exception e ) {
			Trace.println( "problems: " + e );
			e.printStackTrace( Trace.getOut() );
		}
		catch ( Error er ) {
			Trace.println( "problems: " + er );
			er.printStackTrace( Trace.getOut() );
		}
	}
}

