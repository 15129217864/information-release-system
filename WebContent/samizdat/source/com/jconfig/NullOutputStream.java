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

import java.io.*;

/**
An OutputStream which just discards any data written to it. Used by the Trace class.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class NullOutputStream extends OutputStream {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	public void write( int b ) throws IOException {
	}
}

