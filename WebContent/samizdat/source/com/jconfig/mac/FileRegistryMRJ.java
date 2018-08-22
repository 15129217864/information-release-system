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
import java.io.FileNotFoundException;

/**

							********   TO DO *************
							change docs to reflect new impl
							******************************


The main class of the package. This is created by FileRegistryFactoryMac, and on Mac, all calls to the FileRegistry
singleton will be delegated to this class. This class loads the appropriate native code shared library.

<BR>
If Internet Config is installed, some calls are delegated to an IConfigMRJ object; otherwise, those calls are
delegated to a ConfigListFile object.

<BR>
Some other calls are delegated to an AppFinderMRJ object, as well as to native code via AppUtilsMRJ.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class FileRegistryMRJ implements FileRegistryI {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private AppFinderMRJ		appFinder;
	private ConfigList			plainConfig;
	private IConfigMRJ			mrjConfig;
	private MacPropertiesMRJ	props;
	private PlatformInfoMRJ		platformInfo;
	private int					direction;

	private static final String		kJCPropsFileName = "jcprops.txt";

	static final int				kMRJJRI = 0;
	static final int				kCW113 = 1;
	static final int				kMRJOSX = 2;
													
	private static final int		kGetVolumesMaxReturn = 64;

/**
Uses loadLibrary() to load the appropriate native code library.
Creates a ConfigList object using either IConfigMRJ or ConfigListPlain
Creates an AppFinderMRJ object to which some calls are delegated.
*/

	FileRegistryMRJ( PlatformInfoMRJ platformInfo, int which, File curDir, int creator ) throws ConfigException {
		this.platformInfo = platformInfo;

		if ( which == kMRJJRI ) {
			Trace.println( "FileRegistryMRJ, loading JConfig" );
			Runtime.getRuntime().loadLibrary( "JConfig" );
		}
		else if ( which == kMRJOSX ) {
			java.awt.Dimension			d;

			Trace.println( "FileRegistryMRJ, loading JConfigOSX" );

				//	see osxinfo.html for the reason why
			d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

			Runtime.getRuntime().loadLibrary( "JConfigOSX" );

			CocoaUtilsOSX.init();

			IToolboxLock lock = new ToolboxLockOSX();
			AppUtilsMRJ.setLockObject( lock );
			IConfigMRJ.setLockObject( lock );
			XUtilsOSX.setLockObject( lock );
			AppFinderMRJ.setLockObject( lock );
			ResFileMRJ.setLockObject( lock );
		}
		else {
			Trace.println( "FileRegistryMRJ, loading JConfigCW" );
			Runtime.getRuntime().loadLibrary( "JConfigCW" );
		}

		plainConfig = null;
		direction = 0;
		
		props = new MacPropertiesMRJ( curDir, kJCPropsFileName );

		mrjConfig = tryCreateMRJConfig( curDir, creator );
		plainConfig = tryCreatePlainConfig( curDir, creator );

		if ( mrjConfig == null && plainConfig == null )
			throw new ConfigException( "can't create either mrjConfig or plainConfig" );

		Trace.println( "plainConfig=" + plainConfig + ", mrjConfig=" + mrjConfig );

		appFinder = new AppFinderMRJ( curDir, creator );

		createFileUtils( curDir, creator );

		props.dumpProperties( Trace.getOut() );
	}

	public PlatformInfoI getPlatformInfo() {
		return platformInfo;
	}

	private void createFileUtils( File curDir, int creator ) {
		FileUtilsI			fileUtils;
		Class				clazz;
		String				externalClassName;

		fileUtils = null;

		externalClassName = props.getPropFileUtilsClassName();
		if ( externalClassName != null && externalClassName.length() > 0 ) {
			try {
				clazz = Class.forName( externalClassName );
				if ( clazz != null ) {
					fileUtils = (FileUtilsI) clazz.newInstance();
					return;
				}
				else
					Trace.println( "can't create " + externalClassName );
			}
			catch ( Exception e ) {
				Trace.println( "can't create " + externalClassName + ", e=" + e );
			}
		}

		if ( platformInfo.isPowerMac() && platformInfo.isApple() && platformInfo.is11OrGreater() ) {
			try {
				clazz = Class.forName( "com.jconfig.mac.DefaultFileUtils11MRJ" );
				if ( clazz != null )
					fileUtils = (FileUtilsI) clazz.newInstance();
			}
			catch ( Exception e ) {
				Trace.println( "createFileUtils, e=" + e );
			}
		}
		
		if ( fileUtils == null )
			fileUtils = new DefaultFileUtilsNullMRJ();

		fileUtils.initialize( curDir, creator );
		setFileUtils( fileUtils );
	}

	public FileExtension[] findExtensions( FinderInfo fInfo, int maxToReturn ) {
		if ( props.getPropFindExtensions() == MacPropertiesMRJ.kPropDefault ) {
			if ( mrjConfig != null )
				return mrjConfig.findMatches( fInfo, maxToReturn, direction );
			else
				return plainConfig.findMatches( fInfo, maxToReturn, direction );
		}
		else {
			if ( plainConfig != null )
				return plainConfig.findMatches( fInfo, maxToReturn, direction );
			else
				return mrjConfig.findMatches( fInfo, maxToReturn, direction );
		}
	}
	
	public FinderInfo[] findFinderInfo( FileExtension ext, int maxToReturn ) {
		if ( props.getPropFindFinderInfo() == MacPropertiesMRJ.kPropDefault ) {
			if ( mrjConfig != null )
				return mrjConfig.findMatches( ext, maxToReturn, direction );
			else
				return plainConfig.findMatches( ext, maxToReturn, direction );
		}
		else {
			if ( plainConfig != null )
				return plainConfig.findMatches( ext, maxToReturn, direction );
			else
				return mrjConfig.findMatches( ext, maxToReturn, direction );
		}
	}

	public AppFile[] getApps( String appName, int maxToReturn, int flags ) {
		AppFile					firstSetOfApps[], tempApp;
		int						tempVRefAndParID[], theErr;
		byte					tempPName[];

		firstSetOfApps = getApps_classic( appName, maxToReturn, flags );

		if ( !PlatformInfoMRJ.getInstance().isPlatformMRJOSX() )
			return firstSetOfApps;

		tempVRefAndParID = new int[ AppUtilsMRJ.kRefPairLen ];
		tempPName = new byte[ AppUtilsMRJ.kPNameLen ];

		theErr = XUtilsOSX.findApplicationForInfo( XUtilsOSX.kLSUnknownCreator, null, appName,
													tempVRefAndParID, tempPName );
		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "getApps.name: theErr=" + theErr + " for " + appName );
			return firstSetOfApps;
		}

		try {
			tempApp = DOFactoryMRJ.createAppFile( tempVRefAndParID[ AppUtilsMRJ.kVRefOffset ],
													tempVRefAndParID[ AppUtilsMRJ.kParIDOffset ],
													tempPName );
		}
		catch ( Exception e ) {
			Trace.println( "getApps.name: e=" + e + " for " + appName );
			return firstSetOfApps;
		}

		return pushOntoAppArray( tempApp, firstSetOfApps );
	}

	public AppFile[] getApps( FileExtension ext, int maxToReturn, int flags ) {
		AppFile					firstSetOfApps[], tempApp;
		String					plainExtension;
		int						tempVRefAndParID[], theErr;
		byte					tempPName[];

		firstSetOfApps = getApps_classic( ext, maxToReturn, flags );

		if ( !PlatformInfoMRJ.getInstance().isPlatformMRJOSX() )
			return firstSetOfApps;

		tempVRefAndParID = new int[ AppUtilsMRJ.kRefPairLen ];
		tempPName = new byte[ AppUtilsMRJ.kPNameLen ];

		plainExtension = ext.getString().substring( 1, ext.getString().length() );

		theErr = XUtilsOSX.getApplicationForInfo( XUtilsOSX.kLSUnknownType, XUtilsOSX.kLSUnknownCreator,
													plainExtension, XUtilsOSX.kLSRolesAll,
													tempVRefAndParID, tempPName );
		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "getApps.ext: theErr=" + theErr + " for " + plainExtension );
			return firstSetOfApps;
		}

		try {
			tempApp = DOFactoryMRJ.createAppFile( tempVRefAndParID[ AppUtilsMRJ.kVRefOffset ],
													tempVRefAndParID[ AppUtilsMRJ.kParIDOffset ],
													tempPName );
		}
		catch ( Exception e ) {
			Trace.println( "getApps.ext: e=" + e + " for " + plainExtension );
			return firstSetOfApps;
		}

		return pushOntoAppArray( tempApp, firstSetOfApps );
	}

	public AppFile[] getApps( FinderInfo finfo, int maxToReturn, int flags ) {
		AppFile					firstSetOfApps[], tempApp;
		int						tempVRefAndParID[], theErr;
		byte					tempPName[];

		firstSetOfApps = getApps_classic( finfo, maxToReturn, flags );

		if ( !PlatformInfoMRJ.getInstance().isPlatformMRJOSX() )
			return firstSetOfApps;

		tempVRefAndParID = new int[ AppUtilsMRJ.kRefPairLen ];
		tempPName = new byte[ AppUtilsMRJ.kPNameLen ];

		theErr = XUtilsOSX.getApplicationForInfo( XUtilsOSX.kLSUnknownType, finfo.getCreator(),
													null, XUtilsOSX.kLSRolesAll,
													tempVRefAndParID, tempPName );
		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "getApps.finfo: theErr=" + theErr + " for " + Integer.toHexString( finfo.getCreator() ) );
			return firstSetOfApps;
		}

		try {
			tempApp = DOFactoryMRJ.createAppFile( tempVRefAndParID[ AppUtilsMRJ.kVRefOffset ],
													tempVRefAndParID[ AppUtilsMRJ.kParIDOffset ],
													tempPName );
		}
		catch ( Exception e ) {
			Trace.println( "getApps.finfo: e=" + e + " for " + Integer.toHexString( finfo.getCreator() ) );
			return firstSetOfApps;
		}

		return pushOntoAppArray( tempApp, firstSetOfApps );
	}

	public AppFile[] getApps_classic( String appName, int maxToReturn, int flags ) {
		CEVAppByNameFinder		cev;

		if ( props.getPropGetApps() == MacPropertiesMRJ.kPropDefault && mrjConfig != null )
			return mrjConfig.findAppByName( appName, maxToReturn, flags );

		cev = new CEVAppByNameFinder( appFinder, appName, maxToReturn, flags );

		iterate( cev );

		return cev.getApps();
	}

	public AppFile[] getApps_classic( FinderInfo finfo, int maxToReturn, int flags ) {
		return appFinder.findAPPL( finfo.getCreator(), maxToReturn, flags );
	}
	
	public AppFile[] getApps_classic( FileExtension ext, int maxToReturn, int flags ) {
		FinderInfo		finderInfo[];
		int				i, appIndex, retIndex, numApps;
		AppFile			appArray[], retArray[];
		Vector			appVector;
		
		finderInfo = findFinderInfo( ext, maxToReturn );

		if ( finderInfo == null || finderInfo.length < 1 )
			return null;
		
		if ( finderInfo.length == 1 )
			return getApps( finderInfo[ 0 ], maxToReturn, flags );


		appVector = new Vector( 5, 5 );

		for ( i = 0, numApps = 0; i < finderInfo.length; i++ ) {
			appArray = getApps( finderInfo[ i ], 1, flags );
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

		for ( i = 0, retIndex = 0; i < finderInfo.length; i++ ) {
			appArray = (AppFile[]) appVector.elementAt( i );

			for ( appIndex = 0; appIndex < appArray.length; appIndex++ ) {
				if ( appArray[ appIndex ] != null )
					retArray[ retIndex++ ] = appArray[ appIndex ];
			}
		}
		
		return retArray;
	}

	private AppFile[] pushOntoAppArray( AppFile af, AppFile ary[] ) {
		AppFile			retVal[];

		if ( af == null )
			return ary;

		if ( ary == null || ary.length < 1 ) {
			retVal = new AppFile[ 1 ];
			retVal[ 0 ] = af;

			return retVal;
		}

		retVal = new AppFile[ ary.length + 1 ];
		retVal[ 0 ] = af;
		System.arraycopy( ary, 0, retVal, 1, ary.length );

		return retVal;
	}

	public int iterate( ConfigEntryVisitor fdv ) {
		if ( props.getPropIterate() == MacPropertiesMRJ.kPropDefault ) {
			if ( mrjConfig != null )
				return mrjConfig.iterate( fdv );
			else
				return plainConfig.iterate( fdv );
		}
		else {
			if ( plainConfig != null )
				return plainConfig.iterate( fdv );
			else
				return mrjConfig.iterate( fdv );
		}
	}

	public int launchURL( String url, int flags, String preferredBrowsers[] ) {
		int		theErr;

		if ( props.getPropLaunchURL() == MacPropertiesMRJ.kPropDefault && mrjConfig != null ) {
			theErr = mrjConfig.launchURL( url, flags, preferredBrowsers );
			if ( theErr == ErrCodes.ERROR_NONE )
				return 0;
		}

		return AppUtilsMRJ.launchURL( url, flags, preferredBrowsers );
	}

	private IConfigMRJ tryCreateMRJConfig( File curDir, int creator ) {
		try {
			return new IConfigMRJ( creator );
		}
		catch ( Exception e ) {
			return null;
		}
	}

	private ConfigList tryCreatePlainConfig( File curDir, int creator ) {
		try {
			return new ConfigListFile( curDir, ConfigList.kConfigFileName, creator );
		}
		catch ( Exception e ) {
			return null;
		}
	}

	public DiskVolume[] getVolumes() {
		DiskVolume		retVal[];
		int				i, theErr, numReturned, numRet[], vRefs[];
		
		numRet = new int[ 1 ];
		vRefs = new int[ kGetVolumesMaxReturn ];

		theErr = AppUtilsMRJ.getVolumes( kGetVolumesMaxReturn, numRet, vRefs );
		
		numReturned = numRet[ 0 ];
		
		if ( theErr != ErrCodes.ERROR_NONE || numReturned < 1 )
			return null;
		
		retVal = new DiskVolume[ numReturned ];
		
		for ( i = 0; i < numReturned; i++ )
			retVal[ i ] = DOFactoryMRJ.createDiskVolume( vRefs[ i ] );
		
		return retVal;
	}

	public AppFile createAppFile( File fl ) throws FileNotFoundException, DiskFileException {
		String		appPath;
		int			theErr, vRefAndParID[], vRef, parID;
		byte			pName[];

		appPath = FileUtilsHolderMRJ.getPath( fl );

		vRefAndParID = new int[ AppUtilsMRJ.kRefPairLen ];
		pName = new byte[ AppUtilsMRJ.kPNameLen ];

		theErr = AppUtilsMRJ.fullPathToSpec( appPath, vRefAndParID, pName );
		if ( theErr != ErrCodes.ERROR_NONE )
			return null;

		vRef = vRefAndParID[ AppUtilsMRJ.kVRefOffset ];
		parID = vRefAndParID[ AppUtilsMRJ.kParIDOffset ];

		return DOFactoryMRJ.createAppFile( vRef, parID, pName );
	}

	public DiskObject createDiskObject( File fl, int flags )
	throws FileNotFoundException, DiskFileException {
		String		filePath;
		int			theErr, vRefAndParID[], vRef, parID, category[];
		byte			pName[];
		
		if ( !fl.exists() )
			throw new FileNotFoundException( "file must exist " + fl.getPath() );

		filePath = FileUtilsHolderMRJ.getPath( fl );

		vRefAndParID = new int[ AppUtilsMRJ.kRefPairLen ];
		pName = new byte[ AppUtilsMRJ.kPNameLen ];
		category = new int[ 1 ];

		theErr = AppUtilsMRJ.fullPathToSpec( filePath, vRefAndParID, pName );
		if ( theErr != ErrCodes.ERROR_NONE )
			return null;

		if ( flags != 0 ) {
			theErr = AppUtilsMRJ.resolveAlias( vRefAndParID[ AppUtilsMRJ.kVRefOffset ],
															vRefAndParID[ AppUtilsMRJ.kParIDOffset ],
															pName,
															vRefAndParID,
															pName,
															flags );
			if ( theErr != ErrCodes.ERROR_NONE )
				return null;
		}

		vRef = vRefAndParID[ AppUtilsMRJ.kVRefOffset ];
		parID = vRefAndParID[ AppUtilsMRJ.kParIDOffset ];

		return DOFactoryMRJ.createDiskObject( vRef, parID, pName );
	}

	public int createAlias( DiskObject target, File newAlias, int creator, int flags )
	throws FileNotFoundException, DiskFileException {
		IMacDiskObject			macDiskObject;
		String				newAliasPath;
		int					theErr;

		if ( !target.exists() )
			throw new FileNotFoundException( "target not found" );
		if ( !newAlias.exists() )
			throw new FileNotFoundException( "newAlias not found" );

		newAliasPath = FileUtilsHolderMRJ.getPath( newAlias );

		if ( target instanceof DiskVolume ) {
			macDiskObject = (IMacDiskObject) target;
			theErr = AppUtilsMRJ.createVolumeAlias( macDiskObject.getVRef(),
															newAliasPath, creator, flags );
		}
		else if ( target instanceof DiskFile ) {
			macDiskObject = (IMacDiskObject) target;
			theErr = AppUtilsMRJ.createAlias( macDiskObject.getVRef(), macDiskObject.getParID(),
															macDiskObject.getPName(),
															newAliasPath, creator, flags );
		}
		else
			theErr = -1;
		
		return theErr;
	}

	public DiskObject resolveAlias( DiskAlias da, int flags )
	throws FileNotFoundException, DiskFileException {
		IMacDiskObject		macAlias;
		int					theErr, outVRef, outParID, pOutVRefAndParID[], cat[];
		byte				pOutName[];

		if ( !da.exists() )
			return null;

		try {
			macAlias = (IMacDiskObject) da;
		}
		catch ( ClassCastException e ) {
			return null;
		}
		
		pOutVRefAndParID = new int[ AppUtilsMRJ.kRefPairLen ];
		pOutName = new byte[ AppUtilsMRJ.kPNameLen ];

		theErr = AppUtilsMRJ.resolveAlias( macAlias.getVRef(),
														macAlias.getParID(),
														macAlias.getPName(),
														pOutVRefAndParID, pOutName, flags );
		if ( theErr != ErrCodes.ERROR_NONE )
			return null;

		cat = new int[ 1 ];

		outVRef = pOutVRefAndParID[ AppUtilsMRJ.kVRefOffset ];
		outParID = pOutVRefAndParID[ AppUtilsMRJ.kParIDOffset ];

		return DOFactoryMRJ.createDiskObject( outVRef, outParID, pOutName );
	}

	public FileType getFileType( File fl ) throws FileNotFoundException, DiskFileException {
		DiskObject		diskObj;
		FinderInfo		finderInfo;
		int				theErr, fInfo[];

		diskObj = createDiskObject( fl, 0 );
		if ( diskObj == null )
			return null;

		try {
			finderInfo = ( (DiskFile) diskObj ).getFinderInfo();
		}
		catch ( ClassCastException e ) {
			return null;
		}

		if ( finderInfo == null )
			return null;

		return new FileType( finderInfo );
	}

 	public int getDirection() {
		return direction;
	}

	public void setDirection( int dir ) {
		direction = dir & FileRegistryI.INANDOUT_ONLY;
	}

	public Monitor[] getMonitors() {
		return MonitorHelperMRJ.getMonitors();
	}

	public Monitor getMainMonitor() {
		return MonitorHelperMRJ.getMainMonitor();
	}

	public AppProcess[] getProcesses( int maxToReturn, int flags ) {
		return ProcessHelperMRJ.getProcesses( maxToReturn, flags );
	}

 	public FileSystem[] getFileSystems( int maxToReturn, int flags ) {
		return FSCreatorMRJ.getFileSystems( maxToReturn, flags );
	}

	public void setFileUtils( FileUtilsI fi ) { FileUtilsHolderMRJ.setFileUtils( fi ); }
	public FileUtilsI getFileUtils() { return FileUtilsHolderMRJ.getFileUtils(); }
}

