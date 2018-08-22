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
import java.util.Vector;

/**
Called by FileRegistryMSVM to find applications by name or by the file extensions with which they're associated.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppFinderMSVM {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	public AppFinderMSVM() {
	}

/**
Calls native code to search for apps with the given name. The native code returns zero or more full paths, which
are used to create an array of AppFileMSVM objects. May return null if no apps could be found.

@param appName the string to search for.
@param maxToReturn indicates the maximum number of AppFiles to return. NOTE: this is used as
a hint only; the actual array size may be greater or less than this.
@param flags the lower two bits of this int indicate the level of searching which should be
performed. 0 indicates only standard searching; 1, 2, and 3 indicate increasingly full searching
The remaining bits of this int are reserved, and should be set to zero.
*/

	AppFile[] findAppsByName( String appName, int maxToReturn, int flags ) {
		String		appPaths[];
		AppFile		retArray[], tempAppFile;

		Vector		tempAppVec;
		int			i, numApps;

		appPaths = AppUtilsMSVM.findAppsByName( appName, maxToReturn, flags );
		if ( appPaths == null )
			return null;

		tempAppVec = new Vector( appPaths.length, 1 );

		for ( i = 0; i < appPaths.length; i++ ) {
			if ( appPaths[ i ] == null )
				continue;

			try {
				tempAppFile = new AppFileMSVM( appPaths[ i ] );
				tempAppVec.addElement( tempAppFile );
			}
			catch ( Exception e ) {
				Trace.println( "main fabn, failed to create " + appPaths[ i ] );
			}
		}

		numApps = tempAppVec.size();
		if ( numApps < 1 )
			return null;

		retArray = new AppFile[ numApps ];
		tempAppVec.copyInto( retArray );

		return retArray;
	}

/**
Calls native code to search for apps associated with the given file extension.
The native code returns zero or more full paths, which are used to create an array of AppFileMSVM objects.
May return null if no apps could be found.
*/

	public AppFile[] findAppsByExtension( FileExtension ext, int maxToReturn, int flags ) {
		String		appPaths[];
		AppFile		retArray[], tempAppFile;
		Vector		tempAppVec;
		int			i, numApps;

		appPaths = AppUtilsMSVM.findAppsByExtension( ext.getString(), maxToReturn, flags );
		if ( appPaths == null )
			return null;

		tempAppVec = new Vector( 5, 5 );

		for ( i = 0; i < appPaths.length; i++ ) {
			if ( appPaths[ i ] == null )
				continue;

			try {
				tempAppFile = new AppFileMSVM( appPaths[ i ] );
				tempAppVec.addElement( tempAppFile );
			}
			catch ( Exception e ) {
				Trace.println( "main fabe, failed to create " + appPaths[ i ] + e );
			}
		}

		numApps = tempAppVec.size();
		if ( numApps < 1 )
			return null;

		retArray = new AppFile[ numApps ];

		for ( i = 0; i < numApps; i++ )
			retArray[ i ] = (AppFile) tempAppVec.elementAt( i );

		return retArray;
	}
}

