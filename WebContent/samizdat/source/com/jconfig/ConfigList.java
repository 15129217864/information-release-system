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
Represents objects which contain mappings between file types.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface ConfigList {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	static final String kConfigFileName = "jconfig.cfg";

/**
Will call its argument with each ConfigEntry available from this object.
*/

	int iterate( ConfigEntryVisitor fdv );

/**
Returns an array of FileExtension objects corresponding to the indicated FinderInfo object.
If none could be found, null is returned.
*/

	FileExtension[] findMatches( FinderInfo fInfo, int maxToReturn, int direction );

/**
Returns an array of FinderInfo objects corresponding to the indicated FileExtension object.
If none could be found, null is returned.
*/

	FinderInfo[] findMatches( FileExtension ext, int maxToReturn, int direction );
}
