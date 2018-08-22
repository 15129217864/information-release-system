/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CMemory.h"
#include <stdlib.h>
#include <string.h>

void *CMemory::mmalloc( long len, LPCTSTR e )
{
	void		*ret;
	
	ret = malloc( len );
	if ( ret == NULL )
		throw e;

	memset( ret, 0, len );

	return ret;
}

void *CMemory::mrealloc( void *buf, long len, LPCTSTR e )
{
	void		*ret;
	
	ret = realloc( buf, len );
	if ( ret == NULL )
		throw e;

	return ret;
}

void CMemory::mfree( void *buf )
{
	if ( buf != NULL )
		free( buf );
}

