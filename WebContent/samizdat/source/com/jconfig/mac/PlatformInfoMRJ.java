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

import com.jconfig.*;

import java.io.File;
import java.io.PrintStream;
import java.util.Properties;

/**
This class determines the specifics of the machine and VM which is being used.

<P>
A set of accessor methods are provided, for example:

<PRE>
	isPowerMac
		- indicates whether we're running on a PowerMac
</PRE>

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class PlatformInfoMRJ implements PlatformInfoI {
	private VersionNumber			versNum;
	private String					osName, osArch, osVersion, vendor, APIVersion, interpreterVersion;
 	private boolean					bIsPlatformMRJJRI = false,
									bIsPlatformCW113 = false,
									bIsPlatformMRJOSX = false,
									bIsPowerMac = false,
									bIsOSX = false,
									bIsApple = false,
									bIsCW = false,
									bIs102OrLess = false,
									bIs11OrGreater = false,
									bIs113OrGreater = false;

	private static PlatformInfoMRJ	instance = null;

	static PlatformInfoMRJ getInstance() {
		if ( instance == null )
			instance = new PlatformInfoMRJ();

		return instance;
	}

	private PlatformInfoMRJ() {
		figureOutPlatform();
	}

/**
Indicates which platform type we're running on: WINDOWS, LINUX, etc.
*/

	public int getPlatformType() {
		return MAC_PLATFORM;
	}

/**
Returns a string representing the specific platform: "CW", "MRJJRI", or "unknown".
*/

	public String getPlatformString() {
		if ( bIsPlatformMRJJRI )
			return "MRJJRI";
		else if ( bIsPlatformCW113 )
			return "CW";
		else if ( bIsPlatformMRJOSX )
			return "MRJOSX";
		else
			return "unknown";
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
										 
	public boolean isPlatformMRJJRI() {
		return bIsPlatformMRJJRI;
	}

	public boolean isPlatformCW113() {
		return bIsPlatformCW113;
	}

	public boolean isPlatformMRJOSX() {
		return bIsPlatformMRJOSX;
	}

	public boolean isPowerMac() {
		return bIsPowerMac;
	}

	public boolean isApple() {
		return bIsApple;
	}

	public boolean isCW() {
		return bIsCW;
	}

	public boolean is102OrLess() {
		return bIs102OrLess;
	}

	public boolean is11OrGreater() {
		return bIs11OrGreater;
	}

	public boolean is113OrGreater() {
		return bIs113OrGreater;
	}

/**
Use various system properties to figure out which platform we're running on.
Sets the appropriate 'bIsPlatformXXX' boolean to true.
*/

	private final void figureOutPlatform() {
		boolean				bTempPPC;

		try {
			osName = System.getProperty( "os.name" ).toLowerCase();
			osArch = System.getProperty( "os.arch" ).toLowerCase();
			osVersion = System.getProperty( "os.version" ).toLowerCase();
			vendor = System.getProperty( "java.vendor" ).toLowerCase();
			APIVersion = System.getProperty( "java.class.version" ).toLowerCase();
			interpreterVersion = System.getProperty( "java.version" ).toLowerCase();
			versNum = new VersionNumber( interpreterVersion );

			if ( osName.indexOf( "mac os x" ) >= 0 )
				bIsOSX = true;
			else
				bIsOSX = false;

			if ( osArch.indexOf( "power" ) >= 0 || osArch.indexOf( "ppc" ) >= 0 )
				bTempPPC = true;
			else
				bTempPPC = false;

			if ( ( osName.indexOf( "mac os" ) >= 0 ) && bTempPPC ) {
				bIsPowerMac = true;

				if ( vendor.indexOf( "apple" ) >= 0 ) {
					bIsApple = true;
				}
				else if ( vendor.indexOf( "metrowerks" ) >= 0 ) {
					bIsCW = true;
				}
			}

			if ( versNum.getMajorVersion() == 1 && versNum.getMinorVersion() == 0 && versNum.getRevisionStage() <= 2 ) {
				bIs102OrLess = true;
			}
			
			if ( versNum.getMajorVersion() == 1 && versNum.getMinorVersion() >= 1 ) {
				bIs11OrGreater = true;
			}

			if ( versNum.getMajorVersion() == 1 && versNum.getMinorVersion() >= 1 && versNum.getRevisionStage() >= 3 ) {
				bIs113OrGreater = true;
			}

			if ( bIsApple ) {
				if ( bIsOSX )
					bIsPlatformMRJOSX = true;
				else
					bIsPlatformMRJJRI = true;
			}
			else if ( bIsCW ) {
				if ( bIs113OrGreater ) {
					bIsPlatformCW113 = true;
				}
			}
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
		ps.println( indent + "PlatformInfoMRJ: platform=" + getPlatformString() + ", version=" + getVersionNumber() );
		ps.println( indent + "    isPlatformMRJJRI=" + isPlatformMRJJRI() +
								", isPlatformMRJOSX=" + isPlatformMRJOSX() +
								", isPlatformCW113=" + isPlatformCW113() +
								", isPowerMac=" + isPowerMac() +
								", isApple=" + isApple() );
		ps.println( indent + "    isCW=" + isCW() +
								", is102OrLess=" + is102OrLess() +
								", is11OrGreater=" + is11OrGreater() +
								", is113OrGreater=" + is113OrGreater() );
	}
}

//System.getProperty("mrj.version"); 
