package com.tolstoy.testjc;

import com.jconfig.*;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
This is a sample disk browser application. Please note: this application is not meant to
represent good interface design or proper AWT usage, only to demonstrate the use of the file
methods of JConfig.

Feel free to modify this file as you wish.

@author JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class DiskBrowser {
	public DiskBrowser() {
		BrowserFrame		aFrame;

		aFrame = new BrowserFrame( "Disk Browser Test" );
//		aFrame.reshape( 100, 100, 500, 400 );
		aFrame.show();

		System.out.println( System.getProperty( "mrj.version" ) );
	}

	static void showProcesses( String processToQuit ) {
		AppProcess		procs[];
		long			startTime, endTime;
		
		try {
			startTime = System.currentTimeMillis();

			procs = FileRegistry.getProcesses( 100, 0 );

			endTime = System.currentTimeMillis();

			Trace.println( "FileRegistry.getProcesses took " + ( endTime - startTime ) + " milliseconds" );

			Trace.println( "there are " + procs.length + " processes" );

			for ( int i = 0; i < procs.length; i++ ) {
				procs[ i ].dumpInfo( Trace.getOut(), "" );
				procs[ i ].getAppFile().dumpInfo( Trace.getOut(), "" );
				Trace.println( "" );
				Trace.println( "" );

				if ( procs[ i ].getAppFile().getName().toUpperCase().indexOf( processToQuit ) >= 0 ) {
					tryQuitProcess( procs[ i ] );
				}

/*
				if ( procs[ i ].getAppFile().getName().toUpperCase().indexOf( "REALPLAY" ) >= 0 ) {
					AppFile af = procs[ i ].getAppFile();
					AppCommand ac = af.getCommand( "open" );
					ac.addArg( "c:\\myfile.ram" );
					AppProcess appProcess = af.performCommand( ac, 0 );

					ac = af.getCommand( "open" );
					ac.addArg( "c:\\myfile.sdp" );
					appProcess = af.performCommand( ac, 0 );
				}
*/
			}
		}
		catch ( Exception e ) {
			Trace.println( "can't showProcesses, e=" + e );
			e.printStackTrace( Trace.getOut() );
		}
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

		//	initialize JConfig, and then create the browser
	public static void main( String args[] ) {
		FileSystem			systems[];
		File				curDir;
		int					i;

		try {
			curDir = new File( System.getProperty( "user.dir" ) );

						//	output all trace messages to System.out
						//Trace.setDestination( Trace.TRACE_SYSOUT );
			Trace.setOut( new PrintStream( new FileOutputStream( new File( "results.txt" ) ) ) );
			Trace.setDestination( Trace.TRACE_FILE );

			FileRegistry.initialize( curDir, JUtils.asciiToInt( "fred" ) );
			if ( !FileRegistry.isInited() ) {
				Trace.println( "Please check your configuration." );
				return;
			}

			systems = FileRegistry.getFileSystems( 20, 0 );
			if ( systems == null )
				Trace.println( "can't get FileSystems" );
			else {
				Trace.println( "FileSystems:" );
				for ( i = 0; i < systems.length; i++ )
					systems[ i ].dumpInfo( Trace.getOut(), "  " );
			}

			if ( FileRegistry.getPlatformInfo().getPlatformType() == PlatformInfoI.MAC_PLATFORM )
				showProcesses( "TEXTEDIT.APP" );
			else
				showProcesses( "NOTEPAD" );

			DiskBrowser browser = new DiskBrowser();
		}
		catch ( Exception e ) {
			Trace.println( "problems: " + e );
			e.printStackTrace( Trace.getOut() );
		}
	}
}

/*
The browser frame contains 5 panels and a button. Each panel displays information about a file,
directory, alias, or volume. The button is used to go up a level.

If the variable currentContainer is non-null, it holds the directory that contains the five items
at the current level. If currentContainer is null, we're at the top level, and the mounted
volumes should be shown. 
*/

class BrowserFrame extends Frame {
	DiskObject			currentContainer;
	BrowserPanel		thePanels[];
	Button				upALevelButton;

	static final int		kNumPanels = 5;

	public BrowserFrame( String ttl ) {
		super( ttl );
		currentContainer = null;
		initializeDisplay();
	}

/*
Update the display. If we're at the top level, use the FileRegistry to get a list of the
mounted volumes.
*/

	public void refreshCurrentDisplay() {
		DiskVolume			mountedVolumes[];
		int					i;

		if ( currentContainer != null ) {

			Trace.println( "refreshing display with " + currentContainer );
			tryToSetAsContainer( currentContainer );
			return;
		}

		mountedVolumes = FileRegistry.getVolumes();
		if ( mountedVolumes == null ) {
			Trace.println( "can't get mounted volumes" );
			return;
		}

		Trace.println( "there are " + mountedVolumes.length + " volumes" );

		for ( i = 0; i < mountedVolumes.length && i < kNumPanels; i++ ) {
			thePanels[ i ].setDiskObject( mountedVolumes[ i ] );
			thePanels[ i ].repaint();
		}

		for ( ; i < kNumPanels; i++ ) {
			thePanels[ i ].setDiskObject( null );
			thePanels[ i ].repaint();
		}
	}

/*
Try to set the given object as the current container. First, we try to iterate on its contents; if
that fails ( e.g., if it's a file ), we do nothing. Otherwise, set the current container, and
initialize the panels with the contents of the current container.
*/

	void tryToSetAsContainer( DiskObject newContainer ) {
		GenDiskFilter		df;
		DiskObject			savedItems[];
		int					i, theErr, filterFlags;

		df = new GenDiskFilter( kNumPanels );

		filterFlags = DiskFilter.IGNORE_NAME_LOCKED | DiskFilter.IGNORE_HIDDEN;
		theErr = newContainer.iterate( df, filterFlags, 1000 );

		savedItems = df.getArray();

		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "iterated on " + newContainer + ", got err=0x" + Integer.toHexString( theErr ) );
			return;
		}

		if ( savedItems == null || savedItems.length < 1 ) {
			Trace.println( "iterated on " + newContainer + ", savedItems is null or empty" );
		}

		currentContainer = newContainer;
		Trace.println( "setting new container: name=" + currentContainer.getName() );

		for ( i = 0; i < savedItems.length; i++ ) {
			thePanels[ i ].setDiskObject( savedItems[ i ] );
			thePanels[ i ].repaint();
		}

		for ( ; i < kNumPanels; i++ ) {
			thePanels[ i ].setDiskObject( null );
			thePanels[ i ].repaint();
		}
	}

/*
The user clicked on the 'Up A Level' button. If we're at the top level, do nothing. Otherwise,
set the current container to the item which contains the current container.
*/

	void upALevelButtonHit() {
		if ( currentContainer == null )
			Trace.println( "already at top level" );
		else {
			try {
				currentContainer = currentContainer.getContainer();
			}
			catch ( Exception e ) {
				Trace.println( "can't get container for " + currentContainer.getName() + ":" + e );
			}
		}
		
		refreshCurrentDisplay();
	}

/*
One of the panels was clicked on. Try to set the object it holds as the current container.
*/

	void browserPanelHit( int which ) {
		DiskObject			prospectiveContainer;
		
		prospectiveContainer = thePanels[ which ].getDiskObject();
		if ( prospectiveContainer == null ) {
			Trace.println( "can't set null as container" );
			return;
		}
		
		tryToSetAsContainer( prospectiveContainer );
	}

/*
The following are mainly AWT-related methods.
*/

	void initializeDisplay() {
		int				i;

		setLayout( new GridLayout( 6, 1 ) );
		resize( 500, 400 );
//		reshape( 100, 100, 500, 400 );

		thePanels = new BrowserPanel[ kNumPanels ];		
		for ( i = 0; i < kNumPanels; i++ ) {
			thePanels[ i ] = new BrowserPanel( i );
			add( "Center", thePanels[ i ] );
		}

		upALevelButton = new Button( "Up A Level" );
		add( "Center", upALevelButton );
	}

	public void paint( Graphics g ) {
		refreshCurrentDisplay();
	}

	public boolean handleEvent( Event ev ) {
		int			i;

		if ( ev.id == Event.ACTION_EVENT ) {
			if ( ev.target == upALevelButton ) {
				upALevelButtonHit();
				return true;
			}
		}

		if ( ev.id == Event.MOUSE_UP ) {
			for ( i = 0; i < kNumPanels; i++ ) {
				if ( ev.target == thePanels[ i ] ) {
					browserPanelHit( i );
					return true;
				}
			}
		}

		if ( ev.id == Event.WINDOW_DESTROY ) {
			System.exit( 0 );
			return true;
		}

		return false;
	}
}

