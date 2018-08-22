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
Interface for objects which represent an application's disk file. This does not represent a
running instance of the application, only the file.

Arrays of objects of this class are returned from the 'getApps()' methods of the FileRegistry
class.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface AppFile extends DiskFile {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Used with the performCommand() method.
*/

	public static final int		AF_NO_LAYER_SWITCH = 1;

/**
A Mac-specific flag returned by the getSizeFlags method.
*/

	public static final int		AF_DISPLAYMANAGER		= 0x00000004;

/**
A Mac-specific flag returned by the getSizeFlags method.
*/

	public static final int		AF_USE_TES		= 0x00000008;

/**
A Mac-specific flag returned by the getSizeFlags method.
*/

	public static final int		AF_STAT_AWARE			= 0x00000010;

/**
A Mac-specific flag returned by the getSizeFlags method.
*/

	public static final int		AF_LOCAL_REMOTE_EVENTS	= 0x00000020;

/**
A Mac-specific flag returned by the getSizeFlags method.
*/

	public static final int		AF_HLE_AWARE		= 0x00000040;

/**
A Mac-specific flag returned by the getSizeFlags method.
*/

	public static final int		AF_32			= 0x00000080;

/**
A Mac-specific flag returned by the getSizeFlags method.
*/

	public static final int		AF_APP_DIED_MSG				= 0x00000100;

/**
A Mac-specific flag returned by the getSizeFlags method.
*/

	public static final int		AF_FRONT_CLICKS			= 0x00000200;

/**
A Mac-specific flag returned by the getSizeFlags method.
*/

	public static final int		AF_APPE			= 0x00000400;

/**
A Mac-specific flag returned by the getSizeFlags method.
*/

	public static final int		AF_ACTIVATE_FG	= 0x00000800;

/**
A Mac-specific flag returned by the getSizeFlags method.
*/

	public static final int		AF_CAN_BG				= 0x00001000;

/**
A Mac-specific flag returned by the getSizeFlags method.
*/

	public static final int		AF_SUSPEND_RESUME		= 0x00004000;

/**
A Mac-specific flag returned by the getSizeFlags method.
*/

	public static final int		AF_MULTILAUNCH				= 0x00010000;

/**
A Mac-specific flag returned by the getSizeFlags method.
*/

	public static final int		AF_DESKACC				= 0x00020000;

/**
Returned by the getExecutableType method.
*/

	public static final int		AF_W32 = 0;

/**
Returned by the getExecutableType method.
*/

	public static final int		AF_DOS = 1;

/**
Returned by the getExecutableType method.
*/

	public static final int		AF_WOW = 2;

/**
Returned by the getExecutableType method.
*/

	public static final int		AF_PIF = 3;

/**
Returned by the getExecutableType method.
*/

	public static final int		AF_POSIX = 4;

/**
Returned by the getExecutableType method.
*/

	public static final int		AF_OS216 = 5;

/**
Returned by the getExecutableType method.
*/

	public static final int		AF_PEF = 6;

/**
Returned by the getExecutableType method.
*/

	public static final int		AF_CFM68 = 7;

/**
Returned by the getExecutableType method.
*/

	public static final int		AF_68K = 8;

/**
Returned by the getExecutableType method.
*/

	public static final int		AF_UNKNOWN = 9;

/**
Returned by the getExecutableType method.
*/

	public static final int		AF_OSXPACKAGE = 10;

/**
	Returns the IconBundle associated with this application. May return null if the
	application's icons can't be found.
*/

	IconBundle getIconBundle( FileType ft );

/**
Returns the set of FileType's which are associated with this application. May return null
if no file types could be determined.
@param maxToReturn the maximum number of file types requested. The actual number returned
may be less or more than the requested amount.
*/

	FileType[] getFileTypes( int maxToReturn );

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
	Returns all the commands which this application can accept.
*/

	AppCommand[] getAllCommands();

/**
	Performs the indicated command. If an error occurs, or if that command does not
	cause the creation of a new instance of the application, returns null. Otherwise,
	returns an AppProcess object representing the new application instance. To open
	the instance in the background, OR the flags argument with 'AF_NO_LAYER_SWITCH'. If
	the command is not one of those recognized by this application, an IllegalArgumentException
	exception is thrown.
*/ 

	AppProcess performCommand( AppCommand command, int flags );

/**
	Returns the set of all instances of this application currently running.
*/

	AppProcess[] getInstances();

/**
Indicates the architecture of this application. Returns one of the preceding constants.
*/

	int getExecutableType();

/**
Mac-specific method which returns the 'SIZE' flags for this application.
*/

	int getSizeFlags();
	
/**
Mac-specific method which returns the minimum memory space which an instance of this application
would occupy.
*/

	int getMinimumPartition();
	
/**
Mac-specific method which returns the suggested memory space which an instance of this application
would occupy.
*/

	int getSuggestedPartition();

/**
Convenience method which converts the return value of getExecutableType() into a string representation.
*/

	String executableTypeToString( int f );
}


