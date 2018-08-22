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

/**
Called by FileRegistryMSVM to implement its monitor related methods. Calls native methods in AppUtilsMSVM to
get information on the user's monitors, and then creates MonitorMSVM objects with the data returned from
native code.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class MonitorHelperMSVM {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private static final int		kMaxMonitors = 50;

	static Monitor[] getMonitors() {
		Monitor		retVal[];
		int			monitorData[], numReturned[], theErr, i;

		monitorData = new int[ kMaxMonitors * AppUtilsMSVM.kMonitorInfoNumInts ];
		numReturned = new int[ 1 ];

		theErr = AppUtilsMSVM.getAllMonitorInfo( monitorData, kMaxMonitors, numReturned );

		if ( theErr != ErrCodes.ERROR_NONE || numReturned[ 0 ] < 1 )
			return null;

		retVal = new Monitor[ numReturned[ 0 ] ];
		for ( i = 0; i < numReturned[ 0 ]; i++ )
			retVal[ i ] = new MonitorMSVM( monitorData, i * AppUtilsMSVM.kMonitorInfoNumInts,
											AppUtilsMSVM.kMonitorInfoNumInts );

		return retVal;
	}

	static Monitor getMainMonitor() {
		int			monitorData[], theErr;

		monitorData = new int[ AppUtilsMSVM.kMonitorInfoNumInts ];

		theErr = AppUtilsMSVM.getMainMonitorInfo( monitorData );

		if ( theErr != ErrCodes.ERROR_NONE )
			return null;

		return new MonitorMSVM( monitorData, 0, AppUtilsMSVM.kMonitorInfoNumInts );
	}

	private MonitorHelperMSVM() {
	}
}

