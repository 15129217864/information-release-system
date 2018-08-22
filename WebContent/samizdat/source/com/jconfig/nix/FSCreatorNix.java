/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig.nix;

import com.jconfig.*;

/**
Returns:

<UL>
<LI>a list of all the mounted file systems
<LI>the file system which contains a given file/folder
<LI>the file system which contains a given volume
<LI>the total space on all mounted file systems
</UL>

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class FSCreatorNix {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
The minimum length of the array passed to getFileSystemTotalCapInfo
*/

	static final int		FS_TOTAL_CAP_INFO_LEN = 2;

/**
On return from getFileSystemTotalCapInfo, the offset of the total capacity
*/

	static final int		FS_TOTAL_CAP_OFFSET = 0;

/**
On return from getFileSystemTotalCapInfo, the offset of the total free space
*/

	static final int		FS_TOTAL_FREE_SPACE_OFFSET = 1;

/**
Returns a list of the mounted file systems, or null if an error occurs.
@param flags ignored; set to 0
*/

 	static FileSystem[] getFileSystems( int maxToReturn, int flags ) {
		FileSystem		systems[];
		String			retQuads[], fn, dr, ty, op;
		int				i, numReturned[], err;

		retQuads = new String[ AppUtilsNix.kGetMntEntRetArrayLen * maxToReturn ];
		numReturned = new int[ 1 ];

		err = AppUtilsNix.getMntEnt( retQuads, maxToReturn, numReturned );
		if ( err != ErrCodes.ERROR_NONE || numReturned[ 0 ] < 1 ) {
			Trace.println( "gme returned " + err );
			return null;
		}

		systems = new FileSystem[ numReturned[ 0 ] ];
		for ( i = 0; i < numReturned[ 0 ]; i++ ) {
			fn = retQuads[ ( 4 * i ) + AppUtilsNix.kGetMntEntOffs_fsname ];
			dr = retQuads[ ( 4 * i ) + AppUtilsNix.kGetMntEntOffs_dir ];
			ty = retQuads[ ( 4 * i ) + AppUtilsNix.kGetMntEntOffs_type ];
			op = retQuads[ ( 4 * i ) + AppUtilsNix.kGetMntEntOffs_opts ];

			systems[ i ] = new FileSystemNix( fn, dr, ty, op );
		}

		return systems;
	}


/**
Returns the FileSystem on which a file is located, or null if an error occurs.
After getting the dev ID of the file, we get a list of the FileSystems, and search through
the list looking for a file system which matches that dev ID

@param filePath the full path of the file
@param flags ignored; set to 0
*/

	static FileSystem getFileFileSystem( String path, int flags ) {
		FileSystem		systems[];
		String			mountPath;
		int				i, len, theErr, retArray[], datesArray[], matchDev;

		retArray = new int[ AppUtilsNix.kStatRetArrayLen ];
		datesArray = new int[ AppUtilsNix.kDateBundleArrayLen ];

		theErr = AppUtilsNix.stat( path, retArray, datesArray );
		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "getmp=" + theErr + " for " + path );
			return null;
		}

		matchDev = retArray[ AppUtilsNix.kStatOffs_dev ];

		systems = getFileSystems( 30, 0 );
		if ( systems == null ) {
			Trace.println( "getmp2" );
			return null;
		}
	
//		Trace.println( "checking against " + matchDev );

		len = systems.length;
		for ( i = 0; i < len; i++ ) {
			if ( systems[ i ] == null ) {
				Trace.println( "gmp, at " + i + " is null" );
				continue;
			}

			mountPath = ( (FileSystemNix) systems[ i ] ).getDir();
			if ( mountPath == null ) {
				Trace.println( "gmp, gdn null at " + i + " " + systems[ i ] );
				continue;
			}

			theErr = AppUtilsNix.stat( mountPath, retArray, datesArray );
//			Trace.println( "checking " + mountPath + "=" + retArray[ AppUtilsNix.kStatOffs_dev ] );
			if ( theErr == ErrCodes.ERROR_NONE && matchDev == retArray[ AppUtilsNix.kStatOffs_dev ] )
				return systems[ i ];
		}
		
		Trace.println( "gfs nada for " + path );

		return null;
	}

/**
Returns the FileSystem on which a volume is located.
Calls through to getFileFileSystem.
*/

	static FileSystem getVolumeFileSystem( String driveName, int flags ) {
		return getFileFileSystem( driveName, flags );
	}

/**
Sums up the capacity and free space on all file systems.
*/

	static int getFileSystemTotalCapInfo( long capInfo[] ) {
		FileSystem			systems[];
		long				tempCap[];
		int					theErr, len, i;

		capInfo[ FS_TOTAL_CAP_OFFSET ] = 0;
		capInfo[ FS_TOTAL_FREE_SPACE_OFFSET ] = 0;
		
		systems = getFileSystems( 30, 0 );
		if ( systems == null )
			return -1;

		tempCap = new long[ FileSystem.FS_GETCAP_LENGTH ];
		tempCap[ FileSystem.FS_GETCAP_CAP_OFFSET ] = 0;
		tempCap[ FileSystem.FS_GETCAP_FREESPACE_OFFSET ] = 0;

		len = systems.length;
		for ( i = 0; i < len; i++ ) {
			if ( systems[ i ] != null ) {
				theErr = systems[ i ].getCapacity( tempCap );
				if ( theErr == ErrCodes.ERROR_NONE ) {
					capInfo[ FS_TOTAL_CAP_OFFSET ] += tempCap[ FileSystem.FS_GETCAP_CAP_OFFSET ];
					capInfo[ FS_TOTAL_FREE_SPACE_OFFSET ] += tempCap[ FileSystem.FS_GETCAP_FREESPACE_OFFSET ];
				}							   
			}
		}

		return ErrCodes.ERROR_NONE;
	}
}

