/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CFilePathOSX.h"
#include "Debugger.h"

CFilePathOSX::CFilePathOSX( const CStr *cs ) : CStr( cs )
{
	CFURLRef		urlRef = NULL;
	char			tempBuf[ kMaxPath ];
	CFStringRef		cfsPath = NULL, cfsOutPath = NULL;
	LPCTSTR			errMsg = NULL;
	Boolean			bResult;

	deQP();

	cfsPath = CFStringCreateWithCString( NULL, getBuf(), kCFStringEncodingUTF8 );
	if ( cfsPath == NULL ) {
		errMsg = _TXL( "CFilePathOSX::can't create cfsPath" );
		goto bail;
	}

	urlRef = CFURLCreateWithFileSystemPath( kCFAllocatorDefault, cfsPath, kCFURLHFSPathStyle, (Boolean) 0 );
	if ( urlRef == NULL ) {
		errMsg = _TXL( "CFilePathOSX::can't create urlRef" );
		goto bail;
	}

	cfsOutPath = CFURLCopyFileSystemPath( urlRef, kCFURLPOSIXPathStyle );
	if ( cfsOutPath == NULL ) {
		errMsg = _TXL( "CFilePathOSX::can't create cfsOutPath" );
		goto bail;
	}

	bResult = CFStringGetCString( cfsOutPath, tempBuf, sizeof(tempBuf), kCFStringEncodingUTF8 );
	if ( !bResult ) {
		errMsg = _TXL( "CFilePathOSX::bResult" );
		goto bail;
	}

	setBuf( tempBuf );

bail:

	if ( cfsPath != NULL ) CFRelease( cfsPath );
	if ( urlRef != NULL ) CFRelease( urlRef );
	if ( cfsOutPath != NULL ) CFRelease( cfsOutPath );

	if ( errMsg != NULL ) {
		Debugger::debug( __LINE__, errMsg, this, NULL, 0, 0 );
		throw errMsg;
	}
}

CFilePathOSX::~CFilePathOSX()
{
}

ErrCode CFilePathOSX::getPStr( StringPtr ps, long len )
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

void CFilePathOSX::deQP()
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

unsigned char CFilePathOSX::hexToByte( unsigned char cHigh, unsigned char cLow )
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

/*
	FSRef			fsRef;
	Str255			tempStr;

	cfPath.getPStr( tempStr, sizeof(tempStr) );
	CStr		tempCStr( tempStr );

	theErr = FSPathMakeRef( (UInt8*) csFullPath->getBuf(), &fsRef, NULL );
	if ( theErr != noErr )
		Debugger::debug( __LINE__, _TXL( "fpnts.1" ), csFullPath, NULL, theErr, 0 );

	if ( theErr == noErr ) {
		theErr = FSGetCatalogInfo( &fsRef, kFSCatInfoNone, NULL, NULL, theSpec->getSpecP(), NULL );
		if ( theErr != noErr )
			Debugger::debug( __LINE__, _TXL( "fpnts.2" ), csFullPath, NULL, theErr, 0 );
	}
*/

