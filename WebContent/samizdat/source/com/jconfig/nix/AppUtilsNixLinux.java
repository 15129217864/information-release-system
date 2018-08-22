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
import java.util.Date;
import java.io.File;

/**
This is an implementation of AppUtilsNixI for Linux.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppUtilsNixLinux extends AppUtilsNixPlain {
	private final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Used in getMntEnt()
*/

	private static final String		PATH_MOUNTED = "/etc/mtab";
	private static final String		PATH_MNTTAB = "/etc/fstab";

/**
Indicates to the nStat method to use the stat() routine.
*/

	private static final int		kUseStat = 0;

/**
Indicates to the nStat method to use the lstat() routine.
*/

	private static final int		kUseLStat = 1;

/**
Loads "jconfiglx0"
*/

	AppUtilsNixLinux( File temp ) {
		super( temp );
		System.loadLibrary( "jconfiglx0" );
	}

	public int statFS( String fileName, int retInts[] ) {
		return nStatFS( fileName, retInts );
	}

	public int stat( String fileName, int retArray[], int datesArray[] ) {
		return nStat( kUseStat, fileName, retArray, datesArray );
	}

	public int lstat( String fileName, int retArray[], int datesArray[] ) {
		return nStat( kUseLStat, fileName, retArray, datesArray );
	}

	public int getMntEnt( String retQuads[], int maxToReturn, int numReturned[] ) {
		return nGetMntEnt( PATH_MOUNTED, retQuads, maxToReturn, numReturned );
	}

	public int readLink( String linkFilePath, String retPath[] ) {
		return nReadLink( linkFilePath, retPath );
	}

/**
Resolves a symbolic link.
@param linkFilePath the full path of the symlink
@param retPath the resolved link file will be placed at retPath[ 0 ]
@param flags
*/

	public int resolveLinkFile( String linkFilePath, String retPath[], int flags ) {
		return readLink( linkFilePath, retPath );
	}

	private static native int nReadLink( String linkName, String resolvedName[] );
	private static native int nStat( int selector, String fileName, int retArray[], int datesArray[] );
	private static native int nStatFS( String fileName, int retInts[] );
	private static native int nGetMntEnt( String mntFileName, String retQuads[], int maxToReturn, int numReturned[] );
}


