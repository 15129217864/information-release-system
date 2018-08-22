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
import java.io.File;


import com.jconfig.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Vector;

/**
Represents a Windows app, as stored on disk, not a running process.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppFileMSVM extends DiskFileMSVM implements AppFile, AppCommandWatcher {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private AppFileCommandMgrMSVM	commandMgr;
	private Vector					instances;

/**
Construct from the full path of the application. Throws an exception if the file is not an executable.
An instance of 'AppFileCommandMgrMSVM' is created to manage this object's commands.
*/
	
	AppFileMSVM( String path ) throws FileNotFoundException, DiskFileException {
		super( new File( path ) );

		int						i;

		if ( ( getFlags() & AppFile.FILE_EXECUTABLE ) == 0 )	  {
			Trace.println( "file not exe: " + path );
			throw new DiskFileException( "file is not executable " + path );
		}

		instances = new Vector( 2, 2 );

		commandMgr = new AppFileCommandMgrMSVM( path, getName(), this, this );
	}

/**
Construct from the full path of the application and an array of RegCommandMSVM objects.
Throws an exception if the file is not an executable.
An instance of 'AppFileCommandMgrMSVM' is created to manage this object's commands.
*/
	
	AppFileMSVM( String path, RegCommandMSVM regCommands[] ) throws FileNotFoundException, DiskFileException {
		super( new File( path ) );

		int						i;

		if ( ( getFlags() & AppFile.FILE_EXECUTABLE ) == 0 )	  {
			Trace.println( "file not exe: " + path );
			throw new DiskFileException( "file is not executable " + path );
		}

		instances = new Vector( 2, 2 );

		commandMgr = new AppFileCommandMgrMSVM( path, getName(), regCommands, this, this );
	}

/**
Calls the AppFileCommandMgrMSVM object to perform the given command. If an AppProcess is created, it is added to
this object's list of running processes.
*/

	public AppProcess performCommand( AppCommand command, int flags ) {

		AppProcess		spawnee;


		spawnee = commandMgr.performCommand( command, flags );
		if ( spawnee != null )

			instances.addElement( spawnee );



		return spawnee;

	}

/**
Returns the IconBundle for this app.
*/

	public IconBundle getIconBundle( FileType ft ) {
		return new IconBundleFTMSVM( ft );
	}

/**
Calls through to the AppFileCommandMgrMSVM object.
*/

	public FileType[] getFileTypes( int maxToReturn ) {

		return commandMgr.getFileTypes( maxToReturn );
	}
	
/**
Calls through to the AppFileCommandMgrMSVM object.
*/

	public AppCommand getCommand( String commandName ) {

		return commandMgr.getCommand( commandName );
	}
	
/**
Calls through to the AppFileCommandMgrMSVM object.
*/

	public AppCommand[] getAllCommands() {

		return commandMgr.getAllCommands();
	}

/**
Returns an array of the AppProcesses created from this object, or null if none were created or if all that
were created have been terminated. Does not check that the processes are still running.
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
Part of the AppCommandWatcher interface. Always returns false.
*/

	public boolean watchPre( Object target, AppCommand command, int flags ) {
		return false;
	}

/**
Part of the AppCommandWatcher interface. If the command is 'quit', removes the process from the list of processes.
*/

	public boolean watchPost( Object target, AppCommand command, int flags ) {
		if ( ( "quit".equals( command.asString() ) ) && ( target instanceof AppProcessMSVM ) )
			instances.removeElement( target );

		return false;
	}
	
/**
Calls native code to retrieve the type of this application.
*/

	public int getExecutableType() {
		int		err, val[];

		val = new int[ 1 ];

		err = AppUtilsMSVM.getExecutableType( getFilePath(), val );
		if ( err != ErrCodes.ERROR_NONE )
			return AF_UNKNOWN;

		return val[ 0 ];
	}

/**
Mac-specific. Always returns 0.
*/

	public int getSizeFlags() {
		return 0;
	}

/**
Mac-specific. Always returns 0.
*/

	public int getMinimumPartition() {
		return 0;
	}

/**
Mac-specific. Always returns 0.
*/

	public int getSuggestedPartition() {
		return 0;
	}

/**
Convenience method which converts the return value of getExecutableType() into a string representation.
*/

	public String executableTypeToString( int f ) {
		switch ( f ) {
			case AF_W32:
				return "Win32Exe";
			case AF_DOS:
				return "DOSExe";
			case AF_WOW:
				return "WOWExe";
			case AF_PIF:
				return "PIFExe";
			case AF_POSIX:
				return "POSIXExe";
			case AF_OS216:
				return "OS216Exe";
			default:
				return "unknown type";
		}
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		File		tempFile;

		tempFile = getFile();

		ps.println( indent + "for appl " + getName() + ":" );

		if ( tempFile == null )
			ps.println( indent + "  type=" + executableTypeToString( getExecutableType() ) + ", disk file unknown" );
		else
			ps.println( indent + "  type=" + executableTypeToString( getExecutableType() ) + ", disk file=" + tempFile.getPath() );

		ps.println( indent + "  there are " + instances.size() + " spawns" );

		ps.println( indent + "  Commands:" );

		commandMgr.dumpInfo( ps, indent + "    " );
	}
}

