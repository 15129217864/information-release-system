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
#include "AppFinder.h"
#include "SAppFinder.h"
#include "Debugger.h"

enum {
	kFindAPPLMultipleMaxNameLen = 64
};

#if !defined(__osx__)
	#pragma export on
#endif

	//	private native int nativeFindAPPLMultiple( int creator, int vRefs[], int parIDs[], byte pNames[], int maxToReturn );

SFUNC( JINT, AppFinderMRJ_nFindAPPLMultiple ),
JINT creator, 
JINTARRAY pVRefs,
JINTARRAY pParIDs, 
JBYTEARRAY pName, 
JINT maxToReturn,
JINT flags,
JINTARRAY pNumRet )
{
	ErrCode		theErr;
	long		*vRefsP, *parIDsP, numWritten;
	StringPtr	namesP;
	
	BEGINTRY {
		numWritten = 0;

		CHECKSIZE( pVRefs, maxToReturn );
		CHECKSIZE( pParIDs, maxToReturn );
		CHECKSIZE( pName, kFindAPPLMultipleMaxNameLen * maxToReturn );
		CHECKSIZE( pNumRet, 1 );

		vRefsP = LOCKINTARRAY( pVRefs );
		parIDsP = LOCKINTARRAY( pParIDs );
		namesP = (StringPtr) LOCKBYTEARRAY( pName );

		theErr = SAppFinder::findAPPLMultiple( creator, vRefsP, parIDsP, namesP, maxToReturn,
												flags, &numWritten,
												kFindAPPLMultipleMaxNameLen );

		UNLOCKINTARRAY( pVRefs, vRefsP );
		UNLOCKINTARRAY( pParIDs, parIDsP );
		UNLOCKBYTEARRAY( pName, namesP );

		SETJINT1( pNumRet, numWritten );

	bail:
		;

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}




	//	private native int nativeFindAPPLSingle( int vRefAndParID[], byte pName[] );

SFUNC( JINT, AppFinderMRJ_nFindAPPLSingle ),
JINT creator,
JINTARRAY pVRefAndParID, 
JBYTEARRAY pName,
JINT flags )
{
	long		vRef, parID;
	Str255		name;
	ErrCode		theErr;
	
	BEGINTRY {
		CHECKSIZE( pVRefAndParID, 2 );
		CHECKSIZE( pName, sizeof(name) );

		theErr = SAppFinder::findAPPLSingle( creator, &vRef, &parID, name, flags );
		BAILERR( theErr );

		COPYPSTRINGTOJBYTES( pName, name );
		SETJINT2( pVRefAndParID, vRef, parID );

	bail:
		;

	} ENDTRY

	return theErr;
	
	UNUSED( pObj );
}

#if !defined(__osx__)
	#pragma export off
#endif

