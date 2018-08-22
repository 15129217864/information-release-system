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
#include <commctrl.h>
#include "CString.h"
#include "AppUtilsMSVM.h"
#include "AppData.h"

#include "SIconInfo.h"
#include "SFileIterate.h"
#include "SVersionInfo.h"
#include "SAliases.h"
#include "SMonitors.h"
#include "SFileInfo.h"
#include "SVolumes.h"
#include "SAppInfo.h"
#include "SWebBrowser.h"
#include "SAppFinder.h"

#include "CStringVector.h"
#include "CDateBundle.h"
#include "Debugger.h"
#include "CMemory.h"

#include "CWinProcess.h"

#include "stub_macros.h"
#include <native.h>

#if defined(DO_SEH)
	#include "sehstubs.cpp"
#endif



//	nativeGetAllProcessInfo( int cntUsageArray[], int th32ProcessIDArray[],
//									int th32DefaultHeapIDArray[], int th32ModuleIDArray[],
//									int cntThreadsArray[], int th32ParentProcessIDArray[],
//									int pcPriClassBaseArray[], int dwFlagsArray[],
//									int dwThreadIDArray[], int hWndArray[],
//									String fullPathArray[], int maxToReturn, int numReturned[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetAllProcessInfo ),
JINTARRAY pcntUsageArray, 
JINTARRAY pth32ProcessIDArray,
JINTARRAY pth32DefaultHeapIDArray, 
JINTARRAY pth32ModuleIDArray,
JINTARRAY pcntThreadsArray, 
JINTARRAY pth32ParentProcessIDArray,
JINTARRAY ppcPriClassBaseArray, 
JINTARRAY pdwFlagsArray,
JINTARRAY pdwThreadIDArray, 
JINTARRAY phWndArray,
JOBJECTARRAY pfullPathArray, 
JINT maxToReturn, 
JINTARRAY pnumReturned )
{
	CVector			vec( 10, 10 );
	CWinProcess		*processObject;
	CStr			*cstr;
	ErrCode			theErr;
	long			i, numToReturn, *numReturnedP;
	HWND			*hWndArrayP;
	DWORD			*cntUsageArrayP,
					*th32ProcessIDArrayP,
					*th32DefaultHeapIDArrayP,
					*th32ModuleIDArrayP,
					*cntThreadsArrayP,
					*th32ParentProcessIDArrayP,
					*pcPriClassBaseArrayP,
					*dwFlagsArrayP,
					*dwThreadIDArrayP;

	BEGINTRY {
		CHECKSIZE( pcntUsageArray, maxToReturn );
		CHECKSIZE( pth32ProcessIDArray, maxToReturn );
		CHECKSIZE( pth32DefaultHeapIDArray, maxToReturn );
		CHECKSIZE( pth32ModuleIDArray, maxToReturn );
		CHECKSIZE( pcntThreadsArray, maxToReturn );
		CHECKSIZE( pth32ParentProcessIDArray, maxToReturn );
		CHECKSIZE( ppcPriClassBaseArray, maxToReturn );
		CHECKSIZE( pdwFlagsArray, maxToReturn );
		CHECKSIZE( pdwThreadIDArray, maxToReturn );
		CHECKSIZE( phWndArray, maxToReturn );
		CHECKSIZE( pfullPathArray, maxToReturn );
		CHECKSIZE( pnumReturned, 1 );

		theErr = CWinProcess::getAllProcessInfo( vec );
		if ( theErr != kErrNoErr )
			goto bail;

		numToReturn = vec.getSize();
		if ( numToReturn > maxToReturn )
			numToReturn = maxToReturn;

		cntUsageArrayP = (DWORD*) LOCKINTARRAY( pcntUsageArray );
		th32ProcessIDArrayP = (DWORD*) LOCKINTARRAY( pth32ProcessIDArray );
		th32DefaultHeapIDArrayP = (DWORD*) LOCKINTARRAY( pth32DefaultHeapIDArray );
		th32ModuleIDArrayP = (DWORD*) LOCKINTARRAY( pth32ModuleIDArray );
		cntThreadsArrayP = (DWORD*) LOCKINTARRAY( pcntThreadsArray );
		th32ParentProcessIDArrayP = (DWORD*) LOCKINTARRAY( pth32ParentProcessIDArray );
		pcPriClassBaseArrayP = (DWORD*) LOCKINTARRAY( ppcPriClassBaseArray );
		dwFlagsArrayP = (DWORD*) LOCKINTARRAY( pdwFlagsArray );
		dwThreadIDArrayP = (DWORD*) LOCKINTARRAY( pdwThreadIDArray );
		hWndArrayP = (HWND*) LOCKINTARRAY( phWndArray );
		numReturnedP = LOCKINTARRAY( pnumReturned );

		for ( i = 0; i < numToReturn; i++ ) {
			processObject = (CWinProcess*) vec.elementAt( i );

			processObject->getData( &cntUsageArrayP[ i ], &th32ProcessIDArrayP[ i ],
									&th32DefaultHeapIDArrayP[ i ], &th32ModuleIDArrayP[ i ],
									&cntThreadsArrayP[ i ], &th32ParentProcessIDArrayP[ i ],
									&pcPriClassBaseArrayP[ i ], &dwFlagsArrayP[ i ],
									&dwThreadIDArrayP[ i ], &hWndArrayP[ i ] );

			cstr = processObject->getFullPath();

			SETSTRARRAYELEMENT( pfullPathArray, i, cstr );

			delete processObject;
			delete cstr;
		}

		numReturnedP[ 0 ] = numToReturn;

		UNLOCKINTARRAY( pcntUsageArray, (long*) cntUsageArrayP );
		UNLOCKINTARRAY( pth32ProcessIDArray, (long*) th32ProcessIDArrayP );
		UNLOCKINTARRAY( pth32DefaultHeapIDArray, (long*) th32DefaultHeapIDArrayP );
		UNLOCKINTARRAY( pth32ModuleIDArray, (long*) th32ModuleIDArrayP );
		UNLOCKINTARRAY( pcntThreadsArray, (long*) cntThreadsArrayP );
		UNLOCKINTARRAY( pth32ParentProcessIDArray, (long*) th32ParentProcessIDArrayP );
		UNLOCKINTARRAY( ppcPriClassBaseArray, (long*) pcPriClassBaseArrayP );
		UNLOCKINTARRAY( pdwFlagsArray, (long*) dwFlagsArrayP );
		UNLOCKINTARRAY( pdwThreadIDArray, (long*) dwThreadIDArrayP );
		UNLOCKINTARRAY( phWndArray, (long*) hWndArrayP );
		UNLOCKINTARRAY( pnumReturned, (long*) numReturnedP );
	}
	ENDTRY

bail:

	return theErr;
}

/*
#if defined(DO_JNI)
#elif defined(DO_RNI1) || defined(DO_RNI2)

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetAllProcessInfo ),
JVECTOR pVector )
{
	JOBJECT					pProcessObject;
	CVector					vec( 10, 10 );
	CWinProcess				*processObject;
	ErrCode					theErr;
	long					i, len, numDone;
	struct methodblock		*pmbAddElement;

	BEGINTRY {
		numDone = 0;
		
		theErr = CWinProcess::getAllProcessInfo( vec );
		if ( theErr != kErrNoErr )
			goto bail;

		pmbAddElement = get_methodblock( (HObject*) pVector, "addElement", "(Ljava/lang/Object;)V");
		if ( pmbAddElement == NULL ) {
			Debugger::debug( __LINE__, _TXL( "nativeGetAllProcessInfo: pmbAddElement" ) );
			theErr = kErrParamErr;
			goto bail;
		}

		len = vec.getSize();

		Debugger::debug( __LINE__, _TXL( "nativeGetAllProcessInfo: numelements, error" ), NULL, NULL, len, theErr );

		for ( i = 0; i < len; i++ ) {
			processObject = (CWinProcess*) vec.elementAt( i );
			pProcessObject = CREATECWINPROCESSJOBJECT( processObject );
			delete processObject;
			do_execute_java_method( NULL, (void*) pVector, NULL, NULL, pmbAddElement, FALSE, pProcessObject );
		}
	}
	ENDTRY

bail:

	return theErr;
}

#endif
*/


//	int nativeGetDriveDisplayName( String driveName, String displayName[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetShortPathName ),
JSTRING pInFileName,
JOBJECTARRAY pOutFileName )
{
	long			theErr;
	DECLARESTR( inFileName );
	DECLARESTR( outFileName );
	
	BEGINTRY {
		CHECKNULL( pOutFileName );
		MAKESTR( pInFileName, inFileName );
	
		outFileName = new CStr( CStr::kMaxPath );
		theErr = SFileInfo::iGetShortPathName( inFileName, outFileName );
	
		SETSTRARRAYELEMENT( pOutFileName, 0, outFileName );
	
	bail:
	
		DELETESTR( inFileName );
		DELETESTR( outFileName );
	}
	ENDTRY

	return theErr;
}









//	int nativeFindFirstFile( String searchStr, int hFind[], int attrs[], String fileName[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeFindFirstFile ),
JSTRING pSearchString,
JINTARRAY pRetHFind,
JINTARRAY pRetAttrs,
JOBJECTARRAY pRetFileName )
{
	long			theErr, findH, dwAttrs;
	DECLARESTR( searchString );
	DECLARESTR( fileName );

	BEGINTRY {
		CHECKSIZE( pRetHFind, 1 );
		CHECKSIZE( pRetAttrs, 1 );
		CHECKSIZE( pRetFileName, 1 );
		MAKESTR( pSearchString, searchString );

		fileName = new CStr( CStr::kMaxPath );
		theErr = SFileIterate::iFindFirstFile( searchString, &findH, &dwAttrs, fileName );
		BAILERR( theErr );
	
		SETSTRARRAYELEMENT( pRetFileName, 0, fileName );
		SETINTARRAYREGION( pRetHFind, 0, 1, &findH );
		SETINTARRAYREGION( pRetAttrs, 0, 1, &dwAttrs );
	
	bail:
	
		DELETESTR( fileName );
		DELETESTR( searchString );
	}
	ENDTRY

	return theErr;
}




//	int nativeFindNextFile( int findHandle, int attrs[], String fileName[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeFindNextFile ),
JINT findH,
JINTARRAY pRetAttrs,
JOBJECTARRAY pRetFileName )
{
	long			theErr, dwAttrs;
	DECLARESTR( fileName );
	
	BEGINTRY {
		CHECKSIZE( pRetAttrs, 1 );
		CHECKSIZE( pRetFileName, 1 );
		if ( findH == 0 )
			return kErrParamErr;
	
		fileName = new CStr( CStr::kMaxPath );
		theErr = SFileIterate::iFindNextFile( findH, &dwAttrs, fileName );
		BAILERR( theErr );
	
		SETSTRARRAYELEMENT( pRetFileName, 0, fileName );
		SETINTARRAYREGION( pRetAttrs, 0, 1, &dwAttrs );
	
	bail:
	
		DELETESTR( fileName );
	}
	ENDTRY

	return theErr;
}

//	int nativeFindClose( int findHandle );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeFindClose ),
JINT findH )
{
	if ( findH == 0 )
		return kErrParamErr;
	else
		return SFileIterate::iNativeFindClose( findH );
}







//	int nativeGetVolumeInformation( String driveName, String infoStr[], int infoInt[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeInformation ),
JSTRING pDriveName,
JOBJECTARRAY pInfoStrs,
JINTARRAY pInfoInts )
{
	long			theErr, theInts[ SVolumes::kGVIIntArrayLen ];
	DECLARESTR( driveName );
	DECLARESTR( volName );
	DECLARESTR( fileSystemName );

	enum {
		kStringArrayLen = 2		//	the number of strings put into 'pInfoStrs'
	};

	BEGINTRY {
		CHECKNULL( pDriveName );
		CHECKSIZE( pInfoStrs, kStringArrayLen );
		CHECKSIZE( pInfoInts, SVolumes::kGVIIntArrayLen );
		MAKESTR( pDriveName, driveName );
	
		volName = new CStr( CStr::kMaxPath );
		fileSystemName = new CStr( CStr::kMaxPath );
		theErr = SVolumes::iGetVolumeInformation( driveName, volName, fileSystemName, (unsigned long*) &theInts[ 0 ] );
		BAILERR( theErr );
	
		SETSTRARRAYELEMENT( pInfoStrs, 0, volName );
		SETSTRARRAYELEMENT( pInfoStrs, 1, fileSystemName );
		SETINTARRAYREGION( pInfoInts, 0, SVolumes::kGVIIntArrayLen, &theInts[ 0 ] );
	
	bail:
	
		DELETESTR( fileSystemName );
		DELETESTR( volName );
		DELETESTR( driveName );
	}
	ENDTRY

	return theErr;
}





//	int nativeSetVolumeLabel( String driveName, String newLabel );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeSetVolumeLabel ),
JSTRING pDriveName,
JSTRING pNewLabel )
{
	long			theErr;
	DECLARESTR( driveName );
	DECLARESTR( newLabel );
	
	BEGINTRY {
		MAKESTR( pDriveName, driveName );
		MAKESTR( pNewLabel, newLabel );
	
		theErr = SVolumes::iSetVolumeLabel( driveName, newLabel );
	
	bail:
	
		DELETESTR( newLabel );
		DELETESTR( driveName );
	}
	ENDTRY

	return theErr;
}


//	int nativeGetVolumeCapInfo( String driveName, long capInfo[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeCapInfo ),
JSTRING pDriveName,
JLONGARRAY pCapInfo )
{
	unsigned __int64	cap[ SVolumes::kVolumeCapInfoLen ];
	long				theErr;
	DECLARESTR( driveName );
	
	BEGINTRY {
		CHECKNULL( pCapInfo );
		MAKESTR( pDriveName, driveName );
	
		theErr = SVolumes::iGetVolumeCapInfo( driveName, &cap[ 0 ] );
	
		SETLONGARRAYREGION( pCapInfo, 0, SVolumes::kVolumeCapInfoLen, (__int64*) &cap[ 0 ] );
	
	bail:
	
		DELETESTR( driveName );
	}
	ENDTRY

	return theErr;
}


//	int nativeGetVolumeDate( int which, String driveName, int modDate[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeDate ),
JINT which,
JSTRING pDriveName,
JINTARRAY pDatesArray )
{
	long			theErr;
	DECLAREDATEBUNDLE( dateBundle );
	DECLARESTR( driveName );
	
	BEGINTRY {
		CHECKSIZE( pDatesArray, CDateBundle::kDatesArrayLen );
		MAKESTR( pDriveName, driveName );
		dateBundle = new CDateBundle();
	
		theErr = SVolumes::iGetVolumeDates( driveName, dateBundle );
		if ( theErr != kErrNoErr )
			goto bail;
	
		SLAMDATEBUNDLE( dateBundle, pDatesArray );
	
	bail:
	
		DELETEDATEBUNDLE( dateBundle );
		DELETESTR( driveName );
	}
	ENDTRY

	return theErr;
}





//	int nativeCreateVolumeAlias( String driveName, String newAliasPath, int flags );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeCreateVolumeAlias ),
JSTRING pDriveName,
JSTRING pNewAliasPath,
JINT flags )
{
	long			theErr;
	DECLARESTR( driveName );
	DECLARESTR( newAliasPath );
	DECLARESTR( description );

	BEGINTRY {
		MAKESTR( pDriveName, driveName );
		MAKESTR( pNewAliasPath, newAliasPath );
	
		description = new CStr( _TXL("") );
		theErr = SAliases::iCreateVolumeAlias( driveName, newAliasPath,
												description, (SAliases::eCreateFlags) flags );
	
	bail:
	
		DELETESTR( description );
		DELETESTR( newAliasPath );
		DELETESTR( driveName );
	}
	ENDTRY

	return theErr;
}



//	int nativeGetVolumeFlags( String driveName, int flags[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeFlags ),
JSTRING pDriveName,
JINTARRAY pFlags )
{
	long			theErr, flags;
	DECLARESTR( driveName );
	
	BEGINTRY {
		CHECKSIZE( pFlags, 1 );
		MAKESTR( pDriveName, driveName );
		
		theErr = SVolumes::iGetVolumeFlags( driveName, &flags );
	
		SETINTARRAYREGION( pFlags, 0, 1, &flags );
	
	bail:
	
		DELETESTR( driveName );
	}
	ENDTRY

	return theErr;
}


//	int nativeGetVolumeReadFlagsMask( String driveName, int flags[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeReadFlagsMask ),
JSTRING pDriveName,
JINTARRAY pMask )
{
	long			theErr, mask;
	DECLARESTR( driveName );
	
	BEGINTRY {
		CHECKSIZE( pMask, 1 );
		MAKESTR( pDriveName, driveName );
		
		theErr = SVolumes::iGetVolumeFlags( driveName, &mask );
	
		SETINTARRAYREGION( pMask, 0, 1, &mask );
	
	bail:
	
		DELETESTR( driveName );
	}
	ENDTRY

	return theErr;
}



//	int nativeGetAllMonitorInfo( int monitorInfo[], int maxToReturn, int numReturned[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetAllMonitorInfo ),
JINTARRAY pMonitorInfo,
JINT maxToReturn,
JINTARRAY pNumReturned )
{
	long			theErr;
	long			*monitorInfoP, numDone;

	BEGINTRY {
		numDone = 0;
		CHECKSIZE( pMonitorInfo, maxToReturn * SMonitors::kMonitorInfoNumInts );
		CHECKSIZE( pNumReturned, 1 );
		
		monitorInfoP = LOCKINTARRAY( pMonitorInfo );
		
		theErr = SMonitors::iGetAllMonitorInfo( monitorInfoP, maxToReturn, &numDone );
	
		UNLOCKINTARRAY( pMonitorInfo, monitorInfoP );
		SETINTARRAYREGION( pNumReturned, 0, 1, &numDone );
	}
	ENDTRY

bail:

	return theErr;
}


//	int nativeGetMainMonitorInfo( int monitorInfo[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetMainMonitorInfo ),
JINTARRAY pMonitorInfo )
{
	long			*monitorInfoP, theErr;

	BEGINTRY {
		CHECKSIZE( pMonitorInfo, SMonitors::kMonitorInfoNumInts );
		
		monitorInfoP = LOCKINTARRAY( pMonitorInfo );
		
		theErr = SMonitors::iGetMainMonitorInfo( monitorInfoP );
		
		UNLOCKINTARRAY( pMonitorInfo, monitorInfoP );
	}
	ENDTRY

bail:

	return theErr;
}


//	int nativeGetExecutableType( String fullPath, int val[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetExecutableType ),
JSTRING pFullPath,
JINTARRAY pVal )
{
	unsigned long		val;
	long				theErr;
	DECLARESTR( fullPath );
	
	BEGINTRY {
		CHECKSIZE( pVal, 1 );
		MAKESTR( pFullPath, fullPath );
		
		theErr = SFileInfo::iGetExecutableType( fullPath, &val );
	
		SETINTARRAYREGION( pVal, 0, 1, (long*) &val );
	
	bail:
	
		DELETESTR( fullPath );
	}
	ENDTRY

	return theErr;
}




//	int nativeGetDriveDisplayName( String driveName, String displayName[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetDriveDisplayName ),
JSTRING pDriveName,
JOBJECTARRAY pDisplayName )
{
	long			theErr;
	DECLARESTR( driveName );
	DECLARESTR( displayName );
	
	BEGINTRY {
		CHECKNULL( pDisplayName );
		MAKESTR( pDriveName, driveName );
	
		displayName = new CStr( CStr::kMaxPath );
		theErr = SVolumes::iGetDriveDisplayName( driveName, displayName );
	
		SETSTRARRAYELEMENT( pDisplayName, 0, displayName );
	
	bail:
	
		DELETESTR( displayName );
		DELETESTR( driveName );
	}
	ENDTRY

	return theErr;
}



//	int nativeGetFileAttributes( String fullPath, int val[]  );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetFileAttributes ),
JSTRING pFullPath,
JINTARRAY pVal )
{
	unsigned long		val;
	long				theErr;
	DECLARESTR( fullPath );
	
	BEGINTRY {
		CHECKSIZE( pVal, 1 );
		MAKESTR( pFullPath, fullPath );
		
		theErr = SFileInfo::iGetFileAttributes( fullPath, &val );

		SETINTARRAYREGION( pVal, 0, 1, (long*) &val );
	
	bail:
	
		DELETESTR( fullPath );
	}
	ENDTRY

	return theErr;
}

//	int nativeGetFileAttributesMask( String fullPath, int val[]  );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetFileAttributesMask ),
JSTRING pFullPath,
JINTARRAY pVal )
{
	unsigned long		val;
	long				theErr;
	DECLARESTR( fullPath );
	
	BEGINTRY {
		CHECKSIZE( pVal, 1 );
		MAKESTR( pFullPath, fullPath );
		
		theErr = SFileInfo::iGetFileAttributesReadMask( fullPath, &val );

		SETINTARRAYREGION( pVal, 0, 1, (long*) &val );
	
	bail:
	
		DELETESTR( fullPath );
	}
	ENDTRY

	return theErr;
}


//	int nativeSetFileAttributes( String thePath, int whichFlags, int newValues );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeSetFileAttributes ),
JSTRING pFullPath,
JINT whichFlags,
JINT newValues )
{
	long				theErr;
	DECLARESTR( fullPath );
	
	BEGINTRY {
		MAKESTR( pFullPath, fullPath );
		
		theErr = SFileInfo::iSetFileAttributes( fullPath, whichFlags, newValues );

	bail:
	
		DELETESTR( fullPath );
	}
	ENDTRY

	return theErr;
}

//	int nativeGetFileAttributesWriteMask( String fullPath, int val[]  );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetFileAttributesWriteMask ),
JSTRING pFullPath,
JINTARRAY pVal )
{
	unsigned long		val;
	long				theErr;
	DECLARESTR( fullPath );
	
	BEGINTRY {
		CHECKSIZE( pVal, 1 );
		MAKESTR( pFullPath, fullPath );
		
		theErr = SFileInfo::iGetFileAttributesWriteMask( fullPath, &val );

		SETINTARRAYREGION( pVal, 0, 1, (long*) &val );
	
	bail:
	
		DELETESTR( fullPath );
	}
	ENDTRY

	return theErr;
}



//	int nativeResolveLinkFile( String linkFilePath, String retPath[], int flags );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeResolveLinkFile ),
JSTRING pLinkFilePath,
JOBJECTARRAY pRetPath,
JINT flags )
{
	long			theErr;
	DECLARESTR( linkFilePath );
	DECLARESTR( resolvedFile );
	
	BEGINTRY {
		CHECKSIZE( pRetPath, 1 );
		MAKESTR( pLinkFilePath, linkFilePath );
	
		resolvedFile = new CStr( CStr::kMaxPath );
		theErr = SAliases::iResolveLinkFile( linkFilePath, resolvedFile, (SAliases::eResolveFlags) flags );
	
		SETSTRARRAYELEMENT( pRetPath, 0, resolvedFile );
	
	bail:
	
		DELETESTR( resolvedFile );
		DELETESTR( linkFilePath );
	}
	ENDTRY

	return theErr;
}


//	int nativeCreateFileAlias( String targetPath, String newAliasPath, int flags );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeCreateFileAlias ),
JSTRING pTargetPath,
JSTRING pNewAliasPath,
JINT flags )
{
	long			theErr;
	DECLARESTR( targetPath );
	DECLARESTR( newAliasPath );
	DECLARESTR( description );

	BEGINTRY {
		MAKESTR( pTargetPath, targetPath );
		MAKESTR( pNewAliasPath, newAliasPath );
	
		description = new CStr( _TXL("") );
		theErr = SAliases::iCreateFileAlias( targetPath, newAliasPath,
												description, (SAliases::eCreateFlags) flags );
	
	bail:
	
		DELETESTR( description );
		DELETESTR( newAliasPath );
		DELETESTR( targetPath );
	}
	ENDTRY

	return theErr;
}

//	int nativeGetVolumes( int maxToReturn, int numReturned[], String driveNames[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumes ),
JINT maxToReturn,
JINTARRAY pNumReturned,
JOBJECTARRAY pDriveNames )
{
	long			theErr, numReturned;
	DECLARESTRINGVECTOR( stringVec );

	BEGINTRY {
		numReturned = 0;
		CHECKSIZE( pNumReturned, 1 );
		CHECKSIZE( pDriveNames, maxToReturn );
	
		stringVec = new CStringVector( 1 );
		theErr = SVolumes::iGetVolumes( maxToReturn, stringVec );
		BAILERR( theErr );
	
		SLAMSTRINGVECTOR( stringVec, pDriveNames );
		numReturned = stringVec->getNumStrings();
		SETINTARRAYREGION( pNumReturned, 0, 1, &numReturned );
	
	bail:
	
		DELETESTRINGVECTOR( stringVec );
	}
	ENDTRY

	return theErr;
}






//	private static native int nativeVIPGetFileVersionInfoStart( String fileName );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeVIPGetFileVersionInfoStart ),
JSTRING pFileName )
{
	long			theErr, versionH;
	DECLARESTR( fileName );

	BEGINTRY {
		MAKESTR( pFileName, fileName );
	
		theErr = SVersionInfo::iNativeGetFileVersionInfoStart( fileName, &versionH );
	
	bail:
	
		DELETESTR( fileName );
	}
	ENDTRY

	if ( theErr != kErrNoErr || versionH == 0 )
		return 0;
	else
		return versionH;
}

//	private static native int nativeVIPGetFileVersionInfoEnd( int versionH );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeVIPGetFileVersionInfoEnd ),
JINT versionH )
{
	if ( versionH == 0 )
		return kErrParamErr;
	else
		return SVersionInfo::iNativeGetFileVersionInfoEnd( versionH );
}


//	private static native String nativeVIPVerQueryValue( int versionH, String key, String value[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeVIPVerQueryValue ),
JINT versionH, 
JSTRING pKey, 
JOBJECTARRAY pRetArray )
{
	long			theErr;
	DECLARESTR( key );
	DECLARESTR( value );

	BEGINTRY {
		CHECKSIZE( pRetArray, 1 );
		if ( versionH == 0 ) {
			theErr = kErrParamErr;
			goto bail;
		}
		MAKESTR( pKey, key );
	
		value = new CStr( CStr::kMaxPath );
		theErr = SVersionInfo::iNativeVerQueryValue( versionH, key, value );
		if ( theErr != kErrNoErr )
			goto bail;
	
		SETSTRARRAYELEMENT( pRetArray, 0, value );
	
	bail:
	
		DELETESTR( value );
		DELETESTR( key );
	}
	ENDTRY

	return theErr;
}




EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeIcon ),
JSTRING pDriveName, 
JINT whichIcon, 
JINT width, 
JINT height,
JINT xform,
JINT align,
JINTARRAY pData )
{
	long			*dataP, theErr;
	DECLARESTR( driveName );

	BEGINTRY {
		CHECKNULL( pData );
		if ( width < 1 || height < 1 || GETINTARRAYLEN( pData ) < (unsigned long) ( width * height ) )
			return kErrParamErr;
		MAKESTR( pDriveName, driveName );
	
		dataP = (long*) LOCKINTARRAY( pData );
	
		theErr = SIconInfo::iNativeGetVolumeIcon( driveName, (SIconInfo::eWhichIcon) whichIcon, width, height,
													xform, align, dataP );
	
		UNLOCKINTARRAY( pData, dataP );
	
	bail:
	
		DELETESTR( driveName );
	}
	ENDTRY

	return theErr;
}

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetFileIcon ),
JSTRING pFileName, 
JINT whichIcon, 
JINT width, 
JINT height,
JINT xform,
JINT align,
JINTARRAY pData )
{
	long			*dataP, theErr;
	DECLARESTR( fileName );

	BEGINTRY {
		CHECKNULL( pData );
		if ( width < 1 || height < 1 || GETINTARRAYLEN( pData ) < (unsigned long) ( width * height ) )
			return kErrParamErr;
		MAKESTR( pFileName, fileName );
	
		dataP = (long*) LOCKINTARRAY( pData );
	
		theErr = SIconInfo::iNativeGetFileIcon( fileName, (SIconInfo::eWhichIcon) whichIcon, width, height,
												xform, align, dataP );
	
		UNLOCKINTARRAY( pData, dataP );
	
	bail:
	
		DELETESTR( fileName );
	}
	ENDTRY

	return theErr;
}

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetExtIcon ),
JSTRING pExt, 
JINT whichIcon, 
JINT width, 
JINT height,
JINT xform,
JINT align,
JINTARRAY pData )
{
	long			*dataP, theErr;
	DECLARESTR( ext );

	BEGINTRY {
		CHECKNULL( pData );
		if ( width < 1 || height < 1 || GETINTARRAYLEN( pData ) < (unsigned long) ( width * height ) )
			return kErrParamErr;
		MAKESTR( pExt, ext );
	
		dataP = (long*) LOCKINTARRAY( pData );
	
		theErr = SIconInfo::iNativeGetExtIcon( ext, (SIconInfo::eWhichIcon) whichIcon, width, height,
												xform, align, dataP );
	
		UNLOCKINTARRAY( pData, dataP );
	
	bail:
	
		DELETESTR( ext );
	}
	ENDTRY

	return theErr;
}





//	int nativeLaunchApp( String appPath, String verb, String regKey, String commandLine,
//							int retData[], int numArgs, String args[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeLaunchApp ),
JSTRING pAppPath, 
JSTRING pVerb, 
JSTRING pRegKey, 
JSTRING pCommandTemplate, 
JINTARRAY pRetData, 
JINT numArgs, 
JOBJECTARRAY pArgs )
{
	AppDataType		appData;
	long			theErr;
	DECLARESTR( appPath );

	BEGINTRY {
		theErr = kErrNoErr;
		MAKESTR( pAppPath, appPath );

		theErr = SAppInfo::iNativeLaunchApp( appPath, &appData );
		if ( theErr != kErrNoErr )
			goto bail;
	
		SETAPPDATA( &appData, pRetData );
	
	bail:
	
		DELETESTR( appPath );
	}
	ENDTRY

	return theErr;
}

//	int nativeFindAppsByName( String appName, int maxToReturn, int numReturned[], String paths[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeFindAppsByName ),
JSTRING pAppName, 
JINT maxToReturn, 
JINT flags,
JINTARRAY pNumReturned, 
JOBJECTARRAY pPaths )
{
	long				theErr, numReturned;
	DECLARESTRINGVECTOR( stringVec );
	DECLARESTR( appName );

	BEGINTRY {
		numReturned = 0;
		if ( maxToReturn < 1 )
			return kErrParamErr;
		CHECKSIZE( pNumReturned, 1 );
		CHECKSIZE( pPaths, maxToReturn );
		MAKESTR( pAppName, appName );
	
		stringVec = new CStringVector( 1 );
		theErr = SAppFinder::iNativeFindAppsByName( appName, NULL, maxToReturn, flags, stringVec );
		if ( theErr != kErrNoErr )
			goto bail;
	
		SLAMSTRINGVECTOR( stringVec, pPaths );
		numReturned = stringVec->getNumStrings();
		SETINTARRAYREGION( pNumReturned, 0, 1, (long*) &numReturned );
	
	bail:
	
		DELETESTRINGVECTOR( stringVec );
		DELETESTR( appName );
	}
	ENDTRY

	return theErr;
}



//	int nativeFindAppsByExtension( String fileExt, int maxToReturn, int numReturned[], String paths[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeFindAppsByExtension ),
JSTRING pFileExt, 
JSTRING pTempDir, 
JINT maxToReturn,
JINT flags, 
JINTARRAY pNumReturned, 
JOBJECTARRAY pPaths )
{
	long				theErr, numReturned;
	DECLARESTR( fileExt );
	DECLARESTR( tempDir );
	DECLARESTRINGVECTOR( stringVec );

	BEGINTRY {
		numReturned = 0;
		if ( maxToReturn < 1 ) {
			theErr = kErrParamErr;
			goto bail;
		}
		CHECKSIZE( pNumReturned, 1 );
		CHECKSIZE( pPaths, maxToReturn );
		MAKESTR( pFileExt, fileExt );
		MAKESTR( pTempDir, tempDir );

		stringVec = new CStringVector( 1 );
		theErr = SAppFinder::iNativeFindAppsByExtension( fileExt, tempDir, maxToReturn, flags, stringVec );
		if ( theErr != kErrNoErr )
			goto bail;

		SLAMSTRINGVECTOR( stringVec, pPaths );
		numReturned = stringVec->getNumStrings();
		SETINTARRAYREGION( pNumReturned, 0, 1, (long*) &numReturned );
	
	bail:
	
		DELETESTRINGVECTOR( stringVec );
		DELETESTR( tempDir );
		DELETESTR( fileExt );
	}
	ENDTRY

	return theErr;
}

//	native int nativeMoveApp( int appData[], int selector, int flags );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeMoveApp ),
JINTARRAY pAppData, 
JINT selector, 
JINT flags )
{
	AppDataType		appData;
	long			theErr;

	BEGINTRY {
		CHECKSIZE( pAppData, kAppDataNumInts );
		
		GETAPPDATA( &appData, pAppData );
	
		theErr = SAppInfo::iNativeMoveApp( &appData, (SAppInfo::eMoveApp) selector, flags );
		
		SETAPPDATA( &appData, pAppData );
	}
	ENDTRY

bail:

	return theErr;
}


	//	int nativeFindVerbs( String fullPaths[], String fileName, int maxToReturn, int numReturned[], String triples[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeFindVerbs ),
JOBJECTARRAY pFullPaths, 
JSTRING pFileName, 
JINT maxToReturn, 
JINTARRAY pNumReturned, 
JOBJECTARRAY pQuads )
{
	long				i, theErr, numPaths, *numReturnedArray;
	DECLARESTR( fileName );
	DECLARESTRINGVECTOR( vatVec );
	DECLARESTRINGVECTOR( fullPaths );

	BEGINTRY {
		CHECKNULL( pFullPaths );

		numPaths = GETOBJECTARRAYLEN( pFullPaths );

		if ( maxToReturn < 1 || numPaths < 1 ) {
			theErr = kErrParamErr;
			goto bail;
		}

		CHECKSIZE( pNumReturned, numPaths );
		CHECKSIZE( pQuads, 4 * numPaths * maxToReturn );
		MAKESTRINGVECTOR( numPaths, pFullPaths, fullPaths );

		MAKESTR( pFileName, fileName );
	
		vatVec = new CStringVector( 4 * numPaths * maxToReturn );
		for ( i = 0; i < 4 * numPaths * maxToReturn; i++ )
			vatVec->appendString( NULL );

		numReturnedArray = (long*) CMemory::mmalloc( maxToReturn * sizeof(long),
														_TXL( "nativeFindVerbs.numReturnedArray" ) );

		theErr = SAppFinder::iNativeFindVerbs( fullPaths, fileName, vatVec, maxToReturn, numReturnedArray );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, _TXL( "iNFV: " ), fullPaths->getString( 0 ), fileName,
								theErr, numReturnedArray[ 0 ] );
			goto bail;
		}

		SLAMSTRINGVECTOR( vatVec, pQuads );
		SETINTARRAYREGION( pNumReturned, 0, numPaths, numReturnedArray );

	bail:
	
		DELETESTR( fileName );
		DELETESTRINGVECTOR( vatVec );
		DELETESTRINGVECTOR( fullPaths );
		CMemory::mfree( numReturnedArray );
	}
	ENDTRY

	return theErr;
}


//	private static native int nativeVerifyNativeAppData( int appData[] );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeVerifyNativeAppData ),
JINTARRAY pAppData )
{
	AppDataType		appData;
	long			theErr;

	BEGINTRY {
		CHECKSIZE( pAppData, kAppDataNumInts );
	
		GETAPPDATA( &appData, pAppData );
	
		theErr = SAppInfo::iNativeVerifyNativeAppData( &appData );
	
		SETAPPDATA( &appData, pAppData );
	}
	ENDTRY

bail:

	return theErr;
}


//	private static native int nativeGetFileDate( int which, String path, int modDate[] );
//	pDatesArray will be set with the file's three dates in this format: year(i.e.1997), mo, day, hr, min, sec

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetFileDate ),
JINT ignored,
JSTRING pFullPath, 
JINTARRAY pDatesArray )
{
	long			theErr;
	DECLAREDATEBUNDLE( dateBundle );
	DECLARESTR( fullPath );

	BEGINTRY {
		CHECKSIZE( pDatesArray, CDateBundle::kDatesArrayLen );
		MAKESTR( pFullPath, fullPath );
		dateBundle = new CDateBundle();
	
		theErr = SFileInfo::iGetFileDates( fullPath, dateBundle );
		if ( theErr != kErrNoErr )
			goto bail;
	
		SLAMDATEBUNDLE( dateBundle, pDatesArray );
	
	bail:
	
		DELETEDATEBUNDLE( dateBundle );
		DELETESTR( fullPath );
	}
	ENDTRY

	return theErr;
}


//	private static native int nativeQuitApp( int appData[], int flags );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeQuitApp ),
JINTARRAY pAppData, 
JINT flags )
{
	AppDataType		appData;
	long			theErr;

	BEGINTRY {
		CHECKSIZE( pAppData, kAppDataNumInts );
	
		GETAPPDATA( &appData, pAppData );
	
		theErr = SAppInfo::iNativeQuitApp( &appData );
	
		SETAPPDATA( &appData, pAppData );
	}
	ENDTRY

bail:

	return theErr;
}



//	private static native int nativeLaunchURL( String url );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeLaunchURL ),
JSTRING pURL,
JSTRING pTempDir,
JINT flags,
JOBJECTARRAY pPreferredBrowsers )
{
	DECLARESTR( url );
	DECLARESTR( tempDir );
	long			theErr;

	BEGINTRY {
		MAKESTR( pURL, url );
		MAKESTR( pTempDir, tempDir );

		theErr = SWebBrowser::iNativeLaunchURL( url, tempDir, (SWebBrowser::eLaunchURLFlags) flags );

	bail:

		DELETESTR( url );
		DELETESTR( tempDir );
	}
	ENDTRY

	return theErr;

	UNUSED( pPreferredBrowsers );
}


