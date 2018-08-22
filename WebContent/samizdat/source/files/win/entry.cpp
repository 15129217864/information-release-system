/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "comdefs.h"
#include <native.h>
#include "Debugger.h"

/*
RNIGetCompatibleVersion is exported; RNI (SDK version 2.0 or greater) looks for this export,
and uses the value returned to determine whether to load the DLL or not.
*/

extern "C" {
DWORD __cdecl RNIGetCompatibleVersion( void );
}

DWORD __cdecl RNIGetCompatibleVersion( void )
{
#ifdef RNIVER
	Debugger::debug( __LINE__, _TXL( "RNIGetCompatibleVersion" ), NULL, NULL, (long) RNIVER, 0 );
	return RNIVER;
#else
	Debugger::debug( __LINE__, _TXL( "RNIGetCompatibleVersion: zip" ) );
	return 0;
#endif
}

