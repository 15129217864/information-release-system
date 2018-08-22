/****************************************************
	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT
	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement
****************************************************/

#ifndef INC_CWebBrowser_H
#define INC_CWebBrowser_H

#include "comdefs.h"
#include "CString.h"
#include "CStringVector.h"
/*------------------------------------------------------------------------

CLASS
	SWebBrowser	Used to launch URLs

DESCRIPTION
	Used to launch URLs

------------------------------------------------------------------------*/

class SWebBrowser{
public:

	///////////////////////
	//
	//  Flags for iNativeLaunchURL()
	//
	typedef enum tageLaunchURLFlags {
		kLaunchToFront = 1,
		kLaunchToBack = 2,
		kLaunchMinimize = 3,
		kLaunchMaximize = 4
	} eLaunchURLFlags;
	
	///////////////////////
	//
	//  Launch the given file or http URL
	//
	//  [in]	csURL			The URL to launch
	//  [in]	csTempDir		Indicates a temporary directory. Must be writable.
	//	[in]	flags			One of the eLaunchURLFlags values.
	//
	static	ErrCode		iNativeLaunchURL( const CStr *csURL, const CStr *csTempDir, eLaunchURLFlags flags );

private:
	
	static	const CStr	*gcsHTMExtension, *gcsNotscape, *gcsNotscape2,
						*gcsExploder, *gcsDDEOpenURLCommand, *gcsURLCommandPrefix,
						*gcsURLCommandSuffix, *gcsActivateParms, *gcsDDEActivateCommand;
	
	static	ErrCode		launchDDE( const CStr *csURL, const CStr *csTempDir, eLaunchURLFlags flags, const CStr *csHTMLApp );
	static	ErrCode		launchSE( const CStr *csURL, const CStr *csTempDir, eLaunchURLFlags flags, const CStr *csHTMLApp );
	static	ErrCode		launchWE( const CStr *csURL, const CStr *csTempDir, eLaunchURLFlags flags, const CStr *csHTMLApp );
	static	ErrCode		launchCP( const CStr *csURL, const CStr *csTempDir, eLaunchURLFlags flags, const CStr *csHTMLApp );
	static	ErrCode		ddeHelper( DWORD dwDDE, const CStr *csCommand, const CStr *csServiceName, HWND *pTargetHWND );
	static	ErrCode		ddeConnect( DWORD dwDDE, HSZ hszServName, HSZ hszTopic, const CStr *csCommand );
	static	long		launchFlagsToSWFlags( eLaunchURLFlags flags );
};

#endif
