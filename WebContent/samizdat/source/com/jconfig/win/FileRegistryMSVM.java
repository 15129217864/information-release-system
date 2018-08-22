/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig.win;


import com.jconfig.*;
import java.io.File;
import java.util.Vector;
import java.io.FileNotFoundException;


/**
On Windows, the FileRegistry will delegate all calls to this object. See FileRegistryFactoryWin for details.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class FileRegistryMSVM implements FileRegistryI {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	static final int			kMSVM1 = 1;
	static final int			kSun11 = 2;
	static final int			kMSVM2 = 3;
	static final int			kMSVM1W = 4;
	static final int			kSun11W = 5;
	static final int			kMSVM2W = 6;

	private static final int		kGetVolumesMaxReturn = 64;

/**
Name of the DLL used on Win95/Win98 with the JDK
*/

	private static final String	kSun11LibName = "jcnfigSN";

/**
Name of the DLL used on Win95/Win98 with the MSVM (SDK 1.5 or earlier)
*/

	private static final String	kMSVM1LibName = "jcnfigMS";

/**
Name of the DLL used on Win95/Win98 with the MSVM (SDK 2.0 or later)
*/

	private static final String	kMSVM2LibName = "jcnfigM2";

/**
Name of the DLL used on WinNT with the JDK
*/

	private static final String	kSun11LibNameW = "jcnfigSW";

/**
Name of the DLL used on WinNT with the MSVM (SDK 1.5 or earlier)
*/

	private static final String	kMSVM1LibNameW = "jcnfigMW";

/**
Name of the DLL used on WinNT with the MSVM (SDK 2.0 or later)
*/

	private static final String	kMSVM2LibNameW = "jcnfig2W";

	private ConfigList				config;
	private AppFinderMSVM			appFinder;
	private PlatformInfoMSVM		platformInfo;

	private int						direction;

/**
First, try to load the indicated native library. Then, create ConfigListFile and AppFinderMSVM objects,
and initialize AppUtilsMSVM.

<P>
The 'findExtensions', 'findFinderInfo', and 'iterate' methods will be delegated to the ConfigListFile
object.

<P>
The 'getApps' calls will be delegated to the 'AppFinderMSVM' object either in whole or in part.
*/

	FileRegistryMSVM( PlatformInfoMSVM platformInfo, int whichLibrary, File curDir, int creator )
	throws ConfigException {
		String		libName;

		this.platformInfo = platformInfo;

		if ( whichLibrary == kMSVM1 )
			libName = kMSVM1LibName;
		else if ( whichLibrary == kMSVM2 )
			libName = kMSVM2LibName;
		else if ( whichLibrary == kSun11 )
			libName = kSun11LibName;
		else if ( whichLibrary == kMSVM1W )
			libName = kMSVM1LibNameW;
		else if ( whichLibrary == kMSVM2W )
			libName = kMSVM2LibNameW;
		else if ( whichLibrary == kSun11W )
			libName = kSun11LibNameW;
		else
			throw new ConfigException( "bad library number " + whichLibrary );

		Trace.println( "loading " + libName );

		Runtime.getRuntime().loadLibrary( libName );

		try {
			config = new ConfigListFile( curDir, ConfigList.kConfigFileName, creator );
		}
		catch ( Exception e ) {
			throw new ConfigException( "can't create using " + curDir.getPath() );
		}

		AppUtilsMSVM.initialize( curDir );

		direction = 0;
		appFinder = new AppFinderMSVM();
	}

	public PlatformInfoI getPlatformInfo() {
		return platformInfo;
	}

/**
*/

	public FileExtension[] findExtensions( FinderInfo fInfo, int maxToReturn ) {
		return config.findMatches( fInfo, maxToReturn, direction );
	}
	
/**
*/

	public FinderInfo[] findFinderInfo( FileExtension ext, int maxToReturn ) {
		return config.findMatches( ext, maxToReturn, direction );
	}

/**
*/

	public int iterate( ConfigEntryVisitor fdv ) {
		return config.iterate( fdv );
	}

/**
*/

	public AppFile[] getApps( String appName, int maxToReturn, int flags ) {
		return appFinder.findAppsByName( appName, maxToReturn, flags );
	}

/**
*/

	public AppFile[] getApps( FileExtension ext, int maxToReturn, int flags ) {
		return appFinder.findAppsByExtension( ext, maxToReturn, flags );
	}

/**
*/

	public AppFile[] getApps( FinderInfo finfo, int maxToReturn, int flags ) {
		FileExtension	exts[];
		int				i, appIndex, retIndex, numApps;
		AppFile			appArray[], retArray[];
		Vector			appVector;
		
		exts = findExtensions( finfo, 25 );

		if ( exts == null || exts.length < 1 )
			return null;
		
		if ( exts.length == 1 )
			return getApps( exts[ 0 ], maxToReturn, flags );
		else {

			appVector = new Vector( 5, 5 );

			for ( i = 0, numApps = 0; i < exts.length; i++ ) {
				appArray = getApps( exts[ i ], 1, flags );
				if ( appArray == null )
					continue;

				for ( appIndex = 0; appIndex < appArray.length; appIndex++ ) {
					if ( appArray[ appIndex ] != null )
						++numApps;
				}

				appVector.addElement( appArray );
			}
			
			if ( numApps == 0 )
				return null;

			retArray = new AppFile[ numApps ];

			for ( i = 0, retIndex = 0; i < appVector.size(); i++ ) {
			
				appArray = (AppFile[]) appVector.elementAt( i );

				for ( appIndex = 0; appIndex < appArray.length; appIndex++ ) {
					if ( appArray[ appIndex ] != null )
						retArray[ retIndex++ ] = appArray[ appIndex ];
				}
			}
			
			return retArray;
		}
	}
	
/**
*/

	public int launchURL( String url, int flags, String preferredBrowsers[] ) {
		return AppUtilsMSVM.launchURL( url, flags, preferredBrowsers );
	}

/**
*/

	public DiskVolume[] getVolumes() {

		DiskVolume		retVal[];

		String			driveNames[];

		int				i, theErr, numReturned, numRet[];

		

		numRet = new int[ 1 ];

		driveNames = new String[ kGetVolumesMaxReturn ];



		theErr = AppUtilsMSVM.getVolumes( kGetVolumesMaxReturn, numRet, driveNames );

		

		numReturned = numRet[ 0 ];

		

		if ( theErr != ErrCodes.ERROR_NONE || numReturned < 1 )

			return null;

		

		retVal = new DiskVolume[ numReturned ];

		

		for ( i = 0; i < numReturned; i++ )

			retVal[ i ] = new DiskVolumeMSVM( driveNames[ i ] );

		

		return retVal;

	}

/**
*/

	public AppFile createAppFile( File fl ) throws FileNotFoundException, DiskFileException {
		return new AppFileMSVM( AppUtilsMSVM.adjustBadMSVM1Path( fl.getPath() ) );
	}

/**
*/

	public DiskObject createDiskObject( File fl, int flags )
	throws FileNotFoundException, DiskFileException {
		String			filePath, resolvedFile[], driveName;
		int				theErr, newFlags;

		if ( !fl.exists() )
			throw new FileNotFoundException( "file must exist " + fl.getPath() );	   

		filePath = AppUtilsMSVM.adjustBadMSVM1Path( fl.getPath() );

		if ( flags != 0 && AppUtilsMSVM.isLinkFile( fl ) ) {
			newFlags = ( flags == ALIAS_UI ? AppUtilsMSVM.kResolveLinkFileUI : AppUtilsMSVM.kResolveLinkFileNoUI );
			resolvedFile = new String[ 1 ];

			theErr = AppUtilsMSVM.resolveLinkFile( filePath, resolvedFile, newFlags );
			if ( theErr != ErrCodes.ERROR_NONE || resolvedFile[ 0 ] == null )
				return null;

			try {
				fl = new File( resolvedFile[ 0 ] );

				if ( !fl.exists() )
					throw new FileNotFoundException( "file must exist " + fl.getPath() );	   

				filePath = AppUtilsMSVM.adjustBadMSVM1Path( fl.getPath() );
			}
			catch ( Exception e ) {
				return null;
			}
		}

		return DOCreatorMSVM.createDiskObject( fl );

	}


/**
*/

	public int createAlias( DiskObject target, File newAlias, int creator, int flags )
	throws FileNotFoundException, DiskFileException {
		String				newAliasPath;
		int					theErr;

		if ( !target.exists() )
			throw new FileNotFoundException( "target not found" );
		if ( !newAlias.exists() )
			throw new FileNotFoundException( "newAlias not found" );

		newAliasPath = AppUtilsMSVM.adjustBadMSVM1Path( newAlias.getPath() );

		if ( target instanceof DiskVolumeMSVM )
			theErr = AppUtilsMSVM.createVolumeAlias( ( (DiskVolumeMSVM) target ).getDriveName(), newAliasPath, flags );
		else if ( target instanceof DiskFileMSVM )
			theErr = AppUtilsMSVM.createFileAlias( ( (DiskFileMSVM) target ).getFilePath(), newAliasPath, flags );
		else
			theErr = -1;

		return theErr;
	}

/**
*/

	public DiskObject resolveAlias( DiskAlias da, int flags )
	throws FileNotFoundException, DiskFileException {
		DiskAliasMSVM		msvmAlias;
		File				fl;
		String				filePath, resolvedFile[], driveName;
		int					theErr, newFlags;

		if ( !( da instanceof DiskAliasMSVM ) )
			return null;

		msvmAlias = (DiskAliasMSVM) da;

		if ( !msvmAlias.exists() )
			return null;

		resolvedFile = new String[ 1 ];
		newFlags = ( flags == ALIAS_UI ? AppUtilsMSVM.kResolveLinkFileUI : AppUtilsMSVM.kResolveLinkFileNoUI );

		theErr = AppUtilsMSVM.resolveLinkFile( msvmAlias.getFilePath(), resolvedFile, newFlags );
		if ( theErr != ErrCodes.ERROR_NONE || resolvedFile[ 0 ] == null )
			return null;

		try {
			fl = new File( resolvedFile[ 0 ] );
			if ( !fl.exists() )
				throw new FileNotFoundException( "file must exist " + fl.getPath() );	   

			filePath = AppUtilsMSVM.adjustBadMSVM1Path( fl.getPath() );
		}
		catch ( Exception e ) {
			return null;
		}

		return DOCreatorMSVM.createDiskObject( fl );

	}

/**
*/

	public FileType getFileType( File fl )
	throws FileNotFoundException, DiskFileException {
		return new FileType( new FileExtension( fl.getName() ) );
	}

/**
*/

 	public int getDirection() {
		return direction;
	}

/**
*/

	public void setDirection( int dir ) {
		direction = dir & FileRegistryI.INANDOUT_ONLY;
	}

/**
Calls MonitorHelperMSVM.getMonitors()
*/

	public Monitor[] getMonitors() {
		return MonitorHelperMSVM.getMonitors();
	}

/**
Calls MonitorHelperMSVM.getMainMonitor()
*/

	public Monitor getMainMonitor() {
		return MonitorHelperMSVM.getMainMonitor();
	}

/**
Calls ProcessHelperMSVM.getProcesses()
*/

	public AppProcess[] getProcesses( int maxToReturn, int flags ) {
		return ProcessHelperMSVM.getProcesses( maxToReturn, flags );
	}

/**
Calls FSCreatorMSVM.getFileSystems()
*/

 	public FileSystem[] getFileSystems( int maxToReturn, int flags ) {
		return FSCreatorMSVM.getFileSystems( maxToReturn, flags );
	}

	public void setFileUtils( FileUtilsI fi ) {}
	public FileUtilsI getFileUtils() { return null; }
}

