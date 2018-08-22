/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CFilePath.h"
#include "Debugger.h"

CFilePath::CFilePath( const CStr *cs ) : CStr( cs )
{
	replaceSlashesWithColons();
	deQP();
}

CFilePath::~CFilePath()
{
}

ErrCode CFilePath::getPStr( StringPtr ps, long len )
{
	long		strLen;

	if ( len < 1 )
		return -1;
	if ( len < 2 ) {
		ps[ 0 ] = 0;
		return -1;
	}
	if ( len > 255 )
		len = 255;

	strLen = getLength();
	if ( strLen < len )
		len = strLen;
	
	BlockMoveData( getBuf(), ps + 1, len );
	ps[ 0 ] = len;
	
	return 0;
}

void CFilePath::replaceSlashesWithColons()
{
	char		*bufP;
	long		inLen, i;

//	Debugger::debug( __LINE__, "about to rswc, cs=%s", getBuf() );

	inLen = getLength();
	if ( inLen < 1 )
		return;

	bufP = getBuf();

	if ( bufP[ 0 ] == '/' ) {
		BlockMoveData( bufP + 1, bufP, inLen );
		--inLen;
	}

	for ( i = 0; i < inLen; i++ )
		if ( bufP[ i ] == '/' )
			bufP[ i ] = ':';

	bufP[ i ] = 0;

//	Debugger::debug( __LINE__, "  done with rswc, cs=%s", getBuf() );
}

void CFilePath::deQP()
{
	char		*bufP;
	long		inLen, fromI, toI;

//	Debugger::debug( __LINE__, _TXL( "about to deQP" ), this, NULL );

	inLen = getLength();
	if ( inLen < 1 )
		return;

	bufP = getBuf();

	for ( fromI = 0, toI = 0; fromI < inLen; fromI++, toI++ ) {
		if ( bufP[ fromI ] == '%' ) {
			if ( fromI + 2 >= inLen )
				bufP[ toI ] = bufP[ fromI ];
			else {
				bufP[ toI ] = hexToByte( bufP[ fromI + 1 ], bufP[ fromI + 2 ] );
				fromI += 2;
			}
		}
		else
			bufP[ toI ] = bufP[ fromI ];
	}

	bufP[ toI ] = 0;

//	Debugger::debug( __LINE__, _TXL( "  done with deQP" ), this, NULL );
}

unsigned char CFilePath::hexToByte( unsigned char cHigh, unsigned char cLow )
{
	unsigned char		high, low;
	
	if ( cHigh >= 'A' && cHigh <= 'F' )
		high = cHigh - 'A' + 10;
	else if ( cHigh >= 'a' && cHigh <= 'f' )
		high = cHigh - 'a' + 10;
	else if ( cHigh >= '0' && cHigh <= '9' )
		high = cHigh - '0';
	else
		return '?';

	if ( cLow >= 'A' && cLow <= 'F' )
		low = cLow - 'A' + 10;
	else if ( cLow >= 'a' && cLow <= 'f' )
		low = cLow - 'a' + 10;
	else if ( cLow >= '0' && cLow <= '9' )
		low = cLow - '0';
	else
		return '?';

	return ( ( ( high << 4 ) & 0xF0 ) | ( low & 0xF ) );
}

