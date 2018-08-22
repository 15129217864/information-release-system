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
An object of this class is passed to the iterate() method of objects which implement the
DiskObject interface. See that method for more information.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface DiskFilter {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Mask indicating this object does not want to see hidden files.
*/

	static final int		IGNORE_HIDDEN = 0x01;

/**
Mask indicating this object does not want to see folders.
*/

	static final int		IGNORE_FOLDERS = 0x02;

/**
Mask indicating this object does not want to see any files.
*/

	static final int		IGNORE_FILES = 0x04;

/**
Mask indicating this object does not want to see aliases.
*/

	static final int		IGNORE_ALIASES = 0x08;

/**
Mask indicating this object does not want to see objects with locked names.
*/

	static final int		IGNORE_NAME_LOCKED = 0x10;

	boolean visit( DiskObject diskObj );
}


