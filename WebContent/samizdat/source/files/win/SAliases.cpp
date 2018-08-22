/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SAliases.h"
#include "CLinkFileUtils.h"

ErrCode SAliases::iCreateVolumeAlias( const CStr *driveName, const CStr *newAliasPath,
										const CStr *description, eCreateFlags flags )
{
	HRESULT		hres;

	hres = CLinkFileUtils::createLink( driveName, newAliasPath, description );

	return SUCCEEDED(hres) ? kErrNoErr : kErrUnimplementedErr;
}

ErrCode SAliases::iResolveLinkFile( const CStr *linkFilePath, CStr *resolvedFile, eResolveFlags flags )
{
	HRESULT		hres;
	
	hres = CLinkFileUtils::resolveLink( GetDesktopWindow(), linkFilePath, resolvedFile, ( flags == kResolveLinkFileUI ) );

	return SUCCEEDED(hres) ? kErrNoErr : kErrUnimplementedErr;
}

ErrCode SAliases::iCreateFileAlias( const CStr *targetPath, const CStr *newAliasPath,
									const CStr *description, eCreateFlags flags )
{
	HRESULT		hres;
	
	hres = CLinkFileUtils::createLink( targetPath, newAliasPath, description );

	return SUCCEEDED(hres) ? kErrNoErr : kErrUnimplementedErr;
}

