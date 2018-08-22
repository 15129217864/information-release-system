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
import java.io.PrintStream;

/**
Represents a running process.

<P>
This object is created using a set of 12 ints: the HWND of the process and the information returned from
the WinAPI CreateProcess() routine, as well as the contents of the PROCESSENTRY32 struct as applicable.

<P>
This set of 12 ints corresponds to the AppDataType struct, which is defined in AppData.h in the Windows native code:
<PRE>
	typedef struct tagAppDataType {
		HWND					hwnd;
		PROCESS_INFORMATION		pi;
		DWORD					pecntUsage;
		DWORD					peth32DefaultHeapID;
		DWORD					peth32ModuleID;
		DWORD					pecntThreads;
		DWORD					peth32ParentProcessID;
		LONG					pepcPriClassBase;
		DWORD					pedwFlags;
	} AppDataType;
</PRE>

<P>
Java-side, this is referred to as 'AppData'.

<P>
In the WinAPI, PROCESS_INFORMATION is defined as:

<PRE>
typedef struct _PROCESS_INFORMATION { // pi 
    HANDLE hProcess; 
    HANDLE hThread; 
    DWORD dwProcessId; 
    DWORD dwThreadId; 
} PROCESS_INFORMATION; 
</PRE>
 

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppProcessMSVM implements AppProcess {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private AppFileMSVM			appFile;
	private AppCommand			theCommands[];
	private AppCommandWatcher	watcher;
	private int					appData[], numCommands;
	private boolean				bIsRunning;

	static final int			kSizeofAppData = 12;
	private static final int	kNumCommands = 1;

/**
Construct using information on a running process.
@param af the AppFileMSVM from which the process was created
@param data the process' AppData
@param acw an object which will be called when messages are sent to this process.
*/

	AppProcessMSVM( AppFileMSVM af, int data[], AppCommandWatcher acw ) {

		RegCommandMSVM		tempVat[];
		int					i;

		bIsRunning = true;

		appData = new int[ kSizeofAppData ];

		for ( i = 0; i < kSizeofAppData; i++ )
			appData[ i ] = data[ i ];

		appFile = af;
		watcher = acw;

		numCommands = kNumCommands;
		theCommands = new AppCommand[ numCommands ];

		tempVat = new RegCommandMSVM[ 1 ];

		tempVat[ 0 ] = new RegCommandMSVM( "", "", "quit", "" );
		theCommands[ 0 ] = new AppCommandMSVM( tempVat );
	}

/**
Return the AppFile associated with this process.
*/

	public AppFile getAppFile() {
		return appFile;
	}

/**
Searches the list of command for one with the given name, and returns it. If one couldn't be found, returns null.
*/

	public AppCommand getCommand( String commandName ) {
		int			i;
		
		for ( i = 0; i < numCommands; i++ ) {
			if ( commandName.equals( theCommands[ i ].asString() ) )
				return theCommands[ i ];
		}

		return null;
	}

/**
Return an array of all the commands.
*/

	public AppCommand[] getAllCommands() {
		AppCommand		retVal[];
		int				i;

		retVal = new AppCommand[ numCommands ];

		for ( i = 0; i < numCommands; i++ )
			retVal[ i ] = theCommands[ i ].redup();

		return retVal;
	}

/**
Performs the given command. Presently, this must be a 'quit' command, no others are supported.
*/

	public int performCommand( AppCommand command, int flags ) {
		String		args[];
		int			which, theErr = -1;
		
		if ( !"quit".equals( command.asString() ) )
			throw new IllegalArgumentException( "AppCommand not recognized: " + command );
		
		if ( watcher != null && watcher.watchPre( this, command, flags ) )
			return 0;

		theErr = AppUtilsMSVM.quitApp( appData, flags );

		if ( watcher != null )
			watcher.watchPost( this, command, flags );
		
		return theErr;
	}

/**
Move this process.
*/

	public int move( AppProcess fromProcess, int selector, int flags ) {
		if ( selector == APP_MOVE_TOFRONT || selector == APP_MOVE_TOBACK || selector == APP_MOVE_MINIMIZE || selector == APP_MOVE_MAXIMIZE )
			return AppUtilsMSVM.moveApp( appData, selector, flags );
		else
			throw new IllegalArgumentException( "bad selector=" + selector );	//	return ErrCodes.ERROR_PARAM;
	}

/**
Indicates whether this process is still running.
*/

	public boolean isRunning() {
		int			theErr;
		
		if ( !bIsRunning )
			return false;

		theErr = AppUtilsMSVM.verifyNativeAppData( appData );

		if ( theErr == ErrCodes.ERROR_NONE )
			return true;
		else {
			bIsRunning = false;
			return false;
		}
	}

/**
Returns the AppData for this process. See above for a description of AppData.
*/

	public int[] getPlatformData() {
		int		tempArray[], i;
		
		tempArray = new int[ kSizeofAppData ];

		for ( i = 0; i < kSizeofAppData; i++ )
			tempArray[ i ] = appData[ i ];

		return tempArray;
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		int			i;

		ps.println( indent + "for appProcess " + appFile.getName() + ":" );
		ps.println( indent + "  hwnd=" + appData[ 0 ] + ", processID=" + appData[ 3 ] + ", threadID=" + appData[ 4 ] );
		ps.println( indent + "  there are " + kNumCommands + " commands:" );
		for ( i = 0; i < numCommands; i++ )
			theCommands[ i ].dumpInfo( ps, indent + "    " );
	}
}

