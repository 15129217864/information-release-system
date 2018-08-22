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

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class XUtilsOSX {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private static IToolboxLock		lockObject = null;

	public static final int			kLSUnknownCreator = 0;
	public static final int			kLSUnknownType = 0;

	public static final int			kLSRolesNone = 0x00000001;
	public static final int			kLSRolesViewer = 0x00000002;
	public static final int			kLSRolesEditor = 0x00000004;
	public static final int			kLSRolesAll = 0xFFFFFFFF;

	private XUtilsOSX() {
	}

	static void setLockObject( IToolboxLock lock ) {
		lockObject = lock;
	}

	static IToolboxLock getLockObject() {
		return lockObject;
	}

	public static int getPOSIXPath( int vRef, int parID, byte pName[], String outPath[] ) {
		return wrap_getPOSIXPath( vRef, parID, pName, outPath );
	}

	public static int findApplicationForInfo( int creator, String bundleID, String appName, int[] vRefAndParID, byte[] pName ) {
		return wrap_findApplicationForInfo( creator, bundleID, appName, vRefAndParID, pName );
	}

	public static int getApplicationForInfo( int type, int creator, String extension, int roleMask, int[] vRefAndParID, byte[] pName ) {
		return wrap_getApplicationForInfo( type, creator, extension, roleMask, vRefAndParID, pName );
	}

	public static int getProcessBundleLocation( int[] processSerialNumber, int[] vRefAndParID, byte[] pName ) {
		return wrap_getProcessBundleLocation( processSerialNumber, vRefAndParID, pName );
	}

	private static int wrap_getPOSIXPath( int vRef, int parID, byte pName[], String outPath[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetPOSIXPath( vRef, parID, pName, outPath );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_findApplicationForInfo( int creator, String bundleID, String appName, int[] vRefAndParID, byte[] pName ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nFindApplicationForInfo( creator, bundleID, appName, vRefAndParID, pName );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_getProcessBundleLocation( int[] processSerialNumber, int[] vRefAndParID, byte[] pName ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetProcessBundleLocation( processSerialNumber, vRefAndParID, pName );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_getApplicationForInfo( int type, int creator, String extension, int roleMask, int[] vRefAndParID, byte[] pName ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetApplicationForInfo( type, creator, extension, roleMask, vRefAndParID, pName );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static native int nGetPOSIXPath( int vRef, int parID, byte pName[], String outPath[] );

	private static native int nGetProcessBundleLocation( int[] processSerialNumber, int[] vRefAndParID, byte[] pName );

	private static native int nFindApplicationForInfo( int creator, String bundleID, String appName, int[] vRefAndParID, byte[] pName );

	private static native int nGetApplicationForInfo( int type, int creator, String extension, int roleMask, int[] vRefAndParID, byte[] pName );
}



/**
*/

