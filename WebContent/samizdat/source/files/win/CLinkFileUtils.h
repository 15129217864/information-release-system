/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CLINKFILEUTILS_H
#define INC_CLINKFILEUTILS_H

#include "comdefs.h"
#include "CString.h"

/*------------------------------------------------------------------------
CLASS
	CLinkFileUtils

	Uses COM to create and resolve link files.

DESCRIPTION
	Contains two static methods to create and resolve link files.

------------------------------------------------------------------------*/
class CLinkFileUtils {
public:

		///////////////////////
		//
		//  Creates a link file (.lnk) to the given file.
		//	Use the SUCCEEDED(), etc. macros to determine whether
		//	the function has succeeded or not.
		//
		//  [in]	csTargetFile	Full path of the link target.
		//  [in]	csLinkFile		Full path of the new link file.
		//  [in]	csDesc			A description which will be stored with the link file.
		//
	static	HRESULT		createLink( const CStr *csTargetFile, const CStr *csLinkFile, const CStr *csDesc );

		///////////////////////
		//
		//  Resolves the given link file.
		//	Use the SUCCEEDED(), etc. macros to determine whether
		//	the function has succeeded or not.
		//
		//  [in]	hwnd			Used if any dialogs need to be shown to the user.
		//  [in]	csLinkFile		Full path to the link file to be resolved.
		//  [out]	csTargetFile	Set to the full path of the link file's target.
		//	[in]	bUseUI			If TRUE, interaction with the user is possible, such
		//							as showing a dialog box if the file can't be found.
		//							Otherwise, no interaction is allowed.
		//
	static	HRESULT		resolveLink( HWND hwnd, const CStr *csLinkFile, CStr *csTargetFile, BOOL bUseUI );
};

#endif

