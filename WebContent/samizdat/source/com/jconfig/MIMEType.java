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
Represents a MIME type. This class is currently not fully supported.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class MIMEType implements FileCharacteristic, Cloneable {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private String		internalString, type, subtype;
	
	public MIMEType( String intStr ) {
		int			ind;

		ind = intStr.indexOf( "/" );
		if ( ind < 1 ) {
			internalString = intStr;
			type = "";
			subtype = "";
		}
		else {
		internalString = intStr;
		type = intStr.substring( 0, ind );
		subtype = intStr.substring( ind + 1, intStr.length() );
		}
	}
	
	public boolean isMatch( FileCharacteristic fc ) {
		MIMEType			compMime;

		if ( fc == null || !( fc instanceof MIMEType ) )
			return false;
		
		compMime = (MIMEType) fc;
		
		return ( compMime.internalString ).equalsIgnoreCase( internalString );
	}

	public String getString() {
		return internalString;
	}
	
	public String getType() {
		return type;
	}
	
	public String getSubtype() {
		return subtype;
	}
	
	public String toString() {
		return "MIMEType: <" + type + "," + subtype + ">";
	}
}

