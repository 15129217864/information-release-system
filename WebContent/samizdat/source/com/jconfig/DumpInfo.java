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
Objects which implement this interface can write information about themselves to a PrintStream.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface DumpInfo {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
	Writes information about this object to the indicated PrintStream. Each line will
	be preceded with the 'indent' argument.
*/

	void dumpInfo( PrintStream ps, String indent );
}
