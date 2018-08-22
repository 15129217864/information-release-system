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

import java.io.PrintStream;

/**
Represents an alias or shortcut to another file.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface DiskAlias extends DiskFile {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Used to indicate that this is an alias to a disk drive.
*/

	public static final int		ALIAS_VOL = 1;

/**
Used to indicate that this is an alias to a directory.
*/

	public static final int		ALIAS_DIR = 2;

/**
Used to indicate that this is an alias to a file.
*/

	public static final int		ALIAS_FILE = 3;

/**
Used to indicate that this is an alias to an unknown target.
*/

	public static final int		ALIAS_OTHER = 4;

/**
Used to indicate that this alias no longer exists.
*/

	public static final int		ALIAS_NOLONGER = 5;

/**
Indicates to what type of object this is an alias. Returns one of the preceding constants.
*/

	int getAliasType();

/**
Sets the FinderInfo of this file. The FinderInfo is the creator and file
type of this object. You can only set the creator of an alias, the type is not changed. Returns
zero if no error occured; non-zero otherwise.
*/

	int setFinderInfo( FinderInfo newFI );
}

