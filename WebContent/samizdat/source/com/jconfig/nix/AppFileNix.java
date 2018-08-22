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


import com.jconfig.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Vector;

/**
This is a preliminary class derived from the Windows class of similar name. This class may be replaced
or modified in a future version.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppFileNix extends DiskFileNix implements AppFile, AppCommandWatcher {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private AppFileCommandMgrNix	commandMgr;
	private Vector					instances;
	
	AppFileNix( String path ) throws FileNotFoundException, DiskFileException {
		super( new File( path ) );

		int						i;

		if ( ( getFlags() & AppFile.FILE_EXECUTABLE ) == 0 )	  {
			Trace.println( "file not exe: " + path );
			throw new DiskFileException( "file is not executable " + path );
		}

		instances = new Vector( 2, 2 );

		commandMgr = new AppFileCommandMgrNix( path, getName(), this, this );
	}

	public AppProcess performCommand( AppCommand command, int flags ) {
		AppProcess		spawnee;

		spawnee = commandMgr.performCommand( command, flags );
		if ( spawnee != null )
			instances.addElement( spawnee );

		return spawnee;
	}

	public IconBundle getIconBundle( FileType ft ) {
		return new IconBundleFTNix( ft );
	}

	public FileType[] getFileTypes( int maxToReturn ) {
		return commandMgr.getFileTypes( maxToReturn );
	}
	
	public AppCommand getCommand( String commandName ) {
		return commandMgr.getCommand( commandName );
	}
	
	public AppCommand[] getAllCommands() {
		return commandMgr.getAllCommands();
	}

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
	
	public boolean watchPre( Object target, AppCommand command, int flags ) {
		return false;
	}

	public boolean watchPost( Object target, AppCommand command, int flags ) {
		if ( ( "quit".equals( command.asString() ) ) && ( target instanceof AppProcessNix ) )
			instances.removeElement( target );

		return false;
	}
	
	public int getExecutableType() {
		int		err, val[];

		val = new int[ 1 ];

		err = AppUtilsNix.getExecutableType( getFilePath(), val );
		if ( err != ErrCodes.ERROR_NONE )
			return AF_UNKNOWN;

		return val[ 0 ];
	}

	public int getSizeFlags() {
		return 0;
	}

	public int getMinimumPartition() {
		return 0;
	}

	public int getSuggestedPartition() {
		return 0;
	}

/**
Convenience method which converts the return value of getExecutableType() into a string representation.
*/

	public String executableTypeToString( int f ) {
		switch ( f ) {
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

