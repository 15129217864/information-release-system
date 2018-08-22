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
/* Header for class com_jconfig_nix_AppUtilsNixLinux */

#ifndef _Included_com_jconfig_nix_AppUtilsNixLinux
#define _Included_com_jconfig_nix_AppUtilsNixLinux
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_jconfig_nix_AppUtilsNixLinux
 * Method:    nReadLink
 * Signature: (Ljava/lang/String;[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_nix_AppUtilsNixLinux_nReadLink
  (JNIEnv *, jclass, jstring, jobjectArray);

/*
 * Class:     com_jconfig_nix_AppUtilsNixLinux
 * Method:    nStat
 * Signature: (ILjava/lang/String;[I[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_nix_AppUtilsNixLinux_nStat
  (JNIEnv *, jclass, jint, jstring, jintArray, jintArray);

/*
 * Class:     com_jconfig_nix_AppUtilsNixLinux
 * Method:    nStatFS
 * Signature: (Ljava/lang/String;[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_nix_AppUtilsNixLinux_nStatFS
  (JNIEnv *, jclass, jstring, jintArray);

/*
 * Class:     com_jconfig_nix_AppUtilsNixLinux
 * Method:    nGetMntEnt
 * Signature: (Ljava/lang/String;[Ljava/lang/String;I[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_nix_AppUtilsNixLinux_nGetMntEnt
  (JNIEnv *, jclass, jstring, jobjectArray, jint, jintArray);

#ifdef __cplusplus
}
#endif
#endif
