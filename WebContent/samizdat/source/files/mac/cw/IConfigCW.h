/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_jconfig_mac_IConfigMRJ */

#ifndef _Included_com_jconfig_mac_IConfigMRJ
#define _Included_com_jconfig_mac_IConfigMRJ
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_jconfig_mac_IConfigMRJ
 * Method:    nICStart
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_mac_IConfigMRJ_nICStart
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_jconfig_mac_IConfigMRJ
 * Method:    nICStop
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_jconfig_mac_IConfigMRJ_nICStop
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_jconfig_mac_IConfigMRJ
 * Method:    nICGetMapEntrySize
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_mac_IConfigMRJ_nICGetMapEntrySize
  (JNIEnv *, jclass);

/*
 * Class:     com_jconfig_mac_IConfigMRJ
 * Method:    nICGetSeed
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_mac_IConfigMRJ_nICGetSeed
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_jconfig_mac_IConfigMRJ
 * Method:    nICCountMapEntries
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_mac_IConfigMRJ_nICCountMapEntries
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_jconfig_mac_IConfigMRJ
 * Method:    nICGetIndMapEntry
 * Signature: (II[B)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_mac_IConfigMRJ_nICGetIndMapEntry
  (JNIEnv *, jclass, jint, jint, jbyteArray);

/*
 * Class:     com_jconfig_mac_IConfigMRJ
 * Method:    nFindMatchesExt
 * Signature: (ILjava/lang/String;I[II[I[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_mac_IConfigMRJ_nFindMatchesExt
  (JNIEnv *, jclass, jint, jstring, jint, jintArray, jint, jintArray, jintArray);

/*
 * Class:     com_jconfig_mac_IConfigMRJ
 * Method:    nFindMatchesFInfo
 * Signature: (IIII[II[B)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_mac_IConfigMRJ_nFindMatchesFInfo
  (JNIEnv *, jclass, jint, jint, jint, jint, jintArray, jint, jbyteArray);

/*
 * Class:     com_jconfig_mac_IConfigMRJ
 * Method:    nFindAppByName
 * Signature: (ILjava/lang/String;[I[I[I[BI[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_mac_IConfigMRJ_nFindAppByName
  (JNIEnv *, jclass, jint, jstring, jintArray, jintArray, jintArray, jbyteArray, jint, jintArray);

/*
 * Class:     com_jconfig_mac_IConfigMRJ
 * Method:    nLaunchURL
 * Signature: (ILjava/lang/String;I[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_mac_IConfigMRJ_nLaunchURL
  (JNIEnv *, jclass, jint, jstring, jint, jobjectArray);

#ifdef __cplusplus
}
#endif
#endif