/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig;

import java.io.File;
import java.util.Vector;
import java.io.FileNotFoundException;

/**
This is a minimal implementation of the FileRegistryI interface.

<P>
As indicated in the FileRegistry documentation, a class implementing the FileRegistryI interface
is used by the FileRegistry to handle all its calls.

<P>
This class is only used on unsupported platforms, or if a platform-specific object which implements
FileRegistryI could not be created.
Only a small set of functionality is provided; most methods return null or -1.

<P>
FileRegistryFactory creates this object on behalf of the FileRegistry.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class FileRegistryPlain implements FileRegistryI {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private ConfigList			config;
	private PlatformInfoI		platformInfo;
	private int					direction;


/**
Creates a ConfigListFile object using the file 'jconfig.cfg'.
If that file can't be found, default values, stored internally are used.
If an error occurs, throws a ConfigException.
*/

	FileRegistryPlain( File curDir, int creator ) throws ConfigException {
		direction = 0;
		platformInfo = new PlatformInfoPlain();

		try {
			config = new ConfigListFile( curDir, ConfigList.kConfigFileName, creator );
		}
		catch ( Exception e ) {
			throw new ConfigException( "can't create ConfigListFile" );
		}

		Trace.println( "using FileRegistryPlain: if you're on a supported VM, you shouldn't see this message. Please check your configuration." );
	}

/**
Indicates which platform we're running on.
@return a PlatformInfoI object containing information on the current platform/VM
*/

	public PlatformInfoI getPlatformInfo() {
		return platformInfo;
	}

/**
Calls the ConfigListFile to map from a FinderInfo to zero or more FileExtensions.
*/

	public FileExtension[] findExtensions( FinderInfo fInfo, int maxToReturn ) {
		return config.findMatches( fInfo, maxToReturn, direction );
	}
	
/**
Calls the ConfigListFile to map from a FileExtensions to zero or more FinderInfo.
*/

	public FinderInfo[] findFinderInfo( FileExtension ext, int maxToReturn ) {
		return config.findMatches( ext, maxToReturn, direction );
	}

/**
Calls the ConfigListFile to iterate over the ConfigListFile entries.
*/

	public int iterate( ConfigEntryVisitor fdv ) {
		return config.iterate( fdv );
	}

/**
Always returns null.
*/

	public AppFile[] getApps( String appName, int maxToReturn, int flags ) {
		return null;
	}

/**
Always returns null.
*/

	public AppFile[] getApps( FileExtension ext, int maxToReturn, int flags ) {
		return null;
	}

/**
Always returns null.
*/

	public AppFile[] getApps( FinderInfo finfo, int maxToReturn, int flags ) {
		return null;
	}
	
/**
Always returns -1.
*/

	public int launchURL( String url, int flags, String preferredBrowsers[] ) {
		return -1;
	}

/**
Always returns null.
*/

	public DiskVolume[] getVolumes() {
		return null;
	}

/**
Always returns null.
*/

	public AppFile createAppFile( File fl ) throws FileNotFoundException, DiskFileException {
		return null;
	}

/**
Always returns null.
*/

	public DiskObject createDiskObject( File fl, int flags )
	throws FileNotFoundException, DiskFileException {
		return null;
	}

/**
Always returns -1.
*/

	public int createAlias( DiskObject target, File newAlias, int creator, int flags )
	throws FileNotFoundException, DiskFileException {
		return -1;
	}

/**
Always returns null.
*/

	public DiskObject resolveAlias( DiskAlias da, int flags )
	throws FileNotFoundException, DiskFileException {
		return null;
	}

/**
Always returns null.
*/

	public FileType getFileType( File fl )
	throws FileNotFoundException, DiskFileException {
		return null;
	}

 	public int getDirection() {
		return direction;
	}

	public void setDirection( int dir ) {
		direction = dir & FileRegistryI.INANDOUT_ONLY;
	}

/**
Returns an array containing one MonitorPlain object.
*/

	public Monitor[] getMonitors() {
		Monitor		plainArray[];

		plainArray = new Monitor[ 1 ];
		plainArray[ 0 ] = new MonitorPlain();

		return plainArray;
	}

/**
Returns a MonitorPlain object.
*/

	public Monitor getMainMonitor() {
		return new MonitorPlain();
	}

/**
Always returns null.
*/

	public AppProcess[] getProcesses( int maxToReturn, int flags ) {
		return null;
	}

/**
Always returns null.
*/

 	public FileSystem[] getFileSystems( int maxToReturn, int flags ) {
		return null;
	}

	public void setFileUtils( FileUtilsI fi ) {}
	public FileUtilsI getFileUtils() { return null; }
}

