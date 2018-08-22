/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SAppUtilsOSX_H
#define INC_SAppUtilsOSX_H

#include "comdefs.h"
#include "CString.h"
#include "CFSpec.h"
#include <Processes.h>


/*------------------------------------------------------------------------
CLASS
	SAppUtilsOSX

	OSX utility functions.

DESCRIPTION
	OSX utility functions.

------------------------------------------------------------------------*/

class SAppUtilsOSX
{
public:

		///////////////////////
		//
		//	Converts an FSSpec into a POSIX path.
		//
		//  [in]	spec			The input FSSpec
		//  [out]	outPath			On exit, contains the POSIX path of the spec
		//
	static	ErrCode getPOSIXPath( CFSpec *spec, CStr *outPath );

		///////////////////////
		//
		//	Wraps the OSX function GetProcessBundleLocation.
		//
		//  [in]	psn				The input PSN
		//  [out]	spec			On exit, contains the spec of the bundle
		//
	static	ErrCode getProcessBundleLocation( ProcessSerialNumber *psn, FSSpec *spec );

		///////////////////////
		//
		//	Wraps the OSX function LSFindApplicationForInfo.
		//
		//  [in]	inCreator		The input creator code, can be kLSUnknowmCreator
		//  [in]	inBundleID		The input bundle ID, can be NULL
		//  [in]	inName			The input application name, can be NULL
		//  [out]	outSpec			On exit, contains the spec of the app
		//
	static	ErrCode findApplicationForInfo( OSType inCreator, const CStr *inBundleID, const CStr *inName, FSSpec *outSpec );

		///////////////////////
		//
		//	Wraps the OSX function LSGetApplicationForInfo.
		//
		//  [in]	inType			The input creator code, can be kLSUnknowmType
		//  [in]	inCreator		The input creator code, can be kLSUnknowmCreator
		//  [in]	inExtension		The input extension, can be NULL
		//  [in]	roleMask		The input LSRolesMask
		//  [out]	outSpec			On exit, contains the spec of the app
		//
	static	ErrCode	getApplicationForInfo( OSType inType, OSType inCreator, const CStr *inExtension, long roleMask, FSSpec *outSpec );

private:
};

#endif


