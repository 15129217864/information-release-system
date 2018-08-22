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

/**
Singleton which maintains a FileUtilsI object.

<P>
For each method of the FileUtilsI interface, if this class holds a FileUtilsI object, calls are forwarded
to the FileUtilsI object. If the FileUtilsI object is null, the corresponding method in the java File class
is called.

<P>
This class is used by the classes which get a full path from a java File object. Currently, this is used by:

<PRE>
	FileRegistryMRJ
	AppCommandMRJodoc
	AppCommandMRJpdoc
</PRE>

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class FileUtilsHolderMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private static FileUtilsI		utils = null;

	static void setFileUtils( FileUtilsI c ) { utils = c; }
	static FileUtilsI getFileUtils() { return utils; }

/**
If utils isn't null, calls the utils's getPath() method and returns the result
Otherwise, returns fl.getPath()
*/

	static String getPath( File fl ) {
		if ( utils != null )
			return utils.getPath( fl );
		else
			return fl.getPath();
	}

/**
If utils isn't null, calls the utils's getAbsolutePath() method and returns the result
Otherwise, returns fl.getAbsolutePath()
*/

	static String getAbsolutePath( File fl ) {
		if ( utils != null )
			return utils.getAbsolutePath( fl );
		else
			return fl.getAbsolutePath();
	}

/**
If utils isn't null, calls the utils's getName() method and returns the result
Otherwise, returns fl.getName()
*/

	static String getName( File fl ) {
		if ( utils != null )
			return utils.getName( fl );
		else
			return fl.getName();
	}
}

