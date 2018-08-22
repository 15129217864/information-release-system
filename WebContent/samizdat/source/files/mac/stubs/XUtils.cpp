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
#include "XUtils.h"
#include <stdlib.h>
#include "SAppUtilsOSX.h"
#include "CFilePathOSX.h"
#include "Debugger.h"

/*
/home/jconfig/samizdat/common/JConfig.zip
/System/Library/Frameworks

com.tolstoy.testjc.Tester

/home/jconfig/samizdat/jconfig/Tester.app

#if defined(__osx__)
	CFilePathOSX		cfPath( csFullPath );
#else
	CFilePath			cfPath( csFullPath );
#endif
	ErrCode				theErr;

	theErr = FSpLocationFromFullPath( cfPath.getLength(), cfPath.getBuf(), theSpec->getSpecP() );
*/

SFUNC( JINT, XUtilsOSX_nGetPOSIXPath ), 
JINT vRef,
JINT parID,
JBYTEARRAY pName,
jobjectArray pOutPath )
{
	ErrCode			theErr = kErrNoErr;
	DECLARESTR( csOutPath );
	DECLARECFSPEC( theSpec );

	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pOutPath, 1 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		try {
			csOutPath = new CStr( CStr::kMaxPath );
		}
		catch ( LPCTSTR ee ) {
			theErr = kErrException;
			goto bail;
		}

		theErr = SAppUtilsOSX::getPOSIXPath( theSpec, csOutPath );

		setStrArrayElementUTF( pEnv, pOutPath, 0, csOutPath->getBuf(), csOutPath->getLength() );

	bail:

		DELETECFSPEC( theSpec );
		DELETESTR( csOutPath );

	} ENDTRY

	return theErr; 
}

SFUNC( JINT, XUtilsOSX_nGetProcessBundleLocation ), 
JINTARRAY appPSN,
JINTARRAY pVRefAndParID,
JBYTEARRAY pName )
{
	FSSpec					theSpec;
	ErrCode					theErr;
	ProcessSerialNumber		thePSN;

	BEGINTRY {
		CHECKSIZE( appPSN, 2 );
		CHECKSIZE( pVRefAndParID, 2 );
		CHECKNULL( pName );

		GETJINT2( appPSN, &thePSN.lowLongOfPSN, &thePSN.highLongOfPSN );

		theErr = SAppUtilsOSX::getProcessBundleLocation( &thePSN, &theSpec );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "AppUtilsOSX_nGetProcessBundleLocation.gpbl", NULL, NULL, theErr, 0 );
			goto bail;
		}

		COPYPSTRINGTOJBYTES( pName, theSpec.name );
		SETJINT2( pVRefAndParID, theSpec.vRefNum, theSpec.parID );

	bail:

		;

	} ENDTRY

	return theErr; 
}

SFUNC( JINT, XUtilsOSX_nFindApplicationForInfo ), 
JINT creator,
JSTRING pBundleID,
JSTRING pAppName,
JINTARRAY pVRefAndParID,
JBYTEARRAY pName )
{
	FSSpec					theSpec;
	ErrCode					theErr;
	DECLARESTR( csBundleID );
	DECLARESTR( csAppName );

	BEGINTRY {
		CHECKSIZE( pVRefAndParID, 2 );
		CHECKNULL( pName );

		if ( pBundleID != NULL )
			MAKESTR( pBundleID, csBundleID );

		if ( pAppName != NULL )
			MAKESTR( pAppName, csAppName );

		theErr = SAppUtilsOSX::findApplicationForInfo( creator, csBundleID, csAppName, &theSpec );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "XUtilsOSX_nFindApplicationForInfo.fafi", NULL, NULL, theErr, 0 );
			goto bail;
		}

		COPYPSTRINGTOJBYTES( pName, theSpec.name );
		SETJINT2( pVRefAndParID, theSpec.vRefNum, theSpec.parID );

	bail:

		DELETESTR( csAppName );
		DELETESTR( csBundleID );

	} ENDTRY

	return theErr; 
}

SFUNC( JINT, XUtilsOSX_nGetApplicationForInfo ), 
JINT type,
JINT creator,
JSTRING pExtension,
JINT roleMask,
JINTARRAY pVRefAndParID,
JBYTEARRAY pName )
{
	FSSpec					theSpec;
	ErrCode					theErr;
	DECLARESTR( csExtension );

	BEGINTRY {
		CHECKSIZE( pVRefAndParID, 2 );
		CHECKNULL( pName );

		if ( pExtension != NULL )
			MAKESTR( pExtension, csExtension );

		theErr = SAppUtilsOSX::getApplicationForInfo( type, creator, csExtension, roleMask, &theSpec );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "XUtilsOSX_nFindApplicationForInfo.fafi", NULL, NULL, theErr, 0 );
			goto bail;
		}

		COPYPSTRINGTOJBYTES( pName, theSpec.name );
		SETJINT2( pVRefAndParID, theSpec.vRefNum, theSpec.parID );

	bail:

		DELETESTR( csExtension );

	} ENDTRY

	return theErr; 
}

