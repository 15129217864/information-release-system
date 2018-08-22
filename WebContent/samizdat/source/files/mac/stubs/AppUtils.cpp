/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

/*
These are the native routines for AppUtilsMRJ.java
See that file for a description of each of these routines.
See the 'xx.html' file for general details on the structure of the native code

{
	//	declarations

	BEGINTRY {
		//	initialize variables
	
		//	parameter checking

		//	convert any Java arguments into native format,
		//	such as by locking any Java arrays

		//	call the routine that actually does the work,
		//	usually one of the S-files
		
		//	convert any values returned from the S-file routine
		//	into Java format, such as by unlocking any Java arrays

	bail:
		//	delete any objects created
	
	} ENDTRY

	//	return an error value
}
*/

#include "comdefs.h"
#include "jmacros.h"
#include "stub_macros.h"
#include "AppUtils.h"
#include <stdlib.h>
#include "SAppUtils.h"
#include "SIcons.h"
#include "SAliases.h"
#include "SVolumes.h"
#include "SFiles.h"
#include "SIconDrawer.h"
#include "SFlags.h"
#include "SIterate.h"
#include "SMonitors.h"
#include "SProcesses.h"
#include "SWebBrowser.h"
#include "CUtils.h"
#include "CMemory.h"
#include "CFSpec.h"
#include "CDateBundle.h"
#include "Debugger.h"

enum {
	kMaxOpenableFileTypes = 64
};

	//	set in MRJMain / CWMain / OSXmain
extern	OSType					browserCreators[];
extern	long					numBrowserCreators;

#if !defined(__osx__)
	#pragma export on
#endif

SFUNC( JINT, AppUtilsMRJ_nGetProcesses ),
JINT maxToReturn,
JINT flags,
JINTARRAY pNumRet,
JINTARRAY pVRefs,
JINTARRAY pParIDs,
JBYTEARRAY pNames,
JINTARRAY pPSNLo,
JINTARRAY pPSNHi,
JINTARRAY pProFlags )
{
	ErrCode			theErr;
	long			numReturned, *vRefsP, *parIDsP, *PSNLoP, *PSNHiP, *proFlagsP;
	StringPtr		namesP;
	
	BEGINTRY {
		numReturned = 0;
	
		CHECKSIZE( pNumRet, 1 );
		CHECKSIZE( pVRefs, maxToReturn );
		CHECKSIZE( pParIDs, maxToReturn );
		CHECKSIZE( pNames, maxToReturn * SProcesses::kGetProcessMaxNameLen );
		CHECKSIZE( pPSNLo, maxToReturn );
		CHECKSIZE( pPSNHi, maxToReturn );
		CHECKSIZE( pProFlags, maxToReturn );
		
		namesP = (StringPtr) LOCKBYTEARRAY( pNames );
		vRefsP = LOCKINTARRAY( pVRefs );
		parIDsP = LOCKINTARRAY( pParIDs );
		PSNLoP = LOCKINTARRAY( pPSNLo );
		PSNHiP = LOCKINTARRAY( pPSNHi );
		proFlagsP = LOCKINTARRAY( pProFlags );

		theErr = SProcesses::getProcesses( maxToReturn, flags, &numReturned, vRefsP, parIDsP,
											namesP, PSNLoP, PSNHiP, proFlagsP );

		UNLOCKBYTEARRAY( pNames, namesP );
		UNLOCKINTARRAY( pVRefs, vRefsP );
		UNLOCKINTARRAY( pParIDs, parIDsP );
		UNLOCKINTARRAY( pPSNLo, PSNLoP );
		UNLOCKINTARRAY( pPSNHi, PSNHiP );
		UNLOCKINTARRAY( pProFlags, proFlagsP );
	
		SETJINT1( pNumRet, numReturned );

	bail:
		;
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

/*
 * Class:     com_jconfig_mac_AppUtilsMRJ
 * Method:    nativeGetMainMonitorInfo
 * Signature: ([I)I
 */
SFUNC( JINT, AppUtilsMRJ_nGetMainMonitorInfo ),
JINTARRAY pData )
{
	ErrCode			theErr;
	long			*dataP;
	
	BEGINTRY {
		CHECKSIZE( pData, SMonitors::kMonitorInfoNumInts );
		
		dataP = LOCKINTARRAY( pData );
		
		theErr = SMonitors::getMainMonitorInfo( dataP );
		
		UNLOCKINTARRAY( pData, dataP );
	
	bail:
		;
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

/*
 * Class:     com_jconfig_mac_AppUtilsMRJ
 * Method:    nativeGetAllMonitorInfo
 * Signature: ([II[I)I
 */
SFUNC( JINT, AppUtilsMRJ_nGetAllMonitorInfo ),
JINTARRAY pData,
JINT maxToReturn,
JINTARRAY pNumReturned )
{
	ErrCode			theErr;
	long			*dataP, numDone;
	
	BEGINTRY {
		numDone = 0;
	
		CHECKSIZE( pData, maxToReturn * SMonitors::kMonitorInfoNumInts );
		CHECKSIZE( pNumReturned, 1 );
		
		dataP = LOCKINTARRAY( pData );
		
		theErr = SMonitors::getAllMonitorInfo( dataP, maxToReturn, &numDone );

		UNLOCKINTARRAY( pData, dataP );
	
		SETJINT1( pNumReturned, numDone );

	bail:
		;

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeIVC( int vRef, int dirIDArray[], int numRet[],
//									byte buffer[], int numEntries, int flags );

SFUNC( JINT, AppUtilsMRJ_nIVC ),
JINT vRef,
JINTARRAY pDirIDArray,
JINTARRAY pNumRet,
JBYTEARRAY pBuffer,
JINT numEntries,
JINT flags )
{
	char			*bufferP;
	ErrCode			theErr;
	long			dirID, numRet;
	
	BEGINTRY {
		numRet = 0;
	
		CHECKSIZE( pDirIDArray, 1 );
		CHECKSIZE( pNumRet, 1 );
		CHECKSIZE( pBuffer, numEntries * SIterate::kIterateEntrySize );

		bufferP = (char*) LOCKBYTEARRAY( pBuffer );

		theErr = SIterate::iterateVolumeContents( vRef, &dirID, &numRet, (unsigned char*) bufferP,
													numEntries, flags );

		UNLOCKBYTEARRAY( pBuffer, bufferP );

		BAILERR( theErr );

		SETJINT1( pDirIDArray, dirID );
		SETJINT1( pNumRet, numRet );

	bail:
		;
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeSetVolumeColorCoding( int vRef, int newCoding );
SFUNC( JINT, AppUtilsMRJ_nSetVolumeColorCoding ),
JINT vRef,
JINT newCoding )
{
	ErrCode			theErr;
	
	BEGINTRY {
		theErr = SVolumes::setVolumeColorCoding( vRef, newCoding );
	} ENDTRY
	
	return theErr;

	UNUSED( pObj );
	UNUSED( pEnv );
}

//int nativeGetVolumeFinderInfo( int vRef, int pFInfo[] );
SFUNC( JINT, AppUtilsMRJ_nGetVolumeFinderInfo ),
JINT vRef,
JINTARRAY pFInfo )
{
	ErrCode		theErr;
	long		creator, type, flags, attribs;
	
	BEGINTRY {
		CHECKSIZE( pFInfo, 4 );

		theErr = SVolumes::getVolumeFinderInfo( vRef, &creator, &type, &flags, &attribs );
		BAILERR( theErr );

		SETJINT4( pFInfo, creator, type, flags, attribs );

	bail:
		;

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeSetColorCoding( int vRef, int parID, byte pName[], int newCoding );
SFUNC( JINT, AppUtilsMRJ_nSetColorCoding ), 
JINT vRef, 
JINT parID, 
JBYTEARRAY pName, 
JINT newCoding )
{
	ErrCode		theErr;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SFiles::setColorCoding( theSpec, newCoding );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetFinderInfo( int vRef, int parID, byte pName[], int finfo[] );
SFUNC( JINT, AppUtilsMRJ_nGetFinderInfo ), 
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JINTARRAY pFInfo )
{
	ErrCode		theErr;
	long		creator, type, flags, attribs;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pFInfo, 4 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SFiles::getFinderInfo( theSpec, &creator, &type, &flags, &attribs );
		BAILERR( theErr );

		SETJINT4( pFInfo, creator, type, flags, attribs );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//	int nativeFullPathToSpec( String fullPath, int vRefAndParID[], byte pName[] );

SFUNC( JINT, AppUtilsMRJ_nFullPathToSpec ), 
JSTRING pFullPath,
JINTARRAY pVRefAndParID, 
JBYTEARRAY pName )
{
	ErrCode			theErr;
	DECLARECFSPEC( theSpec );
	DECLARESTR( csFullPath );
	
	BEGINTRY {
		CHECKNULL( pFullPath );
		CHECKSIZE( pVRefAndParID, 2 );
		CHECKSIZE( pName, sizeof(Str255) );

		MAKESTR( pFullPath, csFullPath );
		MAKECFSPEC( theSpec, 0, 0, NULL );

		theErr = SFiles::fullPathNameToSpec( csFullPath, theSpec );
		BAILERR( theErr );

		SETJINT2( pVRefAndParID, theSpec->getVRef(), theSpec->getParID() );

		COPYPSTRINGTOJBYTES( pName, theSpec->getName() );

	bail:

		DELETECFSPEC( theSpec );
		DELETESTR( csFullPath );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

SFUNC( JINT, AppUtilsMRJ_nLaunchURLDirect ), 
JSTRING urlID, 
JINT flags,
JOBJECTARRAY pPreferredBrowsers )
{
	ErrCode			theErr;
	long			i;
	DECLARESTR( csURL );
	
	BEGINTRY {
		CHECKNULL( urlID );

		MAKESTR( urlID, csURL );

		theErr = -1;

		for ( i = 0; i < numBrowserCreators; i++ ) {
			theErr = SWebBrowser::launchURLDirect( csURL, browserCreators[ i ], flags );
			Debugger::debug( __LINE__, "launchURLDIRECT", NULL, NULL, browserCreators[ i ], theErr );
			if ( theErr == kErrNoErr )
				break;
		}

	bail:
	
		DELETESTR( csURL );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
	UNUSED( pPreferredBrowsers );
}

	//	private static native int nativeSendAppDocs( int whichCommand, int appPSN[],
	//													String filePaths[] );

SFUNC( JINT, AppUtilsMRJ_nSendAppDocs ), 
JINT whichCommand, 
JINTARRAY appPSN,
JOBJECTARRAY pFilePaths, 
JINT flags )
{
	long					numSpecs;
	FSSpec					*specArray;
	ErrCode					theErr;
	ProcessSerialNumber		thePSN;
	
	BEGINTRY {
		specArray = NULL;

		CHECKSIZE( appPSN, 2 );
		CHECKNULL( pFilePaths );

		GETJINT2( appPSN, &thePSN.lowLongOfPSN, &thePSN.highLongOfPSN );

		theErr = FILENAMEARRAYTOSPECARRAY( pFilePaths, &numSpecs, &specArray );
		BAILERR( theErr );

		theErr = SAppUtils::sendAppDocs( (SAppUtils::eSendAppDocsEventCode) whichCommand,
											&thePSN, specArray, numSpecs,
											(SAppUtils::eLaunchWithDocFlags) flags );

	bail:
	
		CMemory::mfree( specArray );
	
	} ENDTRY

	return theErr; 

	UNUSED( pObj );
}

	//	private static native int moveAppToFront( int appPSN[], int flags );

SFUNC( JINT, AppUtilsMRJ_nMoveApp ), 
JINTARRAY appPSN, 
JINT selector, 
JINT flags )
{
	ErrCode					theErr;
	ProcessSerialNumber		thePSN;
	
	BEGINTRY {
		CHECKSIZE( appPSN, 2 );

		GETJINT2( appPSN, &thePSN.lowLongOfPSN, &thePSN.highLongOfPSN );

		theErr = SProcesses::moveProcess( &thePSN, (SProcesses::eMoveProcess) selector, flags );

	bail:
		;

	} ENDTRY

	return theErr; 

	UNUSED( pObj );
}

	//	private static native int nativeVerifyPSN( int appPSN[] );

SFUNC( JINT, AppUtilsMRJ_nVerifyPSN ), 
JINTARRAY appPSN )
{
	ErrCode					theErr;
	ProcessSerialNumber		thePSN;
	
	BEGINTRY {
		CHECKSIZE( appPSN, 2 );

		GETJINT2( appPSN, &thePSN.lowLongOfPSN, &thePSN.highLongOfPSN );

		theErr = SProcesses::verifyPSN( &thePSN );

	bail:
		;

	} ENDTRY

	return theErr; 

	UNUSED( pObj );
}

	//	int nativeGetOpenableFileTypes( int vRef, int creator, 
	//									int numReturned[], int fileTypes[] );

SFUNC( JINT, AppUtilsMRJ_nGetOpenableFileTypes ), 
JINT vRef, 
JINT creator, 
JINTARRAY pNumRet, 
JINTARRAY pFileTypes )
{
	JINT		*fileTypeP;
	TypesBlock	typesBlock;
	ErrCode		theErr;
	long		i, numWritten;
	
	BEGINTRY {
		numWritten = 0;

		CHECKSIZE( pNumRet, 1 );
		CHECKSIZE( pFileTypes, kMaxOpenableFileTypes );

		CUtils::zeroset( &typesBlock, sizeof(TypesBlock) );

		theErr = SAppUtils::getOpenableFileTypes( vRef, creator, (FileType*) &typesBlock, 
										kMaxOpenableFileTypes, &numWritten );
		BAILERR( theErr );

		fileTypeP = LOCKINTARRAY( pFileTypes );
		
		for ( i = 0; i < numWritten; i++ )
			fileTypeP[ i ] = typesBlock[ i ];

		UNLOCKINTARRAY( pFileTypes, fileTypeP );

		SETJINT1( pNumRet, numWritten );

	bail:
		;
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

	//	private static native int nativeQuitApp( int appPSN[], long flags );

SFUNC( JINT, AppUtilsMRJ_nQuitApp ), 
JINTARRAY appPSN, 
JINT flags )
{
	ErrCode					theErr;
	ProcessSerialNumber		thePSN;
	
	BEGINTRY {
		CHECKSIZE( appPSN, 2 );

		GETJINT2( appPSN, &thePSN.lowLongOfPSN, &thePSN.highLongOfPSN );

		theErr = SProcesses::quitProcess( &thePSN, flags );

	bail:
		;

	} ENDTRY

	return theErr; 

	UNUSED( pObj );
}

//int nativeLaunchApp( int vRef, int parID, byte pName[], int retPSN[], int flags );

SFUNC( JINT, AppUtilsMRJ_nLaunchApp ), 
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JINTARRAY retPSN, 
JINT flags )
{
	ProcessSerialNumber		thePSN;
	ErrCode					theErr;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( retPSN, 2 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SAppUtils::launchApp( theSpec, &thePSN, flags );
		BAILERR( theErr );

		SETJINT2( retPSN, thePSN.lowLongOfPSN, thePSN.highLongOfPSN );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr; 

	UNUSED( pObj );
}

//int nativeLaunchWithDoc( int whichCommand, int vRef, int parID, byte pName[],
//							String filePaths[], int retPSN[], int flags );

SFUNC( JINT, AppUtilsMRJ_nLaunchWithDoc ), 
JINT whichCommand, 
JINT vRef,
JINT parID,
JBYTEARRAY pName,
jobjectArray pFilePaths, 
JINTARRAY retPSN, 
JINT flags )
{
	long					numSpecs;
	FSSpec					*specArray;
	ErrCode					theErr;
	ProcessSerialNumber		thePSN;
	DECLARECFSPEC( appSpec );

	BEGINTRY {
		specArray = NULL;

		CHECKNULL( pName );
		CHECKNULL( pFilePaths );
		CHECKSIZE( retPSN, 2 );

		MAKECFSPEC( appSpec, vRef, parID, pName );

		theErr = FILENAMEARRAYTOSPECARRAY( pFilePaths, &numSpecs, &specArray );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "COM_nLaunchWithDoc.fnatsp", NULL, NULL, theErr, 0 );
			goto bail;
		}

		theErr = SAppUtils::openWithDocs( (SAppUtils::eOpenWithDocsEventCode) whichCommand,
											appSpec, specArray, numSpecs, &thePSN,
											(SAppUtils::eLaunchWithDocFlags) flags );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "COM_nLaunchWithDoc.owd", NULL, NULL, theErr, 0 );
			goto bail;
		}

		SETJINT2( retPSN, thePSN.lowLongOfPSN, thePSN.highLongOfPSN );

	bail:

		DELETECFSPEC( appSpec );

		CMemory::mfree( specArray );

	} ENDTRY

	return theErr; 

	UNUSED( pObj );
}


	//	private static native byte[] nativeCreateFullPathName( int vRef, int parID, String retArray[] );

SFUNC( JINT, AppUtilsMRJ_nCreateFullPathName ), 
JINT vRef, 
JINT parID,
JBYTEARRAY pName, 
jobjectArray pOutNameArray )
{
	Handle			hFullPath;
	ErrCode			theErr;
	short			fullPathLength;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pOutNameArray, 1 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SFiles::specToFullPathName( theSpec, &fullPathLength, &hFullPath );
		BAILERR( theErr );

		HLock( hFullPath );
		
		SETSTRARRAYELEMENTUTF( pOutNameArray, 0, *hFullPath, strlen( *hFullPath ) );

		HUnlock( hFullPath );
		DisposeHandle( hFullPath );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetFileDate( int which, int vRef, int parID, byte pName[], int modDate[] );
SFUNC( JINT, AppUtilsMRJ_nGetFileDate ),
JINT ignored,
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JINTARRAY pDatesArray )
{
	ErrCode			theErr;
	DECLARECFSPEC( theSpec );
	DECLAREDATEBUNDLE( dateBundle );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pDatesArray, CDateBundle::kDatesArrayLen );

		MAKECFSPEC( theSpec, vRef, parID, pName );
		dateBundle = new CDateBundle();

		theErr = SFiles::getFileDate( theSpec, dateBundle );
		BAILERR( theErr );
		
		SLAMDATEBUNDLE( dateBundle, pDatesArray );

	bail:

		DELETEDATEBUNDLE( dateBundle );
		DELETECFSPEC( theSpec );

	} ENDTRY

	return theErr;

	UNUSED( ignored );
	UNUSED( pObj );
}

//int nativeSetFileDate( int which, int vRef, int parID, byte pName[], int modDate[] );
SFUNC( JINT, AppUtilsMRJ_nSetFileDate ),
JINT ignored,
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JINTARRAY pDatesArray )
{
	ErrCode			theErr;
	DECLARECFSPEC( theSpec );
	DECLAREDATEBUNDLE( dateBundle );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pDatesArray, CDateBundle::kDatesArrayLen );

		MAKECFSPEC( theSpec, vRef, parID, pName );
		MAKEDATEBUNDLE( dateBundle, pDatesArray );

		theErr = SFiles::setFileDate( theSpec, dateBundle );

	bail:

		DELETEDATEBUNDLE( dateBundle );
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( ignored );
	UNUSED( pObj );
}

//int nativePlotIcon( int which, int width, int height,
//						int hSuite, int xform, int align, int pData[] );

SFUNC( JINT, AppUtilsMRJ_nPlotIcon ),
JINT which,
JINT width,
JINT height,
JINT hSuite,
JINT xform,
JINT align,
JINTARRAY pData )
{
	long			*dataP;
	ErrCode			theErr;
	
	BEGINTRY {
		CHECKSIZE( pData, width * height );
		if ( hSuite == NULL ) {
			theErr = paramErr;
			goto bail;
		}

		dataP = LOCKINTARRAY( pData );

		theErr = SIconDrawer::plotIcon( (SIconDrawer::eWhichIcon) which, width, height,
										(Handle) hSuite, xform, align, (unsigned long*) dataP );

		UNLOCKINTARRAY( pData, dataP );

	bail:
		;

	} ENDTRY
	
	return theErr;

	UNUSED( pObj );
}

//int nativeGetVolumeDate( int which, int vRef, int modDate[] );
SFUNC( JINT, AppUtilsMRJ_nGetVolumeDate ),
JINT ignored,
JINT vRef,
JINTARRAY pDatesArray )
{
	ErrCode			theErr;
	DECLAREDATEBUNDLE( dateBundle );
	
	BEGINTRY {
		CHECKSIZE( pDatesArray, CDateBundle::kDatesArrayLen );

		dateBundle = new CDateBundle();

		theErr = SVolumes::getVolumeDate( vRef, dateBundle );
		BAILERR( theErr );

		SLAMDATEBUNDLE( dateBundle, pDatesArray );

	bail:

		DELETEDATEBUNDLE( dateBundle );

	} ENDTRY

	return theErr;

	UNUSED( ignored );
	UNUSED( pObj );
}

//int nativeSetVolumeDate( int which, int vRef, int modDate[] );
SFUNC( JINT, AppUtilsMRJ_nSetVolumeDate ),
JINT ignored,
JINT vRef,
JINTARRAY pDatesArray )
{
	ErrCode			theErr;
	DECLAREDATEBUNDLE( dateBundle );
	
	BEGINTRY {
		CHECKSIZE( pDatesArray, CDateBundle::kDatesArrayLen );

		MAKEDATEBUNDLE( dateBundle, pDatesArray );

		theErr = SVolumes::setVolumeDate( vRef, dateBundle );

	bail:

		DELETEDATEBUNDLE( dateBundle );

	} ENDTRY
	
	return theErr;

	UNUSED( ignored );
	UNUSED( pObj );
}

//int nativeSetCreatorAndType( int vRef, int parID, byte pName[], int creatorAndType[] );
SFUNC( JINT, AppUtilsMRJ_nSetCreatorAndType ),
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JINTARRAY pCreatorAndType )
{
	ErrCode			theErr;
	unsigned long	creator, type, flags, attributes;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pCreatorAndType, 4 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		GETJINT4( pCreatorAndType, &creator, &type, &flags, &attributes );

		theErr = SFiles::setCreatorAndType( theSpec, creator, type, flags, attributes );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeVerifyFile( int vRef, int parID, byte pName[] );
SFUNC( JINT, AppUtilsMRJ_nVerifyFile ),
JINT vRef,
JINT parID,
JBYTEARRAY pName )
{
	ErrCode			theErr;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SFiles::verifyFile( theSpec );

	bail:

		DELETECFSPEC( theSpec );

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeVerifyVolume( int vRef );
SFUNC( JINT, AppUtilsMRJ_nVerifyVolume ),
JINT vRef )
{
	ErrCode		theErr;

	BEGINTRY {
		theErr = SVolumes::verifyVolume( vRef );
	} ENDTRY

	return theErr;

	UNUSED( pObj );
	UNUSED( pEnv );
}

//int nativeGetDiskFileFlags( int vRef, int parID, byte pName[], int flags[] );
SFUNC( JINT, AppUtilsMRJ_nGetDiskFileFlags ),
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JINTARRAY pFlags )
{
	ErrCode			theErr;
	unsigned long	flags;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pFlags, 1 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SFlags::getDiskFileFlags( theSpec, &flags );
		BAILERR( theErr );

		SETJINT1( pFlags, flags );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeSetDiskFileFlags( int vRef, int parID, byte pName[], int flagMask, int newFlags );
SFUNC( JINT, AppUtilsMRJ_nSetDiskFileFlags ),
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JINT flagMask,
JINT newFlags )
{
	ErrCode			theErr;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SFlags::setDiskFileFlags( theSpec, flagMask, newFlags );

	bail:

		DELETECFSPEC( theSpec );

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetDFReadFlagsMask( int vRef, int parID, byte pName[], int masks[] );
SFUNC( JINT, AppUtilsMRJ_nGetDFReadFlagsMask ),
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JINTARRAY pMasks )
{
	ErrCode			theErr;
	unsigned long	masks;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pMasks, 1 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SFlags::getDiskFileReadFlagsMask( theSpec, &masks );
		BAILERR( theErr );

		SETJINT1( pMasks, masks );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetDFWriteFlagsMask( int vRef, int parID, byte pName[], int masks[] );
SFUNC( JINT, AppUtilsMRJ_nGetDFWriteFlagsMask ),
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JINTARRAY pMasks )
{
	ErrCode			theErr;
	unsigned long	masks;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pMasks, 1 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SFlags::getDiskFileWriteFlagsMask( theSpec, &masks );
		BAILERR( theErr );

		SETJINT1( pMasks, masks );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetDiskVolumeFlags( int vRef, int flags[] );
SFUNC( JINT, AppUtilsMRJ_nGetDiskVolumeFlags ),
JINT vRef,
JINTARRAY pFlags )
{
	ErrCode			theErr;
	unsigned long	flags;
	
	BEGINTRY {
		CHECKSIZE( pFlags, 1 );

		theErr = SFlags::getDiskVolumeFlags( vRef, &flags );
		BAILERR( theErr );

		SETJINT1( pFlags, flags );

	bail:
		;

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeSetDiskVolumeFlags( int vRef, int flagMask, int newFlags );
SFUNC( JINT, AppUtilsMRJ_nSetDiskVolumeFlags ),
JINT vRef,
JINT flagMask,
JINT newFlags )
{
	ErrCode			theErr;
	
	BEGINTRY {
		theErr = SFlags::setDiskVolumeFlags( vRef, flagMask, newFlags );
	} ENDTRY

	return theErr;

	UNUSED( pObj );
	UNUSED( pEnv );
}

//int nativeGetDVReadFlagsMask( int vRef, int masks[] );
SFUNC( JINT, AppUtilsMRJ_nGetDVReadFlagsMask ),
JINT vRef,
JINTARRAY pMasks )
{
	ErrCode			theErr;
	unsigned long	masks;
	
	BEGINTRY {
		CHECKSIZE( pMasks, 1 );

		theErr = SFlags::getDiskVolumeReadFlagsMask( vRef, &masks );
		BAILERR( theErr );

		SETJINT1( pMasks, masks );

	bail:
		;

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetDVWriteFlagsMask( int vRef, int masks[] );
SFUNC( JINT, AppUtilsMRJ_nGetDVWriteFlagsMask ),
JINT vRef,
JINTARRAY pMasks )
{
	ErrCode			theErr;
	unsigned long	masks;
	
	BEGINTRY {
		CHECKSIZE( pMasks, 1 );

		theErr = SFlags::getDiskVolumeWriteFlagsMask( vRef, &masks );
		BAILERR( theErr );

		SETJINT1( pMasks, masks );

	bail:
		;	

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetVolumeIconSuite( int vRef, int selector, int pHSuite[] );
SFUNC( JINT, AppUtilsMRJ_nGetVolumeIconSuite ),
JINT vRef,
JINT selector,
JINTARRAY pHSuite )
{
	Handle			hSuite;
	ErrCode			theErr;
	
	BEGINTRY {
		CHECKSIZE( pHSuite, 1 );

		theErr = SIcons::getVolumeIconSuite( vRef, selector, &hSuite );
		BAILERR( theErr );

		SETJINT1( pHSuite, (long) hSuite );

	bail:
		;	

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetFileIconSuite( int vRef, int parID, byte pName[], int selector, int pHSuite[] );
SFUNC( JINT, AppUtilsMRJ_nGetFileIconSuite ),
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JINT selector,
JINTARRAY pHSuite )
{
	Handle			hSuite;
	ErrCode			theErr;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pHSuite, 1 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SIcons::getFileIconSuite( theSpec, selector, &hSuite );
		BAILERR( theErr );

		SETJINT1( pHSuite, (long) hSuite );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetFTACIconSuite( int vRef, int creator, int type, int selector, int pHSuite[] );
SFUNC( JINT, AppUtilsMRJ_nGetFTACIconSuite ),
JINT vRef,
JINT creator,
JINT type,
JINT selector,
JINTARRAY pHSuite )
{
	Handle			hSuite;
	ErrCode			theErr;
	
	BEGINTRY {
		CHECKSIZE( pHSuite, 1 );

		theErr = SIcons::getFTACIconSuite( vRef, creator, type, selector, &hSuite );
		BAILERR( theErr );

		SETJINT1( pHSuite, (long) hSuite );

	bail:
		;	

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeDisposeIconSuite( int hSuite, int flags );
SFUNC( JINT, AppUtilsMRJ_nDisposeIconSuite ),
JINT hSuite,
JINT flags )
{
	ErrCode			theErr;
	
	BEGINTRY {
		theErr = SIcons::disposeAnIconSuite( (Handle) hSuite, flags );
	} ENDTRY
	
	return theErr;

	UNUSED( pObj );
	UNUSED( pEnv );
}

//int nativeGetContainer( int vRef, int parID, byte pName[],
//							int pContParID[], byte pContName[] );

SFUNC( JINT, AppUtilsMRJ_nGetContainer ),
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JINTARRAY pContParID,
JBYTEARRAY pContName )
{
	Str255			contName;
	long			contParID;
	ErrCode			theErr;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pContName, sizeof(contName) );
		CHECKSIZE( pContParID, 1 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SFiles::getContainer( theSpec, &contParID, contName );
		BAILERR( theErr );

		SETJINT1( pContParID, contParID );
		COPYPSTRINGTOJBYTES( pContName, contName );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeResolveAlias( int inVRef, int inParID, byte pInName[],
//							int outVRefAndParID[], byte pOutName[], int flags );
SFUNC( JINT, AppUtilsMRJ_nResolveAlias ),
JINT inVRef,
JINT inParID,
JBYTEARRAY pInName,
JINTARRAY pOutVRefAndParID,
JBYTEARRAY pOutName,
JINT flags )
{
	ErrCode			theErr;
	DECLARECFSPEC( inSpec );
	DECLARECFSPEC( outSpec );
	
	BEGINTRY {
		CHECKNULL( pInName );
		CHECKSIZE( pOutName, sizeof(Str255) );
		CHECKSIZE( pOutVRefAndParID, 2 );

		MAKECFSPEC( inSpec, inVRef, inParID, pInName );
		outSpec = new CFSpec( 0, 0, NULL );

		theErr = SAliases::resolveAnAlias( inSpec, outSpec, (SAliases::eAliasInteraction) flags );
		BAILERR( theErr );

		SETJINT2( pOutVRefAndParID, outSpec->getVRef(), outSpec->getParID() );
		COPYPSTRINGTOJBYTES( pOutName, outSpec->getName() );

	bail:
	
		DELETECFSPEC( inSpec );
		DELETECFSPEC( outSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetFileCategory( int vRef, int parID, byte pName[], int cat[] );

SFUNC( JINT, AppUtilsMRJ_nGetFileCategory ),
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JINTARRAY pCat )
{
	ErrCode						theErr;
	SAliases::eFileCategory		category;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pCat, 1 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SAliases::getFileCategory( theSpec, &category );
		BAILERR( theErr );

		SETJINT1( pCat, category );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetVolumes( int maxToReturn, int numRet[], int vRefs[] );

SFUNC( JINT, AppUtilsMRJ_nGetVolumes ),
JINT maxToReturn,
JINTARRAY pNumRet,
JINTARRAY pVRefs )
{
	long		numReturned;
	ErrCode		theErr;
	long		*refsP;

	BEGINTRY {
		numReturned = 0;
		refsP = NULL;

		CHECKSIZE( pNumRet, 1 );
		CHECKSIZE( pVRefs, maxToReturn );

		refsP = (long*) CMemory::mmalloc( maxToReturn * sizeof(long), _TXL( "nativeGetVolumes" ) );

		theErr = SVolumes::getAllVolumes( &numReturned, maxToReturn, refsP );
		if ( theErr != kErrNoErr || numReturned < 1 )
			Debugger::debug( __LINE__, "java_nGetVolumes", NULL, NULL, theErr, numReturned );
		
		SETJINT1( pNumRet, numReturned );

		SETINTARRAYREGION( pVRefs, 0, numReturned, refsP );

	bail:
	
		CMemory::mfree( refsP );

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeUpdateContainer( int vRef, int parID, byte pName[] );
SFUNC( JINT, AppUtilsMRJ_nUpdateContainer ),
JINT vRef,
JINT parID,
JBYTEARRAY pName )
{
	ErrCode			theErr;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SFiles::updateContainer( theSpec );

	bail:

		DELETECFSPEC( theSpec );

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetForkSizes( int vRef, int parID, byte pName[], long len[] );
SFUNC( JINT, AppUtilsMRJ_nGetForkSizes ),
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JLONGARRAY pLen )
{
	ErrCode			theErr;
	UnsignedWide	dataSize, rsrcSize;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pLen, 2 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SFiles::getForkSizes( theSpec, &dataSize, &rsrcSize );
		BAILERR( theErr );

		SETJLONG2( pLen, &dataSize, &rsrcSize );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeRenameFile( int vRef, int parID, byte pName[], byte pOutName[], String newNameID );
SFUNC( JINT, AppUtilsMRJ_nRenameFile ),
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JBYTEARRAY pOutName,
JSTRING newNameID )
{
	Str255			outName;
	ErrCode			theErr;
	DECLARECFSPEC( theSpec );
	DECLARESTR( csNewName );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pOutName, sizeof(outName) );
		CHECKNULL( newNameID );

		MAKESTR( newNameID, csNewName );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		theErr = SFiles::renameFile( theSpec, csNewName, outName, sizeof(outName) );
		BAILERR( theErr );

		COPYPSTRINGTOJBYTES( pOutName, outName );

	bail:
	
		DELETESTR( csNewName );
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeRenameVolume( int vRef, String newNameID );
SFUNC( JINT, AppUtilsMRJ_nRenameVolume ),
JINT vRef,
JSTRING newNameID )
{
	ErrCode			theErr;
	DECLARESTR( csNewName );
	
	BEGINTRY {
		CHECKNULL( newNameID );

		MAKESTR( newNameID, csNewName );

		theErr = SVolumes::renameVolume( vRef, csNewName );

	bail:
	
		DELETESTR( csNewName );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetVolumeCapacity( int vRef, long cap[] );

SFUNC( JINT, AppUtilsMRJ_nGetVolumeCapacity ),
JINT vRef,
JLONGARRAY pCap )
{
	ErrCode			theErr;
	UnsignedWide	capacity;
	
	BEGINTRY {
		CHECKSIZE( pCap, 1 );

		theErr = SVolumes::getVolumeCapacity( vRef, &capacity );
		BAILERR( theErr );

		SETJLONG1( pCap, &capacity );

	bail:
		;	

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetVolumeFreeSpace( int vRef, long space[] );
SFUNC( JINT, AppUtilsMRJ_nGetVolumeFreeSpace ),
JINT vRef,
JLONGARRAY pSpace )
{
	ErrCode			theErr;
	UnsignedWide	space;
	
	BEGINTRY {
		CHECKSIZE( pSpace, 1 );

		theErr = SVolumes::getVolumeFreeSpace( vRef, &space );
		BAILERR( theErr );

		SETJLONG1( pSpace, &space );

	bail:
		;
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeCreateAlias( int targetVRef, int targetParID, byte pTargetName[],
//							String newAlias, int creator, int flags );

SFUNC( JINT, AppUtilsMRJ_nCreateAlias ),
JINT targetVRef,
JINT targetParID,
JBYTEARRAY pTargetName,
JSTRING pNewAliasID,
JINT creator,
JINT flags )
{
	Handle			hNewAliasPath;
	ErrCode			theErr;
	DECLARECFSPEC( targetSpec );
	DECLARESTR( csNewAliasPath );
	
	BEGINTRY {
		hNewAliasPath = NULL;

		CHECKNULL( pTargetName );
		CHECKNULL( pNewAliasID );

		MAKESTR( pNewAliasID, csNewAliasPath );
		MAKECFSPEC( targetSpec, targetVRef, targetParID, pTargetName );

		theErr = SAliases::createAlias( targetSpec, csNewAliasPath, creator, flags );

	bail:
	
		DELETESTR( csNewAliasPath );
		DELETECFSPEC( targetSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}


//int nativeCreateVolumeAlias( int targetVRef, String newAlias, int creator, int flags );
SFUNC( JINT, AppUtilsMRJ_nCreateVolumeAlias ),
JINT targetVRef,
JSTRING pNewAliasID,
JINT creator,
JINT flags )
{
	ErrCode			theErr;
	DECLARESTR( csNewAliasPath );
	
	BEGINTRY {
		CHECKNULL( pNewAliasID );

		MAKESTR( pNewAliasID, csNewAliasPath );

		theErr = SAliases::createVolumeAlias( targetVRef, csNewAliasPath, creator, flags );

	bail:
	
		DELETESTR( csNewAliasPath );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeIterateContents( int vRef, int parID, byte pName[],
//								int dirIDArray[], int numRet[],
//								byte buffer[], int numEntries, int flags );

SFUNC( JINT, AppUtilsMRJ_nIterateContents ),
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JINTARRAY pDirIDArray,
JINTARRAY pNumRet,
JBYTEARRAY pBuffer,
JINT numEntries,
JINT flags )
{
	char			*bufferP;
	ErrCode			theErr;
	long			dirID, numRet;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKNULL( pName );
		CHECKSIZE( pDirIDArray, 1 );
		CHECKSIZE( pNumRet, 1 );
		CHECKSIZE( pBuffer, numEntries * SIterate::kIterateEntrySize );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		bufferP = (char*) LOCKBYTEARRAY( pBuffer );

		theErr = SIterate::iterateContents( theSpec, &dirID, &numRet,
											(unsigned char*) bufferP, numEntries, flags );

		UNLOCKBYTEARRAY( pBuffer, bufferP );

		BAILERR( theErr );

		SETJINT1( pDirIDArray, dirID );
		SETJINT1( pNumRet, numRet );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//int nativeGetVolumeName( int vRef, byte pName[] );

SFUNC( JINT, AppUtilsMRJ_nGetVolumeName ),
JINT vRef,
JBYTEARRAY pName )
{
	Str255			volumeName;
	ErrCode			theErr;
	
	BEGINTRY {
		CHECKSIZE( pName, sizeof(volumeName) );

		theErr = SVolumes::getVolumeName( vRef, volumeName );
		BAILERR( theErr );

		COPYPSTRINGTOJBYTES( pName, volumeName );

	bail:
		;	

	} ENDTRY

	return theErr;

	UNUSED( pObj );
}

//	private static native int nGetRawResourceFork( int flags, int vRef, int parID, byte pName[], byte data[] );

SFUNC( JINT, AppUtilsMRJ_nGetRawResourceFork ),
JINT flags,
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JBYTEARRAY pBuffer )
{
	ErrCode			theErr;
	long			bufferLen;
	char			*bufferP;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKSIZE( pName, 1 );
		CHECKSIZE( pBuffer, 1 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		bufferLen = GETBYTEARRAYLEN( pBuffer );
		bufferP = (char*) LOCKBYTEARRAY( pBuffer );

		theErr = SFiles::getRawResourceFork( flags, theSpec, bufferP, bufferLen );

		UNLOCKBYTEARRAY( pBuffer, bufferP );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
	UNUSED( flags );
}



//	private static native int nSetRawResourceFork( int flags, int vRef, int parID, byte pName[], byte data[] );

SFUNC( JINT, AppUtilsMRJ_nSetRawResourceFork ),
JINT flags,
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JBYTEARRAY pBuffer )
{
	ErrCode			theErr;
	long			bufferLen;
	char			*bufferP;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKSIZE( pName, 1 );
		CHECKSIZE( pBuffer, 1 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		bufferLen = GETBYTEARRAYLEN( pBuffer );
		bufferP = (char*) LOCKBYTEARRAY( pBuffer );

		theErr = SFiles::setRawResourceFork( flags, theSpec, bufferP, bufferLen );

		UNLOCKBYTEARRAY( pBuffer, bufferP );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
	UNUSED( flags );
}


//	private static native int nSetForkLength( int flags, int whichFork, int vRef, int parID, byte pName[], long newLen );

SFUNC( JINT, AppUtilsMRJ_nSetForkLength ),
JINT flags,
JINT whichFork,
JINT vRef,
JINT parID,
JBYTEARRAY pName,
JLONG newLen )
{
	UnsignedWide	tempNewLen;
	ErrCode			theErr;
	DECLARECFSPEC( theSpec );
	
	BEGINTRY {
		CHECKSIZE( pName, 1 );

		MAKECFSPEC( theSpec, vRef, parID, pName );

		GETJLONG( newLen, &tempNewLen );

		theErr = SFiles::setForkLength( flags, whichFork, theSpec, tempNewLen );

	bail:
	
		DELETECFSPEC( theSpec );
	
	} ENDTRY

	return theErr;

	UNUSED( pObj );
}


#if !defined(__osx__)
	#pragma export off
#endif


