/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "MiniBackground.h"
#include "CUtils.h"

//extern	QDGlobals	qd;

ErrCode makeMiniBackground( GWorldXPtr gxP, long width, long height, long pixelDepth )
{
	ErrCode			theErr;

	CUtils::zeroset( gxP, sizeof(GWorldX) );
	SetRect( &( gxP->theRect ), 0, 0, width, height );
	
	theErr = NewGWorld( &( gxP->theGWorld ), pixelDepth, &( gxP->theRect ), NULL, NULL, 0 );
	if ( theErr != kErrNoErr || gxP->theGWorld == NULL ) {
		gxP->theGWorld = NULL;
		if ( theErr == kErrNoErr )
			theErr = memFullErr;
		goto bail;
	}

	gxP->thePixMapH = GetGWorldPixMap( gxP->theGWorld );
	if ( !LockPixels( gxP->thePixMapH ) ) {
		DisposeGWorld( gxP->theGWorld );
		gxP->theGWorld = NULL;
		theErr = memFullErr;
		goto bail;
	}

	gxP->theBytes = (Byte*) GetPixBaseAddr( gxP->thePixMapH );
	gxP->theRowBytes = ( **( gxP->thePixMapH ) ).rowBytes & 0x3FFF;
	gxP->bitDepth = (**( gxP->thePixMapH ) ).pixelSize;

bail:
	
	return theErr;
}

void setAsPortMiniBackground( GWorldXPtr gxP )
{
	if ( gxP->theGWorld == NULL )
		return;

	GetGWorld( &( gxP->saveGWorld ), &( gxP->saveGDevice ) );
	SetGWorld( gxP->theGWorld, NULL );
	ForeColor( blackColor );
	BackColor( whiteColor );
}

void restorePortMiniBackground( GWorldXPtr gxP )
{
	if ( gxP->saveGWorld == NULL )
		return;

	SetGWorld( gxP->saveGWorld, gxP->saveGDevice );
}

void disposeMiniBackground( GWorldXPtr gxP )
{
	if ( gxP->theGWorld != NULL )
		DisposeGWorld( gxP->theGWorld );
}

