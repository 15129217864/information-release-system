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

import java.io.File;
import com.jconfig.FileUtilsI;

/**
Interface which gets the name and path from a java File object.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DefaultFileUtilsNullMRJ implements FileUtilsI {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	public void initialize( File curDir, int creator ) {}

/*
	private String debugHook( String str ) {
		System.out.println( "RETURNING " + str + " for " + str );

		return str;
	}
*/

	public String getPath( File fl ) {
		return fl.getPath();
	}

	public String getAbsolutePath( File fl ) {
		return fl.getAbsolutePath();
	}

	public String getName( File fl ) {
		return fl.getName();
	}
}


