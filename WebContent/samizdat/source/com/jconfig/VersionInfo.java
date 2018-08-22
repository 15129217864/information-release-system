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
Represents version information about an application.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface VersionInfo extends DumpInfo {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Returns the major version.
*/

	int getMajorVersion();

/**
Returns the minor version.
*/

	int getMinorVersion();

/**
Returns the version string.
*/

	String getVersionString();

/**
Returns the copyright notice.
*/

	String getCopyrightNotice();

/**
Returns the product name.
*/

	String getProductName();

/**
Returns the application description.
*/

	String getDescription();
}

