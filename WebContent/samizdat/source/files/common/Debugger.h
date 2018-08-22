/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_Debugger_H
#define	INC_Debugger_H

#if defined(_WIN32)
	#include <windows.h>
#elif defined(__linux__) || defined(macintosh) || defined(__osx__)
	#include "comdefs.h"
#endif

#include "CString.h"

/*------------------------------------------------------------------------
CLASS
	Debugger

	Used to write debugging messages.

DESCRIPTION
	Used to write debugging messages.
	All tracing messages should go through this class.
	Different signatures are provided for various sets of arguments, as follows:

	debug( long, char* );
	debug( long, char*, char* );
	debug( long, char*, CStr*, CStr* );
	debug( long, char*, CStr*, CStr*, long, long );
	debug( long, char*, CStr*, CStr*, long, long, long );

------------------------------------------------------------------------*/

class Debugger
{
public:
		/////////////////////////////
		//
		//  Write the message to the debugging window.
		//	
		//	[in]	msg		the message; This is a plain string, not a printf-style string. May be NULL
		//
	static	void	debug( long line, const LPCTSTR msg );

		/////////////////////////////
		//
		//  Write the message and s1 to the debugging window.
		//	
		//	[in]	msg		the message; This is a plain string, not a printf-style string. May be NULL
		//	[in]	s1		may be NULL
		//
	static	void	debug( long line, const LPCTSTR msg, const LPCTSTR s1 );

		/////////////////////////////
		//
		//  Write the message, cs1, and cs2 to the debugging window.
		//	
		//	[in]	msg		the message; This is a plain string, not a printf-style string. May be NULL
		//	[in]	cs1		may be NULL
		//	[in]	cs2		may be NULL
		//
	static	void	debug( long line, const LPCTSTR msg, const CStr *cs1, const CStr *cs2 );

		/////////////////////////////
		//
		//  Write the message, cs1, cs2, n1, and n2 to the debugging window.
		//	
		//	[in]	msg		the message; This is a plain string, not a printf-style string. May be NULL
		//	[in]	cs1		may be NULL
		//	[in]	cs2		may be NULL
		//
	static	void	debug( long line, const LPCTSTR msg, const CStr *cs1, const CStr *cs2, long n1, long n2 );

		/////////////////////////////
		//
		//  Write the message, cs1, cs2, n1, n2, and n3 to the debugging window.
		//	
		//	[in]	msg		the message; This is a plain string, not a printf-style string. May be NULL
		//	[in]	cs1		may be NULL
		//	[in]	cs2		may be NULL
		//
	static	void	debug( long line, const LPCTSTR msg, const CStr *cs1, const CStr *cs2, long n1, long n2, long n3 );

private:
	static	void	debug1( long line, const LPCTSTR msg, const LPCTSTR s1, const LPCTSTR s2, long n1, long n2, long n3 );

	static	const	LPCTSTR		lpNull;
};

#endif

