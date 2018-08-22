/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SAliases_H
#define INC_SAliases_H

#include "comdefs.h"
#include <shlobj.h>
#include "CString.h"

/*------------------------------------------------------------------------
CLASS
	SAliases

	Used to work with aliases

DESCRIPTION
	Creates and resolves aliases.

------------------------------------------------------------------------*/

class SAliases
{
public:
		///////////////////////
		//
		//  Flags used with iResolveLinkFile()
		//
	typedef enum tageResolveFlags {
		kResolveLinkFileNoUI = 1,
		kResolveLinkFileUI = 2
	} eResolveFlags;

		///////////////////////
		//
		//  Flags used with iCreateVolumeAlias() and iCreateFileAlias()
		//
	typedef enum tageCreateFlags {
		kIgnored = 0
	} eCreateFlags;

		///////////////////////
		//
		//  Resolves an alias
		//
		//  [in]	linkFilePath		The alias to be resolved.
		//  [out]	resolvedFile		On exit, contains the full path of the target file
		//	[in]	flags				If kResolveLinkFileUI, interaction with the user is possible, such
		//								as showing a dialog box if the file can't be found.
		//								Otherwise, no interaction is allowed.
		//
	static	ErrCode iResolveLinkFile( const CStr *linkFilePath, CStr *resolvedFile, eResolveFlags flags );

		///////////////////////
		//
		//  Creates an alias to a volume.
		//
		//  [in]	driveName		The target of the alias
		//  [in]	newAliasPath	The location of the new alias
		//  [in]	description		The description
		//  [in]	flags			Ignored
		//
	static	ErrCode iCreateVolumeAlias( const CStr *driveName, const CStr *newAliasPath,
										const CStr *description, eCreateFlags flags );

		///////////////////////
		//
		//  Creates an alias to a file.
		//
		//  [in]	targetPath		The target of the alias
		//  [in]	newAliasPath	The location of the new alias
		//  [in]	description		The description
		//  [in]	flags			Ignored.
		//
	static	ErrCode iCreateFileAlias( const CStr *targetPath, const CStr *newAliasPath,
										const CStr *description, eCreateFlags flags );
};

#endif

