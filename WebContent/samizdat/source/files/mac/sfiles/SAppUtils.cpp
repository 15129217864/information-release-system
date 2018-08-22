/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SAppUtils.h"
#include "CUtils.h"
#include "MoreFiles.h"
#include "MoreFilesExtras.h"
#include "FullPath.h"
#include "SProcesses.h"
#include "SFiles.h"
#include "SAppFinder.h"
#include "SVolumes.h"
#include "Debugger.h"

/*
If EasyOpen is installed, we use the GetFileTypesThatAppCanNativelyOpen() routine.
Otherwise, we search all the mounted drives, calling PBDTGetIconInfo() until
an error occurs, typesBlockP is full, or we run out of drives
*/

ErrCode SAppUtils::getOpenableFileTypes( long vRef, long creator, FileType *typesBlockP, long maxToReturn,
											long *numReturned )
{
	DTPBRec		dtRec;
	DTPBPtr		dtp;
	ErrCode			theErr;
	Str255		pName;
	long			vRefArray[ 32 ], numVolumes, i, numDone, volIndex, checkNum;
	short			dbRef;
	Boolean		bFoundMatch;
	
	if ( (long) GetFileTypesThatAppCanNativelyOpen != kUnresolvedCFragSymbolAddress ) {
		theErr = GetFileTypesThatAppCanNativelyOpen( vRef, creator, typesBlockP );

		for ( numDone = 0; numDone < maxToReturn && typesBlockP[ numDone ] != 0; numDone++ )
			;

		*numReturned = numDone;
		return theErr;
	}

	*numReturned = 0;
	numDone = 0;
	dtp = &dtRec;

	theErr = SVolumes::getAllVRefNums( &vRefArray[ 0 ], 32, &numVolumes );
	if ( theErr != kErrNoErr || numVolumes < 1 ) {
		if ( theErr == kErrNoErr )
			theErr = nsvErr;
		Debugger::debug( __LINE__, "getOpenableFileTypes: err from getAllVRefNums", NULL, NULL, theErr, 0 );
		goto bail; 
	}

	for ( volIndex = 0; volIndex < numVolumes; volIndex++ ) {
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
		dtp->ioTagInfo = 0;

		for ( i = 0; numDone < maxToReturn; i++ ) {
			dtp->ioIndex = i + 1;

			theErr = PBDTGetIconInfo( dtp, false );
			if ( theErr != kErrNoErr )
				break;
	
			for ( checkNum = 0, bFoundMatch = false; checkNum < numDone; checkNum++ ) {
				if ( typesBlockP[ checkNum ] == dtp->ioFileType ) {
					bFoundMatch = true;
					break;
				}
			}

			if ( !bFoundMatch ) {
				typesBlockP[ numDone++ ] = dtp->ioFileType;
			}
		}
	}

	if ( numDone > 0 )
		theErr = kErrNoErr;
	else if ( theErr == kErrNoErr )
		theErr = nsvErr;

bail:

	*numReturned = numDone;

	return theErr;
}

ErrCode SAppUtils::openWithDocs( eOpenWithDocsEventCode eventCode, CFSpec *appSpec,
									FSSpec *filesToLaunch, long numSpecs,
									ProcessSerialNumber *retPSN, eLaunchWithDocFlags flags )
{
	CFSpec						tempAppSpec( appSpec );
	ErrCode						theErr;
	LaunchParamBlockRec			launchParams;
	ProcessSerialNumber			myPSN;
	AEEventID					theEventID;
	AEDesc						myAddress, docDesc, launchDesc;
	AEDescList					theList;
	AliasHandle					fileAlias;
	AppleEvent					theEvent;
	long						i, launchFlags;
	EventRecord					dummyEvent;

	theEvent.dataHandle = NULL;
	launchDesc.dataHandle = NULL;

	theErr = tempAppSpec.verifySpec();
	BAILERR( theErr );

	if ( eventCode == kLaunchWithDocOpenDoc )
		theEventID = kAEOpenDocuments;
	else if ( eventCode == kLaunchWithDocPrintDoc )
		theEventID = kAEPrintDocuments;
	else {
		theErr = paramErr;
		goto bail;
	}
		
	GetCurrentProcess( &myPSN );
	theErr = AECreateDesc( typeProcessSerialNumber, (Ptr) &myPSN, sizeof(ProcessSerialNumber),
							&myAddress );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "openWithDocs:AECreateDesc.1", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = AECreateAppleEvent( kCoreEventClass, theEventID, &myAddress, kAutoGenerateReturnID,
									kAnyTransactionID, &theEvent );
	AEDisposeDesc( &myAddress );

	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "openWithDocs:AECreateAppleEvent", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = AECreateList( NULL, 0, false, &theList );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "openWithDocs:AECreateList", NULL, NULL, theErr, 0 );
		goto bail;
	}

	for ( i = 0; i < numSpecs; i++, filesToLaunch++ ) {
		theErr = NewAlias( NULL, filesToLaunch, &fileAlias );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "openWithDocs:NewAlias", NULL, NULL, theErr, 0 );
			goto bail;
		}
		if ( fileAlias == NULL ) {
			Debugger::debug( __LINE__, "openWithDocs:NewAlias.2" );
			theErr = memFullErr;
			goto bail;
		}

		HLock( (Handle) fileAlias );
		theErr = AECreateDesc( typeAlias, (Ptr) *fileAlias, GetHandleSize( (Handle) fileAlias ), &docDesc );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "openWithDocs:AECreateDesc.2", NULL, NULL, theErr, 0 );
			goto bail;
		}

		HUnlock( (Handle) fileAlias );
		DisposeHandle( (Handle) fileAlias );

		theErr = AEPutDesc( &theList, i, &docDesc );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "openWithDocs:AEPutDesc", NULL, NULL, theErr, 0 );
			goto bail;
		}

		AEDisposeDesc( &docDesc );
	}

	theErr = AEPutParamDesc( &theEvent, keyDirectObject, &theList );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "openWithDocs:AEPutParamDesc", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = AECoerceDesc( &theEvent, typeAppParameters, &launchDesc );
	if ( theErr != kErrNoErr ) { 
		Debugger::debug( __LINE__, "openWithDocs:AECoerceDesc", NULL, NULL, theErr, 0 );
		goto bail;
	}

	HLock( (Handle) launchDesc.dataHandle );

	launchFlags = launchContinue + launchNoFileFlags;
	if ( ( flags & kDontSwitchLayer ) != 0 )
		launchFlags |= launchDontSwitch;
	
	launchParams.launchAppSpec = tempAppSpec.getSpecP();
	launchParams.launchAppParameters = (AppParametersPtr) *( launchDesc.dataHandle );
	launchParams.launchBlockID = extendedBlock;
	launchParams.launchEPBLength = extendedBlockLen;
	launchParams.launchFileFlags = NULL;
	launchParams.launchControlFlags = launchFlags;
	
	theErr = LaunchApplication( &launchParams );
	
	EventAvail( 0, &dummyEvent );
	EventAvail( 0, &dummyEvent );
	EventAvail( 0, &dummyEvent );

	*retPSN = launchParams.launchProcessSN;

bail:

	if ( theEvent.dataHandle != NULL )
		AEDisposeDesc( &theEvent );

	if ( launchDesc.dataHandle != NULL )
		AEDisposeDesc( &launchDesc );

	return theErr;
}

ErrCode SAppUtils::sendAppDocs( eSendAppDocsEventCode eventCode, ProcessSerialNumber *appPSN,
									FSSpec *filesToLaunch, long numSpecs,
									eLaunchWithDocFlags flags )
{
	ErrCode					theErr;
	AEDesc					appAddress, docDesc;
	AEDescList				theList;
	AEEventID				theEventID;
	AliasHandle				fileAlias;
	AppleEvent				theEvent;
	long					i;
	EventRecord				dummyEvent;

	theEvent.dataHandle = NULL;

	if ( eventCode == SAppUtils::kSendAppDocsOpenDoc )
		theEventID = kAEOpenDocuments;
	else if ( eventCode == SAppUtils::kSendAppDocsPrintDoc )
		theEventID = kAEPrintDocuments;
	else {
		theErr = paramErr;
		goto bail;
	}
	
	theErr = AECreateDesc( typeProcessSerialNumber, (Ptr) appPSN, sizeof(ProcessSerialNumber),
							&appAddress );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "sendAppDocs:AECreateDesc", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = AECreateAppleEvent( kCoreEventClass, theEventID, &appAddress, kAutoGenerateReturnID,
									kAnyTransactionID, &theEvent );
	AEDisposeDesc( &appAddress );

	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "sendAppDocs:AECreateAppleEvent", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = AECreateList( NULL, 0, false, &theList );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "sendAppDocs:AECreateList", NULL, NULL, theErr, 0 );
		goto bail;
	}

	for ( i = 0; i < numSpecs; i++, filesToLaunch++ ) {
		theErr = NewAlias( NULL, filesToLaunch, &fileAlias );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "sendAppDocs:NewAlias", NULL, NULL, theErr, 0 );
			goto bail;
		}

		HLock( (Handle) fileAlias );
		theErr = AECreateDesc( typeAlias, (Ptr) *fileAlias, GetHandleSize( (Handle) fileAlias ), &docDesc );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "sendAppDocs:AECreateDesc", NULL, NULL, theErr, 0 );
			goto bail;
		}
		HUnlock( (Handle) fileAlias );
		DisposeHandle( (Handle) fileAlias );

		theErr = AEPutDesc( &theList, i, &docDesc );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "sendAppDocs:AEPutDesc", NULL, NULL, theErr, 0 );
			goto bail;
		}

		AEDisposeDesc( &docDesc );
	}

	theErr = AEPutParamDesc( &theEvent, keyDirectObject, &theList );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "sendAppDocs:AEPutParamDesc", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = AESend( &theEvent, NULL, kAENoReply + kAECanSwitchLayer, kAENormalPriority,
						kAEDefaultTimeout, NULL, NULL );

	EventAvail( 0, &dummyEvent );
	EventAvail( 0, &dummyEvent );
	EventAvail( 0, &dummyEvent );

bail:
	if ( theEvent.dataHandle != NULL )
		AEDisposeDesc( &theEvent );

	return theErr;

	UNUSED( flags );
}

ErrCode SAppUtils::launchApp( CFSpec *appSpec, ProcessSerialNumber *retPSN, long flags )
{
	LaunchParamBlockRec		launchParams;
	EventRecord					dummyEvent;
	ErrCode							theErr;

	theErr = appSpec->verifySpec();
	if ( theErr != kErrNoErr )
		return theErr;
	
	launchParams.launchBlockID = extendedBlock;
	launchParams.launchEPBLength = extendedBlockLen;
	launchParams.launchFileFlags = 0;
	launchParams.launchAppSpec = appSpec->getSpecP();
	launchParams.launchAppParameters = NULL;
	
	launchParams.launchControlFlags = launchContinue + launchNoFileFlags;

	if ( ( flags & kDontSwitchLayer ) != 0 )
		launchParams.launchControlFlags |= launchDontSwitch;

	theErr = LaunchApplication( &launchParams );
	
	EventAvail( 0, &dummyEvent );
	EventAvail( 0, &dummyEvent );
	EventAvail( 0, &dummyEvent );

	*retPSN = launchParams.launchProcessSN;
	
	return theErr;
}

ErrCode SAppUtils::sendEvent( AppleEvent *theEvent, OSType creator )
{
	ProcessSerialNumber		psn;
	CFSpec					appSpec( 0, 0, (StringPtr) "\0" );
	AppleEvent				reply;
	long					vRef, parID;
	Str255					pName;
	ErrCode					theErr;

	theErr = SProcesses::findProcess( creator, 'APPL', &psn, &appSpec );

	if ( theErr == kErrNoErr ) {
		SetFrontProcess( &psn );
		theErr = AESend( theEvent, &reply, kAENoReply, kAEHighPriority, kNoTimeOut, NULL, NULL );
		if ( theErr != kErrNoErr )
			Debugger::debug( __LINE__, "sevt.aes", NULL, NULL, theErr, 0 );
	
		return theErr;
	}

	theErr = SAppFinder::findAPPLSingle( creator, &vRef, &parID, &pName[ 0 ], 0 );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "sevt.fam 1st try", NULL, NULL, theErr, creator );

		theErr = SAppFinder::findAPPLSingle( creator, &vRef, &parID, &pName[ 0 ], 1 );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "sevt.fam 2nd try", NULL, NULL, theErr, creator );
			goto bail;
		}
	}

	appSpec.setFrom( vRef, parID, &pName[ 0 ] );
	theErr = appSpec.verifySpec();
	if ( theErr != kErrNoErr ) {
		appSpec.dumpInfo( theErr, _TXL( "sevt.fsmfss" ) );
		goto bail;
	}

	theErr = launchFSSpec( &appSpec, theEvent );

bail:

	return theErr;
}

ErrCode SAppUtils::launchFSSpec( CFSpec *appSpec, AppleEvent *theEvent )
{
	ErrCode						theErr;
	LaunchParamBlockRec			launchParams;
	AEDesc						launchDesc;
	EventRecord					dummyEvent;

	launchDesc.dataHandle = NULL;

	theErr = AECoerceDesc( theEvent, typeAppParameters, &launchDesc );
	if ( theErr != kErrNoErr ) { 
		Debugger::debug( __LINE__, "launchFSSpec:AECoerceDesc", NULL, NULL, theErr, 0 );
		goto bail;
	}

	HLock( (Handle) launchDesc.dataHandle );

	launchParams.launchAppSpec = appSpec->getSpecP();
	launchParams.launchAppParameters = (AppParametersPtr) *( launchDesc.dataHandle );
	launchParams.launchBlockID = extendedBlock;
	launchParams.launchEPBLength = extendedBlockLen;
	launchParams.launchFileFlags = NULL;
	launchParams.launchControlFlags = launchContinue + launchNoFileFlags;
	
	theErr = LaunchApplication( &launchParams );
	
	EventAvail( 0, &dummyEvent );
	EventAvail( 0, &dummyEvent );
	EventAvail( 0, &dummyEvent );

bail:

	if ( launchDesc.dataHandle != NULL )
		AEDisposeDesc( &launchDesc );

	return theErr;
}

