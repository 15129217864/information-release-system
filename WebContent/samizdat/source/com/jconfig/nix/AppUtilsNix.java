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
import java.io.File;

/**
This singleton delegates all calls to either a AppUtilsNixLinux or AppUtilsNixPlain object, both of which
implement the AppUtilsNixI interface.

<P>
These methods are the lowest level, and are called by the other classes in this package.
See the AppUtilsNixI interface for details on each of these methods.

<P>
The 'initialize' method must be called before using any other methods of this class.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppUtilsNix {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	static final int					kUnused = AppUtilsNixI.kUnused;

/**
The stat() and lstat() methods are passed
an array of java ints, into which the native code puts the modification,
creation and access dates. Each date takes 6 ints: year, month, day,
hour, minute, and second. These define the total length of the jint array,
and the offset of each date.
*/

	static final int		kDateBundleArrayLen = AppUtilsNixI.kDateBundleArrayLen;
	static final int		DATE_MOD_OFFSET = AppUtilsNixI.DATE_MOD_OFFSET;
	static final int		DATE_CRE_OFFSET = AppUtilsNixI.DATE_CRE_OFFSET;
	static final int		DATE_ACC_OFFSET = AppUtilsNixI.DATE_ACC_OFFSET;

/**
The statFS() method is passed
an array of ints, into which the native code information on a file system.
These constants define the total length of the array, and the offset of each item.
*/

	static final int					kStatFSOffs_type = AppUtilsNixI.kStatFSOffs_type;				//	nStatFS
	static final int					kStatFSOffs_bsize = AppUtilsNixI.kStatFSOffs_bsize;
	static final int					kStatFSOffs_blocks = AppUtilsNixI.kStatFSOffs_blocks;
	static final int					kStatFSOffs_bfree = AppUtilsNixI.kStatFSOffs_bfree;
	static final int					kStatFSOffs_bavail = AppUtilsNixI.kStatFSOffs_bavail;
	static final int					kStatFSOffs_files = AppUtilsNixI.kStatFSOffs_files;
	static final int					kStatFSOffs_ffree = AppUtilsNixI.kStatFSOffs_ffree;
	static final int					kStatFSOffs_fsid0 = AppUtilsNixI.kStatFSOffs_fsid0;
	static final int					kStatFSOffs_fsid1 = AppUtilsNixI.kStatFSOffs_fsid1;
	static final int					kStatFSRetArrayLen = AppUtilsNixI.kStatFSRetArrayLen;

/**
The getMntEnt() method is passed
an array of Strings, into which the native code information on a file system.
These constants define the total length of the array, and the offset of each item.
*/

	static final int		kGetMntEntOffs_fsname = AppUtilsNixI.kGetMntEntOffs_fsname;
	static final int		kGetMntEntOffs_dir = AppUtilsNixI.kGetMntEntOffs_dir;
	static final int		kGetMntEntOffs_type = AppUtilsNixI.kGetMntEntOffs_type;
	static final int		kGetMntEntOffs_opts = AppUtilsNixI.kGetMntEntOffs_opts;
	static final int		kGetMntEntRetArrayLen = AppUtilsNixI.kGetMntEntRetArrayLen;

/**
The stat() method is passed
an array of ints, into which the native code information on a file.
These constants define the total length of the array, and the offset of each item.
*/

	static final int		kStatOffs_dev = AppUtilsNixI.kStatOffs_dev;
	static final int		kStatOffs_ino = AppUtilsNixI.kStatOffs_ino;
	static final int		kStatOffs_mode = AppUtilsNixI.kStatOffs_mode;
	static final int		kStatOffs_nlink = AppUtilsNixI.kStatOffs_nlink;
	static final int		kStatOffs_uid = AppUtilsNixI.kStatOffs_uid;
	static final int		kStatOffs_gid = AppUtilsNixI.kStatOffs_gid;
	static final int		kStatOffs_rdev = AppUtilsNixI.kStatOffs_rdev;
	static final int		kStatOffs_size = AppUtilsNixI.kStatOffs_size;
	static final int		kStatOffs_blksize = AppUtilsNixI.kStatOffs_blksize;
	static final int		kStatOffs_blocks = AppUtilsNixI.kStatOffs_blocks;
	static final int		kStatRetArrayLen = AppUtilsNixI.kStatRetArrayLen;

	static final int		kMonitorInfoNumInts = AppUtilsNixI.kMonitorInfoNumInts;
	static final int		kResolveLinkFileNoUI = AppUtilsNixI.kResolveLinkFileNoUI;
	static final int		kResolveLinkFileUI = AppUtilsNixI.kResolveLinkFileUI;

	private static AppUtilsNixI			delegate = null;

	private AppUtilsNix() {
	}

/**
This method must be called before using any other methods of this class.
It creates the object to which all other calls will be delegated.
If an AppUtilsNixLinux object can't be created (i.e., because we aren't running on Linux),
a AppUtilsNixPlain object is created.

<P>
For all other methods, if delegate is null, the method returns null or an error code.
Otherwise, it calls the method with the same name in the delegate.
*/

	static void initialize( boolean bUseLinux, File temp ) {
		delegate = null;

		if ( bUseLinux ) {
			try {
				delegate = new AppUtilsNixLinux( temp );
			}
			catch ( Exception e ) {
			}
			catch ( Error er ) {
			}
		}

		if ( delegate == null )
			delegate = new AppUtilsNixPlain( temp );
	}

	static final void checkDelegate() {
		if ( delegate == null )
			throw new UninitializedException( "delegate not initialized" );
	}

	static int statFS( String fileName, int retInts[] ) {
		checkDelegate();
		return delegate.statFS( fileName, retInts );
	}

	static int getFileIcon( String fullPath, boolean bIsDir, int whichIcon, int w, int h, int xform, int align, int pData[] ) {
		checkDelegate();
		return delegate.getFileIcon( fullPath, bIsDir, whichIcon, w, h, xform, align, pData );
	}

	static int getExtIcon( String ext, int whichIcon, int w, int h, int xform, int align, int pData[] ) {
		checkDelegate();
		return delegate.getExtIcon( ext, whichIcon, w, h, xform, align, pData );
	}

	static int getVolumeIcon( String driveName, int whichIcon, int w, int h, int xform, int align, int pData[] ) {
		checkDelegate();
		return delegate.getVolumeIcon( driveName, whichIcon, w, h, xform, align, pData ) ;
	}

	static int createVolumeAlias( String driveName, String newAliasPath, int flags ) {
		checkDelegate();
		return delegate.createVolumeAlias( driveName, newAliasPath, flags );
	}

	static int createFileAlias( String targetPath, String newAliasPath, int flags ) {
		checkDelegate();
		return delegate.createFileAlias( targetPath, newAliasPath, flags );
	}

	static boolean isLinkFile( File fl ) {
		checkDelegate();
		return delegate.isLinkFile( fl );
	}

	static boolean isDrivePath( File fl ) {
		checkDelegate();
		return delegate.isDrivePath( fl );
	}

	static String pathToDriveName( File fl ) {
		checkDelegate();
		return delegate.pathToDriveName( fl );
	}

	private static boolean isDriveString( String drivePath ) {
		checkDelegate();
		return delegate.isDriveString( drivePath );
	}

	static int getVolumes( int maxToReturn, int numReturned[], String driveNames[] ) {
		checkDelegate();
		return delegate.getVolumes( maxToReturn, numReturned, driveNames );
	}

	static int getVolumeLabel( String driveName, String label[] ) {
		checkDelegate();
		return delegate.getVolumeLabel( driveName, label );
	}

	static int getVolumeMaxFileNameLength( String driveName, int nameLen[] ) {
		checkDelegate();
		return delegate.getVolumeMaxFileNameLength( driveName, nameLen );
	}

	static int getVolumeReferenceNumber( String driveName, int refNum[] ) {
		checkDelegate();
		return delegate.getVolumeReferenceNumber( driveName, refNum );
	}

	static int setVolumeLabel( String driveName, String newLabel ) {
		checkDelegate();
		return delegate.setVolumeLabel( driveName, newLabel );
	}

	static int getDriveDisplayName( String driveName, String displayName[] ) {
		checkDelegate();
		return delegate.getDriveDisplayName( driveName, displayName );
	}

	static int getVolumeFlags( String driveName, int flags[] ) {
		checkDelegate();
		return delegate.getVolumeFlags( driveName, flags );
	}

	static int getVolumeReadFlagsMask( String driveName, int flags[] ) {
		checkDelegate();
		return delegate.getVolumeReadFlagsMask( driveName, flags );
	}

	static int resolveLinkFile( String linkFilePath, String retPath[], int flags ) {
		checkDelegate();
		return delegate.resolveLinkFile( linkFilePath, retPath, flags );
	}

	static int getAllMonitorInfo( int monitorInfo[], int maxToReturn, int numReturned[] ) {
		checkDelegate();
		return delegate.getAllMonitorInfo( monitorInfo, maxToReturn, numReturned );
	}

	static int getMainMonitorInfo( int monitorInfo[] ) {
		checkDelegate();
		return delegate.getMainMonitorInfo( monitorInfo );
	}

	static int getExecutableType( String fullPath, int val[] ) {
		checkDelegate();
		return delegate.getExecutableType( fullPath, val );
	}

	static int getFileAttributes( String fullPath, int val[]  ) {
		checkDelegate();
		return delegate.getFileAttributes( fullPath, val  );
	}

	static int getFileAttributesMask( String fullPath, int val[]  ) {
		checkDelegate();
		return delegate.getFileAttributesMask( fullPath, val );
	}

	static int launchApp( String appPath, String verb, String regKey, String commandLine, int retData[], int numArgs, String args[] ) {
		checkDelegate();
		return delegate.launchApp( appPath, verb, regKey, commandLine, retData, numArgs, args );
	}

	static int launchURL( String url, int flags, String preferredBrowsers[] ) {
		checkDelegate();
		return delegate.launchURL( url, flags, preferredBrowsers );
	}

	static String[] findAppsByName( String appName, int maxToReturn, int flags ) {
		if ( delegate == null ) return null;
		return delegate.findAppsByName( appName, maxToReturn, flags );
	}	

	static String[] findAppsByExtension( String ext, int maxToReturn, int flags ) {
		if ( delegate == null ) return null;
		return delegate.findAppsByExtension( ext, maxToReturn, flags );
	}	

	static int verifyNativeAppData( int appData[] ) {
		checkDelegate();
		return delegate.verifyNativeAppData( appData );
	}

	static int quitApp( int appData[], int flags ) {
		checkDelegate();
		return delegate.quitApp( appData, flags );
	}

	static int moveApp( int appData[], int selector, int flags ) {
		checkDelegate();
		return delegate.moveApp( appData, selector, flags );
	}

	static int stat( String fileName, int retArray[], int datesArray[] ) {
		checkDelegate();
		return delegate.stat( fileName, retArray, datesArray );
	}

	static int lstat( String fileName, int retArray[], int datesArray[] ) {
		checkDelegate();
		return delegate.lstat( fileName, retArray, datesArray );
	}

	static int getMntEnt( String retQuads[], int maxToReturn, int numReturned[] ) {
		checkDelegate();
		return delegate.getMntEnt( retQuads, maxToReturn, numReturned );
	}

	static int readLink( String linkFilePath, String retPath[] ) {
		checkDelegate();
		return delegate.readLink( linkFilePath, retPath );
	}
}


