/*
	Tester.java is the source to the test program. The compiled version of this file
	is included in JConfig.zip.

	Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

package com.tolstoy.testjc;

import com.jconfig.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Random;

/**
This is the main testing class.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class Tester {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private static final int		MY_SIGNATURE = JUtils.asciiToInt( "fred" );
	private static boolean			bMac = false;

	public static void main( String args[] ) {
		FinderInfo			finfo;
		FileExtension		txtExtension;
		AppFile				simpleAppFile = null;
		AppProcess			simpleAppProcess = null;
		File				curDir;
		int					platformType;
		boolean				bIsMacOSX = false;

		try {
				//	see osxinfo.html for an explanation of the following statement
			if ( System.getProperty( "os.name" ).toLowerCase().indexOf( "mac os x" ) >= 0 )
				bIsMacOSX = true;

			curDir = new File( System.getProperty( "user.dir" ) );

				//	set the file to which we'll write tracing output
			Trace.setOut( new PrintStream( new FileOutputStream( new File( curDir, "results.txt" ) ) ) );
			Trace.setDestination( Trace.TRACE_FILE );

			doubleCheckJConfigInitializationDirectory( curDir );

			FileRegistry.initialize( curDir, MY_SIGNATURE );
			if ( !FileRegistry.isInited() ) {
				Trace.println( "Please check your configuration." );
				return;
			}

			platformType = FileRegistry.getPlatformInfo().getPlatformType();
			if ( platformType == PlatformInfoI.MAC_PLATFORM )
				bMac = true;
			else
				bMac = false;

			if ( bMac && !bIsMacOSX ) {
					//	output all trace messages to System.out
				Trace.setDestination( Trace.TRACE_SYSOUT );
			}

			FileRegistry.getPlatformInfo().dumpInfo( Trace.getOut(), "" );

			if ( bIsMacOSX )
				finfo = new FinderInfo( JUtils.asciiToInt( "ttxt" ), JUtils.asciiToInt( "TEXT" ) );
			else
				finfo = new FinderInfo( JUtils.asciiToInt( "ttxt" ), JUtils.asciiToInt( "TEXT" ) );

			txtExtension = new FileExtension( ".txt" );

			tryFindAppByExtension( txtExtension );

			tryFindAppByFinderInfoMultiple( new FinderInfo( JUtils.asciiToInt( "stxt" ), JUtils.asciiToInt( "TEXT" ) ), 0 );
			tryFindAppByFinderInfoMultiple( new FinderInfo( JUtils.asciiToInt( "ttxt" ), JUtils.asciiToInt( "TEXT" ) ), 0 );
			tryFindAppByFinderInfoMultiple( new FinderInfo( JUtils.asciiToInt( "text" ), JUtils.asciiToInt( "TEXT" ) ), 0 );
			tryFindAppByFinderInfoMultiple( new FinderInfo( JUtils.asciiToInt( "8BIM" ), JUtils.asciiToInt( "JPEG" ) ), 0 );
			AppFile af2 = tryFindAppByFinderInfoMultiple( new FinderInfo( JUtils.asciiToInt( "MSIE" ), JUtils.asciiToInt( "JPEG" ) ), 0 );

			tryOpenApp( af2 );

			simpleAppFile = tryFindAppByFinderInfo( finfo );

			if ( simpleAppFile != null ) {
				tryGetFileTypes( simpleAppFile );

				if ( bMac )
					simpleAppProcess = tryOpenApp( simpleAppFile );
				else
					simpleAppProcess = tryOpenAppWithDoc( simpleAppFile );

				tryDisplayApplicationIcon( simpleAppFile, IconBundle.ICON_LARGE );
			}

			tryShowingMonitors();
			tryIteratingOnConfigEntries();
			tryFindFinderInfo( txtExtension );
			tryFindExtensions( finfo );
			tryFindAppsByName( bMac ? "ed" : "NOTEPAD" );
			tryFindAppsByName( "Tester.app" );
			tryFindAppsByName( "TextEdit" );
			tryFindAppsByName( "textedit.app" );
			tryFindAppsByName( "TextEdit.app" );

			if ( simpleAppProcess != null ) {
				if ( bMac ) {
					tryOpenFile( simpleAppProcess );
					tryMoveAppToFront( simpleAppProcess );
				}
				else {
					tryMaximizeApp( simpleAppProcess );
					tryQuitProcess( simpleAppProcess );
				}
			}
			
			tryShowAllProcesses();

			//tryLaunchingURL( "http://www.tolstoy.com" );
		}
		catch ( Exception e ) {
			Trace.println( "problems: " + e );
			e.printStackTrace( Trace.getOut() );
		}

		Trace.println( "" );
		Trace.println( "*** Ciao." );
	}

/*
If 'jcfactrz.txt' or 'jconfig.cfg' aren't in the given directory, prints out an error message.
*/

	private static void doubleCheckJConfigInitializationDirectory( File rootDir ) {
		File		tempFile;

		Trace.println( "checking " + rootDir.getPath() + " for jcfactrz.txt and jconfig.cfg..." );

		try {
			tempFile = new File( rootDir, "jcfactrz.txt" );
			if ( !tempFile.exists() ) {
				Trace.println( "    jcfactrz.txt doesn't exist!" );
				return;
			}
		}
		catch ( Exception e ) {
			Trace.println( "    checking for jcfactrz.txt, got " + e );
		}

		try {
			tempFile = new File( rootDir, "jconfig.cfg" );
			if ( !tempFile.exists() ) {
				Trace.println( "    jconfig.cfg doesn't exist!" );
				return;
			}
		}
		catch ( Exception e ) {
			Trace.println( "    checking for jconfig.cfg, got " + e );
		}

		Trace.println( "    they're there" );
	}

/*
Try to launch the user's browser. 'url' must be a full url, i.e., complete with 'http', or 'mailto',
etc.
Presently, this method might fail on some platforms if the user has not used Internet Config
to set their default browser.
*/

	private static int tryLaunchingURL( String url ) {
		int			theErr;

		Trace.println( "" );
		Trace.println( "*** Launching a URL: " + url );

		theErr = FileRegistry.launchURL( url, 0, null );
		
		if ( theErr != ErrCodes.ERROR_NONE )
			Trace.println( "can't launch browser, theErr=" + theErr );
		
		return theErr;
	}

/*
Using the FileRegistry, try to find an application corresponding to the given FinderInfo. To make things
interesting, we randomly choose both whether we look for just one app or several, and whether we do
a comprehensive disk search for the app or not.
*/

	private static AppFile tryFindAppByFinderInfo( FinderInfo finfo ) {
		int			randomNumber, searchFlags;

		randomNumber = ( new Random() ).nextInt();

		if ( ( randomNumber & 1 ) == 0 )
			searchFlags = 0;
		else
			searchFlags = FileRegistry.GETAPPS_SEARCH1;

		try {
			if ( ( randomNumber & 2 ) == 0 )
				return tryFindAppByFinderInfoSingle( finfo, searchFlags );
			else
				return tryFindAppByFinderInfoMultiple( finfo, searchFlags );
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryFindAppByFinderInfo: " + e );
			e.printStackTrace( Trace.getOut() );
			return null;
		}
	}

	private static AppFile tryFindAppByFinderInfoSingle( FinderInfo finfo, int searchFlags ) {
		AppFile				theApps[];

		Trace.println( "" );
		Trace.println( "*** Calling FileRegistry.getApps( finfo, 1 ) for finfo=" + finfo +
								"searchFlags=" + Integer.toHexString( searchFlags ) );

		theApps = FileRegistry.getApps( finfo, 1, searchFlags );
		if ( theApps == null || theApps[ 0 ] == null ) {
			Trace.println( "can't find apps using " + finfo );
			return null;
		}

		theApps[ 0 ].dumpInfo( Trace.getOut(), "  " );

		return theApps[ 0 ];
	}

	private static AppFile tryFindAppByFinderInfoMultiple( FinderInfo finfo, int searchFlags ) {
		AppFile				theApps[];
		int					len, i;

		Trace.println( "" );
		Trace.println( "*** Calling FileRegistry.getApps( finfo, 10 ) for finfo=" + finfo +
								"searchFlags=" + Integer.toHexString( searchFlags ) );

		theApps = FileRegistry.getApps( finfo, 10, searchFlags );
		if ( theApps == null ) {
			Trace.println( "can't find apps using " + finfo );
			return null;
		}

		len = theApps.length;
		Trace.println( " asked for 10 apps, got " + len + " apps" );

		for ( i = 0; i < len; i++ )
			theApps[ i ].dumpInfo( Trace.getOut(), "  " );
		
		return theApps[ 0 ];
	}

/*
Using the FileRegistry, try to find an application corresponding to the given FileExtension.
*/

	private static AppFile tryFindAppByExtension( FileExtension ext ) {
		AppFile				theApps[];

		Trace.println( "" );
		Trace.println( "*** Calling FileRegistry.getApps for " + ext );

		try {
			theApps = FileRegistry.getApps( ext, 1, 0 );
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryFindAppByExtension: " + e );
			e.printStackTrace( Trace.getOut() );
			return null;
		}

		if ( theApps == null || theApps[ 0 ] == null ) {
			Trace.println( "can't find apps using " + ext );
			return null;
		}

		theApps[ 0 ].dumpInfo( Trace.getOut(), "  " );
		
		return theApps[ 0 ];
	}

/*
Try to print out the file types associated with the given application.
*/

	private static void tryGetFileTypes( AppFile appFile ) {
		FileType			fileTypes[];
		int					i;

		Trace.println( "" );
		Trace.println( "*** Getting the FileTypes of an application..." );

		try {
			fileTypes = appFile.getFileTypes( 10 );
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryGetFileTypes: " + e );
			e.printStackTrace( Trace.getOut() );
			return;
		}

		if ( fileTypes == null ) {
			Trace.println( "application has no file types" );
			return;
		}

		Trace.println( "application has " + fileTypes.length + " file types:" );

		for ( i = 0; i < fileTypes.length; i++ )
			Trace.println( "  " + fileTypes[ i ] );
	}

/*
Try to create an instance of the given application. Returns the resulting AppProcess, or null
if an error occurs.
*/

	private static AppProcess tryOpenApp( AppFile appFile ) {
		AppCommand			theCommand;
		AppProcess			theProcess;

		Trace.println( "" );
		Trace.println( "*** Calling performCommand to open an application..." );

		theCommand = appFile.getCommand( AppCommand.kAppCommandOpenApp );
		if ( theCommand == null ) {
			theCommand = appFile.getCommand( AppCommand.kAppCommandOpenDoc );
			if ( theCommand == null ) {
				Trace.println( "can't get kAppCommandOpenApp or kAppCommandOpenDoc from process" );
				return null;
			}
		}

		Trace.println( "theCommand = " + theCommand );

		try {
			theProcess = appFile.performCommand( theCommand, AppFile.AF_NO_LAYER_SWITCH );
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryOpenApp: " + e );
			e.printStackTrace( Trace.getOut() );
			return null;
		}

		if ( theProcess == null )
			Trace.println( "couldn't create process" );
		else {
			theProcess.dumpInfo( Trace.getOut(), "" );
			Trace.println( "The AppFile now should have one spawn:" );
			appFile.dumpInfo( Trace.getOut(), "  " );
		}

		return theProcess;
	}

/*
Try to create an instance of the given application.

After using a FileDialog to allow the user to open a file, the full path name
of that file is added as an argument to the open doc command, and then the
performCommand() method of the AppFile is called to execute the command.

Returns the resulting AppProcess, or null if an error occurs.
*/

	private static AppProcess tryOpenAppWithDoc( AppFile appFile ) {
		AppCommand			theCommand;
		AppProcess			theProcess;
		String				fileName;

		Trace.println( "" );
		Trace.println( "*** Calling performCommand to open an app..." );

		theCommand = appFile.getCommand( AppCommand.kAppCommandOpenDoc );
		if ( theCommand == null ) {
			Trace.println( "can't get kAppCommandOpenDoc from process" );
			return null;
		}

		Trace.println( "theCommand = " + theCommand );

		fileName = JUtils.getFileNameFromUser();
		if ( fileName == null ) {
			Trace.println( "-- cancelled --" );
			return null;
		}

		theCommand.addArg( fileName );

		try {
			theProcess = appFile.performCommand( theCommand, AppFile.AF_NO_LAYER_SWITCH );
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryOpenAppWithDoc: " + e );
			e.printStackTrace( Trace.getOut() );
			return null;
		}

		if ( theProcess == null )
			Trace.println( "couldn't create process" );
		else {
			theProcess.dumpInfo( Trace.getOut(), "" );
			Trace.println( "The AppFile now should have one spawn:" );
			appFile.dumpInfo( Trace.getOut(), "  " );
		}

		return theProcess;
	}

/*
Quit the given process.
*/

	private static void tryQuitProcess( AppProcess appProcess ) {
		AppCommand		theCommand;
		int				theErr;

		Trace.println( "" );
		Trace.println( "*** Calling performCommand to quit a process..." );

		if ( !appProcess.isRunning() ) {
			Trace.println( "process is not running" );
			return;
		}

		theCommand = appProcess.getCommand( AppCommand.kAppCommandQuit );
		if ( theCommand == null ) {
			Trace.println( "can't get kAppCommandQuit from process" );
			return;
		}

		Trace.println( "theCommand = " + theCommand );

		try {
			theErr = appProcess.performCommand( theCommand, 0 );
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryQuitProcess: " + e );
			e.printStackTrace( Trace.getOut() );
			return;
		}

		Trace.println( "theErr=" + theErr );
	}

/*
Display every 20th entry in the file mappings database.
*/

	private static void tryIteratingOnConfigEntries() {
		Trace.println( "" );
		Trace.println( "*** Iterating on ConfigEntry's..." );

		FileRegistry.iterate( new BasicCEVisitor( Trace.getOut() ) );
	}

/*
Try to find FinderInfo objects corresponding to the given FileExtension.
*/

	private static void tryFindFinderInfo( FileExtension ext ) {
		FinderInfo		infoArray[];
		int				i, numWritten;

		Trace.println( "" );
		Trace.println( "*** Finding FinderInfo's associated with extension: " + ext );

		try {
			infoArray = FileRegistry.findFinderInfo( ext, 10 );
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryFindFinderInfo: " + e );
			e.printStackTrace( Trace.getOut() );
			return;
		}

		if ( infoArray == null ) {
			Trace.println( "can't find finder info for " + ext );
			return;
		}

		for ( i = 0, numWritten = 0; i < infoArray.length; i++ ) {
			if ( infoArray[ i ] != null ) {
				Trace.println( "at " + i + " is " + infoArray[ i ] );
				++numWritten;
			}
		}
		
		if ( numWritten == 0 )
			Trace.println( "can't find finder info for " + ext );
	}

/*
Try to find FileExtension objects corresponding to the given FinderInfo.
*/

	private static void tryFindExtensions( FinderInfo finfo ) {
		FileExtension		extArray[];
		int					i, numWritten;

		Trace.println( "" );
		Trace.println( "*** Finding extensions associated with " + finfo );

		try {
			extArray = FileRegistry.findExtensions( finfo, 10 );
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryFindExtensions: " + e );
			e.printStackTrace( Trace.getOut() );
			return;
		}

		if ( extArray == null ) {
			Trace.println( "can't find extensions for " + finfo );
			return;
		}

		for ( i = 0, numWritten = 0; i < extArray.length; i++ ) {
			if ( extArray[ i ] != null ) {
				Trace.println( "at " + i + " is " + extArray[ i ] );
				++numWritten;
			}
		}
		
		if ( numWritten == 0 )
			Trace.println( "can't find extensions for " + finfo );
	}

/*
Try to tell a currently running process to open a file. After using a FileDialog to allow the
user to open a file, the full path name of that file is added as an argument to the open doc
command, and then the performCommand() method of the process is called to execute the command.
*/

	private static void tryOpenFile( AppProcess appProcess ) {
		AppCommand		theCommand;
		File			selectedFile;
		String			fileName;
		int				theErr;

		Trace.println( "" );
		Trace.println( "*** Telling process to open a file..." );

		if ( !appProcess.isRunning() ) {
			Trace.println( "process is not running" );
			return;
		}

		theCommand = appProcess.getCommand( AppCommand.kAppCommandOpenDoc );
		if ( theCommand == null ) {
			Trace.println( "can't get kAppCommandOpenDoc from appProcess" );
			return;
		}

		Trace.println( "theCommand = " + theCommand );

		fileName = JUtils.getFileNameFromUser();
		if ( fileName == null ) {
			Trace.println( "-- cancelled --" );
			return;
		}

		try {
			selectedFile = new File( fileName );
		}
		catch ( Exception e ) {
			Trace.println( "-- file problems --" + e );
			return;
		}

		theCommand.addArg( selectedFile );

		Trace.println( "opening " + fileName );

		try {
			theErr = appProcess.performCommand( theCommand, 0 );
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryOpenFile: " + e );
			e.printStackTrace( Trace.getOut() );
			return;
		}

		Trace.println( "the error is " + theErr );
	}

/*
Try to maximize the given process.
*/

	private static void tryMaximizeApp( AppProcess appProcess ) {
		int			theErr;
		
		Trace.println( "" );
		Trace.println( "*** Maximizing application..." );

		if ( !appProcess.isRunning() ) {
			Trace.println( "process is not running" );
			return;
		}

		try {
			theErr = appProcess.move( null, AppProcess.APP_MOVE_MAXIMIZE, 0 );
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryMaximizeApp: " + e );
			e.printStackTrace( Trace.getOut() );
			return;
		}
		
		if ( theErr != ErrCodes.ERROR_NONE )
			Trace.println( "theErr=" + theErr );
	}

/*
Try to find the applications whose name contains the given string.
*/

	private static void tryFindAppsByName( String name ) {
		AppFile			theApps[];
		int				i, numWritten;

		Trace.println( "" );
		Trace.println( "*** Finding apps whose name contains " + name );

		try {
			theApps = FileRegistry.getApps( name, 10, 0 );
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryFindAppsByName: " + e );
			e.printStackTrace( Trace.getOut() );
			return;
		}

		if ( theApps == null ) {
			Trace.println( "can't find apps containing *" + name + "*" );
			return;
		}

		for ( i = 0, numWritten = 0; i < theApps.length; i++ ) {
			if ( theApps[ i ] != null ) {
				theApps[ i ].dumpInfo( Trace.getOut(), "  " );
				++numWritten;
			}
		}
		
		if ( numWritten == 0 )
			Trace.println( "can't find apps containing *" + name + "*" );
	}

/*
Try to move the given process to the front.
*/

	private static void tryMoveAppToFront( AppProcess appProcess ) {
		int			theErr;
		
		Trace.println( "" );
		Trace.println( "*** Moving process to front..." );

		try {
			theErr = appProcess.move( null, AppProcess.APP_MOVE_TOBACK, 0 );
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryMoveAppToFront: " + e );
			e.printStackTrace( Trace.getOut() );
			return;
		}
		
		if ( theErr != ErrCodes.ERROR_NONE )
			Trace.println( "theErr=" + theErr );
	}

/*
Show information about the user's video monitors.
*/

	private static void tryShowingMonitors() {
		Monitor		mainMonitor, theMonitors[];
		int			i, len;

		Trace.println( "" );
		Trace.println( "*** Displaying monitor information:" );

		try {
			mainMonitor = FileRegistry.getMainMonitor();
			if ( mainMonitor == null )
				Trace.println( "  FileRegistry.getMainMonitor() returned null" );
			else {
				Trace.println( "  main monitor is:" );
				mainMonitor.dumpInfo( Trace.getOut(), "    " );
			}

			theMonitors = FileRegistry.getMonitors();
			if ( theMonitors == null )
				Trace.println( "  FileRegistry.getMonitors() returned null" );
			else {
				len = theMonitors.length;
				Trace.println( "  there are " + len + " monitors" );
				for ( i = 0; i < len; i++ )
					theMonitors[ i ].dumpInfo( Trace.getOut(), "    " );
			}
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryShowingMonitors: " + e );
			e.printStackTrace( Trace.getOut() );
		}
	}

/*
Show all the currently running processes.
*/

	private static void tryShowAllProcesses() {
		AppProcess		processes[];
		int				i, len;

		Trace.println( "" );
		Trace.println( "*** Showing current processes:" );

		try {
			processes = FileRegistry.getProcesses( 50, 0 );
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryShowAllProcesses: " + e );
			e.printStackTrace( Trace.getOut() );
			return;
		}

		if ( processes == null ) {
			Trace.println( "  FileRegistry.getProcesses() returned null" );
			return;
		}

		len = processes.length;

		Trace.println( "  there are " + len + " currently running processes" );

		for ( i = 0; i < len; i++ )
			processes[ i ].dumpInfo( Trace.getOut(), "    " );
	}

/*
Try to display an application icon for the given file.
*/

	private static void tryDisplayApplicationIcon( AppFile appFile, int whichIcon ) {
		IconBundle				iconBundle;
		IconDisplayPanel		idp;
		FileType				fileType;
		int						bits[], numBits, theErr;
		IconDisplayFrame		aFrame;

		Trace.println( "" );
		Trace.println( "*** Displaying icon for application..." );

		if ( bMac )
			fileType = new FileType( new FinderInfo( 0, JUtils.asciiToInt( "APPL" ) ) );
		else
			fileType = new FileType( new FileExtension( ".exe" ) );

		try {
			iconBundle = appFile.getIconBundle( fileType );
		}
		catch ( Exception e ) {
			Trace.println( "problems in tryDisplayApplicationIcon: " + e );
			e.printStackTrace( Trace.getOut() );
			return;
		}

		if ( iconBundle == null ) {
			Trace.println( "app has no IconBundle" );
			return;
		}

		numBits = iconBundle.getIconWidth( whichIcon ) * iconBundle.getIconHeight( whichIcon );
		bits = new int[ numBits ];

		theErr = iconBundle.getIcon( whichIcon, 0, 0, bits );
		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "iconBundle.getIcon returned null" );
			return;
		}

		idp = new IconDisplayPanel( bits, iconBundle.getIconWidth( whichIcon ),
											iconBundle.getIconHeight( whichIcon ) );

		aFrame = new IconDisplayFrame( "Icon Test" );
		aFrame.setLayout( new BorderLayout( 0, 0 ) );
		aFrame.resize( 100, 100 );
		aFrame.add( "Center", idp );
		aFrame.show();
	}
}

