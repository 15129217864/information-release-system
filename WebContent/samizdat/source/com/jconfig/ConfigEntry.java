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
Interface for objects which map between different representations of a file type. For instance,
this corresponds to one entry in the Internet Config mapping table. See the IC documentation
for more information.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface ConfigEntry extends DumpInfo {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Returns the FinderInfo associated with this mapping.
*/

	FinderInfo getFinderInfo();

/**
Returns the MIME type associated with this mapping.
*/

	MIMEType getMIMEType();


/**
Returns the FileExtension associated with this mapping.
*/

	FileExtension getFileExtension();

/**
Returns the name of the application associated with this mapping.
*/

	String getAppName();

/**
Returns the name of this mapping.
*/

	String getEntryName();


/**

Returns the direction flags: in, out, or both.

*/

	int getFlags();
}

