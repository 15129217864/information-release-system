/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CFILEUTILS_H
#define INC_CFILEUTILS_H

#include "comdefs.h"
#include "CString.h"

/*------------------------------------------------------------------------
CLASS
	CFileUtils

	Utilities for working with files and directories

DESCRIPTION
	Utilities for working with files and directories

------------------------------------------------------------------------*/

class CFileUtils {

public:

		///////////////////////
		//
		//  Returns TRUE if the given file exists, FALSE otherwise.
		//
		//  [in]	fullPath		The file to check.
		//
	static	BOOL	exists( const CStr *fullPath );

		///////////////////////
		//
		//  Creates a temporary file with a given extension.
		//
		//  [in]	csTempPath			Path to a writable directory in which the file will be created.
		//  [in]	csExt				The extension the file will have
		//  [out]	csOutFilePath		On exit, contains the full path of the temp file.
		//
	static	BOOL	createTempFile( const CStr *csTempPath, const CStr *csExt, CStr *csOutFilePath );

		///////////////////////
		//
		//  Deletes a file.
		//
		//  [in]	fullPath		The full path of the file to delete.
		//
	static	ErrCode	deleteFile( const CStr *fullPath );

		///////////////////////
		//
		//  Returns TRUE if the given drive is mounted, FALSE otherwise.
		//
		//  [in]	driveName		The drive name.
		//
	static	BOOL	isMountedDrive( const CStr *driveName );

		///////////////////////
		//
		//  Returns TRUE if the given file is an executable, FALSE otherwise.
		//
		//  [in]	fileName		The full path of the file to check.
		//
	static	BOOL	isExeFile( const CStr *fileName );

		///////////////////////
		//
		//  Creates a long file name.
		//
		//  [in]	shortFileName		A short ( or long ) path name.
		//  [out]	longFileName		On exit, contains the long path name.
		//
	static	ErrCode	makeLongFileName( const CStr *shortFileName, CStr *longFileName );

		///////////////////////
		//
		//  Gets the creation, backup, and modification dates of a file.
		//
		//  [in]	fullPath		The number of bytes to allocate.
		//  [out]	crDate			On exit, the creation date.
		//  [out]	acDate			On exit, the last access date.
		//  [out]	mdDate			On exit, the modification date.
		//
	static	ErrCode	getFileTime( const CStr *fullPath, FILETIME *crDate, FILETIME *acDate, FILETIME *mdDate );
};

#endif

