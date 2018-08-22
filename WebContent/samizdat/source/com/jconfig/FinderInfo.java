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
Represents a Macintosh creator code/file type pair.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class FinderInfo implements FileCharacteristic, Cloneable {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private int		creator, type, attributes, flags;

	public static final int		APPLICATION_TYPE = JUtils.asciiToInt( "APPL" );
	public static final int		UNKNOWN_TYPE = JUtils.asciiToInt( "????" );

/**
Required length of the array argument to the array constructor and to the toArray method.
*/

	public static final int		FI_ARRAYLEN = 4;

/**
Offset to the creator value to the array constructor and to the toArray method.
*/

	public static final int		FI_OFFS_CRE = 0;

/**
Offset to the type value to the array constructor and to the toArray method.
*/

	public static final int		FI_OFFS_TYPE = 1;

/**
Offset to the flag value to the array constructor and to the toArray method.
*/

	public static final int		FI_OFFS_FLAGS = 2;

/**
Offset to the attributes value to the array constructor and to the toArray method.
*/

	public static final int		FI_OFFS_ATTRIBUTES = 3;

/**
Construct from a creator and type.
*/

	public FinderInfo( int c, int t ) {
		creator = c;
		type = t;
		flags = 0;
		attributes = 0;
	}
	
/**
Construct from a creator, type, flags, and attributes.
*/

	public FinderInfo( int c, int t, int flgs, int attr ) {
		creator = c;
		type = t;
		flags = flgs;
		attributes = attr;
	}
	
/**
Construct from an array containing the creator, type, flags, and attributes.
*/

	public FinderInfo( int a[] ) {
		creator = a[ FI_OFFS_CRE ];
		type = a[ FI_OFFS_TYPE ];
		flags = a[ FI_OFFS_FLAGS ];
		attributes = a[ FI_OFFS_ATTRIBUTES ];
	}
	
/**
Returns the creator value.
*/

	public int getCreator() {
		return creator;
	}
	
/**
Returns the file type value.
*/

	public int getFileType() {
		return type;
	}

/**
Returns the flags value.
*/

	public int getFlags() {
		return flags;
	}

/**
Returns the attributes value.
*/

	public int getAttributes() {
		return attributes;
	}

/**
Sets the creator value.
*/

	public void setCreator( int c ) {
		creator = c;
	}
	
/**
Sets the file type value.
*/

	public void setFileType( int t ) {
		type = t;
	}

/**
Sets the flags value.
*/

	public void setFlags( int f ) {
		flags = f;
	}

/**
Sets the attributes value.
*/

	public void setAttributes( int a ) {
		attributes = a;
	}

/**
Puts the values of this object into the given array. The array must have four elements,
and the items are placed into the array
*/

	public void toArray( int a[] ) {
		a[ FI_OFFS_CRE ] = creator;
		a[ FI_OFFS_TYPE ] = type;
		a[ FI_OFFS_FLAGS ] = flags;
		a[ FI_OFFS_ATTRIBUTES ] = attributes;
	}

/**
If 'fc' is a FinderInfo object, and if it has the same creator and file type values as this object, returns true.
Otherwise, returns false. NOTE: this does not compare the flags and attributes fields.
*/

	public boolean isMatch( FileCharacteristic fc ) {
		FinderInfo		temp;

		if ( !( fc instanceof FinderInfo ) )
			return false;

		temp = (FinderInfo) fc;
		if ( creator == temp.creator && type == temp.type )
			return true;
		else
			return false;
	}

	public String toString() {
		return "FinderInfo: <" +
							JUtils.intToAscii( creator ) +
							"," +
							JUtils.intToAscii( type ) +
							"," +
							Integer.toHexString( flags ) +
							"," +
							Integer.toHexString( attributes ) +
							"> ";
	}
}
