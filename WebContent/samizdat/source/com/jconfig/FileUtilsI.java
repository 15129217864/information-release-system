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

/**
Interface which gets the name and path from a java File object.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface FileUtilsI {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	void initialize( File curDir, int creator );
	String getPath( File fl );
	String getAbsolutePath( File fl );
	String getName( File fl );
}

