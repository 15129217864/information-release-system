/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "JNIUtils.h"
#include "jni_macros.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "CMemory.h"
#include "Debugger.h"
#include <jni.h>


void JNISetStrArrayElement( JNIEnv *pEnv, jobjectArray pArray, jint index, CStr *csStr )
{
	jstring			jStr;

	if ( csStr == NULL ) {
		SETOBJECTARRAYELEMENT( pArray, index, NULL );
	}
	else {
		jStr = csStr->getJString( pEnv );

		SETOBJECTARRAYELEMENT( pArray, index, (jobject) jStr );
	}
}

void getJint2( JNIEnv* pEnv, jintArray jSrcArray, unsigned long *val0P, unsigned long *val1P )
{
	jint		*srcArrayP;

	if ( GETOBJECTARRAYLEN( jSrcArray ) < 2 ) {
		Debugger::debug( __LINE__, _TXL( "bad getJint2 array len" ) );
		return;
	}

	srcArrayP = LOCKINTARRAY( jSrcArray );

	*val0P = srcArrayP[ 0 ];
	*val1P = srcArrayP[ 1 ];

	UNLOCKINTARRAY( jSrcArray, srcArrayP );
}

void getJint4( JNIEnv* pEnv, jintArray jSrcArray, unsigned long *val0P, unsigned long *val1P,
				unsigned long *val2P, unsigned long *val3P )
{
	jint		*srcArrayP;

	if ( GETOBJECTARRAYLEN( jSrcArray ) < 4 ) {
		Debugger::debug( __LINE__, _TXL( "bad getJint4 array len" ) );
		return;
	}

	srcArrayP = LOCKINTARRAY( jSrcArray );

	*val0P = srcArrayP[ 0 ];
	*val1P = srcArrayP[ 1 ];
	*val2P = srcArrayP[ 2 ];
	*val3P = srcArrayP[ 3 ];

	UNLOCKINTARRAY( jSrcArray, srcArrayP );
}

void setJint1( JNIEnv* pEnv, jintArray jDestArray, long val )
{
	jint		*destArrayP;

	if ( GETOBJECTARRAYLEN( jDestArray ) < 1 ) {
		Debugger::debug( __LINE__, _TXL( "bad setJint1 array len" ) );
		return;
	}

	destArrayP = LOCKINTARRAY( jDestArray );

	destArrayP[ 0 ] = val;

	UNLOCKINTARRAY( jDestArray, destArrayP );
}

void setJint4( JNIEnv* pEnv, jintArray jDestArray, long val0, long val1, long val2, long val3 )
{
	jint		*destArrayP;

	if ( GETOBJECTARRAYLEN( jDestArray ) < 4 ) {
		Debugger::debug( __LINE__, _TXL( "bad setJint4 array len" ) );
		return;
	}

	destArrayP = LOCKINTARRAY( jDestArray );

	destArrayP[ 0 ] = val0;
	destArrayP[ 1 ] = val1;
	destArrayP[ 2 ] = val2;
	destArrayP[ 3 ] = val3;

	UNLOCKINTARRAY( jDestArray, destArrayP );
}

void setJint2( JNIEnv* pEnv, jintArray jDestArray, long val0, long val1 )
{
	jint		*destArrayP;

	if ( GETOBJECTARRAYLEN( jDestArray ) < 2 ) {
		Debugger::debug( __LINE__, _TXL( "bad setJint2 array len" ) );
		return;
	}

	destArrayP = LOCKINTARRAY( jDestArray );

	destArrayP[ 0 ] = val0;
	destArrayP[ 1 ] = val1;

	UNLOCKINTARRAY( jDestArray, destArrayP );
}

void setStrArrayElementUTF( JNIEnv *pEnv, jobjectArray pDestArray, jint index, char *strP, jint strLen )
{
	jstring			jStr;

	jStr = pEnv->NewStringUTF( strP );
	SETOBJECTARRAYELEMENT( pDestArray, index, jStr );

	return;

	UNUSED( strLen );
}

void setStringArray1( JNIEnv* pEnv, JSTRING jStr, jobjectArray jDestArray, long index )
{
	SETOBJECTARRAYELEMENT( jDestArray, index, jStr );
}

#if defined(_WIN32)

void JNIGetAppData( JNIEnv *pEnv, AppDataType *setDataP, jintArray pAppData )
{
	jint			*appDataP;

	appDataP = LOCKINTARRAY( pAppData );

	setDataP->hwnd = (HWND) appDataP[ kAppDataHWNDOffset ];
	setDataP->pi.hProcess = (void*) appDataP[ kAppDataHProcessOffset ];
	setDataP->pi.hThread = (void*) appDataP[ kAppDataHThreadOffset ];
	setDataP->pi.dwProcessId = appDataP[ kAppDataProcessIDOffset ];
	setDataP->pi.dwThreadId = appDataP[ kAppDataThreadIDOffset ];

	setDataP->pecntUsage = appDataP[ kAppDataCntUsageOffset ];
	setDataP->peth32DefaultHeapID = appDataP[ kAppDataDefaultHeapIDOffset ];
	setDataP->peth32ModuleID = appDataP[ kAppDataModuleIDOffset ];
	setDataP->pecntThreads = appDataP[ kAppDataCntThreadsOffset ];
	setDataP->peth32ParentProcessID = appDataP[ kAppDataParentProcessIDOffset ];
	setDataP->pepcPriClassBase = appDataP[ kAppDataPriClassBaseOffset ];
	setDataP->pedwFlags = appDataP[ kAppDataFlagsOffset ];

	UNLOCKINTARRAY( pAppData, appDataP );
}

void JNISetAppData( JNIEnv *pEnv, AppDataType *appDataP, jintArray pAppData )
{
	jint			*setDataP;

	setDataP = LOCKINTARRAY( pAppData );

	setDataP[ kAppDataHWNDOffset ] = (DWORD) appDataP->hwnd;
	setDataP[ kAppDataHProcessOffset ] = (DWORD) appDataP->pi.hProcess;
	setDataP[ kAppDataHThreadOffset ] = (DWORD) appDataP->pi.hThread;
	setDataP[ kAppDataProcessIDOffset ] = appDataP->pi.dwProcessId;
	setDataP[ kAppDataThreadIDOffset ] = appDataP->pi.dwThreadId;

	setDataP[ kAppDataCntUsageOffset ] = appDataP->pecntUsage;
	setDataP[ kAppDataDefaultHeapIDOffset ] = appDataP->peth32DefaultHeapID;
	setDataP[ kAppDataModuleIDOffset ] = appDataP->peth32ModuleID;
	setDataP[ kAppDataCntThreadsOffset ] = appDataP->pecntThreads;
	setDataP[ kAppDataParentProcessIDOffset ] = appDataP->peth32ParentProcessID;
	setDataP[ kAppDataPriClassBaseOffset ] = appDataP->pepcPriClassBase;
	setDataP[ kAppDataFlagsOffset ] = appDataP->pedwFlags;

	UNLOCKINTARRAY( pAppData, setDataP );
}

#elif defined(macintosh) || defined(__osx__)

#include "CUtils.h"
#include "SFiles.h"

ErrCode fileNameArrayToSpecArray( JNIEnv *pEnv, jobjectArray stringArray,
									long *numSpecs, FSSpec **specArray )
{
	CFSpec			tempSpec( 0, 0, NULL );
	CStr			*tempCStr;
	jstring			pathStringID;
	FSSpec			*specP;
	ErrCode			theErr;
	long			numStrings, i;

	*numSpecs = 0;
	*specArray = NULL;
	specP = NULL;
	theErr = kErrNoErr;

	numStrings = ( pEnv->GetArrayLength )( stringArray );
	if ( numStrings < 1 ) {
		theErr = kErrParamErr;
		goto bail;
	}

	specP = (FSSpec*) CMemory::mmalloc( numStrings * sizeof(FSSpec), _TXL( "fntospa" ) );

	for ( i = 0; i < numStrings; i++ ) {
		pathStringID = (jstring) (pEnv->GetObjectArrayElement)( stringArray, i );

		try {
			tempCStr = new CStr( pEnv, pathStringID );
		}
		catch ( LPCTSTR ee ) {
			continue;
		}

		theErr = SFiles::fullPathNameToSpec( tempCStr, &tempSpec );

		delete tempCStr;

		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "fnatsp.fsmfss", NULL, NULL, theErr, i );
			goto bail;
		}

		tempSpec.putInto( &specP[ i ] );
	}

	*numSpecs = numStrings;

bail:

	if ( theErr != kErrNoErr && specP != NULL ) {
		CMemory::mfree( specP );
		specP = NULL;
	}

	*specArray = specP;

	return theErr;
}

void slamSpecsToArrays( JNIEnv *pEnv, FSSpec *specP, long num, long nameLen,
						jbyteArray pNames, jintArray pVRefs, jintArray pParIDs )
{
	jbyte		*namesP, *origNamesP;
	jboolean	dummy;
	jint		*vRefsP, *origVRefsP, *parIDsP, *origParIDsP;
	long		i, pNamesLen, pVRefsLen, pParIDsLen;

	if ( num <= 0 )
		return;

	pNamesLen = (pEnv->GetArrayLength)( pNames );
	pVRefsLen = (pEnv->GetArrayLength)( pVRefs );
	pParIDsLen = (pEnv->GetArrayLength)( pParIDs );

	if ( pVRefsLen < num )
		num = pVRefsLen;
	if ( pParIDsLen < num )
		num = pParIDsLen;

	origNamesP = namesP = (pEnv->GetByteArrayElements)( pNames, &dummy );
	origVRefsP = vRefsP = (pEnv->GetIntArrayElements)( pVRefs, &dummy );
	origParIDsP = parIDsP = (pEnv->GetIntArrayElements)( pParIDs, &dummy );

	for ( i = 0; i < num; i++ ) {
		*vRefsP++ = specP->vRefNum;
		*parIDsP++ = specP->parID;
		CUtils::pStrcpy( (unsigned char*) namesP, &specP->name[ 0 ] );
		namesP += nameLen;
		specP++;
	}

	(pEnv->ReleaseIntArrayElements)( pParIDs, origParIDsP, 0 );
	(pEnv->ReleaseIntArrayElements)( pVRefs, origVRefsP, 0 );
	(pEnv->ReleaseByteArrayElements)( pNames, origNamesP, 0 );
}

void copyPStringToJBytes( JNIEnv* pEnv, jbyteArray jDest, StringPtr srcP )
{
	jbyte			*destP;
	jboolean		dummy;
	long			destLen, numToCopy;

	destLen = (pEnv->GetArrayLength)( jDest );
	if ( destLen < 1 )
		return;

	destP = (pEnv->GetByteArrayElements)( jDest, &dummy );

	if ( destLen == 1 ) {
		destP[ 0 ] = 0;
		goto bail;
	}

	numToCopy = srcP[ 0 ] + 1;
	if ( numToCopy > destLen )
		numToCopy = destLen;

	BlockMoveData( srcP, destP, numToCopy );
	destP[ 0 ] = numToCopy - 1;

bail:

	(pEnv->ReleaseByteArrayElements)( jDest, destP, 0 );
}

#endif

#if defined(macintosh)

void setJlong1( JNIEnv* pEnv, jlongArray jDestArray, UnsignedWide *val )
{
	jlong		*destArrayP;
	jboolean		dummy;

	if ( (pEnv->GetArrayLength)( jDestArray ) < 1 ) {
		Debugger::debug( __LINE__, "bad setJint1 array len" );
		return;
	}

	destArrayP = (pEnv->GetLongArrayElements)( jDestArray, &dummy );
	destArrayP[ 0 ].h = val->hi;
	destArrayP[ 0 ].l = val->lo;

	(pEnv->ReleaseLongArrayElements)( jDestArray, destArrayP, 0 );
}

void setJlong2( JNIEnv* pEnv, jlongArray jDestArray, UnsignedWide *val0, UnsignedWide *val1 )
{
	jlong		*destArrayP;
	jboolean		dummy;

	if ( (pEnv->GetArrayLength)( jDestArray ) < 2 ) {
		Debugger::debug( __LINE__, "bad setJlong2 array len" );
		return;
	}

	destArrayP = (pEnv->GetLongArrayElements)( jDestArray, &dummy );
	destArrayP[ 0 ].h = val0->hi;
	destArrayP[ 0 ].l = val0->lo;
	destArrayP[ 1 ].h = val1->hi;
	destArrayP[ 1 ].l = val1->lo;

	(pEnv->ReleaseLongArrayElements)( jDestArray, destArrayP, 0 );
}

void getJlong( JNIEnv* pEnv, jlong jSrc, UnsignedWide *dest )
{
	dest->hi = jSrc.h;
	dest->lo = jSrc.l;
	
	return;

	UNUSED( pEnv );
}

#elif defined(__osx__)

void setJlong1( JNIEnv* pEnv, jlongArray jDestArray, UnsignedWide *val )
{
	jlong			*destArrayP;
	jboolean		dummy;
	long long int	upper, lower;

	if ( (pEnv->GetArrayLength)( jDestArray ) < 1 ) {
		Debugger::debug( __LINE__, "bad setJint1 array len" );
		return;
	}

	destArrayP = (pEnv->GetLongArrayElements)( jDestArray, &dummy );

	upper = val->hi;
	upper <<= 32;
	lower = val->lo;
	lower &= 0xFFFFFFFF;
	upper = upper | lower;
	destArrayP[ 0 ] = upper;
	
	(pEnv->ReleaseLongArrayElements)( jDestArray, destArrayP, 0 );
}

void setJlong2( JNIEnv* pEnv, jlongArray jDestArray, UnsignedWide *val0, UnsignedWide *val1 )
{
	jlong			*destArrayP;
	jboolean		dummy;
	long long int	upper, lower;

	if ( (pEnv->GetArrayLength)( jDestArray ) < 2 ) {
		Debugger::debug( __LINE__, "bad setJlong2 array len" );
		return;
	}

	destArrayP = (pEnv->GetLongArrayElements)( jDestArray, &dummy );

	upper = val0->hi;
	upper <<= 32;
	lower = val0->lo;
	lower &= 0xFFFFFFFF;
	upper = upper | lower;
	destArrayP[ 0 ] = upper;
	
	upper = val1->hi;
	upper <<= 32;
	lower = val1->lo;
	lower &= 0xFFFFFFFF;
	upper = upper | lower;
	destArrayP[ 1 ] = upper;

	(pEnv->ReleaseLongArrayElements)( jDestArray, destArrayP, 0 );
}

void getJlong( JNIEnv* pEnv, jlong jSrc, UnsignedWide *dest )
{
	dest->hi = ( ( jSrc >> 32 ) & 0xFFFFFFFF );
	dest->lo = ( jSrc & 0xFFFFFFFF );

	return;

	UNUSED( pEnv );
}

#endif
