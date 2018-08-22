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
Thrown by methods that are not yet implemented.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class UnimplementedException extends RuntimeException {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	public UnimplementedException() {
		super();
	}
	
	public UnimplementedException( String s ) {
		super( s );
	}
}

