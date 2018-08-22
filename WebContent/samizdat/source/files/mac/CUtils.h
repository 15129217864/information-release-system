/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CUtils_H
#define	INC_CUtils_H

#include "comdefs.h"
#include <string.h>

/*------------------------------------------------------------------------
CLASS
	CUtils

	Various utility routines.

DESCRIPTION
	Various utility routines.

------------------------------------------------------------------------*/

class CUtils
{
public:


		///////////////////////
		//
		//	Convert a Handle containing a C string into quoted-printable format
		//	Returns the converted string in a newly created handle.
		//
	static	ErrCode enQP( short inLen, Handle inH, short *outLen, Handle *outH );

		///////////////////////
		//
		//	Pascal string --> C string.
		//
	static	void pStrToCString( char *to, ConstStr255Param from );

		///////////////////////
		//
		//	Pascal string --> lowercase C string.
		//
	static	void pStrToLowerCString( char *to, ConstStr255Param from );

		///////////////////////
		//
		//	Convert char to lower case.
		//
	static	char toLower( char c );

		///////////////////////
		//
		//	Copy a Pascal string.
		//
	static	void pStrcpy( StringPtr d, ConstStr255Param s );

		///////////////////////
		//
		//	Copy maximum number of Pascal string.
		//
	static	void pStrncpy( StringPtr d, ConstStr255Param s, long max );

		///////////////////////
		//
		//	Set a buffer to zero
		//
	static	void zeroset( void *to, size_t sz );
};

#endif

