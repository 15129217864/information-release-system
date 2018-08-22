/****************************************************
	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT
	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement
****************************************************/

/*	Include the appropriate stub header file
	This depends on the native architecture being used (JNI,etc.)
*/

#ifndef INC_AppUtilsMSVM_H
#define INC_AppUtilsMSVM_H

#if defined(DO_JNI)
	#include "JNI_AppUtilsMSVM.h"
#elif defined(DO_RNI2)
	#include "RNI2_AppUtilsMSVM.h"
#elif defined(DO_RNI1)
	#include "RNI1_AppUtilsMSVM.h"
#elif defined(DO_JDK10)
	#include "JDK10_AppUtilsMSVM.h"
#endif

#endif

