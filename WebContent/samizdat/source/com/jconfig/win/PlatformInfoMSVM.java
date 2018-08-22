/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig.win;

import com.jconfig.*;
import java.io.File;
import java.io.PrintStream;
import java.util.Properties;

/**
This class determines the specifics of the machine and VM which is being used.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class PlatformInfoMSVM implements PlatformInfoI {
	private VersionNumber			versNum;
	private String					osName, osArch, osVersion, vendor, APIVersion, interpreterVersion;
	private boolean					bIsPlatformMSVM15 = false,
									bIsPlatformMSVM15W = false,
									bIsPlatformMSVM20 = false,
									bIsPlatformMSVM20W = false,
									bIsPlatformSun11 = false,
									bIsPlatformSun11W = false,
									bIsWinNT, bIsWin95 = false,
									bIsMS = false,
									bIsSun = false,
									bIsSuperCede = false,
									bIsSymantec = false,
									bIs102OrLess = false,
									bIs11OrGreater = false;

	public PlatformInfoMSVM() {
		figureOutPlatform();
	}

/**
Indicates which platform type we're running on: WINDOWS, LINUX, etc.
*/

	public int getPlatformType() {
		return WINDOWS_PLATFORM;
	}

/**
Returns a string representing the specific platform: "CW", "MRJJRI", or "unknown".
*/

	public String getPlatformString() {
		if ( bIsPlatformMSVM15 )
			return "MSVM15";
		else if ( bIsPlatformMSVM20 )
			return "MSVM20";
		else if ( bIsPlatformSun11 )
			return "Sun11";
		else if ( bIsPlatformMSVM15W )
			return "MSVM15W";
		else if ( bIsPlatformMSVM20W )
			return "MSVM20W";
		else if ( bIsPlatformSun11W )
			return "Sun11W";
		else
			return "unknown";
	}

/**
Returns the value of the "java.version" system property, converted to a VersionNumber object.
*/

	public VersionNumber getVersionNumber() {
		return versNum;
	}

	public boolean isPlatformMSVM15() {
		return bIsPlatformMSVM15;
	}

	public boolean isPlatformMSVM20() {
		return bIsPlatformMSVM20;
	}

	public boolean isPlatformSun11() {
		return bIsPlatformSun11;
	}

	public boolean isPlatformMSVM15W() {
		return bIsPlatformMSVM15W;
	}

	public boolean isPlatformMSVM20W() {
		return bIsPlatformMSVM20W;
	}

	public boolean isPlatformSun11W() {
		return bIsPlatformSun11W;
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

/*
			Trace.println( "osName=" + osName );
			Trace.println( "osArch=" + osArch );
			Trace.println( "osVersion=" + osVersion );
			Trace.println( "vendor=" + vendor );
			Trace.println( "APIVersion=" + APIVersion );
			Trace.println( "interpreterVersion=" + interpreterVersion );
			Trace.println( "versNum=" + versNum );
*/

			if ( osName.startsWith( "windows" ) ) {
				if ( osName.indexOf( "nt" ) >= 0 ) {
					bIsWinNT = true;
				}
				else {
					bIsWin95 = true;
				}

				if ( vendor.indexOf( "microsoft" ) >= 0 ) {
					bIsMS = true;
				}
				else if ( vendor.indexOf( "sun microsystems" ) >= 0 ) {
					bIsSun = true;
				}
				else if ( vendor.indexOf( "supercede" ) >= 0 ) {
					bIsSuperCede = true;
				}
				else if ( vendor.indexOf( "symantec" ) >= 0 ) {
					bIsSymantec = true;
				}
			}

			if ( versNum.getMajorVersion() == 1 && versNum.getMinorVersion() == 0 && versNum.getRevisionStage() <= 2 ) {
				bIs102OrLess = true;
			}
			
			if ( versNum.getMajorVersion() == 1 && versNum.getMinorVersion() >= 1 ) {
				bIs11OrGreater = true;
			}

				//	symantec v1.1 uses "11" as its version number; not cool

			if ( !bIs102OrLess && !bIs11OrGreater && bIsSymantec && versNum.getMajorVersion() >= 11 ) {
				bIs11OrGreater = true;
			}
																
			if ( bIsMS ) {
				if ( bIsWin95 ) {
					if ( bIs102OrLess )
						bIsPlatformMSVM15 = true;
					else
						bIsPlatformMSVM20 = true;
				}
				else if ( bIsWinNT ) {
					if ( bIs102OrLess )
						bIsPlatformMSVM15W = true;
					else
						bIsPlatformMSVM20W = true;
				}
			}
			else if ( bIsSun || bIsSuperCede || bIsSymantec ) {
				if ( bIsWin95 ) {
					if ( bIs11OrGreater ) {
						bIsPlatformSun11 = true;
					}
				}
				else if ( bIsWinNT ) {
					if ( bIs11OrGreater ) {
						bIsPlatformSun11W = true;
					}
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
		ps.println( indent + "PlatformInfoMSVM: platform=" + getPlatformString() + ", version=" + getVersionNumber() );
		ps.println( indent + "    isPlatformMSVM15=" + isPlatformMSVM15() +
								", isPlatformMSVM20=" + isPlatformMSVM20() +
								", isPlatformSun11=" + isPlatformSun11() );
		ps.println( indent + "    isPlatformMSVM15W=" + isPlatformMSVM15W() +
								", isPlatformMSVM20W=" + isPlatformMSVM20W() +
								", isPlatformSun11W=" + isPlatformSun11W() );
	}
}

