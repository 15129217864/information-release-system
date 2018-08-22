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

import java.io.PrintStream;

/**
Given a byte array containing version info in the format of a 'vers' resource, parses the resource
data, and returns version information.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class VersionInfoMRJ implements VersionInfo {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private int			majorVersion, minorVersion;
	private String		abbrevString, infoString, versionString;

/**
Construct from a buffer containing the 'vers' resource.
@param resData the buffer
@param resLength the length of the 'vers' data
*/

	VersionInfoMRJ( byte resData[], int resLength ) {
		if ( resLength < 6 || resLength > resData.length )
			throw new IllegalArgumentException( "bad resData" );

		if ( resLength < resData.length )
			resLength = resData.length;

		abbrevString = "";
		infoString = "";
		versionString = "";

		buildVersionNumbers( resData[ 0 ] & 0xFF, resData[ 1 ] & 0xFF );

		buildVersionString( resData[ 0 ] & 0xFF, resData[ 1 ] & 0xFF, resData[ 2 ] & 0xFF, resData[ 3 ] & 0xFF );

		if ( resLength > 6 ) {
			abbrevString = JUtils.pascalBytesToString( resData, 6 );

			if ( resLength > 6 + abbrevString.length() + 1 )
				infoString = JUtils.pascalBytesToString( resData, 6 + abbrevString.length() + 1 );
		}
	}

	private void buildVersionNumbers( int i0, int i1 ) {
		int			temp;

		temp = 0x0F & ( i0 >> 4 );
		majorVersion = 10 * temp + ( 0x0F & i0 );
		minorVersion = 0x0F & ( i1 >> 4 );
	}

	private void buildVersionString( int i0, int i1, int i2, int i3 ) {
		String		s;
		int			temp;

		temp = i1 & 0x0F;

		versionString = "" + majorVersion + "." + minorVersion;
		if ( temp != 0 )
			versionString = versionString + "." + temp;

		if ( i2 == 0x60 )
			versionString = versionString + "b" + i3;
		else if ( i2 == 0x40 )
			versionString = versionString + "a" + i3;
		else if ( i2 == 0x20 )
			versionString = versionString + "d" + i3;
	}

	public int getMajorVersion() {
		return majorVersion;
	}
	
	public int getMinorVersion() {
		return minorVersion;
	}
	
	public String getVersionString() {
		return versionString;
	}

	public String getCopyrightNotice() {
		if ( infoString == null )
			return abbrevString;
		else
			return infoString;
	}
	
	public String getProductName() {
		return abbrevString;
	}
	
	public String getDescription() {
		return infoString;
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		String		tempAbb, tempInfo;
		
		ps.println( indent + "version info:" );
		ps.println( indent + "  major version=" + majorVersion + ", minor version=" + minorVersion );
		ps.println( indent + "  abbrevString=" + abbrevString + ", versionString=" + getVersionString() );
		ps.println( indent + "  infoString=" + infoString );
	}
}
