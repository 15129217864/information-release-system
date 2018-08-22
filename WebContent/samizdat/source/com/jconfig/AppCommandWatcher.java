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

/**
Interface for objects which watch the commands sent to an AppProcess object.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface AppCommandWatcher {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Called before the event is acted upon. If this method returns true, the event is not
processed.
@param target the target of this AppCommand
@param command the command
@param flags the flags for the command
*/

	boolean watchPre( Object target, AppCommand command, int flags );

/**
Called after the event has been acted upon.
@param target the target of this AppCommand
@param command the command
@param flags the flags for the command
*/

	boolean watchPost( Object target, AppCommand command, int flags );
}

