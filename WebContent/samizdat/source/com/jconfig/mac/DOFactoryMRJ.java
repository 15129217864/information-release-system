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
import java.util.Date;
import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;

/**
@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DOFactoryMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private static final int		kAliasFileTestBits = AppUtilsMRJ.kCategoryOSXAliasFileBit |
															AppUtilsMRJ.kCategoryOSXSymlinkBit;

	private static final int		kCFMAppMask = AppUtilsMRJ.kCategoryOSXApplicationBit |
															AppUtilsMRJ.kCategoryOSXClassicAppBit;

	static DiskVolume createDiskVolume( int vRef ) {
		return new DiskVolumeMRJ( vRef );
	}

	static DiskObject createDiskObject( int vRef, int parID, byte pName[] )
	throws DiskFileException, FileNotFoundException {
		int			theErr, category[];

		category = new int[ 1 ];

		theErr = AppUtilsMRJ.getFileCategory( vRef, parID, pName, category );
		if ( theErr != ErrCodes.ERROR_NONE )
			throw new OSException( "can't getFileCategory=" + theErr );

		AppUtilsMRJ.dumpCategory( Trace.getOut(), category[ 0 ] );

		if ( ( category[ 0 ] & AppUtilsMRJ.kCategoryOSXExtendedInfoBit ) != 0 ) {
			if ( ( category[ 0 ] & AppUtilsMRJ.kCategoryOSXVolumeBit ) != 0 ) {
				Trace.println( "    ----> returning DiskVolumeMRJ" );
				return new DiskVolumeMRJ( vRef );
			}

			else if ( ( category[ 0 ] & AppUtilsMRJ.kCategoryOSXPlainFileBit ) != 0 ) {
				Trace.println( "    ----> returning DiskFileMRJ" );
				return new DiskFileMRJ( vRef, parID, pName, false );
			}

			else if ( ( category[ 0 ] & kCFMAppMask ) == kCFMAppMask ) {
				Trace.println( "    ----> returning AppFileMRJ" );
				return new AppFileMRJ( vRef, parID, pName );
			}

			else if ( ( category[ 0 ] & AppUtilsMRJ.kCategoryOSXApplicationBit ) != 0 ) {
				Trace.println( "    ----> returning AppFileOSX" );
				return new AppFileOSX( vRef, parID, pName, category[ 0 ] );
			}

			else if ( ( category[ 0 ] & kAliasFileTestBits ) != 0 ) {
				Trace.println( "    ----> returning DiskAliasMRJ" );
				return new DiskAliasMRJ( vRef, parID, pName );
			}

			else if ( ( category[ 0 ] & AppUtilsMRJ.kCategoryOSXContainerBit ) != 0 ) {
				Trace.println( "    ----> returning DiskFileMRJ" );
				return new DiskFileMRJ( vRef, parID, pName, true );
			}

			else {
				throw new IllegalArgumentException( "can't create file from unknown type= " +
														category[ 0 ] + ", " +
														AppUtilsMRJ.createFullPathName( vRef, parID, pName ) );
			}
		}
		else {
			switch ( category[ 0 ] ) {
				case AppUtilsMRJ.kCategoryVolume:
					{
						Trace.println( "    ----> returning DiskVolumeMRJ" );
						return new DiskVolumeMRJ( vRef );
					}

				case AppUtilsMRJ.kCategoryDirectory:
					{
						Trace.println( "    ----> returning DiskFileMRJ" );
						return new DiskFileMRJ( vRef, parID, pName, true );
					}

				case AppUtilsMRJ.kCategoryFile:
					{
						Trace.println( "    ----> returning DiskFileMRJ" );
						return new DiskFileMRJ( vRef, parID, pName, false );
					}

				case AppUtilsMRJ.kCategoryAlias:
					{
						Trace.println( "    ----> returning DiskAliasMRJ" );
						return new DiskAliasMRJ( vRef, parID, pName );
					}

				default:
					throw new IllegalArgumentException( "can't create file from unknown type= " +
														category[ 0 ] + ", " +
														AppUtilsMRJ.createFullPathName( vRef, parID, pName ) );
			}
		}
	}

	static AppFile createAppFile( int vRef, int parID, byte pName[] )
	throws DiskFileException, FileNotFoundException {
		int			theErr, category[];

		category = new int[ 1 ];

		theErr = AppUtilsMRJ.getFileCategory( vRef, parID, pName, category );
		if ( theErr != ErrCodes.ERROR_NONE )
			throw new OSException( "can't getFileCategory=" + theErr );

		AppUtilsMRJ.dumpCategory( Trace.getOut(), category[ 0 ] );

		if ( ( category[ 0 ] & AppUtilsMRJ.kCategoryOSXExtendedInfoBit ) != 0 ) {
			if ( ( category[ 0 ] & AppUtilsMRJ.kCategoryOSXVolumeBit ) != 0 )
				throw new IllegalArgumentException( "can't create app from volume " +
														AppUtilsMRJ.createFullPathName( vRef, parID, pName ) );

			else if ( ( category[ 0 ] & kCFMAppMask ) == kCFMAppMask ) {
				Trace.println( "    ----> returning AppFileMRJ" );
				return new AppFileMRJ( vRef, parID, pName );
			}

			else if ( ( category[ 0 ] & AppUtilsMRJ.kCategoryOSXPlainFileBit ) != 0 )
				throw new IllegalArgumentException( "can't create app from plain file " +
														AppUtilsMRJ.createFullPathName( vRef, parID, pName ) );

			else if ( ( category[ 0 ] & AppUtilsMRJ.kCategoryOSXApplicationBit ) != 0 ) {
				Trace.println( "    ----> returning AppFileOSX" );
				return new AppFileOSX( vRef, parID, pName, category[ 0 ] );
			}

			else if ( ( category[ 0 ] & kAliasFileTestBits ) != 0 )
				throw new IllegalArgumentException( "can't create app from alias " +
														AppUtilsMRJ.createFullPathName( vRef, parID, pName ) );

			else if ( ( category[ 0 ] & AppUtilsMRJ.kCategoryOSXContainerBit ) != 0 )
				throw new IllegalArgumentException( "can't create app from plain directory " +
														AppUtilsMRJ.createFullPathName( vRef, parID, pName ) );

			else
				throw new IllegalArgumentException( "can't create app from unknown type= " + category[ 0 ] + ", " +
														AppUtilsMRJ.createFullPathName( vRef, parID, pName ) );
		}
		else {
			switch ( category[ 0 ] ) {
				case AppUtilsMRJ.kCategoryVolume:
					throw new IllegalArgumentException( "can't create app from volume2 " +
														AppUtilsMRJ.createFullPathName( vRef, parID, pName ) );

				case AppUtilsMRJ.kCategoryDirectory:
					{
						PlatformInfoMRJ		info;

						info = PlatformInfoMRJ.getInstance();
						
						if ( info.isPlatformMRJOSX() ) {
							Trace.println( "    ----> returning AppFileOSX" );
							return new AppFileOSX( vRef, parID, pName, 0 );
						}
						else {
							throw new IllegalArgumentException( "can't create app from plain directory2 " +
																AppUtilsMRJ.createFullPathName( vRef, parID, pName ) );
						}
					}

				case AppUtilsMRJ.kCategoryFile:
					{
						Trace.println( "    ----> returning AppFileMRJ" );
						return new AppFileMRJ( vRef, parID, pName );
					}

				case AppUtilsMRJ.kCategoryAlias:
					throw new IllegalArgumentException( "can't create app from alias2 " +
														AppUtilsMRJ.createFullPathName( vRef, parID, pName ) );

				default:
					throw new IllegalArgumentException( "can't create app from unknown type2= " + category[ 0 ] + ", " +
														AppUtilsMRJ.createFullPathName( vRef, parID, pName ) );
			}
		}
	}

/**
Given arrays containing information on application's files, returns an array of AppFile's created
using that information.
Returns null if none of the AppFile's could be created.

@param vRefs the vRefNums of the files
@param parIDs the parIDs of the files
@param pNames the names of the files. Each name is a Pascal string which occupies 'num' elements
@param num the number of AppFiles to create. The actual number returned may be less than this if
an error occurs for some of the applications.
@param nameLen the length of each name in the 'pNames' array
*/

	static AppFile[] createAppFileArray( int vRefs[], int parIDs[], byte pNames[], int num, int nameLen ) {
		AppFile			retArray[], appFile;
		Vector			tempVector;
		int				i;
		byte			tempName[];

		if ( num <= 0 )
			return null;

		tempVector = new Vector( num, 1 );
		tempName = new byte[ nameLen ];

		for ( i = 0; i < num; i++ ) {
			System.arraycopy( pNames, nameLen * i, tempName, 0, nameLen );

			try {
				appFile = DOFactoryMRJ.createAppFile( vRefs[ i ], parIDs[ i ], tempName );
				tempVector.addElement( appFile );
			}
			catch ( Exception e ) {
				Trace.println( "DOFactoryMRJ.createAppFileArray: for vref=" + vRefs[ i ] +
																", parID=" + parIDs[ i ] +
																", name=" + JUtils.pascalBytesToString( tempName, 0 ) +
																", e=" + e );
			}
		}

		if ( tempVector.size() < 1 )
			return null;

		retArray = new AppFile[ tempVector.size() ];

		tempVector.copyInto( retArray );

		return retArray;
	}
}

