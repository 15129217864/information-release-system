/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SVersionInfo_H
#define INC_SVersionInfo_H

#include "comdefs.h"
#include "CString.h"

/*------------------------------------------------------------------------
CLASS
	SVersionInfo

	Gets version info on files.

DESCRIPTION
	Gets version info on files.

------------------------------------------------------------------------*/

class SVersionInfo
{
public:
		///////////////////////
		//
		//  Wraps GetFileVersionInfoStart()
		//
		//  [in]	fullPath		The file to get version info on.
		//  [out]	versionH		The version handle used with the next two calls.
		//
	static	ErrCode iNativeGetFileVersionInfoStart( const CStr *fullPath, long *versionH );

		///////////////////////
		//
		//  Wraps GetFileVersionInfoEnd()
		//
		//  [in]	versionH		The version handle returned from iNativeGetFileVersionInfoStart
		//
	static	ErrCode iNativeGetFileVersionInfoEnd( long nativeH );

		///////////////////////
		//
		//  Wraps VerQueryValue()
		//
		//  [in]	versionH		The version handle returned from iNativeGetFileVersionInfoStart
		//	[in]	key				The key to query.
		//	[out]	value			The returned value.
		//
	static	ErrCode iNativeVerQueryValue( long versionH, const CStr *key, CStr *value );

private:
	static	const CStr	*gcsTranslation;
};

#endif

