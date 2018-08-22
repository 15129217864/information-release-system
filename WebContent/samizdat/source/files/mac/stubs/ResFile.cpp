/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "comdefs.h"
#include "jmacros.h"
#include "stub_macros.h"
#include "ResFile.h"
#include <string.h>
#include "SResFile.h"
#include "CFSpec.h"
#include "Debugger.h"

#if !defined(__osx__)
	#pragma export on
#endif

SFUNC( JINT, ResFileMRJ_nOpenExistingResFile ),
JINT vRef,
JINT parID, 
JBYTEARRAY pName )
{
	ErrCode		theErr;
	short		fileFD;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );

		MAKECFSPEC( theSpec, vRef, parID, pName );
		
		fileFD = SResFile::openExistingResFile( theSpec );
	
	bail:
		
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return fileFD;
	
	UNUSED( pObj );
}

	//	private static native void nativeCloseResFile( int fileFD );

SFUNC( void, ResFileMRJ_nCloseResFile ),
JINT fileFD )
{
	SResFile::closeResFile( fileFD );

	return;

	UNUSED( pObj );
	UNUSED( pEnv );
}

	//	native int nativeGetResourceSize( int fileFD, int resName, int resID, int pSize[] );

SFUNC( JINT, ResFileMRJ_nGetResourceSize ),
JINT fileFD, 
JINT resName, 
JINT resID,
JINTARRAY pSize )
{
	ErrCode		theErr;
	long		resSize;
	
	BEGINTRY {
		CHECKSIZE( pSize, 1 );

		if ( fileFD < 1 ) {
			Debugger::debug( __LINE__, "jriGetResourceSize, bad fileFD", NULL, NULL, fileFD, 0 );
			theErr = paramErr;
			goto bail;
		}

		theErr = SResFile::getResourceSize( fileFD, resName, resID, &resSize );
		BAILERR( theErr );

		SETJINT1( pSize, resSize );
		
	bail:
		;
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

	//	native int nativeGetResource( int fileFD, int resName, int resID, byte pData[] );

SFUNC( JINT, ResFileMRJ_nGetResource ),
JINT fileFD, 
JINT resName, 
JINT resID,
JBYTEARRAY pRetData )
{
	char			*retDataP;
	ErrCode			theErr;
	long			retDataLen;
	
	BEGINTRY {
		CHECKNULL( pRetData );
		if ( fileFD < 1 ) {
			Debugger::debug( __LINE__, "jriGetResource, bad fileFD", NULL, NULL, fileFD, 0 );
			theErr = paramErr;
			goto bail;
		}

		retDataLen = GETBYTEARRAYLEN( pRetData );
		retDataP = (char*) LOCKBYTEARRAY( pRetData );

		theErr = SResFile::getResource( fileFD, resName, resID, retDataLen, (char*) retDataP );
		
		UNLOCKBYTEARRAY( pRetData, retDataP );
	
	bail:
		;
		
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

#if !defined(__osx__)
	#pragma export off
#endif

