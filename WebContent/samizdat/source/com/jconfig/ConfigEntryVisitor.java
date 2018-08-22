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
Interface for objects which want to iterate over a sequence of ConfigEntry's. The 'iterate'
method of FileRegistry takes an argument of this interface.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface ConfigEntryVisitor {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Called for each ConfigEntry available from the calling object.
*/

	void visit( ConfigEntry fd );
}

