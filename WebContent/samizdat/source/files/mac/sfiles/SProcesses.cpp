/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SProcesses.h"
#include "CUtils.h"
#include "Debugger.h"

ErrCode SProcesses::findProcess( OSType creator, OSType typ, ProcessSerialNumber *processP, CFSpec *specP )
{
	ProcessInfoRec		info;
	Boolean				bFound;
	
	bFound = false;

	processP->highLongOfPSN = 0;
	processP->lowLongOfPSN = kNoProcess;

	info.processInfoLength = sizeof( ProcessInfoRec );
	info.processName = NULL;
	info.processAppSpec = specP->getSpecP();

	while ( GetNextProcess( processP ) == kErrNoErr ) {
		if ( GetProcessInformation( processP, &info ) == kErrNoErr && 
			info.processType == typ & info.processSignature == creator ) {
				bFound = true;
				break;
		}
	}
	
	return bFound ? kErrNoErr : -1;
}

ErrCode SProcesses::quitProcess( ProcessSerialNumber *thePSN, long flags )
{
	AppleEvent		theEvent;
	AEDesc			theAddress;
	ErrCode			theErr;

	theErr = AECreateDesc( typeProcessSerialNumber, (Ptr) thePSN,
							sizeof(ProcessSerialNumber), &theAddress );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "quitApp.aecd", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = AECreateAppleEvent( kCoreEventClass, kAEQuitApplication, &theAddress,
								kAutoGenerateReturnID, kAnyTransactionID, &theEvent );
	AEDisposeDesc( &theAddress );

	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "quitApp.aecae", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = AESend( &theEvent, NULL, kAENoReply + kAECanSwitchLayer, kAENormalPriority,
						kAEDefaultTimeout, NULL, NULL );

	AEDisposeDesc( &theEvent );

bail:

	return theErr;

	UNUSED( flags );
}

ErrCode SProcesses::moveProcess( ProcessSerialNumber *psnP, eMoveProcess selector, long flags )
{
	ErrCode		theErr;

	switch ( selector ) {
		case kMoveProcessToFront:
			theErr = SetFrontProcess( psnP );
		break;

		case kMoveProcessToBack:
			theErr = setFinderAsFrontProcess();
		break;

		case kMoveProcessMinimize:
			theErr = hideProcess( psnP );
		break;

		case kMoveProcessMaximize:
			theErr = paramErr;
		break;

		default:
			theErr = paramErr;
		break;
	}
	
	return theErr;

	UNUSED( flags );
}

ErrCode SProcesses::verifyPSN( ProcessSerialNumber *thePSN )
{
	ProcessInfoRec		pir;
	Str31					processName;
	FSSpec				procSpec;
	
	CUtils::zeroset( &pir, sizeof(ProcessInfoRec) );
	pir.processInfoLength = sizeof(ProcessInfoRec);
	pir.processName = processName;
	pir.processAppSpec = &procSpec;

	return GetProcessInformation( thePSN, &pir );
}

ErrCode SProcesses::getProcesses( long maxToReturn, long flags, long *numReturned,
									long *vRefsP, long *parIDsP, StringPtr namesP,
									long *PSNLoP, long *PSNHiP, long *proFlagsP )
{
	ProcessInfoRec			infoRec;
	Str31					processName;
	FSSpec					procSpec;
	ProcessSerialNumber		processSN, curPSN;
	ErrCode					theErr;
	long					numDone;

	GetCurrentProcess( &curPSN );

	processSN.lowLongOfPSN = kNoProcess;
	processSN.highLongOfPSN = 0;

	for ( numDone = 0; numDone < maxToReturn; ) {
		theErr = GetNextProcess( &processSN );
		if ( theErr != kErrNoErr )
			break;

		infoRec.processInfoLength = sizeof(ProcessInfoRec);
		infoRec.processName = processName;
		infoRec.processAppSpec = &procSpec;
		theErr = GetProcessInformation( &processSN, &infoRec );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "getProcesses.gpi", NULL, NULL, theErr, 0 );
			break;
		}
		
		*vRefsP++ = procSpec.vRefNum;
		*parIDsP++ = procSpec.parID;

		CUtils::pStrncpy( namesP, &procSpec.name[ 0 ], kGetProcessMaxNameLen - 1 );
		namesP += kGetProcessMaxNameLen;

		*PSNLoP++ = processSN.lowLongOfPSN;
		*PSNHiP++ = processSN.highLongOfPSN;
		*proFlagsP++ = 0;
		numDone++;
	}

	*numReturned = numDone;
	if ( numDone > 0 )
		theErr = kErrNoErr;

	return theErr;

	UNUSED( flags );
}

ErrCode SProcesses::setFinderAsFrontProcess( void )
{
	ProcessInfoRec		infoRec;
	Str31				processName;
	FSSpec				procSpec;
	ProcessSerialNumber	processSN;
	ErrCode				theErr;

	processSN.lowLongOfPSN = kNoProcess;
	processSN.highLongOfPSN = 0;

	while ( TRUE ) {
		theErr = GetNextProcess( &processSN );
		if ( theErr != kErrNoErr )
			break;

		infoRec.processInfoLength = sizeof(ProcessInfoRec);
		infoRec.processName = processName;
		infoRec.processAppSpec = &procSpec;
		theErr = GetProcessInformation( &processSN, &infoRec );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "setFinderAsFrontProcess.gpi", NULL, NULL, theErr, 0 );
			break;
		}

		if ( infoRec.processSignature == 'MACS' && infoRec.processType == 'FNDR' )
			break;
	}
	
	if ( theErr == kErrNoErr )
		SetFrontProcess( &processSN );
	else
		Debugger::debug( __LINE__, "setFinderAsFrontProcess: finder not found", NULL, NULL, theErr, 0 );
		
	return theErr;
}

ErrCode SProcesses::escortProcessToTheFront( ProcessSerialNumber *psnP )
{
	ProcessSerialNumber		tempPSN;
	EventRecord				dummyEvent;
	ErrCode					theErr;
	long					i;
	Boolean					bSameProcess;

	theErr = SetFrontProcess( psnP );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "epttf.sfp", NULL, NULL, theErr, 0 );
		goto bail;
	}

	for ( i = 0; i < 10; i++ ) {
		EventAvail( everyEvent, &dummyEvent );
		EventAvail( everyEvent, &dummyEvent );
		EventAvail( everyEvent, &dummyEvent );

		theErr = GetFrontProcess( &tempPSN );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "epttf.gfp", NULL, NULL, theErr, 0 );
			goto bail;
		}

		theErr = SameProcess( psnP, &tempPSN, &bSameProcess );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "epttf.sp", NULL, NULL, theErr, 0 );
			goto bail;
		}
			
		if ( bSameProcess ) {
			theErr = kErrNoErr;
			break;
		}
		else
			theErr = -1;
	}

bail:

	return theErr;
}

ErrCode SProcesses::hideProcess( ProcessSerialNumber *psnP )
{
	ProcessSerialNumber		tempPSN;
	ErrCode					theErr;
	Boolean					bSameProcess;

	theErr = GetFrontProcess( &tempPSN );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "hideProcess.gfp", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = SameProcess( psnP, &tempPSN, &bSameProcess );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "hideProcess.sp", NULL, NULL, theErr, 0 );
		goto bail;
	}
		
	if ( !bSameProcess ) {
		theErr = escortProcessToTheFront( psnP );
		if ( theErr != kErrNoErr )
			goto bail;
	}

	hideFrontProcess();

bail:

	return theErr;
}

#if defined(macintosh)
ErrCode SProcesses::hideFrontProcess( void )
{
	SystemMenu( 0xBF970001 );
	
	return kErrNoErr;
}
#elif defined(__osx__)

extern "C" {
extern long CPSPostHideReq( ProcessSerialNumber *psn );
};

ErrCode SProcesses::hideFrontProcess( void )
{
	ProcessSerialNumber		tempPSN;
	ErrCode					theErr;

	if ( CPSPostHideReq == (void *) kUnresolvedCFragSymbolAddress )
		return kErrParamErr;

	theErr = GetFrontProcess( &tempPSN );
	if ( theErr != kErrNoErr )
		return theErr;

	return CPSPostHideReq( &tempPSN );
}
#endif


