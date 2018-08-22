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
import java.io.IOException;

/**
Used to read resources from files on Mac.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class ResFileMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
See the constructor
*/

	public static final int RESFORK_OPENEXISTING = 1;

/**
See the constructor
*/

	public static final int RESFORK_READONLY = 2;

/**
'vers'
*/

	public static final int kResTypevers = JUtils.asciiToInt( "vers" );

/**
'SIZE'
*/

	public static final int kResTypeSIZE = JUtils.asciiToInt( "SIZE" );

	private int			fileFD, vRef, parID, mode, perms;
	private byte		pName[];
	
	private static IToolboxLock		lockObject = null;

/**
Saves the values passed to this routine, but does not open the resource fork.
To open the resource fork, call the 'open' method.
@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@param mode must be RESFORK_OPENEXISTING
@param perms must be RESFORK_READONLY
*/

	ResFileMRJ( int vRef, int parID, byte[] pNm, int mode, int perms ) throws IOException {
		if ( mode != RESFORK_OPENEXISTING || perms != RESFORK_READONLY )
			throw new IllegalArgumentException( "bad mode=" + mode + " or perms=" + perms );
		
		pName = new byte[ pNm.length ];
		System.arraycopy( pNm, 0, pName, 0, pNm.length );

		fileFD = -1;
		this.vRef = vRef;
		this.parID = parID;
		this.mode = mode;
		this.perms = perms;
	}

	static void setLockObject( IToolboxLock lock ) {
		lockObject = lock;
	}

	static IToolboxLock getLockObject() {
		return lockObject;
	}

/**
Open the resource fork.
Returns -1 if an error occurs, ErrCodes.ERROR_NONE otherwise.
*/

	public int open() {
		fileFD = call_nOpenExistingResFile( vRef, parID, pName );
		
		if ( fileFD <= 0 )
			return -1;
		else
			return ErrCodes.ERROR_NONE;
	}

/**
Read a resource of the given type and id. Returns a byte array containing the data, or null if an error
occurs..
*/

	public byte[] getResource( int resName, int resID ) {
		int			retSize[], theErr;
		byte		data[];

		if ( fileFD < 1 ) {
			Trace.println( "ResFileMRJ.getResource, fileFD<1" );
			return null;
		}
	
		retSize = new int[ 1 ];

		theErr = call_nGetResourceSize( fileFD, resName, resID, retSize );
		if ( theErr != ErrCodes.ERROR_NONE || retSize[ 0 ] < 1 )
			return null;

		data = new byte[ retSize[ 0 ] ];

		theErr = call_nGetResource( fileFD, resName, resID, data );
		if ( theErr == ErrCodes.ERROR_NONE )
			return data;
		else
			return null;
	}
	
/**
Close the resource fork.
Call this after calling open().
*/

	public void close() {
		call_nCloseResFile( fileFD );
		fileFD = -1;
	}

	private static int call_nOpenExistingResFile( int vRef, int parID, byte[] pName ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			return nOpenExistingResFile( vRef, parID, pName );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static void call_nCloseResFile( int fileFD ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			nCloseResFile( fileFD );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int call_nGetResourceSize( int fileFD, int resName, int resID, int[] retSize ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			return nGetResourceSize( fileFD, resName, resID, retSize );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int call_nGetResource( int fileFD, int resName, int resID, byte[] data ) {
		try {
			if ( lockObject != null )
				lockObject.lock();
			return nGetResource( fileFD, resName, resID, data );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

/**
Given an FSSpec, open the resource fork of an existing file. Returns the file ref num.
*/

	private static native int nOpenExistingResFile( int vRef, int parID, byte[] pName );

/**
Close a previously opened file.
*/

	private static native void nCloseResFile( int fileFD );

/**
Given a file ref num and a resource type and ID, return the resource's size.
*/

	private static native int nGetResourceSize( int fileFD, int resName, int resID, int[] retSize );

/**
Given a file ref num and a resource type and ID, return the resource data. 'data' must be
>= the size of the resource..
*/

	private static native int nGetResource( int fileFD, int resName, int resID, byte[] data );

/**
Calls each of the native methods with invalid arguments. Used to test if there are link problems.
*/

	static void testLink() {
		int			err;

		try {
			err=nOpenExistingResFile( 0, 0, null );
			Trace.println( "nOpenExistingResFile error value " + err );
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
			nCloseResFile( -1 );
			Trace.println( "nCloseResFile ok" );
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
			err=nGetResourceSize( -1, 0, 0, null );
			Trace.println( "nGetResourceSize error value " + err );
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
			err=nGetResource( -1, 0, 0, null );
			Trace.println( "nGetResource error value " + err );
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

