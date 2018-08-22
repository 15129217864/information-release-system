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
Thrown when an error occurs in native code. Should be constructed with the os-specific error code which
was returned from the os routine involved.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class OSException extends RuntimeException {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	public OSException() {
		super();
	}
	
	public OSException( String s ) {
		super( s );
	}
}

