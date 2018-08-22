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
import java.io.PrintStream;
import java.util.Date;
import java.util.Vector;
import java.io.FileNotFoundException;

/**
Represents a Mac application as stored on disk (not a running process.)

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppFileOSX extends DiskFileMRJ implements AppFile, AppCommandWatcher {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
The creator code of this app.
*/

	private int					creatorCode;

/**
The SIZE flags of this app. This value is read once, and then cached.
*/

	private int					sizeFlags;

/**
The minimum partition of this app. This value is read once, and then cached.
*/

	private int					minimumPartition;

/**
The preferred partition of this app. This value is read once, and then cached.
*/

	private int					preferredPartition;

/**
The file types openable with this app. For instance, 'TEXT' is associated with SimpleText.
*/

	private FileType			fileTypes[];

/**
Stores the processes created from this application.
*/

	private Vector				instances;

/**
The commands that can be sent to this app.
*/

	private AppCommand			theCommands[];

/**
The file types of this app are lazily-created and then cached using this boolean.
*/

	private boolean				bAlreadyRequestedFileTypes;
	
	private static final int		kNumCommands = 3;

/**
Create from an FSSpec
The app must have type either 'APPL' or 'APPE'.
The app is created with three default commands: open app, open doc, and print doc.
The SIZE flags and partition values are read and cached.
The file types openable by this app are not read here; they will only be read if the getFileTypes() method is called.

@param vRef the vRefNum of the app's file
@param parID the parID of the app's file
@param pName the name of the app's file, as a Pascal string
*/

	AppFileOSX( int vRef, int parID, byte pName[], int category )
	throws FileNotFoundException, DiskFileException {
		super( vRef, parID, pName, false );

		FinderInfo		ourFinderInfo;
		int				theErr;

/*
	static final int		kCategoryOSXPackageBit = 0x00000002;
	static final int		kCategoryOSXApplicationBit = 0x00000004;
	static final int		kCategoryOSXContainerBit = 0x00000008;
	static final int		kCategoryOSXAliasFileBit = 0x00000010;
	static final int		kCategoryOSXSymlinkBit = 0x00000020;
	static final int		kCategoryOSXInvisibleBit = 0x00000040;
	static final int		kCategoryOSXNativeAppBit = 0x00000080;
	static final int		kCategoryOSXClassicAppBit = 0x00000100;
	static final int		kCategoryOSXAppPrefersNativeBit = 0x00000200;
	static final int		kCategoryOSXAppPrefersClassicBit = 0x00000400;
	static final int		kCategoryOSXAppIsScriptableBit = 0x00000800;
	static final int		kCategoryOSXVolumeBit = 0x00001000;
	static final int		kCategoryOSXExtensionIsHiddenBit = 0x00100000;
*/

		bAlreadyRequestedFileTypes = false;
		instances = new Vector( 2, 2 );
		fileTypes = null;

		ourFinderInfo = getFinderInfo();

//http://developer.apple.com/technotes/tn/tn2017.html

		creatorCode = ourFinderInfo.getCreator();

		theCommands = new AppCommand[ kNumCommands ];
		theCommands[ 0 ] = new AppCommandMRJoapp();
		theCommands[ 1 ] = new AppCommandMRJodoc();
		theCommands[ 2 ] = new AppCommandMRJpdoc();
		
		sizeFlags = 0;
		minimumPartition = 0;
		preferredPartition = 0;
	}

/**
Returns the icons associated with the given file types.
For instance, photoshop has different icons for PICT and JPEG files.
*/

	public IconBundle getIconBundle( FileType fileType ) {
		FinderInfo			fInfos[];
		FileExtension		exts[];

		if ( fileType.getProvenance() == FileType.CREATED_FROM_EXT ) {
			exts = fileType.getExtensions( 1 );
			if ( exts != null && exts[ 0 ] != null ) {
				if ( exts[ 0 ].isMatch( new FileExtension( ".exe" ) ) )
					return IconBundleFactoryMRJ.createFromFTAC( getVRef(), creatorCode, FinderInfo.APPLICATION_TYPE );
			}
		}

		fInfos = fileType.getFinderInfos( 1 );
		if ( fInfos == null || fInfos[ 0 ] == null )
			return null;

		return IconBundleFactoryMRJ.createFromFTAC( getVRef(), creatorCode, fInfos[ 0 ].getFileType() );
	}

/**
Returns the file types openable by this app, creating them if this method has not been previously called.
These file types will be cached for future calls to this method.
*/

	public FileType[] getFileTypes( int maxToReturn ) {
		String		tempArray[] = new String[ 1 ];
		int			theErr;

		try {
		if ( !bAlreadyRequestedFileTypes ) {
			bAlreadyRequestedFileTypes = true;

			theErr = XUtilsOSX.getPOSIXPath( getVRef(), getParID(), getPName(), tempArray );
			if ( theErr != ErrCodes.ERROR_NONE )
				throw new IllegalArgumentException( "can't create HFS path " + theErr + ", " +
														getFile().getAbsolutePath() );

			fileTypes = CocoaUtilsOSX.getFileTypes( tempArray[ 0 ], creatorCode );
		}
		}
		catch ( Error er ) {
			Trace.println( "ERROR=" + er );
			er.printStackTrace( Trace.getOut() );
		}

		return fileTypes;
	}

/**
Search through our commands, and return a match if found.
*/

	public AppCommand getCommand( String commandName ) {
		int			i;
		
		for ( i = 0; i < kNumCommands; i++ ) {
			if ( commandName.equals( theCommands[ i ].asString() ) )
				return theCommands[ i ].redup();
		}

		return null;
	}

/**
Returns our three built-in commands.
*/
	
	public AppCommand[] getAllCommands() {
		AppCommand		retVal[];

		retVal = new AppCommand[ kNumCommands ];
		retVal[ 0 ] = new AppCommandMRJoapp();
		retVal[ 1 ] = new AppCommandMRJodoc();
		retVal[ 2 ] = new AppCommandMRJpdoc();

		return retVal;
	}

/**
If the command is one of our built-in types, launch the app using the command's arguments, if any.
If the app launched OK, create an AppProcessMRJ using the PSN of the process.
The  process will be added to our list of processes created from this app.
*/

	public AppProcess performCommand( AppCommand command, int flags ) {
		int				theErr, retPSN[];
		AppProcess		spawnee;
		String			args[];
		
		retPSN = new int[ AppUtilsMRJ.kPSNLen ];

		if ( command instanceof AppCommandMRJoapp ) {
			theErr = AppUtilsMRJ.launchApp( getVRef(), getParID(), getPName(), retPSN, flags );
		}
		else if ( command instanceof AppCommandMRJodoc ) {
			args = getArgsFromCommand( command );

			theErr = AppUtilsMRJ.launchWithDoc( AppUtilsMRJ.kLaunchWithDocOpenDoc,
												getVRef(), getParID(), getPName(),
												args,
												retPSN,
												flags );
		}
		else if ( command instanceof AppCommandMRJpdoc ) {
			args = getArgsFromCommand( command );

			theErr = AppUtilsMRJ.launchWithDoc( AppUtilsMRJ.kLaunchWithDocPrintDoc,
												getVRef(), getParID(), getPName(),
												args,
												retPSN,
												flags );
		}
		else
			throw new IllegalArgumentException( "AppCommand not recognized " + command );

		if ( theErr == ErrCodes.ERROR_NONE ) {
			spawnee = new AppProcessMRJ( this, retPSN, this );

			if ( spawnee != null )
				instances.addElement( spawnee );

			return spawnee;
		}
		else {
			Trace.println( "AppFileOSX.performCommand, theErr=" + theErr );
			return null;
		}
	}

/**
Return an array containing the processes created from this app.
Some of these processes may no longer be running.
*/

	public AppProcess[] getInstances() {
		AppProcess		tempArray[];
		int				i;

		if ( instances.size() < 1 )
			return null;

		tempArray = new AppProcess[ instances.size() ];
		for ( i = instances.size() - 1; i >= 0; i-- )
			tempArray[ i ] = (AppProcess) instances.elementAt( i );
			
		return tempArray;
	}
	
/**
Returns an array of Strings created from the arguments to 'command'
*/

	private String[] getArgsFromCommand( AppCommand command ) {
		String		retVal[];
		int			numArgs, i;
		
		numArgs = command.getNumArgs();

		if ( numArgs < 1 )
			return null;
		
		retVal = new String[ numArgs ];

		for ( i = 0; i < numArgs; i++ )
			retVal[ i ] = (String) command.getArg( i );
		
		return retVal;
	}
	
/**
Part of the AppCommandWatcher interface. Always returns false.
*/

	public boolean watchPre( Object target, AppCommand command, int flags ) {
		return false;
	}

/**
Part of the AppCommandWatcher interface. If the command is 'quit', removes the process from the list of processes.
*/

	public boolean watchPost( Object target, AppCommand command, int flags ) {
		if ( ( command instanceof AppCommandMRJquit ) && ( target instanceof AppProcessMRJ ) )
			instances.removeElement( target );

		return false;
	}
	
/**
Currently, always returns AF_PEF.
*/

	public int getExecutableType() {
		return AF_OSXPACKAGE;
	}

/**
Returns the cached size flags.
*/

	public int getSizeFlags() {
		return sizeFlags;
	}

/**
Returns the cached minimum partition.
*/

	public int getMinimumPartition() {
		return minimumPartition;
	}

/**
Returns the cached suggested partition.
*/

	public int getSuggestedPartition() {
		return preferredPartition;
	}

/**
Convenience method which converts the return value of getExecutableType() into a string representation.
*/

	public String executableTypeToString( int f ) {
		switch ( f ) {
			case AF_PEF:
				return "PEFExe";
			case AF_CFM68:
				return "CFM68Exe";
			case AF_68K:
				return "68kExe";
			case AF_OSXPACKAGE:
				return "OSXPackage";
			default:
				return "unknown type";
		}
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		VersionInfo			vers;
		File				tempFile;
		String				dateString;
		int					i;

		vers = getVersion();
		dateString = getDateString();
		tempFile = getFile();

		ps.println( indent + "for appl " + getName() + " with creator " + JUtils.intToAscii( creatorCode ) + ":" );

		if ( tempFile == null )
			ps.println( indent + "  type=" + executableTypeToString( getExecutableType() ) + ", disk file unknown" );
		else
			ps.println( indent + "  type=" + executableTypeToString( getExecutableType() ) + ", disk file=" + tempFile.getPath() );

		ps.println( indent + "  sizeFlags=" + Integer.toHexString( sizeFlags ) + ", minimumPartition=" + minimumPartition + ", preferredPartition=" + preferredPartition );

		if ( vers == null )
			ps.println( indent + "  version information not available" );
		else
			vers.dumpInfo( ps, indent + "  " );

		ps.println( indent + "  date=" + dateString );

		ps.println( indent + "  there are " + instances.size() + " spawns" );

		ps.println( indent + "  there are " + kNumCommands + " commands:" );
		for ( i = 0; i < kNumCommands; i++ )
			theCommands[ i ].dumpInfo( ps, indent + "    " );
	}
}

