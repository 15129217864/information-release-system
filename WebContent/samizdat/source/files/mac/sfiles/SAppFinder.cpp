/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SAppFinder.h"
#include "SVolumes.h"
#include <string.h>
#include "CUtils.h"
#include "MoreDesktopMgr.h"
#include "Debugger.h"

#if defined(__osx__)
	#include <LaunchServices.h>
#endif

ErrCode SAppFinder::findAPPLMultiple( long creator, long *vRefsP, long *parIDsP, StringPtr namesP,
								long maxToReturn, long flags, long *numWritten, long maxNameLen )
{
	DTPBRec			dtRec;
	DTPBPtr			dtp;
	ErrCode			theErr;
	Str255			pName;
	long			vRefArray[ 32 ], numVolumes, i, numDone, volIndex;
	short			dbRef;

	*numWritten = 0;
	numDone = 0;

	if ( maxToReturn < 1 )
		return noErr;

#if defined(__osx__)
	theErr = findAPPLForInfoOSX( creator, vRefsP, parIDsP, namesP, maxNameLen );

	if ( theErr == noErr ) {
		vRefsP++;
		parIDsP++;
		namesP += maxNameLen;

		*numWritten = 1;
		numDone = 1;

		if ( maxToReturn <= 1 )
			return noErr;
	}

#endif

	dtp = &dtRec;

	theErr = SVolumes::getAllVRefNums( &vRefArray[ 0 ], 32, &numVolumes );
	if ( theErr != kErrNoErr || numVolumes < 1 ) {
		if ( theErr == kErrNoErr )
			theErr = nsvErr;
		Debugger::debug( __LINE__, "findAPPLMultiple: err from getAllVRefNums", NULL, NULL, theErr, 0 );
		goto bail; 
	}

	for ( volIndex = 0; volIndex < numVolumes && numDone < maxToReturn; volIndex++ ) {
		CUtils::zeroset( dtp, sizeof(DTPBRec) );
		dtp->ioNamePtr = pName;
		dtp->ioVRefNum = vRefArray[ volIndex ];
		
		theErr = PBDTGetPath( dtp );
		if ( theErr != kErrNoErr )
			continue;
		
		dbRef = dtp->ioDTRefNum;

		CUtils::zeroset( dtp, sizeof(DTPBRec) );
		
		dtp->ioNamePtr = pName;
		dtp->ioDTRefNum = dbRef;
		dtp->ioFileCreator = creator;

		for ( i = 0; numDone < maxToReturn; i++ ) {
			dtp->ioIndex = i + 1;

			theErr = PBDTGetAPPL( dtp, false );
			if ( theErr != kErrNoErr )
				break;

			*vRefsP++ = vRefArray[ volIndex ];
			*parIDsP++ = dtp->ioAPPLParID;
			CUtils::pStrncpy( namesP, pName, maxNameLen - 1 );
			namesP += maxNameLen;
			++numDone;
		}
	}

	if ( numDone > 0 )
		theErr = kErrNoErr;
	else if ( theErr == kErrNoErr )
		theErr = nsvErr;

bail:

	*numWritten = numDone;

	return theErr;

	UNUSED( flags );
}

ErrCode SAppFinder::findAPPLSingle( long creator, long *vRef, long *parID, StringPtr pName,
									long flags )
{
	ErrCode			theErr;
	long			vRefArray[ 32 ], numVolumes, i;
	short			sVRef;
	Boolean			bSearch;

#if defined(__osx__)

	FSSpec			dummySpec;

	theErr = findAPPLForInfoOSX( creator, vRef, parID, pName, sizeof(dummySpec.name) );
	if ( theErr == noErr )
		return theErr;

#endif

	bSearch = ( flags == 0 ) ? false : true;
	
	theErr = SVolumes::getAllVRefNums( &vRefArray[ 0 ], 32, &numVolumes );
	if ( theErr != kErrNoErr || numVolumes < 1 ) {
		if ( theErr == kErrNoErr )
			theErr = nsvErr;
		goto bail; 
	}

	for ( i = 0; i < numVolumes; i++ ) {
		theErr = DTXGetAPPL( NULL, vRefArray[ i ], creator, bSearch, &sVRef, parID, pName );
		*vRef = sVRef;
		if ( theErr == kErrNoErr )
			break;
	}
	
bail:

	return theErr;
}

#if defined(__osx__)

ErrCode SAppFinder::findAPPLForInfoOSX( long creator, long *vRef, long *parID, StringPtr pName, long maxNameLen )
{
	FSSpec		file;
	FSRef		ref;
	ErrCode		theErr;

	if ( LSFindApplicationForInfo == (void *) kUnresolvedCFragSymbolAddress ) {
		Debugger::debug( __LINE__, "findAPPLForInfoOSX: LS not available" );
		return kErrParamErr;
	}

	theErr = LSFindApplicationForInfo( creator, NULL, NULL, &ref, NULL );
	if ( theErr != noErr ) {
		Debugger::debug( __LINE__, "findAPPLForInfoOSX: LS <err,creator>", NULL, NULL, theErr, creator );
		return theErr;
	}

	theErr = FSGetCatalogInfo( &ref, kFSCatInfoNone, NULL, NULL, &file, NULL );
	if ( theErr != noErr ) {
		Debugger::debug( __LINE__, "findAPPLForInfoOSX: GCI <err,creator>", NULL, NULL, theErr, creator );
		return theErr;
	}

	*vRef = file.vRefNum;
	*parID = file.parID;
	CUtils::pStrncpy( pName, file.name, maxNameLen - 1 );
	
/*
	{
		CStrA		str( pName );

		Debugger::debug( __LINE__, "findAPPLForInfoOSX: FOUND name, creator", &str, NULL, creator, 0 );
	}
*/

	return theErr;
}

#endif
