/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SIconInfo.h"
#include "CIconUtils.h"
#include "XToolkit.h"
#include "Debugger.h"

ErrCode SIconInfo::iNativeGetExtIcon( const CStr *ext, eWhichIcon whichIcon, long width, long height,
										long xform, long align, long *bitmapP )
{
	HIMAGELIST		hImageList;
	unsigned long	shFlags;
	long			theErr, iconID;

	theErr = kErrNoErr;

	if ( whichIcon == kGetIconSmallIcon )
		shFlags = SHGFI_USEFILEATTRIBUTES | SHGFI_ICON | SHGFI_SYSICONINDEX | SHGFI_SMALLICON;
	else
		shFlags = SHGFI_USEFILEATTRIBUTES | SHGFI_ICON | SHGFI_SYSICONINDEX | SHGFI_LARGEICON;

	hImageList = XToolkit::XSHGetFileInfoIcon( ext, FILE_ATTRIBUTE_NORMAL, shFlags, &iconID );
	if ( hImageList == NULL ) {
		theErr = kErrSHGetFileInfo;
		goto bail;
	}

	theErr = CIconUtils::slamImage( hImageList, iconID, ILD_NORMAL, width, height, bitmapP );

bail:

	return theErr;
}

ErrCode SIconInfo::iNativeGetVolumeIcon( const CStr *driveName, eWhichIcon whichIcon, long width, long height,
											long xform, long align, long *bitmapP )
{
	HIMAGELIST		hImageList;
	unsigned long	shFlags;
	long			theErr, iconID;

	theErr = kErrNoErr;

	if ( whichIcon == kGetIconSmallIcon )
		shFlags = SHGFI_SYSICONINDEX | SHGFI_SMALLICON;
	else
		shFlags = SHGFI_SYSICONINDEX | SHGFI_LARGEICON;

	hImageList = XToolkit::XSHGetFileInfoIcon( driveName, 0, shFlags, &iconID );
	if ( hImageList == NULL ) {
		theErr = kErrSHGetFileInfo;
		goto bail;
	}

	theErr = CIconUtils::slamImage( hImageList, iconID, ILD_NORMAL, width, height, bitmapP );

bail:

	return theErr;
}

ErrCode SIconInfo::iNativeGetFileIcon( const CStr *fullPath, eWhichIcon whichIcon, long width, long height,
										long xform, long align, long *bitmapP )
{
	HIMAGELIST		hImageList;
	unsigned long	shFlags;
	long			theErr, iconID;

	theErr = kErrNoErr;

	if ( whichIcon == kGetIconSmallIcon )
		shFlags = SHGFI_SYSICONINDEX | SHGFI_SMALLICON;
	else
		shFlags = SHGFI_SYSICONINDEX | SHGFI_LARGEICON;

	hImageList = XToolkit::XSHGetFileInfoIcon( fullPath, 0, shFlags, &iconID );
	if ( hImageList == NULL ) {
		theErr = kErrSHGetFileInfo;
		goto bail;
	}

	theErr = CIconUtils::slamImage( hImageList, iconID, ILD_NORMAL, width, height, bitmapP );

bail:

	return theErr;
}





