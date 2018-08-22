/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_JNIUtils_H
#define INC_JNIUtils_H

#if defined(_WIN32)
	#include <native.h>
#else
	#include <jni.h>
#endif

#include "comdefs.h"
#include "CString.h"

#if defined(_WIN32)
	#include "AppData.h"
#endif

void JNISetStrArrayElement( JNIEnv *pEnv, jobjectArray pArray, jint index, CStr *csStr );
void setJint1( JNIEnv* env, jintArray jDestArray, long val );
void setJint2( JNIEnv* env, jintArray jDestArray, long val0, long val1 );
void setJint4( JNIEnv* env, jintArray jDestArray, long val0, long val1, long val2, long val3 );
void getJint2( JNIEnv* env, jintArray jSrcArray, unsigned long *val0P, unsigned long *val1P );
void getJint4( JNIEnv* pEnv, jintArray jSrcArray, unsigned long *val0P, unsigned long *val1P,
				unsigned long *val2P, unsigned long *val3P );
void setStringArray1( JNIEnv* env, jstring strID, jobjectArray jDestArray, long index );
void setStrArrayElementUTF( JNIEnv *pEnv, jobjectArray pDestArray, jint index, char *strP, jint strLen );

#if defined(_WIN32)
	void JNIGetAppData( JNIEnv *pEnv, AppDataType *appDataP, jintArray pAppData );
	void JNISetAppData( JNIEnv *pEnv, AppDataType *appDataP, jintArray pAppData );
#endif

#if defined(macintosh) || defined(__osx__)
	void slamSpecsToArrays( JNIEnv *env, FSSpec *specP, long num, long nameLen,
								jbyteArray pNames, jintArray pVRefs, jintArray pParIDs );
	ErrCode fileNameArrayToSpecArray( JNIEnv *pEnv, jobjectArray stringArray,
									long *numSpecs, FSSpec **specArray );
	void copyPStringToJBytes( JNIEnv* env, jbyteArray jDest, StringPtr srcP );
	void setJlong1( JNIEnv* env, jlongArray jDestArray, UnsignedWide *val );
	void setJlong2( JNIEnv* env, jlongArray jDestArray, UnsignedWide *val0, UnsignedWide *val1 );

	void getJlong( JNIEnv* pEnv, jlong jSrc, UnsignedWide *dest );
#endif

#endif

