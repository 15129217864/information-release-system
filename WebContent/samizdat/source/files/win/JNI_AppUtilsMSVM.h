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
/* Header for class com_jconfig_win_AppUtilsMSVM */

#ifndef _Included_com_jconfig_win_AppUtilsMSVM
#define _Included_com_jconfig_win_AppUtilsMSVM
#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetShortPathName
 * Signature: (Ljava/lang/String;[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetShortPathName
  (JNIEnv *, jclass, jstring, jobjectArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetAllProcessInfo
 * Signature: ([I[I[I[I[I[I[I[I[I[I[Ljava/lang/String;I[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetAllProcessInfo
  (JNIEnv *, jclass, jintArray, jintArray, jintArray, jintArray, jintArray, jintArray, jintArray, jintArray, jintArray, jintArray, jobjectArray, jint, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeFindClose
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeFindClose
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeSetVolumeLabel
 * Signature: (Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeSetVolumeLabel
  (JNIEnv *, jclass, jstring, jstring);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetVolumeInformation
 * Signature: (Ljava/lang/String;[Ljava/lang/String;[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetVolumeInformation
  (JNIEnv *, jclass, jstring, jobjectArray, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeLaunchURL
 * Signature: (Ljava/lang/String;Ljava/lang/String;I[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeLaunchURL
  (JNIEnv *, jclass, jstring, jstring, jint, jobjectArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetVolumeCapInfo
 * Signature: (Ljava/lang/String;[J)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetVolumeCapInfo
  (JNIEnv *, jclass, jstring, jlongArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeFindAppsByName
 * Signature: (Ljava/lang/String;II[I[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeFindAppsByName
  (JNIEnv *, jclass, jstring, jint, jint, jintArray, jobjectArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetVolumeDate
 * Signature: (ILjava/lang/String;[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetVolumeDate
  (JNIEnv *, jclass, jint, jstring, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetFileDate
 * Signature: (ILjava/lang/String;[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetFileDate
  (JNIEnv *, jclass, jint, jstring, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeVIPGetFileVersionInfoStart
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeVIPGetFileVersionInfoStart
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetVolumeIcon
 * Signature: (Ljava/lang/String;IIIII[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetVolumeIcon
  (JNIEnv *, jclass, jstring, jint, jint, jint, jint, jint, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetFileIcon
 * Signature: (Ljava/lang/String;IIIII[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetFileIcon
  (JNIEnv *, jclass, jstring, jint, jint, jint, jint, jint, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeCreateVolumeAlias
 * Signature: (Ljava/lang/String;Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeCreateVolumeAlias
  (JNIEnv *, jclass, jstring, jstring, jint);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetVolumeFlags
 * Signature: (Ljava/lang/String;[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetVolumeFlags
  (JNIEnv *, jclass, jstring, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetMainMonitorInfo
 * Signature: ([I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetMainMonitorInfo
  (JNIEnv *, jclass, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetExtIcon
 * Signature: (Ljava/lang/String;IIIII[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetExtIcon
  (JNIEnv *, jclass, jstring, jint, jint, jint, jint, jint, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeLaunchApp
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[II[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeLaunchApp
  (JNIEnv *, jclass, jstring, jstring, jstring, jstring, jintArray, jint, jobjectArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetVolumeReadFlagsMask
 * Signature: (Ljava/lang/String;[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetVolumeReadFlagsMask
  (JNIEnv *, jclass, jstring, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeVIPGetFileVersionInfoEnd
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeVIPGetFileVersionInfoEnd
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetExecutableType
 * Signature: (Ljava/lang/String;[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetExecutableType
  (JNIEnv *, jclass, jstring, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeFindNextFile
 * Signature: (I[I[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeFindNextFile
  (JNIEnv *, jclass, jint, jintArray, jobjectArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeVIPVerQueryValue
 * Signature: (ILjava/lang/String;[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeVIPVerQueryValue
  (JNIEnv *, jclass, jint, jstring, jobjectArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetDriveDisplayName
 * Signature: (Ljava/lang/String;[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetDriveDisplayName
  (JNIEnv *, jclass, jstring, jobjectArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeSetFileAttributes
 * Signature: (Ljava/lang/String;[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeSetFileAttributes
  (JNIEnv *, jclass, jstring, jint, jint);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetFileAttributesMask
 * Signature: (Ljava/lang/String;[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetFileAttributesMask
  (JNIEnv *, jclass, jstring, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetFileAttributesWriteMask
 * Signature: (Ljava/lang/String;[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetFileAttributesWriteMask
  (JNIEnv *, jclass, jstring, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeResolveLinkFile
 * Signature: (Ljava/lang/String;[Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeResolveLinkFile
  (JNIEnv *, jclass, jstring, jobjectArray, jint);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetFileAttributes
 * Signature: (Ljava/lang/String;[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetFileAttributes
  (JNIEnv *, jclass, jstring, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeVerifyNativeAppData
 * Signature: ([I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeVerifyNativeAppData
  (JNIEnv *, jclass, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeCreateFileAlias
 * Signature: (Ljava/lang/String;Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeCreateFileAlias
  (JNIEnv *, jclass, jstring, jstring, jint);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeQuitApp
 * Signature: ([II)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeQuitApp
  (JNIEnv *, jclass, jintArray, jint);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeMoveApp
 * Signature: ([III)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeMoveApp
  (JNIEnv *, jclass, jintArray, jint, jint);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeFindVerbs
 * Signature: ([Ljava/lang/String;Ljava/lang/String;I[I[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeFindVerbs
  (JNIEnv *, jclass, jobjectArray, jstring, jint, jintArray, jobjectArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetAllMonitorInfo
 * Signature: ([II[I)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetAllMonitorInfo
  (JNIEnv *, jclass, jintArray, jint, jintArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeGetVolumes
 * Signature: (I[I[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeGetVolumes
  (JNIEnv *, jclass, jint, jintArray, jobjectArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeFindFirstFile
 * Signature: (Ljava/lang/String;[I[I[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeFindFirstFile
  (JNIEnv *, jclass, jstring, jintArray, jintArray, jobjectArray);

/*
 * Class:     com_jconfig_win_AppUtilsMSVM
 * Method:    nativeFindAppsByExtension
 * Signature: (Ljava/lang/String;Ljava/lang/String;II[I[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_jconfig_win_AppUtilsMSVM_nativeFindAppsByExtension
  (JNIEnv *, jclass, jstring, jstring, jint, jint, jintArray, jobjectArray);

#ifdef __cplusplus
}
#endif
#endif
