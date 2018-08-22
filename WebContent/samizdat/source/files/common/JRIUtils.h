/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include <NativeLibSupport.h>
#include <jri.h>
#include "comdefs.h"

void slamSpecsToArrays( JRIEnv *env, FSSpec *specP, long num, long nameLen,
							jbyteArray pNames, jintArray pVRefs, jintArray pParIDs );
void javaStringToPString( JRIEnv* env, JRIStringID stringID, StringPtr destP );
void copyJBytesToPString( JRIEnv* env, StringPtr destP, long destLen, jbyteArray byteArray );
void copyPStringToJBytes( JRIEnv* env, jbyteArray jDest, StringPtr srcP );
void setJint1( JRIEnv* env, jintArray jDestArray, long val );
void setJint2( JRIEnv* env, jintArray jDestArray, long val0, long val1 );
void setJint4( JRIEnv* env, jintArray jDestArray, long val0, long val1, long val2, long val3 );
void getJint2( JRIEnv* env, jintArray jSrcArray, unsigned long *val0P, unsigned long *val1P );
void getJint4( JRIEnv* env, jintArray jSrcArray, unsigned long *val0P, unsigned long *val1P,
				unsigned long *val2P, unsigned long *val3P );
void setJlong1( JRIEnv* env, jlongArray jDestArray, UnsignedWide *val );
void setJlong2( JRIEnv* env, jlongArray jDestArray, UnsignedWide *val0, UnsignedWide *val1 );

void getJlong( JRIEnv* env, jlong jSrc, UnsignedWide *dest );

void getJintArrayRegion( JRIEnv* env, jintArray jSrcArray, long start, long numToWrite, long *destArray );
void setJintArrayRegion( JRIEnv* env, jintArray jDestArray, long start, long numToWrite, long *srcArray );
void getLongArrayRegion( JRIEnv* env, jlongArray jSrcArray, long start, long numToWrite, UnsignedWide *destArray );
void setLongArrayRegion( JRIEnv* env, jlongArray jDestArray, long start, long numToWrite, UnsignedWide *srcArray );

void setStringArray1( JRIEnv* env, JRIStringID strID, jstringArray jDestArray, long index );

ErrCode fileNameArrayToSpecArray( JRIEnv *env, jstringArray stringArray,
								long *numSpecs, FSSpec **specArray );

void setStrArrayElementUTF( JRIEnv *pEnv, jstringArray pDestArray, jint index, char *strP, jint strLen );

