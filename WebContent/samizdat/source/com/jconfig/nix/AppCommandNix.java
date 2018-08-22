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


import com.jconfig.*;
import java.io.PrintStream;
import java.io.File;
import java.util.Vector;

/**
Represents a command which can be sent to a Linux application.

This is a preliminary class derived from the Windows class of similar name. This class may be replaced
or modified in a future version.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppCommandNix implements AppCommand {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private Vector				theArgs;
	private	Class				permissibleClasses[];
	private int					maxNumArgs;
	private	String				command;

	AppCommandNix( String cmd ) {
		int			i, valZ;

		command = cmd;
		maxNumArgs = 1000;
		theArgs = new Vector( 2, 2 );

		try {
			permissibleClasses = new Class[ 3 ];
			permissibleClasses[ 0 ] = Class.forName( "java.lang.String" );
			permissibleClasses[ 1 ] = Class.forName( "java.io.File" );
			permissibleClasses[ 2 ] = Class.forName( "com.jconfig.DiskObject" );
		}
		catch ( ClassNotFoundException e ) {
			permissibleClasses = null;
		}
	}

	public String getCommand() {
		return command;
	}
	
	public String asString() {
		return command;
	}

	public AppCommand redup() {
		return new AppCommandNix( command );
	}

	public int getMaxNumArgs() {
		return maxNumArgs;
	}
	
	public void addArg( Object arg ) {
		theArgs.addElement( convertArgToString( arg ) );
	}

	String convertArgToString( Object arg ) {
		File			fl;

		if ( arg == null )
			throw new IllegalArgumentException( "arg is null" );

		if ( arg instanceof String )
			return (String) arg;

		if ( arg instanceof DiskObject )
			fl = ( (DiskObject) arg ).getFile();
		else if ( arg instanceof File )
			fl = (File) arg;
		else
			throw new IllegalArgumentException( "wrong arg type: it must be String, File, or DiskObject. type=" +
												arg.getClass() );

		if ( fl == null )
			return " ";
		else
			return fl.getPath();
	}

	public int getNumArgs() {
		return theArgs.size();
	}
	
	public void clearArgs() {
		theArgs.setSize( 0 );
	}

	public Class[] getPermissibleArgumentType( int position ) {
		Class		retVal[];
		int			i;
		
		if ( permissibleClasses == null )
			return null;

		retVal = new Class[ permissibleClasses.length ];

		for ( i = 0; i < permissibleClasses.length; i++ )
			retVal[ i ] = permissibleClasses[ i ];

		return retVal;
	}
	
	public Object getArg( int which ) {
		try {
			return (String) theArgs.elementAt( which );
		}
		catch ( Exception e ) {
			return null;
		}
	}

	public boolean isNumArgsUnlimited() {
		return true;
	}

	public boolean isSingleInstanceCapable() {
		return false;
	}
	
	public void dumpInfo( PrintStream ps, String indent ) {
		ps.println( indent + "verb=" + command + ", " + theArgs.size() + " of " + maxNumArgs );
	}
}



