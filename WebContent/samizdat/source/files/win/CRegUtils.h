/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CREGUTILS_H
#define INC_CREGUTILS_H

#include "comdefs.h"
#include "CString.h"

/*------------------------------------------------------------------------
CLASS
	CRegUtils

	Utilities for working with the Registry.

DESCRIPTION
	Contains one function for working with the registry.

------------------------------------------------------------------------*/

class CRegUtils {

public:
		///////////////////////
		//
		//  Returns the string value of a given key's subkey.
		//
		//  [in]	key				The Registry key.
		//  [in]	csSubKey		The subkey.
		//  [out]	csRetData		On exit, contains the value of the subkey.
		//
	static	ErrCode		getRegKey( HKEY key, const CStr *csSubKey, CStr *csRetData );
};

#endif

