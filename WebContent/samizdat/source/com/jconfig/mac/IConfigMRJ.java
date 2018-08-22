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
Wraps several Internet Config routines.

<P>
See the IC documentation for more information on these routines.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class IConfigMRJ implements ConfigList {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private int				ourCreator;

	static final int		kMaxExtensionLen = 32;
	static final int		kFindAppMaxNameLen = 64;

	private static IToolboxLock		lockObject = null;

/**
Tries to connect with Internet Config. If IC is not installed, or if an error occurs, throws a ConfigException exception.
@param creator the creator code of the app calling this method
*/

	IConfigMRJ( int creator ) throws ConfigException {
		int			ourHandle;
		boolean		bValid = false;

		ourCreator = creator;

		bValid = false;
		
		try {
			ourHandle = call_nICStart( ourCreator );
			if ( ourHandle != 0 ) {
				call_nICStop( ourHandle );
				bValid = true;
			}
		}
		catch ( Exception e ) {
			Trace.println( "IConfigMRJ: can't initialize: " + e );
			bValid = false;
		}
		
		if ( !bValid )
			throw new ConfigException( "can't initialize" );
	}

	static void setLockObject( IToolboxLock lock ) {
		lockObject = lock;
	}

	static IToolboxLock getLockObject() {
		return lockObject;
	}

/**
Iterate over the entries of the IC file mapping database.
*/

	public int iterate( ConfigEntryVisitor fdv ) {
		int			ourHandle, entrySize, numEntries, theErr, i;
		String		str;
		byte[]		record;

		theErr = -1;

		ourHandle = call_nICStart( ourCreator );
		if ( ourHandle == 0 )
			return -1;

		entrySize = call_nICGetMapEntrySize();
		numEntries = call_nICCountMapEntries( ourHandle );

		record = new byte[ entrySize + 1000 ];

		for ( i = 1; i <= numEntries; i++ ) {
			theErr = call_nICGetIndMapEntry( ourHandle, i, record );
			if ( theErr != ErrCodes.ERROR_NONE )
				break;

			fdv.visit( new IConfigEntryMRJ( record ) );
		}

		call_nICStop( ourHandle );

		return theErr;
	}

/**
Returns an array of the FileExtension's corresponding to the given FinderInfo.
For instance, ".txt" corresponds to 'TEXT'
*/

	public FileExtension[] findMatches( FinderInfo fInfo, int maxToReturn, int direction ) {
		int				numReturned[], theErr, i;
		FileExtension	retVal[];
		String			theString;
		byte			extensions[];
		
		numReturned = new int[ 1 ];
		extensions = new byte[ kMaxExtensionLen * maxToReturn ];

		theErr = call_nFindMatchesFInfo( ourCreator, fInfo.getCreator(), fInfo.getFileType(), direction,
											numReturned, maxToReturn, extensions );
		if ( theErr != ErrCodes.ERROR_NONE || numReturned[ 0 ] <= 0 ) {
			Trace.println( "IConfigMRJ.findMatches, theErr=" + theErr + ", numRet=" + numReturned[ 0 ] );
			return null;
		}
		
		retVal = new FileExtension[ numReturned[ 0 ] ];
		for ( i = 0; i < numReturned[ 0 ]; i++ ) {
			theString = JUtils.pascalBytesToString( extensions, kMaxExtensionLen * i );
			retVal[ i ] = new FileExtension( theString );
		}
		
		return retVal;
	}

/**
Returns an array of the FinderInfo's corresponding to the given FileExtension.
For instance, 'TEXT' corresponds to ".txt" 
*/

	public FinderInfo[] findMatches( FileExtension ext, int maxToReturn, int direction ) {
		int			numReturned[], cVals[], tVals[], theErr, i;
		FinderInfo	retVal[];
		
		numReturned = new int[ 1 ];
		cVals = new int[ maxToReturn ];
		tVals = new int[ maxToReturn ];
		
		theErr = call_nFindMatchesExt( ourCreator, ext.getString(), direction, numReturned, maxToReturn, cVals, tVals );
		if ( theErr != ErrCodes.ERROR_NONE || numReturned[ 0 ] <= 0 ) {
			Trace.println( "IConfigMRJ.findMatches, theErr=" + theErr + ", numRet=" + numReturned[ 0 ] );
			return null;
		}
		
		retVal = new FinderInfo[ numReturned[ 0 ] ];
		for ( i = 0; i < numReturned[ 0 ]; i++ )
			retVal[ i ] = new FinderInfo( cVals[ i ], tVals[ i ] );
		
		return retVal;
	}

/**
Returns an array of AppFile's whose file names contain the given name.
These file names will be retrieved from the IC mapping table, which contains a list of apps.
That does not mean that the app is necessarily on the user's system, however.
See the IC documentation for more details.
*/

	public AppFile[] findAppByName( String appName, int maxToReturn, int flags ) {
		AppFile			retArray[], appFile;
		byte			pNames[], tempName[];
		int				vRefs[], parIDs[], creators[], numRet[], theErr, i;
		
		pNames = new byte[ kFindAppMaxNameLen * maxToReturn ];
		creators = new int[ maxToReturn ];
		vRefs = new int[ maxToReturn ];
		parIDs = new int[ maxToReturn ];
		numRet = new int[ 1 ];

		theErr = call_nFindAppByName( ourCreator, appName, creators, vRefs, parIDs, pNames, maxToReturn, numRet );
		if ( theErr != ErrCodes.ERROR_NONE || numRet[ 0 ] <= 0 ) {
			Trace.println( "err from call_nFindAppByName is " + theErr );
			return null;
		}


		return DOFactoryMRJ.createAppFileArray( vRefs, parIDs, pNames, numRet[ 0 ], kFindAppMaxNameLen );
	}

/**
Use Internet Config to launch the given URL.
*/
	
	public int launchURL( String url, int flags, String preferredBrowsers[] ) {
		return call_nLaunchURL( ourCreator, url, flags, preferredBrowsers );
	}

	private static int call_nICStart( int creator ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			return nICStart( creator );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static void call_nICStop( int ourHandle ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			nICStop( ourHandle );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int call_nICGetMapEntrySize() {
		try {
			if ( lockObject != null )
				lockObject.lock();
			return nICGetMapEntrySize();
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int call_nICGetSeed( int ourHandle ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			return nICGetSeed( ourHandle );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int call_nICCountMapEntries( int ourHandle ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			return nICCountMapEntries( ourHandle );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int call_nICGetIndMapEntry( int ourHandle, int whichRecord, byte[] record ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			return nICGetIndMapEntry( ourHandle, whichRecord, record );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int call_nFindMatchesExt( int appCreator, String extension, int direction, int numReturned[], int maxToReturn, int cVals[], int tVals[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			return nFindMatchesExt( appCreator, extension, direction, numReturned, maxToReturn, cVals, tVals );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int call_nFindMatchesFInfo( int appCreator, int targetCreator, int targetType, int direction, int numReturned[], int maxToReturn, byte extensions[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			return nFindMatchesFInfo( appCreator, targetCreator, targetType, direction, numReturned, maxToReturn, extensions );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int call_nFindAppByName( int appCreator, String matchName, int creators[], int vRefs[], int parIDs[], byte pNames[], int maxToReturn, int numRet[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			return nFindAppByName( appCreator, matchName, creators, vRefs, parIDs, pNames, maxToReturn, numRet );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int call_nLaunchURL( int appCreator, String url, int flags, String preferredBrowsers[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			return nLaunchURL( appCreator, url, flags, preferredBrowsers );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

/**
Wraps the IC routine of similar name.
*/

	private static native int nICStart( int creator );

/**
Wraps the IC routine of similar name.
*/

	private static native void nICStop( int ourHandle );

/**
Wraps the IC routine of similar name.
*/

	private static native int nICGetMapEntrySize();

/**
Wraps the IC routine of similar name.
*/

	private static native int nICGetSeed( int ourHandle );

/**
Wraps the IC routine of similar name.
*/

	private static native int nICCountMapEntries( int ourHandle );

/**
Wraps the IC routine of similar name.
*/

	private static native int nICGetIndMapEntry( int ourHandle, int whichRecord, byte[] record );

/**
Wraps the IC routine of similar name.
*/

	private static native int nFindMatchesExt( int appCreator, String extension, int direction, int numReturned[], int maxToReturn, int cVals[], int tVals[] );

/**
Wraps the IC routine of similar name.
*/

	private static native int nFindMatchesFInfo( int appCreator, int targetCreator, int targetType, int direction, int numReturned[], int maxToReturn, byte extensions[] );

/**
Wraps the IC routine of similar name.
*/

	private static native int nFindAppByName( int appCreator, String matchName, int creators[], int vRefs[], int parIDs[], byte pNames[], int maxToReturn, int numRet[] );

/**
Wraps the IC routine of similar name.
*/

	private static native int nLaunchURL( int appCreator, String url, int flags, String preferredBrowsers[] );


/**
Calls each of the native methods with invalid arguments. Used to test if there are link problems.
*/

	static void testLink() {

		int			err;



		try {

			nICStop( 0 );

			Trace.println( "nICStop ok" );

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

			err=nICGetMapEntrySize();

			Trace.println( "nICGetMapEntrySize error value " + err );

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

			err=nICGetSeed( 0 );

			Trace.println( "nICGetSeed error value " + err );

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

			err=nICCountMapEntries( 0 );

			Trace.println( "nICCountMapEntries error value " + err );

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

			err=nICGetIndMapEntry( 0, 0, null );

			Trace.println( "nICGetIndMapEntry error value " + err );

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

			err=nFindMatchesExt( 0, null, 0, null, 0, null, null );

			Trace.println( "nFindMatchesExt error value " + err );

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

			err=nFindMatchesFInfo( 0, 0, 0, 0, null, 0, null );

			Trace.println( "nFindMatchesFInfo error value " + err );

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

			err=nFindAppByName( 0, null, null, null, null, null, 0, null );

			Trace.println( "nFindAppByName error value " + err );

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

			err=nLaunchURL( 0, null, 0, null );

			Trace.println( "nLaunchURL error value " + err );

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


