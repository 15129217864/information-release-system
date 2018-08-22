/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SResFile.h"
#include "CUtils.h"
#include "Debugger.h"

short SResFile::openExistingResFile( const CFSpec *theSpec )
{
	CFSpec		tempSpec( theSpec );
	ErrCode			theErr;
	short			oldResFile, fileFD;

	theErr = tempSpec.verifySpec();
	if ( theErr != kErrNoErr )
		return -1;

	oldResFile = CurResFile();

	SetResLoad( false );
	fileFD = FSpOpenResFile( tempSpec.getSpecP(), fsRdPerm );
	SetResLoad( true );

	theErr = ResError();
	
	UseResFile( oldResFile );

	if ( theErr == kErrNoErr && fileFD > 0 )
		return fileFD;
	else
		return -1;
}

void SResFile::closeResFile( long fileFD )
{
	if ( fileFD > 0 )
		CloseResFile( fileFD );
}

ErrCode SResFile::getResourceSize( long fileFD, long resName, long resID, long *resSizeP )
{
	Handle		hRes;
	ErrCode			theErr;
	long			handleLen;
	short			oldResFile;

	if ( fileFD < 1 )
		return paramErr;

	theErr = kErrNoErr;
	*resSizeP = 0;

	oldResFile = CurResFile();
	UseResFile( fileFD );

	hRes = Get1Resource( resName, resID );
	if ( hRes == NULL ) {
		theErr = ResError();
		if ( theErr == kErrNoErr )
			theErr = -1;
		goto bail;
	}

	LoadResource( hRes );
	if ( *hRes == NULL ) {
		theErr = ResError();
		if ( theErr == kErrNoErr )
			theErr = -1;
		goto bail;
	}

	handleLen = GetHandleSize( hRes );
	ReleaseResource( hRes );

	*resSizeP = handleLen;

bail:

	UseResFile( oldResFile );

	return theErr;
}

ErrCode SResFile::getResource( long fileFD, long resName, long resID, long bufLen, char *bufP )
{
	Handle		hRes;
	ErrCode			theErr;
	long			handleLen;
	short			oldResFile;

	if ( fileFD < 1 )
		return paramErr;

	theErr = kErrNoErr;

	oldResFile = CurResFile();
	UseResFile( fileFD );

	hRes = Get1Resource( resName, resID );
	if ( hRes == NULL ) {
		theErr = ResError();
		if ( theErr == kErrNoErr )
			theErr = -1;
		goto bail;
	}

	LoadResource( hRes );
	if ( *hRes == NULL ) {
		theErr = ResError();
		if ( theErr == kErrNoErr )
			theErr = -1;
		goto bail;
	}

	DetachResource( hRes );
	HLock( hRes );
	handleLen = GetHandleSize( hRes );

	if ( handleLen < bufLen )
		bufLen = handleLen;
	
	BlockMoveData( *hRes, bufP, bufLen );
	
	DisposeHandle( hRes );
	
bail:

	UseResFile( oldResFile );
	
	return theErr;
}

