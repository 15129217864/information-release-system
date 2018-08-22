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
An object implementing this interface is returned by FileRegistry.getPlatformInfo().

<P>
Use this to get information on the current platform. See com.tolstoy.testjc.Tester.java for an example.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface PlatformInfoI extends DumpInfo {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	public static final int			WINDOWS_PLATFORM = 1;
	public static final int			LINUX_PLATFORM = 2;
	public static final int			UNIX_PLATFORM = 3;
	public static final int			MAC_PLATFORM = 4;
	public static final int			UNKNOWN_PLATFORM = 5;

/**
Indicates which platform type we're running on: WINDOWS_PLATFORM, LINUX_PLATFORM, etc.
*/

	public int getPlatformType();

/**
Returns a string representing the specific platform, such as "Linux x86" or "MRJJRI".
*/

	public String getPlatformString();

/**
Returns the value of the "java.version" system property, converted to a VersionNumber object.
*/

	public VersionNumber getVersionNumber();

/**
Returns the value of the "os.name" system property, converted to lowercase.
*/

	public String getOSName();

/**
Returns the value of the "os.arch" system property, converted to lowercase.
*/

	public String getOSArchitecture();

/**
Returns the value of the "os.version" system property, converted to lowercase.
*/

	public String getOSVersion();

/**
Returns the value of the "java.vendor" system property, converted to lowercase.
*/

	public String getVendor();

/**
Returns the value of the "java.class.version" system property, converted to lowercase.
*/

	public String getAPIVersion();

/**
Returns the value of the "java.version" system property, converted to lowercase.
*/

	public String getInterpreterVersion();
}

