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
FileExtension, FinderInfo, and MIMEType implement this interface.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface FileCharacteristic {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Indicate whether this object is the same as 'fc'.
*/

	boolean isMatch( FileCharacteristic fc );
}

