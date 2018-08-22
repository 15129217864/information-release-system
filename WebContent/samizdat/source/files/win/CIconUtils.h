/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CIconUtils_H
#define INC_CIconUtils_H

#include "comdefs.h"
#include <commctrl.h>

/*------------------------------------------------------------------------
CLASS
	CIconUtils

	Converts HICON's to pixel arrays.

DESCRIPTION
	Converts HICON's to pixel arrays.

------------------------------------------------------------------------*/

class CIconUtils {
public:
		///////////////////////
		//
		//  Copies the pixel values of an icon into bitmapP,
		//	translating from Windows format into Java's ARGB format.
		//
		//	[in]	himl		passed to ImageList_Draw
		//	[in]	iconID		passed to ImageList_Draw
		//	[in]	style		passed to ImageList_Draw
		//	[in]	width		the width
		//	[in]	height		the height
		//	[in]	bitmapP		must have at least width*height elements
		//
	static ErrCode		slamImage( HIMAGELIST himl, long iconID, unsigned long style,
									long width, long height, long *bitmapP );

private:

	enum {
		kWidthLarge = 32,
		kHeightLarge = 32,
		kWidthSmall = 16,
		kHeightSmall = 16
	};

	static HBITMAP	buildDIBSection( HDC aDC, long width, long height, void **lpBytes );
};

#endif

