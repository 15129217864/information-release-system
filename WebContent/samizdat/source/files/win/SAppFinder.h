/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SAppFinder_H
#define	INC_SAppFinder_H

#include "comdefs.h"
#include "CString.h"
#include "CStringVector.h"
#include "AppData.h"

/*------------------------------------------------------------------------
CLASS
	SAppFinder

	Routines for finding applications.

DESCRIPTION
	Routines for finding applications.

------------------------------------------------------------------------*/

class SAppFinder
{
public:
	
		///////////////////////
		//
		//  Searches the Registry for entries which mention one or more applications, and returns the registry entries in
		//	'VAT' format.
		//	A 'VAT' consists of a set of four strings: an extension, a Registry key, a verb, and a command line.
		//	Ex. { ".txt", "txtfile\\shell\\open\\command", "open", "c:\windows\notepad %1" }
		//
		//  [in]	fullPaths		The full paths of the applications.
		//  [in]	fileName		The name of the application. Not used.
		//  [out]	vatVec			On exit, contains the returned VATs.
		//							Each application which is searched for takes up 4 * maxToReturn
		//							entries in the vector. Thus, this vector might be sparse.
		//							This vector will have 4 * fullPaths.length() * maxToReturn entries.
		//  [in]	maxToReturn		For each member of fullPaths, the maximum number of VATs to return.
		//  [out]	numReturned		On exit, contains the number of entries for each application,
		//							in the same order as the applications are listed in fullPaths.
		//
	static	ErrCode iNativeFindVerbs( CStringVector *fullPaths, const CStr *fileName, CStringVector *vatVec,
									long maxToReturn, long *numReturned ); 
	
		///////////////////////
		//
		//	Finds apps by name.
		//
		//  [in]	appName			The application name.
		//	[in]	defaultDir		the default directory
		//  [in]	maxToReturn		The maximum number of apps to return
		//  [in]	flags			The lower 2 bits indicate the search level,
		//							with 0 being the fastest and 3 being the most extensive
		//  [out]	pRetTable		On exit, contains the found applications.
		//
	static	ErrCode iNativeFindAppsByName( const CStr *appName, const CStr *defaultDir, long maxToReturn,
											long flags, CStringVector *pRetTable );
	
		///////////////////////
		//
		//	Finds apps by extension.
		//
		//  [in]	fileExt			The extension.
		//	[in]	tempDir			The path to a writable temporary directory.
		//  [in]	maxToReturn		The maximum number of apps to return
		//  [in]	flags			Various flags
		//  [out]	pRetTable		On exit, contains the found applications.
		//
	static	ErrCode iNativeFindAppsByExtension( const CStr *fileExt, const CStr *tempDir, long maxToReturn,
												long flags, CStringVector *pRetTable );
	
protected:

	static	const CStr	*gcsRegKeyAppPath, *gcsExeExtension;

	static	ErrCode createPathsVectors( CStringVector *csvPaths,
											CStringVector *csvLongPaths, CStringVector *csvShortPaths );
	static	ErrCode doVerbLists( CStringVector *csvShortPaths, CStringVector *csvLongPaths,
										CStringVector *vatVec, long maxToReturn, long *numReturned,
										CStr *csExtension, CStr *csRegKey, CStr *csShellKey, HKEY hShellKey );
	static	ErrCode iNativeFindAppsByNameLevel0( const CStr *appName, const CStr *defaultDir, long maxToReturn,
													long flags, CStringVector *pRetTable );
	static	ErrCode iNativeFindAppsByNameLevel1( const CStr *appName, const CStr *defaultDir, long maxToReturn,
													long flags, CStringVector *pRetTable );
	static	ErrCode iNativeFindAppsByNameAppPaths( const CStr *appName, const CStr *defaultDir, long maxToReturn,
													long flags, CStringVector *pRetTable );
	static	ErrCode iNativeFindAppsByNameFindExe( const CStr *appName, const CStr *defaultDir, long maxToReturn,
													long flags, CStringVector *pRetTable );
	static	ErrCode	altFindExecutable( const CStr *fileExt, CStr *retExe );
};

#endif

