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
#define INC_SAppFinder_H

#include "comdefs.h"

/*------------------------------------------------------------------------
CLASS
	SAppFinder

	Searches for applications.

DESCRIPTION
	Searches for applications.

------------------------------------------------------------------------*/

class SAppFinder
{
public:

		///////////////////////
		//
		//	Find a series of apps with the given creator. The FSSpec for each app
		//	will be stored unbundled in the 'vRefsP', 'parIDsP', and 'namesP' arrays
		//	'vRefsP' and 'parIDsP' must have at least 'maxToReturn' elements,
		//	and 'namesP' must be (maxToReturn * maxNameLen) bytes long.
		//
		//  [in]	creator			The creator to search for
		//  [out]	vRefsP			On exit, contains a series of vRefNums
		//  [out]	parIDsP			On exit, contains a series of parIDs
		//  [out]	namesP			On exit, contains a series of Pascal strings
		//  [in]	maxToReturn		The max number to search for
		//  [in]	flags			unused
		//  [out]	numWritten		On exit, the number of apps found
		//  [in]	maxNameLen		The size of each name in 'namesP'
		//
	static	ErrCode findAPPLMultiple( long creator, long *vRefsP, long *parIDsP,
										StringPtr namesP, long maxToReturn, long flags,
										long *numWritten, long maxNameLen );

		///////////////////////
		//
		//	Find one app with the given creator. If found, returns a FSSpec for
		//	the app, unbundled in the 'vRef', 'parID', and 'pName' arguments.
		//
		//  [in]	creator			The creator to search for
		//  [out]	vRef			On exit, contains the vRefNums of the app
		//  [out]	parID			On exit, contains the parIDs of the app
		//  [out]	pName			On exit, contains the Pascal name of the app
		//							Must be a Str255
		//  [in]	flags			if non-zero, a more comprehensive search will be performed
		//
	static	ErrCode findAPPLSingle( long creator, long *vRef, long *parID,
									StringPtr pName, long flags );

private:

#if defined(__osx__)
	static ErrCode	findAPPLForInfoOSX( long creator, long *vRef, long *parID, StringPtr pName, long maxNameLen );
#endif

};

#endif
