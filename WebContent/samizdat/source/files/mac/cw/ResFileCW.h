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
/* Header for class com_jconfig_mac_ResFileMRJ */

#ifndef _Included_com_jconfig_mac_ResFileMRJ
#define _Included_com_jconfig_mac_ResFileMRJ
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_jconfig_mac_ResFileMRJ
 * Method:    nOpenExistingResFile
 * Signature: (II[B)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_mac_ResFileMRJ_nOpenExistingResFile
  (JNIEnv *, jclass, jint, jint, jbyteArray);

/*
 * Class:     com_jconfig_mac_ResFileMRJ
 * Method:    nCloseResFile
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_jconfig_mac_ResFileMRJ_nCloseResFile
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_jconfig_mac_ResFileMRJ
 * Method:    nGetResourceSize
 * Signature: (III[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_mac_ResFileMRJ_nGetResourceSize
  (JNIEnv *, jclass, jint, jint, jint, jintArray);

/*
 * Class:     com_jconfig_mac_ResFileMRJ
 * Method:    nGetResource
 * Signature: (III[B)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_mac_ResFileMRJ_nGetResource
  (JNIEnv *, jclass, jint, jint, jint, jbyteArray);

#ifdef __cplusplus
}
#endif
#endif