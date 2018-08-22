/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SFileIterate.h"
#include "XToolkit.h"

ErrCode SFileIterate::iFindFirstFile( const CStr *csSearchString, long *hFindP, long *dwAttrs, CStr *csFileName )
{
	HANDLE				hFind;

	*hFindP = 0;
	*dwAttrs = 0;

	hFind = XToolkit::XFindFirstFile( csSearchString, (LPDWORD) dwAttrs, csFileName );
	if ( hFind == INVALID_HANDLE_VALUE )
		return GetLastError();

	*hFindP = (long) hFind;

	return kErrNoErr;
}

ErrCode SFileIterate::iFindNextFile( long findH, long *dwAttrs, CStr *csFileName )
{
	DWORD				lastError;
	BOOL				bRet;

	*dwAttrs = 0;

	if ( findH == 0 )
		return kErrParamErr;

	bRet = XToolkit::XFindNextFile( (HANDLE) findH, (LPDWORD) dwAttrs, csFileName );
	if ( !bRet ) {
		lastError = GetLastError();
		if ( lastError == ERROR_NO_MORE_FILES )
			return kErrFindNextFileNoMoreFiles;
		else
			return kErrFindNextFile;
	}

	return kErrNoErr;
}

ErrCode SFileIterate::iNativeFindClose( long findH )
{
	if ( findH == 0 )
		return kErrParamErr;

	FindClose( (HANDLE) findH );

	return kErrNoErr;
}

