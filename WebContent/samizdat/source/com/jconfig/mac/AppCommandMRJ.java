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

import java.io.PrintStream;

/**
This is the superclass for the default MRJ AppCommand's.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

abstract class AppCommandMRJ implements AppCommand {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	AppCommandMRJ() {
	}
	
	public abstract String getCommand();
	
	public abstract String asString();

	public abstract AppCommand redup();

/**
Always returns 0.
*/

	public int getMaxNumArgs() {
		return 0;
	}
	
/**
Does nothing.
*/

	public void addArg( Object arg ) {
	}

/**
Always returns 0.
*/

	public int getNumArgs() {
		return 0;
	}
	
/**
Does nothing.
*/

	public void clearArgs() {
	}

/**
Always returns null.
*/

	public Class[] getPermissibleArgumentType( int position ) {
		return null;
	}
	
/**
Always returns null.
*/

	public Object getArg( int which ) {
		return null;
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

	public void dumpInfo( PrintStream ps, String indent ) {
		ps.println( indent + "AppCommand: " + asString() );
	}
}
