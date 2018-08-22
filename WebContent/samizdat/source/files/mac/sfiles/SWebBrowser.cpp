/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SWebBrowser.h"
#include "CUtils.h"
#include "SAppUtils.h"
#include "Debugger.h"

ErrCode SWebBrowser::launchURLDirect( const CStr *csURL, OSType creator, long flags )
{
	AppleEvent		theEvent;
	AEDesc			targetAddress;
	ErrCode			theErr;

	theEvent.dataHandle = NULL;
	targetAddress.dataHandle = NULL;

	theErr = AECreateDesc( typeApplSignature, &creator, sizeof(creator), &targetAddress );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "lurld.aecd", NULL, NULL, theErr, creator );
		goto bail;
	}

	theErr = AECreateAppleEvent( 'GURL', 'GURL', &targetAddress, kAutoGenerateReturnID, kAnyTransactionID, &theEvent );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "lurld.aecae", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = AEPutKeyPtr( &theEvent, keyDirectObject, typeChar,
							csURL->getBuf(), csURL->getLength() );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "lurld.aepkp", csURL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = SAppUtils::sendEvent( &theEvent, creator );

bail:

	if ( theEvent.dataHandle != NULL )
		AEDisposeDesc( &theEvent );

	if ( targetAddress.dataHandle != NULL )
		AEDisposeDesc( &targetAddress );

	return theErr;

	UNUSED( flags );
}

