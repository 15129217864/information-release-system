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

import java.util.Vector;
import java.io.File;

/**
Corresponds to an 'ODOC' AppleEvent, which opens one or more documents.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppCommandMRJodoc extends AppCommandMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Holds the arguments to this command.
*/

	private Vector		theArgs;

/**
The classes which we can take as arguments.
*/

	private	Class		permissibleClasses[];

/**
Initializes 'permissibleClasses' with String, File, and DiskObject.
*/

	AppCommandMRJodoc() {
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

/**
Returns a new AppCommandMRJodoc object.
*/

	public AppCommand redup() {
		return new AppCommandMRJodoc();
	}

/**
Returns AppCommand.kAppCommandOpenDoc
*/

	public String getCommand() {
		return kAppCommandOpenDoc;
	}
	
/**
Returns AppCommand.kAppCommandOpenDoc
*/

	public String asString() {
		return kAppCommandOpenDoc;
	}

/**
Always returns -1, meaning an unlimited number
*/

	public int getMaxNumArgs() {
		return -1;
	}
	
/**
Copies and returns permissibleClasses.
*/
	
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

/**
If 'arg' is a permissible type, adds it to our list of arguments.
*/
	
	public void addArg( Object arg ) {
//		if ( arg == null || !( arg instanceof String ) )
//			throw new IllegalArgumentException( "wrong argument type " + arg.getClass() );
		
//		theArgs.addElement( arg );
		theArgs.addElement( convertArgToString( arg ) );
	}

/**
If 'arg' is a String, returns it.
If 'arg' is a File or a DiskObject, converts it to a String, and returns the String.
*/

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
			return FileUtilsHolderMRJ.getPath( fl );
	}

/**
Returns the size of 'theArgs'
*/

	public int getNumArgs() {
		return theArgs.size();
	}
	
/**
Return the specified argument, or null if 'which' is out of bounds.
*/

	public Object getArg( int which ) {
		try {
			return (String) theArgs.elementAt( which );
		}
		catch ( Exception e ) {
			return null;
		}
	}

/**
Remove any arguments previously added.
*/

	public void clearArgs() {
		theArgs.setSize( 0 );
	}

/**
Always returns true.
*/

	public boolean isNumArgsUnlimited() {
		return true;
	}

/**
Always returns true.
*/

	public boolean isSingleInstanceCapable() {
		return true;
	}
}
