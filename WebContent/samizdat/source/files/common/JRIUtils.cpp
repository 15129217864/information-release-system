/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "JRIUtils.h"
#include <string.h>
#include <stdio.h>
#include "CString.h"
#include "CFSpec.h"
#include "CMemory.h"
#include "SFiles.h"
#include <JRICMacros.h>
#include "Debugger.h"

void setStrArrayElementUTF( JRIEnv *pEnv, jstringArray pDestArray, jint index, char *strP, jint strLen )
{
	JRIStringID			strID;

	strID = JRI_NewStringUTF( pEnv, strP, strLen );
	JRI_SetObjectArrayElement( pEnv, pDestArray, index, strID );
}

ErrCode fileNameArrayToSpecArray( JRIEnv *env, jstringArray stringArray,
								long *numSpecs, FSSpec **specArray )
{
	CFSpec			tempSpec( 0, 0, NULL );
	CStr			*tempCStr;
	JRIStringID		pathStringID;
	FSSpec			*specP;
	ErrCode			theErr;
	long			numStrings, i;

	*numSpecs = 0;
	*specArray = NULL;
	specP = NULL;
	theErr = kErrNoErr;

	numStrings = JRI_GetObjectArrayLength( env, stringArray );
	if ( numStrings < 1 ) {
		theErr = kErrParamErr;
		goto bail;
	}

	specP = (FSSpec*) CMemory::mmalloc( numStrings * sizeof(FSSpec), _TXL( "fileNameArrayToSpecArray" ) );

	for ( i = 0; i < numStrings; i++ ) {
		pathStringID = (JRIStringID) JRI_GetObjectArrayElement( env, stringArray, i );

		try {
			tempCStr = new CStr( env, pathStringID );
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

void getJintArrayRegion( JRIEnv* env, jintArray jSrcArray, long start, long numToWrite, long *destArray )
{
	jint			*src;
	long			i, len;
	
	if ( numToWrite < 1  || start < 0 )
		return;

	len = JRI_GetIntArrayLength( env, jSrcArray );
	if ( len < numToWrite )
		numToWrite = len;

	src = JRI_GetIntArrayElements( env, jSrcArray );
	
	for ( i = 0; i < numToWrite; i++ )
		destArray[ i ] = src[ i + start ];
}

void setJintArrayRegion( JRIEnv* env, jintArray jDestArray, long start, long numToWrite, long *srcArray )
{
	jint			*dest;
	long			i, len;
	
	if ( numToWrite < 1  || start < 0 )
		return;

	len = JRI_GetIntArrayLength( env, jDestArray );
	if ( len < numToWrite )
		numToWrite = len;

	dest = JRI_GetIntArrayElements( env, jDestArray );
	
	for ( i = 0; i < numToWrite; i++ )
		dest[ i + start ] = srcArray[ i ];
}

void getLongArrayRegion( JRIEnv* env, jlongArray jSrcArray, long start, long numToWrite, UnsignedWide *destArray )
{
	jlong			*src;
	long			i, len;
	
	if ( numToWrite < 1  || start < 0 )
		return;

	len = JRI_GetLongArrayLength( env, jSrcArray );
	if ( len < numToWrite )
		numToWrite = len;

	src = JRI_GetLongArrayElements( env, jSrcArray );
	
	for ( i = 0; i < numToWrite; i++ ) {
		destArray[ i ].hi = src[ i + start ].hi;
		destArray[ i ].lo = src[ i + start ].lo;
	}
}

void setLongArrayRegion( JRIEnv* env, jlongArray jDestArray, long start, long numToWrite, UnsignedWide *srcArray )
{
	jlong			*dest;
	long			i, len;
	
	if ( numToWrite < 1  || start < 0 )
		return;

	len = JRI_GetLongArrayLength( env, jDestArray );
	if ( len < numToWrite )
		numToWrite = len;

	dest = JRI_GetLongArrayElements( env, jDestArray );
	
	for ( i = 0; i < numToWrite; i++ ) {
		dest[ i + start ].hi = srcArray[ i ].hi;
		dest[ i + start ].lo = srcArray[ i ].lo;
	}
}

void getJint2( JRIEnv* env, jintArray jSrcArray, unsigned long *val0P, unsigned long *val1P )
{
	jint		*srcArrayP;

	if ( JRI_GetIntArrayLength( env, jSrcArray ) < 2 ) {
		Debugger::debug( __LINE__, "bad getJint2 array len" );
		return;
	}

	srcArrayP = JRI_GetIntArrayElements( env, jSrcArray );
	*val0P = srcArrayP[ 0 ];
	*val1P = srcArrayP[ 1 ];
}

void getJint4( JRIEnv* env, jintArray jSrcArray, unsigned long *val0P, unsigned long *val1P,
				unsigned long *val2P, unsigned long *val3P )
{
	jint		*srcArrayP;

	if ( JRI_GetIntArrayLength( env, jSrcArray ) < 4 ) {
		Debugger::debug( __LINE__, "bad getJint4 array len" );
		return;
	}

	srcArrayP = JRI_GetIntArrayElements( env, jSrcArray );
	*val0P = srcArrayP[ 0 ];
	*val1P = srcArrayP[ 1 ];
	*val2P = srcArrayP[ 2 ];
	*val3P = srcArrayP[ 3 ];
}

void setJlong1( JRIEnv* env, jlongArray jDestArray, UnsignedWide *val )
{
	jlong		*destArrayP;

	if ( JRI_GetLongArrayLength( env, jDestArray ) < 1 ) {
		Debugger::debug( __LINE__, "bad setJint1 array len" );
		return;
	}

	destArrayP = JRI_GetLongArrayElements( env, jDestArray );
	destArrayP[ 0 ].hi = val->hi;
	destArrayP[ 0 ].lo = val->lo;
}

void setJlong2( JRIEnv* env, jlongArray jDestArray, UnsignedWide *val0, UnsignedWide *val1 )
{
	jlong		*destArrayP;

	if ( JRI_GetLongArrayLength( env, jDestArray ) < 2 ) {
		Debugger::debug( __LINE__, "bad setJlong2 array len" );
		return;
	}

	destArrayP = JRI_GetLongArrayElements( env, jDestArray );
	destArrayP[ 0 ].hi = val0->hi;
	destArrayP[ 0 ].lo = val0->lo;
	destArrayP[ 1 ].hi = val1->hi;
	destArrayP[ 1 ].lo = val1->lo;
}

void setJint1( JRIEnv* env, jintArray jDestArray, long val )
{
	jint		*destArrayP;

	if ( JRI_GetIntArrayLength( env, jDestArray ) < 1 ) {
		Debugger::debug( __LINE__, "bad setJint1 array len" );
		return;
	}

	destArrayP = JRI_GetIntArrayElements( env, jDestArray );
	destArrayP[ 0 ] = val;
}

void setJint4( JRIEnv* env, jintArray jDestArray, long val0, long val1, long val2, long val3 )
{
	jint		*destArrayP;

	if ( JRI_GetIntArrayLength( env, jDestArray ) < 4 ) {
		Debugger::debug( __LINE__, "bad setJint4 array len" );
		return;
	}

	destArrayP = JRI_GetIntArrayElements( env, jDestArray );
	destArrayP[ 0 ] = val0;
	destArrayP[ 1 ] = val1;
	destArrayP[ 2 ] = val2;
	destArrayP[ 3 ] = val3;
}

void setJint2( JRIEnv* env, jintArray jDestArray, long val0, long val1 )
{
	jint		*destArrayP;

	if ( JRI_GetIntArrayLength( env, jDestArray ) < 2 ) {
		Debugger::debug( __LINE__, "bad setJint2 array len" );
		return;
	}

	destArrayP = JRI_GetIntArrayElements( env, jDestArray );
	destArrayP[ 0 ] = val0;
	destArrayP[ 1 ] = val1;
}

void getJlong( JRIEnv* env, jlong jSrc, UnsignedWide *dest )
{
	dest->hi = jSrc.hi;
	dest->lo = jSrc.lo;

	return;

	UNUSED( env );
}

void setStringArray1( JRIEnv* env, JRIStringID strID, jstringArray jDestArray, long index )
{
	JRI_SetObjectArrayElement( env, jDestArray, index, strID );
}

void copyPStringToJBytes( JRIEnv* env, jbyteArray jDest, StringPtr srcP )
{
	jbyte		*destP;
	long		destLen, numToCopy;

	destLen = JRI_GetByteArrayLength( env, jDest );
	if ( destLen < 1 )
		return;

	destP = JRI_GetByteArrayElements( env, jDest );

	if ( destLen == 1 ) {
		destP[ 0 ] = 0;
		return;
	}

	numToCopy = srcP[ 0 ] + 1;
	if ( numToCopy > destLen )
		numToCopy = destLen;

	BlockMoveData( srcP, destP, numToCopy );
	destP[ 0 ] = numToCopy - 1;
}

