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

#if defined(macintosh)
	#include "ICAPI.h"
#endif

#include "IConfig.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "SIConfig.h"
#include "SAppFinder.h"
#include "CString.h"
#include "Debugger.h"

#if !defined(__osx__)
	#pragma export on
#endif

	//	private native int nativeFindAppByName( int creators[], int vRefs[], int parIDs[],
	//											byte pNames[], int maxToReturn, int numRet
SFUNC( JINT, IConfigMRJ_nFindAppByName ),
JINT appCreator, 
JSTRING nameID,
JINTARRAY pCreators, 
JINTARRAY pVRefs, 
JINTARRAY pParIDs, 
JBYTEARRAY pNames,
JINT maxToReturn, 
JINTARRAY pNumRet )
{
	ErrCode		theErr;
	long		numWritten, *creatorsP, *vRefsP, *parIDsP;
	StringPtr	namesP;
	DECLARESTR( csMatchName );
	
	BEGINTRY {
		numWritten = 0;

		CHECKNULL( nameID );
		CHECKSIZE( pCreators, maxToReturn );
		CHECKSIZE( pVRefs, maxToReturn );
		CHECKSIZE( pParIDs, maxToReturn );
		CHECKSIZE( pNames, SIConfig::kFindAppByNameMaxNameLen * maxToReturn );
		CHECKSIZE( pNumRet, 1 );
		MAKESTR( nameID, csMatchName );

		creatorsP = LOCKINTARRAY( pCreators );
		vRefsP = LOCKINTARRAY( pVRefs );
		parIDsP = LOCKINTARRAY( pParIDs );
		namesP = (StringPtr) LOCKBYTEARRAY( pNames );

		theErr = SIConfig::iCFindAppByName( appCreator, csMatchName, creatorsP,
											vRefsP, parIDsP, namesP,
											maxToReturn, &numWritten );

		UNLOCKINTARRAY( pCreators, creatorsP );
		UNLOCKINTARRAY( pVRefs, vRefsP );
		UNLOCKINTARRAY( pParIDs, parIDsP );
		UNLOCKBYTEARRAY( pNames, namesP );

		SETJINT1( pNumRet, numWritten );

	bail:
	
		DELETESTR( csMatchName );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//	private native int nativeLaunchURL( int appCreator, String url, int flags, String prefBrows[] );

SFUNC( JINT, IConfigMRJ_nLaunchURL ),
JINT appCreator,
JSTRING urlID,
JINT flags,
JOBJECTARRAY pPreferredBrowsers )
{
	ErrCode		theErr;
	DECLARESTR( csURL );
	
	BEGINTRY {
		CHECKNULL( urlID );
		MAKESTR( urlID, csURL );

		theErr = SIConfig::iCLaunchURL( appCreator, csURL );
	
	bail:
	
		DELETESTR( csURL );
	
	} ENDTRY

	return theErr;

	UNUSED( flags );
	UNUSED( pPreferredBrowsers );
	UNUSED( pObj );
}

	//	private native int nativeFindMatchesExt( int appCreator, String extension,
	//												int numReturned[], int maxToReturn,
	//												int cVals[], int tVals[] );

SFUNC( JINT, IConfigMRJ_nFindMatchesExt ),
JINT appCreator,
JSTRING extensionID,
JINT direction,
JINTARRAY pNumRet,
JINT maxToReturn,
JINTARRAY pCVals,
JINTARRAY pTVals )
{
	ErrCode		theErr;
	long		*cValsP, *tValsP, numReturned;
	DECLARESTR( csExtension );
	
	BEGINTRY {
		numReturned = 0;

		CHECKNULL( extensionID );
		CHECKSIZE( pNumRet, 1 );
		CHECKSIZE( pCVals, maxToReturn );
		CHECKSIZE( pTVals, maxToReturn );
		MAKESTR( extensionID, csExtension );

		cValsP = LOCKINTARRAY( pCVals );
		tValsP = LOCKINTARRAY( pTVals );

		theErr = SIConfig::iCFindMatchesExt( appCreator, csExtension, direction, &numReturned,
												maxToReturn, cValsP, tValsP );
		
		SETJINT1( pNumRet, numReturned );

		UNLOCKINTARRAY( pCVals, cValsP );
		UNLOCKINTARRAY( pTVals, tValsP );
	
	bail:
	
		DELETESTR( csExtension );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

	//	int nativeFindMatchesFInfo( int appCreator, int targetCreator,
	//								int targetType, int direction, int numReturned[],
	//								int maxToReturn, byte extensions[] );

SFUNC( JINT, IConfigMRJ_nFindMatchesFInfo ),
JINT appCreator,
JINT targetCreator,
JINT targetType,
JINT direction,
JINTARRAY pNumRet,
JINT maxToReturn,
JBYTEARRAY pExtensions )
{
	char		*extP;
	long		numReturned;
	ErrCode		theErr;
	
	BEGINTRY {
		numReturned = 0;

		CHECKSIZE( pNumRet, 1 );
		CHECKSIZE( pExtensions, SIConfig::kMaxExtensionLength * maxToReturn );

		extP = (char*) LOCKBYTEARRAY( pExtensions );

		theErr = SIConfig::iCFindMatchesFInfo( appCreator, targetCreator, targetType, direction,
									&numReturned, maxToReturn, (StringPtr) extP );
		
		UNLOCKBYTEARRAY( pExtensions, extP );

		SETJINT1( pNumRet, numReturned );

	bail:
		;

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}


SFUNC( JINT, IConfigMRJ_nICStart ),
JINT creator )
{
	SIConfig::OurDataH		ourDataH;
	ErrCode					theErr;
	
	BEGINTRY {

#if defined(macintosh)
		ourDataH = (SIConfig::OurDataH) NewHandleSysClear( sizeof(SIConfig::OurData) );
#elif defined(__osx__)
		ourDataH = (SIConfig::OurDataH) NewHandleClear( sizeof(SIConfig::OurData) );
#endif

		if ( ourDataH == NULL ) {
			theErr = memFullErr;
			goto bail;
		}

		theErr = SIConfig::iCStart( creator, ourDataH );

	bail:
			
		if ( theErr != kErrNoErr ) {
			if ( ourDataH != NULL )
				DisposeHandle( (Handle) ourDataH );
			ourDataH = NULL;
		}
	
	} ENDTRY
	
	return (long) ourDataH;

	UNUSED( pObj );
	UNUSED( pEnv );
}

SFUNC( void, IConfigMRJ_nICStop ),
JINT ourHandle )
{
	SIConfig::iCStop( (SIConfig::OurDataH) ourHandle );

	return;

	UNUSED( pObj );
	UNUSED( pEnv );
}

SFUNC( JINT, IConfigMRJ_nICGetMapEntrySize )
)
{
	return sizeof(ICMapEntry);

	UNUSED( pObj );
	UNUSED( pEnv );
}

SFUNC( JINT, IConfigMRJ_nICGetSeed ),
JINT ourHandle )
{
	ErrCode		theErr;
	long		seed;
	
	BEGINTRY {
		theErr = SIConfig::iCGetSeed( (SIConfig::OurDataH) ourHandle, &seed );
	} ENDTRY
	
	return ( theErr == kErrNoErr ) ? seed : 0;
	
	UNUSED( pObj );
	UNUSED( pEnv );
}

SFUNC( JINT, IConfigMRJ_nICCountMapEntries ),
JINT ourHandle )
{
	ErrCode		theErr;
	long		count;
	
	BEGINTRY {
		theErr = SIConfig::iCCountMapEntries( (SIConfig::OurDataH) ourHandle, &count );
	} ENDTRY
	
	return ( theErr == kErrNoErr ) ? count : 0;

	UNUSED( pObj );
	UNUSED( pEnv );
}

SFUNC( JINT, IConfigMRJ_nICGetIndMapEntry ),
JINT ourHandle,
JINT whichRecord,
JBYTEARRAY pRecord )
{
	ErrCode			theErr;
	ICMapEntry		mapEntry;
	char			*recordP;
	
	BEGINTRY {
		CHECKSIZE( pRecord, sizeof(ICMapEntry) );

		theErr = SIConfig::iCGetIndMapEntry( (SIConfig::OurDataH) ourHandle, whichRecord, &mapEntry );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "_ICGIME.ICGIME", NULL, NULL, theErr, whichRecord );
			goto bail;
		}

		recordP = (char*) LOCKBYTEARRAY( pRecord );

		BlockMoveData( &mapEntry, recordP, sizeof(ICMapEntry) );

		UNLOCKBYTEARRAY( pRecord, recordP );

	bail:
		;

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

#if !defined(__osx__)
	#pragma export off
#endif

