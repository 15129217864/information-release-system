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

import com.jconfig.FileSystem;

/**
A singleton which returns:

<UL>
<LI>a list of all the mounted file systems
<LI>the file system which contains a given file/folder
<LI>the file system which contains a given volume
</UL>

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class FSCreatorMSVM {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Returns a list of the currently mounted file systems. Not yet implemented.
*/

 	static FileSystem[] getFileSystems( int maxToReturn, int flags ) {
		return null;
	}

	static FileSystem getFileFileSystem( String path, int flags ) {
		return null;
	}

	static FileSystem getVolumeFileSystem( String driveName, int flags ) {
		return null;
	}

	private FSCreatorMSVM() {}
}


