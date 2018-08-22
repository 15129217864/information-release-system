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

import java.io.File;
import java.util.Vector;
import java.io.FileNotFoundException;
import com.jconfig.*;

/**
The FileRegistry delegates all calls to this object. 
This is used on Nix.


@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class FileRegistryNix implements FileRegistryI {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	static final int			kNoLibrary = 0;
	static final int			kLinuxX86Library = 1;

	private ConfigList			config;
	private PlatformInfoNix		platformInfo;
	private int					direction;

	FileRegistryNix( PlatformInfoNix platformInfo, int whichLibrary, File curDir, int creator ) throws ConfigException {
		direction = 0;
		this.platformInfo = platformInfo;

		try {
			config = new ConfigListFile( curDir, ConfigList.kConfigFileName, creator );
			AppUtilsNix.initialize( ( whichLibrary == kLinuxX86Library ), curDir );
		}
		catch ( Exception e ) {
			throw new ConfigException( "can't create" );
		}

		Trace.println( "using FileRegistryNix" );
	}

	public PlatformInfoI getPlatformInfo() {
		return platformInfo;
	}

	public FileExtension[] findExtensions( FinderInfo fInfo, int maxToReturn ) {
		return config.findMatches( fInfo, maxToReturn, direction );
	}
	
	public FinderInfo[] findFinderInfo( FileExtension ext, int maxToReturn ) {
		return config.findMatches( ext, maxToReturn, direction );
	}

	public int iterate( ConfigEntryVisitor fdv ) {
		return config.iterate( fdv );
	}

	public AppFile[] getApps( String appName, int maxToReturn, int flags ) {
		return null;
	}

	public AppFile[] getApps( FileExtension ext, int maxToReturn, int flags ) {
		return null;
	}

	public AppFile[] getApps( FinderInfo finfo, int maxToReturn, int flags ) {
		return null;
	}
	
	public int launchURL( String url, int flags, String preferredBrowsers[] ) {
		return AppUtilsNix.launchURL( url, flags, preferredBrowsers );
	}

	public DiskVolume[] getVolumes() {
		DiskVolume		retVal[];

		retVal = new DiskVolume[ 1 ];

		retVal[ 0 ] = new DiskVolumeNix( "" );

		return retVal;
	}

	public AppFile createAppFile( File fl ) throws FileNotFoundException, DiskFileException {
		return null;
	}

	public DiskObject createDiskObject( File fl, int flags )
	throws FileNotFoundException, DiskFileException {
		String			filePath, resolvedFile[], driveName;
		int				theErr;

		if ( !fl.exists() )
			throw new FileNotFoundException( "file must exist " + fl.getPath() );	   

		filePath = fl.getPath();

		if ( flags != 0 && AppUtilsNix.isLinkFile( fl ) ) {
			resolvedFile = new String[ 1 ];
			theErr = AppUtilsNix.resolveLinkFile( filePath, resolvedFile, flags );
			if ( theErr != ErrCodes.ERROR_NONE || resolvedFile[ 0 ] == null )
				return null;

			try {
				fl = new File( resolvedFile[ 0 ] );

				if ( !fl.exists() )
					throw new FileNotFoundException( "file must exist " + fl.getPath() );	   
			}
			catch ( Exception e ) {
				return null;
			}
		}

		return DOCreatorNix.createDiskObject( fl );
	}

	public int createAlias( DiskObject target, File newAlias, int creator, int flags )
	throws FileNotFoundException, DiskFileException {
		String				newAliasPath;
		int					theErr;

		if ( !target.exists() )
			throw new FileNotFoundException( "target not found" );
		if ( !newAlias.exists() )
			throw new FileNotFoundException( "newAlias not found" );

		newAliasPath = newAlias.getPath();

		if ( target instanceof DiskVolumeNix )
			theErr = AppUtilsNix.createVolumeAlias( ( (DiskVolumeNix) target ).getDriveName(), newAliasPath, flags );
		else if ( target instanceof DiskFileNix )
			theErr = AppUtilsNix.createFileAlias( ( (DiskFileNix) target ).getFilePath(), newAliasPath, flags );
		else
			theErr = -1;

		return theErr;
	}

	public DiskObject resolveAlias( DiskAlias da, int flags )
	throws FileNotFoundException, DiskFileException {
		DiskAliasNix		linuxAlias;
		File				fl;
		String				resolvedFile[], driveName;
		int					theErr;

		if ( !( da instanceof DiskAliasNix ) )
			return null;

		linuxAlias = (DiskAliasNix) da;

		if ( !linuxAlias.exists() )
			return null;

		resolvedFile = new String[ 1 ];

		theErr = AppUtilsNix.resolveLinkFile( linuxAlias.getFilePath(), resolvedFile, flags );
		if ( theErr != ErrCodes.ERROR_NONE || resolvedFile[ 0 ] == null )
			return null;

		try {
			fl = new File( resolvedFile[ 0 ] );
			if ( !fl.exists() )
				throw new FileNotFoundException( "file must exist " + fl.getPath() );	   
		}
		catch ( Exception e ) {
			return null;
		}

		return DOCreatorNix.createDiskObject( fl );
	}

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

	public Monitor[] getMonitors() {
		Monitor		plainArray[];

		plainArray = new Monitor[ 1 ];
		plainArray[ 0 ] = new MonitorPlain();

		return plainArray;
	}

	public Monitor getMainMonitor() {
		return new MonitorPlain();
	}

	public AppProcess[] getProcesses( int maxToReturn, int flags ) {
		return null;
	}

 	public FileSystem[] getFileSystems( int maxToReturn, int flags ) {
		return FSCreatorNix.getFileSystems( maxToReturn, flags );
	}

	public void setFileUtils( FileUtilsI fi ) {}
	public FileUtilsI getFileUtils() { return null; }
}

