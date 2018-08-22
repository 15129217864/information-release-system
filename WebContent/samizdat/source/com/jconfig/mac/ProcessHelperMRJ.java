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

import java.util.Vector;
import java.io.File;

/**
Contains a static method which returns a list of the running processes.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class ProcessHelperMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Returns a list of the running processes.
Creates a series of arrays to hold the return values, and then calls AppUtilsMRJ.getProcesses()
For each process returned by the AppUtilsMRJ.getProcesses method, tries to create an AppProcessMRJ object
@param maxToReturn the maximum number of processes to return
@param flags the same flags as to the FileRegistry.getProcesses() method
*/

	static AppProcess[] getProcesses( int maxToReturn, int flags ) {
		AppFile			appFile;
		AppProcess		retArray[], appProcess;
		Vector			processes;
		int				i, theErr, vRef[], parID[], psnLo[], psnHi[], proFlags[], numRet[], tempPSN[], vRefAndParID[];
		byte			pNames[], tempName[];

		numRet = new int[ 1 ];
		vRef = new int[ maxToReturn ];
		parID = new int[ maxToReturn ];
		psnLo = new int[ maxToReturn ];
		psnHi = new int[ maxToReturn ];
		proFlags = new int[ maxToReturn ];
		pNames = new byte[ maxToReturn * AppUtilsMRJ.kGetProcessesNameLen ];

		theErr = AppUtilsMRJ.getProcesses( maxToReturn, flags, numRet, vRef, parID, pNames, psnLo, psnHi, proFlags );
		if ( theErr != ErrCodes.ERROR_NONE )
			return null;

		tempName = new byte[ AppUtilsMRJ.kGetProcessesNameLen ];
		tempPSN = new int[ 2 ];
		vRefAndParID = new int[ 2 ];
		processes = new Vector( numRet[ 0 ], 1 );

		for ( i = 0; i < numRet[ 0 ]; i++ ) {
			try {
				tempPSN[ AppUtilsMRJ.kPSNLoOffset ] = psnLo[ i ];
				tempPSN[ AppUtilsMRJ.kPSNHiOffset ] = psnHi[ i ];
				System.arraycopy( pNames, AppUtilsMRJ.kGetProcessesNameLen * i, tempName, 0, AppUtilsMRJ.kGetProcessesNameLen );
				appFile = DOFactoryMRJ.createAppFile( vRef[ i ], parID[ i ], tempName );
			}
			catch ( Exception e ) {
				Trace.println( "ProcessHelperMRJ.getProcess: " + e );

				try {
					if ( PlatformInfoMRJ.getInstance().isPlatformMRJOSX() ) {
						tempPSN[ AppUtilsMRJ.kPSNLoOffset ] = psnLo[ i ];
						tempPSN[ AppUtilsMRJ.kPSNHiOffset ] = psnHi[ i ];
						theErr = XUtilsOSX.getProcessBundleLocation( tempPSN, vRefAndParID, tempName );
						if ( theErr != ErrCodes.ERROR_NONE )
							continue;

						appFile = DOFactoryMRJ.createAppFile( vRefAndParID[ 0 ], vRefAndParID[ 1 ], tempName );
					}
					else
						continue;
				}
				catch ( Exception e2 ) {
					continue;
				}
			}

			appProcess = new AppProcessMRJ( appFile, tempPSN, null );
			processes.addElement( appProcess );
		}

		if ( processes.size() < 1 )
			return null;

		retArray = new AppProcess[ processes.size() ];
		processes.copyInto( retArray );

		return retArray;
	}

	private ProcessHelperMRJ() {
	}
}

