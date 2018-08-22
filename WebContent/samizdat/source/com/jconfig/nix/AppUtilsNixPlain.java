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
import java.io.PrintStream;
import java.io.FileOutputStream;

/**
This is an implementation of AppUtilsNixI that doesn't use native code.
If it's not possible to implement a method without using native code, that method returns null
or an error code.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppUtilsNixPlain implements AppUtilsNixI {
	private final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private String				tempDir = null;
	private DefaultIconManager	defaultIconManager;

	AppUtilsNixPlain( File temp ) {
		tempDir = temp.getPath();
		defaultIconManager = new DefaultIconManager( temp );
	}

	public int statFS( String fileName, int retInts[] ) {
		return -1;
	}

	public int stat( String fileName, int retArray[], int datesArray[] ) {
		return -1;
	}

	public int lstat( String fileName, int retArray[], int datesArray[] ) {
		return -1;
	}

	public int getMntEnt( String retQuads[], int maxToReturn, int numReturned[] ) {
		return -1;
	}

	public int readLink( String linkFilePath, String retPath[] ) {
		return -1;
	}

	public int getFileIcon( String fullPath, boolean bIsDir, int whichIcon, int w, int h, int xform, int align, int pData[] ) {
		int			type;

		if ( w == 32 && h == 32 ) {
			if ( bIsDir )
				type = DefaultIconManager.kLargeDir;
			else
				type = DefaultIconManager.kLargeFile;
		}
		else if ( w == 16 && h == 16 ) {
			if ( bIsDir )
				type = DefaultIconManager.kSmallDir;
			else
				type = DefaultIconManager.kSmallFile;
		}
		else
			throw new IllegalArgumentException( "bad <w,h>=<" + w + "," + h + ">"  );	//	return ErrCodes.ERROR_PARAM;

		return defaultIconManager.getIcon( type, pData );
	}

	public int getExtIcon( String ext, int whichIcon, int w, int h, int xform, int align, int pData[] ) {
		int			type;

		if ( w == 32 && h == 32 )
			type = DefaultIconManager.kLargeFile;
		else if ( w == 16 && h == 16 )
			type = DefaultIconManager.kSmallFile;
		else
			throw new IllegalArgumentException( "bad <w,h>=<" + w + "," + h + ">" );	//	return ErrCodes.ERROR_PARAM;

		return defaultIconManager.getIcon( type, pData );
	}

	public int getVolumeIcon( String driveName, int whichIcon, int w, int h, int xform, int align, int pData[] ) {
		int			type;

		if ( w == 32 && h == 32 )
			type = DefaultIconManager.kLargeVolume;
		else if ( w == 16 && h == 16 )
			type = DefaultIconManager.kSmallVolume;
		else
			throw new IllegalArgumentException( "bad <w,h>=<" + w + "," + h + ">" );	//	return ErrCodes.ERROR_PARAM;

		return defaultIconManager.getIcon( type, pData );
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int createVolumeAlias( String driveName, String newAliasPath, int flags ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int createFileAlias( String targetPath, String newAliasPath, int flags ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

	public boolean isLinkFile( File fl ) {
		return false;
	}

	public boolean isDrivePath( File fl ) {
		return isDriveString( fl.getPath() );
	}

	public String pathToDriveName( File fl ) {
		return "/";
	}

	public boolean isDriveString( String drivePath ) {
		if ( drivePath == null )
			return false;

		return drivePath.equals( "/" );
	}

	public int getVolumes( int maxToReturn, int numReturned[], String driveNames[] ) {
		numReturned[ 0 ] = 1;
		driveNames[ 0 ] = "/";

		return 0;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int getVolumeLabel( String driveName, String label[] ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

	public int getVolumeMaxFileNameLength( String driveName, int nameLen[] ) {
		return 256;
	}

	public int getVolumeReferenceNumber( String driveName, int refNum[] ) {
		refNum[ 0 ] = 0;

		return 0;
	}

	public DateBundle getFileDateBundle( int flags, String path ) {
		return null;
	}

	public DateBundle getVolumeDateBundle( int flags, String driveName ) {
		return null;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int setVolumeLabel( String driveName, String newLabel ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int getDriveDisplayName( String driveName, String displayName[] ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

	public int getVolumeFlags( String driveName, int flags[] ) {
		flags[ 0 ] = kStandardVolumeAttrsMask;

		return ErrCodes.ERROR_NONE;
	}

	public int getVolumeReadFlagsMask( String driveName, int flags[] ) {
		flags[ 0 ] = kStandardVolumeAttrsMask;

		return ErrCodes.ERROR_NONE;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int resolveLinkFile( String linkFilePath, String retPath[], int flags ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int getAllMonitorInfo( int monitorInfo[], int maxToReturn, int numReturned[] ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int getMainMonitorInfo( int monitorInfo[] ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int getExecutableType( String fullPath, int val[] ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

	public int getFileAttributes( String fullPath, int val[]  ) {
		File		tempFile;

		if ( fullPath == null || val == null )
			throw new IllegalArgumentException( "fullPath or val is null" );	//	return ErrCodes.ERROR_PARAM;

		val[ 0 ] = 0;

		tempFile = new File( fullPath );
		if ( !tempFile.exists() )
			return -1;

		if ( tempFile.isDirectory() )
			val[ 0 ] |= DiskFile.FILE_DIR;

		if ( fullPath.startsWith( "." ) )
			val[ 0 ] |= DiskFile.FILE_HIDDEN;

		return ErrCodes.ERROR_NONE;
	}

	public int getFileAttributesMask( String fullPath, int val[]  ) {
		val[ 0 ] = kStandardFileAttrsMask;

		return ErrCodes.ERROR_NONE;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int launchApp( String appPath, String verb, String regKey, String commandLine, int retData[], int numArgs, String args[] ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

	public int launchURL( String url, int flags, String preferredBrowsers[] ) {
		Process		proc = null;

		try {
			proc = Runtime.getRuntime().exec( "netscape " + url );
		}
		catch ( Exception e ) {
			Trace.println( "can't launch netscape e=" + e );
			return -37;
		}

		if ( proc != null )
			return 0;
		else
			return -37;
	}

	public String[] findAppsByName( String appName, int maxToReturn, int flags ) {
		return null;
	}	

	public String[] findAppsByExtension( String ext, int maxToReturn, int flags ) {
		return null;
	}	

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int verifyNativeAppData( int appData[] ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int quitApp( int appData[], int flags ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int moveApp( int appData[], int selector, int flags ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}
}

