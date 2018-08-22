/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

/*
	Defines macros for working with CStrA and CStrW, and includes their header files.
*/

#ifndef INC_CString_H
#define INC_CString_H

#if defined(UNICODE)
	#define		CStr		CStrW
#else
	#define		CStr		CStrA
#endif

#if defined(_WIN32)
	#include "CStrW.h"
#endif

#include "CStrA.h"

#if defined(DO_JNI) || defined(DO_JRI)
	#define	MAKESTR(a,b)	{											\
		if ( a == NULL ) { theErr = kErrParamErr; goto bail; }			\
		try {															\
			b = new CStr( pEnv, a );									\
		}																\
		catch ( LPCTSTR e ) {											\
			Debugger::debug( __LINE__, _TXL( "got exception" ), e );	\
			theErr = kErrCantCreateString;								\
			goto bail;													\
		}																\
	}
	
	#define	DECLARESTR(a)	CStr	*a = NULL
	#define	DELETESTR(a)	{ if ( a != NULL ) delete a; }

#elif defined( DO_RNI2 ) || defined( DO_RNI1 )

	#define	MAKESTR(a,b)	{											\
		if ( a == NULL ) { theErr = kErrParamErr; goto bail; }			\
		try {															\
			b = new CStr( a );											\
		}																\
		catch ( LPCTSTR e ) {											\
			Debugger::debug( __LINE__, _TXL( "got exception" ), e );	\
			theErr = kErrCantCreateString;								\
			goto bail;													\
		}																\
	}
	
	#define	DECLARESTR(a)	CStr	*a = NULL
	#define	DELETESTR(a)	{ if ( a != NULL ) delete a; }

#endif

#endif

