/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig;

import java.io.File;
import java.io.PrintStream;

/**
	Represents a currently running instance of an application. Objects of this type are created
	using the 'performCommand' method of an AppFile object.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface AppProcess extends DumpInfo {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";



/**

See the move() method.

*/



	public static final int APP_MOVE_TOFRONT = 1;



/**

See the move() method.

*/



	public static final int APP_MOVE_TOBACK = 2;



/**

See the move() method.

*/



	public static final int APP_MOVE_MINIMIZE = 3;



/**

See the move() method.

*/



	public static final int APP_MOVE_MAXIMIZE = 4;

/**
	Returns the AppFile object from which this object was created.
*/

	AppFile getAppFile();

/**
Returns the indicated command. The 'commandName' argument is one of the constants defined in
the AppCommand interface. If this instance cannot accept commands of the indicated type,
null is returned.
<BR>
After retrieving a command using this method, you can add arguments to it, if desired, and then
pass the command to the 'performCommand' method to execute the command.
*/

	AppCommand getCommand( String commandName );

/**
	Returns all the commands which this instance can accept. 
*/

	AppCommand[] getAllCommands();

/**
Performs the indicated command. If the command is not one of those recognized by this
application, an IllegalArgumentException exception is thrown. Returns an error code; if this
is non-zero, an error occurred.
@param flags reserved; set to 0
*/
	
	int performCommand( AppCommand command, int flags );

/**
Used to minimize, maximize this process, or send it in front of or behind other processes, if possible.
@param fromProcess reserved; set to null

@param selector one of the values: APP_MOVE_TOFRONT, APP_MOVE_TOBACK, APP_MOVE_MINIMIZE, APP_MOVE_MAXIMIZE

@param flags reserved; set to 0

*/

	int move( AppProcess fromProcess, int selector, int flags );


/**
Indicates whether this instance is indeed still running.
*/

	boolean isRunning();

/**
Returns platform-specific data for this process.
<BR>
On MRJ/PowerMac, the return array contains the ProcessSerialNumber for this process,
lowLongOfPSN in the first element of the array, highLongOfPSN in the second.
<BR>
On MS VM/Win95, the return array contains five 32-bit values: the first element of the array
contains the HWND for the main window of the main thread of the process, and the next four elements contain
the PROCESS_INFORMATION struct from the call to CreateProcess, with 'hProcess' at the second
element of the array, etc. 
<BR>
Other platforms: TBA.
*/

	int[] getPlatformData();
}

