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
Represents a Windows-style file extension.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class FileExtension implements FileCharacteristic, Cloneable {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private String		internalExt;

/**
The argument 'str' should contain the extension alone, complete with a '.' character, e.g.,
".txt"
*/

	public FileExtension( String str ) {
		int			index;

		if ( str == null || str.length() < 2 ) {
			internalExt = ".";
			return;
		}
		
		internalExt = str.toLowerCase();

		index = internalExt.lastIndexOf( "." );
		if ( index < 0 )
			internalExt = "." + internalExt;
		else
			internalExt = internalExt.substring( index, internalExt.length() );
	}

/**
If 'fc' is a FileExtension object, and it has the same string value (e.g., ".txt") as this object, return
true. Otherwise, returns false.
If either this object or 'fc' are ".", returns false.
Wildcards are not fully supported.
*/
	
	public boolean isMatch( FileCharacteristic fc ) {
		FileExtension		other;

		if ( !( fc instanceof FileExtension ) )
			return false;

		other = (FileExtension) fc;

		if ( this.internalExt.equals( "." ) || other.internalExt.equals( "." ) )
			return false;

		if ( this.internalExt.indexOf( "*" ) >= 0 || other.internalExt.indexOf( "*" ) >= 0 )
			return true;

		return internalExt.equals( other.internalExt );
	}

/**
Returns the extension, including the '.' character.
*/

	public String getString() {
		return internalExt;
	}

	public String toString() {
		return "FileExtension: " + internalExt + " ";
	}
}

