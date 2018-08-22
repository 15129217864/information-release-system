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
#include "CString.h"
#include "AppUtilsNixLinux.h"
#include "CNixUtils.h"
#include "CStringVector.h"
#include "CDateBundle.h"
#include "Debugger.h"

/*
	private static native int nStat( int selector, String filePath, int retArray[], int datesArray[] );
 * Class:     com_jconfig_nix_AppUtilsNixLinux
 * Method:    nStat
 * Signature: (ILjava/lang/String;[I[I)I
 */
EXP JINT SFUNC( jconfig, AppUtilsNixLinux, nStat ),
JINT selector,
JSTRING pFilePath,
JINTARRAY pRetArray,
JINTARRAY pDatesArray )
{
	long			theErr, theInts[ CNixUtils::kStatRetArrayLen ];
	DECLAREDATEBUNDLE( dateBundle );
	DECLARESTR( csFilePath );
	
	BEGINTRY {
		CHECKNULL( pFilePath );
		CHECKSIZE( pRetArray, CNixUtils::kStatRetArrayLen );
		CHECKSIZE( pDatesArray, CDateBundle::kDatesArrayLen );
		MAKESTR( pFilePath, csFilePath );
		dateBundle = new CDateBundle();
	
		theErr = CNixUtils::iStat( selector, csFilePath, &theInts[ 0 ], dateBundle );
		BAILERR( theErr );
	
		SLAMDATEBUNDLE( dateBundle, pDatesArray );
		SETINTARRAYREGION( pRetArray, 0, CNixUtils::kStatRetArrayLen, (int*) &theInts[ 0 ] );
	
	bail:
	
		DELETEDATEBUNDLE( dateBundle );
		DELETESTR( csFilePath );
	}
	ENDTRY

	return theErr;
}

 /*
   private static native int nReadLink( String linkFilePath, String resolvedFile[] );
 * Class:     com_jconfig_nix_AppUtilsNixLinux
 * Method:    nReadLink
 * Signature: (Ljava/lang/String;[Ljava/lang/String;)I
 */
EXP JINT SFUNC( jconfig, AppUtilsNixLinux, nReadLink ),
JSTRING pLinkFilePath,
JOBJECTARRAY pRetPath )
{
	long			theErr;
	DECLARESTR( csLinkFilePath );
	DECLARESTR( csResolvedFile );
	
	BEGINTRY {
		CHECKSIZE( pRetPath, 1 );
		MAKESTR( pLinkFilePath, csLinkFilePath );
	
		csResolvedFile = new CStr( CStr::kMaxPath );
		theErr = CNixUtils::iReadLink( csLinkFilePath, csResolvedFile );
	
		SETSTRARRAYELEMENT( pRetPath, 0, csResolvedFile );
	
	bail:
	
		DELETESTR( csResolvedFile );
		DELETESTR( csLinkFilePath );
	}
	ENDTRY

	return theErr;
}

/*
	private static native int nStatFS( String fileName, int retInts[] );
 * Class:     com_jconfig_nix_AppUtilsNixLinux
 * Method:    nStatFS
 * Signature: (Ljava/lang/String;[I)I
 */
EXP JINT SFUNC( jconfig, AppUtilsNixLinux, nStatFS ),
JSTRING pFilePath,
JINTARRAY pRetArray )
{
	long			theErr, theInts[ CNixUtils::kStatFSRetArrayLen ];
	DECLARESTR( csFilePath );
	
	BEGINTRY {
		CHECKNULL( pFilePath );
		CHECKSIZE( pRetArray, CNixUtils::kStatFSRetArrayLen );
		MAKESTR( pFilePath, csFilePath );
	
		theErr = CNixUtils::iStatFS( csFilePath, &theInts[ 0 ] );
		BAILERR( theErr );
	
		SETINTARRAYREGION( pRetArray, 0, CNixUtils::kStatFSRetArrayLen, (int*) &theInts[ 0 ] );
	
	bail:
	
		DELETESTR( csFilePath );
	}
	ENDTRY

	return theErr;
}

/*
	private static native int nGetMntEnt( String mntFileName, String retQuads[], int maxToReturn, int numReturned[] );
 * Class:     com_jconfig_nix_AppUtilsNixLinux
 * Method:    nGetMntEnt
 * Signature: (Ljava/lang/String;[Ljava/lang/String;I[I)I
 */
EXP JINT SFUNC( jconfig, AppUtilsNixLinux, nGetMntEnt ),
JSTRING pMntFileName,
JOBJECTARRAY pRetQuads, 
JINT maxToReturn, 
JINTARRAY pNumReturned )
{
	long			theErr, numReturned;
	DECLARESTRINGVECTOR( quadVec );
	DECLARESTR( csMntFileName );
	
	BEGINTRY {
		numReturned = 0;
		if ( maxToReturn < 1 ) {
			theErr = kErrParamErr;
			goto bail;
		}

		CHECKNULL( pMntFileName );
		CHECKSIZE( pRetQuads, 4 * maxToReturn );
		CHECKSIZE( pNumReturned, 1 );
		MAKESTR( pMntFileName, csMntFileName );
	
		quadVec = new CStringVector( 4 * maxToReturn );

		theErr = CNixUtils::iGetMntEnt( csMntFileName, quadVec, maxToReturn, &numReturned );
		BAILERR( theErr );
	
		SLAMSTRINGVECTOR( quadVec, pRetQuads );
		SETINTARRAYREGION( pNumReturned, 0, 1, (int*) &numReturned );
	
	bail:
	
		DELETESTR( csMntFileName );
		DELETESTRINGVECTOR( quadVec );
	}
	ENDTRY

	return theErr;
}


