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

import java.util.StringTokenizer;

/**
Parses a string in the form "majorVersion?minorVersion?revisionStage?buildNumber", where 'majorVersion', etc.
are numbers, and the '?' represent non-numeric characters. Unspecified numbers will be zero.

<BR>
E.g., "1.0.2" results in majorVersion=1, minorVersion=0, revisionStage=2, and buildNumber=0.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class VersionNumber {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private String		versionString;
	private int			majorVersion, minorVersion, revisionStage, buildNumber;

	public VersionNumber( String s ) {
		String		sub[] = { "0", "0", "0", "0" };
		int			i, indexArray[];

		majorVersion = 0;
		minorVersion = 0;
		revisionStage = 0;
		buildNumber = 0;

		if ( s == null ) {
			versionString = "";
			return;
		}

		versionString = s;

		indexArray = new int[ 1 ];
		indexArray[ 0 ] = 0;

		for ( i = 0; i < 4; i++ )
			sub[ i ] = getNextNumber( s, indexArray );

		try {
			majorVersion = Integer.parseInt( sub[ 0 ] );
		}
		catch ( Exception e ) {}

		try {
			minorVersion = Integer.parseInt( sub[ 1 ] );
		}
		catch ( Exception e ) {}

		try {
			revisionStage = Integer.parseInt( sub[ 2 ] );
		}
		catch ( Exception e ) {}

		try {
			buildNumber = Integer.parseInt( sub[ 3 ] );
		}
		catch ( Exception e ) {}
	}

	private String getNextNumber( String s, int index[] ) {
		String		retVal;
		int			len, i;
		char		cc;

		try {
			retVal = "0";
			if ( s == null )
				return retVal;

			len = s.length();

			if ( index[ 0 ] >= len )
				return retVal;

			for ( ;index[ 0 ] < len; index[ 0 ]++ ) {
				cc = s.charAt( index[ 0 ] );
				if ( Character.isDigit( cc ) )
					break;
			}

			if ( index[ 0 ] >= len )
				return retVal;

			for ( ;index[ 0 ] < len; index[ 0 ]++ ) {
				cc = s.charAt( index[ 0 ] );
				if ( !Character.isDigit( cc ) )
					break;
				retVal += cc;
			}

			return retVal;
		}
		catch ( Exception e ) {
			return "0";
		}
	}

	public String getVersionString() {
		return versionString;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

	public int getRevisionStage() {
		return revisionStage;
	}

	public int getBuildNumber() {
		return buildNumber;
	}

	public String toString() {
		return "{" + majorVersion + ", " + minorVersion + ", " + revisionStage + ", " + buildNumber + " }";
	}
}



