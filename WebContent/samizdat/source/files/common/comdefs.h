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
	The main include file which defines some global items.
*/

#ifndef INC_comdefs_H
#define INC_comdefs_H

#if defined(_WIN32)

			//	CWPro2 doesn't define this
	#if defined(__MWERKS__)
		typedef	long __int32;
	#endif

	#include <windows.h>

			//	CWPro2 apparently doesn't define _T, so _TXL is used instead throughout
	#if defined(UNICODE)
		#define		_TXL(a)	L##a
		#define		kDDECodePage		CP_WINUNICODE
	#else
		#define		_TXL(a)	a
		#define		kDDECodePage		CP_WINANSI
	#endif

#elif defined(__linux__)

	#define	_TXL(a)					(a)
	#define	MAX_PATH				256
	typedef	long					BOOL;
	typedef	char*					LPCTSTR;

#elif defined(macintosh)

	#define	_TXL( a )				(a)
	typedef	Boolean					BOOL;
	typedef	char*					LPCTSTR;
	typedef char					TCHAR;

	#if !defined(NULL)
		#define NULL					((void*)0)
	#endif

#elif defined(__osx__)

	#include <Finder.h>
	#define	_TXL( a )				(a)
	typedef	char*					LPCTSTR;
	typedef char					TCHAR;
	typedef	Boolean					BOOL;
	#if !defined(NULL)
		#define NULL					((void*)0)
	#endif

#endif

#define	UNUSED(a)	{(a) = (a);}

#define	BAILERR(a)				{ if ( (a) != kErrNoErr ) goto bail; }

	//	used to represent error codes throughout
typedef	long	ErrCode;

#include "errcodes.h"

		//	used in the stubs files (AppUtilsMSVM.cpp, etc.)
#define	BEGINTRY	try

#define	ENDTRY		catch ( LPCTSTR msg ) {							\
		Debugger::debug( __LINE__, _TXL( "got exception" ), msg );	\
		theErr = kErrException;										\
	}

#endif

