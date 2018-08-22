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

/**
Corresponds to an 'QUIT' AppleEvent, used to quit an application.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppCommandMRJquit extends AppCommandMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	AppCommandMRJquit() {
	}
	
/**
Returns a new AppCommandMRJquit
*/

	public AppCommand redup() {
		return new AppCommandMRJquit();
	}

/**
Returns AppCommand.kAppCommandQuit
*/

	public String getCommand() {
		return kAppCommandQuit;
	}
	
/**
Returns AppCommand.kAppCommandQuit
*/

	public String asString() {
		return kAppCommandQuit;
	}

/**
Always return 0.
*/

	public int getNumArgs() {
		return 0;
	}
	
/**
Always return 0.
*/

	public int getMaxNumArgs() {
		return 0;
	}
	
/**
This class doesn't take arguments, so throws an IllegalArgumentException exception.
*/

	public void addArg( Object arg ) {
		throw new IllegalArgumentException( "this command does not take arguments" );
	}
	
/**
Does nothing.
*/

	public void clearArgs() {
	}

/**
Always return null.
*/

	public Object getArg( int which ) {
		return null;
	}

/**
Always return false.
*/

	public boolean isNumArgsUnlimited() {
		return false;
	}

/**
Always return true.
*/

	public boolean isSingleInstanceCapable() {
		return true;
	}
}





