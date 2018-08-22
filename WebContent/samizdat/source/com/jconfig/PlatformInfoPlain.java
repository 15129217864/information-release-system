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
import java.io.PrintStream;
import java.util.Properties;

/**
This class determines the specifics of the machine and VM which is being used.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class PlatformInfoPlain implements PlatformInfoI {
	private VersionNumber			versNum;
	private String					osName, osArch, osVersion, vendor, APIVersion, interpreterVersion;

	public PlatformInfoPlain() {
		figureOutPlatform();
	}

/**
Indicates which platform type we're running on: WINDOWS, LINUX, etc.
*/

	public int getPlatformType() {
		return UNKNOWN_PLATFORM;
	}

/**
Returns a string representing the specific platform: "CW", "MRJJRI", or "unknown".
*/

	public String getPlatformString() {
		return "plain";
	}

/**
Returns the value of the "java.version" system property, converted to a VersionNumber object.
*/

	public VersionNumber getVersionNumber() {
		return versNum;
	}

/**
Returns the value of the "os.name" system property, converted to lowercase.
*/

	public String getOSName() {
		return osName;
	}

/**
Returns the value of the "os.arch" system property, converted to lowercase.
*/

	public String getOSArchitecture() {
		return osArch;
	}

/**
Returns the value of the "os.version" system property, converted to lowercase.
*/

	public String getOSVersion() {
		return osVersion;
	}

/**
Returns the value of the "java.vendor" system property, converted to lowercase.
*/

	public String getVendor() {
		return vendor;
	}

/**
Returns the value of the "java.class.version" system property, converted to lowercase.
*/

	public String getAPIVersion() {
		return APIVersion;
	}

/**
Returns the value of the "java.version" system property, converted to lowercase.
*/

	public String getInterpreterVersion() {
		return interpreterVersion;
	}

/**
Use various system properties to figure out which platform we're running on.
Sets the appropriate 'bIsPlatformXXX' boolean to true.
*/

	private final void figureOutPlatform() {
		try {
			osName = System.getProperty( "os.name" ).toLowerCase();
			osArch = System.getProperty( "os.arch" ).toLowerCase();
			osVersion = System.getProperty( "os.version" ).toLowerCase();
			vendor = System.getProperty( "java.vendor" ).toLowerCase();
			APIVersion = System.getProperty( "java.class.version" ).toLowerCase();
			interpreterVersion = System.getProperty( "java.version" ).toLowerCase();
			versNum = new VersionNumber( interpreterVersion );
		}
		catch ( Exception e ) {
			Trace.println( "figureOutPlatforms, e=" + e );
			return;
		}
		catch ( Error er ) {
			Trace.println( "figureOutPlatforms, er=" + er );
			return;
		}
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		ps.println( indent + "PlatformInfoPlain: platform=" + getPlatformString() + ", version=" + getVersionNumber() );
		ps.println( indent + "    osName=" + getOSName() +
								", osArch=" + getOSArchitecture() +
								", osVersion=" + getOSVersion() );
		ps.println( indent + "    APIVersion=" + getAPIVersion() +
								", vendor=" + getVendor() );
	}
}



