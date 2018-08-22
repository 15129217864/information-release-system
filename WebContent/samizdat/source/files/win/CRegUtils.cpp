/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CRegUtils.h"
#include "XToolkit.h"

ErrCode CRegUtils::getRegKey( HKEY key, const CStr *csSubKey, CStr *csRetData )
{
	long		retval = ERROR_SUCCESS;
	HKEY		hKey;

	retval = XToolkit::XRegOpenKeyEx( key, csSubKey, 0, KEY_QUERY_VALUE, &hKey );
	if( retval != ERROR_SUCCESS )
		return retval;

	retval = XToolkit::XRegQueryValue( hKey, NULL, csRetData );

	RegCloseKey( hKey );

	return retval;
}

