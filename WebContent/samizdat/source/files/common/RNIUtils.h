/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_RNIUtils_H
#define INC_RNIUtils_H

#include "comdefs.h"
#include <native.h>

#if defined(_WIN32)
	#include "AppData.h"
#endif

void RNIGetAppData( AppDataType *appDataP, struct HArrayOfInt* pAppData );
void RNISetAppData( AppDataType *appDataP, struct HArrayOfInt* pAppData );
void RNISetIntArrayRegion( struct HArrayOfInt* pArray, long st, long end, long *dataP );
void RNISetLongArrayRegion( struct HArrayOfLong* pArray, long st, long end, __int64 *dataP );
void RNIGetIntArrayRegion( struct HArrayOfInt* pArray, long st, long end, long *dataP );
void RNIGetLongArrayRegion( struct HArrayOfLong* pArray, long st, long end, __int64 *dataP );

#endif


