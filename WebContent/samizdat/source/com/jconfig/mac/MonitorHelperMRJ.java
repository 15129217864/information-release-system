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
Called by FileRegistryMRJ to implement its monitor related methods. Calls native methods in AppUtilsMRJ to
get information on the user's monitors, and then creates MonitorMRJ objects with the data returned from
native code.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class MonitorHelperMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
The maximum number of monitors getMonitors() returns
*/

	private static final int		kMaxMonitors = 50;

/**
Returns a list of all the user's monitors, or null if an error occurs.
*/

	static Monitor[] getMonitors() {
		Monitor		retVal[];
		int			monitorData[], numReturned[], theErr, i;

		monitorData = new int[ kMaxMonitors * AppUtilsMRJ.kMonitorInfoNumInts ];
		numReturned = new int[ 1 ];

		theErr = AppUtilsMRJ.getAllMonitorInfo( monitorData, kMaxMonitors, numReturned );

		if ( theErr != ErrCodes.ERROR_NONE || numReturned[ 0 ] < 1 )
			return null;

		retVal = new Monitor[ numReturned[ 0 ] ];
		for ( i = 0; i < numReturned[ 0 ]; i++ )
			retVal[ i ] = new MonitorMRJ( monitorData, i * AppUtilsMRJ.kMonitorInfoNumInts,
											AppUtilsMRJ.kMonitorInfoNumInts );

		return retVal;
	}

/**
Returns the user's main monitor, or null if an error occurs.
*/

	static Monitor getMainMonitor() {
		int			monitorData[], theErr;

		monitorData = new int[ AppUtilsMRJ.kMonitorInfoNumInts ];

		theErr = AppUtilsMRJ.getMainMonitorInfo( monitorData );

		if ( theErr != ErrCodes.ERROR_NONE )
			return null;

		return new MonitorMRJ( monitorData, 0, AppUtilsMRJ.kMonitorInfoNumInts );
	}

	private MonitorHelperMRJ() {
	}
}

