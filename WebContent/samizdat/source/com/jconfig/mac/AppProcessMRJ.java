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

/**
Represents a Mac process.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppProcessMRJ implements AppProcess {
	private static final String		copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
The AppFile from which this process was created.
*/

	private AppFile					appFile;

/**
The commands this process can perform
*/

	private AppCommand				theCommands[];

/**
Watches commands sent to this process.
*/

	private AppCommandWatcher		watcher;

/**
The PSN of this process.
*/

	private int						ourPSN[];

/**
Are we still running?
*/

	private boolean					bIsRunning;

	private static final int		kNumCommands = 3;

/**
Create using the PSN of the process
The process is created with three commands: open app, open doc, and print doc.

@param af the AppFile corresponding to this process
@param psn the PSN, the low int of the PSN at 'AppUtilsMRJ.kPSNLoOffset', and the high int at 'AppUtilsMRJ.kPSNHiOffset'
@param acw an object which will be called when messages are sent to this process.
*/

	AppProcessMRJ( AppFile af, int psn[], AppCommandWatcher acw ) {
		bIsRunning = true;

		ourPSN = new int[ AppUtilsMRJ.kPSNLen ];
		ourPSN[ AppUtilsMRJ.kPSNLoOffset ] = psn[ AppUtilsMRJ.kPSNLoOffset ];
		ourPSN[ AppUtilsMRJ.kPSNHiOffset ] = psn[ AppUtilsMRJ.kPSNHiOffset ];

		appFile = af;
		watcher = acw;

		theCommands = new AppCommand[ kNumCommands ];
		theCommands[ 0 ] = new AppCommandMRJodoc();
		theCommands[ 1 ] = new AppCommandMRJpdoc();
		theCommands[ 2 ] = new AppCommandMRJquit();
	}

/**
Returns the AppFile from which this process was created.
*/

	public AppFile getAppFile() {
		return appFile;
	}

/**
Search through our cammands, and return a match if found.
*/

	public AppCommand getCommand( String commandName ) {
		int			i;
		
		for ( i = 0; i < kNumCommands; i++ ) {
			if ( commandName.equals( theCommands[ i ].asString() ) )
				return theCommands[ i ];
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

	public int performCommand( AppCommand command, int flags ) {
		String		args[];
		int			which, theErr = -1;
		
		if ( command instanceof AppCommandMRJquit )
			which = 1;
		else if ( command instanceof AppCommandMRJodoc )
			which = 2;
		else if ( command instanceof AppCommandMRJpdoc )
			which = 3;
		else
			throw new IllegalArgumentException( "AppCommand not recognized: " + command );
		
		if ( watcher != null && watcher.watchPre( this, command, flags ) )
			return 0;

		if ( which == 1 ) {
			theErr = AppUtilsMRJ.quitApp( ourPSN, flags );
		}
		else if ( which == 2 ) {
			args = getArgsFromCommand( command );

			theErr = AppUtilsMRJ.sendAppDocs( AppUtilsMRJ.kSendAppDocsOpenDoc,
												ourPSN, args, flags );
		}
		else if ( which == 3 ) {
			args = getArgsFromCommand( command );

			theErr = AppUtilsMRJ.sendAppDocs( AppUtilsMRJ.kSendAppDocsPrintDoc,
												ourPSN, args, flags );
		}

		if ( watcher != null )
			watcher.watchPost( this, command, flags );
		
		return theErr;
	}

/**
Used to minimize, maximize this process, or send it in front of or behind other processes, if possible.
@param fromProcess reserved; set to null
@param selector one of the values defined in AppProcess.java: APP_MOVE_TOFRONT, APP_MOVE_TOBACK, APP_MOVE_MINIMIZE, APP_MOVE_MAXIMIZE
@param flags reserved; set to 0
*/

	public int move( AppProcess fromProcess, int selector, int flags ) {
		if ( selector == APP_MOVE_TOFRONT )
			return AppUtilsMRJ.moveApp( ourPSN, selector, flags );
		else if ( selector == APP_MOVE_TOBACK )
			return AppUtilsMRJ.moveApp( ourPSN, selector, flags );
		else if ( selector == APP_MOVE_MINIMIZE || selector == APP_MOVE_MAXIMIZE )
			return 0;	//	nothing right now
		else
			throw new IllegalArgumentException( "bad selector=" + selector );
	}

/**
When this object is created, bIsRunning is set to true.
This method checks whether this process is still running, sets bIsRunning appropriately, and returns bIsRunning.
If bIsRunning was previously set to false, we just return false: i.e., once the process stops running, we
assume it can't be restarted.
*/

	public boolean isRunning() {
		int			theErr;
		
		if ( !bIsRunning )
			return false;

		theErr = AppUtilsMRJ.verifyPSN( ourPSN );
		
		if ( theErr == ErrCodes.ERROR_NONE )
			return true;
		else {
			bIsRunning = false;
			return false;
		}
	}

/**
Returns the PSN of this process,
the low int of the PSN at 'AppUtilsMRJ.kPSNLoOffset', and the high int at 'AppUtilsMRJ.kPSNHiOffset'
of the returned array.
*/

	public int[] getPlatformData() {
		int		tempArray[];
		
		tempArray = new int[ AppUtilsMRJ.kPSNLen ];
		
		tempArray[ AppUtilsMRJ.kPSNLoOffset ] = ourPSN[ AppUtilsMRJ.kPSNLoOffset ];
		tempArray[ AppUtilsMRJ.kPSNHiOffset ] = ourPSN[ AppUtilsMRJ.kPSNHiOffset ];
		
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

	public void dumpInfo( PrintStream ps, String indent ) {
		int			i;

		ps.println( indent + "for appProcess " + appFile.getName() + ":" );
		ps.println( indent + "  psn=<" + ourPSN[ AppUtilsMRJ.kPSNLoOffset ] + "," + ourPSN[ AppUtilsMRJ.kPSNHiOffset ] + ">" );
		ps.println( indent + "there are " + kNumCommands + " commands:" );
		for ( i = 0; i < kNumCommands; i++ )
			theCommands[ i ].dumpInfo( ps, indent + "  " );
	}
}
		
