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
	Works around a problem with CWPro2 using __int64 multiply from C++
*/

#ifndef INC_UTILS_H
#define INC_UTILS_H

#include "comdefs.h"

void setVolumeCapInfo(
unsigned __int64 *capP,
DWORD sectorsPerClst,
DWORD bytesPerSector,
DWORD numFreeClst,
DWORD totNumClst );

#endif

