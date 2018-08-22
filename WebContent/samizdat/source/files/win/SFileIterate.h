/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SFileIterate_H
#define INC_SFileIterate_H

#include "comdefs.h"
#include "CString.h"

/*------------------------------------------------------------------------
CLASS
	SFileIterate

	Used to obtain the contents of directories

DESCRIPTION
	Wraps the FindFirstFile, FindNextFile, and FindClose routines

------------------------------------------------------------------------*/

class SFileIterate {
public:
		///////////////////////
		//
		//  Wraps the FindFirstFile routine.
		//
		//  [in]	searchString	The directory to search; may have wildcards
		//  [out]	hFindP			The search handle
		//  [out]	dwAttrs			The attributes of the first found file
		//  [out]	fileName		The name of the first found file.
		//
	static	ErrCode iFindFirstFile( const CStr *searchString, long *hFindP, long *dwAttrs, CStr *fileName );

		///////////////////////
		//
		//  Wraps the FindNextFile routine.
		//
		//  [in]	hFind			The search handle returned from iFindFirstFile
		//  [out]	dwAttrs			The attributes of the next found file
		//  [out]	fileName		The name of the next found file.
		//
	static	ErrCode iFindNextFile( long hFind, long *dwAttrs, CStr *fileName );

		///////////////////////
		//
		//  Wraps the FindClose routine.
		//
		//  [in]	hFind			The search handle returned from iFindFirstFile
		//
	static	ErrCode iNativeFindClose( long hFind );
};

#endif
