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
import java.io.File;

/**
A singleton which contains a method which iterates over the files in a directory.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class FileIteratorNix {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Return value from the private method presentToDiskFilter().
*/

	private static final int		kPTDFGotFalse = 0;

/**
Return value from the private method presentToDiskFilter().
*/

	private static final int		kPTDFGotTrue = 1;

/**
Return value from the private method presentToDiskFilter().
*/

	private static final int		kPTDFNotAMatch = 2;

/**
Iterates over the files in the given directory, and presents them to a DiskFilter object.
Uses the 'nativeFindFirstFile' and related methods to obtain the list of files.
Calls 'presentToDiskFilter' to present each files to the DiskFilter.
@param thePath the full path to the directory.
@param flags indicates what types of files to present to the DiskFilter.
@param flags one or more of the flags defined in DiskFilter.java
@param maxToIterate indicates the maximum number of files to present.
*/

	public static int runDiskFilter( String thePath, DiskFilter filter, int flags, int maxToIterate ) {
		File			fl;
		String			fileList[];
		int				i, len, presVal, theErr;

		theErr = ErrCodes.ERROR_NONE;

		fl = new File( thePath );
		if ( !fl.exists() || !fl.isDirectory() )
			throw new IllegalArgumentException( "not exists or not directory" );	//	return ErrCodes.ERROR_PARAM;

		fileList = fl.list();
		if ( fileList == null )
			throw new IllegalArgumentException( "fileList==null" );	//	return ErrCodes.ERROR_PARAM;

		len = fileList.length;
		if ( len > maxToIterate )
			len = maxToIterate;

		for ( i = 0; i < len; i++ ) {
			presVal = presentToDiskFilter( filter, flags, fl, fileList[ i ] );
			if ( presVal == kPTDFGotFalse ) {
				theErr = ErrCodes.ERROR_NONE;
				break;
			}
		}

		return theErr;
	}

/**
Called by 'runDiskFilter()' to present the given file to a DiskFilter object.
Calls the DiskFilter's visit() method with the file.
@param name the name of the file
@param path the directory containing the file
@param attrs the attributes of the file
@param flags one or more of the flags defined in DiskFilter.java
*/

	private static int presentToDiskFilter( DiskFilter filter, int flags, File par, String name ) {
		File			fl;
		DiskObject		dobj;
		String			driveName;
		boolean			bRet, bIgnoreHidden, bIgnoreFiles, bIgnoreFolders, bIgnoreAliases, bIgnoreNameLocked;

		bIgnoreHidden = ( ( flags & DiskFilter.IGNORE_HIDDEN ) != 0 );
		bIgnoreNameLocked = ( ( flags & DiskFilter.IGNORE_NAME_LOCKED ) != 0 );
		bIgnoreFolders = ( ( flags & DiskFilter.IGNORE_FOLDERS ) != 0 );
		bIgnoreFiles = ( ( flags & DiskFilter.IGNORE_FILES ) != 0 );
		bIgnoreAliases = ( ( flags & DiskFilter.IGNORE_ALIASES ) != 0 );

		if ( name.equals( "." ) || name.equals( ".." ) )
			return kPTDFNotAMatch;

		if ( bIgnoreHidden && name.startsWith( "." ) )
			return kPTDFNotAMatch;

		try {
			fl = new File( par, name );
			if ( fl.isDirectory() ) {
				if ( bIgnoreFolders )
					return kPTDFNotAMatch;
			}
			else {
				if ( bIgnoreFiles )
					return kPTDFNotAMatch;
			}

			if ( bIgnoreAliases && AppUtilsNix.isLinkFile( fl ) )
				return kPTDFNotAMatch;

			dobj = DOCreatorNix.createDiskObject( fl );
		}
		catch ( Exception e ) {
			dobj = null;
		}

		if ( dobj == null )
			return kPTDFNotAMatch;

		bRet = filter.visit( dobj );

		return bRet ? kPTDFGotTrue : kPTDFGotFalse;
	}

	private FileIteratorNix() {}
}

