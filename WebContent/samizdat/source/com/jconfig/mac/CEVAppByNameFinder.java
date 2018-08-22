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

/**
Searches through a ConfigList for apps which match a given name.

<P>
This class is used by FileRegistyMRJ as follows:

<PRE>
	cev = new CEVAppByNameFinder( appFinder, appName, maxToReturn, flags );

	FileRegisty.iterate( cev );

	appList = cev.getApps();
</PRE>

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class CEVAppByNameFinder implements ConfigEntryVisitor {
	AppFinderMRJ		appFinder;
	String				appName;
	AppFile				tempAppArray[];
	int					maxToReturn, numWritten, tempCreatorArray[], flags;

/**
Search for apps with the given name.
@param fndr used to search for apps by creator
@param name the name to search for.
@param max the max number to return
@param fgs the search level; used with the AppFinderMRJ object
*/

	CEVAppByNameFinder( AppFinderMRJ fndr, String name, int max, int fgs ) {
		appFinder = fndr;
		appName = name.toLowerCase();
		maxToReturn = max;

		flags = fgs;
		numWritten = 0;
		tempAppArray = new AppFile[ maxToReturn ];
		tempCreatorArray = new int[ maxToReturn ];
	}

/**
This will be called for each ConfigEntry of the ConfigList.
If an app with a matching name is found, get the app's creator code from the ConfigEntry,
and call the AppFinderMRJ object to search for the app by its creator code.
*/

	public void visit( ConfigEntry fd ) {
		AppFile		createdApps[];
		int			i, testCreator;
		boolean		bFound;

		if ( numWritten >= maxToReturn )
			return;

		if ( ( fd.getAppName().toLowerCase() ).indexOf( appName ) >= 0 ) {
			testCreator = fd.getFinderInfo().getCreator();
			
			for ( i = 0, bFound = false; i < numWritten; i++ ) {
				if ( testCreator == tempCreatorArray[ i ] ) {
					bFound = true;
					break;
				}
			}
			
			if ( !bFound ) {
				createdApps = appFinder.findAPPL( testCreator, 1, flags );

				if ( createdApps != null && createdApps[ 0 ] != null ) {
					tempCreatorArray[ numWritten ] = testCreator;
					tempAppArray[ numWritten ] = createdApps[ 0 ];
					++numWritten;
				}
			}
		}
	}

/**
Return the previously constructed list of apps.
*/

	AppFile[] getApps() {
		AppFile		retArray[];
		int			i;

		if ( numWritten < 1 )
			return null;

		retArray = new AppFile[ numWritten ];

		for ( i = 0; i < numWritten; i++ )
			retArray[ i ] = tempAppArray[ i ];

		return retArray;
	}
}

