/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CVector.h"
#include "CMemory.h"

#if defined(__linux__)
	#include "jmacros.h"
	#include <time.h>
#endif

CVector::CVector( long initNum, long incr )
{
	increment = incr;
	initialNum = initNum;
	objs = (void**) CMemory::mmalloc( sizeof(void*) * initNum, _TXL( "CVector" ) );

	numElements = 0;
	numSlots = initNum;
}

CVector::~CVector( void )
{
	CMemory::mfree( objs );
}

long CVector::getSize( void )
{
	return numElements;
}

ErrCode CVector::insertAt( void *obj, long where )
{
	void		**temp;
	long		i;

	if ( objs == NULL )
		return kErrMallocErr;

	if ( where > numElements )
		return kErrParamErr;

	if ( numElements >= numSlots ) {
		temp = (void**) CMemory::mrealloc( objs, sizeof(void*) * ( numElements + increment ), _TXL( "CVector" ) );

		objs = temp;
		numSlots += increment;

		insertAt( obj, where );
		return 0;
	}
	else {
		if ( numElements > 0 )
			for ( i = numElements - 1; i >= where; i-- ) {
				objs[ i + 1 ] = objs[ i ];
			}
	}

	objs[ where ] = obj;
	++numElements;
	
	return 0;
}

ErrCode CVector::insertAfter( void *obj, void *afterWhich )
{
	long		index;

	index = findIndex( afterWhich );
	if ( index < 0 )
		return kErrParamErr;

	return insertAt( obj, index + 1 );
}

ErrCode CVector::prepend( void *obj )
{
	return insertAt( obj, 0 );
}

ErrCode CVector::append( void *obj )
{
	return insertAt( obj, numElements );
}

void CVector::remove( void *obj )
{
	long		index, i;

	index = findIndex( obj );
	if ( index < 0 ) {
		return;
	}

	for ( i = index; i < numElements - 1; i++ )
		objs[ i ] = objs[ i + 1 ];
	
	--numElements;

	if ( ( numElements % increment ) == 0 && numElements > initialNum ) {
		objs = (void**) CMemory::mrealloc( objs, sizeof(void*) * numElements, _TXL( "CVector" ) );
		numSlots = numElements;
	}
}

void *CVector::elementAt( long i )
{
	if ( objs == NULL )
		return NULL;

	if ( i < 0 || i >= numElements )
		return NULL;
	return objs[ i ];
}

void CVector::setAt( long i, void *obj )
{
	if ( objs == NULL )
		return;

	if ( i >= 0 && i < numElements )
		objs[ i ] = obj;
}

long CVector::findIndex( void *obj )
{
	long		i;

	if ( objs == NULL )
		return -1;

	for ( i = 0; i < numElements; i++ )
		if ( elementAt( i ) == obj )
			return i;

	return -1;
}

