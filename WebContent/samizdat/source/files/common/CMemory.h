/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CMemory_H
#define INC_CMemory_H

#include "comdefs.h"

/*------------------------------------------------------------------------
CLASS
	CMemory

	Used to allocate and free memory.

DESCRIPTION
	Contains three static methods which wrap the 'malloc()', 'realloc()',
	and 'free()' routines. The first two of these throw exceptions if memory couldn't be allocated.

------------------------------------------------------------------------*/

class CMemory
{
public:
		///////////////////////
		//
		//  Same as 'malloc', except throws an exception if the memory
		//	couldn't be allocated. Also, fills the memory block with zeroes.
		//
		//  [in]	len		The number of bytes to allocate.
		//  [in]	e		The exception which will be thrown.
		//
	static	void	*mmalloc( long len, LPCTSTR e );

		///////////////////////
		//
		//  Same as 'realloc', except throws an exception if the memory
		//	couldn't be allocated.
		//
		//  [in]	buf		Address of the existing memory block.
		//  [in]	len		The new size of the memory block in bytes.
		//  [in]	e		The exception which will be thrown.
		//
	static	void	*mrealloc( void *buf, long len, LPCTSTR e );

		///////////////////////
		//
		//  Same as 'free'.
		//
		//  [in]	buf		Address of the block to free.
		//
	static	void	mfree( void *buf );
};

#endif

