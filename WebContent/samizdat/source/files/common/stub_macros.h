/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_stub_macros_H
#define INC_stub_macros_H

/*
This file defines the macros used to define the native stub routines:

	EXP
	FUNC
	SFUNC

It does this based on:

	the platform ( _WIN32, __linux__, macintosh )
	the native architecture ( DO_JNI, DO_JRI, DO_RNI1, DO_RNI2 )

On Windows, if the value 'DO_SEH' is defined, the EXP, FUNC, and SFUNC macros
have different values, and the following macros are defined as well:

	SEH_EXP
	SEH_FUNC
	SEH_SFUNC
	SEH_TRY
	SEH_EXCEPT
	CALL_INNER
	INNER_PREFIX
*/

#if defined(DO_JNI)

	#if defined(_WIN32)

		#if defined(DO_SEH)

			#define SEH_EXP							JNIEXPORT

			#define	SEH_FUNC(pkg,clz,mth)			JNICALL Java_com_##pkg##_##clz##_##mth(	\
															JNIEnv *pEnv,	\
															jobject pObj
			#define	SEH_SFUNC(pkg,clz,mth)			JNICALL Java_com_##pkg##_win_##clz##_##mth(	\
															JNIEnv *pEnv,	\
															jclass pObj

			#define EXP
			#define	FUNC(pkg,clz,mth)				INNER_##mth(	\
													JNIEnv *pEnv,	\
													jobject pObj

			#define	SFUNC(pkg,clz,mth)				INNER_##mth(	\
													JNIEnv *pEnv,	\
													jclass pObj

			#define	SEH_TRY							__try
			#define	SEH_EXCEPT(a)					__except ( EXCEPTION_EXECUTE_HANDLER ) {		\
																Debugger::debug( __LINE__, a );				\
																return kErrSEH; }
			#define	CALL_INNER(a)					INNER_##a
			#define	INNER_PREFIX					pEnv, pObj

		#else

			#define EXP								JNIEXPORT


			#define	FUNC(pkg,clz,mth)				JNICALL Java_com_##pkg##_win_##clz##_##mth(	\
													JNIEnv *pEnv,	\
													jobject pObj
			#define	SFUNC(pkg,clz,mth)				JNICALL Java_com_##pkg##_win_##clz##_##mth(	\
													JNIEnv *pEnv,	\
													jclass pObj


		#endif	//	_WIN32

	#elif defined(__linux__)

		#include <jni.h>
		#include <native.h>

		#define EXP							JNIEXPORT

		#define	FUNC(pkg,clz,mth)			JNICALL Java_com_##pkg##_nix_##clz##_##mth( JNIEnv *pEnv, jobject pObj
		#define	SFUNC(pkg,clz,mth)			JNICALL Java_com_##pkg##_nix_##clz##_##mth( JNIEnv *pEnv, jclass pObj


	#elif defined(macintosh) || defined(__osx__)

		#include <jni.h>

		#define	FUNC(retVal,fc)			JNIEXPORT retVal JNICALL Java_com_jconfig_mac_##fc(	\
											JNIEnv *pEnv,	\
											jobject pObj

		#define	SFUNC(retVal,fc)		JNIEXPORT retVal JNICALL Java_com_jconfig_mac_##fc(	\
											JNIEnv *pEnv,	\
											jclass pObj

	#endif	//	JNI platform switch

#elif defined(DO_RNI1) || defined(DO_RNI2)

//	windows is assumed

	#if defined(DO_SEH)
		#define SEH_EXP							__declspec(dllexport)
		
		#define	SEH_FUNC(pkg,clz,mth)			__cdecl com_##pkg##_win_##clz##_##mth(	\
												struct Hcom_jconfig_win_AppUtilsMSVM *pObj

		#define	SEH_SFUNC(pkg,clz,mth)			__cdecl com_##pkg##_win_##clz##_##mth(	\
												struct Hcom_jconfig_win_AppUtilsMSVM *pObj

		#define EXP
		#define	FUNC(pkg,clz,mth)				INNER_##mth(	\
												struct Hcom_jconfig_win_AppUtilsMSVM *pObj

		#define	SFUNC(pkg,clz,mth)				INNER_##mth(	\
												struct Hcom_jconfig_win_AppUtilsMSVM *pObj


		#define	SEH_TRY							__try
		#define	SEH_EXCEPT(a)					__except ( EXCEPTION_EXECUTE_HANDLER ) {		\
															Debugger::debug( __LINE__, a );				\
															return kErrSEH; }
		#define	CALL_INNER(a)					INNER_##a
		#define	INNER_PREFIX					pObj
	#else
		#define EXP								__declspec(dllexport)

		#define	FUNC(pkg,clz,mth)				__cdecl com_##pkg##_win_##clz##_##mth(	\
												struct Hcom_jconfig_win_AppUtilsMSVM *pObj
		#define	SFUNC(pkg,clz,mth)				__cdecl com_##pkg##_win_##clz##_##mth(	\
												struct Hcom_jconfig_win_AppUtilsMSVM *pObj

	#endif

#elif defined(DO_JRI)

//	macintosh is assumed

	#define	FUNC(retVal,fc)			retVal Java_com_jconfig_mac_##fc(	\
										JRIEnv *pEnv,	\
										jref pObj

	#define	SFUNC(retVal,fc)		retVal Java_com_jconfig_mac_##fc(	\
										JRIEnv *pEnv,	\
										jref pObj

#endif

#endif
