/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_MiniBackground_H
#define INC_MiniBackground_H

#include <QDOffscreen.h>
#include "comdefs.h"

typedef struct {
	GWorldPtr		theGWorld;
	Rect			theRect;
	GWorldPtr		saveGWorld;
	GDHandle		saveGDevice;
	Byte			*theBytes;
	PixMapHandle	thePixMapH;
	long			theRowBytes, bitDepth;
} GWorldX, *GWorldXPtr;

ErrCode makeMiniBackground( GWorldXPtr gxP, long width, long height, long pixelDepth );
void setAsPortMiniBackground( GWorldXPtr gxP );
void restorePortMiniBackground( GWorldXPtr gxP );
void disposeMiniBackground( GWorldXPtr gxP );

#endif

