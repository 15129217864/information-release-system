/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CUtils.h"
#include "Debugger.h"

void CUtils::pStrToCString( char *to, ConstStr255Param from )
{
	BlockMoveData( &from[ 1 ], to, from[ 0 ] );
	to[ from[ 0 ] ] = 0;
}

void CUtils::pStrToLowerCString( char *to, ConstStr255Param from )
{
	long		len, i;
	
	len = from[ 0 ];
	++from;
	
	for ( i = 0; i < len; i++ )
		*to++ = toLower( *from++ );
	
	*to = 0;
}

char CUtils::toLower( char c )
{
	if ( c >= 'A' && c <= 'Z' )
		return c - 'A' + 'a';
	return c;
}

void CUtils::pStrcpy( StringPtr d, ConstStr255Param s )
{
	BlockMoveData( &s[ 0 ], d, s[ 0 ] + 1 );
}

void CUtils::pStrncpy( StringPtr d, ConstStr255Param s, long max )
{
	if ( s[ 0 ] < max )
		max = s[ 0 ];

	BlockMove( &s[ 0 ], d, max + 1 );
	
	d[ 0 ] = max;
}

void CUtils::zeroset( void *to, size_t sz )
{
	memset( to, 0, sz );
}

static char kHex[] = { '0', '1', '2', '3', '4', '5', '6', '7', 
								'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

ErrCode CUtils::enQP( short inLen, Handle inH, short *outLen, Handle *outH )
{
	Handle				workH;
	long					inI, workI, numSpecial, numCharsOut;
	unsigned char		*inP, *workP, c;
	SInt8					inHState;

	*outH = NULL;
	*outLen = 0;
	
	if ( inLen < 1 || inH == NULL )
		return -1;
	
	inHState = HGetState( inH );
	HLock( inH );
	inP = (unsigned char*) *inH;

	for ( inI = 0, numSpecial = 0; inI < inLen; inI++ ) {
		c = inP[ inI ];
		if ( c <= ' ' || c > 0x7F || c == '%' || c == '/' )
			++numSpecial;
	}

	numCharsOut = inLen + ( 2 * numSpecial );

	workH = NewHandleClear( numCharsOut + 4 );
	if ( workH == NULL ) {
		HSetState( inH, inHState );
		return -1;
	}

	HLock( workH );
	workP = (unsigned char*) *workH;

	for ( inI = 0, workI = 0; inI < inLen; inI++ ) {
		c = inP[ inI ];

		if ( c <= ' ' || c > 0x7F || c == '%' || c == '/' ) {
			workP[ workI++ ] = '%';
			workP[ workI++ ] = kHex[ c >> 4 ];
			workP[ workI++ ] = kHex[ c & 0x0F ];
		}
		else
			workP[ workI++ ] = c;
	}

	HSetState( inH, inHState );
	HUnlock( workH );
	workP[ workI ] = 0;
	*outLen = numCharsOut;
	*outH = workH;

	return kErrNoErr;
}

