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
Contains the static method 'getProcesses()', which is called by the FileRegistryMSVM to return a list of the 
currently running processes.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class ProcessHelperMSVM {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	static final int kAppDataHWNDOffset = 0;
	static final int kAppDataHProcessOffset = 1;
	static final int kAppDataHThreadOffset = 2;
	static final int kAppDataProcessIDOffset = 3;
	static final int kAppDataThreadIDOffset = 4;
	static final int kAppDataCntUsageOffset = 5;
	static final int kAppDataDefaultHeapIDOffset = 6;
	static final int kAppDataModuleIDOffset = 7;
	static final int kAppDataCntThreadsOffset = 8;
	static final int kAppDataParentProcessIDOffset = 9;
	static final int kAppDataPriClassBaseOffset = 10;
	static final int kAppDataFlagsOffset = 11;
	static final int kAppDataNumInts = 12;

	static AppProcess[] getProcesses( int maxToReturn, int flags ) {
		AppFileMSVM			appFile;
		AppProcess			retVal[];
		Vector				appProcessVec, vatVector;
		String				fullPathArray[];
		int					i, theErr, numProcs, numToReturn,
							cntUsageArray[], th32ProcessIDArray[],
							th32DefaultHeapIDArray[], th32ModuleIDArray[],
							cntThreadsArray[], th32ParentProcessIDArray[],
							pcPriClassBaseArray[], dwFlagsArray[],
							dwThreadIDArray[], hWndArray[],
							numReturned[], appDataArray[];

		cntUsageArray = new int[ maxToReturn ]; 
		th32ProcessIDArray = new int[ maxToReturn ];
		th32DefaultHeapIDArray = new int[ maxToReturn ];
		th32ModuleIDArray = new int[ maxToReturn ];
		cntThreadsArray = new int[ maxToReturn ]; 
		th32ParentProcessIDArray = new int[ maxToReturn ];
		pcPriClassBaseArray = new int[ maxToReturn ]; 
		dwFlagsArray = new int[ maxToReturn ];
		dwThreadIDArray = new int[ maxToReturn ]; 
		hWndArray = new int[ maxToReturn ];
		fullPathArray = new String[ maxToReturn ]; 
		numReturned = new int[ 1 ];

		theErr = AppUtilsMSVM.getAllProcessInfo( cntUsageArray, th32ProcessIDArray,
									th32DefaultHeapIDArray, th32ModuleIDArray,
									cntThreadsArray, th32ParentProcessIDArray,
									pcPriClassBaseArray, dwFlagsArray,
									dwThreadIDArray, hWndArray,
									fullPathArray, maxToReturn, numReturned );

		if ( theErr != ErrCodes.ERROR_NONE )
			throw new OSException( "can't getProcesses=" + theErr );

		numProcs = numReturned[ 0 ];
		appProcessVec = new Vector( numProcs );

		if ( fullPathArray.length != numProcs ) {
			String			tempArray[];

			tempArray = new String[ numProcs ];
			System.arraycopy( fullPathArray, 0, tempArray, 0, tempArray.length );
			fullPathArray = tempArray;
		}

		vatVector = CommandLineUtilsMSVM.findVerbs( fullPathArray );

		appDataArray = new int[ kAppDataNumInts ];

		for ( i = 0; i < numProcs; i++ ) {
			try {
				appDataArray[ kAppDataHWNDOffset ] = hWndArray[ i ];
				appDataArray[ kAppDataHProcessOffset ] = 0;
				appDataArray[ kAppDataHThreadOffset ] = 0;
				appDataArray[ kAppDataProcessIDOffset ] = th32ProcessIDArray[ i ];
				appDataArray[ kAppDataThreadIDOffset ] = dwThreadIDArray[ i ];
				appDataArray[ kAppDataCntUsageOffset ] = cntUsageArray[ i ];
				appDataArray[ kAppDataDefaultHeapIDOffset ] = th32DefaultHeapIDArray[ i ];
				appDataArray[ kAppDataModuleIDOffset ] = th32ModuleIDArray[ i ];
				appDataArray[ kAppDataCntThreadsOffset ] = cntThreadsArray[ i ];
				appDataArray[ kAppDataParentProcessIDOffset ] = th32ParentProcessIDArray[ i ];
				appDataArray[ kAppDataPriClassBaseOffset ] = pcPriClassBaseArray[ i ];
				appDataArray[ kAppDataFlagsOffset ] = dwFlagsArray[ i ];

				appFile = new AppFileMSVM( fullPathArray[ i ], (RegCommandMSVM[]) vatVector.elementAt( i ) );

				AppProcess appProcess = new AppProcessMSVM( appFile, appDataArray, null );

				appProcessVec.addElement( appProcess );
			}
			catch ( Exception e ) {
				Trace.println( "CAN'T CREATE PROCESS " + e );
				e.printStackTrace( Trace.getOut() );
			}
		}

		numToReturn = appProcessVec.size();
		retVal = new AppProcess[ numToReturn ];

		for ( i = 0; i < numToReturn; i++ )
			retVal[ i ] = (AppProcess) appProcessVec.elementAt( i );

		return retVal;
	}

	private ProcessHelperMSVM() {
	}
}

