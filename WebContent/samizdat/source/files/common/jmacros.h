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
	Includes the appropriate set of macros and utility routines,
	depending on which native code architecture we're building.
*/

#ifndef INC_jmacros_H
#define INC_jmacros_H

#if defined(DO_JNI)

	#include "jni_macros.h"
	#include "JNIUtils.h"

#elif defined(DO_RNI2)

	#include "rni_macros.h"
	#include "RNIUtils.h"

#elif defined(DO_RNI1)

	#include "rni_macros.h"
	#include "RNIUtils.h"

#elif defined(DO_JRI)

	#include "jri_macros.h"
	#include "JRIUtils.h"

#endif

#endif


