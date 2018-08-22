/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SIconInfo_H
#define INC_SIconInfo_H

#include "comdefs.h"
#include <commctrl.h>
#include "CString.h"
#include "CIconUtils.h"

/*------------------------------------------------------------------------
CLASS
	SIconInfo

	Used to work with icons

DESCRIPTION
	Gets the icons for files and volumes, and the icons associated with extensions.

------------------------------------------------------------------------*/

class SIconInfo
{
public:

		///////////////////////
		//
		//  Which icon to return, large or small.
		//
	typedef enum tageWhichIcon {
		kGetIconLargeIcon = 1,
		kGetIconSmallIcon = 2
	} eWhichIcon;

		///////////////////////
		//
		//  Returns the icon associated with a volume.
		//
		//  [in]	driveName		The drive name.
		//  [in]	whichIcon		One of the eWhichIcon constants.
		//  [in]	width			The width of the icon.
		//  [in]	height			The height of the icon.
		//  [in]	xform			The transform value of the icon.
		//  [in]	align			The alignment value of the icon.
		//  [out]	bitmapP			On exit, contains the icon pixels
		//
	static	ErrCode iNativeGetVolumeIcon( const CStr *driveName, eWhichIcon whichIcon, long width, long height,
										long xform, long align, long *bitmapP );

		///////////////////////
		//
		//  Returns the icon associated with a file.
		//
		//  [in]	fileName		The file name.
		//  [in]	whichIcon		One of the eWhichIcon constants.
		//  [in]	width			The width of the icon.
		//  [in]	height			The height of the icon.
		//  [in]	xform			The transform value of the icon.
		//  [in]	align			The alignment value of the icon.
		//  [out]	bitmapP			On exit, contains the icon pixels
		//
	static	ErrCode iNativeGetFileIcon( const CStr *fileName, eWhichIcon whichIcon, long width, long height,
										long xform, long align, long *bitmapP );

		///////////////////////
		//
		//  Returns the icon associated with a file extension.
		//
		//  [in]	ext				The extension.
		//  [in]	whichIcon		One of the eWhichIcon constants.
		//  [in]	width			The width of the icon.
		//  [in]	height			The height of the icon.
		//  [in]	xform			The transform value of the icon.
		//  [in]	align			The alignment value of the icon.
		//  [out]	bitmapP			On exit, contains the icon pixels
		//
	static	ErrCode iNativeGetExtIcon( const CStr *ext, eWhichIcon whichIcon, long width, long height,
									long xform, long align, long *bitmapP );
};

#endif
